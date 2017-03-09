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
 * $Id: Constants.java,v 1.2.4.1 2005/09/15 08:15:37 suresh_emailid Exp $
 * <p>
 *  $ Id：Constants.java,v 1.2.4.1 2005/09/15 08:15:37 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

/**
 * Primary constants used by the XSLT Processor
 * @xsl.usage advanced
 * <p>
 *  XSLT Processor @ xsl.usage使用的主常数
 * 
 */
public class Constants
{

  /**
   * Mnemonics for standard XML Namespace URIs, as Java Strings:
   * <ul>
   * <li>S_XMLNAMESPACEURI (http://www.w3.org/XML/1998/namespace) is the
   * URI permanantly assigned to the "xml:" prefix. This is used for some
   * features built into the XML specification itself, such as xml:space
   * and xml:lang. It was defined by the W3C's XML Namespaces spec.</li>
   * <li>S_XSLNAMESPACEURL (http://www.w3.org/1999/XSL/Transform) is the
   * URI which indicates that a name may be an XSLT directive. In most
   * XSLT stylesheets, this is bound to the "xsl:" prefix. It's defined
   * by the W3C's XSLT Recommendation.</li>
   * <li>S_OLDXSLNAMESPACEURL (http://www.w3.org/XSL/Transform/1.0) was
   * used in early prototypes of XSLT processors for much the same purpose
   * as S_XSLNAMESPACEURL. It is now considered obsolete, and the version
   * of XSLT which it signified is not fully compatable with the final
   * XSLT Recommendation, so what it really signifies is a badly obsolete
   * stylesheet.</li>
   * <p>
   *  标准XML命名空间URI的助记符,如Java字符串：
   * <ul>
   * <li> S_XMLNAMESPACEURI(http://www.w3.org/XML/1998/namespace)是永久分配给"xml："前缀的URI。
   * 这用于XML规范本身内置的一些功能,例如xml：space和xml：lang。它由W3C的XML命名空间规范定义。
   * </li> <li> S_XSLNAMESPACEURL(http://www.w3.org/1999/XSL/Transform)是一个URI,表示一个名称可能是XSLT指令。
   * 在大多数XSLT样式表中,这是绑定到"xsl："前缀。
   *  </li> <li> S_OLDXSLNAMESPACEURL(http://www.w3.org/XSL/Transform/1.0)在XSLT处理器的早期原型中使用,与S_XSLNAMESPACE
   * URL的用途大同小异。
   * 在大多数XSLT样式表中,这是绑定到"xsl："前缀。它现在被认为是过时的,它所表示的XSLT版本与最终的XSLT建议不完全兼容,所以它真正意味着是一个过时的样式表。</li>。
   * 
   * 
   * </ul> */
  public static final String
        S_XMLNAMESPACEURI = "http://www.w3.org/XML/1998/namespace",
        S_XSLNAMESPACEURL = "http://www.w3.org/1999/XSL/Transform",
        S_OLDXSLNAMESPACEURL = "http://www.w3.org/XSL/Transform/1.0";

  /** Authorship mnemonics, as Java Strings. Not standardized,
   * as far as I know.
   * <ul>
   * <li>S_VENDOR -- the name of the organization/individual who published
   * this XSLT processor. </li>
   * <li>S_VENDORURL -- URL where one can attempt to retrieve more
   * information about this publisher and product.</li>
   * </ul>
   * <p>
   *  我所知道的。
   * <ul>
   *  <li> S_VENDOR  - 发布此XSLT处理器的组织/个人的名称。 </li> <li> S_VENDORURL  - 可以尝试检索有关此发布商和产品的更多信息的网址。</li>
   * </ul>
   */
  public static final String
        S_VENDOR = "Apache Software Foundation",
        S_VENDORURL = "http://xml.apache.org";

  /** S_BUILTIN_EXTENSIONS_URL is a mnemonic for the XML Namespace
   *(http://xml.apache.org/xalan) predefined to signify Xalan's
   * built-in XSLT Extensions. When used in stylesheets, this is often
   * bound to the "xalan:" prefix.
   * <p>
   *  http://xml.apache.org/xalan)预定义为表示Xalan的内置XSLT扩展。当在样式表中使用时,这通常绑定到"xalan："前缀。
   * 
   */
  public static final String
    S_BUILTIN_EXTENSIONS_URL = "http://xml.apache.org/xalan";

  /**
   * The old built-in extension url. It is still supported for
   * backward compatibility.
   * <p>
   *  旧的内置扩展程序网址。它仍然支持向后兼容性。
   * 
   */
  public static final String
    S_BUILTIN_OLD_EXTENSIONS_URL = "http://xml.apache.org/xslt";

  /**
   * Xalan extension namespaces.
   * <p>
   *  Xalan扩展名命名空间。
   * 
   */
  public static final String
    // The old namespace for Java extension
    S_EXTENSIONS_OLD_JAVA_URL = "http://xml.apache.org/xslt/java",
    // The new namespace for Java extension
    S_EXTENSIONS_JAVA_URL = "http://xml.apache.org/xalan/java",
    S_EXTENSIONS_LOTUSXSL_JAVA_URL = "http://xsl.lotus.com/java",
    S_EXTENSIONS_XALANLIB_URL = "http://xml.apache.org/xalan",
    S_EXTENSIONS_REDIRECT_URL = "http://xml.apache.org/xalan/redirect",
    S_EXTENSIONS_PIPE_URL = "http://xml.apache.org/xalan/PipeDocument",
    S_EXTENSIONS_SQL_URL = "http://xml.apache.org/xalan/sql";

  /**
   * EXSLT extension namespaces.
   * <p>
   *  EXSLT扩展名命名空间。
   * 
   */
  public static final String
    S_EXSLT_COMMON_URL = "http://exslt.org/common",
    S_EXSLT_MATH_URL = "http://exslt.org/math",
    S_EXSLT_SETS_URL = "http://exslt.org/sets",
    S_EXSLT_DATETIME_URL = "http://exslt.org/dates-and-times",
    S_EXSLT_FUNCTIONS_URL = "http://exslt.org/functions",
    S_EXSLT_DYNAMIC_URL = "http://exslt.org/dynamic",
    S_EXSLT_STRINGS_URL = "http://exslt.org/strings";


  /**
   * The minimum version of XSLT supported by this processor.
   * <p>
   *  此处理器支持的最低版本的XSLT。
   */
  public static final double XSLTVERSUPPORTED = 1.0;
}
