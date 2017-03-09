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

import java.awt.event.ActionEvent;
import java.io.*;
import java.text.*;
import java.text.AttributedCharacterIterator.Attribute;
import java.util.*;
import javax.swing.*;

/**
 * <code>InternationalFormatter</code> extends <code>DefaultFormatter</code>,
 * using an instance of <code>java.text.Format</code> to handle the
 * conversion to a String, and the conversion from a String.
 * <p>
 * If <code>getAllowsInvalid()</code> is false, this will ask the
 * <code>Format</code> to format the current text on every edit.
 * <p>
 * You can specify a minimum and maximum value by way of the
 * <code>setMinimum</code> and <code>setMaximum</code> methods. In order
 * for this to work the values returned from <code>stringToValue</code> must be
 * comparable to the min/max values by way of the <code>Comparable</code>
 * interface.
 * <p>
 * Be careful how you configure the <code>Format</code> and the
 * <code>InternationalFormatter</code>, as it is possible to create a
 * situation where certain values can not be input. Consider the date
 * format 'M/d/yy', an <code>InternationalFormatter</code> that is always
 * valid (<code>setAllowsInvalid(false)</code>), is in overwrite mode
 * (<code>setOverwriteMode(true)</code>) and the date 7/1/99. In this
 * case the user will not be able to enter a two digit month or day of
 * month. To avoid this, the format should be 'MM/dd/yy'.
 * <p>
 * If <code>InternationalFormatter</code> is configured to only allow valid
 * values (<code>setAllowsInvalid(false)</code>), every valid edit will result
 * in the text of the <code>JFormattedTextField</code> being completely reset
 * from the <code>Format</code>.
 * The cursor position will also be adjusted as literal characters are
 * added/removed from the resulting String.
 * <p>
 * <code>InternationalFormatter</code>'s behavior of
 * <code>stringToValue</code> is  slightly different than that of
 * <code>DefaultTextFormatter</code>, it does the following:
 * <ol>
 *   <li><code>parseObject</code> is invoked on the <code>Format</code>
 *       specified by <code>setFormat</code>
 *   <li>If a Class has been set for the values (<code>setValueClass</code>),
 *       supers implementation is invoked to convert the value returned
 *       from <code>parseObject</code> to the appropriate class.
 *   <li>If a <code>ParseException</code> has not been thrown, and the value
 *       is outside the min/max a <code>ParseException</code> is thrown.
 *   <li>The value is returned.
 * </ol>
 * <code>InternationalFormatter</code> implements <code>stringToValue</code>
 * in this manner so that you can specify an alternate Class than
 * <code>Format</code> may return.
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
 *  <code> InternationalFormatter </code>扩展<code> DefaultFormatter </code>,使用<code> java.text.Format </code>
 * 的实例来处理对String的转换以及从String的转换。
 * <p>
 *  如果<code> getAllowsInvalid()</code>为false,这将要求<code> Format </code>在每次编辑时设置当前文本的格式。
 * <p>
 *  您可以通过<code> setMinimum </code>和<code> setMaximum </code>方法指定最小值和最大值。
 * 为了使其工作,从<code> stringToValue </code>返回的值必须通过<code> Comparable </code>接口与最小/最大值相当。
 * <p>
 *  请小心配置<code>格式</code>和<code> InternationalFormatter </code>,因为可能会创建无法输入某些值的情况。
 * 考虑日期格式"M / d / yy",一个总是有效的<code> InternationalFormatter </code>(<code> setAllowsInvalid(false)</code>
 * ),处于覆盖模式(<code> setOverwriteMode )</code>)和日期7/1/99。
 *  请小心配置<code>格式</code>和<code> InternationalFormatter </code>,因为可能会创建无法输入某些值的情况。
 * 在这种情况下,用户将无法输入两位数的月份或月份。为避免这种情况,格式应为"MM / dd / yy"。
 * <p>
 * 如果<code> InternationalFormatter </code>配置为只允许有效值(<code> setAllowsInvalid(false)</code>),每个有效的编辑将导致<code>
 *  JFormattedTextField </code>从<code> Format </code>重置。
 * 还将调整光标位置,因为从生成的字符串添加/删除文字字符。
 * <p>
 *  <code> InternationalFormatter </code>的行为<code> stringToValue </code>与<code> DefaultTextFormatter </code>
 * 略有不同,它执行以下操作：。
 * <ol>
 *  <li>如果已为值设置了类(<code> setValueClass),则在<code> setFormat </code> <li>指定的<code> Format </code> </code>)
 * ,supers实现被调用来将从<code> parseObject </code>返回的值转换为适当的类。
 *  <li>如果未抛出<code> ParseException </code>,并且值超出最小/最大值,则抛出<code> ParseException </code>。 <li>返回值。
 * </ol>
 *  <code> InternationalFormatter </code>以这种方式实现<code> stringToValue </code>,以便可以指定一个替代类,而不是<code> Forma
 * t </code>可能返回。
 * <p>
 * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see java.text.Format
 * @see java.lang.Comparable
 *
 * @since 1.4
 */
