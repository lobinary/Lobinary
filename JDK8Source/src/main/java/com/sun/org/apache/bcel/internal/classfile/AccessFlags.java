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
 */

import  com.sun.org.apache.bcel.internal.Constants;

/**
 * Super class for all objects that have modifiers like private, final, ...
 * I.e. classes, fields, and methods.
 *
 * <p>
 * 
 *  本软件按"原样"提供,任何明示或暗示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 在任何情况下,APACHE软件基金会或其捐赠者均不对任何直接,间接,偶发,特殊,惩罚性或后果性损害(包括但不限于替代商品或服务的采购,使用,数据丢失或利润或业务中断),无论是由于任何责任推理原因,无论是
 * 在合同,严格责任或侵权(包括疏忽或其他方式)中,以任何方式使用本软件,即使已被告知此类软件的可能性损伤。
 *  本软件按"原样"提供,任何明示或暗示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 *  ================================================== ==================。
 * 
 *  该软件包括许多个人代表Apache软件基金会所做的自愿捐款。有关Apache Software Foundation的更多信息,请参阅<http://www.apache.org/>。
 * 
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class AccessFlags implements java.io.Serializable {
  protected int access_flags;

  public AccessFlags() {}

  /**
  /* <p>
  /* 
   * @param a inital access flags
   */
  public AccessFlags(int a) {
    access_flags = a;
  }

  /**
  /* <p>
  /*  所有具有修饰符的对象的超类,如private,final,... I.e。类,字段和方法。
  /* 
  /* 
   * @return Access flags of the object aka. "modifiers".
   */
  public final int getAccessFlags() { return access_flags; }

  /**
  /* <p>
  /* 
   * @return Access flags of the object aka. "modifiers".
   */
  public final int getModifiers() { return access_flags; }

  /** Set access flags aka "modifiers".
  /* <p>
  /* 
   * @param access_flags Access flags of the object.
   */
  public final void setAccessFlags(int access_flags) {
    this.access_flags = access_flags;
  }

  /** Set access flags aka "modifiers".
  /* <p>
  /* 
   * @param access_flags Access flags of the object.
   */
  public final void setModifiers(int access_flags) {
    setAccessFlags(access_flags);
  }

  private final void setFlag(int flag, boolean set) {
    if((access_flags & flag) != 0) { // Flag is set already
      if(!set) // Delete flag ?
        access_flags ^= flag;
    } else {   // Flag not set
      if(set)  // Set flag ?
        access_flags |= flag;
    }
  }

  public final void isPublic(boolean flag) { setFlag(Constants.ACC_PUBLIC, flag); }
  public final boolean isPublic() {
    return (access_flags & Constants.ACC_PUBLIC) != 0;
  }

  public final void isPrivate(boolean flag) { setFlag(Constants.ACC_PRIVATE, flag); }
  public final boolean isPrivate() {
    return (access_flags & Constants.ACC_PRIVATE) != 0;
  }

  public final void isProtected(boolean flag) { setFlag(Constants.ACC_PROTECTED, flag); }
  public final boolean isProtected() {
    return (access_flags & Constants.ACC_PROTECTED) != 0;
  }

  public final void isStatic(boolean flag) { setFlag(Constants.ACC_STATIC, flag); }
  public final boolean isStatic() {
    return (access_flags & Constants.ACC_STATIC) != 0;
  }

  public final void isFinal(boolean flag) { setFlag(Constants.ACC_FINAL, flag); }
  public final boolean isFinal() {
    return (access_flags & Constants.ACC_FINAL) != 0;
  }

  public final void isSynchronized(boolean flag) { setFlag(Constants.ACC_SYNCHRONIZED, flag); }
  public final boolean isSynchronized() {
    return (access_flags & Constants.ACC_SYNCHRONIZED) != 0;
  }

  public final void isVolatile(boolean flag) { setFlag(Constants.ACC_VOLATILE, flag); }
  public final boolean isVolatile() {
    return (access_flags & Constants.ACC_VOLATILE) != 0;
  }

  public final void isTransient(boolean flag) { setFlag(Constants.ACC_TRANSIENT, flag); }
  public final boolean isTransient() {
    return (access_flags & Constants.ACC_TRANSIENT) != 0;
  }

  public final void isNative(boolean flag) { setFlag(Constants.ACC_NATIVE, flag); }
  public final boolean isNative() {
    return (access_flags & Constants.ACC_NATIVE) != 0;
  }

  public final void isInterface(boolean flag) { setFlag(Constants.ACC_INTERFACE, flag); }
  public final boolean isInterface() {
    return (access_flags & Constants.ACC_INTERFACE) != 0;
  }

  public final void isAbstract(boolean flag) { setFlag(Constants.ACC_ABSTRACT, flag); }
  public final boolean isAbstract() {
    return (access_flags & Constants.ACC_ABSTRACT) != 0;
  }

  public final void isStrictfp(boolean flag) { setFlag(Constants.ACC_STRICT, flag); }
  public final boolean isStrictfp() {
    return (access_flags & Constants.ACC_STRICT) != 0;
  }
}
