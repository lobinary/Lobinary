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
 *  本软件按"原样"提供,任何明示或暗示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 在任何情况下,APACHE软件基金会或其捐赠者均不对任何直接,间接,偶发,特殊,惩罚性或后果性损害(包括但不限于替代商品或服务的采购,使用,数据丢失或利润或业务中断),无论是由于任何责任推理原因,无论是
 * 在合同,严格责任或侵权(包括疏忽或其他方式)中,以任何方式使用本软件,即使已被告知此类软件的可能性损伤。
 *  本软件按"原样"提供,任何明示或暗示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 *  ================================================== ==================。
 * 
 *  该软件包括许多个人代表Apache软件基金会所做的自愿捐款。有关Apache Software Foundation的更多信息,请参阅<http://www.apache.org/>。
 * 
 */

import com.sun.org.apache.bcel.internal.Constants;
import com.sun.org.apache.bcel.internal.classfile.Constant;
import com.sun.org.apache.bcel.internal.util.ByteSequence;
import java.io.*;
import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;

/**
 * This class is a container for a list of <a
 * href="Instruction.html">Instruction</a> objects. Instructions can
 * be appended, inserted, moved, deleted, etc.. Instructions are being
 * wrapped into <a
 * href="InstructionHandle.html">InstructionHandles</a> objects that
 * are returned upon append/insert operations. They give the user
 * (read only) access to the list structure, such that it can be traversed and
 * manipulated in a controlled way.
 *
 * A list is finally dumped to a byte code array with <a
 * href="#getByteCode()">getByteCode</a>.
 *
 * <p>
 * 此类是<a href="Instruction.html">指令</a>对象列表的容器。指令可以被附加,插入,移动,删除等。
 * 指令被包装到在附加/插入操作时返回的<a href="InstructionHandle.html"> InstructionHandles </a>对象中。
 * 它们给予用户(只读)对列表结构的访问,使得它可以以受控的方式被遍历和操纵。
 * 
 *  最终将列表转储到具有<a href="#getByteCode()"> getByteCode </a>的字节代码数组。
 * 
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @see     Instruction
 * @see     InstructionHandle
 * @see BranchHandle
 */
public class InstructionList implements Serializable {
  private InstructionHandle start  = null, end = null;
  private int               length = 0; // number of elements in list
  private int[]             byte_positions; // byte code offsets corresponding to instructions

  /**
   * Create (empty) instruction list.
   * <p>
   *  创建(空)指令列表。
   * 
   */
  public InstructionList() {}

  /**
   * Create instruction list containing one instruction.
   * <p>
   *  创建包含一条指令的指令列表。
   * 
   * 
   * @param i initial instruction
   */
  public InstructionList(Instruction i) {
    append(i);
  }

  /**
   * Create instruction list containing one instruction.
   * <p>
   *  创建包含一条指令的指令列表。
   * 
   * 
   * @param i initial instruction
   */
  public InstructionList(BranchInstruction i) {
    append(i);
  }

  /**
   * Initialize list with (nonnull) compound instruction. Consumes argument
   * list, i.e., it becomes empty.
   *
   * <p>
   *  使用(非空)复合指令初始化列表。使用参数列表,即它变为空。
   * 
   * 
   * @param c compound instruction (list)
   */
  public InstructionList(CompoundInstruction c) {
    append(c.getInstructionList());
  }

  /**
   * Test for empty list.
   * <p>
   *  测试空列表。
   * 
   */
  public boolean isEmpty() { return start == null; } // && end == null

  /**
   * Find the target instruction (handle) that corresponds to the given target
   * position (byte code offset).
   *
   * <p>
   *  找到与给定目标位置(字节代码偏移量)相对应的目标指令(句柄)。
   * 
   * 
   * @param ihs array of instruction handles, i.e. il.getInstructionHandles()
   * @param pos array of positions corresponding to ihs, i.e. il.getInstructionPositions()
   * @param count length of arrays
   * @param target target position to search for
   * @return target position's instruction handle if available
   */
  public static InstructionHandle findHandle(InstructionHandle[] ihs,
                                             int[] pos, int count,
                                             int target) {
    int l=0, r = count - 1;

    /* Do a binary search since the pos array is orderd.
    /* <p>
     */
    do {
      int i = (l + r) / 2;
      int j = pos[i];

      if(j == target) // target found
        return ihs[i];
      else if(target < j) // else constrain search area
        r = i - 1;
      else // target > j
        l = i + 1;
    } while(l <= r);

    return null;
  }

  /**
   * Get instruction handle for instruction at byte code position pos.
   * This only works properly, if the list is freshly initialized from a byte array or
   * setPositions() has been called before this method.
   *
   * <p>
   *  获取字节码位置pos处的指令的指令句柄。这只能正常工作,如果列表刚刚从字节数组初始化或setPositions()在此方法之前被调用。
   * 
   * 
   * @param pos byte code position to search for
   * @return target position's instruction handle if available
   */
  public InstructionHandle findHandle(int pos) {
    InstructionHandle[] ihs = getInstructionHandles();
    return findHandle(ihs, byte_positions, length, pos);
  }

