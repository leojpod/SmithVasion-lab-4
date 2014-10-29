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
public final class RemoveAgentsMessage extends SmithVasionMessageAbs {
  public static final String NUMBER_KEY = "NUM";
  
  public final Integer numOfAgents;

  public RemoveAgentsMessage(Integer numOfAgents) {
    super(SmithVasionMessageFactory.RemoveSomeAgents);
    this.numOfAgents = numOfAgents;
  }

  
  
  public RemoveAgentsMessage(ACLMessage msg) {
    this(Integer.parseInt(msg.getUserDefinedParameter(NUMBER_KEY)));
  }

  @Override
  public ACLMessage createACLMessage() {
    ACLMessage msg = this.createACLMessageTemplate();
    msg.addUserDefinedParameter(NUMBER_KEY, numOfAgents.toString());
    return msg;
  }
  
}
