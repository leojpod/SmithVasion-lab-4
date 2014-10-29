/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.ContainerController;

/**
 *
 * @author leojpod
 */
public class StartJade {
  public static final void main(String[] args) {
    Profile p = new ProfileImpl(true);
    ContainerController cc = jade.core.Runtime.instance().createMainContainer(p);
    
  }
}
