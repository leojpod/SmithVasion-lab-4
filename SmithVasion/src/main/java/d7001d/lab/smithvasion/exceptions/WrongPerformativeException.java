/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion.exceptions;

import d7001d.lab.smithvasion.messages.SmithVasionMessageFactory;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author leojpod
 */
public class WrongPerformativeException extends Exception{
  private final SmithVasionMessageFactory targetedType;
  private final int receivedPerformative;

  public WrongPerformativeException(SmithVasionMessageFactory target, int received) {
    super();
    this.targetedType = target;
    this.receivedPerformative = received;
  }

  @Override
  public String getMessage() {
    StringBuilder strBuild = new StringBuilder();
    strBuild.append("Error while processing an ACLMessage. Type parameter ");
    strBuild.append(targetedType.name());
    strBuild.append(" indicates \"");
    strBuild.append(ACLMessage.getPerformative(targetedType.performative));
    strBuild.append("\" while the received message had \"");
    strBuild.append(ACLMessage.getPerformative(receivedPerformative));
    strBuild.append("\"");
    return strBuild.toString();
  }
  
  
  
}
