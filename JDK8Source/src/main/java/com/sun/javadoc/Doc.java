/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2012, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package com.sun.javadoc;

import java.text.BreakIterator;
import java.util.Locale;

/**
 * Represents Java language constructs (package, class, constructor,
 * method, field) which have comments and have been processed by this
 * run of javadoc.  All Doc objects are unique, that is, they
 * are == comparable.
 *
 * <p>
 *  表示具有注释并已由此javadoc运行处理的Java语言结构(包,类,构造函数,方法,字段)。所有Doc对象都是唯一的,也就是说,它们是==可比的。
 * 
 * 
 * @since 1.2
 * @author Robert Field
 * @author Scott Seligman (generics, enums, annotations)
 */
public interface Doc extends Comparable<Object> {

    /**
     * Return the text of the comment for this doc item.
     * Tags have been removed.
     * <p>
     *  返回此doc文档的注释文本。标签已删除。
     * 
     */
    String commentText();

    /**
     * Return all tags in this Doc item.
     *
     * <p>
     *  返回此文档项中的所有标签。
     * 
     * 
     * @return an array of {@link Tag} objects containing all tags on
     *         this Doc item.
     */
    Tag[] tags();

    /**
     * Return tags of the specified {@linkplain Tag#kind() kind} in
     * this Doc item.
     *
     * <p>
     *  返回此文档项中指定的{@linkplain Tag#kind()kind}的标签。
     * 
     * 
     * For example, if 'tagname' has value "@serial", all tags in
     * this Doc item of kind "@serial" will be returned.
     *
     * @param tagname name of the tag kind to search for.
     * @return an array of Tag containing all tags whose 'kind()'
     * matches 'tagname'.
     */
    Tag[] tags(String tagname);

    /**
     * Return the see also tags in this Doc item.
     *
     * <p>
     *  返回此Doc项中的see also tags。
     * 
     * 
     * @return an array of SeeTag containing all @see tags.
     */
    SeeTag[] seeTags();

    /**
     * Return comment as an array of tags. Includes inline tags
     * (i.e. {&#64;link <i>reference</i>} tags)  but not
     * block tags.
     * Each section of plain text is represented as a {@link Tag}
     * of {@linkplain Tag#kind() kind} "Text".
     * <p>
     *  将注释作为标签数组返回。包含内置标记(即{@link <i>引用</i>}标记),但不包括阻止标记。
     * 纯文本的每个部分表示为{@linkplain Tag#kind()kind}"Text"的{@link Tag}。
     * 
     * 
     * Inline tags are represented as a {@link SeeTag} of kind "@see"
     * and name "@link".
     *
     * @return an array of {@link Tag}s representing the comment
     */
    Tag[] inlineTags();

    /**
     * Return the first sentence of the comment as an array of tags.
     * Includes inline tags
     * (i.e. {&#64;link <i>reference</i>} tags)  but not
     * block tags.
     * Each section of plain text is represented as a {@link Tag}
     * of {@linkplain Tag#kind() kind} "Text".
     * <p>
     *  将注释的第一个句子作为一个标签数组。包含内置标记(即{@link <i>引用</i>}标记),但不包括阻止标记。
     * 纯文本的每个部分表示为{@linkplain Tag#kind()kind}"Text"的{@link Tag}。
     * 
     * 
     * Inline tags are represented as a {@link SeeTag} of kind "@see"
     * and name "@link".
     * <p>
     * If the locale is English language, the first sentence is
     * determined by the rules described in the Java Language
     * Specification (first version): &quot;This sentence ends
     * at the first period that is followed by a blank, tab, or
     * line terminator or at the first tagline.&quot;, in
     * addition a line will be terminated by block
     * HTML tags: &lt;p&gt;  &lt;/p&gt;  &lt;h1&gt;
     * &lt;h2&gt;  &lt;h3&gt; &lt;h4&gt;  &lt;h5&gt;  &lt;h6&gt;
     * &lt;hr&gt;  &lt;pre&gt;  or &lt;/pre&gt;.
     * If the locale is not English, the sentence end will be
     * determined by
     * {@link BreakIterator#getSentenceInstance(Locale)}.

     * @return an array of {@link Tag}s representing the
     * first sentence of the comment
     */
    Tag[] firstSentenceTags();

    /**
     * Return the full unprocessed text of the comment.  Tags
     * are included as text.  Used mainly for store and retrieve
     * operations like internalization.
     * <p>
     *  返回注释的完整未处理文本。标签作为文本包括在内。主要用于存储和检索操作,如内部化。
     * 
     */
    String getRawCommentText();

    /**
     * Set the full unprocessed text of the comment.  Tags
     * are included as text.  Used mainly for store and retrieve
     * operations like internalization.
     * <p>
     *  设置注释的完整未处理文本。标签作为文本包括在内。主要用于存储和检索操作,如内部化。
     * 
     */
    void setRawCommentText(String rawDocumentation);

    /**
     * Returns the non-qualified name of this Doc item.
     *
     * <p>
     *  返回此Doc项的非限定名称。
     * 
     * 
     * @return  the name
     */
    String name();

