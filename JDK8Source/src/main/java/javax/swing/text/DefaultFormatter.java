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

import sun.reflect.misc.ReflectUtil;
import sun.swing.SwingUtilities2;

import java.io.Serializable;
import java.lang.reflect.*;
import java.text.ParseException;
import javax.swing.*;
import javax.swing.text.*;

/**
 * <code>DefaultFormatter</code> formats arbitrary objects. Formatting is done
 * by invoking the <code>toString</code> method. In order to convert the
 * value back to a String, your class must provide a constructor that
 * takes a String argument. If no single argument constructor that takes a
 * String is found, the returned value will be the String passed into
 * <code>stringToValue</code>.
 * <p>
 * Instances of <code>DefaultFormatter</code> can not be used in multiple
 * instances of <code>JFormattedTextField</code>. To obtain a copy of
 * an already configured <code>DefaultFormatter</code>, use the
 * <code>clone</code> method.
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
 *  <code> DefaultFormatter </code>格式化任意对象。格式化是通过调用<code> toString </code>方法来完成的。
 * 为了将值转换回String,您的类必须提供一个接受String参数的构造函数。
 * 如果没有找到带有String的单个参数构造函数,则返回的值将是传递到<code> stringToValue </code>中的String。
 * <p>
 *  <code> DefaultFormatter </code>实例不能在<code> JFormattedTextField </code>的多个实例中使用。
 * 要获取已配置的<code> DefaultFormatter </code>的副本,请使用<code> clone </code>方法。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see javax.swing.JFormattedTextField.AbstractFormatter
 *
 * @since 1.4
 */
