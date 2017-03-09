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
 * $Id: ErrorMessages_sk.java,v 1.1.6.1 2005/09/05 11:53:00 pvedula Exp $
 * <p>
 *  $ Id：ErrorMessages_sk.java,v 1.1.6.1 2005/09/05 11:53:00 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler.util;

import java.util.ListResourceBundle;

/**
/* <p>
/* 
 * @author Morten Jorgensen
 */
public class ErrorMessages_sk extends ListResourceBundle {

/*
 * XSLTC compile-time error messages.
 *
 * General notes to translators and definitions:
 *
 *   1) XSLTC is the name of the product.  It is an acronym for "XSLT Compiler".
 *      XSLT is an acronym for "XML Stylesheet Language: Transformations".
 *
 *   2) A stylesheet is a description of how to transform an input XML document
 *      into a resultant XML document (or HTML document or text).  The
 *      stylesheet itself is described in the form of an XML document.
 *
 *   3) A template is a component of a stylesheet that is used to match a
 *      particular portion of an input document and specifies the form of the
 *      corresponding portion of the output document.
 *
 *   4) An axis is a particular "dimension" in a tree representation of an XML
 *      document; the nodes in the tree are divided along different axes.
 *      Traversing the "child" axis, for instance, means that the program
 *      would visit each child of a particular node; traversing the "descendant"
 *      axis means that the program would visit the child nodes of a particular
 *      node, their children, and so on until the leaf nodes of the tree are
 *      reached.
 *
 *   5) An iterator is an object that traverses nodes in a tree along a
 *      particular axis, one at a time.
 *
 *   6) An element is a mark-up tag in an XML document; an attribute is a
 *      modifier on the tag.  For example, in <elem attr='val' attr2='val2'>
 *      "elem" is an element name, "attr" and "attr2" are attribute names with
 *      the values "val" and "val2", respectively.
 *
 *   7) A namespace declaration is a special attribute that is used to associate
 *      a prefix with a URI (the namespace).  The meanings of element names and
 *      attribute names that use that prefix are defined with respect to that
 *      namespace.
 *
 *   8) DOM is an acronym for Document Object Model.  It is a tree
 *      representation of an XML document.
 *
 *      SAX is an acronym for the Simple API for XML processing.  It is an API
 *      used inform an XML processor (in this case XSLTC) of the structure and
 *      content of an XML document.
 *
 *      Input to the stylesheet processor can come from an XML parser in the
 *      form of a DOM tree or through the SAX API.
 *
 *   9) DTD is a document type declaration.  It is a way of specifying the
 *      grammar for an XML file, the names and types of elements, attributes,
 *      etc.
 *
 *  10) XPath is a specification that describes a notation for identifying
 *      nodes in a tree-structured representation of an XML document.  An
 *      instance of that notation is referred to as an XPath expression.
 *
 *  11) Translet is an invented term that refers to the class file that contains
 *      the compiled form of a stylesheet.
 * <p>
 *  XSLTC编译时错误消息。
 * 
 *  翻译者和定义的一般注释：
 * 
 *  1)XSLTC是产品的名称。它是"XSLT编译器"的缩写。 XSLT是"XML Stylesheet Language：Transformations"的缩写。
 * 
 *  2)样式表是如何将输入XML文档转换为结果XML文档(或HTML文档或文本)的描述。样式表本身以XML文档的形式描述。
 * 
 *  3)模板是用于匹配输入文档的特定部分并指定输出文档的对应部分的形式的样式表的组件。
 * 
 * 4)轴是XML文档的树表示中的特定"维度";树中的节点沿不同的轴被划分。
 * 例如,遍历"子"轴意味着程序将访问特定节点的每个子节点;遍历"后代"轴意味着程序将访问特定节点的子节点,它们的子节点,等等,直到到达树的叶节点。
 * 
 *  5)迭代器是沿着特定轴遍历树中的节点的对象,一次一个。
 * 
 *  6)元素是XML文档中的标记标记;属性是标记上的修饰符。
 * 例如,在<elem attr ='val'attr2 ='val2'>"elem"是元素名称,"attr"和"attr2"分别是具有值"val"和"val2"的属性名称。
 * 
 *  7)命名空间声明是用于将前缀与URI(命名空间)相关联的特殊属性。使用该前缀的元素名称和属性名称的含义是相对于该命名空间定义的。
 * 
 *  8)DOM是文档对象模型的首字母缩写。它是XML文档的树表示。
 * 
 *  SAX是Simple API for XML处理的首字母缩略词。它是一个API,用于通知XML处理器(在本例中为XSLTC)XML文档的结构和内容。
 * 
 *  对样式表处理器的输入可以来自XML解析器,以DOM树的形式或通过SAX API。
 * 
 * 9)DTD是一个文档类型声明。它是一种指定XML文件的语法,元素的名称和类型,属性等的方法。
 * 
 *  10)XPath是描述用于标识XML文档的树结构表示中的节点的符号的规范。该符号的实例称为XPath表达式。
 * 
 *  11)Translet是一个发明的术语,它引用包含样式表的编译形式的类文件。
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
        {ErrorMsg.MULTIPLE_STYLESHEET_ERR,
        "Viac ne\u017e jeden \u0161t\u00fdl dokumentu bol definovan\u00fd v rovnakom s\u00fabore."},

        /*
         * Note to translators:  The substitution text is the name of a
         * template.  The same name was used on two different templates in the
         * same stylesheet.
         * <p>
         *  翻译者注意：替换文本是模板的名称。在同一样式表中的两个不同模板上使用了相同的名称。
         * 
         */
        {ErrorMsg.TEMPLATE_REDEF_ERR,
        "Vzor ''{0}'' je u\u017e v tomto \u0161t\u00fdle dokumentu definovan\u00fd."},


        /*
         * Note to translators:  The substitution text is the name of a
         * template.  A reference to the template name was encountered, but the
         * template is undefined.
         * <p>
         *  翻译者注意：替换文本是模板的名称。遇到对模板名称的引用,但模板未定义。
         * 
         */
        {ErrorMsg.TEMPLATE_UNDEF_ERR,
        "Vzor ''{0}'' nie je v tomto \u0161t\u00fdle dokumentu definovan\u00fd."},

        /*
         * Note to translators:  The substitution text is the name of a variable
         * that was defined more than once.
         * <p>
         *  对翻译者的注意：替换文本是被不止一次定义的变量的名称。
         * 
         */
        {ErrorMsg.VARIABLE_REDEF_ERR,
        "Premenn\u00e1 ''{0}'' je viackr\u00e1t definovan\u00e1 v tom istom rozsahu."},