  /**
   * Initialize instruction list from byte array.
   *
   * <p>
   *  从字节数组初始化指令列表。
   * 
   * 
   * @param code byte array containing the instructions
   */
  public InstructionList(byte[] code) {
    ByteSequence        bytes = new ByteSequence(code);
    InstructionHandle[] ihs   = new InstructionHandle[code.length];
    int[]               pos   = new int[code.length]; // Can't be more than that
    int                 count = 0; // Contains actual length

    /* Pass 1: Create an object for each byte code and append them
     * to the list.
     * <p>
     *  到列表。
     * 
     */
    try {
      while(bytes.available() > 0) {
        // Remember byte offset and associate it with the instruction
        int off =  bytes.getIndex();
        pos[count] = off;

        /* Read one instruction from the byte stream, the byte position is set
         * accordingly.
         * <p>
         *  因此。
         * 
         */
        Instruction       i = Instruction.readInstruction(bytes);
        InstructionHandle ih;
        if(i instanceof BranchInstruction) // Use proper append() method
          ih = append((BranchInstruction)i);
        else
          ih = append(i);

        ih.setPosition(off);
        ihs[count] = ih;

        count++;
      }
    } catch(IOException e) { throw new ClassGenException(e.toString()); }

    byte_positions = new int[count]; // Trim to proper size
    System.arraycopy(pos, 0, byte_positions, 0, count);

    /* Pass 2: Look for BranchInstruction and update their targets, i.e.,
     * convert offsets to instruction handles.
     * <p>
     *  将偏移转换为指令句柄。
     * 
     */
    for(int i=0; i < count; i++) {
      if(ihs[i] instanceof BranchHandle) {
        BranchInstruction bi = (BranchInstruction)ihs[i].instruction;
        int target = bi.position + bi.getIndex(); /* Byte code position:
        int target = bi.position + bi.getIndex(); /* <p>
        int target = bi.position + bi.getIndex(); /* 
                                                   * relative -> absolute. */
        // Search for target position
        InstructionHandle ih = findHandle(ihs, pos, count, target);

        if(ih == null) // Search failed
          throw new ClassGenException("Couldn't find target for branch: " + bi);

        bi.setTarget(ih); // Update target

        // If it is a Select instruction, update all branch targets
        if(bi instanceof Select) { // Either LOOKUPSWITCH or TABLESWITCH
          Select s       = (Select)bi;
          int[]  indices = s.getIndices();

          for(int j=0; j < indices.length; j++) {
            target = bi.position + indices[j];
            ih     = findHandle(ihs, pos, count, target);

            if(ih == null) // Search failed
              throw new ClassGenException("Couldn't find target for switch: " + bi);

            s.setTarget(j, ih); // Update target
          }
        }
      }
    }
  }

  /**
   * Append another list after instruction (handle) ih contained in this list.
   * Consumes argument list, i.e., it becomes empty.
   *
   * <p>
   *  在此列表中包含的指令(句柄)ih之后附加另一个列表。使用参数列表,即它变为空。
   * 
   * 
   * @param ih where to append the instruction list
   * @param il Instruction list to append to this one
   * @return instruction handle pointing to the <B>first</B> appended instruction
   */
  public InstructionHandle append(InstructionHandle ih, InstructionList il) {
    if(il == null)
      throw new ClassGenException("Appending null InstructionList");

    if(il.isEmpty()) // Nothing to do
      return ih;

    InstructionHandle next = ih.next, ret = il.start;

    ih.next = il.start;
    il.start.prev = ih;

    il.end.next = next;

    if(next != null) // i == end ?
      next.prev = il.end;
    else
      end = il.end; // Update end ...

    length += il.length; // Update length

    il.clear();

    return ret;
  }

  /**
   * Append another list after instruction i contained in this list.
   * Consumes argument list, i.e., it becomes empty.
   *
   * <p>
   *  在此列表中包含的指令i之后附加另一个列表。使用参数列表,即它变为空。
   * 
   * 
   * @param i  where to append the instruction list
   * @param il Instruction list to append to this one
   * @return instruction handle pointing to the <B>first</B> appended instruction
   */
  public InstructionHandle append(Instruction i, InstructionList il) {
    InstructionHandle ih;

    if((ih = findInstruction2(i)) == null) // Also applies for empty list
      throw new ClassGenException("Instruction " + i +
                                  " is not contained in this list.");

    return append(ih, il);
  }

  /**
   * Append another list to this one.
   * Consumes argument list, i.e., it becomes empty.
   *
   * <p>
   * 将另一个列表附加到此列表。使用参数列表,即它变为空。
   * 
   * 
   * @param il list to append to end of this list
   * @return instruction handle of the <B>first</B> appended instruction
   */
  public InstructionHandle append(InstructionList il) {
    if(il == null)
      throw new ClassGenException("Appending null InstructionList");

    if(il.isEmpty()) // Nothing to do
      return null;

    if(isEmpty()) {
      start  = il.start;
      end    = il.end;
      length = il.length;

      il.clear();

      return start;
    } else
      return append(end, il);  // was end.instruction
  }

  /**
   * Append an instruction to the end of this list.
   *
   * <p>
   *  将指令附加到此列表的末尾。
   * 
   * 
   * @param ih instruction to append
   */
  private void append(InstructionHandle ih) {
    if(isEmpty()) {
      start = end = ih;
      ih.next = ih.prev = null;
    }
    else {
      end.next = ih;
      ih.prev  = end;
      ih.next  = null;
      end      = ih;
    }

    length++; // Update length
  }

