/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion.agents;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import java.net.URL;
import java.util.logging.Logger;

/**
 *
 * @author leojpod
 */
public class AgentSmith extends Agent{
  private static final Logger logger = Logger.getLogger(AgentSmith.class.getName());
  private long attackPeriod;
  private URL ip;
  
  private void parseParams(Object[] args) {
    
  }
  
  @Override
  protected void setup() {
    Object[] args = this.getArguments();
    parseParams(args);
  }
  
  private final class AttackBehavior extends TickerBehaviour{

    public AttackBehavior(Agent a, long period) {
      super(a, period);
    }

    @Override
    protected void onTick() {
      //TODO Not supported yet
      throw new UnsupportedOperationException("Not supported yet.");
    }
  }
  
}
