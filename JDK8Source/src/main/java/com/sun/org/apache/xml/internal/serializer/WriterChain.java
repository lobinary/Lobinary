/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2004 The Apache Software Foundation.
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
 *  版权所有2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: WriterChain.java,v 1.1.4.1 2005/09/08 10:58:44 suresh_emailid Exp $
 * <p>
 *  $ Id：WriterChain.java,v 1.1.4.1 2005/09/08 10:58:44 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;

import java.io.IOException;

/**
 * It is unfortunate that java.io.Writer is a class rather than an interface.
 * The serializer has a number of classes that extend java.io.Writer
 * and which send their ouput to a yet another wrapped Writer or OutputStream.
 *
 * The purpose of this interface is to force such classes to over-ride all of
 * the important methods defined on the java.io.Writer class, namely these:
 * <code>
 * write(int val)
 * write(char[] chars)
 * write(char[] chars, int start, int count)
 * write(String chars)
 * write(String chars, int start, int count)
 * flush()
 * close()
 * </code>
 * In this manner nothing will accidentally go directly to
 * the base class rather than to the wrapped Writer or OutputStream.
 *
 * The purpose of this class is to have a uniform way of chaining the output of one writer to
 * the next writer in the chain. In addition there are methods to obtain the Writer or
 * OutputStream that this object sends its output to.
 *
 * This interface is only for internal use withing the serializer.
 * @xsl.usage internal
 * <p>
 *  不幸的是,java.io.Writer是一个类而不是一个接口。序列化器有一些扩展java.io.Writer的类,并将它们的输出发送给另一个Writer或OutputStream。
 * 
 *  这个接口的目的是强制这些类覆盖java.io.Writer类上定义的所有重要方法,即：
 * <code>
 *  写(char char,int start,int count)write(String chars)write(String chars,int start,int count)
 * </code>
 *  以这种方式,没有什么会意外地直接到基类,而不是包装的Writer或OutputStream。
 * 
 * 这个类的目的是有一个统一的方式链接一个作家的输出到链中的下一个作家。此外,还有一些方法来获取该对象将其输出发送到的Writer或OutputStream。
 * 
 */
interface WriterChain
{
    /** This method forces us to over-ride the method defined in java.io.Writer */
    public void write(int val) throws IOException;
    /** This method forces us to over-ride the method defined in java.io.Writer */
    public void write(char[] chars) throws IOException;
    /** This method forces us to over-ride the method defined in java.io.Writer */
    public void write(char[] chars, int start, int count) throws IOException;
    /** This method forces us to over-ride the method defined in java.io.Writer */
    public void write(String chars) throws IOException;
    /** This method forces us to over-ride the method defined in java.io.Writer */
    public void write(String chars, int start, int count) throws IOException;
    /** This method forces us to over-ride the method defined in java.io.Writer */
    public void flush() throws IOException;
    /** This method forces us to over-ride the method defined in java.io.Writer */
    public void close() throws IOException;

    /**
     * If this method returns null, getOutputStream() must return non-null.
     * Get the writer that this writer sends its output to.
     *
     * It is possible that the Writer returned by this method does not
     * implement the WriterChain interface.
     * <p>
     *  此接口仅供内部使用串行器。 @ xsl.usage internal
     * 
     */
    public java.io.Writer getWriter();

    /**
     * If this method returns null, getWriter() must return non-null.
     * Get the OutputStream that this writer sends its output to.
     * <p>
     *  如果此方法返回null,getOutputStream()必须返回非null。获取该作者将其输出发送给的作者。
     * 
     *  这种方法返回的Writer可能不会实现WriterChain接口。
     * 
     */
    public java.io.OutputStream getOutputStream();
}
