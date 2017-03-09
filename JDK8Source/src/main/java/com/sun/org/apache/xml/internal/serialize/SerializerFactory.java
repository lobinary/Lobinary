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


package com.sun.org.apache.xml.internal.serialize;

import com.sun.org.apache.xerces.internal.utils.ObjectFactory;
import com.sun.org.apache.xerces.internal.utils.SecuritySupport;
import java.io.OutputStream;
import java.io.Writer;
import java.io.UnsupportedEncodingException;
import java.util.Hashtable;
import java.util.StringTokenizer;

/**
 *
 *
 * <p>
 * 
 * @version $Revision: 1.6 $ $Date: 2010-11-01 04:40:36 $
 * @author <a href="mailto:Scott_Boag/CAM/Lotus@lotus.com">Scott Boag</a>
 * @author <a href="mailto:arkin@intalio.com">Assaf Arkin</a>
 */
public abstract class SerializerFactory
{


    public static final String FactoriesProperty = "com.sun.org.apache.xml.internal.serialize.factories";


    private static Hashtable  _factories = new Hashtable();


    static
    {
        SerializerFactory factory;
        String            list;
        StringTokenizer   token;
        String            className;

        // The default factories are always registered first,
        // any factory specified in the properties file and supporting
        // the same method will override the default factory.
        factory =  new SerializerFactoryImpl( Method.XML );
        registerSerializerFactory( factory );
        factory =  new SerializerFactoryImpl( Method.HTML );
        registerSerializerFactory( factory );
        factory =  new SerializerFactoryImpl( Method.XHTML );
        registerSerializerFactory( factory );
        factory =  new SerializerFactoryImpl( Method.TEXT );
        registerSerializerFactory( factory );

        list = SecuritySupport.getSystemProperty( FactoriesProperty );
        if ( list != null ) {
            token = new StringTokenizer( list, " ;,:" );
            while ( token.hasMoreTokens() ) {
                className = token.nextToken();
                try {
                    factory = (SerializerFactory) ObjectFactory.newInstance( className, true);
                    if ( _factories.containsKey( factory.getSupportedMethod() ) )
                        _factories.put( factory.getSupportedMethod(), factory );
                } catch ( Exception except ) { }
            }
        }
    }


    /**
     * Register a serializer factory, keyed by the given
     * method string.
     * <p>
     *  注册一个序列化工厂,由给定的方法字符串键入。
     * 
     */
    public static void registerSerializerFactory( SerializerFactory factory )
    {
        String method;

        synchronized ( _factories ) {
            method = factory.getSupportedMethod();
            _factories.put( method, factory );
        }
    }


    /**
     * Register a serializer factory, keyed by the given
     * method string.
     * <p>
     *  注册一个序列化工厂,由给定的方法字符串键入。
     * 
     */
    public static SerializerFactory getSerializerFactory( String method )
    {
        return (SerializerFactory) _factories.get( method );
    }


    /**
     * Returns the method supported by this factory and used to register
     * the factory. This call is required so factories can be added from
     * a properties file by knowing only the class name. This method is
     * protected, it is only required by this class but must be implemented
     * in derived classes.
     * <p>
     *  返回此工厂支持的用于注册工厂的方法。此调用是必需的,因此工厂可以通过仅知道类名称从属性文件添加。这个方法是受保护的,它只需要这个类,但必须在派生类中实现。
     * 
     */
    protected abstract String getSupportedMethod();


    /**
     * Create a new serializer based on the {@link OutputFormat}.
     * If this method is used to create the serializer, the {@link
     * Serializer#setOutputByteStream} or {@link Serializer#setOutputCharStream}
     * methods must be called before serializing a document.
     * <p>
     *  基于{@link OutputFormat}创建新的序列化程序。
     * 如果使用此方法创建序列化程序,则必须在序列化文档之前调用{@link Serializer#setOutputByteStream}或{@link Serializer#setOutputCharStream}
     * 方法。
     *  基于{@link OutputFormat}创建新的序列化程序。
     * 
     */
    public abstract Serializer makeSerializer(OutputFormat format);


    /**
     * Create a new serializer, based on the {@link OutputFormat} and
     * using the writer as the output character stream.  If this
     * method is used, the encoding property will be ignored.
     * <p>
     * 基于{@link OutputFormat}创建一个新的序列化程序,并使用writer作为输出字符流。如果使用此方法,将忽略encoding属性。
     * 
     */
    public abstract Serializer makeSerializer( Writer writer,
                                               OutputFormat format );


    /**
     * Create a new serializer, based on the {@link OutputFormat} and
     * using the output byte stream and the encoding specified in the
     * output format.
     *
     * <p>
     *  基于{@link OutputFormat}并使用输出字节流和输出格式中指定的编码创建新的序列化程序。
     * 
     * @throws UnsupportedEncodingException The specified encoding is
     *   not supported
     */
    public abstract Serializer makeSerializer( OutputStream output,
                                               OutputFormat format )
        throws UnsupportedEncodingException;


}
