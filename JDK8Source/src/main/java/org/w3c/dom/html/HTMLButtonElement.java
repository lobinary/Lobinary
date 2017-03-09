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

/**
 *  Push button. See the  BUTTON element definition in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 * <p>
 *  按钮。请参阅HTML 4.0中的BUTTON元素定义。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>文档对象模型(DOM)2级规范</a>。
 * 
 */
public interface HTMLButtonElement extends HTMLElement {
    /**
     *  Returns the <code>FORM</code> element containing this control. Returns
     * <code>null</code> if this control is not within the context of a form.
     * <p>
     *  返回包含此控件的<code> FORM </code>元素。如果此控件不在表单的上下文中,则返回<code> null </code>。
     * 
     */
    public HTMLFormElement getForm();

    /**
     *  A single character access key to give access to the form control. See
     * the  accesskey attribute definition in HTML 4.0.
     * <p>
     *  单个字符访问键,用于访问表单控件。请参阅HTML 4.0中的accesskey属性定义。
     * 
     */
    public String getAccessKey();
    public void setAccessKey(String accessKey);

    /**
     *  The control is unavailable in this context. See the  disabled
     * attribute definition in HTML 4.0.
     * <p>
     *  该控件在此上下文中不可用。请参阅HTML 4.0中的disabled属性定义。
     * 
     */
    public boolean getDisabled();
    public void setDisabled(boolean disabled);

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
     *  Index that represents the element's position in the tabbing order. See
     * the  tabindex attribute definition in HTML 4.0.
     * <p>
     *  表示元素在制表顺序中的位置的索引。请参阅HTML 4.0中的tabindex属性定义。
     * 
     */
    public int getTabIndex();
    public void setTabIndex(int tabIndex);

    /**
     *  The type of button. See the  type attribute definition in HTML 4.0.
     * <p>
     *  按钮的类型。请参阅HTML 4.0中的类型属性定义。
     * 
     */
    public String getType();

    /**
     *  The current form control value. See the  value attribute definition in
     * HTML 4.0.
     * <p>
     * 当前窗体控件值。请参阅HTML 4.0中的值属性定义。
     */
    public String getValue();
    public void setValue(String value);

}
