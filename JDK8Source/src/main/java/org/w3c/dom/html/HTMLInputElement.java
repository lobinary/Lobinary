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
 *  Form control.  Note. Depending upon the environment in which the page is
 * being viewed, the value property may be read-only for the file upload
 * input type. For the "password" input type, the actual value returned may
 * be masked to prevent unauthorized use. See the  INPUT element definition
 * in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 * <p>
 *  表单控制。注意。根据查看页面的环境,value属性对于文件上载输入类型可能是只读的。对于"密码"输入类型,返回的实际值可能会被屏蔽,以防止未经授权的使用。
 * 请参阅HTML 4.0中的INPUT元素定义。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>文档对象模型(DOM)2级规范</a>。
 * 
 */
public interface HTMLInputElement extends HTMLElement {
    /**
     *  When the <code>type</code> attribute of the element has the value
     * "Text", "File" or "Password", this represents the HTML value attribute
     * of the element. The value of this attribute does not change if the
     * contents of the corresponding form control, in an interactive user
     * agent, changes. Changing this attribute, however, resets the contents
     * of the form control. See the  value attribute definition in HTML 4.0.
     * <p>
     *  当元素的<code> type </code>属性具有值"Text","File"或"Password"时,这表示元素的HTML值属性。
     * 如果交互式用户代理中相应表单控件的内容发生更改,则此属性的值不会更改。但是,更改此属性会重置表单控件的内容。请参阅HTML 4.0中的值属性定义。
     * 
     */
    public String getDefaultValue();
    public void setDefaultValue(String defaultValue);

    /**
     *  When <code>type</code> has the value "Radio" or "Checkbox", this
     * represents the HTML checked attribute of the element. The value of
     * this attribute does not change if the state of the corresponding form
     * control, in an interactive user agent, changes. Changes to this
     * attribute, however, resets the state of the form control. See the
     * checked attribute definition in HTML 4.0.
     * <p>
     * 当<code> type </code>的值为"Radio"或"Checkbox"时,表示元素的HTML checked属性。如果交互式用户代理中对应表单控件的状态更改,则此属性的值不会更改。
     * 但是,对此属性的更改将重置表单控件的状态。请参阅HTML 4.0中的checked属性定义。
     * 
     */
    public boolean getDefaultChecked();
    public void setDefaultChecked(boolean defaultChecked);

    /**
     *  Returns the <code>FORM</code> element containing this control. Returns
     * <code>null</code> if this control is not within the context of a form.
     * <p>
     *  返回包含此控件的<code> FORM </code>元素。如果此控件不在表单的上下文中,则返回<code> null </code>。
     * 
     */
    public HTMLFormElement getForm();

    /**
     *  A comma-separated list of content types that a server processing this
     * form will handle correctly. See the  accept attribute definition in
     * HTML 4.0.
     * <p>
     *  由处理此表单的服务器正确处理的内容类型的逗号分隔列表。请参阅HTML 4.0中的accept属性定义。
     * 
     */
    public String getAccept();
    public void setAccept(String accept);

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
     *  Alternate text for user agents not rendering the normal content of
     * this element. See the  alt attribute definition in HTML 4.0.
     * <p>
     *  不呈现此元素的正常内容的用户代理的替代文本。请参阅HTML 4.0中的alt属性定义。
     * 
     */
    public String getAlt();
    public void setAlt(String alt);

    /**
     *  When the <code>type</code> attribute of the element has the value
     * "Radio" or "Checkbox", this represents the current state of the form
     * control, in an interactive user agent. Changes to this attribute
     * change the state of the form control, but do not change the value of
     * the HTML value attribute of the element.
     * <p>
     *  当元素的<code> type </code>属性具有值"Radio"或"Checkbox"时,这表示交互式用户代理中表单控件的当前状态。
     * 对此属性的更改会更改表单控件的状态,但不要更改元素的HTML值属性的值。
     * 
     */
    public boolean getChecked();
    public void setChecked(boolean checked);