  /**
   * Append an instruction to the end of this list.
   *
   * <p>
   *  将指令附加到此列表的末尾。
   * 
   * 
   * @param i instruction to append
   * @return instruction handle of the appended instruction
   */
  public InstructionHandle append(Instruction i) {
    InstructionHandle ih = InstructionHandle.getInstructionHandle(i);
    append(ih);

    return ih;
  }

  /**
   * Append a branch instruction to the end of this list.
   *
   * <p>
   *  将分支指令附加到此列表的结尾。
   * 
   * 
   * @param i branch instruction to append
   * @return branch instruction handle of the appended instruction
   */
  public BranchHandle append(BranchInstruction i) {
    BranchHandle ih = BranchHandle.getBranchHandle(i);
    append(ih);

    return ih;
  }

  /**
   * Append a single instruction j after another instruction i, which
   * must be in this list of course!
   *
   * <p>
   *  在另一个指令i后附加单个指令j,这必须在这个列表中！
   * 
   * 
   * @param i Instruction in list
   * @param j Instruction to append after i in list
   * @return instruction handle of the first appended instruction
   */
  public InstructionHandle append(Instruction i, Instruction j) {
    return append(i, new InstructionList(j));
  }

  /**
   * Append a compound instruction, after instruction i.
   *
   * <p>
   *  在指令i后附加复合指令。
   * 
   * 
   * @param i Instruction in list
   * @param c The composite instruction (containing an InstructionList)
   * @return instruction handle of the first appended instruction
   */
  public InstructionHandle append(Instruction i, CompoundInstruction c) {
    return append(i, c.getInstructionList());
  }

  /**
   * Append a compound instruction.
   *
   * <p>
   *  附加复合指令。
   * 
   * 
   * @param c The composite instruction (containing an InstructionList)
   * @return instruction handle of the first appended instruction
   */
  public InstructionHandle append(CompoundInstruction c) {
    return append(c.getInstructionList());
  }

  /**
   * Append a compound instruction.
   *
   * <p>
   *  附加复合指令。
   * 
   * 
   * @param ih where to append the instruction list
   * @param c The composite instruction (containing an InstructionList)
   * @return instruction handle of the first appended instruction
   */
  public InstructionHandle append(InstructionHandle ih, CompoundInstruction c) {
    return append(ih, c.getInstructionList());
  }

  /**
   * Append an instruction after instruction (handle) ih contained in this list.
   *
   * <p>
   *  在此列表中包含的指令(句柄)ih之后附加指令。
   * 
   * 
   * @param ih where to append the instruction list
   * @param i Instruction to append
   * @return instruction handle pointing to the <B>first</B> appended instruction
   */
  public InstructionHandle append(InstructionHandle ih, Instruction i) {
    return append(ih, new InstructionList(i));
  }

  /**
   * Append an instruction after instruction (handle) ih contained in this list.
   *
   * <p>
   *  在此列表中包含的指令(句柄)ih之后附加指令。
   * 
   * 
   * @param ih where to append the instruction list
   * @param i Instruction to append
   * @return instruction handle pointing to the <B>first</B> appended instruction
   */
  public BranchHandle append(InstructionHandle ih, BranchInstruction i) {
    BranchHandle    bh = BranchHandle.getBranchHandle(i);
    InstructionList il = new InstructionList();
    il.append(bh);

    append(ih, il);

    return bh;
  }

  /**
   * Insert another list before Instruction handle ih contained in this list.
   * Consumes argument list, i.e., it becomes empty.
   *
   * <p>
   *  在此列表中包含的指令句柄ih之前插入另一个列表。使用参数列表,即它变为空。
   * 
   * 
   * @param i  where to append the instruction list
   * @param il Instruction list to insert
   * @return instruction handle of the first inserted instruction
   */
  public InstructionHandle insert(InstructionHandle ih, InstructionList il) {
    if(il == null)
      throw new ClassGenException("Inserting null InstructionList");

    if(il.isEmpty()) // Nothing to do
      return ih;

    InstructionHandle prev = ih.prev, ret = il.start;

    ih.prev = il.end;
    il.end.next = ih;

    il.start.prev = prev;

    if(prev != null) // ih == start ?
      prev.next = il.start;
    else
      start = il.start; // Update start ...

    length += il.length; // Update length

    il.clear();

    return ret;
  }

  /**
   * Insert another list.
   *
   * <p>
   *  插入另一个列表。
   * 
   * 
   * @param il list to insert before start of this list
   * @return instruction handle of the first inserted instruction
   */
  public InstructionHandle insert(InstructionList il) {
    if(isEmpty()) {
      append(il); // Code is identical for this case
      return start;
    }
    else
      return insert(start, il);
  }

  /**
   * Insert an instruction at start of this list.
   *
   * <p>
   *  在此列表的开始处插入指令。
   * 
   * 
   * @param ih instruction to insert
   */
  private void insert(InstructionHandle ih) {
    if(isEmpty()) {
      start = end = ih;
      ih.next = ih.prev = null;
    } else {
      start.prev = ih;
      ih.next    = start;
      ih.prev    = null;
      start      = ih;
    }

    length++;
  }

