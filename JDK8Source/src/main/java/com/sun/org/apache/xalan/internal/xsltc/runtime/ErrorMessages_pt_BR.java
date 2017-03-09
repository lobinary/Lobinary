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

package com.sun.org.apache.xalan.internal.xsltc.runtime;

import java.util.ListResourceBundle;

/**
/* <p>
/* 
 * @author Morten Jorgensen
 */
public class ErrorMessages_pt_BR extends ListResourceBundle {

/*
 * XSLTC run-time error messages.
 *
 * General notes to translators and definitions:
 *
 *   1) XSLTC is the name of the product.  It is an acronym for XML Stylesheet:
 *      Transformations Compiler
 *
 *   2) A stylesheet is a description of how to transform an input XML document
 *      into a resultant output XML document (or HTML document or text)
 *
 *   3) An axis is a particular "dimension" in a tree representation of an XML
 *      document; the nodes in the tree are divided along different axes.
 *      Traversing the "child" axis, for instance, means that the program
 *      would visit each child of a particular node; traversing the "descendant"
 *      axis means that the program would visit the child nodes of a particular
 *      node, their children, and so on until the leaf nodes of the tree are
 *      reached.
 *
 *   4) An iterator is an object that traverses nodes in a tree along a
 *      particular axis, one at a time.
 *
 *   5) An element is a mark-up tag in an XML document; an attribute is a
 *      modifier on the tag.  For example, in <elem attr='val' attr2='val2'>
 *      "elem" is an element name, "attr" and "attr2" are attribute names with
 *      the values "val" and "val2", respectively.
 *
 *   6) A namespace declaration is a special attribute that is used to associate
 *      a prefix with a URI (the namespace).  The meanings of element names and
 *      attribute names that use that prefix are defined with respect to that
 *      namespace.
 *
 *   7) DOM is an acronym for Document Object Model.  It is a tree
 *      representation of an XML document.
 *
 *      SAX is an acronym for the Simple API for XML processing.  It is an API
 *      used inform an XML processor (in this case XSLTC) of the structure and
 *      content of an XML document.
 *
 *      Input to the stylesheet processor can come from an XML parser in the
 *      form of a DOM tree or through the SAX API.
 *
 *   8) DTD is a document type declaration.  It is a way of specifying the
 *      grammar for an XML file, the names and types of elements, attributes,
 *      etc.
 *
 *   9) Translet is an invented term that refers to the class file that contains
 *      the compiled form of a stylesheet.
 * <p>
 *  XSLTC运行时错误消息。
 * 
 *  翻译者和定义的一般注释：
 * 
 *  1)XSLTC是产品的名称。它是XML样式表：转换编译器的首字母缩写
 * 
 *  2)样式表是如何将输入XML文档转换为结果输出XML文档(或HTML文档或文本)的描述,
 * 
 *  3)轴是XML文档的树表示中的特定"维度";树中的节点沿不同的轴被划分。
 * 例如,遍历"子"轴意味着程序将访问特定节点的每个子节点;遍历"后代"轴意味着程序将访问特定节点的子节点,它们的子节点,等等,直到到达树的叶节点。
 * 
 * 4)迭代器是沿着特定轴遍历树中的节点的对象,一次一个。
 * 
 *  5)元素是XML文档中的标记标记;属性是标记上的修饰符。
 * 例如,在<elem attr ='val'attr2 ='val2'>"elem"是元素名称,"attr"和"attr2"分别是具有值"val"和"val2"的属性名称。
 * 
 *  6)命名空间声明是用于将前缀与URI(命名空间)相关联的特殊属性。使用该前缀的元素名称和属性名称的含义是相对于该命名空间定义的。
 * 
 *  7)DOM是文档对象模型的首字母缩略词。它是XML文档的树表示。
 * 
 *  SAX是Simple API for XML处理的首字母缩略词。它是一个API,用于通知XML处理器(在本例中为XSLTC)XML文档的结构和内容。
 * 
 *  对样式表处理器的输入可以来自XML解析器,以DOM树的形式或通过SAX API。
 * 
 *  8)DTD是一个文档类型声明。它是一种指定XML文件的语法,元素的名称和类型,属性等的方法。
 * 
 *  9)Translet是一个发明的术语,它引用包含样式表的编译形式的类文件。
 * 
 */

