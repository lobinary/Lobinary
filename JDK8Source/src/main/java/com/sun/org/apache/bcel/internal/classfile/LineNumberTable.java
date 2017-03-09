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

import  com.sun.org.apache.bcel.internal.Constants;
import  java.io.*;

/**
 * This class represents a table of line numbers for debugging
 * purposes. This attribute is used by the <em>Code</em> attribute. It
 * contains pairs of PCs and line numbers.
 *
 * <p>
 *  此类表示用于调试目的的行号表。此属性由<em> </em>代码属性使用。它包含多对PC和行号。
 * 
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @see     Code
 * @see LineNumber
 */
public final class LineNumberTable extends Attribute {
  private int          line_number_table_length;
  private LineNumber[] line_number_table; // Table of line/numbers pairs

  /*
   * Initialize from another object. Note that both objects use the same
   * references (shallow copy). Use copy() for a physical copy.
   * <p>
   * 从另一个对象初始化。注意两个对象使用相同的引用(浅拷贝)。对物理副本使用copy()。
   * 
   */
  public LineNumberTable(LineNumberTable c) {
    this(c.getNameIndex(), c.getLength(), c.getLineNumberTable(),
         c.getConstantPool());
  }

  /*
  /* <p>
  /* 
   * @param name_index Index of name
   * @param length Content length in bytes
   * @param line_number_table Table of line/numbers pairs
   * @param constant_pool Array of constants
   */
  public LineNumberTable(int name_index, int length,
                         LineNumber[] line_number_table,
                         ConstantPool constant_pool)
  {
    super(Constants.ATTR_LINE_NUMBER_TABLE, name_index, length, constant_pool);
    setLineNumberTable(line_number_table);
  }

  /**
   * Construct object from file stream.
   * <p>
   *  从文件流构造对象。
   * 
   * 
   * @param name_index Index of name
   * @param length Content length in bytes
   * @param file Input stream
   * @throws IOException
   * @param constant_pool Array of constants
   */
  LineNumberTable(int name_index, int length, DataInputStream file,
                  ConstantPool constant_pool) throws IOException
  {
    this(name_index, length, (LineNumber[])null, constant_pool);
    line_number_table_length = (file.readUnsignedShort());
    line_number_table = new LineNumber[line_number_table_length];

    for(int i=0; i < line_number_table_length; i++)
      line_number_table[i] = new LineNumber(file);
  }
  /**
   * Called by objects that are traversing the nodes of the tree implicitely
   * defined by the contents of a Java class. I.e., the hierarchy of methods,
   * fields, attributes, etc. spawns a tree of objects.
   *
   * <p>
   *  由遍历由Java类的内容隐含地定义的树的节点的对象调用。即,方法,字段,属性等的层次结构产生对象树。
   * 
   * 
   * @param v Visitor object
   */
  public void accept(Visitor v) {
    v.visitLineNumberTable(this);
  }
  /**
   * Dump line number table attribute to file stream in binary format.
   *
   * <p>
   *  以二进制格式将行号表属性转储到文件流。
   * 
   * 
   * @param file Output file stream
   * @throws IOException
   */
  public final void dump(DataOutputStream file) throws IOException
  {
    super.dump(file);
    file.writeShort(line_number_table_length);
    for(int i=0; i < line_number_table_length; i++)
      line_number_table[i].dump(file);
  }

  /**
  /* <p>
  /* 
   * @return Array of (pc offset, line number) pairs.
   */
  public final LineNumber[] getLineNumberTable() { return line_number_table; }

  /**
  /* <p>
  /* 
   * @param line_number_table.
   */
  public final void setLineNumberTable(LineNumber[] line_number_table) {
    this.line_number_table = line_number_table;

    line_number_table_length = (line_number_table == null)? 0 :
      line_number_table.length;
  }

  /**
  /* <p>
  /* 
   * @return String representation.
   */
  public final String toString() {
    StringBuffer buf  = new StringBuffer();
    StringBuffer line = new StringBuffer();

    for(int i=0; i < line_number_table_length; i++) {
      line.append(line_number_table[i].toString());

      if(i < line_number_table_length - 1)
        line.append(", ");

      if(line.length() > 72) {
        line.append('\n');
        buf.append(line);
        line.setLength(0);
      }
    }

    buf.append(line);

    return buf.toString();
  }

  /**
   * Map byte code positions to source code lines.
   *
   * <p>
   *  将字节代码位置映射到源代码行。
   * 
   * 
   * @param pos byte code offset
   * @return corresponding line in source code
   */
  public int getSourceLine(int pos) {
    int l = 0, r = line_number_table_length-1;

    if(r < 0) // array is empty
      return -1;

    int min_index = -1, min=-1;

    /* Do a binary search since the array is ordered.
    /* <p>
     */
    do {
      int i = (l + r) / 2;
      int j = line_number_table[i].getStartPC();

      if(j == pos)
        return line_number_table[i].getLineNumber();
      else if(pos < j) // else constrain search area
        r = i - 1;
      else // pos > j
        l = i + 1;

      /* If exact match can't be found (which is the most common case)
       * return the line number that corresponds to the greatest index less
       * than pos.
       * <p>
       *  返回对应于小于pos的最大索引的行号。
       * 
       */
      if(j < pos && j > min) {
        min       = j;
        min_index = i;
      }
    } while(l <= r);

    /* It's possible that we did not find any valid entry for the bytecode
     * offset we were looking for.
     * <p>
     *  偏移我们正在寻找。
     * 
     */
    if (min_index < 0)
      return -1;

    return line_number_table[min_index].getLineNumber();
  }

  /**
  /* <p>
  /* 
   * @return deep copy of this attribute
   */
  public Attribute copy(ConstantPool constant_pool) {
    LineNumberTable c = (LineNumberTable)clone();

    c.line_number_table = new LineNumber[line_number_table_length];
    for(int i=0; i < line_number_table_length; i++)
      c.line_number_table[i] = line_number_table[i].copy();

    c.constant_pool = constant_pool;
    return c;
  }

  public final int getTableLength() { return line_number_table_length; }
}
