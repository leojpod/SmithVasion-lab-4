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
public class NewTargetMessage extends SmithVasionMessageAbs{
  private static final String PORT_KEY = "PORT";
  private static final String ADDRESS_KEY = "ADDRESS";
  
  public final String targetAddress;
  public final Integer targetPort;

  public NewTargetMessage(String target, int port) {
    super(SmithVasionMessageFactory.NewTarget);
    this.targetAddress = target;
    this.targetPort = port;
  }
  public NewTargetMessage(ACLMessage message) {
    this(
            message.getUserDefinedParameter(ADDRESS_KEY),
            Integer.parseInt(message.getUserDefinedParameter(PORT_KEY))
    );
  }

  @Override
  public ACLMessage createACLMessage() {
    ACLMessage msg = this.createACLMessageTemplate();
    msg.addUserDefinedParameter(PORT_KEY, String.valueOf(targetPort));
    msg.addUserDefinedParameter(ADDRESS_KEY, targetAddress);
    return msg;
  }

  @Override
  public String toString() {
    return "your mission is to locate and destroy this target -> " +
            targetAddress +":" + targetPort;
  }
  
  
  
}
