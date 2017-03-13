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

import  com.sun.org.apache.bcel.internal.Constants;
import  java.io.*;

/**
 * This class represents the constant pool, i.e., a table of constants, of
 * a parsed classfile. It may contain null references, due to the JVM
 * specification that skips an entry after an 8-byte constant (double,
 * long) entry.  Those interested in generating constant pools
 * programatically should see <a href="../generic/ConstantPoolGen.html">
 * ConstantPoolGen</a>.

 * <p>
 *  此类表示已解析类文件的常量池(即常量表)由于JVM规范在8字节常量(双,长)条目后跳过一个条目,因此可能包含空引用。
 * 对感兴趣生成常量程序化地应该看到<a href=\"/generic/ConstantPoolGenhtml\"> ConstantPoolGen </a>。
 * 
 * 
 * @see     Constant
 * @see     com.sun.org.apache.bcel.internal.generic.ConstantPoolGen
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public class ConstantPool implements Cloneable, Node, Serializable {
  private int        constant_pool_count;
  private Constant[] constant_pool;

  /**
  /* <p>
  /* 
   * @param constant_pool Array of constants
   */
  public ConstantPool(Constant[] constant_pool)
  {
    setConstantPool(constant_pool);
  }

  /**
   * Read constants from given file stream.
   *
   * <p>
   *  从给定文件流读取常量
   * 
   * 
   * @param file Input stream
   * @throws IOException
   * @throws ClassFormatException
   */
  ConstantPool(DataInputStream file) throws IOException, ClassFormatException
  {
    byte tag;

    constant_pool_count = file.readUnsignedShort();
    constant_pool       = new Constant[constant_pool_count];

    /* constant_pool[0] is unused by the compiler and may be used freely
     * by the implementation.
     * <p>
     *  通过实现
     * 
     */
    for(int i=1; i < constant_pool_count; i++) {
      constant_pool[i] = Constant.readConstant(file);

      /* Quote from the JVM specification:
       * "All eight byte constants take up two spots in the constant pool.
       * If this is the n'th byte in the constant pool, then the next item
       * will be numbered n+2"
       *
       * Thus we have to increment the index counter.
       * <p>
       *  "所有八个字节常量占用常量池中的两个点如果这是常量池中的第n个字节,则下一个项将被编号为n + 2"
       * 
       * 因此我们必须递增索引计数器
       * 
       */
      tag = constant_pool[i].getTag();
      if((tag == Constants.CONSTANT_Double) || (tag == Constants.CONSTANT_Long))
        i++;
    }
  }

  /**
   * Called by objects that are traversing the nodes of the tree implicitely
   * defined by the contents of a Java class. I.e., the hierarchy of methods,
   * fields, attributes, etc. spawns a tree of objects.
   *
   * <p>
   *  由遍历由Java类Ie的内容隐含地定义的树的节点的对象调用,方法,字段,属性等的层次结构产生对象的树
   * 
   * 
   * @param v Visitor object
   */
  public void accept(Visitor v) {
    v.visitConstantPool(this);
  }

  /**
   * Resolve constant to a string representation.
   *
   * <p>
   *  将常量解析为字符串表示形式
   * 
   * 
   * @param  constant Constant to be printed
   * @return String representation
   */
  public String constantToString(Constant c)
       throws ClassFormatException
  {
    String   str;
    int      i;
    byte     tag = c.getTag();

    switch(tag) {
    case Constants.CONSTANT_Class:
      i   = ((ConstantClass)c).getNameIndex();
      c   = getConstant(i, Constants.CONSTANT_Utf8);
      str = Utility.compactClassName(((ConstantUtf8)c).getBytes(), false);
      break;

    case Constants.CONSTANT_String:
      i   = ((ConstantString)c).getStringIndex();
      c   = getConstant(i, Constants.CONSTANT_Utf8);
      str = "\"" + escape(((ConstantUtf8)c).getBytes()) + "\"";
      break;

    case Constants.CONSTANT_Utf8:    str = ((ConstantUtf8)c).getBytes();         break;
    case Constants.CONSTANT_Double:  str = "" + ((ConstantDouble)c).getBytes();  break;
    case Constants.CONSTANT_Float:   str = "" + ((ConstantFloat)c).getBytes();   break;
    case Constants.CONSTANT_Long:    str = "" + ((ConstantLong)c).getBytes();    break;
    case Constants.CONSTANT_Integer: str = "" + ((ConstantInteger)c).getBytes(); break;

    case Constants.CONSTANT_NameAndType:
      str = (constantToString(((ConstantNameAndType)c).getNameIndex(),
                              Constants.CONSTANT_Utf8) + " " +
             constantToString(((ConstantNameAndType)c).getSignatureIndex(),
                              Constants.CONSTANT_Utf8));
      break;

    case Constants.CONSTANT_InterfaceMethodref: case Constants.CONSTANT_Methodref:
    case Constants.CONSTANT_Fieldref:
      str = (constantToString(((ConstantCP)c).getClassIndex(),
                              Constants.CONSTANT_Class) + "." +
             constantToString(((ConstantCP)c).getNameAndTypeIndex(),
                              Constants.CONSTANT_NameAndType));
      break;

    default: // Never reached
      throw new RuntimeException("Unknown constant type " + tag);
    }

    return str;
  }

  private static final String escape(String str) {
    int          len = str.length();
    StringBuffer buf = new StringBuffer(len + 5);
    char[]       ch  = str.toCharArray();

    for(int i=0; i < len; i++) {
      switch(ch[i]) {
      case '\n' : buf.append("\\n"); break;
      case '\r' : buf.append("\\r"); break;
      case '\t' : buf.append("\\t"); break;
      case '\b' : buf.append("\\b"); break;
      case '"'  : buf.append("\\\""); break;
      default: buf.append(ch[i]);
      }
    }

    return buf.toString();
  }


  /**
   * Retrieve constant at `index' from constant pool and resolve it to
   * a string representation.
   *
   * <p>
   *  从常量池检索常量'index'并将其解析为字符串表示形式
   * 
   * 
   * @param  index of constant in constant pool
   * @param  tag expected type
   * @return String representation
   */
  public String constantToString(int index, byte tag)
       throws ClassFormatException
  {
    Constant c = getConstant(index, tag);
    return constantToString(c);
  }

  /**
   * Dump constant pool to file stream in binary format.
   *
   * <p>
   *  将常量池转储为二进制格式的文件流
   * 
   * 
   * @param file Output file stream
   * @throws IOException
   */
  public void dump(DataOutputStream file) throws IOException
  {
    file.writeShort(constant_pool_count);

    for(int i=1; i < constant_pool_count; i++)
      if(constant_pool[i] != null)
        constant_pool[i].dump(file);
  }

  /**
   * Get constant from constant pool.
   *
   * <p>
   *  从常量池获取常量
   * 
   * 
   * @param  index Index in constant pool
   * @return Constant value
   * @see    Constant
   */
  public Constant getConstant(int index) {
    if (index >= constant_pool.length || index < 0)
      throw new ClassFormatException("Invalid constant pool reference: " +
                                 index + ". Constant pool size is: " +
                                 constant_pool.length);
    return constant_pool[index];
  }

  /**
   * Get constant from constant pool and check whether it has the
   * expected type.
   *
   * <p>
   *  从常量池获取常量,并检查其是否具有期望的类型
   * 
   * 
   * @param  index Index in constant pool
   * @param  tag Tag of expected constant, i.e., its type
   * @return Constant value
   * @see    Constant
   * @throws  ClassFormatException
   */
  public Constant getConstant(int index, byte tag)
       throws ClassFormatException
  {
    Constant c;

    c = getConstant(index);

    if(c == null)
      throw new ClassFormatException("Constant pool at index " + index + " is null.");

    if(c.getTag() == tag)
      return c;
    else
      throw new ClassFormatException("Expected class `" + Constants.CONSTANT_NAMES[tag] +
                                 "' at index " + index + " and got " + c);
  }

  /**
  /* <p>
  /* 
   * @return Array of constants.
   * @see    Constant
   */
  public Constant[] getConstantPool() { return constant_pool;  }
  /**
   * Get string from constant pool and bypass the indirection of
   * `ConstantClass' and `ConstantString' objects. I.e. these classes have
   * an index field that points to another entry of the constant pool of
   * type `ConstantUtf8' which contains the real data.
   *
   * <p>
   *  从常量池中获取字符串,并绕过"ConstantClass"和"ConstantString"对象的间接,这些类有一个索引字段,它指向类型为ConstantUtf8的常量池的另一个条目,它包含实数据
   * 
   * 
   * @param  index Index in constant pool
   * @param  tag Tag of expected constant, either ConstantClass or ConstantString
   * @return Contents of string reference
   * @see    ConstantClass
   * @see    ConstantString
   * @throws  ClassFormatException
   */
  public String getConstantString(int index, byte tag)
       throws ClassFormatException
  {
    Constant c;
    int    i;

    c = getConstant(index, tag);

    /* This switch() is not that elegant, since the two classes have the
     * same contents, they just differ in the name of the index
     * field variable.
     * But we want to stick to the JVM naming conventions closely though
     * we could have solved these more elegantly by using the same
     * variable name or by subclassing.
     * <p>
     * 相同的内容,它们只是索引字段变量的名称不同但我们想坚持JVM命名约定,虽然我们可以通过使用相同的变量名或通过子类化更优雅地解决这些
     * 
     */
    switch(tag) {
    case Constants.CONSTANT_Class:  i = ((ConstantClass)c).getNameIndex();    break;
    case Constants.CONSTANT_String: i = ((ConstantString)c).getStringIndex(); break;
    default:
      throw new RuntimeException("getConstantString called with illegal tag " + tag);
    }

    // Finally get the string from the constant pool
    c = getConstant(i, Constants.CONSTANT_Utf8);
    return ((ConstantUtf8)c).getBytes();
  }
  /**
  /* <p>
  /* 
   * @return Length of constant pool.
   */
  public int getLength()
  {
    return constant_pool_count;
  }

  /**
  /* <p>
  /* 
   * @param constant Constant to set
   */
  public void setConstant(int index, Constant constant) {
    constant_pool[index] = constant;
  }

  /**
  /* <p>
  /* 
   * @param constant_pool
   */
  public void setConstantPool(Constant[] constant_pool) {
    this.constant_pool = constant_pool;
    constant_pool_count = (constant_pool == null)? 0 : constant_pool.length;
  }
  /**
  /* <p>
  /* 
   * @return String representation.
   */
  public String toString() {
    StringBuffer buf = new StringBuffer();

    for(int i=1; i < constant_pool_count; i++)
      buf.append(i + ")" + constant_pool[i] + "\n");

    return buf.toString();
  }

  /**
  /* <p>
  /* 
   * @return deep copy of this constant pool
   */
  public ConstantPool copy() {
    ConstantPool c = null;

    try {
      c = (ConstantPool)clone();
    } catch(CloneNotSupportedException e) {}

    c.constant_pool = new Constant[constant_pool_count];

    for(int i=1; i < constant_pool_count; i++) {
      if(constant_pool[i] != null)
        c.constant_pool[i] = constant_pool[i].copy();
    }

    return c;
  }
}
