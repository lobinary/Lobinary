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
 * 
 */

import com.sun.org.apache.bcel.internal.Constants;
import java.io.*;
import java.util.HashMap;

/**
 * Abstract super class for <em>Attribute</em> objects. Currently the
 * <em>ConstantValue</em>, <em>SourceFile</em>, <em>Code</em>,
 * <em>Exceptiontable</em>, <em>LineNumberTable</em>,
 * <em>LocalVariableTable</em>, <em>InnerClasses</em> and
 * <em>Synthetic</em> attributes are supported. The
 * <em>Unknown</em> attribute stands for non-standard-attributes.
 *
 * <p>
 * 摘要超类的<em>属性</em>的对象。
 * 目前的<em> ConstantValue </em>的<EM>的SourceFile </em>的<EM>代码</em>的<EM> Exceptiontable </em>的<EM> LineNumb
 * erTable </em>的<EM> LocalVariableTable </em>的<EM> InnerClasses </EM>和<EM>合成</em>的属性的支持。
 * 摘要超类的<em>属性</em>的对象。在<EM>未知</em>的属性代表的非标准的属性。
 * 
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @see     ConstantValue
 * @see     SourceFile
 * @see     Code
 * @see     Unknown
 * @see     ExceptionTable
 * @see     LineNumberTable
 * @see     LocalVariableTable
 * @see     InnerClasses
 * @see     Synthetic
 * @see     Deprecated
 * @see     Signature
*/
public abstract class Attribute implements Cloneable, Node, Serializable {
  protected int          name_index; // Points to attribute name in constant pool
  protected int          length;     // Content length of attribute field
  protected byte         tag;        // Tag to distiguish subclasses
  protected ConstantPool constant_pool;

  protected Attribute(byte tag, int name_index, int length,
                      ConstantPool constant_pool) {
    this.tag           = tag;
    this.name_index    = name_index;
    this.length        = length;
    this.constant_pool = constant_pool;
  }

  /**
   * Called by objects that are traversing the nodes of the tree implicitely
   * defined by the contents of a Java class. I.e., the hierarchy of methods,
   * fields, attributes, etc. spawns a tree of objects.
   *
   * <p>
   *  由阙对象称为被遍历由Java类的内容隐含定义的树的节点。即方法,字段,属性等的层级派生对象树。
   * 
   * 
   * @param v Visitor object
   */
  public abstract void accept(Visitor v);

  /**
   * Dump attribute to file stream in binary format.
   *
   * <p>
   *  转储属性以二进制格式文件流。
   * 
   * 
   * @param file Output file stream
   * @throws IOException
   */
  public void dump(DataOutputStream file) throws IOException
  {
    file.writeShort(name_index);
    file.writeInt(length);
  }

  private static HashMap readers = new HashMap();

  /** Add an Attribute reader capable of parsing (user-defined) attributes
   * named "name". You should not add readers for the standard attributes
   * such as "LineNumberTable", because those are handled internally.
   *
   * <p>
   *  命名为"名"。你不应该添加读者的标准属性,例如"LineNumberTable",因为那些是内部处理。
   * 
   * 
   * @param name the name of the attribute as stored in the class file
   * @param r the reader object
   */
  public static void addAttributeReader(String name, AttributeReader r) {
    readers.put(name, r);
  }

  /** Remove attribute reader
   *
   * <p>
   * 
   * @param name the name of the attribute as stored in the class file
   */
  public static void removeAttributeReader(String name) {
    readers.remove(name);
  }

