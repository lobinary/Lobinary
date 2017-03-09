/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2005 The Apache Software Foundation.
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
 *  版权所有2005 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 */

package com.sun.org.apache.xerces.internal.util;

import com.sun.org.apache.xerces.internal.xni.XMLLocator;
import org.xml.sax.Locator;
import org.xml.sax.ext.Locator2;

/**
 * Wraps {@link XMLLocator} and make it look like a SAX {@link Locator}.
 *
 * <p>
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 * 
 * @author Arnaud Le Hors, IBM
 * @author Andy Clark, IBM
 *
 */
public class LocatorProxy implements Locator2 {

    //
    // Data
    //

    /** XML locator. */
    private final XMLLocator fLocator;

    //
    // Constructors
    //

    /** Constructs an XML locator proxy. */
    public LocatorProxy(XMLLocator locator) {
        fLocator = locator;
    }

    //
    // Locator methods
    //

    /** Public identifier. */
    public String getPublicId() {
        return fLocator.getPublicId();
    }

    /** System identifier. */
    public String getSystemId() {
        return fLocator.getExpandedSystemId();
    }

    /** Line number. */
    public int getLineNumber() {
        return fLocator.getLineNumber();
    }

    /** Column number. */
    public int getColumnNumber() {
        return fLocator.getColumnNumber();
    }

    //
    // Locator2 methods
    //

    public String getXMLVersion() {
        return fLocator.getXMLVersion();
    }

    public String getEncoding() {
        return fLocator.getEncoding();
    }

}
