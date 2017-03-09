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

package javax.xml.bind;

/**
 * <p>
 * The DatatypeConverterInterface is for JAXB provider use only. A
 * JAXB provider must supply a class that implements this interface.
 * JAXB Providers are required to call the
 * {@link DatatypeConverter#setDatatypeConverter(DatatypeConverterInterface)
 * DatatypeConverter.setDatatypeConverter} api at
 * some point before the first marshal or unmarshal operation (perhaps during
 * the call to JAXBContext.newInstance).  This step is necessary to configure
 * the converter that should be used to perform the print and parse
 * functionality.  Calling this api repeatedly will have no effect - the
 * DatatypeConverter instance passed into the first invocation is the one that
 * will be used from then on.
 * </p>
 *
 * <p>
 * This interface defines the parse and print methods. There is one
 * parse and print method for each XML schema datatype specified in the
 * the default binding Table 5-1 in the JAXB specification.
 * </p>
 *
 * <p>
 * The parse and print methods defined here are invoked by the static parse
 * and print methods defined in the {@link DatatypeConverter DatatypeConverter}
 * class.
 * </p>
 *
 * <p>
 * A parse method for a XML schema datatype must be capable of converting any
 * lexical representation of the XML schema datatype ( specified by the
 * <a href="http://www.w3.org/TR/xmlschema-2/"> XML Schema Part2: Datatypes
 * specification</a> into a value in the value space of the XML schema datatype.
 * If an error is encountered during conversion, then an IllegalArgumentException
 * or a subclass of IllegalArgumentException must be thrown by the method.
 *
 * </p>
 *
 * <p>
 * A print method for a XML schema datatype can output any lexical
 * representation that is valid with respect to the XML schema datatype.
 * If an error is encountered during conversion, then an IllegalArgumentException,
 * or a subclass of IllegalArgumentException must be thrown by the method.
 * </p>
 *
 * The prefix xsd: is used to refer to XML schema datatypes
 * <a href="http://www.w3.org/TR/xmlschema-2/"> XML Schema Part2: Datatypes
 * specification.</a>
 *
 * <p>
 * <p>
 * <p>
 *  DatatypeConverterInterface仅供JAXB提供程序使用。 JAXB提供程序必须提供实现此接口的类。
 *  JAXB提供程序需要在第一个编组或解组操作之前的某个时间(可能在调用JAXBContext.newInstance期间)调用{@link DatatypeConverter#setDatatypeConverter(DatatypeConverterInterface)DatatypeConverter.setDatatypeConverter}
 *  api。
 *  DatatypeConverterInterface仅供JAXB提供程序使用。 JAXB提供程序必须提供实现此接口的类。此步骤对于配置应用于执行打印和解析功能的转换器是必要的。
 * 重复调用此api将不起作用 - 传递到第一次调用的DatatypeConverter实例是从那时起将使用的实例。
 * </p>
 * 
 * <p>
 *  此接口定义了解析和打印方法。对于JAXB规范中的默认绑定表5-1中指定的每个XML模式数据类型,都有一个解析和打印方法。
 * </p>
 * 
 * <p>
 *  此处定义的解析和打印方法由{@link DatatypeConverter DatatypeConverter}类中定义的静态解析和打印方法调用。
 * </p>
 * 
 * <p>
 * XML模式数据类型的解析方法必须能够转换XML模式数据类型的任何词法表示(由<a href="http://www.w3.org/TR/xmlschema-2/"> XML模式指定) Part2：数据类
 * 型规范</a>转换为XML模式数据类型的值空间中的值如果在转换期间遇到错误,则必须由该方法抛出IllegalArgumentException或IllegalArgumentException的子类。
 * 
 * </p>
 * 
 * <p>
 *  XML模式数据类型的打印方法可以输出对于XML模式数据类型有效的任何词法表示。
 * 如果在转换期间遇到错误,则必须由方法抛出IllegalArgumentException或IllegalArgumentException的子类。
 * </p>
 * 
 *  前缀xsd：用于引用XML模式数据类型<a href="http://www.w3.org/TR/xmlschema-2/"> XML模式Part2：数据类型规范</a>。
 * 
 * <p>
 * 
 * @author <ul><li>Sekhar Vajjhala, Sun Microsystems, Inc.</li><li>Joe Fialli, Sun Microsystems Inc.</li><li>Kohsuke Kawaguchi, Sun Microsystems, Inc.</li><li>Ryan Shoemaker,Sun Microsystems Inc.</li></ul>
 * @see DatatypeConverter
 * @see ParseConversionEvent
 * @see PrintConversionEvent
 * @since JAXB1.0
 */

