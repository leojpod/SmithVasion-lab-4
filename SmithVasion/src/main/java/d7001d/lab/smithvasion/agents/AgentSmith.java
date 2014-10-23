/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion.agents;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import java.io.IOException;
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
    if (args.length > 3) {
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
    }
  }
  
  @Override
  protected void setup() {
    Object[] args = this.getArguments();
    parseParams(args);
    //"final" parameters for now
    this.addBehaviour(new AttackBehavior(this, attackPeriod));
  }
  
  private final class AttackBehavior extends TickerBehaviour{
    public AttackBehavior(Agent a, long period) {
      super(a, period);
    }
    @Override
    protected void onTick() {
      try {
        Socket s = new Socket(targetAddress, targetPort);
        s.getOutputStream().write("42\r\n".getBytes());
        s.getOutputStream().write("Welcome in the matrix Neo\r\n".getBytes());
        s.getOutputStream().flush();
      } catch (IOException ex) {
        logger.log(Level.SEVERE, null, ex);
      }
    }
  }
  
}
