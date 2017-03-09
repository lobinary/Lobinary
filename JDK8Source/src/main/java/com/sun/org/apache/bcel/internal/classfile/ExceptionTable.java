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
 * This class represents the table of exceptions that are thrown by a
 * method. This attribute may be used once per method.  The name of
 * this class is <em>ExceptionTable</em> for historical reasons; The
 * Java Virtual Machine Specification, Second Edition defines this
 * attribute using the name <em>Exceptions</em> (which is inconsistent
 * with the other classes).
 *
 * <p>
 * 
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @see     Code
 */
public final class ExceptionTable extends Attribute {
  private int   number_of_exceptions;  // Table of indices into
  private int[] exception_index_table; // constant pool

  /**
   * Initialize from another object. Note that both objects use the same
   * references (shallow copy). Use copy() for a physical copy.
   * <p>
   * 此类表示由方法抛出的异常表。每个方法可以使用此属性一次。
   * 由于历史原因,此类的名称是<em> ExceptionTable </em>; Java虚拟机规范第二版使用名称<em>​​异常</em>(与其他类不一致)定义此属性。
   * 
   */
  public ExceptionTable(ExceptionTable c) {
    this(c.getNameIndex(), c.getLength(), c.getExceptionIndexTable(),
         c.getConstantPool());
  }

  /**
  /* <p>
  /*  从另一个对象初始化。注意两个对象使用相同的引用(浅拷贝)。对物理副本使用copy()。
  /* 
  /* 
   * @param name_index Index in constant pool
   * @param length Content length in bytes
   * @param exception_index_table Table of indices in constant pool
   * @param constant_pool Array of constants
   */
  public ExceptionTable(int        name_index, int length,
                        int[]      exception_index_table,
                        ConstantPool constant_pool)
  {
    super(Constants.ATTR_EXCEPTIONS, name_index, length, constant_pool);
    setExceptionIndexTable(exception_index_table);
  }

  /**
   * Construct object from file stream.
   * <p>
   * 
   * @param name_index Index in constant pool
   * @param length Content length in bytes
   * @param file Input stream
   * @param constant_pool Array of constants
   * @throws IOException
   */
  ExceptionTable(int name_index, int length, DataInputStream file,
                 ConstantPool constant_pool) throws IOException
  {
    this(name_index, length, (int[])null, constant_pool);

    number_of_exceptions  = file.readUnsignedShort();
    exception_index_table = new int[number_of_exceptions];

    for(int i=0; i < number_of_exceptions; i++)
      exception_index_table[i] = file.readUnsignedShort();
  }

  /**
   * Called by objects that are traversing the nodes of the tree implicitely
   * defined by the contents of a Java class. I.e., the hierarchy of methods,
   * fields, attributes, etc. spawns a tree of objects.
   *
   * <p>
   *  从文件流构造对象。
   * 
   * 
   * @param v Visitor object
   */
  public void accept(Visitor v) {
    v.visitExceptionTable(this);
  }

  /**
   * Dump exceptions attribute to file stream in binary format.
   *
   * <p>
   *  由遍历由Java类的内容隐含地定义的树的节点的对象调用。即,方法,字段,属性等的层次结构产生对象树。
   * 
   * 
   * @param file Output file stream
   * @throws IOException
   */
  public final void dump(DataOutputStream file) throws IOException
  {
    super.dump(file);
    file.writeShort(number_of_exceptions);
    for(int i=0; i < number_of_exceptions; i++)
      file.writeShort(exception_index_table[i]);
  }

  /**
  /* <p>
  /*  将异常属性以二进制格式转储到文件流。
  /* 
  /* 
   * @return Array of indices into constant pool of thrown exceptions.
   */
  public final int[] getExceptionIndexTable() {return exception_index_table;}
  /**
  /* <p>
  /* 
   * @return Length of exception table.
   */
  public final int getNumberOfExceptions() { return number_of_exceptions; }

  /**
  /* <p>
  /* 
   * @return class names of thrown exceptions
   */
  public final String[] getExceptionNames() {
    String[] names = new String[number_of_exceptions];
    for(int i=0; i < number_of_exceptions; i++)
      names[i] = constant_pool.getConstantString(exception_index_table[i],
                                                 Constants.CONSTANT_Class).
        replace('/', '.');
    return names;
  }

  /**
  /* <p>
  /* 
   * @param exception_index_table.
   * Also redefines number_of_exceptions according to table length.
   */
  public final void setExceptionIndexTable(int[] exception_index_table) {
    this.exception_index_table = exception_index_table;
    number_of_exceptions       = (exception_index_table == null)? 0 :
      exception_index_table.length;
  }
  /**
  /* <p>
  /* 
   * @return String representation, i.e., a list of thrown exceptions.
   */
  public final String toString() {
    StringBuffer buf = new StringBuffer("");
    String       str;

    for(int i=0; i < number_of_exceptions; i++) {
      str = constant_pool.getConstantString(exception_index_table[i],
                                            Constants.CONSTANT_Class);
      buf.append(Utility.compactClassName(str, false));

      if(i < number_of_exceptions - 1)
        buf.append(", ");
    }

    return buf.toString();
  }

  /**
  /* <p>
  /* 
   * @return deep copy of this attribute
   */
  public Attribute copy(ConstantPool constant_pool) {
    ExceptionTable c = (ExceptionTable)clone();
    c.exception_index_table = (int[])exception_index_table.clone();
    c.constant_pool = constant_pool;
    return c;
  }
}
