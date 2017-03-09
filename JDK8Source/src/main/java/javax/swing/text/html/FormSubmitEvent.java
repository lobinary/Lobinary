/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.text.html;

import javax.swing.text.*;
import java.net.URL;

/**
 * FormSubmitEvent is used to notify interested
 * parties that a form was submitted.
 *
 * <p>
 *  FormSubmitEvent用于通知感兴趣的各方表单已提交。
 * 
 * 
 * @since 1.5
 * @author    Denis Sharypov
 */

public class FormSubmitEvent extends HTMLFrameHyperlinkEvent {

    /**
     * Represents an HTML form method type.
     * <UL>
     * <LI><code>GET</code> corresponds to the GET form method</LI>
     * <LI><code>POST</code> corresponds to the POST from method</LI>
     * </UL>
     * <p>
     *  表示HTML表单方法类型。
     * <UL>
     *  <LI> <code> GET </code>对应于GET方法</li> <li> <code>
     * </UL>
     * 
     * @since 1.5
     */
    public enum MethodType { GET, POST };

    /**
     * Creates a new object representing an html form submit event.
     *
     * <p>
     *  创建表示html表单提交事件的新对象。
     * 
     * 
     * @param source the object responsible for the event
     * @param type the event type
     * @param actionURL the form action URL
     * @param sourceElement the element that corresponds to the source
     *                      of the event
     * @param targetFrame the Frame to display the document in
     * @param method the form method type
     * @param data the form submission data
     */
    FormSubmitEvent(Object source, EventType type, URL targetURL,
                   Element sourceElement, String targetFrame,
                    MethodType method, String data) {
        super(source, type, targetURL, sourceElement, targetFrame);
        this.method = method;
        this.data = data;
    }


    /**
     * Gets the form method type.
     *
     * <p>
     *  获取表单方法类型。
     * 
     * 
     * @return the form method type, either
     * <code>Method.GET</code> or <code>Method.POST</code>.
     */
    public MethodType getMethod() {
        return method;
    }

    /**
     * Gets the form submission data.
     *
     * <p>
     *  获取表单提交数据。
     * 
     * @return the string representing the form submission data.
     */
    public String getData() {
        return data;
    }

    private MethodType method;
    private String data;
}
