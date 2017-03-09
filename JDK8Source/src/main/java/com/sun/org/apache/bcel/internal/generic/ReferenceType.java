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
import com.sun.org.apache.bcel.internal.Repository;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;

/**
 * Super class for object and array types.
 *
 * <p>
 *  对象和数组类型的超类。
 * 
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class ReferenceType extends Type {
  protected ReferenceType(byte t, String s) {
    super(t, s);
  }

  /** Class is non-abstract but not instantiable from the outside
  /* <p>
   */
  ReferenceType() {
    super(Constants.T_OBJECT, "<null object>");
  }

  /**
   * Return true iff this type is castable to another type t as defined in
   * the JVM specification.  The case where this is Type.NULL is not
   * defined (see the CHECKCAST definition in the JVM specification).
   * However, because e.g. CHECKCAST doesn't throw a
   * ClassCastException when casting a null reference to any Object,
   * true is returned in this case.
   * <p>
   * 如果此类型可转换为JVM规范中定义的另一个类型t,则返回true。未定义此为Type.NULL的情况(请参阅JVM规范中的CHECKCAST定义)。
   * 然而,当转换对任何对象的空引用时,CHECKCAST不抛出ClassCastException,在这种情况下返回true。
   * 
   */
  public boolean isCastableTo(Type t) {
    if (this.equals(Type.NULL))
      return true;              // If this is ever changed in isAssignmentCompatible()

    return isAssignmentCompatibleWith(t);
    /* Yes, it's true: It's the same definition.
     * See vmspec2 AASTORE / CHECKCAST definitions.
     * <p>
     *  请参见vmspec2 AASTORE / CHECKCAST定义。
     * 
     */
  }

  /**
   * Return true iff this is assignment compatible with another type t
   * as defined in the JVM specification; see the AASTORE definition
   * there.
   * <p>
   *  返回true iff这是与JVM规范中定义的另一个类型t兼容的赋值;看到AASTORE的定义。
   * 
   */
  public boolean isAssignmentCompatibleWith(Type t) {
    if (!(t instanceof ReferenceType))
      return false;

    ReferenceType T = (ReferenceType) t;

    if (this.equals(Type.NULL))
      return true; // This is not explicitely stated, but clear. Isn't it?

    /* If this is a class type then
    /* <p>
     */
    if ((this instanceof ObjectType) && (((ObjectType) this).referencesClass())) {
      /* If T is a class type, then this must be the same class as T,
         or this must be a subclass of T;
      /* <p>
      /*  或者这必须是T的子类;
      /* 
      */
      if ((T instanceof ObjectType) && (((ObjectType) T).referencesClass())) {
        if (this.equals(T))
          return true;

        if (Repository.instanceOf(((ObjectType) this).getClassName(),
                                  ((ObjectType) T).getClassName()))
          return true;
      }

      /* If T is an interface type, this must implement interface T.
      /* <p>
       */
      if ((T instanceof ObjectType) && (((ObjectType) T).referencesInterface())) {
        if (Repository.implementationOf(((ObjectType) this).getClassName(),
                                        ((ObjectType) T).getClassName()))
          return true;
      }
    }

    /* If this is an interface type, then:
    /* <p>
     */
    if ((this instanceof ObjectType) && (((ObjectType) this).referencesInterface())) {
      /* If T is a class type, then T must be Object (2.4.7).
      /* <p>
       */
      if ((T instanceof ObjectType) && (((ObjectType) T).referencesClass())) {
        if (T.equals(Type.OBJECT)) return true;
      }

      /* If T is an interface type, then T must be the same interface
       * as this or a superinterface of this (2.13.2).
       * <p>
       *  作为这个或这个的超级接口(2.13.2)。
       * 
       */
      if ((T instanceof ObjectType) && (((ObjectType) T).referencesInterface())) {
        if (this.equals(T)) return true;
        if (Repository.implementationOf(((ObjectType) this).getClassName(),
                                        ((ObjectType) T).getClassName()))
          return true;
      }
    }

    /* If this is an array type, namely, the type SC[], that is, an
     * array of components of type SC, then:
     * <p>
     *  数组SC类型的组件,则：
     * 
     */
    if (this instanceof ArrayType) {
      /* If T is a class type, then T must be Object (2.4.7).
      /* <p>
       */
      if ((T instanceof ObjectType) && (((ObjectType) T).referencesClass())) {
        if (T.equals(Type.OBJECT)) return true;
      }

      /* If T is an array type TC[], that is, an array of components
       * of type TC, then one of the following must be true:
       * <p>
       *  的类型TC,则以下之一必须为真：
       * 
       */
      if (T instanceof ArrayType) {
        /* TC and SC are the same primitive type (2.4.1).
        /* <p>
         */
        Type sc = ((ArrayType) this).getElementType();
        Type tc = ((ArrayType) this).getElementType();

        if (sc instanceof BasicType && tc instanceof BasicType && sc.equals(tc))
          return true;

        /* TC and SC are reference types (2.4.6), and type SC is
         * assignable to TC by these runtime rules.
         * <p>
         *  通过这些运行时规则可分配给TC。
         * 
         */
        if (tc instanceof ReferenceType && sc instanceof ReferenceType &&
            ((ReferenceType) sc).isAssignmentCompatibleWith((ReferenceType) tc))
          return true;
      }

      /* If T is an interface type, T must be one of the interfaces implemented by arrays (2.15). */
      // TODO: Check if this is still valid or find a way to dynamically find out which
      // interfaces arrays implement. However, as of the JVM specification edition 2, there
      // are at least two different pages where assignment compatibility is defined and
      // on one of them "interfaces implemented by arrays" is exchanged with "'Cloneable' or
      // 'java.io.Serializable'"
      if ((T instanceof ObjectType) && (((ObjectType) T).referencesInterface())) {
        for (int ii = 0; ii < Constants.INTERFACES_IMPLEMENTED_BY_ARRAYS.length; ii++) {
          if (T.equals(new ObjectType(Constants.INTERFACES_IMPLEMENTED_BY_ARRAYS[ii]))) return true;
        }
      }
    }
    return false; // default.
  }

  /**
   * This commutative operation returns the first common superclass (narrowest ReferenceType
   * referencing a class, not an interface).
   * If one of the types is a superclass of the other, the former is returned.
   * If "this" is Type.NULL, then t is returned.
   * If t is Type.NULL, then "this" is returned.
   * If "this" equals t ['this.equals(t)'] "this" is returned.
   * If "this" or t is an ArrayType, then Type.OBJECT is returned;
   * unless their dimensions match. Then an ArrayType of the same
   * number of dimensions is returned, with its basic type being the
   * first common super class of the basic types of "this" and t.
   * If "this" or t is a ReferenceType referencing an interface, then Type.OBJECT is returned.
   * If not all of the two classes' superclasses cannot be found, "null" is returned.
   * See the JVM specification edition 2, "4.9.2 The Bytecode Verifier".
   * <p>
   * 此交换操作返回第一个公共超类(最窄的ReferenceType引用类,而不是接口)。如果其中一个类型是另一个的超类,则返回前者。如果"this"是Type.NULL,则返回t。
   * 如果t是Type.NULL,则返回"this"。如果"this"等于t ['this.equals(t)'],则返回"this"。
   * 如果"this"或t是一个ArrayType,则返回Type.OBJECT;除非它们的尺寸匹配。然后返回相同维数的ArrayType,其基本类型是"this"和t的基本类型的第一个公共超类。
   * 如果"this"或t是引用接口的ReferenceType,则返回Type.OBJECT。如果不是所有的两个类的超类都找不到,则返回"null"。请参阅JVM规范版本2"4.9.2字节码验证程序"。
   * 
   */
  public ReferenceType getFirstCommonSuperclass(ReferenceType t) {
    if (this.equals(Type.NULL)) return t;
    if (t.equals(Type.NULL)) return this;
    if (this.equals(t)) return this;
    /*
     * TODO: Above sounds a little arbitrary. On the other hand, there is
     * no object referenced by Type.NULL so we can also say all the objects
     * referenced by Type.NULL were derived from java.lang.Object.
     * However, the Java Language's "instanceof" operator proves us wrong:
     * "null" is not referring to an instance of java.lang.Object :)
     * <p>
     *  TODO：以上听起来有点随意。另一方面,没有被Type.NULL引用的对象,所以我们也可以说Type.NULL引用的所有对象都是从java.lang.Object派生的。
     * 但是,Java语言的"instanceof"运算符证明我们错了："null"不是指java.lang.Object的一个实例:)。
     * 
     */

    /* This code is from a bug report by Konstantin Shagin <konst@cs.technion.ac.il> */

    if ((this instanceof ArrayType) && (t instanceof ArrayType)) {
      ArrayType arrType1 = (ArrayType) this;
      ArrayType arrType2 = (ArrayType) t;
      if (
          (arrType1.getDimensions() == arrType2.getDimensions()) &&
          arrType1.getBasicType() instanceof ObjectType &&
          arrType2.getBasicType() instanceof ObjectType) {
        return new ArrayType(
                             ((ObjectType) arrType1.getBasicType()).getFirstCommonSuperclass((ObjectType) arrType2.getBasicType()),
                             arrType1.getDimensions()
                             );

      }
    }

    if ((this instanceof ArrayType) || (t instanceof ArrayType))
      return Type.OBJECT;
    // TODO: Is there a proof of OBJECT being the direct ancestor of every ArrayType?

    if (((this instanceof ObjectType) && ((ObjectType) this).referencesInterface()) ||
        ((t instanceof ObjectType) && ((ObjectType) t).referencesInterface()))
      return Type.OBJECT;
    // TODO: The above line is correct comparing to the vmspec2. But one could
    // make class file verification a bit stronger here by using the notion of
    // superinterfaces or even castability or assignment compatibility.


    // this and t are ObjectTypes, see above.
    ObjectType thiz = (ObjectType) this;
    ObjectType other = (ObjectType) t;
    JavaClass[] thiz_sups = Repository.getSuperClasses(thiz.getClassName());
    JavaClass[] other_sups = Repository.getSuperClasses(other.getClassName());

    if ((thiz_sups == null) || (other_sups == null)) {
      return null;
    }

    // Waaahh...
    JavaClass[] this_sups = new JavaClass[thiz_sups.length + 1];
    JavaClass[] t_sups = new JavaClass[other_sups.length + 1];
    System.arraycopy(thiz_sups, 0, this_sups, 1, thiz_sups.length);
    System.arraycopy(other_sups, 0, t_sups, 1, other_sups.length);
    this_sups[0] = Repository.lookupClass(thiz.getClassName());
    t_sups[0] = Repository.lookupClass(other.getClassName());

    for (int i = 0; i < t_sups.length; i++) {
      for (int j = 0; j < this_sups.length; j++) {
        if (this_sups[j].equals(t_sups[i])) return new ObjectType(this_sups[j].getClassName());
      }
    }

    // Huh? Did you ask for Type.OBJECT's superclass??
    return null;
  }

  /**
   * This commutative operation returns the first common superclass (narrowest ReferenceType
   * referencing a class, not an interface).
   * If one of the types is a superclass of the other, the former is returned.
   * If "this" is Type.NULL, then t is returned.
   * If t is Type.NULL, then "this" is returned.
   * If "this" equals t ['this.equals(t)'] "this" is returned.
   * If "this" or t is an ArrayType, then Type.OBJECT is returned.
   * If "this" or t is a ReferenceType referencing an interface, then Type.OBJECT is returned.
   * If not all of the two classes' superclasses cannot be found, "null" is returned.
   * See the JVM specification edition 2, "4.9.2 The Bytecode Verifier".
   *
   * <p>
   * 此交换操作返回第一个公共超类(最窄的ReferenceType引用类,而不是接口)。如果其中一个类型是另一个的超类,则返回前者。如果"this"是Type.NULL,则返回t。
   * 如果t是Type.NULL,则返回"this"。如果"this"等于t ['this.equals(t)'],则返回"this"。
   * 如果"this"或t是一个ArrayType,则返回Type.OBJECT。如果"this"或t是引用接口的ReferenceType,则返回Type.OBJECT。
   * 如果不是所有的两个类的超类都找不到,则返回"null"。请参阅JVM规范版本2"4.9.2字节码验证程序"。
   * 
   * 
   * @deprecated use getFirstCommonSuperclass(ReferenceType t) which has
   *             slightly changed semantics.
   */
  public ReferenceType firstCommonSuperclass(ReferenceType t) {
    if (this.equals(Type.NULL)) return t;
    if (t.equals(Type.NULL)) return this;
    if (this.equals(t)) return this;
    /*
     * TODO: Above sounds a little arbitrary. On the other hand, there is
     * no object referenced by Type.NULL so we can also say all the objects
     * referenced by Type.NULL were derived from java.lang.Object.
     * However, the Java Language's "instanceof" operator proves us wrong:
     * "null" is not referring to an instance of java.lang.Object :)
     * <p>
     */

    if ((this instanceof ArrayType) || (t instanceof ArrayType))
      return Type.OBJECT;
    // TODO: Is there a proof of OBJECT being the direct ancestor of every ArrayType?

    if (((this instanceof ObjectType) && ((ObjectType) this).referencesInterface()) ||
        ((t instanceof ObjectType) && ((ObjectType) t).referencesInterface()))
      return Type.OBJECT;
    // TODO: The above line is correct comparing to the vmspec2. But one could
    // make class file verification a bit stronger here by using the notion of
    // superinterfaces or even castability or assignment compatibility.


    // this and t are ObjectTypes, see above.
    ObjectType thiz = (ObjectType) this;
    ObjectType other = (ObjectType) t;
    JavaClass[] thiz_sups = Repository.getSuperClasses(thiz.getClassName());
    JavaClass[] other_sups = Repository.getSuperClasses(other.getClassName());

    if ((thiz_sups == null) || (other_sups == null)) {
      return null;
    }

    // Waaahh...
    JavaClass[] this_sups = new JavaClass[thiz_sups.length + 1];
    JavaClass[] t_sups = new JavaClass[other_sups.length + 1];
    System.arraycopy(thiz_sups, 0, this_sups, 1, thiz_sups.length);
    System.arraycopy(other_sups, 0, t_sups, 1, other_sups.length);
    this_sups[0] = Repository.lookupClass(thiz.getClassName());
    t_sups[0] = Repository.lookupClass(other.getClassName());

    for (int i = 0; i < t_sups.length; i++) {
      for (int j = 0; j < this_sups.length; j++) {
        if (this_sups[j].equals(t_sups[i])) return new ObjectType(this_sups[j].getClassName());
      }
    }

    // Huh? Did you ask for Type.OBJECT's superclass??
    return null;
  }
}
