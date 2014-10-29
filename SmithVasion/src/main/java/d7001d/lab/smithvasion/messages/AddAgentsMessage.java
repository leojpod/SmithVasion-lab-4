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
public final class AddAgentsMessage extends SmithVasionMessageAbs {
  public static final String NUMBER_KEY = "NUM";

  public final Integer numOfAgents;

  public AddAgentsMessage(Integer numOfAgents) {
    super(SmithVasionMessageFactory.AddAgents);
    this.numOfAgents = numOfAgents;
  }
  
  
  public AddAgentsMessage(ACLMessage msg) {
    this(Integer.parseInt(
            msg.getUserDefinedParameter(NUMBER_KEY)));
  }

  @Override
  public ACLMessage createACLMessage() {
    ACLMessage msg = this.createACLMessageTemplate();
    msg.addUserDefinedParameter(NUMBER_KEY, numOfAgents.toString());
    return msg;
  }
}
