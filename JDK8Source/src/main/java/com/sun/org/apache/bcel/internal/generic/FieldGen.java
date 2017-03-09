/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sun.org.apache.bcel.internal.generic;

/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Apache" and "Apache Software Foundation" and
 *    "Apache BCEL" must not be used to endorse or promote products
 *    derived from this software without prior written permission. For
 *    written permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    "Apache BCEL", nor may "Apache" appear in their name, without
 *    prior written permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 * <p>
 *  Apache软件许可证,版本1.1
 * 
 *  版权所有(c)2001 Apache软件基金会。版权所有。
 * 
 *  如果满足以下条件,则允许重新分发和使用源代码和二进制形式(带或不带修改)：
 * 
 *  1.源代码的再分发必须保留上述版权声明,此条件列表和以下免责声明。
 * 
 *  2.二进制形式的再分发必须在分发所提供的文档和/或其他材料中复制上述版权声明,此条件列表和以下免责声明。
 * 
 *  3.包含在重新分发中的最终用户文档(如果有)必须包括以下声明："本产品包括由Apache Software Foundation(http://www.apache.org/)开发的软件。
 * 或者,如果此类第三方确认通常出现,则此确认可能出现在软件本身中。
 * 
 *  4.未经事先书面许可,不得使用名称"Apache"和"Apache Software Foundation"和"Apache BCEL"来认可或推广从本软件衍生的产品。
 * 如需书面许可,请联系apache@apache.org。
 * 
 * 未经Apache软件基金会事先书面许可,从本软件衍生的产品可能不会被称为"Apache","Apache BCEL",也不可能出现在他们的名字中。
 * 
 *  本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 在任何情况下,APACHE软件基金会或其捐赠者均不对任何直接,间接,偶发,特殊,惩罚性或后果性损害(包括但不限于替代商品或服务的采购,使用,数据丢失或利润或业务中断),无论是由于任何责任推理原因,无论是
 * 在合同,严格责任或侵权(包括疏忽或其他方式)中,以任何方式使用本软件,即使已被告知此类软件的可能性损伤。
 *  本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 *  ================================================== ==================。
 * 
 *  该软件包括许多个人代表Apache软件基金会所做的自愿捐款。有关Apache Software Foundation的更多信息,请参阅<http://www.apache.org/>。
 * 
 */

import com.sun.org.apache.bcel.internal.Constants;
import com.sun.org.apache.bcel.internal.classfile.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Template class for building up a field.  The only extraordinary thing
 * one can do is to add a constant value attribute to a field (which must of
 * course be compatible with to the declared type).
 *
 * <p>
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @see Field
 */
public class FieldGen extends FieldGenOrMethodGen {
  private Object value = null;

  /**
   * Declare a field. If it is static (isStatic() == true) and has a
   * basic type like int or String it may have an initial value
   * associated with it as defined by setInitValue().
   *
   * <p>
   *  建立字段的模板类。唯一不寻常的事情是可以做的是添加一个常量值属性到一个字段(当然必须与声明的类型兼容)。
   * 
   * 
   * @param access_flags access qualifiers
   * @param type  field type
   * @param name field name
   * @param cp constant pool
   */
  public FieldGen(int access_flags, Type type, String name, ConstantPoolGen cp) {
    setAccessFlags(access_flags);
    setType(type);
    setName(name);
    setConstantPool(cp);
  }

  /**
   * Instantiate from existing field.
   *
   * <p>
   * 声明一个字段。如果它是静态的(isStatic()== true),并且有一个基本类型,如int或String,它可以有一个与setInitValue()定义的初始值相关联。
   * 
   * 
   * @param field Field object
   * @param cp constant pool (must contain the same entries as the field's constant pool)
   */
  public FieldGen(Field field, ConstantPoolGen cp) {
    this(field.getAccessFlags(), Type.getType(field.getSignature()), field.getName(), cp);

    Attribute[] attrs = field.getAttributes();

    for(int i=0; i < attrs.length; i++) {
      if(attrs[i] instanceof ConstantValue)
        setValue(((ConstantValue)attrs[i]).getConstantValueIndex());
      else
        addAttribute(attrs[i]);
    }
  }

  private void setValue(int index) {
    ConstantPool cp  = this.cp.getConstantPool();
    Constant     c   = cp.getConstant(index);
    value = ((ConstantObject)c).getConstantValue(cp);
  }

  /**
   * Set (optional) initial value of field, otherwise it will be set to null/0/false
   * by the JVM automatically.
   * <p>
   *  从现有字段实例化。
   * 
   */
  public void setInitValue(String str) {
    checkType(new ObjectType("java.lang.String"));

    if(str != null)
      value = str;
  }

