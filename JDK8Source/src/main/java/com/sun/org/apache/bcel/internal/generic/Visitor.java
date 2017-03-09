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
 */

/**
 * Interface implementing the Visitor pattern programming style.
 * I.e., a class that implements this interface can handle all types of
 * instructions with the properly typed methods just by calling the accept()
 * method.
 *
 * <p>
 * 
 * 未经Apache软件基金会事先书面许可,从本软件衍生的产品可能不会被称为"Apache","Apache BCEL",也不可能出现在他们的名字中。
 * 
 *  本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 在任何情况下,APACHE软件基金会或其捐赠者均不对任何直接,间接,偶发,特殊,惩罚性或后果性损害(包括但不限于替代商品或服务的采购,使用,数据丢失或利润或业务中断),无论是由于任何责任推理原因,无论是
 * 在合同,严格责任或侵权(包括疏忽或其他方式)中,以任何方式使用本软件,即使已被告知此类软件的可能性损伤。
 *  本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public interface Visitor {
  public void visitStackInstruction(StackInstruction obj);
  public void visitLocalVariableInstruction(LocalVariableInstruction obj);
  public void visitBranchInstruction(BranchInstruction obj);
  public void visitLoadClass(LoadClass obj);
  public void visitFieldInstruction(FieldInstruction obj);
  public void visitIfInstruction(IfInstruction obj);
  public void visitConversionInstruction(ConversionInstruction obj);
  public void visitPopInstruction(PopInstruction obj);
  public void visitStoreInstruction(StoreInstruction obj);
  public void visitTypedInstruction(TypedInstruction obj);
  public void visitSelect(Select obj);
  public void visitJsrInstruction(JsrInstruction obj);
  public void visitGotoInstruction(GotoInstruction obj);
  public void visitUnconditionalBranch(UnconditionalBranch obj);
  public void visitPushInstruction(PushInstruction obj);
  public void visitArithmeticInstruction(ArithmeticInstruction obj);
  public void visitCPInstruction(CPInstruction obj);
  public void visitInvokeInstruction(InvokeInstruction obj);
  public void visitArrayInstruction(ArrayInstruction obj);
  public void visitAllocationInstruction(AllocationInstruction obj);
  public void visitReturnInstruction(ReturnInstruction obj);
  public void visitFieldOrMethod(FieldOrMethod obj);
  public void visitConstantPushInstruction(ConstantPushInstruction obj);
  public void visitExceptionThrower(ExceptionThrower obj);
  public void visitLoadInstruction(LoadInstruction obj);
  public void visitVariableLengthInstruction(VariableLengthInstruction obj);
  public void visitStackProducer(StackProducer obj);
  public void visitStackConsumer(StackConsumer obj);
  public void visitACONST_NULL(ACONST_NULL obj);
  public void visitGETSTATIC(GETSTATIC obj);
  public void visitIF_ICMPLT(IF_ICMPLT obj);
  public void visitMONITOREXIT(MONITOREXIT obj);
  public void visitIFLT(IFLT obj);
  public void visitLSTORE(LSTORE obj);
  public void visitPOP2(POP2 obj);
  public void visitBASTORE(BASTORE obj);
  public void visitISTORE(ISTORE obj);
  public void visitCHECKCAST(CHECKCAST obj);
  public void visitFCMPG(FCMPG obj);
  public void visitI2F(I2F obj);
  public void visitATHROW(ATHROW obj);
  public void visitDCMPL(DCMPL obj);
  public void visitARRAYLENGTH(ARRAYLENGTH obj);
  public void visitDUP(DUP obj);
  public void visitINVOKESTATIC(INVOKESTATIC obj);
  public void visitLCONST(LCONST obj);
  public void visitDREM(DREM obj);
  public void visitIFGE(IFGE obj);
  public void visitCALOAD(CALOAD obj);
  public void visitLASTORE(LASTORE obj);
  public void visitI2D(I2D obj);
  public void visitDADD(DADD obj);
  public void visitINVOKESPECIAL(INVOKESPECIAL obj);
  public void visitIAND(IAND obj);
  public void visitPUTFIELD(PUTFIELD obj);
  public void visitILOAD(ILOAD obj);
  public void visitDLOAD(DLOAD obj);
  public void visitDCONST(DCONST obj);
  public void visitNEW(NEW obj);
  public void visitIFNULL(IFNULL obj);
  public void visitLSUB(LSUB obj);
  public void visitL2I(L2I obj);
  public void visitISHR(ISHR obj);
  public void visitTABLESWITCH(TABLESWITCH obj);
  public void visitIINC(IINC obj);
  public void visitDRETURN(DRETURN obj);
  public void visitFSTORE(FSTORE obj);
  public void visitDASTORE(DASTORE obj);
  public void visitIALOAD(IALOAD obj);
  public void visitDDIV(DDIV obj);
  public void visitIF_ICMPGE(IF_ICMPGE obj);
  public void visitLAND(LAND obj);
  public void visitIDIV(IDIV obj);
  public void visitLOR(LOR obj);
  public void visitCASTORE(CASTORE obj);
  public void visitFREM(FREM obj);
  public void visitLDC(LDC obj);
  public void visitBIPUSH(BIPUSH obj);
  public void visitDSTORE(DSTORE obj);
  public void visitF2L(F2L obj);
  public void visitFMUL(FMUL obj);
  public void visitLLOAD(LLOAD obj);
  public void visitJSR(JSR obj);
  public void visitFSUB(FSUB obj);
  public void visitSASTORE(SASTORE obj);
  public void visitALOAD(ALOAD obj);
  public void visitDUP2_X2(DUP2_X2 obj);
  public void visitRETURN(RETURN obj);
  public void visitDALOAD(DALOAD obj);
  public void visitSIPUSH(SIPUSH obj);
  public void visitDSUB(DSUB obj);
  public void visitL2F(L2F obj);
  public void visitIF_ICMPGT(IF_ICMPGT obj);
  public void visitF2D(F2D obj);
  public void visitI2L(I2L obj);
  public void visitIF_ACMPNE(IF_ACMPNE obj);
  public void visitPOP(POP obj);
  public void visitI2S(I2S obj);
  public void visitIFEQ(IFEQ obj);
  public void visitSWAP(SWAP obj);
  public void visitIOR(IOR obj);
  public void visitIREM(IREM obj);
  public void visitIASTORE(IASTORE obj);
  public void visitNEWARRAY(NEWARRAY obj);
  public void visitINVOKEINTERFACE(INVOKEINTERFACE obj);
  public void visitINEG(INEG obj);
  public void visitLCMP(LCMP obj);
  public void visitJSR_W(JSR_W obj);
  public void visitMULTIANEWARRAY(MULTIANEWARRAY obj);
  public void visitDUP_X2(DUP_X2 obj);
  public void visitSALOAD(SALOAD obj);
  public void visitIFNONNULL(IFNONNULL obj);
  public void visitDMUL(DMUL obj);
  public void visitIFNE(IFNE obj);
  public void visitIF_ICMPLE(IF_ICMPLE obj);
  public void visitLDC2_W(LDC2_W obj);
  public void visitGETFIELD(GETFIELD obj);
  public void visitLADD(LADD obj);
  public void visitNOP(NOP obj);
  public void visitFALOAD(FALOAD obj);
  public void visitINSTANCEOF(INSTANCEOF obj);
  public void visitIFLE(IFLE obj);
  public void visitLXOR(LXOR obj);
  public void visitLRETURN(LRETURN obj);
  public void visitFCONST(FCONST obj);
  public void visitIUSHR(IUSHR obj);
  public void visitBALOAD(BALOAD obj);
  public void visitDUP2(DUP2 obj);
  public void visitIF_ACMPEQ(IF_ACMPEQ obj);
  public void visitIMPDEP1(IMPDEP1 obj);
  public void visitMONITORENTER(MONITORENTER obj);
  public void visitLSHL(LSHL obj);
  public void visitDCMPG(DCMPG obj);
  public void visitD2L(D2L obj);
  public void visitIMPDEP2(IMPDEP2 obj);
  public void visitL2D(L2D obj);
  public void visitRET(RET obj);
  public void visitIFGT(IFGT obj);
  public void visitIXOR(IXOR obj);
  public void visitINVOKEVIRTUAL(INVOKEVIRTUAL obj);
  public void visitFASTORE(FASTORE obj);
  public void visitIRETURN(IRETURN obj);
  public void visitIF_ICMPNE(IF_ICMPNE obj);
  public void visitFLOAD(FLOAD obj);
  public void visitLDIV(LDIV obj);
  public void visitPUTSTATIC(PUTSTATIC obj);
  public void visitAALOAD(AALOAD obj);
  public void visitD2I(D2I obj);
  public void visitIF_ICMPEQ(IF_ICMPEQ obj);
  public void visitAASTORE(AASTORE obj);
  public void visitARETURN(ARETURN obj);
  public void visitDUP2_X1(DUP2_X1 obj);
  public void visitFNEG(FNEG obj);
  public void visitGOTO_W(GOTO_W obj);
  public void visitD2F(D2F obj);
  public void visitGOTO(GOTO obj);
  public void visitISUB(ISUB obj);
  public void visitF2I(F2I obj);
  public void visitDNEG(DNEG obj);
  public void visitICONST(ICONST obj);
  public void visitFDIV(FDIV obj);
  public void visitI2B(I2B obj);
  public void visitLNEG(LNEG obj);
  public void visitLREM(LREM obj);
  public void visitIMUL(IMUL obj);
  public void visitIADD(IADD obj);
  public void visitLSHR(LSHR obj);
  public void visitLOOKUPSWITCH(LOOKUPSWITCH obj);
  public void visitDUP_X1(DUP_X1 obj);
  public void visitFCMPL(FCMPL obj);
  public void visitI2C(I2C obj);
  public void visitLMUL(LMUL obj);
  public void visitLUSHR(LUSHR obj);
  public void visitISHL(ISHL obj);
  public void visitLALOAD(LALOAD obj);
  public void visitASTORE(ASTORE obj);
  public void visitANEWARRAY(ANEWARRAY obj);
  public void visitFRETURN(FRETURN obj);
  public void visitFADD(FADD obj);
  public void visitBREAKPOINT(BREAKPOINT obj);
}