        /*
         * Note to translators:  The substitution text is the name of a variable
         * or parameter.  A reference to the variable or parameter was found,
         * but it was never defined.
         * <p>
         *  翻译者注意：替换文本是变量或参数的名称。发现对变量或参数的引用,但它从未定义。
         * 
         */
        {ErrorMsg.VARIABLE_UNDEF_ERR,
        "Premenn\u00e1 alebo parameter ''{0}'' nie je definovan\u00e1."},

        /*
         * Note to translators:  The word "class" here refers to a Java class.
         * Processing the stylesheet required a class to be loaded, but it could
         * not be found.  The substitution text is the name of the class.
         * <p>
         *  注意翻译者：这里的"类"这个词指的是Java类。处理样式表需要加载一个类,但无法找到它。替换文本是类的名称。
         * 
         */
        {ErrorMsg.CLASS_NOT_FOUND_ERR,
        "Nie je mo\u017en\u00e9 n\u00e1js\u0165 triedu ''{0}''."},

        /*
         * Note to translators:  The word "method" here refers to a Java method.
         * Processing the stylesheet required a reference to the method named by
         * the substitution text, but it could not be found.  "public" is the
         * Java keyword.
         * <p>
         *  注意翻译者：这里的"方法"一词是指Java方法。处理样式表需要对替换文本命名的方法的引用,但找不到它。 "public"是Java关键字。
         * 
         */
        {ErrorMsg.METHOD_NOT_FOUND_ERR,
        "Nie je mo\u017en\u00e9 n\u00e1js\u0165 extern\u00fa met\u00f3du ''{0}'' (mus\u00ed by\u0165 verejn\u00e1)."},

        /*
         * Note to translators:  The word "method" here refers to a Java method.
         * Processing the stylesheet required a reference to the method named by
         * the substitution text, but no method with the required types of
         * arguments or return type could be found.
         * <p>
         * 注意翻译者：这里的"方法"一词是指Java方法。处理样式表需要对替换文本命名的方法的引用,但是没有找到具有所需类型的参数或返回类型的方法。
         * 
         */
        {ErrorMsg.ARGUMENT_CONVERSION_ERR,
        "Nie je mo\u017en\u00e9 konvertova\u0165 typ argumentu/n\u00e1vratu vo volan\u00ed met\u00f3dy ''{0}''"},

        /*
         * Note to translators:  The file or URI named in the substitution text
         * is missing.
         * <p>
         *  翻译者注意：替换文本中命名的文件或URI丢失。
         * 
         */
        {ErrorMsg.FILE_NOT_FOUND_ERR,
        "S\u00fabor alebo URI ''{0}'' sa nena\u0161li."},

        /*
         * Note to translators:  This message is displayed when the URI
         * mentioned in the substitution text is not well-formed syntactically.
         * <p>
         *  注意翻译者：当替换文本中提到的URI语法不正确时,将显示此消息。
         * 
         */
        {ErrorMsg.INVALID_URI_ERR,
        "Neplatn\u00fd URI ''{0}''."},

        /*
         * Note to translators:  The file or URI named in the substitution text
         * exists but could not be opened.
         * <p>
         *  翻译者注意：替换文本中命名的文件或URI存在,但无法打开。
         * 
         */
        {ErrorMsg.FILE_ACCESS_ERR,
        "Nie je mo\u017en\u00e9 otvori\u0165 s\u00fabor alebo URI ''{0}''."},

        /*
         * Note to translators: <xsl:stylesheet> and <xsl:transform> are
         * keywords that should not be translated.
         * <p>
         *  注意翻译者：<xsl：stylesheet>和<xsl：transform>是不应翻译的关键字。
         * 
         */
        {ErrorMsg.MISSING_ROOT_ERR,
        "O\u010dak\u00e1va sa element <xsl:stylesheet> alebo <xsl:transform>."},

        /*
         * Note to translators:  The stylesheet contained a reference to a
         * namespace prefix that was undefined.  The value of the substitution
         * text is the name of the prefix.
         * <p>
         *  翻译者注意：样式表包含对未定义的命名空间前缀的引用。替换文本的值是前缀的名称。
         * 
         */
        {ErrorMsg.NAMESPACE_UNDEF_ERR,
        "Predpona n\u00e1zvov\u00e9ho priestoru ''{0}'' nie je deklarovan\u00e1."},

        /*
         * Note to translators:  The Java function named in the stylesheet could
         * not be found.
         * <p>
         *  翻译者注意：找不到样式表中命名的Java函数。
         * 
         */
        {ErrorMsg.FUNCTION_RESOLVE_ERR,
        "Nie je mo\u017en\u00e9 rozl\u00ed\u0161i\u0165 volanie funkcie ''{0}''."},

        /*
         * Note to translators:  The substitution text is the name of a
         * function.  A literal string here means a constant string value.
         * <p>
         *  注意翻译者：替换文本是函数的名称。这里的文字字符串意味着一个常量字符串值。
         * 
         */
        {ErrorMsg.NEED_LITERAL_ERR,
        "Argument pre ''{0}'' mus\u00ed by\u0165 re\u0165azcom liter\u00e1lu."},

        /*
         * Note to translators:  This message indicates there was a syntactic
         * error in the form of an XPath expression.  The substitution text is
         * the expression.
         * <p>
         *  注意翻译者：此消息指示在XPath表达式的形式中存在句法错误。替换文本是表达式。
         * 
         */
        {ErrorMsg.XPATH_PARSER_ERR,
        "Chyba pri anal\u00fdze v\u00fdrazu XPath ''{0}''."},

        /*
         * Note to translators:  An element in the stylesheet requires a
         * particular attribute named by the substitution text, but that
         * attribute was not specified in the stylesheet.
         * <p>
         *  翻译者注意：样式表中的元素需要由替换文本命名的特定属性,但该属性未在样式表中指定。
         * 
         */
        {ErrorMsg.REQUIRED_ATTR_ERR,
        "Ch\u00fdba po\u017eadovan\u00fd atrib\u00fat ''{0}''."},

        /*
         * Note to translators:  This message indicates that a character not
         * permitted in an XPath expression was encountered.  The substitution
         * text is the offending character.
         * <p>
         * 翻译者注意：此消息指示遇到XPath表达式中不允许的字符。替换文本是令人讨厌的字符。
         * 
         */
        {ErrorMsg.ILLEGAL_CHAR_ERR,
        "Neplatn\u00fd znak ''{0}'' vo v\u00fdraze XPath."},

        /*
         * Note to translators:  A processing instruction is a mark-up item in
         * an XML document that request some behaviour of an XML processor.  The
         * form of the name of was invalid in this case, and the substitution
         * text is the name.
         * <p>
         *  对翻译者的注意：处理指令是XML文档中的标记项,其请求XML处理器的某些行为。在这种情况下,名称的形式无效,替换文本是名称。
         * 
         */
        {ErrorMsg.ILLEGAL_PI_ERR,
        "Neplatn\u00fd n\u00e1zov ''{0}'' pre in\u0161trukciu spracovania."},

