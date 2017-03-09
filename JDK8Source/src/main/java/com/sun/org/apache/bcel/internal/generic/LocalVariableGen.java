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
 */

import com.sun.org.apache.bcel.internal.Constants;
import com.sun.org.apache.bcel.internal.classfile.*;
import java.util.Objects;

/**
 * This class represents a local variable within a method. It contains its
 * scope, name and type. The generated LocalVariable object can be obtained
 * with getLocalVariable which needs the instruction list and the constant
 * pool as parameters.
 *
 * <p>
 * 
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @see     LocalVariable
 * @see     MethodGen
 */
public class LocalVariableGen
  implements InstructionTargeter, NamedAndTyped, Cloneable,
             java.io.Serializable
{
  private final int   index;
  private String      name;
  private Type        type;
  private InstructionHandle start, end;

  /**
   * Generate a local variable that with index `index'. Note that double and long
   * variables need two indexs. Index indices have to be provided by the user.
   *
   * <p>
   *  此类表示方法中的局部变量。它包含其范围,名称和类型。生成的LocalVariable对象可以通过getLocalVariable获得,它需要指令列表和常量池作为参数。
   * 
   * 
   * @param index index of local variable
   * @param name its name
   * @param type its type
   * @param start from where the instruction is valid (null means from the start)
   * @param end until where the instruction is valid (null means to the end)
   */
  public LocalVariableGen(int index, String name, Type type,
                          InstructionHandle start, InstructionHandle end) {
    if((index < 0) || (index > Constants.MAX_SHORT))
      throw new ClassGenException("Invalid index index: " + index);

    this.name  = name;
    this.type  = type;
    this.index  = index;
    setStart(start);
    setEnd(end);
  }

  /**
   * Get LocalVariable object.
   *
   * This relies on that the instruction list has already been dumped to byte code or
   * or that the `setPositions' methods has been called for the instruction list.
   *
   * Note that for local variables whose scope end at the last
   * instruction of the method's code, the JVM specification is ambiguous:
   * both a start_pc+length ending at the last instruction and
   * start_pc+length ending at first index beyond the end of the code are
   * valid.
   *
   * <p>
   * 生成索引为"index"的局部变量。注意,double和long变量需要两个索引。索引索引必须由用户提供。
   * 
   * 
   * @param il instruction list (byte code) which this variable belongs to
   * @param cp constant pool
   */
  public LocalVariable getLocalVariable(ConstantPoolGen cp) {
    int start_pc        = start.getPosition();
    int length          = end.getPosition() - start_pc;

    if(length > 0)
      length += end.getInstruction().getLength();

    int name_index      = cp.addUtf8(name);
    int signature_index = cp.addUtf8(type.getSignature());

    return new LocalVariable(start_pc, length, name_index,
                             signature_index, index, cp.getConstantPool());
  }

  public int         getIndex()                  { return index; }
  @Override
  public void        setName(String name)        { this.name = name; }
  @Override
  public String      getName()                   { return name; }
  @Override
  public void        setType(Type type)          { this.type = type; }
  @Override
  public Type        getType()                   { return type; }

  public InstructionHandle getStart()                  { return start; }
  public InstructionHandle getEnd()                    { return end; }

  /**
   * Remove this from any known HashSet in which it might be registered.
   * <p>
   *  获取LocalVariable对象。
   * 
   *  这依赖于指令列表已经被转储为字节代码或者已经为指令列表调用了"setPositions"方法。
   * 
   *  注意,对于范围在方法代码的最后一条指令处结束的局部变量,JVM规范是不明确的：在最后一条指令处结束的start_pc +长度和在代码结束处第一条索引处结束的start_pc +长度都是有效的。
   * 
   */
  void notifyTargetChanging() {
    // hashCode depends on 'index', 'start', and 'end'.
    // Therefore before changing any of these values we
    // need to unregister 'this' from any HashSet where
    // this is registered, and then we need to add it
    // back...

    // Unregister 'this' from the HashSet held by 'start'.
    BranchInstruction.notifyTargetChanging(this.start, this);
    if (this.end != this.start) {
        // Since hashCode() is going to change we need to unregister
        // 'this' both form 'start' and 'end'.
        // Unregister 'this' from the HashSet held by 'end'.
        BranchInstruction.notifyTargetChanging(this.end, this);
    }
  }

  /**
   * Add back 'this' in all HashSet in which it should be registered.
   * <p>
   *  从任何已知的可能注册的HashSet中删除它。
   * 
   * 
   **/
  void notifyTargetChanged() {
    // hashCode depends on 'index', 'start', and 'end'.
    // Therefore before changing any of these values we
    // need to unregister 'this' from any HashSet where
    // this is registered, and then we need to add it
    // back...

    // Register 'this' in the HashSet held by start.
    BranchInstruction.notifyTargetChanged(this.start, this);
    if (this.end != this.start) {
        // Since hashCode() has changed we need to register
        // 'this' again in 'end'.
        // Add back 'this' in the HashSet held by 'end'.
        BranchInstruction.notifyTargetChanged(this.end, this);
    }
  }

  public final void setStart(InstructionHandle start) {

    // Call notifyTargetChanging *before* modifying this,
    // as the code triggered by notifyTargetChanging
    // depends on this pointing to the 'old' start.
    notifyTargetChanging();

    this.start = start;

    // call notifyTargetChanged *after* modifying this,
    // as the code triggered by notifyTargetChanged
    // depends on this pointing to the 'new' start.
    notifyTargetChanged();
  }

  public final void setEnd(InstructionHandle end) {
    // call notifyTargetChanging *before* modifying this,
    // as the code triggered by notifyTargetChanging
    // depends on this pointing to the 'old' end.
    // Unregister 'this' from the HashSet held by the 'old' end.
    notifyTargetChanging();

    this.end = end;

    // call notifyTargetChanged *after* modifying this,
    // as the code triggered by notifyTargetChanged
    // depends on this pointing to the 'new' end.
    // Register 'this' in the HashSet held by the 'new' end.
    notifyTargetChanged();

  }

  /**
  /* <p>
  /*  在所有应该注册的HashSet中添加"this"。
  /* 
  /* 
   * @param old_ih old target, either start or end
   * @param new_ih new target
   */
  @Override
  public void updateTarget(InstructionHandle old_ih, InstructionHandle new_ih) {
    boolean targeted = false;

    if(start == old_ih) {
      targeted = true;
      setStart(new_ih);
    }

    if(end == old_ih) {
      targeted = true;
      setEnd(new_ih);
    }

    if(!targeted)
      throw new ClassGenException("Not targeting " + old_ih + ", but {" + start + ", " +
                                  end + "}");
  }

  /**
  /* <p>
  /* 
   * @return true, if ih is target of this variable
   */
  @Override
  public boolean containsTarget(InstructionHandle ih) {
    return (start == ih) || (end == ih);
  }

  /**
   * We consider two local variables to be equal, if they use the same index and
   * are valid in the same range.
   * <p>
   */
  @Override
  public boolean equals(Object o) {
    if (o==this)
      return true;

    if(!(o instanceof LocalVariableGen))
      return false;

    LocalVariableGen l = (LocalVariableGen)o;
    return (l.index == index) && (l.start == start) && (l.end == end);
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 59 * hash + this.index;
    hash = 59 * hash + Objects.hashCode(this.start);
    hash = 59 * hash + Objects.hashCode(this.end);
    return hash;
  }

  @Override
  public String toString() {
    return "LocalVariableGen(" + name +  ", " + type +  ", " + start + ", " + end + ")";
  }

  @Override
  public Object clone() {
    try {
      return super.clone();
    } catch(CloneNotSupportedException e) {
      System.err.println(e);
      return null;
    }
  }
}
