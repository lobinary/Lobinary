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
 * 
 */

package com.sun.org.apache.xerces.internal.dom;

import org.w3c.dom.DOMError;
import org.w3c.dom.DOMLocator;
import com.sun.org.apache.xerces.internal.xni.parser.XMLParseException;


/**
 * <code>DOMErrorImpl</code> is an implementation that describes an error.
 * <strong>Note:</strong> The error object that describes the error
 * might be reused by Xerces implementation, across multiple calls to the
 * handleEvent method on DOMErrorHandler interface.
 *
 *
 * <p>See also the <a href='http://www.w3.org/TR/2001/WD-DOM-Level-3-Core-20010913'>Document Object Model (DOM) Level 3 Core Specification</a>.
 *
 * @xerces.internal
 *
 * <p>
 *  <code> DOMErrorImpl </code>是一个描述错误的实现。
 *  <strong>注意</strong>：描述错误的错误对象可能会被多次调用到DOMErrorHandler接口上的handleEvent方法时由Xerces实现重用。
 * 
 *  <p>另请参阅<a href='http://www.w3.org/TR/2001/WD-DOM-Level-3-Core-20010913'>文档对象模型(DOM)3级核心规范< a>。
 * 
 *  @ xerces.internal
 * 
 * 
 * @author Gopal Sharma, SUN Microsystems Inc.
 * @author Elena Litani, IBM
 *
 */

// REVISIT: the implementation of ErrorReporter.
//          we probably should not pass XMLParseException
//

public class DOMErrorImpl implements DOMError {

    //
    // Data
    //

    public short fSeverity = DOMError.SEVERITY_WARNING;
    public String fMessage = null;
    public DOMLocatorImpl fLocator = new DOMLocatorImpl();
    public Exception fException = null;
    public String fType;
    public Object fRelatedData;



    //
    // Constructors
    //

    /** Default constructor. */
    public DOMErrorImpl () {
    }

    /** Exctracts information from XMLParserException) */
    public DOMErrorImpl (short severity, XMLParseException exception) {
        fSeverity = severity;
        fException = exception;
        fLocator = createDOMLocator (exception);
    }

    /**
     * The severity of the error, either <code>SEVERITY_WARNING</code>,
     * <code>SEVERITY_ERROR</code>, or <code>SEVERITY_FATAL_ERROR</code>.
     * <p>
     *  错误的严重性,<code> SEVERITY_WARNING </code>,<code> SEVERITY_ERROR </code>或<code> SEVERITY_FATAL_ERROR </code>
     * 。
     * 
     */

    public short getSeverity() {
        return fSeverity;
    }

    /**
     * An implementation specific string describing the error that occured.
     * <p>
     *  一个实现特定的字符串,描述发生的错误。
     * 
     */

    public String getMessage() {
        return fMessage;
    }

    /**
     * The location of the error.
     * <p>
     *  错误的位置。
     * 
     */

    public DOMLocator getLocation() {
        return fLocator;
    }

    // method to get the DOMLocator Object
    private DOMLocatorImpl createDOMLocator(XMLParseException exception) {
        // assuming DOMLocator wants the *expanded*, not the literal, URI of the doc... - neilg
        return new DOMLocatorImpl(exception.getLineNumber(),
                                  exception.getColumnNumber(),
                                  exception.getCharacterOffset(),
                                  exception.getExpandedSystemId());
    } // createDOMLocator()


    /**
     * The related platform dependent exception if any.exception is a reserved
     * word, we need to rename it.Change to "relatedException". (F2F 26 Sep
     * 2001)
     * <p>
     * 相关平台相关的异常如果any.exception是一个保留字,我们需要重命名it.Change为"relatedException"。 (F2F 26 Sep 2001)
     */
    public Object getRelatedException(){
        return fException;
    }

    public void reset(){
        fSeverity = DOMError.SEVERITY_WARNING;
        fException = null;
    }

    public String getType(){
        return fType;
    }

    public Object getRelatedData(){
        return fRelatedData;
    }


}// class DOMErrorImpl
