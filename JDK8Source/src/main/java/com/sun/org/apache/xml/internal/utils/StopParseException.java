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
 * $Id: StopParseException.java,v 1.2.4.1 2005/09/15 08:15:54 suresh_emailid Exp $
 * <p>
 *  $ Id：StopParseException.java,v 1.2.4.1 2005/09/15 08:15:54 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.utils;

/**
 * This is a special exception that is used to stop parsing when
 * search for an element.  For instance, when searching for xml:stylesheet
 * PIs, it is used to stop the parse once the document element is found.
 * <p>
 *  这是一个特殊的异常,用于在搜索元素时停止解析。例如,在搜索xml：stylesheet PI时,它用于在找到文档元素后停止解析。
 * 
 * 
 * @see StylesheetPIHandler
 * @xsl.usage internal
 */
public class StopParseException extends org.xml.sax.SAXException
{
        static final long serialVersionUID = 210102479218258961L;
  /**
   * Constructor StopParseException.
   * <p>
   *  构造函数StopParseException。
   */
  StopParseException()
  {
    super("Stylesheet PIs found, stop the parse");
  }
}