public interface DatatypeConverterInterface {
    /**
     * <p>
     * Convert the string argument into a string.
     * <p>
     * <p>
     *  将字符串参数转换为字符串。
     * 
     * 
     * @param lexicalXSDString
     *     A lexical representation of the XML Schema datatype xsd:string
     * @return
     *     A string that is the same as the input string.
     */
    public String parseString( String lexicalXSDString );

    /**
     * <p>
     * Convert the string argument into a BigInteger value.
     * <p>
     * <p>
     *  将字符串参数转换为BigInteger值。
     * 
     * 
     * @param lexicalXSDInteger
     *     A string containing a lexical representation of
     *     xsd:integer.
     * @return
     *     A BigInteger value represented by the string argument.
     * @throws NumberFormatException <code>lexicalXSDInteger</code> is not a valid string representation of a {@link java.math.BigInteger} value.
     */
    public java.math.BigInteger parseInteger( String lexicalXSDInteger );

    /**
     * <p>
     * Convert the string argument into an int value.
     * <p>
     * <p>
     *  将字符串参数转换为int值。
     * 
     * 
     * @param lexicalXSDInt
     *     A string containing a lexical representation of
     *     xsd:int.
     * @return
     *     An int value represented byte the string argument.
     * @throws NumberFormatException <code>lexicalXSDInt</code> is not a valid string representation of an <code>int</code> value.
     */
    public int parseInt( String lexicalXSDInt );

    /**
     * <p>
     * Converts the string argument into a long value.
     * <p>
     * <p>
     *  将字符串参数转换为长整型值。
     * 
     * 
     * @param lexicalXSDLong
     *     A string containing lexical representation of
     *     xsd:long.
     * @return
     *     A long value represented by the string argument.
     * @throws NumberFormatException <code>lexicalXSDLong</code> is not a valid string representation of a <code>long</code> value.
     */
    public long parseLong( String lexicalXSDLong );

    /**
     * <p>
     * Converts the string argument into a short value.
     * <p>
     * <p>
     *  将字符串参数转换为短值。
     * 
     * 
     * @param lexicalXSDShort
     *     A string containing lexical representation of
     *     xsd:short.
     * @return
     *     A short value represented by the string argument.
     * @throws NumberFormatException <code>lexicalXSDShort</code> is not a valid string representation of a <code>short</code> value.
     */
    public short parseShort( String lexicalXSDShort );

    /**
     * <p>
     * Converts the string argument into a BigDecimal value.
     * <p>
     * <p>
     *  将字符串参数转换为BigDecimal值。
     * 
     * 
     * @param lexicalXSDDecimal
     *     A string containing lexical representation of
     *     xsd:decimal.
     * @return
     *     A BigDecimal value represented by the string argument.
     * @throws NumberFormatException <code>lexicalXSDDecimal</code> is not a valid string representation of {@link java.math.BigDecimal}.
     */
    public java.math.BigDecimal parseDecimal( String lexicalXSDDecimal );

    /**
     * <p>
     * Converts the string argument into a float value.
     * <p>
     * <p>
     *  将字符串参数转换为浮点值。
     * 
     * 
     * @param lexicalXSDFloat
     *     A string containing lexical representation of
     *     xsd:float.
     * @return
     *     A float value represented by the string argument.
     * @throws NumberFormatException <code>lexicalXSDFloat</code> is not a valid string representation of a <code>float</code> value.
     */
    public float parseFloat( String lexicalXSDFloat );

    /**
     * <p>
     * Converts the string argument into a double value.
     * <p>
     * <p>
     *  将字符串参数转换为双精度值。
     * 
     * 
     * @param lexicalXSDDouble
     *     A string containing lexical representation of
     *     xsd:double.
     * @return
     *     A double value represented by the string argument.
     * @throws NumberFormatException <code>lexicalXSDDouble</code> is not a valid string representation of a <code>double</code> value.
     */
    public double parseDouble( String lexicalXSDDouble );

