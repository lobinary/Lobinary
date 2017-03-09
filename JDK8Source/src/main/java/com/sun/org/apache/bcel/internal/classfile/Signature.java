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
 *  本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 在任何情况下,APACHE软件基金会或其捐赠者均不对任何直接,间接,偶发,特殊,惩罚性或后果性损害(包括但不限于替代商品或服务的采购,使用,数据丢失或利润或业务中断),无论是由于任何责任推理原因,无论是
 * 在合同,严格责任或侵权(包括疏忽或其他方式)中,以任何方式使用本软件,即使已被告知此类软件的可能性损伤。
 *  本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 *  ================================================== ==================。
 * 
 *  该软件包括许多个人代表Apache软件基金会所做的自愿捐款。有关Apache Software Foundation的更多信息,请参阅<http://www.apache.org/>。
 */

import  com.sun.org.apache.bcel.internal.Constants;
import  java.io.*;

/**
 * This class is derived from <em>Attribute</em> and represents a reference
 * to a <href="http://wwwipd.ira.uka.de/~pizza/gj/">GJ</a> attribute.
 *
 * <p>
 * 
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @see     Attribute
 */
public final class Signature extends Attribute {
  private int signature_index;

  /**
   * Initialize from another object. Note that both objects use the same
   * references (shallow copy). Use clone() for a physical copy.
   * <p>
   *  此类派生自<em>属性</em>,表示对<href ="http://wwwipd.ira.uka.de/~pizza/gj/"> GJ </a>属性的引用。
   * 
   */
  public Signature(Signature c) {
    this(c.getNameIndex(), c.getLength(), c.getSignatureIndex(), c.getConstantPool());
  }

  /**
   * Construct object from file stream.
   * <p>
   *  从另一个对象初始化。注意两个对象使用相同的引用(浅拷贝)。使用clone()作为物理副本。
   * 
   * 
   * @param name_index Index in constant pool to CONSTANT_Utf8
   * @param length Content length in bytes
   * @param file Input stream
   * @param constant_pool Array of constants
   * @throws IOException
   */
  Signature(int name_index, int length, DataInputStream file,
           ConstantPool constant_pool) throws IOException
  {
    this(name_index, length, file.readUnsignedShort(), constant_pool);
  }

  /**
  /* <p>
  /* 从文件流构造对象。
  /* 
  /* 
   * @param name_index Index in constant pool to CONSTANT_Utf8
   * @param length Content length in bytes
   * @param constant_pool Array of constants
   * @param Signature_index Index in constant pool to CONSTANT_Utf8
   */
  public Signature(int name_index, int length, int signature_index,
                  ConstantPool constant_pool)
  {
    super(Constants.ATTR_SIGNATURE, name_index, length, constant_pool);
    this.signature_index = signature_index;
  }

  /**
   * Called by objects that are traversing the nodes of the tree implicitely
   * defined by the contents of a Java class. I.e., the hierarchy of methods,
   * fields, attributes, etc. spawns a tree of objects.
   *
   * <p>
   * 
   * @param v Visitor object
   */
   public void accept(Visitor v) {
     System.err.println("Visiting non-standard Signature object");
     v.visitSignature(this);
   }

  /**
   * Dump source file attribute to file stream in binary format.
   *
   * <p>
   *  由遍历由Java类的内容隐含地定义的树的节点的对象调用。即,方法,字段,属性等的层次结构产生对象树。
   * 
   * 
   * @param file Output file stream
   * @throws IOException
   */
  public final void dump(DataOutputStream file) throws IOException
  {
    super.dump(file);
    file.writeShort(signature_index);
  }

  /**
  /* <p>
  /*  以二进制格式将源文件属性转储到文件流。
  /* 
  /* 
   * @return Index in constant pool of source file name.
   */
  public final int getSignatureIndex() { return signature_index; }

  /**
  /* <p>
  /* 
   * @param Signature_index.
   */
  public final void setSignatureIndex(int signature_index) {
    this.signature_index = signature_index;
  }

  /**
  /* <p>
  /* 
   * @return GJ signature.
   */
  public final String getSignature() {
    ConstantUtf8 c = (ConstantUtf8)constant_pool.getConstant(signature_index,
                                                             Constants.CONSTANT_Utf8);
    return c.getBytes();
  }

