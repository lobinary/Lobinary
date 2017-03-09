/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2010, Oracle and/or its affiliates. All rights reserved.
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

package org.omg.CORBA;


/**
 * The Java mapping of the IDL enum <code>TCKind</code>, which
 * specifies the kind of a <code>TypeCode</code> object.  There is
 * one kind for each primitive and essential IDL data type.
 * <P>
 * The class <code>TCKind</code> consists of:
 * <UL>
 * <LI>a set of <code>int</code> constants, one for each
 * kind of IDL data type.  These <code>int</code> constants
 * make it possible to use a <code>switch</code> statement.
 * <LI>a set of <code>TCKind</code> constants, one for each
 * kind of IDL data type.  The <code>value</code> field for
 * each <code>TCKind</code> instance is initialized with
 * the <code>int</code> constant that corresponds with
 * the IDL data type that the instance represents.
 * <LI>the method <code>from_int</code>for converting
 * an <code>int</code> to its
 * corresponding <code>TCKind</code> instance
 * <P>Example:
 * <PRE>
 *      org.omg.CORBA.TCKind k = org.omg.CORBA.TCKind.from_int(
 *                         org.omg.CORBA.TCKind._tk_string);
 * </PRE>
 * The variable <code>k</code> represents the <code>TCKind</code>
 * instance for the IDL type <code>string</code>, which is
 * <code>tk_string</code>.
 * <P>
 * <LI>the method <code>value</code> for accessing the
 * <code>_value</code> field of a <code>TCKind</code> constant
 * <P>Example:
 * <PRE>
 *   int i = org.omg.CORBA.TCKind.tk_char.value();
 * </PRE>
 * The variable <code>i</code> represents 9, the value for the
 * IDL data type <code>char</code>.
 * </UL>
 * <P>The <code>value</code> field of a <code>TCKind</code> instance
 * is the CDR encoding used for a <code>TypeCode</code> object in
 * an IIOP message.
 * <p>
 *  IDL枚举<code> TCKind </code>的Java映射,它指定<code> TypeCode </code>对象的种类。每种原语和基本的IDL数据类型都有一种类型。
 * <P>
 *  类<code> TCKind </code>包括：
 * <UL>
 *  <LI>一组<code> int </code>常量,每种类型的IDL数据类型一个。这些<code> int </code>常量使得可以使用<code> switch </code>语句。
 *  <LI>一组<code> TCKind </code>常量,每种类型的IDL数据类型一个。
 * 每个<code> TCKind </code>实例的<code> value </code>字段使用与实例表示的IDL数据类型相对应的<code> int </code>常数初始化。
 *  <li>用于将<code> int </code>转换为其对应的<code> TCKind </code>实例</>的方法<code> from_int </code>。
 * <PRE>
 *  org.omg.CORBA.TCKind k = org.omg.CORBA.TCKind.from_int(org.omg.CORBA.TCKind._tk_string);
 * </PRE>
 *  变量<code> k </code>表示IDL类型<code> string </code>的<code> TCKind </code>实例,即<code> tk_string </code>。
 * <P>
 *  访问<code> TCKind </code>常量<P>的<code> _value </code>字段的<code>值</>
 * <PRE>
 *  int i = org.omg.CORBA.TCKind.tk_char.value();
 * </PRE>
 *  变量<code> i </code>表示9,IDL数据类型的值<code> char </code>。
 * </UL>
 * <code> TCKind </code>实例的<code> value </code>字段是用于IIOP消息中的<code> TypeCode </code>对象的CDR编码。
 * 
 */

public class TCKind {

    /**
     * The <code>int</code> constant for a <code>null</code> IDL data type.
     * <p>
     *  IDL数据类型的<code> null </code>常量<code> int </code>
     * 
     */
    public static final int _tk_null = 0;

    /**
     * The <code>int</code> constant for the IDL data type <code>void</code>.
     * <p>
     *  IDL数据类型<code> void </code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_void = 1;

    /**
     * The <code>int</code> constant for the IDL data type <code>short</code>.
     * <p>
     *  IDL数据类型<code> short </code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_short = 2;

    /**
     * The <code>int</code> constant for the IDL data type <code>long</code>.
     * <p>
     *  IDL数据类型<code> long </code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_long = 3;

    /**
     * The <code>int</code> constant for the IDL data type <code>ushort</code>.
     * <p>
     *  IDL数据类型<code> ushort </code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_ushort = 4;

    /**
     * The <code>int</code> constant for the IDL data type <code>ulong</code>.
     * <p>
     *  IDL数据类型<code> ulong </code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_ulong = 5;

    /**
     * The <code>int</code> constant for the IDL data type <code>float</code>.
     * <p>
     *  IDL数据类型<code> float </code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_float = 6;

    /**
     * The <code>int</code> constant for the IDL data type <code>double</code>.
     * <p>
     *  IDL数据类型<code> double </code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_double = 7;

    /**
     * The <code>int</code> constant for the IDL data type <code>boolean</code>.
     * <p>
     *  IDL数据类型的<code> int </code>常量<code> boolean </code>。
     * 
     */
    public static final int _tk_boolean = 8;

