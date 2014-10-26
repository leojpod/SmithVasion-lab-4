/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion.agents;

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
        ACLMessage msg = receive();
        
      }
    });
    // end
  }
  
  
}