    /**
     * <p>
     * Converts the string argument into a boolean value.
     * <p>
     * <p>
     *  将字符串参数转换为布尔值。
     * 
     * 
     * @param lexicalXSDBoolean
     *     A string containing lexical representation of
     *     xsd:boolean.
     * @return
     *     A boolean value represented by the string argument.
     * @throws IllegalArgumentException if string parameter does not conform to lexical value space defined in XML Schema Part 2: Datatypes for xsd:boolean.
     */
    public boolean parseBoolean( String lexicalXSDBoolean );

    /**
     * <p>
     * Converts the string argument into a byte value.
     * <p>
     * <p>
     *  将字符串参数转换为字节值。
     * 
     * 
     * @param lexicalXSDByte
     *     A string containing lexical representation of
     *     xsd:byte.
     * @return
     *     A byte value represented by the string argument.
     * @throws NumberFormatException <code>lexicalXSDByte</code> does not contain a parseable byte.
     * @throws IllegalArgumentException if string parameter does not conform to lexical value space defined in XML Schema Part 2: Datatypes for xsd:byte.
     */
    public byte parseByte( String lexicalXSDByte );

    /**
     * <p>
     * Converts the string argument into a QName value.
     *
     * <p>
     * String parameter <tt>lexicalXSDQname</tt> must conform to lexical value space specifed at
     * <a href="http://www.w3.org/TR/xmlschema-2/#QName">XML Schema Part 2:Datatypes specification:QNames</a>
     *
     * <p>
     * <p>
     *  将字符串参数转换为QName值。
     * 
     * <p>
     * 字符串参数<tt> lexicalXSDQname </tt>必须符合在<a href="http://www.w3.org/TR/xmlschema-2/#QName"> XML模式第2部分：数据类型
     * 规范中指定的词法值空间：QNames </a>。
     * 
     * 
     * @param lexicalXSDQName
     *     A string containing lexical representation of xsd:QName.
     * @param nsc
     *     A namespace context for interpreting a prefix within a QName.
     * @return
     *     A QName value represented by the string argument.
     * @throws IllegalArgumentException  if string parameter does not conform to XML Schema Part 2 specification or
     *      if namespace prefix of <tt>lexicalXSDQname</tt> is not bound to a URI in NamespaceContext <tt>nsc</tt>.
     */
    public javax.xml.namespace.QName parseQName( String lexicalXSDQName,
                                             javax.xml.namespace.NamespaceContext nsc);

    /**
     * <p>
     * Converts the string argument into a Calendar value.
     * <p>
     * <p>
     *  将字符串参数转换为日历值。
     * 
     * 
     * @param lexicalXSDDateTime
     *     A string containing lexical representation of
     *     xsd:datetime.
     * @return
     *     A Calendar object represented by the string argument.
     * @throws IllegalArgumentException if string parameter does not conform to lexical value space defined in XML Schema Part 2: Datatypes for xsd:dateTime.
     */
    public java.util.Calendar parseDateTime( String lexicalXSDDateTime );

    /**
     * <p>
     * Converts the string argument into an array of bytes.
     * <p>
     * <p>
     *  将字符串参数转换为字节数组。
     * 
     * 
     * @param lexicalXSDBase64Binary
     *     A string containing lexical representation
     *     of xsd:base64Binary.
     * @return
     *     An array of bytes represented by the string argument.
     * @throws IllegalArgumentException if string parameter does not conform to lexical value space defined in XML Schema Part 2: Datatypes for xsd:base64Binary
     */
    public byte[] parseBase64Binary( String lexicalXSDBase64Binary );

    /**
     * <p>
     * Converts the string argument into an array of bytes.
     * <p>
     * <p>
     *  将字符串参数转换为字节数组。
     * 
     * 
     * @param lexicalXSDHexBinary
     *     A string containing lexical representation of
     *     xsd:hexBinary.
     * @return
     *     An array of bytes represented by the string argument.
     * @throws IllegalArgumentException if string parameter does not conform to lexical value space defined in XML Schema Part 2: Datatypes for xsd:hexBinary.
     */
    public byte[] parseHexBinary( String lexicalXSDHexBinary );

    /**
     * <p>
     * Converts the string argument into a long value.
     * <p>
     * <p>
     *  将字符串参数转换为长整型值。
     * 
     * 
     * @param lexicalXSDUnsignedInt
     *     A string containing lexical representation
     *     of xsd:unsignedInt.
     * @return
     *     A long value represented by the string argument.
     * @throws NumberFormatException if string parameter can not be parsed into a <tt>long</tt> value.
     */
    public long parseUnsignedInt( String lexicalXSDUnsignedInt );