  /**
   * Insert another list before Instruction i contained in this list.
   * Consumes argument list, i.e., it becomes empty.
   *
   * <p>
   *  在此列表中包含的指令i之前插入另一个列表。使用参数列表,即它变为空。
   * 
   * 
   * @param i  where to append the instruction list
   * @param il Instruction list to insert
   * @return instruction handle pointing to the first inserted instruction,
   * i.e., il.getStart()
   */
  public InstructionHandle insert(Instruction i, InstructionList il) {
    InstructionHandle ih;

    if((ih = findInstruction1(i)) == null)
      throw new ClassGenException("Instruction " + i +
                                  " is not contained in this list.");

    return insert(ih, il);
  }

  /**
   * Insert an instruction at start of this list.
   *
   * <p>
   *  在此列表的开始处插入指令。
   * 
   * 
   * @param i instruction to insert
   * @return instruction handle of the inserted instruction
   */
  public InstructionHandle insert(Instruction i) {
    InstructionHandle ih = InstructionHandle.getInstructionHandle(i);
    insert(ih);

    return ih;
  }

  /**
   * Insert a branch instruction at start of this list.
   *
   * <p>
   *  在此列表的开始处插入分支指令。
   * 
   * 
   * @param i branch instruction to insert
   * @return branch instruction handle of the appended instruction
   */
  public BranchHandle insert(BranchInstruction i) {
    BranchHandle ih = BranchHandle.getBranchHandle(i);
    insert(ih);
    return ih;
  }

  /**
   * Insert a single instruction j before another instruction i, which
   * must be in this list of course!
   *
   * <p>
   *  在另一个指令i之前插入单个指令j,它必须在此列表中！
   * 
   * 
   * @param i Instruction in list
   * @param j Instruction to insert before i in list
   * @return instruction handle of the first inserted instruction
   */
  public InstructionHandle insert(Instruction i, Instruction j) {
    return insert(i, new InstructionList(j));
  }

  /**
   * Insert a compound instruction before instruction i.
   *
   * <p>
   *  在指令i前插入复合指令。
   * 
   * 
   * @param i Instruction in list
   * @param c The composite instruction (containing an InstructionList)
   * @return instruction handle of the first inserted instruction
   */
  public InstructionHandle insert(Instruction i, CompoundInstruction c) {
    return insert(i, c.getInstructionList());
  }

  /**
   * Insert a compound instruction.
   *
   * <p>
   *  插入复合指令。
   * 
   * 
   * @param c The composite instruction (containing an InstructionList)
   * @return instruction handle of the first inserted instruction
   */
  public InstructionHandle insert(CompoundInstruction c) {
    return insert(c.getInstructionList());
  }

  /**
   * Insert an instruction before instruction (handle) ih contained in this list.
   *
   * <p>
   *  在此列表中包含的指令(句柄)ih之前插入指令。
   * 
   * 
   * @param ih where to insert to the instruction list
   * @param i Instruction to insert
   * @return instruction handle of the first inserted instruction
   */
  public InstructionHandle insert(InstructionHandle ih, Instruction i) {
    return insert(ih, new InstructionList(i));
  }

  /**
   * Insert a compound instruction.
   *
   * <p>
   *  插入复合指令。
   * 
   * 
   * @param ih where to insert the instruction list
   * @param c The composite instruction (containing an InstructionList)
   * @return instruction handle of the first inserted instruction
   */
  public InstructionHandle insert(InstructionHandle ih, CompoundInstruction c) {
    return insert(ih, c.getInstructionList());
  }

  /**
   * Insert an instruction before instruction (handle) ih contained in this list.
   *
   * <p>
   *  在此列表中包含的指令(句柄)ih之前插入指令。
   * 
   * 
   * @param ih where to insert to the instruction list
   * @param i Instruction to insert
   * @return instruction handle of the first inserted instruction
   */
  public BranchHandle insert(InstructionHandle ih, BranchInstruction i) {
    BranchHandle    bh = BranchHandle.getBranchHandle(i);
    InstructionList il = new InstructionList();
    il.append(bh);

    insert(ih, il);

    return bh;
  }

  /**
   * Take all instructions (handles) from "start" to "end" and append them after the
   * new location "target". Of course, "end" must be after "start" and target must
   * not be located withing this range. If you want to move something to the start of
   * the list use null as value for target.<br>
   * Any instruction targeters pointing to handles within the block, keep their targets.
   *
   * <p>
   * 将所有指令(句柄)从"开始"到"结束",并将它们附加到新位置"目标"之后。当然,"结束"必须在"开始"之后,并且目标不能与该范围一起定位。
   * 如果你想将某个东西移动到列表的开头,请使用null作为目标的值。<br>任何指令指向程序块中的句柄,保留它们的目标。
   * 
   * 
   * @param start  of moved block
   * @param end    of moved block
   * @param target of moved block
   */
  public void move(InstructionHandle start, InstructionHandle end, InstructionHandle target) {
    // Step 1: Check constraints

    if((start == null) || (end == null))
      throw new ClassGenException("Invalid null handle: From " + start + " to " + end);

    if((target == start) || (target == end))
      throw new ClassGenException("Invalid range: From " + start + " to " + end +
                                  " contains target " + target);

    for(InstructionHandle ih = start; ih != end.next; ih = ih.next) {
      if(ih == null) // At end of list, end not found yet
        throw new ClassGenException("Invalid range: From " + start + " to " + end);
      else if(ih == target) // target may be null
        throw new ClassGenException("Invalid range: From " + start + " to " + end +
                                    " contains target " + target);
    }

    // Step 2: Temporarily remove the given instructions from the list

    InstructionHandle prev = start.prev, next = end.next;

    if(prev != null)
      prev.next = next;
    else // start == this.start!
      this.start = next;

    if(next != null)
      next.prev = prev;
    else // end == this.end!
      this.end = prev;

    start.prev = end.next = null;

    // Step 3: append after target

    if(target == null) { // append to start of list
      end.next = this.start;
      this.start = start;
    } else {
      next = target.next;

      target.next = start;
      start.prev  = target;
      end.next    = next;

      if(next != null)
        next.prev = end;
    }
  }

