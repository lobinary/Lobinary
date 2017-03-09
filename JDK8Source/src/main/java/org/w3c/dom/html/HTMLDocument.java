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
 * PURPOSE. See W3C License http://www.w3.org/Consortium/Legal/ for more
 * details.
 * <p>
 *  版权所有(c)2000万维网联盟,(马萨诸塞理工学院,庆应义藩大学信息自动化研究所)。版权所有。该程序根据W3C的软件知识产权许可证分发。
 * 这个程序是分发的,希望它将是有用的,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证。有关详细信息,请参阅W3C许可证http://www.w3.org/Consortium/Legal/。
 * 
 */

package org.w3c.dom.html;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 *  An <code>HTMLDocument</code> is the root of the HTML hierarchy and holds
 * the entire content. Besides providing access to the hierarchy, it also
 * provides some convenience methods for accessing certain sets of
 * information from the document.
 * <p> The following properties have been deprecated in favor of the
 * corresponding ones for the <code>BODY</code> element: alinkColor background
 *  bgColor fgColor linkColor vlinkColor In DOM Level 2, the method
 * <code>getElementById</code> is inherited from the <code>Document</code>
 * interface where it was moved.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 * <p>
 *  <code> HTMLDocument </code>是HTML层次结构的根,并保存整个内容。除了提供对层次结构的访问,它还提供了一些方便的方法来访问来自文档的某些信息集。
 *  <p>以下属性已被弃用,有利于相应的<code> BODY </code>元素：alinkColor background bgColor fgColor linkColor vlinkColor在D
 * OM级别2中,方法<code> getElementById </code>来自它被移动的<code> Document </code>接口。
 *  <code> HTMLDocument </code>是HTML层次结构的根,并保存整个内容。除了提供对层次结构的访问,它还提供了一些方便的方法来访问来自文档的某些信息集。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>文档对象模型(DOM)2级规范</a>。
 * 
 */
public interface HTMLDocument extends Document {
    /**
     *  The title of a document as specified by the <code>TITLE</code> element
     * in the head of the document.
     * <p>
     *  文档标题,由文档标题中的<code> TITLE </code>元素指定。
     * 
     */
    public String getTitle();
    public void setTitle(String title);

    /**
     *  Returns the URI  of the page that linked to this page. The value is an
     * empty string if the user navigated to the page directly (not through a
     * link, but, for example, via a bookmark).
     * <p>
     * 返回链接到此网页的网页的URI。如果用户直接导航到页面(而不是通过链接,但例如通过书签),则该值为空字符串。
     * 
     */
    public String getReferrer();

    /**
     *  The domain name of the server that served the document, or
     * <code>null</code> if the server cannot be identified by a domain name.
     * <p>
     *  服务该文档的服务器的域名,如果服务器无法通过域名识别,则为<code> null </code>。
     * 
     */
    public String getDomain();

    /**
     *  The complete URI  of the document.
     * <p>
     *  文档的完整URI。
     * 
     */
    public String getURL();

    /**
     *  The element that contains the content for the document. In documents
     * with <code>BODY</code> contents, returns the <code>BODY</code>
     * element. In frameset documents, this returns the outermost
     * <code>FRAMESET</code> element.
     * <p>
     *  包含文档内容的元素。在具有<code> BODY </code>内容的文档中,返回<code> BODY </code>元素。
     * 在框架集文档中,这返回最外面的<code> FRAMESET </code>元素。
     * 
     */
    public HTMLElement getBody();
    public void setBody(HTMLElement body);

    /**
     *  A collection of all the <code>IMG</code> elements in a document. The
     * behavior is limited to <code>IMG</code> elements for backwards
     * compatibility.
     * <p>
     *  文档中所有<code> IMG </code>元素的集合。为了向后兼容,该行为仅限于<code> IMG </code>元素。
     * 
     */
    public HTMLCollection getImages();

    /**
     *  A collection of all the <code>OBJECT</code> elements that include
     * applets and <code>APPLET</code> ( deprecated ) elements in a document.
     * <p>
     *  包含文档中applet和<code> APPLET </code>(已弃用)元素的所有<code> OBJECT </code>元素的集合。
     * 
     */
    public HTMLCollection getApplets();

