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

import com.sun.org.apache.bcel.internal.Constants;
import java.io.*;
import java.util.*;

/**
 * This class represents a reference to an unknown (i.e.,
 * application-specific) attribute of a class.  It is instantiated from the
 * <em>Attribute.readAttribute()</em> method.  Applications that need to
 * read in application-specific attributes should create an <a
 * href="./AttributeReader.html">AttributeReader</a> implementation and
 * attach it via <a
 * href="./Attribute.html#addAttributeReader(java.lang.String,
 * com.sun.org.apache.bcel.internal.classfile.AttributeReader)">Attribute.addAttributeReader</a>.

 *
 * <p>
 *  此类表示对类的未知(即,特定于应用程序)属性的引用。它从<em> AttributereadAttribute()</em>方法实例化。
 * 需要在应用程序特定属性中读取的应用程序应创建<a href ="/ AttributeReaderhtml"> AttributeReader </a>实现,并通过<a href=\"/Attributehtml#addAttributeReader(javalangString, comsunorgapachebcelinternalclassfileAttributeReader )\">
 *  AttributeaddAttributeReader </a>附加。
 *  此类表示对类的未知(即,特定于应用程序)属性的引用。它从<em> AttributereadAttribute()</em>方法实例化。
 * 
 * 
 * @see com.sun.org.apache.bcel.internal.classfile.Attribute
 * @see com.sun.org.apache.bcel.internal.classfile.AttributeReader
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public final class Unknown extends Attribute {
  private byte[] bytes;
  private String name;

  private static HashMap unknown_attributes = new HashMap();

  /** @return array of unknown attributes, but just one for each kind.
  /* <p>
   */
  static Unknown[] getUnknownAttributes() {
    Unknown[] unknowns = new Unknown[unknown_attributes.size()];
    Iterator  entries  = unknown_attributes.values().iterator();

    for(int i=0; entries.hasNext(); i++)
      unknowns[i] = (Unknown)entries.next();

    unknown_attributes.clear();
    return unknowns;
  }

  /**
   * Initialize from another object. Note that both objects use the same
   * references (shallow copy). Use clone() for a physical copy.
   * <p>
   * 从另一个对象初始化请注意,两个对象使用相同的引用(浅拷贝)对物理副本使用clone()
   * 
   */
  public Unknown(Unknown c) {
    this(c.getNameIndex(), c.getLength(), c.getBytes(), c.getConstantPool());
  }

  /**
   * Create a non-standard attribute.
   *
   * <p>
   *  创建非标准属性
   * 
   * 
   * @param name_index Index in constant pool
   * @param length Content length in bytes
   * @param bytes Attribute contents
   * @param constant_pool Array of constants
   */
  public Unknown(int name_index, int length, byte[] bytes,
                 ConstantPool constant_pool)
  {
    super(Constants.ATTR_UNKNOWN, name_index, length, constant_pool);
    this.bytes = bytes;

    name = ((ConstantUtf8)constant_pool.getConstant(name_index,
                                                    Constants.CONSTANT_Utf8)).getBytes();
    unknown_attributes.put(name, this);
  }

  /**
   * Construct object from file stream.
   * <p>
   *  从文件流构造对象
   * 
   * 
   * @param name_index Index in constant pool
   * @param length Content length in bytes
   * @param file Input stream
   * @param constant_pool Array of constants
   * @throws IOException
   */
  Unknown(int name_index, int length, DataInputStream file,
          ConstantPool constant_pool)
       throws IOException
  {
    this(name_index, length, (byte [])null, constant_pool);

    if(length > 0) {
      bytes = new byte[length];
      file.readFully(bytes);
    }
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
    v.visitUnknown(this);
  }
  /**
   * Dump unknown bytes to file stream.
   *
   * <p>
   *  将未知字节转储到文件流
   * 
   * 
   * @param file Output file stream
   * @throws IOException
   */
  public final void dump(DataOutputStream file) throws IOException
  {
    super.dump(file);
    if(length > 0)
      file.write(bytes, 0, length);
  }
  /**
  /* <p>
  /* 
   * @return data bytes.
   */
  public final byte[] getBytes() { return bytes; }

  /**
  /* <p>
  /* 
   * @return name of attribute.
   */
  public final String getName() { return name; }

  /**
  /* <p>
  /* 
   * @param bytes.
   */
  public final void setBytes(byte[] bytes) {
    this.bytes = bytes;
  }

  /**
  /* <p>
  /* 
   * @return String representation.
   */
  public final String toString() {
    if(length == 0 || bytes == null)
      return "(Unknown attribute " + name + ")";

    String hex;
    if(length > 10) {
      byte[] tmp = new byte[10];
      System.arraycopy(bytes, 0, tmp, 0, 10);
      hex = Utility.toHexString(tmp) + "... (truncated)";
    }
    else
      hex = Utility.toHexString(bytes);

    return "(Unknown attribute " + name + ": " + hex + ")";
  }

  /**
  /* <p>
  /* 
   * @return deep copy of this attribute
   */
  public Attribute copy(ConstantPool constant_pool) {
    Unknown c = (Unknown)clone();

    if(bytes != null)
      c.bytes = (byte[])bytes.clone();

    c.constant_pool = constant_pool;
    return c;
  }
}
