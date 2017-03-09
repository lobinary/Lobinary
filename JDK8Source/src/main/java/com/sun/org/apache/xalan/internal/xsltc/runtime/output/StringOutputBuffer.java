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
 * $Id: StringOutputBuffer.java,v 1.2.4.1 2005/09/06 11:36:16 pvedula Exp $
 * <p>
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.runtime.output;


/**
/* <p>
/*  $ Id：StringOutputBuffer.java,v 1.2.4.1 2005/09/06 11:36:16 pvedula Exp $
/* 
/* 
 * @author Santiago Pericas-Geertsen
 */
class StringOutputBuffer implements OutputBuffer {
    private StringBuffer _buffer;

    public StringOutputBuffer() {
        _buffer = new StringBuffer();
    }

    public String close() {
        return _buffer.toString();
    }

    public OutputBuffer append(String s) {
        _buffer.append(s);
        return this;
    }

    public OutputBuffer append(char[] s, int from, int to) {
        _buffer.append(s, from, to);
        return this;
    }

    public OutputBuffer append(char ch) {
        _buffer.append(ch);
        return this;
    }
}
