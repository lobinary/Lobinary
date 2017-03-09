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
 * $Id: SerializerFactory.java,v 1.2.4.1 2005/09/15 08:15:24 suresh_emailid Exp $
 * <p>
 *  $ Id：SerializerFactory.java,v 1.2.4.1 2005/09/15 08:15:24 suresh_emailid Exp $
 * 
 */
package com.sun.org.apache.xml.internal.serializer;

import java.util.Hashtable;
import java.util.Properties;

import javax.xml.transform.OutputKeys;

import com.sun.org.apache.xml.internal.serializer.utils.MsgKey;
import com.sun.org.apache.xml.internal.serializer.utils.Utils;
import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
import org.xml.sax.ContentHandler;

/**
 * This class is a public API, it is a factory for creating serializers.
   *
   * The properties object passed to the getSerializer() method should be created by
   * the OutputPropertiesFactory. Although the properties object
   * used to create a serializer does not need to be obtained
   * from OutputPropertiesFactory,
   * using this factory ensures that the default key/value properties
   * are set for the given output "method".
   *
   * <p>
   * The standard property keys supported are: "method", "version", "encoding",
   * "omit-xml-declaration", "standalone", doctype-public",
   * "doctype-system", "cdata-section-elements", "indent", "media-type".
   * These property keys and their values are described in the XSLT recommendation,
   * see {@link <a href="http://www.w3.org/TR/1999/REC-xslt-19991116"> XSLT 1.0 recommendation</a>}
   *
   * <p>
   * The value of the "cdata-section-elements" property key is a whitespace
   * separated list of elements. If the element is in a namespace then
   * value is passed in this format: {uri}localName
   *
   * <p>
   * The non-standard property keys supported are defined in {@link OutputPropertiesFactory}.
   *
   * <p>
   *  这个类是一个公共API,它是一个用于创建序列化程序的工厂。
   * 
   *  传递给getSerializer()方法的属性对象应由OutputPropertiesFactory创建。
   * 虽然用于创建序列化程序的属性对象不需要从OutputPropertiesFactory获取,但使用此出厂确保为给定输出"方法"设置默认键/值属性。
   * 
   * <p>
   * 支持的标准属性键包括："method","version","encoding","omit-xml-declaration","standalone",doctype-public","doctype
   * -system","cdata-section- "indent","media-type"。
   * 这些属性键及其值在XSLT建议中描述,参见{@link <a href ="http://www.w3.org/TR/1999/REC-xslt- 19991116"> XSLT 1.0推荐</a>}。
   * 
   * <p>
   *  "cdata-section-elements"属性键的值是空格分隔的元素列表。如果元素在命名空间中,则以下面的格式传递值：{uri} localName
   * 
   * <p>
   *  支持的非标准属性键在{@link OutputPropertiesFactory}中定义。
   * 
   * 
   * @see OutputPropertiesFactory
   * @see Method
   * @see Serializer
   */
public final class SerializerFactory
{
  /**
   * This constructor is private just to prevent the creation of such an object.
   * <p>
   */

  private SerializerFactory() {

  }
  /**
   * Associates output methods to default output formats.
   * <p>
   *  这个构造函数是私有的,只是为了防止创建这样的对象。
   * 
   */
  private static Hashtable m_formats = new Hashtable();

  /**
   * Returns a serializer for the specified output method. The output method
   * is specified by the value of the property associated with the "method" key.
   * If no implementation exists that supports the specified output method
   * an exception of some type will be thrown.
   * For a list of the output "method" key values see {@link Method}.
   *
   * <p>
   *  将输出方法与默认输出格式相关联。
   * 
   * 
   * @param format The output format, minimally the "method" property must be set.
   * @return A suitable serializer.
   * @throws IllegalArgumentException if method is
   * null or an appropriate serializer can't be found
   * @throws Exception if the class for the serializer is found but does not
   * implement ContentHandler.
   * @throws WrappedRuntimeException if an exception is thrown while trying to find serializer
   */
  public static Serializer getSerializer(Properties format)
  {
      Serializer ser;

      try
      {
        String method = format.getProperty(OutputKeys.METHOD);

        if (method == null) {
            String msg = Utils.messages.createMessage(
                MsgKey.ER_FACTORY_PROPERTY_MISSING,
                new Object[] { OutputKeys.METHOD});
            throw new IllegalArgumentException(msg);
        }

        String className =
            format.getProperty(OutputPropertiesFactory.S_KEY_CONTENT_HANDLER);


        if (null == className)
        {
            // Missing Content Handler property, load default using OutputPropertiesFactory
            Properties methodDefaults =
                OutputPropertiesFactory.getDefaultMethodProperties(method);
            className =
            methodDefaults.getProperty(OutputPropertiesFactory.S_KEY_CONTENT_HANDLER);
            if (null == className) {
                String msg = Utils.messages.createMessage(
                    MsgKey.ER_FACTORY_PROPERTY_MISSING,
                    new Object[] { OutputPropertiesFactory.S_KEY_CONTENT_HANDLER});
                throw new IllegalArgumentException(msg);
            }

        }



        Class cls = ObjectFactory.findProviderClass(className, true);

        // _serializers.put(method, cls);

        Object obj = cls.newInstance();

        if (obj instanceof SerializationHandler)
        {
              // this is one of the supplied serializers
            ser = (Serializer) cls.newInstance();
            ser.setOutputFormat(format);
        }
        else
        {
              /*
               *  This  must be a user defined Serializer.
               *  It had better implement ContentHandler.
               * <p>
               *  返回指定输出方法的序列化程序。输出方法由与"方法"键关联的属性的值指定。如果没有支持指定输出方法的实现,将抛出某种类型的异常。有关输出"方法"键值的列表,请参见{@link Method}。
               * 
               */
               if (obj instanceof ContentHandler)
               {

                  /*
                   * The user defined serializer defines ContentHandler,
                   * but we need to wrap it with ToXMLSAXHandler which
                   * will collect SAX-like events and emit true
                   * SAX ContentHandler events to the users handler.
                   * <p>
                   *  这必须是用户定义的序列化程序。它有更好的实现ContentHandler。
                   * 
                   */
                  className = SerializerConstants.DEFAULT_SAX_SERIALIZER;
                  cls = ObjectFactory.findProviderClass(className, true);
                  SerializationHandler sh =
                      (SerializationHandler) cls.newInstance();
                  sh.setContentHandler( (ContentHandler) obj);
                  sh.setOutputFormat(format);

                  ser = sh;
               }
               else
               {
                  // user defined serializer does not implement
                  // ContentHandler, ... very bad
                   throw new Exception(
                       Utils.messages.createMessage(
                           MsgKey.ER_SERIALIZER_NOT_CONTENTHANDLER,
                               new Object[] { className}));
               }

        }
      }
      catch (Exception e)
      {
        throw new com.sun.org.apache.xml.internal.serializer.utils.WrappedRuntimeException(e);
      }

      // If we make it to here ser is not null.
      return ser;
  }
}
