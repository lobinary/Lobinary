/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有1999-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: FuncLoader.java,v 1.1.2.1 2005/08/01 01:30:35 jeffsuttor Exp $
 * <p>
 *  $ Id：FuncLoader.java,v 1.1.2.1 2005/08/01 01:30:35 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.compiler;

import javax.xml.transform.TransformerException;

import com.sun.org.apache.xpath.internal.functions.Function;
import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
import com.sun.org.apache.xalan.internal.utils.ConfigurationError;

/**
 * Lazy load of functions into the function table as needed, so we don't
 * have to load all the functions allowed in XPath and XSLT on startup.
 * @xsl.usage advanced
 * <p>
 *  根据需要将函数的延迟加载到函数表中,因此我们不必在启动时加载XPath和XSLT中允许的所有函数。 @ xsl.usage advanced
 * 
 */
public class FuncLoader
{

  /** The function ID, which may correspond to one of the FUNC_XXX values
   *  found in {@link com.sun.org.apache.xpath.internal.compiler.FunctionTable}, but may
   * <p>
   *  在{@link com.sun.org.apache.xpath.internal.compiler.FunctionTable}中找到,但可能
   * 
   * 
   *  be a value installed by an external module.  */
  private int m_funcID;

  /** The class name of the function.  Must not be null.   */
  private String m_funcName;

  /**
   * Get the local class name of the function class.  If function name does
   * not have a '.' in it, it is assumed to be relative to
   * 'com.sun.org.apache.xpath.internal.functions'.
   *
   * <p>
   *  获取函数类的本地类名。如果函数名没有'。'在它,它被假定是相对于'com.sun.org.apache.xpath.internal.functions'。
   * 
   * 
   * @return The class name of the {com.sun.org.apache.xpath.internal.functions.Function} class.
   */
  public String getName()
  {
    return m_funcName;
  }

  /**
   * Construct a function loader
   *
   * <p>
   *  构造函数加载器
   * 
   * 
   * @param funcName The class name of the {com.sun.org.apache.xpath.internal.functions.Function}
   *             class, which, if it does not have a '.' in it, is assumed to
   *             be relative to 'com.sun.org.apache.xpath.internal.functions'.
   * @param funcID  The function ID, which may correspond to one of the FUNC_XXX
   *    values found in {@link com.sun.org.apache.xpath.internal.compiler.FunctionTable}, but may
   *    be a value installed by an external module.
   */
  public FuncLoader(String funcName, int funcID)
  {

    super();

    m_funcID = funcID;
    m_funcName = funcName;
  }

  /**
   * Get a Function instance that this instance is liaisoning for.
   *
   * <p>
   *  获取该实例与之联系的Function实例。
   * 
   * @return non-null reference to Function derivative.
   *
   * @throws javax.xml.transform.TransformerException if ClassNotFoundException,
   *    IllegalAccessException, or InstantiationException is thrown.
   */
  Function getFunction() throws TransformerException
  {
    try
    {
      String className = m_funcName;
      if (className.indexOf(".") < 0) {
        className = "com.sun.org.apache.xpath.internal.functions." + className;
      }
      //hack for loading only built-in function classes.
      String subString = className.substring(0,className.lastIndexOf('.'));
      if(!(subString.equals ("com.sun.org.apache.xalan.internal.templates") ||
           subString.equals ("com.sun.org.apache.xpath.internal.functions"))) {
            throw new TransformerException("Application can't install his own xpath function.");
      }

      return (Function) ObjectFactory.newInstance(className, true);

    }
    catch (ConfigurationError e)
    {
      throw new TransformerException(e.getException());
    }
  }
}
