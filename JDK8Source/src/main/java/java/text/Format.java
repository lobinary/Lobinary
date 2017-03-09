/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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
 * (C) Copyright Taligent, Inc. 1996, 1997 - All Rights Reserved
 * (C) Copyright IBM Corp. 1996 - 1998 - All Rights Reserved
 *
 *   The original version of this source code and documentation is copyrighted
 * and owned by Taligent, Inc., a wholly-owned subsidiary of IBM. These
 * materials are provided under terms of a License Agreement between Taligent
 * and Sun. This technology is protected by multiple US and International
 * patents. This notice and attribution to Taligent may not be removed.
 *   Taligent is a registered trademark of Taligent, Inc.
 *
 * <p>
 *  (C)版权Taligent,Inc. 1996,1997  - 保留所有权利(C)版权所有IBM Corp. 1996  -  1998  - 保留所有权利
 * 
 *  此源代码和文档的原始版本由IBM的全资子公司Taligent,Inc.拥有版权和所有权。这些材料是根据Taligent和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。
 * 此通知和归因于Taligent不得删除。 Taligent是Taligent,Inc.的注册商标。
 * 
 */

package java.text;

import java.io.Serializable;

/**
 * <code>Format</code> is an abstract base class for formatting locale-sensitive
 * information such as dates, messages, and numbers.
 *
 * <p>
 * <code>Format</code> defines the programming interface for formatting
 * locale-sensitive objects into <code>String</code>s (the
 * <code>format</code> method) and for parsing <code>String</code>s back
 * into objects (the <code>parseObject</code> method).
 *
 * <p>
 * Generally, a format's <code>parseObject</code> method must be able to parse
 * any string formatted by its <code>format</code> method. However, there may
 * be exceptional cases where this is not possible. For example, a
 * <code>format</code> method might create two adjacent integer numbers with
 * no separator in between, and in this case the <code>parseObject</code> could
 * not tell which digits belong to which number.
 *
 * <h3>Subclassing</h3>
 *
 * <p>
 * The Java Platform provides three specialized subclasses of <code>Format</code>--
 * <code>DateFormat</code>, <code>MessageFormat</code>, and
 * <code>NumberFormat</code>--for formatting dates, messages, and numbers,
 * respectively.
 * <p>
 * Concrete subclasses must implement three methods:
 * <ol>
 * <li> <code>format(Object obj, StringBuffer toAppendTo, FieldPosition pos)</code>
 * <li> <code>formatToCharacterIterator(Object obj)</code>
 * <li> <code>parseObject(String source, ParsePosition pos)</code>
 * </ol>
 * These general methods allow polymorphic parsing and formatting of objects
 * and are used, for example, by <code>MessageFormat</code>.
 * Subclasses often also provide additional <code>format</code> methods for
 * specific input types as well as <code>parse</code> methods for specific
 * result types. Any <code>parse</code> method that does not take a
 * <code>ParsePosition</code> argument should throw <code>ParseException</code>
 * when no text in the required format is at the beginning of the input text.
 *
 * <p>
 * Most subclasses will also implement the following factory methods:
 * <ol>
 * <li>
 * <code>getInstance</code> for getting a useful format object appropriate
 * for the current locale
 * <li>
 * <code>getInstance(Locale)</code> for getting a useful format
 * object appropriate for the specified locale
 * </ol>
 * In addition, some subclasses may also implement other
 * <code>getXxxxInstance</code> methods for more specialized control. For
 * example, the <code>NumberFormat</code> class provides
 * <code>getPercentInstance</code> and <code>getCurrencyInstance</code>
 * methods for getting specialized number formatters.
 *
 * <p>
 * Subclasses of <code>Format</code> that allow programmers to create objects
 * for locales (with <code>getInstance(Locale)</code> for example)
 * must also implement the following class method:
 * <blockquote>
 * <pre>
 * public static Locale[] getAvailableLocales()
 * </pre>
 * </blockquote>
 *
 * <p>
 * And finally subclasses may define a set of constants to identify the various
 * fields in the formatted output. These constants are used to create a FieldPosition
 * object which identifies what information is contained in the field and its
 * position in the formatted result. These constants should be named
 * <code><em>item</em>_FIELD</code> where <code><em>item</em></code> identifies
 * the field. For examples of these constants, see <code>ERA_FIELD</code> and its
 * friends in {@link DateFormat}.
 *
 * <h4><a name="synchronization">Synchronization</a></h4>
 *
 * <p>
 * Formats are generally not synchronized.
 * It is recommended to create separate format instances for each thread.
 * If multiple threads access a format concurrently, it must be synchronized
 * externally.
 *
 * <p>
 *  <code>格式</code>是用于格式化区域设置敏感信息(例如日期,消息和数字)的抽象基类。
 * 
 * <p>
 *  <code> Format </code>定义了用于将区域敏感对象格式化为<code> String </code> s(<code> format </code>方法)和解析<code> Strin
 * g </code >回到对象(<code> parseObject </code>方法)。
 * 
 * <p>
 *  通常,格式的<code> parseObject </code>方法必须能够解析通过其<code> format </code>方法格式化的任何字符串。但是,可能有特殊情况,这是不可能的。
 * 例如,<code> format </code>方法可能创建两个相邻的整数,两者之间没有分隔符,在这种情况下,<code> parseObject </code>无法分辨哪些数字属于哪个数字。
 * 
 *  <h3>子类</h3>
 * 
 * <p>
 * Java平台为格式化日期提供了<code> Format </code>  -  <code> DateFormat </code>,<code> MessageFormat </code>和<code>
 *  NumberFormat </code> ,消息和数字。
 * <p>
 *  具体子类必须实现三种方法：
 * <ol>
 *  <li> <code> parseObject(String source,ParsePosition pos)</b> </b> </b> </b> </code>
 * </ol>
 *  这些通用方法允许对象的多态性解析和格式化,并且例如由<code> MessageFormat </code>使用。
 * 子类通常还为特定输入类型以及针对特定结果类型的<code> parse </code>方法提供额外的<code> format </code>方法。
 * 当在输入文本的开头没有所需格式的文本时,任何不带有<code> ParsePosition </code>参数的<code> parse </code>方法都应该抛出<code> ParseExcept
 * ion </code>。
 * 子类通常还为特定输入类型以及针对特定结果类型的<code> parse </code>方法提供额外的<code> format </code>方法。
 * 
 * <p>
 *  大多数子类也将实现以下工厂方法：
 * <ol>
 * <li>
 *  <code> getInstance </code>用于获取适合当前语言环境的有用的格式对象
 * <li>
 *  <code> getInstance(Locale)</code>用于获取适合指定区域设置的有用的格式对象
 * </ol>
 * 另外,一些子类也可以实现其他<code> getXxxxInstance </code>方法用于更专门的控制。
 * 例如,<code> NumberFormat </code>类提供<code> getPercentInstance </code>和<code> getCurrencyInstance </code>
 * 方法来获取特定的数字格式化程序。
 * 另外,一些子类也可以实现其他<code> getXxxxInstance </code>方法用于更专门的控制。
 * 
 * <p>
 *  允许程序员为区域设置创建对象的<code> Format </code>子类(例如使用<code> getInstance(Locale)</code>)还必须实现以下类方法：
 * <blockquote>
 * <pre>
 *  public static Locale [] getAvailableLocales()
 * </pre>
 * </blockquote>
 * 
 * <p>
 *  最后子类可以定义一组常量来标识格式化输出中的各个字段。这些常量用于创建FieldPosition对象,该对象标识字段中包含的信息及其在格式化结果中的位置。
 * 这些常数应命名为<code> <em> item </em> _FIELD </code>,其中<code> <em> item </em> </code>有关这些常量的示例,请参阅<code> ERA
 * _FIELD </code>及其在{@link DateFormat}中的朋友。
 *  最后子类可以定义一组常量来标识格式化输出中的各个字段。这些常量用于创建FieldPosition对象,该对象标识字段中包含的信息及其在格式化结果中的位置。
 * 
 *  <h4> <a name="synchronization">同步</a> </h4>
 * 
 * <p>
 *  格式通常不同步。建议为每个线程创建单独的格式实例。如果多个线程并发访问格式,则必须在外部同步。
 * 
 * 
 * @see          java.text.ParsePosition
 * @see          java.text.FieldPosition
 * @see          java.text.NumberFormat
 * @see          java.text.DateFormat
 * @see          java.text.MessageFormat
 * @author       Mark Davis
 */