    /**
     * The <code>int</code> constant for the IDL data type <code>char</code>.
     * <p>
     *  IDL数据类型<code> char </code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_char = 9;

    /**
     * The <code>int</code> constant for the IDL data type <code>octet</code>.
     * <p>
     *  IDL数据类型<code> octet </code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_octet = 10;

    /**
     * The <code>int</code> constant for the IDL data type <code>any</code>.
     * <p>
     *  IDL数据类型的<code> int </code>常量<code> any </code>。
     * 
     */
    public static final int _tk_any = 11;

    /**
     * The <code>int</code> constant for the IDL data type <code>TypeCode</code>.
     * <p>
     *  IDL数据类型<code> TypeCode </code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_TypeCode = 12;

    /**
     * The <code>int</code> constant for the IDL data type <code>Principal</code>.
     * <p>
     *  IDL数据类型<code> Principal </code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_Principal = 13;

    /**
     * The <code>int</code> constant for the IDL data type <code>objref</code>.
     * <p>
     *  IDL数据类型<code> objref </code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_objref = 14;

    /**
     * The <code>int</code> constant for the IDL data type <code>struct</code>.
     * <p>
     *  IDL数据类型<code> struct </code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_struct = 15;

    /**
     * The <code>int</code> constant for the IDL data type <code>union</code>.
     * <p>
     *  IDL数据类型<code> union </code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_union = 16;

    /**
     * The <code>int</code> constant for the IDL data type <code>enum</code>.
     * <p>
     *  IDL数据类型<code>枚举</code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_enum = 17;

    /**
     * The <code>int</code> constant for the IDL data type <code>string</code>.
     * <p>
     * IDL数据类型<code> string </code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_string = 18;

    /**
     * The <code>int</code> constant for the IDL data type <code>sequence</code>.
     * <p>
     *  IDL数据类型<code>序列</code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_sequence = 19;

    /**
     * The <code>int</code> constant for the IDL data type <code>array</code>.
     * <p>
     *  IDL数据类型<code>数组</code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_array = 20;

    /**
     * The <code>int</code> constant for the IDL data type <code>alias</code>.
     * <p>
     *  IDL数据类型<code>别名</code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_alias = 21;

    /**
     * The <code>int</code> constant for the IDL data type <code>except</code>.
     * <p>
     *  除了</code>之外的IDL数据类型<code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_except = 22;

    /**
     * The <code>int</code> constant for the IDL data type <code>longlong</code>.
     * <p>
     *  IDL数据类型<code> longlong </code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_longlong = 23;

    /**
     * The <code>int</code> constant for the IDL data type <code>ulonglong</code>.
     * <p>
     *  IDL数据类型<code> ulonglong </code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_ulonglong = 24;

    /**
     * The <code>int</code> constant for the IDL data type <code>longdouble</code>.
     * <p>
     *  IDL数据类型<code> longdouble </code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_longdouble = 25;

    /**
     * The <code>int</code> constant for the IDL data type <code>wchar</code>.
     * <p>
     *  IDL数据类型<code> wchar </code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_wchar = 26;

    /**
     * The <code>int</code> constant for the IDL data type <code>wstring</code>.
     * <p>
     *  IDL数据类型<code> wstring </code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_wstring = 27;

    /**
     * The <code>int</code> constant for the IDL data type <code>fixed</code>.
     * <p>
     *  IDL数据类型<code>固定</code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_fixed = 28;

    /**
     * The <code>int</code> constant for the IDL data type <code>value</code>.
     * <p>
     *  IDL数据类型<code>值</code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_value = 29;             // orbos 98-01-18: Objects By Value

    /**
     * The <code>int</code> constant for the IDL data type <code>value_box</code>.
     * <p>
     *  IDL数据类型<code> value_box </code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_value_box = 30; // orbos 98-01-18: Objects By Value

    /**
     * The <code>int</code> constant for the IDL data type <code>native</code>.
     * <p>
     *  IDL数据类型<code> native </code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_native = 31;        // Verify

    /**
     * The <code>int</code> constant for the IDL data type <code>abstract interface</code>.
     * <p>
     *  IDL数据类型<code>抽象接口</code>的<code> int </code>常量。
     * 
     */
    public static final int _tk_abstract_interface = 32;


    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_null</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_null </code>初始化。
     * 
     */
    public static final TCKind tk_null = new TCKind(_tk_null);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_void</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_void </code>进行初始化。
     * 
     */
    public static final TCKind tk_void = new TCKind(_tk_void);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_short</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_short </code>初始化。
     * 
     */
    public static final TCKind tk_short = new TCKind(_tk_short);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_long</code>.
     * <p>
     * <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_long </code>初始化。
     * 
     */
    public static final TCKind tk_long = new TCKind(_tk_long);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_ushort</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段用<code> TCKind._tk_ushort </code>初始化。
     * 
     */
    public static final TCKind tk_ushort = new TCKind(_tk_ushort);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_ulong</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_ulong </code>初始化。
     * 
     */
    public static final TCKind tk_ulong = new TCKind(_tk_ulong);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_float</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_float </code>初始化。
     * 
     */
    public static final TCKind tk_float = new TCKind(_tk_float);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_double</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_double </code>初始化。
     * 
     */
    public static final TCKind tk_double = new TCKind(_tk_double);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_boolean</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_boolean </code>初始化。
     * 
     */
    public static final TCKind tk_boolean = new TCKind(_tk_boolean);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_char</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_char </code>初始化。
     * 
     */
    public static final TCKind tk_char = new TCKind(_tk_char);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_octet</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_octet </code>初始化。
     * 
     */
    public static final TCKind tk_octet = new TCKind(_tk_octet);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_any</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_any </code>初始化。
     * 
     */
    public static final TCKind tk_any = new TCKind(_tk_any);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_TypeCode</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code>值</code>字段使用<code> TCKind._tk_TypeCode </code>初始化。
     * 
     */
    public static final TCKind tk_TypeCode = new TCKind(_tk_TypeCode);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_Principal</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_Principal </code>初始化。
     * 
     */
    public static final TCKind tk_Principal = new TCKind(_tk_Principal);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_objref</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_objref </code>初始化。
     * 
     */
    public static final TCKind tk_objref = new TCKind(_tk_objref);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_struct</code>.
     * <p>
     * <code> TCKind </code>常量,其<code>值</code>字段使用<code> TCKind._tk_struct </code>初始化。
     * 
     */
    public static final TCKind tk_struct = new TCKind(_tk_struct);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_union</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_union </code>初始化。
     * 
     */
    public static final TCKind tk_union = new TCKind(_tk_union);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_enum</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_enum </code>初始化。
     * 
     */
    public static final TCKind tk_enum = new TCKind(_tk_enum);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_string</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_string </code>进行初始化。
     * 
     */
    public static final TCKind tk_string = new TCKind(_tk_string);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_sequence</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段用<code> TCKind._tk_sequence </code>初始化。
     * 
     */
    public static final TCKind tk_sequence = new TCKind(_tk_sequence);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_array</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_array </code>初始化。
     * 
     */
    public static final TCKind tk_array = new TCKind(_tk_array);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_alias</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_alias </code>进行初始化。
     * 
     */
    public static final TCKind tk_alias = new TCKind(_tk_alias);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_except</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_except </code>初始化。
     * 
     */
    public static final TCKind tk_except = new TCKind(_tk_except);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_longlong</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_longlong </code>初始化。
     * 
     */
    public static final TCKind tk_longlong = new TCKind(_tk_longlong);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_ulonglong</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_ulonglong </code>初始化。
     * 
     */
    public static final TCKind tk_ulonglong = new TCKind(_tk_ulonglong);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_longdouble</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_longdouble </code>初始化。
     * 
     */
    public static final TCKind tk_longdouble = new TCKind(_tk_longdouble);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_wchar</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_wchar </code>进行初始化。
     * 
     */
    public static final TCKind tk_wchar = new TCKind(_tk_wchar);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_wstring</code>.
     * <p>
     * <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_wstring </code>初始化。
     * 
     */
    public static final TCKind tk_wstring = new TCKind(_tk_wstring);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_fixed</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段用<code> TCKind._tk_fixed </code>初始化。
     * 
     */
    public static final TCKind tk_fixed = new TCKind(_tk_fixed);