public class InternationalFormatter extends DefaultFormatter {
    /**
     * Used by <code>getFields</code>.
     * <p>
     *  由<code> getFields </code>使用。
     * 
     */
    private static final Format.Field[] EMPTY_FIELD_ARRAY =new Format.Field[0];

    /**
     * Object used to handle the conversion.
     * <p>
     *  用于处理转换的对象。
     * 
     */
    private Format format;
    /**
     * Can be used to impose a maximum value.
     * <p>
     *  可用于施加最大值。
     * 
     */
    private Comparable max;
    /**
     * Can be used to impose a minimum value.
     * <p>
     *  可以用于施加最小值。
     * 
     */
    private Comparable min;

    /**
     * <code>InternationalFormatter</code>'s behavior is dicatated by a
     * <code>AttributedCharacterIterator</code> that is obtained from
     * the <code>Format</code>. On every edit, assuming
     * allows invalid is false, the <code>Format</code> instance is invoked
     * with <code>formatToCharacterIterator</code>. A <code>BitSet</code> is
     * also kept upto date with the non-literal characters, that is
     * for every index in the <code>AttributedCharacterIterator</code> an
     * entry in the bit set is updated based on the return value from
     * <code>isLiteral(Map)</code>. <code>isLiteral(int)</code> then uses
     * this cached information.
     * <p>
     * If allowsInvalid is false, every edit results in resetting the complete
     * text of the JTextComponent.
     * <p>
     * InternationalFormatterFilter can also provide two actions suitable for
     * incrementing and decrementing. To enable this a subclass must
     * override <code>getSupportsIncrement</code> to return true, and
     * override <code>adjustValue</code> to handle the changing of the
     * value. If you want to support changing the value outside of
     * the valid FieldPositions, you will need to override
     * <code>canIncrement</code>.
     * <p>
     *  <code> InternationalFormatter </code>的行为由从<code> Format </code>获得的<code> AttributedCharacterIterator
     *  </code>分隔。
     * 在每次编辑时,假设允许invalid为false,则使用<code> formatToCharacterIterator </code>来调用<code> Format </code>实例。
     *  <code> BitSet </code>也保持最新的非字面字符,即对于<code> AttributedCharacterIterator </code>中的每个索引,位集合中的条目基于来自<code>
     *  isLiteral(Map)</code>。
     * 在每次编辑时,假设允许invalid为false,则使用<code> formatToCharacterIterator </code>来调用<code> Format </code>实例。
     *  <code> isLiteral(int)</code>然后使用这个缓存的信息。
     * <p>
     *  如果allowedInvalid为false,则每次编辑都将重置JTextComponent的完整文本。
     * <p>
     * InternationalFormatterFilter也可以提供适合递增和递减的两个动作。
     * 要启用它,子类必须重写<code> getSupportsIncrement </code>以返回true,并覆盖<code> adjustValue </code>以处理值的更改。
     * 如果要支持更改有效FieldPositions之外的值,则需要覆盖<code> canIncrement </code>。
     * 
     */
    /**
     * A bit is set for every index identified in the
     * AttributedCharacterIterator that is not considered decoration.
     * This should only be used if validMask is true.
     * <p>
     *  对于在AttributedCharacterIterator中标识的每个不被认为是装饰的索引,设置一个位。这应该只有当validMask为真时才使用。
     * 
     */
    private transient BitSet literalMask;
    /**
     * Used to iterate over characters.
     * <p>
     *  用于遍历字符。
     * 
     */
    private transient AttributedCharacterIterator iterator;
    /**
     * True if the Format was able to convert the value to a String and
     * back.
     * <p>
     *  如果格式能够将值转换为字符串并返回,则为True。
     * 
     */
    private transient boolean validMask;
    /**
     * Current value being displayed.
     * <p>
     *  显示当前值。
     * 
     */
    private transient String string;
    /**
     * If true, DocumentFilter methods are unconditionally allowed,
     * and no checking is done on their values. This is used when
     * incrementing/decrementing via the actions.
     * <p>
     *  如果为true,则DocumentFilter方法被无条件地允许,并且不对其值进行检查。当通过操作递增/递减时使用。
     * 
     */
    private transient boolean ignoreDocumentMutate;


    /**
     * Creates an <code>InternationalFormatter</code> with no
     * <code>Format</code> specified.
     * <p>
     *  创建一个未指定<code> Format </code>的<code> InternationalFormatter </code>。
     * 
     */
    public InternationalFormatter() {
        setOverwriteMode(false);
    }

