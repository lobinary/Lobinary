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
 */

import  com.sun.org.apache.bcel.internal.Constants;
import  java.io.*;

/**
 * This class represents a stack map entry recording the types of
 * local variables and the the of stack items at a given byte code offset.
 * See CLDC specification 5.3.1.2
 *
 * <p>
 *  本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 *  ================================================== ==================。
 * 
 *  该软件包括许多个人代表Apache软件基金会所做的自愿捐款。有关Apache Software Foundation的更多信息,请参阅<http://www.apache.org/>。
 * 
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @see     StackMap
 * @see     StackMapType
 */
public final class StackMapEntry implements Cloneable {
  private int            byte_code_offset;
  private int            number_of_locals;
  private StackMapType[] types_of_locals;
  private int            number_of_stack_items;
  private StackMapType[] types_of_stack_items;
  private ConstantPool   constant_pool;

  /**
   * Construct object from file stream.
   * <p>
   *  该类表示记录局部变量的类型和给定字节代码偏移处的堆栈项的堆栈映射条目。参见CLDC规范5.3.1.2
   * 
   * 
   * @param file Input stream
   * @throws IOException
   */
  StackMapEntry(DataInputStream file, ConstantPool constant_pool) throws IOException
  {
    this(file.readShort(), file.readShort(), null, -1, null, constant_pool);

    types_of_locals = new StackMapType[number_of_locals];
    for(int i=0; i < number_of_locals; i++)
      types_of_locals[i] = new StackMapType(file, constant_pool);

    number_of_stack_items = file.readShort();
    types_of_stack_items = new StackMapType[number_of_stack_items];
    for(int i=0; i < number_of_stack_items; i++)
      types_of_stack_items[i] = new StackMapType(file, constant_pool);
  }

  public StackMapEntry(int byte_code_offset, int number_of_locals,
                       StackMapType[] types_of_locals,
                       int number_of_stack_items,
                       StackMapType[] types_of_stack_items,
                       ConstantPool constant_pool) {
    this.byte_code_offset = byte_code_offset;
    this.number_of_locals = number_of_locals;
    this.types_of_locals = types_of_locals;
    this.number_of_stack_items = number_of_stack_items;
    this.types_of_stack_items = types_of_stack_items;
    this.constant_pool = constant_pool;
  }

  /**
   * Dump stack map entry
   *
   * <p>
   *  从文件流构造对象。
   * 
   * 
   * @param file Output file stream
   * @throws IOException
   */
  public final void dump(DataOutputStream file) throws IOException
  {
    file.writeShort(byte_code_offset);

    file.writeShort(number_of_locals);
    for(int i=0; i < number_of_locals; i++)
      types_of_locals[i].dump(file);

    file.writeShort(number_of_stack_items);
    for(int i=0; i < number_of_stack_items; i++)
      types_of_stack_items[i].dump(file);
  }

  /**
  /* <p>
  /*  转储堆栈映射条目
  /* 
  /* 
   * @return String representation.
   */
  public final String toString() {
    StringBuffer buf = new StringBuffer("(offset=" + byte_code_offset);

    if(number_of_locals > 0) {
      buf.append(", locals={");

      for(int i=0; i < number_of_locals; i++) {
        buf.append(types_of_locals[i]);
        if(i < number_of_locals - 1)
          buf.append(", ");
      }

      buf.append("}");
    }

    if(number_of_stack_items > 0) {
      buf.append(", stack items={");

      for(int i=0; i < number_of_stack_items; i++) {
        buf.append(types_of_stack_items[i]);
        if(i < number_of_stack_items - 1)
          buf.append(", ");
      }

      buf.append("}");
    }

    buf.append(")");

    return buf.toString();
  }


  public void           setByteCodeOffset(int b)               { byte_code_offset = b; }
  public int            getByteCodeOffset()                    { return byte_code_offset; }
  public void           setNumberOfLocals(int n)               { number_of_locals = n; }
  public int            getNumberOfLocals()                    { return number_of_locals; }
  public void           setTypesOfLocals(StackMapType[] t)     { types_of_locals = t; }
  public StackMapType[] getTypesOfLocals()                     { return types_of_locals; }
  public void           setNumberOfStackItems(int n)           { number_of_stack_items = n; }
  public int            getNumberOfStackItems()                { return number_of_stack_items; }
  public void           setTypesOfStackItems(StackMapType[] t) { types_of_stack_items = t; }
  public StackMapType[] getTypesOfStackItems()                 { return types_of_stack_items; }

  /**
  /* <p>
  /* 
   * @return deep copy of this object
   */
  public StackMapEntry copy() {
    try {
      return (StackMapEntry)clone();
    } catch(CloneNotSupportedException e) {}

    return null;
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
    v.visitStackMapEntry(this);
  }

  /**
  /* <p>
  /* 由遍历树的节点的对象调用,该节点由Java类的内容隐式定义。即,方法,字段,属性等的层次结构产生对象树。
  /* 
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
}
