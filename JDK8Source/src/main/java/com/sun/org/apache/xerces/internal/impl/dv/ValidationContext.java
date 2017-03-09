/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001, 2002,2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2001,2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 */

package com.sun.org.apache.xerces.internal.impl.dv;

import java.util.Locale;

/**
 * ValidationContext has all the information required for the
 * validation of: id, idref, entity, notation, qname
 *
 * @xerces.internal
 *
 * <p>
 * 
 * 
 * @author Sandy Gao, IBM
 * @version $Id: ValidationContext.java,v 1.6 2010/07/23 02:09:29 joehw Exp $
 */
public interface ValidationContext {
    // whether to validate against facets
    public boolean needFacetChecking();

    // whether to do extra id/idref/entity checking
    public boolean needExtraChecking();

    // whether we need to normalize the value that is passed!
    public boolean needToNormalize();

    // are namespaces relevant in this context?
    public boolean useNamespaces();

    // entity
    public boolean isEntityDeclared (String name);
    public boolean isEntityUnparsed (String name);

    // id
    public boolean isIdDeclared (String name);
    public void    addId(String name);

    // idref
    public void addIdRef(String name);

    // get symbol from symbol table
    public String getSymbol (String symbol);

    // qname
    public String getURI(String prefix);

    // Locale
    public Locale getLocale();

}
