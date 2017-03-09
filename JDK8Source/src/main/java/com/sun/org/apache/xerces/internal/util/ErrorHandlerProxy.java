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
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.util;

import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Wraps {@link XMLErrorHandler} and make it look like a SAX {@link ErrorHandler}.
 *
 * <p>
 * The derived class should override the {@link #getErrorHandler()} method
 * so that it will return the correct {@link XMLErrorHandler} instance.
 * This method will be called whenever an error/warning is found.
 *
 * <p>
 * Experience shows that it is better to store the actual
 * {@link XMLErrorHandler} in one place and looks up that variable,
 * rather than copying it into every component that needs an error handler
 * and update all of them whenever it is changed, IMO.
 *
 * <p>
 *  封装{@link XMLErrorHandler},使其看起来像一个SAX {@link ErrorHandler}。
 * 
 * <p>
 *  派生类应该覆盖{@link #getErrorHandler()}方法,以便它返回正确的{@link XMLErrorHandler}实例。每当发现错误/警告时,将调用此方法。
 * 
 * @author Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 *
 */
public abstract class ErrorHandlerProxy implements ErrorHandler {

    public void error(SAXParseException e) throws SAXException {
        XMLErrorHandler eh = getErrorHandler();
        if (eh instanceof ErrorHandlerWrapper) {
            ((ErrorHandlerWrapper)eh).fErrorHandler.error(e);
        }
        else {
            eh.error("","",ErrorHandlerWrapper.createXMLParseException(e));
        }
        // if an XNIException is thrown, just let it go.
        // REVISIT: is this OK? or should we try to wrap it into SAXException?
    }

    public void fatalError(SAXParseException e) throws SAXException {
        XMLErrorHandler eh = getErrorHandler();
        if (eh instanceof ErrorHandlerWrapper) {
            ((ErrorHandlerWrapper)eh).fErrorHandler.fatalError(e);
        }
        else {
            eh.fatalError("","",ErrorHandlerWrapper.createXMLParseException(e));
        }
    }

    public void warning(SAXParseException e) throws SAXException {
        XMLErrorHandler eh = getErrorHandler();
        if (eh instanceof ErrorHandlerWrapper) {
            ((ErrorHandlerWrapper)eh).fErrorHandler.warning(e);
        }
        else {
            eh.warning("","",ErrorHandlerWrapper.createXMLParseException(e));
        }
    }

    protected abstract XMLErrorHandler getErrorHandler();
}
