/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sun.org.apache.bcel.internal.classfile;

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
 */

import  com.sun.org.apache.bcel.internal.Constants;
import  java.io.*;

/**
 * Abstract superclass for classes to represent the different constant types
 * in the constant pool of a class file. The classes keep closely to
 * the JVM specification.
 *
 * <p>
 *  ================================================== ==================。
 * 
 *  该软件包括许多个人代表Apache软件基金会所做的自愿捐款。有关Apache Software Foundation的更多信息,请参阅<http://www.apache.org/>。
 * 
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class Constant implements Cloneable, Node, Serializable {
  /* In fact this tag is redundant since we can distinguish different
   * `Constant' objects by their type, i.e., via `instanceof'. In some
   * places we will use the tag for switch()es anyway.
   *
   * First, we want match the specification as closely as possible. Second we
   * need the tag as an index to select the corresponding class name from the
   * `CONSTANT_NAMES' array.
   * <p>
   *  类的抽象超类,用于在类文件的常量池中表示不同的常量类型。类与JVM规范密切相关。
   * 
   */
  protected byte tag;

  Constant(byte tag) { this.tag = tag; }

  /**
   * Called by objects that are traversing the nodes of the tree implicitely
   * defined by the contents of a Java class. I.e., the hierarchy of methods,
   * fields, attributes, etc. spawns a tree of objects.
   *
   * <p>
   *  "Constant"对象的类型,即通过`instanceof'。在某些地方,我们将使用switch()es的标签。
   * 
   * 首先,我们希望尽可能接近规格。第二,我们需要标签作为索引,从"CONSTANT_NAMES"数组中选择相应的类名。
   * 
   * 
   * @param v Visitor object
   */
  public abstract void accept(Visitor v);

  public abstract void dump(DataOutputStream file) throws IOException;

  /**
  /* <p>
  /*  由遍历由Java类的内容隐含地定义的树的节点的对象调用。即,方法,字段,属性等的层次结构产生对象树。
  /* 
  /* 
   * @return Tag of constant, i.e., its type. No setTag() method to avoid
   * confusion.
   */
  public final byte getTag() { return tag; }

  /**
  /* <p>
  /* 
   * @return String representation.
   */
  public String toString() {
    return Constants.CONSTANT_NAMES[tag] + "[" + tag + "]";
  }

  /**
  /* <p>
  /* 
   * @return deep copy of this constant
   */
  public Constant copy() {
    try {
      return (Constant)super.clone();
    } catch(CloneNotSupportedException e) {}

    return null;
  }

  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  /**
   * Read one constant from the given file, the type depends on a tag byte.
   *
   * <p>
   * 
   * @param file Input stream
   * @return Constant object
   */
  static final Constant readConstant(DataInputStream file)
    throws IOException, ClassFormatException
  {
    byte b = file.readByte(); // Read tag byte

    switch(b) {
    case Constants.CONSTANT_Class:              return new ConstantClass(file);
    case Constants.CONSTANT_Fieldref:           return new ConstantFieldref(file);
    case Constants.CONSTANT_Methodref:          return new ConstantMethodref(file);
    case Constants.CONSTANT_InterfaceMethodref: return new
                                        ConstantInterfaceMethodref(file);
    case Constants.CONSTANT_String:             return new ConstantString(file);
    case Constants.CONSTANT_Integer:            return new ConstantInteger(file);
    case Constants.CONSTANT_Float:              return new ConstantFloat(file);
    case Constants.CONSTANT_Long:               return new ConstantLong(file);
    case Constants.CONSTANT_Double:             return new ConstantDouble(file);
    case Constants.CONSTANT_NameAndType:        return new ConstantNameAndType(file);
    case Constants.CONSTANT_Utf8:               return new ConstantUtf8(file);
    default:
      throw new ClassFormatException("Invalid byte tag in constant pool: " + b);
    }
  }
}
