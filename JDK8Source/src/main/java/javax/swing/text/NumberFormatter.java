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

import java.lang.reflect.*;
import java.text.*;
import java.util.*;
import sun.reflect.misc.ReflectUtil;
import sun.swing.SwingUtilities2;

/**
 * <code>NumberFormatter</code> subclasses <code>InternationalFormatter</code>
 * adding special behavior for numbers. Among the specializations are
 * (these are only used if the <code>NumberFormatter</code> does not display
 * invalid numbers, for example, <code>setAllowsInvalid(false)</code>):
 * <ul>
 *   <li>Pressing +/- (- is determined from the
 *       <code>DecimalFormatSymbols</code> associated with the
 *       <code>DecimalFormat</code>) in any field but the exponent
 *       field will attempt to change the sign of the number to
 *       positive/negative.
 *   <li>Pressing +/- (- is determined from the
 *       <code>DecimalFormatSymbols</code> associated with the
 *       <code>DecimalFormat</code>) in the exponent field will
 *       attempt to change the sign of the exponent to positive/negative.
 * </ul>
 * <p>
 * If you are displaying scientific numbers, you may wish to turn on
 * overwrite mode, <code>setOverwriteMode(true)</code>. For example:
 * <pre>
 * DecimalFormat decimalFormat = new DecimalFormat("0.000E0");
 * NumberFormatter textFormatter = new NumberFormatter(decimalFormat);
 * textFormatter.setOverwriteMode(true);
 * textFormatter.setAllowsInvalid(false);
 * </pre>
 * <p>
 * If you are going to allow the user to enter decimal
 * values, you should either force the DecimalFormat to contain at least
 * one decimal (<code>#.0###</code>), or allow the value to be invalid
 * <code>setAllowsInvalid(true)</code>. Otherwise users may not be able to
 * input decimal values.
 * <p>
 * <code>NumberFormatter</code> provides slightly different behavior to
 * <code>stringToValue</code> than that of its superclass. If you have
 * specified a Class for values, {@link #setValueClass}, that is one of
 * of <code>Integer</code>, <code>Long</code>, <code>Float</code>,
 * <code>Double</code>, <code>Byte</code> or <code>Short</code> and
 * the Format's <code>parseObject</code> returns an instance of
 * <code>Number</code>, the corresponding instance of the value class
 * will be created using the constructor appropriate for the primitive
 * type the value class represents. For example:
 * <code>setValueClass(Integer.class)</code> will cause the resulting
 * value to be created via
 * <code>new Integer(((Number)formatter.parseObject(string)).intValue())</code>.
 * This is typically useful if you
 * wish to set a min/max value as the various <code>Number</code>
 * implementations are generally not comparable to each other. This is also
 * useful if for some reason you need a specific <code>Number</code>
 * implementation for your values.
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
 *  <code> NumberFormatter </code> subclasses <code> InternationalFormatter </code>为数字添加特殊行为。
 * 专业化之中(这些仅在<code> NumberFormatter </code>不显示无效数字时使用,例如<code> setAllowsInvalid(false)</code>)：。
 * <ul>
 *  <li>在任何字段中按+/-( - 由与<code> DecimalFormat </code>相关联的<code> DecimalFormatSymbols </code>确定),但指数字段将尝试将
 * 数字的符号更改为正负。
 *  <li>按+/-( - 由指数字段中与<code> DecimalFormat </code>相关联的<code> DecimalFormatSymbols </code>确定)将尝试将指数的符号更改
 * 为正/负。
 * </ul>
 * <p>
 *  如果显示科学数字,您可能希望打开覆盖模式,<code> setOverwriteMode(true)</code>。例如：
 * <pre>
 *  DecimalFormat decimalFormat = new DecimalFormat("0.000E0"); NumberFormatter textFormatter = new Numb
 * erFormatter(decimalFormat); textFormatter.setOverwriteMode(true); textFormatter.setAllowsInvalid(fals
 * e);。
 * </pre>
 * <p>
 * 如果要允许用户输入十进制值,应该强制DecimalFormat包含至少一个小数(<code>#。
 * 0 ### </code>),或者允许该值无效<code > setAllowsInvalid(true)</code>。否则用户可能无法输入十进制值。
 * <p>
 *  <code> NumberFormatter </code>对<code> stringToValue </code>提供了与其超类略有不同的行为。
 * 如果您为值指定了类别,{@link #setValueClass},即<code> Integer </code>,<code> Long </code>,<code> Float </code> > 
 * Double </code>,<code> Byte </code>或<code> Short </code>,格式的<code> parseObject </code>返回<code> Number 
 * </code>的实例,将使用适用于值类所表示的基本类型的构造函数创建值类的实例。
 *  <code> NumberFormatter </code>对<code> stringToValue </code>提供了与其超类略有不同的行为。
 * 例如：<code> setValueClass(Integer.class)</code>会导致通过<code> new Integer((Number)formatter.parseObject(st
 * ring))创建结果值intValue >。
 *  <code> NumberFormatter </code>对<code> stringToValue </code>提供了与其超类略有不同的行为。
 * 这通常是有用的,如果你想设置一个最小/最大值,因为各种<code> Number </code>实现通常不能相互比较。
 * 如果由于某种原因,您需要为您的值需要一个特定的<code> Number </code>实现,这也是有用的。
 * <p>
 * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @since 1.4
 */