    /**
     * Creates an <code>InternationalFormatter</code> with the specified
     * <code>Format</code> instance.
     *
     * <p>
     *  使用指定的<code> Format </code>实例创建<code> InternationalFormatter </code>。
     * 
     * 
     * @param format Format instance used for converting from/to Strings
     */
    public InternationalFormatter(Format format) {
        this();
        setFormat(format);
    }

    /**
     * Sets the format that dictates the legal values that can be edited
     * and displayed.
     *
     * <p>
     *  设置指定可编辑和显示的合法值的格式。
     * 
     * 
     * @param format <code>Format</code> instance used for converting
     * from/to Strings
     */
    public void setFormat(Format format) {
        this.format = format;
    }

    /**
     * Returns the format that dictates the legal values that can be edited
     * and displayed.
     *
     * <p>
     *  返回指定可编辑和显示的合法值的格式。
     * 
     * 
     * @return Format instance used for converting from/to Strings
     */
    public Format getFormat() {
        return format;
    }

    /**
     * Sets the minimum permissible value. If the <code>valueClass</code> has
     * not been specified, and <code>minimum</code> is non null, the
     * <code>valueClass</code> will be set to that of the class of
     * <code>minimum</code>.
     *
     * <p>
     *  设置最小允许值。
     * 如果未指定<code> valueClass </code>,并且<code> minimum </code>为非null,则<code> valueClass </code>将设置为<code> mi
     * nimum </code>。
     *  设置最小允许值。
     * 
     * 
     * @param minimum Minimum legal value that can be input
     * @see #setValueClass
     */
    public void setMinimum(Comparable minimum) {
        if (getValueClass() == null && minimum != null) {
            setValueClass(minimum.getClass());
        }
        min = minimum;
    }

    /**
     * Returns the minimum permissible value.
     *
     * <p>
     *  返回最小允许值。
     * 
     * 
     * @return Minimum legal value that can be input
     */
    public Comparable getMinimum() {
        return min;
    }

    /**
     * Sets the maximum permissible value. If the <code>valueClass</code> has
     * not been specified, and <code>max</code> is non null, the
     * <code>valueClass</code> will be set to that of the class of
     * <code>max</code>.
     *
     * <p>
     * 设置最大允许值。
     * 如果未指定<code> valueClass </code>,且<code> max </code>为非空,则<code> valueClass </code>将设置为<code> max </code>
     * 。
     * 设置最大允许值。
     * 
     * 
     * @param max Maximum legal value that can be input
     * @see #setValueClass
     */
    public void setMaximum(Comparable max) {
        if (getValueClass() == null && max != null) {
            setValueClass(max.getClass());
        }
        this.max = max;
    }

    /**
     * Returns the maximum permissible value.
     *
     * <p>
     *  返回最大允许值。
     * 
     * 
     * @return Maximum legal value that can be input
     */
    public Comparable getMaximum() {
        return max;
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
     *  将<code> DefaultFormatter </code>安装到特定的<code> JFormattedTextField </code>。
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
        updateMaskIfNecessary();
        // invoked again as the mask should now be valid.
        positionCursorAtInitialLocation();
    }

    /**
     * Returns a String representation of the Object <code>value</code>.
     * This invokes <code>format</code> on the current <code>Format</code>.
     *
     * <p>
     *  返回Object <code>值</code>的String表示形式。这将调用当前<code> Format </code>上的<code>格式</code>。
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
        Format f = getFormat();

        if (f == null) {
            return value.toString();
        }
        return f.format(value);
    }

    /**
     * Returns the <code>Object</code> representation of the
     * <code>String</code> <code>text</code>.
     *
     * <p>
     * 返回<code> String </code> <code> text </code>的<code> Object </code>表示形式。
     * 
     * 
     * @param text <code>String</code> to convert
     * @return <code>Object</code> representation of text
     * @throws ParseException if there is an error in the conversion
     */
    public Object stringToValue(String text) throws ParseException {
        Object value = stringToValue(text, getFormat());

        // Convert to the value class if the Value returned from the
        // Format does not match.
        if (value != null && getValueClass() != null &&
                             !getValueClass().isInstance(value)) {
            value = super.stringToValue(value.toString());
        }
        try {
            if (!isValidValue(value, true)) {
                throw new ParseException("Value not within min/max range", 0);
            }
        } catch (ClassCastException cce) {
            throw new ParseException("Class cast exception comparing values: "
                                     + cce, 0);
        }
        return value;
    }

