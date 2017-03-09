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

package javax.swing.text.html.parser;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.HTML;
import javax.swing.text.ChangedCharSetException;

import java.util.*;
import java.io.*;
import java.net.*;

/**
 * A Parser for HTML Documents (actually, you can specify a DTD, but
 * you should really only use this class with the html dtd in swing).
 * Reads an InputStream of HTML and
 * invokes the appropriate methods in the ParserCallback class. This
 * is the default parser used by HTMLEditorKit to parse HTML url's.
 * <p>This will message the callback for all valid tags, as well as
 * tags that are implied but not explicitly specified. For example, the
 * html string (&lt;p&gt;blah) only has a p tag defined. The callback
 * will see the following methods:
 * <ol><li><i>handleStartTag(html, ...)</i></li>
 *     <li><i>handleStartTag(head, ...)</i></li>
 *     <li><i>handleEndTag(head)</i></li>
 *     <li><i>handleStartTag(body, ...)</i></li>
 *     <li><i>handleStartTag(p, ...)</i></li>
 *     <li><i>handleText(...)</i></li>
 *     <li><i>handleEndTag(p)</i></li>
 *     <li><i>handleEndTag(body)</i></li>
 *     <li><i>handleEndTag(html)</i></li>
 * </ol>
 * The items in <i>italic</i> are implied, that is, although they were not
 * explicitly specified, to be correct html they should have been present
 * (head isn't necessary, but it is still generated). For tags that
 * are implied, the AttributeSet argument will have a value of
 * <code>Boolean.TRUE</code> for the key
 * <code>HTMLEditorKit.ParserCallback.IMPLIED</code>.
 * <p>HTML.Attributes defines a type safe enumeration of html attributes.
 * If an attribute key of a tag is defined in HTML.Attribute, the
 * HTML.Attribute will be used as the key, otherwise a String will be used.
 * For example &lt;p foo=bar class=neat&gt; has two attributes. foo is
 * not defined in HTML.Attribute, where as class is, therefore the
 * AttributeSet will have two values in it, HTML.Attribute.CLASS with
 * a String value of 'neat' and the String key 'foo' with a String value of
 * 'bar'.
 * <p>The position argument will indicate the start of the tag, comment
 * or text. Similar to arrays, the first character in the stream has a
 * position of 0. For tags that are
 * implied the position will indicate
 * the location of the next encountered tag. In the first example,
 * the implied start body and html tags will have the same position as the
 * p tag, and the implied end p, html and body tags will all have the same
 * position.
 * <p>As html skips whitespace the position for text will be the position
 * of the first valid character, eg in the string '\n\n\nblah'
 * the text 'blah' will have a position of 3, the newlines are skipped.
 * <p>
 * For attributes that do not have a value, eg in the html
 * string <code>&lt;foo blah&gt;</code> the attribute <code>blah</code>
 * does not have a value, there are two possible values that will be
 * placed in the AttributeSet's value:
 * <ul>
 * <li>If the DTD does not contain an definition for the element, or the
 *     definition does not have an explicit value then the value in the
 *     AttributeSet will be <code>HTML.NULL_ATTRIBUTE_VALUE</code>.
 * <li>If the DTD contains an explicit value, as in:
 *     <code>&lt;!ATTLIST OPTION selected (selected) #IMPLIED&gt;</code>
 *     this value from the dtd (in this case selected) will be used.
 * </ul>
 * <p>
 * Once the stream has been parsed, the callback is notified of the most
 * likely end of line string. The end of line string will be one of
 * \n, \r or \r\n, which ever is encountered the most in parsing the
 * stream.
 *
 * <p>
 *  HTML文档的解析器(实际上,你可以指定一个DTD,但你应该真的只使用这个类与html dtd在swing)。读取HTML的InputStream并调用ParserCallback类中的相应方法。
 * 这是HTMLEditorKit用来解析HTML网址的默认解析器。 <p>这会回覆所有有效标记的回呼,以及隐含但未明确指定的标记。
 * 例如,html字符串(&lt; p&gt; blah)仅具有定义的p标签。
 * 回调将看到以下方法：<ol> <li> <i> handleStartTag(html,...)</i> </li> <li> <i> handleStartTag(head,...)</i > </li>
 *  <li> </li> handleEndTag(head)</i> </li> <li> <i> handleStartTag(body,...) > handleStartTag(p,...)</li>
 *  </li> <li> </li> > </li> </li> handleEndTag(html)</li> </li>。
 * 例如,html字符串(&lt; p&gt; blah)仅具有定义的p标签。
 * </ol>
 * <i> italic </i>中的项目是隐含的,也就是说,虽然它们没有明确指定,但是它们应该是正确的html(head不是必需的,但它仍然是生成的)。
 * 对于隐含的标签,AttributeSet参数对于键<code> HTMLEditorKit.ParserCallback.IMPLIED </code>的值为<code> Boolean.TRUE </code>
 * 。
 * <i> italic </i>中的项目是隐含的,也就是说,虽然它们没有明确指定,但是它们应该是正确的html(head不是必需的,但它仍然是生成的)。
 *  <p> HTML.Attributes定义了html属性的类型安全枚举。如果在HTML.Attribute中定义了标签的属性键,则HTML.Attribute将用作键,否则将使用String。
 * 例如&lt; p foo = bar class = neat&gt;有两个属性。
 *  foo不是在HTML.Attribute中定义的,因为在类中,AttributeSet将有两个值,HTML.Attribute.CLASS的String值为'neat',String键'foo'的St
 * ring值为'酒吧'。
 * 例如&lt; p foo = bar class = neat&gt;有两个属性。 <p> position参数将指示标记,注释或文本的开始。
 * 与数组类似,流中的第一个字符的位置为0.对于隐含的标记,位置将指示下一个遇到的标记的位置。
 * 在第一个例子中,隐含的开始体和html标签将具有与p标签相同的位置,隐含的结束p,html和body标签都将具有相同的位置。
 *  <p>当html跳过空格时,文本的位置将是第一个有效字符的位置,例如在字符串'\ n \ n \ nblah'中,文本'blah'的位置为3,跳过新行。
 * <p>
 * 对于没有值的属性,例如在html字符串<code>&lt; foo blah&gt; </code>中,属性<code> blah </code>没有值,有两个可能的值放置在AttributeSet的值
 * 中：。
 * <ul>
 *  <li>如果DTD不包含元素的定义,或者定义没有显式值,那么AttributeSet中的值将为<code> HTML.NULL_ATTRIBUTE_VALUE </code>。
 * 
 * @author      Sunita Mani
 */
