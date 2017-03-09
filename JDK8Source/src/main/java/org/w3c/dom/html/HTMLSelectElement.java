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

import org.w3c.dom.DOMException;

/**
 *  The select element allows the selection of an option. The contained
 * options can be directly accessed through the select element as a
 * collection. See the  SELECT element definition in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 * <p>
 *  select元素允许选择选项。所包含的选项可以通过select元素直接访问作为集合。请参阅HTML 4.0中的SELECT元素定义。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>文档对象模型(DOM)2级规范</a>。
 * 
 */
public interface HTMLSelectElement extends HTMLElement {
    /**
     *  The type of this form control. This is the string "select-multiple"
     * when the multiple attribute is <code>true</code> and the string
     * "select-one" when <code>false</code> .
     * <p>
     *  此表单控件的类型。当多个属性是<code> true </code>时,这是字符串"select-multiple",而当<code> false </code>时,字符串"select-one"。
     * 
     */
    public String getType();

    /**
     *  The ordinal index of the selected option, starting from 0. The value
     * -1 is returned if no element is selected. If multiple options are
     * selected, the index of the first selected option is returned.
     * <p>
     *  所选选项的序号索引,从0开始。如果未选择任何元素,则返回值-1。如果选择多个选项,则返回第一个选定选项的索引。
     * 
     */
    public int getSelectedIndex();
    public void setSelectedIndex(int selectedIndex);

    /**
     *  The current form control value.
     * <p>
     *  当前窗体控件值。
     * 
     */
    public String getValue();
    public void setValue(String value);

    /**
     *  The number of options in this <code>SELECT</code> .
     * <p>
     *  此<code> SELECT </code>中的选项数。
     * 
     */
    public int getLength();

    /**
     *  Returns the <code>FORM</code> element containing this control. Returns
     * <code>null</code> if this control is not within the context of a form.
     * <p>
     * 返回包含此控件的<code> FORM </code>元素。如果此控件不在表单的上下文中,则返回<code> null </code>。
     * 
     */
    public HTMLFormElement getForm();

    /**
     *  The collection of <code>OPTION</code> elements contained by this
     * element.
     * <p>
     *  此元素包含的<code> OPTION </code>元素的集合。
     * 
     */
    public HTMLCollection getOptions();

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
     *  If true, multiple <code>OPTION</code> elements may  be selected in
     * this <code>SELECT</code> . See the  multiple attribute definition in
     * HTML 4.0.
     * <p>
     *  如果为true,则可以在此<code> SELECT </code>中选择多个<code> OPTION </code>元素。请参阅HTML 4.0中的多属性定义。
     * 
     */
    public boolean getMultiple();
    public void setMultiple(boolean multiple);

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
     *  Number of visible rows. See the  size attribute definition in HTML 4.0.
     * <p>
     *  可见行数。请参阅HTML 4.0中的size属性定义。
     * 
     */
    public int getSize();
    public void setSize(int size);

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
     *  Add a new element to the collection of <code>OPTION</code> elements
     * for this <code>SELECT</code> . This method is the equivalent of the
     * <code>appendChild</code> method of the <code>Node</code> interface if
     * the <code>before</code> parameter is <code>null</code> . It is
     * equivalent to the <code>insertBefore</code> method on the parent of
     * <code>before</code> in all other cases.
     * <p>
     *  为<code> SELECT </code>元素的<code> OPTION </code>元素集合添加一个新元素。
     * 如果<code> before </code>参数为<code> null </code>之前,此方法等同于<code> Node </code>接口的<code> appendChild </code>
     * 它在所有其他情况下等效于<code>之前</code>的父代码上的<code> insertBefore </code>方法。
     *  为<code> SELECT </code>元素的<code> OPTION </code>元素集合添加一个新元素。
     * 
     * 
     * @param element  The element to add.
     * @param before  The element to insert before, or <code>null</code> for
     *   the tail of the list.
     * @exception DOMException
     *    NOT_FOUND_ERR: Raised if <code>before</code> is not a descendant of
     *   the <code>SELECT</code> element.
     */
    public void add(HTMLElement element,
                    HTMLElement before)
                    throws DOMException;

    /**
     *  Remove an element from the collection of <code>OPTION</code> elements
     * for this <code>SELECT</code> . Does nothing if no element has the given
     *  index.
     * <p>
     *  从<code> SELECT </code>的<code> OPTION </code>元素集合中删除一个元素。如果没有元素具有给定的索引,则不执行任何操作。
     * 
     * 
     * @param index  The index of the item to remove, starting from 0.
     */
    public void remove(int index);

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
     */
    public void focus();

}
