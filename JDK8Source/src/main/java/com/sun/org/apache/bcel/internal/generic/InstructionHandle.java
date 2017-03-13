/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sun.org.apache.bcel.internal.generic;

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

import com.sun.org.apache.bcel.internal.classfile.Utility;
import java.util.HashSet;
import java.util.Collection;
import java.util.HashMap;

/**
 * Instances of this class give users a handle to the instructions contained in
 * an InstructionList. Instruction objects may be used more than once within a
 * list, this is useful because it saves memory and may be much faster.
 *
 * Within an InstructionList an InstructionHandle object is wrapped
 * around all instructions, i.e., it implements a cell in a
 * doubly-linked list. From the outside only the next and the
 * previous instruction (handle) are accessible. One
 * can traverse the list via an Enumeration returned by
 * InstructionList.elements().
 *
 * <p>
 *  这个类的实例为用户提供了包含在指令列表中的指令的句柄。指令对象可以在列表内被多次使用,这是有用的,因为它节省了存储器并且可以更快
 * 
 *  在一个InstructionList中,一个InstructionHandle对象被包裹在所有的指令上,也就是说,它实现了一个双向链表中的一个单元格。
 * 从外部只有下一个指令和前一个指令(句柄)可以被访问。可以通过由InstructionListelements返回的枚举遍历列表()。
 * 
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @see Instruction
 * @see BranchHandle
 * @see InstructionList
 */
public class InstructionHandle implements java.io.Serializable {
  InstructionHandle next, prev;  // Will be set from the outside
  Instruction       instruction;
  protected int     i_position = -1; // byte code offset of instruction
  private HashSet   targeters;
  private HashMap   attributes;

  public final InstructionHandle getNext()        { return next; }
  public final InstructionHandle getPrev()        { return prev; }
  public final Instruction       getInstruction() { return instruction; }

  /**
   * Replace current instruction contained in this handle.
   * Old instruction is disposed using Instruction.dispose().
   * <p>
   * 替换此句柄中包含的当前指令旧指令使用Instructiondispose()
   * 
   */
  public void setInstruction(Instruction i) { // Overridden in BranchHandle
    if(i == null)
      throw new ClassGenException("Assigning null to handle");

    if((this.getClass() != BranchHandle.class) && (i instanceof BranchInstruction))
      throw new ClassGenException("Assigning branch instruction " + i + " to plain handle");

    if(instruction != null)
      instruction.dispose();

    instruction = i;
  }

  /**
   * Temporarily swap the current instruction, without disturbing
   * anything. Meant to be used by a debugger, implementing
   * breakpoints. Current instruction is returned.
   * <p>
   *  暂时交换当前指令,而不干扰任何调试器使用的事物,实现断点返回当前指令
   * 
   */
  public Instruction swapInstruction(Instruction i) {
    Instruction oldInstruction = instruction;
    instruction = i;
    return oldInstruction;
  }

  /*private*/ protected InstructionHandle(Instruction i) {
    setInstruction(i);
  }

  private static InstructionHandle ih_list = null; // List of reusable handles

  /** Factory method.
  /* <p>
  /*  setInstruction(i); }}
  /* 
  /*  private static InstructionHandle ih_list = null; //可重用句柄列表
  /* 
  /*  / **工厂方法
  /* 
   */
  static final InstructionHandle getInstructionHandle(Instruction i) {
    if(ih_list == null)
      return new InstructionHandle(i);
    else {
      InstructionHandle ih = ih_list;
      ih_list = ih.next;

      ih.setInstruction(i);

      return ih;
    }
  }

