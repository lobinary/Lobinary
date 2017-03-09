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

import javax.xml.namespace.NamespaceContext;

/**
 * <p>
 * The javaType binding declaration can be used to customize the binding of
 * an XML schema datatype to a Java datatype. Customizations can involve
 * writing a parse and print method for parsing and printing lexical
 * representations of a XML schema datatype respectively. However, writing
 * parse and print methods requires knowledge of the lexical representations (
 * <a href="http://www.w3.org/TR/xmlschema-2/"> XML Schema Part2: Datatypes
 * specification </a>) and hence may be difficult to write.
 * </p>
 * <p>
 * This class makes it easier to write parse and print methods. It defines
 * static parse and print methods that provide access to a JAXB provider's
 * implementation of parse and print methods. These methods are invoked by
 * custom parse and print methods. For example, the binding of xsd:dateTime
 * to a long can be customized using parse and print methods as follows:
 * <blockquote>
 *    <pre>
 *    // Customized parse method
 *    public long myParseCal( String dateTimeString ) {
 *        java.util.Calendar cal = DatatypeConverter.parseDateTime(dateTimeString);
 *        long longval = convert_calendar_to_long(cal); //application specific
 *        return longval;
 *    }
 *
 *    // Customized print method
 *    public String myPrintCal( Long longval ) {
 *        java.util.Calendar cal = convert_long_to_calendar(longval) ; //application specific
 *        String dateTimeString = DatatypeConverter.printDateTime(cal);
 *        return dateTimeString;
 *    }
 *    </pre>
 * </blockquote>
 * <p>
 * There is a static parse and print method corresponding to each parse and
 * print method respectively in the {@link DatatypeConverterInterface
 * DatatypeConverterInterface}.
 * <p>
 * The static methods defined in the class can also be used to specify
 * a parse or a print method in a javaType binding declaration.
 * </p>
 * <p>
 * JAXB Providers are required to call the
 * {@link #setDatatypeConverter(DatatypeConverterInterface)
 * setDatatypeConverter} api at some point before the first marshal or unmarshal
 * operation (perhaps during the call to JAXBContext.newInstance).  This step is
 * necessary to configure the converter that should be used to perform the
 * print and parse functionality.
 * </p>
 *
 * <p>
 * A print method for a XML schema datatype can output any lexical
 * representation that is valid with respect to the XML schema datatype.
 * If an error is encountered during conversion, then an IllegalArgumentException,
 * or a subclass of IllegalArgumentException must be thrown by the method.
 * </p>
 *
 * <p>
 * <p>
 *  javaType绑定声明可用于自定义XML模式数据类型与Java数据类型的绑定。定制可以涉及编写用于分别解析和打印XML模式数据类型的词法表示的解析和打印方法。
 * 但是,编写解析和打印方法需要知道词法表示(<a href="http://www.w3.org/TR/xmlschema-2/"> XML模式Part2：数据类型规范</a>),因此可能很难写。
 * </p>
 * <p>
 *  这个类使得更容易编写解析和打印方法。它定义静态解析和打印方法,提供对JAXB提供程序实现的解析和打印方法的访问。这些方法由自定义的解析和打印方法调用。
 * 例如,xsd：dateTime到long的绑定可以使用解析和打印方法来定制,如下所示：。
 * <blockquote>
 * <pre>
 *  //定制的解析方法public long myParseCal(String dateTimeString){java.util.Calendar cal = DatatypeConverter.parseDateTime(dateTimeString); long longval = convert_calendar_to_long(cal); // application specific return longval; }
 * }。
 * 
 *  //定制打印方法public String myPrintCal(Long longval){java.util.Calendar cal = convert_long_to_calendar(longval); // application specific String dateTimeString = DatatypeConverter.printDateTime(cal); return dateTimeString; }
 * }。
 * </pre>
 * </blockquote>
 * <p>
 * 在{@link DatatypeConverterInterface DatatypeConverterInterface}中分别有对应于每个解析和打印方法的静态解析和打印方法。
 * <p>
 *  类中定义的静态方法也可以用于在javaType绑定声明中指定解析或打印方法。
 * </p>
 * <p>
 *  JAXB提供程序需要在第一个编组或解组操作之前的某个时间(可能在调用JAXBContext.newInstance期间)调用{@link #setDatatypeConverter(DatatypeConverterInterface)setDatatypeConverter}
 *  api。
 * 此步骤对于配置应用于执行打印和解析功能的转换器是必要的。
 * </p>
 * 
 * <p>
 *  XML模式数据类型的打印方法可以输出对于XML模式数据类型有效的任何词法表示。
 * 如果在转换期间遇到错误,则必须由方法抛出IllegalArgumentException或IllegalArgumentException的子类。
 * </p>
 * 
 * 
 * @author <ul><li>Sekhar Vajjhala, Sun Microsystems, Inc.</li><li>Joe Fialli, Sun Microsystems Inc.</li><li>Kohsuke Kawaguchi, Sun Microsystems, Inc.</li><li>Ryan Shoemaker,Sun Microsystems Inc.</li></ul>
 * @see DatatypeConverterInterface
 * @see ParseConversionEvent
 * @see PrintConversionEvent
 * @since JAXB1.0
 */

