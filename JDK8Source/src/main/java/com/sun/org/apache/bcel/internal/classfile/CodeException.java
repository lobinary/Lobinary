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
 * This class represents an entry in the exception table of the <em>Code</em>
 * attribute and is used only there. It contains a range in which a
 * particular exception handler is active.
 *
 * <p>
 *  此类表示<em> </em>属性的异常表中的一个条目,仅用于其中。它包含特定异常处理程序处于活动状态的范围
 * 
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @see     Code
 */
public final class CodeException
  implements Cloneable, Constants, Node, Serializable
{
  private int start_pc;   // Range in the code the exception handler is
  private int end_pc;     // active. start_pc is inclusive, end_pc exclusive
  private int handler_pc; /* Starting address of exception handler, i.e.,
                           * an offset from start of code.
                           * <p>
                           *  从代码开始的偏移量
                           * 
                           */
  private int catch_type; /* If this is zero the handler catches any
                           * exception, otherwise it points to the
                           * exception class which is to be caught.
                           * <p>
                           *  异常,否则指向要捕获的异常类
                           * 
                           */
  /**
   * Initialize from another object.
   * <p>
   *  从另一个对象初始化
   * 
   */
  public CodeException(CodeException c) {
    this(c.getStartPC(), c.getEndPC(), c.getHandlerPC(), c.getCatchType());
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
  CodeException(DataInputStream file) throws IOException
  {
    this(file.readUnsignedShort(), file.readUnsignedShort(),
         file.readUnsignedShort(), file.readUnsignedShort());
  }

  /**
  /* <p>
  /* 
   * @param start_pc Range in the code the exception handler is active,
   * start_pc is inclusive while
   * @param end_pc is exclusive
   * @param handler_pc Starting address of exception handler, i.e.,
   * an offset from start of code.
   * @param catch_type If zero the handler catches any
   * exception, otherwise it points to the exception class which is
   * to be caught.
   */
  public CodeException(int start_pc, int end_pc, int handler_pc,
                       int catch_type)
  {
    this.start_pc   = start_pc;
    this.end_pc     = end_pc;
    this.handler_pc = handler_pc;
    this.catch_type = catch_type;
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
    v.visitCodeException(this);
  }
  /**
   * Dump code exception to file stream in binary format.
   *
   * <p>
   * 以二进制格式将代码异常转储到文件流
   * 
   * 
   * @param file Output file stream
   * @throws IOException
   */
  public final void dump(DataOutputStream file) throws IOException
  {
    file.writeShort(start_pc);
    file.writeShort(end_pc);
    file.writeShort(handler_pc);
    file.writeShort(catch_type);
  }

  /**
  /* <p>
  /* 
   * @return 0, if the handler catches any exception, otherwise it points to
   * the exception class which is to be caught.
   */
  public final int getCatchType() { return catch_type; }

  /**
  /* <p>
  /* 
   * @return Exclusive end index of the region where the handler is active.
   */
  public final int getEndPC() { return end_pc; }

  /**
  /* <p>
  /* 
   * @return Starting address of exception handler, relative to the code.
   */
  public final int getHandlerPC() { return handler_pc; }

  /**
  /* <p>
  /* 
   * @return Inclusive start index of the region where the handler is active.
   */
  public final int getStartPC() { return start_pc; }

  /**
  /* <p>
  /* 
   * @param catch_type.
   */
  public final void setCatchType(int catch_type) {
    this.catch_type = catch_type;
  }

  /**
  /* <p>
  /* 
   * @param end_pc end of handled block
   */
  public final void setEndPC(int end_pc) {
    this.end_pc = end_pc;
  }

  /**
  /* <p>
  /* 
   * @param handler_pc where the actual code is
   */
  public final void setHandlerPC(int handler_pc) {
    this.handler_pc = handler_pc;
  }

  /**
  /* <p>
  /* 
   * @param start_pc start of handled block
   */
  public final void setStartPC(int start_pc) {
    this.start_pc = start_pc;
  }

  /**
  /* <p>
  /* 
   * @return String representation.
   */
  public final String toString() {
    return "CodeException(start_pc = " + start_pc +
      ", end_pc = " + end_pc +
      ", handler_pc = " + handler_pc + ", catch_type = " + catch_type + ")";
  }

  /**
  /* <p>
  /* 
   * @return String representation.
   */
  public final String toString(ConstantPool cp, boolean verbose) {
    String str;

    if(catch_type == 0)
      str = "<Any exception>(0)";
    else
      str = Utility.compactClassName(cp.getConstantString(catch_type, CONSTANT_Class), false) +
        (verbose? "(" + catch_type + ")" : "");

    return start_pc + "\t" + end_pc + "\t" + handler_pc + "\t" + str;
  }

  public final String toString(ConstantPool cp) {
    return toString(cp, true);
  }

  /**
  /* <p>
  /* 
   * @return deep copy of this object
   */
  public CodeException copy() {
    try {
      return (CodeException)clone();
    } catch(CloneNotSupportedException e) {}

    return null;
  }
}
