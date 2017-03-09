/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2002,2004 The Apache Software Foundation.
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
 *  版权所有1999-2002,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.impl.xpath.regex;

/**
 * @xerces.internal
 *
 * <p>
 * 
 * @author TAMURA Kent &lt;kent@trl.ibm.co.jp&gt;
 */
public class ParseException extends RuntimeException {

    /** Serialization version. */
    static final long serialVersionUID = -7012400318097691370L;

    int location;

    /*
    public ParseException(String mes) {
        this(mes, -1);
    }
    /* <p>
    /*  @ xerces.internal
    /* 
    */
    /**
     *
     * <p>
     *  public ParseException(String mes){this(mes,-1); }}
     * 
     */
    public ParseException(String mes, int location) {
        super(mes);
        this.location = location;
    }

    /**
     *
     * <p>
     * 
     * @return -1 if location information is not available.
     */
    public int getLocation() {
        return this.location;
    }
}
