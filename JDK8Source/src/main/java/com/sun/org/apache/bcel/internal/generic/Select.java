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
import java.io.*;
import com.sun.org.apache.bcel.internal.util.ByteSequence;

/**
 * Select - Abstract super class for LOOKUPSWITCH and TABLESWITCH instructions.
 *
 * <p>
 *  选择 -  LOOKUPSWITCH和TABLESWITCH指令的抽象超类
 * 
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @see LOOKUPSWITCH
 * @see TABLESWITCH
 * @see InstructionList
 */
public abstract class Select extends BranchInstruction
  implements VariableLengthInstruction, StackProducer
{
  protected int[]               match;        // matches, i.e., case 1: ...
  protected int[]               indices;      // target offsets
  protected InstructionHandle[] targets;      // target objects in instruction list
  protected int                 fixed_length; // fixed length defined by subclasses
  protected int                 match_length; // number of cases
  protected int                 padding = 0;  // number of pad bytes for alignment

  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   * <p>
   *  InstructionreadInstruction()中的ClassnewInstance()语句所需的空构造函数否则不要使用
   * 
   */
  Select() {}

  /**
   * (Match, target) pairs for switch.
   * `Match' and `targets' must have the same length of course.
   *
   * <p>
   *  (匹配,目标)对开关`Match'和`targets'必须具有相同的长度
   * 
   * 
   * @param match array of matching values
   * @param targets instruction targets
   * @param target default instruction target
   */
  Select(short opcode, int[] match, InstructionHandle[] targets,
         InstructionHandle target) {
    super(opcode, target);

    this.targets = targets;
    for(int i=0; i < targets.length; i++) {
      BranchInstruction.notifyTargetChanged(targets[i], this);
    }

    this.match = match;

    if((match_length = match.length) != targets.length)
      throw new ClassGenException("Match and target array have not the same length");

    indices = new int[match_length];
  }

  /**
   * Since this is a variable length instruction, it may shift the following
   * instructions which then need to update their position.
   *
   * Called by InstructionList.setPositions when setting the position for every
   * instruction. In the presence of variable length instructions `setPositions'
   * performs multiple passes over the instruction list to calculate the
   * correct (byte) positions and offsets by calling this function.
   *
   * <p>
   *  由于这是一个可变长度指令,它可以移动以下指令,然后需要更新它们的位置
   * 
   * 在设置每个指令的位置时由InstructionListsetPositions调用在变长指令存在的情况下,`setPositions'在指令表上执行多次遍历,通过调用此函数来计算正确的(字节)位置和偏移
   * 量。
   * 
   * 
   * @param offset additional offset caused by preceding (variable length) instructions
   * @param max_offset the maximum offset that may be caused by these instructions
   * @return additional offset caused by possible change of this instruction's length
   */
  @Override
  protected int updatePosition(int offset, int max_offset) {
    position += offset; // Additional offset caused by preceding SWITCHs, GOTOs, etc.

    short old_length = length;

    /* Alignment on 4-byte-boundary, + 1, because of tag byte.
    /* <p>
     */
    padding = (4 - ((position + 1) % 4)) % 4;
    length  = (short)(fixed_length + padding); // Update length

    return length - old_length;
  }

  /**
   * Dump instruction as byte code to stream out.
   * <p>
   *  转储指令作为字节码流输出
   * 
   * 
   * @param out Output stream
   */
  @Override
  public void dump(DataOutputStream out) throws IOException {
    out.writeByte(opcode);

    for(int i=0; i < padding; i++) // Padding bytes
      out.writeByte(0);

    index = getTargetOffset();     // Write default target offset
    out.writeInt(index);
  }

  /**
   * Read needed data (e.g. index) from file.
   * <p>
   *  从文件中读取所需的数据(例如索引)
   * 
   */
  @Override
  protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException
  {
    padding = (4 - (bytes.getIndex() % 4)) % 4; // Compute number of pad bytes

    for(int i=0; i < padding; i++) {
      bytes.readByte();
    }

    // Default branch target common for both cases (TABLESWITCH, LOOKUPSWITCH)
    index = bytes.readInt();
  }

  /**
  /* <p>
  /* 
   * @return mnemonic for instruction
   */
  @Override
  public String toString(boolean verbose) {
    final StringBuilder buf = new StringBuilder(super.toString(verbose));

    if(verbose) {
      for(int i=0; i < match_length; i++) {
        String s = "null";

        if(targets[i] != null)
          s = targets[i].getInstruction().toString();

          buf.append("(").append(match[i]).append(", ")
             .append(s).append(" = {").append(indices[i]).append("})");
      }
    }
    else
      buf.append(" ...");

    return buf.toString();
  }

  /**
   * Set branch target for `i'th case
   * <p>
   *  设置第i种情况的分支目标
   * 
   */
  public final void setTarget(int i, InstructionHandle target) {
    notifyTargetChanging(targets[i], this);
    targets[i] = target;
    notifyTargetChanged(targets[i], this);
  }

  /**
  /* <p>
  /* 
   * @param old_ih old target
   * @param new_ih new target
   */
  @Override
  public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih) {
    boolean targeted = false;

    if(target == old_ih) {
      targeted = true;
      setTarget(new_ih);
    }

    for(int i=0; i < targets.length; i++) {
      if(targets[i] == old_ih) {
        targeted = true;
        setTarget(i, new_ih);
      }
    }

    if(!targeted)
      throw new ClassGenException("Not targeting " + old_ih);
  }

  /**
  /* <p>
  /* 
   * @return true, if ih is target of this instruction
   */
  @Override
  public boolean containsTarget(InstructionHandle ih) {
    if(target == ih)
      return true;

    for(int i=0; i < targets.length; i++)
      if(targets[i] == ih)
        return true;

    return false;
  }

  /**
   * Inform targets that they're not targeted anymore.
   * <p>
   *  通知目标他们不再定位了
   * 
   */
  @Override
  void dispose() {
    super.dispose();

    for(int i=0; i < targets.length; i++)
      targets[i].removeTargeter(this);
  }

  /**
  /* <p>
  /* 
   * @return array of match indices
   */
  public int[] getMatchs() { return match; }

  /**
  /* <p>
  /* 
   * @return array of match target offsets
   */
  public int[] getIndices() { return indices; }

  /**
  /* <p>
  /* 
   * @return array of match targets
   */
  public InstructionHandle[] getTargets() { return targets; }
}
