/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.sun.org.apache.bcel.internal.util;

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

import java.io.*;

import java.util.Map;
import java.util.HashMap;

import com.sun.org.apache.bcel.internal.classfile.*;

/**
 * This repository is used in situations where a Class is created
 * outside the realm of a ClassLoader. Classes are loaded from
 * the file systems using the paths specified in the given
 * class path. By default, this is the value returned by
 * ClassPath.getClassPath().
 * <br>
 * It is designed to be used as a singleton, however it
 * can also be used with custom classpaths.
 *
/**
 * Abstract definition of a class repository. Instances may be used
 * to load classes from different sources and may be used in the
 * Repository.setRepository method.
 *
 * <p>
 * 
 * 
 * @see com.sun.org.apache.bcel.internal.Repository
 *
 * @author <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @author David Dixon-Peugh
 */
public class SyntheticRepository implements Repository {
  private static final String DEFAULT_PATH = ClassPath.getClassPath();

  private static HashMap _instances = new HashMap(); // CLASSPATH X REPOSITORY

  private ClassPath _path = null;
  private HashMap   _loadedClasses = new HashMap(); // CLASSNAME X JAVACLASS

  private SyntheticRepository(ClassPath path) {
    _path = path;
  }

  public static SyntheticRepository getInstance() {
    return getInstance(ClassPath.SYSTEM_CLASS_PATH);
  }

  public static SyntheticRepository getInstance(ClassPath classPath) {
    SyntheticRepository rep = (SyntheticRepository)_instances.get(classPath);

    if(rep == null) {
      rep = new SyntheticRepository(classPath);
      _instances.put(classPath, rep);
    }

    return rep;
  }

  /**
   * Store a new JavaClass instance into this Repository.
   * <p>
   *  此存储库用于在ClassLoader领域之外创建类的情况。使用给定类路径中指定的路径从文件系统加载类。默认情况下,这是由ClassPath.getClassPath()返回的值。
   * <br>
   * 它被设计为用作单例,但它也可以与自定义类路径一起使用。
   * 
   *  / **类库的抽象定义。实例可用于从不同来源加载类,并可在Repository.setRepository方法中使用。
   * 
   */
  public void storeClass(JavaClass clazz) {
    _loadedClasses.put(clazz.getClassName(), clazz);
    clazz.setRepository(this);
 }

  /**
   * Remove class from repository
   * <p>
   *  将新的JavaClass实例存储到此存储库。
   * 
   */
  public void removeClass(JavaClass clazz) {
    _loadedClasses.remove(clazz.getClassName());
  }

  /**
   * Find an already defined (cached) JavaClass object by name.
   * <p>
   *  从存储库中删除类
   * 
   */
  public JavaClass findClass(String className) {
    return (JavaClass)_loadedClasses.get(className);
  }

  /**
   * Load a JavaClass object for the given class name using
   * the CLASSPATH environment variable.
   * <p>
   *  按名称查找已定义(缓存)的JavaClass对象。
   * 
   */
  public JavaClass loadClass(String className)
    throws ClassNotFoundException
  {
    if(className == null || className.equals("")) {
      throw new IllegalArgumentException("Invalid class name " + className);
    }

    className = className.replace('/', '.'); // Just in case, canonical form

    try {
      return loadClass(_path.getInputStream(className), className);
    } catch(IOException e) {
      throw new ClassNotFoundException("Exception while looking for class " +
                                       className + ": " + e.toString());
    }
  }

  /**
   * Try to find class source via getResourceAsStream().
   * <p>
   *  使用CLASSPATH环境变量为给定类名称加载JavaClass对象。
   * 
   * 
   * @see Class
   * @return JavaClass object for given runtime class
   */
  public JavaClass loadClass(Class clazz) throws ClassNotFoundException {
    String className = clazz.getName();
    String name      = className;
    int    i         = name.lastIndexOf('.');

    if(i > 0) {
      name = name.substring(i + 1);
    }

    return loadClass(clazz.getResourceAsStream(name + ".class"), className);
  }

  private JavaClass loadClass(InputStream is, String className)
    throws ClassNotFoundException
  {
    JavaClass clazz = findClass(className);

    if(clazz != null) {
      return clazz;
    }

    try {
      if(is != null) {
        ClassParser parser = new ClassParser(is, className);
        clazz = parser.parse();

        storeClass(clazz);

        return clazz;
      }
    } catch(IOException e) {
      throw new ClassNotFoundException("Exception while looking for class " +
                                       className + ": " + e.toString());
    }

    throw new ClassNotFoundException("SyntheticRepository could not load " +
                                     className);
  }

  /** Clear all entries from cache.
  /* <p>
  /*  尝试通过getResourceAsStream()查找类源。
  /* 
   */
  public void clear() {
    _loadedClasses.clear();
  }
}
