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
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leojpod
 */
public class AgentSmith extends Agent {
  private static final Logger logger = Logger.getLogger(AgentSmith.class.getName());
  private long attackPeriod;
  private int targetPort;
  private InetAddress targetAddress;
  
  private void parseParams(Object[] args) {
    if (args.length >= 3) {
      try {
        targetAddress = (InetAddress) args[0];
      } catch (ClassCastException ex) {
        logger.log(Level.SEVERE, "Wrong target address parameter!", ex);
        targetAddress = null;
        return;
      }
      try {
        targetPort = (int) args[1];
      } catch (ClassCastException ex) {
        logger.log(Level.INFO, "Wrong target port parameter", ex);
      }
      try {
        attackPeriod = (long) args[2];
      } catch (ClassCastException ex) {
        logger.log(Level.INFO, "Wrong attack period parameter", ex);
      }
    } else {
      logger.log(Level.INFO, "Not enough parameter to start working!");
    }
  }
  
  @Override
  protected void setup() {
    Object[] args = this.getArguments();
    parseParams(args);
    
        // register to DF
    DFAgentDescription dfd = new DFAgentDescription();
    ServiceDescription sd = new ServiceDescription();
    sd.setType(AgentSmith.class.getName());
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
    
    //"final" parameters for now
    this.addBehaviour(new AttackBehavior(this, attackPeriod));
    this.addBehaviour(new UpdateTarget(this));
  }
    
  private final class UpdateTarget extends CyclicBehaviour {

    public UpdateTarget(Agent a) {
      super(a);
    }

    @Override
    public void action() {
      try {
        ACLMessage msg = receive();
        if (msg != null) {
          SmithVasionMessageAbs message = SmithVasionMessageFactory.fromACLMessage(msg);
          //use instance of to find if this is a message this agent should handle
       
          if (message instanceof NewTargetMessage) {
            NewTargetMessage newTargetMsg = (NewTargetMessage) message;

            logger.log(Level.INFO, 
                    "Received a new Target order from the SubCoord!\r\n\t {0}",
                    newTargetMsg );
            
            //Didn't get why targetAddress in newTargetMsg is a String but I deal with it here
            InetAddress newTargetAddress = null;
            try {
              newTargetAddress = InetAddress.getAllByName(newTargetMsg.targetAddress)[0];
            } catch (UnknownHostException ex) {
              logger.log(Level.SEVERE, null, ex);
            }
            
            //Set new target of Smith
            setTargetAddress(newTargetAddress);
            setTargetPort(newTargetMsg.targetPort);
          }
          
        }
      } catch (WrongPerformativeException | NoSuchMessageException ex) {
        logger.log(Level.SEVERE, null, ex);
      }
      
      block();
    }
  }
  
  private final class AttackBehavior extends TickerBehaviour{
    public AttackBehavior(Agent a, long period) {
      super(a, period);
    }
    @Override
    protected void onTick() {
      if (targetAddress != null) {
        
      try {
          Socket s = new Socket(targetAddress, targetPort);
          logger.log(Level.INFO, "tick!");
          s.getOutputStream().write("42\r\n".getBytes());
          s.getOutputStream().write("Welcome in the matrix Neo\r\n".getBytes());
          s.getOutputStream().flush();
        } catch (IOException ex) {
          logger.log(Level.INFO, "connection failed on {0} but we don't really care", targetAddress);
        }
      } else {
        logger.log(Level.INFO, "Target address unset. Do nothing");
        
      }
    }
  }
  
  public void setTargetPort(int targetPort) {
    this.targetPort = targetPort;
  }

  public void setTargetAddress(InetAddress targetAddress) {
    this.targetAddress = targetAddress;
  }
  
}
