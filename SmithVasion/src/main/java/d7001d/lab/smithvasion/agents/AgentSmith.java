/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion.agents;

import d7001d.lab.smithvasion.messages.*;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leojpod
 */
public class AgentSmith extends Agent{
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
    //"final" parameters for now
    this.addBehaviour(new AttackBehavior(this, attackPeriod));
    
    // ----------------------------------
    // Register to DF
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
    
    logger.log(Level.INFO, "AgentSmith {0} reporting for duty!", this.getLocalName());
    
    // ----------------------------------
  }
  
  private final class AttackBehavior extends TickerBehaviour{
    public AttackBehavior(Agent a, long period) {
      super(a, period);
    }
    @Override
    protected void onTick() {
      try {
        Socket s = new Socket(targetAddress, targetPort);
        logger.log(Level.INFO, "tick!");
        s.getOutputStream().write("42\r\n".getBytes());
        s.getOutputStream().write("Welcome in the matrix Neo\r\n".getBytes());
        s.getOutputStream().flush();
      } catch (IOException ex) {
        logger.log(Level.INFO, "connection failed but we don't really care");
      }
    }
  }
  
  // -----------------------------------------------------
  // Create a cyclic behavior to read newTargetMessages
  public class MessagesReader extends CyclicBehaviour {
		
        // Variable to Hold the content of the received Message
        private String Message_Content;
        private String SenderName;
        private StringBuffer Target_Message;

        @Override
        public void action() {
            //Receive a Message
            ACLMessage msg = receive();
            if(msg != null) {

                Message_Content = msg.getContent();
                SenderName = msg.getSender().getLocalName();
                
                // Check receiver ID 
                if(SenderName == "AgentSmith") {        	
                	Target_Message.append(Message_Content + "\n");              	 
                	logger.log(Level.INFO, "AgentSmith received message!", this.Message_Content);	
                }
                
                System.out.println("Current Message: " + Target_Message);
            }
        }					
  }
  // -----------------------------------------------------
}