public class NumberFormatter extends InternationalFormatter {
    /** The special characters from the Format instance. */
    private String specialChars;

    /**
     * Creates a <code>NumberFormatter</code> with the a default
     * <code>NumberFormat</code> instance obtained from
     * <code>NumberFormat.getNumberInstance()</code>.
     * <p>
     *  使用从<code> NumberFormat.getNumberInstance()</code>获取的默认<code> NumberFormat </code>实例创建<code> NumberFo
     * rmatter </code>。
     * 
     */
    public NumberFormatter() {
        this(NumberFormat.getNumberInstance());
    }

    /**
     * Creates a NumberFormatter with the specified Format instance.
     *
     * <p>
     *  使用指定的Format实例创建NumberFormatter。
     * 
     * 
     * @param format Format used to dictate legal values
     */
    public NumberFormatter(NumberFormat format) {
        super(format);
        setFormat(format);
        setAllowsInvalid(true);
        setCommitsOnValidEdit(false);
        setOverwriteMode(false);
    }

    /**
     * Sets the format that dictates the legal values that can be edited
     * and displayed.
     * <p>
     * If you have used the nullary constructor the value of this property
     * will be determined for the current locale by way of the
     * <code>NumberFormat.getNumberInstance()</code> method.
     *
     * <p>
     *  设置指定可编辑和显示的合法值的格式。
     * <p>
     *  如果已经使用nullary构造函数,则将通过<code> NumberFormat.getNumberInstance()</code>方法为当前语言环境确定此属性的值。
     * 
     * 
     * @param format NumberFormat instance used to dictate legal values
     */
    public void setFormat(Format format) {
        super.setFormat(format);

        DecimalFormatSymbols dfs = getDecimalFormatSymbols();

        if (dfs != null) {
            StringBuilder sb = new StringBuilder();

            sb.append(dfs.getCurrencySymbol());
            sb.append(dfs.getDecimalSeparator());
            sb.append(dfs.getGroupingSeparator());
            sb.append(dfs.getInfinity());
            sb.append(dfs.getInternationalCurrencySymbol());
            sb.append(dfs.getMinusSign());
            sb.append(dfs.getMonetaryDecimalSeparator());
            sb.append(dfs.getNaN());
            sb.append(dfs.getPercent());
            sb.append('+');
            specialChars = sb.toString();
        }
        else {
            specialChars = "";
        }
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
        Object value = f.parseObject(text);

        return convertValueToValueClass(value, getValueClass());
    }

    /**
     * Converts the passed in value to the passed in class. This only
     * works if <code>valueClass</code> is one of <code>Integer</code>,
     * <code>Long</code>, <code>Float</code>, <code>Double</code>,
     * <code>Byte</code> or <code>Short</code> and <code>value</code>
     * is an instanceof <code>Number</code>.
     * <p>
     *  将传入的值转换为传入的类。
     * 这仅在<code> valueClass </code>是<code> Integer </code>,<code> Long </code>,<code> Float </code>,<code> D
     * ouble </code> ,<code> Byte </code>或<code> Short </code>和<code> value </code>是<code> Number </code>的一个
     * 实例。
     *  将传入的值转换为传入的类。
     * 
     */
    private Object convertValueToValueClass(Object value, Class valueClass) {
        if (valueClass != null && (value instanceof Number)) {
            Number numberValue = (Number)value;
            if (valueClass == Integer.class) {
                return Integer.valueOf(numberValue.intValue());
            }
            else if (valueClass == Long.class) {
                return Long.valueOf(numberValue.longValue());
            }
            else if (valueClass == Float.class) {
                return Float.valueOf(numberValue.floatValue());
            }
            else if (valueClass == Double.class) {
                return Double.valueOf(numberValue.doubleValue());
            }
            else if (valueClass == Byte.class) {
                return Byte.valueOf(numberValue.byteValue());
            }
            else if (valueClass == Short.class) {
                return Short.valueOf(numberValue.shortValue());
            }
        }
        return value;
    }

