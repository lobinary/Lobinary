/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sun.org.apache.bcel.internal.classfile;

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
 */
import java.util.Stack;

/**
 * Traverses a JavaClass with another Visitor object 'piggy-backed'
 * that is applied to all components of a JavaClass object. I.e. this
 * class supplies the traversal strategy, other classes can make use
 * of it.
 *
 * <p>
 *  本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 在任何情况下,APACHE软件基金会或其捐赠者均不对任何直接,间接,偶发,特殊,惩罚性或后果性损害(包括但不限于替代商品或服务的采购,使用,数据丢失或利润或业务中断),无论是由于任何责任推理原因,无论是
 * 在合同,严格责任或侵权(包括疏忽或其他方式)中,以任何方式使用本软件,即使已被告知此类软件的可能性损伤。
 *  本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 *  ================================================== ==================。
 * 
 *  该软件包括许多个人代表Apache软件基金会所做的自愿捐款。有关Apache Software Foundation的更多信息,请参阅<http://www.apache.org/>。
 * 
 * 
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class DescendingVisitor implements Visitor {
  private JavaClass clazz;
  private Visitor   visitor;
  private Stack     stack = new Stack();

  /** @return container of current entitity, i.e., predecessor during traversal
  /* <p>
  /*  遍历具有应用于JavaClass对象的所有组件的另一个Visitor对象"piggy-backed"的JavaClass。也就是说这个类提供了遍历策略,其他类可以利用它。
  /* 
   */
  public Object predecessor() {
    return predecessor(0);
  }

  /**
  /* <p>
  /* 
   * @param level nesting level, i.e., 0 returns the direct predecessor
   * @return container of current entitity, i.e., predecessor during traversal
   */
  public Object predecessor(int level) {
    int size = stack.size();

    if((size < 2) || (level < 0))
      return null;
    else
      return stack.elementAt(size - (level + 2)); // size - 1 == current
  }

  /** @return current object
  /* <p>
   */
  public Object current() {
    return stack.peek();
  }

  /**
  /* <p>
  /* 
   * @param clazz Class to traverse
   * @param visitor visitor object to apply to all components
   */
  public DescendingVisitor(JavaClass clazz, Visitor visitor) {
    this.clazz   = clazz;
    this.visitor = visitor;
  }

  /**
   * Start traversal.
   * <p>
   */
  public void visit() { clazz.accept(this); }

  public void visitJavaClass(JavaClass clazz) {
    stack.push(clazz);
    clazz.accept(visitor);

    Field[] fields = clazz.getFields();
    for(int i=0; i < fields.length; i++)
      fields[i].accept(this);

    Method[] methods = clazz.getMethods();
    for(int i=0; i < methods.length; i++)
      methods[i].accept(this);

    Attribute[] attributes = clazz.getAttributes();
    for(int i=0; i < attributes.length; i++)
      attributes[i].accept(this);

    clazz.getConstantPool().accept(this);
    stack.pop();
  }

  public void visitField(Field field) {
    stack.push(field);
    field.accept(visitor);

    Attribute[] attributes = field.getAttributes();
    for(int i=0; i < attributes.length; i++)
      attributes[i].accept(this);
    stack.pop();
  }

  public void visitConstantValue(ConstantValue cv) {
    stack.push(cv);
    cv.accept(visitor);
    stack.pop();
  }

  public void visitMethod(Method method) {
    stack.push(method);
    method.accept(visitor);

    Attribute[] attributes = method.getAttributes();
    for(int i=0; i < attributes.length; i++)
      attributes[i].accept(this);

    stack.pop();
  }

  public void visitExceptionTable(ExceptionTable table) {
    stack.push(table);
    table.accept(visitor);
    stack.pop();
  }

  public void visitCode(Code code) {
    stack.push(code);
    code.accept(visitor);

    CodeException[] table = code.getExceptionTable();
    for(int i=0; i < table.length; i++)
      table[i].accept(this);

    Attribute[] attributes = code.getAttributes();
    for(int i=0; i < attributes.length; i++)
      attributes[i].accept(this);
    stack.pop();
  }

  public void visitCodeException(CodeException ce) {
    stack.push(ce);
    ce.accept(visitor);
    stack.pop();
  }

  public void visitLineNumberTable(LineNumberTable table) {
    stack.push(table);
    table.accept(visitor);

    LineNumber[] numbers = table.getLineNumberTable();
    for(int i=0; i < numbers.length; i++)
      numbers[i].accept(this);
    stack.pop();
  }

  public void visitLineNumber(LineNumber number) {
    stack.push(number);
    number.accept(visitor);
    stack.pop();
  }

  public void visitLocalVariableTable(LocalVariableTable table) {
    stack.push(table);
    table.accept(visitor);

    LocalVariable[] vars = table.getLocalVariableTable();
    for(int i=0; i < vars.length; i++)
      vars[i].accept(this);
    stack.pop();
  }

  public void visitLocalVariableTypeTable(LocalVariableTypeTable obj) {
    stack.push(obj);
    obj.accept(visitor);

    LocalVariable[] vars = obj.getLocalVariableTypeTable();
    for(int i=0; i < vars.length; i++)
      vars[i].accept(this);
    stack.pop();
  }

  public void visitStackMap(StackMap table) {
    stack.push(table);
    table.accept(visitor);

    StackMapEntry[] vars = table.getStackMap();

    for(int i=0; i < vars.length; i++)
      vars[i].accept(this);
    stack.pop();
  }

  public void visitStackMapEntry(StackMapEntry var) {
    stack.push(var);
    var.accept(visitor);
    stack.pop();
  }

  public void visitLocalVariable(LocalVariable var) {
    stack.push(var);
    var.accept(visitor);
    stack.pop();
  }

  public void visitConstantPool(ConstantPool cp) {
    stack.push(cp);
    cp.accept(visitor);

    Constant[] constants = cp.getConstantPool();
    for(int i=1; i < constants.length; i++) {
      if(constants[i] != null)
        constants[i].accept(this);
    }

    stack.pop();
  }

  public void visitConstantClass(ConstantClass constant) {
    stack.push(constant);
    constant.accept(visitor);
    stack.pop();
  }

  public void visitConstantDouble(ConstantDouble constant) {
    stack.push(constant);
    constant.accept(visitor);
    stack.pop();
  }

  public void visitConstantFieldref(ConstantFieldref constant) {
    stack.push(constant);
    constant.accept(visitor);
    stack.pop();
  }

  public void visitConstantFloat(ConstantFloat constant) {
    stack.push(constant);
    constant.accept(visitor);
    stack.pop();
 }

  public void visitConstantInteger(ConstantInteger constant) {
    stack.push(constant);
    constant.accept(visitor);
    stack.pop();
  }

  public void visitConstantInterfaceMethodref(ConstantInterfaceMethodref constant) {
    stack.push(constant);
    constant.accept(visitor);
    stack.pop();
  }

  public void visitConstantLong(ConstantLong constant) {
    stack.push(constant);
    constant.accept(visitor);
    stack.pop();
  }

  public void visitConstantMethodref(ConstantMethodref constant) {
    stack.push(constant);
    constant.accept(visitor);
    stack.pop();
  }

  public void visitConstantNameAndType(ConstantNameAndType constant) {
    stack.push(constant);
    constant.accept(visitor);
    stack.pop();
  }

  public void visitConstantString(ConstantString constant) {
    stack.push(constant);
    constant.accept(visitor);
    stack.pop();
  }

  public void visitConstantUtf8(ConstantUtf8 constant) {
    stack.push(constant);
    constant.accept(visitor);
    stack.pop();
  }

  public void visitInnerClasses(InnerClasses ic) {
    stack.push(ic);
    ic.accept(visitor);

    InnerClass[] ics = ic.getInnerClasses();
    for(int i=0; i < ics.length; i++)
      ics[i].accept(this);
    stack.pop();
  }

  public void visitInnerClass(InnerClass inner) {
    stack.push(inner);
    inner.accept(visitor);
    stack.pop();
  }

  public void visitDeprecated(Deprecated attribute) {
    stack.push(attribute);
    attribute.accept(visitor);
    stack.pop();
  }

  public void visitSignature(Signature attribute) {
    stack.push(attribute);
    attribute.accept(visitor);
    stack.pop();
  }

  public void visitSourceFile(SourceFile attribute) {
    stack.push(attribute);
    attribute.accept(visitor);
    stack.pop();
  }

  public void visitSynthetic(Synthetic attribute) {
    stack.push(attribute);
    attribute.accept(visitor);
    stack.pop();
  }

  public void visitUnknown(Unknown attribute) {
    stack.push(attribute);
    attribute.accept(visitor);
    stack.pop();
 }
}
