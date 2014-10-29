/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion.agents;

import d7001d.lab.smithvasion.gui.ArchimAgentUI;
import d7001d.lab.smithvasion.gui.events.ArchimEvent;
import d7001d.lab.smithvasion.messages.NewTargetMessage;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leojpod
 */
public class ArchimAgent extends GuiAgent{
  private static final Logger logger = Logger.getLogger(ArchimAgent.class.getName());
  
  @Override
  protected void setup() {
    
    // register to DF
    DFAgentDescription dfd = new DFAgentDescription();
    ServiceDescription sd = new ServiceDescription();
    sd.setType(ArchimAgent.class.getName());
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
    
    
    ArchimAgentUI ui = new ArchimAgentUI();
    ui.setAgent(this);
    ui.setVisible(true);
  }

  
  
  @Override
  protected void onGuiEvent(GuiEvent ge) {
    if (ge instanceof ArchimEvent) {
      ArchimEvent archimEvent = (ArchimEvent) ge;
      if (archimEvent instanceof ArchimEvent.NewTargetEvent) {
        ArchimEvent.NewTargetEvent newTargetEvent = (ArchimEvent.NewTargetEvent) archimEvent;
        this.addBehaviour(new SendNewTargetBehaviour(newTargetEvent.address, newTargetEvent.port));
      }
    } else {
      logger.log(Level.WARNING, "Received an unexpected UI event! should not be possible");
    }
  }
  
  private DFAgentDescription[] searchByType(String type) {
    DFAgentDescription dfd = new DFAgentDescription();
    ServiceDescription sd = new ServiceDescription();
    sd.setType(type);
    dfd.addServices(sd);
    try {
      return DFService.search(this, dfd);
    } catch (FIPAException ex) {
      logger.log(Level.SEVERE, null, ex);
      return new DFAgentDescription[]{};
    }
  }
  
  public class SendNewTargetBehaviour extends OneShotBehaviour {
    private final String address;
    private final int port;
    private SendNewTargetBehaviour(String address, int port) {
      this.address = address;
      this.port = port;
    }

    @Override
    public void action() {
      NewTargetMessage targetMsg = new NewTargetMessage(address, port);
      DFAgentDescription[] subCoordsDesc = ArchimAgent.this.searchByType(SubCoordAgent.class.getName());
      ACLMessage msg = targetMsg.createACLMessage();
      for (DFAgentDescription subCoord : subCoordsDesc) {
        msg.addReceiver(subCoord.getName());
      }
      ArchimAgent.this.send(msg);
    }
    
  }
}
