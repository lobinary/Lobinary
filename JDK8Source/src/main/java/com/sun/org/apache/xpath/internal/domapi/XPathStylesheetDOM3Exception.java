/***** Lobxxx Translate Finished ******/
/*
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

/*
 *
 *
 *
 *
 *
 * Copyright (c) 2002 World Wide Web Consortium,
 * (Massachusetts Institute of Technology, Institut National de
 * Recherche en Informatique et en Automatique, Keio University). All
 * Rights Reserved. This program is distributed under the W3C's Software
 * Intellectual Property License. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.
 * See W3C License http://www.w3.org/Consortium/Legal/ for more details.
 * <p>
 *  版权所有(c)2002万维网联盟,(马萨诸塞理工学院,庆应义藩大学信息自动化研究所)。版权所有。该程序根据W3C的软件知识产权许可证分发。
 * 这个程序是分发的,希望它将是有用的,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证。有关详细信息,请参阅W3C许可证http://www.w3.org/Consortium/Legal/。
 * 
 */

package com.sun.org.apache.xpath.internal.domapi;

import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;

/**
 *
 * A new exception to add support for DOM Level 3 XPath API.
 * This class is needed to throw a org.w3c.dom.DOMException with proper error code in
 * createExpression method of XPathEvaluatorImpl (a DOM Level 3 class).
 *
 * This class extends TransformerException because the error message includes information
 * about where the XPath problem is in the stylesheet as well as the XPath expression itself.
 *
 * @xsl.usage internal
 * <p>
 *  添加对DOM Level 3 XPath API的支持的新异常。
 * 这个类需要抛出一个org.w3c.dom.DOMException与正确的错误代码在XPathEvaluatorImpl(DOM级别3类)的createExpression方法。
 * 
 *  此类扩展了TransformerException,因为错误消息包括有关样式表中XPath问题的位置以及XPath表达式本身的信息。
 */
final public class XPathStylesheetDOM3Exception extends TransformerException {
        public XPathStylesheetDOM3Exception(String msg, SourceLocator arg1)
        {
                super(msg, arg1);
        }
}
