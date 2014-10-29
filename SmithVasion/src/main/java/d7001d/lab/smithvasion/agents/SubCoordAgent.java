/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion.agents;

import d7001d.lab.smithvasion.exceptions.NoSuchMessageException;
import d7001d.lab.smithvasion.exceptions.WrongPerformativeException;
import d7001d.lab.smithvasion.messages.NewTargetMessage;
import d7001d.lab.smithvasion.messages.SmithVasionMessageAbs;
import d7001d.lab.smithvasion.messages.SmithVasionMessageFactory;
import jade.core.AID;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import static jade.domain.DFService.search;
import jade.domain.DFSubscriber;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.SearchConstraints;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SubscriptionInitiator;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
  private static final int nSmithLaunch = 3;
  
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
    this.launchDefault(nSmithLaunch);
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
              NewTargetMessage newTargetMsg = (NewTargetMessage) message;
              sendToAllSmith(newTargetMsg);
              
              logger.log(Level.INFO, 
                      "Received a new Target order from the Architect!\r\n\t {0}",
                      newTargetMsg);
            }
          }
        } catch (WrongPerformativeException | NoSuchMessageException ex) {
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
  
  protected DFAgentDescription[] getAllAgent(Agent a) {
    try {
      DFAgentDescription dfdSmith = new DFAgentDescription();
      ServiceDescription sd = new ServiceDescription();
      sd.setType(AgentSmith.class.getName());
      dfdSmith.addServices(sd);
      DFAgentDescription[] result = search( this , dfdSmith);
      return result;
    } catch (FIPAException ex) {
      logger.log(Level.SEVERE, null, ex);
      return null;
    }
  }
  
  public void sendToAllSmith(NewTargetMessage msg) {
    DFAgentDescription[] dfAgentTab = this.getAllAgent((Agent)new AgentSmith());
    
    ACLMessage aclMsg = msg.createACLMessage();
    
    for (DFAgentDescription dfAgent: dfAgentTab) {
      
      aclMsg.addReceiver(dfAgent.getName());
    }
    this.send(aclMsg);
  }
  
  /**
   * 
   * @param nSmith Number of Smith instance to launch
   */
  protected void launchDefault(int nSmith) {
    
    for (int i = 0; i < nSmith; i += 1) {
      AgentController smithCtrl;
      try {
        smithCtrl = containerController.createNewAgent(
                this.baseSmithName + (i + 1),
                AgentSmith.class.getCanonicalName(),
                new Object[]{
                  null,
                  9876,
                  5000l
                });
        
        smithCtrl.start();
      } catch (StaleProxyException ex) {
        logger.log(Level.SEVERE, null, ex);
      }
    }
  }
  
  protected void createContainer(String containerName) {
    jade.core.Runtime rt = jade.core.Runtime.instance();
    profile = new ProfileImpl();
    profile.setParameter(Profile.CONTAINER_NAME, containerName); 
    // Create a new non-main container, connecting to the default 
    // main container (i.e. on this host, port 1099) 
    containerController = rt.createAgentContainer(profile);
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
    
}
