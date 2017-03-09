/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001-2004 The Apache Software Foundation.
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
 *  版权所有2001-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: DOMCache.java,v 1.2.4.1 2005/08/31 10:23:55 pvedula Exp $
 * <p>
 *  $ Id：DOMCache.java,v 1.2.4.1 2005/08/31 10:23:55 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc;


/**
/* <p>
/* 
 * @author Morten Jorgensen
 */
public interface DOMCache {

    /**
     * This method is responsible for:
     *
     * (1) building the DOMImpl tree
     *
     *      Parser  _parser = new Parser();
     *      DOMImpl _dom = new DOMImpl();
     *      _parser.setDocumentHandler(_dom.getBuilder());
     *      _parser.setDTDHandler(_dom.getBuilder());
     *      _parser.parse(uri);
     *
     * (2) giving the translet an early opportunity to extract anything from
     *     the DOMImpl that it would like
     *
     *      translet.documentPrepass(_dom);
     *
     * (3) setting the document URI:
     *
     *      _dom.setDocumentURI(uri);
     *
     * <p>
     *  此方法负责：
     * 
     *  (1)构建DOMImpl树
     * 
     *  Parser _parser = new Parser(); DOMImpl _dom = new DOMImpl(); _parser.setDocumentHandler(_dom.getBuil
     * der()); _parser.setDTDHandler(_dom.getBuilder()); _parser.parse(uri);。
     * 
     *  (2)给予translet早期的机会从其想要的DOMImpl中提取任何东西
     * 
     * @param baseURI The base URI used by the document call.
     * @param href The href argument passed to the document function.
     * @param translet A reference to the translet requesting the document
     */
    public DOM retrieveDocument(String baseURI, String href, Translet translet);

}
