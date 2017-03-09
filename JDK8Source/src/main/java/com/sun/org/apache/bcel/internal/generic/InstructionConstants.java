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
 */

import com.sun.org.apache.bcel.internal.Constants;

/**
 * This interface contains shareable instruction objects.
 *
 * In order to save memory you can use some instructions multiply,
 * since they have an immutable state and are directly derived from
 * Instruction.  I.e. they have no instance fields that could be
 * changed. Since some of these instructions like ICONST_0 occur
 * very frequently this can save a lot of time and space. This
 * feature is an adaptation of the FlyWeight design pattern, we
 * just use an array instead of a factory.
 *
 * The Instructions can also accessed directly under their names, so
 * it's possible to write il.append(Instruction.ICONST_0);
 *
 * <p>
 *  ================================================== ==================。
 * 
 *  该软件包括许多个人代表Apache软件基金会所做的自愿捐款。有关Apache Software Foundation的更多信息,请参阅<http://www.apache.org/>。
 * 
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public interface InstructionConstants {
  /** Predefined instruction objects
  /* <p>
  /*  此接口包含可共享指令对象。
  /* 
  /* 为了节省内存,您可以使用一些指令乘法,因为它们具有不可变状态,并直接从指令派生。也就是说它们没有可以更改的实例字段。由于像ICONST_0这样的一些指令发生得很频繁,这可以节省大量的时间和空间。
  /* 这个功能是对FlyWeight设计模式的改编,我们只是使用数组而不是工厂。
  /* 
  /*  指令也可以直接在它们的名字下访问,所以可以写入il.append(Instruction.ICONST_0);
  /* 
   */
  public static final Instruction           NOP          = new NOP();
  public static final Instruction           ACONST_NULL  = new ACONST_NULL();
  public static final Instruction           ICONST_M1    = new ICONST(-1);
  public static final Instruction           ICONST_0     = new ICONST(0);
  public static final Instruction           ICONST_1     = new ICONST(1);
  public static final Instruction           ICONST_2     = new ICONST(2);
  public static final Instruction           ICONST_3     = new ICONST(3);
  public static final Instruction           ICONST_4     = new ICONST(4);
  public static final Instruction           ICONST_5     = new ICONST(5);
  public static final Instruction           LCONST_0     = new LCONST(0);
  public static final Instruction           LCONST_1     = new LCONST(1);
  public static final Instruction           FCONST_0     = new FCONST(0);
  public static final Instruction           FCONST_1     = new FCONST(1);
  public static final Instruction           FCONST_2     = new FCONST(2);
  public static final Instruction           DCONST_0     = new DCONST(0);
  public static final Instruction           DCONST_1     = new DCONST(1);
  public static final ArrayInstruction      IALOAD       = new IALOAD();
  public static final ArrayInstruction      LALOAD       = new LALOAD();
  public static final ArrayInstruction      FALOAD       = new FALOAD();
  public static final ArrayInstruction      DALOAD       = new DALOAD();
  public static final ArrayInstruction      AALOAD       = new AALOAD();
  public static final ArrayInstruction      BALOAD       = new BALOAD();
  public static final ArrayInstruction      CALOAD       = new CALOAD();
  public static final ArrayInstruction      SALOAD       = new SALOAD();
  public static final ArrayInstruction      IASTORE      = new IASTORE();
  public static final ArrayInstruction      LASTORE      = new LASTORE();
  public static final ArrayInstruction      FASTORE      = new FASTORE();
  public static final ArrayInstruction      DASTORE      = new DASTORE();
  public static final ArrayInstruction      AASTORE      = new AASTORE();
  public static final ArrayInstruction      BASTORE      = new BASTORE();
  public static final ArrayInstruction      CASTORE      = new CASTORE();
  public static final ArrayInstruction      SASTORE      = new SASTORE();
  public static final StackInstruction      POP          = new POP();
  public static final StackInstruction      POP2         = new POP2();
  public static final StackInstruction      DUP          = new DUP();
  public static final StackInstruction      DUP_X1       = new DUP_X1();
  public static final StackInstruction      DUP_X2       = new DUP_X2();
  public static final StackInstruction      DUP2         = new DUP2();
  public static final StackInstruction      DUP2_X1      = new DUP2_X1();
  public static final StackInstruction      DUP2_X2      = new DUP2_X2();
  public static final StackInstruction      SWAP         = new SWAP();
  public static final ArithmeticInstruction IADD         = new IADD();
  public static final ArithmeticInstruction LADD         = new LADD();
  public static final ArithmeticInstruction FADD         = new FADD();
  public static final ArithmeticInstruction DADD         = new DADD();
  public static final ArithmeticInstruction ISUB         = new ISUB();
  public static final ArithmeticInstruction LSUB         = new LSUB();
  public static final ArithmeticInstruction FSUB         = new FSUB();
  public static final ArithmeticInstruction DSUB         = new DSUB();
  public static final ArithmeticInstruction IMUL         = new IMUL();
  public static final ArithmeticInstruction LMUL         = new LMUL();
  public static final ArithmeticInstruction FMUL         = new FMUL();
  public static final ArithmeticInstruction DMUL         = new DMUL();
  public static final ArithmeticInstruction IDIV         = new IDIV();
  public static final ArithmeticInstruction LDIV         = new LDIV();
  public static final ArithmeticInstruction FDIV         = new FDIV();
  public static final ArithmeticInstruction DDIV         = new DDIV();
  public static final ArithmeticInstruction IREM         = new IREM();
  public static final ArithmeticInstruction LREM         = new LREM();
  public static final ArithmeticInstruction FREM         = new FREM();
  public static final ArithmeticInstruction DREM         = new DREM();
  public static final ArithmeticInstruction INEG         = new INEG();
  public static final ArithmeticInstruction LNEG         = new LNEG();
  public static final ArithmeticInstruction FNEG         = new FNEG();
  public static final ArithmeticInstruction DNEG         = new DNEG();
  public static final ArithmeticInstruction ISHL         = new ISHL();
  public static final ArithmeticInstruction LSHL         = new LSHL();
  public static final ArithmeticInstruction ISHR         = new ISHR();
  public static final ArithmeticInstruction LSHR         = new LSHR();
  public static final ArithmeticInstruction IUSHR        = new IUSHR();
  public static final ArithmeticInstruction LUSHR        = new LUSHR();
  public static final ArithmeticInstruction IAND         = new IAND();
  public static final ArithmeticInstruction LAND         = new LAND();
  public static final ArithmeticInstruction IOR          = new IOR();
  public static final ArithmeticInstruction LOR          = new LOR();
  public static final ArithmeticInstruction IXOR         = new IXOR();
  public static final ArithmeticInstruction LXOR         = new LXOR();
  public static final ConversionInstruction I2L          = new I2L();
  public static final ConversionInstruction I2F          = new I2F();
  public static final ConversionInstruction I2D          = new I2D();
  public static final ConversionInstruction L2I          = new L2I();
  public static final ConversionInstruction L2F          = new L2F();
  public static final ConversionInstruction L2D          = new L2D();
  public static final ConversionInstruction F2I          = new F2I();
  public static final ConversionInstruction F2L          = new F2L();
  public static final ConversionInstruction F2D          = new F2D();
  public static final ConversionInstruction D2I          = new D2I();
  public static final ConversionInstruction D2L          = new D2L();
  public static final ConversionInstruction D2F          = new D2F();
  public static final ConversionInstruction I2B          = new I2B();
  public static final ConversionInstruction I2C          = new I2C();
  public static final ConversionInstruction I2S          = new I2S();
  public static final Instruction           LCMP         = new LCMP();
  public static final Instruction           FCMPL        = new FCMPL();
  public static final Instruction           FCMPG        = new FCMPG();
  public static final Instruction           DCMPL        = new DCMPL();
  public static final Instruction           DCMPG        = new DCMPG();
  public static final ReturnInstruction     IRETURN      = new IRETURN();
  public static final ReturnInstruction     LRETURN      = new LRETURN();
  public static final ReturnInstruction     FRETURN      = new FRETURN();
  public static final ReturnInstruction     DRETURN      = new DRETURN();
  public static final ReturnInstruction     ARETURN      = new ARETURN();
  public static final ReturnInstruction     RETURN       = new RETURN();
  public static final Instruction           ARRAYLENGTH  = new ARRAYLENGTH();
  public static final Instruction           ATHROW       = new ATHROW();
  public static final Instruction           MONITORENTER = new MONITORENTER();
  public static final Instruction           MONITOREXIT  = new MONITOREXIT();

  /** You can use these constants in multiple places safely, if you can guarantee
   * that you will never alter their internal values, e.g. call setIndex().
   * <p>
   */
  public static final LocalVariableInstruction THIS    = new ALOAD(0);
  public static final LocalVariableInstruction ALOAD_0 = THIS;
  public static final LocalVariableInstruction ALOAD_1 = new ALOAD(1);
  public static final LocalVariableInstruction ALOAD_2 = new ALOAD(2);
  public static final LocalVariableInstruction ILOAD_0 = new ILOAD(0);
  public static final LocalVariableInstruction ILOAD_1 = new ILOAD(1);
  public static final LocalVariableInstruction ILOAD_2 = new ILOAD(2);
  public static final LocalVariableInstruction ASTORE_0 = new ASTORE(0);
  public static final LocalVariableInstruction ASTORE_1 = new ASTORE(1);
  public static final LocalVariableInstruction ASTORE_2 = new ASTORE(2);
  public static final LocalVariableInstruction ISTORE_0 = new ISTORE(0);
  public static final LocalVariableInstruction ISTORE_1 = new ISTORE(1);
  public static final LocalVariableInstruction ISTORE_2 = new ISTORE(2);


  /** Get object via its opcode, for immutable instructions like
   * branch instructions entries are set to null.
   * <p>
   *  你永远不会改变他们的内部价值观,例如。调用setIndex()。
   * 
   */
  public static final Instruction[] INSTRUCTIONS = new Instruction[256];

  /** Interfaces may have no static initializers, so we simulate this
   * with an inner class.
   * <p>
   *  分支指令条目设置为空。
   * 
   */
  static final Clinit bla = new Clinit();

  static class Clinit {
    Clinit() {
      INSTRUCTIONS[Constants.NOP] = NOP;
      INSTRUCTIONS[Constants.ACONST_NULL] = ACONST_NULL;
      INSTRUCTIONS[Constants.ICONST_M1] = ICONST_M1;
      INSTRUCTIONS[Constants.ICONST_0] = ICONST_0;
      INSTRUCTIONS[Constants.ICONST_1] = ICONST_1;
      INSTRUCTIONS[Constants.ICONST_2] = ICONST_2;
      INSTRUCTIONS[Constants.ICONST_3] = ICONST_3;
      INSTRUCTIONS[Constants.ICONST_4] = ICONST_4;
      INSTRUCTIONS[Constants.ICONST_5] = ICONST_5;
      INSTRUCTIONS[Constants.LCONST_0] = LCONST_0;
      INSTRUCTIONS[Constants.LCONST_1] = LCONST_1;
      INSTRUCTIONS[Constants.FCONST_0] = FCONST_0;
      INSTRUCTIONS[Constants.FCONST_1] = FCONST_1;
      INSTRUCTIONS[Constants.FCONST_2] = FCONST_2;
      INSTRUCTIONS[Constants.DCONST_0] = DCONST_0;
      INSTRUCTIONS[Constants.DCONST_1] = DCONST_1;
      INSTRUCTIONS[Constants.IALOAD] = IALOAD;
      INSTRUCTIONS[Constants.LALOAD] = LALOAD;
      INSTRUCTIONS[Constants.FALOAD] = FALOAD;
      INSTRUCTIONS[Constants.DALOAD] = DALOAD;
      INSTRUCTIONS[Constants.AALOAD] = AALOAD;
      INSTRUCTIONS[Constants.BALOAD] = BALOAD;
      INSTRUCTIONS[Constants.CALOAD] = CALOAD;
      INSTRUCTIONS[Constants.SALOAD] = SALOAD;
      INSTRUCTIONS[Constants.IASTORE] = IASTORE;
      INSTRUCTIONS[Constants.LASTORE] = LASTORE;
      INSTRUCTIONS[Constants.FASTORE] = FASTORE;
      INSTRUCTIONS[Constants.DASTORE] = DASTORE;
      INSTRUCTIONS[Constants.AASTORE] = AASTORE;
      INSTRUCTIONS[Constants.BASTORE] = BASTORE;
      INSTRUCTIONS[Constants.CASTORE] = CASTORE;
      INSTRUCTIONS[Constants.SASTORE] = SASTORE;
      INSTRUCTIONS[Constants.POP] = POP;
      INSTRUCTIONS[Constants.POP2] = POP2;
      INSTRUCTIONS[Constants.DUP] = DUP;
      INSTRUCTIONS[Constants.DUP_X1] = DUP_X1;
      INSTRUCTIONS[Constants.DUP_X2] = DUP_X2;
      INSTRUCTIONS[Constants.DUP2] = DUP2;
      INSTRUCTIONS[Constants.DUP2_X1] = DUP2_X1;
      INSTRUCTIONS[Constants.DUP2_X2] = DUP2_X2;
      INSTRUCTIONS[Constants.SWAP] = SWAP;
      INSTRUCTIONS[Constants.IADD] = IADD;
      INSTRUCTIONS[Constants.LADD] = LADD;
      INSTRUCTIONS[Constants.FADD] = FADD;
      INSTRUCTIONS[Constants.DADD] = DADD;
      INSTRUCTIONS[Constants.ISUB] = ISUB;
      INSTRUCTIONS[Constants.LSUB] = LSUB;
      INSTRUCTIONS[Constants.FSUB] = FSUB;
      INSTRUCTIONS[Constants.DSUB] = DSUB;
      INSTRUCTIONS[Constants.IMUL] = IMUL;
      INSTRUCTIONS[Constants.LMUL] = LMUL;
      INSTRUCTIONS[Constants.FMUL] = FMUL;
      INSTRUCTIONS[Constants.DMUL] = DMUL;
      INSTRUCTIONS[Constants.IDIV] = IDIV;
      INSTRUCTIONS[Constants.LDIV] = LDIV;
      INSTRUCTIONS[Constants.FDIV] = FDIV;
      INSTRUCTIONS[Constants.DDIV] = DDIV;
      INSTRUCTIONS[Constants.IREM] = IREM;
      INSTRUCTIONS[Constants.LREM] = LREM;
      INSTRUCTIONS[Constants.FREM] = FREM;
      INSTRUCTIONS[Constants.DREM] = DREM;
      INSTRUCTIONS[Constants.INEG] = INEG;
      INSTRUCTIONS[Constants.LNEG] = LNEG;
      INSTRUCTIONS[Constants.FNEG] = FNEG;
      INSTRUCTIONS[Constants.DNEG] = DNEG;
      INSTRUCTIONS[Constants.ISHL] = ISHL;
      INSTRUCTIONS[Constants.LSHL] = LSHL;
      INSTRUCTIONS[Constants.ISHR] = ISHR;
      INSTRUCTIONS[Constants.LSHR] = LSHR;
      INSTRUCTIONS[Constants.IUSHR] = IUSHR;
      INSTRUCTIONS[Constants.LUSHR] = LUSHR;
      INSTRUCTIONS[Constants.IAND] = IAND;
      INSTRUCTIONS[Constants.LAND] = LAND;
      INSTRUCTIONS[Constants.IOR] = IOR;
      INSTRUCTIONS[Constants.LOR] = LOR;
      INSTRUCTIONS[Constants.IXOR] = IXOR;
      INSTRUCTIONS[Constants.LXOR] = LXOR;
      INSTRUCTIONS[Constants.I2L] = I2L;
      INSTRUCTIONS[Constants.I2F] = I2F;
      INSTRUCTIONS[Constants.I2D] = I2D;
      INSTRUCTIONS[Constants.L2I] = L2I;
      INSTRUCTIONS[Constants.L2F] = L2F;
      INSTRUCTIONS[Constants.L2D] = L2D;
      INSTRUCTIONS[Constants.F2I] = F2I;
      INSTRUCTIONS[Constants.F2L] = F2L;
      INSTRUCTIONS[Constants.F2D] = F2D;
      INSTRUCTIONS[Constants.D2I] = D2I;
      INSTRUCTIONS[Constants.D2L] = D2L;
      INSTRUCTIONS[Constants.D2F] = D2F;
      INSTRUCTIONS[Constants.I2B] = I2B;
      INSTRUCTIONS[Constants.I2C] = I2C;
      INSTRUCTIONS[Constants.I2S] = I2S;
      INSTRUCTIONS[Constants.LCMP] = LCMP;
      INSTRUCTIONS[Constants.FCMPL] = FCMPL;
      INSTRUCTIONS[Constants.FCMPG] = FCMPG;
      INSTRUCTIONS[Constants.DCMPL] = DCMPL;
      INSTRUCTIONS[Constants.DCMPG] = DCMPG;
      INSTRUCTIONS[Constants.IRETURN] = IRETURN;
      INSTRUCTIONS[Constants.LRETURN] = LRETURN;
      INSTRUCTIONS[Constants.FRETURN] = FRETURN;
      INSTRUCTIONS[Constants.DRETURN] = DRETURN;
      INSTRUCTIONS[Constants.ARETURN] = ARETURN;
      INSTRUCTIONS[Constants.RETURN] = RETURN;
      INSTRUCTIONS[Constants.ARRAYLENGTH] = ARRAYLENGTH;
      INSTRUCTIONS[Constants.ATHROW] = ATHROW;
      INSTRUCTIONS[Constants.MONITORENTER] = MONITORENTER;
      INSTRUCTIONS[Constants.MONITOREXIT] = MONITOREXIT;
    }
  }
}
