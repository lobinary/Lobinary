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
 *  A selectable choice. See the  OPTION element definition in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 * <p>
 *  可选择的选择。请参阅HTML 4.0中的OPTION元素定义。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>文档对象模型(DOM)2级规范</a>。
 * 
 */
public interface HTMLOptionElement extends HTMLElement {
    /**
     *  Returns the <code>FORM</code> element containing this control. Returns
     * <code>null</code> if this control is not within the context of a form.
     * <p>
     *  返回包含此控件的<code> FORM </code>元素。如果此控件不在表单的上下文中,则返回<code> null </code>。
     * 
     */
    public HTMLFormElement getForm();

    /**
     *  Represents the value of the HTML selected attribute. The value of this
     * attribute does not change if the state of the corresponding form
     * control, in an interactive user agent, changes. Changing
     * <code>defaultSelected</code> , however, resets the state of the form
     * control. See the  selected attribute definition in HTML 4.0.
     * <p>
     *  表示HTML选定属性的值。如果交互式用户代理中对应表单控件的状态更改,则此属性的值不会更改。但是,更改<code> defaultSelected </code>会重置表单控件的状态。
     * 请参阅HTML 4.0中选定的属性定义。
     * 
     */
    public boolean getDefaultSelected();
    public void setDefaultSelected(boolean defaultSelected);

    /**
     *  The text contained within the option element.
     * <p>
     *  选项元素中包含的文本。
     * 
     */
    public String getText();

    /**
     *  The index of this <code>OPTION</code> in its parent <code>SELECT</code>
     *  , starting from 0.
     * <p>
     *  这个<code> OPTION </code>在其父<code> SELECT </code>中的索引,从0开始。
     * 
     */
    public int getIndex();

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
     *  Option label for use in hierarchical menus. See the  label attribute
     * definition in HTML 4.0.
     * <p>
     * 用于层级菜单的选项标签。请参阅HTML 4.0中的标签属性定义。
     * 
     */
    public String getLabel();
    public void setLabel(String label);

    /**
     *  Represents the current state of the corresponding form control, in an
     * interactive user agent. Changing this attribute changes the state of
     * the form control, but does not change the value of the HTML selected
     * attribute of the element.
     * <p>
     *  表示交互式用户代理中对应表单控件的当前状态。更改此属性可更改表单控件的状态,但不会更改元素的HTML选定属性的值。
     * 
     */
    public boolean getSelected();
    public void setSelected(boolean selected);

    /**
     *  The current form control value. See the  value attribute definition in
     * HTML 4.0.
     * <p>
     *  当前窗体控件值。请参阅HTML 4.0中的值属性定义。
     */
    public String getValue();
    public void setValue(String value);

}
