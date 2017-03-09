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
 */

import java.lang.reflect.*;

/**
 * Java interpreter replacement, i.e., wrapper that uses its own ClassLoader
 * to modify/generate classes as they're requested. You can take this as a template
 * for your own applications.<br>
 * Call this wrapper with
 * <pre>java com.sun.org.apache.bcel.internal.util.JavaWrapper &lt;real.class.name&gt; [arguments]</pre>
 * <p>
 * To use your own class loader you can set the "bcel.classloader" system property
 * which defaults to "com.sun.org.apache.bcel.internal.util.ClassLoader", e.g., with
 * <pre>java com.sun.org.apache.bcel.internal.util.JavaWrapper -Dbcel.classloader=foo.MyLoader &lt;real.class.name&gt; [arguments]</pre>
 * </p>
 *
 * <p>
 *  ================================================== ==================。
 * 
 *  该软件包括许多个人代表Apache软件基金会所做的自愿捐款。有关Apache Software Foundation的更多信息,请参阅<http://www.apache.org/>。
 * 
 * 
 * @version $Id: JavaWrapper.java,v 1.3 2007-07-19 04:34:52 ofung Exp $
 * @author  <A HREF="mailto:markus.dahm@berlin.de">M. Dahm</A>
 * @see ClassLoader
 */
public class JavaWrapper {
  private java.lang.ClassLoader loader;

  private static java.lang.ClassLoader getClassLoader() {
    String s = SecuritySupport.getSystemProperty("bcel.classloader");

    if((s == null) || "".equals(s))
      s = "com.sun.org.apache.bcel.internal.util.ClassLoader";

    try {
      return (java.lang.ClassLoader)Class.forName(s).newInstance();
    } catch(Exception e) {
      throw new RuntimeException(e.toString());
    }
  }

  public JavaWrapper(java.lang.ClassLoader loader) {
    this.loader = loader;
  }

  public JavaWrapper() {
    this(getClassLoader());
  }

  /** Runs the _main method of the given class with the arguments passed in argv
   *
   * <p>
   * Java解释器替换,即使用自己的ClassLoader在请求时修改/生成类的包装器。您可以将其作为自己的应用程序的模板。
   * <br>使用<pre> java com.sun.org.apache.bcel.internal.util.JavaWrapper&lt; real.class.name&gt; [arguments
   * ] </pre>。
   * Java解释器替换,即使用自己的ClassLoader在请求时修改/生成类的包装器。您可以将其作为自己的应用程序的模板。
   * <p>
   *  要使用自己的类加载器,您可以设置"bcel.classloader"系统属性,默认为"com.sun.org.apache.bcel.internal.util.ClassLoader",例如,使用<pre>
   *  java com.sun.org .apache.bcel.internal.util.JavaWrapper -Dbcel.classloader = foo.MyLoader&lt; real.c
   * lass.name&gt; [arguments] </pre>。
   * 
   * @param class_name the fully qualified class name
   * @param argv the arguments just as you would pass them directly
   */
  public void runMain(String class_name, String[] argv) throws ClassNotFoundException
  {
    Class   cl    = loader.loadClass(class_name);
    Method method = null;

    try {
      method = cl.getMethod("_main",  new Class[] { argv.getClass() });

      /* Method _main is sane ?
      /* <p>
      /* </p>
      /* 
       */
      int   m = method.getModifiers();
      Class r = method.getReturnType();

      if(!(Modifier.isPublic(m) && Modifier.isStatic(m)) ||
         Modifier.isAbstract(m) || (r != Void.TYPE))
        throw new NoSuchMethodException();
    } catch(NoSuchMethodException no) {
      System.out.println("In class " + class_name +
                         ": public static void _main(String[] argv) is not defined");
      return;
    }

    try {
      method.invoke(null, new Object[] { argv });
    } catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  /** Default _main method used as wrapper, expects the fully qualified class name
   * of the real class as the first argument.
   * <p>
   */
  public static void _main(String[] argv) throws Exception {
    /* Expects class name as first argument, other arguments are by-passed.
    /* <p>
     */
    if(argv.length == 0) {
      System.out.println("Missing class name.");
      return;
    }

    String class_name = argv[0];
    String[] new_argv = new String[argv.length - 1];
    System.arraycopy(argv, 1, new_argv, 0, new_argv.length);

    JavaWrapper wrapper = new JavaWrapper();
    wrapper.runMain(class_name, new_argv);
  }
}
