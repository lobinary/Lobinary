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
 *  Apache软件许可证,版本11
 * 
 *  版权所有(c)2001 Apache软件基金会保留所有权利
 * 
 *  如果满足以下条件,则允许重新分发和使用源代码和二进制形式(带或不带修改)：
 * 
 *  1源代码的再分发必须保留上述版权声明,此条件列表和以下免责声明
 * 
 *  2二进制形式的再分发必须在随分发版提供的文档和/或其他材料中复制上述版权声明,此条件列表和以下免责声明
 * 
 * 3包含在重新分发中的最终用户文档(如果有的话)必须包括以下声明："本产品包括Apache Software Foundation(http：// wwwapacheorg /)开发的软件。
 * 或者,此确认可能出现在软件本身,如果和第三方承诺通常出现的地方。
 * 
 *  4未经事先书面许可,不得使用"Apache"和"Apache Software Foundation"和"Apache BCEL"这些名称来认可或推广从本软件衍生的产品对于书面许可,请联系apache
 *  @ apacheorg。
 * 
 *  5未经Apache软件基金会事先书面许可,不得将本软件衍生的产品称为"Apache","Apache BCEL"或"Apache"名称。
 * 
 * 本软件按"原样"提供,任何明示或暗示的保证,包括但不限于适销性和针对特定用途的适用性的默示担保,在任何情况下均不得免责,APACHE软件基金会或其参与人应负赔偿责任对于任何直接,间接,偶发,特殊,惩罚性
 * 或后果性损害(包括但不限于替代商品或服务的采购;使用,数据或利润损失;或业务中断)责任,无论是在合同,严格责任或侵权(包括疏忽或其他方式),以任何方式使用本软件,即使已被告知此类损害的可能性======
 * ==============================================================。
 * 
 * 此软件包括许多个人代表Apache软件基金会所做的自愿捐款有关Apache软件基金会的更多信息,请参阅<http：// wwwapacheorg />
 * 
 */

import  com.sun.org.apache.bcel.internal.Constants;
import  java.io.*;

/**
 * This class represents a inner class attribute, i.e., the class
 * indices of the inner and outer classes, the name and the attributes
 * of the inner class.
 *
 * <p>
 *  此类表示内部类属性,即内部类和外部类的类索引,内部类的名称和属性
 * 
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @see InnerClasses
 */
public final class InnerClass implements Cloneable, Node {
  private int inner_class_index;
  private int outer_class_index;
  private int inner_name_index;
  private int inner_access_flags;

  /**
   * Initialize from another object.
   * <p>
   *  从另一个对象初始化
   * 
   */
  public InnerClass(InnerClass c) {
    this(c.getInnerClassIndex(), c.getOuterClassIndex(), c.getInnerNameIndex(),
         c.getInnerAccessFlags());
  }

  /**
   * Construct object from file stream.
   * <p>
   *  从文件流构造对象
   * 
   * 
   * @param file Input stream
   * @throws IOException
   */
  InnerClass(DataInputStream file) throws IOException
  {
    this(file.readUnsignedShort(), file.readUnsignedShort(),
         file.readUnsignedShort(), file.readUnsignedShort());
  }

  /**
  /* <p>
  /* 
   * @param inner_class_index Class index in constant pool of inner class
   * @param outer_class_index Class index in constant pool of outer class
   * @param inner_name_index  Name index in constant pool of inner class
   * @param inner_access_flags Access flags of inner class
   */
  public InnerClass(int inner_class_index, int outer_class_index,
                    int inner_name_index, int inner_access_flags)
  {
    this.inner_class_index  = inner_class_index;
    this.outer_class_index  = outer_class_index;
    this.inner_name_index   = inner_name_index;
    this.inner_access_flags = inner_access_flags;
  }

  /**
   * Called by objects that are traversing the nodes of the tree implicitely
   * defined by the contents of a Java class. I.e., the hierarchy of methods,
   * fields, attributes, etc. spawns a tree of objects.
   *
   * <p>
   *  由遍历由Java类Ie的内容隐含地定义的树的节点的对象调用,方法,字段,属性等的层次结构产生对象的树
   * 
   * 
   * @param v Visitor object
   */
  public void accept(Visitor v) {
    v.visitInnerClass(this);
  }
  /**
   * Dump inner class attribute to file stream in binary format.
   *
   * <p>
   *  以二进制格式将内部类属性转储为文件流
   * 
   * 
   * @param file Output file stream
   * @throws IOException
   */
  public final void dump(DataOutputStream file) throws IOException
  {
    file.writeShort(inner_class_index);
    file.writeShort(outer_class_index);
    file.writeShort(inner_name_index);
    file.writeShort(inner_access_flags);
  }
  /**
  /* <p>
  /* 
   * @return access flags of inner class.
   */
  public final int getInnerAccessFlags() { return inner_access_flags; }
  /**
  /* <p>
  /* 
   * @return class index of inner class.
   */
  public final int getInnerClassIndex() { return inner_class_index; }
  /**
  /* <p>
  /* 
   * @return name index of inner class.
   */
  public final int getInnerNameIndex() { return inner_name_index; }
  /**
  /* <p>
  /* 
   * @return class index of outer class.
   */
  public final int getOuterClassIndex() { return outer_class_index; }
  /**
  /* <p>
  /* 
   * @param inner_access_flags.
   */
  public final void setInnerAccessFlags(int inner_access_flags) {
    this.inner_access_flags = inner_access_flags;
  }
  /**
  /* <p>
  /* 
   * @param inner_class_index.
   */
  public final void setInnerClassIndex(int inner_class_index) {
    this.inner_class_index = inner_class_index;
  }
  /**
  /* <p>
  /* 
   * @param inner_name_index.
   */
  public final void setInnerNameIndex(int inner_name_index) {
    this.inner_name_index = inner_name_index;
  }
  /**
  /* <p>
  /* 
   * @param outer_class_index.
   */
  public final void setOuterClassIndex(int outer_class_index) {
    this.outer_class_index = outer_class_index;
  }
  /**
  /* <p>
  /* 
   * @return String representation.
   */
  public final String toString() {
    return "InnerClass(" + inner_class_index + ", " + outer_class_index +
      ", " + inner_name_index + ", " + inner_access_flags + ")";
  }

  /**
  /* <p>
  /* 
   * @return Resolved string representation
   */
  public final String toString(ConstantPool constant_pool) {
    String inner_class_name, outer_class_name, inner_name, access;

    inner_class_name = constant_pool.getConstantString(inner_class_index,
                                                       Constants.CONSTANT_Class);
    inner_class_name = Utility.compactClassName(inner_class_name);

    if (outer_class_index != 0) {
      outer_class_name = constant_pool.getConstantString(outer_class_index,
                                                         Constants.CONSTANT_Class);
      outer_class_name = Utility.compactClassName(outer_class_name);
    }
    else
      outer_class_name = "<not a member>";

    if(inner_name_index != 0)
      inner_name = ((ConstantUtf8)constant_pool.
                    getConstant(inner_name_index, Constants.CONSTANT_Utf8)).getBytes();
    else
      inner_name = "<anonymous>";

    access = Utility.accessToString(inner_access_flags, true);
    access = access.equals("")? "" : (access + " ");

    return "InnerClass:" + access + inner_class_name +
      "(\"" + outer_class_name + "\", \"" + inner_name + "\")";
  }

  /**
  /* <p>
  /* 
   * @return deep copy of this object
   */
  public InnerClass copy() {
    try {
      return (InnerClass)clone();
    } catch(CloneNotSupportedException e) {}

    return null;
  }
}