    /**
     * Returns the <code>Format.Field</code> constants associated with
     * the text at <code>offset</code>. If <code>offset</code> is not
     * a valid location into the current text, this will return an
     * empty array.
     *
     * <p>
     *  返回与<code> offset </code>上的文本相关联的<code> Format.Field </code>常量。
     * 如果<code> offset </code>不是当前文本中的有效位置,则将返回一个空数组。
     * 
     * 
     * @param offset offset into text to be examined
     * @return Format.Field constants associated with the text at the
     *         given position.
     */
    public Format.Field[] getFields(int offset) {
        if (getAllowsInvalid()) {
            // This will work if the currently edited value is valid.
            updateMask();
        }

        Map<Attribute, Object> attrs = getAttributes(offset);

        if (attrs != null && attrs.size() > 0) {
            ArrayList<Attribute> al = new ArrayList<Attribute>();

            al.addAll(attrs.keySet());
            return al.toArray(EMPTY_FIELD_ARRAY);
        }
        return EMPTY_FIELD_ARRAY;
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
        InternationalFormatter formatter = (InternationalFormatter)super.
                                           clone();

        formatter.literalMask = null;
        formatter.iterator = null;
        formatter.validMask = false;
        formatter.string = null;
        return formatter;
    }

    /**
     * If <code>getSupportsIncrement</code> returns true, this returns
     * two Actions suitable for incrementing/decrementing the value.
     * <p>
     *  如果<code> getSupportsIncrement </code>返回true,则返回两个适用于递增/递减值的操作。
     * 
     */
    protected Action[] getActions() {
        if (getSupportsIncrement()) {
            return new Action[] { new IncrementAction("increment", 1),
                                  new IncrementAction("decrement", -1) };
        }
        return null;
    }

    /**
     * Invokes <code>parseObject</code> on <code>f</code>, returning
     * its value.
     * <p>
     *  调用<code> f </code>上的<code> parseObject </code>,返回其值。
     * 
     */
    Object stringToValue(String text, Format f) throws ParseException {
        if (f == null) {
            return text;
        }
        return f.parseObject(text);
    }

    /**
     * Returns true if <code>value</code> is between the min/max.
     *
     * <p>
     *  如果<code> value </code>在min / max之间,则返回true。
     * 
     * 
     * @param wantsCCE If false, and a ClassCastException is thrown in
     *                 comparing the values, the exception is consumed and
     *                 false is returned.
     */
    boolean isValidValue(Object value, boolean wantsCCE) {
        Comparable min = getMinimum();

        try {
            if (min != null && min.compareTo(value) > 0) {
                return false;
            }
        } catch (ClassCastException cce) {
            if (wantsCCE) {
                throw cce;
            }
            return false;
        }

        Comparable max = getMaximum();
        try {
            if (max != null && max.compareTo(value) < 0) {
                return false;
            }
        } catch (ClassCastException cce) {
            if (wantsCCE) {
                throw cce;
            }
            return false;
        }
        return true;
    }

    /**
     * Returns a Set of the attribute identifiers at <code>index</code>.
     * <p>
     *  返回<code> index </code>的属性标识符集合。
     * 
     */
    Map<Attribute, Object> getAttributes(int index) {
        if (isValidMask()) {
            AttributedCharacterIterator iterator = getIterator();

            if (index >= 0 && index <= iterator.getEndIndex()) {
                iterator.setIndex(index);
                return iterator.getAttributes();
            }
        }
        return null;
    }


    /**
     * Returns the start of the first run that contains the attribute
     * <code>id</code>. This will return <code>-1</code> if the attribute
     * can not be found.
     * <p>
     *  返回包含属性<code> id </code>的第一个运行的开始。如果找不到属性,将返回<code> -1 </code>。
     * 
     */
    int getAttributeStart(AttributedCharacterIterator.Attribute id) {
        if (isValidMask()) {
            AttributedCharacterIterator iterator = getIterator();

            iterator.first();
            while (iterator.current() != CharacterIterator.DONE) {
                if (iterator.getAttribute(id) != null) {
                    return iterator.getIndex();
                }
                iterator.next();
            }
        }
        return -1;
    }

    /**
     * Returns the <code>AttributedCharacterIterator</code> used to
     * format the last value.
     * <p>
     *  返回用于格式化最后一个值的<code> AttributedCharacterIterator </code>。
     * 
     */
    AttributedCharacterIterator getIterator() {
        return iterator;
    }

    /**
     * Updates the AttributedCharacterIterator and bitset, if necessary.
     * <p>
     *  如有必要,更新AttributedCharacterIterator和bitset。
     * 
     */
    void updateMaskIfNecessary() {
        if (!getAllowsInvalid() && (getFormat() != null)) {
            if (!isValidMask()) {
                updateMask();
            }
            else {
                String newString = getFormattedTextField().getText();

                if (!newString.equals(string)) {
                    updateMask();
                }
            }
        }
    }

