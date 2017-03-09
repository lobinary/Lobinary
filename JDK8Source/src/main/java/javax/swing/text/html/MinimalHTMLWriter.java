/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2010, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.text.html;

import java.io.Writer;
import java.io.IOException;
import java.util.*;
import java.awt.Color;
import javax.swing.text.*;

/**
 * MinimalHTMLWriter is a fallback writer used by the
 * HTMLEditorKit to write out HTML for a document that
 * is a not produced by the EditorKit.
 *
 * The format for the document is:
 * <pre>
 * &lt;html&gt;
 *   &lt;head&gt;
 *     &lt;style&gt;
 *        &lt;!-- list of named styles
 *         p.normal {
 *            font-family: SansSerif;
 *            margin-height: 0;
 *            font-size: 14
 *         }
 *        --&gt;
 *      &lt;/style&gt;
 *   &lt;/head&gt;
 *   &lt;body&gt;
 *    &lt;p style=normal&gt;
 *        <b>Bold, italic, and underline attributes
 *        of the run are emitted as HTML tags.
 *        The remaining attributes are emitted as
 *        part of the style attribute of a &lt;span&gt; tag.
 *        The syntax is similar to inline styles.</b>
 *    &lt;/p&gt;
 *   &lt;/body&gt;
 * &lt;/html&gt;
 * </pre>
 *
 * <p>
 *  MinimalHTMLWriter是由HTMLEditorKit使用的后备写入器,用于为非由EditorKit生成的文档写入HTML。
 * 
 *  文档的格式为：
 * <pre>
 *  &lt; html&gt; &lt; head&gt; &lt; style&gt; &lt;！ - 命名样式列表p.normal {font-family：SansSerif; margin-height：0; font-size：14}
 *   - &gt; &lt; / style&gt; &lt; / head&gt; &lt; body&gt; &lt; p style = normal&gt; <b>运行的粗体,斜体和下划线属性作为
 * HTML标记发出。
 * 其余属性作为&lt; span&gt;的样式属性的一部分发出。标签。语法类似于内联样式。</b>&lt; / p&gt; &lt; / body&gt; &lt; / html&gt;。
 * </pre>
 * 
 * 
 * @author Sunita Mani
 */

public class MinimalHTMLWriter extends AbstractWriter {

    /**
     * These static finals are used to
     * tweak and query the fontMask about which
     * of these tags need to be generated or
     * terminated.
     * <p>
     *  这些静态决赛用来调整和查询fontMask关于哪些标签需要生成或终止。
     * 
     */
    private static final int BOLD = 0x01;
    private static final int ITALIC = 0x02;
    private static final int UNDERLINE = 0x04;

    // Used to map StyleConstants to CSS.
    private static final CSS css = new CSS();

    private int fontMask = 0;

    int startOffset = 0;
    int endOffset = 0;

    /**
     * Stores the attributes of the previous run.
     * Used to compare with the current run's
     * attributeset.  If identical, then a
     * &lt;span&gt; tag is not emitted.
     * <p>
     *  存储先前运行的属性。用于与当前运行的属性集进行比较。如果相同,则&lt; span&gt;标签不发射。
     * 
     */
    private AttributeSet fontAttributes;

    /**
     * Maps from style name as held by the Document, to the archived
     * style name (style name written out). These may differ.
     * <p>
     *  从文档所持有的样式名称到归档样式名称(样式名称已写出)的映射。这些可能不同。
     * 
     */
    private Hashtable<String, String> styleNameMapping;

    /**
     * Creates a new MinimalHTMLWriter.
     *
     * <p>
     *  创建一个新的MinimalHTMLWriter。
     * 
     * 
     * @param w  Writer
     * @param doc StyledDocument
     *
     */
    public MinimalHTMLWriter(Writer w, StyledDocument doc) {
        super(w, doc);
    }

    /**
     * Creates a new MinimalHTMLWriter.
     *
     * <p>
     *  创建一个新的MinimalHTMLWriter。
     * 
     * 
     * @param w  Writer
     * @param doc StyledDocument
     * @param pos The location in the document to fetch the
     *   content.
     * @param len The amount to write out.
     *
     */
    public MinimalHTMLWriter(Writer w, StyledDocument doc, int pos, int len) {
        super(w, doc, pos, len);
    }

