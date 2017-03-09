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
package com.sun.org.apache.xml.internal.serializer.utils;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * An instance of this class is a ListResourceBundle that
 * has the required getContents() method that returns
 * an array of message-key/message associations.
 * <p>
 * The message keys are defined in {@link MsgKey}. The
 * messages that those keys map to are defined here.
 * <p>
 * The messages in the English version are intended to be
 * translated.
 *
 * This class is not a public API, it is only public because it is
 * used in com.sun.org.apache.xml.internal.serializer.
 *
 * @xsl.usage internal
 * <p>
 *  此类的实例是ListResourceBundle,它具有返回消息键/消息关联数组的所需getContents()方法。
 * <p>
 *  消息键在{@link MsgKey}中定义。这些键映射到的消息在此定义。
 * <p>
 *  英语版本中的消息旨在翻译。
 * 
 *  此类不是公共API,它只是公共的,因为它在com.sun.org.apache.xml.internal.serializer中使用。
 * 
 *  @ xsl.usage internal
 * 
 */
public class SerializerMessages_de extends ListResourceBundle {

    /*
     * This file contains error and warning messages related to
     * Serializer Error Handling.
     *
     *  General notes to translators:

     *  1) A stylesheet is a description of how to transform an input XML document
     *     into a resultant XML document (or HTML document or text).  The
     *     stylesheet itself is described in the form of an XML document.

     *
     *  2) An element is a mark-up tag in an XML document; an attribute is a
     *     modifier on the tag.  For example, in <elem attr='val' attr2='val2'>
     *     "elem" is an element name, "attr" and "attr2" are attribute names with
     *     the values "val" and "val2", respectively.
     *
     *  3) A namespace declaration is a special attribute that is used to associate
     *     a prefix with a URI (the namespace).  The meanings of element names and
     *     attribute names that use that prefix are defined with respect to that
     *     namespace.
     *
     *
     * <p>
     *  此文件包含与串行器错误处理相关的错误和警告消息。
     * 
     *  翻译者的一般注意事项：
     * 
     *  1)样式表是如何将输入XML文档转换为结果XML文档(或HTML文档或文本)的描述。样式表本身以XML文档的形式描述。
     * 
     * 2)元素是XML文档中的标记标记;属性是标记上的修饰符。
     * 例如,在<elem attr ='val'attr2 ='val2'>"elem"是元素名称,"attr"和"attr2"分别是具有值"val"和"val2"的属性名称。
     * 
     *  3)命名空间声明是一个特殊属性,用于将前缀与URI(命名空间)相关联。使用该前缀的元素名称和属性名称的含义是相对于该命名空间定义的。
     * 
     */