        /*
         * Note to translators:  This message is reported if the stylesheet
         * being processed attempted to construct an XML document with an
         * attribute in a place other than on an element.  The substitution text
         * specifies the name of the attribute.
         * <p>
         *  翻译者注意：如果正在处理的样式表试图在一个元素之外的地方构造一个具有属性的XML文档,则会报告此消息。替换文本指定属性的名称。
         * 
         */
        {ErrorMsg.STRAY_ATTRIBUTE_ERR,
        "Atrib\u00fat ''{0}'' mimo elementu."},

        /*
         * Note to translators:  An attribute that wasn't recognized was
         * specified on an element in the stylesheet.  The attribute is named
         * by the substitution
         * text.
         * <p>
         *  翻译者注意：在样式表中的元素上指定了无法识别的属性。属性由替换文本命名。
         * 
         */
        {ErrorMsg.ILLEGAL_ATTRIBUTE_ERR,
        "Neleg\u00e1lny atrib\u00fat ''{0}''."},

        /*
         * Note to translators:  "import" and "include" are keywords that should
         * not be translated.  This messages indicates that the stylesheet
         * named in the substitution text imported or included itself either
         * directly or indirectly.
         * <p>
         *  注意翻译者："import"和"include"是不应翻译的关键字。此消息表示在替换文本中命名的样式表直接或间接导入或包含自身。
         * 
         */
        {ErrorMsg.CIRCULAR_INCLUDE_ERR,
        "Cirkul\u00e1rny import/zahrnutie. \u0160t\u00fdl dokumentu ''{0}'' je u\u017e zaveden\u00fd."},

        /*
         * Note to translators:  A result-tree fragment is a portion of a
         * resulting XML document represented as a tree.  "<xsl:sort>" is a
         * keyword and should not be translated.
         * <p>
         *  对翻译者的注意：结果树片段是作为树表示的结果XML文档的一部分。 "<xsl：sort>"是关键字,不应翻译。
         * 
         */
        {ErrorMsg.RESULT_TREE_SORT_ERR,
        "Fragmenty stromu v\u00fdsledkov nemo\u017eno triedi\u0165 (elementy <xsl:sort> s\u00fa ignorovan\u00e9). Ke\u010f vytv\u00e1rate v\u00fdsledkov\u00fd strom, mus\u00edte triedi\u0165 uzly."},

        /*
         * Note to translators:  A name can be given to a particular style to be
         * used to format decimal values.  The substitution text gives the name
         * of such a style for which more than one declaration was encountered.
         * <p>
         *  对翻译者的注意：可以给一个名称赋予用于格式十进制值的特定样式。替换文本给出了遇到多个声明的样式的名称。
         * 
         */
        {ErrorMsg.SYMBOLS_REDEF_ERR,
        "Desiatkov\u00e9 form\u00e1tovanie ''{0}'' je u\u017e definovan\u00e9."},

        /*
         * Note to translators:  The stylesheet version named in the
         * substitution text is not supported.
         * <p>
         *  注意翻译者：不支持在替换文本中命名的样式表版本。
         * 
         */
        {ErrorMsg.XSL_VERSION_ERR,
        "Verzia XSL ''{0}'' nie je podporovan\u00e1 XSLTC."},

        /*
         * Note to translators:  The definitions of one or more variables or
         * parameters depend on one another.
         * <p>
         * 翻译者注意：一个或多个变量或参数的定义取决于彼此。
         * 
         */
        {ErrorMsg.CIRCULAR_VARIABLE_ERR,
        "Cirkul\u00e1rna referencia premennej/parametra v ''{0}''."},

        /*
         * Note to translators:  The operator in an expresion with two operands was
         * not recognized.
         * <p>
         *  对翻译者的注意：无法识别具有两个操作数的表达式中的运算符。
         * 
         */
        {ErrorMsg.ILLEGAL_BINARY_OP_ERR,
        "Nezn\u00e1my oper\u00e1tor pre bin\u00e1rny v\u00fdraz."},

        /*
         * Note to translators:  This message is produced if a reference to a
         * function has too many or too few arguments.
         * <p>
         *  对翻译者的注意：如果对一个函数的引用具有太多或太少的参数,则会产生此消息。
         * 
         */
        {ErrorMsg.ILLEGAL_ARG_ERR,
        "Neplatn\u00fd argument(y) pre volanie funkcie."},

        /*
         * Note to translators:  "document()" is the name of function and must
         * not be translated.  A node-set is a set of the nodes in the tree
         * representation of an XML document.
         * <p>
         *  注意翻译者："document()"是函数的名称,不能翻译。节点集是XML文档的树表示中的一组节点。
         * 
         */
        {ErrorMsg.DOCUMENT_ARG_ERR,
        "Druh\u00fd argument pre funkciu dokumentu() mus\u00ed by\u0165 sada uzlov."},

        /*
         * Note to translators:  "<xsl:when>" and "<xsl:choose>" are keywords
         * and should not be translated.  This message describes a syntax error
         * in the stylesheet.
         * <p>
         *  注意翻译者："<xsl：when>"和"<xsl：choose>"是关键字,不应翻译。此消息描述样式表中的语法错误。
         * 
         */
        {ErrorMsg.MISSING_WHEN_ERR,
        "V <xsl:choose> sa vy\u017eaduje najmenej jeden element <xsl:when>."},

        /*
         * Note to translators:  "<xsl:otherwise>" and "<xsl:choose>" are
         * keywords and should not be translated.  This message describes a
         * syntax error in the stylesheet.
         * <p>
         *  注意翻译者："<xsl：other>"和"<xsl：choose>"是关键字,不应翻译。此消息描述样式表中的语法错误。
         * 
         */
        {ErrorMsg.MULTIPLE_OTHERWISE_ERR,
        "V  <xsl:choose> je povolen\u00fd len jeden element <xsl:otherwise>."},

        /*
         * Note to translators:  "<xsl:otherwise>" and "<xsl:choose>" are
         * keywords and should not be translated.  This message describes a
         * syntax error in the stylesheet.
         * <p>
         *  注意翻译者："<xsl：other>"和"<xsl：choose>"是关键字,不应翻译。此消息描述样式表中的语法错误。
         * 
         */
        {ErrorMsg.STRAY_OTHERWISE_ERR,
        "<xsl:otherwise> mo\u017eno pou\u017ei\u0165 len v <xsl:choose>."},

        /*
         * Note to translators:  "<xsl:when>" and "<xsl:choose>" are keywords
         * and should not be translated.  This message describes a syntax error
         * in the stylesheet.
         * <p>
         *  注意翻译者："<xsl：when>"和"<xsl：choose>"是关键字,不应翻译。此消息描述样式表中的语法错误。
         * 
         */
        {ErrorMsg.STRAY_WHEN_ERR,
        "<xsl:when> mo\u017eno pou\u017ei\u0165 len v <xsl:choose>."},

