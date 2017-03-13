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
 * This class represents a local variable within a method. It contains its
 * scope, name, signature and index on the method's frame.
 *
 * <p>
 *  此类表示方法中的局部变量。它包含方法框架上的作用域,名称,签名和索引
 * 
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @see     LocalVariableTable
 */
public final class LocalVariable
  implements Constants, Cloneable, Node, Serializable
{
  private int start_pc;        // Range in which the variable is valid
  private int length;
  private int name_index;      // Index in constant pool of variable name
  private int signature_index; // Index of variable signature
  private int index;            /* Variable is `index'th local variable on
                                * this method's frame.
                                * <p>
                                *  这个方法的框架
                                * 
                                */

  private ConstantPool constant_pool;

  /**
   * Initialize from another object. Note that both objects use the same
   * references (shallow copy). Use copy() for a physical copy.
   * <p>
   *  从另一个对象初始化请注意,两个对象使用相同的引用(浅拷贝)对物理副本使用copy()
   * 
   */
  public LocalVariable(LocalVariable c) {
    this(c.getStartPC(), c.getLength(), c.getNameIndex(),
         c.getSignatureIndex(), c.getIndex(), c.getConstantPool());
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
  LocalVariable(DataInputStream file, ConstantPool constant_pool)
       throws IOException
  {
    this(file.readUnsignedShort(), file.readUnsignedShort(),
         file.readUnsignedShort(), file.readUnsignedShort(),
         file.readUnsignedShort(), constant_pool);
  }

  /**
  /* <p>
  /* 
   * @param start_pc Range in which the variable
   * @param length ... is valid
   * @param name_index Index in constant pool of variable name
   * @param signature_index Index of variable's signature
   * @param index Variable is `index'th local variable on the method's frame
   * @param constant_pool Array of constants
   */
  public LocalVariable(int start_pc, int length, int name_index,
                       int signature_index, int index,
                       ConstantPool constant_pool)
  {
    this.start_pc        = start_pc;
    this.length          = length;
    this.name_index      = name_index;
    this.signature_index = signature_index;
    this.index           = index;
    this.constant_pool   = constant_pool;
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
    v.visitLocalVariable(this);
  }

  /**
   * Dump local variable to file stream in binary format.
   *
   * <p>
   *  以二进制格式将局部变量转储到文件流
   * 
   * 
   * @param file Output file stream
   * @throws IOException
   */
  public final void dump(DataOutputStream file) throws IOException
  {
    file.writeShort(start_pc);
    file.writeShort(length);
    file.writeShort(name_index);
    file.writeShort(signature_index);
    file.writeShort(index);
  }

  /**
  /* <p>
  /* 
   * @return Constant pool used by this object.
   */
  public final ConstantPool getConstantPool() { return constant_pool; }

  /**
  /* <p>
  /* 
   * @return Variable is valid within getStartPC() .. getStartPC()+getLength()
   */
  public final int getLength()         { return length; }

  /**
  /* <p>
  /* 
   * @return Variable name.
   */
  public final String getName() {
    ConstantUtf8  c;

    c = (ConstantUtf8)constant_pool.getConstant(name_index, CONSTANT_Utf8);
    return c.getBytes();
  }

  /**
  /* <p>
  /* 
   * @return Index in constant pool of variable name.
   */
  public final int getNameIndex()      { return name_index; }

  /**
  /* <p>
  /* 
   * @return Signature.
   */
  public final String getSignature() {
    ConstantUtf8  c;
    c = (ConstantUtf8)constant_pool.getConstant(signature_index,
                                                CONSTANT_Utf8);
    return c.getBytes();
  }

  /**
  /* <p>
  /* 
   * @return Index in constant pool of variable signature.
   */
  public final int getSignatureIndex() { return signature_index; }

  /**
  /* <p>
  /* 
   * @return index of register where variable is stored
   */
  public final int getIndex()           { return index; }

  /**
  /* <p>
  /* 
   * @return Start of range where he variable is valid
   */
  public final int getStartPC()        { return start_pc; }

  /**
  /* <p>
  /* 
   * @param constant_pool Constant pool to be used for this object.
   */
  public final void setConstantPool(ConstantPool constant_pool) {
    this.constant_pool = constant_pool;
  }

  /**
  /* <p>
  /* 
   * @param length.
   */
  public final void setLength(int length) {
    this.length = length;
  }

  /**
  /* <p>
  /* 
   * @param name_index.
   */
  public final void setNameIndex(int name_index) {
    this.name_index = name_index;
  }

  /**
  /* <p>
  /* 
   * @param signature_index.
   */
  public final void setSignatureIndex(int signature_index) {
    this.signature_index = signature_index;
  }

  /**
  /* <p>
  /* 
   * @param index.
   */
  public final void setIndex(int index) { this.index = index; }

  /**
  /* <p>
  /* 
   * @param start_pc Specify range where the local variable is valid.
   */
  public final void setStartPC(int start_pc) {
    this.start_pc = start_pc;
  }

  /**
  /* <p>
  /* 
   * @return string representation.
   */
  public final String toString() {
    String name = getName(), signature = Utility.signatureToString(getSignature());

    return "LocalVariable(start_pc = " + start_pc + ", length = " + length +
      ", index = " + index + ":" + signature + " " + name + ")";
  }

  /**
  /* <p>
  /* 
   * @return deep copy of this object
   */
  public LocalVariable copy() {
    try {
      return (LocalVariable)clone();
    } catch(CloneNotSupportedException e) {}

    return null;
  }
}
