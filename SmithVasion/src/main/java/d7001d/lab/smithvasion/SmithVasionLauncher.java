/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion;

import d7001d.lab.smithvasion.agents.AgentSmith;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leojpod
 */
public class SmithVasionLauncher {
  private static final Logger logger = Logger.getLogger(SmithVasionLauncher.class.getName());
  public static final void main(String[] args) throws StaleProxyException, IOException {
    System.out.println("pick a name for agent smith?");
    String smithName = new BufferedReader(new InputStreamReader(System.in)).readLine();
    jade.core.Runtime rt = jade.core.Runtime.instance();
    Profile p = new ProfileImpl(); 
    // Create a new non-main container, connecting to the default 
    // main container (i.e. on this host, port 1099) 
    ContainerController cc = rt.createAgentContainer(p); 
    
    logger.log(Level.INFO, "Agent Smith name? -> {0}", AgentSmith.class.getCanonicalName());
    AgentController smithCtrl = 
            cc.createNewAgent(smithName, AgentSmith.class.getCanonicalName(), 
                    new Object[]{
                      InetAddress.getByName("localhost"),
                      9876,
                      5000l
                    });
    smithCtrl.start();
  }
  
}