        /*
         * Note to translators:  "<xsl:when>", "<xsl:otherwise>" and
         * "<xsl:choose>" are keywords and should not be translated.  This
         * message describes a syntax error in the stylesheet.
         * <p>
         *  注意翻译者："<xsl：when>","<xsl：other>"和"<xsl：choose>"是关键字,不应翻译。此消息描述样式表中的语法错误。
         * 
         */
        {ErrorMsg.WHEN_ELEMENT_ERR,
        "V <xsl:choose> s\u00fa povolen\u00e9 len elementy <xsl:when> a <xsl:otherwise>."},

        /*
         * Note to translators:  "<xsl:attribute-set>" and "name" are keywords
         * that should not be translated.
         * <p>
         *  注意翻译者："<xsl：attribute-set>"和"name"是不应翻译的关键字。
         * 
         */
        {ErrorMsg.UNNAMED_ATTRIBSET_ERR,
        "<xsl:attribute-set> ch\u00fdba atrib\u00fat 'name'."},

        /*
         * Note to translators:  An element in the stylesheet contained an
         * element of a type that it was not permitted to contain.
         * <p>
         * 翻译者注意：样式表中的元素包含不允许包含的类型的元素。
         * 
         */
        {ErrorMsg.ILLEGAL_CHILD_ERR,
        "Neplatn\u00fd element potomka."},

        /*
         * Note to translators:  The stylesheet tried to create an element with
         * a name that was not a valid XML name.  The substitution text contains
         * the name.
         * <p>
         *  翻译者注意：样式表试图创建一个名称不是有效XML名称的元素。替换文本包含名称。
         * 
         */
        {ErrorMsg.ILLEGAL_ELEM_NAME_ERR,
        "Nem\u00f4\u017eete vola\u0165 element ''{0}''"},

        /*
         * Note to translators:  The stylesheet tried to create an attribute
         * with a name that was not a valid XML name.  The substitution text
         * contains the name.
         * <p>
         *  翻译者注意：样式表试图创建一个名称不是有效XML名称的属性。替换文本包含名称。
         * 
         */
        {ErrorMsg.ILLEGAL_ATTR_NAME_ERR,
        "Nem\u00f4\u017eete vola\u0165 atrib\u00fat ''{0}''"},

        /*
         * Note to translators:  The children of the outermost element of a
         * stylesheet are referred to as top-level elements.  No text should
         * occur within that outermost element unless it is within a top-level
         * element.  This message indicates that that constraint was violated.
         * "<xsl:stylesheet>" is a keyword that should not be translated.
         * <p>
         *  对翻译者的注意：样式表的最外层元素的子元素被称为顶级元素。除非它在顶层元素中,否则不应在该最外层元素中出现文本。此消息指示违反了该约束。 "<xsl：stylesheet>"是不应翻译的关键字。
         * 
         */
        {ErrorMsg.ILLEGAL_TEXT_NODE_ERR,
        "Textov\u00e9 \u00fadaje s\u00fa mimo elementu vrchnej \u00farovne <xsl:stylesheet>."},

        /*
         * Note to translators:  JAXP is an acronym for the Java API for XML
         * Processing.  This message indicates that the XML parser provided to
         * XSLTC to process the XML input document had a configuration problem.
         * <p>
         *  翻译者注意：JAXP是Java API for XML Processing的缩写。此消息表明提供给XSLTC以处理XML输入文档的XML解析器有一个配置问题。
         * 
         */
        {ErrorMsg.SAX_PARSER_CONFIG_ERR,
        "Analyz\u00e1tor JAXP nie je spr\u00e1vne nakonfigurovan\u00fd"},

        /*
         * Note to translators:  The substitution text names the internal error
         * encountered.
         * <p>
         *  注意翻译者：替换文本命名遇到的内部错误。
         * 
         */
        {ErrorMsg.INTERNAL_ERR,
        "Neodstr\u00e1nite\u013en\u00e1 intern\u00e1 chyba XSLTC: ''{0}''"},

        /*
         * Note to translators:  The stylesheet contained an element that was
         * not recognized as part of the XSL syntax.  The substitution text
         * gives the element name.
         * <p>
         *  对翻译者的注意：样式表包含一个未被识别为XSL语法一部分的元素。替换文本提供元素名称。
         * 
         */
        {ErrorMsg.UNSUPPORTED_XSL_ERR,
        "Nepodporovan\u00fd element XSL ''{0}''."},

        /*
         * Note to translators:  The stylesheet referred to an extension to the
         * XSL syntax and indicated that it was defined by XSLTC, but XSTLC does
         * not recognized the particular extension named.  The substitution text
         * gives the extension name.
         * <p>
         *  对翻译者的注意：样式表指的是XSL语法的扩展,并指示它是由XSLTC定义的,但XSTLC不识别特定的扩展名。替换文本提供扩展名。
         * 
         */
        {ErrorMsg.UNSUPPORTED_EXT_ERR,
        "Nerozl\u00ed\u0161en\u00e9 roz\u0161\u00edrenie XSLTC ''{0}''."},

        /*
         * Note to translators:  The XML document given to XSLTC as a stylesheet
         * was not, in fact, a stylesheet.  XSLTC is able to detect that in this
         * case because the outermost element in the stylesheet has to be
         * declared with respect to the XSL namespace URI, but no declaration
         * for that namespace was seen.
         * <p>
         * 对翻译者的注意：给XSLTC作为样式表的XML文档实际上不是样式表。 XSLTC能够检测到在这种情况下,因为样式表中的最外面的元素必须相对于XSL命名空间URI声明,但没有看到该命名空间的声明。
         * 
         */
        {ErrorMsg.MISSING_XSLT_URI_ERR,
        "Vstupn\u00fd dokument nie je \u0161t\u00fdlom dokumentu (n\u00e1zvov\u00fd priestor XSL nie je deklarovan\u00fd v kore\u0148ovom elemente)."},

        /*
         * Note to translators:  XSLTC could not find the stylesheet document
         * with the name specified by the substitution text.
         * <p>
         *  对翻译者的注意：XSLTC找不到具有替换文本指定的名称的样式表文档。
         * 
         */
        {ErrorMsg.MISSING_XSLT_TARGET_ERR,
        "Nebolo mo\u017en\u00e9 n\u00e1js\u0165 cie\u013e \u0161t\u00fdlu dokumentu ''{0}''."},

        /*
         * Note to translators:  access to the stylesheet target is denied
         * <p>
         *  注意翻译者：访问样式表目标被拒绝
         * 
         */
        {ErrorMsg.ACCESSING_XSLT_TARGET_ERR,
        "Could not read stylesheet target ''{0}'', because ''{1}'' access is not allowed."},