public abstract class Format implements Serializable, Cloneable {

    private static final long serialVersionUID = -299282585814624189L;

    /**
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)
     * <p>
     *  唯一构造函数。 (对于子类构造函数的调用,通常是隐式的。)
     * 
     */
    protected Format() {
    }

    /**
     * Formats an object to produce a string. This is equivalent to
     * <blockquote>
     * {@link #format(Object, StringBuffer, FieldPosition) format}<code>(obj,
     *         new StringBuffer(), new FieldPosition(0)).toString();</code>
     * </blockquote>
     *
     * <p>
     *  格式化对象以生成字符串。这相当于
     * <blockquote>
     * {@link #format(Object,StringBuffer,FieldPosition)format} <code>(obj,new StringBuffer(),new FieldPosit
     * ion(0))。
     * toString(); </code>。
     * </blockquote>
     * 
     * 
     * @param obj    The object to format
     * @return       Formatted string.
     * @exception IllegalArgumentException if the Format cannot format the given
     *            object
     */
    public final String format (Object obj) {
        return format(obj, new StringBuffer(), new FieldPosition(0)).toString();
    }

    /**
     * Formats an object and appends the resulting text to a given string
     * buffer.
     * If the <code>pos</code> argument identifies a field used by the format,
     * then its indices are set to the beginning and end of the first such
     * field encountered.
     *
     * <p>
     *  格式化对象,并将生成的文本附加到给定的字符串缓冲区。如果<code> pos </code>参数标识格式使用的字段,那么其索引将设置为遇到的第一个此类字段的开始和结束。
     * 
     * 
     * @param obj    The object to format
     * @param toAppendTo    where the text is to be appended
     * @param pos    A <code>FieldPosition</code> identifying a field
     *               in the formatted text
     * @return       the string buffer passed in as <code>toAppendTo</code>,
     *               with formatted text appended
     * @exception NullPointerException if <code>toAppendTo</code> or
     *            <code>pos</code> is null
     * @exception IllegalArgumentException if the Format cannot format the given
     *            object
     */
    public abstract StringBuffer format(Object obj,
                    StringBuffer toAppendTo,
                    FieldPosition pos);

