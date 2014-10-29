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
public abstract class SmithVasionMessageAbs {
  public static final String TYPE_KEY = "TYPE";
  public final SmithVasionMessageFactory enumType;

  public SmithVasionMessageAbs(SmithVasionMessageFactory enumType) {
    this.enumType = enumType;
  }
  public SmithVasionMessageAbs(SmithVasionMessageFactory enumType, ACLMessage message) {
    this(enumType);
  }
  
  protected ACLMessage createACLMessageTemplate() {
    ACLMessage msg = new ACLMessage(enumType.performative);
    msg.addUserDefinedParameter(TYPE_KEY, enumType.name());
    return msg;
  }
  abstract public ACLMessage createACLMessage();
}