    /**
     * Returns the character that is used to toggle to positive values.
     * <p>
     *  返回用于切换到正值的字符。
     * 
     */
    private char getPositiveSign() {
        return '+';
    }

    /**
     * Returns the character that is used to toggle to negative values.
     * <p>
     *  返回用于切换为负值的字符。
     * 
     */
    private char getMinusSign() {
        DecimalFormatSymbols dfs = getDecimalFormatSymbols();

        if (dfs != null) {
            return dfs.getMinusSign();
        }
        return '-';
    }

    /**
     * Returns the character that is used to toggle to negative values.
     * <p>
     *  返回用于切换为负值的字符。
     * 
     */
    private char getDecimalSeparator() {
        DecimalFormatSymbols dfs = getDecimalFormatSymbols();

        if (dfs != null) {
            return dfs.getDecimalSeparator();
        }
        return '.';
    }

    /**
     * Returns the DecimalFormatSymbols from the Format instance.
     * <p>
     * 从Format实例返回DecimalFormatSymbols。
     * 
     */
    private DecimalFormatSymbols getDecimalFormatSymbols() {
        Format f = getFormat();

        if (f instanceof DecimalFormat) {
            return ((DecimalFormat)f).getDecimalFormatSymbols();
        }
        return null;
    }

    /**
     * Subclassed to return false if <code>text</code> contains in an invalid
     * character to insert, that is, it is not a digit
     * (<code>Character.isDigit()</code>) and
     * not one of the characters defined by the DecimalFormatSymbols.
     * <p>
     *  如果<code> text </code>包含无效字符以插入,即子字符不是数字(<code> Character.isDigit()</code>)而不是某个字符由DecimalFormatSymbo
     * ls。
     * 
     */
    boolean isLegalInsertText(String text) {
        if (getAllowsInvalid()) {
            return true;
        }
        for (int counter = text.length() - 1; counter >= 0; counter--) {
            char aChar = text.charAt(counter);

            if (!Character.isDigit(aChar) &&
                           specialChars.indexOf(aChar) == -1){
                return false;
            }
        }
        return true;
    }

    /**
     * Subclassed to treat the decimal separator, grouping separator,
     * exponent symbol, percent, permille, currency and sign as literals.
     * <p>
     *  子类处理小数分隔符,分隔符,指数符号,百分比,permille,货币和符号作为文字。
     * 
     */
    boolean isLiteral(Map attrs) {
        if (!super.isLiteral(attrs)) {
            if (attrs == null) {
                return false;
            }
            int size = attrs.size();

            if (attrs.get(NumberFormat.Field.GROUPING_SEPARATOR) != null) {
                size--;
                if (attrs.get(NumberFormat.Field.INTEGER) != null) {
                    size--;
                }
            }
            if (attrs.get(NumberFormat.Field.EXPONENT_SYMBOL) != null) {
                size--;
            }
            if (attrs.get(NumberFormat.Field.PERCENT) != null) {
                size--;
            }
            if (attrs.get(NumberFormat.Field.PERMILLE) != null) {
                size--;
            }
            if (attrs.get(NumberFormat.Field.CURRENCY) != null) {
                size--;
            }
            if (attrs.get(NumberFormat.Field.SIGN) != null) {
                size--;
            }
            return size == 0;
        }
        return true;
    }

    /**
     * Subclassed to make the decimal separator navigable, as well
     * as making the character between the integer field and the next
     * field navigable.
     * <p>
     *  子类使小数分隔符可导航,以及使整数字段和下一个字段之间的字符可导航。
     * 
     */
    boolean isNavigatable(int index) {
        if (!super.isNavigatable(index)) {
            // Don't skip the decimal, it causes wierd behavior
            return getBufferedChar(index) == getDecimalSeparator();
        }
        return true;
    }

    /**
     * Returns the first <code>NumberFormat.Field</code> starting
     * <code>index</code> incrementing by <code>direction</code>.
     * <p>
     *  返回从<code> index </code>开始的<code>方向</code>开始的第一个<code> NumberFormat.Field </code>。
     * 
     */
    private NumberFormat.Field getFieldFrom(int index, int direction) {
        if (isValidMask()) {
            int max = getFormattedTextField().getDocument().getLength();
            AttributedCharacterIterator iterator = getIterator();

            if (index >= max) {
                index += direction;
            }
            while (index >= 0 && index < max) {
                iterator.setIndex(index);

                Map attrs = iterator.getAttributes();

                if (attrs != null && attrs.size() > 0) {
                    for (Object key : attrs.keySet()) {
                        if (key instanceof NumberFormat.Field) {
                            return (NumberFormat.Field)key;
                        }
                    }
                }
                index += direction;
            }
        }
        return null;
    }

