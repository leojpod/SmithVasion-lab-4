/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion.models;

import d7001d.lab.smithvasion.gui.events.ArchimEvent;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import java.util.EventListener;
import java.util.EventObject;
import java.util.Objects;
import javax.swing.event.EventListenerList;

/**
 *
 * @author leojpod
 */
public class PlatformReport {
  public class AgentChangeEvent extends EventObject {
    public AgentChangeEvent(Object source) {
      super(source);
    }
  }
  public class AddAgentEvent extends EventObject {
    public final PlatformReport report;
    public final int numOfAgents;
    public AddAgentEvent(Object source, PlatformReport report, int numOfAgents) {
      super(source);
      this.report = report; this.numOfAgents = numOfAgents;
    }
  }
  public interface AgentChangeEventListener extends EventListener {
    public void agentChangeEventOccurred(AgentChangeEvent evt);
  }
  public interface AgentRequestEventListener extends EventListener {
    public void addAgentEventOccurred(ArchimEvent.AddAgentsEvent evt);

    public void removeAgentEventOccurred(ArchimEvent.RemoveAgentsEvent evt);

    public void killCoordEventOccurred(ArchimEvent.KillCoordEvent evt);
  }
  
  protected EventListenerList listenerList = new EventListenerList();
  protected EventListenerList agentRequestListeners = new EventListenerList();
  public final String name;
  private int numAgents;
  public final DFAgentDescription dfd;
  
  public PlatformReport(DFAgentDescription dfd, int numAgents) {
    this.name = dfd.getName().getName(); this.numAgents = numAgents;
    this.dfd = dfd;
  }
  public PlatformReport(String name, int numAgents) {
    this.name = name; this.numAgents = numAgents;
    this.dfd = null;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof PlatformReport) {
      PlatformReport platformReport = (PlatformReport) obj;
      return this.dfd.getName().equals(platformReport.dfd.getName());
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 71 * hash + Objects.hashCode(this.name);
    return hash;
  }
  
  public Integer getNumAgents() {
    return this.numAgents;
  }
  public void setNumAgents(int numAgents) {
    this.numAgents = numAgents;
    fireAgentChangeEvent(new AgentChangeEvent(this));
  }
  
  public void addAgentChangeEventListener(AgentChangeEventListener listener) {
    listenerList.add(AgentChangeEventListener.class, listener);
  }
  public void removeAgentChangeEventListener(AgentChangeEventListener listener) {
    listenerList.remove(AgentChangeEventListener.class, listener);
  }
  public void addAgentRequestEventListener(AgentRequestEventListener listener) {
    listenerList.add(AgentRequestEventListener.class, listener);
  }
  public void removeAgentRequestEventListener(AgentRequestEventListener listener) {
    listenerList.remove(AgentRequestEventListener.class, listener);
  }
  void fireAgentChangeEvent(AgentChangeEvent evt) {
    for (AgentChangeEventListener listener: this.listenerList.getListeners(AgentChangeEventListener.class)) {
      listener.agentChangeEventOccurred(evt);
    }
  }
  
  public void fireAchimEvent(ArchimEvent.AddAgentsEvent evt) {
    for (AgentRequestEventListener listener: this.listenerList.getListeners(AgentRequestEventListener.class)) {
      listener.addAgentEventOccurred(evt);
    }
  }
  public void fireAchimEvent(ArchimEvent.RemoveAgentsEvent evt) {
    for (AgentRequestEventListener listener: this.listenerList.getListeners(AgentRequestEventListener.class)) {
      listener.removeAgentEventOccurred(evt);
    }
  }
  public void fireAchimEvent(ArchimEvent.KillCoordEvent evt) {
    for (AgentRequestEventListener listener: this.listenerList.getListeners(AgentRequestEventListener.class)) {
      listener.killCoordEventOccurred(evt);
    }
  }
}
