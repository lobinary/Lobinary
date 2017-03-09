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

import java.io.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

/**
 * <code>MaskFormatter</code> is used to format and edit strings. The behavior
 * of a <code>MaskFormatter</code> is controlled by way of a String mask
 * that specifies the valid characters that can be contained at a particular
 * location in the <code>Document</code> model. The following characters can
 * be specified:
 *
 * <table border=1 summary="Valid characters and their descriptions">
 * <tr>
 *    <th>Character&nbsp;</th>
 *    <th><p style="text-align:left">Description</p></th>
 * </tr>
 * <tr>
 *    <td>#</td>
 *    <td>Any valid number, uses <code>Character.isDigit</code>.</td>
 * </tr>
 * <tr>
 *    <td>'</td>
 *    <td>Escape character, used to escape any of the
 *       special formatting characters.</td>
 * </tr>
 * <tr>
 *    <td>U</td><td>Any character (<code>Character.isLetter</code>). All
 *        lowercase letters are mapped to upper case.</td>
 * </tr>
 * <tr><td>L</td><td>Any character (<code>Character.isLetter</code>). All
 *        upper case letters are mapped to lower case.</td>
 * </tr>
 * <tr><td>A</td><td>Any character or number (<code>Character.isLetter</code>
 *       or <code>Character.isDigit</code>)</td>
 * </tr>
 * <tr><td>?</td><td>Any character
 *        (<code>Character.isLetter</code>).</td>
 * </tr>
 * <tr><td>*</td><td>Anything.</td></tr>
 * <tr><td>H</td><td>Any hex character (0-9, a-f or A-F).</td></tr>
 * </table>
 *
 * <p>
 * Typically characters correspond to one char, but in certain languages this
 * is not the case. The mask is on a per character basis, and will thus
 * adjust to fit as many chars as are needed.
 * <p>
 * You can further restrict the characters that can be input by the
 * <code>setInvalidCharacters</code> and <code>setValidCharacters</code>
 * methods. <code>setInvalidCharacters</code> allows you to specify
 * which characters are not legal. <code>setValidCharacters</code> allows
 * you to specify which characters are valid. For example, the following
 * code block is equivalent to a mask of '0xHHH' with no invalid/valid
 * characters:
 * <pre>
 * MaskFormatter formatter = new MaskFormatter("0x***");
 * formatter.setValidCharacters("0123456789abcdefABCDEF");
 * </pre>
 * <p>
 * When initially formatting a value if the length of the string is
 * less than the length of the mask, two things can happen. Either
 * the placeholder string will be used, or the placeholder character will
 * be used. Precedence is given to the placeholder string. For example:
 * <pre>
 *   MaskFormatter formatter = new MaskFormatter("###-####");
 *   formatter.setPlaceholderCharacter('_');
 *   formatter.getDisplayValue(tf, "123");
 * </pre>
 * <p>
 * Would result in the string '123-____'. If
 * <code>setPlaceholder("555-1212")</code> was invoked '123-1212' would
 * result. The placeholder String is only used on the initial format,
 * on subsequent formats only the placeholder character will be used.
 * <p>
 * If a <code>MaskFormatter</code> is configured to only allow valid characters
 * (<code>setAllowsInvalid(false)</code>) literal characters will be skipped as
 * necessary when editing. Consider a <code>MaskFormatter</code> with
 * the mask "###-####" and current value "555-1212". Using the right
 * arrow key to navigate through the field will result in (| indicates the
 * position of the caret):
 * <pre>
 *   |555-1212
 *   5|55-1212
 *   55|5-1212
 *   555-|1212
 *   555-1|212
 * </pre>
 * The '-' is a literal (non-editable) character, and is skipped.
 * <p>
 * Similar behavior will result when editing. Consider inserting the string
 * '123-45' and '12345' into the <code>MaskFormatter</code> in the
 * previous example. Both inserts will result in the same String,
 * '123-45__'. When <code>MaskFormatter</code>
 * is processing the insert at character position 3 (the '-'), two things can
 * happen:
 * <ol>
 *   <li>If the inserted character is '-', it is accepted.
 *   <li>If the inserted character matches the mask for the next non-literal
 *       character, it is accepted at the new location.
 *   <li>Anything else results in an invalid edit
 * </ol>
 * <p>
 * By default <code>MaskFormatter</code> will not allow invalid edits, you can
 * change this with the <code>setAllowsInvalid</code> method, and will
 * commit edits on valid edits (use the <code>setCommitsOnValidEdit</code> to
 * change this).
 * <p>
 * By default, <code>MaskFormatter</code> is in overwrite mode. That is as
 * characters are typed a new character is not inserted, rather the character
 * at the current location is replaced with the newly typed character. You
 * can change this behavior by way of the method <code>setOverwriteMode</code>.
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
 *  <code> MaskFormatter </code>用于格式化和编辑字符串。
 * 通过指定可以包含在<code> Document </code>模型中特定位置的有效字符的String掩码来控制<code> MaskFormatter </code>的行为。可以指定以下字符：。
 * 
 * <table border=1 summary="Valid characters and their descriptions">
 * <tr>
 *  <th>字符</th> <th> <p style ="text-align：left">描述</p> </th>
 * </tr>
 * <tr>
 *  <td>#</td> <td>任何有效的数字,使用<code> Character.isDigit </code>。</td>
 * </tr>
 * <tr>
 *  <td>'</td> <td>转义字符,用于转义任何特殊格式字符。</td>
 * </tr>
 * <tr>
 *  <td> U </td> <td>任何字符(<code> Character.isLetter </code>)。所有小写​​字母都映射为大写。</td>
 * </tr>
 *  <tr> <td> L </td> <td>任何字符(<code> Character.isLetter </code>)。所有大写字母都映射为小写。</td>
 * </tr>
 *  <tr> <td> A </td> <td>任何字符或数字(<code> Character.isLetter </code>或<code> Character.isDigit </code>)</td>
 * 。
 * </tr>
 *  <tr> <td>?</td> <td>任何字符(<code> Character.isLetter </code>)。</td>
 * </tr>
 *  </td>任何十六进制字符(0-9,af或AF)</td> </td> </td> 。</td> </tr>
 * </table>
 * 
 * <p>
 *  通常字符对应一个字符,但在某些语言中不是这样。掩码基于每个字符,因此将调整以适合所需要的字符数。
 * <p>
 * 您可以进一步限制可以通过<code> setInvalidCharacters </code>和<code> setValidCharacters </code>方法输入的字符。
 *  <code> setInvalidCharacters </code>允许您指定哪些字符不合法。 <code> setValidCharacters </code>允许您指定哪些字符有效。
 * 例如,以下代码块等效于没有无效/有效字符的"0xHHH"掩码：。
 * <pre>
 *  MaskFormatter formatter = new MaskFormatter("0x ***"); formatter.setValidCharacters("0123456789abcde
 * fABCDEF");。
 * </pre>
 * <p>
 *  当最初格式化值时,如果字符串的长度小于掩码的长度,可能会发生两种情况。将使用占位符字符串,或使用占位符字符。优先级赋予占位符字符串。例如：
 * <pre>
 *  MaskFormatter formatter = new MaskFormatter("###  -  ####"); formatter.setPlaceholderCharacter('_');
 *  formatter.getDisplayValue(tf,"123");。
 * </pre>
 * <p>
 *  将导致字符串'123 -____'。如果调用<code> setPlaceholder("555-1212")</code>,将会导致"123-1212"。
 * 占位符字符串仅用于初始格式,在后续格式中,仅使用占位符字符。
 * <p>
 * 如果将<code> MaskFormatter </code>配置为仅允许有效字符(<code> setAllowsInvalid(false)</code>),则编辑时将根据需要跳过文字字符。
 * 考虑使用掩码"###  -  ####"和当前值"555-1212"的<code> MaskFormatter </code>。使用右箭头键在字段中导航将导致(|表示插入符的位置)：。
 * <pre>
 *  | 555-1212 5 | 55-1212 55 | 5-1212 555- | 1212 555-1 | 212
 * </pre>
 *  " - "是一个文字(不可编辑)字符,并被跳过。
 * <p>
 *  编辑时将产生类似的行为。考虑将字符串'123-45'和'12345'插入到上面示例中的<code> MaskFormatter </code>中。这两个插入将产生相同的字符串,'123-45__'。
 * 当<code> MaskFormatter </code>正在处理字符位置3处的插入(' - ')时,可能会发生以下两种情况：。
 * <ol>
 *  <li>如果插入的字符是" - ",则会被接受。 <li>如果插入的字符与下一个非文字字符的掩码匹配,则在新位置接受。 <li>其他任何操作都会导致无效的修改
 * </ol>
 * <p>
 *  默认情况下,<code> MaskFormatter </code>不会允许无效的编辑,你可以用<code> setAllowsInvalid </code>方法改变它,并且会对有效的编辑提交编辑(使
 * 用<code> setCommitsOnValidEdit </code>改变这个)。
 * <p>
 * 默认情况下,<code> MaskFormatter </code>处于覆盖模式。也就是说,当键入字符时,不插入新字符,而是将当前位置处的字符替换为新键入的字符。
 * 您可以通过方法<code> setOverwriteMode </code>更改此行为。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @since 1.4
 */
