/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.transform.sax;

import javax.xml.transform.Result;

import org.xml.sax.ContentHandler;
import org.xml.sax.ext.LexicalHandler;

/**
 * <p>Acts as an holder for a transformation Result.</p>
 *
 * <p>
 *  <p>作为转换结果的持有人。</p>
 * 
 * 
 * @author <a href="Jeff.Suttor@Sun.com">Jeff Suttor</a>
 */
public class SAXResult implements Result {

    /**
     * If {@link javax.xml.transform.TransformerFactory#getFeature}
     * returns true when passed this value as an argument,
     * the Transformer supports Result output of this type.
     * <p>
     *  如果{@link javax.xml.transform.TransformerFactory#getFeature}在将此值作为参数传递时返回true,则Transformer支持此类型的Resul
     * t输出。
     * 
     */
    public static final String FEATURE =
        "http://javax.xml.transform.sax.SAXResult/feature";

    /**
     * Zero-argument default constructor.
     * <p>
     *  零参数默认构造函数。
     * 
     */
    public SAXResult() {
    }

    /**
     * Create a SAXResult that targets a SAX2 {@link org.xml.sax.ContentHandler}.
     *
     * <p>
     *  创建以SAX2 {@link org.xml.sax.ContentHandler}为目标的SAXResult。
     * 
     * 
     * @param handler Must be a non-null ContentHandler reference.
     */
    public SAXResult(ContentHandler handler) {
        setHandler(handler);
    }

    /**
     * Set the target to be a SAX2 {@link org.xml.sax.ContentHandler}.
     *
     * <p>
     *  将目标设置为SAX2 {@link org.xml.sax.ContentHandler}。
     * 
     * 
     * @param handler Must be a non-null ContentHandler reference.
     */
    public void setHandler(ContentHandler handler) {
        this.handler = handler;
    }

    /**
     * Get the {@link org.xml.sax.ContentHandler} that is the Result.
     *
     * <p>
     *  获取结果的{@link org.xml.sax.ContentHandler}。
     * 
     * 
     * @return The ContentHandler that is to be transformation output.
     */
    public ContentHandler getHandler() {
        return handler;
    }

    /**
     * Set the SAX2 {@link org.xml.sax.ext.LexicalHandler} for the output.
     *
     * <p>This is needed to handle XML comments and the like.  If the
     * lexical handler is not set, an attempt should be made by the
     * transformer to cast the {@link org.xml.sax.ContentHandler} to a
     * <code>LexicalHandler</code>.</p>
     *
     * <p>
     *  为输出设置SAX2 {@link org.xml.sax.ext.LexicalHandler}。
     * 
     *  <p>这需要处理XML注释等。
     * 如果未设置词法处理程序,则应由变换器尝试将{@link org.xml.sax.ContentHandler}转换为<code> LexicalHandler </code>。</p>。
     * 
     * 
     * @param handler A non-null <code>LexicalHandler</code> for
     * handling lexical parse events.
     */
    public void setLexicalHandler(LexicalHandler handler) {
        this.lexhandler = handler;
    }

    /**
     * Get a SAX2 {@link org.xml.sax.ext.LexicalHandler} for the output.
     *
     * <p>
     *  为输出获取SAX2 {@link org.xml.sax.ext.LexicalHandler}。
     * 
     * 
     * @return A <code>LexicalHandler</code>, or null.
     */
    public LexicalHandler getLexicalHandler() {
        return lexhandler;
    }

    /**
     * Method setSystemId Set the systemID that may be used in association
     * with the {@link org.xml.sax.ContentHandler}.
     *
     * <p>
     *  方法setSystemId设置可以与{@link org.xml.sax.ContentHandler}关联使用的systemID。
     * 
     * 
     * @param systemId The system identifier as a URI string.
     */
    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    /**
     * Get the system identifier that was set with setSystemId.
     *
     * <p>
     *  获取使用setSystemId设置的系统标识符。
     * 
     * 
     * @return The system identifier that was set with setSystemId, or null
     * if setSystemId was not called.
     */
    public String getSystemId() {
        return systemId;
    }

    //////////////////////////////////////////////////////////////////////
    // Internal state.
    //////////////////////////////////////////////////////////////////////

    /**
     * The handler for parse events.
     * <p>
     *  解析事件的处理程序。
     * 
     */
    private ContentHandler handler;

    /**
     * The handler for lexical events.
     * <p>
     *  词汇事件的处理程序。
     * 
     */
    private LexicalHandler lexhandler;

    /**
     * The systemID that may be used in association
     * with the node.
     * <p>
     *  可以与节点相关联使用的systemID。
     */
    private String systemId;
}