    /**
     *  A collection of all <code>AREA</code> elements and anchor (
     * <code>A</code> ) elements in a document with a value for the
     * <code>href</code> attribute.
     * <p>
     *  文档中所有<code> AREA </code>元素和锚(<code> A </code>)元素的集合,其值为<code> href </code>属性。
     * 
     */
    public HTMLCollection getLinks();

    /**
     *  A collection of all the forms of a document.
     * <p>
     *  文档的所有形式的集合。
     * 
     */
    public HTMLCollection getForms();

    /**
     *  A collection of all the anchor (<code>A</code> ) elements in a document
     *  with a value for the <code>name</code> attribute. Note. For reasons
     * of backwards compatibility, the returned set of anchors only contains
     * those anchors created with the <code>name</code>  attribute, not those
     * created with the <code>id</code> attribute.
     * <p>
     *  文档中所有锚(<code> A </code>)元素的集合,其值为<code> name </code>属性。注意。
     * 出于向后兼容性的原因,返回的锚点集合仅包含使用<code> name </code>属性创建的那些锚点,而不是使用<code> id </code>属性创建的那些锚点。
     * 
     */
    public HTMLCollection getAnchors();

    /**
     *  The cookies associated with this document. If there are none, the
     * value is an empty string. Otherwise, the value is a string: a
     * semicolon-delimited list of "name, value" pairs for all the cookies
     * associated with the page. For example,
     * <code>name=value;expires=date</code> .
     * <p>
     * 与此文档相关联的Cookie。如果没有,则该值为空字符串。否则,该值为字符串：与该页面关联的所有Cookie的"名称,值"对的分号分隔列表。
     * 例如,<code> name = value; expires = date </code>。
     * 
     */
    public String getCookie();
    public void setCookie(String cookie);

    /**
     *  Note. This method and the ones following  allow a user to add to or
     * replace the structure model of a document using strings of unparsed
     * HTML. At the time of  writing alternate methods for providing similar
     * functionality for  both HTML and XML documents were being considered.
     * The following methods may be deprecated at some point in the future in
     * favor of a more general-purpose mechanism.
     * <br> Open a document stream for writing. If a document exists in the
     * target, this method clears it.
     * <p>
     *  注意。此方法和下面的方法允许用户使用未解析的HTML字符串添加或替换文档的结构模型。在编写时,正在考虑为HTML和XML文档提供类似功能的替代方法。
     * 在未来的某个时候,可能会弃用以下方法来支持更通用的机制。 <br>打开要写入的文档流。如果目标中存在文档,则此方法将清除它。
     * 
     */
    public void open();

    /**
     *  Closes a document stream opened by <code>open()</code> and forces
     * rendering.
     * <p>
     *  关闭由<code> open()</code>打开的文档流并强制呈现。
     * 
     */
    public void close();

    /**
     *  Write a string of text to a document stream opened by
     * <code>open()</code> . The text is parsed into the document's structure
     * model.
     * <p>
     *  将一个文本字符串写入由<code> open()</code>打开的文档流。文本被解析为文档的结构模型。
     * 
     * 
     * @param text  The string to be parsed into some structure in the
     *   document structure model.
     */
    public void write(String text);

    /**
     *  Write a string of text followed by a newline character to a document
     * stream opened by <code>open()</code> . The text is parsed into the
     * document's structure model.
     * <p>
     *  将一个文本字符串后跟一个换行符写入由<code> open()</code>打开的文档流。文本被解析为文档的结构模型。
     * 
     * 
     * @param text  The string to be parsed into some structure in the
     *   document structure model.
     */
    public void writeln(String text);

    /**
     *  Returns the (possibly empty) collection of elements whose
     * <code>name</code> value is given by <code>elementName</code> .
     * <p>
     *  返回<code> name </code>值由<code> elementName </code>给出的元素集合(可能为空)。
     * 
     * @param elementName  The <code>name</code> attribute value for an
     *   element.
     * @return  The matching elements.
     */
    public NodeList getElementsByName(String elementName);

}
