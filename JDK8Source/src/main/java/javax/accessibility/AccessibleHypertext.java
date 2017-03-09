/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2000, Oracle and/or its affiliates. All rights reserved.
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

package javax.accessibility;


import java.util.*;
import java.awt.*;
import javax.swing.text.*;


/**
 * <P>The AccessibleHypertext class is the base class for all
 * classes that present hypertext information on the display.  This class
 * provides the standard mechanism for an assistive technology to access
 * that text via its content, attributes, and spatial location.
 * It also provides standard mechanisms for manipulating hyperlinks.
 * Applications can determine if an object supports the AccessibleHypertext
 * interface by first obtaining its AccessibleContext (see {@link Accessible})
 * and then calling the {@link AccessibleContext#getAccessibleText}
 * method of AccessibleContext.  If the return value is a class which extends
 * AccessibleHypertext, then that object supports AccessibleHypertext.
 *
 * <p>
 *  <P> AccessibleHypertext类是在显示器上呈现超文本信息的所有类的基类。此类为辅助技术通过其内容,属性和空间位置访问文本提供了标准机制。它还提供了操纵超链接的标准机制。
 * 应用程序可以通过首先获取其AccessibleContext(参见{@link Accessible})然后调用AccessibleContext的{@link AccessibleContext#getAccessibleText}
 * 方法来确定对象是否支持AccessibleHypertext接口。
 *  <P> AccessibleHypertext类是在显示器上呈现超文本信息的所有类的基类。此类为辅助技术通过其内容,属性和空间位置访问文本提供了标准机制。它还提供了操纵超链接的标准机制。
 * 如果返回值是一个扩展AccessibleHypertext的类,那么该对象支持AccessibleHypertext。
 * 
 * 
 * @see Accessible
 * @see Accessible#getAccessibleContext
 * @see AccessibleContext
 * @see AccessibleText
 * @see AccessibleContext#getAccessibleText
 *
 * @author      Peter Korn
 */
public interface AccessibleHypertext extends AccessibleText {

    /**
     * Returns the number of links within this hypertext document.
     *
     * <p>
     *  返回此超文本文档中的链接数。
     * 
     * 
     * @return number of links in this hypertext doc.
     */
    public abstract int getLinkCount();

    /**
     * Returns the nth Link of this Hypertext document.
     *
     * <p>
     *  返回此超文本文档的第n个链接。
     * 
     * 
     * @param linkIndex within the links of this Hypertext
     * @return Link object encapsulating the nth link(s)
     */
    public abstract AccessibleHyperlink getLink(int linkIndex);

    /**
     * Returns the index into an array of hyperlinks that
     * is associated with this character index, or -1 if there
     * is no hyperlink associated with this index.
     *
     * <p>
     *  将索引返回到与此字符索引相关联的超链接数组,如果没有与此索引相关联的超链接,则返回-1。
     * 
     * @param charIndex index within the text
     * @return index into the set of hyperlinks for this hypertext doc.
     */
    public abstract int getLinkIndex(int charIndex);
}
