/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

import sun.awt.AppContext;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.text.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.TextUI;
import java.util.*;
import javax.accessibility.*;
import java.lang.ref.*;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * The Swing JEditorPane text component supports different kinds
 * of content via a plug-in mechanism called an EditorKit.  Because
 * HTML is a very popular format of content, some support is provided
 * by default.  The default support is provided by this class, which
 * supports HTML version 3.2 (with some extensions), and is migrating
 * toward version 4.0.
 * The &lt;applet&gt; tag is not supported, but some support is provided
 * for the &lt;object&gt; tag.
 * <p>
 * There are several goals of the HTML EditorKit provided, that have
 * an effect upon the way that HTML is modeled.  These
 * have influenced its design in a substantial way.
 * <dl>
 * <dt>
 * Support editing
 * <dd>
 * It might seem fairly obvious that a plug-in for JEditorPane
 * should provide editing support, but that fact has several
 * design considerations.  There are a substantial number of HTML
 * documents that don't properly conform to an HTML specification.
 * These must be normalized somewhat into a correct form if one
 * is to edit them.  Additionally, users don't like to be presented
 * with an excessive amount of structure editing, so using traditional
 * text editing gestures is preferred over using the HTML structure
 * exactly as defined in the HTML document.
 * <p>
 * The modeling of HTML is provided by the class <code>HTMLDocument</code>.
 * Its documentation describes the details of how the HTML is modeled.
 * The editing support leverages heavily off of the text package.
 *
 * <dt>
 * Extendable/Scalable
 * <dd>
 * To maximize the usefulness of this kit, a great deal of effort
 * has gone into making it extendable.  These are some of the
 * features.
 * <ol>
 *   <li>
 *   The parser is replaceable.  The default parser is the Hot Java
 *   parser which is DTD based.  A different DTD can be used, or an
 *   entirely different parser can be used.  To change the parser,
 *   reimplement the getParser method.  The default parser is
 *   dynamically loaded when first asked for, so the class files
 *   will never be loaded if an alternative parser is used.  The
 *   default parser is in a separate package called parser below
 *   this package.
 *   <li>
 *   The parser drives the ParserCallback, which is provided by
 *   HTMLDocument.  To change the callback, subclass HTMLDocument
 *   and reimplement the createDefaultDocument method to return
 *   document that produces a different reader.  The reader controls
 *   how the document is structured.  Although the Document provides
 *   HTML support by default, there is nothing preventing support of
 *   non-HTML tags that result in alternative element structures.
 *   <li>
 *   The default view of the models are provided as a hierarchy of
 *   View implementations, so one can easily customize how a particular
 *   element is displayed or add capabilities for new kinds of elements
 *   by providing new View implementations.  The default set of views
 *   are provided by the <code>HTMLFactory</code> class.  This can
 *   be easily changed by subclassing or replacing the HTMLFactory
 *   and reimplementing the getViewFactory method to return the alternative
 *   factory.
 *   <li>
 *   The View implementations work primarily off of CSS attributes,
 *   which are kept in the views.  This makes it possible to have
 *   multiple views mapped over the same model that appear substantially
 *   different.  This can be especially useful for printing.  For
 *   most HTML attributes, the HTML attributes are converted to CSS
 *   attributes for display.  This helps make the View implementations
 *   more general purpose
 * </ol>
 *
 * <dt>
 * Asynchronous Loading
 * <dd>
 * Larger documents involve a lot of parsing and take some time
 * to load.  By default, this kit produces documents that will be
 * loaded asynchronously if loaded using <code>JEditorPane.setPage</code>.
 * This is controlled by a property on the document.  The method
 * {@link #createDefaultDocument createDefaultDocument} can
 * be overriden to change this.  The batching of work is done
 * by the <code>HTMLDocument.HTMLReader</code> class.  The actual
 * work is done by the <code>DefaultStyledDocument</code> and
 * <code>AbstractDocument</code> classes in the text package.
 *
 * <dt>
 * Customization from current LAF
 * <dd>
 * HTML provides a well known set of features without exactly
 * specifying the display characteristics.  Swing has a theme
 * mechanism for its look-and-feel implementations.  It is desirable
 * for the look-and-feel to feed display characteristics into the
 * HTML views.  An user with poor vision for example would want
 * high contrast and larger than typical fonts.
 * <p>
 * The support for this is provided by the <code>StyleSheet</code>
 * class.  The presentation of the HTML can be heavily influenced
 * by the setting of the StyleSheet property on the EditorKit.
 *
 * <dt>
 * Not lossy
 * <dd>
 * An EditorKit has the ability to be read and save documents.
 * It is generally the most pleasing to users if there is no loss
 * of data between the two operation.  The policy of the HTMLEditorKit
 * will be to store things not recognized or not necessarily visible
 * so they can be subsequently written out.  The model of the HTML document
 * should therefore contain all information discovered while reading the
 * document.  This is constrained in some ways by the need to support
 * editing (i.e. incorrect documents sometimes must be normalized).
 * The guiding principle is that information shouldn't be lost, but
 * some might be synthesized to produce a more correct model or it might
 * be rearranged.
 * </dl>
 *
 * <p>
 *  Swing JEditorPane文本组件通过称为EditorKit的插件机制支持不同类型的内容因为HTML是一种非常流行的内容格式,所以默认提供了一些支持默认支持由此类提供,支持HTML版本32具有
 * 一些扩展),并且正朝向版本40迁移&lt; applet&gt;标签,但是为&lt; object&gt;标签提供了一些支持。
 * 标签。
 * <p>
 *  HTML EditorKit提供了几个目标,它们对HTML建模的方式有影响。这些都在很大程度上影响了它的设计
 * <dl>
 * <dt>
 *  支持编辑
 * <dd>
 * 看起来很明显,JEditorPane的插件应该提供编辑支持,但是这个事实有几个设计注意事项有大量的HTML文档不能正确符合HTML规范。
 * 这些必须被归一化为正确的形式(如果要编辑它们)另外,用户不喜欢呈现过多的结构编辑,因此使用传统的文本编辑手势比使用完全如在HTML文档中定义的HTML结构更受欢迎。
 * <p>
 *  HTML的建模由<code> HTMLDocument </code>类提供它的文档描述了如何建模HTML的细节编辑支持利用了大量的文本包
 * 
 * <dt>
 *  可扩展/可扩展
 * <dd>
 * 为了最大化这个套件的有用性,大量的努力已经使其可扩展这些是一些功能
 * <ol>
 * <li>
 *  解析器是可替换的默认解析器是基于DTD的热Java解析器可以使用不同的DTD,或者可以使用完全不同的解析器。
 * 更改解析器,重新实现getParser方法默认解析器在首次请求时动态加载,所以类文件将永远不会加载,如果使用替代解析器默认解析器是在一个单独的包名为解析器下面这个包。
 * <li>
 * 解析器驱动由HTMLDocument提供的ParserCallback要更改回调,将HTMLDocument子类化并重新实现createDefaultDocument方法以返回生成不同阅读器的文档读取器
 * 控制文档的结构虽然Document默认提供HTML支持,没有什么可以阻止支持导致替代元素结构的非HTML标记。
 * <li>
 * 模型的默认视图是作为View实现的层次结构提供的,因此可以通过提供新的View实现来轻松地定制特定元素的显示方式或为新类型的元素添加功能。
 * 默认的视图集由<code > HTMLFactory </code>类这可以通过子类化或替换HTMLFactory并重新实现getViewFactory方法来轻松更改,以返回替代工厂。
 * <li>
 * View实现主要是从CSS属性保存在视图中这使得有可能在同一模型上映射多个视图显示不同这对于打印尤其有用对于大多数HTML属性,HTML属性被转换到CSS属性显示这有助于使View实现更通用的目的
 * </ol>
 * 
 * <dt>
 *  异步加载
 * <dd>
 * 较大的文档涉及大量的解析并需要一些时间加载默认情况下,这个工具包生成的文档将被异步加载,如果使用<code> JEditorPanesetPage </code>加载这是由文档的一个属性控制方法{@link #createDefaultDocument createDefaultDocument}
 * 可以被覆盖改变这个批处理的工作是由<code> HTMLDocumentHTMLReader </code>类实现的工作是由<code> DefaultStyledDocument </code>和<code>
 *  AbstractDocument </code >在文本包中的类。
 * 
 * <dt>
 *  从当前LAF进行定制
 * <dd>
 * HTML提供了一组众所周知的特征而没有精确地指定显示特性Swing具有用于其外观和感觉实现的主题机制。
 * 期望的是,外观和感觉将显示特性馈送到HTML视图中具有较差视觉的用户例如想要高对比度和大于典型字体。
 * <p>
 *  对此的支持由<code> StyleSheet </code>类提供。HTML的表示可能受到EditorKit上的StyleSheet属性的设置的很大影响
 * 
 * <dt>
 *  不有损
 * <dd>
 * EditorKit具有读取和保存文档的能力如果两个操作之间没有数据丢失,通常是最令人满意的。
 * HTMLEditorKit的策略是存储未被识别的东西,或者不一定可见,因此他们可以随后写出HTML文档的模型因此应包含在阅读文档时发现的所有信息。
 * 这在某些方面受到需要支持编辑的约束(即,不正确的文档有时必须被归一化)指导原则是信息不应该丢失,但有些可能被合成以产生更正确的模型或可能被重新排列。
 * </dl>
 * 
 * 
 * @author  Timothy Prinzing
 */
public class HTMLEditorKit extends StyledEditorKit implements Accessible {

    private JEditorPane theEditor;

    /**
     * Constructs an HTMLEditorKit, creates a StyleContext,
     * and loads the style sheet.
     * <p>
     *  构造一个HTMLEditorKit,创建一个StyleContext,并加载样式表
     * 
     */
    public HTMLEditorKit() {

    }

    /**
     * Get the MIME type of the data that this
     * kit represents support for.  This kit supports
     * the type <code>text/html</code>.
     *
     * <p>
     * 获取此套件表示支持的数据的MIME类型此套件支持类型<code> text / html </code>
     * 
     * 
     * @return the type
     */
    public String getContentType() {
        return "text/html";
    }

    /**
     * Fetch a factory that is suitable for producing
     * views of any models that are produced by this
     * kit.
     *
     * <p>
     *  获取适合于生成此套件生产的任何型号视图的工厂
     * 
     * 
     * @return the factory
     */
    public ViewFactory getViewFactory() {
        return defaultFactory;
    }

    /**
     * Create an uninitialized text storage model
     * that is appropriate for this type of editor.
     *
     * <p>
     *  创建适合此类型编辑器的未初始化文本存储模型
     * 
     * 
     * @return the model
     */
    public Document createDefaultDocument() {
        StyleSheet styles = getStyleSheet();
        StyleSheet ss = new StyleSheet();

        ss.addStyleSheet(styles);

        HTMLDocument doc = new HTMLDocument(ss);
        doc.setParser(getParser());
        doc.setAsynchronousLoadPriority(4);
        doc.setTokenThreshold(100);
        return doc;
    }

    /**
     * Try to get an HTML parser from the document.  If no parser is set for
     * the document, return the editor kit's default parser.  It is an error
     * if no parser could be obtained from the editor kit.
     * <p>
     *  尝试从文档中获取HTML解析器如果没有为文档设置解析器,则返回编辑器工具箱的默认解析器如果无法从编辑器套件中获取解析器,则会出现错误
     * 
     */
    private Parser ensureParser(HTMLDocument doc) throws IOException {
        Parser p = doc.getParser();
        if (p == null) {
            p = getParser();
        }
        if (p == null) {
            throw new IOException("Can't load parser");
        }
        return p;
    }

    /**
     * Inserts content from the given stream. If <code>doc</code> is
     * an instance of HTMLDocument, this will read
     * HTML 3.2 text. Inserting HTML into a non-empty document must be inside
     * the body Element, if you do not insert into the body an exception will
     * be thrown. When inserting into a non-empty document all tags outside
     * of the body (head, title) will be dropped.
     *
     * <p>
     * 插入来自给定流的内容如果<code> doc </code>是HTMLDocument的实例,则将读取HTML 32文本将HTML插入到非空文档中必须在body元素内,如果不插入到主体中将抛出异常当插入
     * 到非空文档中时,将删除body(head,title)之外的所有标记。
     * 
     * 
     * @param in  the stream to read from
     * @param doc the destination for the insertion
     * @param pos the location in the document to place the
     *   content
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *   location within the document
     * @exception RuntimeException (will eventually be a BadLocationException)
     *            if pos is invalid
     */
    public void read(Reader in, Document doc, int pos) throws IOException, BadLocationException {

        if (doc instanceof HTMLDocument) {
            HTMLDocument hdoc = (HTMLDocument) doc;
            if (pos > doc.getLength()) {
                throw new BadLocationException("Invalid location", pos);
            }

            Parser p = ensureParser(hdoc);
            ParserCallback receiver = hdoc.getReader(pos);
            Boolean ignoreCharset = (Boolean)doc.getProperty("IgnoreCharsetDirective");
            p.parse(in, receiver, (ignoreCharset == null) ? false : ignoreCharset.booleanValue());
            receiver.flush();
        } else {
            super.read(in, doc, pos);
        }
    }

    /**
     * Inserts HTML into an existing document.
     *
     * <p>
     *  将HTML插入到现有文档中
     * 
     * 
     * @param doc       the document to insert into
     * @param offset    the offset to insert HTML at
     * @param popDepth  the number of ElementSpec.EndTagTypes to generate before
     *        inserting
     * @param pushDepth the number of ElementSpec.StartTagTypes with a direction
     *        of ElementSpec.JoinNextDirection that should be generated
     *        before inserting, but after the end tags have been generated
     * @param insertTag the first tag to start inserting into document
     * @exception RuntimeException (will eventually be a BadLocationException)
     *            if pos is invalid
     */
    public void insertHTML(HTMLDocument doc, int offset, String html,
                           int popDepth, int pushDepth,
                           HTML.Tag insertTag) throws
                       BadLocationException, IOException {
        if (offset > doc.getLength()) {
            throw new BadLocationException("Invalid location", offset);
        }

        Parser p = ensureParser(doc);
        ParserCallback receiver = doc.getReader(offset, popDepth, pushDepth,
                                                insertTag);
        Boolean ignoreCharset = (Boolean)doc.getProperty
                                ("IgnoreCharsetDirective");
        p.parse(new StringReader(html), receiver, (ignoreCharset == null) ?
                false : ignoreCharset.booleanValue());
        receiver.flush();
    }

    /**
     * Write content from a document to the given stream
     * in a format appropriate for this kind of content handler.
     *
     * <p>
     *  以适合此类内容处理程序的格式将内容从文档写入给定流
     * 
     * 
     * @param out  the stream to write to
     * @param doc  the source for the write
     * @param pos  the location in the document to fetch the
     *   content
     * @param len  the amount to write out
     * @exception IOException on any I/O error
     * @exception BadLocationException if pos represents an invalid
     *   location within the document
     */
    public void write(Writer out, Document doc, int pos, int len)
        throws IOException, BadLocationException {

        if (doc instanceof HTMLDocument) {
            HTMLWriter w = new HTMLWriter(out, (HTMLDocument)doc, pos, len);
            w.write();
        } else if (doc instanceof StyledDocument) {
            MinimalHTMLWriter w = new MinimalHTMLWriter(out, (StyledDocument)doc, pos, len);
            w.write();
        } else {
            super.write(out, doc, pos, len);
        }
    }

    /**
     * Called when the kit is being installed into the
     * a JEditorPane.
     *
     * <p>
     *  在将工具包安装到JEditorPane中时调用
     * 
     * 
     * @param c the JEditorPane
     */
    public void install(JEditorPane c) {
        c.addMouseListener(linkHandler);
        c.addMouseMotionListener(linkHandler);
        c.addCaretListener(nextLinkAction);
        super.install(c);
        theEditor = c;
    }

    /**
     * Called when the kit is being removed from the
     * JEditorPane.  This is used to unregister any
     * listeners that were attached.
     *
     * <p>
     *  在从JEditorPane中删除套件时调用此命令用于注销已连接的任何侦听器
     * 
     * 
     * @param c the JEditorPane
     */
    public void deinstall(JEditorPane c) {
        c.removeMouseListener(linkHandler);
        c.removeMouseMotionListener(linkHandler);
        c.removeCaretListener(nextLinkAction);
        super.deinstall(c);
        theEditor = null;
    }

    /**
     * Default Cascading Style Sheet file that sets
     * up the tag views.
     * <p>
     *  默认级联样式表文件,用于设置标记视图
     * 
     */
    public static final String DEFAULT_CSS = "default.css";

    /**
     * Set the set of styles to be used to render the various
     * HTML elements.  These styles are specified in terms of
     * CSS specifications.  Each document produced by the kit
     * will have a copy of the sheet which it can add the
     * document specific styles to.  By default, the StyleSheet
     * specified is shared by all HTMLEditorKit instances.
     * This should be reimplemented to provide a finer granularity
     * if desired.
     * <p>
     * 设置要用于呈现各种HTML元素的样式集合这些样式根据CSS规范指定由工具包生成的每个文档都将有一个工作表副本,它可以添加特定于文档的样式。
     * 默认情况下,StyleSheet指定由所有HTMLEditorKit实例共享。如果需要,应重新实现以提供更细的粒度。
     * 
     */
    public void setStyleSheet(StyleSheet s) {
        if (s == null) {
            AppContext.getAppContext().remove(DEFAULT_STYLES_KEY);
        } else {
            AppContext.getAppContext().put(DEFAULT_STYLES_KEY, s);
        }
    }

    /**
     * Get the set of styles currently being used to render the
     * HTML elements.  By default the resource specified by
     * DEFAULT_CSS gets loaded, and is shared by all HTMLEditorKit
     * instances.
     * <p>
     *  获取当前用于呈现HTML元素的样式集默认情况下,由DEFAULT_CSS指定的资源将被加载,并由所有HTMLEditorKit实例共享
     * 
     */
    public StyleSheet getStyleSheet() {
        AppContext appContext = AppContext.getAppContext();
        StyleSheet defaultStyles = (StyleSheet) appContext.get(DEFAULT_STYLES_KEY);

        if (defaultStyles == null) {
            defaultStyles = new StyleSheet();
            appContext.put(DEFAULT_STYLES_KEY, defaultStyles);
            try {
                InputStream is = HTMLEditorKit.getResourceAsStream(DEFAULT_CSS);
                Reader r = new BufferedReader(
                        new InputStreamReader(is, "ISO-8859-1"));
                defaultStyles.loadRules(r, null);
                r.close();
            } catch (Throwable e) {
                // on error we simply have no styles... the html
                // will look mighty wrong but still function.
            }
        }
        return defaultStyles;
    }

    /**
     * Fetch a resource relative to the HTMLEditorKit classfile.
     * If this is called on 1.2 the loading will occur under the
     * protection of a doPrivileged call to allow the HTMLEditorKit
     * to function when used in an applet.
     *
     * <p>
     *  获取相对于HTMLEditorKit类文件的资源如果在12上调用,加载将发生在doPrivileged调用的保护下,以允许HTMLEditorKit在applet中使用时起作用
     * 
     * 
     * @param name the name of the resource, relative to the
     *  HTMLEditorKit class
     * @return a stream representing the resource
     */
    static InputStream getResourceAsStream(final String name) {
        return AccessController.doPrivileged(
                new PrivilegedAction<InputStream>() {
                    public InputStream run() {
                        return HTMLEditorKit.class.getResourceAsStream(name);
                    }
                });
    }

    /**
     * Fetches the command list for the editor.  This is
     * the list of commands supported by the superclass
     * augmented by the collection of commands defined
     * locally for style operations.
     *
     * <p>
     * 获取编辑器的命令列表这是超类支持的命令列表,由样式操作本地定义的命令集合扩充
     * 
     * 
     * @return the command list
     */
    public Action[] getActions() {
        return TextAction.augmentList(super.getActions(), this.defaultActions);
    }

    /**
     * Copies the key/values in <code>element</code>s AttributeSet into
     * <code>set</code>. This does not copy component, icon, or element
     * names attributes. Subclasses may wish to refine what is and what
     * isn't copied here. But be sure to first remove all the attributes that
     * are in <code>set</code>.<p>
     * This is called anytime the caret moves over a different location.
     *
     * <p>
     *  将<code> element </code>的AttributeSet中的键/值复制到<code> set </code>这不会复制组件,图标或元素名称属性子类可能希望改进什么是什么,这里但是请确保
     * 先删除<code> set </code> <p>中的所有属性。
     * 这是在插入符移动到不同位置时调用的。
     * 
     */
    protected void createInputAttributes(Element element,
                                         MutableAttributeSet set) {
        set.removeAttributes(set);
        set.addAttributes(element.getAttributes());
        set.removeAttribute(StyleConstants.ComposedTextAttribute);

        Object o = set.getAttribute(StyleConstants.NameAttribute);
        if (o instanceof HTML.Tag) {
            HTML.Tag tag = (HTML.Tag)o;
            // PENDING: we need a better way to express what shouldn't be
            // copied when editing...
            if(tag == HTML.Tag.IMG) {
                // Remove the related image attributes, src, width, height
                set.removeAttribute(HTML.Attribute.SRC);
                set.removeAttribute(HTML.Attribute.HEIGHT);
                set.removeAttribute(HTML.Attribute.WIDTH);
                set.addAttribute(StyleConstants.NameAttribute,
                                 HTML.Tag.CONTENT);
            }
            else if (tag == HTML.Tag.HR || tag == HTML.Tag.BR) {
                // Don't copy HRs or BRs either.
                set.addAttribute(StyleConstants.NameAttribute,
                                 HTML.Tag.CONTENT);
            }
            else if (tag == HTML.Tag.COMMENT) {
                // Don't copy COMMENTs either
                set.addAttribute(StyleConstants.NameAttribute,
                                 HTML.Tag.CONTENT);
                set.removeAttribute(HTML.Attribute.COMMENT);
            }
            else if (tag == HTML.Tag.INPUT) {
                // or INPUT either
                set.addAttribute(StyleConstants.NameAttribute,
                                 HTML.Tag.CONTENT);
                set.removeAttribute(HTML.Tag.INPUT);
            }
            else if (tag instanceof HTML.UnknownTag) {
                // Don't copy unknowns either:(
                set.addAttribute(StyleConstants.NameAttribute,
                                 HTML.Tag.CONTENT);
                set.removeAttribute(HTML.Attribute.ENDTAG);
            }
        }
    }

    /**
     * Gets the input attributes used for the styled
     * editing actions.
     *
     * <p>
     *  获取用于样式编辑操作的输入属性
     * 
     * 
     * @return the attribute set
     */
    public MutableAttributeSet getInputAttributes() {
        if (input == null) {
            input = getStyleSheet().addStyle(null, null);
        }
        return input;
    }

    /**
     * Sets the default cursor.
     *
     * <p>
     *  设置默认光标
     * 
     * 
     * @since 1.3
     */
    public void setDefaultCursor(Cursor cursor) {
        defaultCursor = cursor;
    }

    /**
     * Returns the default cursor.
     *
     * <p>
     *  返回默认游标
     * 
     * 
     * @since 1.3
     */
    public Cursor getDefaultCursor() {
        return defaultCursor;
    }

    /**
     * Sets the cursor to use over links.
     *
     * <p>
     *  将光标设置为通过链接使用
     * 
     * 
     * @since 1.3
     */
    public void setLinkCursor(Cursor cursor) {
        linkCursor = cursor;
    }

    /**
     * Returns the cursor to use over hyper links.
     * <p>
     *  返回要通过超链接使用的游标
     * 
     * 
     * @since 1.3
     */
    public Cursor getLinkCursor() {
        return linkCursor;
    }

    /**
     * Indicates whether an html form submission is processed automatically
     * or only <code>FormSubmitEvent</code> is fired.
     *
     * <p>
     * 指示是自动处理html表单提交还是仅触发<code> FormSubmitEvent </code>
     * 
     * 
     * @return true  if html form submission is processed automatically,
     *         false otherwise.
     *
     * @see #setAutoFormSubmission
     * @since 1.5
     */
    public boolean isAutoFormSubmission() {
        return isAutoFormSubmission;
    }

    /**
     * Specifies if an html form submission is processed
     * automatically or only <code>FormSubmitEvent</code> is fired.
     * By default it is set to true.
     *
     * <p>
     *  指定是否自动处理html表单提交,或只有<code> FormSubmitEvent </code>被触发。默认情况下,它设置为true
     * 
     * 
     * @see #isAutoFormSubmission()
     * @see FormSubmitEvent
     * @since 1.5
     */
    public void setAutoFormSubmission(boolean isAuto) {
        isAutoFormSubmission = isAuto;
    }

    /**
     * Creates a copy of the editor kit.
     *
     * <p>
     *  创建编辑器工具包的副本
     * 
     * 
     * @return the copy
     */
    public Object clone() {
        HTMLEditorKit o = (HTMLEditorKit)super.clone();
        if (o != null) {
            o.input = null;
            o.linkHandler = new LinkController();
        }
        return o;
    }

    /**
     * Fetch the parser to use for reading HTML streams.
     * This can be reimplemented to provide a different
     * parser.  The default implementation is loaded dynamically
     * to avoid the overhead of loading the default parser if
     * it's not used.  The default parser is the HotJava parser
     * using an HTML 3.2 DTD.
     * <p>
     *  获取用于读取HTML流的解析器可以重新实现以提供不同的解析器默认实现是动态加载的,以避免加载默认解析器(如果没有使用)的开销默认解析器是使用HTML 32 DTD的HotJava解析器
     * 
     */
    protected Parser getParser() {
        if (defaultParser == null) {
            try {
                Class c = Class.forName("javax.swing.text.html.parser.ParserDelegator");
                defaultParser = (Parser) c.newInstance();
            } catch (Throwable e) {
            }
        }
        return defaultParser;
    }

    // ----- Accessibility support -----
    private AccessibleContext accessibleContext;

    /**
     * returns the AccessibleContext associated with this editor kit
     *
     * <p>
     *  返回与此编辑器工具包相关联的AccessibleContext
     * 
     * 
     * @return the AccessibleContext associated with this editor kit
     * @since 1.4
     */
    public AccessibleContext getAccessibleContext() {
        if (theEditor == null) {
            return null;
        }
        if (accessibleContext == null) {
            AccessibleHTML a = new AccessibleHTML(theEditor);
            accessibleContext = a.getAccessibleContext();
        }
        return accessibleContext;
    }

    // --- variables ------------------------------------------

    private static final Cursor MoveCursor = Cursor.getPredefinedCursor
                                    (Cursor.HAND_CURSOR);
    private static final Cursor DefaultCursor = Cursor.getPredefinedCursor
                                    (Cursor.DEFAULT_CURSOR);

    /** Shared factory for creating HTML Views. */
    private static final ViewFactory defaultFactory = new HTMLFactory();

    MutableAttributeSet input;
    private static final Object DEFAULT_STYLES_KEY = new Object();
    private LinkController linkHandler = new LinkController();
    private static Parser defaultParser = null;
    private Cursor defaultCursor = DefaultCursor;
    private Cursor linkCursor = MoveCursor;
    private boolean isAutoFormSubmission = true;

    /**
     * Class to watch the associated component and fire
     * hyperlink events on it when appropriate.
     * <p>
     *  类在适当的时候监视相关组件并在其上触发超链接事件
     * 
     */
    public static class LinkController extends MouseAdapter implements MouseMotionListener, Serializable {
        private Element curElem = null;
        /**
         * If true, the current element (curElem) represents an image.
         * <p>
         * 如果为true,则当前元素(curElem)表示图像
         * 
         */
        private boolean curElemImage = false;
        private String href = null;
        /** This is used by viewToModel to avoid allocing a new array each
        /* <p>
        /* 
         * time. */
        private transient Position.Bias[] bias = new Position.Bias[1];
        /**
         * Current offset.
         * <p>
         *  电流偏移
         * 
         */
        private int curOffset;

        /**
         * Called for a mouse click event.
         * If the component is read-only (ie a browser) then
         * the clicked event is used to drive an attempt to
         * follow the reference specified by a link.
         *
         * <p>
         *  调用鼠标单击事件如果组件是只读的(即浏览器),则单击的事件用于驱动尝试遵循链接指定的引用
         * 
         * 
         * @param e the mouse event
         * @see MouseListener#mouseClicked
         */
        public void mouseClicked(MouseEvent e) {
            JEditorPane editor = (JEditorPane) e.getSource();

            if (! editor.isEditable() && editor.isEnabled() &&
                    SwingUtilities.isLeftMouseButton(e)) {
                Point pt = new Point(e.getX(), e.getY());
                int pos = editor.viewToModel(pt);
                if (pos >= 0) {
                    activateLink(pos, editor, e);
                }
            }
        }

        // ignore the drags
        public void mouseDragged(MouseEvent e) {
        }

        // track the moving of the mouse.
        public void mouseMoved(MouseEvent e) {
            JEditorPane editor = (JEditorPane) e.getSource();
            if (!editor.isEnabled()) {
                return;
            }

            HTMLEditorKit kit = (HTMLEditorKit)editor.getEditorKit();
            boolean adjustCursor = true;
            Cursor newCursor = kit.getDefaultCursor();
            if (!editor.isEditable()) {
                Point pt = new Point(e.getX(), e.getY());
                int pos = editor.getUI().viewToModel(editor, pt, bias);
                if (bias[0] == Position.Bias.Backward && pos > 0) {
                    pos--;
                }
                if (pos >= 0 &&(editor.getDocument() instanceof HTMLDocument)){
                    HTMLDocument hdoc = (HTMLDocument)editor.getDocument();
                    Element elem = hdoc.getCharacterElement(pos);
                    if (!doesElementContainLocation(editor, elem, pos,
                                                    e.getX(), e.getY())) {
                        elem = null;
                    }
                    if (curElem != elem || curElemImage) {
                        Element lastElem = curElem;
                        curElem = elem;
                        String href = null;
                        curElemImage = false;
                        if (elem != null) {
                            AttributeSet a = elem.getAttributes();
                            AttributeSet anchor = (AttributeSet)a.
                                                   getAttribute(HTML.Tag.A);
                            if (anchor == null) {
                                curElemImage = (a.getAttribute(StyleConstants.
                                            NameAttribute) == HTML.Tag.IMG);
                                if (curElemImage) {
                                    href = getMapHREF(editor, hdoc, elem, a,
                                                      pos, e.getX(), e.getY());
                                }
                            }
                            else {
                                href = (String)anchor.getAttribute
                                    (HTML.Attribute.HREF);
                            }
                        }

                        if (href != this.href) {
                            // reference changed, fire event(s)
                            fireEvents(editor, hdoc, href, lastElem, e);
                            this.href = href;
                            if (href != null) {
                                newCursor = kit.getLinkCursor();
                            }
                        }
                        else {
                            adjustCursor = false;
                        }
                    }
                    else {
                        adjustCursor = false;
                    }
                    curOffset = pos;
                }
            }
            if (adjustCursor && editor.getCursor() != newCursor) {
                editor.setCursor(newCursor);
            }
        }

        /**
         * Returns a string anchor if the passed in element has a
         * USEMAP that contains the passed in location.
         * <p>
         *  如果传入的in元素具有包含传入的位置的USEMAP,则返回字符串anchor
         * 
         */
        private String getMapHREF(JEditorPane html, HTMLDocument hdoc,
                                  Element elem, AttributeSet attr, int offset,
                                  int x, int y) {
            Object useMap = attr.getAttribute(HTML.Attribute.USEMAP);
            if (useMap != null && (useMap instanceof String)) {
                Map m = hdoc.getMap((String)useMap);
                if (m != null && offset < hdoc.getLength()) {
                    Rectangle bounds;
                    TextUI ui = html.getUI();
                    try {
                        Shape lBounds = ui.modelToView(html, offset,
                                                   Position.Bias.Forward);
                        Shape rBounds = ui.modelToView(html, offset + 1,
                                                   Position.Bias.Backward);
                        bounds = lBounds.getBounds();
                        bounds.add((rBounds instanceof Rectangle) ?
                                    (Rectangle)rBounds : rBounds.getBounds());
                    } catch (BadLocationException ble) {
                        bounds = null;
                    }
                    if (bounds != null) {
                        AttributeSet area = m.getArea(x - bounds.x,
                                                      y - bounds.y,
                                                      bounds.width,
                                                      bounds.height);
                        if (area != null) {
                            return (String)area.getAttribute(HTML.Attribute.
                                                             HREF);
                        }
                    }
                }
            }
            return null;
        }

        /**
         * Returns true if the View representing <code>e</code> contains
         * the location <code>x</code>, <code>y</code>. <code>offset</code>
         * gives the offset into the Document to check for.
         * <p>
         *  如果代表<code> e </code>的视图包含位置<code> x </code>,<code> y </code> <code> offset </code>对于
         * 
         */
        private boolean doesElementContainLocation(JEditorPane editor,
                                                   Element e, int offset,
                                                   int x, int y) {
            if (e != null && offset > 0 && e.getStartOffset() == offset) {
                try {
                    TextUI ui = editor.getUI();
                    Shape s1 = ui.modelToView(editor, offset,
                                              Position.Bias.Forward);
                    if (s1 == null) {
                        return false;
                    }
                    Rectangle r1 = (s1 instanceof Rectangle) ? (Rectangle)s1 :
                                    s1.getBounds();
                    Shape s2 = ui.modelToView(editor, e.getEndOffset(),
                                              Position.Bias.Backward);
                    if (s2 != null) {
                        Rectangle r2 = (s2 instanceof Rectangle) ? (Rectangle)s2 :
                                    s2.getBounds();
                        r1.add(r2);
                    }
                    return r1.contains(x, y);
                } catch (BadLocationException ble) {
                }
            }
            return true;
        }

        /**
         * Calls linkActivated on the associated JEditorPane
         * if the given position represents a link.<p>This is implemented
         * to forward to the method with the same name, but with the following
         * args both == -1.
         *
         * <p>
         *  调用linkActivated在相关联的JEditorPane上,如果给定位置表示链接<p>这被实现为转发到具有相同名称的方法,但具有以下args均== == -1
         * 
         * 
         * @param pos the position
         * @param editor the editor pane
         */
        protected void activateLink(int pos, JEditorPane editor) {
            activateLink(pos, editor, null);
        }

        /**
         * Calls linkActivated on the associated JEditorPane
         * if the given position represents a link. If this was the result
         * of a mouse click, <code>x</code> and
         * <code>y</code> will give the location of the mouse, otherwise
         * they will be {@literal <} 0.
         *
         * <p>
         * 如果给定位置表示链接,则在关联的JEditorPane上调用linkedActivated如果这是鼠标点击的结果,则<code> x </code>和<code> y </code>将给出鼠标的位置,否
         * 则,将为{@literal <} 0。
         * 
         * 
         * @param pos the position
         * @param html the editor pane
         */
        void activateLink(int pos, JEditorPane html, MouseEvent mouseEvent) {
            Document doc = html.getDocument();
            if (doc instanceof HTMLDocument) {
                HTMLDocument hdoc = (HTMLDocument) doc;
                Element e = hdoc.getCharacterElement(pos);
                AttributeSet a = e.getAttributes();
                AttributeSet anchor = (AttributeSet)a.getAttribute(HTML.Tag.A);
                HyperlinkEvent linkEvent = null;
                String description;
                int x = -1;
                int y = -1;

                if (mouseEvent != null) {
                    x = mouseEvent.getX();
                    y = mouseEvent.getY();
                }

                if (anchor == null) {
                    href = getMapHREF(html, hdoc, e, a, pos, x, y);
                }
                else {
                    href = (String)anchor.getAttribute(HTML.Attribute.HREF);
                }

                if (href != null) {
                    linkEvent = createHyperlinkEvent(html, hdoc, href, anchor,
                                                     e, mouseEvent);
                }
                if (linkEvent != null) {
                    html.fireHyperlinkUpdate(linkEvent);
                }
            }
        }

        /**
         * Creates and returns a new instance of HyperlinkEvent. If
         * <code>hdoc</code> is a frame document a HTMLFrameHyperlinkEvent
         * will be created.
         * <p>
         *  创建并返回一个新的HyperlinkEvent实例如果<code> hdoc </code>是一个框架文档,将创建一个HTMLFrameHyperlinkEvent
         * 
         */
        HyperlinkEvent createHyperlinkEvent(JEditorPane html,
                                            HTMLDocument hdoc, String href,
                                            AttributeSet anchor,
                                            Element element,
                                            MouseEvent mouseEvent) {
            URL u;
            try {
                URL base = hdoc.getBase();
                u = new URL(base, href);
                // Following is a workaround for 1.2, in which
                // new URL("file://...", "#...") causes the filename to
                // be lost.
                if (href != null && "file".equals(u.getProtocol()) &&
                    href.startsWith("#")) {
                    String baseFile = base.getFile();
                    String newFile = u.getFile();
                    if (baseFile != null && newFile != null &&
                        !newFile.startsWith(baseFile)) {
                        u = new URL(base, baseFile + href);
                    }
                }
            } catch (MalformedURLException m) {
                u = null;
            }
            HyperlinkEvent linkEvent;

            if (!hdoc.isFrameDocument()) {
                linkEvent = new HyperlinkEvent(
                        html, HyperlinkEvent.EventType.ACTIVATED, u, href,
                        element, mouseEvent);
            } else {
                String target = (anchor != null) ?
                    (String)anchor.getAttribute(HTML.Attribute.TARGET) : null;
                if ((target == null) || (target.equals(""))) {
                    target = hdoc.getBaseTarget();
                }
                if ((target == null) || (target.equals(""))) {
                    target = "_self";
                }
                    linkEvent = new HTMLFrameHyperlinkEvent(
                        html, HyperlinkEvent.EventType.ACTIVATED, u, href,
                        element, mouseEvent, target);
            }
            return linkEvent;
        }

        void fireEvents(JEditorPane editor, HTMLDocument doc, String href,
                        Element lastElem, MouseEvent mouseEvent) {
            if (this.href != null) {
                // fire an exited event on the old link
                URL u;
                try {
                    u = new URL(doc.getBase(), this.href);
                } catch (MalformedURLException m) {
                    u = null;
                }
                HyperlinkEvent exit = new HyperlinkEvent(editor,
                                 HyperlinkEvent.EventType.EXITED, u, this.href,
                                 lastElem, mouseEvent);
                editor.fireHyperlinkUpdate(exit);
            }
            if (href != null) {
                // fire an entered event on the new link
                URL u;
                try {
                    u = new URL(doc.getBase(), href);
                } catch (MalformedURLException m) {
                    u = null;
                }
                HyperlinkEvent entered = new HyperlinkEvent(editor,
                                            HyperlinkEvent.EventType.ENTERED,
                                            u, href, curElem, mouseEvent);
                editor.fireHyperlinkUpdate(entered);
            }
        }
    }

    /**
     * Interface to be supported by the parser.  This enables
     * providing a different parser while reusing some of the
     * implementation provided by this editor kit.
     * <p>
     *  解析器支持的接口这允许提供不同的解析器,同时重用此编辑器工具包提供的一些实现
     * 
     */
    public static abstract class Parser {
        /**
         * Parse the given stream and drive the given callback
         * with the results of the parse.  This method should
         * be implemented to be thread-safe.
         * <p>
         *  解析给定的流并驱动给定的回调与解析的结果此方法应实现为线程安全
         * 
         */
        public abstract void parse(Reader r, ParserCallback cb, boolean ignoreCharSet) throws IOException;

    }

    /**
     * The result of parsing drives these callback methods.
     * The open and close actions should be balanced.  The
     * <code>flush</code> method will be the last method
     * called, to give the receiver a chance to flush any
     * pending data into the document.
     * <p>Refer to DocumentParser, the default parser used, for further
     * information on the contents of the AttributeSets, the positions, and
     * other info.
     *
     * <p>
     * 解析的结果驱动这些回调方法打开和关闭动作应该是平衡的</code> flush </code>方法将是最后一个调用的方法,让接收者有机会将任何挂起的数据冲入文档<p>参考DocumentParser,使
     * 用的默认解析器,有关AttributeSets的内容,位置和其他信息的更多信息。
     * 
     * 
     * @see javax.swing.text.html.parser.DocumentParser
     */
    public static class ParserCallback {
        /**
         * This is passed as an attribute in the attributeset to indicate
         * the element is implied eg, the string '&lt;&gt;foo&lt;\t&gt;'
         * contains an implied html element and an implied body element.
         *
         * <p>
         *  这作为属性集中的属性被传递以指示元素被隐含,例如,字符串'&lt;&gt; foo&lt; \\ t&gt;包含一个隐含的html元素和一个隐含的body元素
         * 
         * 
         * @since 1.3
         */
        public static final Object IMPLIED = "_implied_";


        public void flush() throws BadLocationException {
        }

        public void handleText(char[] data, int pos) {
        }

        public void handleComment(char[] data, int pos) {
        }

        public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
        }

        public void handleEndTag(HTML.Tag t, int pos) {
        }

        public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
        }

        public void handleError(String errorMsg, int pos){
        }

        /**
         * This is invoked after the stream has been parsed, but before
         * <code>flush</code>. <code>eol</code> will be one of \n, \r
         * or \r\n, which ever is encountered the most in parsing the
         * stream.
         *
         * <p>
         *  这在流被解析之后但在<code> flush </code> <code> eol </code>之前是\n,\\ r或\\ r\n之一被调用大多数在解析流
         * 
         * 
         * @since 1.3
         */
        public void handleEndOfLineString(String eol) {
        }
    }

    /**
     * A factory to build views for HTML.  The following
     * table describes what this factory will build by
     * default.
     *
     * <table summary="Describes the tag and view created by this factory by default">
     * <tr>
     * <th align=left>Tag<th align=left>View created
     * </tr><tr>
     * <td>HTML.Tag.CONTENT<td>InlineView
     * </tr><tr>
     * <td>HTML.Tag.IMPLIED<td>javax.swing.text.html.ParagraphView
     * </tr><tr>
     * <td>HTML.Tag.P<td>javax.swing.text.html.ParagraphView
     * </tr><tr>
     * <td>HTML.Tag.H1<td>javax.swing.text.html.ParagraphView
     * </tr><tr>
     * <td>HTML.Tag.H2<td>javax.swing.text.html.ParagraphView
     * </tr><tr>
     * <td>HTML.Tag.H3<td>javax.swing.text.html.ParagraphView
     * </tr><tr>
     * <td>HTML.Tag.H4<td>javax.swing.text.html.ParagraphView
     * </tr><tr>
     * <td>HTML.Tag.H5<td>javax.swing.text.html.ParagraphView
     * </tr><tr>
     * <td>HTML.Tag.H6<td>javax.swing.text.html.ParagraphView
     * </tr><tr>
     * <td>HTML.Tag.DT<td>javax.swing.text.html.ParagraphView
     * </tr><tr>
     * <td>HTML.Tag.MENU<td>ListView
     * </tr><tr>
     * <td>HTML.Tag.DIR<td>ListView
     * </tr><tr>
     * <td>HTML.Tag.UL<td>ListView
     * </tr><tr>
     * <td>HTML.Tag.OL<td>ListView
     * </tr><tr>
     * <td>HTML.Tag.LI<td>BlockView
     * </tr><tr>
     * <td>HTML.Tag.DL<td>BlockView
     * </tr><tr>
     * <td>HTML.Tag.DD<td>BlockView
     * </tr><tr>
     * <td>HTML.Tag.BODY<td>BlockView
     * </tr><tr>
     * <td>HTML.Tag.HTML<td>BlockView
     * </tr><tr>
     * <td>HTML.Tag.CENTER<td>BlockView
     * </tr><tr>
     * <td>HTML.Tag.DIV<td>BlockView
     * </tr><tr>
     * <td>HTML.Tag.BLOCKQUOTE<td>BlockView
     * </tr><tr>
     * <td>HTML.Tag.PRE<td>BlockView
     * </tr><tr>
     * <td>HTML.Tag.BLOCKQUOTE<td>BlockView
     * </tr><tr>
     * <td>HTML.Tag.PRE<td>BlockView
     * </tr><tr>
     * <td>HTML.Tag.IMG<td>ImageView
     * </tr><tr>
     * <td>HTML.Tag.HR<td>HRuleView
     * </tr><tr>
     * <td>HTML.Tag.BR<td>BRView
     * </tr><tr>
     * <td>HTML.Tag.TABLE<td>javax.swing.text.html.TableView
     * </tr><tr>
     * <td>HTML.Tag.INPUT<td>FormView
     * </tr><tr>
     * <td>HTML.Tag.SELECT<td>FormView
     * </tr><tr>
     * <td>HTML.Tag.TEXTAREA<td>FormView
     * </tr><tr>
     * <td>HTML.Tag.OBJECT<td>ObjectView
     * </tr><tr>
     * <td>HTML.Tag.FRAMESET<td>FrameSetView
     * </tr><tr>
     * <td>HTML.Tag.FRAME<td>FrameView
     * </tr>
     * </table>
     * <p>
     * 构建HTML视图的工厂下表描述了默认情况下将构建的工厂
     * 
     * <table summary="Describes the tag and view created by this factory by default">
     * <tr>
     * <th align = left> Tag <th align = left> View created </tr> <tr> <td> HTMLTagCONTENT <td> InlineView </tr>
     *  <tr> <td> HTMLTagIMPLIED <td> javaxswingtexthtmlParagraphView </tr> < tr> <td> HTMLTagP <td> javaxsw
     * ingtexthtmlParagraphView </tr> <tr> <td> HTMLTagH1 <td> javaxswingtexthtmlParagraphView </tr> <tr> <td>
     *  HTMLTagH2 <td> javaxswingtexthtmlParagraphView </tr> <tr> <td> HTMLTagH3 <td> javaxswingtexthtmlPara
     * graphView </tr> <td> javaxswingtexthtmlParagraphView </tr> <trd> <td> HTMLTagH4 <td> javaxswingtextht
     * mlParagraphView </tr> <td> </tr> <tr> <td> HTMLTagDT <td> HTMLTagDIR <td> HTMLTagDIR <td> </tr> <trd>
     *  <td> ListView </tr> tr> <td> HTMLTagUL <td> ListView </tr> <tr> <td> HTMLTagOL <td> ListView </tr> <tr>
     *  <td> HTMLTag李<TD> BlockView用来</TR> <TR> <TD> HTMLTagDL <TD> BlockView用来</TR> <TR> <TD> HTMLTagDD <TD>
     *  BlockView用来</TR> <TR> <TD> HTMLTagBODY <TD> BlockView用来</TR> <TR> <TD> HTMLTagHTML <TD> BlockView用来</TR>
     *  <TR> <TD> HTMLTagCENTER <TD> BlockView用来</TR> <TR> <TD> HTMLTagDIV <TD> BlockView用来</TR> < TR> <TD> 
     * HTMLTagBLOCKQUOTE <TD> BlockView用来</TR> <TR> <TD> HTMLTagPRE <TD> BlockView用来</TR> <TR> <TD> HTMLTagB
     * LOCKQUOTE <TD> BlockView用来</TR> <TR> <TD> HTMLTagPRE <TD> BlockView用来</TR> <TR> <TD> HTMLTagIMG <TD>的
     * ImageView </TR> <TR> <TD> HTMLTagHR <TD> HRuleView </TR> <TR> <TD> HTMLTagBR <TD> BRView </TR> <TR> <TD>
     *  HTMLTagTABLE <TD> javaxswingtexthtmlTableView </TR> <TR> <TD> HTMLTagINPUT <TD> FormView控件</TR> <TR>
     *  <TD> HTMLTagSELECT <TD> FormView控件</TR> < TR> <TD> HTMLTagTEXTAREA <TD> FormView控件</TR> <TR> <TD> HT
     * MLTagOBJECT <TD>的ObjectView </TR> <TR> <TD> HTMLTagFRAMESET <td> FrameSetView </tr> <tr> <td> HTMLTag
     * FRAME <td> FrameView。
     * </tr>
     * </table>
     */
    public static class HTMLFactory implements ViewFactory {

        /**
         * Creates a view from an element.
         *
         * <p>
         * 从元素创建视图
         * 
         * 
         * @param elem the element
         * @return the view
         */
        public View create(Element elem) {
            AttributeSet attrs = elem.getAttributes();
            Object elementName =
                attrs.getAttribute(AbstractDocument.ElementNameAttribute);
            Object o = (elementName != null) ?
                null : attrs.getAttribute(StyleConstants.NameAttribute);
            if (o instanceof HTML.Tag) {
                HTML.Tag kind = (HTML.Tag) o;
                if (kind == HTML.Tag.CONTENT) {
                    return new InlineView(elem);
                } else if (kind == HTML.Tag.IMPLIED) {
                    String ws = (String) elem.getAttributes().getAttribute(
                        CSS.Attribute.WHITE_SPACE);
                    if ((ws != null) && ws.equals("pre")) {
                        return new LineView(elem);
                    }
                    return new javax.swing.text.html.ParagraphView(elem);
                } else if ((kind == HTML.Tag.P) ||
                           (kind == HTML.Tag.H1) ||
                           (kind == HTML.Tag.H2) ||
                           (kind == HTML.Tag.H3) ||
                           (kind == HTML.Tag.H4) ||
                           (kind == HTML.Tag.H5) ||
                           (kind == HTML.Tag.H6) ||
                           (kind == HTML.Tag.DT)) {
                    // paragraph
                    return new javax.swing.text.html.ParagraphView(elem);
                } else if ((kind == HTML.Tag.MENU) ||
                           (kind == HTML.Tag.DIR) ||
                           (kind == HTML.Tag.UL)   ||
                           (kind == HTML.Tag.OL)) {
                    return new ListView(elem);
                } else if (kind == HTML.Tag.BODY) {
                    return new BodyBlockView(elem);
                } else if (kind == HTML.Tag.HTML) {
                    return new BlockView(elem, View.Y_AXIS);
                } else if ((kind == HTML.Tag.LI) ||
                           (kind == HTML.Tag.CENTER) ||
                           (kind == HTML.Tag.DL) ||
                           (kind == HTML.Tag.DD) ||
                           (kind == HTML.Tag.DIV) ||
                           (kind == HTML.Tag.BLOCKQUOTE) ||
                           (kind == HTML.Tag.PRE) ||
                           (kind == HTML.Tag.FORM)) {
                    // vertical box
                    return new BlockView(elem, View.Y_AXIS);
                } else if (kind == HTML.Tag.NOFRAMES) {
                    return new NoFramesView(elem, View.Y_AXIS);
                } else if (kind==HTML.Tag.IMG) {
                    return new ImageView(elem);
                } else if (kind == HTML.Tag.ISINDEX) {
                    return new IsindexView(elem);
                } else if (kind == HTML.Tag.HR) {
                    return new HRuleView(elem);
                } else if (kind == HTML.Tag.BR) {
                    return new BRView(elem);
                } else if (kind == HTML.Tag.TABLE) {
                    return new javax.swing.text.html.TableView(elem);
                } else if ((kind == HTML.Tag.INPUT) ||
                           (kind == HTML.Tag.SELECT) ||
                           (kind == HTML.Tag.TEXTAREA)) {
                    return new FormView(elem);
                } else if (kind == HTML.Tag.OBJECT) {
                    return new ObjectView(elem);
                } else if (kind == HTML.Tag.FRAMESET) {
                     if (elem.getAttributes().isDefined(HTML.Attribute.ROWS)) {
                         return new FrameSetView(elem, View.Y_AXIS);
                     } else if (elem.getAttributes().isDefined(HTML.Attribute.COLS)) {
                         return new FrameSetView(elem, View.X_AXIS);
                     }
                     throw new RuntimeException("Can't build a"  + kind + ", " + elem + ":" +
                                     "no ROWS or COLS defined.");
                } else if (kind == HTML.Tag.FRAME) {
                    return new FrameView(elem);
                } else if (kind instanceof HTML.UnknownTag) {
                    return new HiddenTagView(elem);
                } else if (kind == HTML.Tag.COMMENT) {
                    return new CommentView(elem);
                } else if (kind == HTML.Tag.HEAD) {
                    // Make the head never visible, and never load its
                    // children. For Cursor positioning,
                    // getNextVisualPositionFrom is overriden to always return
                    // the end offset of the element.
                    return new BlockView(elem, View.X_AXIS) {
                        public float getPreferredSpan(int axis) {
                            return 0;
                        }
                        public float getMinimumSpan(int axis) {
                            return 0;
                        }
                        public float getMaximumSpan(int axis) {
                            return 0;
                        }
                        protected void loadChildren(ViewFactory f) {
                        }
                        public Shape modelToView(int pos, Shape a,
                               Position.Bias b) throws BadLocationException {
                            return a;
                        }
                        public int getNextVisualPositionFrom(int pos,
                                     Position.Bias b, Shape a,
                                     int direction, Position.Bias[] biasRet) {
                            return getElement().getEndOffset();
                        }
                    };
                } else if ((kind == HTML.Tag.TITLE) ||
                           (kind == HTML.Tag.META) ||
                           (kind == HTML.Tag.LINK) ||
                           (kind == HTML.Tag.STYLE) ||
                           (kind == HTML.Tag.SCRIPT) ||
                           (kind == HTML.Tag.AREA) ||
                           (kind == HTML.Tag.MAP) ||
                           (kind == HTML.Tag.PARAM) ||
                           (kind == HTML.Tag.APPLET)) {
                    return new HiddenTagView(elem);
                }
            }
            // If we get here, it's either an element we don't know about
            // or something from StyledDocument that doesn't have a mapping to HTML.
            String nm = (elementName != null) ? (String)elementName :
                                                elem.getName();
            if (nm != null) {
                if (nm.equals(AbstractDocument.ContentElementName)) {
                    return new LabelView(elem);
                } else if (nm.equals(AbstractDocument.ParagraphElementName)) {
                    return new ParagraphView(elem);
                } else if (nm.equals(AbstractDocument.SectionElementName)) {
                    return new BoxView(elem, View.Y_AXIS);
                } else if (nm.equals(StyleConstants.ComponentElementName)) {
                    return new ComponentView(elem);
                } else if (nm.equals(StyleConstants.IconElementName)) {
                    return new IconView(elem);
                }
            }

            // default to text display
            return new LabelView(elem);
        }

        static class BodyBlockView extends BlockView implements ComponentListener {
            public BodyBlockView(Element elem) {
                super(elem,View.Y_AXIS);
            }
            // reimplement major axis requirements to indicate that the
            // block is flexible for the body element... so that it can
            // be stretched to fill the background properly.
            protected SizeRequirements calculateMajorAxisRequirements(int axis, SizeRequirements r) {
                r = super.calculateMajorAxisRequirements(axis, r);
                r.maximum = Integer.MAX_VALUE;
                return r;
            }

            protected void layoutMinorAxis(int targetSpan, int axis, int[] offsets, int[] spans) {
                Container container = getContainer();
                Container parentContainer;
                if (container != null
                    && (container instanceof javax.swing.JEditorPane)
                    && (parentContainer = container.getParent()) != null
                    && (parentContainer instanceof javax.swing.JViewport)) {
                    JViewport viewPort = (JViewport)parentContainer;
                    if (cachedViewPort != null) {
                        JViewport cachedObject = cachedViewPort.get();
                        if (cachedObject != null) {
                            if (cachedObject != viewPort) {
                                cachedObject.removeComponentListener(this);
                            }
                        } else {
                            cachedViewPort = null;
                        }
                    }
                    if (cachedViewPort == null) {
                        viewPort.addComponentListener(this);
                        cachedViewPort = new WeakReference<JViewport>(viewPort);
                    }

                    componentVisibleWidth = viewPort.getExtentSize().width;
                    if (componentVisibleWidth > 0) {
                    Insets insets = container.getInsets();
                    viewVisibleWidth = componentVisibleWidth - insets.left - getLeftInset();
                    //try to use viewVisibleWidth if it is smaller than targetSpan
                    targetSpan = Math.min(targetSpan, viewVisibleWidth);
                    }
                } else {
                    if (cachedViewPort != null) {
                        JViewport cachedObject = cachedViewPort.get();
                        if (cachedObject != null) {
                            cachedObject.removeComponentListener(this);
                        }
                        cachedViewPort = null;
                    }
                }
                super.layoutMinorAxis(targetSpan, axis, offsets, spans);
            }

            public void setParent(View parent) {
                //if parent == null unregister component listener
                if (parent == null) {
                    if (cachedViewPort != null) {
                        Object cachedObject;
                        if ((cachedObject = cachedViewPort.get()) != null) {
                            ((JComponent)cachedObject).removeComponentListener(this);
                        }
                        cachedViewPort = null;
                    }
                }
                super.setParent(parent);
            }

            public void componentResized(ComponentEvent e) {
                if ( !(e.getSource() instanceof JViewport) ) {
                    return;
                }
                JViewport viewPort = (JViewport)e.getSource();
                if (componentVisibleWidth != viewPort.getExtentSize().width) {
                    Document doc = getDocument();
                    if (doc instanceof AbstractDocument) {
                        AbstractDocument document = (AbstractDocument)getDocument();
                        document.readLock();
                        try {
                            layoutChanged(X_AXIS);
                            preferenceChanged(null, true, true);
                        } finally {
                            document.readUnlock();
                        }

                    }
                }
            }
            public void componentHidden(ComponentEvent e) {
            }
            public void componentMoved(ComponentEvent e) {
            }
            public void componentShown(ComponentEvent e) {
            }
            /*
             * we keep weak reference to viewPort if and only if BodyBoxView is listening for ComponentEvents
             * only in that case cachedViewPort is not equal to null.
             * we need to keep this reference in order to remove BodyBoxView from viewPort listeners.
             *
             * <p>
             *  我们保持弱引用viewPort当且仅当BodyBoxView正在侦听ComponentEvents只有在那种情况下cachedViewPort不等于null我们需要保持这个引用为了从viewPort侦
             * 听器中删除BodyBoxView。
             * 
             */
            private Reference<JViewport> cachedViewPort = null;
            private boolean isListening = false;
            private int viewVisibleWidth = Integer.MAX_VALUE;
            private int componentVisibleWidth = Integer.MAX_VALUE;
        }

    }

    // --- Action implementations ------------------------------