public class DocumentParser extends javax.swing.text.html.parser.Parser {

    private int inbody;
    private int intitle;
    private int inhead;
    private int instyle;
    private int inscript;
    private boolean seentitle;
    private HTMLEditorKit.ParserCallback callback = null;
    private boolean ignoreCharSet = false;
    private static final boolean debugFlag = false;

    public DocumentParser(DTD dtd) {
        super(dtd);
    }

    public void parse(Reader in,  HTMLEditorKit.ParserCallback callback, boolean ignoreCharSet) throws IOException {
        this.ignoreCharSet = ignoreCharSet;
        this.callback = callback;
        parse(in);
        // end of line
        callback.handleEndOfLineString(getEndOfLineString());
    }

    /**
     * Handle Start Tag.
     * <p>
     *  <li>如果DTD包含显式值,如：<code>&lt;！ATTLIST OPTION selected(selected)#IMPLIED&gt; </code>将使用来自dtd(在本例中为选中)的值
     * 。
     *  <li>如果DTD不包含元素的定义,或者定义没有显式值,那么AttributeSet中的值将为<code> HTML.NULL_ATTRIBUTE_VALUE </code>。
     * </ul>
     * <p>
     *  一旦流已经被解析,回调被通知最可能的行结束字符串。行结束字符串将是\ n,\ r或\ r \ n之一,在解析流时遇到最多。
     * 
     */
    protected void handleStartTag(TagElement tag) {

        Element elem = tag.getElement();
        if (elem == dtd.body) {
            inbody++;
        } else if (elem == dtd.html) {
        } else if (elem == dtd.head) {
            inhead++;
        } else if (elem == dtd.title) {
            intitle++;
        } else if (elem == dtd.style) {
            instyle++;
        } else if (elem == dtd.script) {
            inscript++;
        }
        if (debugFlag) {
            if (tag.fictional()) {
                debug("Start Tag: " + tag.getHTMLTag() + " pos: " + getCurrentPos());
            } else {
                debug("Start Tag: " + tag.getHTMLTag() + " attributes: " +
                      getAttributes() + " pos: " + getCurrentPos());
            }
        }
        if (tag.fictional()) {
            SimpleAttributeSet attrs = new SimpleAttributeSet();
            attrs.addAttribute(HTMLEditorKit.ParserCallback.IMPLIED,
                               Boolean.TRUE);
            callback.handleStartTag(tag.getHTMLTag(), attrs,
                                    getBlockStartPosition());
        } else {
            callback.handleStartTag(tag.getHTMLTag(), getAttributes(),
                                    getBlockStartPosition());
            flushAttributes();
        }
    }