  /**
   * Move a single instruction (handle) to a new location.
   *
   * <p>
   *  将单个指令(句柄)移动到新位置。
   * 
   * 
   * @param ih     moved instruction
   * @param target new location of moved instruction
   */
  public void move(InstructionHandle ih, InstructionHandle target) {
    move(ih, ih, target);
  }

  /**
   * Remove from instruction `prev' to instruction `next' both contained
   * in this list. Throws TargetLostException when one of the removed instruction handles
   * is still being targeted.
   *
   * <p>
   *  从指令`prev'移除到此列表中包含的指令`next'。当其中一个已删除的指令句柄仍在定向时抛出TargetLostException。
   * 
   * 
   * @param prev where to start deleting (predecessor, exclusive)
   * @param next where to end deleting (successor, exclusive)
   */
  private void remove(InstructionHandle prev, InstructionHandle next)
    throws TargetLostException
  {
    InstructionHandle first, last; // First and last deleted instruction

    if((prev == null) && (next == null)) { // singleton list
      first = last = start;
      start = end = null;
    } else {
      if(prev == null) { // At start of list
        first = start;
        start = next;
      } else {
        first     = prev.next;
        prev.next = next;
      }

      if(next == null) { // At end of list
        last = end;
        end  = prev;
      } else {
        last      = next.prev;
        next.prev = prev;
      }
    }

    first.prev = null; // Completely separated from rest of list
    last.next  = null;

    ArrayList target_vec = new ArrayList();

    for(InstructionHandle ih=first; ih != null; ih = ih.next)
      ih.getInstruction().dispose(); // e.g. BranchInstructions release their targets

    StringBuffer buf = new StringBuffer("{ ");
    for(InstructionHandle ih=first; ih != null; ih = next) {
      next = ih.next;
      length--;

      if(ih.hasTargeters()) { // Still got targeters?
        target_vec.add(ih);
        buf.append(ih.toString(true) + " ");
        ih.next = ih.prev = null;
      } else
        ih.dispose();
    }

    buf.append("}");

    if(!target_vec.isEmpty()) {
      InstructionHandle[] targeted = new InstructionHandle[target_vec.size()];
      target_vec.toArray(targeted);
      throw new TargetLostException(targeted, buf.toString());
    }
  }

  /**
   * Remove instruction from this list. The corresponding Instruction
   * handles must not be reused!
   *
   * <p>
   *  从此列表中删除指令。相应的指令句柄不能重复使用！
   * 
   * 
   * @param ih instruction (handle) to remove
   */
  public void delete(InstructionHandle ih) throws TargetLostException {
    remove(ih.prev, ih.next);
  }

  /**
   * Remove instruction from this list. The corresponding Instruction
   * handles must not be reused!
   *
   * <p>
   *  从此列表中删除指令。相应的指令句柄不能重复使用！
   * 
   * 
   * @param i instruction to remove
   */
  public void delete(Instruction i) throws TargetLostException {
    InstructionHandle ih;

    if((ih = findInstruction1(i)) == null)
      throw new ClassGenException("Instruction " + i +
                                  " is not contained in this list.");
    delete(ih);
  }

  /**
   * Remove instructions from instruction `from' to instruction `to' contained
   * in this list. The user must ensure that `from' is an instruction before
   * `to', or risk havoc. The corresponding Instruction handles must not be reused!
   *
   * <p>
   *  删除包含在此列表中的指令"从"到指令"到"的指令。用户必须确保`from'是在`to'之前的一条指令,或危险的危险。相应的指令句柄不能重复使用！
   * 
   * 
   * @param from where to start deleting (inclusive)
   * @param to   where to end deleting (inclusive)
   */
  public void delete(InstructionHandle from, InstructionHandle to)
    throws TargetLostException
  {
    remove(from.prev, to.next);
  }

  /**
   * Remove instructions from instruction `from' to instruction `to' contained
   * in this list. The user must ensure that `from' is an instruction before
   * `to', or risk havoc. The corresponding Instruction handles must not be reused!
   *
   * <p>
   *  删除包含在此列表中的指令"从"到指令"到"的指令。用户必须确保`from'是在`to'之前的一条指令,或危险的危险。相应的指令句柄不能重复使用！
   * 
   * 
   * @param from where to start deleting (inclusive)
   * @param to   where to end deleting (inclusive)
   */
  public void delete(Instruction from, Instruction to) throws TargetLostException {
    InstructionHandle from_ih, to_ih;

    if((from_ih = findInstruction1(from)) == null)
      throw new ClassGenException("Instruction " + from +
                                  " is not contained in this list.");

    if((to_ih = findInstruction2(to)) == null)
      throw new ClassGenException("Instruction " + to +
                                  " is not contained in this list.");
    delete(from_ih, to_ih);
  }