/** The bold action identifier
/* <p>
*/
    public static final String  BOLD_ACTION = "html-bold-action";
/** The italic action identifier
/* <p>
*/
    public static final String  ITALIC_ACTION = "html-italic-action";
/** The paragraph left indent action identifier
/* <p>
*/
    public static final String  PARA_INDENT_LEFT = "html-para-indent-left";
/** The paragraph right indent action identifier
/* <p>
*/
    public static final String  PARA_INDENT_RIGHT = "html-para-indent-right";
/** The  font size increase to next value action identifier
/* <p>
*/
    public static final String  FONT_CHANGE_BIGGER = "html-font-bigger";
/** The font size decrease to next value action identifier
/* <p>
*/
    public static final String  FONT_CHANGE_SMALLER = "html-font-smaller";
/** The Color choice action identifier
     The color is passed as an argument
/* <p>
/*  颜色作为参数传递
/* 
*/
    public static final String  COLOR_ACTION = "html-color-action";
/** The logical style choice action identifier
     The logical style is passed in as an argument
/* <p>
/*  逻辑样式作为参数传入
/* 
*/
    public static final String  LOGICAL_STYLE_ACTION = "html-logical-style-action";
    /**
     * Align images at the top.
     * <p>
     *  在顶部对齐图像
     * 
     */
    public static final String  IMG_ALIGN_TOP = "html-image-align-top";

    /**
     * Align images in the middle.
     * <p>
     *  在中间对齐图像
     * 
     */
    public static final String  IMG_ALIGN_MIDDLE = "html-image-align-middle";

    /**
     * Align images at the bottom.
     * <p>
     *  对齐底部的图像
     * 
     */
    public static final String  IMG_ALIGN_BOTTOM = "html-image-align-bottom";

    /**
     * Align images at the border.
     * <p>
     *  在边框处对齐图像
     * 
     */
    public static final String  IMG_BORDER = "html-image-border";


    /** HTML used when inserting tables. */
    private static final String INSERT_TABLE_HTML = "<table border=1><tr><td></td></tr></table>";

    /** HTML used when inserting unordered lists. */
    private static final String INSERT_UL_HTML = "<ul><li></li></ul>";

    /** HTML used when inserting ordered lists. */
    private static final String INSERT_OL_HTML = "<ol><li></li></ol>";

    /** HTML used when inserting hr. */
    private static final String INSERT_HR_HTML = "<hr>";

    /** HTML used when inserting pre. */
    private static final String INSERT_PRE_HTML = "<pre></pre>";

    private static final NavigateLinkAction nextLinkAction =
        new NavigateLinkAction("next-link-action");

    private static final NavigateLinkAction previousLinkAction =
        new NavigateLinkAction("previous-link-action");

    private static final ActivateLinkAction activateLinkAction =
        new ActivateLinkAction("activate-link-action");

    private static final Action[] defaultActions = {
        new InsertHTMLTextAction("InsertTable", INSERT_TABLE_HTML,
                                 HTML.Tag.BODY, HTML.Tag.TABLE),
        new InsertHTMLTextAction("InsertTableRow", INSERT_TABLE_HTML,
                                 HTML.Tag.TABLE, HTML.Tag.TR,
                                 HTML.Tag.BODY, HTML.Tag.TABLE),
        new InsertHTMLTextAction("InsertTableDataCell", INSERT_TABLE_HTML,
                                 HTML.Tag.TR, HTML.Tag.TD,
                                 HTML.Tag.BODY, HTML.Tag.TABLE),
        new InsertHTMLTextAction("InsertUnorderedList", INSERT_UL_HTML,
                                 HTML.Tag.BODY, HTML.Tag.UL),
        new InsertHTMLTextAction("InsertUnorderedListItem", INSERT_UL_HTML,
                                 HTML.Tag.UL, HTML.Tag.LI,
                                 HTML.Tag.BODY, HTML.Tag.UL),
        new InsertHTMLTextAction("InsertOrderedList", INSERT_OL_HTML,
                                 HTML.Tag.BODY, HTML.Tag.OL),
        new InsertHTMLTextAction("InsertOrderedListItem", INSERT_OL_HTML,
                                 HTML.Tag.OL, HTML.Tag.LI,
                                 HTML.Tag.BODY, HTML.Tag.OL),
        new InsertHRAction(),
        new InsertHTMLTextAction("InsertPre", INSERT_PRE_HTML,
                                 HTML.Tag.BODY, HTML.Tag.PRE),
        nextLinkAction, previousLinkAction, activateLinkAction,

        new BeginAction(beginAction, false),
        new BeginAction(selectionBeginAction, true)
    };

    // link navigation support
    private boolean foundLink = false;
    private int prevHypertextOffset = -1;
    private Object linkNavigationTag;


    /**
     * An abstract Action providing some convenience methods that may
     * be useful in inserting HTML into an existing document.
     * <p>NOTE: None of the convenience methods obtain a lock on the
     * document. If you have another thread modifying the text these
     * methods may have inconsistent behavior, or return the wrong thing.
     * <p>
     *  一个抽象动作提供一些方便的方法,可能有助于将HTML插入到现有文档中<p>注意：没有方便的方法获得对文档的锁如果你有另一个线程修改文本,这些方法可能有不一致的行为,返回错误的东西
     * 
     */
    public static abstract class HTMLTextAction extends StyledTextAction {
        public HTMLTextAction(String name) {
            super(name);
        }

        /**
        /* <p>
        /* 
         * @return HTMLDocument of <code>e</code>.
         */
        protected HTMLDocument getHTMLDocument(JEditorPane e) {
            Document d = e.getDocument();
            if (d instanceof HTMLDocument) {
                return (HTMLDocument) d;
            }
            throw new IllegalArgumentException("document must be HTMLDocument");
        }

        /**
        /* <p>
        /* 
         * @return HTMLEditorKit for <code>e</code>.
         */
        protected HTMLEditorKit getHTMLEditorKit(JEditorPane e) {
            EditorKit k = e.getEditorKit();
            if (k instanceof HTMLEditorKit) {
                return (HTMLEditorKit) k;
            }
            throw new IllegalArgumentException("EditorKit must be HTMLEditorKit");
        }

        /**
         * Returns an array of the Elements that contain <code>offset</code>.
         * The first elements corresponds to the root.
         * <p>
         * 返回包含<code> offset </code>的Elements数组。第一个元素对应于根
         * 
         */
        protected Element[] getElementsAt(HTMLDocument doc, int offset) {
            return getElementsAt(doc.getDefaultRootElement(), offset, 0);
        }

        /**
         * Recursive method used by getElementsAt.
         * <p>
         *  getElementsAt使用递归方法
         * 
         */
        private Element[] getElementsAt(Element parent, int offset,
                                        int depth) {
            if (parent.isLeaf()) {
                Element[] retValue = new Element[depth + 1];
                retValue[depth] = parent;
                return retValue;
            }
            Element[] retValue = getElementsAt(parent.getElement
                          (parent.getElementIndex(offset)), offset, depth + 1);
            retValue[depth] = parent;
            return retValue;
        }

        /**
         * Returns number of elements, starting at the deepest leaf, needed
         * to get to an element representing <code>tag</code>. This will
         * return -1 if no elements is found representing <code>tag</code>,
         * or 0 if the parent of the leaf at <code>offset</code> represents
         * <code>tag</code>.
         * <p>
         *  返回从最深的叶开始,到达代表<code>标签的元素所需的元素数量</code>如果没有找到代表<code> tag </code>的元素,则返回-1;如果在<code> offset </code>处
         * 的叶的父代表<code>标签</code>。
         * 
         */
        protected int elementCountToTag(HTMLDocument doc, int offset,
                                        HTML.Tag tag) {
            int depth = -1;
            Element e = doc.getCharacterElement(offset);
            while (e != null && e.getAttributes().getAttribute
                   (StyleConstants.NameAttribute) != tag) {
                e = e.getParentElement();
                depth++;
            }
            if (e == null) {
                return -1;
            }
            return depth;
        }

        /**
         * Returns the deepest element at <code>offset</code> matching
         * <code>tag</code>.
         * <p>
         *  返回与<code> offset </code>匹配的<code>标签</code>中最深的元素
         * 
         */
        protected Element findElementMatchingTag(HTMLDocument doc, int offset,
                                                 HTML.Tag tag) {
            Element e = doc.getDefaultRootElement();
            Element lastMatch = null;
            while (e != null) {
                if (e.getAttributes().getAttribute
                   (StyleConstants.NameAttribute) == tag) {
                    lastMatch = e;
                }
                e = e.getElement(e.getElementIndex(offset));
            }
            return lastMatch;
        }
    }


    /**
     * InsertHTMLTextAction can be used to insert an arbitrary string of HTML
     * into an existing HTML document. At least two HTML.Tags need to be
     * supplied. The first Tag, parentTag, identifies the parent in
     * the document to add the elements to. The second tag, addTag,
     * identifies the first tag that should be added to the document as
     * seen in the HTML string. One important thing to remember, is that
     * the parser is going to generate all the appropriate tags, even if
     * they aren't in the HTML string passed in.<p>
     * For example, lets say you wanted to create an action to insert
     * a table into the body. The parentTag would be HTML.Tag.BODY,
     * addTag would be HTML.Tag.TABLE, and the string could be something
     * like &lt;table&gt;&lt;tr&gt;&lt;td&gt;&lt;/td&gt;&lt;/tr&gt;&lt;/table&gt;.
     * <p>There is also an option to supply an alternate parentTag and
     * addTag. These will be checked for if there is no parentTag at
     * offset.
     * <p>
     * InsertHTMLTextAction可用于将HTML的任意字符串插入现有HTML文档至少需要提供两个HTMLTag第一个标签parentTag标识文档中的父元素以添加元素第二个标签addTag标识第
     * 一个应该添加到文档中的HTML标记中的一个重要的事情是,解析器将生成所有适当的标记,即使它们不在<p>中传递的HTML字符串中。
     * 例如,让我们说,你想创建一个操作来将表插入到body中parentTag将是HTMLTagBODY,addTag将是HTMLTagTABLE,并且该字符串可以是类似&lt; table&gt;&lt; 
     * tr&gt;&lt; td&gt;&lt; / td&gt ;&lt; / tr&gt;&lt; / table&gt;<p>还有一个选项提供一个备用的parentTag和addTag这些将被检查是否没
     * 有parentTag在偏移。
     * 
     */
    public static class InsertHTMLTextAction extends HTMLTextAction {
        public InsertHTMLTextAction(String name, String html,
                                    HTML.Tag parentTag, HTML.Tag addTag) {
            this(name, html, parentTag, addTag, null, null);
        }

        public InsertHTMLTextAction(String name, String html,
                                    HTML.Tag parentTag,
                                    HTML.Tag addTag,
                                    HTML.Tag alternateParentTag,
                                    HTML.Tag alternateAddTag) {
            this(name, html, parentTag, addTag, alternateParentTag,
                 alternateAddTag, true);
        }

        /* public */
        InsertHTMLTextAction(String name, String html,
                                    HTML.Tag parentTag,
                                    HTML.Tag addTag,
                                    HTML.Tag alternateParentTag,
                                    HTML.Tag alternateAddTag,
                                    boolean adjustSelection) {
            super(name);
            this.html = html;
            this.parentTag = parentTag;
            this.addTag = addTag;
            this.alternateParentTag = alternateParentTag;
            this.alternateAddTag = alternateAddTag;
            this.adjustSelection = adjustSelection;
        }

        /**
         * A cover for HTMLEditorKit.insertHTML. If an exception it
         * thrown it is wrapped in a RuntimeException and thrown.
         * <p>
         * HTMLEditorKitinsertHTML的封面如果它抛出的异常被包装在一个RuntimeException并抛出
         * 
         */
        protected void insertHTML(JEditorPane editor, HTMLDocument doc,
                                  int offset, String html, int popDepth,
                                  int pushDepth, HTML.Tag addTag) {
            try {
                getHTMLEditorKit(editor).insertHTML(doc, offset, html,
                                                    popDepth, pushDepth,
                                                    addTag);
            } catch (IOException ioe) {
                throw new RuntimeException("Unable to insert: " + ioe);
            } catch (BadLocationException ble) {
                throw new RuntimeException("Unable to insert: " + ble);
            }
        }

        /**
         * This is invoked when inserting at a boundary. It determines
         * the number of pops, and then the number of pushes that need
         * to be performed, and then invokes insertHTML.
         * <p>
         *  当在边界处插入时调用它确定pops的数量,然后确定需要执行的推进的数量,然后调用insertHTML
         * 
         * 
         * @since 1.3
         */
        protected void insertAtBoundary(JEditorPane editor, HTMLDocument doc,
                                        int offset, Element insertElement,
                                        String html, HTML.Tag parentTag,
                                        HTML.Tag addTag) {
            insertAtBoundry(editor, doc, offset, insertElement, html,
                            parentTag, addTag);
        }

        /**
         * This is invoked when inserting at a boundary. It determines
         * the number of pops, and then the number of pushes that need
         * to be performed, and then invokes insertHTML.
         * <p>
         *  当在边界处插入时调用它确定pops的数量,然后确定需要执行的推进的数量,然后调用insertHTML
         * 
         * 
         * @deprecated As of Java 2 platform v1.3, use insertAtBoundary
         */
        @Deprecated
        protected void insertAtBoundry(JEditorPane editor, HTMLDocument doc,
                                       int offset, Element insertElement,
                                       String html, HTML.Tag parentTag,
                                       HTML.Tag addTag) {
            // Find the common parent.
            Element e;
            Element commonParent;
            boolean isFirst = (offset == 0);

            if (offset > 0 || insertElement == null) {
                e = doc.getDefaultRootElement();
                while (e != null && e.getStartOffset() != offset &&
                       !e.isLeaf()) {
                    e = e.getElement(e.getElementIndex(offset));
                }
                commonParent = (e != null) ? e.getParentElement() : null;
            }
            else {
                // If inserting at the origin, the common parent is the
                // insertElement.
                commonParent = insertElement;
            }
            if (commonParent != null) {
                // Determine how many pops to do.
                int pops = 0;
                int pushes = 0;
                if (isFirst && insertElement != null) {
                    e = commonParent;
                    while (e != null && !e.isLeaf()) {
                        e = e.getElement(e.getElementIndex(offset));
                        pops++;
                    }
                }
                else {
                    e = commonParent;
                    offset--;
                    while (e != null && !e.isLeaf()) {
                        e = e.getElement(e.getElementIndex(offset));
                        pops++;
                    }

                    // And how many pushes
                    e = commonParent;
                    offset++;
                    while (e != null && e != insertElement) {
                        e = e.getElement(e.getElementIndex(offset));
                        pushes++;
                    }
                }
                pops = Math.max(0, pops - 1);

                // And insert!
                insertHTML(editor, doc, offset, html, pops, pushes, addTag);
            }
        }

        /**
         * If there is an Element with name <code>tag</code> at
         * <code>offset</code>, this will invoke either insertAtBoundary
         * or <code>insertHTML</code>. This returns true if there is
         * a match, and one of the inserts is invoked.
         * <p>
         *  如果在<code> offset </code>处有一个名为<code>标签</code>的元素,则将调用insertAtBoundary或<code> insertHTML </code>如果匹配,
         * 则返回true,的插入。
         * 
         */
        /*protected*/
        boolean insertIntoTag(JEditorPane editor, HTMLDocument doc,
                              int offset, HTML.Tag tag, HTML.Tag addTag) {
            Element e = findElementMatchingTag(doc, offset, tag);
            if (e != null && e.getStartOffset() == offset) {
                insertAtBoundary(editor, doc, offset, e, html,
                                 tag, addTag);
                return true;
            }
            else if (offset > 0) {
                int depth = elementCountToTag(doc, offset - 1, tag);
                if (depth != -1) {
                    insertHTML(editor, doc, offset, html, depth, 0, addTag);
                    return true;
                }
            }
            return false;
        }

        /**
         * Called after an insertion to adjust the selection.
         * <p>
         *  插入后调用以调整选择
         * 
         */
        /* protected */
        void adjustSelection(JEditorPane pane, HTMLDocument doc,
                             int startOffset, int oldLength) {
            int newLength = doc.getLength();
            if (newLength != oldLength && startOffset < newLength) {
                if (startOffset > 0) {
                    String text;
                    try {
                        text = doc.getText(startOffset - 1, 1);
                    } catch (BadLocationException ble) {
                        text = null;
                    }
                    if (text != null && text.length() > 0 &&
                        text.charAt(0) == '\n') {
                        pane.select(startOffset, startOffset);
                    }
                    else {
                        pane.select(startOffset + 1, startOffset + 1);
                    }
                }
                else {
                    pane.select(1, 1);
                }
            }
        }

        /**
         * Inserts the HTML into the document.
         *
         * <p>
         *  将HTML插入文档
         * 
         * 
         * @param ae the event
         */
        public void actionPerformed(ActionEvent ae) {
            JEditorPane editor = getEditor(ae);
            if (editor != null) {
                HTMLDocument doc = getHTMLDocument(editor);
                int offset = editor.getSelectionStart();
                int length = doc.getLength();
                boolean inserted;
                // Try first choice
                if (!insertIntoTag(editor, doc, offset, parentTag, addTag) &&
                    alternateParentTag != null) {
                    // Then alternate.
                    inserted = insertIntoTag(editor, doc, offset,
                                             alternateParentTag,
                                             alternateAddTag);
                }
                else {
                    inserted = true;
                }
                if (adjustSelection && inserted) {
                    adjustSelection(editor, doc, offset, length);
                }
            }
        }

        /** HTML to insert. */
        protected String html;
        /** Tag to check for in the document. */
        protected HTML.Tag parentTag;
        /** Tag in HTML to start adding tags from. */
        protected HTML.Tag addTag;
        /** Alternate Tag to check for in the document if parentTag is
        /* <p>
        /* 
         * not found. */
        protected HTML.Tag alternateParentTag;
        /** Alternate tag in HTML to start adding tags from if parentTag
        /* <p>
        /* 
         * is not found and alternateParentTag is found. */
        protected HTML.Tag alternateAddTag;
        /** True indicates the selection should be adjusted after an insert. */
        boolean adjustSelection;
    }


    /**
     * InsertHRAction is special, at actionPerformed time it will determine
     * the parent HTML.Tag based on the paragraph element at the selection
     * start.
     * <p>
     * InsertHRAction是特殊的,在actionPerformed时,它将基于选择开始处的段落元素确定父HTMLTag
     * 
     */
    static class InsertHRAction extends InsertHTMLTextAction {
        InsertHRAction() {
            super("InsertHR", "<hr>", null, HTML.Tag.IMPLIED, null, null,
                  false);
        }

        /**
         * Inserts the HTML into the document.
         *
         * <p>
         *  将HTML插入文档
         * 
         * 
         * @param ae the event
         */
        public void actionPerformed(ActionEvent ae) {
            JEditorPane editor = getEditor(ae);
            if (editor != null) {
                HTMLDocument doc = getHTMLDocument(editor);
                int offset = editor.getSelectionStart();
                Element paragraph = doc.getParagraphElement(offset);
                if (paragraph.getParentElement() != null) {
                    parentTag = (HTML.Tag)paragraph.getParentElement().
                                  getAttributes().getAttribute
                                  (StyleConstants.NameAttribute);
                    super.actionPerformed(ae);
                }
            }
        }

    }

    /*
     * Returns the object in an AttributeSet matching a key
     * <p>
     *  返回与键匹配的AttributeSet中的对象
     * 
     */
    static private Object getAttrValue(AttributeSet attr, HTML.Attribute key) {
        Enumeration names = attr.getAttributeNames();
        while (names.hasMoreElements()) {
            Object nextKey = names.nextElement();
            Object nextVal = attr.getAttribute(nextKey);
            if (nextVal instanceof AttributeSet) {
                Object value = getAttrValue((AttributeSet)nextVal, key);
                if (value != null) {
                    return value;
                }
            } else if (nextKey == key) {
                return nextVal;
            }
        }
        return null;
    }

    /*
     * Action to move the focus on the next or previous hypertext link
     * or object. TODO: This method relies on support from the
     * javax.accessibility package.  The text package should support
     * keyboard navigation of text elements directly.
     * <p>
     *  将焦点移动到下一个或上一个超文本链接或对象的动作TODO：此方法依赖于javaxaccessibility包的支持文本包应支持直接的文本元素的键盘导航
     * 
     */
    static class NavigateLinkAction extends TextAction implements CaretListener {

        private static final FocusHighlightPainter focusPainter =
            new FocusHighlightPainter(null);
        private final boolean focusBack;

        /*
         * Create this action with the appropriate identifier.
         * <p>
         *  使用适当的标识符创建此操作
         * 
         */
        public NavigateLinkAction(String actionName) {
            super(actionName);
            focusBack = "previous-link-action".equals(actionName);
        }

        /**
         * Called when the caret position is updated.
         *
         * <p>
         *  更新插入符位置时调用
         * 
         * 
         * @param e the caret event
         */
        public void caretUpdate(CaretEvent e) {
            Object src = e.getSource();
            if (src instanceof JTextComponent) {
                JTextComponent comp = (JTextComponent) src;
                HTMLEditorKit kit = getHTMLEditorKit(comp);
                if (kit != null && kit.foundLink) {
                    kit.foundLink = false;
                    // TODO: The AccessibleContext for the editor should register
                    // as a listener for CaretEvents and forward the events to
                    // assistive technologies listening for such events.
                    comp.getAccessibleContext().firePropertyChange(
                        AccessibleContext.ACCESSIBLE_HYPERTEXT_OFFSET,
                        Integer.valueOf(kit.prevHypertextOffset),
                        Integer.valueOf(e.getDot()));
                }
            }
        }

        /*
         * The operation to perform when this action is triggered.
         * <p>
         *  触发此操作时执行的操作
         * 
         */
        public void actionPerformed(ActionEvent e) {
            JTextComponent comp = getTextComponent(e);
            if (comp == null || comp.isEditable()) {
                return;
            }

            Document doc = comp.getDocument();
            HTMLEditorKit kit = getHTMLEditorKit(comp);
            if (doc == null || kit == null) {
                return;
            }

            // TODO: Should start successive iterations from the
            // current caret position.
            ElementIterator ei = new ElementIterator(doc);
            int currentOffset = comp.getCaretPosition();
            int prevStartOffset = -1;
            int prevEndOffset = -1;

            // highlight the next link or object after the current caret position
            Element nextElement;
            while ((nextElement = ei.next()) != null) {
                String name = nextElement.getName();
                AttributeSet attr = nextElement.getAttributes();

                Object href = getAttrValue(attr, HTML.Attribute.HREF);
                if (!(name.equals(HTML.Tag.OBJECT.toString())) && href == null) {
                    continue;
                }

                int elementOffset = nextElement.getStartOffset();
                if (focusBack) {
                    if (elementOffset >= currentOffset &&
                        prevStartOffset >= 0) {

                        kit.foundLink = true;
                        comp.setCaretPosition(prevStartOffset);
                        moveCaretPosition(comp, kit, prevStartOffset,
                                          prevEndOffset);
                        kit.prevHypertextOffset = prevStartOffset;
                        return;
                    }
                } else { // focus forward
                    if (elementOffset > currentOffset) {

                        kit.foundLink = true;
                        comp.setCaretPosition(elementOffset);
                        moveCaretPosition(comp, kit, elementOffset,
                                          nextElement.getEndOffset());
                        kit.prevHypertextOffset = elementOffset;
                        return;
                    }
                }
                prevStartOffset = nextElement.getStartOffset();
                prevEndOffset = nextElement.getEndOffset();
            }
            if (focusBack && prevStartOffset >= 0) {
                kit.foundLink = true;
                comp.setCaretPosition(prevStartOffset);
                moveCaretPosition(comp, kit, prevStartOffset, prevEndOffset);
                kit.prevHypertextOffset = prevStartOffset;
            }
        }

        /*
         * Moves the caret from mark to dot
         * <p>
         *  将插入符号从标记移动到圆点
         * 
         */
        private void moveCaretPosition(JTextComponent comp, HTMLEditorKit kit,
                                       int mark, int dot) {
            Highlighter h = comp.getHighlighter();
            if (h != null) {
                int p0 = Math.min(dot, mark);
                int p1 = Math.max(dot, mark);
                try {
                    if (kit.linkNavigationTag != null) {
                        h.changeHighlight(kit.linkNavigationTag, p0, p1);
                    } else {
                        kit.linkNavigationTag =
                                h.addHighlight(p0, p1, focusPainter);
                    }
                } catch (BadLocationException e) {
                }
            }
        }

        private HTMLEditorKit getHTMLEditorKit(JTextComponent comp) {
            if (comp instanceof JEditorPane) {
                EditorKit kit = ((JEditorPane) comp).getEditorKit();
                if (kit instanceof HTMLEditorKit) {
                    return (HTMLEditorKit) kit;
                }
            }
            return null;
        }

        /**
         * A highlight painter that draws a one-pixel border around
         * the highlighted area.
         * <p>
         *  突出显示的绘图工具,在突出显示的区域周围绘制一个像素的边框
         * 
         */
        static class FocusHighlightPainter extends
            DefaultHighlighter.DefaultHighlightPainter {

            FocusHighlightPainter(Color color) {
                super(color);
            }

            /**
             * Paints a portion of a highlight.
             *
             * <p>
             *  描绘高亮的一部分
             * 
             * 
             * @param g the graphics context
             * @param offs0 the starting model offset &ge; 0
             * @param offs1 the ending model offset &ge; offs1
             * @param bounds the bounding box of the view, which is not
             *        necessarily the region to paint.
             * @param c the editor
             * @param view View painting for
             * @return region in which drawing occurred
             */
            public Shape paintLayer(Graphics g, int offs0, int offs1,
                                    Shape bounds, JTextComponent c, View view) {

                Color color = getColor();

                if (color == null) {
                    g.setColor(c.getSelectionColor());
                }
                else {
                    g.setColor(color);
                }
                if (offs0 == view.getStartOffset() &&
                    offs1 == view.getEndOffset()) {
                    // Contained in view, can just use bounds.
                    Rectangle alloc;
                    if (bounds instanceof Rectangle) {
                        alloc = (Rectangle)bounds;
                    }
                    else {
                        alloc = bounds.getBounds();
                    }
                    g.drawRect(alloc.x, alloc.y, alloc.width - 1, alloc.height);
                    return alloc;
                }
                else {
                    // Should only render part of View.
                    try {
                        // --- determine locations ---
                        Shape shape = view.modelToView(offs0, Position.Bias.Forward,
                                                       offs1,Position.Bias.Backward,
                                                       bounds);
                        Rectangle r = (shape instanceof Rectangle) ?
                            (Rectangle)shape : shape.getBounds();
                        g.drawRect(r.x, r.y, r.width - 1, r.height);
                        return r;
                    } catch (BadLocationException e) {
                        // can't render
                    }
                }
                // Only if exception
                return null;
            }
        }
    }

    /*
     * Action to activate the hypertext link that has focus.
     * TODO: This method relies on support from the
     * javax.accessibility package.  The text package should support
     * keyboard navigation of text elements directly.
     * <p>
     * 激活具有焦点的超文本链接的操作TODO：此方法依赖于javaxaccessibility包的支持文本包应支持直接的文本元素的键盘导航
     * 
     */
    static class ActivateLinkAction extends TextAction {

        /**
         * Create this action with the appropriate identifier.
         * <p>
         *  使用适当的标识符创建此操作
         * 
         */
        public ActivateLinkAction(String actionName) {
            super(actionName);
        }

        /*
         * activates the hyperlink at offset
         * <p>
         *  激活偏移处的超链接
         * 
         */
        private void activateLink(String href, HTMLDocument doc,
                                  JEditorPane editor, int offset) {
            try {
                URL page =
                    (URL)doc.getProperty(Document.StreamDescriptionProperty);
                URL url = new URL(page, href);
                HyperlinkEvent linkEvent = new HyperlinkEvent
                    (editor, HyperlinkEvent.EventType.
                     ACTIVATED, url, url.toExternalForm(),
                     doc.getCharacterElement(offset));
                editor.fireHyperlinkUpdate(linkEvent);
            } catch (MalformedURLException m) {
            }
        }

        /*
         * Invokes default action on the object in an element
         * <p>
         *  在元素中的对象上调用默认操作
         * 
         */
        private void doObjectAction(JEditorPane editor, Element elem) {
            View view = getView(editor, elem);
            if (view != null && view instanceof ObjectView) {
                Component comp = ((ObjectView)view).getComponent();
                if (comp != null && comp instanceof Accessible) {
                    AccessibleContext ac = comp.getAccessibleContext();
                    if (ac != null) {
                        AccessibleAction aa = ac.getAccessibleAction();
                        if (aa != null) {
                            aa.doAccessibleAction(0);
                        }
                    }
                }
            }
        }

        /*
         * Returns the root view for a document
         * <p>
         *  返回文档的根视图
         * 
         */
        private View getRootView(JEditorPane editor) {
            return editor.getUI().getRootView(editor);
        }

        /*
         * Returns a view associated with an element
         * <p>
         *  返回与元素关联的视图
         * 
         */
        private View getView(JEditorPane editor, Element elem) {
            Object lock = lock(editor);
            try {
                View rootView = getRootView(editor);
                int start = elem.getStartOffset();
                if (rootView != null) {
                    return getView(rootView, elem, start);
                }
                return null;
            } finally {
                unlock(lock);
            }
        }

        private View getView(View parent, Element elem, int start) {
            if (parent.getElement() == elem) {
                return parent;
            }
            int index = parent.getViewIndex(start, Position.Bias.Forward);

            if (index != -1 && index < parent.getViewCount()) {
                return getView(parent.getView(index), elem, start);
            }
            return null;
        }

        /*
         * If possible acquires a lock on the Document.  If a lock has been
         * obtained a key will be retured that should be passed to
         * <code>unlock</code>.
         * <p>
         *  如果可能,获取文档上的锁如果已获得锁,则将重新生成一个键,该键应传递到<code> unlock </code>
         * 
         */
        private Object lock(JEditorPane editor) {
            Document document = editor.getDocument();

            if (document instanceof AbstractDocument) {
                ((AbstractDocument)document).readLock();
                return document;
            }
            return null;
        }

        /*
         * Releases a lock previously obtained via <code>lock</code>.
         * <p>
         *  释放之前通过<code> lock </code>获得的锁
         * 
         */
        private void unlock(Object key) {
            if (key != null) {
                ((AbstractDocument)key).readUnlock();
            }
        }

        /*
         * The operation to perform when this action is triggered.
         * <p>
         *  触发此操作时执行的操作
         * 
         */
        public void actionPerformed(ActionEvent e) {

            JTextComponent c = getTextComponent(e);
            if (c.isEditable() || !(c instanceof JEditorPane)) {
                return;
            }
            JEditorPane editor = (JEditorPane)c;

            Document d = editor.getDocument();
            if (d == null || !(d instanceof HTMLDocument)) {
                return;
            }
            HTMLDocument doc = (HTMLDocument)d;

            ElementIterator ei = new ElementIterator(doc);
            int currentOffset = editor.getCaretPosition();

            // invoke the next link or object action
            String urlString = null;
            String objString = null;
            Element currentElement;
            while ((currentElement = ei.next()) != null) {
                String name = currentElement.getName();
                AttributeSet attr = currentElement.getAttributes();

                Object href = getAttrValue(attr, HTML.Attribute.HREF);
                if (href != null) {
                    if (currentOffset >= currentElement.getStartOffset() &&
                        currentOffset <= currentElement.getEndOffset()) {

                        activateLink((String)href, doc, editor, currentOffset);
                        return;
                    }
                } else if (name.equals(HTML.Tag.OBJECT.toString())) {
                    Object obj = getAttrValue(attr, HTML.Attribute.CLASSID);
                    if (obj != null) {
                        if (currentOffset >= currentElement.getStartOffset() &&
                            currentOffset <= currentElement.getEndOffset()) {

                            doObjectAction(editor, currentElement);
                            return;
                        }
                    }
                }
            }
        }
    }

    private static int getBodyElementStart(JTextComponent comp) {
        Element rootElement = comp.getDocument().getRootElements()[0];
        for (int i = 0; i < rootElement.getElementCount(); i++) {
            Element currElement = rootElement.getElement(i);
            if("body".equals(currElement.getName())) {
                return currElement.getStartOffset();
            }
        }
        return 0;
    }

    /*
     * Move the caret to the beginning of the document.
     * <p>
     *  将插入符号移动到文档的开头
     * 
     * @see DefaultEditorKit#beginAction
     * @see HTMLEditorKit#getActions
     */

    static class BeginAction extends TextAction {

        /* Create this object with the appropriate identifier. */
        BeginAction(String nm, boolean select) {
            super(nm);
            this.select = select;
        }

        /** The operation to perform when this action is triggered. */
        public void actionPerformed(ActionEvent e) {
            JTextComponent target = getTextComponent(e);
            int bodyStart = getBodyElementStart(target);

            if (target != null) {
                if (select) {
                    target.moveCaretPosition(bodyStart);
                } else {
                    target.setCaretPosition(bodyStart);
                }
            }
        }

        private boolean select;
    }
}
