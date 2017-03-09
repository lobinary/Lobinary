/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.text;

import java.io.Serializable;
import java.text.ParseException;
import javax.swing.JFormattedTextField;

/**
 * An implementation of
 * <code>JFormattedTextField.AbstractFormatterFactory</code>.
 * <code>DefaultFormatterFactory</code> allows specifying a number of
 * different <code>JFormattedTextField.AbstractFormatter</code>s that are to
 * be used.
 * The most important one is the default one
 * (<code>setDefaultFormatter</code>). The default formatter will be used
 * if a more specific formatter could not be found. The following process
 * is used to determine the appropriate formatter to use.
 * <ol>
 *   <li>Is the passed in value null? Use the null formatter.
 *   <li>Does the <code>JFormattedTextField</code> have focus? Use the edit
 *       formatter.
 *   <li>Otherwise, use the display formatter.
 *   <li>If a non-null <code>AbstractFormatter</code> has not been found, use
 *       the default formatter.
 * </ol>
 * <p>
 * The following code shows how to configure a
 * <code>JFormattedTextField</code> with two
 * <code>JFormattedTextField.AbstractFormatter</code>s, one for display and
 * one for editing.
 * <pre>
 * JFormattedTextField.AbstractFormatter editFormatter = ...;
 * JFormattedTextField.AbstractFormatter displayFormatter = ...;
 * DefaultFormatterFactory factory = new DefaultFormatterFactory(
 *                 displayFormatter, displayFormatter, editFormatter);
 * JFormattedTextField tf = new JFormattedTextField(factory);
 * </pre>
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
 *  <code> JFormattedTextField.AbstractFormatterFactory </code>的实现。
 *  <code> DefaultFormatterFactory </code>允许指定要使用的多个不同的<code> JFormattedTextField.AbstractFormatter </code>
 * 。
 *  <code> JFormattedTextField.AbstractFormatterFactory </code>的实现。
 * 最重要的是默认的(<code> setDefaultFormatter </code>)。如果找不到更具体的格式化程序,将使用默认格式化程序。以下过程用于确定要使用的适当格式化程序。
 * <ol>
 *  <li>传入的值是否为null?使用null格式化程序。 <li> <code> JFormattedTextField </code>有焦点吗?使用编辑格式化程序。
 *  <li>否则,使用显示格式化程序。 <li>如果未找到非空的<code> AbstractFormatter </code>,请使用默认格式化程序。
 * </ol>
 * <p>
 *  以下代码显示如何使用两个<code> JFormattedTextField.AbstractFormatter </code>配置<code> JFormattedTextField </code>
 * ,一个用于显示,一个用于编辑。
 * <pre>
 *  JFormattedTextField.AbstractFormatter editFormatter = ...; JFormattedTextField.AbstractFormatter dis
 * playFormatter = ...; DefaultFormatterFactory factory = new DefaultFormatterFactory(displayFormatter,d
 * isplayFormatter,editFormatter); JFormattedTextField tf = new JFormattedTextField(factory);。
 * </pre>
 * <p>
 * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see javax.swing.JFormattedTextField
 *
 * @since 1.4
 */
public class DefaultFormatterFactory extends JFormattedTextField.AbstractFormatterFactory implements Serializable {
    /**
     * Default <code>AbstractFormatter</code> to use if a more specific one has
     * not been specified.
     * <p>
     *  默认<code> AbstractFormatter </code>用于如果没有指定更具体的一个。
     * 
     */
    private JFormattedTextField.AbstractFormatter defaultFormat;

    /**
     * <code>JFormattedTextField.AbstractFormatter</code> to use for display.
     * <p>
     *  <code> JFormattedTextField.AbstractFormatter </code>用于显示。
     * 
     */
    private JFormattedTextField.AbstractFormatter displayFormat;

    /**
     * <code>JFormattedTextField.AbstractFormatter</code> to use for editing.
     * <p>
     *  <code> JFormattedTextField.AbstractFormatter </code>用于编辑。
     * 
     */
    private JFormattedTextField.AbstractFormatter editFormat;

    /**
     * <code>JFormattedTextField.AbstractFormatter</code> to use if the value
     * is null.
     * <p>
     *  <code> JFormattedTextField.AbstractFormatter </code>如果值为null,则使用。
     * 
     */
    private JFormattedTextField.AbstractFormatter nullFormat;


    public DefaultFormatterFactory() {
    }

    /**
     * Creates a <code>DefaultFormatterFactory</code> with the specified
     * <code>JFormattedTextField.AbstractFormatter</code>.
     *
     * <p>
     *  使用指定的<code> JFormattedTextField.AbstractFormatter </code>创建<code> DefaultFormatterFactory </code>。
     * 
     * 
     * @param defaultFormat JFormattedTextField.AbstractFormatter to be used
     *                      if a more specific
     *                      JFormattedTextField.AbstractFormatter can not be
     *                      found.
     */
    public DefaultFormatterFactory(JFormattedTextField.
                                       AbstractFormatter defaultFormat) {
        this(defaultFormat, null);
    }

