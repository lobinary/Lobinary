/***** Lobxxx Translate Finished ******/
/*
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

/*
 *
 *
 *
 *
 *
 * Copyright (c) 2000 World Wide Web Consortium,
 * (Massachusetts Institute of Technology, Institut National de
 * Recherche en Informatique et en Automatique, Keio University). All
 * Rights Reserved. This program is distributed under the W3C's Software
 * Intellectual Property License. This program is distributed in the
 * hope that it will be useful, but WITHOUT ANY WARRANTY; without even
 * the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR
 * PURPOSE.
 * See W3C License http://www.w3.org/Consortium/Legal/ for more details.
 * <p>
 *  版权所有(c)2000万维网联盟,(马萨诸塞理工学院,庆应义藩大学信息自动化研究所)。版权所有。该程序根据W3C的软件知识产权许可证分发。
 * 这个程序是分发的,希望它将是有用的,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证。有关详细信息,请参阅W3C许可证http://www.w3.org/Consortium/Legal/。
 * 
 */

package org.w3c.dom.stylesheets;

import org.w3c.dom.Node;

/**
 *  The <code>StyleSheet</code> interface is the abstract base interface for
 * any type of style sheet. It represents a single style sheet associated
 * with a structured document. In HTML, the StyleSheet interface represents
 * either an external style sheet, included via the HTML  LINK element, or
 * an inline  STYLE element. In XML, this interface represents an external
 * style sheet, included via a style sheet processing instruction.
 * <p>See also the <a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>Document Object Model (DOM) Level 2 Style Specification</a>.
 * <p>
 *  <code> StyleSheet </code>接口是任何类型样式表的抽象基本接口。它表示与结构化文档相关联的单个样式表。
 * 在HTML中,StyleSheet接口代表外部样式表,通过HTML LINK元素或内联STYLE元素包含。在XML中,此接口表示通过样式表处理指令包括的外部样式表。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/REC-DOM-Level-2-Style-20001113'>文档对象模型(DOM)2级样式规范< a>。
 * 
 * 
 * @since DOM Level 2
 */
public interface StyleSheet {
    /**
     *  This specifies the style sheet language for this style sheet. The
     * style sheet language is specified as a content type (e.g.
     * "text/css"). The content type is often specified in the
     * <code>ownerNode</code>. Also see the type attribute definition for
     * the <code>LINK</code> element in HTML 4.0, and the type
     * pseudo-attribute for the XML style sheet processing instruction.
     * <p>
     * 它指定此样式表的样式表语言。样式表语言被指定为内容类型(例如"text / css")。内容类型通常在<code> ownerNode </code>中指定。
     * 另请参阅HTML 4.0中的<code> LINK </code>元素的类型属性定义,以及XML样式表处理指令的类型伪属性。
     * 
     */
    public String getType();

    /**
     *  <code>false</code> if the style sheet is applied to the document.
     * <code>true</code> if it is not. Modifying this attribute may cause a
     * new resolution of style for the document. A stylesheet only applies
     * if both an appropriate medium definition is present and the disabled
     * attribute is false. So, if the media doesn't apply to the current
     * user agent, the <code>disabled</code> attribute is ignored.
     * <p>
     *  <code> false </code>如果样式表应用于文档。 <code> true </code>如果不是。修改此属性可能会导致文档的样式的新分辨率。
     * 仅当存在适当的介质定义且disabled属性为false时,样式表才适用。因此,如果媒体不适用于当前用户代理,则会忽略<code> disabled </code>属性。
     * 
     */
    public boolean getDisabled();
    /**
     *  <code>false</code> if the style sheet is applied to the document.
     * <code>true</code> if it is not. Modifying this attribute may cause a
     * new resolution of style for the document. A stylesheet only applies
     * if both an appropriate medium definition is present and the disabled
     * attribute is false. So, if the media doesn't apply to the current
     * user agent, the <code>disabled</code> attribute is ignored.
     * <p>
     *  <code> false </code>如果样式表应用于文档。 <code> true </code>如果不是。修改此属性可能会导致文档的样式的新分辨率。
     * 仅当存在适当的介质定义且disabled属性为false时,样式表才适用。因此,如果媒体不适用于当前用户代理,则会忽略<code> disabled </code>属性。
     * 
     */
    public void setDisabled(boolean disabled);

    /**
     *  The node that associates this style sheet with the document. For HTML,
     * this may be the corresponding <code>LINK</code> or <code>STYLE</code>
     * element. For XML, it may be the linking processing instruction. For
     * style sheets that are included by other style sheets, the value of
     * this attribute is <code>null</code>.
     * <p>
     *  将此样式表与文档相关联的节点。对于HTML,这可以是相应的<code> LINK </code>或<code> STYLE </code>元素。对于XML,它可以是链接处理指令。
     * 对于其他样式表包含的样式表,此属性的值为<code> null </code>。
     * 
     */
    public Node getOwnerNode();

    /**
     *  For style sheet languages that support the concept of style sheet
     * inclusion, this attribute represents the including style sheet, if
     * one exists. If the style sheet is a top-level style sheet, or the
     * style sheet language does not support inclusion, the value of this
     * attribute is <code>null</code>.
     * <p>
     * 对于支持样式表包含概念的样式表语言,此属性表示包含样式表(如果存在)。如果样式表是顶级样式表,或样式表语言不支持包含,则此属性的值为<code> null </code>。
     * 
     */
    public StyleSheet getParentStyleSheet();

    /**
     *  If the style sheet is a linked style sheet, the value of its attribute
     * is its location. For inline style sheets, the value of this attribute
     * is <code>null</code>. See the href attribute definition for the
     * <code>LINK</code> element in HTML 4.0, and the href pseudo-attribute
     * for the XML style sheet processing instruction.
     * <p>
     *  如果样式表是链接样式表,则其属性的值为其位置。对于内联样式表,此属性的值为<code> null </code>。
     * 请参阅HTML 4.0中的<code> LINK </code>元素的href属性定义,以及XML样式表处理指令的href伪属性。
     * 
     */
    public String getHref();

    /**
     *  The advisory title. The title is often specified in the
     * <code>ownerNode</code>. See the title attribute definition for the
     * <code>LINK</code> element in HTML 4.0, and the title pseudo-attribute
     * for the XML style sheet processing instruction.
     * <p>
     *  咨询标题。标题通常在<code> ownerNode </code>中指定。
     * 请参阅HTML 4.0中的<code> LINK </code>元素的标题属性定义,以及XML样式表处理指令的标题pseudo属性。
     * 
     */
    public String getTitle();

    /**
     *  The intended destination media for style information. The media is
     * often specified in the <code>ownerNode</code>. If no media has been
     * specified, the <code>MediaList</code> will be empty. See the media
     * attribute definition for the <code>LINK</code> element in HTML 4.0,
     * and the media pseudo-attribute for the XML style sheet processing
     * instruction . Modifying the media list may cause a change to the
     * attribute <code>disabled</code>.
     * <p>
     *  用于样式信息的目标媒体。媒体通常在<code> ownerNode </code>中指定。如果没有指定媒体,则<code> MediaList </code>将为空。
     * 请参阅HTML 4.0中的<code> LINK </code>元素的媒体属性定义,以及XML样式表处理指令的媒体伪属性。
     */
    public MediaList getMedia();

}