  public void setInitValue(long l) {
    checkType(Type.LONG);

    if(l != 0L)
      value = new Long(l);
  }

  public void setInitValue(int i) {
    checkType(Type.INT);

    if(i != 0)
      value = new Integer(i);
  }

  public void setInitValue(short s) {
    checkType(Type.SHORT);

    if(s != 0)
      value = new Integer(s);
  }

  public void setInitValue(char c) {
    checkType(Type.CHAR);

    if(c != 0)
      value = new Integer(c);
  }

  public void setInitValue(byte b) {
    checkType(Type.BYTE);

    if(b != 0)
      value = new Integer(b);
  }

  public void setInitValue(boolean b) {
    checkType(Type.BOOLEAN);

    if(b)
      value = new Integer(1);
  }

  public void setInitValue(float f) {
    checkType(Type.FLOAT);

    if(f != 0.0)
      value = new Float(f);
  }

  public void setInitValue(double d) {
    checkType(Type.DOUBLE);

    if(d != 0.0)
      value = new Double(d);
  }

  /** Remove any initial value.
  /* <p>
  /*  设置(可选)字段的初始值,否则将由JVM自动设置为null / 0 / false。
  /* 
   */
  public void cancelInitValue() {
    value = null;
  }

  private void checkType(Type atype) {
    if(type == null)
      throw new ClassGenException("You haven't defined the type of the field yet");

    if(!isFinal())
      throw new ClassGenException("Only final fields may have an initial value!");

    if(!type.equals(atype))
      throw new ClassGenException("Types are not compatible: " + type + " vs. " + atype);
  }

  /**
   * Get field object after having set up all necessary values.
   * <p>
   */
  public Field getField() {
    String      signature       = getSignature();
    int         name_index      = cp.addUtf8(name);
    int         signature_index = cp.addUtf8(signature);

    if(value != null) {
      checkType(type);
      int index = addConstant();
      addAttribute(new ConstantValue(cp.addUtf8("ConstantValue"),
                                     2, index, cp.getConstantPool()));
    }

    return new Field(access_flags, name_index, signature_index, getAttributes(),
                     cp.getConstantPool());
  }

  private int addConstant() {
    switch(type.getType()) {
    case Constants.T_INT: case Constants.T_CHAR: case Constants.T_BYTE:
    case Constants.T_BOOLEAN: case Constants.T_SHORT:
      return cp.addInteger(((Integer)value).intValue());

    case Constants.T_FLOAT:
      return cp.addFloat(((Float)value).floatValue());

    case Constants.T_DOUBLE:
      return cp.addDouble(((Double)value).doubleValue());

    case Constants.T_LONG:
      return cp.addLong(((Long)value).longValue());

    case Constants.T_REFERENCE:
      return cp.addString(((String)value));

    default:
      throw new RuntimeException("Oops: Unhandled : " + type.getType());
    }
  }

  public String  getSignature()  { return type.getSignature(); }

  private ArrayList observers;

  /** Add observer for this object.
  /* <p>
  /*  在设置所有必需的值后获取字段对象。
  /* 
   */
  public void addObserver(FieldObserver o) {
    if(observers == null)
      observers = new ArrayList();

    observers.add(o);
  }

  /** Remove observer for this object.
  /* <p>
   */
  public void removeObserver(FieldObserver o) {
    if(observers != null)
      observers.remove(o);
  }

  /** Call notify() method on all observers. This method is not called
   * automatically whenever the state has changed, but has to be
   * called by the user after he has finished editing the object.
   * <p>
   */
  public void update() {
    if(observers != null)
      for(Iterator e = observers.iterator(); e.hasNext(); )
        ((FieldObserver)e.next()).notify(this);
  }

  public String getInitValue() {
    if(value != null) {
      return value.toString();
    } else
      return null;
  }

  /**
   * Return string representation close to declaration format,
   * `public static final short MAX = 100', e.g..
   *
   * <p>
   *  每当状态改变时自动地,但是在用户完成编辑对象之后必须被用户调用。
   * 
   * 
   * @return String representation of field
   */
  public final String toString() {
    String name, signature, access; // Short cuts to constant pool

    access    = Utility.accessToString(access_flags);
    access    = access.equals("")? "" : (access + " ");
    signature = type.toString();
    name      = getName();

    StringBuffer buf = new StringBuffer(access + signature + " " + name);
    String value = getInitValue();

    if(value != null)
      buf.append(" = " + value);

    return buf.toString();
  }

  /** @return deep copy of this field
  /* <p>
  /*  返回字符串表示接近声明格式,`public static final short MAX = 100',eg ..
  /* 
   */
  public FieldGen copy(ConstantPoolGen cp) {
    FieldGen fg = (FieldGen)clone();

    fg.setConstantPool(cp);
    return fg;
  }
}