    // These message should be read from a locale-specific resource bundle
    /** Get the lookup table for error messages.
     *
     * <p>
     * 
     * @return The message lookup table.
     */
    public Object[][] getContents()
    {
        return new Object[][] {

        /*
         * Note to translators:  the substitution text in the following message
         * is a class name.  Used for internal errors in the processor.
         * <p>
         *  注意翻译者：以下消息中的替换文本是类名。用于处理器中的内部错误。
         * 
         */
        {BasisLibrary.RUN_TIME_INTERNAL_ERR,
        "Erro interno de runtime em ''{0}''"},

        /*
         * Note to translators:  <xsl:copy> is a keyword that should not be
         * translated.
         * <p>
         *  注意翻译者：<xsl：copy>是不应翻译的关键字。
         * 
         */
        {BasisLibrary.RUN_TIME_COPY_ERR,
        "Erro de runtime ao executar <xsl:copy>."},

        /*
         * Note to translators:  The substitution text refers to data types.
         * The message is displayed if a value in a particular context needs to
         * be converted to type {1}, but that's not possible for a value of type
         * {0}.
         * <p>
         * 翻译者注意：替换文本指的是数据类型。如果特定上下文中的值需要转换为类型{1},那么将显示该消息,但对于类型为{0}的值不可能。
         * 
         */
        {BasisLibrary.DATA_CONVERSION_ERR,
        "Convers\u00E3o inv\u00E1lida de ''{0}'' para ''{1}''."},

        /*
         * Note to translators:  This message is displayed if the function named
         * by the substitution text is not a function that is supported.  XSLTC
         * is the acronym naming the product.
         * <p>
         *  注意翻译者：如果替换文本命名的函数不是受支持的函数,则会显示此消息。 XSLTC是命名产品的首字母缩略词。
         * 
         */
        {BasisLibrary.EXTERNAL_FUNC_ERR,
        "Fun\u00E7\u00E3o externa ''{0}'' n\u00E3o suportada por XSLTC."},

        /*
         * Note to translators:  This message is displayed if two values are
         * compared for equality, but the data type of one of the values is
         * unknown.
         * <p>
         *  翻译者注意：如果对两个值进行相等比较,但其中一个值的数据类型未知,则会显示此消息。
         * 
         */
        {BasisLibrary.EQUALITY_EXPR_ERR,
        "Tipo de argumento desconhecido na express\u00E3o de igualdade."},

        /*
         * Note to translators:  The substitution text for {0} will be a data
         * type; the substitution text for {1} will be the name of a function.
         * This is displayed if an argument of the particular data type is not
         * permitted for a call to this function.
         * <p>
         *  对翻译者的注解：{0}的替换文本将是一种数据类型; {1}的替换文本将是函数的名称。如果特定数据类型的参数不允许调用此函数,则会显示此消息。
         * 
         */
        {BasisLibrary.INVALID_ARGUMENT_ERR,
        "Tipo de argumento inv\u00E1lido ''{0}'' na chamada para ''{1}''"},

        /*
         * Note to translators:  There is way of specifying a format for a
         * number using a pattern; the processor was unable to format the
         * particular value using the specified pattern.
         * <p>
         *  注意翻译者：有一种方法使用模式指定数字的格式;处理器无法使用指定的模式格式化特定值。
         * 
         */
        {BasisLibrary.FORMAT_NUMBER_ERR,
        "Tentativa de formatar o n\u00FAmero ''{0}'' usando o padr\u00E3o ''{1}''."},

        /*
         * Note to translators:  The following represents an internal error
         * situation in XSLTC.  The processor was unable to create a copy of an
         * iterator.  (See definition of iterator above.)
         * <p>
         *  转换器注意事项：以下代表XSLTC中的内部错误情况。处理器无法创建迭代器的副本。 (见上面的迭代器的定义。)
         * 
         */
        {BasisLibrary.ITERATOR_CLONE_ERR,
        "N\u00E3o \u00E9 poss\u00EDvel clonar o iterador ''{0}''."},

        /*
         * Note to translators:  The following represents an internal error
         * situation in XSLTC.  The processor attempted to create an iterator
         * for a particular axis (see definition above) that it does not
         * support.
         * <p>
         *  转换器注意事项：以下代表XSLTC中的内部错误情况。处理器尝试为特定轴(见上面的定义)创建一个不支持的迭代器。
         * 
         */
        {BasisLibrary.AXIS_SUPPORT_ERR,
        "Iterador do eixo ''{0}'' n\u00E3o suportado."},

        /*
         * Note to translators:  The following represents an internal error
         * situation in XSLTC.  The processor attempted to create an iterator
         * for a particular axis (see definition above) that it does not
         * support.
         * <p>
         * 转换器注意事项：以下代表XSLTC中的内部错误情况。处理器尝试为特定轴(见上面的定义)创建一个不支持的迭代器。
         * 
         */
        {BasisLibrary.TYPED_AXIS_SUPPORT_ERR,
        "Iterador do eixo digitado ''{0}'' n\u00E3o suportado."},

        /*
         * Note to translators:  This message is reported if the stylesheet
         * being processed attempted to construct an XML document with an
         * attribute in a place other than on an element.  The substitution text
         * specifies the name of the attribute.
         * <p>
         *  翻译者注意：如果正在处理的样式表试图在一个元素之外的地方构造一个具有属性的XML文档,则会报告此消息。替换文本指定属性的名称。
         * 
         */
        {BasisLibrary.STRAY_ATTRIBUTE_ERR,
        "Atributo ''{0}'' fora do elemento."},

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
        {BasisLibrary.STRAY_NAMESPACE_ERR,
        "Declara\u00E7\u00E3o de namespace ''{0}''=''{1}'' fora do elemento."},

        /*
         * Note to translators:  The stylesheet contained a reference to a
         * namespace prefix that was undefined.  The value of the substitution
         * text is the name of the prefix.
         * <p>
         *  翻译者注意：样式表包含对未定义的命名空间前缀的引用。替换文本的值是前缀的名称。
         * 
         */
        {BasisLibrary.NAMESPACE_PREFIX_ERR,
        "O namespace do prefixo ''{0}'' n\u00E3o foi declarado."},

        /*
         * Note to translators:  The following represents an internal error.
         * DOMAdapter is a Java class in XSLTC.
         * <p>
         *  翻译者注意：以下代表内部错误。 DOMAdapter是XSLTC中的一个Java类。
         * 
         */
        {BasisLibrary.DOM_ADAPTER_INIT_ERR,
        "DOMAdapter criado usando o tipo incorreto de DOM de origem."},

        /*
         * Note to translators:  The following message indicates that the XML
         * parser that is providing input to XSLTC cannot be used because it
         * does not describe to XSLTC the structure of the input XML document's
         * DTD.
         * <p>
         *  注意翻译者：以下消息表明,提供输入到XSLTC的XML解析器不能使用,因为它不向XSLTC描述输入XML文档的DTD的结构。
         * 
         */
        {BasisLibrary.PARSER_DTD_SUPPORT_ERR,
        "O parser SAX que voc\u00EA est\u00E1 usando n\u00E3o trata eventos de declara\u00E7\u00E3o de DTD."},

        /*
         * Note to translators:  The following message indicates that the XML
         * parser that is providing input to XSLTC cannot be used because it
         * does not distinguish between ordinary XML attributes and namespace
         * declarations.
         * <p>
         *  注意翻译者：以下消息表明,提供输入到XSLTC的XML解析器不能使用,因为它不区分普通的XML属性和命名空间声明。
         * 
         */
        {BasisLibrary.NAMESPACES_SUPPORT_ERR,
        "O parser SAX que voc\u00EA est\u00E1 usando n\u00E3o tem suporte para os Namespaces de XML."},

        /*
         * Note to translators:  The substitution text is the URI that was in
         * error.
         * <p>
         *  注意翻译者：替换文本是错误的URI。
         * 
         */
        {BasisLibrary.CANT_RESOLVE_RELATIVE_URI_ERR,
        "N\u00E3o foi poss\u00EDvel resolver a refer\u00EAncia do URI ''{0}''."},

         /*
         * Note to translators:  The stylesheet contained an element that was
         * not recognized as part of the XSL syntax.  The substitution text
         * gives the element name.
         * <p>
         * 对翻译者的注意：样式表包含一个未被识别为XSL语法一部分的元素。替换文本提供元素名称。
         * 
         */
        {BasisLibrary.UNSUPPORTED_XSL_ERR,
        "Elemento XSL ''{0}'' n\u00E3o suportado"},

        /*
         * Note to translators:  The stylesheet referred to an extension to the
         * XSL syntax and indicated that it was defined by XSLTC, but XSLTC does
         * not recognize the particular extension named.  The substitution text
         * gives the extension name.
         * <p>
         *  对翻译者的注意：样式表指的是XSL语法的扩展,并指示它是由XSLTC定义的,但XSLTC不识别特定的扩展名。替换文本提供扩展名。
         * 
         */
        {BasisLibrary.UNSUPPORTED_EXT_ERR,
        "Extens\u00E3o ''{0}'' de XSLTC n\u00E3o reconhecida"},


        /*
         * Note to translators:  This error message is produced if the translet
         * class was compiled using a newer version of XSLTC and deployed for
         * execution with an older version of XSLTC.  The substitution text is
         * the name of the translet class.
         * <p>
         *  翻译员注意：如果translet类是使用较新版本的XSLTC编译并部署为使用旧版本的XSLTC执行,则会生成此错误消息。替换文本是translet类的名称。
         * 
         */
        {BasisLibrary.UNKNOWN_TRANSLET_VERSION_ERR,
        "O translet especificado, ''{0}'', foi criado usando uma vers\u00E3o do XSLTC mais recente que a vers\u00E3o de runtime de XSLTC em uso. Recompile a folha de estilos ou use uma vers\u00E3o mais recente de XSLTC para executar este translet."},

        /*
         * Note to translators:  An attribute whose effective value is required
         * to be a "QName" had a value that was incorrect.
         * 'QName' is an XML syntactic term that must not be translated.  The
         * substitution text contains the actual value of the attribute.
         * <p>
         *  翻译者注意：有效值必须为"QName"的属性的值不正确。 'QName'是一个XML语法术语,不能翻译。替换文本包含属性的实际值。
         * 
         */
        {BasisLibrary.INVALID_QNAME_ERR,
        "Um atributo cujo valor deve ser um QName tinha o valor ''{0}''"},


        /*
         * Note to translators:  An attribute whose effective value is required
         * to be a "NCName" had a value that was incorrect.
         * 'NCName' is an XML syntactic term that must not be translated.  The
         * substitution text contains the actual value of the attribute.
         * <p>
         *  翻译者注意：有效值必须为"NCName"的属性的值不正确。 "NCName"是不能翻译的XML句法术语。替换文本包含属性的实际值。
         */
        {BasisLibrary.INVALID_NCNAME_ERR,
        "Um atributo cujo valor deve ser um NCName tinha o valor ''{0}''"},

        {BasisLibrary.UNALLOWED_EXTENSION_FUNCTION_ERR,
        "O uso da fun\u00E7\u00E3o da extens\u00E3o ''{0}'' n\u00E3o ser\u00E1 permitido quando o recurso de processamento seguro for definido como verdadeiro."},

        {BasisLibrary.UNALLOWED_EXTENSION_ELEMENT_ERR,
        "O uso do elemento da extens\u00E3o ''{0}'' n\u00E3o ser\u00E1 permitido quando o recurso de processamento seguro for definido como verdadeiro."},
    };
    }

}