    /**
     * Updates the AttributedCharacterIterator by invoking
     * <code>formatToCharacterIterator</code> on the <code>Format</code>.
     * If this is successful,
     * <code>updateMask(AttributedCharacterIterator)</code>
     * is then invoked to update the internal bitmask.
     * <p>
     *  通过调用<code> Format </code>上的<code> formatToCharacterIterator </code>更新AttributedCharacterIterator。
     * 如果这是成功的,然后调用<code> updateMask(AttributedCharacterIterator)</code>来更新内部位掩码。
     * 
     */
    void updateMask() {
        if (getFormat() != null) {
            Document doc = getFormattedTextField().getDocument();

            validMask = false;
            if (doc != null) {
                try {
                    string = doc.getText(0, doc.getLength());
                } catch (BadLocationException ble) {
                    string = null;
                }
                if (string != null) {
                    try {
                        Object value = stringToValue(string);
                        AttributedCharacterIterator iterator = getFormat().
                                  formatToCharacterIterator(value);

                        updateMask(iterator);
                    }
                    catch (ParseException pe) {}
                    catch (IllegalArgumentException iae) {}
                    catch (NullPointerException npe) {}
                }
            }
        }
    }

    /**
     * Returns the number of literal characters before <code>index</code>.
     * <p>
     *  返回<code> index </code>之前的文字字符数。
     * 
     */
    int getLiteralCountTo(int index) {
        int lCount = 0;

        for (int counter = 0; counter < index; counter++) {
            if (isLiteral(counter)) {
                lCount++;
            }
        }
        return lCount;
    }

    /**
     * Returns true if the character at index is a literal, that is
     * not editable.
     * <p>
     *  如果索引处的字符是字面值,那么返回true,即不可编辑。
     * 
     */
    boolean isLiteral(int index) {
        if (isValidMask() && index < string.length()) {
            return literalMask.get(index);
        }
        return false;
    }

    /**
     * Returns the literal character at index.
     * <p>
     *  返回索引处的文字字符。
     * 
     */
    char getLiteral(int index) {
        if (isValidMask() && string != null && index < string.length()) {
            return string.charAt(index);
        }
        return (char)0;
    }

    /**
     * Returns true if the character at offset is navigable too. This
     * is implemented in terms of <code>isLiteral</code>, subclasses
     * may wish to provide different behavior.
     * <p>
     * 如果偏移处的字符也可导航,则返回true。这是根据<code> isLiteral </code>实现的,子类可能希望提供不同的行为。
     * 
     */
    boolean isNavigatable(int offset) {
        return !isLiteral(offset);
    }

    /**
     * Overriden to update the mask after invoking supers implementation.
     * <p>
     *  覆盖在调用supers实现后更新掩码。
     * 
     */
    void updateValue(Object value) {
        super.updateValue(value);
        updateMaskIfNecessary();
    }

    /**
     * Overriden to unconditionally allow the replace if
     * ignoreDocumentMutate is true.
     * <p>
     *  如果ignoreDocumentMutate为true,则覆盖到无条件地允许替换。
     * 
     */
    void replace(DocumentFilter.FilterBypass fb, int offset,
                     int length, String text,
                     AttributeSet attrs) throws BadLocationException {
        if (ignoreDocumentMutate) {
            fb.replace(offset, length, text, attrs);
            return;
        }
        super.replace(fb, offset, length, text, attrs);
    }

    /**
     * Returns the index of the next non-literal character starting at
     * index. If index is not a literal, it will be returned.
     *
     * <p>
     *  返回从索引开始的下一个非文字字符的索引。如果index不是文字,它将被返回。
     * 
     * 
     * @param direction Amount to increment looking for non-literal
     */
    private int getNextNonliteralIndex(int index, int direction) {
        int max = getFormattedTextField().getDocument().getLength();

        while (index >= 0 && index < max) {
            if (isLiteral(index)) {
                index += direction;
            }
            else {
                return index;
            }
        }
        return (direction == -1) ? 0 : max;
    }