final public class DatatypeConverter {

    // delegate to this instance of DatatypeConverter
    private static volatile DatatypeConverterInterface theConverter = null;

    private final static JAXBPermission SET_DATATYPE_CONVERTER_PERMISSION =
                           new JAXBPermission("setDatatypeConverter");

    private DatatypeConverter() {
        // private constructor
    }

    /**
     * This method is for JAXB provider use only.
     * <p>
     * JAXB Providers are required to call this method at some point before
     * allowing any of the JAXB client marshal or unmarshal operations to
     * occur.  This is necessary to configure the datatype converter that
     * should be used to perform the print and parse conversions.
     *
     * <p>
     * Calling this api repeatedly will have no effect - the
     * DatatypeConverterInterface instance passed into the first invocation is
     * the one that will be used from then on.
     *
     * <p>
     *  此方法仅供JAXB提供程序使用。
     * <p>
     *  JAXB提供程序需要在允许任何JAXB客户端编组或解组操作发生之前的某个时间调用此方法。这对于配置应用于执行打印和解析转换的数据类型转换器是必要的。
     * 
     * <p>
     *  重复调用此api将不起作用 - 传递到第一次调用的DatatypeConverterInterface实例是从那时起将使用的实例。
     * 
     * 
     * @param converter an instance of a class that implements the
     * DatatypeConverterInterface class - this parameter must not be null.
     * @throws IllegalArgumentException if the parameter is null
     * @throws SecurityException
     *      If the {@link SecurityManager} in charge denies the access to
     *      set the datatype converter.
     * @see JAXBPermission
     */
    public static void setDatatypeConverter( DatatypeConverterInterface converter ) {
        if( converter == null ) {
            throw new IllegalArgumentException(
                Messages.format( Messages.CONVERTER_MUST_NOT_BE_NULL ) );
        } else if( theConverter == null ) {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null)
                sm.checkPermission(SET_DATATYPE_CONVERTER_PERMISSION);
            theConverter = converter;
        }
    }

    private static synchronized void initConverter() {
        theConverter = new DatatypeConverterImpl();
    }

    /**
     * <p>
     * Convert the lexical XSD string argument into a String value.
     * <p>
     * <p>
     *  将词法XSD字符串参数转换为字符串值。
     * 
     * 
     * @param lexicalXSDString
     *     A string containing a lexical representation of
     *     xsd:string.
     * @return
     *     A String value represented by the string argument.
     */
    public static String parseString( String lexicalXSDString ) {
        if (theConverter == null) initConverter();
        return theConverter.parseString( lexicalXSDString );
    }

    /**
     * <p>
     * Convert the string argument into a BigInteger value.
     * <p>
     * <p>
     * 将字符串参数转换为BigInteger值。
     * 
     * 
     * @param lexicalXSDInteger
     *     A string containing a lexical representation of
     *     xsd:integer.
     * @return
     *     A BigInteger value represented by the string argument.
     * @throws NumberFormatException <code>lexicalXSDInteger</code> is not a valid string representation of a {@link java.math.BigInteger} value.
     */
    public static java.math.BigInteger parseInteger( String lexicalXSDInteger ) {
        if (theConverter == null) initConverter();
        return theConverter.parseInteger( lexicalXSDInteger );
    }

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
     *     A int value represented by the string argument.
     * @throws NumberFormatException <code>lexicalXSDInt</code> is not a valid string representation of an <code>int</code> value.
     */
    public static int parseInt( String lexicalXSDInt ) {
        if (theConverter == null) initConverter();
        return theConverter.parseInt( lexicalXSDInt );
    }

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
    public static long parseLong( String lexicalXSDLong ) {
        if (theConverter == null) initConverter();
        return theConverter.parseLong( lexicalXSDLong );
    }

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
    public static short parseShort( String lexicalXSDShort ) {
        if (theConverter == null) initConverter();
        return theConverter.parseShort( lexicalXSDShort );
    }

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
    public static java.math.BigDecimal parseDecimal( String lexicalXSDDecimal ) {
        if (theConverter == null) initConverter();
        return theConverter.parseDecimal( lexicalXSDDecimal );
    }

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
    public static float parseFloat( String lexicalXSDFloat ) {
        if (theConverter == null) initConverter();
        return theConverter.parseFloat( lexicalXSDFloat );
    }

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
    public static double parseDouble( String lexicalXSDDouble ) {
        if (theConverter == null) initConverter();
        return theConverter.parseDouble( lexicalXSDDouble );
    }

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
    public static boolean parseBoolean( String lexicalXSDBoolean ) {
        if (theConverter == null) initConverter();
        return theConverter.parseBoolean( lexicalXSDBoolean );
    }

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
     * @throws IllegalArgumentException if string parameter does not conform to lexical value space defined in XML Schema Part 2: Datatypes for xsd:byte.
     */
    public static byte parseByte( String lexicalXSDByte ) {
        if (theConverter == null) initConverter();
        return theConverter.parseByte( lexicalXSDByte );
    }

    /**
     * <p>
     * Converts the string argument into a byte value.
     *
     * <p>
     * String parameter <tt>lexicalXSDQname</tt> must conform to lexical value space specifed at
     * <a href="http://www.w3.org/TR/xmlschema-2/#QName">XML Schema Part 2:Datatypes specification:QNames</a>
     *
     * <p>
     * <p>
     *  将字符串参数转换为字节值。
     * 
     * <p>
     *  字符串参数<tt> lexicalXSDQname </tt>必须符合在<a href="http://www.w3.org/TR/xmlschema-2/#QName"> XML模式第2部分：数据类
     * 型规范中指定的词法值空间：QNames </a>。
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
    public static javax.xml.namespace.QName parseQName( String lexicalXSDQName,
                                                    NamespaceContext nsc) {
        if (theConverter == null) initConverter();
        return theConverter.parseQName( lexicalXSDQName, nsc );
    }

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
    public static java.util.Calendar parseDateTime( String lexicalXSDDateTime ) {
        if (theConverter == null) initConverter();
        return theConverter.parseDateTime( lexicalXSDDateTime );
    }

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
    public static byte[] parseBase64Binary( String lexicalXSDBase64Binary ) {
        if (theConverter == null) initConverter();
        return theConverter.parseBase64Binary( lexicalXSDBase64Binary );
    }

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
   public static byte[] parseHexBinary( String lexicalXSDHexBinary ) {
        if (theConverter == null) initConverter();
        return theConverter.parseHexBinary( lexicalXSDHexBinary );
    }

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
    public static long parseUnsignedInt( String lexicalXSDUnsignedInt ) {
        if (theConverter == null) initConverter();
        return theConverter.parseUnsignedInt( lexicalXSDUnsignedInt );
    }

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
    public static int   parseUnsignedShort( String lexicalXSDUnsignedShort ) {
        if (theConverter == null) initConverter();
        return theConverter.parseUnsignedShort( lexicalXSDUnsignedShort );
    }

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
     *     xsd:time.
     * @return
     *     A Calendar value represented by the string argument.
     * @throws IllegalArgumentException if string parameter does not conform to lexical value space defined in XML Schema Part 2: Datatypes for xsd:Time.
     */
    public static java.util.Calendar parseTime( String lexicalXSDTime ) {
        if (theConverter == null) initConverter();
        return theConverter.parseTime( lexicalXSDTime );
    }
    /**
     * <p>
     * Converts the string argument into a Calendar value.
     * <p>
     * <p>
     *  将字符串参数转换为日历值。
     * 
     * 
     * @param lexicalXSDDate
     *      A string containing lexical representation of
     *     xsd:Date.
     * @return
     *     A Calendar value represented by the string argument.
     * @throws IllegalArgumentException if string parameter does not conform to lexical value space defined in XML Schema Part 2: Datatypes for xsd:Date.
     */
    public static java.util.Calendar parseDate( String lexicalXSDDate ) {
        if (theConverter == null) initConverter();
        return theConverter.parseDate( lexicalXSDDate );
    }

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
    public static String parseAnySimpleType( String lexicalXSDAnySimpleType ) {
        if (theConverter == null) initConverter();
        return theConverter.parseAnySimpleType( lexicalXSDAnySimpleType );
    }
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
     *     A string containing a lexical representation of xsd:string.
     */
     // also indicate the print methods produce a lexical
     // representation for given Java datatypes.

    public static String printString( String val ) {
        if (theConverter == null) initConverter();
        return theConverter.printString( val );
    }

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
    public static String printInteger( java.math.BigInteger val ) {
        if (theConverter == null) initConverter();
        return theConverter.printInteger( val );
    }

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
    public static String printInt( int val ) {
        if (theConverter == null) initConverter();
        return theConverter.printInt( val );
    }

    /**
     * <p>
     * Converts A long value into a string.
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
    public static String printLong( long val ) {
        if (theConverter == null) initConverter();
        return theConverter.printLong( val );
    }

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
    public static String printShort( short val ) {
        if (theConverter == null) initConverter();
        return theConverter.printShort( val );
    }

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
    public static String printDecimal( java.math.BigDecimal val ) {
        if (theConverter == null) initConverter();
        return theConverter.printDecimal( val );
    }

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
    public static String printFloat( float val ) {
        if (theConverter == null) initConverter();
        return theConverter.printFloat( val );
    }

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
    public static String printDouble( double val ) {
        if (theConverter == null) initConverter();
        return theConverter.printDouble( val );
    }

    /**
     * <p>
     * Converts a boolean value into a string.
     * <p>
     * <p>
     * 将布尔值转换为字符串。
     * 
     * 
     * @param val
     *     A boolean value
     * @return
     *     A string containing a lexical representation of xsd:boolean
     */
    public static String printBoolean( boolean val ) {
        if (theConverter == null) initConverter();
        return theConverter.printBoolean( val );
    }

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
    public static String printByte( byte val ) {
        if (theConverter == null) initConverter();
        return theConverter.printByte( val );
    }

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
    public static String printQName( javax.xml.namespace.QName val,
                                     NamespaceContext nsc ) {
        if (theConverter == null) initConverter();
        return theConverter.printQName( val, nsc );
    }

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
    public static String printDateTime( java.util.Calendar val ) {
        if (theConverter == null) initConverter();
        return theConverter.printDateTime( val );
    }

    /**
     * <p>
     * Converts an array of bytes into a string.
     * <p>
     * <p>
     *  将字节数组转换为字符串。
     * 
     * 
     * @param val
     *     An array of bytes
     * @return
     *     A string containing a lexical representation of xsd:base64Binary
     * @throws IllegalArgumentException if <tt>val</tt> is null.
     */
    public static String printBase64Binary( byte[] val ) {
        if (theConverter == null) initConverter();
        return theConverter.printBase64Binary( val );
    }

    /**
     * <p>
     * Converts an array of bytes into a string.
     * <p>
     * <p>
     *  将字节数组转换为字符串。
     * 
     * 
     * @param val
     *     An array of bytes
     * @return
     *     A string containing a lexical representation of xsd:hexBinary
     * @throws IllegalArgumentException if <tt>val</tt> is null.
     */
    public static String printHexBinary( byte[] val ) {
        if (theConverter == null) initConverter();
        return theConverter.printHexBinary( val );
    }

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
    public static String printUnsignedInt( long val ) {
        if (theConverter == null) initConverter();
        return theConverter.printUnsignedInt( val );
    }

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
    public static String printUnsignedShort( int val ) {
        if (theConverter == null) initConverter();
        return theConverter.printUnsignedShort( val );
    }

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
    public static String printTime( java.util.Calendar val ) {
        if (theConverter == null) initConverter();
        return theConverter.printTime( val );
    }

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
    public static String printDate( java.util.Calendar val ) {
        if (theConverter == null) initConverter();
        return theConverter.printDate( val );
    }

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
    public static String printAnySimpleType( String val ) {
        if (theConverter == null) initConverter();
        return theConverter.printAnySimpleType( val );
    }
}