  /**
   * Called by InstructionList.setPositions when setting the position for every
   * instruction. In the presence of variable length instructions `setPositions()'
   * performs multiple passes over the instruction list to calculate the
   * correct (byte) positions and offsets by calling this function.
   *
   * <p>
   *  在设置每个指令的位置时由InstructionListsetPositions调用在可变长度指令存在的情况下,`setPositions()'在指令表上执行多次遍历,通过调用此函数计算正确的(字节)位
   * 置和偏移量。
   * 
   * 
   * @param offset additional offset caused by preceding (variable length) instructions
   * @param max_offset the maximum offset that may be caused by these instructions
   * @return additional offset caused by possible change of this instruction's length
   */
  protected int updatePosition(int offset, int max_offset) {
    i_position += offset;
    return 0;
  }

  /** @return the position, i.e., the byte code offset of the contained
   * instruction. This is accurate only after
   * InstructionList.setPositions() has been called.
   * <p>
   *  指令只有在调用了InstructionListsetPositions()之后才是准确的
   * 
   */
  public int getPosition() { return i_position; }

  /** Set the position, i.e., the byte code offset of the contained
   * instruction.
   * <p>
   *  指令
   * 
   */
  void setPosition(int pos) { i_position = pos; }

  /** Overridden in BranchHandle
  /* <p>
   */
  protected void addHandle() {
    next    = ih_list;
    ih_list = this;
  }

  /**
   * Delete contents, i.e., remove user access and make handle reusable.
   * <p>
   * 删除内容,即删除用户访问并使句柄可重用
   * 
   */
  void dispose() {
    next = prev = null;
    instruction.dispose();
    instruction = null;
    i_position = -1;
    attributes = null;
    removeAllTargeters();
    addHandle();
  }

  /** Remove all targeters, if any.
  /* <p>
   */
  public void removeAllTargeters() {
    if(targeters != null)
      targeters.clear();
  }

  /**
   * Denote this handle isn't referenced anymore by t.
   * <p>
   *  表示t不再引用此句柄
   * 
   */
  public void removeTargeter(InstructionTargeter t) {
    targeters.remove(t);
  }

  /**
   * Denote this handle is being referenced by t.
   * <p>
   *  表示t正在引用此句柄
   * 
   */
  public void addTargeter(InstructionTargeter t) {
    if(targeters == null)
      targeters = new HashSet();

    //if(!targeters.contains(t))
    targeters.add(t);
  }

  public boolean hasTargeters() {
    return (targeters != null) && (targeters.size() > 0);
  }

  /**
  /* <p>
  /* 
   * @return null, if there are no targeters
   */
  public InstructionTargeter[] getTargeters() {
    if(!hasTargeters())
      return null;

    InstructionTargeter[] t = new InstructionTargeter[targeters.size()];
    targeters.toArray(t);
    return t;
  }

  /** @return a (verbose) string representation of the contained instruction.
  /* <p>
   */
  public String toString(boolean verbose) {
    return Utility.format(i_position, 4, false, ' ') + ": " + instruction.toString(verbose);
  }

  /** @return a string representation of the contained instruction.
  /* <p>
   */
  public String toString() {
    return toString(true);
  }

  /** Add an attribute to an instruction handle.
   *
   * <p>
   * 
   * @param key the key object to store/retrieve the attribute
   * @param attr the attribute to associate with this handle
   */
  public void addAttribute(Object key, Object attr) {
    if(attributes == null)
      attributes = new HashMap(3);

    attributes.put(key, attr);
  }

  /** Delete an attribute of an instruction handle.
   *
   * <p>
   * 
   * @param key the key object to retrieve the attribute
   */
  public void removeAttribute(Object key) {
    if(attributes != null)
      attributes.remove(key);
  }

  /** Get attribute of an instruction handle.
   *
   * <p>
   * 
   * @param key the key object to store/retrieve the attribute
   */
  public Object getAttribute(Object key) {
    if(attributes != null)
      return attributes.get(key);

    return null;
  }

  /** @return all attributes associated with this handle
  /* <p>
   */
  public Collection getAttributes() {
    return attributes.values();
  }

  /** Convenience method, simply calls accept() on the contained instruction.
   *
   * <p>
   * 
   * @param v Visitor object
   */
  public void accept(Visitor v) {
    instruction.accept(v);
  }
}
