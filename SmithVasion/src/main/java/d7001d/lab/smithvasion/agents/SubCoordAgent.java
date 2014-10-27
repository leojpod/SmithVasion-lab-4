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
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leojpod
 */
public class SubCoordAgent extends Agent{
  private static final Logger logger = Logger.getLogger(SubCoordAgent.class.getName());
  
  @Override
  protected void setup() {
    // register to DF
    DFAgentDescription dfd = new DFAgentDescription();
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
              //TODO pass on this message to all the listening instances of AgentSmith
              
              // ---------------------------------------------------------------
              // Send a ACL message to AgentSmith
              ACLMessage out_msg = new ACLMessage(ACLMessage.INFORM);
              out_msg.addReceiver(new AID("AgentSmith", AID.ISLOCALNAME));
              out_msg.setContent(newTargetMsg.toString());
              send(out_msg);
              
              logger.log(Level.INFO, "Pass a message to AgentSmith ", newTargetMsg);
              
              // ---------------------------------------------------------------
              
              
              //but for now:
              logger.log(Level.INFO, 
                      "Received a new Target order from the Architect!\r\n\t {0}",
                      newTargetMsg);
            }
          }
        } catch (WrongPerformativeException | NoSuchMessageException ex) {
          logger.log(Level.SEVERE, null, ex);
        }
        
      }
    });
    // end
  }
  
  
  @Override
  protected void takeDown() {
    try { DFService.deregister(this); }
    catch (Exception e) {}
  }
}