public class MaskFormatter extends DefaultFormatter {
    // Potential values in mask.
    private static final char DIGIT_KEY = '#';
    private static final char LITERAL_KEY = '\'';
    private static final char UPPERCASE_KEY = 'U';
    private static final char LOWERCASE_KEY = 'L';
    private static final char ALPHA_NUMERIC_KEY = 'A';
    private static final char CHARACTER_KEY = '?';
    private static final char ANYTHING_KEY = '*';
    private static final char HEX_KEY = 'H';

    private static final MaskCharacter[] EmptyMaskChars = new MaskCharacter[0];

    /** The user specified mask. */
    private String mask;

    private transient MaskCharacter[] maskChars;

    /** List of valid characters. */
    private String validCharacters;

    /** List of invalid characters. */
    private String invalidCharacters;

    /** String used for the passed in value if it does not completely
    /* <p>
    /* 
     * fill the mask. */
    private String placeholderString;

    /** String used to represent characters not present. */
    private char placeholder;

    /** Indicates if the value contains the literal characters. */
    private boolean containsLiteralChars;


    /**
     * Creates a MaskFormatter with no mask.
     * <p>
     *  创建一个没有掩码的MaskFormatter。
     * 
     */
    public MaskFormatter() {
        setAllowsInvalid(false);
        containsLiteralChars = true;
        maskChars = EmptyMaskChars;
        placeholder = ' ';
    }

