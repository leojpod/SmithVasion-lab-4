/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion.messages;

import jade.core.AID;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author leojpod
 */
public final class AddAgentsMessage extends SmithVasionMessageAbs {
  public static final String NUMBER_KEY = "NUM";

  public final AID subCoord;
  public final Integer numOfAgents;

  public AddAgentsMessage(AID subCoord, Integer numOfAgents) {
    super(SmithVasionMessageFactory.AddAgents);
    this.subCoord = subCoord;
    this.numOfAgents = numOfAgents;
  }
  
  
  public AddAgentsMessage(ACLMessage msg) {
    this(
            msg.getSender(),
            Integer.parseInt(msg.getUserDefinedParameter(NUMBER_KEY)));
  }

  @Override
  public ACLMessage createACLMessage() {
    ACLMessage msg = this.createACLMessageTemplate();
    msg.addUserDefinedParameter(NUMBER_KEY, numOfAgents.toString());
    msg.addReceiver(subCoord);
    return msg;
  }
}