    /**
     * Overriden in an attempt to honor the literals.
     * <p>If we do not allow invalid values and are in overwrite mode, this
     * {@code rh.length} is corrected as to preserve trailing literals.
     * If not in overwrite mode, and there is text to insert it is
     * inserted at the next non literal index going forward.  If there
     * is only text to remove, it is removed from the next non literal
     * index going backward.
     * <p>
     *  覆盖以试图兑现文字。 <p>如果我们不允许无效值并且处于覆盖模式,则会对此{@code rh.length}进行更正,以保留尾随文字。
     * 如果不是在覆盖模式下,并且有要插入的文本,它将在下一个非文字索引上插入。如果只有要删除的文本,它将从下一个非文本索引向后移除。
     * 
     */
    boolean canReplace(ReplaceHolder rh) {
        if (!getAllowsInvalid()) {
            String text = rh.text;
            int tl = (text != null) ? text.length() : 0;
            JTextComponent c = getFormattedTextField();

            if (tl == 0 && rh.length == 1 && c.getSelectionStart() != rh.offset) {
                // Backspace, adjust to actually delete next non-literal.
                rh.offset = getNextNonliteralIndex(rh.offset, -1);
            } else if (getOverwriteMode()) {
                int pos = rh.offset;
                int textPos = pos;
                boolean overflown = false;

                for (int i = 0; i < rh.length; i++) {
                    while (isLiteral(pos)) pos++;
                    if (pos >= string.length()) {
                        pos = textPos;
                        overflown = true;
                        break;
                    }
                    textPos = ++pos;
                }
                if (overflown || c.getSelectedText() == null) {
                    rh.length = pos - rh.offset;
                }
            }
            else if (tl > 0) {
                // insert (or insert and remove)
                rh.offset = getNextNonliteralIndex(rh.offset, 1);
            }
            else {
                // remove only
                rh.offset = getNextNonliteralIndex(rh.offset, -1);
            }
            ((ExtendedReplaceHolder)rh).endOffset = rh.offset;
            ((ExtendedReplaceHolder)rh).endTextLength = (rh.text != null) ?
                                                    rh.text.length() : 0;
        }
        else {
            ((ExtendedReplaceHolder)rh).endOffset = rh.offset;
            ((ExtendedReplaceHolder)rh).endTextLength = (rh.text != null) ?
                                                    rh.text.length() : 0;
        }
        boolean can = super.canReplace(rh);
        if (can && !getAllowsInvalid()) {
            ((ExtendedReplaceHolder)rh).resetFromValue(this);
        }
        return can;
    }

    /**
     * When in !allowsInvalid mode the text is reset on every edit, thus
     * supers implementation will position the cursor at the wrong position.
     * As such, this invokes supers implementation and then invokes
     * <code>repositionCursor</code> to correctly reset the cursor.
     * <p>
     *  当in！允许无效模式时,文本在每次编辑时被重置,因此,超级实施将把光标定位在错误的位置。
     * 因此,这将调用supers实现,然后调用<code> repositionCursor </code>以正确重置游标。
     * 
     */
    boolean replace(ReplaceHolder rh) throws BadLocationException {
        int start = -1;
        int direction = 1;
        int literalCount = -1;

        if (rh.length > 0 && (rh.text == null || rh.text.length() == 0) &&
               (getFormattedTextField().getSelectionStart() != rh.offset ||
                   rh.length > 1)) {
            direction = -1;
        }
        if (!getAllowsInvalid()) {
            if ((rh.text == null || rh.text.length() == 0) && rh.length > 0) {
                // remove
                start = getFormattedTextField().getSelectionStart();
            }
            else {
                start = rh.offset;
            }
            literalCount = getLiteralCountTo(start);
        }
        if (super.replace(rh)) {
            if (start != -1) {
                int end = ((ExtendedReplaceHolder)rh).endOffset;

                end += ((ExtendedReplaceHolder)rh).endTextLength;
                repositionCursor(literalCount, end, direction);
            }
            else {
                start = ((ExtendedReplaceHolder)rh).endOffset;
                if (direction == 1) {
                    start += ((ExtendedReplaceHolder)rh).endTextLength;
                }
                repositionCursor(start, direction);
            }
            return true;
        }
        return false;
    }

    /**
     * Repositions the cursor. <code>startLiteralCount</code> gives
     * the number of literals to the start of the deleted range, end
     * gives the ending location to adjust from, direction gives
     * the direction relative to <code>end</code> to position the
     * cursor from.
     * <p>
     *  重新定位游标。
     *  <code> startLiteralCount </code>给出了删除范围开始处的文字数,end给出了从中调整的结束位置,方向给出了相对于<code> end </code>的方向来定位光标。
     * 
     */
    private void repositionCursor(int startLiteralCount, int end,
                                  int direction)  {
        int endLiteralCount = getLiteralCountTo(end);

        if (endLiteralCount != end) {
            end -= startLiteralCount;
            for (int counter = 0; counter < end; counter++) {
                if (isLiteral(counter)) {
                    end++;
                }
            }
        }
        repositionCursor(end, 1 /*direction*/);
    }

    /**
     * Returns the character from the mask that has been buffered
     * at <code>index</code>.
     * <p>
     *  }}
     * 
     *  / **返回在<code> index </code>缓冲的掩码中的字符。
     * 
     */
    char getBufferedChar(int index) {
        if (isValidMask()) {
            if (string != null && index < string.length()) {
                return string.charAt(index);
            }
        }
        return (char)0;
    }

    /**
     * Returns true if the current mask is valid.
     * <p>
     *  如果当前掩码有效,则返回true。
     * 
     */
    boolean isValidMask() {
        return validMask;
    }

    /**
     * Returns true if <code>attributes</code> is null or empty.
     * <p>
     * 如果<code> attributes </code>为空或为空,则返回true。
     * 
     */
    boolean isLiteral(Map attributes) {
        return ((attributes == null) || attributes.size() == 0);
    }

