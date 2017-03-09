/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sun.org.apache.bcel.internal.util;

import com.sun.org.apache.bcel.internal.generic.*;
import com.sun.org.apache.bcel.internal.classfile.Utility;
import com.sun.org.apache.bcel.internal.Constants;
import java.io.PrintWriter;
import java.util.*;

/* ====================================================================
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2002 The Apache Software Foundation.  All rights
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
 *  版权所有(c)2002 Apache软件基金会。版权所有。
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
 * Factory creates il.append() statements, and sets instruction targets.
 * A helper class for BCELifier.
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
 * @see BCELifier
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
class BCELFactory extends EmptyVisitor {
  private MethodGen       _mg;
  private PrintWriter     _out;
  private ConstantPoolGen _cp;

  BCELFactory(MethodGen mg, PrintWriter out) {
    _mg  = mg;
    _cp  = mg.getConstantPool();
    _out = out;
  }

  private HashMap branch_map = new HashMap(); // Map<Instruction, InstructionHandle>

  public void start() {
    if(!_mg.isAbstract() && !_mg.isNative()) {
      for(InstructionHandle ih = _mg.getInstructionList().getStart();
          ih != null; ih = ih.getNext()) {
        Instruction i = ih.getInstruction();

        if(i instanceof BranchInstruction) {
          branch_map.put(i, ih); // memorize container
        }

        if(ih.hasTargeters()) {
          if(i instanceof BranchInstruction) {
            _out.println("    InstructionHandle ih_" + ih.getPosition() + ";");
          } else {
            _out.print("    InstructionHandle ih_" + ih.getPosition() + " = ");
          }
        } else {
          _out.print("    ");
        }

        if(!visitInstruction(i))
          i.accept(this);
      }

      updateBranchTargets();
      updateExceptionHandlers();
    }
  }

  private boolean visitInstruction(Instruction i) {
    short opcode = i.getOpcode();

    if((InstructionConstants.INSTRUCTIONS[opcode] != null) &&
       !(i instanceof ConstantPushInstruction) &&
       !(i instanceof ReturnInstruction)) { // Handled below
      _out.println("il.append(InstructionConstants." +
                   i.getName().toUpperCase() + ");");
      return true;
    }

    return false;
  }

  public void visitLocalVariableInstruction(LocalVariableInstruction i) {
    short  opcode = i.getOpcode();
    Type   type   = i.getType(_cp);

    if(opcode == Constants.IINC) {
      _out.println("il.append(new IINC(" + i.getIndex() + ", " +
                   ((IINC)i).getIncrement() + "));");
    } else {
      String kind   = (opcode < Constants.ISTORE)? "Load" : "Store";
      _out.println("il.append(_factory.create" + kind + "(" +
                   BCELifier.printType(type) + ", " +
                   i.getIndex() + "));");
    }
  }

  public void visitArrayInstruction(ArrayInstruction i) {
    short  opcode = i.getOpcode();
    Type   type   = i.getType(_cp);
    String kind   = (opcode < Constants.IASTORE)? "Load" : "Store";

    _out.println("il.append(_factory.createArray" + kind + "(" +
                 BCELifier.printType(type) + "));");
  }

  public void visitFieldInstruction(FieldInstruction i) {
    short  opcode = i.getOpcode();

    String class_name = i.getClassName(_cp);
    String field_name = i.getFieldName(_cp);
    Type   type       = i.getFieldType(_cp);

    _out.println("il.append(_factory.createFieldAccess(\"" +
                 class_name + "\", \"" + field_name + "\", " +
                 BCELifier.printType(type) + ", " +
                 "Constants." + Constants.OPCODE_NAMES[opcode].toUpperCase() +
                 "));");
  }

  public void visitInvokeInstruction(InvokeInstruction i) {
    short  opcode      = i.getOpcode();
    String class_name  = i.getClassName(_cp);
    String method_name = i.getMethodName(_cp);
    Type   type        = i.getReturnType(_cp);
    Type[] arg_types   = i.getArgumentTypes(_cp);

    _out.println("il.append(_factory.createInvoke(\"" +
                 class_name + "\", \"" + method_name + "\", " +
                 BCELifier.printType(type) + ", " +
                 BCELifier.printArgumentTypes(arg_types) + ", " +
                 "Constants." + Constants.OPCODE_NAMES[opcode].toUpperCase() +
                 "));");
  }

  public void visitAllocationInstruction(AllocationInstruction i) {
    Type type;

    if(i instanceof CPInstruction) {
      type = ((CPInstruction)i).getType(_cp);
    } else {
      type = ((NEWARRAY)i).getType();
    }

    short opcode = ((Instruction)i).getOpcode();
    int   dim    = 1;

    switch(opcode) {
    case Constants.NEW:
      _out.println("il.append(_factory.createNew(\"" +
                   ((ObjectType)type).getClassName() + "\"));");
      break;

    case Constants.MULTIANEWARRAY:
      dim = ((MULTIANEWARRAY)i).getDimensions();

    case Constants.ANEWARRAY:
    case Constants.NEWARRAY:
      _out.println("il.append(_factory.createNewArray(" +
                   BCELifier.printType(type) + ", (short) " + dim + "));");
      break;

    default:
      throw new RuntimeException("Oops: " + opcode);
    }
  }

  private void createConstant(Object value) {
    String embed = value.toString();

    if(value instanceof String)
      embed = '"' + Utility.convertString(value.toString()) + '"';
    else if(value instanceof Character)
      embed = "(char)0x" + Integer.toHexString(((Character)value).charValue());

    _out.println("il.append(new PUSH(_cp, " + embed + "));");
  }

  public void visitLDC(LDC i) {
    createConstant(i.getValue(_cp));
  }

  public void visitLDC2_W(LDC2_W i) {
    createConstant(i.getValue(_cp));
  }

  public void visitConstantPushInstruction(ConstantPushInstruction i) {
    createConstant(i.getValue());
  }

  public void visitINSTANCEOF(INSTANCEOF i) {
    Type type = i.getType(_cp);

    _out.println("il.append(new INSTANCEOF(_cp.addClass(" +
                 BCELifier.printType(type) + ")));");
  }

  public void visitCHECKCAST(CHECKCAST i) {
    Type type = i.getType(_cp);

    _out.println("il.append(_factory.createCheckCast(" +
                 BCELifier.printType(type) + "));");
  }

  public void visitReturnInstruction(ReturnInstruction i) {
    Type type = i.getType(_cp);

    _out.println("il.append(_factory.createReturn(" +
                 BCELifier.printType(type) + "));");
  }

  // Memorize BranchInstructions that need an update
  private ArrayList branches = new ArrayList();

  public void visitBranchInstruction(BranchInstruction bi) {
    BranchHandle bh   = (BranchHandle)branch_map.get(bi);
    int          pos  = bh.getPosition();
    String       name = bi.getName() + "_" + pos;

    if(bi instanceof Select) {
      Select s = (Select)bi;
      branches.add(bi);

      StringBuffer args   = new StringBuffer("new int[] { ");
      int[]        matchs = s.getMatchs();

      for(int i=0; i < matchs.length; i++) {
        args.append(matchs[i]);

        if(i < matchs.length - 1)
          args.append(", ");
      }

      args.append(" }");

      _out.print("    Select " + name + " = new " +
                 bi.getName().toUpperCase() + "(" + args +
                 ", new InstructionHandle[] { ");

      for(int i=0; i < matchs.length; i++) {
        _out.print("null");

        if(i < matchs.length - 1)
          _out.print(", ");
      }

      _out.println(");");
    } else {
      int    t_pos  = bh.getTarget().getPosition();
      String target;

      if(pos > t_pos) {
        target = "ih_" + t_pos;
      } else {
        branches.add(bi);
        target = "null";
      }

      _out.println("    BranchInstruction " + name +
                   " = _factory.createBranchInstruction(" +
                   "Constants." + bi.getName().toUpperCase() + ", " +
                   target + ");");
    }

    if(bh.hasTargeters())
      _out.println("    ih_" + pos + " = il.append(" + name + ");");
    else
      _out.println("    il.append(" + name + ");");
  }

  public void visitRET(RET i) {
    _out.println("il.append(new RET(" + i.getIndex() + ")));");
  }

  private void updateBranchTargets() {
    for(Iterator i = branches.iterator(); i.hasNext(); ) {
      BranchInstruction bi    = (BranchInstruction)i.next();
      BranchHandle      bh    = (BranchHandle)branch_map.get(bi);
      int               pos   = bh.getPosition();
      String            name  = bi.getName() + "_" + pos;
      int               t_pos = bh.getTarget().getPosition();

      _out.println("    " + name + ".setTarget(ih_" + t_pos + ");");

      if(bi instanceof Select) {
        InstructionHandle[] ihs = ((Select)bi).getTargets();

        for(int j = 0; j < ihs.length; j++) {
          t_pos = ihs[j].getPosition();

          _out.println("    " + name + ".setTarget(" + j +
                       ", ih_" + t_pos + ");");
        }
      }
    }
  }

  private void updateExceptionHandlers() {
    CodeExceptionGen[] handlers = _mg.getExceptionHandlers();

    for(int i=0; i < handlers.length; i++) {
      CodeExceptionGen h    = handlers[i];
      String           type = (h.getCatchType() == null)?
        "null" : BCELifier.printType(h.getCatchType());

      _out.println("    method.addExceptionHandler(" +
                   "ih_" + h.getStartPC().getPosition() + ", " +
                   "ih_" + h.getEndPC().getPosition() + ", " +
                   "ih_" + h.getHandlerPC().getPosition() + ", " +
                   type + ");");
    }
  }
}
