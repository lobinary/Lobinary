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

import com.sun.org.apache.bcel.internal.Constants;
import com.sun.org.apache.bcel.internal.classfile.Utility;
import com.sun.org.apache.bcel.internal.classfile.ConstantPool;
import java.io.*;
import com.sun.org.apache.bcel.internal.util.ByteSequence;

/**
 * Abstract super class for all Java byte codes.
 *
 * <p>
 *  所有Java字节代码的抽象超类。
 * 
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class Instruction implements Cloneable, Serializable {
  protected short length = 1;  // Length of instruction in bytes
  protected short opcode = -1; // Opcode number

  private static InstructionComparator cmp = InstructionComparator.DEFAULT;

  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   * <p>
   *  Instruction.readInstruction()中的Class.newInstance()语句所需的空构造函数。不要以其他方式使用。
   * 
   */
  Instruction() {}

  public Instruction(short opcode, short length) {
    this.length = length;
    this.opcode = opcode;
  }

  /**
   * Dump instruction as byte code to stream out.
   * <p>
   *  转储指令作为字节码流输出。
   * 
   * 
   * @param out Output stream
   */
  public void dump(DataOutputStream out) throws IOException {
    out.writeByte(opcode); // Common for all instructions
  }

  /** @return name of instruction, i.e., opcode name
  /* <p>
   */
  public String getName() {
    return Constants.OPCODE_NAMES[opcode];
  }

  /**
   * Long output format:
   *
   * &lt;name of opcode&gt; "["&lt;opcode number&gt;"]"
   * "("&lt;length of instruction&gt;")"
   *
   * <p>
   *  长输出格式：
   * 
   * &lt;操作码的名称&gt; "["&lt; opcode number&gt;"]""("&lt;指令长度&gt;")"
   * 
   * 
   * @param verbose long/short format switch
   * @return mnemonic for instruction
   */
  public String toString(boolean verbose) {
    if(verbose)
      return getName() + "[" + opcode + "](" + length + ")";
    else
      return getName();
  }

  /**
  /* <p>
  /* 
   * @return mnemonic for instruction in verbose format
   */
  public String toString() {
    return toString(true);
  }

  /**
  /* <p>
  /* 
   * @return mnemonic for instruction with sumbolic references resolved
   */
  public String toString(ConstantPool cp) {
    return toString(false);
  }

  /**
   * Use with caution, since `BranchInstruction's have a `target' reference which
   * is not copied correctly (only basic types are). This also applies for
   * `Select' instructions with their multiple branch targets.
   *
   * <p>
   *  使用时要小心,因为`BranchInstruction的'target'引用没有被正确复制(只有基本类型)。这也适用于具有多个分支目标的"选择"指令。
   * 
   * 
   * @see BranchInstruction
   * @return (shallow) copy of an instruction
   */
  public Instruction copy() {
    Instruction i = null;

    // "Constant" instruction, no need to duplicate
    if(InstructionConstants.INSTRUCTIONS[this.getOpcode()] != null)
      i = this;
    else {
      try {
        i = (Instruction)clone();
      } catch(CloneNotSupportedException e) {
        System.err.println(e);
      }
    }

    return i;
  }

  /**
   * Read needed data (e.g. index) from file.
   *
   * <p>
   *  从文件中读取所需的数据(例如索引)。
   * 
   * 
   * @param bytes byte sequence to read from
   * @param wide "wide" instruction flag
   */
  protected void initFromFile(ByteSequence bytes, boolean wide)
    throws IOException
  {}

  /**
   * Read an instruction from (byte code) input stream and return the
   * appropiate object.
   *
   * <p>
   *  从(字节码)输入流读取指令并返回适当的对象。
   * 
   * 
   * @param file file to read from
   * @return instruction object being read
   */
  public static final Instruction readInstruction(ByteSequence bytes)
    throws IOException
  {
    boolean     wide   = false;
    short       opcode = (short)bytes.readUnsignedByte();
    Instruction obj    = null;

    if(opcode == Constants.WIDE) { // Read next opcode after wide byte
      wide = true;
      opcode  = (short)bytes.readUnsignedByte();
    }

    if(InstructionConstants.INSTRUCTIONS[opcode] != null)
      return InstructionConstants.INSTRUCTIONS[opcode]; // Used predefined immutable object, if available

    /* Find appropiate class, instantiate an (empty) instruction object
     * and initialize it by hand.
     * <p>
     *  并手动初始化。
     * 
     */
    Class clazz;

    try {
      clazz = Class.forName(className(opcode));
    } catch (ClassNotFoundException cnfe){
      // If a class by that name does not exist, the opcode is illegal.
      // Note that IMPDEP1, IMPDEP2, BREAKPOINT are also illegal in a sense.
      throw new ClassGenException("Illegal opcode detected.");
    }

    try {
      obj = (Instruction)clazz.newInstance();

      if(wide && !((obj instanceof LocalVariableInstruction) ||
                   (obj instanceof IINC) ||
                   (obj instanceof RET)))
        throw new Exception("Illegal opcode after wide: " + opcode);

      obj.setOpcode(opcode);
      obj.initFromFile(bytes, wide); // Do further initializations, if any
      // Byte code offset set in InstructionList
    } catch(Exception e) { throw new ClassGenException(e.toString()); }

    return obj;
  }

  private static final String className(short opcode) {
    String name = Constants.OPCODE_NAMES[opcode].toUpperCase();

    /* ICONST_0, etc. will be shortened to ICONST, etc., since ICONST_0 and the like
     * are not implemented (directly).
     * <p>
     *  (直接)。
     * 
     */
    try {
      int  len = name.length();
      char ch1 = name.charAt(len - 2), ch2 = name.charAt(len - 1);

      if((ch1 == '_') && (ch2 >= '0')  && (ch2 <= '5'))
        name = name.substring(0, len - 2);

      if(name.equals("ICONST_M1")) // Special case
        name = "ICONST";
    } catch(StringIndexOutOfBoundsException e) { System.err.println(e); }

    return "com.sun.org.apache.bcel.internal.generic." + name;
  }

  /**
   * This method also gives right results for instructions whose
   * effect on the stack depends on the constant pool entry they
   * reference.
   * <p>
   *  这种方法也给出了对栈的影响取决于它们引用的常量池条目的指令的正确结果。
   * 
   * 
   *  @return Number of words consumed from stack by this instruction,
   * or Constants.UNPREDICTABLE, if this can not be computed statically
   */
  public int consumeStack(ConstantPoolGen cpg) {
    return Constants.CONSUME_STACK[opcode];
  }

  /**
   * This method also gives right results for instructions whose
   * effect on the stack depends on the constant pool entry they
   * reference.
   * <p>
   *  这种方法也给出了对栈的影响取决于它们引用的常量池条目的指令的正确结果。
   * 
   * 
   * @return Number of words produced onto stack by this instruction,
   * or Constants.UNPREDICTABLE, if this can not be computed statically
   */
  public int produceStack(ConstantPoolGen cpg) {
    return Constants.PRODUCE_STACK[opcode];
  }

  /**
  /* <p>
  /* 
   * @return this instructions opcode
   */
  public short getOpcode()    { return opcode; }

  /**
  /* <p>
  /* 
   * @return length (in bytes) of instruction
   */
  public int getLength()   { return length; }

  /**
   * Needed in readInstruction.
   * <p>
   *  需要在readInstruction。
   * 
   */
  private void setOpcode(short opcode) { this.opcode = opcode; }

  /** Some instructions may be reused, so don't do anything by default.
  /* <p>
   */
  void dispose() {}

  /**
   * Call corresponding visitor method(s). The order is:
   * Call visitor methods of implemented interfaces first, then
   * call methods according to the class hierarchy in descending order,
   * i.e., the most specific visitXXX() call comes last.
   *
   * <p>
   *  调用相应的访问者方法。顺序是：首先调用已实现接口的访问者方法,然后根据类层次结构以降序调用方法,即最具体的visitXXX()调用最后。
   * 
   * 
   * @param v Visitor object
   */
  public abstract void accept(Visitor v);

  /** Get Comparator object used in the equals() method to determine
   * equality of instructions.
   *
   * <p>
   *  指令平等。
   * 
   * 
   * @return currently used comparator for equals()
   */
  public static InstructionComparator getComparator() { return cmp; }

  /** Set comparator to be used for equals().
  /* <p>
   */
  public static void setComparator(InstructionComparator c) { cmp = c; }

  /** Check for equality, delegated to comparator
  /* <p>
  /* 
   * @return true if that is an Instruction and has the same opcode
   */
  public boolean equals(Object that) {
    return (that instanceof Instruction)?
      cmp.equals(this, (Instruction)that) : false;
  }
}