    // orbos 98-01-18: Objects By Value -- begin

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_value</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_value </code>初始化。
     * 
     */
    public static final TCKind tk_value = new TCKind(_tk_value);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_value_box</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_value_box </code>初始化。
     * 
     */
    public static final TCKind tk_value_box = new TCKind(_tk_value_box);
    // orbos 98-01-18: Objects By Value -- end

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_native</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段用<code> TCKind._tk_native </code>初始化。
     * 
     */
    public static final TCKind tk_native = new TCKind(_tk_native);

    /**
     * The <code>TCKind</code> constant whose <code>value</code> field is
     * initialized with <code>TCKind._tk_abstract_interface</code>.
     * <p>
     *  <code> TCKind </code>常量,其<code> value </code>字段使用<code> TCKind._tk_abstract_interface </code>初始化。
     * 
     */
    public static final TCKind tk_abstract_interface = new TCKind(_tk_abstract_interface);




    /**
     * Retrieves the value of this <code>TCKind</code> instance.
     *
     * <p>
     *  检索此<code> TCKind </code>实例的值。
     * 
     * 
     * @return  the <code>int</code> that represents the kind of
     * IDL data type for this <code>TCKind</code> instance
     */
    public int value() {
        return _value;
    }

    /**
     * Converts the given <code>int</code> to the corresponding
     * <code>TCKind</code> instance.
     *
     * <p>
     *  将给定的<code> int </code>转换为相应的<code> TCKind </code>实例。
     * 
     * 
     * @param i the <code>int</code> to convert.  It must be one of
     *         the <code>int</code> constants in the class
     *         <code>TCKind</code>.
     * @return  the <code>TCKind</code> instance whose <code>value</code>
     * field matches the given <code>int</code>
     * @exception  BAD_PARAM  if the given <code>int</code> does not
     * match the <code>_value</code> field of
     * any <code>TCKind</code> instance
     */
    public static TCKind from_int(int i) {
        switch (i) {
        case _tk_null:
            return tk_null;
        case _tk_void:
            return tk_void;
        case _tk_short:
            return tk_short;
        case _tk_long:
            return tk_long;
        case _tk_ushort:
            return tk_ushort;
        case _tk_ulong:
            return tk_ulong;
        case _tk_float:
            return tk_float;
        case _tk_double:
            return tk_double;
        case _tk_boolean:
            return tk_boolean;
        case _tk_char:
            return tk_char;
        case _tk_octet:
            return tk_octet;
        case _tk_any:
            return tk_any;
        case _tk_TypeCode:
            return tk_TypeCode;
        case _tk_Principal:
            return tk_Principal;
        case _tk_objref:
            return tk_objref;
        case _tk_struct:
            return tk_struct;
        case _tk_union:
            return tk_union;
        case _tk_enum:
            return tk_enum;
        case _tk_string:
            return tk_string;
        case _tk_sequence:
            return tk_sequence;
        case _tk_array:
            return tk_array;
        case _tk_alias:
            return tk_alias;
        case _tk_except:
            return tk_except;
        case _tk_longlong:
            return tk_longlong;
        case _tk_ulonglong:
            return tk_ulonglong;
        case _tk_longdouble:
            return tk_longdouble;
        case _tk_wchar:
            return tk_wchar;
        case _tk_wstring:
            return tk_wstring;
        case _tk_fixed:
            return tk_fixed;
        case _tk_value:         // orbos 98-01-18: Objects By Value
            return tk_value;
        case _tk_value_box:     // orbos 98-01-18: Objects By Value
            return tk_value_box;
        case _tk_native:
            return tk_native;
        case _tk_abstract_interface:
            return tk_abstract_interface;
        default:
            throw new org.omg.CORBA.BAD_PARAM();
        }
    }


    /**
    * Creates a new <code>TCKind</code> instance initialized with the given
    * <code>int</code>.
    * <p>
    *  创建用给定的<code> int </code>初始化的一个新的<code> TCKind </code>实例。
    * 
    * @deprecated Do not use this constructor as this method should be private
    * according to the OMG specification. Use {@link #from_int(int)} instead.
    *
    * @param  _value the <code>int</code> to convert.  It must be one of
    *         the <code>int</code> constants in the class
    *         <code>TCKind</code>.
    */
    @Deprecated
    protected TCKind(int _value){
        this._value = _value;
    }
    private int _value;
}
