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
import com.sun.org.apache.bcel.internal.classfile.Utility;
import com.sun.org.apache.bcel.internal.Constants;

/**
 * Abstract super class for instructions dealing with local variables.
 *
 * <p>
 *  抽象超类用于处理局部变量的指令。
 * 
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class LocalVariableInstruction extends Instruction
  implements TypedInstruction, IndexedInstruction {
  protected int     n         = -1; // index of referenced variable
  private short     c_tag     = -1; // compact version, such as ILOAD_0
  private short     canon_tag = -1; // canonical tag such as ILOAD

  private final boolean wide() { return n > Constants.MAX_BYTE; }

  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Not to be used otherwise.
   * tag and length are defined in readInstruction and initFromFile, respectively.
   * <p>
   *  Instruction.readInstruction()中的Class.newInstance()语句所需的空构造函数。不要以其他方式使用。
   * 标签和长度分别在readInstruction和initFromFile中定义。
   * 
   */
  LocalVariableInstruction(short canon_tag, short c_tag) {
    super();
    this.canon_tag = canon_tag;
    this.c_tag     = c_tag;
  }

  /**
   * Empty constructor needed for the Class.newInstance() statement in
   * Instruction.readInstruction(). Also used by IINC()!
   * <p>
   * Instruction.readInstruction()中的Class.newInstance()语句所需的空构造函数。也由IINC()使用！
   * 
   */
  LocalVariableInstruction() {
  }

  /**
  /* <p>
  /* 
   * @param opcode Instruction opcode
   * @param c_tag Instruction number for compact version, ALOAD_0, e.g.
   * @param n local variable index (unsigned short)
   */
  protected LocalVariableInstruction(short opcode, short c_tag, int n) {
    super(opcode, (short)2);

    this.c_tag = c_tag;
    canon_tag  = opcode;

    setIndex(n);
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
    if(wide()) // Need WIDE prefix ?
      out.writeByte(Constants.WIDE);

    out.writeByte(opcode);

    if(length > 1) { // Otherwise ILOAD_n, instruction, e.g.
      if(wide())
        out.writeShort(n);
      else
        out.writeByte(n);
    }
  }

  /**
   * Long output format:
   *
   * &lt;name of opcode&gt; "["&lt;opcode number&gt;"]"
   * "("&lt;length of instruction&gt;")" "&lt;"&lt; local variable index&gt;"&gt;"
   *
   * <p>
   *  长输出格式：
   * 
   *  &lt;操作码的名称&gt; "["&lt; opcode number&gt;"]""("&lt;指令长度&gt;")""&lt;"&lt;局部变量索引&gt;"&gt;"
   * 
   * 
   * @param verbose long/short format switch
   * @return mnemonic for instruction
   */
  public String toString(boolean verbose) {
    if(((opcode >= Constants.ILOAD_0) &&
        (opcode <= Constants.ALOAD_3)) ||
       ((opcode >= Constants.ISTORE_0) &&
        (opcode <= Constants.ASTORE_3)))
      return super.toString(verbose);
    else
      return super.toString(verbose) + " " + n;
  }

  /**
   * Read needed data (e.g. index) from file.
   * PRE: (ILOAD <= tag <= ALOAD_3) || (ISTORE <= tag <= ASTORE_3)
   * <p>
   *  从文件中读取所需的数据(例如索引)。 PRE：(ILOAD <= tag <= ALOAD_3)|| (ISTORE <= tag <= ASTORE_3)
   * 
   */
  protected void initFromFile(ByteSequence bytes, boolean wide)
    throws IOException
  {
    if(wide) {
      n         = bytes.readUnsignedShort();
      length    = 4;
    } else if(((opcode >= Constants.ILOAD) &&
               (opcode <= Constants.ALOAD)) ||
              ((opcode >= Constants.ISTORE) &&
               (opcode <= Constants.ASTORE))) {
      n      = bytes.readUnsignedByte();
      length = 2;
    } else if(opcode <= Constants.ALOAD_3) { // compact load instruction such as ILOAD_2
      n      = (opcode - Constants.ILOAD_0) % 4;
      length = 1;
    } else { // Assert ISTORE_0 <= tag <= ASTORE_3
      n      = (opcode - Constants.ISTORE_0) % 4;
      length = 1;
    }
 }

  /**
  /* <p>
  /* 
   * @return local variable index  referred by this instruction.
   */
  public final int getIndex() { return n; }

  /**
   * Set the local variable index
   * <p>
   *  设置局部变量索引
   * 
   */
  public void setIndex(int n) {
    if((n < 0) || (n > Constants.MAX_SHORT))
      throw new ClassGenException("Illegal value: " + n);

    this.n = n;

    if(n >= 0 && n <= 3) { // Use more compact instruction xLOAD_n
      opcode = (short)(c_tag + n);
      length = 1;
    } else {
      opcode = canon_tag;

      if(wide()) // Need WIDE prefix ?
        length = 4;
      else
        length = 2;
    }
  }

  /** @return canonical tag for instruction, e.g., ALOAD for ALOAD_0
  /* <p>
   */
  public short getCanonicalTag() {
    return canon_tag;
  }

  /**
   * Returns the type associated with the instruction -
   * in case of ALOAD or ASTORE Type.OBJECT is returned.
   * This is just a bit incorrect, because ALOAD and ASTORE
   * may work on every ReferenceType (including Type.NULL) and
   * ASTORE may even work on a ReturnaddressType .
   * <p>
   *  返回与指令相关联的类型 - 在返回ALOAD或ASTORE Type.OBJECT的情况下。
   * 这只是有点不正确,因为ALOAD和ASTORE可能工作在每个ReferenceType(包括Type.NULL)和ASTORE甚至可以工作在一个ReturnaddressType。
   * 
   * @return type associated with the instruction
   */
  public Type getType(ConstantPoolGen cp) {
    switch(canon_tag) {
    case Constants.ILOAD: case Constants.ISTORE:
      return Type.INT;
    case Constants.LLOAD: case Constants.LSTORE:
      return Type.LONG;
    case Constants.DLOAD: case Constants.DSTORE:
      return Type.DOUBLE;
    case Constants.FLOAD: case Constants.FSTORE:
      return Type.FLOAT;
    case Constants.ALOAD: case Constants.ASTORE:
      return Type.OBJECT;

    default: throw new ClassGenException("Oops: unknown case in switch" + canon_tag);
    }
  }
}
