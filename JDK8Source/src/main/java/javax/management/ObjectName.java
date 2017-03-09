/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.management;

import com.sun.jmx.mbeanserver.GetPropertyAction;
import com.sun.jmx.mbeanserver.Util;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.security.AccessController;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * <p>Represents the object name of an MBean, or a pattern that can
 * match the names of several MBeans.  Instances of this class are
 * immutable.</p>
 *
 * <p>An instance of this class can be used to represent:</p>
 * <ul>
 * <li>An object name</li>
 * <li>An object name pattern, within the context of a query</li>
 * </ul>
 *
 * <p>An object name consists of two parts, the domain and the key
 * properties.</p>
 *
 * <p>The <em>domain</em> is a string of characters not including
 * the character colon (<code>:</code>).  It is recommended that the domain
 * should not contain the string "{@code //}", which is reserved for future use.
 *
 * <p>If the domain includes at least one occurrence of the wildcard
 * characters asterisk (<code>*</code>) or question mark
 * (<code>?</code>), then the object name is a pattern.  The asterisk
 * matches any sequence of zero or more characters, while the question
 * mark matches any single character.</p>
 *
 * <p>If the domain is empty, it will be replaced in certain contexts
 * by the <em>default domain</em> of the MBean server in which the
 * ObjectName is used.</p>
 *
 * <p>The <em>key properties</em> are an unordered set of keys and
 * associated values.</p>
 *
 * <p>Each <em>key</em> is a nonempty string of characters which may
 * not contain any of the characters comma (<code>,</code>), equals
 * (<code>=</code>), colon, asterisk, or question mark.  The same key
 * may not occur twice in a given ObjectName.</p>
 *
 * <p>Each <em>value</em> associated with a key is a string of
 * characters that is either unquoted or quoted.</p>
 *
 * <p>An <em>unquoted value</em> is a possibly empty string of
 * characters which may not contain any of the characters comma,
 * equals, colon, or quote.</p>
 *
 * <p>If the <em>unquoted value</em> contains at least one occurrence
 * of the wildcard characters asterisk or question mark, then the object
 * name is a <em>property value pattern</em>. The asterisk matches any
 * sequence of zero or more characters, while the question mark matches
 * any single character.</p>
 *
 * <p>A <em>quoted value</em> consists of a quote (<code>"</code>),
 * followed by a possibly empty string of characters, followed by
 * another quote.  Within the string of characters, the backslash
 * (<code>\</code>) has a special meaning.  It must be followed by
 * one of the following characters:</p>
 *
 * <ul>
 * <li>Another backslash.  The second backslash has no special
 * meaning and the two characters represent a single backslash.</li>
 *
 * <li>The character 'n'.  The two characters represent a newline
 * ('\n' in Java).</li>
 *
 * <li>A quote.  The two characters represent a quote, and that quote
 * is not considered to terminate the quoted value. An ending closing
 * quote must be present for the quoted value to be valid.</li>
 *
 * <li>A question mark (?) or asterisk (*).  The two characters represent
 * a question mark or asterisk respectively.</li>
 * </ul>
 *
 * <p>A quote may not appear inside a quoted value except immediately
 * after an odd number of consecutive backslashes.</p>
 *
 * <p>The quotes surrounding a quoted value, and any backslashes
 * within that value, are considered to be part of the value.</p>
 *
 * <p>If the <em>quoted value</em> contains at least one occurrence of
 * the characters asterisk or question mark and they are not preceded
 * by a backslash, then they are considered as wildcard characters and
 * the object name is a <em>property value pattern</em>. The asterisk
 * matches any sequence of zero or more characters, while the question
 * mark matches any single character.</p>
 *
 * <p>An ObjectName may be a <em>property list pattern</em>. In this
 * case it may have zero or more keys and associated values. It matches
 * a nonpattern ObjectName whose domain matches and that contains the
 * same keys and associated values, as well as possibly other keys and
 * values.</p>
 *
 * <p>An ObjectName is a <em>property value pattern</em> when at least
 * one of its <em>quoted</em> or <em>unquoted</em> key property values
 * contains the wildcard characters asterisk or question mark as described
 * above. In this case it has one or more keys and associated values, with
 * at least one of the values containing wildcard characters. It matches a
 * nonpattern ObjectName whose domain matches and that contains the same
 * keys whose values match; if the property value pattern is also a
 * property list pattern then the nonpattern ObjectName can contain
 * other keys and values.</p>
 *
 * <p>An ObjectName is a <em>property pattern</em> if it is either a
 * <em>property list pattern</em> or a <em>property value pattern</em>
 * or both.</p>
 *
 * <p>An ObjectName is a pattern if its domain contains a wildcard or
 * if the ObjectName is a property pattern.</p>
 *
 * <p>If an ObjectName is not a pattern, it must contain at least one
 * key with its associated value.</p>
 *
 * <p>Examples of ObjectName patterns are:</p>
 *
 * <ul>
 * <li>{@code *:type=Foo,name=Bar} to match names in any domain whose
 *     exact set of keys is {@code type=Foo,name=Bar}.</li>
 * <li>{@code d:type=Foo,name=Bar,*} to match names in the domain
 *     {@code d} that have the keys {@code type=Foo,name=Bar} plus
 *     zero or more other keys.</li>
 * <li>{@code *:type=Foo,name=Bar,*} to match names in any domain
 *     that has the keys {@code type=Foo,name=Bar} plus zero or
 *     more other keys.</li>
 * <li>{@code d:type=F?o,name=Bar} will match e.g.
 *     {@code d:type=Foo,name=Bar} and {@code d:type=Fro,name=Bar}.</li>
 * <li>{@code d:type=F*o,name=Bar} will match e.g.
 *     {@code d:type=Fo,name=Bar} and {@code d:type=Frodo,name=Bar}.</li>
 * <li>{@code d:type=Foo,name="B*"} will match e.g.
 *     {@code d:type=Foo,name="Bling"}. Wildcards are recognized even
 *     inside quotes, and like other special characters can be escaped
 *     with {@code \}.</li>
 * </ul>
 *
 * <p>An ObjectName can be written as a String with the following
 * elements in order:</p>
 *
 * <ul>
 * <li>The domain.
 * <li>A colon (<code>:</code>).
 * <li>A key property list as defined below.
 * </ul>
 *
 * <p>A key property list written as a String is a comma-separated
 * list of elements.  Each element is either an asterisk or a key
 * property.  A key property consists of a key, an equals
 * (<code>=</code>), and the associated value.</p>
 *
 * <p>At most one element of a key property list may be an asterisk.
 * If the key property list contains an asterisk element, the
 * ObjectName is a property list pattern.</p>
 *
 * <p>Spaces have no special significance in a String representing an
 * ObjectName.  For example, the String:
 * <pre>
 * domain: key1 = value1 , key2 = value2
 * </pre>
 * represents an ObjectName with two keys.  The name of each key
 * contains six characters, of which the first and last are spaces.
 * The value associated with the key <code>"&nbsp;key1&nbsp;"</code>
 * also begins and ends with a space.
 *
 * <p>In addition to the restrictions on characters spelt out above,
 * no part of an ObjectName may contain a newline character
 * (<code>'\n'</code>), whether the domain, a key, or a value, whether
 * quoted or unquoted.  The newline character can be represented in a
 * quoted value with the sequence <code>\n</code>.
 *
 * <p>The rules on special characters and quoting apply regardless of
 * which constructor is used to make an ObjectName.</p>
 *
 * <p>To avoid collisions between MBeans supplied by different
 * vendors, a useful convention is to begin the domain name with the
 * reverse DNS name of the organization that specifies the MBeans,
 * followed by a period and a string whose interpretation is
 * determined by that organization.  For example, MBeans specified by
 * <code>example.com</code>  would have
 * domains such as <code>com.example.MyDomain</code>.  This is essentially
 * the same convention as for Java-language package names.</p>
 *
 * <p>The <b>serialVersionUID</b> of this class is <code>1081892073854801359L</code>.
 *
 * <p>
 *  <p>表示MBean的对象名称,或者表示可以匹配多个MBean的名称的模式。这个类的实例是不可变的。</p>
 * 
 *  <p>此类的实例可用于表示：</p>
 * <ul>
 *  <li>对象名称</li> <li>在查询上下文中的对象名称模式</li>
 * </ul>
 * 
 *  <p>对象名称由两部分组成,即域和键属性。</p>
 * 
 *  <p> <em>域</em>是一个字符串,不包括字符冒号(<code>：</code>)。建议域不应包含字符串"{@code //}",保留以供将来使用。
 * 
 *  <p>如果域包含至少一个通配符星号(<code> * </code>)或问号(<code>?</code>),则对象名称为模式。星号匹配任何零个或多个字符的序列,而问号匹配任何单个字符。</p>
 * 
 *  <p>如果域为空,则在某些上下文中它将被使用ObjectName的MBean服务器的<em>默认域</em>替换。</p>
 * 
 *  <p> <em>键属性</em>是无序的键和相关值集合。</p>
 * 
 * <p>每个<em>键</em>是一个非空字符串,可能不包含任何逗号(<code>,</code>),equals(<code> = </code>冒号,星号或问号。
 * 相同的键在给定的ObjectName中可能不会出现两次。</p>。
 * 
 *  <p>与键相关联的每个<em>值</em>是一个字符串,不带引号或引号。</p>
 * 
 *  <p>非引号值</em>是一个可能为空的字符串,可能不包含任何逗号,等号,冒号或引号。</p>
 * 
 *  <p>如果<em>无引号值</em>至少包含一个通配符星号或问号,则对象名称为<em>属性值模式</em>。星号匹配任何零个或多个字符的序列,而问号匹配任何单个字符。</p>
 * 
 *  <p>引用的值</em>由一个引号(<code>"</code>)组成,后面跟着一个可能为空的字符串,后跟另一个引号。
 * 在字符串中,反斜杠(<code> \ </code>)具有特殊含义,必须后跟以下字符之一：</p>。
 * 
 * <ul>
 *  <li>另一个反斜杠。第二个反斜杠没有特殊含义,两个字符表示一个反斜杠。</li>
 * 
 *  <li>字符"n"。这两个字符表示换行符(Java中为"\ n")。</li>
 * 
 * <li>报价。这两个字符表示一个引号,并且该引号不被认为终止引用的值。必须存在结束结束报价,以使引用的值有效。</li>
 * 
 *  <li>问号(?)或星号(*)。这两个字符分别表示问号或星号。</li>
 * </ul>
 * 
 *  <p>除了紧接着奇数个连续反斜线之后,引号可能不会出现在引号中。</p>
 * 
 *  <p>引号值周围的引号以及该值中的任何反斜杠都被视为值的一部分。</p>
 * 
 *  <p>如果<em>引号的值</em>至少包含一个字符星号或问号,而且它们前面没有反斜杠,则它们被视为通配符,对象名称为<em >属性值模式</em>。
 * 星号匹配任何零个或多个字符的序列,而问号匹配任何单个字符。</p>。
 * 
 *  <p> ObjectName可以是<em>属性列表模式</em>。在这种情况下,它可以具有零个或多个键和相关联的值。它匹配其域匹配且包含相同键和关联值以及可能其他键和值的非模式ObjectName。
 * </p>。
 * 
 * <p>当至少其中一个引号</em>或<em>无引号</em>键属性值包含通配符星号或问题时,ObjectName是<em>属性值模式</em>标记如上所述。
 * 在这种情况下,它具有一个或多个键和关联的值,其中至少一个值包含通配符。
 * 它匹配域匹配的非模式ObjectName,并且包含其值匹配的相同键;如果属性值模式也是属性列表模式,则非模式ObjectName可以包含其他键和值。</p>。
 * 
 *  <p>如果对象名称是<em>属性列表模式</em>或<em>属性值模式</em>或两者,都是<em>属性模式</em>。</p>
 * 
 *  <p> ObjectName是一个模式,如果其域包含通配符或如果ObjectName是属性模式。</p>
 * 
 *  <p>如果ObjectName不是模式,则必须至少包含一个键及其关联值。</p>
 * 
 *  <p> ObjectName模式的示例有：</p>
 * 
 * <ul>
 * <li> {@ code *：type = Foo,name = Bar}可匹配任何确切键为{@code type = Foo,name = Bar}的网域中的名称。
 * </li> d：type = Foo,name = Bar,*}以匹配域{@code d}中具有键{@code type = Foo,name = Bar}加上零个或多个其他键的名称。
 * </li> li> {@ code *：type = Foo,name = Bar,*}以匹配具有键{@code type = Foo,name = Bar}和零个或多个其他键的任何域中的名称。
 * </li> li> {@ code d：type = F?o,name = Bar}会匹配eg {@code d：type = Foo,name = Bar}和{@code d：type = Fro,name = Bar}
 * 。
 * </li> li> {@ code *：type = Foo,name = Bar,*}以匹配具有键{@code type = Foo,name = Bar}和零个或多个其他键的任何域中的名称。
 * </li> <li> {@ code d：type = F * o,name = Bar}将匹配例如{@code d：type = Fo,name = Bar}和{@code d：type = Frodo,name = Bar}
 * 。
 * </li> li> {@ code *：type = Foo,name = Bar,*}以匹配具有键{@code type = Foo,name = Bar}和零个或多个其他键的任何域中的名称。
 * </li> <li> {@ code d：type = Foo,name ="B * }将匹配eg {@code d：type = Foo,name ="Bling"}。
 * 通配符甚至可以在引号内识别,像其他特殊字符一样,可以使用{@code \}转义。</li>。
 * </ul>
 * 
 *  <p> ObjectName可以写成一个字符串,其顺序如下：</p>
 * 
 * <ul>
 *  <li>网域。 <li>冒号(<code>：</code>)。 <li>以下定义的关键媒体资源列表。
 * </ul>
 * 
 *  <p>写为String的关键属性列表是逗号分隔的元素列表。每个元素都是星号或键属性。键属性由键,等号(<code> = </code>)和相关值组成。</p>
 * 
 *  <p>键属性列表的最多一个元素可能是星号。如果键属性列表包含星号元素,则ObjectName是属性列表模式。</p>
 * 
 * <p>空间在表示ObjectName的String中没有特殊意义。例如,String：
 * <pre>
 *  domain：key1 = value1,key2 = value2
 * </pre>
 *  表示具有两个键的ObjectName。每个键的名称包含六个字符,其中第一个和最后一个是空格。与键<code>"&nbsp; key1&quot;"</code>相关联的值也以空格开头和结尾。
 * 
 *  <p>除了上面阐述的字符限制之外,ObjectName的任何部分都不能包含换行符(<code>'\ n'</code>),无论是域,键还是值引号或不引号。
 * 换行符可以用序列<code> \ n </code>在引号中表示。
 * 
 *  <p>特殊字符和引用的规则适用,无论使用哪个构造函数创建ObjectName。</p>
 * 
 *  <p>为避免不同供应商提供的MBeans之间发生冲突,一个有用的约定是使用指定MBean的组织的反向DNS名称开始域名,后跟一个句点和一个字符串,其解释由该组织确定。
 * 例如,由<code> example.com </code>指定的MBean将具有例如<code> com.example.MyDomain </code>的域。这与Java语言包名称的约定基本相同。
 * </p>。
 * 
 *  <p>此类的<b> serialVersionUID </b>是<code> 1081892073854801359L </code>。
 * 
 * 
 * @since 1.5
 */
@SuppressWarnings("serial") // don't complain serialVersionUID not constant
public class ObjectName implements Comparable<ObjectName>, QueryExp {

    /**
     * A structure recording property structure and
     * proposing minimal services
     * <p>
     *  结构记录属性结构并提出最低限度的服务
     * 
     */
    private static class Property {

        int _key_index;
        int _key_length;
        int _value_length;

        /**
         * Constructor.
         * <p>
         *  构造函数。
         * 
         */
        Property(int key_index, int key_length, int value_length) {
            _key_index = key_index;
            _key_length = key_length;
            _value_length = value_length;
        }

        /**
         * Assigns the key index of property
         * <p>
         *  分配属性的键索引
         * 
         */
        void setKeyIndex(int key_index) {
            _key_index = key_index;
        }

        /**
         * Returns a key string for receiver key
         * <p>
         * 返回接收器密钥的密钥字符串
         * 
         */
        String getKeyString(String name) {
            return name.substring(_key_index, _key_index + _key_length);
        }

        /**
         * Returns a value string for receiver key
         * <p>
         *  返回接收器键的值字符串
         * 
         */
        String getValueString(String name) {
            int in_begin = _key_index + _key_length + 1;
            int out_end = in_begin + _value_length;
            return name.substring(in_begin, out_end);
        }
    }

    /**
     * Marker class for value pattern property.
     * <p>
     *  值模式属性的标记类。
     * 
     */
    private static class PatternProperty extends Property {
        /**
         * Constructor.
         * <p>
         *  构造函数。
         * 
         */
        PatternProperty(int key_index, int key_length, int value_length) {
            super(key_index, key_length, value_length);
        }
    }

    // Inner classes <========================================



    // Private fields ---------------------------------------->


    // Serialization compatibility stuff -------------------->

    // Two serial forms are supported in this class. The selected form depends
    // on system property "jmx.serial.form":
    //  - "1.0" for JMX 1.0
    //  - any other value for JMX 1.1 and higher
    //
    // Serial version for old serial form
    private static final long oldSerialVersionUID = -5467795090068647408L;
    //
    // Serial version for new serial form
    private static final long newSerialVersionUID = 1081892073854801359L;
    //
    // Serializable fields in old serial form
    private static final ObjectStreamField[] oldSerialPersistentFields =
    {
        new ObjectStreamField("domain", String.class),
        new ObjectStreamField("propertyList", Hashtable.class),
        new ObjectStreamField("propertyListString", String.class),
        new ObjectStreamField("canonicalName", String.class),
        new ObjectStreamField("pattern", Boolean.TYPE),
        new ObjectStreamField("propertyPattern", Boolean.TYPE)
    };
    //
    // Serializable fields in new serial form
    private static final ObjectStreamField[] newSerialPersistentFields = { };
    //
    // Actual serial version and serial form
    private static final long serialVersionUID;
    private static final ObjectStreamField[] serialPersistentFields;
    private static boolean compat = false;
    static {
        try {
            GetPropertyAction act = new GetPropertyAction("jmx.serial.form");
            String form = AccessController.doPrivileged(act);
            compat = (form != null && form.equals("1.0"));
        } catch (Exception e) {
            // OK: exception means no compat with 1.0, too bad
        }
        if (compat) {
            serialPersistentFields = oldSerialPersistentFields;
            serialVersionUID = oldSerialVersionUID;
        } else {
            serialPersistentFields = newSerialPersistentFields;
            serialVersionUID = newSerialVersionUID;
        }
    }

    //
    // Serialization compatibility stuff <==============================

    // Class private fields ----------------------------------->

    /**
     * a shared empty array for empty property lists
     * <p>
     *  空属性列表的共享空数组
     * 
     */
    static final private Property[] _Empty_property_array = new Property[0];


    // Class private fields <==============================

    // Instance private fields ----------------------------------->

    /**
     * a String containing the canonical name
     * <p>
     *  包含规范名称的字符串
     * 
     */
    private transient String _canonicalName;


    /**
     * An array of properties in the same seq order as time creation
     * <p>
     *  与时间创建相同顺序的属性数组
     * 
     */
    private transient Property[] _kp_array;

    /**
     * An array of properties in the same seq order as canonical order
     * <p>
     *  与规范顺序相同seq顺序的属性数组
     * 
     */
    private transient Property[] _ca_array;


    /**
     * The length of the domain part of built objectname
     * <p>
     *  构建的objectname的域部分的长度
     * 
     */
    private transient int _domain_length = 0;


    /**
     * The propertyList of built object name. Initialized lazily.
     * Table that contains all the pairs (key,value) for this ObjectName.
     * <p>
     *  构建对象名的propertyList。初始化懒惰。包含此ObjectName的所有对(键,值)的表。
     * 
     */
    private transient Map<String,String> _propertyList;

    /**
     * boolean that declares if this ObjectName domain part is a pattern
     * <p>
     *  boolean,它声明这个ObjectName域部分是否是一个模式
     * 
     */
    private transient boolean _domain_pattern = false;

    /**
     * boolean that declares if this ObjectName contains a pattern on the
     * key property list
     * <p>
     *  boolean,声明此ObjectName是否包含关键属性列表中的模式
     * 
     */
    private transient boolean _property_list_pattern = false;

    /**
     * boolean that declares if this ObjectName contains a pattern on the
     * value of at least one key property
     * <p>
     *  布尔值,声明此ObjectName是否包含至少一个键属性值的模式
     * 
     */
    private transient boolean _property_value_pattern = false;

    // Instance private fields <=======================================

    // Private fields <========================================


    //  Private methods ---------------------------------------->

    // Category : Instance construction ------------------------->

    /**
     * Initializes this {@link ObjectName} from the given string
     * representation.
     *
     * <p>
     *  从给定的字符串表示初始化此{@link ObjectName}。
     * 
     * 
     * @param name A string representation of the {@link ObjectName}
     *
     * @exception MalformedObjectNameException The string passed as a
     * parameter does not have the right format.
     * @exception NullPointerException The <code>name</code> parameter
     * is null.
     */
    private void construct(String name)
        throws MalformedObjectNameException {

        // The name cannot be null
        if (name == null)
            throw new NullPointerException("name cannot be null");

        // Test if the name is empty
        if (name.length() == 0) {
            // this is equivalent to the whole word query object name.
            _canonicalName = "*:*";
            _kp_array = _Empty_property_array;
            _ca_array = _Empty_property_array;
            _domain_length = 1;
            _propertyList = null;
            _domain_pattern = true;
            _property_list_pattern = true;
            _property_value_pattern = false;
            return;
        }

        // initialize parsing of the string
        final char[] name_chars = name.toCharArray();
        final int len = name_chars.length;
        final char[] canonical_chars = new char[len]; // canonical form will
                                                      // be same length at most
        int cname_index = 0;
        int index = 0;
        char c, c1;

        // parses domain part
    domain_parsing:
        while (index < len) {
            switch (name_chars[index]) {
                case ':' :
                    _domain_length = index++;
                    break domain_parsing;
                case '=' :
                    // ":" omission check.
                    //
                    // Although "=" is a valid character in the domain part
                    // it is true that it is rarely used in the real world.
                    // So check straight away if the ":" has been omitted
                    // from the ObjectName. This allows us to provide a more
                    // accurate exception message.
                    int i = ++index;
                    while ((i < len) && (name_chars[i++] != ':'))
                        if (i == len)
                            throw new MalformedObjectNameException(
                                "Domain part must be specified");
                    break;
                case '\n' :
                    throw new MalformedObjectNameException(
                              "Invalid character '\\n' in domain name");
                case '*' :
                case '?' :
                    _domain_pattern = true;
                    index++;
                    break;
                default :
                    index++;
                    break;
            }
        }

        // check for non-empty properties
        if (index == len)
            throw new MalformedObjectNameException(
                                         "Key properties cannot be empty");

        // we have got the domain part, begins building of _canonicalName
        System.arraycopy(name_chars, 0, canonical_chars, 0, _domain_length);
        canonical_chars[_domain_length] = ':';
        cname_index = _domain_length + 1;

        // parses property list
        Property prop;
        Map<String,Property> keys_map = new HashMap<String,Property>();
        String[] keys;
        String key_name;
        boolean quoted_value;
        int property_index = 0;
        int in_index;
        int key_index, key_length, value_index, value_length;

        keys = new String[10];
        _kp_array = new Property[10];
        _property_list_pattern = false;
        _property_value_pattern = false;

        while (index < len) {
            c = name_chars[index];

            // case of pattern properties
            if (c == '*') {
                if (_property_list_pattern)
                    throw new MalformedObjectNameException(
                              "Cannot have several '*' characters in pattern " +
                              "property list");
                else {
                    _property_list_pattern = true;
                    if ((++index < len ) && (name_chars[index] != ','))
                        throw new MalformedObjectNameException(
                                  "Invalid character found after '*': end of " +
                                  "name or ',' expected");
                    else if (index == len) {
                        if (property_index == 0) {
                            // empty properties case
                            _kp_array = _Empty_property_array;
                            _ca_array = _Empty_property_array;
                            _propertyList = Collections.emptyMap();
                        }
                        break;
                    } else {
                        // correct pattern spec in props, continue
                        index++;
                        continue;
                    }
                }
            }

            // standard property case, key part
            in_index = index;
            key_index = in_index;
            if (name_chars[in_index] == '=')
                throw new MalformedObjectNameException("Invalid key (empty)");
            while ((in_index < len) && ((c1 = name_chars[in_index++]) != '='))
                switch (c1) {
                    // '=' considered to introduce value part
                    case  '*' :
                    case  '?' :
                    case  ',' :
                    case  ':' :
                    case  '\n' :
                        final String ichar = ((c1=='\n')?"\\n":""+c1);
                        throw new MalformedObjectNameException(
                                  "Invalid character '" + ichar +
                                  "' in key part of property");
                }
            if (name_chars[in_index - 1] != '=')
                throw new MalformedObjectNameException(
                                             "Unterminated key property part");
            value_index = in_index; // in_index pointing after '=' char
            key_length = value_index - key_index - 1; // found end of key

            // standard property case, value part
            boolean value_pattern = false;
            if (in_index < len && name_chars[in_index] == '\"') {
                quoted_value = true;
                // the case of quoted value part
            quoted_value_parsing:
                while ((++in_index < len) &&
                       ((c1 = name_chars[in_index]) != '\"')) {
                    // the case of an escaped character
                    if (c1 == '\\') {
                        if (++in_index == len)
                            throw new MalformedObjectNameException(
                                               "Unterminated quoted value");
                        switch (c1 = name_chars[in_index]) {
                            case '\\' :
                            case '\"' :
                            case '?' :
                            case '*' :
                            case 'n' :
                                break; // valid character
                            default :
                                throw new MalformedObjectNameException(
                                          "Invalid escape sequence '\\" +
                                          c1 + "' in quoted value");
                        }
                    } else if (c1 == '\n') {
                        throw new MalformedObjectNameException(
                                                     "Newline in quoted value");
                    } else {
                        switch (c1) {
                            case '?' :
                            case '*' :
                                value_pattern = true;
                                break;
                        }
                    }
                }
                if (in_index == len)
                    throw new MalformedObjectNameException(
                                                 "Unterminated quoted value");
                else value_length = ++in_index - value_index;
            } else {
                // the case of standard value part
                quoted_value = false;
                while ((in_index < len) && ((c1 = name_chars[in_index]) != ','))
                switch (c1) {
                    // ',' considered to be the value separator
                    case '*' :
                    case '?' :
                        value_pattern = true;
                        in_index++;
                        break;
                    case '=' :
                    case ':' :
                    case '"' :
                    case '\n' :
                        final String ichar = ((c1=='\n')?"\\n":""+c1);
                        throw new MalformedObjectNameException(
                                                 "Invalid character '" + ichar +
                                                 "' in value part of property");
                    default :
                        in_index++;
                        break;
                }
                value_length = in_index - value_index;
            }

            // Parsed property, checks the end of name
            if (in_index == len - 1) {
                if (quoted_value)
                    throw new MalformedObjectNameException(
                                             "Invalid ending character `" +
                                             name_chars[in_index] + "'");
                else throw new MalformedObjectNameException(
                                                  "Invalid ending comma");
            } else in_index++;

            // we got the key and value part, prepare a property for this
            if (!value_pattern) {
                prop = new Property(key_index, key_length, value_length);
            } else {
                _property_value_pattern = true;
                prop = new PatternProperty(key_index, key_length, value_length);
            }
            key_name = name.substring(key_index, key_index + key_length);

            if (property_index == keys.length) {
                String[] tmp_string_array = new String[property_index + 10];
                System.arraycopy(keys, 0, tmp_string_array, 0, property_index);
                keys = tmp_string_array;
            }
            keys[property_index] = key_name;

            addProperty(prop, property_index, keys_map, key_name);
            property_index++;
            index = in_index;
        }

        // computes and set canonical name
        setCanonicalName(name_chars, canonical_chars, keys,
                         keys_map, cname_index, property_index);
    }

    /**
     * Construct an ObjectName from a domain and a Hashtable.
     *
     * <p>
     *  从域和Hashtable构造ObjectName。
     * 
     * 
     * @param domain Domain of the ObjectName.
     * @param props  Map containing couples <i>key</i> {@literal ->} <i>value</i>.
     *
     * @exception MalformedObjectNameException The <code>domain</code>
     * contains an illegal character, or one of the keys or values in
     * <code>table</code> contains an illegal character, or one of the
     * values in <code>table</code> does not follow the rules for quoting.
     * @exception NullPointerException One of the parameters is null.
     */
    private void construct(String domain, Map<String,String> props)
        throws MalformedObjectNameException {

        // The domain cannot be null
        if (domain == null)
            throw new NullPointerException("domain cannot be null");

        // The key property list cannot be null
        if (props == null)
            throw new NullPointerException("key property list cannot be null");

        // The key property list cannot be empty
        if (props.isEmpty())
            throw new MalformedObjectNameException(
                                         "key property list cannot be empty");

        // checks domain validity
        if (!isDomain(domain))
            throw new MalformedObjectNameException("Invalid domain: " + domain);

        // init canonicalname
        final StringBuilder sb = new StringBuilder();
        sb.append(domain).append(':');
        _domain_length = domain.length();

        // allocates the property array
        int nb_props = props.size();
        _kp_array = new Property[nb_props];

        String[] keys = new String[nb_props];
        final Map<String,Property> keys_map = new HashMap<String,Property>();
        Property prop;
        int key_index;
        int i = 0;
        for (Map.Entry<String,String> entry : props.entrySet()) {
            if (sb.length() > 0)
                sb.append(",");
            String key = entry.getKey();
            String value;
            try {
                value = entry.getValue();
            } catch (ClassCastException e) {
                throw new MalformedObjectNameException(e.getMessage());
            }
            key_index = sb.length();
            checkKey(key);
            sb.append(key);
            keys[i] = key;
            sb.append("=");
            boolean value_pattern = checkValue(value);
            sb.append(value);
            if (!value_pattern) {
                prop = new Property(key_index,
                                    key.length(),
                                    value.length());
            } else {
                _property_value_pattern = true;
                prop = new PatternProperty(key_index,
                                           key.length(),
                                           value.length());
            }
            addProperty(prop, i, keys_map, key);
            i++;
        }

        // initialize canonical name and data structure
        int len = sb.length();
        char[] initial_chars = new char[len];
        sb.getChars(0, len, initial_chars, 0);
        char[] canonical_chars = new char[len];
        System.arraycopy(initial_chars, 0, canonical_chars, 0,
                         _domain_length + 1);
        setCanonicalName(initial_chars, canonical_chars, keys, keys_map,
                         _domain_length + 1, _kp_array.length);
    }
    // Category : Instance construction <==============================

    // Category : Internal utilities ------------------------------>

    /**
     * Add passed property to the list at the given index
     * for the passed key name
     * <p>
     *  将传递的属性添加到传递的键名称的给定索引的列表中
     * 
     */
    private void addProperty(Property prop, int index,
                             Map<String,Property> keys_map, String key_name)
        throws MalformedObjectNameException {

        if (keys_map.containsKey(key_name)) throw new
                MalformedObjectNameException("key `" +
                                         key_name +"' already defined");

        // if no more space for property arrays, have to increase it
        if (index == _kp_array.length) {
            Property[] tmp_prop_array = new Property[index + 10];
            System.arraycopy(_kp_array, 0, tmp_prop_array, 0, index);
            _kp_array = tmp_prop_array;
        }
        _kp_array[index] = prop;
        keys_map.put(key_name, prop);
    }

    /**
     * Sets the canonical name of receiver from input 'specified_chars'
     * array, by filling 'canonical_chars' array with found 'nb-props'
     * properties starting at position 'prop_index'.
     * <p>
     *  通过使用在位置'prop_index'处开始的找到的'nb-props'属性填充'canonical_chars'数组来从输入'specified_chars'数组设置接收器的规范名称。
     * 
     */
    private void setCanonicalName(char[] specified_chars,
                                  char[] canonical_chars,
                                  String[] keys, Map<String,Property> keys_map,
                                  int prop_index, int nb_props) {

        // Sort the list of found properties
        if (_kp_array != _Empty_property_array) {
            String[] tmp_keys = new String[nb_props];
            Property[] tmp_props = new Property[nb_props];

            System.arraycopy(keys, 0, tmp_keys, 0, nb_props);
            Arrays.sort(tmp_keys);
            keys = tmp_keys;
            System.arraycopy(_kp_array, 0, tmp_props, 0 , nb_props);
            _kp_array = tmp_props;
            _ca_array = new Property[nb_props];

            // now assigns _ca_array to the sorted list of keys
            // (there cannot be two identical keys in an objectname.
            for (int i = 0; i < nb_props; i++)
                _ca_array[i] = keys_map.get(keys[i]);

            // now we build the canonical name and set begin indexes of
            // properties to reflect canonical form
            int last_index = nb_props - 1;
            int prop_len;
            Property prop;
            for (int i = 0; i <= last_index; i++) {
                prop = _ca_array[i];
                // length of prop including '=' char
                prop_len = prop._key_length + prop._value_length + 1;
                System.arraycopy(specified_chars, prop._key_index,
                                 canonical_chars, prop_index, prop_len);
                prop.setKeyIndex(prop_index);
                prop_index += prop_len;
                if (i != last_index) {
                    canonical_chars[prop_index] = ',';
                    prop_index++;
                }
            }
        }

        // terminate canonicalname with '*' in case of pattern
        if (_property_list_pattern) {
            if (_kp_array != _Empty_property_array)
                canonical_chars[prop_index++] = ',';
            canonical_chars[prop_index++] = '*';
        }

        // we now build the canonicalname string
        _canonicalName = (new String(canonical_chars, 0, prop_index)).intern();
    }

    /**
     * Parse a key.
     * <pre>final int endKey=parseKey(s,startKey);</pre>
     * <p>key starts at startKey (included), and ends at endKey (excluded).
     * If (startKey == endKey), then the key is empty.
     *
     * <p>
     *  解析键。
     *  <pre> final int endKey = parseKey(s,startKey); </p> <p> key从startKey(included)开始,结束于endKey如果(startKe
     * y == endKey),那么键是空的。
     *  解析键。
     * 
     * 
     * @param s The char array of the original string.
     * @param startKey index at which to begin parsing.
     * @return The index following the last character of the key.
     **/
    private static int parseKey(final char[] s, final int startKey)
        throws MalformedObjectNameException {
        int next   = startKey;
        int endKey = startKey;
        final int len = s.length;
        while (next < len) {
            final char k = s[next++];
            switch (k) {
            case '*':
            case '?':
            case ',':
            case ':':
            case '\n':
                final String ichar = ((k=='\n')?"\\n":""+k);
                throw new
                    MalformedObjectNameException("Invalid character in key: `"
                                                 + ichar + "'");
            case '=':
                // we got the key.
                endKey = next-1;
                break;
            default:
                if (next < len) continue;
                else endKey=next;
            }
            break;
        }
        return endKey;
    }

    /**
     * Parse a value.
     * <pre>final int endVal=parseValue(s,startVal);</pre>
     * <p>value starts at startVal (included), and ends at endVal (excluded).
     * If (startVal == endVal), then the key is empty.
     *
     * <p>
     * 解析值。
     *  <pre> final int endVal = parseValue(s,startVal); </pre> <p>值从startVal(included)开始,结束于endVal(excluded
     * )。
     * 解析值。如果(startVal == endVal),则键为空。
     * 
     * 
     * @param s The char array of the original string.
     * @param startValue index at which to begin parsing.
     * @return The first element of the int array indicates the index
     *         following the last character of the value. The second
     *         element of the int array indicates that the value is
     *         a pattern when its value equals 1.
     **/
    private static int[] parseValue(final char[] s, final int startValue)
        throws MalformedObjectNameException {

        boolean value_pattern = false;

        int next   = startValue;
        int endValue = startValue;

        final int len = s.length;
        final char q=s[startValue];

        if (q == '"') {
            // quoted value
            if (++next == len) throw new
                MalformedObjectNameException("Invalid quote");
            while (next < len) {
                char last = s[next];
                if (last == '\\') {
                    if (++next == len) throw new
                        MalformedObjectNameException(
                           "Invalid unterminated quoted character sequence");
                    last = s[next];
                    switch (last) {
                        case '\\' :
                        case '?' :
                        case '*' :
                        case 'n' :
                            break;
                        case '\"' :
                            // We have an escaped quote. If this escaped
                            // quote is the last character, it does not
                            // qualify as a valid termination quote.
                            //
                            if (next+1 == len) throw new
                                MalformedObjectNameException(
                                                 "Missing termination quote");
                            break;
                        default:
                            throw new
                                MalformedObjectNameException(
                                "Invalid quoted character sequence '\\" +
                                last + "'");
                    }
                } else if (last == '\n') {
                    throw new MalformedObjectNameException(
                                                 "Newline in quoted value");
                } else if (last == '\"') {
                    next++;
                    break;
                } else {
                    switch (last) {
                        case '?' :
                        case '*' :
                            value_pattern = true;
                            break;
                    }
                }
                next++;

                // Check that last character is a termination quote.
                // We have already handled the case were the last
                // character is an escaped quote earlier.
                //
                if ((next >= len) && (last != '\"')) throw new
                    MalformedObjectNameException("Missing termination quote");
            }
            endValue = next;
            if (next < len) {
                if (s[next++] != ',') throw new
                    MalformedObjectNameException("Invalid quote");
            }
        } else {
            // Non quoted value.
            while (next < len) {
                final char v=s[next++];
                switch(v) {
                    case '*':
                    case '?':
                        value_pattern = true;
                        if (next < len) continue;
                        else endValue=next;
                        break;
                    case '=':
                    case ':':
                    case '\n' :
                        final String ichar = ((v=='\n')?"\\n":""+v);
                        throw new
                            MalformedObjectNameException("Invalid character `" +
                                                         ichar + "' in value");
                    case ',':
                        endValue = next-1;
                        break;
                    default:
                        if (next < len) continue;
                        else endValue=next;
                }
                break;
            }
        }
        return new int[] { endValue, value_pattern ? 1 : 0 };
    }

    /**
     * Check if the supplied value is a valid value.
     *
     * <p>
     *  检查所提供的值是否为有效值。
     * 
     * 
     * @return true if the value is a pattern, otherwise false.
     */
    private static boolean checkValue(String val)
        throws MalformedObjectNameException {

        if (val == null) throw new
            NullPointerException("Invalid value (null)");

        final int len = val.length();
        if (len == 0)
            return false;

        final char[] s = val.toCharArray();
        final int[] result = parseValue(s,0);
        final int endValue = result[0];
        final boolean value_pattern = result[1] == 1;
        if (endValue < len) throw new
            MalformedObjectNameException("Invalid character in value: `" +
                                         s[endValue] + "'");
        return value_pattern;
    }

    /**
     * Check if the supplied key is a valid key.
     * <p>
     *  检查提供的密钥是否为有效密钥。
     * 
     */
    private static void checkKey(String key)
        throws MalformedObjectNameException {

        if (key == null) throw new
            NullPointerException("Invalid key (null)");

        final int len = key.length();
        if (len == 0) throw new
            MalformedObjectNameException("Invalid key (empty)");
        final char[] k=key.toCharArray();
        final int endKey = parseKey(k,0);
        if (endKey < len) throw new
            MalformedObjectNameException("Invalid character in value: `" +
                                         k[endKey] + "'");
    }


    // Category : Internal utilities <==============================

    // Category : Internal accessors ------------------------------>

    /**
     * Check if domain is a valid domain.  Set _domain_pattern if appropriate.
     * <p>
     *  检查域是否是有效的域。如果适用,请设置_domain_pattern。
     * 
     */
    private boolean isDomain(String domain) {
        if (domain == null) return true;
        final int len = domain.length();
        int next = 0;
        while (next < len) {
            final char c = domain.charAt(next++);
            switch (c) {
                case ':' :
                case '\n' :
                    return false;
                case '*' :
                case '?' :
                    _domain_pattern = true;
                    break;
            }
        }
        return true;
    }

    // Category : Internal accessors <==============================

    // Category : Serialization ----------------------------------->

    /**
     * Deserializes an {@link ObjectName} from an {@link ObjectInputStream}.
     * <p>
     *  从{@link ObjectInputStream}反序列化{@link ObjectName}。
     * 
     * 
     * @serialData <ul>
     *               <li>In the current serial form (value of property
     *                   <code>jmx.serial.form</code> differs from
     *                   <code>1.0</code>): the string
     *                   &quot;&lt;domain&gt;:&lt;properties&gt;&lt;wild&gt;&quot;,
     *                   where: <ul>
     *                            <li>&lt;domain&gt; represents the domain part
     *                                of the {@link ObjectName}</li>
     *                            <li>&lt;properties&gt; represents the list of
     *                                properties, as returned by
     *                                {@link #getKeyPropertyListString}
     *                            <li>&lt;wild&gt; is empty if not
     *                                <code>isPropertyPattern</code>, or
     *                                is the character "<code>*</code>" if
     *                                <code>isPropertyPattern</code>
     *                                and &lt;properties&gt; is empty, or
     *                                is "<code>,*</code>" if
     *                                <code>isPropertyPattern</code> and
     *                                &lt;properties&gt; is not empty.
     *                            </li>
     *                          </ul>
     *                   The intent is that this string could be supplied
     *                   to the {@link #ObjectName(String)} constructor to
     *                   produce an equivalent {@link ObjectName}.
     *               </li>
     *               <li>In the old serial form (value of property
     *                   <code>jmx.serial.form</code> is
     *                   <code>1.0</code>): &lt;domain&gt; &lt;propertyList&gt;
     *                   &lt;propertyListString&gt; &lt;canonicalName&gt;
     *                   &lt;pattern&gt; &lt;propertyPattern&gt;,
     *                   where: <ul>
     *                            <li>&lt;domain&gt; represents the domain part
     *                                of the {@link ObjectName}</li>
     *                            <li>&lt;propertyList&gt; is the
     *                                {@link Hashtable} that contains all the
     *                                pairs (key,value) for this
     *                                {@link ObjectName}</li>
     *                            <li>&lt;propertyListString&gt; is the
     *                                {@link String} representation of the
     *                                list of properties in any order (not
     *                                mandatorily a canonical representation)
     *                                </li>
     *                            <li>&lt;canonicalName&gt; is the
     *                                {@link String} containing this
     *                                {@link ObjectName}'s canonical name</li>
     *                            <li>&lt;pattern&gt; is a boolean which is
     *                                <code>true</code> if this
     *                                {@link ObjectName} contains a pattern</li>
     *                            <li>&lt;propertyPattern&gt; is a boolean which
     *                                is <code>true</code> if this
     *                                {@link ObjectName} contains a pattern in
     *                                the list of properties</li>
     *                          </ul>
     *               </li>
     *             </ul>
     */
    private void readObject(ObjectInputStream in)
        throws IOException, ClassNotFoundException {

        String cn;
        if (compat) {
            // Read an object serialized in the old serial form
            //
            //in.defaultReadObject();
            final ObjectInputStream.GetField fields = in.readFields();
            String propListString =
                    (String)fields.get("propertyListString", "");

            // 6616825: take care of property patterns
            final boolean propPattern =
                    fields.get("propertyPattern" , false);
            if (propPattern) {
                propListString =
                        (propListString.length()==0?"*":(propListString+",*"));
            }

            cn = (String)fields.get("domain", "default")+
                ":"+ propListString;
        } else {
            // Read an object serialized in the new serial form
            //
            in.defaultReadObject();
            cn = (String)in.readObject();
        }

        try {
            construct(cn);
        } catch (NullPointerException e) {
            throw new InvalidObjectException(e.toString());
        } catch (MalformedObjectNameException e) {
            throw new InvalidObjectException(e.toString());
        }
    }


    /**
     * Serializes an {@link ObjectName} to an {@link ObjectOutputStream}.
     * <p>
     *  将{@link ObjectName}序列化为{@link ObjectOutputStream}。
     * 
     * 
     * @serialData <ul>
     *               <li>In the current serial form (value of property
     *                   <code>jmx.serial.form</code> differs from
     *                   <code>1.0</code>): the string
     *                   &quot;&lt;domain&gt;:&lt;properties&gt;&lt;wild&gt;&quot;,
     *                   where: <ul>
     *                            <li>&lt;domain&gt; represents the domain part
     *                                of the {@link ObjectName}</li>
     *                            <li>&lt;properties&gt; represents the list of
     *                                properties, as returned by
     *                                {@link #getKeyPropertyListString}
     *                            <li>&lt;wild&gt; is empty if not
     *                                <code>isPropertyPattern</code>, or
     *                                is the character "<code>*</code>" if
     *                                this <code>isPropertyPattern</code>
     *                                and &lt;properties&gt; is empty, or
     *                                is "<code>,*</code>" if
     *                                <code>isPropertyPattern</code> and
     *                                &lt;properties&gt; is not empty.
     *                            </li>
     *                          </ul>
     *                   The intent is that this string could be supplied
     *                   to the {@link #ObjectName(String)} constructor to
     *                   produce an equivalent {@link ObjectName}.
     *               </li>
     *               <li>In the old serial form (value of property
     *                   <code>jmx.serial.form</code> is
     *                   <code>1.0</code>): &lt;domain&gt; &lt;propertyList&gt;
     *                   &lt;propertyListString&gt; &lt;canonicalName&gt;
     *                   &lt;pattern&gt; &lt;propertyPattern&gt;,
     *                   where: <ul>
     *                            <li>&lt;domain&gt; represents the domain part
     *                                of the {@link ObjectName}</li>
     *                            <li>&lt;propertyList&gt; is the
     *                                {@link Hashtable} that contains all the
     *                                pairs (key,value) for this
     *                                {@link ObjectName}</li>
     *                            <li>&lt;propertyListString&gt; is the
     *                                {@link String} representation of the
     *                                list of properties in any order (not
     *                                mandatorily a canonical representation)
     *                                </li>
     *                            <li>&lt;canonicalName&gt; is the
     *                                {@link String} containing this
     *                                {@link ObjectName}'s canonical name</li>
     *                            <li>&lt;pattern&gt; is a boolean which is
     *                                <code>true</code> if this
     *                                {@link ObjectName} contains a pattern</li>
     *                            <li>&lt;propertyPattern&gt; is a boolean which
     *                                is <code>true</code> if this
     *                                {@link ObjectName} contains a pattern in
     *                                the list of properties</li>
     *                          </ul>
     *               </li>
     *             </ul>
     */
    private void writeObject(ObjectOutputStream out)
            throws IOException {

      if (compat)
      {
        // Serializes this instance in the old serial form
        // Read CR 6441274 before making any changes to this code
        ObjectOutputStream.PutField fields = out.putFields();
        fields.put("domain", _canonicalName.substring(0, _domain_length));
        fields.put("propertyList", getKeyPropertyList());
        fields.put("propertyListString", getKeyPropertyListString());
        fields.put("canonicalName", _canonicalName);
        fields.put("pattern", (_domain_pattern || _property_list_pattern));
        fields.put("propertyPattern", _property_list_pattern);
        out.writeFields();
      }
      else
      {
        // Serializes this instance in the new serial form
        //
        out.defaultWriteObject();
        out.writeObject(getSerializedNameString());
      }
    }

    //  Category : Serialization <===================================

    // Private methods <========================================

    // Public methods ---------------------------------------->

    // Category : ObjectName Construction ------------------------------>

    /**
     * <p>Return an instance of ObjectName that can be used anywhere
     * an object obtained with {@link #ObjectName(String) new
     * ObjectName(name)} can be used.  The returned object may be of
     * a subclass of ObjectName.  Calling this method twice with the
     * same parameters may return the same object or two equal but
     * not identical objects.</p>
     *
     * <p>
     *  <p>返回ObjectName的实例,可以在使用{@link #ObjectName(String)new ObjectName(name)}获得的对象的任何地方使用。
     * 返回的对象可以是ObjectName的子类。使用相同的参数调用此方法两次可能会返回相同的对象或两个相同但不完全相同的对象。</p>。
     * 
     * 
     * @param name  A string representation of the object name.
     *
     * @return an ObjectName corresponding to the given String.
     *
     * @exception MalformedObjectNameException The string passed as a
     * parameter does not have the right format.
     * @exception NullPointerException The <code>name</code> parameter
     * is null.
     *
     */
    public static ObjectName getInstance(String name)
            throws MalformedObjectNameException, NullPointerException {
        return new ObjectName(name);
    }

    /**
     * <p>Return an instance of ObjectName that can be used anywhere
     * an object obtained with {@link #ObjectName(String, String,
     * String) new ObjectName(domain, key, value)} can be used.  The
     * returned object may be of a subclass of ObjectName.  Calling
     * this method twice with the same parameters may return the same
     * object or two equal but not identical objects.</p>
     *
     * <p>
     *  <p>返回一个ObjectName实例,可以在使用{@link #ObjectName(String,String,String)new ObjectName(domain,key,value)}获得
     * 的对象的任何地方使用。
     * 返回的对象可以是ObjectName的子类。使用相同的参数调用此方法两次可能会返回相同的对象或两个相同但不完全相同的对象。</p>。
     * 
     * 
     * @param domain  The domain part of the object name.
     * @param key  The attribute in the key property of the object name.
     * @param value The value in the key property of the object name.
     *
     * @return an ObjectName corresponding to the given domain,
     * key, and value.
     *
     * @exception MalformedObjectNameException The
     * <code>domain</code>, <code>key</code>, or <code>value</code>
     * contains an illegal character, or <code>value</code> does not
     * follow the rules for quoting.
     * @exception NullPointerException One of the parameters is null.
     *
     */
    public static ObjectName getInstance(String domain, String key,
                                         String value)
            throws MalformedObjectNameException {
        return new ObjectName(domain, key, value);
    }

    /**
     * <p>Return an instance of ObjectName that can be used anywhere
     * an object obtained with {@link #ObjectName(String, Hashtable)
     * new ObjectName(domain, table)} can be used.  The returned
     * object may be of a subclass of ObjectName.  Calling this method
     * twice with the same parameters may return the same object or
     * two equal but not identical objects.</p>
     *
     * <p>
     * <p>返回一个ObjectName实例,可以在使用{@link #ObjectName(String,Hashtable)new ObjectName(domain,table)}获得的对象的任何地方使
     * 用。
     * 返回的对象可以是ObjectName的子类。使用相同的参数调用此方法两次可能会返回相同的对象或两个相同但不完全相同的对象。</p>。
     * 
     * 
     * @param domain  The domain part of the object name.
     * @param table A hash table containing one or more key
     * properties.  The key of each entry in the table is the key of a
     * key property in the object name.  The associated value in the
     * table is the associated value in the object name.
     *
     * @return an ObjectName corresponding to the given domain and
     * key mappings.
     *
     * @exception MalformedObjectNameException The <code>domain</code>
     * contains an illegal character, or one of the keys or values in
     * <code>table</code> contains an illegal character, or one of the
     * values in <code>table</code> does not follow the rules for
     * quoting.
     * @exception NullPointerException One of the parameters is null.
     *
     */
    public static ObjectName getInstance(String domain,
                                         Hashtable<String,String> table)
        throws MalformedObjectNameException {
        return new ObjectName(domain, table);
    }

    /**
     * <p>Return an instance of ObjectName that can be used anywhere
     * the given object can be used.  The returned object may be of a
     * subclass of ObjectName.  If <code>name</code> is of a subclass
     * of ObjectName, it is not guaranteed that the returned object
     * will be of the same class.</p>
     *
     * <p>The returned value may or may not be identical to
     * <code>name</code>.  Calling this method twice with the same
     * parameters may return the same object or two equal but not
     * identical objects.</p>
     *
     * <p>Since ObjectName is immutable, it is not usually useful to
     * make a copy of an ObjectName.  The principal use of this method
     * is to guard against a malicious caller who might pass an
     * instance of a subclass with surprising behavior to sensitive
     * code.  Such code can call this method to obtain an ObjectName
     * that is known not to have surprising behavior.</p>
     *
     * <p>
     *  <p>返回ObjectName的实例,可以在可以使用给定对象的任何位置使用。返回的对象可以是ObjectName的子类。
     * 如果<code> name </code>是ObjectName的子类,则不能保证返回的对象将是相同的类。</p>。
     * 
     *  <p>返回的值可能与<code> name </code>相同或不一致。使用相同的参数调用此方法两次可能会返回相同的对象或两个相同但不完全相同的对象。</p>
     * 
     *  <p>由于ObjectName是不可变的,因此创建ObjectName的副本通常不是有用的。这种方法的主要用途是防止恶意的调用者,其可能将具有惊人行为的子类的实例传递到敏感代码。
     * 这样的代码可以调用这个方法来获得一个已知没有令人惊讶的行为的ObjectName。</p>。
     * 
     * 
     * @param name an instance of the ObjectName class or of a subclass
     *
     * @return an instance of ObjectName or a subclass that is known to
     * have the same semantics.  If <code>name</code> respects the
     * semantics of ObjectName, then the returned object is equal
     * (though not necessarily identical) to <code>name</code>.
     *
     * @exception NullPointerException The <code>name</code> is null.
     *
     */
    public static ObjectName getInstance(ObjectName name) {
        if (name.getClass().equals(ObjectName.class))
            return name;
        return Util.newObjectName(name.getSerializedNameString());
    }

    /**
     * Construct an object name from the given string.
     *
     * <p>
     *  从给定字符串构造对象名称。
     * 
     * 
     * @param name  A string representation of the object name.
     *
     * @exception MalformedObjectNameException The string passed as a
     * parameter does not have the right format.
     * @exception NullPointerException The <code>name</code> parameter
     * is null.
     */
    public ObjectName(String name)
        throws MalformedObjectNameException {
        construct(name);
    }

    /**
     * Construct an object name with exactly one key property.
     *
     * <p>
     *  构造具有正确一个键属性的对象名称。
     * 
     * 
     * @param domain  The domain part of the object name.
     * @param key  The attribute in the key property of the object name.
     * @param value The value in the key property of the object name.
     *
     * @exception MalformedObjectNameException The
     * <code>domain</code>, <code>key</code>, or <code>value</code>
     * contains an illegal character, or <code>value</code> does not
     * follow the rules for quoting.
     * @exception NullPointerException One of the parameters is null.
     */
    public ObjectName(String domain, String key, String value)
        throws MalformedObjectNameException {
        // If key or value are null a NullPointerException
        // will be thrown by the put method in Hashtable.
        //
        Map<String,String> table = Collections.singletonMap(key, value);
        construct(domain, table);
    }

    /**
     * Construct an object name with several key properties from a Hashtable.
     *
     * <p>
     *  使用Hashtable中的几个关键属性构造对象名称。
     * 
     * 
     * @param domain  The domain part of the object name.
     * @param table A hash table containing one or more key
     * properties.  The key of each entry in the table is the key of a
     * key property in the object name.  The associated value in the
     * table is the associated value in the object name.
     *
     * @exception MalformedObjectNameException The <code>domain</code>
     * contains an illegal character, or one of the keys or values in
     * <code>table</code> contains an illegal character, or one of the
     * values in <code>table</code> does not follow the rules for
     * quoting.
     * @exception NullPointerException One of the parameters is null.
     */
    public ObjectName(String domain, Hashtable<String,String> table)
            throws MalformedObjectNameException {
        construct(domain, table);
        /* The exception for when a key or value in the table is not a
           String is now ClassCastException rather than
           MalformedObjectNameException.  This was not previously
        /* <p>
        /*  String现在是ClassCastException,而不是MalformedObjectNameException。这不是以前
        /* 
        /* 
           specified.  */
    }

    // Category : ObjectName Construction <==============================


    // Category : Getter methods ------------------------------>

    /**
     * Checks whether the object name is a pattern.
     * <p>
     * An object name is a pattern if its domain contains a
     * wildcard or if the object name is a property pattern.
     *
     * <p>
     * 检查对象名称是否为模式。
     * <p>
     *  如果对象名称的域包含通配符或对象名称是属性模式,则对象名称是模式。
     * 
     * 
     * @return  True if the name is a pattern, otherwise false.
     */
    public boolean isPattern() {
        return (_domain_pattern ||
                _property_list_pattern ||
                _property_value_pattern);
    }

    /**
     * Checks whether the object name is a pattern on the domain part.
     *
     * <p>
     *  检查对象名称是否是域部件上的模式。
     * 
     * 
     * @return  True if the name is a domain pattern, otherwise false.
     *
     */
    public boolean isDomainPattern() {
        return _domain_pattern;
    }

    /**
     * Checks whether the object name is a pattern on the key properties.
     * <p>
     * An object name is a pattern on the key properties if it is a
     * pattern on the key property list (e.g. "d:k=v,*") or on the
     * property values (e.g. "d:k=*") or on both (e.g. "d:k=*,*").
     *
     * <p>
     *  检查对象名称是否是键属性上的模式。
     * <p>
     *  对象名称是关键属性上的模式,如果它是关键属性列表上的模式(例如"d：k = v,*")或属性值(例如"d：k = *"), (例如"d：k = *,*")。
     * 
     * 
     * @return  True if the name is a property pattern, otherwise false.
     */
    public boolean isPropertyPattern() {
        return _property_list_pattern || _property_value_pattern;
    }

    /**
     * Checks whether the object name is a pattern on the key property list.
     * <p>
     * For example, "d:k=v,*" and "d:k=*,*" are key property list patterns
     * whereas "d:k=*" is not.
     *
     * <p>
     *  检查对象名称是否为关键属性列表中的模式。
     * <p>
     *  例如,"d：k = v,*"和"d：k = *,*"是关键属性列表模式,而"d：k =
     * 
     * 
     * @return  True if the name is a property list pattern, otherwise false.
     *
     * @since 1.6
     */
    public boolean isPropertyListPattern() {
        return _property_list_pattern;
    }

    /**
     * Checks whether the object name is a pattern on the value part
     * of at least one of the key properties.
     * <p>
     * For example, "d:k=*" and "d:k=*,*" are property value patterns
     * whereas "d:k=v,*" is not.
     *
     * <p>
     *  检查对象名称是否是至少一个键属性的值部分上的模式。
     * <p>
     *  例如,"d：k = *"和"d：k = *,*"是属性值模式,而"d：k = v,*"
     * 
     * 
     * @return  True if the name is a property value pattern, otherwise false.
     *
     * @since 1.6
     */
    public boolean isPropertyValuePattern() {
        return _property_value_pattern;
    }

    /**
     * Checks whether the value associated with a key in a key
     * property is a pattern.
     *
     * <p>
     *  检查与键属性中的键相关联的值是否为模式。
     * 
     * 
     * @param property The property whose value is to be checked.
     *
     * @return True if the value associated with the given key property
     * is a pattern, otherwise false.
     *
     * @exception NullPointerException If <code>property</code> is null.
     * @exception IllegalArgumentException If <code>property</code> is not
     * a valid key property for this ObjectName.
     *
     * @since 1.6
     */
    public boolean isPropertyValuePattern(String property) {
        if (property == null)
            throw new NullPointerException("key property can't be null");
        for (int i = 0; i < _ca_array.length; i++) {
            Property prop = _ca_array[i];
            String key = prop.getKeyString(_canonicalName);
            if (key.equals(property))
                return (prop instanceof PatternProperty);
        }
        throw new IllegalArgumentException("key property not found");
    }

    /**
     * <p>Returns the canonical form of the name; that is, a string
     * representation where the properties are sorted in lexical
     * order.</p>
     *
     * <p>More precisely, the canonical form of the name is a String
     * consisting of the <em>domain part</em>, a colon
     * (<code>:</code>), the <em>canonical key property list</em>, and
     * a <em>pattern indication</em>.</p>
     *
     * <p>The <em>canonical key property list</em> is the same string
     * as described for {@link #getCanonicalKeyPropertyListString()}.</p>
     *
     * <p>The <em>pattern indication</em> is:
     * <ul>
     * <li>empty for an ObjectName
     * that is not a property list pattern;
     * <li>an asterisk for an ObjectName
     * that is a property list pattern with no keys; or
     * <li>a comma and an
     * asterisk (<code>,*</code>) for an ObjectName that is a property
     * list pattern with at least one key.
     * </ul>
     *
     * <p>
     *  <p>返回名称的规范形式;即,字符串表示,其中按照词法顺序对属性进行排序。</p>
     * 
     *  <p>更精确地说,名称的规范形式是一个字符串,它由<em>域部分</em>,冒号(<code>：</code>), / em>和<em>模式指示</em>。</p>
     * 
     *  <p> <em> </em>标准键属性列表与{@link #getCanonicalKeyPropertyListString()}中所述的字符串相同。</p>
     * 
     *  <p> <em>模式指示</em>是：
     * <ul>
     * <li>对于不是属性列表模式的ObjectName,为空; <li> ObjectName的星号,它是没有键的属性列表模式;或对于具有至少一个键的属性列表模式的ObjectName,使用<li>逗号和星
     * 号(<code>,* </code>)。
     * </ul>
     * 
     * 
     * @return The canonical form of the name.
     */
    public String getCanonicalName()  {
        return _canonicalName;
    }

    /**
     * Returns the domain part.
     *
     * <p>
     *  返回域部件。
     * 
     * 
     * @return The domain.
     */
    public String getDomain()  {
        return _canonicalName.substring(0, _domain_length);
    }

    /**
     * Obtains the value associated with a key in a key property.
     *
     * <p>
     *  获取与键属性中的键相关联的值。
     * 
     * 
     * @param property The property whose value is to be obtained.
     *
     * @return The value of the property, or null if there is no such
     * property in this ObjectName.
     *
     * @exception NullPointerException If <code>property</code> is null.
     */
    public String getKeyProperty(String property) {
        return _getKeyPropertyList().get(property);
    }

    /**
     * <p>Returns the key properties as a Map.  The returned
     * value is a Map in which each key is a key in the
     * ObjectName's key property list and each value is the associated
     * value.</p>
     *
     * <p>The returned value must not be modified.</p>
     *
     * <p>
     *  <p>以地图形式返回键属性。返回的值是一个Map,其中每个键是ObjectName的键属性列表中的键,每个值都是关联的值。</p>
     * 
     *  <p>不得修改返回的值。</p>
     * 
     * 
     * @return The table of key properties.
     */
    private Map<String,String> _getKeyPropertyList()  {
        synchronized (this) {
            if (_propertyList == null) {
                // build (lazy eval) the property list from the canonical
                // properties array
                _propertyList = new HashMap<String,String>();
                int len = _ca_array.length;
                Property prop;
                for (int i = len - 1; i >= 0; i--) {
                    prop = _ca_array[i];
                    _propertyList.put(prop.getKeyString(_canonicalName),
                                      prop.getValueString(_canonicalName));
                }
            }
        }
        return _propertyList;
    }

    /**
     * <p>Returns the key properties as a Hashtable.  The returned
     * value is a Hashtable in which each key is a key in the
     * ObjectName's key property list and each value is the associated
     * value.</p>
     *
     * <p>The returned value may be unmodifiable.  If it is
     * modifiable, changing it has no effect on this ObjectName.</p>
     *
     * <p>
     *  <p>将关键属性作为Hashtable返回。返回的值是一个Hashtable,其中每个键是ObjectName的键属性列表中的键,每个值都是关联的值。</p>
     * 
     *  <p>返回的值可能不可修改。如果是可修改的,更改它对此ObjectName没有影响。</p>
     * 
     * 
     * @return The table of key properties.
     */
    // CR 6441274 depends on the modification property defined above
    public Hashtable<String,String> getKeyPropertyList()  {
        return new Hashtable<String,String>(_getKeyPropertyList());
    }

    /**
     * <p>Returns a string representation of the list of key
     * properties specified at creation time.  If this ObjectName was
     * constructed with the constructor {@link #ObjectName(String)},
     * the key properties in the returned String will be in the same
     * order as in the argument to the constructor.</p>
     *
     * <p>
     *  <p>返回创建时指定的键属性列表的字符串表示形式。
     * 如果这个ObjectName是使用构造函数{@link #ObjectName(String)}构造的,则返回的String中的键属性的顺序与构造函数的参数中的顺序相同。</p>。
     * 
     * 
     * @return The key property list string.  This string is
     * independent of whether the ObjectName is a pattern.
     */
    public String getKeyPropertyListString()  {
        // BEWARE : we rebuild the propertyliststring at each call !!
        if (_kp_array.length == 0) return "";

        // the size of the string is the canonical one minus domain
        // part and pattern part
        final int total_size = _canonicalName.length() - _domain_length - 1
            - (_property_list_pattern?2:0);

        final char[] dest_chars = new char[total_size];
        final char[] value = _canonicalName.toCharArray();
        writeKeyPropertyListString(value,dest_chars,0);
        return new String(dest_chars);
    }

    /**
     * <p>Returns the serialized string of the ObjectName.
     * properties specified at creation time.  If this ObjectName was
     * constructed with the constructor {@link #ObjectName(String)},
     * the key properties in the returned String will be in the same
     * order as in the argument to the constructor.</p>
     *
     * <p>
     *  <p>返回ObjectName的序列化字符串。在创建时指定的属性。
     * 如果这个ObjectName是用构造函数{@link #ObjectName(String)}构造的,则返回的String中的键属性的顺序与构造函数的参数的顺序相同。</p>。
     * 
     * 
     * @return The key property list string.  This string is
     * independent of whether the ObjectName is a pattern.
     */
    private String getSerializedNameString()  {

        // the size of the string is the canonical one
        final int total_size = _canonicalName.length();
        final char[] dest_chars = new char[total_size];
        final char[] value = _canonicalName.toCharArray();
        final int offset = _domain_length+1;

        // copy "domain:" into dest_chars
        //
        System.arraycopy(value, 0, dest_chars, 0, offset);

        // Add property list string
        final int end = writeKeyPropertyListString(value,dest_chars,offset);

        // Add ",*" if necessary
        if (_property_list_pattern) {
            if (end == offset)  {
                // Property list string is empty.
                dest_chars[end] = '*';
            } else {
                // Property list string is not empty.
                dest_chars[end]   = ',';
                dest_chars[end+1] = '*';
            }
        }

        return new String(dest_chars);
    }

    /**
     * <p>Write a string representation of the list of key
     * properties specified at creation time in the given array, starting
     * at the specified offset.  If this ObjectName was
     * constructed with the constructor {@link #ObjectName(String)},
     * the key properties in the returned String will be in the same
     * order as in the argument to the constructor.</p>
     *
     * <p>
     * <p>在指定的数组中,从指定的偏移量开始,写一个在创建时指定的键属性列表的字符串表示。
     * 如果这个ObjectName是用构造函数{@link #ObjectName(String)}构造的,则返回的String中的键属性的顺序与构造函数的参数的顺序相同。</p>。
     * 
     * 
     * @return offset + #of chars written
     */
    private int writeKeyPropertyListString(char[] canonicalChars,
                                           char[] data, int offset)  {
        if (_kp_array.length == 0) return offset;

        final char[] dest_chars = data;
        final char[] value = canonicalChars;

        int index = offset;
        final int len = _kp_array.length;
        final int last = len - 1;
        for (int i = 0; i < len; i++) {
            final Property prop = _kp_array[i];
            final int prop_len = prop._key_length + prop._value_length + 1;
            System.arraycopy(value, prop._key_index, dest_chars, index,
                             prop_len);
            index += prop_len;
            if (i < last ) dest_chars[index++] = ',';
        }
        return index;
    }



    /**
     * Returns a string representation of the list of key properties,
     * in which the key properties are sorted in lexical order. This
     * is used in lexicographic comparisons performed in order to
     * select MBeans based on their key property list.  Lexical order
     * is the order implied by {@link String#compareTo(String)
     * String.compareTo(String)}.
     *
     * <p>
     *  返回键属性列表的字符串表示形式,其中键属性按词汇顺序排序。这用于执行的词典比较,以便基于它们的关键属性列表选择MBean。
     * 词法顺序是由{@link String#compareTo(String)String.compareTo(String)}隐含的顺序。
     * 
     * 
     * @return The canonical key property list string.  This string is
     * independent of whether the ObjectName is a pattern.
     */
    public String getCanonicalKeyPropertyListString()  {
        if (_ca_array.length == 0) return "";

        int len = _canonicalName.length();
        if (_property_list_pattern) len -= 2;
        return _canonicalName.substring(_domain_length +1, len);
    }
    // Category : Getter methods <===================================

    // Category : Utilities ---------------------------------------->

    /**
     * <p>Returns a string representation of the object name.  The
     * format of this string is not specified, but users can expect
     * that two ObjectNames return the same string if and only if they
     * are equal.</p>
     *
     * <p>
     *  <p>返回对象名称的字符串表示形式。此字符串的格式未指定,但用户可以预期两个ObjectNames返回相同的字符串,如果且仅当它们相等。</p>
     * 
     * 
     * @return a string representation of this object name.
     */
    @Override
    public String toString()  {
        return getSerializedNameString();
    }

    /**
     * Compares the current object name with another object name.  Two
     * ObjectName instances are equal if and only if their canonical
     * forms are equal.  The canonical form is the string described
     * for {@link #getCanonicalName()}.
     *
     * <p>
     *  将当前对象名称与另一个对象名称进行比较。当且仅当它们的规范形式相等时,两个ObjectName实例是相等的。规范形式是{@link #getCanonicalName()}描述的字符串。
     * 
     * 
     * @param object  The object name that the current object name is to be
     *        compared with.
     *
     * @return True if <code>object</code> is an ObjectName whose
     * canonical form is equal to that of this ObjectName.
     */
    @Override
    public boolean equals(Object object)  {

        // same object case
        if (this == object) return true;

        // object is not an object name case
        if (!(object instanceof ObjectName)) return false;

        // equality when canonical names are the same
        // (because usage of intern())
        ObjectName on = (ObjectName) object;
        String on_string = on._canonicalName;
        if (_canonicalName == on_string) return true;  // ES: OK

        // Because we are sharing canonical form between object names,
        // we have finished the comparison at this stage ==> unequal
        return false;
   }

    /**
     * Returns a hash code for this object name.
     *
     * <p>
     *  返回此对象名的哈希代码。
     * 
     */
    @Override
    public int hashCode() {
        return _canonicalName.hashCode();
    }

    /**
     * <p>Returns a quoted form of the given String, suitable for
     * inclusion in an ObjectName.  The returned value can be used as
     * the value associated with a key in an ObjectName.  The String
     * <code>s</code> may contain any character.  Appropriate quoting
     * ensures that the returned value is legal in an ObjectName.</p>
     *
     * <p>The returned value consists of a quote ('"'), a sequence of
     * characters corresponding to the characters of <code>s</code>,
     * and another quote.  Characters in <code>s</code> appear
     * unchanged within the returned value except:</p>
     *
     * <ul>
     * <li>A quote ('"') is replaced by a backslash (\) followed by a quote.</li>
     * <li>An asterisk ('*') is replaced by a backslash (\) followed by an
     * asterisk.</li>
     * <li>A question mark ('?') is replaced by a backslash (\) followed by
     * a question mark.</li>
     * <li>A backslash ('\') is replaced by two backslashes.</li>
     * <li>A newline character (the character '\n' in Java) is replaced
     * by a backslash followed by the character '\n'.</li>
     * </ul>
     *
     * <p>
     *  <p>返回给定String的引用形式,适合包含在ObjectName中。返回的值可以用作与ObjectName中的键相关联的值。字符串<code> s </code>可以包含任何字符。
     * 适当的引用可确保返回的值在ObjectName中是合法的。</p>。
     * 
     * <p>返回值由一个引号('"'),一个与<code> s </code>的字符相对应的字符序列,以及另一个引号。<code> s </code>在以下范围内：</p>
     * 
     * <ul>
     *  <li>引号('"')由反斜杠(\)后接引号替换。</li> <li>星号('*')由反斜杠(\)替代,后跟星号。 </li> <li>问号('?')由反斜杠(\)替换为问号。
     * </li> <li>反斜杠('\')由两个反斜杠代替。 / li> <li>换行符(在Java中的字符'\ n')被反斜杠替换,后面跟着字符'\ n'。</li>。
     * </ul>
     * 
     * 
     * @param s the String to be quoted.
     *
     * @return the quoted String.
     *
     * @exception NullPointerException if <code>s</code> is null.
     *
     */
    public static String quote(String s) {
        final StringBuilder buf = new StringBuilder("\"");
        final int len = s.length();
        for (int i = 0; i < len; i++) {
            char c = s.charAt(i);
            switch (c) {
            case '\n':
                c = 'n';
                buf.append('\\');
                break;
            case '\\':
            case '\"':
            case '*':
            case '?':
                buf.append('\\');
                break;
            }
            buf.append(c);
        }
        buf.append('"');
        return buf.toString();
    }

    /**
     * <p>Returns an unquoted form of the given String.  If
     * <code>q</code> is a String returned by {@link #quote quote(s)},
     * then <code>unquote(q).equals(s)</code>.  If there is no String
     * <code>s</code> for which <code>quote(s).equals(q)</code>, then
     * unquote(q) throws an IllegalArgumentException.</p>
     *
     * <p>These rules imply that there is a one-to-one mapping between
     * quoted and unquoted forms.</p>
     *
     * <p>
     *  <p>返回给定String的无引号形式。
     * 如果<code> q </code>是{@link #quote quote(s)}返回的字符串,则<code> unquote(q).equals(s)</code>。
     * 如果没有<code> quote(s).equals(q)</code>的String <code> s </code>,那么unquote(q)会抛出IllegalArgumentException。
     * 如果<code> q </code>是{@link #quote quote(s)}返回的字符串,则<code> unquote(q).equals(s)</code>。</p>。
     * 
     *  <p>这些规则意味着引号和非引号形式之间存在一对一的映射。</p>
     * 
     * 
     * @param q the String to be unquoted.
     *
     * @return the unquoted String.
     *
     * @exception IllegalArgumentException if <code>q</code> could not
     * have been returned by the {@link #quote} method, for instance
     * if it does not begin and end with a quote (").
     *
     * @exception NullPointerException if <code>q</code> is null.
     *
     */
    public static String unquote(String q) {
        final StringBuilder buf = new StringBuilder();
        final int len = q.length();
        if (len < 2 || q.charAt(0) != '"' || q.charAt(len - 1) != '"')
            throw new IllegalArgumentException("Argument not quoted");
        for (int i = 1; i < len - 1; i++) {
            char c = q.charAt(i);
            if (c == '\\') {
                if (i == len - 2)
                    throw new IllegalArgumentException("Trailing backslash");
                c = q.charAt(++i);
                switch (c) {
                case 'n':
                    c = '\n';
                    break;
                case '\\':
                case '\"':
                case '*':
                case '?':
                    break;
                default:
                  throw new IllegalArgumentException(
                                   "Bad character '" + c + "' after backslash");
                }
            } else {
                switch (c) {
                    case '*' :
                    case '?' :
                    case '\"':
                    case '\n':
                         throw new IllegalArgumentException(
                                          "Invalid unescaped character '" + c +
                                          "' in the string to unquote");
                }
            }
            buf.append(c);
        }
        return buf.toString();
    }

    /**
     * Defines the wildcard "*:*" ObjectName.
     *
     * <p>
     *  定义通配符"*：*"ObjectName。
     * 
     * 
     * @since 1.6
     */
    public static final ObjectName WILDCARD = Util.newObjectName("*:*");

    // Category : Utilities <===================================

    // Category : QueryExp Interface ---------------------------------------->

    /**
     * <p>Test whether this ObjectName, which may be a pattern,
     * matches another ObjectName.  If <code>name</code> is a pattern,
     * the result is false.  If this ObjectName is a pattern, the
     * result is true if and only if <code>name</code> matches the
     * pattern.  If neither this ObjectName nor <code>name</code> is
     * a pattern, the result is true if and only if the two
     * ObjectNames are equal as described for the {@link
     * #equals(Object)} method.</p>
     *
     * <p>
     * <p>测试此ObjectName(可能是模式)是否与另一个ObjectName匹配。如果<code> name </code>是模式,则结果为false。
     * 如果此ObjectName是一个模式,当且仅当<code> name </code>与模式匹配时,结果为true。
     * 如果这个ObjectName和<code> name </code>都不是一个模式,当且仅当两个ObjectNames等于{@link #equals(Object)}方法时,结果才为真。</p>。
     * 
     * 
     * @param name The name of the MBean to compare to.
     *
     * @return True if <code>name</code> matches this ObjectName.
     *
     * @exception NullPointerException if <code>name</code> is null.
     *
     */
    public boolean apply(ObjectName name) {

        if (name == null) throw new NullPointerException();

        if (name._domain_pattern ||
            name._property_list_pattern ||
            name._property_value_pattern)
            return false;

        // No pattern
        if (!_domain_pattern &&
            !_property_list_pattern &&
            !_property_value_pattern)
            return _canonicalName.equals(name._canonicalName);

        return matchDomains(name) && matchKeys(name);
    }

    private final boolean matchDomains(ObjectName name) {
        if (_domain_pattern) {
            // wildmatch domains
            // This ObjectName is the pattern
            // The other ObjectName is the string.
            return Util.wildmatch(name.getDomain(),getDomain());
        }
        return getDomain().equals(name.getDomain());
    }

    private final boolean matchKeys(ObjectName name) {
        // If key property value pattern but not key property list
        // pattern, then the number of key properties must be equal
        //
        if (_property_value_pattern &&
            !_property_list_pattern &&
            (name._ca_array.length != _ca_array.length))
                return false;

        // If key property value pattern or key property list pattern,
        // then every property inside pattern should exist in name
        //
        if (_property_value_pattern || _property_list_pattern) {
            final Map<String,String> nameProps = name._getKeyPropertyList();
            final Property[] props = _ca_array;
            final String cn = _canonicalName;
            for (int i = props.length - 1; i >= 0 ; i--) {
                // Find value in given object name for key at current
                // index in receiver
                //
                final Property p = props[i];
                final String   k = p.getKeyString(cn);
                final String   v = nameProps.get(k);
                // Did we find a value for this key ?
                //
                if (v == null) return false;
                // If this property is ok (same key, same value), go to next
                //
                if (_property_value_pattern && (p instanceof PatternProperty)) {
                    // wildmatch key property values
                    // p is the property pattern, v is the string
                    if (Util.wildmatch(v,p.getValueString(cn)))
                        continue;
                    else
                        return false;
                }
                if (v.equals(p.getValueString(cn))) continue;
                return false;
            }
            return true;
        }

        // If no pattern, then canonical names must be equal
        //
        final String p1 = name.getCanonicalKeyPropertyListString();
        final String p2 = getCanonicalKeyPropertyListString();
        return (p1.equals(p2));
    }

    /* Method inherited from QueryExp, no implementation needed here
       because ObjectName is not relative to an MBeanServer and does
       not contain a subquery.
    /* <p>
    /*  因为ObjectName不是相对于MBeanServer的,并且不包含子查询。
    /* 
    */
    public void setMBeanServer(MBeanServer mbs) { }

    // Category : QueryExp Interface <=========================

    // Category : Comparable Interface ---------------------------------------->

    /**
     * <p>Compares two ObjectName instances. The ordering relation between
     * ObjectNames is not completely specified but is intended to be such
     * that a sorted list of ObjectNames will appear in an order that is
     * convenient for a person to read.</p>
     *
     * <p>In particular, if the two ObjectName instances have different
     * domains then their order is the lexicographical order of the domains.
     * The ordering of the key property list remains unspecified.</p>
     *
     * <p>For example, the ObjectName instances below:</p>
     * <ul>
     * <li>Shapes:type=Square,name=3</li>
     * <li>Colors:type=Red,name=2</li>
     * <li>Shapes:type=Triangle,side=isosceles,name=2</li>
     * <li>Colors:type=Red,name=1</li>
     * <li>Shapes:type=Square,name=1</li>
     * <li>Colors:type=Blue,name=1</li>
     * <li>Shapes:type=Square,name=2</li>
     * <li>JMImplementation:type=MBeanServerDelegate</li>
     * <li>Shapes:type=Triangle,side=scalene,name=1</li>
     * </ul>
     * <p>could be ordered as follows:</p>
     * <ul>
     * <li>Colors:type=Blue,name=1</li>
     * <li>Colors:type=Red,name=1</li>
     * <li>Colors:type=Red,name=2</li>
     * <li>JMImplementation:type=MBeanServerDelegate</li>
     * <li>Shapes:type=Square,name=1</li>
     * <li>Shapes:type=Square,name=2</li>
     * <li>Shapes:type=Square,name=3</li>
     * <li>Shapes:type=Triangle,side=scalene,name=1</li>
     * <li>Shapes:type=Triangle,side=isosceles,name=2</li>
     * </ul>
     *
     * <p>
     *  <p>比较两个ObjectName实例。 ObjectNames之间的排序关系没有完全指定,而是希望对象名称的排序列表将按照方便人们阅读的顺序显示。</p>
     * 
     *  <p>特别是,如果两个ObjectName实例具有不同的域,那么它们的顺序是域的词典顺序。关键属性列表的顺序仍未指定。</p>
     * 
     *  <p>例如,下面的ObjectName实例：</p>
     * <ul>
     *  <li>形状：type = Square,name = 3 </li> <li>颜色：type = Red,name = 2 </li> <li>形状：type = Triangle,side = i
     * sosceles,name = li> <li>颜色：type = Blue,name = 1 </li> <li>形状：type = Square,name = 1 </li> <li>形状：type
     *  = Square,name = 2 </li> <li> JMI实现：type = MBeanServerDelegate </li> <li>形状：type = Triangle,side = sc
     * 
     * @param name the ObjectName to be compared.
     *
     * @return a negative integer, zero, or a positive integer as this
     *         ObjectName is less than, equal to, or greater than the
     *         specified ObjectName.
     *
     * @since 1.6
     */
    public int compareTo(ObjectName name) {
        // Quick optimization:
        //
        if (name == this) return 0;

        // (1) Compare domains
        //
        int domainValue = this.getDomain().compareTo(name.getDomain());
        if (domainValue != 0)
            return domainValue;

        // (2) Compare "type=" keys
        //
        // Within a given domain, all names with missing or empty "type="
        // come before all names with non-empty type.
        //
        // When both types are missing or empty, canonical-name ordering
        // applies which is a total order.
        //
        String thisTypeKey = this.getKeyProperty("type");
        String anotherTypeKey = name.getKeyProperty("type");
        if (thisTypeKey == null)
            thisTypeKey = "";
        if (anotherTypeKey == null)
            anotherTypeKey = "";
        int typeKeyValue = thisTypeKey.compareTo(anotherTypeKey);
        if (typeKeyValue != 0)
            return typeKeyValue;

        // (3) Compare canonical names
        //
        return this.getCanonicalName().compareTo(name.getCanonicalName());
    }

    // Category : Comparable Interface <=========================

    // Public methods <========================================

}