    /**
     * Overriden to toggle the value if the positive/minus sign
     * is inserted.
     * <p>
     *  如果插入正/负符号,则覆盖以切换值。
     * 
     */
    void replace(DocumentFilter.FilterBypass fb, int offset, int length,
                String string, AttributeSet attr) throws BadLocationException {
        if (!getAllowsInvalid() && length == 0 && string != null &&
            string.length() == 1 &&
            toggleSignIfNecessary(fb, offset, string.charAt(0))) {
            return;
        }
        super.replace(fb, offset, length, string, attr);
    }

    /**
     * Will change the sign of the integer or exponent field if
     * <code>aChar</code> is the positive or minus sign. Returns
     * true if a sign change was attempted.
     * <p>
     *  如果<code> aChar </code>是正号或负号,将更改整数或指数字段的符号。如果尝试进行符号更改,则返回true。
     * 
     */
    private boolean toggleSignIfNecessary(DocumentFilter.FilterBypass fb,
                                              int offset, char aChar) throws
                              BadLocationException {
        if (aChar == getMinusSign() || aChar == getPositiveSign()) {
            NumberFormat.Field field = getFieldFrom(offset, -1);
            Object newValue;

            try {
                if (field == null ||
                    (field != NumberFormat.Field.EXPONENT &&
                     field != NumberFormat.Field.EXPONENT_SYMBOL &&
                     field != NumberFormat.Field.EXPONENT_SIGN)) {
                    newValue = toggleSign((aChar == getPositiveSign()));
                }
                else {
                    // exponent
                    newValue = toggleExponentSign(offset, aChar);
                }
                if (newValue != null && isValidValue(newValue, false)) {
                    int lc = getLiteralCountTo(offset);
                    String string = valueToString(newValue);

                    fb.remove(0, fb.getDocument().getLength());
                    fb.insertString(0, string, null);
                    updateValue(newValue);
                    repositionCursor(getLiteralCountTo(offset) -
                                     lc + offset, 1);
                    return true;
                }
            } catch (ParseException pe) {
                invalidEdit();
            }
        }
        return false;
    }

    /**
     * Invoked to toggle the sign. For this to work the value class
     * must have a single arg constructor that takes a String.
     * <p>
     *  调用以切换符号。为了使这个工作的值类必须有一个单一的arg构造函数接受一个字符串。
     * 
     */
    private Object toggleSign(boolean positive) throws ParseException {
        Object value = stringToValue(getFormattedTextField().getText());

        if (value != null) {
            // toString isn't localized, so that using +/- should work
            // correctly.
            String string = value.toString();

            if (string != null && string.length() > 0) {
                if (positive) {
                    if (string.charAt(0) == '-') {
                        string = string.substring(1);
                    }
                }
                else {
                    if (string.charAt(0) == '+') {
                        string = string.substring(1);
                    }
                    if (string.length() > 0 && string.charAt(0) != '-') {
                        string = "-" + string;
                    }
                }
                if (string != null) {
                    Class<?> valueClass = getValueClass();

                    if (valueClass == null) {
                        valueClass = value.getClass();
                    }
                    try {
                        ReflectUtil.checkPackageAccess(valueClass);
                        SwingUtilities2.checkAccess(valueClass.getModifiers());
                        Constructor cons = valueClass.getConstructor(
                                              new Class[] { String.class });
                        if (cons != null) {
                            SwingUtilities2.checkAccess(cons.getModifiers());
                            return cons.newInstance(new Object[]{string});
                        }
                    } catch (Throwable ex) { }
                }
            }
        }
        return null;
    }

    /**
     * Invoked to toggle the sign of the exponent (for scientific
     * numbers).
     * <p>
     *  调用以切换指数的符号(对于科学数字)。
     */
    private Object toggleExponentSign(int offset, char aChar) throws
                             BadLocationException, ParseException {
        String string = getFormattedTextField().getText();
        int replaceLength = 0;
        int loc = getAttributeStart(NumberFormat.Field.EXPONENT_SIGN);

        if (loc >= 0) {
            replaceLength = 1;
            offset = loc;
        }
        if (aChar == getPositiveSign()) {
            string = getReplaceString(offset, replaceLength, null);
        }
        else {
            string = getReplaceString(offset, replaceLength,
                                      new String(new char[] { aChar }));
        }
        return stringToValue(string);
    }
}