    /**
     * Formats an Object producing an <code>AttributedCharacterIterator</code>.
     * You can use the returned <code>AttributedCharacterIterator</code>
     * to build the resulting String, as well as to determine information
     * about the resulting String.
     * <p>
     * Each attribute key of the AttributedCharacterIterator will be of type
     * <code>Field</code>. It is up to each <code>Format</code> implementation
     * to define what the legal values are for each attribute in the
     * <code>AttributedCharacterIterator</code>, but typically the attribute
     * key is also used as the attribute value.
     * <p>The default implementation creates an
     * <code>AttributedCharacterIterator</code> with no attributes. Subclasses
     * that support fields should override this and create an
     * <code>AttributedCharacterIterator</code> with meaningful attributes.
     *
     * <p>
     *  格式化产生<code> AttributedCharacterIterator </code>的对象。
     * 您可以使用返回的<code> AttributedCharacterIterator </code>来构建生成的String,以及确定有关生成的String的信息。
     * <p>
     *  AttributedCharacterIterator的每个属性键的类型为<code> Field </code>。
     * 每个<code> Format </code>实现定义了<code> AttributedCharacterIterator </code>中每个属性的合法值,但通常属性键也用作属性值。
     *  <p>默认实现创建一个没有属性的<code> AttributedCharacterIterator </code>。
     * 支持字段的子类应该覆盖此并创建具有有意义属性的<code> AttributedCharacterIterator </code>。
     * 
     * 
     * @exception NullPointerException if obj is null.
     * @exception IllegalArgumentException when the Format cannot format the
     *            given object.
     * @param obj The object to format
     * @return AttributedCharacterIterator describing the formatted value.
     * @since 1.4
     */
    public AttributedCharacterIterator formatToCharacterIterator(Object obj) {
        return createAttributedCharacterIterator(format(obj));
    }