  /**
   * Extends ByteArrayInputStream to make 'unreading' chars possible.
   * <p>
   */
  private static final class MyByteArrayInputStream extends ByteArrayInputStream {
    MyByteArrayInputStream(String data) { super(data.getBytes()); }
    final int  mark()                   { return pos; }
    final String getData()              { return new String(buf); }
    final void reset(int p)             { pos = p; }
    final void unread()                 { if(pos > 0) pos--; }
  }

  private static boolean identStart(int ch) {
    return ch == 'T' || ch == 'L';
  }

  private static boolean identPart(int ch) {
    return ch == '/' || ch == ';';
  }

  private static final void matchIdent(MyByteArrayInputStream in, StringBuffer buf) {
    int ch;

    if((ch = in.read()) == -1)
      throw new RuntimeException("Illegal signature: " + in.getData() +
                                 " no ident, reaching EOF");

    //System.out.println("return from ident:" + (char)ch);

    if(!identStart(ch)) {
      StringBuffer buf2 = new StringBuffer();

      int count = 1;
      while(Character.isJavaIdentifierPart((char)ch)) {
        buf2.append((char)ch);
        count++;
        ch = in.read();
      }

      if(ch == ':') { // Ok, formal parameter
        in.skip("Ljava/lang/Object".length());
        buf.append(buf2);

        ch = in.read();
        in.unread();
        //System.out.println("so far:" + buf2 + ":next:" +(char)ch);
      } else {
        for(int i=0; i < count; i++)
          in.unread();
      }

      return;
    }

    StringBuffer buf2 = new StringBuffer();
    ch = in.read();

    do {
      buf2.append((char)ch);
      ch = in.read();
      //System.out.println("within ident:"+ (char)ch);

    } while((ch != -1) && (Character.isJavaIdentifierPart((char)ch) || (ch == '/')));

    buf.append(buf2.toString().replace('/', '.'));

    //System.out.println("regular return ident:"+ (char)ch + ":" + buf2);

    if(ch != -1)
      in.unread();
  }

  private static final void matchGJIdent(MyByteArrayInputStream in,
                                         StringBuffer buf)
  {
    int ch;

    matchIdent(in, buf);

    ch = in.read();
    if((ch == '<') || ch == '(') { // Parameterized or method
      //System.out.println("Enter <");
      buf.append((char)ch);
      matchGJIdent(in, buf);

      while(((ch = in.read()) != '>') && (ch != ')')) { // List of parameters
        if(ch == -1)
          throw new RuntimeException("Illegal signature: " + in.getData() +
                                     " reaching EOF");

        //System.out.println("Still no >");
        buf.append(", ");
        in.unread();
        matchGJIdent(in, buf); // Recursive call
      }

      //System.out.println("Exit >");

      buf.append((char)ch);
    } else
      in.unread();

    ch = in.read();
    if(identStart(ch)) {
      in.unread();
      matchGJIdent(in, buf);
    } else if(ch == ')') {
      in.unread();
      return;
    } else if(ch != ';')
      throw new RuntimeException("Illegal signature: " + in.getData() + " read " +
                                 (char)ch);
  }

  public static String translate(String s) {
    //System.out.println("Sig:" + s);
    StringBuffer buf = new StringBuffer();

    matchGJIdent(new MyByteArrayInputStream(s), buf);

    return buf.toString();
  }

  public static final boolean isFormalParameterList(String s) {
    return s.startsWith("<") && (s.indexOf(':') > 0);
  }

  public static final boolean isActualParameterList(String s) {
    return s.startsWith("L") && s.endsWith(">;");
  }

  /**
  /* <p>
  /*  扩展ByteArrayInputStream以使"unreading"字符可能。
  /* 
  /* 
   * @return String representation
   */
  public final String toString() {
    String s = getSignature();

    return "Signature(" + s + ")";
  }

  /**
  /* <p>
  /* 
   * @return deep copy of this attribute
   */
  public Attribute copy(ConstantPool constant_pool) {
    return (Signature)clone();
  }
}
