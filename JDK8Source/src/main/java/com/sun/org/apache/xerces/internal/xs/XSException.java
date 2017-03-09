/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2003,2004 The Apache Software Foundation.
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
 *  版权所有2003,2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.xs;

/**
 * The XML Schema API operations only raise exceptions in "exceptional"
 * circumstances, i.e., when an operation is impossible to perform (either
 * for logical reasons, because data is lost, or because the implementation
 * has become unstable).
 * <p>Implementations should raise other exceptions under other circumstances.
 * <p>Some languages and object systems do not support the concept of
 * exceptions. For such systems, error conditions may be indicated using
 * native error reporting mechanisms. For some bindings, for example,
 * methods may return error codes similar to those listed in the
 * corresponding method descriptions.
 * <p>
 *  XML模式API操作仅在"异常"情况下,即,当操作不可能执行时(由于逻辑原因,因为数据丢失或者因为实现已变得不稳定)而引发异常。 <p>在其他情况下,实施应引发其他例外。
 *  <p>某些语言和对象系统不支持异常的概念。对于这样的系统,可以使用本机错误报告机制来指示错误状况。对于某些绑定,例如,方法可能返回类似于相应方法描述中列出的错误代码。
 * 
 */
public class XSException extends RuntimeException {

    /** Serialization version. */
    static final long serialVersionUID = 3111893084677917742L;

    public XSException(short code, String message) {
       super(message);
       this.code = code;
    }
    public short   code;
    // ExceptionCode
    /**
     * If the implementation does not support the requested type of object or
     * operation.
     * <p>
     *  如果实现不支持请求类型的对象或操作。
     * 
     */
    public static final short NOT_SUPPORTED_ERR         = 1;
    /**
     * If index or size is negative, or greater than the allowed value
     * <p>
     *  如果索引或大小为负值,或大于允许值
     */
    public static final short INDEX_SIZE_ERR            = 2;

}