    /**
     * <p>
     * Converts the string argument into an int value.
     * <p>
     * <p>
     *  将字符串参数转换为int值。
     * 
     * 
     * @param lexicalXSDUnsignedShort
     *     A string containing lexical
     *     representation of xsd:unsignedShort.
     * @return
     *     An int value represented by the string argument.
     * @throws NumberFormatException if string parameter can not be parsed into an <tt>int</tt> value.
     */
    public int parseUnsignedShort( String lexicalXSDUnsignedShort );

    /**
     * <p>
     * Converts the string argument into a Calendar value.
     * <p>
     * <p>
     *  将字符串参数转换为日历值。
     * 
     * 
     * @param lexicalXSDTime
     *     A string containing lexical representation of
     *     xsd:Time.
     * @return
     *     A Calendar value represented by the string argument.
     * @throws IllegalArgumentException if string parameter does not conform to lexical value space defined in XML Schema Part 2: Datatypes for xsd:Time.
     */
    public java.util.Calendar parseTime( String lexicalXSDTime );

    /**
     * <p>
     * Converts the string argument into a Calendar value.
     * <p>
     * <p>
     *  将字符串参数转换为日历值。
     * 
     * 
     * @param lexicalXSDDate
     *     A string containing lexical representation of
     *     xsd:Date.
     * @return
     *     A Calendar value represented by the string argument.
     * @throws IllegalArgumentException if string parameter does not conform to lexical value space defined in XML Schema Part 2: Datatypes for xsd:Date.
     */
    public java.util.Calendar parseDate( String lexicalXSDDate );

    /**
     * <p>
     * Return a string containing the lexical representation of the
     * simple type.
     * <p>
     * <p>
     *  返回一个包含简单类型的词法表示的字符串。
     * 
     * 
     * @param lexicalXSDAnySimpleType
     *     A string containing lexical
     *     representation of the simple type.
     * @return
     *     A string containing the lexical representation of the
     *     simple type.
     */
    public String parseAnySimpleType( String lexicalXSDAnySimpleType );

    /**
     * <p>
     * Converts the string argument into a string.
     * <p>
     * <p>
     *  将字符串参数转换为字符串。
     * 
     * 
     * @param val
     *     A string value.
     * @return
     *     A string containing a lexical representation of xsd:string
     */
    public String printString( String val );

    /**
     * <p>
     * Converts a BigInteger value into a string.
     * <p>
     * <p>
     *  将BigInteger值转换为字符串。
     * 
     * 
     * @param val
     *     A BigInteger value
     * @return
     *     A string containing a lexical representation of xsd:integer
     * @throws IllegalArgumentException <tt>val</tt> is null.
     */
    public String printInteger( java.math.BigInteger val );

    /**
     * <p>
     * Converts an int value into a string.
     * <p>
     * <p>
     *  将int值转换为字符串。
     * 
     * 
     * @param val
     *     An int value
     * @return
     *     A string containing a lexical representation of xsd:int
     */
    public String printInt( int val );


    /**
     * <p>
     * Converts a long value into a string.
     * <p>
     * <p>
     *  将长整型值转换为字符串。
     * 
     * 
     * @param val
     *     A long value
     * @return
     *     A string containing a lexical representation of xsd:long
     */
    public String printLong( long val );

    /**
     * <p>
     * Converts a short value into a string.
     * <p>
     * <p>
     *  将短值转换为字符串。
     * 
     * 
     * @param val
     *     A short value
     * @return
     *     A string containing a lexical representation of xsd:short
     */
    public String printShort( short val );

    /**
     * <p>
     * Converts a BigDecimal value into a string.
     * <p>
     * <p>
     *  将BigDecimal值转换为字符串。
     * 
     * 
     * @param val
     *     A BigDecimal value
     * @return
     *     A string containing a lexical representation of xsd:decimal
     * @throws IllegalArgumentException <tt>val</tt> is null.
     */
    public String printDecimal( java.math.BigDecimal val );

    /**
     * <p>
     * Converts a float value into a string.
     * <p>
     * <p>
     *  将浮点值转换为字符串。
     * 
     * 
     * @param val
     *     A float value
     * @return
     *     A string containing a lexical representation of xsd:float
     */
    public String printFloat( float val );