    /**
     *  The control is unavailable in this context. See the  disabled
     * attribute definition in HTML 4.0.
     * <p>
     * 该控件在此上下文中不可用。请参阅HTML 4.0中的disabled属性定义。
     * 
     */
    public boolean getDisabled();
    public void setDisabled(boolean disabled);

    /**
     *  Maximum number of characters for text fields, when <code>type</code>
     * has the value "Text" or "Password". See the  maxlength attribute
     * definition in HTML 4.0.
     * <p>
     *  当<code>类型</code>的值为"文本"或"密码"时,文本字段的最大字符数。请参阅HTML 4.0中的maxlength属性定义。
     * 
     */
    public int getMaxLength();
    public void setMaxLength(int maxLength);

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
     *  This control is read-only. Relevant only when <code>type</code> has
     * the value "Text" or "Password". See the  readonly attribute definition
     * in HTML 4.0.
     * <p>
     *  此控件是只读的。仅当<code> type </code>的值为"Text"或"Password"时才相关。请参阅HTML 4.0中的readonly属性定义。
     * 
     */
    public boolean getReadOnly();
    public void setReadOnly(boolean readOnly);

    /**
     *  Size information. The precise meaning is specific to each type of
     * field.  See the  size attribute definition in HTML 4.0.
     * <p>
     *  尺寸信息。精确的含义是针对每种类型的字段。请参阅HTML 4.0中的size属性定义。
     * 
     */
    public String getSize();
    public void setSize(String size);

    /**
     *  When the <code>type</code> attribute has the value "Image", this
     * attribute specifies the location of the image to be used to decorate
     * the graphical submit button. See the  src attribute definition in HTML
     * 4.0.
     * <p>
     *  当<code> type </code>属性具有值"Image"时,此属性指定要用于装饰图形提交按钮的图像的位置。请参阅HTML 4.0中的src属性定义。
     * 
     */
    public String getSrc();
    public void setSrc(String src);

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
     *  The type of control created. See the  type attribute definition in
     * HTML 4.0.
     * <p>
     *  创建的控件的类型。请参阅HTML 4.0中的类型属性定义。
     * 
     */
    public String getType();

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
     *  When the <code>type</code> attribute of the element has the value
     * "Text", "File" or "Password", this represents the current contents of
     * the corresponding form control, in an interactive user agent. Changing
     * this attribute changes the contents of the form control, but does not
     * change the value of the HTML value attribute of the element. When the
     * <code>type</code> attribute of the element has the value "Button",
     * "Hidden", "Submit", "Reset", "Image", "Checkbox" or "Radio", this
     * represents the HTML value attribute of the element. See the  value
     * attribute definition in HTML 4.0.
     * <p>
     * 当元素的<code> type </code>属性具有值"Text","File"或"Password"时,这表示交互式用户代理中相应表单控件的当前内容。
     * 更改此属性会更改表单控件的内容,但不会更改元素的HTML值属性的值。
     * 当元素的<code> type </code>属性具有值"Button","Hidden","Submit","Reset","Image","Checkbox"或"Radio"属性的元素。
     * 请参阅HTML 4.0中的值属性定义。
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
     *  Select the contents of the text area. For <code>INPUT</code> elements
     * whose <code>type</code> attribute has one of the following values:
     * "Text", "File", or "Password".
     * <p>
     *  选择文本区域的内容。对于<code> type </code>属性具有以下值之一的<code> INPUT </code>元素："Text","File"或"Password"。
     * 
     */
    public void select();

    /**
     *  Simulate a mouse-click. For <code>INPUT</code> elements whose
     * <code>type</code> attribute has one of the following values: "Button",
     * "Checkbox", "Radio", "Reset", or "Submit".
     * <p>
     *  模拟鼠标单击。
     * 对于<code> type </code>属性具有以下值之一的<code> INPUT </code>元素："Button","Checkbox","Radio","Reset"或"Submit"。
     */
    public void click();

}
