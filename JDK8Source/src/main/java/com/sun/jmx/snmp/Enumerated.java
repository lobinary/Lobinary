/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2012, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.sun.jmx.snmp;


import java.io.*;
import java.util.Hashtable;
import java.util.*;



/** This class is used for implementing enumerated values.
 *
 * An enumeration is represented by a class derived from Enumerated.
 * The derived class defines what are the permitted values in the enumeration.
 *
 * An enumerated value is represented by an instance of the derived class.
 * It can be represented :
 *  - as an integer
 *  - as a string
 *
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  枚举由从枚举派生的类表示。派生类定义枚举中允许的值。
 * 
 *  枚举值由派生类的实例表示。它可以表示为： - 作为整数 - 作为字符串
 * 
 *  <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 */

abstract public class Enumerated  implements Serializable {

  /**
   * Construct an enumerated with a default value.
   * The default value is the first available in getIntTable().
   * <p>
   *  使用默认值构造枚举。默认值是getIntTable()中的第一个可用值。
   * 
   * 
    * @exception IllegalArgumentException One of the arguments passed to the method is illegal or inappropriate.
   */
  public Enumerated() throws IllegalArgumentException {
    Enumeration<Integer> e =getIntTable().keys();
    if (e.hasMoreElements()) {
      value = e.nextElement().intValue() ;
    }
    else {
      throw new IllegalArgumentException() ;
    }
  }

  /**
   * Construct an enumerated from its integer form.
   *
   * <p>
   *  从其整数形式构造枚举。
   * 
   * 
   * @param valueIndex The integer form.
   * @exception IllegalArgumentException One of the arguments passed to
   *            the method is illegal or inappropriate.
   */
  public Enumerated(int valueIndex) throws IllegalArgumentException {
    if (getIntTable().get(new Integer(valueIndex)) == null) {
      throw new IllegalArgumentException() ;
    }
    value = valueIndex ;
  }

  /**
   * Construct an enumerated from its Integer form.
   *
   * <p>
   *  从其Integer形式构造枚举。
   * 
   * 
   * @param valueIndex The Integer form.
   * @exception IllegalArgumentException One of the arguments passed to
   *            the method is illegal or inappropriate.
   */
  public Enumerated(Integer valueIndex) throws IllegalArgumentException {
    if (getIntTable().get(valueIndex) == null) {
      throw new IllegalArgumentException() ;
    }
    value = valueIndex.intValue() ;
  }


  /**
   * Construct an enumerated from its string form.
   *
   * <p>
   *  从其字符串形式构造枚举。
   * 
   * 
   * @param valueString The string form.
   * @exception IllegalArgumentException One of the arguments passed
   *  to the method is illegal or inappropriate.
   */
  public Enumerated(String valueString) throws IllegalArgumentException {
    Integer index = getStringTable().get(valueString) ;
    if (index == null) {
      throw new IllegalArgumentException() ;
    }
    else {
      value = index.intValue() ;
    }
  }


  /**
   * Return the integer form of the enumerated.
   *
   * <p>
   *  返回枚举的整数形式。
   * 
   * 
   * @return The integer form
   */

  public int intValue() {
    return value ;
  }


  /**
   * Returns an Java enumeration of the permitted integers.
   *
   * <p>
   *  返回允许的整数的Java枚举。
   * 
   * 
   * @return An enumeration of Integer instances
   */

  public Enumeration<Integer> valueIndexes() {
    return getIntTable().keys() ;
  }


  /**
   * Returns an Java enumeration of the permitted strings.
   *
   * <p>
   *  返回允许的字符串的Java枚举。
   * 
   * 
   * @return An enumeration of String instances
   */

  public Enumeration<String> valueStrings() {
    return getStringTable().keys() ;
  }


  /**
   * Compares this enumerated to the specified enumerated.
   *
   * The result is true if and only if the argument is not null
   * and is of the same class.
   *
   * <p>
   *  将此枚举值与指定的枚举值进行比较。
   * 
   *  当且仅当参数不为null并且属于同一类时,结果为true。
   * 
   * 
   * @param obj The object to compare with.
   *
   * @return True if this and obj are the same; false otherwise
   */
  @Override
  public boolean equals(Object obj) {

    return ((obj != null) &&
            (getClass() == obj.getClass()) &&
            (value == ((Enumerated)obj).value)) ;
  }


  /**
   * Returns the hash code for this enumerated.
   *
   * <p>
   *  返回此枚举的哈希码。
   * 
   * 
   * @return A hash code value for this object.
   */
  @Override
  public int hashCode() {
    String hashString = getClass().getName() + String.valueOf(value) ;
    return hashString.hashCode() ;
  }


  /**
   * Returns the string form of this enumerated.
   *
   * <p>
   *  返回此枚举的字符串形式。
   * 
   * 
   * @return The string for for this object.
   */
  @Override
  public String toString() {
    return getIntTable().get(new Integer(value)) ;
  }


  /**
   * Returns the hashtable of the integer forms.
   * getIntTable().get(x) returns the string form associated
   * to the integer x.
   *
   * This method must be implemented by the derived class.
   *
   * <p>
   *  返回整数形式的散列表。 getIntTable()。get(x)返回与整数x相关联的字符串形式。
   * 
   *  此方法必须由派生类实现。
   * 
   * 
   * @return An hashtable for read-only purpose
   */

  protected abstract Hashtable<Integer,String>  getIntTable() ;



  /**
   * Returns the hashtable of the string forms.
   * getStringTable().get(s) returns the integer form associated
   * to the string s.
   *
   * This method must be implemented by the derived class.
   *
   * <p>
   *  返回字符串表单的散列表。 getStringTable()。get(s)返回与字符串s相关联的整数形式。
   * 
   *  此方法必须由派生类实现。
   * 
   * 
   * @return An hashtable for read-only purpose
   */

  protected abstract Hashtable<String,Integer> getStringTable() ;


  /**
   * This variable keeps the integer form of the enumerated.
   * The string form is retrieved using getIntTable().
   * <p>
   */
  protected int value ;

}
