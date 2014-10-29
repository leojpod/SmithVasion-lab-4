/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion.gui.events;

import d7001d.lab.smithvasion.models.PlatformReport;
import jade.gui.GuiEvent;

/**
 *
 * @author leojpod
 */
public interface ArchimEvent{
  public static final int NEW_TARGET = 1;
  public static final int ADD_AGENTS = 2;
  public static final int REMOVE_AGENTS = 3;
  public static final int KILL_PLATFORM = 4;
  
  
  public class NewTargetEvent extends GuiEvent implements ArchimEvent {
    public final String address;
    public final int port;
    
    public NewTargetEvent(Object eventSource, String address, int port) {
      super(eventSource, NEW_TARGET);
      this.address = address;
      this.port = port;
    }
  }

  public static class AddAgentsEvent extends GuiEvent implements ArchimEvent{
    public final PlatformReport platform;
    public final int numOfAgents;

    public AddAgentsEvent(Object eventSource, PlatformReport platform, int numOfAgents) {
      super(eventSource, ADD_AGENTS);
      this.platform = platform;
      this.numOfAgents = numOfAgents;
    }
  }

  public static class RemoveAgentsEvent extends GuiEvent implements ArchimEvent{
    public final PlatformReport platform;
    public final int numOfAgents;

    public RemoveAgentsEvent(Object eventSource, PlatformReport platform, int numOfAgents) {
      super(eventSource, REMOVE_AGENTS);
      this.platform = platform;
      this.numOfAgents = numOfAgents;
    }
  }

  public static class KillCoordEvent extends GuiEvent implements ArchimEvent{
    public final PlatformReport platform;
    
    public KillCoordEvent(Object eventSource, PlatformReport report) {
      super(eventSource, KILL_PLATFORM);
      this.platform = report;
    }
  }
}
