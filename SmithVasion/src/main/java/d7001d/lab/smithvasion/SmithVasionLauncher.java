/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion;

import d7001d.lab.smithvasion.agents.AgentSmith;
import d7001d.lab.smithvasion.agents.ArchimAgent;
import d7001d.lab.smithvasion.agents.SubCoordAgent;
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
    System.out.println("pick a base name for the agent smith?");
    String smithName = new BufferedReader(new InputStreamReader(System.in)).readLine();
    System.out.println("pick a name for the subCoord?");
    String subCoord = new BufferedReader(new InputStreamReader(System.in)).readLine();
    System.out.println("pick a name for the archimagent?");
    String archimAgent = new BufferedReader(new InputStreamReader(System.in)).readLine();
    jade.core.Runtime rt = jade.core.Runtime.instance();
    Profile p = new ProfileImpl();
    // Create a new non-main container, connecting to the default 
    // main container (i.e. on this host, port 1099) 
    ContainerController cc = rt.createAgentContainer(p);

    /*for (int i = 0; i < 1; i += 1) {
      AgentController smithCtrl = 
          cc.createNewAgent(
              smithName + (i + 1), 
              AgentSmith.class.getCanonicalName(),
              new Object[]{
                InetAddress.getByName("localhost"),
                9876,
                5000l
              });
      smithCtrl.start();
    }*/

    AgentController subCoordCtrl = 
        cc.createNewAgent(
            subCoord, 
            SubCoordAgent.class.getCanonicalName(),
            new Object[]{
              smithName,
            }
    );
    subCoordCtrl.start();
    AgentController archimAgentCtrl = 
        cc.createNewAgent(
            archimAgent, 
            ArchimAgent.class.getCanonicalName(),
            new Object[]{
            }
    );
    archimAgentCtrl.start();
  }

}
