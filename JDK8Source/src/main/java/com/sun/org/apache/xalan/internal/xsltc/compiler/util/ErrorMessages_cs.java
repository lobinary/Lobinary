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
 * $Id: ErrorMessages_cs.java,v 1.1.6.1 2005/09/05 11:52:59 pvedula Exp $
 * <p>
 *  $ Id：ErrorMessages_cs.java,v 1.1.6.1 2005/09/05 11:52:59 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.compiler.util;

import java.util.ListResourceBundle;

/**
/* <p>
/* 
 * @author Morten Jorgensen
 */
public class ErrorMessages_cs extends ListResourceBundle {

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
        "V\u00edce ne\u017e jedna p\u0159edloha stylu je definov\u00e1na ve stejn\u00e9m souboru."},

        /*
         * Note to translators:  The substitution text is the name of a
         * template.  The same name was used on two different templates in the
         * same stylesheet.
         * <p>
         *  翻译者注意：替换文本是模板的名称。在同一样式表中的两个不同模板上使用了相同的名称。
         * 
         */
        {ErrorMsg.TEMPLATE_REDEF_ERR,
        "\u0160ablona ''{0}'' je ji\u017e v t\u00e9to p\u0159edloze stylu definov\u00e1na."},


        /*
         * Note to translators:  The substitution text is the name of a
         * template.  A reference to the template name was encountered, but the
         * template is undefined.
         * <p>
         *  翻译者注意：替换文本是模板的名称。遇到对模板名称的引用,但模板未定义。
         * 
         */
        {ErrorMsg.TEMPLATE_UNDEF_ERR,
        "\u0160ablona ''{0}'' nen\u00ed v t\u00e9to p\u0159edloze stylu definov\u00e1na."},

        /*
         * Note to translators:  The substitution text is the name of a variable
         * that was defined more than once.
         * <p>
         *  对翻译者的注意：替换文本是被不止一次定义的变量的名称。
         * 
         */
        {ErrorMsg.VARIABLE_REDEF_ERR,
        "Prom\u011bnn\u00e1 ''{0}'' je n\u011bkolikan\u00e1sobn\u011b definov\u00e1na ve stejn\u00e9m oboru."},

        /*
         * Note to translators:  The substitution text is the name of a variable
         * or parameter.  A reference to the variable or parameter was found,
         * but it was never defined.
         * <p>
         *  翻译者注意：替换文本是变量或参数的名称。发现对变量或参数的引用,但它从未定义。
         * 
         */
        {ErrorMsg.VARIABLE_UNDEF_ERR,
        "Prom\u011bnn\u00e1 nebo parametr ''{0}'' nejsou definov\u00e1ny."},

        /*
         * Note to translators:  The word "class" here refers to a Java class.
         * Processing the stylesheet required a class to be loaded, but it could
         * not be found.  The substitution text is the name of the class.
         * <p>
         *  注意翻译者：这里的"类"这个词指的是Java类。处理样式表需要加载一个类,但无法找到它。替换文本是类的名称。
         * 
         */
        {ErrorMsg.CLASS_NOT_FOUND_ERR,
        "Nelze naj\u00edt t\u0159\u00eddu ''{0}''."},

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
        "Nelze naj\u00edt extern\u00ed metodu ''{0}'' (mus\u00ed b\u00fdt ve\u0159ejn\u00e1)."},

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
        "Nelze p\u0159ev\u00e9st argument/n\u00e1vratov\u00fd typ ve vol\u00e1n\u00ed metody ''{0}''"},

        /*
         * Note to translators:  The file or URI named in the substitution text
         * is missing.
         * <p>
         *  翻译者注意：替换文本中命名的文件或URI丢失。
         * 
         */
        {ErrorMsg.FILE_NOT_FOUND_ERR,
        "Soubor nebo URI ''{0}'' nebyl nalezen."},

        /*
         * Note to translators:  This message is displayed when the URI
         * mentioned in the substitution text is not well-formed syntactically.
         * <p>
         *  注意翻译者：当替换文本中提到的URI语法不正确时,将显示此消息。
         * 
         */
        {ErrorMsg.INVALID_URI_ERR,
        "Neplatn\u00e9 URI ''{0}''."},

        /*
         * Note to translators:  The file or URI named in the substitution text
         * exists but could not be opened.
         * <p>
         *  翻译者注意：替换文本中命名的文件或URI存在,但无法打开。
         * 
         */
        {ErrorMsg.FILE_ACCESS_ERR,
        "Nelze otev\u0159\u00edt soubor nebo URI ''{0}''."},

        /*
         * Note to translators: <xsl:stylesheet> and <xsl:transform> are
         * keywords that should not be translated.
         * <p>
         *  注意翻译者：<xsl：stylesheet>和<xsl：transform>是不应翻译的关键字。
         * 
         */
        {ErrorMsg.MISSING_ROOT_ERR,
        "Byl o\u010dek\u00e1v\u00e1n prvek <xsl:stylesheet> nebo <xsl:transform>."},

        /*
         * Note to translators:  The stylesheet contained a reference to a
         * namespace prefix that was undefined.  The value of the substitution
         * text is the name of the prefix.
         * <p>
         *  翻译者注意：样式表包含对未定义的命名空间前缀的引用。替换文本的值是前缀的名称。
         * 
         */
        {ErrorMsg.NAMESPACE_UNDEF_ERR,
        "P\u0159edpona oboru n\u00e1zv\u016f ''{0}'' nen\u00ed deklarov\u00e1na."},

        /*
         * Note to translators:  The Java function named in the stylesheet could
         * not be found.
         * <p>
         *  翻译者注意：找不到样式表中命名的Java函数。
         * 
         */
        {ErrorMsg.FUNCTION_RESOLVE_ERR,
        "Nelze vy\u0159e\u0161it vol\u00e1n\u00ed funkce ''{0}''."},

        /*
         * Note to translators:  The substitution text is the name of a
         * function.  A literal string here means a constant string value.
         * <p>
         *  注意翻译者：替换文本是函数的名称。这里的文字字符串意味着一个常量字符串值。
         * 
         */
        {ErrorMsg.NEED_LITERAL_ERR,
        "Argument pro ''{0}'' mus\u00ed b\u00fdt \u0159et\u011bzcem liter\u00e1lu."},

        /*
         * Note to translators:  This message indicates there was a syntactic
         * error in the form of an XPath expression.  The substitution text is
         * the expression.
         * <p>
         *  注意翻译者：此消息指示在XPath表达式的形式中存在句法错误。替换文本是表达式。
         * 
         */
        {ErrorMsg.XPATH_PARSER_ERR,
        "Chyba p\u0159i anal\u00fdze v\u00fdrazu XPath ''{0}''."},

        /*
         * Note to translators:  An element in the stylesheet requires a
         * particular attribute named by the substitution text, but that
         * attribute was not specified in the stylesheet.
         * <p>
         *  翻译者注意：样式表中的元素需要由替换文本命名的特定属性,但该属性未在样式表中指定。
         * 
         */
        {ErrorMsg.REQUIRED_ATTR_ERR,
        "Po\u017eadovan\u00fd atribut ''{0}'' chyb\u00ed."},

        /*
         * Note to translators:  This message indicates that a character not
         * permitted in an XPath expression was encountered.  The substitution
         * text is the offending character.
         * <p>
         * 翻译者注意：此消息指示遇到XPath表达式中不允许的字符。替换文本是令人讨厌的字符。
         * 
         */
        {ErrorMsg.ILLEGAL_CHAR_ERR,
        "Neplatn\u00fd znak ''{0}'' ve v\u00fdrazu XPath."},

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
        "Neplatn\u00fd n\u00e1zev ''{0}'' pro zpracov\u00e1n\u00ed instrukce."},

        /*
         * Note to translators:  This message is reported if the stylesheet
         * being processed attempted to construct an XML document with an
         * attribute in a place other than on an element.  The substitution text
         * specifies the name of the attribute.
         * <p>
         *  翻译者注意：如果正在处理的样式表试图在一个元素之外的地方构造具有属性的XML文档,则会报告此消息。替换文本指定属性的名称。
         * 
         */
        {ErrorMsg.STRAY_ATTRIBUTE_ERR,
        "Atribut ''{0}'' je vn\u011b prvku."},

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
        "Neplatn\u00fd atribut ''{0}''."},

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
        "Cyklick\u00fd import/zahrnut\u00ed. P\u0159edloha stylu ''{0}'' je ji\u017e zavedena."},

        /*
         * Note to translators:  A result-tree fragment is a portion of a
         * resulting XML document represented as a tree.  "<xsl:sort>" is a
         * keyword and should not be translated.
         * <p>
         *  对翻译者的注意：结果树片段是作为树表示的结果XML文档的一部分。 "<xsl：sort>"是关键字,不应翻译。
         * 
         */
        {ErrorMsg.RESULT_TREE_SORT_ERR,
        "Fragmenty stromu v\u00fdsledk\u016f nemohou b\u00fdt \u0159azeny (prvky <xsl:sort> se ignoruj\u00ed). P\u0159i vytv\u00e1\u0159en\u00ed stromu v\u00fdsledk\u016f mus\u00edte se\u0159adit uzly."},

        /*
         * Note to translators:  A name can be given to a particular style to be
         * used to format decimal values.  The substitution text gives the name
         * of such a style for which more than one declaration was encountered.
         * <p>
         *  对翻译者的注意：可以给一个名称赋予用于格式十进制值的特定样式。替换文本给出了遇到多个声明的样式的名称。
         * 
         */
        {ErrorMsg.SYMBOLS_REDEF_ERR,
        "Desetinn\u00e9 form\u00e1tov\u00e1n\u00ed ''{0}'' je ji\u017e definov\u00e1no."},

        /*
         * Note to translators:  The stylesheet version named in the
         * substitution text is not supported.
         * <p>
         *  注意翻译者：不支持在替换文本中命名的样式表版本。
         * 
         */
        {ErrorMsg.XSL_VERSION_ERR,
        "Verze XSL ''{0}'' nen\u00ed produktem XSLTC podporov\u00e1na."},

        /*
         * Note to translators:  The definitions of one or more variables or
         * parameters depend on one another.
         * <p>
         * 翻译者注意：一个或多个变量或参数的定义取决于彼此。
         * 
         */
        {ErrorMsg.CIRCULAR_VARIABLE_ERR,
        "Cyklick\u00fd odkaz na prom\u011bnnou/parametr v ''{0}''."},

        /*
         * Note to translators:  The operator in an expresion with two operands was
         * not recognized.
         * <p>
         *  对翻译者的注意：无法识别具有两个操作数的表达式中的运算符。
         * 
         */
        {ErrorMsg.ILLEGAL_BINARY_OP_ERR,
        "Nezn\u00e1m\u00fd oper\u00e1tor pro bin\u00e1rn\u00ed v\u00fdraz."},

        /*
         * Note to translators:  This message is produced if a reference to a
         * function has too many or too few arguments.
         * <p>
         *  对翻译者的注意：如果对一个函数的引用具有太多或太少的参数,则会产生此消息。
         * 
         */
        {ErrorMsg.ILLEGAL_ARG_ERR,
        "Neplatn\u00fd argument pro vol\u00e1n\u00ed funkce."},

        /*
         * Note to translators:  "document()" is the name of function and must
         * not be translated.  A node-set is a set of the nodes in the tree
         * representation of an XML document.
         * <p>
         *  注意翻译者："document()"是函数的名称,不能翻译。节点集是XML文档的树表示中的一组节点。
         * 
         */
        {ErrorMsg.DOCUMENT_ARG_ERR,
        "Druh\u00fd argument pro funkci document() mus\u00ed b\u00fdt node-set."},

        /*
         * Note to translators:  "<xsl:when>" and "<xsl:choose>" are keywords
         * and should not be translated.  This message describes a syntax error
         * in the stylesheet.
         * <p>
         *  注意翻译者："<xsl：when>"和"<xsl：choose>"是关键字,不应翻译。此消息描述样式表中的语法错误。
         * 
         */
        {ErrorMsg.MISSING_WHEN_ERR,
        "Alespo\u0148 jeden prvek <xsl:when> se vy\u017eaduje v <xsl:choose>."},

        /*
         * Note to translators:  "<xsl:otherwise>" and "<xsl:choose>" are
         * keywords and should not be translated.  This message describes a
         * syntax error in the stylesheet.
         * <p>
         *  注意翻译者："<xsl：other>"和"<xsl：choose>"是关键字,不应翻译。此消息描述样式表中的语法错误。
         * 
         */
        {ErrorMsg.MULTIPLE_OTHERWISE_ERR,
        "Jen jeden prvek <xsl:otherwise> je povolen v <xsl:choose>."},

        /*
         * Note to translators:  "<xsl:otherwise>" and "<xsl:choose>" are
         * keywords and should not be translated.  This message describes a
         * syntax error in the stylesheet.
         * <p>
         *  注意翻译者："<xsl：other>"和"<xsl：choose>"是关键字,不应翻译。此消息描述样式表中的语法错误。
         * 
         */
        {ErrorMsg.STRAY_OTHERWISE_ERR,
        "Prvek <xsl:otherwise> m\u016f\u017ee b\u00fdt pou\u017eit jen v <xsl:choose>."},

        /*
         * Note to translators:  "<xsl:when>" and "<xsl:choose>" are keywords
         * and should not be translated.  This message describes a syntax error
         * in the stylesheet.
         * <p>
         *  注意翻译者："<xsl：when>"和"<xsl：choose>"是关键字,不应翻译。此消息描述样式表中的语法错误。
         * 
         */
        {ErrorMsg.STRAY_WHEN_ERR,
        "Prvek <xsl:when> m\u016f\u017ee b\u00fdt pou\u017eit jen v <xsl:choose>."},

        /*
         * Note to translators:  "<xsl:when>", "<xsl:otherwise>" and
         * "<xsl:choose>" are keywords and should not be translated.  This
         * message describes a syntax error in the stylesheet.
         * <p>
         *  注意翻译者："<xsl：when>","<xsl：other>"和"<xsl：choose>"是关键字,不应翻译。此消息描述样式表中的语法错误。
         * 
         */
        {ErrorMsg.WHEN_ELEMENT_ERR,
        "Pouze prvky <xsl:when> a <xsl:otherwise> jsou povoleny v <xsl:choose>."},

        /*
         * Note to translators:  "<xsl:attribute-set>" and "name" are keywords
         * that should not be translated.
         * <p>
         *  注意翻译者："<xsl：attribute-set>"和"name"是不应翻译的关键字。
         * 
         */
        {ErrorMsg.UNNAMED_ATTRIBSET_ERR,
        "V prvku <xsl:attribute-set> chyb\u00ed atribut 'name'."},

        /*
         * Note to translators:  An element in the stylesheet contained an
         * element of a type that it was not permitted to contain.
         * <p>
         * 翻译者注意：样式表中的元素包含不允许包含的类型的元素。
         * 
         */
        {ErrorMsg.ILLEGAL_CHILD_ERR,
        "Neplatn\u00fd prvek potomka."},

        /*
         * Note to translators:  The stylesheet tried to create an element with
         * a name that was not a valid XML name.  The substitution text contains
         * the name.
         * <p>
         *  翻译者注意：样式表试图创建一个名称不是有效XML名称的元素。替换文本包含名称。
         * 
         */
        {ErrorMsg.ILLEGAL_ELEM_NAME_ERR,
        "Nelze volat prvek ''{0}''"},

        /*
         * Note to translators:  The stylesheet tried to create an attribute
         * with a name that was not a valid XML name.  The substitution text
         * contains the name.
         * <p>
         *  翻译者注意：样式表试图创建一个名称不是有效XML名称的属性。替换文本包含名称。
         * 
         */
        {ErrorMsg.ILLEGAL_ATTR_NAME_ERR,
        "Nelze volat atribut ''{0}''"},

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
        "Textov\u00e1 data jsou vn\u011b prvku nejvy\u0161\u0161\u00ed \u00farovn\u011b <xsl:stylesheet>."},

        /*
         * Note to translators:  JAXP is an acronym for the Java API for XML
         * Processing.  This message indicates that the XML parser provided to
         * XSLTC to process the XML input document had a configuration problem.
         * <p>
         *  翻译者注意：JAXP是Java API for XML Processing的缩写。此消息表明提供给XSLTC以处理XML输入文档的XML解析器有一个配置问题。
         * 
         */
        {ErrorMsg.SAX_PARSER_CONFIG_ERR,
        "Analyz\u00e1tor JAXP je nespr\u00e1vn\u011b konfigurov\u00e1n."},

        /*
         * Note to translators:  The substitution text names the internal error
         * encountered.
         * <p>
         *  注意翻译者：替换文本命名遇到的内部错误。
         * 
         */
        {ErrorMsg.INTERNAL_ERR,
        "Neopraviteln\u00e1 chyba XSLTC-internal: ''{0}''"},

        /*
         * Note to translators:  The stylesheet contained an element that was
         * not recognized as part of the XSL syntax.  The substitution text
         * gives the element name.
         * <p>
         *  对翻译者的注意：样式表包含一个未被识别为XSL语法一部分的元素。替换文本提供元素名称。
         * 
         */
        {ErrorMsg.UNSUPPORTED_XSL_ERR,
        "Nepodporovan\u00fd prvek XSL ''{0}''."},

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
        "Nerozpoznan\u00e1 p\u0159\u00edpona XSLTC ''{0}''."},

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
        "Vstupn\u00ed dokument nen\u00ed p\u0159edloha stylu (obor n\u00e1zv\u016f XSL nen\u00ed deklarov\u00e1n v ko\u0159enov\u00e9m elementu)."},

        /*
         * Note to translators:  XSLTC could not find the stylesheet document
         * with the name specified by the substitution text.
         * <p>
         *  对翻译者的注意：XSLTC找不到具有替换文本指定的名称的样式表文档。
         * 
         */
        {ErrorMsg.MISSING_XSLT_TARGET_ERR,
        "Nelze naj\u00edt c\u00edlovou p\u0159edlohu se stylem ''{0}''."},

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
        "Neimplementov\u00e1no: ''{0}''."},

        /*
         * Note to translators:  The XML document given to XSLTC as a stylesheet
         * was not, in fact, a stylesheet.
         * <p>
         *  对翻译者的注意：给XSLTC作为样式表的XML文档实际上不是样式表。
         * 
         */
        {ErrorMsg.NOT_STYLESHEET_ERR,
        "Vstupn\u00ed dokument neobsahuje p\u0159edlohu stylu XSL."},

        /*
         * Note to translators:  The element named in the substitution text was
         * encountered in the stylesheet but is not recognized.
         * <p>
         *  翻译者注意：替换文本中命名的元素在样式表中遇到,但无法识别。
         * 
         */
        {ErrorMsg.ELEMENT_PARSE_ERR,
        "Nelze analyzovat prvek ''{0}''"},

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
        "Atribut use prom\u011bnn\u00e9 <key> mus\u00ed b\u00fdt typu node, node-set, string nebo number."},

        /*
         * Note to translators:  An XML document can specify the version of the
         * XML specification to which it adheres.  This message indicates that
         * the version specified for the output document was not valid.
         * <p>
         *  翻译者注意：XML文档可以指定它所遵循的XML规范的版本。此消息表明为输出文档指定的版本无效。
         * 
         */
        {ErrorMsg.OUTPUT_VERSION_ERR,
        "V\u00fdstupn\u00ed verze dokumentu XML by m\u011bla b\u00fdt 1.0"},

        /*
         * Note to translators:  The operator in a comparison operation was
         * not recognized.
         * <p>
         *  对翻译员的注释：无法识别比较操作中的运算符。
         * 
         */
        {ErrorMsg.ILLEGAL_RELAT_OP_ERR,
        "Nezn\u00e1m\u00fd oper\u00e1tor pro rela\u010dn\u00ed v\u00fdraz"},

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
        "Pokus pou\u017e\u00edt neexistuj\u00edc\u00ed sadu atribut\u016f ''{0}''."},

        /*
         * Note to translators:  The term "attribute value template" is a term
         * defined by XSLT which describes the value of an attribute that is
         * determined by an XPath expression.  The message indicates that the
         * expression was syntactically incorrect; the substitution text
         * contains the expression that was in error.
         * <p>
         *  对翻译者的注解：术语"属性值模板"是由XSLT定义的术语,用于描述由XPath表达式确定的属性的值。消息指示表达式在语法上不正确;替换文本包含错误的表达式。
         * 
         */
        {ErrorMsg.ATTR_VAL_TEMPLATE_ERR,
        "Nelze analyzovat \u0161ablonu hodnoty atributu ''{0}''."},

        /*
         * Note to translators:  ???
         * <p>
         *  注意翻译：???
         * 
         */
        {ErrorMsg.UNKNOWN_SIG_TYPE_ERR,
        "Nezn\u00e1m\u00fd datov\u00fd typ prom\u011bnn\u00e9 signature pro t\u0159\u00eddu ''{0}''."},

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
        "Nelze p\u0159ev\u00e9st datov\u00fd typ ''{0}'' na ''{1}''."},

        /*
         * Note to translators:  "Templates" is a Java class name that should
         * not be translated.
         * <p>
         *  注意翻译者："模板"是不应该翻译的Java类名。
         * 
         */
        {ErrorMsg.NO_TRANSLET_CLASS_ERR,
        "Tato \u0161ablona neobsahuje platnou definici t\u0159\u00eddy translet."},

        /*
         * Note to translators:  "Templates" is a Java class name that should
         * not be translated.
         * <p>
         *  注意翻译者："模板"是不应该翻译的Java类名。
         * 
         */
        {ErrorMsg.NO_MAIN_TRANSLET_ERR,
        "Tato \u0161ablona neobsahuje t\u0159\u00eddu se jm\u00e9nem ''{0}''."},

        /*
         * Note to translators:  The substitution text is the name of a class.
         * <p>
         *  翻译者注意：替换文本是类的名称。
         * 
         */
        {ErrorMsg.TRANSLET_CLASS_ERR,
        "Nelze zav\u00e9st t\u0159\u00eddu translet ''{0}''."},

        {ErrorMsg.TRANSLET_OBJECT_ERR,
        "T\u0159\u00edda translet byla zavedena, av\u0161ak nelze vytvo\u0159it instanci translet."},

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
        "Pokus nastavit objekt ErrorListener pro ''{0}'' na hodnotu null"},

        /*
         * Note to translators:  StreamSource, SAXSource and DOMSource are Java
         * interface names that should not be translated.
         * <p>
         *  注意翻译者：StreamSource,SAXSource和DOMSource是不应该翻译的Java接口名称。
         * 
         */
        {ErrorMsg.JAXP_UNKNOWN_SOURCE_ERR,
        "Pouze prom\u011bnn\u00e9 StreamSource, SAXSource a DOMSource jsou podporov\u00e1ny produktem XSLTC"},

        /*
         * Note to translators:  "Source" is a Java class name that should not
         * be translated.  The substitution text is the name of Java method.
         * <p>
         * 注意翻译者："源"是一个Java类名,不应该翻译。替换文本是Java方法的名称。
         * 
         */
        {ErrorMsg.JAXP_NO_SOURCE_ERR,
        "Zdrojov\u00fd objekt p\u0159edan\u00fd ''{0}'' nem\u00e1 \u017e\u00e1dn\u00fd obsah."},

        /*
         * Note to translators:  The message indicates that XSLTC failed to
         * compile the stylesheet into a translet (class file).
         * <p>
         *  注意翻译者：消息指示XSLTC未能将样式表编译为translet(类文件)。
         * 
         */
        {ErrorMsg.JAXP_COMPILE_ERR,
        "Nelze kompilovat p\u0159edlohu se stylem"},

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
        "T\u0159\u00edda TransformerFactory nerozpoznala atribut ''{0}''."},

        /*
         * Note to translators:  "setResult()" and "startDocument()" are Java
         * method names that should not be translated.
         * <p>
         *  注意翻译者："setResult()"和"startDocument()"是不应该翻译的Java方法名称。
         * 
         */
        {ErrorMsg.JAXP_SET_RESULT_ERR,
        "Metoda setResult() mus\u00ed b\u00fdt vol\u00e1na p\u0159ed metodou startDocument()."},

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
        "Objekt Transformer nem\u00e1 \u017e\u00e1dn\u00fd zapouzd\u0159en\u00fd objekt translet."},

        /*
         * Note to translators:  The XML document that results from a
         * transformation needs to be sent to an output handler object; this
         * message is produced if that requirement is not met.
         * <p>
         *  对转换器的注意：从转换产生的XML文档需要发送到输出处理程序对象;如果不满足该要求,则生成此消息。
         * 
         */
        {ErrorMsg.JAXP_NO_HANDLER_ERR,
        "Neexistuje \u017e\u00e1dn\u00fd definovan\u00fd v\u00fdstupn\u00ed obslu\u017en\u00fd program pro v\u00fdsledek transformace."},

        /*
         * Note to translators:  "Result" is a Java interface name in this
         * context.  The substitution text is a method name.
         * <p>
         *  对于翻译者的注意："Result"是此上下文中的Java接口名称。替换文本是一个方法名称。
         * 
         */
        {ErrorMsg.JAXP_NO_RESULT_ERR,
        "V\u00fdsledn\u00fd objekt p\u0159edan\u00fd ''{0}'' je neplatn\u00fd."},

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
        "Pokus o p\u0159\u00edstup k neplatn\u00e9 vlastnosti objektu Transformer: ''{0}''."},

        /*
         * Note to translators:  SAX2DOM is the name of a Java class that should
         * not be translated.  This is an adapter in the sense that it takes a
         * DOM object and converts it to something that uses the SAX API.
         * <p>
         *  注意翻译者：SAX2DOM是不应被翻译的Java类的名称。这是一个适配器,它意味着它需要一个DOM对象并将其转换为使用SAX API的东西。
         * 
         */
        {ErrorMsg.SAX2DOM_ADAPTER_ERR,
        "Nelze vytvo\u0159it adapt\u00e9r SAX2DOM: ''{0}''."},

        /*
         * Note to translators:  "XSLTCSource.build()" is a Java method name.
         * "systemId" is an XML term that is short for "system identification".
         * <p>
         *  注意翻译者："XSLTCSource.build()"是一个Java方法名称。 "systemId"是"系统标识"的缩写的XML术语。
         * 
         */
        {ErrorMsg.XSLTC_SOURCE_ERR,
        "Byla vol\u00e1na metoda XSLTCSource.build(), ani\u017e by byla nastavena hodnota systemId."},


        {ErrorMsg.COMPILE_STDIN_ERR,
        "Volba -i mus\u00ed b\u00fdt pou\u017eita s volbou -o."},


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
         * 字符串<output>,<directory>等表示用户指定的参数值,并且可以翻译 - 参数<package>是指Java包,因此应该按照与处理JDK相同的方式处理文档。
         * 
         */
        {ErrorMsg.COMPILE_USAGE_STR,
        "SYNOPSIS\n   java com.sun.org.apache.xalan.internal.xsltc.cmdline.Compile [-o <output>]\n      [-d <directory>] [-j <jarfile>] [-p <package>]\n      [-n] [-x] [-s] [-u] [-v] [-h] { <stylesheet> | -i }\n\nVOLBY\n   -o <output>    p\u0159i\u0159azuje n\u00e1zev <output> generovan\u00e9mu\n                  transletu. Standardn\u011b je n\u00e1zev transletu\n                  p\u0159evzat z n\u00e1zvu <stylesheet>. Tato volba\n                   se ignoruje, pokud se kompiluj\u00ed n\u00e1sobn\u00e9 p\u0159edlohy styl\u016f.\n   -d <directory> ur\u010duje v\u00fdchoz\u00ed adres\u00e1\u0159 pro translet\n   -j <jarfile>   zabal\u00ed t\u0159\u00eddu transletu do souboru jar\n     pojmenovan\u00e9ho jako <jarfile>\n   -p <package>   ur\u010duje p\u0159edponu n\u00e1zvu bal\u00ed\u010dku pro v\u0161echny generovan\u00e9 \n t\u0159\u00eddy transletu.\n   -n             povoluje zarovn\u00e1n\u00ed \u0161ablony (v\u00fdchoz\u00ed chov\u00e1n\u00ed je v pr\u016fm\u011bru lep\u0161\u00ed\n                  .\n   -x             zapne dal\u0161\u00ed v\u00fdstup zpr\u00e1vy lad\u011bn\u00ed\n   -s             zak\u00e1\u017ee vol\u00e1n\u00ed System.exit\n   -u             interpretuje <stylesheet> argumenty jako URL\n   -i             vynut\u00ed kompil\u00e1tor \u010d\u00edst p\u0159edlohu styl\u016f ze stdin\n   -v             tiskne verzi kompil\u00e1toru \n   -h             tiskne v\u00fdpis tohoto pou\u017eit\u00ed \n"},

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
        "SYNOPSIS \n   java com.sun.org.apache.xalan.internal.xsltc.cmdline.Transform [-j <jarfile>]\n      [-x] [-s] [-n <iterations>] {-u <document_url> | <document>}\n      <class> [<param1>=<value1> ...]\n\n   pou\u017eije translet <class> k transformaci dokumentu XML \n ur\u010den\u00e9ho jako <document>. Translet <class> je bu\u010f v\n   v u\u017eivatelsk\u00e9 cest\u011b CLASSPATH nebo ve voliteln\u011b ur\u010den\u00e9m souboru <jarfile>.\nVOLBY\n     -j <jarfile>    ur\u010duje soubor jarfile, ze kter\u00e9ho se zavede translet\n   -x      p\u0159evede dal\u0161\u00ed v\u00fdstup zpr\u00e1vy lad\u011bn\u00ed\n   -s              vypne vol\u00e1n\u00ed System.exit\n   -n <iterations> spust\u00ed transformaci <iterations> kr\u00e1t a\n                   zobraz\u00ed informaci  o profilu\n   -u <document_url> ur\u010d\u00ed vstupn\u00ed dokument XML jako URL\n"},



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
        "Prvek <xsl:sort> m\u016f\u017ee b\u00fdt pou\u017eit jen v <xsl:for-each> nebo <xsl:apply-templates>."},

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
        "V\u00fdstupn\u00ed k\u00f3dov\u00e1n\u00ed ''{0}'' nen\u00ed v tomto prost\u0159ed\u00ed JVM podporov\u00e1no."},

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
        "Nelze naj\u00edt vn\u011bj\u0161\u00ed konstruktor ''{0}''."},

        /*
         * Note to translators:  "static" is the Java keyword.  The substitution
         * text is the name of a function.  The first argument of that function
         * is not of the required type.
         * <p>
         *  注意转换器："static"是Java关键字。替换文本是函数的名称。该函数的第一个参数不是必需的类型。
         * 
         */
        {ErrorMsg.NO_JAVA_FUNCT_THIS_REF,
        "Prvn\u00ed argument nestatick\u00e9 funkce Java ''{0}'' nen\u00ed platn\u00fdm odkazem na objekt."},

        /*
         * Note to translators:  An XPath expression was not of the type
         * required in a particular context.  The substitution text is the
         * expression that was in error.
         * <p>
         *  翻译者注意：XPath表达式不是特定上下文中所需的类型。替换文本是错误的表达式。
         * 
         */
        {ErrorMsg.TYPE_CHECK_ERR,
        "Chyba p\u0159i kontrole typu v\u00fdrazu ''{0}''."},

        /*
         * Note to translators:  An XPath expression was not of the type
         * required in a particular context.  However, the location of the
         * problematic expression is unknown.
         * <p>
         *  翻译者注意：XPath表达式不是特定上下文中所需的类型。然而,有问题的表达式的位置是未知的。
         * 
         */
        {ErrorMsg.TYPE_CHECK_UNK_LOC_ERR,
        "Chyba p\u0159i kontrole typu v\u00fdrazu na nezn\u00e1m\u00e9m m\u00edst\u011b."},

        /*
         * Note to translators:  The substitution text is the name of a command-
         * line option that was not recognized.
         * <p>
         *  翻译者注意：替换文本是无法识别的命令行选项的名称。
         * 
         */
        {ErrorMsg.ILLEGAL_CMDLINE_OPTION_ERR,
        "Volba p\u0159\u00edkazov\u00e9ho \u0159\u00e1dku ''{0}'' nen\u00ed platn\u00e1."},

        /*
         * Note to translators:  The substitution text is the name of a command-
         * line option.
         * <p>
         *  注意翻译者：替换文本是命令行选项的名称。
         * 
         */
        {ErrorMsg.CMDLINE_OPT_MISSING_ARG_ERR,
        "Volb\u011b p\u0159\u00edkazov\u00e9ho \u0159\u00e1dku ''{0}'' chyb\u00ed po\u017eadovan\u00fd argument."},

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
        "VAROV\u00c1N\u00cd: ''{0}''\n        :{1}"},

        /*
         * Note to translators:  This message is used to indicate the severity
         * of another message.  The substitution text is an error message.
         * <p>
         *  翻译者注意：此消息用于指示另一封邮件的严重性。替换文本是错误消息。
         * 
         */
        {ErrorMsg.WARNING_MSG,
        "VAROV\u00c1N\u00cd: ''{0}''"},

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
        "Z\u00c1VA\u017dN\u00c1 CHYBA: ''{0}''\n             :{1}"},

        /*
         * Note to translators:  This message is used to indicate the severity
         * of another message.  The substitution text is an error message.
         * <p>
         *  翻译者注意：此消息用于指示另一封邮件的严重性。替换文本是错误消息。
         * 
         */
        {ErrorMsg.FATAL_ERR_MSG,
        "Z\u00c1VA\u017dN\u00c1 CHYBA: ''{0}''"},

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
        "CHYBA: ''{0}''\n     :{1}"},

        /*
         * Note to translators:  This message is used to indicate the severity
         * of another message.  The substitution text is an error message.
         * <p>
         *  翻译者注意：此消息用于指示另一封邮件的严重性。替换文本是错误消息。
         * 
         */
        {ErrorMsg.ERROR_MSG,
        "CHYBA: ''{0}''"},

        /*
         * Note to translators:  The substitution text is the name of a class.
         * <p>
         *  翻译者注意：替换文本是类的名称。
         * 
         */
        {ErrorMsg.TRANSFORM_WITH_TRANSLET_STR,
        "Transformace pou\u017eit\u00edm transletu ''{0}'' "},

        /*
         * Note to translators:  The first substitution is the name of a class,
         * while the second substitution is the name of a jar file.
         * <p>
         *  注意翻译者：第一个替换是类的名称,而第二个替换是jar文件的名称。
         * 
         */
        {ErrorMsg.TRANSFORM_WITH_JAR_STR,
        "Transformace pou\u017eit\u00edm transletu ''{0}'' ze souboru jar ''{1}''"},

        /*
         * Note to translators:  "TransformerFactory" is the name of a Java
         * interface and must not be translated.  The substitution text is
         * the name of the class that could not be instantiated.
         * <p>
         * 注意转换器："TransformerFactory"是Java接口的名称,不能翻译。替换文本是无法实例化的类的名称。
         * 
         */
        {ErrorMsg.COULD_NOT_CREATE_TRANS_FACT,
        "Nelze vytvo\u0159it instanci t\u0159\u00eddy TransformerFactory ''{0}''."},

        /*
         * Note to translators:  The following message is used as a header.
         * All the error messages are collected together and displayed beneath
         * this message.
         * <p>
         *  翻译者注意：以下消息用作标题。所有错误消息将一起收集并显示在此消息的下方。
         * 
         */
        {ErrorMsg.COMPILER_ERROR_KEY,
        "Chyby kompil\u00e1toru:"},

        /*
         * Note to translators:  The following message is used as a header.
         * All the warning messages are collected together and displayed
         * beneath this message.
         * <p>
         *  翻译者注意：以下消息用作标题。所有警告消息将一起收集并显示在此消息的下方。
         * 
         */
        {ErrorMsg.COMPILER_WARNING_KEY,
        "Varov\u00e1n\u00ed kompil\u00e1toru:"},

        /*
         * Note to translators:  The following message is used as a header.
         * All the error messages that are produced when the stylesheet is
         * applied to an input document are collected together and displayed
         * beneath this message.  A 'translet' is the compiled form of a
         * stylesheet (see above).
         * <p>
         *  翻译者注意：以下消息用作标题。将样式表应用于输入文档时生成的所有错误消息一起收集并显示在此消息下面。 "translet"是样式表的编译形式(见上文)。
         */
        {ErrorMsg.RUNTIME_ERROR_KEY,
        "Chyby transletu:"},

        {ErrorMsg.JAXP_SECUREPROCESSING_FEATURE,
        "FEATURE_SECURE_PROCESSING: Cannot set the feature to false when security manager is present."}
    };

    }
}
