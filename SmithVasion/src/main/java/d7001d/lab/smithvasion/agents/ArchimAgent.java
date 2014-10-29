/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion.agents;

import d7001d.lab.smithvasion.gui.ArchimAgentUI;
import d7001d.lab.smithvasion.gui.events.ArchimEvent;
import d7001d.lab.smithvasion.messages.NewTargetMessage;
import d7001d.lab.smithvasion.models.PlatformReport;
import d7001d.lab.smithvasion.models.SetModel;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.DFSubscriber;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leojpod
 */
public class ArchimAgent extends GuiAgent{
  private static final Logger logger = Logger.getLogger(ArchimAgent.class.getName());
  SetModel<PlatformReport> subCoords;
  
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
    subCoords = ui.plateformsModel;
    
    //Create a dfd template for SubCoordinator
    DFAgentDescription subCoordDfd = new DFAgentDescription();
    ServiceDescription subCoordSd = new ServiceDescription();
    subCoordSd.setType(SubCoordAgent.class.getName());
    subCoordDfd.addServices(subCoordSd);
    //init the subCoord set with the existing agents the subscription will deal with that
//    try {
//      DFAgentDescription[] subCoordAgents;
//      subCoordAgents = DFService.search(this, subCoordDfd);
//      logger.log(Level.INFO, "ArchimAgent has {0} subcoord to start with", subCoordAgents.length);
//      this.subCoords.addAll(Arrays.asList(subCoordAgents));
//    } catch (FIPAException ex) {
//      Logger.getLogger(ArchimAgent.class.getName()).log(Level.SEVERE, null, ex);
//    }
    //feed the template to DFSubscriber to keep track of the subCoord Agents
    this.addBehaviour(new DFSubscriber(this, subCoordDfd) {
      @Override
      public void onRegister(DFAgentDescription dfad) {
        ArchimAgent.this.subCoords.add(new PlatformReport(dfad, 0));
        logger.log(Level.INFO, 
                "ArchimAgent has a new SubCoordinator to talk to ({0})", 
                ArchimAgent.this.subCoords.size());
      }
      @Override
      public void onDeregister(DFAgentDescription dfad) {
        ArchimAgent.this.subCoords.remove(dfad);
        logger.log(Level.INFO, 
                "ArchimAgent losts a new SubCoordinator to talk to ({0})",
                ArchimAgent.this.subCoords.size());
      }
    });
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
