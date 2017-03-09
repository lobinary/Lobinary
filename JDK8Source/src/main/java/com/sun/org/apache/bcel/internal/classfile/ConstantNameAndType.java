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
 *  ================================================== ==================。
 * 
 *  该软件包括许多个人代表Apache软件基金会所做的自愿捐款。有关Apache Software Foundation的更多信息,请参阅<http://www.apache.org/>。
 */

import  com.sun.org.apache.bcel.internal.Constants;
import  java.io.*;

/**
 * This class is derived from the abstract
 * <A HREF="com.sun.org.apache.bcel.internal.classfile.Constant.html">Constant</A> class
 * and represents a reference to the name and signature
 * of a field or method.
 *
 * <p>
 * 
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @see     Constant
 */
public final class ConstantNameAndType extends Constant {
  private int name_index;      // Name of field/method
  private int signature_index; // and its signature.

  /**
   * Initialize from another object.
   * <p>
   *  此类源自抽象<A HREF="com.sun.org.apache.bcel.internal.classfile.Constant.html">常数</A>类,表示对字段的名称和签名的引用,或方法。
   * 
   */
  public ConstantNameAndType(ConstantNameAndType c) {
    this(c.getNameIndex(), c.getSignatureIndex());
  }

  /**
   * Initialize instance from file data.
   *
   * <p>
   *  从另一个对象初始化。
   * 
   * 
   * @param file Input stream
   * @throws IOException
   */
  ConstantNameAndType(DataInputStream file) throws IOException
  {
    this((int)file.readUnsignedShort(), (int)file.readUnsignedShort());
  }

  /**
  /* <p>
  /*  从文件数据初始化实例。
  /* 
  /* 
   * @param name_index Name of field/method
   * @param signature_index and its signature
   */
  public ConstantNameAndType(int name_index,
                             int signature_index)
  {
    super(Constants.CONSTANT_NameAndType);
    this.name_index      = name_index;
    this.signature_index = signature_index;
  }

  /**
   * Called by objects that are traversing the nodes of the tree implicitely
   * defined by the contents of a Java class. I.e., the hierarchy of methods,
   * fields, attributes, etc. spawns a tree of objects.
   *
   * <p>
   * 
   * @param v Visitor object
   */
  public void accept(Visitor v) {
    v.visitConstantNameAndType(this);
  }

  /**
   * Dump name and signature index to file stream in binary format.
   *
   * <p>
   * 由遍历由Java类的内容隐含地定义的树的节点的对象调用。即,方法,字段,属性等的层次结构产生对象树。
   * 
   * 
   * @param file Output file stream
   * @throws IOException
   */
  public final void dump(DataOutputStream file) throws IOException
  {
    file.writeByte(tag);
    file.writeShort(name_index);
    file.writeShort(signature_index);
  }

  /**
  /* <p>
  /*  以二进制格式转储文件流的名称和签名索引。
  /* 
  /* 
   * @return Name index in constant pool of field/method name.
   */
  public final int getNameIndex()      { return name_index; }

  /** @return name
  /* <p>
   */
  public final String getName(ConstantPool cp) {
    return cp.constantToString(getNameIndex(), Constants.CONSTANT_Utf8);
  }

  /**
  /* <p>
  /* 
   * @return Index in constant pool of field/method signature.
   */
  public final int getSignatureIndex() { return signature_index; }

  /** @return signature
  /* <p>
   */
  public final String getSignature(ConstantPool cp) {
    return cp.constantToString(getSignatureIndex(), Constants.CONSTANT_Utf8);
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
   * @return String representation
   */
  public final String toString() {
    return super.toString() + "(name_index = " + name_index +
      ", signature_index = " + signature_index + ")";
  }
}
