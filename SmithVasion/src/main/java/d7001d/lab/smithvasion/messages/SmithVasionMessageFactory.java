/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion.messages;

import d7001d.lab.smithvasion.exceptions.NoSuchMessageException;
import d7001d.lab.smithvasion.exceptions.WrongPerformativeException;
import jade.lang.acl.ACLMessage;

/**
 *
 * @author leojpod
 */
public enum SmithVasionMessageFactory {
  NewTarget(ACLMessage.PROPOSE), 
  AddAgents(ACLMessage.PROPOSE), 
  RemoveSomeAgents(ACLMessage.PROPOSE),
  KillCoord(ACLMessage.PROPOSE),
  KillAgent(ACLMessage.PROPOSE);

  public final int performative;
  private SmithVasionMessageFactory(int performative) {
    this.performative = performative;
  }
  
  
  
  public static SmithVasionMessageAbs fromACLMessage(ACLMessage msg) throws WrongPerformativeException, NoSuchMessageException {
    int performative = msg.getPerformative();
    String type = msg.getUserDefinedParameter(SmithVasionMessageAbs.TYPE_KEY);
    try {
      SmithVasionMessageFactory enumType = SmithVasionMessageFactory.valueOf(type);
      if (performative != enumType.performative) {
        throw new WrongPerformativeException(enumType, performative);
      }
      switch(enumType) {
        case NewTarget:
          return new NewTargetMessage(msg);
        case AddAgents:
          return new AddAgentsMessage(msg);
        case RemoveSomeAgents:
          return new RemoveAgentsMessage(msg);
        case KillCoord: 
          return new KillCoordMessage(msg);
        case KillAgent:
          return new KillAgentMessage(msg);
        default: 
          assert false; // should never be reached!
          return null;
      }
    } catch (EnumConstantNotPresentException | NullPointerException ex){
      throw new NoSuchMessageException(type);
    }
  }
}
