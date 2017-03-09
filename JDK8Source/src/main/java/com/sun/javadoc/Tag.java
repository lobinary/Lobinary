/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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
/* <p>
/* 
 * Represents a simple documentation tag, such as @since, @author, @version.
 * Given a tag (e.g. "@since 1.2"), holds tag name (e.g. "@since")
 * and tag text (e.g. "1.2").  Tags with structure or which require
 * special processing are handled by subclasses such as ParamTag
 * (for @param), SeeTag (for @see and {&#064;link}), and ThrowsTag
 * (for @throws).
 *
 * @author Robert Field
 * @author Atul M Dambalkar
 * @see SeeTag
 * @see ParamTag
 * @see ThrowsTag
 * @see SerialFieldTag
 * @see Doc#tags()
 *
 */
public interface Tag {

    /**
     * Return the name of this tag.  The name is the string
     * starting with "@" that is used in a doc comment, such as
     * <p>
     *  返回此标记的名称。名称是以"@"开头的字符串,用于doc注释中,例如
     * 
     * 
     * <code>@return</code>.  For inline tags, such as
     * <code>{&#064;link}</code>, the curly brackets
     * are not part of the name, so in this example the name
     * would be simply <code>@link</code>.
     *
     * @return the name of this tag
     */
    String name();

    /**
     * Return the containing {@link Doc} of this Tag element.
     *
     * <p>
     *  返回此标签元素的包含{@link Doc}。
     * 
     * 
     * @return the containing {@link Doc} of this Tag element
     */
    Doc holder();

    /**
     * Return the kind of this tag.
     * For most tags,
     * <code>kind()&nbsp;==&nbsp;name()</code>;
     * the following table lists those cases where there is more
     * than one tag of a given kind:
     *
     * <table border="1" cellpadding="4" cellspacing="0" summary="related tags">
     * <tr><th>{@code kind()  }</th>  <th>{@code name()      }</th></tr>
     * <p>
     *  返回此标签的类型。对于大多数标签,<code> kind()&nbsp; ==&nbsp; name()</code>;下表列出了有多个给定类型的标签的情况：
     * 
     * <table border="1" cellpadding="4" cellspacing="0" summary="related tags">
     *  <tr> <th> {@ code kind()} </th> <th> {@ code name()} </th> </tr>
     * 
     * 
     * <tr><td>{@code @throws }</td>  <td>{@code @throws     }</td></tr>
     * <tr><td>{@code @throws }</td>  <td>{@code @exception  }</td></tr>
     * <tr><td>{@code @see    }</td>  <td>{@code @see        }</td></tr>
     * <tr><td>{@code @see    }</td>  <td>{@code @link       }</td></tr>
     * <tr><td>{@code @see    }</td>  <td>{@code @linkplain  }</td></tr>
     * <tr><td>{@code @serial }</td>  <td>{@code @serial     }</td></tr>
     * <tr><td>{@code @serial }</td>  <td>{@code @serialData }</td></tr>
     * </table>
     *
     * @return the kind of this tag.
     */
    String kind();

    /**
     * Return the text of this tag, that is, the portion beyond tag name.
     *
     * <p>
     *  返回此标记的文本,即标记名称之外的部分。
     * 
     * 
     * @return the text of this tag
     */
    String text();

    /**
     * Convert this object to a string.
     * <p>
     *  将此对象转换为字符串。
     * 
     */
    String toString();

    /**
     * For a documentation comment with embedded <code>{&#064;link}</code>
     * tags, return an array of <code>Tag</code> objects.  The entire
     * doc comment is broken down into strings separated by
     * <code>{&#064;link}</code> tags, where each successive element
     * of the array represents either a string or
     * <code>{&#064;link}</code> tag, in order, from start to end.
     * Each string is represented by a <code>Tag</code> object of
     * name "Text", where {@link #text()} returns the string.  Each
     * <code>{&#064;link}</code> tag is represented by a
     * <p>
     *  对于嵌入了<code> {@ link} </code>标签的文档注释,返回<code> Tag </code>对象的数组。
     * 整个文档评论被细分为由<code> {@ link} </code>标签分隔的字符串,其中数组的每个后续元素都表示字符串或<code> {@ link} </code>顺序,从开始到结束。
     * 每个字符串由名称为"Text"的<code> Tag </code>对象表示,其中{@link #text()}返回字符串。每个<code> {@ link} </code>标记由a表示。
     * 
     * 
     * {@link SeeTag} of name "@link" and kind "@see".
     * For example, given the following comment
     * tag:
     * <p>
     *  <code>This is a {&#064;link Doc commentlabel} example.</code>
     * <p>
     * return an array of Tag objects:
     * <ul>
     *    <li> tags[0] is a {@link Tag} with name "Text" and text consisting
     *         of "This is a "
     *    <li> tags[1] is a {@link SeeTag} with name "@link", referenced
     *         class <code>Doc</code> and label "commentlabel"
     *    <li> tags[2] is a {@link Tag} with name "Text" and text consisting
     *         of " example."
     * </ul>
     *
     * @return Tag[] array of tags
     * @see ParamTag
     * @see ThrowsTag
     */
    Tag[] inlineTags();

    /**
     * Return the first sentence of the comment as an array of tags.
     * Includes inline tags
     * (i.e. {&#64;link <i>reference</i>} tags)  but not
     * block tags.
     * Each section of plain text is represented as a {@link Tag}
     * of kind "Text".
     * Inline tags are represented as a {@link SeeTag} of kind "@link".
     * If the locale is English language, the first sentence is
     * determined by the rules described in the Java Language
     * Specification (first version): &quot;This sentence ends
     * at the first period that is followed by a blank, tab, or
     * line terminator or at the first tagline.&quot;, in
     * addition a line will be terminated by paragraph and
     * section terminating HTML tags: &lt;p&gt;  &lt;/p&gt;  &lt;h1&gt;
     * &lt;h2&gt;  &lt;h3&gt; &lt;h4&gt;  &lt;h5&gt;  &lt;h6&gt;
     * &lt;hr&gt;  &lt;pre&gt;  or &lt;/pre&gt;.
     * If the locale is not English, the sentence end will be
     * determined by
     * {@link BreakIterator#getSentenceInstance(Locale)}.
     *
     * <p>
     * 将注释的第一个句子作为一个标签数组。包含内置标记(即{@link <i>参考</i>}标记),但不能屏蔽标记。纯文本的每个部分表示为类型为"Text"的{@link Tag}。
     * 内联标记表示为类型为"@link"的{@link SeeTag}。
     * 如果语言环境是英语语言,则第一句由Java语言规范(第一版本)中描述的规则确定："该语句在第一周期结束,其后是空白,制表符或行终止符,或在第一标签",另外一行将由段落和段终止HTML标签终止：&lt; 
     * p&gt; &lt; / p&gt; &lt; h1&gt; &lt; h2&gt; &lt; h3&gt; &lt; h4&gt; &lt; h5&gt; &lt; h6&gt; &lt; hr&gt
     * ; &lt; pre&gt;或&lt; / pre&gt ;.如果语言环境不是英语,则句子结束将由{@link BreakIterator#getSentenceInstance(Locale)}确定。
     * 
     * @return an array of {@link Tag} objects representing the
     *         first sentence of the comment
     */
    Tag[] firstSentenceTags();

    /**
     * Return the source position of this tag.
     * <p>
     * 内联标记表示为类型为"@link"的{@link SeeTag}。
     * 
     * 
     * @return the source position of this tag.
     */
    public SourcePosition position();
}
