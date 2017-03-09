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
 * $Id: XRTreeFragSelectWrapper.java,v 1.2.4.1 2005/09/15 02:02:35 jeffsuttor Exp $
 * <p>
 *  $ Id：XRTreeFragSelectWrapper.java,v 1.2.4.1 2005/09/15 02:02:35 jeffsuttor Exp $
 * 
 */
package com.sun.org.apache.xpath.internal.objects;

import com.sun.org.apache.xalan.internal.res.XSLMessages;
import com.sun.org.apache.xml.internal.dtm.DTMIterator;
import com.sun.org.apache.xml.internal.utils.XMLString;
import com.sun.org.apache.xpath.internal.Expression;
import com.sun.org.apache.xpath.internal.XPathContext;
import com.sun.org.apache.xpath.internal.res.XPATHErrorResources;

/**
 * This class makes an select statement act like an result tree fragment.
 * <p>
 *  这个类使一个select语句像一个结果树片段。
 * 
 */
public class XRTreeFragSelectWrapper extends XRTreeFrag implements Cloneable
{
    static final long serialVersionUID = -6526177905590461251L;
  public XRTreeFragSelectWrapper(Expression expr)
  {
    super(expr);
  }

  /**
   * This function is used to fixup variables from QNames to stack frame
   * indexes at stylesheet build time.
   * <p>
   *  此函数用于在样式表构建时将QNames中的变量固定到堆栈帧索引。
   * 
   * 
   * @param vars List of QNames that correspond to variables.  This list
   * should be searched backwards for the first qualified name that
   * corresponds to the variable reference qname.  The position of the
   * QName in the vector from the start of the vector will be its position
   * in the stack frame (but variables above the globalsTop value will need
   * to be offset to the current stack frame).
   */
  public void fixupVariables(java.util.Vector vars, int globalsSize)
  {
    ((Expression)m_obj).fixupVariables(vars, globalsSize);
  }

  /**
   * For support of literal objects in xpaths.
   *
   * <p>
   *  用于支持xpath中的文字对象。
   * 
   * 
   * @param xctxt The XPath execution context.
   *
   * @return the result of executing the select expression
   *
   * @throws javax.xml.transform.TransformerException
   */
  public XObject execute(XPathContext xctxt)
          throws javax.xml.transform.TransformerException
  {
         XObject m_selected;
     m_selected = ((Expression)m_obj).execute(xctxt);
     m_selected.allowDetachToRelease(m_allowRelease);
     if (m_selected.getType() == CLASS_STRING)
       return m_selected;
     else
       return new XString(m_selected.str());
  }

  /**
   * Detaches the <code>DTMIterator</code> from the set which it iterated
   * over, releasing any computational resources and placing the iterator
   * in the INVALID state. After <code>detach</code> has been invoked,
   * calls to <code>nextNode</code> or <code>previousNode</code> will
   * raise a runtime exception.
   *
   * In general, detach should only be called once on the object.
   * <p>
   *  从迭代的集合中分离<code> DTMIterator </code>,释放任何计算资源并将迭代器置于INVALID状态。
   * 调用<code> detach </code>后,对<code> nextNode </code>或<code> previousNode </code>的调用将引发运行时异常。
   * 
   *  一般来说,detach只应该在对象上调用一次。
   * 
   */
  public void detach()
  {
        throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_DETACH_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER, null)); //"detach() not supported by XRTreeFragSelectWrapper!");
  }

  /**
   * Cast result object to a number.
   *
   * <p>
   *  将结果对象转换为数字。
   * 
   * 
   * @return The result tree fragment as a number or NaN
   */
  public double num()
    throws javax.xml.transform.TransformerException
  {

        throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_NUM_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER, null)); //"num() not supported by XRTreeFragSelectWrapper!");
  }


  /**
   * Cast result object to an XMLString.
   *
   * <p>
   *  将结果对象强制转换为XMLString。
   * 
   * 
   * @return The document fragment node data or the empty string.
   */
  public XMLString xstr()
  {
        throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_XSTR_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER, null)); //"xstr() not supported by XRTreeFragSelectWrapper!");
  }

  /**
   * Cast result object to a string.
   *
   * <p>
   *  将结果对象转换为字符串。
   * 
   * 
   * @return The document fragment node data or the empty string.
   */
  public String str()
  {
        throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_STR_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER, null)); //"str() not supported by XRTreeFragSelectWrapper!");
  }

  /**
   * Tell what kind of class this is.
   *
   * <p>
   * 告诉这是什么类的类。
   * 
   * 
   * @return the string type
   */
  public int getType()
  {
    return CLASS_STRING;
  }

  /**
   * Cast result object to a result tree fragment.
   *
   * <p>
   *  将结果对象强制转换到结果树片段。
   * 
   * 
   * @return The document fragment this wraps
   */
  public int rtf()
  {
    throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_RTF_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER, null)); //"rtf() not supported by XRTreeFragSelectWrapper!");
  }

  /**
   * Cast result object to a DTMIterator.
   *
   * <p>
   *  将结果对象转换为DTMIterator。
   * 
   * @return The document fragment as a DTMIterator
   */
  public DTMIterator asNodeIterator()
  {
    throw new RuntimeException(XSLMessages.createXPATHMessage(XPATHErrorResources.ER_RTF_NOT_SUPPORTED_XRTREEFRAGSELECTWRAPPER, null)); //"asNodeIterator() not supported by XRTreeFragSelectWrapper!");
  }

}