    /**
     * Creates a <code>MaskFormatter</code> with the specified mask.
     * A <code>ParseException</code>
     * will be thrown if <code>mask</code> is an invalid mask.
     *
     * <p>
     *  使用指定的掩码创建<code> MaskFormatter </code>。
     * 如果<code> mask </code>是一个无效的掩码,则会抛出<code> ParseException </code>。
     * 
     * 
     * @throws ParseException if mask does not contain valid mask characters
     */
    public MaskFormatter(String mask) throws ParseException {
        this();
        setMask(mask);
    }

    /**
     * Sets the mask dictating the legal characters.
     * This will throw a <code>ParseException</code> if <code>mask</code> is
     * not valid.
     *
     * <p>
     *  设置指示法定字符的掩码。如果<code>掩码</code>无效,则会抛出<code> ParseException </code>。
     * 
     * 
     * @throws ParseException if mask does not contain valid mask characters
     */
    public void setMask(String mask) throws ParseException {
        this.mask = mask;
        updateInternalMask();
    }

    /**
     * Returns the formatting mask.
     *
     * <p>
     *  返回格式化掩码。
     * 
     * 
     * @return Mask dictating legal character values.
     */
    public String getMask() {
        return mask;
    }

    /**
     * Allows for further restricting of the characters that can be input.
     * Only characters specified in the mask, not in the
     * <code>invalidCharacters</code>, and in
     * <code>validCharacters</code> will be allowed to be input. Passing
     * in null (the default) implies the valid characters are only bound
     * by the mask and the invalid characters.
     *
     * <p>
     *  允许进一步限制可以输入的字符。
     * 只允许在掩码中指定的字符,而不是在<code> invalidCharacters </code>和<code> validCharacters </code>中指定的字符。
     * 传入null(默认值)意味着有效字符仅由掩码和无效字符绑定。
     * 
     * 
     * @param validCharacters If non-null, specifies legal characters.
     */
    public void setValidCharacters(String validCharacters) {
        this.validCharacters = validCharacters;
    }

    /**
     * Returns the valid characters that can be input.
     *
     * <p>
     *  返回可以输入的有效字符。
     * 
     * 
     * @return Legal characters
     */
    public String getValidCharacters() {
        return validCharacters;
    }

    /**
     * Allows for further restricting of the characters that can be input.
     * Only characters specified in the mask, not in the
     * <code>invalidCharacters</code>, and in
     * <code>validCharacters</code> will be allowed to be input. Passing
     * in null (the default) implies the valid characters are only bound
     * by the mask and the valid characters.
     *
     * <p>
     * 允许进一步限制可以输入的字符。
     * 只允许在掩码中指定的字符,而不是在<code> invalidCharacters </code>和<code> validCharacters </code>中指定的字符。
     * 传入null(默认值)意味着有效字符仅由掩码和有效字符绑定。
     * 
     * 
     * @param invalidCharacters If non-null, specifies illegal characters.
     */
    public void setInvalidCharacters(String invalidCharacters) {
        this.invalidCharacters = invalidCharacters;
    }

    /**
     * Returns the characters that are not valid for input.
     *
     * <p>
     *  返回对输入无效的字符。
     * 
     * 
     * @return illegal characters.
     */
    public String getInvalidCharacters() {
        return invalidCharacters;
    }

    /**
     * Sets the string to use if the value does not completely fill in
     * the mask. A null value implies the placeholder char should be used.
     *
     * <p>
     *  如果值未完全填充掩码,请设置要使用的字符串。空值意味着应使用占位符char。
     * 
     * 
     * @param placeholder String used when formatting if the value does not
     *        completely fill the mask
     */
    public void setPlaceholder(String placeholder) {
        this.placeholderString = placeholder;
    }

    /**
     * Returns the String to use if the value does not completely fill
     * in the mask.
     *
     * <p>
     *  返回要使用的字符串,如果值不完全填充掩码。
     * 
     * 
     * @return String used when formatting if the value does not
     *        completely fill the mask
     */
    public String getPlaceholder() {
        return placeholderString;
    }

    /**
     * Sets the character to use in place of characters that are not present
     * in the value, ie the user must fill them in. The default value is
     * a space.
     * <p>
     * This is only applicable if the placeholder string has not been
     * specified, or does not completely fill in the mask.
     *
     * <p>
     *  设置要用于替换值中不存在的字符的字符,即用户必须填充它们。默认值为空格。
     * <p>
     *  这仅适用于未指定占位符字符串或未完全填充掩码的情况。
     * 
     * 
     * @param placeholder Character used when formatting if the value does not
     *        completely fill the mask
     */
    public void setPlaceholderCharacter(char placeholder) {
        this.placeholder = placeholder;
    }

