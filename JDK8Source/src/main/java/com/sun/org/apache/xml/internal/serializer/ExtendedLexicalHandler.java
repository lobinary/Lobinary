/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2003-2004 The Apache Software Foundation.
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
 *  版权所有2003-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: ExtendedLexicalHandler.java,v 1.2.4.1 2005/09/15 08:15:18 suresh_emailid Exp $
 * <p>
 *  $ Id：ExtendedLexicalHandler.java,v 1.2.4.1 2005/09/15 08:15:18 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;

import org.xml.sax.SAXException;

/**
 * This interface has extensions to the standard SAX LexicalHandler interface.
 * This interface is intended to be used by a serializer.
 * @xsl.usage internal
 * <p>
 *  此接口具有标准SAX LexicalHandler接口的扩展。此接口旨在由串行器使用。 @ xsl.usage internal
 * 
 */
abstract interface ExtendedLexicalHandler extends org.xml.sax.ext.LexicalHandler
{
    /**
     * This method is used to notify of a comment
     * <p>
     *  此方法用于通知注释
     * 
     * @param comment the comment, but unlike the SAX comment() method this
     * method takes a String rather than a character array.
     * @throws SAXException
     */
    public void comment(String comment) throws SAXException;
}
