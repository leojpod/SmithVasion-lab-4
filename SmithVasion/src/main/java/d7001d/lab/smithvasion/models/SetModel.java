/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package d7001d.lab.smithvasion.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author leojpod
 * @param <E>
 */
public class SetModel<E> extends AbstractListModel<E> implements Collection<E>{
  private final List<E> model = new ArrayList<>();
  @Override
  public int size() {
    return model.size();
  }
  public int lastIdx() {
    return (model.size() > 0)? model.size() -1: 0;
  }
  @Override
  public boolean isEmpty() {
    return this.model.isEmpty();
  }
  @Override
  public boolean contains(Object o) {
    return this.model.contains(o);
  }
  @Override
  public Iterator<E> iterator() {
    return this.model.iterator();
  }
  @Override
  public Object[] toArray() {
    return this.model.toArray();
  }
  @Override
  public <T> T[] toArray(T[] a) {
    return this.model.toArray(a);
  }
  @Override
  public boolean add(E e) {
    if (this.contains(e)) {
      return false;
    }
    
    boolean res = this.model.add(e);
    if (res) {
      this.fireContentsChanged(this, 0, this.lastIdx());
    }
    return res;
  }
  @Override
  public boolean remove(Object o) {
    boolean res = this.model.remove(o);
    if (res) {
      this.fireContentsChanged(this, 0, this.lastIdx());
    }
    return res;
  }
  @Override
  public boolean containsAll(Collection<?> c) {
    return this.model.containsAll(c);
  }
  @Override
  public boolean addAll(Collection<? extends E> c) {
    boolean goodSoFar;
    for (E e : c) {
      goodSoFar = this.add(e);
      if (goodSoFar == false) {
        return false;
      }
    }
    this.fireContentsChanged(this, 0, this.lastIdx());
    return true;
  }
  @Override
  public boolean removeAll(Collection<?> c) {
    boolean res = this.model.removeAll(c);
    if (res) {
      this.fireContentsChanged(this, 0, this.lastIdx());
    }
    return res;
  }
  @Override
  public boolean retainAll(Collection<?> c) {
    boolean res = this.model.retainAll(c);
    if (res) {
      this.fireContentsChanged(this, 0, this.lastIdx());
    }
    return res;
  }
  @Override
  public void clear() {
    this.model.clear();
    this.fireContentsChanged(this, 0, this.lastIdx());
  }
  @Override
  public int getSize() {
    return this.size();
  }
  @Override
  public E getElementAt(int index) {
    return this.model.get(index);
  }
}