    /**
     * Creates a <code>DefaultFormatterFactory</code> with the specified
     * <code>JFormattedTextField.AbstractFormatter</code>s.
     *
     * <p>
     *  使用指定的<code> JFormattedTextField.AbstractFormatter </code>创建<code> DefaultFormatterFactory </code>。
     * 
     * 
     * @param defaultFormat JFormattedTextField.AbstractFormatter to be used
     *                      if a more specific
     *                      JFormattedTextField.AbstractFormatter can not be
     *                      found.
     * @param displayFormat JFormattedTextField.AbstractFormatter to be used
     *                      when the JFormattedTextField does not have focus.
     */
    public DefaultFormatterFactory(
                     JFormattedTextField.AbstractFormatter defaultFormat,
                     JFormattedTextField.AbstractFormatter displayFormat) {
        this(defaultFormat, displayFormat, null);
    }

    /**
     * Creates a DefaultFormatterFactory with the specified
     * JFormattedTextField.AbstractFormatters.
     *
     * <p>
     *  使用指定的JFormattedTextField.AbstractFormatters创建DefaultFormatterFactory。
     * 
     * 
     * @param defaultFormat JFormattedTextField.AbstractFormatter to be used
     *                      if a more specific
     *                      JFormattedTextField.AbstractFormatter can not be
     *                      found.
     * @param displayFormat JFormattedTextField.AbstractFormatter to be used
     *                      when the JFormattedTextField does not have focus.
     * @param editFormat    JFormattedTextField.AbstractFormatter to be used
     *                      when the JFormattedTextField has focus.
     */
    public DefaultFormatterFactory(
                   JFormattedTextField.AbstractFormatter defaultFormat,
                   JFormattedTextField.AbstractFormatter displayFormat,
                   JFormattedTextField.AbstractFormatter editFormat) {
        this(defaultFormat, displayFormat, editFormat, null);
    }

    /**
     * Creates a DefaultFormatterFactory with the specified
     * JFormattedTextField.AbstractFormatters.
     *
     * <p>
     *  使用指定的JFormattedTextField.AbstractFormatters创建DefaultFormatterFactory。
     * 
     * 
     * @param defaultFormat JFormattedTextField.AbstractFormatter to be used
     *                      if a more specific
     *                      JFormattedTextField.AbstractFormatter can not be
     *                      found.
     * @param displayFormat JFormattedTextField.AbstractFormatter to be used
     *                      when the JFormattedTextField does not have focus.
     * @param editFormat    JFormattedTextField.AbstractFormatter to be used
     *                      when the JFormattedTextField has focus.
     * @param nullFormat    JFormattedTextField.AbstractFormatter to be used
     *                      when the JFormattedTextField has a null value.
     */
    public DefaultFormatterFactory(
                  JFormattedTextField.AbstractFormatter defaultFormat,
                  JFormattedTextField.AbstractFormatter displayFormat,
                  JFormattedTextField.AbstractFormatter editFormat,
                  JFormattedTextField.AbstractFormatter nullFormat) {
        this.defaultFormat = defaultFormat;
        this.displayFormat = displayFormat;
        this.editFormat = editFormat;
        this.nullFormat = nullFormat;
    }

    /**
     * Sets the <code>JFormattedTextField.AbstractFormatter</code> to use as
     * a last resort, eg in case a display, edit or null
     * <code>JFormattedTextField.AbstractFormatter</code> has not been
     * specified.
     *
     * <p>
     *  设置<code> JFormattedTextField.AbstractFormatter </code>作为最后手段,例如,如果未指定显示,编辑或null <code> JFormattedTex
     * tField.AbstractFormatter </code>。
     * 
     * 
     * @param atf JFormattedTextField.AbstractFormatter used if a more
     *            specific is not specified
     */
    public void setDefaultFormatter(JFormattedTextField.AbstractFormatter atf){
        defaultFormat = atf;
    }

    /**
     * Returns the <code>JFormattedTextField.AbstractFormatter</code> to use
     * as a last resort, eg in case a display, edit or null
     * <code>JFormattedTextField.AbstractFormatter</code>
     * has not been specified.
     *
     * <p>
     * 返回<code> JFormattedTextField.AbstractFormatter </code>作为最后手段,例如,如果没有指定显示,编辑或null <code> JFormattedTex
     * tField.AbstractFormatter </code>。
     * 
     * 
     * @return JFormattedTextField.AbstractFormatter used if a more specific
     *         one is not specified.
     */
    public JFormattedTextField.AbstractFormatter getDefaultFormatter() {
        return defaultFormat;
    }