        /*
         * Note to translators:  This message represents an internal error in
         * condition in XSLTC.  The substitution text is the class name in XSLTC
         * that is missing some functionality.
         * <p>
         *  翻译者注意：此消息表示XSLTC中的内部错误。替换文本是XSLTC中缺少某些功能的类名。
         * 
         */
        {ErrorMsg.NOT_IMPLEMENTED_ERR,
        "Nie je implementovan\u00e9: ''{0}''."},

        /*
         * Note to translators:  The XML document given to XSLTC as a stylesheet
         * was not, in fact, a stylesheet.
         * <p>
         *  对翻译者的注意：给XSLTC作为样式表的XML文档实际上不是样式表。
         * 
         */
        {ErrorMsg.NOT_STYLESHEET_ERR,
        "Vstupn\u00fd dokument neobsahuje \u0161t\u00fdl dokumentu XSL."},

        /*
         * Note to translators:  The element named in the substitution text was
         * encountered in the stylesheet but is not recognized.
         * <p>
         *  翻译者注意：替换文本中命名的元素在样式表中遇到,但无法识别。
         * 
         */
        {ErrorMsg.ELEMENT_PARSE_ERR,
        "Nebolo mo\u017en\u00e9 analyzova\u0165 element ''{0}''"},

        /*
         * Note to translators:  "use", "<key>", "node", "node-set", "string"
         * and "number" are keywords in this context and should not be
         * translated.  This message indicates that the value of the "use"
         * attribute was not one of the permitted values.
         * <p>
         *  对翻译者的注意："使用","<key>","节点","节点集","字符串"和"数字"是此上下文中的关键字,不应翻译。此消息表明"use"属性的值不是允许的值之一。
         * 
         */
        {ErrorMsg.KEY_USE_ATTR_ERR,
        "Atrib\u00fat pou\u017eitia <key> mus\u00ed by\u0165 uzol, sada uzlov, re\u0165azec alebo \u010d\u00edslo."},

        /*
         * Note to translators:  An XML document can specify the version of the
         * XML specification to which it adheres.  This message indicates that
         * the version specified for the output document was not valid.
         * <p>
         *  翻译者注意：XML文档可以指定它所遵循的XML规范的版本。此消息表明为输出文档指定的版本无效。
         * 
         */
        {ErrorMsg.OUTPUT_VERSION_ERR,
        "Verzia v\u00fdstupn\u00e9ho dokumentu XML by mala by\u0165 1.0"},

        /*
         * Note to translators:  The operator in a comparison operation was
         * not recognized.
         * <p>
         *  对翻译员的注释：无法识别比较操作中的运算符。
         * 
         */
        {ErrorMsg.ILLEGAL_RELAT_OP_ERR,
        "Nezn\u00e1my oper\u00e1tor pre rela\u010dn\u00fd v\u00fdraz"},

        /*
         * Note to translators:  An attribute set defines as a set of XML
         * attributes that can be added to an element in the output XML document
         * as a group.  This message is reported if the name specified was not
         * used to declare an attribute set.  The substitution text is the name
         * that is in error.
         * <p>
         * 翻译器注意事项：属性集定义为一组XML属性,可以作为组添加到输出XML文档中的元素。如果指定的名称未用于声明属性集,则会报告此消息。替换文本是错误的名称。
         * 
         */
        {ErrorMsg.ATTRIBSET_UNDEF_ERR,
        "Pokus o pou\u017eitie neexistuj\u00facej sady atrib\u00fatov ''{0}''."},

        /*
         * Note to translators:  The term "attribute value template" is a term
         * defined by XSLT which describes the value of an attribute that is
         * determined by an XPath expression.  The message indicates that the
         * expression was syntactically incorrect; the substitution text
         * contains the expression that was in error.
         * <p>
         *  翻译者注意：术语"属性值模板"是由XSLT定义的术语,用于描述由XPath表达式确定的属性的值。消息指示表达式在语法上不正确;替换文本包含错误的表达式。
         * 
         */
        {ErrorMsg.ATTR_VAL_TEMPLATE_ERR,
        "Nie je mo\u017en\u00e9 analyzova\u0165 vzor hodnoty atrib\u00fatu ''{0}''."},

        /*
         * Note to translators:  ???
         * <p>
         *  注意翻译：???
         * 
         */
        {ErrorMsg.UNKNOWN_SIG_TYPE_ERR,
        "Nezn\u00e1my typ \u00fadajov v podpise pre triedu ''{0}''."},

        /*
         * Note to translators:  The substitution text refers to data types.
         * The message is displayed if a value in a particular context needs to
         * be converted to type {1}, but that's not possible for a value of
         * type {0}.
         * <p>
         *  翻译者注意：替换文本指的是数据类型。如果特定上下文中的值需要转换为类型{1},那么将显示该消息,但对于类型为{0}的值不可能。
         * 
         */
        {ErrorMsg.DATA_CONVERSION_ERR,
        "Nie je mo\u017en\u00e9 konvertova\u0165 typ \u00fadajov ''{0}'' na ''{1}''."},

        /*
         * Note to translators:  "Templates" is a Java class name that should
         * not be translated.
         * <p>
         *  注意翻译者："模板"是不应该翻译的Java类名。
         * 
         */
        {ErrorMsg.NO_TRANSLET_CLASS_ERR,
        "Tento vzor neobsahuje platn\u00fa defin\u00edciu triedy transletu."},

        /*
         * Note to translators:  "Templates" is a Java class name that should
         * not be translated.
         * <p>
         *  注意翻译者："模板"是不应该翻译的Java类名。
         * 
         */
        {ErrorMsg.NO_MAIN_TRANSLET_ERR,
        "Tento vzor neobsahuje triedu s n\u00e1zvom ''{0}''."},

        /*
         * Note to translators:  The substitution text is the name of a class.
         * <p>
         *  翻译者注意：替换文本是类的名称。
         * 
         */
        {ErrorMsg.TRANSLET_CLASS_ERR,
        "Nebolo mo\u017en\u00e9 zavies\u0165 triedu transletu ''{0}''."},

        {ErrorMsg.TRANSLET_OBJECT_ERR,
        "Trieda transletu zaveden\u00e1, ale nie je mo\u017en\u00e9 vytvori\u0165 in\u0161tanciu transletu."},

        /*
         * Note to translators:  "ErrorListener" is a Java interface name that
         * should not be translated.  The message indicates that the user tried
         * to set an ErrorListener object on object of the class named in the
         * substitution text with "null" Java value.
         * <p>
         *  注意翻译者："ErrorListener"是不应该翻译的Java接口名称。该消息表示用户尝试在替换文本中以"null"Java值命名的类的对象上设置ErrorListener对象。
         * 
         */
        {ErrorMsg.ERROR_LISTENER_NULL_ERR,
        "Pokus o nastavenie ErrorListener pre ''{0}'' na null"},