    /**
     * Updates the interal bitset from <code>iterator</code>. This will
     * set <code>validMask</code> to true if <code>iterator</code> is
     * non-null.
     * <p>
     *  更新<code> iterator </code>中的interal bitset。
     * 如果<code> iterator </code>为非空,这将会将<code> validMask </code>设置为true。
     * 
     */
    private void updateMask(AttributedCharacterIterator iterator) {
        if (iterator != null) {
            validMask = true;
            this.iterator = iterator;

            // Update the literal mask
            if (literalMask == null) {
                literalMask = new BitSet();
            }
            else {
                for (int counter = literalMask.length() - 1; counter >= 0;
                     counter--) {
                    literalMask.clear(counter);
                }
            }

            iterator.first();
            while (iterator.current() != CharacterIterator.DONE) {
                Map attributes = iterator.getAttributes();
                boolean set = isLiteral(attributes);
                int start = iterator.getIndex();
                int end = iterator.getRunLimit();

                while (start < end) {
                    if (set) {
                        literalMask.set(start);
                    }
                    else {
                        literalMask.clear(start);
                    }
                    start++;
                }
                iterator.setIndex(start);
            }
        }
    }

    /**
     * Returns true if <code>field</code> is non-null.
     * Subclasses that wish to allow incrementing to happen outside of
     * the known fields will need to override this.
     * <p>
     *  如果<code>字段</code>为非空,则返回true。希望允许递增发生在已知字段之外的子类将需要覆盖此。
     * 
     */
    boolean canIncrement(Object field, int cursorPosition) {
        return (field != null);
    }

    /**
     * Selects the fields identified by <code>attributes</code>.
     * <p>
     *  选择由<code> attributes </code>标识的字段。
     * 
     */
    void selectField(Object f, int count) {
        AttributedCharacterIterator iterator = getIterator();

        if (iterator != null &&
                        (f instanceof AttributedCharacterIterator.Attribute)) {
            AttributedCharacterIterator.Attribute field =
                                   (AttributedCharacterIterator.Attribute)f;

            iterator.first();
            while (iterator.current() != CharacterIterator.DONE) {
                while (iterator.getAttribute(field) == null &&
                       iterator.next() != CharacterIterator.DONE);
                if (iterator.current() != CharacterIterator.DONE) {
                    int limit = iterator.getRunLimit(field);

                    if (--count <= 0) {
                        getFormattedTextField().select(iterator.getIndex(),
                                                       limit);
                        break;
                    }
                    iterator.setIndex(limit);
                    iterator.next();
                }
            }
        }
    }

    /**
     * Returns the field that will be adjusted by adjustValue.
     * <p>
     *  返回将由adjustValue调整的字段。
     * 
     */
    Object getAdjustField(int start, Map attributes) {
        return null;
    }

    /**
     * Returns the number of occurrences of <code>f</code> before
     * the location <code>start</code> in the current
     * <code>AttributedCharacterIterator</code>.
     * <p>
     *  返回当前<code> AttributedCharacterIterator </code>中位置<code> start </code>之前<code> f </code>的出现次数。
     * 
     */
    private int getFieldTypeCountTo(Object f, int start) {
        AttributedCharacterIterator iterator = getIterator();
        int count = 0;

        if (iterator != null &&
                    (f instanceof AttributedCharacterIterator.Attribute)) {
            AttributedCharacterIterator.Attribute field =
                                   (AttributedCharacterIterator.Attribute)f;

            iterator.first();
            while (iterator.getIndex() < start) {
                while (iterator.getAttribute(field) == null &&
                       iterator.next() != CharacterIterator.DONE);
                if (iterator.current() != CharacterIterator.DONE) {
                    iterator.setIndex(iterator.getRunLimit(field));
                    iterator.next();
                    count++;
                }
                else {
                    break;
                }
            }
        }
        return count;
    }

    /**
     * Subclasses supporting incrementing must override this to handle
     * the actual incrementing. <code>value</code> is the current value,
     * <code>attributes</code> gives the field the cursor is in (may be
     * null depending upon <code>canIncrement</code>) and
     * <code>direction</code> is the amount to increment by.
     * <p>
     *  支持增量的子类必须覆盖此来处理实际的增量。
     *  <code> value </code>是当前值,<code> attributes </code>给出游标所在的字段(根据<code> canIncrement </code>可以为null) co
     * de>是要增加的量。
     *  支持增量的子类必须覆盖此来处理实际的增量。
     * 
     */
    Object adjustValue(Object value, Map attributes, Object field,
                           int direction) throws
                      BadLocationException, ParseException {
        return null;
    }

