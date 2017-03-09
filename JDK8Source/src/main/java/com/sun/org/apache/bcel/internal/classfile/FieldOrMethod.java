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
import java.io.*;

/**
 * Abstract super class for fields and methods.
 *
 * <p>
 * 
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class FieldOrMethod extends AccessFlags implements Cloneable, Node {
  protected int          name_index;      // Points to field name in constant pool
  protected int          signature_index; // Points to encoded signature
  protected int          attributes_count;// No. of attributes
  protected Attribute[]  attributes;      // Collection of attributes
  protected ConstantPool constant_pool;

  FieldOrMethod() {}

  /**
   * Initialize from another object. Note that both objects use the same
   * references (shallow copy). Use clone() for a physical copy.
   * <p>
   *  抽象超类的字段和方法。
   * 
   */
  protected FieldOrMethod(FieldOrMethod c) {
    this(c.getAccessFlags(), c.getNameIndex(), c.getSignatureIndex(),
         c.getAttributes(), c.getConstantPool());
  }

  /**
   * Construct object from file stream.
   * <p>
   *  从另一个对象初始化。注意两个对象使用相同的引用(浅拷贝)。使用clone()作为物理副本。
   * 
   * 
   * @param file Input stream
   * @throws IOException
   * @throws ClassFormatException
   */
  protected FieldOrMethod(DataInputStream file, ConstantPool constant_pool)
    throws IOException, ClassFormatException
  {
    this(file.readUnsignedShort(), file.readUnsignedShort(),
         file.readUnsignedShort(), null, constant_pool);

    attributes_count = file.readUnsignedShort();
    attributes       = new Attribute[attributes_count];
    for(int i=0; i < attributes_count; i++)
      attributes[i] = Attribute.readAttribute(file, constant_pool);
  }

  /**
  /* <p>
  /*  从文件流构造对象。
  /* 
  /* 
   * @param access_flags Access rights of method
   * @param name_index Points to field name in constant pool
   * @param signature_index Points to encoded signature
   * @param attributes Collection of attributes
   * @param constant_pool Array of constants
   */
  protected FieldOrMethod(int access_flags, int name_index, int signature_index,
                          Attribute[] attributes, ConstantPool constant_pool)
  {
    this.access_flags    = access_flags;
    this.name_index      = name_index;
    this.signature_index = signature_index;
    this.constant_pool   = constant_pool;

    setAttributes(attributes);
  }

  /**
   * Dump object to file stream on binary format.
   *
   * <p>
   * 
   * @param file Output file stream
   * @throws IOException
   */
  public final void dump(DataOutputStream file) throws IOException
  {
    file.writeShort(access_flags);
    file.writeShort(name_index);
    file.writeShort(signature_index);
    file.writeShort(attributes_count);

    for(int i=0; i < attributes_count; i++)
      attributes[i].dump(file);
  }

  /**
  /* <p>
  /*  将对象转换为二进制格式的文件流。
  /* 
  /* 
   * @return Collection of object attributes.
   */
  public final Attribute[] getAttributes() { return attributes; }

  /**
  /* <p>
  /* 
   * @param attributes Collection of object attributes.
   */
  public final void setAttributes(Attribute[] attributes) {
    this.attributes  = attributes;
    attributes_count = (attributes == null)? 0 : attributes.length;
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
   * @param constant_pool Constant pool to be used for this object.
   */
  public final void setConstantPool(ConstantPool constant_pool) {
    this.constant_pool = constant_pool;
  }

  /**
  /* <p>
  /* 
   * @return Index in constant pool of object's name.
   */
  public final int getNameIndex() { return name_index; }

  /**
  /* <p>
  /* 
   * @param name_index Index in constant pool of object's name.
   */
  public final void setNameIndex(int name_index) {
    this.name_index = name_index;
  }

  /**
  /* <p>
  /* 
   * @return Index in constant pool of field signature.
   */
  public final int getSignatureIndex() { return signature_index; }

  /**
  /* <p>
  /* 
   * @param signature_index Index in constant pool of field signature.
   */
  public final void setSignatureIndex(int signature_index) {
    this.signature_index = signature_index;
  }

  /**
  /* <p>
  /* 
   * @return Name of object, i.e., method name or field name
   */
  public final String getName() {
    ConstantUtf8  c;
    c = (ConstantUtf8)constant_pool.getConstant(name_index,
                                                Constants.CONSTANT_Utf8);
    return c.getBytes();
  }

  /**
  /* <p>
  /* 
   * @return String representation of object's type signature (java style)
   */
  public final String getSignature() {
    ConstantUtf8  c;
    c = (ConstantUtf8)constant_pool.getConstant(signature_index,
                                                Constants.CONSTANT_Utf8);
    return c.getBytes();
  }

  /**
  /* <p>
  /* 
   * @return deep copy of this field
   */
  protected FieldOrMethod copy_(ConstantPool constant_pool) {
    FieldOrMethod c = null;

    try {
      c = (FieldOrMethod)clone();
    } catch(CloneNotSupportedException e) {}

    c.constant_pool    = constant_pool;
    c.attributes       = new Attribute[attributes_count];

    for(int i=0; i < attributes_count; i++)
      c.attributes[i] = attributes[i].copy(constant_pool);

    return c;
  }
}
