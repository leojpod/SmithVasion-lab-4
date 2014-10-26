/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion.exceptions;

/**
 *
 * @author leojpod
 */
public class NoSuchMessageException extends Exception {
  private final String type;

  /**
   * Creates a new instance of <code>NoSuchMessageException</code> without
   * detail message.
   * @param type
   */
  public NoSuchMessageException(String type) {
    this.type = type;
  }

  @Override
  public String getMessage() {
    return type + " does not match any known message definition";
  }
  
  
}