    /**
     * Returns false, indicating InternationalFormatter does not allow
     * incrementing of the value. Subclasses that wish to support
     * incrementing/decrementing the value should override this and
     * return true. Subclasses should also override
     * <code>adjustValue</code>.
     * <p>
     *  返回false,表示InternationalFormatter不允许值的递增。希望支持递增/递减值的子类应该覆盖此并返回true。子类也应覆盖<code> adjustValue </code>。
     * 
     */
    boolean getSupportsIncrement() {
        return false;
    }

    /**
     * Resets the value of the JFormattedTextField to be
     * <code>value</code>.
     * <p>
     *  将JFormattedTextField的值重置为<code> value </code>。
     * 
     */
    void resetValue(Object value) throws BadLocationException, ParseException {
        Document doc = getFormattedTextField().getDocument();
        String string = valueToString(value);

        try {
            ignoreDocumentMutate = true;
            doc.remove(0, doc.getLength());
            doc.insertString(0, string, null);
        } finally {
            ignoreDocumentMutate = false;
        }
        updateValue(value);
    }

    /**
     * Subclassed to update the internal representation of the mask after
     * the default read operation has completed.
     * <p>
     *  子类化以在默认读取操作完成后更新掩码的内部表示。
     * 
     */
    private void readObject(ObjectInputStream s)
        throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        updateMaskIfNecessary();
    }


    /**
     * Overriden to return an instance of <code>ExtendedReplaceHolder</code>.
     * <p>
     *  覆盖以返回<code> ExtendedReplaceHolder </code>的实例。
     * 
     */
    ReplaceHolder getReplaceHolder(DocumentFilter.FilterBypass fb, int offset,
                                   int length, String text,
                                   AttributeSet attrs) {
        if (replaceHolder == null) {
            replaceHolder = new ExtendedReplaceHolder();
        }
        return super.getReplaceHolder(fb, offset, length, text, attrs);
    }


    /**
     * As InternationalFormatter replaces the complete text on every edit,
     * ExtendedReplaceHolder keeps track of the offset and length passed
     * into canReplace.
     * <p>
     * 由于InternationalFormatter在每次编辑时替换了完整的文本,ExtendedReplaceHolder会跟踪传递到canReplace中的偏移量和长度。
     * 
     */
    static class ExtendedReplaceHolder extends ReplaceHolder {
        /** Offset of the insert/remove. This may differ from offset in
        /* <p>
        /* 
         * that if !allowsInvalid the text is replaced on every edit. */
        int endOffset;
        /** Length of the text. This may differ from text.length in
        /* <p>
        /* 
         * that if !allowsInvalid the text is replaced on every edit. */
        int endTextLength;

        /**
         * Resets the region to delete to be the complete document and
         * the text from invoking valueToString on the current value.
         * <p>
         *  将要删除的区域重置为完整文档,并将文本从当前值调用valueToString。
         * 
         */
        void resetFromValue(InternationalFormatter formatter) {
            // Need to reset the complete string as Format's result can
            // be completely different.
            offset = 0;
            try {
                text = formatter.valueToString(value);
            } catch (ParseException pe) {
                // Should never happen, otherwise canReplace would have
                // returned value.
                text = "";
            }
            length = fb.getDocument().getLength();
        }
    }


    /**
     * IncrementAction is used to increment the value by a certain amount.
     * It calls into <code>adjustValue</code> to handle the actual
     * incrementing of the value.
     * <p>
     *  IncrementAction用于将值增加一定量。它调用<code> adjustValue </code>来处理该值的实际递增。
     */
    private class IncrementAction extends AbstractAction {
        private int direction;

        IncrementAction(String name, int direction) {
            super(name);
            this.direction = direction;
        }

        public void actionPerformed(ActionEvent ae) {

            if (getFormattedTextField().isEditable()) {
                if (getAllowsInvalid()) {
                    // This will work if the currently edited value is valid.
                    updateMask();
                }

                boolean validEdit = false;

                if (isValidMask()) {
                    int start = getFormattedTextField().getSelectionStart();

                    if (start != -1) {
                        AttributedCharacterIterator iterator = getIterator();

                        iterator.setIndex(start);

                        Map attributes = iterator.getAttributes();
                        Object field = getAdjustField(start, attributes);

                        if (canIncrement(field, start)) {
                            try {
                                Object value = stringToValue(
                                        getFormattedTextField().getText());
                                int fieldTypeCount = getFieldTypeCountTo(
                                        field, start);

                                value = adjustValue(value, attributes,
                                        field, direction);
                                if (value != null && isValidValue(value, false)) {
                                    resetValue(value);
                                    updateMask();

                                    if (isValidMask()) {
                                        selectField(field, fieldTypeCount);
                                    }
                                    validEdit = true;
                                }
                            }
                            catch (ParseException pe) { }
                            catch (BadLocationException ble) { }
                        }
                    }
                }
                if (!validEdit) {
                    invalidEdit();
                }
            }
        }
    }
}