public class DefaultFormatter extends JFormattedTextField.AbstractFormatter
                    implements Cloneable, Serializable {
    /** Indicates if the value being edited must match the mask. */
    private boolean allowsInvalid;

    /** If true, editing mode is in overwrite (or strikethough). */
    private boolean overwriteMode;

    /** If true, any time a valid edit happens commitEdit is invoked. */
    private boolean commitOnEdit;

    /** Class used to create new instances. */
    private Class<?> valueClass;

    /** NavigationFilter that forwards calls back to DefaultFormatter. */
    private NavigationFilter navigationFilter;

    /** DocumentFilter that forwards calls back to DefaultFormatter. */
    private DocumentFilter documentFilter;

    /** Used during replace to track the region to replace. */
    transient ReplaceHolder replaceHolder;


    /**
     * Creates a DefaultFormatter.
     * <p>
     *  创建DefaultFormatter。
     * 
     */
    public DefaultFormatter() {
        overwriteMode = true;
        allowsInvalid = true;
    }

    /**
     * Installs the <code>DefaultFormatter</code> onto a particular
     * <code>JFormattedTextField</code>.
     * This will invoke <code>valueToString</code> to convert the
     * current value from the <code>JFormattedTextField</code> to
     * a String. This will then install the <code>Action</code>s from
     * <code>getActions</code>, the <code>DocumentFilter</code>
     * returned from <code>getDocumentFilter</code> and the
     * <code>NavigationFilter</code> returned from
     * <code>getNavigationFilter</code> onto the
     * <code>JFormattedTextField</code>.
     * <p>
     * Subclasses will typically only need to override this if they
     * wish to install additional listeners on the
     * <code>JFormattedTextField</code>.
     * <p>
     * If there is a <code>ParseException</code> in converting the
     * current value to a String, this will set the text to an empty
     * String, and mark the <code>JFormattedTextField</code> as being
     * in an invalid state.
     * <p>
     * While this is a public method, this is typically only useful
     * for subclassers of <code>JFormattedTextField</code>.
     * <code>JFormattedTextField</code> will invoke this method at
     * the appropriate times when the value changes, or its internal
     * state changes.
     *
     * <p>
     * 将<code> DefaultFormatter </code>安装到特定的<code> JFormattedTextField </code>。
     * 这将调用<code> valueToString </code>将当前值从<code> JFormattedTextField </code>转换为字符串。
     * 这将安装<code> Action </code>从<code> getActions </code>,<code> DocumentFilter </code>从<code> getDocumentF
     * ilter </code>返回,<code> NavigationFilter < / code>从<code> getNavigationFilter </code>返回到<code> JFormat
     * tedTextField </code>。
     * 这将调用<code> valueToString </code>将当前值从<code> JFormattedTextField </code>转换为字符串。
     * <p>
     *  如果子类想要在<code> JFormattedTextField </code>上安装附加的监听器,通常只需要覆盖这个。
     * <p>
     *  如果在将当前值转换为字符串时存在<code> ParseException </code>,则会将文本设置为空字符串,并将<code> JFormattedTextField </code>标记为无效
     * 状态。
     * <p>
     *  虽然这是一个公共方法,但这通常只对<code> JFormattedTextField </code>的子类有用。
     *  <code> JFormattedTextField </code>将在值更改或其内部状态更改的适当时间调用此方法。
     * 
     * 
     * @param ftf JFormattedTextField to format for, may be null indicating
     *            uninstall from current JFormattedTextField.
     */
    public void install(JFormattedTextField ftf) {
        super.install(ftf);
        positionCursorAtInitialLocation();
    }

    /**
     * Sets when edits are published back to the
     * <code>JFormattedTextField</code>. If true, <code>commitEdit</code>
     * is invoked after every valid edit (any time the text is edited). On
     * the other hand, if this is false than the <code>DefaultFormatter</code>
     * does not publish edits back to the <code>JFormattedTextField</code>.
     * As such, the only time the value of the <code>JFormattedTextField</code>
     * will change is when <code>commitEdit</code> is invoked on
     * <code>JFormattedTextField</code>, typically when enter is pressed
     * or focus leaves the <code>JFormattedTextField</code>.
     *
     * <p>
     * 设置何时将编辑发布回<code> JFormattedTextField </code>。如果为true,则在每次有效编辑(任何时候编辑文本)后调用<code> commitEdit </code>。
     * 另一方面,如果这不是<code> DefaultFormatter </code>不发布编辑回到<code> JFormattedTextField </code>。
     * 因此,<code> JFormattedTextField </code>的值只有当<code> commitEdit </code>在<code> JFormattedTextField </code>
     * 上调用时,通常当按下enter或焦点离开<code> JFormattedTextField </code>。
     * 另一方面,如果这不是<code> DefaultFormatter </code>不发布编辑回到<code> JFormattedTextField </code>。
     * 
     * 
     * @param commit Used to indicate when edits are committed back to the
     *               JTextComponent
     */
    public void setCommitsOnValidEdit(boolean commit) {
        commitOnEdit = commit;
    }

    /**
     * Returns when edits are published back to the
     * <code>JFormattedTextField</code>.
     *
     * <p>
     *  编辑发布回到<code> JFormattedTextField </code>时返回。
     * 
     * 
     * @return true if edits are committed after every valid edit
     */
    public boolean getCommitsOnValidEdit() {
        return commitOnEdit;
    }

    /**
     * Configures the behavior when inserting characters. If
     * <code>overwriteMode</code> is true (the default), new characters
     * overwrite existing characters in the model.
     *
     * <p>
     *  配置插入字符时的行为。如果<code> overwriteMode </code>为true(默认值),则新字符将覆盖模型中的现有字符。
     * 
     * 
     * @param overwriteMode Indicates if overwrite or overstrike mode is used
     */
    public void setOverwriteMode(boolean overwriteMode) {
        this.overwriteMode = overwriteMode;
    }

    /**
     * Returns the behavior when inserting characters.
     *
     * <p>
     *  返回插入字符时的行为。
     * 
     * 
     * @return true if newly inserted characters overwrite existing characters
     */
    public boolean getOverwriteMode() {
        return overwriteMode;
    }

    /**
     * Sets whether or not the value being edited is allowed to be invalid
     * for a length of time (that is, <code>stringToValue</code> throws
     * a <code>ParseException</code>).
     * It is often convenient to allow the user to temporarily input an
     * invalid value.
     *
     * <p>
     *  设置是否允许正在编辑的值在一段时间内无效(即<code> stringToValue </code>抛出<code> ParseException </code>)。
     * 通常方便的是允许用户临时输入无效值。
     * 
     * 
     * @param allowsInvalid Used to indicate if the edited value must always
     *        be valid
     */
    public void setAllowsInvalid(boolean allowsInvalid) {
        this.allowsInvalid = allowsInvalid;
    }

    /**
     * Returns whether or not the value being edited is allowed to be invalid
     * for a length of time.
     *
     * <p>
     *  返回正在编辑的值是否允许在一段时间内无效。
     * 
     * 
     * @return false if the edited value must always be valid
     */
    public boolean getAllowsInvalid() {
        return allowsInvalid;
    }

    /**
     * Sets that class that is used to create new Objects. If the
     * passed in class does not have a single argument constructor that
     * takes a String, String values will be used.
     *
     * <p>
     *  设置用于创建新对象的类。如果传入的类不具有接受String的单个参数构造函数,则将使用String值。
     * 
     * 
     * @param valueClass Class used to construct return value from
     *        stringToValue
     */
    public void setValueClass(Class<?> valueClass) {
        this.valueClass = valueClass;
    }

    /**
     * Returns that class that is used to create new Objects.
     *
     * <p>
     *  返回用于创建新对象的类。
     * 
     * 
     * @return Class used to construct return value from stringToValue
     */
    public Class<?> getValueClass() {
        return valueClass;
    }

    /**
     * Converts the passed in String into an instance of
     * <code>getValueClass</code> by way of the constructor that
     * takes a String argument. If <code>getValueClass</code>
     * returns null, the Class of the current value in the
     * <code>JFormattedTextField</code> will be used. If this is null, a
     * String will be returned. If the constructor throws an exception, a
     * <code>ParseException</code> will be thrown. If there is no single
     * argument String constructor, <code>string</code> will be returned.
     *
     * <p>
     * 通过接受String参数的构造函数将传递的String转换为<code> getValueClass </code>的实例。
     * 如果<code> getValueClass </code>返回null,将使用<code> JFormattedTextField </code>中的当前值的类。如果这是null,将返回一个字符串。
     * 如果构造函数抛出异常,将抛出<code> ParseException </code>。如果没有单个参数String构造函数,将返回<code> string </code>。
     * 
     * 
     * @throws ParseException if there is an error in the conversion
     * @param string String to convert
     * @return Object representation of text
     */
    public Object stringToValue(String string) throws ParseException {
        Class<?> vc = getValueClass();
        JFormattedTextField ftf = getFormattedTextField();

        if (vc == null && ftf != null) {
            Object value = ftf.getValue();

            if (value != null) {
                vc = value.getClass();
            }
        }
        if (vc != null) {
            Constructor cons;

            try {
                ReflectUtil.checkPackageAccess(vc);
                SwingUtilities2.checkAccess(vc.getModifiers());
                cons = vc.getConstructor(new Class[]{String.class});

            } catch (NoSuchMethodException nsme) {
                cons = null;
            }

            if (cons != null) {
                try {
                    SwingUtilities2.checkAccess(cons.getModifiers());
                    return cons.newInstance(new Object[] { string });
                } catch (Throwable ex) {
                    throw new ParseException("Error creating instance", 0);
                }
            }
        }
        return string;
    }

    /**
     * Converts the passed in Object into a String by way of the
     * <code>toString</code> method.
     *
     * <p>
     *  通过<code> toString </code>方法将传入的Object转换为String。
     * 
     * 
     * @throws ParseException if there is an error in the conversion
     * @param value Value to convert
     * @return String representation of value
     */
    public String valueToString(Object value) throws ParseException {
        if (value == null) {
            return "";
        }
        return value.toString();
    }

    /**
     * Returns the <code>DocumentFilter</code> used to restrict the characters
     * that can be input into the <code>JFormattedTextField</code>.
     *
     * <p>
     *  返回用于限制可以输入到<code> JFormattedTextField </code>中的字符的<code> DocumentFilter </code>。
     * 
     * 
     * @return DocumentFilter to restrict edits
     */
    protected DocumentFilter getDocumentFilter() {
        if (documentFilter == null) {
            documentFilter = new DefaultDocumentFilter();
        }
        return documentFilter;
    }

    /**
     * Returns the <code>NavigationFilter</code> used to restrict where the
     * cursor can be placed.
     *
     * <p>
     *  返回用于限制光标位置的<code> NavigationFilter </code>。
     * 
     * 
     * @return NavigationFilter to restrict navigation
     */
    protected NavigationFilter getNavigationFilter() {
        if (navigationFilter == null) {
            navigationFilter = new DefaultNavigationFilter();
        }
        return navigationFilter;
    }

    /**
     * Creates a copy of the DefaultFormatter.
     *
     * <p>
     *  创建DefaultFormatter的副本。
     * 
     * 
     * @return copy of the DefaultFormatter
     */
    public Object clone() throws CloneNotSupportedException {
        DefaultFormatter formatter = (DefaultFormatter)super.clone();

        formatter.navigationFilter = null;
        formatter.documentFilter = null;
        formatter.replaceHolder = null;
        return formatter;
    }


    /**
     * Positions the cursor at the initial location.
     * <p>
     *  将光标定位在初始位置。
     * 
     */
    void positionCursorAtInitialLocation() {
        JFormattedTextField ftf = getFormattedTextField();
        if (ftf != null) {
            ftf.setCaretPosition(getInitialVisualPosition());
        }
    }

    /**
     * Returns the initial location to position the cursor at. This forwards
     * the call to <code>getNextNavigatableChar</code>.
     * <p>
     *  返回将光标定位到的初始位置。这会将调用转发到<code> getNextNavigatableChar </code>。
     * 
     */
    int getInitialVisualPosition() {
        return getNextNavigatableChar(0, 1);
    }

    /**
     * Subclasses should override this if they want cursor navigation
     * to skip certain characters. A return value of false indicates
     * the character at <code>offset</code> should be skipped when
     * navigating throught the field.
     * <p>
     *  如果子类想要光标导航跳过某些字符,它应该覆盖这个。返回值false表示在导航字段时应跳过<code> offset </code>处的字符。
     * 
     */
    boolean isNavigatable(int offset) {
        return true;
    }

    /**
     * Returns true if the text in <code>text</code> can be inserted.  This
     * does not mean the text will ultimately be inserted, it is used if
     * text can trivially reject certain characters.
     * <p>
     *  如果<code> text </code>中的文本可以插入,则返回true。这并不意味着文本将最终被插入,如果文本可以平凡地拒绝某些字符,则使用它。
     * 
     */
    boolean isLegalInsertText(String text) {
        return true;
    }

    /**
     * Returns the next editable character starting at offset incrementing
     * the offset by <code>direction</code>.
     * <p>
     * 返回下一个可编辑字符,从offset开始,按<code> direction </code>增加偏移量。
     * 
     */
    private int getNextNavigatableChar(int offset, int direction) {
        int max = getFormattedTextField().getDocument().getLength();

        while (offset >= 0 && offset < max) {
            if (isNavigatable(offset)) {
                return offset;
            }
            offset += direction;
        }
        return offset;
    }

    /**
     * A convenience methods to return the result of deleting
     * <code>deleteLength</code> characters at <code>offset</code>
     * and inserting <code>replaceString</code> at <code>offset</code>
     * in the current text field.
     * <p>
     *  返回在<code> offset </code>处删除<code> deleteLength </code>字符并在当前文本中<code> offset </code>插入<code> replace
     * String </code>领域。
     * 
     */
    String getReplaceString(int offset, int deleteLength,
                            String replaceString) {
        String string = getFormattedTextField().getText();
        String result;

        result = string.substring(0, offset);
        if (replaceString != null) {
            result += replaceString;
        }
        if (offset + deleteLength < string.length()) {
            result += string.substring(offset + deleteLength);
        }
        return result;
    }

    /*
     * Returns true if the operation described by <code>rh</code> will
     * result in a legal edit.  This may set the <code>value</code>
     * field of <code>rh</code>.
     * <p>
     *  如果<code> rh </code>描述的操作将导致合法编辑,则返回true。这可以设置<code> rh </code>的<code>值</code>字段。
     * 
     */
    boolean isValidEdit(ReplaceHolder rh) {
        if (!getAllowsInvalid()) {
            String newString = getReplaceString(rh.offset, rh.length, rh.text);

            try {
                rh.value = stringToValue(newString);

                return true;
            } catch (ParseException pe) {
                return false;
            }
        }
        return true;
    }

    /**
     * Invokes <code>commitEdit</code> on the JFormattedTextField.
     * <p>
     *  在JFormattedTextField上调用<code> commitEdit </code>。
     * 
     */
    void commitEdit() throws ParseException {
        JFormattedTextField ftf = getFormattedTextField();

        if (ftf != null) {
            ftf.commitEdit();
        }
    }

    /**
     * Pushes the value to the JFormattedTextField if the current value
     * is valid and invokes <code>setEditValid</code> based on the
     * validity of the value.
     * <p>
     *  如果当前值有效,则将值推送到JFormattedTextField,并根据值的有效性调用<code> setEditValid </code>。
     * 
     */
    void updateValue() {
        updateValue(null);
    }

    /**
     * Pushes the <code>value</code> to the editor if we are to
     * commit on edits. If <code>value</code> is null, the current value
     * will be obtained from the text component.
     * <p>
     *  如果我们要在编辑时提交,请将<code>值</code>按到编辑器。如果<code> value </code>为null,则将从文本组件获取当前值。
     * 
     */
    void updateValue(Object value) {
        try {
            if (value == null) {
                String string = getFormattedTextField().getText();

                value = stringToValue(string);
            }

            if (getCommitsOnValidEdit()) {
                commitEdit();
            }
            setEditValid(true);
        } catch (ParseException pe) {
            setEditValid(false);
        }
    }

    /**
     * Returns the next cursor position from offset by incrementing
     * <code>direction</code>. This uses
     * <code>getNextNavigatableChar</code>
     * as well as constraining the location to the max position.
     * <p>
     *  通过递增<code> direction </code>返回从偏移量开始的下一个光标位置。这使用<code> getNextNavigatableChar </code>以及将位置限制在最大位置。
     * 
     */
    int getNextCursorPosition(int offset, int direction) {
        int newOffset = getNextNavigatableChar(offset, direction);
        int max = getFormattedTextField().getDocument().getLength();

        if (!getAllowsInvalid()) {
            if (direction == -1 && offset == newOffset) {
                // Case where hit backspace and only characters before
                // offset are fixed.
                newOffset = getNextNavigatableChar(newOffset, 1);
                if (newOffset >= max) {
                    newOffset = offset;
                }
            }
            else if (direction == 1 && newOffset >= max) {
                // Don't go beyond last editable character.
                newOffset = getNextNavigatableChar(max - 1, -1);
                if (newOffset < max) {
                    newOffset++;
                }
            }
        }
        return newOffset;
    }

    /**
     * Resets the cursor by using getNextCursorPosition.
     * <p>
     *  使用getNextCursorPosition重置光标。
     * 
     */
    void repositionCursor(int offset, int direction) {
        getFormattedTextField().getCaret().setDot(getNextCursorPosition
                                                  (offset, direction));
    }


    /**
     * Finds the next navigable character.
     * <p>
     *  查找下一个可导航字符。
     * 
     */
    int getNextVisualPositionFrom(JTextComponent text, int pos,
                                  Position.Bias bias, int direction,
                                  Position.Bias[] biasRet)
                                           throws BadLocationException {
        int value = text.getUI().getNextVisualPositionFrom(text, pos, bias,
                                                           direction, biasRet);

        if (value == -1) {
            return -1;
        }
        if (!getAllowsInvalid() && (direction == SwingConstants.EAST ||
                                    direction == SwingConstants.WEST)) {
            int last = -1;

            while (!isNavigatable(value) && value != last) {
                last = value;
                value = text.getUI().getNextVisualPositionFrom(
                              text, value, bias, direction,biasRet);
            }
            int max = getFormattedTextField().getDocument().getLength();
            if (last == value || value == max) {
                if (value == 0) {
                    biasRet[0] = Position.Bias.Forward;
                    value = getInitialVisualPosition();
                }
                if (value >= max && max > 0) {
                    // Pending: should not assume forward!
                    biasRet[0] = Position.Bias.Forward;
                    value = getNextNavigatableChar(max - 1, -1) + 1;
                }
            }
        }
        return value;
    }

    /**
     * Returns true if the edit described by <code>rh</code> will result
     * in a legal value.
     * <p>
     *  如果<code> rh </code>描述的编辑将导致合法值,则返回true。
     * 
     */
    boolean canReplace(ReplaceHolder rh) {
        return isValidEdit(rh);
    }

    /**
     * DocumentFilter method, funnels into <code>replace</code>.
     * <p>
     *  DocumentFilter方法,将funnels转换为<code> replace </code>。
     * 
     */
    void replace(DocumentFilter.FilterBypass fb, int offset,
                     int length, String text,
                     AttributeSet attrs) throws BadLocationException {
        ReplaceHolder rh = getReplaceHolder(fb, offset, length, text, attrs);

        replace(rh);
    }

    /**
     * If the edit described by <code>rh</code> is legal, this will
     * return true, commit the edit (if necessary) and update the cursor
     * position.  This forwards to <code>canReplace</code> and
     * <code>isLegalInsertText</code> as necessary to determine if
     * the edit is in fact legal.
     * <p>
     * All of the DocumentFilter methods funnel into here, you should
     * generally only have to override this.
     * <p>
     * 如果<code> rh </code>描述的编辑是合法的,这将返回true,提交编辑(如果需要)并更新光标位置。
     * 这将根据需要转发到<code> canReplace </code>和<code> isLegalInsertText </code>,以确定编辑实际上是否合法。
     * <p>
     *  所有的DocumentFilter方法漏斗到这里,你通常只需要覆盖这个。
     * 
     */
    boolean replace(ReplaceHolder rh) throws BadLocationException {
        boolean valid = true;
        int direction = 1;

        if (rh.length > 0 && (rh.text == null || rh.text.length() == 0) &&
               (getFormattedTextField().getSelectionStart() != rh.offset ||
                   rh.length > 1)) {
            direction = -1;
        }

        if (getOverwriteMode() && rh.text != null &&
            getFormattedTextField().getSelectedText() == null)
        {
            rh.length = Math.min(Math.max(rh.length, rh.text.length()),
                                 rh.fb.getDocument().getLength() - rh.offset);
        }
        if ((rh.text != null && !isLegalInsertText(rh.text)) ||
            !canReplace(rh) ||
            (rh.length == 0 && (rh.text == null || rh.text.length() == 0))) {
            valid = false;
        }
        if (valid) {
            int cursor = rh.cursorPosition;

            rh.fb.replace(rh.offset, rh.length, rh.text, rh.attrs);
            if (cursor == -1) {
                cursor = rh.offset;
                if (direction == 1 && rh.text != null) {
                    cursor = rh.offset + rh.text.length();
                }
            }
            updateValue(rh.value);
            repositionCursor(cursor, direction);
            return true;
        }
        else {
            invalidEdit();
        }
        return false;
    }

    /**
     * NavigationFilter method, subclasses that wish finer control should
     * override this.
     * <p>
     *  NavigationFilter方法,希望更好的控制应该覆盖这个子类。
     * 
     */
    void setDot(NavigationFilter.FilterBypass fb, int dot, Position.Bias bias){
        fb.setDot(dot, bias);
    }

    /**
     * NavigationFilter method, subclasses that wish finer control should
     * override this.
     * <p>
     *  NavigationFilter方法,希望更好的控制应该覆盖这个子类。
     * 
     */
    void moveDot(NavigationFilter.FilterBypass fb, int dot,
                 Position.Bias bias) {
        fb.moveDot(dot, bias);
    }


    /**
     * Returns the ReplaceHolder to track the replace of the specified
     * text.
     * <p>
     *  返回ReplaceHolder以跟踪指定文本的替换。
     * 
     */
    ReplaceHolder getReplaceHolder(DocumentFilter.FilterBypass fb, int offset,
                                   int length, String text,
                                   AttributeSet attrs) {
        if (replaceHolder == null) {
            replaceHolder = new ReplaceHolder();
        }
        replaceHolder.reset(fb, offset, length, text, attrs);
        return replaceHolder;
    }


    /**
     * ReplaceHolder is used to track where insert/remove/replace is
     * going to happen.
     * <p>
     *  ReplaceHolder用于跟踪将在哪里插入/删除/替换。
     * 
     */
    static class ReplaceHolder {
        /** The FilterBypass that was passed to the DocumentFilter method. */
        DocumentFilter.FilterBypass fb;
        /** Offset where the remove/insert is going to occur. */
        int offset;
        /** Length of text to remove. */
        int length;
        /** The text to insert, may be null. */
        String text;
        /** AttributeSet to attach to text, may be null. */
        AttributeSet attrs;
        /** The resulting value, this may never be set. */
        Object value;
        /** Position the cursor should be adjusted from.  If this is -1
         * the cursor position will be adjusted based on the direction of
         * the replace (-1: offset, 1: offset + text.length()), otherwise
         * the cursor position is adusted from this position.
         * <p>
         *  光标位置将根据替换的方向(-1：offset,1：offset + text.length())进行调整,否则光标位置将从该位置开始。
         * 
         */
        int cursorPosition;

        void reset(DocumentFilter.FilterBypass fb, int offset, int length,
                   String text, AttributeSet attrs) {
            this.fb = fb;
            this.offset = offset;
            this.length = length;
            this.text = text;
            this.attrs = attrs;
            this.value = null;
            cursorPosition = -1;
        }
    }


    /**
     * NavigationFilter implementation that calls back to methods with
     * same name in DefaultFormatter.
     * <p>
     *  NavigationFilter实现,它调用回DefaultFormatter中具有相同名称的方法。
     * 
     */
    private class DefaultNavigationFilter extends NavigationFilter
                             implements Serializable {
        public void setDot(FilterBypass fb, int dot, Position.Bias bias) {
            JTextComponent tc = DefaultFormatter.this.getFormattedTextField();
            if (tc.composedTextExists()) {
                // bypass the filter
                fb.setDot(dot, bias);
            } else {
                DefaultFormatter.this.setDot(fb, dot, bias);
            }
        }

        public void moveDot(FilterBypass fb, int dot, Position.Bias bias) {
            JTextComponent tc = DefaultFormatter.this.getFormattedTextField();
            if (tc.composedTextExists()) {
                // bypass the filter
                fb.moveDot(dot, bias);
            } else {
                DefaultFormatter.this.moveDot(fb, dot, bias);
            }
        }

        public int getNextVisualPositionFrom(JTextComponent text, int pos,
                                             Position.Bias bias,
                                             int direction,
                                             Position.Bias[] biasRet)
                                           throws BadLocationException {
            if (text.composedTextExists()) {
                // forward the call to the UI directly
                return text.getUI().getNextVisualPositionFrom(
                        text, pos, bias, direction, biasRet);
            } else {
                return DefaultFormatter.this.getNextVisualPositionFrom(
                        text, pos, bias, direction, biasRet);
            }
        }
    }


    /**
     * DocumentFilter implementation that calls back to the replace
     * method of DefaultFormatter.
     * <p>
     *  DocumentFilter实现,调用回DefaultFormatter的replace方法。
     */
    private class DefaultDocumentFilter extends DocumentFilter implements
                             Serializable {
        public void remove(FilterBypass fb, int offset, int length) throws
                              BadLocationException {
            JTextComponent tc = DefaultFormatter.this.getFormattedTextField();
            if (tc.composedTextExists()) {
                // bypass the filter
                fb.remove(offset, length);
            } else {
                DefaultFormatter.this.replace(fb, offset, length, null, null);
            }
        }

        public void insertString(FilterBypass fb, int offset,
                                 String string, AttributeSet attr) throws
                              BadLocationException {
            JTextComponent tc = DefaultFormatter.this.getFormattedTextField();
            if (tc.composedTextExists() ||
                Utilities.isComposedTextAttributeDefined(attr)) {
                // bypass the filter
                fb.insertString(offset, string, attr);
            } else {
                DefaultFormatter.this.replace(fb, offset, 0, string, attr);
            }
        }

        public void replace(FilterBypass fb, int offset, int length,
                                 String text, AttributeSet attr) throws
                              BadLocationException {
            JTextComponent tc = DefaultFormatter.this.getFormattedTextField();
            if (tc.composedTextExists() ||
                Utilities.isComposedTextAttributeDefined(attr)) {
                // bypass the filter
                fb.replace(offset, length, text, attr);
            } else {
                DefaultFormatter.this.replace(fb, offset, length, text, attr);
            }
        }
    }
}