    /**
     * <p>
     * Converts a double value into a string.
     * <p>
     * <p>
     *  将双精度值转换为字符串。
     * 
     * 
     * @param val
     *     A double value
     * @return
     *     A string containing a lexical representation of xsd:double
     */
    public String printDouble( double val );

    /**
     * <p>
     * Converts a boolean value into a string.
     * <p>
     * <p>
     *  将布尔值转换为字符串。
     * 
     * 
     * @param val
     *     A boolean value
     * @return
     *     A string containing a lexical representation of xsd:boolean
     */
    public String printBoolean( boolean val );

    /**
     * <p>
     * Converts a byte value into a string.
     * <p>
     * <p>
     *  将字节值转换为字符串。
     * 
     * 
     * @param val
     *     A byte value
     * @return
     *     A string containing a lexical representation of xsd:byte
     */
    public String printByte( byte val );

    /**
     * <p>
     * Converts a QName instance into a string.
     * <p>
     * <p>
     *  将QName实例转换为字符串。
     * 
     * 
     * @param val
     *     A QName value
     * @param nsc
     *     A namespace context for interpreting a prefix within a QName.
     * @return
     *     A string containing a lexical representation of QName
     * @throws IllegalArgumentException if <tt>val</tt> is null or
     * if <tt>nsc</tt> is non-null or <tt>nsc.getPrefix(nsprefixFromVal)</tt> is null.
     */
    public String printQName( javax.xml.namespace.QName val,
                              javax.xml.namespace.NamespaceContext nsc );

    /**
     * <p>
     * Converts a Calendar value into a string.
     * <p>
     * <p>
     *  将日历值转换为字符串。
     * 
     * 
     * @param val
     *     A Calendar value
     * @return
     *     A string containing a lexical representation of xsd:dateTime
     * @throws IllegalArgumentException if <tt>val</tt> is null.
     */
    public String printDateTime( java.util.Calendar val );

    /**
     * <p>
     * Converts an array of bytes into a string.
     * <p>
     * <p>
     *  将字节数组转换为字符串。
     * 
     * 
     * @param val
     *     an array of bytes
     * @return
     *     A string containing a lexical representation of xsd:base64Binary
     * @throws IllegalArgumentException if <tt>val</tt> is null.
     */
    public String printBase64Binary( byte[] val );

    /**
     * <p>
     * Converts an array of bytes into a string.
     * <p>
     * <p>
     *  将字节数组转换为字符串。
     * 
     * 
     * @param val
     *     an array of bytes
     * @return
     *     A string containing a lexical representation of xsd:hexBinary
     * @throws IllegalArgumentException if <tt>val</tt> is null.
     */
    public String printHexBinary( byte[] val );

    /**
     * <p>
     * Converts a long value into a string.
     * <p>
     * <p>
     *  将长整型值转换为字符串。
     * 
     * 
     * @param val
     *     A long value
     * @return
     *     A string containing a lexical representation of xsd:unsignedInt
     */
    public String printUnsignedInt( long val );

    /**
     * <p>
     * Converts an int value into a string.
     * <p>
     * <p>
     *  将int值转换为字符串。
     * 
     * 
     * @param val
     *     An int value
     * @return
     *     A string containing a lexical representation of xsd:unsignedShort
     */
    public String printUnsignedShort( int val );

    /**
     * <p>
     * Converts a Calendar value into a string.
     * <p>
     * <p>
     *  将日历值转换为字符串。
     * 
     * 
     * @param val
     *     A Calendar value
     * @return
     *     A string containing a lexical representation of xsd:time
     * @throws IllegalArgumentException if <tt>val</tt> is null.
     */
    public String printTime( java.util.Calendar val );

    /**
     * <p>
     * Converts a Calendar value into a string.
     * <p>
     * <p>
     *  将日历值转换为字符串。
     * 
     * 
     * @param val
     *     A Calendar value
     * @return
     *     A string containing a lexical representation of xsd:date
     * @throws IllegalArgumentException if <tt>val</tt> is null.
     */
    public String printDate( java.util.Calendar val );

    /**
     * <p>
     * Converts a string value into a string.
     * <p>
     * <p>
     *  将字符串值转换为字符串。
     * 
     * @param val
     *     A string value
     * @return
     *     A string containing a lexical representation of xsd:AnySimpleType
     */
    public String printAnySimpleType( String val );
}
