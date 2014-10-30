/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion;

import d7001d.lab.smithvasion.agents.ArchimAgent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.io.IOException;

/**
 *
 * @author leojpod
 */
public class StartArchimagent {
  public final static void main(String[] args) throws IOException, StaleProxyException {
    String remote = "54.171.195.96";
    int port = 1099;
    if (args.length == 2 ) {
      remote = args[0];
      port = Integer.parseInt(args[1]);
    }
    jade.core.Runtime rt = jade.core.Runtime.instance();
    Profile remoteProfile = new ProfileImpl(remote, port, "smithvasion", false);
    
    ContainerController cc = rt.createAgentContainer(remoteProfile);
    AgentController archimAgentCtrl = 
            cc.createNewAgent(
                    "archimage" + (int) (Math.random() * 1000), 
                    ArchimAgent.class.getCanonicalName(),
                    new Object[0]
            );
    archimAgentCtrl.start();
  }
}
