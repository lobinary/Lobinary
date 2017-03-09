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
 * $Id: QName.java,v 1.2.4.1 2005/09/02 11:45:56 pvedula Exp $
 * <p>
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler;

/**
/* <p>
/*  $ Id：QName.java,v 1.2.4.1 2005/09/02 11:45:56 pvedula Exp $
/* 
/* 
 * @author Jacek Ambroziak
 * @author Santiago Pericas-Geertsen
 * @author Morten Jorgensen
 */
final class QName {
    private final String _localname;
    private String _prefix;
    private String _namespace;
    private String _stringRep;
    private int    _hashCode;

    public QName(String namespace, String prefix, String localname) {
        _namespace = namespace;
        _prefix    = prefix;
        _localname = localname;

        _stringRep =
            (namespace != null && !namespace.equals(Constants.EMPTYSTRING)) ?
            (namespace + ':' + localname) : localname;

        _hashCode  = _stringRep.hashCode() + 19; // cached for speed
    }

    public void clearNamespace() {
        _namespace = Constants.EMPTYSTRING;
    }

    public String toString() {
        return _stringRep;
    }

    public String getStringRep() {
        return _stringRep;
    }

    public boolean equals(Object other) {
        return (this == other)
                   || (other instanceof QName
                           && _stringRep.equals(((QName) other).getStringRep()));
    }

    public String getLocalPart() {
        return _localname;
    }

    public String getNamespace() {
        return _namespace;
    }

    public String getPrefix() {
        return _prefix;
    }

    public int hashCode() {
        return _hashCode;
    }

    public String dump() {
        return "QName: " + _namespace + "(" + _prefix + "):" + _localname;
    }
}
