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
 *  Multi-line text field. See the  TEXTAREA element definition in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 * <p>
 *  多行文本字段。请参阅HTML 4.0中的TEXTAREA元素定义。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>文档对象模型(DOM)2级规范</a>。
 * 
 */
public interface HTMLTextAreaElement extends HTMLElement {
    /**
     *  Represents the contents of the element. The value of this attribute
     * does not change if the contents of the corresponding form control, in
     * an interactive user agent, changes. Changing this attribute, however,
     * resets the contents of the form control.
     * <p>
     *  表示元素的内容。如果交互式用户代理中相应表单控件的内容发生更改,则此属性的值不会更改。但是,更改此属性会重置表单控件的内容。
     * 
     */
    public String getDefaultValue();
    public void setDefaultValue(String defaultValue);

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
     *  Width of control (in characters). See the  cols attribute definition
     * in HTML 4.0.
     * <p>
     *  控制宽度(以字符为单位)。请参阅HTML 4.0中的cols属性定义。
     * 
     */
    public int getCols();
    public void setCols(int cols);

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
     * 使用表单提交时的表单控件或对象名称。请参阅HTML 4.0中的名称属性定义。
     * 
     */
    public String getName();
    public void setName(String name);

    /**
     *  This control is read-only. See the  readonly attribute definition in
     * HTML 4.0.
     * <p>
     *  此控件是只读的。请参阅HTML 4.0中的readonly属性定义。
     * 
     */
    public boolean getReadOnly();
    public void setReadOnly(boolean readOnly);

    /**
     *  Number of text rows. See the  rows attribute definition in HTML 4.0.
     * <p>
     *  文本行数。请参阅HTML 4.0中的行属性定义。
     * 
     */
    public int getRows();
    public void setRows(int rows);

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
     *  The type of this form control. This the string "textarea".
     * <p>
     *  此表单控件的类型。这个字符串"textarea"。
     * 
     */
    public String getType();

    /**
     *  Represents the current contents of the corresponding form control, in
     * an interactive user agent. Changing this attribute changes the
     * contents of the form control, but does not change the contents of the
     * element. If the entirety of the data can not fit into a single
     * <code>DOMString</code> , the implementation may truncate the data.
     * <p>
     *  在交互式用户代理中表示相应表单控件的当前内容。更改此属性会更改表单控件的内容,但不会更改元素的内容。如果整个数据不能适应单个<code> DOMString </code>,则实现可能会截断数据。
     * 
     */
    public String getValue();
    public void setValue(String value);

    /**
     *  Removes keyboard focus from this element.
     * <p>
     *  从此元素中删除键盘焦点。
     * 
     */
    public void blur();

    /**
     *  Gives keyboard focus to this element.
     * <p>
     *  为此元素提供键盘焦点。
     * 
     */
    public void focus();

    /**
     *  Select the contents of the <code>TEXTAREA</code> .
     * <p>
     *  选择<code> TEXTAREA </code>的内容。
     */
    public void select();

}
