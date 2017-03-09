/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 1999-2004 The Apache Software Foundation.
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
 *  版权所有1999-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: DTMException.java,v 1.3 2005/09/28 13:48:50 pvedula Exp $
 * <p>
 *  $ Id：DTMException.java,v 1.3 2005/09/28 13:48:50 pvedula Exp $
 * 
 */
package com.sun.org.apache.xml.internal.dtm;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.xml.transform.SourceLocator;

import com.sun.org.apache.xml.internal.res.XMLErrorResources;
import com.sun.org.apache.xml.internal.res.XMLMessages;


/**
 * This class specifies an exceptional condition that occured
 * in the DTM module.
 * <p>
 *  此类指定发生在DTM模块中的异常情况。
 * 
 */
public class DTMException extends RuntimeException {
    static final long serialVersionUID = -775576419181334734L;

    /** Field locator specifies where the error occured.
    /* <p>
    /* 
     *  @serial */
    SourceLocator locator;

    /**
     * Method getLocator retrieves an instance of a SourceLocator
     * object that specifies where an error occured.
     *
     * <p>
     *  方法getLocator检索指定错误发生位置的SourceLocator对象的实例。
     * 
     * 
     * @return A SourceLocator object, or null if none was specified.
     */
    public SourceLocator getLocator() {
        return locator;
    }

    /**
     * Method setLocator sets an instance of a SourceLocator
     * object that specifies where an error occured.
     *
     * <p>
     *  方法setLocator设置一个SourceLocator对象的实例,该对象指定发生错误的位置。
     * 
     * 
     * @param location A SourceLocator object, or null to clear the location.
     */
    public void setLocator(SourceLocator location) {
        locator = location;
    }

    /** Field containedException specifies a wrapped exception.  May be null.
    /* <p>
    /* 
     *  @serial */
    Throwable containedException;

    /**
     * This method retrieves an exception that this exception wraps.
     *
     * <p>
     *  此方法检索此异常包装的异常。
     * 
     * 
     * @return An Throwable object, or null.
     * @see #getCause
     */
    public Throwable getException() {
        return containedException;
    }

    /**
     * Returns the cause of this throwable or <code>null</code> if the
     * cause is nonexistent or unknown.  (The cause is the throwable that
     * caused this throwable to get thrown.)
     * <p>
     *  如果原因不存在或未知,则返回此throwable或<code> null </code>的原因。 (原因是throwable引起这个throwable被抛出。)
     * 
     */
    public Throwable getCause() {

        return ((containedException == this)
                ? null
                : containedException);
    }

    /**
     * Initializes the <i>cause</i> of this throwable to the specified value.
     * (The cause is the throwable that caused this throwable to get thrown.)
     *
     * <p>This method can be called at most once.  It is generally called from
     * within the constructor, or immediately after creating the
     * throwable.  If this throwable was created
     * with {@link #DTMException(Throwable)} or
     * {@link #DTMException(String,Throwable)}, this method cannot be called
     * even once.
     *
     * <p>
     *  将此可抛弃项的<i>原因</i>初始化为指定的值。 (原因是throwable引起这个throwable被抛出。)
     * 
     * <p>此方法最多可调用一次。它通常在构造函数内调用,或者在创建可抛出对象之后立即调用。
     * 如果此throwable是使用{@link #DTMException(Throwable)}或{@link #DTMException(String,Throwable)}创建的,则此方法不能被调用一
     * 次。
     * <p>此方法最多可调用一次。它通常在构造函数内调用,或者在创建可抛出对象之后立即调用。
     * 
     * 
     * @param  cause the cause (which is saved for later retrieval by the
     *         {@link #getCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     * @return  a reference to this <code>Throwable</code> instance.
     * @throws IllegalArgumentException if <code>cause</code> is this
     *         throwable.  (A throwable cannot
     *         be its own cause.)
     * @throws IllegalStateException if this throwable was
     *         created with {@link #DTMException(Throwable)} or
     *         {@link #DTMException(String,Throwable)}, or this method has already
     *         been called on this throwable.
     */
    public synchronized Throwable initCause(Throwable cause) {

        if ((this.containedException == null) && (cause != null)) {
            throw new IllegalStateException(XMLMessages.createXMLMessage(XMLErrorResources.ER_CANNOT_OVERWRITE_CAUSE, null)); //"Can't overwrite cause");
        }

        if (cause == this) {
            throw new IllegalArgumentException(
                XMLMessages.createXMLMessage(XMLErrorResources.ER_SELF_CAUSATION_NOT_PERMITTED, null)); //"Self-causation not permitted");
        }

        this.containedException = cause;

        return this;
    }

    /**
     * Create a new DTMException.
     *
     * <p>
     *  创建一个新的DTMException。
     * 
     * 
     * @param message The error or warning message.
     */
    public DTMException(String message) {

        super(message);

        this.containedException = null;
        this.locator            = null;
    }

    /**
     * Create a new DTMException wrapping an existing exception.
     *
     * <p>
     *  创建一个新的DTMException包装一个现有的异常。
     * 
     * 
     * @param e The exception to be wrapped.
     */
    public DTMException(Throwable e) {

        super(e.getMessage());

        this.containedException = e;
        this.locator            = null;
    }

    /**
     * Wrap an existing exception in a DTMException.
     *
     * <p>This is used for throwing processor exceptions before
     * the processing has started.</p>
     *
     * <p>
     *  在DTMException中包含现有异常。
     * 
     *  <p>这用于在处理开始之前抛出处理器异常。</p>
     * 
     * 
     * @param message The error or warning message, or null to
     *                use the message from the embedded exception.
     * @param e Any exception
     */
    public DTMException(String message, Throwable e) {

        super(((message == null) || (message.length() == 0))
              ? e.getMessage()
              : message);

        this.containedException = e;
        this.locator            = null;
    }