        /*
         * Note to translators:  StreamSource, SAXSource and DOMSource are Java
         * interface names that should not be translated.
         * <p>
         *  注意翻译者：StreamSource,SAXSource和DOMSource是不应该翻译的Java接口名称。
         * 
         */
        {ErrorMsg.JAXP_UNKNOWN_SOURCE_ERR,
        "XSLTC podporuje len StreamSource, SAXSource a DOMSource"},

        /*
         * Note to translators:  "Source" is a Java class name that should not
         * be translated.  The substitution text is the name of Java method.
         * <p>
         * 注意翻译者："源"是一个Java类名,不应该翻译。替换文本是Java方法的名称。
         * 
         */
        {ErrorMsg.JAXP_NO_SOURCE_ERR,
        "Objekt zdroja odovzdan\u00fd ''{0}'' nem\u00e1 \u017eiadny obsah."},

        /*
         * Note to translators:  The message indicates that XSLTC failed to
         * compile the stylesheet into a translet (class file).
         * <p>
         *  注意翻译者：消息指示XSLTC未能将样式表编译为translet(类文件)。
         * 
         */
        {ErrorMsg.JAXP_COMPILE_ERR,
        "Nebolo mo\u017en\u00e9 skompilova\u0165 \u0161t\u00fdl dokumentu"},

        /*
         * Note to translators:  "TransformerFactory" is a class name.  In this
         * context, an attribute is a property or setting of the
         * TransformerFactory object.  The substitution text is the name of the
         * unrecognised attribute.  The method used to retrieve the attribute is
         * "getAttribute", so it's not clear whether it would be best to
         * translate the term "attribute".
         * <p>
         *  注意翻译者："TransformerFactory"是一个类名。在此上下文中,属性是TransformerFactory对象的属性或设置。替换文本是无法识别的属性的名称。
         * 用于检索属性的方法是"getAttribute",因此不清楚是否最好翻译术语"attribute"。
         * 
         */
        {ErrorMsg.JAXP_INVALID_ATTR_ERR,
        "TransformerFactory nerozozn\u00e1va atrib\u00fat ''{0}''."},

        /*
         * Note to translators:  "setResult()" and "startDocument()" are Java
         * method names that should not be translated.
         * <p>
         *  注意翻译者："setResult()"和"startDocument()"是不应该翻译的Java方法名称。
         * 
         */
        {ErrorMsg.JAXP_SET_RESULT_ERR,
        "setResult() sa mus\u00ed vola\u0165 pred startDocument()."},

        /*
         * Note to translators:  "Transformer" is a Java interface name that
         * should not be translated.  A Transformer object should contained a
         * reference to a translet object in order to be used for
         * transformations; this message is produced if that requirement is not
         * met.
         * <p>
         *  对于翻译者的注意："Transformer"是不应该翻译的Java接口名称。 Transformer对象应该包含对translet对象的引用,以便用于转换;如果不满足该要求,则生成此消息。
         * 
         */
        {ErrorMsg.JAXP_NO_TRANSLET_ERR,
        "Transform\u00e1tor nem\u00e1 \u017eiadny zapuzdren\u00fd objekt transletu."},

        /*
         * Note to translators:  The XML document that results from a
         * transformation needs to be sent to an output handler object; this
         * message is produced if that requirement is not met.
         * <p>
         *  对转换器的注意：从转换产生的XML文档需要发送到输出处理程序对象;如果不满足该要求,则生成此消息。
         * 
         */
        {ErrorMsg.JAXP_NO_HANDLER_ERR,
        "Pre v\u00fdsledok transform\u00e1cie nebol definovan\u00fd \u017eiadny v\u00fdstupn\u00fd handler."},

        /*
         * Note to translators:  "Result" is a Java interface name in this
         * context.  The substitution text is a method name.
         * <p>
         *  对于翻译者的注意："Result"是此上下文中的Java接口名称。替换文本是一个方法名称。
         * 
         */
        {ErrorMsg.JAXP_NO_RESULT_ERR,
        "Objekt v\u00fdsledku odovzdan\u00fd ''{0}'' je neplatn\u00fd."},

        /*
         * Note to translators:  "Transformer" is a Java interface name.  The
         * user's program attempted to access an unrecognized property with the
         * name specified in the substitution text.  The method used to retrieve
         * the property is "getOutputProperty", so it's not clear whether it
         * would be best to translate the term "property".
         * <p>
         * 注意转换器："Transformer"是一个Java接口名称。用户程序尝试使用替换文本中指定的名称访问无法识别的属性。
         * 用于检索属性的方法是"getOutputProperty",因此不清楚是否最好翻译术语"属性"。
         * 
         */
        {ErrorMsg.JAXP_UNKNOWN_PROP_ERR,
        "Pokus o pr\u00edstup k neplatn\u00e9mu majetku transform\u00e1tora ''{0}''."},

        /*
         * Note to translators:  SAX2DOM is the name of a Java class that should
         * not be translated.  This is an adapter in the sense that it takes a
         * DOM object and converts it to something that uses the SAX API.
         * <p>
         *  注意翻译者：SAX2DOM是不应被翻译的Java类的名称。这是一个适配器,它意味着它需要一个DOM对象并将其转换为使用SAX API的东西。
         * 
         */
        {ErrorMsg.SAX2DOM_ADAPTER_ERR,
        "Nebolo mo\u017en\u00e9 vytvori\u0165 adapt\u00e9r SAX2DOM: ''{0}''."},

        /*
         * Note to translators:  "XSLTCSource.build()" is a Java method name.
         * "systemId" is an XML term that is short for "system identification".
         * <p>
         *  注意翻译者："XSLTCSource.build()"是一个Java方法名称。 "systemId"是"系统标识"的缩写的XML术语。
         * 
         */
        {ErrorMsg.XSLTC_SOURCE_ERR,
        "XSLTCSource.build() bol zavolan\u00fd bez nastaven\u00e9ho systemId."},


        {ErrorMsg.COMPILE_STDIN_ERR,
        "Vo\u013eba -i sa mus\u00ed pou\u017e\u00edva\u0165 s vo\u013ebou -o."},


