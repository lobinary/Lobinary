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

import javax.xml.transform.*;

import org.xml.sax.ContentHandler;

/**
 * A SAX ContentHandler that may be used to process SAX
 * parse events (parsing transformation instructions) into a Templates object.
 *
 * <p>Note that TemplatesHandler does not need to implement LexicalHandler.</p>
 * <p>
 *  可用于将SAX解析事件(解析转换指令)转换为Templates对象的SAX ContentHandler。
 * 
 *  <p>请注意,TemplatesHandler不需要实现LexicalHandler。</p>
 * 
 */
public interface TemplatesHandler extends ContentHandler {

    /**
     * When a TemplatesHandler object is used as a ContentHandler
     * for the parsing of transformation instructions, it creates a Templates object,
     * which the caller can get once the SAX events have been completed.
     *
     * <p>
     *  当一个TemplatesHandler对象用作ContentHandler来解析转换指令时,它会创建一个Templates对象,一旦SAX事件完成,调用者就可以得到它。
     * 
     * 
     * @return The Templates object that was created during
     * the SAX event process, or null if no Templates object has
     * been created.
     *
     */
    public Templates getTemplates();

    /**
     * Set the base ID (URI or system ID) for the Templates object
     * created by this builder.  This must be set in order to
     * resolve relative URIs in the stylesheet.  This must be
     * called before the startDocument event.
     *
     * <p>
     *  设置此构建器创建的Templates对象的基本ID(URI或系统ID)。必须设置此值才能解析样式表中的相对URI。这必须在startDocument事件之前调用。
     * 
     * 
     * @param systemID Base URI for this stylesheet.
     */
    public void setSystemId(String systemID);

    /**
     * Get the base ID (URI or system ID) from where relative
     * URLs will be resolved.
     * <p>
     *  获取将从中解析相对URL的基本ID(URI或系统ID)。
     * 
     * @return The systemID that was set with {@link #setSystemId}.
     */
    public String getSystemId();
}
