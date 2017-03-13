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

import com.sun.org.apache.bcel.internal.Constants;
import com.sun.org.apache.bcel.internal.classfile.*;
import java.util.ArrayList;

/**
 * Abstract super class for all possible java types, namely basic types
 * such as int, object types like String and array types, e.g. int[]
 *
 * <p>
 * 
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class Type implements java.io.Serializable {
  protected byte   type;
  protected String signature; // signature for the type

  /** Predefined constants
  /* <p>
  /*  对所有可能的java类型的抽象超类,即基本类型,如int,对象类型,如String和数组类型,例如int []
  /* 
   */
  public static final BasicType     VOID         = new BasicType(Constants.T_VOID);
  public static final BasicType     BOOLEAN      = new BasicType(Constants.T_BOOLEAN);
  public static final BasicType     INT          = new BasicType(Constants.T_INT);
  public static final BasicType     SHORT        = new BasicType(Constants.T_SHORT);
  public static final BasicType     BYTE         = new BasicType(Constants.T_BYTE);
  public static final BasicType     LONG         = new BasicType(Constants.T_LONG);
  public static final BasicType     DOUBLE       = new BasicType(Constants.T_DOUBLE);
  public static final BasicType     FLOAT        = new BasicType(Constants.T_FLOAT);
  public static final BasicType     CHAR         = new BasicType(Constants.T_CHAR);
  public static final ObjectType    OBJECT       = new ObjectType("java.lang.Object");
  public static final ObjectType    STRING       = new ObjectType("java.lang.String");
  public static final ObjectType    STRINGBUFFER = new ObjectType("java.lang.StringBuffer");
  public static final ObjectType    THROWABLE    = new ObjectType("java.lang.Throwable");
  public static final Type[]        NO_ARGS      = new Type[0];
  public static final ReferenceType NULL         = new ReferenceType(){};
  public static final Type          UNKNOWN      = new Type(Constants.T_UNKNOWN,
                                                            "<unknown object>"){};

  protected Type(byte t, String s) {
    type      = t;
    signature = s;
  }

  /**
  /* <p>
  /* 
   * @return signature for given type.
   */
  public String getSignature() { return signature; }

  /**
  /* <p>
  /* 
   * @return type as defined in Constants
   */
  public byte getType() { return type; }

  /**
  /* <p>
  /* 
   * @return stack size of this type (2 for long and double, 0 for void, 1 otherwise)
   */
  public int getSize() {
    switch(type) {
    case Constants.T_DOUBLE:
    case Constants.T_LONG: return 2;
    case Constants.T_VOID: return 0;
    default:     return 1;
    }
  }

  /**
  /* <p>
  /* 
   * @return Type string, e.g. `int[]'
   */
  public String toString() {
    return ((this.equals(Type.NULL) || (type >= Constants.T_UNKNOWN)))? signature :
      Utility.signatureToString(signature, false);
  }

  /**
   * Convert type to Java method signature, e.g. int[] f(java.lang.String x)
   * becomes (Ljava/lang/String;)[I
   *
   * <p>
   * 
   * @param return_type what the method returns
   * @param arg_types what are the argument types
   * @return method signature for given type(s).
   */
  public static String getMethodSignature(Type return_type, Type[] arg_types) {
    StringBuffer buf = new StringBuffer("(");
    int length = (arg_types == null)? 0 : arg_types.length;

    for(int i=0; i < length; i++)
      buf.append(arg_types[i].getSignature());

    buf.append(')');
    buf.append(return_type.getSignature());

    return buf.toString();
  }

  private static int consumed_chars=0; // Remember position in string, see getArgumentTypes

  /**
   * Convert signature to a Type object.
   * <p>
   *  将类型转换为Java方法签名,例如int [] f(javalangString x)变成(Ljava / lang / String;)[I
   * 
   * 
   * @param signature signature string such as Ljava/lang/String;
   * @return type object
   */
  public static final Type getType(String signature)
    throws StringIndexOutOfBoundsException
  {
    byte type = Utility.typeOfSignature(signature);

    if(type <= Constants.T_VOID) {
      consumed_chars = 1;
      return BasicType.getType(type);
    } else if(type == Constants.T_ARRAY) {
      int dim=0;
      do { // Count dimensions
        dim++;
      } while(signature.charAt(dim) == '[');

      // Recurse, but just once, if the signature is ok
      Type t = getType(signature.substring(dim));

      consumed_chars += dim; // update counter

      return new ArrayType(t, dim);
    } else { // type == T_REFERENCE
      int index = signature.indexOf(';'); // Look for closing `;'

      if(index < 0)
        throw new ClassFormatException("Invalid signature: " + signature);

      consumed_chars = index + 1; // "Lblabla;" `L' and `;' are removed

      return new ObjectType(signature.substring(1, index).replace('/', '.'));
    }
  }

  /**
   * Convert return value of a method (signature) to a Type object.
   *
   * <p>
   *  将签名转换为Type对象
   * 
   * 
   * @param signature signature string such as (Ljava/lang/String;)V
   * @return return type
   */
  public static Type getReturnType(String signature) {
    try {
      // Read return type after `)'
      int index = signature.lastIndexOf(')') + 1;
      return getType(signature.substring(index));
    } catch(StringIndexOutOfBoundsException e) { // Should never occur
      throw new ClassFormatException("Invalid method signature: " + signature);
    }
  }

  /**
   * Convert arguments of a method (signature) to an array of Type objects.
   * <p>
   *  将方法(签名)的返回值转换为Type对象
   * 
   * 
   * @param signature signature string such as (Ljava/lang/String;)V
   * @return array of argument types
   */
  public static Type[] getArgumentTypes(String signature) {
    ArrayList vec = new ArrayList();
    int       index;
    Type[]     types;

    try { // Read all declarations between for `(' and `)'
      if(signature.charAt(0) != '(')
        throw new ClassFormatException("Invalid method signature: " + signature);

      index = 1; // current string position

      while(signature.charAt(index) != ')') {
        vec.add(getType(signature.substring(index)));
        index += consumed_chars; // update position
      }
    } catch(StringIndexOutOfBoundsException e) { // Should never occur
      throw new ClassFormatException("Invalid method signature: " + signature);
    }

    types = new Type[vec.size()];
    vec.toArray(types);
    return types;
  }

  /** Convert runtime java.lang.Class to BCEL Type object.
  /* <p>
  /*  将方法(签名)的参数转换为Type对象的数组
  /* 
  /* 
   * @param cl Java class
   * @return corresponding Type object
   */
  public static Type getType(java.lang.Class cl) {
    if(cl == null) {
      throw new IllegalArgumentException("Class must not be null");
    }

    /* That's an amzingly easy case, because getName() returns
     * the signature. That's what we would have liked anyway.
     * <p>
     */
    if(cl.isArray()) {
      return getType(cl.getName());
    } else if(cl.isPrimitive()) {
      if(cl == Integer.TYPE) {
        return INT;
      } else if(cl == Void.TYPE) {
        return VOID;
      } else if(cl == Double.TYPE) {
        return DOUBLE;
      } else if(cl == Float.TYPE) {
        return FLOAT;
      } else if(cl == Boolean.TYPE) {
        return BOOLEAN;
      } else if(cl == Byte.TYPE) {
        return BYTE;
      } else if(cl == Short.TYPE) {
        return SHORT;
      } else if(cl == Byte.TYPE) {
        return BYTE;
      } else if(cl == Long.TYPE) {
        return LONG;
      } else if(cl == Character.TYPE) {
        return CHAR;
      } else {
        throw new IllegalStateException("Ooops, what primitive type is " + cl);
      }
    } else { // "Real" class
      return new ObjectType(cl.getName());
    }
  }

  public static String getSignature(java.lang.reflect.Method meth) {
    StringBuffer sb = new StringBuffer("(");
    Class[] params = meth.getParameterTypes(); // avoid clone

    for(int j = 0; j < params.length; j++) {
      sb.append(getType(params[j]).getSignature());
    }

    sb.append(")");
    sb.append(getType(meth.getReturnType()).getSignature());
    return sb.toString();
  }
}
