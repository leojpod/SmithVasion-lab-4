/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion.models;

import java.util.EventListener;
import java.util.EventObject;
import javax.swing.event.EventListenerList;

/**
 *
 * @author leojpod
 */
public class PlateformReport {
  public class AgentChangeEvent extends EventObject {
    public AgentChangeEvent(Object source) {
      super(source);
    }
  }
  public interface AgentChangeEventListener extends EventListener {
    public void agentChangeEventOccurred(AgentChangeEvent evt);
  }
  
  protected EventListenerList listenerList = new EventListenerList();
  public final String name;
  private int numAgents;
  //public SomethingFromJade id;
  
  public PlateformReport(String name, int numAgents) {
    this.name = name; this.numAgents = numAgents;
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
  void fireAgentChangeEvent(AgentChangeEvent evt) {
    for (AgentChangeEventListener listener: this.listenerList.getListeners(AgentChangeEventListener.class)) {
      listener.agentChangeEventOccurred(evt);
    }
  }
}