    /**
     * Parses text from a string to produce an object.
     * <p>
     * The method attempts to parse text starting at the index given by
     * <code>pos</code>.
     * If parsing succeeds, then the index of <code>pos</code> is updated
     * to the index after the last character used (parsing does not necessarily
     * use all characters up to the end of the string), and the parsed
     * object is returned. The updated <code>pos</code> can be used to
     * indicate the starting point for the next call to this method.
     * If an error occurs, then the index of <code>pos</code> is not
     * changed, the error index of <code>pos</code> is set to the index of
     * the character where the error occurred, and null is returned.
     *
     * <p>
     *  从字符串解析文本以生成对象。
     * <p>
     * 该方法尝试解析从<code> pos </code>给出的索引开始的文本。
     * 如果解析成功,则将<code> pos </code>的索引更新为使用最后一个字符后的索引(解析不一定使用直到字符串结尾的所有字符),并返回已解析的对象。
     * 更新的<code> pos </code>可用于指示下一次调用此方法的起点。
     * 如果发生错误,则<code> pos </code>的索引不改变,<code> pos </code>的错误索引设置为发生错误的字符的索引,返回null 。
     * 
     * 
     * @param source A <code>String</code>, part of which should be parsed.
     * @param pos A <code>ParsePosition</code> object with index and error
     *            index information as described above.
     * @return An <code>Object</code> parsed from the string. In case of
     *         error, returns null.
     * @exception NullPointerException if <code>pos</code> is null.
     */
    public abstract Object parseObject (String source, ParsePosition pos);

    /**
     * Parses text from the beginning of the given string to produce an object.
     * The method may not use the entire text of the given string.
     *
     * <p>
     *  从给定字符串的开头解析文本以产生对象。该方法可能不使用给定字符串的整个文本。
     * 
     * 
     * @param source A <code>String</code> whose beginning should be parsed.
     * @return An <code>Object</code> parsed from the string.
     * @exception ParseException if the beginning of the specified string
     *            cannot be parsed.
     */
    public Object parseObject(String source) throws ParseException {
        ParsePosition pos = new ParsePosition(0);
        Object result = parseObject(source, pos);
        if (pos.index == 0) {
            throw new ParseException("Format.parseObject(String) failed",
                pos.errorIndex);
        }
        return result;
    }

