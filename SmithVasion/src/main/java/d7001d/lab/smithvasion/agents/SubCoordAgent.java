/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion.agents;

import d7001d.lab.smithvasion.exceptions.NoSuchMessageException;
import d7001d.lab.smithvasion.exceptions.WrongPerformativeException;
import d7001d.lab.smithvasion.messages.AddAgentsMessage;
import d7001d.lab.smithvasion.messages.KillAgentMessage;
import d7001d.lab.smithvasion.messages.KillCoordMessage;
import d7001d.lab.smithvasion.messages.NewTargetMessage;
import d7001d.lab.smithvasion.messages.RemoveAgentsMessage;
import d7001d.lab.smithvasion.messages.SmithVasionMessageAbs;
import d7001d.lab.smithvasion.messages.SmithVasionMessageFactory;
import jade.core.AID;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import static jade.domain.DFService.search;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leojpod
 */
public class SubCoordAgent extends Agent {
  private static final Logger logger = Logger.getLogger(SubCoordAgent.class.getName());
  
  private Agent[] smithTab;
  private ProfileImpl profile;
  private AgentContainer containerController;
  private DFAgentDescription dfd;
  private NewTargetMessage currentTarget = new NewTargetMessage(null, 1099);
  private int startingSmith = 0,
          stopingSmith = 0;
  
  private String baseSmithName;
  
  @Override
  protected void setup() {
    
    //Get arguments
    Object[] args = this.getArguments();
    parseParams(args);
    
    // register to DF
    this.dfd = new DFAgentDescription();
    ServiceDescription sd = new ServiceDescription();
    sd.setType(SubCoordAgent.class.getName());
    sd.setName(this.getLocalName());
    dfd.setName(this.getAID());
    dfd.addServices(sd);
    
    try {
      DFService.register(this, dfd);
    } catch (FIPAException ex) {
      logger.log(Level.SEVERE, "{0} registration to the DF failed", this.getLocalName());
      this.doDelete();
      return;
    }
    this.createContainer("subCoordContainer");
//    this.launchDefault(nSmithLaunch);
    logger.log(Level.INFO, "SubCoordAgent {0} reporting for duty!", this.getLocalName());
    
    
    // create cycling behaviour
    this.addBehaviour(new CyclicBehaviour() {
      @Override
      public void action() {
        try {
          
          ACLMessage msg = receive();
          if (msg != null) {
            SmithVasionMessageAbs message = SmithVasionMessageFactory.fromACLMessage(msg);
            //use instance of to find if this is a message this agent should handle
            if (message instanceof NewTargetMessage) {
              SubCoordAgent.this.currentTarget = (NewTargetMessage) message;
              SubCoordAgent.this.addBehaviour(new OneShotBehaviour() {
                @Override
                public void action() {
                  DFAgentDescription[] dfAgentTab = SubCoordAgent.this.getAllAgentSmith();
                  ACLMessage aclMsg = SubCoordAgent.this.currentTarget.createACLMessage();
                  for (DFAgentDescription dfAgent: dfAgentTab) {
                    aclMsg.addReceiver(dfAgent.getName());
                  }
                  SubCoordAgent.this.send(aclMsg);
                }
              });
              logger.log(Level.INFO, 
                      "Received a new Target order from the Architect!\r\n\t {0}",
                      SubCoordAgent.this.currentTarget);
            } else if (message instanceof AddAgentsMessage) {
              AddAgentsMessage addAgentsMessage = (AddAgentsMessage) message;
              SubCoordAgent.this.addBehaviour(new StartSmiths(addAgentsMessage.numOfAgents));
              logger.log(Level.INFO, "Adding {0} agents to this SubCoord", addAgentsMessage.numOfAgents);
            } else if (message instanceof RemoveAgentsMessage) {
              RemoveAgentsMessage removeAgentsMessage = (RemoveAgentsMessage) message;
              SubCoordAgent.this.addBehaviour(new RemoveSmiths(removeAgentsMessage.numOfAgents));
            } else if (message instanceof KillCoordMessage) {
              SubCoordAgent.this.addBehaviour(new OneShotBehaviour() {
                @Override
                public void action() {
                  //kill all the smith!
                  ACLMessage msg = new KillAgentMessage().createACLMessage();
                  while(SubCoordAgent.this.stopingSmith < SubCoordAgent.this.startingSmith) {
                    String name = SubCoordAgent.this.getSmithName(SubCoordAgent.this.stopingSmith);
                    SubCoordAgent.this.stopingSmith += 1;
                    msg.addReceiver(new AID(name, false));
                  }
                  SubCoordAgent.this.send(msg);
                  try {
                    SubCoordAgent.this.containerController.kill();
                  } catch (StaleProxyException ex) {
                    Logger.getLogger(SubCoordAgent.class.getName()).log(Level.SEVERE, null, ex);
                  }
                  SubCoordAgent.this.doDelete();
                }
              });
            } else {
              logger.log(Level.INFO, "Got a not expected message");
            }
          }
        } catch (NoSuchMessageException ex) {
          logger.log(Level.INFO, "non SmithVasion message received");
        } catch (WrongPerformativeException ex) {
          logger.log(Level.SEVERE, null, ex);
        }
        block();
      }
    });
    
  }
  