    /**
     * Sets the <code>JFormattedTextField.AbstractFormatter</code> to use if
     * the <code>JFormattedTextField</code> is not being edited and either
     * the value is not-null, or the value is null and a null formatter has
     * has not been specified.
     *
     * <p>
     *  设置<code> JFormattedTextField.AbstractFormatter </code>,以便在未编辑<code> JFormattedTextField </code>且值为非n
     * ull或值为null且未有空格式化程序时使用指定。
     * 
     * 
     * @param atf JFormattedTextField.AbstractFormatter to use when the
     *            JFormattedTextField does not have focus
     */
    public void setDisplayFormatter(JFormattedTextField.AbstractFormatter atf){
        displayFormat = atf;
    }

    /**
     * Returns the <code>JFormattedTextField.AbstractFormatter</code> to use
     * if the <code>JFormattedTextField</code> is not being edited and either
     * the value is not-null, or the value is null and a null formatter has
     * has not been specified.
     *
     * <p>
     *  返回<code> JFormattedTextField.AbstractFormatter </code>,如果<code> JFormattedTextField </code>未被编辑,并且值不
     * 为null,或值为null,并且没有空格式化器指定。
     * 
     * 
     * @return JFormattedTextField.AbstractFormatter to use when the
     *         JFormattedTextField does not have focus
     */
    public JFormattedTextField.AbstractFormatter getDisplayFormatter() {
        return displayFormat;
    }

    /**
     * Sets the <code>JFormattedTextField.AbstractFormatter</code> to use if
     * the <code>JFormattedTextField</code> is being edited and either
     * the value is not-null, or the value is null and a null formatter has
     * has not been specified.
     *
     * <p>
     *  设置<code> JFormattedTextField.AbstractFormatter </code>以便在编辑<code> JFormattedTextField </code>并且值不为nu
     * ll或值为null且未指定空格式化程序时使用。
     * 
     * 
     * @param atf JFormattedTextField.AbstractFormatter to use when the
     *            component has focus
     */
    public void setEditFormatter(JFormattedTextField.AbstractFormatter atf) {
        editFormat = atf;
    }

    /**
     * Returns the <code>JFormattedTextField.AbstractFormatter</code> to use
     * if the <code>JFormattedTextField</code> is being edited and either
     * the value is not-null, or the value is null and a null formatter has
     * has not been specified.
     *
     * <p>
     *  返回<code> JFormattedTextField.AbstractFormatter </code>,如果正在编辑<code> JFormattedTextField </code>,并且值不
     * 为null,或者值为null,并且没有指定null格式化程序。
     * 
     * 
     * @return JFormattedTextField.AbstractFormatter to use when the
     *         component has focus
     */
    public JFormattedTextField.AbstractFormatter getEditFormatter() {
        return editFormat;
    }

    /**
     * Sets the formatter to use if the value of the JFormattedTextField is
     * null.
     *
     * <p>
     *  设置要在JFormattedTextField的值为null时使用的格式化程序。
     * 
     * 
     * @param atf JFormattedTextField.AbstractFormatter to use when
     * the value of the JFormattedTextField is null.
     */
    public void setNullFormatter(JFormattedTextField.AbstractFormatter atf) {
        nullFormat = atf;
    }

    /**
     * Returns the formatter to use if the value is null.
     *
     * <p>
     *  如果值为null,则返回要使用的格式化程序。
     * 
     * 
     * @return JFormattedTextField.AbstractFormatter to use when the value is
     *         null
     */
    public JFormattedTextField.AbstractFormatter getNullFormatter() {
        return nullFormat;
    }

    /**
     * Returns either the default formatter, display formatter, editor
     * formatter or null formatter based on the state of the
     * JFormattedTextField.
     *
     * <p>
     *  基于JFormattedTextField的状态,返回默认格式化程序,显示格式化程序,编辑器格式化程序或空格式化程序。
     * 
     * @param source JFormattedTextField requesting
     *               JFormattedTextField.AbstractFormatter
     * @return JFormattedTextField.AbstractFormatter to handle
     *         formatting duties.
     */
    public JFormattedTextField.AbstractFormatter getFormatter(
                     JFormattedTextField source) {
        JFormattedTextField.AbstractFormatter format = null;

        if (source == null) {
            return null;
        }
        Object value = source.getValue();

        if (value == null) {
            format = getNullFormatter();
        }
        if (format == null) {
            if (source.hasFocus()) {
                format = getEditFormatter();
            }
            else {
                format = getDisplayFormatter();
            }
            if (format == null) {
                format = getDefaultFormatter();
            }
        }
        return format;
    }
}
