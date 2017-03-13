/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sun.org.apache.bcel.internal;

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

import com.sun.org.apache.bcel.internal.classfile.JavaClass;
import com.sun.org.apache.bcel.internal.util.*;
import java.io.*;

/**
 * The repository maintains informations about class interdependencies, e.g.,
 * whether a class is a sub-class of another. Delegates actual class loading
 * to SyntheticRepository with current class path by default.
 *
 * <p>
 *  存储库维护关于类相互依赖性的信息,例如,类是否是另一个的子类委托实际类加载到具有当前类路径的默认的SyntheticRepository
 * 
 * 
 * @see com.sun.org.apache.bcel.internal.util.Repository
 * @see com.sun.org.apache.bcel.internal.util.SyntheticRepository
 *
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 */
public abstract class Repository {
  private static com.sun.org.apache.bcel.internal.util.Repository _repository =
    SyntheticRepository.getInstance();

  /** @return currently used repository instance
  /* <p>
   */
  public static com.sun.org.apache.bcel.internal.util.Repository getRepository() {
    return _repository;
  }

  /** Set repository instance to be used for class loading
  /* <p>
   */
  public static void setRepository(com.sun.org.apache.bcel.internal.util.Repository rep) {
    _repository = rep;
  }

  /** Lookup class somewhere found on your CLASSPATH, or whereever the
   * repository instance looks for it.
   *
   * <p>
   *  存储库实例查找它
   * 
   * 
   * @return class object for given fully qualified class name, or null
   * if the class could not be found or parsed correctly
   */
  public static JavaClass lookupClass(String class_name) {
    try {
      JavaClass clazz = _repository.findClass(class_name);

      if(clazz == null) {
        return _repository.loadClass(class_name);
      } else {
        return clazz;
      }
    } catch(ClassNotFoundException ex) { return null; }
  }

  /**
   * Try to find class source via getResourceAsStream().
   * <p>
   *  尝试找到类源通过getResourceAsStream()
   * 
   * 
   * @see Class
   * @return JavaClass object for given runtime class
   */
  public static JavaClass lookupClass(Class clazz) {
    try {
      return _repository.loadClass(clazz);
    } catch(ClassNotFoundException ex) { return null; }
  }

  /** @return class file object for given Java class.
  /* <p>
   */
  public static ClassPath.ClassFile lookupClassFile(String class_name) {
    try {
      return ClassPath.SYSTEM_CLASS_PATH.getClassFile(class_name);
    } catch(IOException e) { return null; }
  }

  /** Clear the repository.
  /* <p>
   */
  public static void clearCache() {
    _repository.clear();
  }

  /**
   * Add clazz to repository if there isn't an equally named class already in there.
   *
   * <p>
   *  如果没有一个同名的类已经在那里添加clazz到存储库
   * 
   * 
   * @return old entry in repository
   */
  public static JavaClass addClass(JavaClass clazz) {
    JavaClass old = _repository.findClass(clazz.getClassName());
    _repository.storeClass(clazz);
    return old;
  }

  /**
   * Remove class with given (fully qualified) name from repository.
   * <p>
   *  从存储库中删除具有给定(完全限定)名称的类
   * 
   */
  public static void removeClass(String clazz) {
    _repository.removeClass(_repository.findClass(clazz));
  }

  /**
   * Remove given class from repository.
   * <p>
   *  从存储库中删除给定的类
   * 
   */
  public static void removeClass(JavaClass clazz) {
    _repository.removeClass(clazz);
  }

  /**
  /* <p>
  /* 
   * @return list of super classes of clazz in ascending order, i.e.,
   * Object is always the last element
   */
  public static JavaClass[] getSuperClasses(JavaClass clazz) {
    return clazz.getSuperClasses();
  }

  /**
  /* <p>
  /* 
   * @return list of super classes of clazz in ascending order, i.e.,
   * Object is always the last element. return "null", if class
   * cannot be found.
   */
  public static JavaClass[] getSuperClasses(String class_name) {
    JavaClass jc = lookupClass(class_name);
    return (jc == null? null : getSuperClasses(jc));
  }

  /**
  /* <p>
  /* 
   * @return all interfaces implemented by class and its super
   * classes and the interfaces that those interfaces extend, and so on.
   * (Some people call this a transitive hull).
   */
  public static JavaClass[] getInterfaces(JavaClass clazz) {
    return clazz.getAllInterfaces();
  }

  /**
  /* <p>
  /* 
   * @return all interfaces implemented by class and its super
   * classes and the interfaces that extend those interfaces, and so on
   */
  public static JavaClass[] getInterfaces(String class_name) {
    return getInterfaces(lookupClass(class_name));
  }

  /**
   * Equivalent to runtime "instanceof" operator.
   * <p>
   *  相当于运行时"instanceof"运算符
   * 
   * 
   * @return true, if clazz is an instance of super_class
   */
  public static boolean instanceOf(JavaClass clazz, JavaClass super_class) {
    return clazz.instanceOf(super_class);
  }

  /**
  /* <p>
  /* 
   * @return true, if clazz is an instance of super_class
   */
  public static boolean instanceOf(String clazz, String super_class) {
    return instanceOf(lookupClass(clazz), lookupClass(super_class));
  }

  /**
  /* <p>
  /* 
   * @return true, if clazz is an instance of super_class
   */
  public static boolean instanceOf(JavaClass clazz, String super_class) {
    return instanceOf(clazz, lookupClass(super_class));
  }

  /**
  /* <p>
  /* 
   * @return true, if clazz is an instance of super_class
   */
  public static boolean instanceOf(String clazz, JavaClass super_class) {
    return instanceOf(lookupClass(clazz), super_class);
  }

  /**
  /* <p>
  /* 
   * @return true, if clazz is an implementation of interface inter
   */
  public static boolean implementationOf(JavaClass clazz, JavaClass inter) {
    return clazz.implementationOf(inter);
  }

  /**
  /* <p>
  /* 
   * @return true, if clazz is an implementation of interface inter
   */
  public static boolean implementationOf(String clazz, String inter) {
    return implementationOf(lookupClass(clazz), lookupClass(inter));
  }

  /**
  /* <p>
  /* 
   * @return true, if clazz is an implementation of interface inter
   */
  public static boolean implementationOf(JavaClass clazz, String inter) {
    return implementationOf(clazz, lookupClass(inter));
  }

  /**
  /* <p>
  /* 
   * @return true, if clazz is an implementation of interface inter
   */
  public static boolean implementationOf(String clazz, JavaClass inter) {
    return implementationOf(lookupClass(clazz), inter);
  }
}