    /**
     * Create a new DTMException from a message and a Locator.
     *
     * <p>This constructor is especially useful when an application is
     * creating its own exception from within a DocumentHandler
     * callback.</p>
     *
     * <p>
     *  从消息和定位器创建新的DTMException。
     * 
     *  <p>当应用程序从DocumentHandler回调中创建自己的异常时,此构造函数尤其有用。</p>
     * 
     * 
     * @param message The error or warning message.
     * @param locator The locator object for the error or warning.
     */
    public DTMException(String message, SourceLocator locator) {

        super(message);

        this.containedException = null;
        this.locator            = locator;
    }

    /**
     * Wrap an existing exception in a DTMException.
     *
     * <p>
     *  在DTMException中包含现有异常。
     * 
     * 
     * @param message The error or warning message, or null to
     *                use the message from the embedded exception.
     * @param locator The locator object for the error or warning.
     * @param e Any exception
     */
    public DTMException(String message, SourceLocator locator,
                                Throwable e) {

        super(message);

        this.containedException = e;
        this.locator            = locator;
    }

    /**
     * Get the error message with location information
     * appended.
     * <p>
     *  获取附加了位置信息的错误消息。
     * 
     */
    public String getMessageAndLocation() {

        StringBuffer sbuffer = new StringBuffer();
        String       message = super.getMessage();

        if (null != message) {
            sbuffer.append(message);
        }

        if (null != locator) {
            String systemID = locator.getSystemId();
            int    line     = locator.getLineNumber();
            int    column   = locator.getColumnNumber();

            if (null != systemID) {
                sbuffer.append("; SystemID: ");
                sbuffer.append(systemID);
            }

            if (0 != line) {
                sbuffer.append("; Line#: ");
                sbuffer.append(line);
            }

            if (0 != column) {
                sbuffer.append("; Column#: ");
                sbuffer.append(column);
            }
        }

        return sbuffer.toString();
    }

    /**
     * Get the location information as a string.
     *
     * <p>
     *  以字符串形式获取位置信息。
     * 
     * 
     * @return A string with location info, or null
     * if there is no location information.
     */
    public String getLocationAsString() {

        if (null != locator) {
            StringBuffer sbuffer  = new StringBuffer();
            String       systemID = locator.getSystemId();
            int          line     = locator.getLineNumber();
            int          column   = locator.getColumnNumber();

            if (null != systemID) {
                sbuffer.append("; SystemID: ");
                sbuffer.append(systemID);
            }

            if (0 != line) {
                sbuffer.append("; Line#: ");
                sbuffer.append(line);
            }

            if (0 != column) {
                sbuffer.append("; Column#: ");
                sbuffer.append(column);
            }

            return sbuffer.toString();
        } else {
            return null;
        }
    }

    /**
     * Print the the trace of methods from where the error
     * originated.  This will trace all nested exception
     * objects, as well as this object.
     * <p>
     *  从错误发生的地方打印方法的跟踪。这将跟踪所有嵌套异常对象以及此对象。
     * 
     */
    public void printStackTrace() {
        printStackTrace(new java.io.PrintWriter(System.err, true));
    }

    /**
     * Print the the trace of methods from where the error
     * originated.  This will trace all nested exception
     * objects, as well as this object.
     * <p>
     *  从错误发生的地方打印方法的跟踪。这将跟踪所有嵌套异常对象以及此对象。
     * 
     * 
     * @param s The stream where the dump will be sent to.
     */
    public void printStackTrace(java.io.PrintStream s) {
        printStackTrace(new java.io.PrintWriter(s));
    }

    /**
     * Print the the trace of methods from where the error
     * originated.  This will trace all nested exception
     * objects, as well as this object.
     * <p>
     *  从错误发生的地方打印方法的跟踪。这将跟踪所有嵌套异常对象以及此对象。
     * 
     * @param s The writer where the dump will be sent to.
     */
    public void printStackTrace(java.io.PrintWriter s) {

        if (s == null) {
            s = new java.io.PrintWriter(System.err, true);
        }

        try {
            String locInfo = getLocationAsString();

            if (null != locInfo) {
                s.println(locInfo);
            }

            super.printStackTrace(s);
        } catch (Throwable e) {}

        boolean isJdk14OrHigher = false;
        try {
            Throwable.class.getMethod("getCause", (Class[]) null);
            isJdk14OrHigher = true;
        } catch (NoSuchMethodException nsme) {
            // do nothing
        }

        // The printStackTrace method of the Throwable class in jdk 1.4
        // and higher will include the cause when printing the backtrace.
        // The following code is only required when using jdk 1.3 or lower
        if (!isJdk14OrHigher) {
            Throwable exception = getException();

            for (int i = 0; (i < 10) && (null != exception); i++) {
                s.println("---------");

                try {
                    if (exception instanceof DTMException) {
                        String locInfo =
                            ((DTMException) exception)
                                .getLocationAsString();

                        if (null != locInfo) {
                            s.println(locInfo);
                        }
                    }

                    exception.printStackTrace(s);
                } catch (Throwable e) {
                    s.println("Could not print stack trace...");
                }

                try {
                    Method meth =
                        ((Object) exception).getClass().getMethod("getException",
                            (Class[]) null);

                    if (null != meth) {
                        Throwable prev = exception;

                        exception = (Throwable) meth.invoke(exception, (Object[]) null);

                        if (prev == exception) {
                            break;
                        }
                    } else {
                        exception = null;
                    }
                } catch (InvocationTargetException ite) {
                    exception = null;
                } catch (IllegalAccessException iae) {
                    exception = null;
                } catch (NoSuchMethodException nsme) {
                    exception = null;
                }
            }
        }
    }
}
