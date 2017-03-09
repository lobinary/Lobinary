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
 *  The <code>FORM</code> element encompasses behavior similar to a collection
 * and an element. It provides direct access to the contained input elements
 * as well as the attributes of the form element. See the  FORM element
 * definition in HTML 4.0.
 * <p>See also the <a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>Document Object Model (DOM) Level 2 Specification</a>.
 * <p>
 *  <code> FORM </code>元素包含类似于集合和元素的行为。它提供对包含的输入元素以及表单元素的属性的直接访问。请参阅HTML 4.0中的FORM元素定义。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2000/CR-DOM-Level-2-20000510'>文档对象模型(DOM)2级规范</a>。
 * 
 */
public interface HTMLFormElement extends HTMLElement {
    /**
     *  Returns a collection of all control elements in the form.
     * <p>
     *  返回表单中所有控件元素的集合。
     * 
     */
    public HTMLCollection getElements();

    /**
     *  The number of form controls in the form.
     * <p>
     *  表单中表单控件的数量。
     * 
     */
    public int getLength();

    /**
     *  Names the form.
     * <p>
     *  命名表单。
     * 
     */
    public String getName();
    public void setName(String name);

    /**
     *  List of character sets supported by the server. See the
     * accept-charset attribute definition in HTML 4.0.
     * <p>
     *  服务器支持的字符集列表。请参阅HTML 4.0中的accept-charset属性定义。
     * 
     */
    public String getAcceptCharset();
    public void setAcceptCharset(String acceptCharset);

    /**
     *  Server-side form handler. See the  action attribute definition in HTML
     * 4.0.
     * <p>
     *  服务器端表单处理程序。请参阅HTML 4.0中的操作属性定义。
     * 
     */
    public String getAction();
    public void setAction(String action);

    /**
     *  The content type of the submitted form,  generally
     * "application/x-www-form-urlencoded".  See the  enctype attribute
     * definition in HTML 4.0.
     * <p>
     *  提交表单的内容类型,通常为"application / x-www-form-urlencoded"。请参阅HTML 4.0中的enctype属性定义。
     * 
     */
    public String getEnctype();
    public void setEnctype(String enctype);

    /**
     *  HTTP method used to submit form. See the  method attribute definition
     * in HTML 4.0.
     * <p>
     *  用于提交表单的HTTP方法。请参阅HTML 4.0中的方法属性定义。
     * 
     */
    public String getMethod();
    public void setMethod(String method);

    /**
     *  Frame to render the resource in. See the  target attribute definition
     * in HTML 4.0.
     * <p>
     * 呈现资源的框架。请参阅HTML 4.0中的目标属性定义。
     * 
     */
    public String getTarget();
    public void setTarget(String target);

    /**
     *  Submits the form. It performs the same action as a  submit button.
     * <p>
     *  提交表单。它执行与提交按钮相同的操作。
     * 
     */
    public void submit();

    /**
     *  Restores a form element's default values. It performs  the same action
     * as a reset button.
     * <p>
     *  恢复表单元素的默认值。它执行与复位按钮相同的操作。
     */
    public void reset();

}
