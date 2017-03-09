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
 *  本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 在任何情况下,APACHE软件基金会或其捐赠者均不对任何直接,间接,偶发,特殊,惩罚性或后果性损害(包括但不限于替代商品或服务的采购,使用,数据丢失或利润或业务中断),无论是由于任何责任推理原因,无论是
 * 在合同,严格责任或侵权(包括疏忽或其他方式)中,以任何方式使用本软件,即使已被告知此类软件的可能性损伤。
 *  本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 *  ================================================== ==================。
 * 
 *  该软件包括许多个人代表Apache软件基金会所做的自愿捐款。有关Apache Software Foundation的更多信息,请参阅<http://www.apache.org/>。
 * 
 */

import java.io.*;
import com.sun.org.apache.bcel.internal.util.ByteSequence;

/**
 * Abstract super class for branching instructions like GOTO, IFEQ, etc..
 * Branch instructions may have a variable length, namely GOTO, JSR,
 * LOOKUPSWITCH and TABLESWITCH.
 *
 * <p>
 *  用于分支指令的抽象超类,如GOTO,IFEQ等。分支指令可以具有可变长度,即GOTO,JSR,LOOKUPSWITCH和TABLESWITCH。
 * 
 * 
 * @see InstructionList
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class BranchInstruction extends Instruction implements InstructionTargeter {
  protected int               index;    // Branch target relative to this instruction
  protected InstructionHandle target;   // Target object in instruction list
  protected int               position; // Byte code offset

  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   * <p>
   *  Instruction.readInstruction()中的Class.newInstance()语句所需的空构造函数。不要以其他方式使用。
   * 
   */
  BranchInstruction() {}

  /** Common super constructor
  /* <p>
  /* 
   * @param opcodee Instruction opcode
   * @param target instruction to branch to
   */
  protected BranchInstruction(short opcode, InstructionHandle target) {
    super(opcode, (short)3);
    setTarget(target);
  }

  /**
   * Dump instruction as byte code to stream out.
   * <p>
   * 转储指令作为字节码流输出。
   * 
   * 
   * @param out Output stream
   */
  @Override
  public void dump(DataOutputStream out) throws IOException {
    out.writeByte(opcode);

    index = getTargetOffset();

    if(Math.abs(index) >= 32767) // too large for short
      throw new ClassGenException("Branch target offset too large for short");

    out.writeShort(index); // May be negative, i.e., point backwards
  }

  /**
  /* <p>
  /* 
   * @param target branch target
   * @return the offset to  `target' relative to this instruction
   */
  protected int getTargetOffset(InstructionHandle target) {
    if(target == null)
      throw new ClassGenException("Target of " + super.toString(true) +
                                  " is invalid null handle");

    int t = target.getPosition();

    if(t < 0)
      throw new ClassGenException("Invalid branch target position offset for " +
                                  super.toString(true) + ":" + t + ":" + target);

    return t - position;
  }

  /**
  /* <p>
  /* 
   * @return the offset to this instruction's target
   */
  protected int getTargetOffset() { return getTargetOffset(target); }

  /**
   * Called by InstructionList.setPositions when setting the position for every
   * instruction. In the presence of variable length instructions `setPositions'
   * performs multiple passes over the instruction list to calculate the
   * correct (byte) positions and offsets by calling this function.
   *
   * <p>
   *  在为每个指令设置位置时由InstructionList.setPositions调用。
   * 在存在可变长度指令的情况下,`setPositions'在指令表上执行多次遍历,通过调用此函数来计算正确的(字节)位置和偏移量。
   * 
   * 
   * @param offset additional offset caused by preceding (variable length) instructions
   * @param max_offset the maximum offset that may be caused by these instructions
   * @return additional offset caused by possible change of this instruction's length
   */
  protected int updatePosition(int offset, int max_offset) {
    position += offset;
    return 0;
  }

  /**
   * Long output format:
   *
   * &lt;position in byte code&gt;
   * &lt;name of opcode&gt; "["&lt;opcode number&gt;"]"
   * "("&lt;length of instruction&gt;")"
   * "&lt;"&lt;target instruction&gt;"&gt;" "@"&lt;branch target offset&gt;
   *
   * <p>
   *  长输出格式：
   * 
   *  &lt;字节代码中的位置&gt; &lt;操作码的名称&gt; "["&lt; opcode number&gt;"]""("&lt;指令长度&gt;")""&lt;"&lt; target inst
   * ruction&gt; "@"&lt; branch target offset&gt;。
   * 
   * 
   * @param verbose long/short format switch
   * @return mnemonic for instruction
   */
  @Override
  public String toString(boolean verbose) {
    String s = super.toString(verbose);
    String t = "null";

    if(verbose) {
      if(target != null) {
        if(target.getInstruction() == this)
          t = "<points to itself>";
        else if(target.getInstruction() == null)
          t = "<null instruction!!!?>";
        else
          t = target.getInstruction().toString(false); // Avoid circles
      }
    } else {
      if(target != null) {
        index = getTargetOffset();
        t = "" + (index + position);
      }
    }

    return s + " -> " + t;
  }

  /**
   * Read needed data (e.g. index) from file. Conversion to a InstructionHandle
   * is done in InstructionList(byte[]).
   *
   * <p>
   *  从文件中读取所需的数据(例如索引)。转换为InstructionHandle在InstructionList(byte [])中完成。
   * 
   * 
   * @param bytes input stream
   * @param wide wide prefix?
   * @see InstructionList
   */
  @Override
  protected void initFromFile(ByteSequence bytes, boolean wide) throws IOException
  {
    length = 3;
    index  = bytes.readShort();
  }

  /**
  /* <p>
  /* 
   * @return target offset in byte code
   */
  public final int getIndex() { return index; }

  /**
  /* <p>
  /* 
   * @return target of branch instruction
   */
  public InstructionHandle getTarget() { return target; }

  /**
   * Set branch target
   * <p>
   *  设置分支目标
   * 
   * 
   * @param target branch target
   */
  public final void setTarget(InstructionHandle target) {
    notifyTargetChanging(this.target, this);
    this.target = target;
    notifyTargetChanged(this.target, this);
  }

  /**
   * Used by BranchInstruction, LocalVariableGen, CodeExceptionGen.
   * Must be called before the target is actually changed in the
   * InstructionTargeter.
   * <p>
   *  用于BranchInstruction,LocalVariableGen,CodeExceptionGen。必须在目标在InstructionTargeter中实际更改之前调用。
   * 
   */
  static void notifyTargetChanging(InstructionHandle old_ih,
                                 InstructionTargeter t) {
    if(old_ih != null) {
      old_ih.removeTargeter(t);
    }
  }

  /**
   * Used by BranchInstruction, LocalVariableGen, CodeExceptionGen.
   * Must be called after the target is actually changed in the
   * InstructionTargeter.
   * <p>
   *  用于BranchInstruction,LocalVariableGen,CodeExceptionGen。必须在目标在InstructionTargeter中实际更改后调用。
   * 
   */
  static void notifyTargetChanged(InstructionHandle new_ih,
                                 InstructionTargeter t) {
    if(new_ih != null) {
      new_ih.addTargeter(t);
    }
  }

  /**
  /* <p>
  /* 
   * @param old_ih old target
   * @param new_ih new target
   */
  @Override
  public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih) {
    if(target == old_ih)
      setTarget(new_ih);
    else
      throw new ClassGenException("Not targeting " + old_ih + ", but " + target);
  }

  /**
  /* <p>
  /* 
   * @return true, if ih is target of this instruction
   */
  @Override
  public boolean containsTarget(InstructionHandle ih) {
    return (target == ih);
  }

  /**
   * Inform target that it's not targeted anymore.
   * <p>
   *  通知目标,它不再有针对性。
   */
  @Override
  void dispose() {
    setTarget(null);
    index=-1;
    position=-1;
  }
}