  /**
   * Search for given Instruction reference, start at beginning of list.
   *
   * <p>
   *  搜索给定的指令引用,从列表的开始处开始。
   * 
   * 
   * @param i instruction to search for
   * @return instruction found on success, null otherwise
   */
  private InstructionHandle findInstruction1(Instruction i) {
    for(InstructionHandle ih=start; ih != null; ih = ih.next)
      if(ih.instruction == i)
        return ih;

    return null;
  }

  /**
   * Search for given Instruction reference, start at end of list
   *
   * <p>
   *  搜索给定的指令引用,从列表的结尾开始
   * 
   * 
   * @param i instruction to search for
   * @return instruction found on success, null otherwise
   */
  private InstructionHandle findInstruction2(Instruction i) {
    for(InstructionHandle ih=end; ih != null; ih = ih.prev)
      if(ih.instruction == i)
        return ih;

    return null;
  }

  public boolean contains(InstructionHandle i) {
    if(i == null)
      return false;

    for(InstructionHandle ih=start; ih != null; ih = ih.next)
      if(ih == i)
        return true;

    return false;
  }

  public boolean contains(Instruction i) {
    return findInstruction1(i) != null;
  }

  public void setPositions() {
    setPositions(false);
  }

  /**
   * Give all instructions their position number (offset in byte stream), i.e.,
   * make the list ready to be dumped.
   *
   * <p>
   *  给所有指令它们的位置号(字节流中的偏移量),即使该列表准备被转储。
   * 
   * 
   * @param check Perform sanity checks, e.g. if all targeted instructions really belong
   * to this list
   */
  public void setPositions(boolean check) {
    int max_additional_bytes = 0, additional_bytes = 0;
    int index = 0, count = 0;
    int[] pos = new int[length];

    /* Pass 0: Sanity checks
    /* <p>
     */
    if(check) {
      for(InstructionHandle ih=start; ih != null; ih = ih.next) {
        Instruction i = ih.instruction;

        if(i instanceof BranchInstruction) { // target instruction within list?
          Instruction inst = ((BranchInstruction)i).getTarget().instruction;
          if(!contains(inst))
            throw new ClassGenException("Branch target of " +
                                        Constants.OPCODE_NAMES[i.opcode] + ":" +
                                        inst + " not in instruction list");

          if(i instanceof Select) {
            InstructionHandle[] targets = ((Select)i).getTargets();

            for(int j=0; j < targets.length; j++) {
              inst = targets[j].instruction;
              if(!contains(inst))
                throw new ClassGenException("Branch target of " +
                                            Constants.OPCODE_NAMES[i.opcode] + ":" +
                                            inst + " not in instruction list");
            }
          }

          if(!(ih instanceof BranchHandle))
            throw new ClassGenException("Branch instruction " +
                                        Constants.OPCODE_NAMES[i.opcode] + ":" +
                                        inst + " not contained in BranchHandle.");

        }
      }
    }

    /* Pass 1: Set position numbers and sum up the maximum number of bytes an
     * instruction may be shifted.
     * <p>
     * 指令可以被移位。
     * 
     */
    for(InstructionHandle ih=start; ih != null; ih = ih.next) {
      Instruction i = ih.instruction;

      ih.setPosition(index);
      pos[count++] = index;

      /* Get an estimate about how many additional bytes may be added, because
       * BranchInstructions may have variable length depending on the target
       * offset (short vs. int) or alignment issues (TABLESWITCH and
       * LOOKUPSWITCH).
       * <p>
       *  BranchInstructions可以具有可变长度,这取决于目标偏移(短与int)或对齐问题(TABLESWITCH和LOOKUPSWITCH)。
       * 
       */
      switch(i.getOpcode()) {
      case Constants.JSR: case Constants.GOTO:
        max_additional_bytes += 2;
        break;

      case Constants.TABLESWITCH: case Constants.LOOKUPSWITCH:
        max_additional_bytes += 3;
        break;
      }

      index += i.getLength();
    }

    /* Pass 2: Expand the variable-length (Branch)Instructions depending on
     * the target offset (short or int) and ensure that branch targets are
     * within this list.
     * <p>
     *  目标偏移(short或int),并确保分支目标在此列表内。
     * 
     */
    for(InstructionHandle ih=start; ih != null; ih = ih.next)
      additional_bytes += ih.updatePosition(additional_bytes, max_additional_bytes);

    /* Pass 3: Update position numbers (which may have changed due to the
     * preceding expansions), like pass 1.
     * <p>
     *  之前的扩展),如通过1。
     * 
     */
    index=count=0;
    for(InstructionHandle ih=start; ih != null; ih = ih.next) {
      Instruction i = ih.instruction;

      ih.setPosition(index);
      pos[count++] = index;
      index += i.getLength();
    }

    byte_positions = new int[count]; // Trim to proper size
    System.arraycopy(pos, 0, byte_positions, 0, count);
  }

