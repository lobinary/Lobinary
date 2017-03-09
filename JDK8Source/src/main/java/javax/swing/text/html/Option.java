/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.Serializable;
import javax.swing.text.*;

/**
 * Value for the ListModel used to represent
 * &lt;option&gt; elements.  This is the object
 * installed as items of the DefaultComboBoxModel
 * used to represent the &lt;select&gt; element.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  用于表示&lt; option&gt;的ListModel的值元素。这是作为DefaultComboBoxModel的项目安装的对象,用于表示&lt; select&gt;元件。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author  Timothy Prinzing
 */
public class Option implements Serializable {

    /**
     * Creates a new Option object.
     *
     * <p>
     *  创建一个新的Option对象。
     * 
     * 
     * @param attr the attributes associated with the
     *  option element.  The attributes are copied to
     *  ensure they won't change.
     */
    public Option(AttributeSet attr) {
        this.attr = attr.copyAttributes();
        selected = (attr.getAttribute(HTML.Attribute.SELECTED) != null);
    }

    /**
     * Sets the label to be used for the option.
     * <p>
     *  设置要用于选项的标签。
     * 
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Fetch the label associated with the option.
     * <p>
     *  获取与选项关联的标签。
     * 
     */
    public String getLabel() {
        return label;
    }

    /**
     * Fetch the attributes associated with this option.
     * <p>
     *  获取与此选项关联的属性。
     * 
     */
    public AttributeSet getAttributes() {
        return attr;
    }

    /**
     * String representation is the label.
     * <p>
     *  字符串表示是标签。
     * 
     */
    public String toString() {
        return label;
    }

    /**
     * Sets the selected state.
     * <p>
     *  设置所选状态。
     * 
     */
    protected void setSelection(boolean state) {
        selected = state;
    }

    /**
     * Fetches the selection state associated with this option.
     * <p>
     *  获取与此选项关联的选择状态。
     * 
     */
    public boolean isSelected() {
        return selected;
    }

    /**
     * Convenience method to return the string associated
     * with the <code>value</code> attribute.  If the
     * value has not been specified, the label will be
     * returned.
     * <p>
     *  方便方法返回与<code> value </code>属性相关联的字符串。如果未指定值,将返回标签。
     */
    public String getValue() {
        String value = (String) attr.getAttribute(HTML.Attribute.VALUE);
        if (value == null) {
            value = label;
        }
        return value;
    }

    private boolean selected;
    private String label;
    private AttributeSet attr;
}