    protected void handleComment(char text[]) {
        if (debugFlag) {
            debug("comment: ->" + new String(text) + "<-"
                  + " pos: " + getCurrentPos());
        }
        callback.handleComment(text, getBlockStartPosition());
    }

    /**
     * Handle Empty Tag.
     * <p>
     *  处理开始标签。
     * 
     */
    protected void handleEmptyTag(TagElement tag) throws ChangedCharSetException {

        Element elem = tag.getElement();
        if (elem == dtd.meta && !ignoreCharSet) {
            SimpleAttributeSet atts = getAttributes();
            if (atts != null) {
                String content = (String)atts.getAttribute(HTML.Attribute.CONTENT);
                if (content != null) {
                    if ("content-type".equalsIgnoreCase((String)atts.getAttribute(HTML.Attribute.HTTPEQUIV))) {
                        if (!content.equalsIgnoreCase("text/html") &&
                                !content.equalsIgnoreCase("text/plain")) {
                            throw new ChangedCharSetException(content, false);
                        }
                    } else if ("charset" .equalsIgnoreCase((String)atts.getAttribute(HTML.Attribute.HTTPEQUIV))) {
                        throw new ChangedCharSetException(content, true);
                    }
                }
            }
        }
        if (inbody != 0 || elem == dtd.meta || elem == dtd.base || elem == dtd.isindex || elem == dtd.style || elem == dtd.link) {
            if (debugFlag) {
                if (tag.fictional()) {
                    debug("Empty Tag: " + tag.getHTMLTag() + " pos: " + getCurrentPos());
                } else {
                    debug("Empty Tag: " + tag.getHTMLTag() + " attributes: "
                          + getAttributes() + " pos: " + getCurrentPos());
                }
            }
            if (tag.fictional()) {
                SimpleAttributeSet attrs = new SimpleAttributeSet();
                attrs.addAttribute(HTMLEditorKit.ParserCallback.IMPLIED,
                                   Boolean.TRUE);
                callback.handleSimpleTag(tag.getHTMLTag(), attrs,
                                         getBlockStartPosition());
            } else {
                callback.handleSimpleTag(tag.getHTMLTag(), getAttributes(),
                                         getBlockStartPosition());
                flushAttributes();
            }
        }
    }

    /**
     * Handle End Tag.
     * <p>
     *  处理空标签。
     * 
     */
    protected void handleEndTag(TagElement tag) {
        Element elem = tag.getElement();
        if (elem == dtd.body) {
            inbody--;
        } else if (elem == dtd.title) {
            intitle--;
            seentitle = true;
        } else if (elem == dtd.head) {
            inhead--;
        } else if (elem == dtd.style) {
            instyle--;
        } else if (elem == dtd.script) {
            inscript--;
        }
        if (debugFlag) {
            debug("End Tag: " + tag.getHTMLTag() + " pos: " + getCurrentPos());
        }
        callback.handleEndTag(tag.getHTMLTag(), getBlockStartPosition());

    }

    /**
     * Handle Text.
     * <p>
     *  句柄结束标签。
     * 
     */
    protected void handleText(char data[]) {
        if (data != null) {
            if (inscript != 0) {
                callback.handleComment(data, getBlockStartPosition());
                return;
            }
            if (inbody != 0 || ((instyle != 0) ||
                                ((intitle != 0) && !seentitle))) {
                if (debugFlag) {
                    debug("text:  ->" + new String(data) + "<-" + " pos: " + getCurrentPos());
                }
                callback.handleText(data, getBlockStartPosition());
            }
        }
    }

    /*
     * Error handling.
     * <p>
     *  处理文本。
     * 
     */
    protected void handleError(int ln, String errorMsg) {
        if (debugFlag) {
            debug("Error: ->" + errorMsg + "<-" + " pos: " + getCurrentPos());
        }
        /* PENDING: need to improve the error string. */
        callback.handleError(errorMsg, getCurrentPos());
    }


    /*
     * debug messages
     * <p>
     *  错误处理。
     * 
     */
    private void debug(String msg) {
        System.out.println(msg);
    }
}