  /**
   * When everything is finished, use this method to convert the instruction
   * list into an array of bytes.
   *
   * <p>
   *  当一切完成后,使用此方法将指令列表转换为字节数组。
   * 
   * 
   * @return the byte code ready to be dumped
   */
  public byte[] getByteCode() {
    // Update position indices of instructions
    setPositions();

    ByteArrayOutputStream b   = new ByteArrayOutputStream();
    DataOutputStream      out = new DataOutputStream(b);

    try {
      for(InstructionHandle ih=start; ih != null; ih = ih.next) {
        Instruction i = ih.instruction;
        i.dump(out); // Traverse list
      }
    } catch(IOException e) {
      System.err.println(e);
      return null;
    }

    return b.toByteArray();
  }

  /**
  /* <p>
  /* 
   * @return an array of instructions without target information for branch instructions.
   */
  public Instruction[] getInstructions() {
    ByteSequence  bytes        = new ByteSequence(getByteCode());
    ArrayList     instructions = new ArrayList();

    try {
      while(bytes.available() > 0) {
        instructions.add(Instruction.readInstruction(bytes));
      }
    } catch(IOException e) { throw new ClassGenException(e.toString()); }

    Instruction[] result = new Instruction[instructions.size()];
    instructions.toArray(result);
    return result;
  }

  public String toString() {
    return toString(true);
  }

  /**
  /* <p>
  /* 
   * @param verbose toggle output format
   * @return String containing all instructions in this list.
   */
  public String toString(boolean verbose) {
    StringBuffer buf = new StringBuffer();

    for(InstructionHandle ih=start; ih != null; ih = ih.next) {
      buf.append(ih.toString(verbose) + "\n");
    }

    return buf.toString();
  }

  /**
  /* <p>
  /* 
   * @return Enumeration that lists all instructions (handles)
   */
  public Iterator iterator() {
    return new Iterator() {
      private InstructionHandle ih = start;

      public Object next() {
        InstructionHandle i = ih;
        ih = ih.next;
        return i;
      }

      public void remove() {
        throw new UnsupportedOperationException();
      }

      public boolean hasNext() { return ih != null; }
    };
  }

  /**
  /* <p>
  /* 
   * @return array containing all instructions (handles)
   */
  public InstructionHandle[] getInstructionHandles() {
    InstructionHandle[] ihs = new InstructionHandle[length];
    InstructionHandle   ih  = start;

    for(int i=0; i < length; i++) {
      ihs[i] = ih;
      ih = ih.next;
    }

    return ihs;
  }

  /**
   * Get positions (offsets) of all instructions in the list. This relies on that
   * the list has been freshly created from an byte code array, or that setPositions()
   * has been called. Otherwise this may be inaccurate.
   *
   * <p>
   *  获取列表中所有指令的位置(偏移量)。这依赖于列表是从字节代码数组新创建的,或者setPositions()已被调用。否则,这可能不准确。
   * 
   * 
   * @return array containing all instruction's offset in byte code
   */
  public int[] getInstructionPositions() { return byte_positions; }

  /**
  /* <p>
  /* 
   * @return complete, i.e., deep copy of this list
   */
  public InstructionList copy() {
    HashMap         map = new HashMap();
    InstructionList il  = new InstructionList();

    /* Pass 1: Make copies of all instructions, append them to the new list
     * and associate old instruction references with the new ones, i.e.,
     * a 1:1 mapping.
     * <p>
     *  并将旧的指令引用与新的指令引用相关联,即1：1映射。
     * 
     */
    for(InstructionHandle ih=start; ih != null; ih = ih.next) {
      Instruction i = ih.instruction;
      Instruction c = i.copy(); // Use clone for shallow copy

      if(c instanceof BranchInstruction)
        map.put(ih, il.append((BranchInstruction)c));
      else
        map.put(ih, il.append(c));
    }

    /* Pass 2: Update branch targets.
    /* <p>
     */
    InstructionHandle ih=start;
    InstructionHandle ch=il.start;

    while(ih != null) {
      Instruction i = ih.instruction;
      Instruction c = ch.instruction;

      if(i instanceof BranchInstruction) {
        BranchInstruction bi      = (BranchInstruction)i;
        BranchInstruction bc      = (BranchInstruction)c;
        InstructionHandle itarget = bi.getTarget(); // old target

        // New target is in hash map
        bc.setTarget((InstructionHandle)map.get(itarget));

        if(bi instanceof Select) { // Either LOOKUPSWITCH or TABLESWITCH
          InstructionHandle[] itargets = ((Select)bi).getTargets();
          InstructionHandle[] ctargets = ((Select)bc).getTargets();

          for(int j=0; j < itargets.length; j++) { // Update all targets
            ctargets[j] = (InstructionHandle)map.get(itargets[j]);
          }
        }
      }

      ih = ih.next;
      ch = ch.next;
    }

    return il;
  }