    /**
     * Generates HTML output
     * from a StyledDocument.
     *
     * <p>
     *  从StyledDocument生成HTML输出。
     * 
     * 
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *            location within the document.
     *
     */
    public void write() throws IOException, BadLocationException {
        styleNameMapping = new Hashtable<String, String>();
        writeStartTag("<html>");
        writeHeader();
        writeBody();
        writeEndTag("</html>");
    }


    /**
     * Writes out all the attributes for the
     * following types:
     *  StyleConstants.ParagraphConstants,
     *  StyleConstants.CharacterConstants,
     *  StyleConstants.FontConstants,
     *  StyleConstants.ColorConstants.
     * The attribute name and value are separated by a colon.
     * Each pair is separated by a semicolon.
     *
     * <p>
     * 写出以下类型的所有属性：StyleConstants.ParagraphConstants,StyleConstants.CharacterConstants,StyleConstants.FontCo
     * nstants,StyleConstants.ColorConstants。
     * 属性名称和值由冒号分隔。每对由分号分隔。
     * 
     * 
     * @exception IOException on any I/O error
     */
    protected void writeAttributes(AttributeSet attr) throws IOException {
        Enumeration attributeNames = attr.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            Object name = attributeNames.nextElement();
            if ((name instanceof StyleConstants.ParagraphConstants) ||
                (name instanceof StyleConstants.CharacterConstants) ||
                (name instanceof StyleConstants.FontConstants) ||
                (name instanceof StyleConstants.ColorConstants)) {
                indent();
                write(name.toString());
                write(':');
                write(css.styleConstantsValueToCSSValue
                      ((StyleConstants)name, attr.getAttribute(name)).
                      toString());
                write(';');
                write(NEWLINE);
            }
        }
    }


    /**
     * Writes out text.
     *
     * <p>
     *  写出文本。
     * 
     * 
     * @exception IOException on any I/O error
     */
    protected void text(Element elem) throws IOException, BadLocationException {
        String contentStr = getText(elem);
        if ((contentStr.length() > 0) &&
            (contentStr.charAt(contentStr.length()-1) == NEWLINE)) {
            contentStr = contentStr.substring(0, contentStr.length()-1);
        }
        if (contentStr.length() > 0) {
            write(contentStr);
        }
    }

    /**
     * Writes out a start tag appropriately
     * indented.  Also increments the indent level.
     *
     * <p>
     *  写出一个适当缩进的开始标签。还增加缩进级别。
     * 
     * 
     * @exception IOException on any I/O error
     */
    protected void writeStartTag(String tag) throws IOException {
        indent();
        write(tag);
        write(NEWLINE);
        incrIndent();
    }


    /**
     * Writes out an end tag appropriately
     * indented.  Also decrements the indent level.
     *
     * <p>
     *  写出一个适当缩进的结束标签。也递减缩进级别。
     * 
     * 
     * @exception IOException on any I/O error
     */
    protected void writeEndTag(String endTag) throws IOException {
        decrIndent();
        indent();
        write(endTag);
        write(NEWLINE);
    }


    /**
     * Writes out the &lt;head&gt; and &lt;style&gt;
     * tags, and then invokes writeStyles() to write
     * out all the named styles as the content of the
     * &lt;style&gt; tag.  The content is surrounded by
     * valid HTML comment markers to ensure that the
     * document is viewable in applications/browsers
     * that do not support the tag.
     *
     * <p>
     *  写出&lt; head&gt;和&lt; style&gt;标签,然后调用writeStyles()来写出所有命名的样式作为&lt; style&gt;标签。
     * 内容由有效的HTML注释标记包围,以确保文档在不支持标记的应用程序/浏览器中可见。
     * 
     * 
     * @exception IOException on any I/O error
     */
    protected void writeHeader() throws IOException {
        writeStartTag("<head>");
        writeStartTag("<style>");
        writeStartTag("<!--");
        writeStyles();
        writeEndTag("-->");
        writeEndTag("</style>");
        writeEndTag("</head>");
    }



    /**
     * Writes out all the named styles as the
     * content of the &lt;style&gt; tag.
     *
     * <p>
     *  将所有命名的样式写为&lt; style&gt;的内容。标签。
     * 
     * 
     * @exception IOException on any I/O error
     */
    protected void writeStyles() throws IOException {
        /*
         *  Access to DefaultStyledDocument done to workaround
         *  a missing API in styled document to access the
         *  stylenames.
         * <p>
         *  访问DefaultStyledDocument完成以解决样式文档中缺少的API,以访问样式名。
         * 
         */
        DefaultStyledDocument styledDoc =  ((DefaultStyledDocument)getDocument());
        Enumeration styleNames = styledDoc.getStyleNames();

        while (styleNames.hasMoreElements()) {
            Style s = styledDoc.getStyle((String)styleNames.nextElement());

            /** PENDING: Once the name attribute is removed
            /* <p>
            /* 
                from the list we check check for 0. **/
            if (s.getAttributeCount() == 1 &&
                s.isDefined(StyleConstants.NameAttribute)) {
                continue;
            }
            indent();
            write("p." + addStyleName(s.getName()));
            write(" {\n");
            incrIndent();
            writeAttributes(s);
            decrIndent();
            indent();
            write("}\n");
        }
    }


    /**
     * Iterates over the elements in the document
     * and processes elements based on whether they are
     * branch elements or leaf elements.  This method specially handles
     * leaf elements that are text.
     *
     * <p>
     *  迭代文档中的元素,并根据元素是分支元素还是叶元素来处理元素。此方法专门处理文本的叶元素。
     * 
     * 
     * @exception IOException on any I/O error
     */
    protected void writeBody() throws IOException, BadLocationException {
        ElementIterator it = getElementIterator();

        /*
          This will be a section element for a styled document.
          We represent this element in HTML as the body tags.
          Therefore we ignore it.
        /* <p>
        /*  这将是样式文档的节元素。我们在HTML中表示这个元素作为body标签。因此我们忽略它。
        /* 
         */
        it.current();

        Element next;

        writeStartTag("<body>");

        boolean inContent = false;

        while((next = it.next()) != null) {
            if (!inRange(next)) {
                continue;
            }
            if (next instanceof AbstractDocument.BranchElement) {
                if (inContent) {
                    writeEndParagraph();
                    inContent = false;
                    fontMask = 0;
                }
                writeStartParagraph(next);
            } else if (isText(next)) {
                writeContent(next, !inContent);
                inContent = true;
            } else {
                writeLeaf(next);
                inContent = true;
            }
        }
        if (inContent) {
            writeEndParagraph();
        }
        writeEndTag("</body>");
    }


    /**
     * Emits an end tag for a &lt;p&gt;
     * tag.  Before writing out the tag, this method ensures
     * that all other tags that have been opened are
     * appropriately closed off.
     *
     * <p>
     *  发出&lt; p&gt;的结束标记标签。在写出标签之前,此方法确保已经打开的所有其他标签被适当地关闭。
     * 
     * 
     * @exception IOException on any I/O error
     */
    protected void writeEndParagraph() throws IOException {
        writeEndMask(fontMask);
        if (inFontTag()) {
            endSpanTag();
        } else {
            write(NEWLINE);
        }
        writeEndTag("</p>");
    }


    /**
     * Emits the start tag for a paragraph. If
     * the paragraph has a named style associated with it,
     * then this method also generates a class attribute for the
     * &lt;p&gt; tag and sets its value to be the name of the
     * style.
     *
     * <p>
     * 发出段落的开始标签。如果段落具有与其相关联的命名样式,则该方法还生成用于&lt; p&gt;的类属性。标记并将其值设置为样式的名称。
     * 
     * 
     * @exception IOException on any I/O error
     */
    protected void writeStartParagraph(Element elem) throws IOException {
        AttributeSet attr = elem.getAttributes();
        Object resolveAttr = attr.getAttribute(StyleConstants.ResolveAttribute);
        if (resolveAttr instanceof StyleContext.NamedStyle) {
            writeStartTag("<p class=" + mapStyleName(((StyleContext.NamedStyle)resolveAttr).getName()) + ">");
        } else {
            writeStartTag("<p>");
        }
    }


    /**
     * Responsible for writing out other non-text leaf
     * elements.
     *
     * <p>
     *  负责写出其他非文本叶元素。
     * 
     * 
     * @exception IOException on any I/O error
     */
    protected void writeLeaf(Element elem) throws IOException {
        indent();
        if (elem.getName() == StyleConstants.IconElementName) {
            writeImage(elem);
        } else if (elem.getName() == StyleConstants.ComponentElementName) {
            writeComponent(elem);
        }
    }


    /**
     * Responsible for handling Icon Elements;
     * deliberately unimplemented.  How to implement this method is
     * an issue of policy.  For example, if you're generating
     * an &lt;img&gt; tag, how should you
     * represent the src attribute (the location of the image)?
     * In certain cases it could be a URL, in others it could
     * be read from a stream.
     *
     * <p>
     *  负责处理图标元素;故意未执行。如何实现这种方法是一个政策问题。
     * 例如,如果您生成&lt; img&gt;标签,你应该如何表示src属性(图像的位置)?在某些情况下,它可以是一个URL,在其他情况下,它可以从流中读取。
     * 
     * 
     * @param elem element of type StyleConstants.IconElementName
     */
    protected void writeImage(Element elem) throws IOException {
    }


    /**
     * Responsible for handling Component Elements;
     * deliberately unimplemented.
     * How this method is implemented is a matter of policy.
     * <p>
     *  负责处理组件元素;故意未执行。如何实施这种方法是一个政策问题。
     * 
     */
    protected void writeComponent(Element elem) throws IOException {
    }


    /**
     * Returns true if the element is a text element.
     *
     * <p>
     *  如果元素是文本元素,则返回true。
     * 
     */
    protected boolean isText(Element elem) {
        return (elem.getName() == AbstractDocument.ContentElementName);
    }


    /**
     * Writes out the attribute set
     * in an HTML-compliant manner.
     *
     * <p>
     *  以符合HTML的方式写入设置的属性。
     * 
     * 
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *            location within the document.
     */
    protected void writeContent(Element elem,  boolean needsIndenting)
        throws IOException, BadLocationException {

        AttributeSet attr = elem.getAttributes();
        writeNonHTMLAttributes(attr);
        if (needsIndenting) {
            indent();
        }
        writeHTMLTags(attr);
        text(elem);
    }


    /**
     * Generates
     * bold &lt;b&gt;, italic &lt;i&gt;, and &lt;u&gt; tags for the
     * text based on its attribute settings.
     *
     * <p>
     *  生成粗体&lt; b&gt;,italic&lt; i&gt;和&lt; u&标签的文本基于其属性设置。
     * 
     * 
     * @exception IOException on any I/O error
     */

    protected void writeHTMLTags(AttributeSet attr) throws IOException {

        int oldMask = fontMask;
        setFontMask(attr);

        int endMask = 0;
        int startMask = 0;
        if ((oldMask & BOLD) != 0) {
            if ((fontMask & BOLD) == 0) {
                endMask |= BOLD;
            }
        } else if ((fontMask & BOLD) != 0) {
            startMask |= BOLD;
        }

        if ((oldMask & ITALIC) != 0) {
            if ((fontMask & ITALIC) == 0) {
                endMask |= ITALIC;
            }
        } else if ((fontMask & ITALIC) != 0) {
            startMask |= ITALIC;
        }

        if ((oldMask & UNDERLINE) != 0) {
            if ((fontMask & UNDERLINE) == 0) {
                endMask |= UNDERLINE;
            }
        } else if ((fontMask & UNDERLINE) != 0) {
            startMask |= UNDERLINE;
        }
        writeEndMask(endMask);
        writeStartMask(startMask);
    }


    /**
     * Tweaks the appropriate bits of fontMask
     * to reflect whether the text is to be displayed in
     * bold, italic, and/or with an underline.
     *
     * <p>
     *  调整fontMask的相应位,以反映文本是以粗体,斜体和/或下划线显示。
     * 
     */
    private void setFontMask(AttributeSet attr) {
        if (StyleConstants.isBold(attr)) {
            fontMask |= BOLD;
        }

        if (StyleConstants.isItalic(attr)) {
            fontMask |= ITALIC;
        }

        if (StyleConstants.isUnderline(attr)) {
            fontMask |= UNDERLINE;
        }
    }




    /**
     * Writes out start tags &lt;u&gt;, &lt;i&gt;, and &lt;b&gt; based on
     * the mask settings.
     *
     * <p>
     *  写出开始标签&lt; u&gt;,&lt; i&gt;和&lt; b&gt;基于掩模设置。
     * 
     * 
     * @exception IOException on any I/O error
     */
    private void writeStartMask(int mask) throws IOException  {
        if (mask != 0) {
            if ((mask & UNDERLINE) != 0) {
                write("<u>");
            }
            if ((mask & ITALIC) != 0) {
                write("<i>");
            }
            if ((mask & BOLD) != 0) {
                write("<b>");
            }
        }
    }

    /**
     * Writes out end tags for &lt;u&gt;, &lt;i&gt;, and &lt;b&gt; based on
     * the mask settings.
     *
     * <p>
     *  写出&lt; u&gt;,&lt; i&gt;和&lt; b&gt;的结束标记基于掩模设置。
     * 
     * 
     * @exception IOException on any I/O error
     */
    private void writeEndMask(int mask) throws IOException {
        if (mask != 0) {
            if ((mask & BOLD) != 0) {
                write("</b>");
            }
            if ((mask & ITALIC) != 0) {
                write("</i>");
            }
            if ((mask & UNDERLINE) != 0) {
                write("</u>");
            }
        }
    }


    /**
     * Writes out the remaining
     * character-level attributes (attributes other than bold,
     * italic, and underline) in an HTML-compliant way.  Given that
     * attributes such as font family and font size have no direct
     * mapping to HTML tags, a &lt;span&gt; tag is generated and its
     * style attribute is set to contain the list of remaining
     * attributes just like inline styles.
     *
     * <p>
     * 以HTML兼容方式写出其余字符级属性(粗体,斜体和下划线以外的属性)。
     * 假定诸如字体族和字体大小的属性没有直接映射到HTML标签,则&lt; span&gt;标签,并且其样式属性设置为包含剩余属性的列表,就像内联样式一样。
     * 
     * 
     * @exception IOException on any I/O error
     */
    protected void writeNonHTMLAttributes(AttributeSet attr) throws IOException {

        String style = "";
        String separator = "; ";

        if (inFontTag() && fontAttributes.isEqual(attr)) {
            return;
        }

        boolean first = true;
        Color color = (Color)attr.getAttribute(StyleConstants.Foreground);
        if (color != null) {
            style += "color: " + css.styleConstantsValueToCSSValue
                                    ((StyleConstants)StyleConstants.Foreground,
                                     color);
            first = false;
        }
        Integer size = (Integer)attr.getAttribute(StyleConstants.FontSize);
        if (size != null) {
            if (!first) {
                style += separator;
            }
            style += "font-size: " + size.intValue() + "pt";
            first = false;
        }

        String family = (String)attr.getAttribute(StyleConstants.FontFamily);
        if (family != null) {
            if (!first) {
                style += separator;
            }
            style += "font-family: " + family;
            first = false;
        }

        if (style.length() > 0) {
            if (fontMask != 0) {
                writeEndMask(fontMask);
                fontMask = 0;
            }
            startSpanTag(style);
            fontAttributes = attr;
        }
        else if (fontAttributes != null) {
            writeEndMask(fontMask);
            fontMask = 0;
            endSpanTag();
        }
    }


    /**
     * Returns true if we are currently in a &lt;font&gt; tag.
     * <p>
     *  如果我们目前位于&lt; font&gt;标签。
     * 
     */
    protected boolean inFontTag() {
        return (fontAttributes != null);
    }

    /**
     * This is no longer used, instead &lt;span&gt; will be written out.
     * <p>
     * Writes out an end tag for the &lt;font&gt; tag.
     *
     * <p>
     *  这已不再使用,而是&lt; span&gt;将被写出。
     * <p>
     *  写出&lt; font&gt;的结束标记标签。
     * 
     * 
     * @exception IOException on any I/O error
     */
    protected void endFontTag() throws IOException {
        write(NEWLINE);
        writeEndTag("</font>");
        fontAttributes = null;
    }


    /**
     * This is no longer used, instead &lt;span&gt; will be written out.
     * <p>
     * Writes out a start tag for the &lt;font&gt; tag.
     * Because font tags cannot be nested,
     * this method closes out
     * any enclosing font tag before writing out a
     * new start tag.
     *
     * <p>
     *  这已不再使用,而是&lt; span&gt;将被写出。
     * <p>
     *  为&lt; font&gt;写入一个开始标签。标签。因为字体标记不能嵌套,所以在写出新的开始标记之前,此方法会关闭任何封闭的字体标记。
     * 
     * 
     * @exception IOException on any I/O error
     */
    protected void startFontTag(String style) throws IOException {
        boolean callIndent = false;
        if (inFontTag()) {
            endFontTag();
            callIndent = true;
        }
        writeStartTag("<font style=\"" + style + "\">");
        if (callIndent) {
            indent();
        }
    }

    /**
     * Writes out a start tag for the &lt;font&gt; tag.
     * Because font tags cannot be nested,
     * this method closes out
     * any enclosing font tag before writing out a
     * new start tag.
     *
     * <p>
     *  为&lt; font&gt;写入一个开始标签。标签。因为字体标记不能嵌套,所以在写出新的开始标记之前,此方法会关闭任何封闭的字体标记。
     * 
     * 
     * @exception IOException on any I/O error
     */
    private void startSpanTag(String style) throws IOException {
        boolean callIndent = false;
        if (inFontTag()) {
            endSpanTag();
            callIndent = true;
        }
        writeStartTag("<span style=\"" + style + "\">");
        if (callIndent) {
            indent();
        }
    }

    /**
     * Writes out an end tag for the &lt;span&gt; tag.
     *
     * <p>
     *  为&lt; span&gt;写入结束标记标签。
     * 
     * 
     * @exception IOException on any I/O error
     */
    private void endSpanTag() throws IOException {
        write(NEWLINE);
        writeEndTag("</span>");
        fontAttributes = null;
    }

    /**
     * Adds the style named <code>style</code> to the style mapping. This
     * returns the name that should be used when outputting. CSS does not
     * allow the full Unicode set to be used as a style name.
     * <p>
     *  将名为<code> style </code>的样式添加到样式映射中。这将返回在输出时应使用的名称。 CSS不允许将完整的Unicode集用作样式名称。
     * 
     */
    private String addStyleName(String style) {
        if (styleNameMapping == null) {
            return style;
        }
        StringBuilder sb = null;
        for (int counter = style.length() - 1; counter >= 0; counter--) {
            if (!isValidCharacter(style.charAt(counter))) {
                if (sb == null) {
                    sb = new StringBuilder(style);
                }
                sb.setCharAt(counter, 'a');
            }
        }
        String mappedName = (sb != null) ? sb.toString() : style;
        while (styleNameMapping.get(mappedName) != null) {
            mappedName = mappedName + 'x';
        }
        styleNameMapping.put(style, mappedName);
        return mappedName;
    }

    /**
     * Returns the mapped style name corresponding to <code>style</code>.
     * <p>
     *  返回与<code> style </code>对应的映射样式名称。
     */
    private String mapStyleName(String style) {
        if (styleNameMapping == null) {
            return style;
        }
        String retValue = styleNameMapping.get(style);
        return (retValue == null) ? style : retValue;
    }

    private boolean isValidCharacter(char character) {
        return ((character >= 'a' && character <= 'z') ||
                (character >= 'A' && character <= 'Z'));
    }
}
