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

/**
 *  Generic embedded object.  Note. In principle, all properties on the object
 * element are read-write but in some environments some properties may be
 * read-only once the underlying object is instantiated. See the  OBJECT
 * element definition in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 * <p>
 *  通用嵌入对象。注意。原则上,对象元素上的所有属性都是读写的,但在某些环境中,一旦实例化底层对象,某些属性可能是只读的。请参阅HTML 4.0中的OBJECT元素定义。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>文档对象模型(DOM)2级规范</a>。
 * 
 */
public interface HTMLObjectElement extends HTMLElement {
    /**
     *  Returns the <code>FORM</code> element containing this control. Returns
     * <code>null</code> if this control is not within the context of a form.
     * <p>
     *  返回包含此控件的<code> FORM </code>元素。如果此控件不在表单的上下文中,则返回<code> null </code>。
     * 
     */
    public HTMLFormElement getForm();

    /**
     *  Applet class file. See the <code>code</code> attribute for
     * HTMLAppletElement.
     * <p>
     *  Applet类文件。请参阅HTMLAppletElement的<code> code </code>属性。
     * 
     */
    public String getCode();
    public void setCode(String code);

    /**
     *  Aligns this object (vertically or horizontally)  with respect to its
     * surrounding text. See the  align attribute definition in HTML 4.0.
     * This attribute is deprecated in HTML 4.0.
     * <p>
     *  将此对象(垂直或水平)与其周围文本对齐。请参阅HTML 4.0中的align属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getAlign();
    public void setAlign(String align);

    /**
     *  Space-separated list of archives. See the  archive attribute definition
     *  in HTML 4.0.
     * <p>
     *  空格分隔的归档列表。请参阅HTML 4.0中的归档属性定义。
     * 
     */
    public String getArchive();
    public void setArchive(String archive);

    /**
     *  Width of border around the object. See the  border attribute definition
     *  in HTML 4.0. This attribute is deprecated in HTML 4.0.
     * <p>
     * 对象周围的边框宽度。请参阅HTML 4.0中的边框属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getBorder();
    public void setBorder(String border);

    /**
     *  Base URI for <code>classid</code> , <code>data</code> , and
     * <code>archive</code> attributes. See the  codebase attribute definition
     *  in HTML 4.0.
     * <p>
     *  <code> classid </code>,<code> data </code>和<code>归档</code>属性的基本URI。请参阅HTML 4.0中的codebase属性定义。
     * 
     */
    public String getCodeBase();
    public void setCodeBase(String codeBase);

    /**
     *  Content type for data downloaded via <code>classid</code> attribute.
     * See the  codetype attribute definition in HTML 4.0.
     * <p>
     *  通过<code> classid </code>属性下载的数据的内容类型。请参阅HTML 4.0中的codetype属性定义。
     * 
     */
    public String getCodeType();
    public void setCodeType(String codeType);

    /**
     *  A URI specifying the location of the object's data.  See the  data
     * attribute definition in HTML 4.0.
     * <p>
     *  指定对象数据位置的URI。请参阅HTML 4.0中的数据属性定义。
     * 
     */
    public String getData();
    public void setData(String data);

    /**
     *  Declare (for future reference), but do not instantiate, this object.
     * See the  declare attribute definition in HTML 4.0.
     * <p>
     *  声明(供将来参考),但不实例化,此对象。请参阅HTML 4.0中的declare属性定义。
     * 
     */
    public boolean getDeclare();
    public void setDeclare(boolean declare);

    /**
     *  Override height. See the  height attribute definition in HTML 4.0.
     * <p>
     *  覆盖高度。请参阅HTML 4.0中的height属性定义。
     * 
     */
    public String getHeight();
    public void setHeight(String height);

    /**
     *  Horizontal space to the left and right of this image, applet, or
     * object. See the  hspace attribute definition in HTML 4.0. This
     * attribute is deprecated in HTML 4.0.
     * <p>
     *  此图像,小程序或对象左右的水平空间。请参阅HTML 4.0中的hspace属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getHspace();
    public void setHspace(String hspace);

    /**
     *  Form control or object name when submitted with a form. See the  name
     * attribute definition in HTML 4.0.
     * <p>
     *  使用表单提交时的表单控件或对象名称。请参阅HTML 4.0中的名称属性定义。
     * 
     */
    public String getName();
    public void setName(String name);

    /**
     *  Message to render while loading the object. See the  standby attribute
     * definition in HTML 4.0.
     * <p>
     *  加载对象时要呈现的消息。请参阅HTML 4.0中的standby属性定义。
     * 
     */
    public String getStandby();
    public void setStandby(String standby);

    /**
     *  Index that represents the element's position in the tabbing order. See
     * the  tabindex attribute definition in HTML 4.0.
     * <p>
     *  表示元素在制表顺序中的位置的索引。请参阅HTML 4.0中的tabindex属性定义。
     * 
     */
    public int getTabIndex();
    public void setTabIndex(int tabIndex);

    /**
     *  Content type for data downloaded via <code>data</code> attribute. See
     * the  type attribute definition in HTML 4.0.
     * <p>
     *  通过<code> data </code>属性下载的数据的内容类型。请参阅HTML 4.0中的类型属性定义。
     * 
     */
    public String getType();
    public void setType(String type);

    /**
     *  Use client-side image map. See the  usemap attribute definition in
     * HTML 4.0.
     * <p>
     *  使用客户端图像映射。请参阅HTML 4.0中的usemap属性定义。
     * 
     */
    public String getUseMap();
    public void setUseMap(String useMap);

    /**
     *  Vertical space above and below this image, applet, or object. See the
     * vspace attribute definition in HTML 4.0. This attribute is deprecated
     * in HTML 4.0.
     * <p>
     * 该图像上方和下方的垂直空间,小程序或对象。请参阅HTML 4.0中的vspace属性定义。此属性在HTML 4.0中已弃用。
     * 
     */
    public String getVspace();
    public void setVspace(String vspace);

    /**
     *  Override width. See the  width attribute definition in HTML 4.0.
     * <p>
     *  覆盖宽度。请参阅HTML 4.0中的width属性定义。
     * 
     */
    public String getWidth();
    public void setWidth(String width);

    /**
     *  The document this object contains, if there is any and it is
     * available, or <code>null</code> otherwise.
     * <p>
     *  该对象包含的文档,如果有任何和它可用,或<code> null </code>。
     * 
     * @since DOM Level 2
     */
    public Document getContentDocument();

}
