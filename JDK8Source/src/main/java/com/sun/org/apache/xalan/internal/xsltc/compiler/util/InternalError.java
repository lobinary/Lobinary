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
 * $Id: InternalError.java,v 1.0 2011-08-18 04:34:19 joehw Exp $
 * <p>
 *  $ Id：InternalError.java,v 1.0 2011-08-18 04:34:19 joehw Exp $
 * 
 */
package com.sun.org.apache.xalan.internal.xsltc.compiler.util;
/**
 * Marks a class of errors in which XSLTC has reached some incorrect internal
 * state from which it cannot recover.
 * <p>
 *  标记一类错误,其中XSLTC已达到某些不正确的内部状态,它不能恢复。
 * 
 */
public class InternalError extends Error {
    /**
     * Construct an <code>InternalError</code> with the specified error message.
     * <p>
     *  使用指定的错误消息构造<code> InternalError </code>。
     * 
     * @param msg the error message
     */
    public InternalError(String msg) {
        super(msg);
    }
}