        /*
         * Note to translators:  This message contains usage information for a
         * means of invoking XSLTC from the command-line.  The message is
         * formatted for presentation in English.  The strings <output>,
         * <directory>, etc. indicate user-specified argument values, and can
         * be translated - the argument <package> refers to a Java package, so
         * it should be handled in the same way the term is handled for JDK
         * documentation.
         * <p>
         *  注意翻译者：此消息包含从命令行调用XSLTC的方法的使用信息。消息格式化为英语演示。
         * 字符串<output>,<directory>等表示用户指定的参数值,并且可以翻译 - 参数<package>是指Java包,因此它应该按照与JDK相同的方式处理文档。
         * 
         */
        {ErrorMsg.COMPILE_USAGE_STR,
        "SYNOPSIS\n   java com.sun.org.apache.xalan.internal.xsltc.cmdline.Compile [-o <output>]\n      [-d <directory>] [-j <jarfile>] [-p <package>]\n      [-n] [-x] [-s] [-u] [-v] [-h] { <stylesheet> | -i }\n\nOPTIONS\n   -o <output>    prira\u010fuje n\u00e1zov <output> generovan\u00e9mu transletu \n. \u0160tandardne sa n\u00e1zov transletu \n berie z n\u00e1zvu <stylesheet>. T\u00e1to vo\u013eba sa ignoruje pri kompilovan\u00ed viacer\u00fdch \u0161t\u00fdlov dokumentov\n\n.   -d <directory> uv\u00e1dza cie\u013eov\u00fd adres\u00e1r pre translet\n   -j <jarfile>   pakuje triedy transletov do s\u00faboru jar n\u00e1zvu \n uveden\u00e9ho ako <jarfile>\n   -p <package>   uv\u00e1dza predponu n\u00e1zvu bal\u00edku pre v\u0161etky generovan\u00e9 triedy transletu.\n\n   -n             povo\u013euje zoradenie vzorov v riadku (\u0161tandardn\u00e9 chovanie v priemere lep\u0161ie). \n\n   -x             zap\u00edna   v\u00fdstupy spr\u00e1v ladenia \n   -s             zakazuje volanie System.exit\n   -u             interpretuje<stylesheet> argumenty ako URL\n   -i             n\u00fati kompil\u00e1tor \u010d\u00edta\u0165 \u0161t\u00fdl dokumentu z stdin\n   -v             tla\u010d\u00ed verziu kompil\u00e1tora\n   -h             tla\u010d\u00ed pr\u00edkaz tohto pou\u017eitia\n"},

        /*
         * Note to translators:  This message contains usage information for a
         * means of invoking XSLTC from the command-line.  The message is
         * formatted for presentation in English.  The strings <jarfile>,
         * <document>, etc. indicate user-specified argument values, and can
         * be translated - the argument <class> refers to a Java class, so it
         * should be handled in the same way the term is handled for JDK
         * documentation.
         * <p>
         *  注意翻译者：此消息包含从命令行调用XSLTC的方法的使用信息。消息格式化为英语演示。
         * 字符串<jarfile>,<document>等表示用户指定的参数值,并且可以翻译 - 参数<class>指的是Java类,因此它应该以与处理JDK相同的方式处理文档。
         * 
         */
        {ErrorMsg.TRANSFORM_USAGE_STR,
        "SYNOPSIS \n   java com.sun.org.apache.xalan.internal.xsltc.cmdline.Transform [-j <jarfile>]\n      [-x] [-s] [-n <iterations>] {-u <document_url> | <document>}\n      <class> [<param1>=<value1> ...]\n\n   pou\u017e\u00edva translet <class> na transform\u00e1ciu dokumentu XML \n   uveden\u00e9ho ako <document>. <class> transletu je bu\u010f v \n u\u017e\u00edvate\u013eovej CLASSPATH alebo vo volite\u013ene uvedenom <jarfile>.\nVO\u013dBY\n   -j <jarfile>    uv\u00e1dza s\u00fabor jar, z ktor\u00e9ho sa m\u00e1 zavies\u0165 translet\n   -x              zap\u00edna \u010fal\u0161\u00ed v\u00fdstup spr\u00e1v ladenia\n   -s              zakazuje volanie System.exit\n   -n <iterations> sp\u00fa\u0161\u0165a transform\u00e1ciu <iterations> r\u00e1z a \n                   zobrazuje inform\u00e1cie profilovania\n   -u <document_url> uv\u00e1dza vstupn\u00fd dokument XML ako URL\n"},



        /*
         * Note to translators:  "<xsl:sort>", "<xsl:for-each>" and
         * "<xsl:apply-templates>" are keywords that should not be translated.
         * The message indicates that an xsl:sort element must be a child of
         * one of the other kinds of elements mentioned.
         * <p>
         * 注意翻译者："<xsl：sort>","<xsl：for-each>"和"<xsl：apply-templates>"是不应翻译的关键字。
         * 消息指示xsl：sort元素必须是所提及的其他种类的元素的子元素。
         * 
         */
        {ErrorMsg.STRAY_SORT_ERR,
        "<xsl:sort> mo\u017eno pou\u017ei\u0165 len v <xsl:for-each> alebo <xsl:apply-templates>."},

        /*
         * Note to translators:  The message indicates that the encoding
         * requested for the output document was on that requires support that
         * is not available from the Java Virtual Machine being used to execute
         * the program.
         * <p>
         *  转换器注意事项：消息表明对输出文档请求的编码是需要支持的,而这些支持不能从用于执行程序的Java虚拟机上获得。
         * 
         */
        {ErrorMsg.UNSUPPORTED_ENCODING,
        "V\u00fdstupn\u00e9 k\u00f3dovanie ''{0}'' nie je v tomto JVM podporovan\u00e9."},

        /*
         * Note to translators:  The message indicates that the XPath expression
         * named in the substitution text was not well formed syntactically.
         * <p>
         *  注意翻译者：消息表明替换文本中命名的XPath表达式没有很好地形成语法。
         * 
         */
        {ErrorMsg.SYNTAX_ERR,
        "Chyba syntaxe v ''{0}''."},

        /*
         * Note to translators:  The substitution text is the name of a Java
         * class.  The term "constructor" here is the Java term.  The message is
         * displayed if XSLTC could not find a constructor for the specified
         * class.
         * <p>
         *  翻译者注意：替换文本是Java类的名称。这里的术语"构造函数"是Java术语。如果XSLTC找不到指定类的构造函数,则显示该消息。
         * 
         */
        {ErrorMsg.CONSTRUCTOR_NOT_FOUND,
        "Nie je mo\u017en\u00e9 n\u00e1js\u0165 extern\u00fd kon\u0161truktor ''{0}''."},

        /*
         * Note to translators:  "static" is the Java keyword.  The substitution
         * text is the name of a function.  The first argument of that function
         * is not of the required type.
         * <p>
         *  注意转换器："static"是Java关键字。替换文本是函数的名称。该函数的第一个参数不是必需的类型。
         * 
         */
        {ErrorMsg.NO_JAVA_FUNCT_THIS_REF,
        "Prv\u00fd argument pre nestatick\u00fa funkciu Java ''{0}'' nie je platnou referenciou objektu."},

        /*
         * Note to translators:  An XPath expression was not of the type
         * required in a particular context.  The substitution text is the
         * expression that was in error.
         * <p>
         *  翻译者注意：XPath表达式不是特定上下文中所需的类型。替换文本是错误的表达式。
         * 
         */
        {ErrorMsg.TYPE_CHECK_ERR,
        "Chyba pri kontrole typu v\u00fdrazu ''{0}''."},