    /**
     * Returns the character to use in place of characters that are not present
     * in the value, ie the user must fill them in.
     *
     * <p>
     *  返回要使用的字符,以替换值中不存在的字符,即用户必须填写它们。
     * 
     * 
     * @return Character used when formatting if the value does not
     *        completely fill the mask
     */
    public char getPlaceholderCharacter() {
        return placeholder;
    }

    /**
     * If true, the returned value and set value will also contain the literal
     * characters in mask.
     * <p>
     * For example, if the mask is <code>'(###) ###-####'</code>, the
     * current value is <code>'(415) 555-1212'</code>, and
     * <code>valueContainsLiteralCharacters</code> is
     * true <code>stringToValue</code> will return
     * <code>'(415) 555-1212'</code>. On the other hand, if
     * <code>valueContainsLiteralCharacters</code> is false,
     * <code>stringToValue</code> will return <code>'4155551212'</code>.
     *
     * <p>
     *  如果为true,则返回的值和设置值也将包含掩码中的文字字符。
     * <p>
     *  例如,如果掩码是<code>'(###)###  -  ####'</code>,当前值为<code>'(415)555-1212'</code>并且<code> valueContainsLiter
     * alCharacters </code>为true <code> stringToValue </code>将返回<code>'(415)555-1212'</code>。
     * 另一方面,如果<code> valueContainsLiteralCharacters </code>为false,<code> stringToValue </code>将返回<code>'4155
     * 551212'</code>。
     * 
     * 
     * @param containsLiteralChars Used to indicate if literal characters in
     *        mask should be returned in stringToValue
     */
    public void setValueContainsLiteralCharacters(
                        boolean containsLiteralChars) {
        this.containsLiteralChars = containsLiteralChars;
    }

    /**
     * Returns true if <code>stringToValue</code> should return literal
     * characters in the mask.
     *
     * <p>
     * 返回true如果<code> stringToValue </code>应该返回掩码中的文字字符。
     * 
     * 
     * @return True if literal characters in mask should be returned in
     *         stringToValue
     */
    public boolean getValueContainsLiteralCharacters() {
        return containsLiteralChars;
    }

    /**
     * Parses the text, returning the appropriate Object representation of
     * the String <code>value</code>. This strips the literal characters as
     * necessary and invokes supers <code>stringToValue</code>, so that if
     * you have specified a value class (<code>setValueClass</code>) an
     * instance of it will be created. This will throw a
     * <code>ParseException</code> if the value does not match the current
     * mask.  Refer to {@link #setValueContainsLiteralCharacters} for details
     * on how literals are treated.
     *
     * <p>
     *  解析文本,返回字符串<code>值</code>的相应对象表示形式。
     * 这会根据需要删除文字字符,并调用<code> stringToValue </code>,以便如果您指定了一个值类(<code> setValueClass </code>),它的实例将被创建。
     * 如果值与当前掩码不匹配,则会抛出<code> ParseException </code>。
     * 有关如何处理文字的详细信息,请参阅{@link #setValueContainsLiteralCharacters}。
     * 
     * 
     * @throws ParseException if there is an error in the conversion
     * @param value String to convert
     * @see #setValueContainsLiteralCharacters
     * @return Object representation of text
     */
    public Object stringToValue(String value) throws ParseException {
        return stringToValue(value, true);
    }

    /**
     * Returns a String representation of the Object <code>value</code>
     * based on the mask.  Refer to
     * {@link #setValueContainsLiteralCharacters} for details
     * on how literals are treated.
     *
     * <p>
     *  根据掩码返回Object <code>值</code>的String表示形式。有关如何处理文字的详细信息,请参阅{@link #setValueContainsLiteralCharacters}。
     * 
     * 
     * @throws ParseException if there is an error in the conversion
     * @param value Value to convert
     * @see #setValueContainsLiteralCharacters
     * @return String representation of value
     */
    public String valueToString(Object value) throws ParseException {
        String sValue = (value == null) ? "" : value.toString();
        StringBuilder result = new StringBuilder();
        String placeholder = getPlaceholder();
        int[] valueCounter = { 0 };

        append(result, sValue, valueCounter, placeholder, maskChars);
        return result.toString();
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
     * 如果在将当前值转换为字符串时存在<code> ParseException </code>,则会将文本设置为空字符串,并将<code> JFormattedTextField </code>标记为无效状
     * 态。
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
        // valueToString doesn't throw, but stringToValue does, need to
        // update the editValid state appropriately
        if (ftf != null) {
            Object value = ftf.getValue();

            try {
                stringToValue(valueToString(value));
            } catch (ParseException pe) {
                setEditValid(false);
            }
        }
    }