  /** Replace all references to the old constant pool with references to the new
   *  constant pool
   * <p>
   *  常数池
   * 
   */
  public void replaceConstantPool(ConstantPoolGen old_cp, ConstantPoolGen new_cp) {
    for(InstructionHandle ih=start; ih != null; ih = ih.next) {
      Instruction i = ih.instruction;

      if(i instanceof CPInstruction) {
        CPInstruction ci = (CPInstruction)i;
        Constant      c  = old_cp.getConstant(ci.getIndex());
        ci.setIndex(new_cp.addConstant(c, old_cp));
      }
    }
  }

  private void clear() {
    start = end = null;
    length = 0;
  }

  /**
   * Delete contents of list. Provides besser memory utilization,
   * because the system then may reuse the instruction handles. This
   * method is typically called right after
   * <href="MethodGen.html#getMethod()">MethodGen.getMethod()</a>.
   * <p>
   *  删除列表的内容。提供besser内存利用,因为系统然后可以重用指令句柄。
   * 此方法通常在<href ="MethodGen.html#getMethod()"> MethodGen.getMethod()</a>之后调用。
   * 
   */
  public void dispose() {
    // Traverse in reverse order, because ih.next is overwritten
    for(InstructionHandle ih=end; ih != null; ih = ih.prev)
      /* Causes BranchInstructions to release target and targeters, because it
       * calls dispose() on the contained instruction.
       * <p>
       *  在包含的指令上调用dispose()。
       * 
       */
      ih.dispose();

    clear();
  }

  /**
  /* <p>
  /* 
   * @return start of list
   */
  public InstructionHandle getStart() { return start; }

  /**
  /* <p>
  /* 
   * @return end of list
   */
  public InstructionHandle getEnd()   { return end; }

  /**
  /* <p>
  /* 
   * @return length of list (Number of instructions, not bytes)
   */
  public int getLength() { return length; }

  /**
  /* <p>
  /* 
   * @return length of list (Number of instructions, not bytes)
   */
  public int size() { return length; }

  /**
   * Redirect all references from old_target to new_target, i.e., update targets
   * of branch instructions.
   *
   * <p>
   *  将所有引用从old_target重定向到new_target,即更新分支指令的目标。
   * 
   * 
   * @param old_target the old target instruction handle
   * @param new_target the new target instruction handle
   */
  public void redirectBranches(InstructionHandle old_target,
                               InstructionHandle new_target) {
    for(InstructionHandle ih = start; ih != null; ih = ih.next) {
      Instruction i  = ih.getInstruction();

      if(i instanceof BranchInstruction) {
        BranchInstruction b      = (BranchInstruction)i;
        InstructionHandle target = b.getTarget();

        if(target == old_target)
          b.setTarget(new_target);

        if(b instanceof Select) { // Either LOOKUPSWITCH or TABLESWITCH
          InstructionHandle[] targets = ((Select)b).getTargets();

          for(int j=0; j < targets.length; j++) // Update targets
            if(targets[j] == old_target)
              ((Select)b).setTarget(j, new_target);
        }
      }
    }
  }

  /**
   * Redirect all references of local variables from old_target to new_target.
   *
   * <p>
   *  将局部变量的所有引用从old_target重定向到new_target。
   * 
   * 
   * @param lg array of local variables
   * @param old_target the old target instruction handle
   * @param new_target the new target instruction handle
   * @see MethodGen
   */
  public void redirectLocalVariables(LocalVariableGen[] lg,
                                     InstructionHandle old_target,
                                     InstructionHandle new_target) {
    for(int i=0; i < lg.length; i++) {
      InstructionHandle start = lg[i].getStart();
      InstructionHandle end   = lg[i].getEnd();

      if(start == old_target)
        lg[i].setStart(new_target);

      if(end == old_target)
        lg[i].setEnd(new_target);
    }
  }

  /**
   * Redirect all references of exception handlers from old_target to new_target.
   *
   * <p>
   *  将异常处理程序的所有引用从old_target重定向到new_target。
   * 
   * 
   * @param exceptions array of exception handlers
   * @param old_target the old target instruction handle
   * @param new_target the new target instruction handle
   * @see MethodGen
   */
  public void redirectExceptionHandlers(CodeExceptionGen[] exceptions,
                                        InstructionHandle old_target,
                                        InstructionHandle new_target) {
    for(int i=0; i < exceptions.length; i++) {
      if(exceptions[i].getStartPC() == old_target)
        exceptions[i].setStartPC(new_target);

      if(exceptions[i].getEndPC() == old_target)
        exceptions[i].setEndPC(new_target);

      if(exceptions[i].getHandlerPC() == old_target)
        exceptions[i].setHandlerPC(new_target);
    }
  }

  private ArrayList observers;

  /** Add observer for this object.
  /* <p>
   */
  public void addObserver(InstructionListObserver o) {
    if(observers == null)
      observers = new ArrayList();

    observers.add(o);
  }

  /** Remove observer for this object.
  /* <p>
   */
  public void removeObserver(InstructionListObserver o) {
    if(observers != null)
      observers.remove(o);
  }

  /** Call notify() method on all observers. This method is not called
   * automatically whenever the state has changed, but has to be
   * called by the user after he has finished editing the object.
   * <p>
   *  每当状态改变时自动地,但是在用户完成编辑对象之后必须被用户调用。
   */
  public void update() {
    if(observers != null)
      for(Iterator e = observers.iterator(); e.hasNext(); )
        ((InstructionListObserver)e.next()).notify(this);
  }
}