    /** The lookup table for error messages.   */
    public Object[][] getContents() {
        Object[][] contents = new Object[][] {
            {   MsgKey.BAD_MSGKEY,
                "Der Nachrichtenschl\u00FCssel \"{0}\" ist nicht in der Nachrichtenklasse \"{1}\" enthalten" },

            {   MsgKey.BAD_MSGFORMAT,
                "Das Format der Nachricht \"{0}\" in der Nachrichtenklasse \"{1}\" war nicht erfolgreich." },

            {   MsgKey.ER_SERIALIZER_NOT_CONTENTHANDLER,
                "Serializer-Klasse \"{0}\" implementiert org.xml.sax.ContentHandler nicht." },

            {   MsgKey.ER_RESOURCE_COULD_NOT_FIND,
                    "Ressource [ {0} ] konnte nicht gefunden werden.\n {1}" },

            {   MsgKey.ER_RESOURCE_COULD_NOT_LOAD,
                    "Ressource [ {0} ] konnte nicht geladen werden: {1} \n {2} \t {3}" },

            {   MsgKey.ER_BUFFER_SIZE_LESSTHAN_ZERO,
                    "Puffergr\u00F6\u00DFe <=0" },

            {   MsgKey.ER_INVALID_UTF16_SURROGATE,
                    "Ung\u00FCltige UTF-16-Ersetzung festgestellt: {0}?" },

            {   MsgKey.ER_OIERROR,
                "I/O-Fehler" },

            {   MsgKey.ER_ILLEGAL_ATTRIBUTE_POSITION,
                "Attribut {0} kann nicht nach untergeordneten Knoten oder vor dem Erstellen eines Elements hinzugef\u00FCgt werden. Attribut wird ignoriert." },

            /*
             * Note to translators:  The stylesheet contained a reference to a
             * namespace prefix that was undefined.  The value of the substitution
             * text is the name of the prefix.
             * <p>
             *  翻译者注意：样式表包含对未定义的命名空间前缀的引用。替换文本的值是前缀的名称。
             * 
             */
            {   MsgKey.ER_NAMESPACE_PREFIX,
                "Namespace f\u00FCr Pr\u00E4fix \"{0}\" wurde nicht deklariert." },

            /*
             * Note to translators:  This message is reported if the stylesheet
             * being processed attempted to construct an XML document with an
             * attribute in a place other than on an element.  The substitution text
             * specifies the name of the attribute.
             * <p>
             *  翻译者注意：如果正在处理的样式表试图在一个元素之外的地方构造一个具有属性的XML文档,则会报告此消息。替换文本指定属性的名称。
             * 
             */
            {   MsgKey.ER_STRAY_ATTRIBUTE,
                "Attribut \"{0}\" au\u00DFerhalb des Elements." },

            /*
             * Note to translators:  As with the preceding message, a namespace
             * declaration has the form of an attribute and is only permitted to
             * appear on an element.  The substitution text {0} is the namespace
             * prefix and {1} is the URI that was being used in the erroneous
             * namespace declaration.
             * <p>
             *  对翻译者的注意：与前面的消息一样,命名空间声明具有属性的形式,并且只允许出现在元素上。替换文本{0}是命名空间前缀,{1}是在错误的命名空间声明中使用的URI。
             * 
             */
            {   MsgKey.ER_STRAY_NAMESPACE,
                "Namespace-Deklaration {0}={1} au\u00DFerhalb des Elements." },

            {   MsgKey.ER_COULD_NOT_LOAD_RESOURCE,
                "\"{0}\" konnte nicht geladen werden (CLASSPATH pr\u00FCfen). Die Standardwerte werden verwendet" },

            {   MsgKey.ER_ILLEGAL_CHARACTER,
                "Versuch, Zeichen mit Integralwert {0} auszugeben, das nicht in der speziellen Ausgabecodierung von {1} dargestellt wird." },

            {   MsgKey.ER_COULD_NOT_LOAD_METHOD_PROPERTY,
                "Property-Datei \"{0}\" konnte f\u00FCr Ausgabemethode \"{1}\" nicht geladen werden (CLASSPATH pr\u00FCfen)" },

            {   MsgKey.ER_INVALID_PORT,
                "Ung\u00FCltige Portnummer" },

            {   MsgKey.ER_PORT_WHEN_HOST_NULL,
                "Port kann nicht festgelegt werden, wenn der Host null ist" },

            {   MsgKey.ER_HOST_ADDRESS_NOT_WELLFORMED,
                "Host ist keine wohlgeformte Adresse" },

            {   MsgKey.ER_SCHEME_NOT_CONFORMANT,
                "Schema ist nicht konform." },

            {   MsgKey.ER_SCHEME_FROM_NULL_STRING,
                "Schema kann nicht von Nullzeichenfolge festgelegt werden" },

            {   MsgKey.ER_PATH_CONTAINS_INVALID_ESCAPE_SEQUENCE,
                "Pfad enth\u00E4lt eine ung\u00FCltige Escapesequenz" },

            {   MsgKey.ER_PATH_INVALID_CHAR,
                "Pfad enth\u00E4lt ung\u00FCltiges Zeichen: {0}" },

            {   MsgKey.ER_FRAG_INVALID_CHAR,
                "Fragment enth\u00E4lt ein ung\u00FCltiges Zeichen" },

            {   MsgKey.ER_FRAG_WHEN_PATH_NULL,
                "Fragment kann nicht festgelegt werden, wenn der Pfad null ist" },

            {   MsgKey.ER_FRAG_FOR_GENERIC_URI,
                "Fragment kann nur f\u00FCr einen generischen URI festgelegt werden" },

            {   MsgKey.ER_NO_SCHEME_IN_URI,
                "Kein Schema gefunden in URI" },

            {   MsgKey.ER_CANNOT_INIT_URI_EMPTY_PARMS,
                "URI kann nicht mit leeren Parametern initialisiert werden" },

            {   MsgKey.ER_NO_FRAGMENT_STRING_IN_PATH,
                "Fragment kann nicht im Pfad und im Fragment angegeben werden" },

            {   MsgKey.ER_NO_QUERY_STRING_IN_PATH,
                "Abfragezeichenfolge kann nicht im Pfad und in der Abfragezeichenfolge angegeben werden" },

            {   MsgKey.ER_NO_PORT_IF_NO_HOST,
                "Port kann nicht angegeben werden, wenn der Host nicht angegeben wurde" },

            {   MsgKey.ER_NO_USERINFO_IF_NO_HOST,
                "Benutzerinformationen k\u00F6nnen nicht angegeben werden, wenn der Host nicht angegeben wurde" },

            {   MsgKey.ER_XML_VERSION_NOT_SUPPORTED,
                "Warnung: Die Version des Ausgabedokuments soll \"{0}\" sein. Diese Version von XML wird nicht unterst\u00FCtzt. Die Version des Ausgabedokuments wird \"1.0\" sein." },

            {   MsgKey.ER_SCHEME_REQUIRED,
                "Schema ist erforderlich." },

            /*
             * Note to translators:  The words 'Properties' and
             * 'SerializerFactory' in this message are Java class names
             * and should not be translated.
             * <p>
             *  注意翻译者：此消息中的'Properties'和'SerializerFactory'字是Java类名,不应翻译。
             */
            {   MsgKey.ER_FACTORY_PROPERTY_MISSING,
                "Das an die SerializerFactory \u00FCbergebene Properties-Objekt verf\u00FCgt \u00FCber keine Eigenschaft \"{0}\"." },

            {   MsgKey.ER_ENCODING_NOT_SUPPORTED,
                "Warnung: Die Codierung \"{0}\" wird nicht von der Java-Laufzeit unterst\u00FCtzt." },


        };

        return contents;
    }
}