    /**
     * Actual <code>stringToValue</code> implementation.
     * If <code>completeMatch</code> is true, the value must exactly match
     * the mask, on the other hand if <code>completeMatch</code> is false
     * the string must match the mask or the placeholder string.
     * <p>
     *  实际的<code> stringToValue </code>实现。
     * 如果<code> completeMatch </code>为true,则该值必须与掩码完全匹配,另一方面,如果<code> completeMatch </code>为false,则字符串必须匹配掩码
     * 或占位符字符串。
     *  实际的<code> stringToValue </code>实现。
     * 
     */
    private Object stringToValue(String value, boolean completeMatch) throws
                         ParseException {
        int errorOffset;

        if ((errorOffset = getInvalidOffset(value, completeMatch)) == -1) {
            if (!getValueContainsLiteralCharacters()) {
                value = stripLiteralChars(value);
            }
            return super.stringToValue(value);
        }
        throw new ParseException("stringToValue passed invalid value",
                                 errorOffset);
    }

    /**
     * Returns -1 if the passed in string is valid, otherwise the index of
     * the first bogus character is returned.
     * <p>
     *  如果传入的字符串有效,则返回-1,否则返回第一个伪字符的索引。
     * 
     */
    private int getInvalidOffset(String string, boolean completeMatch) {
        int iLength = string.length();

        if (iLength != getMaxLength()) {
            // trivially false
            return iLength;
        }
        for (int counter = 0, max = string.length(); counter < max; counter++){
            char aChar = string.charAt(counter);

            if (!isValidCharacter(counter, aChar) &&
                (completeMatch || !isPlaceholder(counter, aChar))) {
                return counter;
            }
        }
        return -1;
    }

    /**
     * Invokes <code>append</code> on the mask characters in
     * <code>mask</code>.
     * <p>
     *  在<code> mask </code>中的掩码字符上调用<code>追加</code>。
     * 
     */
    private void append(StringBuilder result, String value, int[] index,
                        String placeholder, MaskCharacter[] mask)
                          throws ParseException {
        for (int counter = 0, maxCounter = mask.length;
             counter < maxCounter; counter++) {
            mask[counter].append(result, value, index, placeholder);
        }
    }

    /**
     * Updates the internal representation of the mask.
     * <p>
     *  更新掩码的内部表示。
     * 
     */
    private void updateInternalMask() throws ParseException {
        String mask = getMask();
        ArrayList<MaskCharacter> fixed = new ArrayList<MaskCharacter>();
        ArrayList<MaskCharacter> temp = fixed;

        if (mask != null) {
            for (int counter = 0, maxCounter = mask.length();
                 counter < maxCounter; counter++) {
                char maskChar = mask.charAt(counter);

                switch (maskChar) {
                case DIGIT_KEY:
                    temp.add(new DigitMaskCharacter());
                    break;
                case LITERAL_KEY:
                    if (++counter < maxCounter) {
                        maskChar = mask.charAt(counter);
                        temp.add(new LiteralCharacter(maskChar));
                    }
                    // else: Could actually throw if else
                    break;
                case UPPERCASE_KEY:
                    temp.add(new UpperCaseCharacter());
                    break;
                case LOWERCASE_KEY:
                    temp.add(new LowerCaseCharacter());
                    break;
                case ALPHA_NUMERIC_KEY:
                    temp.add(new AlphaNumericCharacter());
                    break;
                case CHARACTER_KEY:
                    temp.add(new CharCharacter());
                    break;
                case ANYTHING_KEY:
                    temp.add(new MaskCharacter());
                    break;
                case HEX_KEY:
                    temp.add(new HexCharacter());
                    break;
                default:
                    temp.add(new LiteralCharacter(maskChar));
                    break;
                }
            }
        }
        if (fixed.size() == 0) {
            maskChars = EmptyMaskChars;
        }
        else {
            maskChars = new MaskCharacter[fixed.size()];
            fixed.toArray(maskChars);
        }
    }

    /**
     * Returns the MaskCharacter at the specified location.
     * <p>
     *  返回指定位置的MaskCharacter。
     * 
     */
    private MaskCharacter getMaskCharacter(int index) {
        if (index >= maskChars.length) {
            return null;
        }
        return maskChars[index];
    }

    /**
     * Returns true if the placeholder character matches aChar.
     * <p>
     *  如果占位符字符匹配aChar,则返回true。
     * 
     */
    private boolean isPlaceholder(int index, char aChar) {
        return (getPlaceholderCharacter() == aChar);
    }

    /**
     * Returns true if the passed in character matches the mask at the
     * specified location.
     * <p>
     *  如果传入的字符与指定位置的掩码匹配,则返回true。
     * 
     */
    private boolean isValidCharacter(int index, char aChar) {
        return getMaskCharacter(index).isValidCharacter(aChar);
    }

    /**
     * Returns true if the character at the specified location is a literal,
     * that is it can not be edited.
     * <p>
     *  如果指定位置处的字符是文字​​,则返回true,即无法编辑。
     * 
     */
    private boolean isLiteral(int index) {
        return getMaskCharacter(index).isLiteral();
    }

