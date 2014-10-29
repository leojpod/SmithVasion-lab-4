/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion.messages;

import jade.lang.acl.ACLMessage;

/**
 *
 * @author leojpod
 */
public final class KillAgentMessage extends SmithVasionMessageAbs {

  public KillAgentMessage() {
    super(SmithVasionMessageFactory.KillAgent);
  }
  
  
  public KillAgentMessage(ACLMessage msg) {
    this();
  }

  @Override
  public ACLMessage createACLMessage() {
    return this.createACLMessageTemplate();
  }
  
}