    /**
     * Creates and returns a copy of this object.
     *
     * <p>
     *  创建并返回此对象的副本。
     * 
     * 
     * @return a clone of this instance.
     */
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // will never happen
            throw new InternalError(e);
        }
    }

    //
    // Convenience methods for creating AttributedCharacterIterators from
    // different parameters.
    //

    /**
     * Creates an <code>AttributedCharacterIterator</code> for the String
     * <code>s</code>.
     *
     * <p>
     *  为字符串<code> s </code>创建<code> AttributedCharacterIterator </code>。
     * 
     * 
     * @param s String to create AttributedCharacterIterator from
     * @return AttributedCharacterIterator wrapping s
     */
    AttributedCharacterIterator createAttributedCharacterIterator(String s) {
        AttributedString as = new AttributedString(s);

        return as.getIterator();
    }

    /**
     * Creates an <code>AttributedCharacterIterator</code> containing the
     * concatenated contents of the passed in
     * <code>AttributedCharacterIterator</code>s.
     *
     * <p>
     *  创建一个包含<code> AttributedCharacterIterator </code>中传递的连接内容的<code> AttributedCharacterIterator </code>。
     * 
     * 
     * @param iterators AttributedCharacterIterators used to create resulting
     *                  AttributedCharacterIterators
     * @return AttributedCharacterIterator wrapping passed in
     *         AttributedCharacterIterators
     */
    AttributedCharacterIterator createAttributedCharacterIterator(
                       AttributedCharacterIterator[] iterators) {
        AttributedString as = new AttributedString(iterators);

        return as.getIterator();
    }

    /**
     * Returns an AttributedCharacterIterator with the String
     * <code>string</code> and additional key/value pair <code>key</code>,
     * <code>value</code>.
     *
     * <p>
     *  使用String <code> string </code>和附加键/值对<code>键</code>,<code> value </code>返回AttributedCharacterIterato
     * r。
     * 
     * 
     * @param string String to create AttributedCharacterIterator from
     * @param key Key for AttributedCharacterIterator
     * @param value Value associated with key in AttributedCharacterIterator
     * @return AttributedCharacterIterator wrapping args
     */
    AttributedCharacterIterator createAttributedCharacterIterator(
                      String string, AttributedCharacterIterator.Attribute key,
                      Object value) {
        AttributedString as = new AttributedString(string);

        as.addAttribute(key, value);
        return as.getIterator();
    }

    /**
     * Creates an AttributedCharacterIterator with the contents of
     * <code>iterator</code> and the additional attribute <code>key</code>
     * <code>value</code>.
     *
     * <p>
     *  使用<code> iterator </code>和附加属性<code>键</code> <code> value </code>的内容创建AttributedCharacterIterator。
     * 
     * 
     * @param iterator Initial AttributedCharacterIterator to add arg to
     * @param key Key for AttributedCharacterIterator
     * @param value Value associated with key in AttributedCharacterIterator
     * @return AttributedCharacterIterator wrapping args
     */
    AttributedCharacterIterator createAttributedCharacterIterator(
              AttributedCharacterIterator iterator,
              AttributedCharacterIterator.Attribute key, Object value) {
        AttributedString as = new AttributedString(iterator);

        as.addAttribute(key, value);
        return as.getIterator();
    }


    /**
     * Defines constants that are used as attribute keys in the
     * <code>AttributedCharacterIterator</code> returned
     * from <code>Format.formatToCharacterIterator</code> and as
     * field identifiers in <code>FieldPosition</code>.
     *
     * <p>
     * 定义在<code> Format.formatToCharacterIterator </code>中返回的<code> AttributedCharacterIterator </code>中作为属性
     * 键使用的常量,以及<code> FieldPosition </code>中的字段标识。
     * 
     * 
     * @since 1.4
     */
    public static class Field extends AttributedCharacterIterator.Attribute {

        // Proclaim serial compatibility with 1.4 FCS
        private static final long serialVersionUID = 276966692217360283L;

        /**
         * Creates a Field with the specified name.
         *
         * <p>
         *  创建具有指定名称的字段。
         * 
         * 
         * @param name Name of the attribute
         */
        protected Field(String name) {
            super(name);
        }
    }


    /**
     * FieldDelegate is notified by the various <code>Format</code>
     * implementations as they are formatting the Objects. This allows for
     * storage of the individual sections of the formatted String for
     * later use, such as in a <code>FieldPosition</code> or for an
     * <code>AttributedCharacterIterator</code>.
     * <p>
     * Delegates should NOT assume that the <code>Format</code> will notify
     * the delegate of fields in any particular order.
     *
     * <p>
     *  FieldDelegate通过各种<code> Format </code>实现来通知,因为它们正在格式化对象。
     * 这允许存储格式化的字符串的各个部分以供稍后使用,例如在<code> FieldPosition </code>或<code> AttributedCharacterIterator </code>中。
     * <p>
     *  代理不应假设<code> Format </code>将以任何特定顺序通知委托人字段。
     * 
     * 
     * @see FieldPosition#getFieldDelegate
     * @see CharacterIteratorFieldDelegate
     */
    interface FieldDelegate {
        /**
         * Notified when a particular region of the String is formatted. This
         * method will be invoked if there is no corresponding integer field id
         * matching <code>attr</code>.
         *
         * <p>
         *  当字符串的特定区域被格式化时通知。如果没有相应的整数字段id匹配<code> attr </code>,则将调用此方法。
         * 
         * 
         * @param attr Identifies the field matched
         * @param value Value associated with the field
         * @param start Beginning location of the field, will be >= 0
         * @param end End of the field, will be >= start and <= buffer.length()
         * @param buffer Contains current formatted value, receiver should
         *        NOT modify it.
         */
        public void formatted(Format.Field attr, Object value, int start,
                              int end, StringBuffer buffer);

        /**
         * Notified when a particular region of the String is formatted.
         *
         * <p>
         *  当字符串的特定区域被格式化时通知。
         * 
         * @param fieldID Identifies the field by integer
         * @param attr Identifies the field matched
         * @param value Value associated with the field
         * @param start Beginning location of the field, will be >= 0
         * @param end End of the field, will be >= start and <= buffer.length()
         * @param buffer Contains current formatted value, receiver should
         *        NOT modify it.
         */
        public void formatted(int fieldID, Format.Field attr, Object value,
                              int start, int end, StringBuffer buffer);
    }
}