    /**
     * Returns the maximum length the text can be.
     * <p>
     *  返回文本的最大长度。
     * 
     */
    private int getMaxLength() {
        return maskChars.length;
    }

    /**
     * Returns the literal character at the specified location.
     * <p>
     *  返回指定位置的文字字符。
     * 
     */
    private char getLiteral(int index) {
        return getMaskCharacter(index).getChar((char)0);
    }

    /**
     * Returns the character to insert at the specified location based on
     * the passed in character.  This provides a way to map certain sets
     * of characters to alternative values (lowercase to
     * uppercase...).
     * <p>
     * 根据传入的字符返回要在指定位置插入的字符。这提供了一种将某些字符集映射到替代值(小写字母到大写字母...)的方法。
     * 
     */
    private char getCharacter(int index, char aChar) {
        return getMaskCharacter(index).getChar(aChar);
    }

    /**
     * Removes the literal characters from the passed in string.
     * <p>
     *  从传递的字符串中删除文字字符。
     * 
     */
    private String stripLiteralChars(String string) {
        StringBuilder sb = null;
        int last = 0;

        for (int counter = 0, max = string.length(); counter < max; counter++){
            if (isLiteral(counter)) {
                if (sb == null) {
                    sb = new StringBuilder();
                    if (counter > 0) {
                        sb.append(string.substring(0, counter));
                    }
                    last = counter + 1;
                }
                else if (last != counter) {
                    sb.append(string.substring(last, counter));
                }
                last = counter + 1;
            }
        }
        if (sb == null) {
            // Assume the mask isn't all literals.
            return string;
        }
        else if (last != string.length()) {
            if (sb == null) {
                return string.substring(last);
            }
            sb.append(string.substring(last));
        }
        return sb.toString();
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
        try {
            updateInternalMask();
        } catch (ParseException pe) {
            // assert();
        }
    }

    /**
     * Returns true if the MaskFormatter allows invalid, or
     * the offset is less than the max length and the character at
     * <code>offset</code> is a literal.
     * <p>
     *  如果MaskFormatter允许无效,则返回true,或者偏移量小于最大长度,并且<code> offset </code>处的字符是文字​​。
     * 
     */
    boolean isNavigatable(int offset) {
        if (!getAllowsInvalid()) {
            return (offset < getMaxLength() && !isLiteral(offset));
        }
        return true;
    }