  /* Class method reads one attribute from the input data stream.
   * This method must not be accessible from the outside.  It is
   * called by the Field and Method constructor methods.
   *
   * <p>
   *  此方法不能从外部访问。它是由字段和方法构造方法调用。
   * 
   * 
   * @see    Field
   * @see    Method
   * @param  file Input stream
   * @param  constant_pool Array of constants
   * @return Attribute
   * @throws  IOException
   * @throws  ClassFormatException
   */
  public static final Attribute readAttribute(DataInputStream file,
                                              ConstantPool constant_pool)
    throws IOException, ClassFormatException
  {
    ConstantUtf8 c;
    String       name;
    int          name_index;
    int          length;
    byte         tag = Constants.ATTR_UNKNOWN; // Unknown attribute

    // Get class name from constant pool via `name_index' indirection
    name_index = (int)file.readUnsignedShort();
    c          = (ConstantUtf8)constant_pool.getConstant(name_index,
                                                         Constants.CONSTANT_Utf8);
    name       = c.getBytes();

    // Length of data in bytes
    length = file.readInt();

    // Compare strings to find known attribute
    for(byte i=0; i < Constants.KNOWN_ATTRIBUTES; i++) {
      if(name.equals(Constants.ATTRIBUTE_NAMES[i])) {
        tag = i; // found!
        break;
      }
    }

    // Call proper constructor, depending on `tag'
    switch(tag) {
    case Constants.ATTR_UNKNOWN:
      AttributeReader r = (AttributeReader)readers.get(name);

      if(r != null)
        return r.createAttribute(name_index, length, file, constant_pool);
      else
        return new Unknown(name_index, length, file, constant_pool);

    case Constants.ATTR_CONSTANT_VALUE:
      return new ConstantValue(name_index, length, file, constant_pool);

    case Constants.ATTR_SOURCE_FILE:
      return new SourceFile(name_index, length, file, constant_pool);

    case Constants.ATTR_CODE:
      return new Code(name_index, length, file, constant_pool);

    case Constants.ATTR_EXCEPTIONS:
      return new ExceptionTable(name_index, length, file, constant_pool);

    case Constants.ATTR_LINE_NUMBER_TABLE:
      return new LineNumberTable(name_index, length, file, constant_pool);

    case Constants.ATTR_LOCAL_VARIABLE_TABLE:
      return new LocalVariableTable(name_index, length, file, constant_pool);

    case Constants.ATTR_LOCAL_VARIABLE_TYPE_TABLE:
      return new LocalVariableTypeTable(name_index, length, file, constant_pool);

    case Constants.ATTR_INNER_CLASSES:
      return new InnerClasses(name_index, length, file, constant_pool);

    case Constants.ATTR_SYNTHETIC:
      return new Synthetic(name_index, length, file, constant_pool);

    case Constants.ATTR_DEPRECATED:
      return new Deprecated(name_index, length, file, constant_pool);

    case Constants.ATTR_PMG:
      return new PMGClass(name_index, length, file, constant_pool);

    case Constants.ATTR_SIGNATURE:
      return new Signature(name_index, length, file, constant_pool);

    case Constants.ATTR_STACK_MAP:
      return new StackMap(name_index, length, file, constant_pool);

    default: // Never reached
      throw new IllegalStateException("Ooops! default case reached.");
    }
  }

  /**
  /* <p>
  /* 
   * @return Length of attribute field in bytes.
   */
  public final int   getLength()    { return length; }

  /**
  /* <p>
  /* 
   * @param Attribute length in bytes.
   */
  public final void setLength(int length) {
    this.length = length;
  }

  /**
  /* <p>
  /* 
   * @param name_index of attribute.
   */
  public final void setNameIndex(int name_index) {
    this.name_index = name_index;
  }

  /**
  /* <p>
  /* 
   * @return Name index in constant pool of attribute name.
   */
  public final int getNameIndex() { return name_index; }

  /**
  /* <p>
  /* 
   * @return Tag of attribute, i.e., its type. Value may not be altered, thus
   * there is no setTag() method.
   */
  public final byte  getTag()       { return tag; }

  /**
  /* <p>
  /* 
   * @return Constant pool used by this object.
   * @see ConstantPool
   */
  public final ConstantPool getConstantPool() { return constant_pool; }

  /**
  /* <p>
  /* 
   * @param constant_pool Constant pool to be used for this object.
   * @see ConstantPool
   */
  public final void setConstantPool(ConstantPool constant_pool) {
    this.constant_pool = constant_pool;
  }

  /**
   * Use copy() if you want to have a deep copy(), i.e., with all references
   * copied correctly.
   *
   * <p>
   *  使用复制()如果你想有一个深拷贝(),即所有引用正确复制。
   * 
   * 
   * @return shallow copy of this attribute
   */
  public Object clone() {
    Object o = null;

    try {
      o = super.clone();
    } catch(CloneNotSupportedException e) {
      e.printStackTrace(); // Never occurs
    }

    return o;
  }

  /**
  /* <p>
  /* 
   * @return deep copy of this attribute
   */
  public abstract Attribute copy(ConstantPool constant_pool);

  /**
  /* <p>
  /* 
   * @return attribute name.
   */
  public String toString() {
    return Constants.ATTRIBUTE_NAMES[tag];
  }
}