    /**
     * Compares this doc object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this doc object is less
     * than, equal to, or greater than the given object.
     * <p>
     * This method satisfies the {@link java.lang.Comparable} interface.
     *
     * <p>
     * 将此doc对象与指定的对象进行比较以进行排序。返回负整数,零或正整数,因为此doc对象小于,等于或大于给定对象。
     * <p>
     *  此方法满足{@link java.lang.Comparable}接口。
     * 
     * 
     * @param   obj  the <code>Object</code> to be compared.
     * @return  a negative integer, zero, or a positive integer as this Object
     *      is less than, equal to, or greater than the given Object.
     * @exception ClassCastException the specified Object's type prevents it
     *        from being compared to this Object.
     */
    int compareTo(Object obj);

    /**
     * Is this Doc item a field (but not an enum constant)?
     *
     * <p>
     *  这个Doc项目是一个字段(但不是枚举常量)?
     * 
     * 
     * @return true if it represents a field
     */
    boolean isField();

    /**
     * Is this Doc item an enum constant?
     *
     * <p>
     *  这个文档项是枚举常量吗?
     * 
     * 
     * @return true if it represents an enum constant
     * @since 1.5
     */
    boolean isEnumConstant();

    /**
     * Is this Doc item a constructor?
     *
     * <p>
     *  这个Doc项目是一个构造函数吗?
     * 
     * 
     * @return true if it represents a constructor
     */
    boolean isConstructor();

    /**
     * Is this Doc item a method (but not a constructor or annotation
     * type element)?
     *
     * <p>
     *  这个Doc项目是一个方法(但不是一个构造函数或注释类型元素)?
     * 
     * 
     * @return true if it represents a method
     */
    boolean isMethod();

    /**
     * Is this Doc item an annotation type element?
     *
     * <p>
     *  这个Doc项目是注释类型元素吗?
     * 
     * 
     * @return true if it represents an annotation type element
     * @since 1.5
     */
    boolean isAnnotationTypeElement();

    /**
     * Is this Doc item an interface (but not an annotation type)?
     *
     * <p>
     *  这个Doc项目是一个接口(但不是注释类型)?
     * 
     * 
     * @return true if it represents an interface
     */
    boolean isInterface();

    /**
     * Is this Doc item an exception class?
     *
     * <p>
     *  这个Doc项目是一个异常类吗?
     * 
     * 
     * @return true if it represents an exception
     */
    boolean isException();

    /**
     * Is this Doc item an error class?
     *
     * <p>
     *  这个Doc项目是一个错误类吗?
     * 
     * 
     * @return true if it represents a error
     */
    boolean isError();

    /**
     * Is this Doc item an enum type?
     *
     * <p>
     *  这个文档项是枚举类型吗?
     * 
     * 
     * @return true if it represents an enum type
     * @since 1.5
     */
    boolean isEnum();

    /**
     * Is this Doc item an annotation type?
     *
     * <p>
     *  这个Doc项目是注释类型吗?
     * 
     * 
     * @return true if it represents an annotation type
     * @since 1.5
     */
    boolean isAnnotationType();

    /**
     * Is this Doc item an
     * <a href="{@docRoot}/com/sun/javadoc/package-summary.html#class">ordinary
     * class</a>?
     * (i.e. not an interface, annotation type, enum, exception, or error)?
     *
     * <p>
     *  此文档项目是<a href="{@docRoot}/com/sun/javadoc/package-summary.html#class">普通班</a>吗? (即不是一个接口,注释类型,枚举,异常或
     * 错误)?。
     * 
     * 
     * @return true if it represents an ordinary class
     */
    boolean isOrdinaryClass();

    /**
     * Is this Doc item a
     * <a href="{@docRoot}/com/sun/javadoc/package-summary.html#class">class</a>
     * (and not an interface or annotation type)?
     * This includes ordinary classes, enums, errors and exceptions.
     *
     * <p>
     *  此文档项是<a href="{@docRoot}/com/sun/javadoc/package-summary.html#class">类</a>(而不是界面或注释类型)吗?这包括普通类,枚举,错误
     * 和异常。
     * 
     * 
     * @return true if it represents a class
     */
    boolean isClass();

    /**
     * Return true if this Doc item is
     * <a href="{@docRoot}/com/sun/javadoc/package-summary.html#included">included</a>
     * in the result set.
     * <p>
     *  如果此文档项目在结果集中<a href="{@docRoot}/com/sun/javadoc/package-summary.html#included">包含</a>,则返回true。
     * 
     */
    boolean isIncluded();

    /**
     * Return the source position of the first line of the
     * corresponding declaration, or null if
     * no position is available.  A default constructor returns
     * null because it has no location in the source file.
     *
     * <p>
     *  返回相应声明的第一行的源位置,如果没有位置可用则返回null。默认构造函数返回null,因为它在源文件中没有位置。
     * 
     * @since 1.4
     */
    SourcePosition position();
}