    /*
     * Returns true if the operation described by <code>rh</code> will
     * result in a legal edit.  This may set the <code>value</code>
     * field of <code>rh</code>.
     * <p>
     * This is overriden to return true for a partial match.
     * <p>
     *  如果<code> rh </code>描述的操作将导致合法编辑,则返回true。这可以设置<code> rh </code>的<code>值</code>字段。
     * <p>
     *  这被覆盖为部分匹配返回true。
     * 
     */
    boolean isValidEdit(ReplaceHolder rh) {
        if (!getAllowsInvalid()) {
            String newString = getReplaceString(rh.offset, rh.length, rh.text);

            try {
                rh.value = stringToValue(newString, false);

                return true;
            } catch (ParseException pe) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method does the following (assuming !getAllowsInvalid()):
     * iterate over the max of the deleted region or the text length, for
     * each character:
     * <ol>
     * <li>If it is valid (matches the mask at the particular position, or
     *     matches the literal character at the position), allow it
     * <li>Else if the position identifies a literal character, add it. This
     *     allows for the user to paste in text that may/may not contain
     *     the literals.  For example, in pasing in 5551212 into ###-####
     *     when the 1 is evaluated it is illegal (by the first test), but there
     *     is a literal at this position (-), so it is used.  NOTE: This has
     *     a problem that you can't tell (without looking ahead) if you should
     *     eat literals in the text. For example, if you paste '555' into
     *     #5##, should it result in '5555' or '555 '? The current code will
     *     result in the latter, which feels a little better as selecting
     *     text than pasting will always result in the same thing.
     * <li>Else if at the end of the inserted text, the replace the item with
     *     the placeholder
     * <li>Otherwise the insert is bogus and false is returned.
     * </ol>
     * <p>
     *  此方法执行以下操作(假设！getAllowsInvalid())：针对每个字符遍历删除区域的最大值或文本长度：
     * <ol>
     * <li>如果它有效(匹配特定位置的掩码,或匹配该位置的文字字符),则允许<li>否则,如果位置标识文字字符,则添加它。这允许用户粘贴可以/可以不包含文字的文本。
     * 例如,在5551212中将其放入###  -  ####当1被评估时,它是非法的(通过第一个测试),但是在这个位置( - )有一个文字,所以它被使用。
     * 注意：这有一个问题,你不能告诉(不向前看)如果你应该在文本中使用文字。
     * 例如,如果你将'555'粘贴到#5 ##,是否应该导致'5555'或'555'?当前代码将导致后者,感觉更好的选择文本比粘贴将总是导致相同的事情。
     *  <li>否则,如果在插入的文本末尾,请将该项替换为占位符<li>否则,插入是假的,返回false。
     * </ol>
     */
    boolean canReplace(ReplaceHolder rh) {
        // This method is rather long, but much of the burden is in
        // maintaining a String and swapping to a StringBuilder only if
        // absolutely necessary.
        if (!getAllowsInvalid()) {
            StringBuilder replace = null;
            String text = rh.text;
            int tl = (text != null) ? text.length() : 0;

            if (tl == 0 && rh.length == 1 && getFormattedTextField().
                              getSelectionStart() != rh.offset) {
                // Backspace, adjust to actually delete next non-literal.
                while (rh.offset > 0 && isLiteral(rh.offset)) {
                    rh.offset--;
                }
            }
            int max = Math.min(getMaxLength() - rh.offset,
                               Math.max(tl, rh.length));
            for (int counter = 0, textIndex = 0; counter < max; counter++) {
                if (textIndex < tl && isValidCharacter(rh.offset + counter,
                                                   text.charAt(textIndex))) {
                    char aChar = text.charAt(textIndex);
                    if (aChar != getCharacter(rh.offset + counter, aChar)) {
                        if (replace == null) {
                            replace = new StringBuilder();
                            if (textIndex > 0) {
                                replace.append(text.substring(0, textIndex));
                            }
                        }
                    }
                    if (replace != null) {
                        replace.append(getCharacter(rh.offset + counter,
                                                    aChar));
                    }
                    textIndex++;
                }
                else if (isLiteral(rh.offset + counter)) {
                    if (replace != null) {
                        replace.append(getLiteral(rh.offset + counter));
                        if (textIndex < tl) {
                            max = Math.min(max + 1, getMaxLength() -
                                           rh.offset);
                        }
                    }
                    else if (textIndex > 0) {
                        replace = new StringBuilder(max);
                        replace.append(text.substring(0, textIndex));
                        replace.append(getLiteral(rh.offset + counter));
                        if (textIndex < tl) {
                            // Evaluate the character in text again.
                            max = Math.min(max + 1, getMaxLength() -
                                           rh.offset);
                        }
                        else if (rh.cursorPosition == -1) {
                            rh.cursorPosition = rh.offset + counter;
                        }
                    }
                    else {
                        rh.offset++;
                        rh.length--;
                        counter--;
                        max--;
                    }
                }
                else if (textIndex >= tl) {
                    // placeholder
                    if (replace == null) {
                        replace = new StringBuilder();
                        if (text != null) {
                            replace.append(text);
                        }
                    }
                    replace.append(getPlaceholderCharacter());
                    if (tl > 0 && rh.cursorPosition == -1) {
                        rh.cursorPosition = rh.offset + counter;
                    }
                }
                else {
                    // Bogus character.
                    return false;
                }
            }
            if (replace != null) {
                rh.text = replace.toString();
            }
            else if (text != null && rh.offset + tl > getMaxLength()) {
                rh.text = text.substring(0, getMaxLength() - rh.offset);
            }
            if (getOverwriteMode() && rh.text != null) {
                rh.length = rh.text.length();
            }
        }
        return super.canReplace(rh);
    }


    //
    // Interal classes used to represent the mask.
    //
    private class MaskCharacter {
        /**
         * Subclasses should override this returning true if the instance
         * represents a literal character. The default implementation
         * returns false.
         * <p>
         *  如果实例表示文字字符,子类应该覆盖此返回的true。默认实现返回false。
         * 
         */
        public boolean isLiteral() {
            return false;
        }

        /**
         * Returns true if <code>aChar</code> is a valid reprensentation of
         * the receiver. The default implementation returns true if the
         * receiver represents a literal character and <code>getChar</code>
         * == aChar. Otherwise, this will return true is <code>aChar</code>
         * is contained in the valid characters and not contained
         * in the invalid characters.
         * <p>
         *  如果<code> aChar </code>是接收器的有效重新定位,则返回true。如果接收方表示文字字符和<code> getChar </code> == aChar,则默认实现将返回true。
         * 否则,这将返回true是<code> aChar </code>包含在有效字符中,而不包含在无效字符中。
         * 
         */
        public boolean isValidCharacter(char aChar) {
            if (isLiteral()) {
                return (getChar(aChar) == aChar);
            }

            aChar = getChar(aChar);

            String filter = getValidCharacters();

            if (filter != null && filter.indexOf(aChar) == -1) {
                return false;
            }
            filter = getInvalidCharacters();
            if (filter != null && filter.indexOf(aChar) != -1) {
                return false;
            }
            return true;
        }

        /**
         * Returns the character to insert for <code>aChar</code>. The
         * default implementation returns <code>aChar</code>. Subclasses
         * that wish to do some sort of mapping, perhaps lower case to upper
         * case should override this and do the necessary mapping.
         * <p>
         * 返回要插入<code> aChar </code>的字符。默认实现返回<code> aChar </code>。希望做某种类型的映射的子类,可能小写到大写应该覆盖这个并做必要的映射。
         * 
         */
        public char getChar(char aChar) {
            return aChar;
        }

        /**
         * Appends the necessary character in <code>formatting</code> at
         * <code>index</code> to <code>buff</code>.
         * <p>
         *  将<code>格式</code>中的必要字符附加到<code> index </code>到<code> buff </code>。
         * 
         */
        public void append(StringBuilder buff, String formatting, int[] index,
                           String placeholder)
                          throws ParseException {
            boolean inString = index[0] < formatting.length();
            char aChar = inString ? formatting.charAt(index[0]) : 0;

            if (isLiteral()) {
                buff.append(getChar(aChar));
                if (getValueContainsLiteralCharacters()) {
                    if (inString && aChar != getChar(aChar)) {
                        throw new ParseException("Invalid character: " +
                                                 aChar, index[0]);
                    }
                    index[0] = index[0] + 1;
                }
            }
            else if (index[0] >= formatting.length()) {
                if (placeholder != null && index[0] < placeholder.length()) {
                    buff.append(placeholder.charAt(index[0]));
                }
                else {
                    buff.append(getPlaceholderCharacter());
                }
                index[0] = index[0] + 1;
            }
            else if (isValidCharacter(aChar)) {
                buff.append(getChar(aChar));
                index[0] = index[0] + 1;
            }
            else {
                throw new ParseException("Invalid character: " + aChar,
                                         index[0]);
            }
        }
    }


    /**
     * Used to represent a fixed character in the mask.
     * <p>
     *  用于表示掩码中的固定字符。
     * 
     */
    private class LiteralCharacter extends MaskCharacter {
        private char fixedChar;

        public LiteralCharacter(char fixedChar) {
            this.fixedChar = fixedChar;
        }

        public boolean isLiteral() {
            return true;
        }

        public char getChar(char aChar) {
            return fixedChar;
        }
    }


    /**
     * Represents a number, uses <code>Character.isDigit</code>.
     * <p>
     *  表示一个数字,使用<code> Character.isDigit </code>。
     * 
     */
    private class DigitMaskCharacter extends MaskCharacter {
        public boolean isValidCharacter(char aChar) {
            return (Character.isDigit(aChar) &&
                    super.isValidCharacter(aChar));
        }
    }


    /**
     * Represents a character, lower case letters are mapped to upper case
     * using <code>Character.toUpperCase</code>.
     * <p>
     *  表示一个字符,小写字母使用<code> Character.toUpperCase </code>映射到大写。
     * 
     */
    private class UpperCaseCharacter extends MaskCharacter {
        public boolean isValidCharacter(char aChar) {
            return (Character.isLetter(aChar) &&
                     super.isValidCharacter(aChar));
        }

        public char getChar(char aChar) {
            return Character.toUpperCase(aChar);
        }
    }


    /**
     * Represents a character, upper case letters are mapped to lower case
     * using <code>Character.toLowerCase</code>.
     * <p>
     *  表示一个字符,大写字母使用<code> Character.toLowerCase </code>映射到小写。
     * 
     */
    private class LowerCaseCharacter extends MaskCharacter {
        public boolean isValidCharacter(char aChar) {
            return (Character.isLetter(aChar) &&
                     super.isValidCharacter(aChar));
        }

        public char getChar(char aChar) {
            return Character.toLowerCase(aChar);
        }
    }


    /**
     * Represents either a character or digit, uses
     * <code>Character.isLetterOrDigit</code>.
     * <p>
     *  表示字符或数字,使用<code> Character.isLetterOrDigit </code>。
     * 
     */
    private class AlphaNumericCharacter extends MaskCharacter {
        public boolean isValidCharacter(char aChar) {
            return (Character.isLetterOrDigit(aChar) &&
                     super.isValidCharacter(aChar));
        }
    }


    /**
     * Represents a letter, uses <code>Character.isLetter</code>.
     * <p>
     *  表示一个字母,使用<code> Character.isLetter </code>。
     * 
     */
    private class CharCharacter extends MaskCharacter {
        public boolean isValidCharacter(char aChar) {
            return (Character.isLetter(aChar) &&
                     super.isValidCharacter(aChar));
        }
    }


    /**
     * Represents a hex character, 0-9a-fA-F. a-f is mapped to A-F
     * <p>
     *  表示十六进制字符,0-9a-fA-F。 a-f映射到A-F
     */
    private class HexCharacter extends MaskCharacter {
        public boolean isValidCharacter(char aChar) {
            return ((aChar == '0' || aChar == '1' ||
                     aChar == '2' || aChar == '3' ||
                     aChar == '4' || aChar == '5' ||
                     aChar == '6' || aChar == '7' ||
                     aChar == '8' || aChar == '9' ||
                     aChar == 'a' || aChar == 'A' ||
                     aChar == 'b' || aChar == 'B' ||
                     aChar == 'c' || aChar == 'C' ||
                     aChar == 'd' || aChar == 'D' ||
                     aChar == 'e' || aChar == 'E' ||
                     aChar == 'f' || aChar == 'F') &&
                    super.isValidCharacter(aChar));
        }

        public char getChar(char aChar) {
            if (Character.isDigit(aChar)) {
                return aChar;
            }
            return Character.toUpperCase(aChar);
        }
    }
}