        /*
         * Note to translators:  An XPath expression was not of the type
         * required in a particular context.  However, the location of the
         * problematic expression is unknown.
         * <p>
         *  翻译者注意：XPath表达式不是特定上下文中所需的类型。然而,有问题的表达式的位置是未知的。
         * 
         */
        {ErrorMsg.TYPE_CHECK_UNK_LOC_ERR,
        "Chyba pri kontrole typu v\u00fdrazu na nezn\u00e1mom mieste."},

        /*
         * Note to translators:  The substitution text is the name of a command-
         * line option that was not recognized.
         * <p>
         *  翻译者注意：替换文本是无法识别的命令行选项的名称。
         * 
         */
        {ErrorMsg.ILLEGAL_CMDLINE_OPTION_ERR,
        "Vo\u013eba pr\u00edkazov\u00e9ho riadka ''{0}'' je neplatn\u00e1."},

        /*
         * Note to translators:  The substitution text is the name of a command-
         * line option.
         * <p>
         *  注意翻译者：替换文本是命令行选项的名称。
         * 
         */
        {ErrorMsg.CMDLINE_OPT_MISSING_ARG_ERR,
        "Vo\u013ebe pr\u00edkazov\u00e9ho riadka ''{0}'' ch\u00fdba po\u017eadovan\u00fd argument."},

        /*
         * Note to translators:  This message is used to indicate the severity
         * of another message.  The substitution text contains two error
         * messages.  The spacing before the second substitution text indents
         * it the same amount as the first in English.
         * <p>
         * 翻译者注意：此消息用于指示另一封邮件的严重性。替换文本包含两个错误消息。第二个替换文本之前的间距缩进与英语中第一个相同的量。
         * 
         */
        {ErrorMsg.WARNING_PLUS_WRAPPED_MSG,
        "UPOZORNENIE:  ''{0}''\n       :{1}"},

        /*
         * Note to translators:  This message is used to indicate the severity
         * of another message.  The substitution text is an error message.
         * <p>
         *  翻译者注意：此消息用于指示另一封邮件的严重性。替换文本是错误消息。
         * 
         */
        {ErrorMsg.WARNING_MSG,
        "UPOZORNENIE:  ''{0}''"},

        /*
         * Note to translators:  This message is used to indicate the severity
         * of another message.  The substitution text contains two error
         * messages.  The spacing before the second substitution text indents
         * it the same amount as the first in English.
         * <p>
         *  翻译者注意：此消息用于指示另一封邮件的严重性。替换文本包含两个错误消息。第二个替换文本之前的间距缩进与英语中第一个相同的量。
         * 
         */
        {ErrorMsg.FATAL_ERR_PLUS_WRAPPED_MSG,
        "KRITICK\u00c1 CHYBA:  ''{0}''\n           :{1}"},

        /*
         * Note to translators:  This message is used to indicate the severity
         * of another message.  The substitution text is an error message.
         * <p>
         *  翻译者注意：此消息用于指示另一封邮件的严重性。替换文本是错误消息。
         * 
         */
        {ErrorMsg.FATAL_ERR_MSG,
        "KRITICK\u00c1 CHYBA:  ''{0}''"},

        /*
         * Note to translators:  This message is used to indicate the severity
         * of another message.  The substitution text contains two error
         * messages.  The spacing before the second substitution text indents
         * it the same amount as the first in English.
         * <p>
         *  翻译者注意：此消息用于指示另一封邮件的严重性。替换文本包含两个错误消息。第二个替换文本之前的间距缩进与英语中第一个相同的量。
         * 
         */
        {ErrorMsg.ERROR_PLUS_WRAPPED_MSG,
        "CHYBA:  ''{0}''\n     :{1}"},

        /*
         * Note to translators:  This message is used to indicate the severity
         * of another message.  The substitution text is an error message.
         * <p>
         *  翻译者注意：此消息用于指示另一封邮件的严重性。替换文本是错误消息。
         * 
         */
        {ErrorMsg.ERROR_MSG,
        "CHYBA:  ''{0}''"},

        /*
         * Note to translators:  The substitution text is the name of a class.
         * <p>
         *  翻译者注意：替换文本是类的名称。
         * 
         */
        {ErrorMsg.TRANSFORM_WITH_TRANSLET_STR,
        "Transform\u00e1cia pomocou transletu ''{0}'' "},

        /*
         * Note to translators:  The first substitution is the name of a class,
         * while the second substitution is the name of a jar file.
         * <p>
         *  注意翻译者：第一个替换是类的名称,而第二个替换是jar文件的名称。
         * 
         */
        {ErrorMsg.TRANSFORM_WITH_JAR_STR,
        "Transform\u00e1cia pomocou transletu ''{0}'' zo s\u00faboru jar ''{1}''"},

        /*
         * Note to translators:  "TransformerFactory" is the name of a Java
         * interface and must not be translated.  The substitution text is
         * the name of the class that could not be instantiated.
         * <p>
         * 注意转换器："TransformerFactory"是Java接口的名称,不能翻译。替换文本是无法实例化的类的名称。
         * 
         */
        {ErrorMsg.COULD_NOT_CREATE_TRANS_FACT,
        "Nebolo mo\u017en\u00e9 vytvori\u0165 in\u0161tanciu triedy TransformerFactory ''{0}''."},

        /*
         * Note to translators:  The following message is used as a header.
         * All the error messages are collected together and displayed beneath
         * this message.
         * <p>
         *  翻译者注意：以下消息用作标题。所有错误消息将一起收集并显示在此消息的下方。
         * 
         */
        {ErrorMsg.COMPILER_ERROR_KEY,
        "Chyby preklada\u010da:"},

        /*
         * Note to translators:  The following message is used as a header.
         * All the warning messages are collected together and displayed
         * beneath this message.
         * <p>
         *  翻译者注意：以下消息用作标题。所有警告消息将一起收集并显示在此消息的下方。
         * 
         */
        {ErrorMsg.COMPILER_WARNING_KEY,
        "Upozornenia preklada\u010da:"},

        /*
         * Note to translators:  The following message is used as a header.
         * All the error messages that are produced when the stylesheet is
         * applied to an input document are collected together and displayed
         * beneath this message.  A 'translet' is the compiled form of a
         * stylesheet (see above).
         * <p>
         *  翻译者注意：以下消息用作标题。将样式表应用于输入文档时生成的所有错误消息一起收集并显示在此消息的下方。 "translet"是样式表的编译形式(见上文)。
         */
        {ErrorMsg.RUNTIME_ERROR_KEY,
        "Chyby transletu:"},

        {ErrorMsg.JAXP_SECUREPROCESSING_FEATURE,
        "FEATURE_SECURE_PROCESSING: Cannot set the feature to false when security manager is present."}
    };

    }
}