  @Override
  protected void takeDown() {
    try { DFService.deregister(this); }
    catch (Exception e) {}
  }
  
  protected DFAgentDescription[] getAllAgentSmith() {
    List<DFAgentDescription> agentsDfd = new ArrayList<>();
    try {
      for (int i = 0; i < (SubCoordAgent.this.startingSmith / 100) + 1; i ++) {
        DFAgentDescription dfdSmith = new DFAgentDescription();
        ServiceDescription sd = new ServiceDescription();
        sd.setType(AgentSmith.class.getName());
        sd.setOwnership(this.getOwnerShipName(i));
        dfdSmith.addServices(sd);
        DFAgentDescription[] result = search( this , dfdSmith);
        System.out.println("********* found " + result.length + " agents for " + this.getOwnerShipName(i));
        agentsDfd.addAll(Arrays.asList(result));
      }
      System.out.println("############# total of " + agentsDfd.size() + " agents");
      return agentsDfd.toArray(new DFAgentDescription[0]);
    } catch (FIPAException ex) {
      logger.log(Level.SEVERE, null, ex);
      return null;
    }
  }
  
  /**
   * 
   * @param nSmith Number of Smith instance to launch
   */
//  protected void launchDefault(int nSmith) {
//    
//    for (int i = 0; i < nSmith; i += 1) {
//      AgentController smithCtrl;
//      try {
//        smithCtrl = containerController.createNewAgent(
//                this.baseSmithName + (i + 1),
//                AgentSmith.class.getCanonicalName(),
//                new Object[]{
//                  null,
//                  9876,
//                  5000l
//                });
//        
//        smithCtrl.start();
//      } catch (StaleProxyException ex) {
//        logger.log(Level.SEVERE, null, ex);
//      }
//    }
//  }
  
  protected void createContainer(String containerName) {
    jade.core.Runtime rt = jade.core.Runtime.instance();
    profile = new ProfileImpl();
    profile.setParameter(Profile.CONTAINER_NAME, this.getLocalName() + "-SmithContainer"); 
    // Create a new non-main container, connecting to the default 
    // main container (i.e. on this host, port 1099) 
    containerController = rt.createAgentContainer(profile);
  }

  public String getSmithName(int idx) {
    String agent = this.getLocalName() +
            "Smith" + (idx + 1);
    return agent;
  }
  
  private void parseParams(Object[] args) {
    if (args.length >= 1) {
      try {
        this.baseSmithName = (String) args[0];
      } catch (ClassCastException ex) {
        logger.log(Level.SEVERE, "Wrong base Smith name parameter! Default in use \"smith\"");
        this.baseSmithName = "smith";
      }
    } else {
      logger.log(Level.INFO, "No base name in parameter. Default in use \"smith\"");
      this.baseSmithName = "smith";
    }
  }

  private String getOwnerShipName(int idx) {
    return this.getName() + "gp" +((idx / 100) + 1);
  }
  
  private class StartSmiths extends OneShotBehaviour{
    public final int numOfAgents;

    public StartSmiths(int numOfAgents) {
      this.numOfAgents = numOfAgents;
    }
    @Override
    public void action() {
      AgentController smithCtrl;
      boolean under500 = SubCoordAgent.this.startingSmith - SubCoordAgent.this.stopingSmith < 500;
      for (int i = 0; i < numOfAgents; i ++) {
        try {
          //create a smith
          smithCtrl = containerController.createNewAgent(
                  SubCoordAgent.this.getSmithName(SubCoordAgent.this.startingSmith),
                  AgentSmith.class.getCanonicalName(),
                  new Object[]{
                    SubCoordAgent.this.currentTarget.targetAddress,
                    SubCoordAgent.this.currentTarget.targetPort,
                    5000l,
                    SubCoordAgent.this.getOwnerShipName(SubCoordAgent.this.startingSmith)
                  });
          System.out.println("ownership -> " + SubCoordAgent.this.getOwnerShipName(SubCoordAgent.this.startingSmith));
          SubCoordAgent.this.startingSmith += 1;
          smithCtrl.start();
        } catch (StaleProxyException ex) {
          logger.log(Level.SEVERE, null, ex);
        }
      }
      //less that 500 agents before
      if (under500) {
        //more than 500 agents now?
        under500 = SubCoordAgent.this.startingSmith - SubCoordAgent.this.stopingSmith < 500;
        if (!under500) {
          try {
            Runtime.getRuntime().exec("bash newSubCoord.sh");
          } catch (IOException ex) {
            Logger.getLogger(SubCoordAgent.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
      }
    }
  }
  public class RemoveSmiths extends OneShotBehaviour{
    public final int numOfAgents;

    public RemoveSmiths(int numOfAgents) {
      this.numOfAgents = numOfAgents;
    }
    @Override
    public void action() {
      ACLMessage msg = new KillAgentMessage().createACLMessage();
      for (int i = 0; 
              i < numOfAgents && 
              SubCoordAgent.this.stopingSmith < SubCoordAgent.this.startingSmith;
              i += 1) {
        String agent = getSmithName(SubCoordAgent.this.stopingSmith);
        SubCoordAgent.this.stopingSmith += 1;
        msg.addReceiver(new AID(agent, false));
      }
      SubCoordAgent.this.send(msg);
    }
  }
}
