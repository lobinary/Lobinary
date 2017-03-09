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
 */
/*
 * $Id: Translet.java,v 1.2.4.1 2005/08/31 10:46:27 pvedula Exp $
 * <p>
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc;

import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

/**
/* <p>
/*  $ Id：Translet.java,v 1.2.4.1 2005/08/31 10:46:27 pvedula Exp $
/* 
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 */
public interface Translet {

    public void transform(DOM document, SerializationHandler handler)
        throws TransletException;
    public void transform(DOM document, SerializationHandler[] handlers)
        throws TransletException;
    public void transform(DOM document, DTMAxisIterator iterator,
                          SerializationHandler handler)
        throws TransletException;

    public Object addParameter(String name, Object value);

    public void buildKeys(DOM document, DTMAxisIterator iterator,
                          SerializationHandler handler, int root)
        throws TransletException;
    public void addAuxiliaryClass(Class auxClass);
    public Class getAuxiliaryClass(String className);
    public String[] getNamesArray();
    public String[] getUrisArray();
    public int[]    getTypesArray();
    public String[] getNamespaceArray();
    public boolean useServicesMechnism();
    public void setServicesMechnism(boolean flag);

}
