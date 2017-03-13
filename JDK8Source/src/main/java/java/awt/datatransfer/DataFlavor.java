/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2014, Oracle and/or its affiliates. All rights reserved.
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

package java.awt.datatransfer;

import sun.awt.datatransfer.DataTransferer;
import sun.reflect.misc.ReflectUtil;

import java.io.ByteArrayInputStream;
import java.io.CharArrayReader;
import java.io.Externalizable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.OptionalDataException;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

import static sun.security.util.SecurityConstants.GET_CLASSLOADER_PERMISSION;

/**
 * A {@code DataFlavor} provides meta information about data. {@code DataFlavor}
 * is typically used to access data on the clipboard, or during
 * a drag and drop operation.
 * <p>
 * An instance of {@code DataFlavor} encapsulates a content type as
 * defined in <a href="http://www.ietf.org/rfc/rfc2045.txt">RFC 2045</a>
 * and <a href="http://www.ietf.org/rfc/rfc2046.txt">RFC 2046</a>.
 * A content type is typically referred to as a MIME type.
 * <p>
 * A content type consists of a media type (referred
 * to as the primary type), a subtype, and optional parameters. See
 * <a href="http://www.ietf.org/rfc/rfc2045.txt">RFC 2045</a>
 * for details on the syntax of a MIME type.
 * <p>
 * The JRE data transfer implementation interprets the parameter &quot;class&quot;
 * of a MIME type as <B>a representation class</b>.
 * The representation class reflects the class of the object being
 * transferred. In other words, the representation class is the type of
 * object returned by {@link Transferable#getTransferData}.
 * For example, the MIME type of {@link #imageFlavor} is
 * {@code "image/x-java-image;class=java.awt.Image"},
 * the primary type is {@code image}, the subtype is
 * {@code x-java-image}, and the representation class is
 * {@code java.awt.Image}. When {@code getTransferData} is invoked
 * with a {@code DataFlavor} of {@code imageFlavor}, an instance of
 * {@code java.awt.Image} is returned.
 * It's important to note that {@code DataFlavor} does no error checking
 * against the representation class. It is up to consumers of
 * {@code DataFlavor}, such as {@code Transferable}, to honor the representation
 * class.
 * <br>
 * Note, if you do not specify a representation class when
 * creating a {@code DataFlavor}, the default
 * representation class is used. See appropriate documentation for
 * {@code DataFlavor}'s constructors.
 * <p>
 * Also, {@code DataFlavor} instances with the &quot;text&quot; primary
 * MIME type may have a &quot;charset&quot; parameter. Refer to
 * <a href="http://www.ietf.org/rfc/rfc2046.txt">RFC 2046</a> and
 * {@link #selectBestTextFlavor} for details on &quot;text&quot; MIME types
 * and the &quot;charset&quot; parameter.
 * <p>
 * Equality of {@code DataFlavors} is determined by the primary type,
 * subtype, and representation class. Refer to {@link #equals(DataFlavor)} for
 * details. When determining equality, any optional parameters are ignored.
 * For example, the following produces two {@code DataFlavors} that
 * are considered identical:
 * <pre>
 *   DataFlavor flavor1 = new DataFlavor(Object.class, &quot;X-test/test; class=&lt;java.lang.Object&gt;; foo=bar&quot;);
 *   DataFlavor flavor2 = new DataFlavor(Object.class, &quot;X-test/test; class=&lt;java.lang.Object&gt;; x=y&quot;);
 *   // The following returns true.
 *   flavor1.equals(flavor2);
 * </pre>
 * As mentioned, {@code flavor1} and {@code flavor2} are considered identical.
 * As such, asking a {@code Transferable} for either {@code DataFlavor} returns
 * the same results.
 * <p>
 * For more information on the using data transfer with Swing see
 * the <a href="https://docs.oracle.com/javase/tutorial/uiswing/dnd/index.html">
 * How to Use Drag and Drop and Data Transfer</a>,
 * section in <em>Java Tutorial</em>.
 *
 * <p>
 *  {@code DataFlavor}提供有关数据的元信息{@code DataFlavor}通常用于访问剪贴板上的数据,或者在拖放操作期间
 * <p>
 *  {@code DataFlavor}的实例封装了<a href=\"http://wwwietforg/rfc/rfc2045txt\"> RFC 2045 </a>和<a href ="http：// wwwietforg / rfc中定义的内容类型/ rfc2046txt">
 *  RFC 2046 </a>内容类型通常称为MIME类型。
 * <p>
 *  内容类型由媒体类型(称为主要类型),子类型和可选参数组成。
 * 有关详细信息,请参见<a href=\"http://wwwietforg/rfc/rfc2045txt\"> RFC 2045 </a> MIME类型的语法。
 * <p>
 * JRE数据传输实现解释参数"类" MIME类型作为<B>表示类</b>。表示类反映了正在传输的对象的类。
 * 换句话说,表示类是由{@link Transferable#getTransferData}返回的对象类型。
 * 例如, {@link #imageFlavor}的MIME类型是{@code"image / x-java-image; class = javaawtImage"},主要类型是{@code image}
 * ,子类型是{@code x-java-image },表示类是{@code javaawtImage}当{@code getTransferData}被{@code imageFlavor}的{@code DataFlavor}
 * 调用时,返回一个{@code javaawtImage}的实例。
 * 换句话说,表示类是由{@link Transferable#getTransferData}返回的对象类型。
 * 重要的是要注意{ @code DataFlavor}对表示类没有检查错误{@code DataFlavor}的消费者(例如{@code Transferable})要遵守表示类。
 * <br>
 * 请注意,如果在创建{@code DataFlavor}时未指定表示类,则使用默认表示类。请参阅{@code DataFlavor}的构造函数的相应文档
 * <p>
 *  此外,{@code DataFlavor}实例与"文本"主MIME类型可以具有"字符集"参数有关"文本"参数的详细信息,请参阅<a href=\"http://wwwietforg/rfc/rfc2046txt\">
 *  RFC 2046 </a>和{@link #selectBestTextFlavor}。
 *  MIME类型和"字符集"参数。
 * <p>
 * {@code DataFlavors}的平等性由主类型,子类型和表示类型确定。有关详细信息,请参阅{@link #equals(DataFlavor)}当确定相等时,忽略任何可选参数。
 * 例如,代码DataFlavors}被认为是相同的：。
 * <pre>
 *  DataFlavor flavor1 = new DataFlavor(Objectclass,"X-test / test; class =&lt; javalangObject&gt ;; foo
 *  = bar"); DataFlavor flavor2 = new DataFlavor(Objectclass,"X-test / test; class =&lt; javalangObject&
 * gt ;; x = y"); //以下返回true flavor1equals(flavor2);。
 * </pre>
 *  如上所述,{@code flavor1}和{@code flavor2}被认为是相同的因此,要求{@code Transferable} {@code DataFlavor}返回相同的结果
 * <p>
 * 有关使用Swing使用数据传输的详细信息,请参阅<a href=\"https://docsoraclecom/javase/tutorial/uiswing/dnd/indexhtml\">如何使用拖
 * 放和数据传输</a>部分Java教程</em>。
 * 
 * 
 * @author      Blake Sullivan
 * @author      Laurence P. G. Cable
 * @author      Jeff Dunn
 */
public class DataFlavor implements Externalizable, Cloneable {

    private static final long serialVersionUID = 8367026044764648243L;
    private static final Class<InputStream> ioInputStreamClass = InputStream.class;

    /**
     * Tries to load a class from: the bootstrap loader, the system loader,
     * the context loader (if one is present) and finally the loader specified.
     *
     * <p>
     *  尝试加载类：引导加载程序,系统加载程序,上下文加载程序(如果存在)和最后加载程序指定
     * 
     * 
     * @param className the name of the class to be loaded
     * @param fallback the fallback loader
     * @return the class loaded
     * @exception ClassNotFoundException if class is not found
     */
    protected final static Class<?> tryToLoadClass(String className,
                                                   ClassLoader fallback)
        throws ClassNotFoundException
    {
        ReflectUtil.checkPackageAccess(className);
        try {
            SecurityManager sm = System.getSecurityManager();
            if (sm != null) {
                sm.checkPermission(GET_CLASSLOADER_PERMISSION);
            }
            ClassLoader loader = ClassLoader.getSystemClassLoader();
            try {
                // bootstrap class loader and system class loader if present
                return Class.forName(className, true, loader);
            }
            catch (ClassNotFoundException exception) {
                // thread context class loader if and only if present
                loader = Thread.currentThread().getContextClassLoader();
                if (loader != null) {
                    try {
                        return Class.forName(className, true, loader);
                    }
                    catch (ClassNotFoundException e) {
                        // fallback to user's class loader
                    }
                }
            }
        } catch (SecurityException exception) {
            // ignore secured class loaders
        }
        return Class.forName(className, true, fallback);
    }

    /*
     * private initializer
     * <p>
     *  私有初始化
     * 
     */
    static private DataFlavor createConstant(Class<?> rc, String prn) {
        try {
            return new DataFlavor(rc, prn);
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * private initializer
     * <p>
     *  私有初始化
     * 
     */
    static private DataFlavor createConstant(String mt, String prn) {
        try {
            return new DataFlavor(mt, prn);
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * private initializer
     * <p>
     *  私有初始化
     * 
     */
    static private DataFlavor initHtmlDataFlavor(String htmlFlavorType) {
        try {
            return new DataFlavor ("text/html; class=java.lang.String;document=" +
                                       htmlFlavorType + ";charset=Unicode");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * The <code>DataFlavor</code> representing a Java Unicode String class,
     * where:
     * <pre>
     *     representationClass = java.lang.String
     *     mimeType           = "application/x-java-serialized-object"
     * </pre>
     * <p>
     *  代表Java Unicode String类的<code> DataFlavor </code>,其中：
     * <pre>
     *  representationClass = javalangString mimeType ="application / x-java-serialized-object"
     * </pre>
     */
    public static final DataFlavor stringFlavor = createConstant(java.lang.String.class, "Unicode String");

    /**
     * The <code>DataFlavor</code> representing a Java Image class,
     * where:
     * <pre>
     *     representationClass = java.awt.Image
     *     mimeType            = "image/x-java-image"
     * </pre>
     * <p>
     *  代表Java Image类的<code> DataFlavor </code>,其中：
     * <pre>
     *  representationClass = javaawtImage mimeType ="image / x-java-image"
     * </pre>
     */
    public static final DataFlavor imageFlavor = createConstant("image/x-java-image; class=java.awt.Image", "Image");

    /**
     * The <code>DataFlavor</code> representing plain text with Unicode
     * encoding, where:
     * <pre>
     *     representationClass = InputStream
     *     mimeType            = "text/plain; charset=unicode"
     * </pre>
     * This <code>DataFlavor</code> has been <b>deprecated</b> because
     * (1) Its representation is an InputStream, an 8-bit based representation,
     * while Unicode is a 16-bit character set; and (2) The charset "unicode"
     * is not well-defined. "unicode" implies a particular platform's
     * implementation of Unicode, not a cross-platform implementation.
     *
     * <p>
     * <code> DataFlavor </code>表示使用Unicode编码的纯文本,其中：
     * <pre>
     *  representationClass = InputStream mimeType ="text / plain; charset = unicode"
     * </pre>
     *  此<code> DataFlavor </code>已被<b>弃用</b>,因为(1)它的表示是一个InputStream,一个基于8位的表示,而Unicode是一个16位字符集;和(2)字符集"un
     * icode"没有明确定义"unicode"意味着特定平台的Unicode实现,而不是跨平台实现。
     * 
     * 
     * @deprecated as of 1.3. Use <code>DataFlavor.getReaderForText(Transferable)</code>
     *             instead of <code>Transferable.getTransferData(DataFlavor.plainTextFlavor)</code>.
     */
    @Deprecated
    public static final DataFlavor plainTextFlavor = createConstant("text/plain; charset=unicode; class=java.io.InputStream", "Plain Text");

    /**
     * A MIME Content-Type of application/x-java-serialized-object represents
     * a graph of Java object(s) that have been made persistent.
     *
     * The representation class associated with this <code>DataFlavor</code>
     * identifies the Java type of an object returned as a reference
     * from an invocation <code>java.awt.datatransfer.getTransferData</code>.
     * <p>
     *  MIME内容 - 应用程序的类型/ x-java-serialized-object表示已经被持久化的Java对象的图形
     * 
     * 与此<code> DataFlavor </code>关联的表示类标识从调用返回作为引用的对象的Java类型<code> javaawtdatatransfergetTransferData </code>
     * 。
     * 
     */
    public static final String javaSerializedObjectMimeType = "application/x-java-serialized-object";

    /**
     * To transfer a list of files to/from Java (and the underlying
     * platform) a <code>DataFlavor</code> of this type/subtype and
     * representation class of <code>java.util.List</code> is used.
     * Each element of the list is required/guaranteed to be of type
     * <code>java.io.File</code>.
     * <p>
     *  为了向/从Java(和底层平台)传输文件列表,使用<code>该类型/子类型的<code> DataFlavor </code>和<code> javautilList </code>的表示类。
     * 列表的每个元素需要/保证是<code> javaioFile </code>类型。
     * 
     */
    public static final DataFlavor javaFileListFlavor = createConstant("application/x-java-file-list;class=java.util.List", null);

    /**
     * To transfer a reference to an arbitrary Java object reference that
     * has no associated MIME Content-type, across a <code>Transferable</code>
     * interface WITHIN THE SAME JVM, a <code>DataFlavor</code>
     * with this type/subtype is used, with a <code>representationClass</code>
     * equal to the type of the class/interface being passed across the
     * <code>Transferable</code>.
     * <p>
     * The object reference returned from
     * <code>Transferable.getTransferData</code> for a <code>DataFlavor</code>
     * with this MIME Content-Type is required to be
     * an instance of the representation Class of the <code>DataFlavor</code>.
     * <p>
     * 要将引用传递给在同一个JVM上的<code> Transferable </code>接口中没有相关MIME内容类型的任意Java对象引用,使用此类型/子类型的<code> DataFlavor </code>
     * 使用,与<code> representationClass </code>等于类/接口的类型通过<code>可传递</code>。
     * <p>
     *  对于具有此MIME Con​​tent-Type的<code> DataFlavor </code>,从<code> TransferablegetTransferData </code>返回的对象引
     * 用需要是<code> DataFlavor </code>。
     * 
     */
    public static final String javaJVMLocalObjectMimeType = "application/x-java-jvm-local-objectref";

    /**
     * In order to pass a live link to a Remote object via a Drag and Drop
     * <code>ACTION_LINK</code> operation a Mime Content Type of
     * application/x-java-remote-object should be used,
     * where the representation class of the <code>DataFlavor</code>
     * represents the type of the <code>Remote</code> interface to be
     * transferred.
     * <p>
     * 为了通过拖放<code> ACTION_LINK </code>操作将一个活动链接传递给一个远程对象,应该使用一个应用程序的Mime内容类型/ x-java-remote-object,代码> Data
     * Flavor </code>表示要传输的<code> Remote </code>接口的类型。
     * 
     */
    public static final String javaRemoteObjectMimeType = "application/x-java-remote-object";

    /**
     * Represents a piece of an HTML markup. The markup consists of the part
     * selected on the source side. Therefore some tags in the markup may be
     * unpaired. If the flavor is used to represent the data in
     * a {@link Transferable} instance, no additional changes will be made.
     * This DataFlavor instance represents the same HTML markup as DataFlavor
     * instances which content MIME type does not contain document parameter
     * and representation class is the String class.
     * <pre>
     *     representationClass = String
     *     mimeType           = "text/html"
     * </pre>
     * <p>
     *  表示一个HTML标记的标记标记由在源边上选择的部分组成因此标记中的某些标记可​​能不成对如果flavor用于表示{@link Transferable}实例中的数据,则不会进行其他更改made此Dat
     * aFlavor实例表示与DataFlavor实例相同的HTML标记,其内容MIME类型不包含文档参数,表示类是String类。
     * <pre>
     * representationClass = String mimeType ="text / html"
     * </pre>
     */
    public static DataFlavor selectionHtmlFlavor = initHtmlDataFlavor("selection");

    /**
     * Represents a piece of an HTML markup. If possible, the markup received
     * from a native system is supplemented with pair tags to be
     * a well-formed HTML markup. If the flavor is used to represent the data in
     * a {@link Transferable} instance, no additional changes will be made.
     * <pre>
     *     representationClass = String
     *     mimeType           = "text/html"
     * </pre>
     * <p>
     *  表示HTML标记的一部分如果可能,从本机系统接收的标记将使用对标记进行补充,以形成格式良好的HTML标记。如果flavor用于表示{@link Transferable}实例中的数据,将进行更改
     * <pre>
     *  representationClass = String mimeType ="text / html"
     * </pre>
     */
    public static DataFlavor fragmentHtmlFlavor = initHtmlDataFlavor("fragment");

    /**
     * Represents a piece of an HTML markup. If possible, the markup
     * received from a native system is supplemented with additional
     * tags to make up a well-formed HTML document. If the flavor is used to
     * represent the data in a {@link Transferable} instance,
     * no additional changes will be made.
     * <pre>
     *     representationClass = String
     *     mimeType           = "text/html"
     * </pre>
     * <p>
     *  表示HTML标记的一部分如果可能,从本机系统接收的标记将用附加标记进行补充,以构成格式良好的HTML文档如果使用flavor来表示{@link Transferable}实例中的数据,则不会将进行额外
     * 的更改。
     * <pre>
     *  representationClass = String mimeType ="text / html"
     * </pre>
     */
    public static  DataFlavor allHtmlFlavor = initHtmlDataFlavor("all");

    /**
     * Constructs a new <code>DataFlavor</code>.  This constructor is
     * provided only for the purpose of supporting the
     * <code>Externalizable</code> interface.  It is not
     * intended for public (client) use.
     *
     * <p>
     * 构造新的<code> DataFlavor </code>此构造函数仅用于支持<code> Externalizable </code>接口的目的。它不适用于公共
     * 
     * 
     * @since 1.2
     */
    public DataFlavor() {
        super();
    }

    /**
     * Constructs a fully specified <code>DataFlavor</code>.
     *
     * <p>
     *  构造完全指定的<code> DataFlavor </code>
     * 
     * 
     * @exception NullPointerException if either <code>primaryType</code>,
     *            <code>subType</code> or <code>representationClass</code> is null
     */
    private DataFlavor(String primaryType, String subType, MimeTypeParameterList params, Class<?> representationClass, String humanPresentableName) {
        super();
        if (primaryType == null) {
            throw new NullPointerException("primaryType");
        }
        if (subType == null) {
            throw new NullPointerException("subType");
        }
        if (representationClass == null) {
            throw new NullPointerException("representationClass");
        }

        if (params == null) params = new MimeTypeParameterList();

        params.set("class", representationClass.getName());

        if (humanPresentableName == null) {
            humanPresentableName = params.get("humanPresentableName");

            if (humanPresentableName == null)
                humanPresentableName = primaryType + "/" + subType;
        }

        try {
            mimeType = new MimeType(primaryType, subType, params);
        } catch (MimeTypeParseException mtpe) {
            throw new IllegalArgumentException("MimeType Parse Exception: " + mtpe.getMessage());
        }

        this.representationClass  = representationClass;
        this.humanPresentableName = humanPresentableName;

        mimeType.removeParameter("humanPresentableName");
    }

    /**
     * Constructs a <code>DataFlavor</code> that represents a Java class.
     * <p>
     * The returned <code>DataFlavor</code> will have the following
     * characteristics:
     * <pre>
     *    representationClass = representationClass
     *    mimeType            = application/x-java-serialized-object
     * </pre>
     * <p>
     *  构造表示Java类的<code> DataFlavor </code>
     * <p>
     *  返回的<code> DataFlavor </code>将具有以下特征：
     * <pre>
     *  representationClass = representationClass mimeType = application / x-java-serialized-object
     * </pre>
     * 
     * @param representationClass the class used to transfer data in this flavor
     * @param humanPresentableName the human-readable string used to identify
     *                 this flavor; if this parameter is <code>null</code>
     *                 then the value of the the MIME Content Type is used
     * @exception NullPointerException if <code>representationClass</code> is null
     */
    public DataFlavor(Class<?> representationClass, String humanPresentableName) {
        this("application", "x-java-serialized-object", null, representationClass, humanPresentableName);
        if (representationClass == null) {
            throw new NullPointerException("representationClass");
        }
    }

    /**
     * Constructs a <code>DataFlavor</code> that represents a
     * <code>MimeType</code>.
     * <p>
     * The returned <code>DataFlavor</code> will have the following
     * characteristics:
     * <p>
     * If the <code>mimeType</code> is
     * "application/x-java-serialized-object; class=&lt;representation class&gt;",
     * the result is the same as calling
     * <code>new DataFlavor(Class:forName(&lt;representation class&gt;)</code>.
     * <p>
     * Otherwise:
     * <pre>
     *     representationClass = InputStream
     *     mimeType            = mimeType
     * </pre>
     * <p>
     *  构造代表<code> MimeType </code>的<code> DataFlavor </code>
     * <p>
     *  返回的<code> DataFlavor </code>将具有以下特征：
     * <p>
     * 如果<code> mimeType </code>是"application / x-java-serialized-object; class =&lt; representation class&g
     * t;",结果与调用<code> new DataFlavor(Class：forName(&lt;表示类&gt;)</code>。
     * <p>
     *  除此以外：
     * <pre>
     *  representationClass = InputStream mimeType = mimeType
     * </pre>
     * 
     * @param mimeType the string used to identify the MIME type for this flavor;
     *                 if the the <code>mimeType</code> does not specify a
     *                 "class=" parameter, or if the class is not successfully
     *                 loaded, then an <code>IllegalArgumentException</code>
     *                 is thrown
     * @param humanPresentableName the human-readable string used to identify
     *                 this flavor; if this parameter is <code>null</code>
     *                 then the value of the the MIME Content Type is used
     * @exception IllegalArgumentException if <code>mimeType</code> is
     *                 invalid or if the class is not successfully loaded
     * @exception NullPointerException if <code>mimeType</code> is null
     */
    public DataFlavor(String mimeType, String humanPresentableName) {
        super();
        if (mimeType == null) {
            throw new NullPointerException("mimeType");
        }
        try {
            initialize(mimeType, humanPresentableName, this.getClass().getClassLoader());
        } catch (MimeTypeParseException mtpe) {
            throw new IllegalArgumentException("failed to parse:" + mimeType);
        } catch (ClassNotFoundException cnfe) {
            throw new IllegalArgumentException("can't find specified class: " + cnfe.getMessage());
        }
    }

    /**
     * Constructs a <code>DataFlavor</code> that represents a
     * <code>MimeType</code>.
     * <p>
     * The returned <code>DataFlavor</code> will have the following
     * characteristics:
     * <p>
     * If the mimeType is
     * "application/x-java-serialized-object; class=&lt;representation class&gt;",
     * the result is the same as calling
     * <code>new DataFlavor(Class:forName(&lt;representation class&gt;)</code>.
     * <p>
     * Otherwise:
     * <pre>
     *     representationClass = InputStream
     *     mimeType            = mimeType
     * </pre>
     * <p>
     *  构造代表<code> MimeType </code>的<code> DataFlavor </code>
     * <p>
     *  返回的<code> DataFlavor </code>将具有以下特征：
     * <p>
     *  如果mimeType是"application / x-java-serialized-object; class =&lt; representation class&gt;",则结果与调用<code>
     *  new DataFlavor(Class：forName(&lt; representation class&gt;)</code >。
     * <p>
     *  除此以外：
     * <pre>
     *  representationClass = InputStream mimeType = mimeType
     * </pre>
     * 
     * @param mimeType the string used to identify the MIME type for this flavor
     * @param humanPresentableName the human-readable string used to
     *          identify this flavor
     * @param classLoader the class loader to use
     * @exception ClassNotFoundException if the class is not loaded
     * @exception IllegalArgumentException if <code>mimeType</code> is
     *                 invalid
     * @exception NullPointerException if <code>mimeType</code> is null
     */
    public DataFlavor(String mimeType, String humanPresentableName, ClassLoader classLoader) throws ClassNotFoundException {
        super();
        if (mimeType == null) {
            throw new NullPointerException("mimeType");
        }
        try {
            initialize(mimeType, humanPresentableName, classLoader);
        } catch (MimeTypeParseException mtpe) {
            throw new IllegalArgumentException("failed to parse:" + mimeType);
        }
    }

    /**
     * Constructs a <code>DataFlavor</code> from a <code>mimeType</code> string.
     * The string can specify a "class=&lt;fully specified Java class name&gt;"
     * parameter to create a <code>DataFlavor</code> with the desired
     * representation class. If the string does not contain "class=" parameter,
     * <code>java.io.InputStream</code> is used as default.
     *
     * <p>
     * 从<code> mimeType </code>字符串构造<code> DataFlavor </code>字符串可以指定"class =&lt; fully specified Java class 
     * name&gt;参数使用所需的表示类创建一个<code> DataFlavor </code>如果字符串不包含"class ="参数,则使用<code> javaioInputStream </code>
     * 。
     * 
     * 
     * @param mimeType the string used to identify the MIME type for this flavor;
     *                 if the class specified by "class=" parameter is not
     *                 successfully loaded, then an
     *                 <code>ClassNotFoundException</code> is thrown
     * @exception ClassNotFoundException if the class is not loaded
     * @exception IllegalArgumentException if <code>mimeType</code> is
     *                 invalid
     * @exception NullPointerException if <code>mimeType</code> is null
     */
    public DataFlavor(String mimeType) throws ClassNotFoundException {
        super();
        if (mimeType == null) {
            throw new NullPointerException("mimeType");
        }
        try {
            initialize(mimeType, null, this.getClass().getClassLoader());
        } catch (MimeTypeParseException mtpe) {
            throw new IllegalArgumentException("failed to parse:" + mimeType);
        }
    }

   /**
    * Common initialization code called from various constructors.
    *
    * <p>
    *  从各种构造函数调用的公共初始化代码
    * 
    * 
    * @param mimeType the MIME Content Type (must have a class= param)
    * @param humanPresentableName the human Presentable Name or
    *                 <code>null</code>
    * @param classLoader the fallback class loader to resolve against
    *
    * @throws MimeTypeParseException
    * @throws ClassNotFoundException
    * @throws  NullPointerException if <code>mimeType</code> is null
    *
    * @see #tryToLoadClass
    */
    private void initialize(String mimeType, String humanPresentableName, ClassLoader classLoader) throws MimeTypeParseException, ClassNotFoundException {
        if (mimeType == null) {
            throw new NullPointerException("mimeType");
        }

        this.mimeType = new MimeType(mimeType); // throws

        String rcn = getParameter("class");

        if (rcn == null) {
            if ("application/x-java-serialized-object".equals(this.mimeType.getBaseType()))

                throw new IllegalArgumentException("no representation class specified for:" + mimeType);
            else
                representationClass = java.io.InputStream.class; // default
        } else { // got a class name
            representationClass = DataFlavor.tryToLoadClass(rcn, classLoader);
        }

        this.mimeType.setParameter("class", representationClass.getName());

        if (humanPresentableName == null) {
            humanPresentableName = this.mimeType.getParameter("humanPresentableName");
            if (humanPresentableName == null)
                humanPresentableName = this.mimeType.getPrimaryType() + "/" + this.mimeType.getSubType();
        }

        this.humanPresentableName = humanPresentableName; // set it.

        this.mimeType.removeParameter("humanPresentableName"); // just in case
    }

    /**
     * String representation of this <code>DataFlavor</code> and its
     * parameters. The resulting <code>String</code> contains the name of
     * the <code>DataFlavor</code> class, this flavor's MIME type, and its
     * representation class. If this flavor has a primary MIME type of "text",
     * supports the charset parameter, and has an encoded representation, the
     * flavor's charset is also included. See <code>selectBestTextFlavor</code>
     * for a list of text flavors which support the charset parameter.
     *
     * <p>
     * 此<code> DataFlavor </code>及其参数的字符串表示形式<code> String </code>包含<code> DataFlavor </code>类的名称,此类型的MIME类型
     * 及其表示类If这个flavor有一个主要的MIME类型"text",支持charset参数,并且有一个编码表示形式,也包括flavor的字符集。
     * 参见<code> selectBestTextFlavor </code>了解支持charset参数。
     * 
     * 
     * @return  string representation of this <code>DataFlavor</code>
     * @see #selectBestTextFlavor
     */
    public String toString() {
        String string = getClass().getName();
        string += "["+paramString()+"]";
        return string;
    }

    private String paramString() {
        String params = "";
        params += "mimetype=";
        if (mimeType == null) {
            params += "null";
        } else {
            params += mimeType.getBaseType();
        }
        params += ";representationclass=";
        if (representationClass == null) {
           params += "null";
        } else {
           params += representationClass.getName();
        }
        if (DataTransferer.isFlavorCharsetTextType(this) &&
            (isRepresentationClassInputStream() ||
             isRepresentationClassByteBuffer() ||
             byte[].class.equals(representationClass)))
        {
            params += ";charset=" + DataTransferer.getTextCharset(this);
        }
        return params;
    }

    /**
     * Returns a <code>DataFlavor</code> representing plain text with Unicode
     * encoding, where:
     * <pre>
     *     representationClass = java.io.InputStream
     *     mimeType            = "text/plain;
     *                            charset=&lt;platform default Unicode encoding&gt;"
     * </pre>
     * Sun's implementation for Microsoft Windows uses the encoding <code>utf-16le</code>.
     * Sun's implementation for Solaris and Linux uses the encoding
     * <code>iso-10646-ucs-2</code>.
     *
     * <p>
     *  返回表示带有Unicode编码的纯文本的<code> DataFlavor </code>,其中：
     * <pre>
     *  representationClass = javaioInputStream mimeType ="text / plain; charset =&lt; platform default Unic
     * ode encoding&gt;"。
     * </pre>
     * Sun的Microsoft Windows实现使用编码<code> utf-16le </code> Sun的Solaris实现和Linux使用编码<code> iso-10646-ucs-2 </code>
     * 。
     * 
     * 
     * @return a <code>DataFlavor</code> representing plain text
     *    with Unicode encoding
     * @since 1.3
     */
    public static final DataFlavor getTextPlainUnicodeFlavor() {
        String encoding = null;
        DataTransferer transferer = DataTransferer.getInstance();
        if (transferer != null) {
            encoding = transferer.getDefaultUnicodeEncoding();
        }
        return new DataFlavor(
            "text/plain;charset="+encoding
            +";class=java.io.InputStream", "Plain Text");
    }

    /**
     * Selects the best text <code>DataFlavor</code> from an array of <code>
     * DataFlavor</code>s. Only <code>DataFlavor.stringFlavor</code>, and
     * equivalent flavors, and flavors that have a primary MIME type of "text",
     * are considered for selection.
     * <p>
     * Flavors are first sorted by their MIME types in the following order:
     * <ul>
     * <li>"text/sgml"
     * <li>"text/xml"
     * <li>"text/html"
     * <li>"text/rtf"
     * <li>"text/enriched"
     * <li>"text/richtext"
     * <li>"text/uri-list"
     * <li>"text/tab-separated-values"
     * <li>"text/t140"
     * <li>"text/rfc822-headers"
     * <li>"text/parityfec"
     * <li>"text/directory"
     * <li>"text/css"
     * <li>"text/calendar"
     * <li>"application/x-java-serialized-object"
     * <li>"text/plain"
     * <li>"text/&lt;other&gt;"
     * </ul>
     * <p>For example, "text/sgml" will be selected over
     * "text/html", and <code>DataFlavor.stringFlavor</code> will be chosen
     * over <code>DataFlavor.plainTextFlavor</code>.
     * <p>
     * If two or more flavors share the best MIME type in the array, then that
     * MIME type will be checked to see if it supports the charset parameter.
     * <p>
     * The following MIME types support, or are treated as though they support,
     * the charset parameter:
     * <ul>
     * <li>"text/sgml"
     * <li>"text/xml"
     * <li>"text/html"
     * <li>"text/enriched"
     * <li>"text/richtext"
     * <li>"text/uri-list"
     * <li>"text/directory"
     * <li>"text/css"
     * <li>"text/calendar"
     * <li>"application/x-java-serialized-object"
     * <li>"text/plain"
     * </ul>
     * The following MIME types do not support, or are treated as though they
     * do not support, the charset parameter:
     * <ul>
     * <li>"text/rtf"
     * <li>"text/tab-separated-values"
     * <li>"text/t140"
     * <li>"text/rfc822-headers"
     * <li>"text/parityfec"
     * </ul>
     * For "text/&lt;other&gt;" MIME types, the first time the JRE needs to
     * determine whether the MIME type supports the charset parameter, it will
     * check whether the parameter is explicitly listed in an arbitrarily
     * chosen <code>DataFlavor</code> which uses that MIME type. If so, the JRE
     * will assume from that point on that the MIME type supports the charset
     * parameter and will not check again. If the parameter is not explicitly
     * listed, the JRE will assume from that point on that the MIME type does
     * not support the charset parameter and will not check again. Because
     * this check is performed on an arbitrarily chosen
     * <code>DataFlavor</code>, developers must ensure that all
     * <code>DataFlavor</code>s with a "text/&lt;other&gt;" MIME type specify
     * the charset parameter if it is supported by that MIME type. Developers
     * should never rely on the JRE to substitute the platform's default
     * charset for a "text/&lt;other&gt;" DataFlavor. Failure to adhere to this
     * restriction will lead to undefined behavior.
     * <p>
     * If the best MIME type in the array does not support the charset
     * parameter, the flavors which share that MIME type will then be sorted by
     * their representation classes in the following order:
     * <code>java.io.InputStream</code>, <code>java.nio.ByteBuffer</code>,
     * <code>[B</code>, &lt;all others&gt;.
     * <p>
     * If two or more flavors share the best representation class, or if no
     * flavor has one of the three specified representations, then one of those
     * flavors will be chosen non-deterministically.
     * <p>
     * If the best MIME type in the array does support the charset parameter,
     * the flavors which share that MIME type will then be sorted by their
     * representation classes in the following order:
     * <code>java.io.Reader</code>, <code>java.lang.String</code>,
     * <code>java.nio.CharBuffer</code>, <code>[C</code>, &lt;all others&gt;.
     * <p>
     * If two or more flavors share the best representation class, and that
     * representation is one of the four explicitly listed, then one of those
     * flavors will be chosen non-deterministically. If, however, no flavor has
     * one of the four specified representations, the flavors will then be
     * sorted by their charsets. Unicode charsets, such as "UTF-16", "UTF-8",
     * "UTF-16BE", "UTF-16LE", and their aliases, are considered best. After
     * them, the platform default charset and its aliases are selected.
     * "US-ASCII" and its aliases are worst. All other charsets are chosen in
     * alphabetical order, but only charsets supported by this implementation
     * of the Java platform will be considered.
     * <p>
     * If two or more flavors share the best charset, the flavors will then
     * again be sorted by their representation classes in the following order:
     * <code>java.io.InputStream</code>, <code>java.nio.ByteBuffer</code>,
     * <code>[B</code>, &lt;all others&gt;.
     * <p>
     * If two or more flavors share the best representation class, or if no
     * flavor has one of the three specified representations, then one of those
     * flavors will be chosen non-deterministically.
     *
     * <p>
     *  从<code> DataFlavor </code> s Only <code> DataFlavorstringFlavor </code>数组中选择最佳文本<code> DataFlavor </code>
     * ,以及具有主MIME类型为"text" ",被考虑选择。
     * <p>
     *  风味首先按照它们的MIME类型按以下顺序排序：
     * <ul>
     * <li>"text / sgml"<li>"text / xml"<li>"text / html"<li>"text / rtf"<li>"text / rich" >"text / tab-sepa
     * rated-values"<li>"text / t140"<li>"text / rfc822-headers"<li>"text / parityfec" text / directory"<li>
     * "text / css"<li>"text / calendar"<li>"application / x-java-serialized-object"<li>"text / plain"其他&gt;
     * 。
     * </ul>
     *  <p>例如,将通过"text / html"选择"text / sgml",将通过<code> DataFlavorplainTextFlavor </code>选择<code> DataFlavor
     * stringFlavor </code>。
     * <p>
     *  如果两个或多个口味在数组中共享最好的MIME类型,那么将检查该MIME类型以查看它是否支持charset参数
     * <p>
     *  以下MIME类型支持或被视为支持charset参数：
     * <ul>
     * <li>"text / richtext"<li>"text / url-list"<li>"text / <li>"text / directory"<li>"text / css"<li>"text
     *  / calendar"<li>"application / x-java-serialized-object"。
     * </ul>
     *  以下MIME类型不支持或视为不支持charset参数：
     * <ul>
     *  <li>"text / rtf"<li>"text / tab-separated-values"<li>"text / t140"<li>"text / rfc822-headers"
     * </ul>
     * 对于"text /&lt; other&gt;" MIME类型,第一次JRE需要确定MIME类型是否支持charset参数,它将检查该参数是否显式地列在任意选择的使用该MIME类型的<code> Dat
     * aFlavor </code>中。
     * 如果是, JRE将从这一点假定MIME类型支持charset参数,并且不会再次检查如果没有显式地列出参数,则JRE将假定从该点开始MIME类型不支持charset参数,并且不会检查再次因为这个检查是在任
     * 意选择的&lt; code&gt; DataFlavor&lt; / code&gt;上执行的,开发人员必须确保所有<code> DataFlavor </code> MIME类型指定charset参数
     * (如果该MIME类型支持)开发人员不应该依赖JRE将平台的默认字符集替换为"text /&lt; other&gt;" DataFlavor不遵守此限制将导致未定义的行为。
     * <p>
     * 如果数组中最好的MIME类型不支持charset参数,那么共享该MIME类型的类型将按照它们的表示类按以下顺序排序：<code> javaioInputStream </code>,<code> jav
     * anioByteBuffer < code>,<code> [B </code>,&lt; all others&gt;。
     * <p>
     *  如果两个或多个口味共享最佳表示类,或者如果没有口味具有三个指定表示中的一个,那么那些口味之一将被非确定性地选择
     * <p>
     * 如果数组中最好的MIME类型支持charset参数,那么共享该MIME类型的风格将按照它们的表示类按以下顺序排序：<code> javaioReader </code>,<code> javalangS
     * tring </code >,<code> javanioCharBuffer </code>,<code> [C </code>,&lt; all others&gt;。
     * <p>
     * 如果两个或多个口味共享最佳表示类,并且该表示是四个明确列出的之一,那么那些口味之一将被非确定性地选择。
     * 然而,如果没有口味具有四个指定表示中的一个,则口味将然后按其字符集排序Unicode字符集,如"UTF-16","UTF-8","UTF-16BE","UTF-16LE"及其别名,被认为是最好的之后,平
     * 台默认字符集和其别名被选择为"US-ASCII"并且其别名最差所有其他字符集按字母顺序选择,但只有Java平台的此实现支持的字符集才会被考虑。
     * 如果两个或多个口味共享最佳表示类,并且该表示是四个明确列出的之一,那么那些口味之一将被非确定性地选择。
     * <p>
     * 如果两个或多个口味共享最好的字符集,则口味将再次通过它们的表示类按以下顺序排序：<code> javaioInputStream </code>,<code> javanioByteBuffer </code>
     * ,<code> / code>,&lt; all others&gt;。
     * <p>
     *  如果两个或多个口味共享最佳表示类,或者如果没有口味具有三个指定表示中的一个,那么那些口味之一将被非确定性地选择
     * 
     * 
     * @param availableFlavors an array of available <code>DataFlavor</code>s
     * @return the best (highest fidelity) flavor according to the rules
     *         specified above, or <code>null</code>,
     *         if <code>availableFlavors</code> is <code>null</code>,
     *         has zero length, or contains no text flavors
     * @since 1.3
     */
    public static final DataFlavor selectBestTextFlavor(
                                       DataFlavor[] availableFlavors) {
        if (availableFlavors == null || availableFlavors.length == 0) {
            return null;
        }

        if (textFlavorComparator == null) {
            textFlavorComparator = new TextFlavorComparator();
        }

        DataFlavor bestFlavor =
            (DataFlavor)Collections.max(Arrays.asList(availableFlavors),
                                        textFlavorComparator);

        if (!bestFlavor.isFlavorTextType()) {
            return null;
        }

        return bestFlavor;
    }

    private static Comparator<DataFlavor> textFlavorComparator;

    static class TextFlavorComparator
        extends DataTransferer.DataFlavorComparator {

        /**
         * Compares two <code>DataFlavor</code> objects. Returns a negative
         * integer, zero, or a positive integer as the first
         * <code>DataFlavor</code> is worse than, equal to, or better than the
         * second.
         * <p>
         * <code>DataFlavor</code>s are ordered according to the rules outlined
         * for <code>selectBestTextFlavor</code>.
         *
         * <p>
         *  比较两个<code> DataFlavor </code>对象返回一个负整数,零或正整数作为第一个<code> DataFlavor </code>比第二个更糟糕,等于或更好
         * <p>
         *  <code> DataFlavor </code>根据<code> selectBestTextFlavor </code>中列出的规则进行排序
         * 
         * 
         * @param obj1 the first <code>DataFlavor</code> to be compared
         * @param obj2 the second <code>DataFlavor</code> to be compared
         * @return a negative integer, zero, or a positive integer as the first
         *         argument is worse, equal to, or better than the second
         * @throws ClassCastException if either of the arguments is not an
         *         instance of <code>DataFlavor</code>
         * @throws NullPointerException if either of the arguments is
         *         <code>null</code>
         *
         * @see #selectBestTextFlavor
         */
        public int compare(Object obj1, Object obj2) {
            DataFlavor flavor1 = (DataFlavor)obj1;
            DataFlavor flavor2 = (DataFlavor)obj2;

            if (flavor1.isFlavorTextType()) {
                if (flavor2.isFlavorTextType()) {
                    return super.compare(obj1, obj2);
                } else {
                    return 1;
                }
            } else if (flavor2.isFlavorTextType()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    /**
     * Gets a Reader for a text flavor, decoded, if necessary, for the expected
     * charset (encoding). The supported representation classes are
     * <code>java.io.Reader</code>, <code>java.lang.String</code>,
     * <code>java.nio.CharBuffer</code>, <code>[C</code>,
     * <code>java.io.InputStream</code>, <code>java.nio.ByteBuffer</code>,
     * and <code>[B</code>.
     * <p>
     * Because text flavors which do not support the charset parameter are
     * encoded in a non-standard format, this method should not be called for
     * such flavors. However, in order to maintain backward-compatibility,
     * if this method is called for such a flavor, this method will treat the
     * flavor as though it supports the charset parameter and attempt to
     * decode it accordingly. See <code>selectBestTextFlavor</code> for a list
     * of text flavors which do not support the charset parameter.
     *
     * <p>
     * 支持的表示类有<code> javaioReader </code>,<code> javalangString </code>,<code> javanioCharBuffer </code >,<code>
     *  [C </code>,<code> javaioInputStream </code>,<code> javanioByteBuffer </code>。
     * <p>
     * 因为不支持charset参数的文本风格以非标准格式编码,所以不应该为这种风格调用此方法。
     * 但是,为了保持向后兼容性,如果为这种风格调用此方法,则此方法将处理风味,如同它支持charset参数,并尝试相应地解码查看<code> selectBestTextFlavor </code>以获取不支
     * 持charset参数的文本风格的列表。
     * 因为不支持charset参数的文本风格以非标准格式编码,所以不应该为这种风格调用此方法。
     * 
     * 
     * @param transferable the <code>Transferable</code> whose data will be
     *        requested in this flavor
     *
     * @return a <code>Reader</code> to read the <code>Transferable</code>'s
     *         data
     *
     * @exception IllegalArgumentException if the representation class
     *            is not one of the seven listed above
     * @exception IllegalArgumentException if the <code>Transferable</code>
     *            has <code>null</code> data
     * @exception NullPointerException if the <code>Transferable</code> is
     *            <code>null</code>
     * @exception UnsupportedEncodingException if this flavor's representation
     *            is <code>java.io.InputStream</code>,
     *            <code>java.nio.ByteBuffer</code>, or <code>[B</code> and
     *            this flavor's encoding is not supported by this
     *            implementation of the Java platform
     * @exception UnsupportedFlavorException if the <code>Transferable</code>
     *            does not support this flavor
     * @exception IOException if the data cannot be read because of an
     *            I/O error
     * @see #selectBestTextFlavor
     * @since 1.3
     */
    public Reader getReaderForText(Transferable transferable)
        throws UnsupportedFlavorException, IOException
    {
        Object transferObject = transferable.getTransferData(this);
        if (transferObject == null) {
            throw new IllegalArgumentException
                ("getTransferData() returned null");
        }

        if (transferObject instanceof Reader) {
            return (Reader)transferObject;
        } else if (transferObject instanceof String) {
            return new StringReader((String)transferObject);
        } else if (transferObject instanceof CharBuffer) {
            CharBuffer buffer = (CharBuffer)transferObject;
            int size = buffer.remaining();
            char[] chars = new char[size];
            buffer.get(chars, 0, size);
            return new CharArrayReader(chars);
        } else if (transferObject instanceof char[]) {
            return new CharArrayReader((char[])transferObject);
        }

        InputStream stream = null;

        if (transferObject instanceof InputStream) {
            stream = (InputStream)transferObject;
        } else if (transferObject instanceof ByteBuffer) {
            ByteBuffer buffer = (ByteBuffer)transferObject;
            int size = buffer.remaining();
            byte[] bytes = new byte[size];
            buffer.get(bytes, 0, size);
            stream = new ByteArrayInputStream(bytes);
        } else if (transferObject instanceof byte[]) {
            stream = new ByteArrayInputStream((byte[])transferObject);
        }

        if (stream == null) {
            throw new IllegalArgumentException("transfer data is not Reader, String, CharBuffer, char array, InputStream, ByteBuffer, or byte array");
        }

        String encoding = getParameter("charset");
        return (encoding == null)
            ? new InputStreamReader(stream)
            : new InputStreamReader(stream, encoding);
    }

    /**
     * Returns the MIME type string for this <code>DataFlavor</code>.
     * <p>
     *  返回此<code> DataFlavor </code>的MIME类型字符串
     * 
     * 
     * @return the MIME type string for this flavor
     */
    public String getMimeType() {
        return (mimeType != null) ? mimeType.toString() : null;
    }

    /**
     * Returns the <code>Class</code> which objects supporting this
     * <code>DataFlavor</code> will return when this <code>DataFlavor</code>
     * is requested.
     * <p>
     *  返回<code> Class </code>当请求此<code> DataFlavor </code>时,支持此<code> DataFlavor </code>
     * 
     * 
     * @return the <code>Class</code> which objects supporting this
     * <code>DataFlavor</code> will return when this <code>DataFlavor</code>
     * is requested
     */
    public Class<?> getRepresentationClass() {
        return representationClass;
    }

    /**
     * Returns the human presentable name for the data format that this
     * <code>DataFlavor</code> represents.  This name would be localized
     * for different countries.
     * <p>
     * 返回此<code> DataFlavor </code>表示的数据格式的人类可呈现名称此名称将针对不同国家/地区进行本地化
     * 
     * 
     * @return the human presentable name for the data format that this
     *    <code>DataFlavor</code> represents
     */
    public String getHumanPresentableName() {
        return humanPresentableName;
    }

    /**
     * Returns the primary MIME type for this <code>DataFlavor</code>.
     * <p>
     *  返回此<code> DataFlavor </code>的主要MIME类型
     * 
     * 
     * @return the primary MIME type of this <code>DataFlavor</code>
     */
    public String getPrimaryType() {
        return (mimeType != null) ? mimeType.getPrimaryType() : null;
    }

    /**
     * Returns the sub MIME type of this <code>DataFlavor</code>.
     * <p>
     *  返回此<code> DataFlavor </code>的子MIME类型
     * 
     * 
     * @return the Sub MIME type of this <code>DataFlavor</code>
     */
    public String getSubType() {
        return (mimeType != null) ? mimeType.getSubType() : null;
    }

    /**
     * Returns the human presentable name for this <code>DataFlavor</code>
     * if <code>paramName</code> equals "humanPresentableName".  Otherwise
     * returns the MIME type value associated with <code>paramName</code>.
     *
     * <p>
     *  如果<code> paramName </code>等于"humanPresentableName",则返回此<code> DataFlavor </code>的人类可呈现的名称否则返回与<code>
     *  paramName </code>。
     * 
     * 
     * @param paramName the parameter name requested
     * @return the value of the name parameter, or <code>null</code>
     *  if there is no associated value
     */
    public String getParameter(String paramName) {
        if (paramName.equals("humanPresentableName")) {
            return humanPresentableName;
        } else {
            return (mimeType != null)
                ? mimeType.getParameter(paramName) : null;
        }
    }

    /**
     * Sets the human presentable name for the data format that this
     * <code>DataFlavor</code> represents. This name would be localized
     * for different countries.
     * <p>
     *  设置此<code> DataFlavor </code>表示的数据格式的人类可表示名称此名称将针对不同国家/地区进行本地化
     * 
     * 
     * @param humanPresentableName the new human presentable name
     */
    public void setHumanPresentableName(String humanPresentableName) {
        this.humanPresentableName = humanPresentableName;
    }

    /**
     * {@inheritDoc}
     * <p>
     * The equals comparison for the {@code DataFlavor} class is implemented
     * as follows: Two <code>DataFlavor</code>s are considered equal if and
     * only if their MIME primary type and subtype and representation class are
     * equal. Additionally, if the primary type is "text", the subtype denotes
     * a text flavor which supports the charset parameter, and the
     * representation class is not <code>java.io.Reader</code>,
     * <code>java.lang.String</code>, <code>java.nio.CharBuffer</code>, or
     * <code>[C</code>, the <code>charset</code> parameter must also be equal.
     * If a charset is not explicitly specified for one or both
     * <code>DataFlavor</code>s, the platform default encoding is assumed. See
     * <code>selectBestTextFlavor</code> for a list of text flavors which
     * support the charset parameter.
     *
     * <p>
     *  {@inheritDoc}
     * <p>
     * {@code DataFlavor}类的equals比较实现如下：当且仅当它们的MIME主类型以及子类型和表示类相等时,两个<code> DataFlavor </code>被认为是相等的此外,如果主类
     * 型是"text",子类型表示支持charset参数的文本flavor,并且表示类不是<code> javaioReader </code>,<code> javalangString </code>,<code>
     *  javanioCharBuffer </code>或<code> [C </code>,<code> charset </code>]参数也必须相等如果没有明确指定一个或两个<code> DataFl
     * avor </code>假设参见<code> selectBestTextFlavor </code>,以获取支持charset参数的文本类型列表。
     * 
     * 
     * @param o the <code>Object</code> to compare with <code>this</code>
     * @return <code>true</code> if <code>that</code> is equivalent to this
     *         <code>DataFlavor</code>; <code>false</code> otherwise
     * @see #selectBestTextFlavor
     */
    public boolean equals(Object o) {
        return ((o instanceof DataFlavor) && equals((DataFlavor)o));
    }

    /**
     * This method has the same behavior as {@link #equals(Object)}.
     * The only difference being that it takes a {@code DataFlavor} instance
     * as a parameter.
     *
     * <p>
     * 此方法具有与{@link #equals(Object)}相同的行为唯一的区别是它需要一个{@code DataFlavor}实例作为参数
     * 
     * 
     * @param that the <code>DataFlavor</code> to compare with
     *        <code>this</code>
     * @return <code>true</code> if <code>that</code> is equivalent to this
     *         <code>DataFlavor</code>; <code>false</code> otherwise
     * @see #selectBestTextFlavor
     */
    public boolean equals(DataFlavor that) {
        if (that == null) {
            return false;
        }
        if (this == that) {
            return true;
        }

        if (!Objects.equals(this.getRepresentationClass(), that.getRepresentationClass())) {
            return false;
        }

        if (mimeType == null) {
            if (that.mimeType != null) {
                return false;
            }
        } else {
            if (!mimeType.match(that.mimeType)) {
                return false;
            }

            if ("text".equals(getPrimaryType())) {
                if (DataTransferer.doesSubtypeSupportCharset(this)
                        && representationClass != null
                        && !isStandardTextRepresentationClass()) {
                    String thisCharset =
                            DataTransferer.canonicalName(this.getParameter("charset"));
                    String thatCharset =
                            DataTransferer.canonicalName(that.getParameter("charset"));
                    if (!Objects.equals(thisCharset, thatCharset)) {
                        return false;
                    }
                }

                if ("html".equals(getSubType())) {
                    String thisDocument = this.getParameter("document");
                    String thatDocument = that.getParameter("document");
                    if (!Objects.equals(thisDocument, thatDocument)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * Compares only the <code>mimeType</code> against the passed in
     * <code>String</code> and <code>representationClass</code> is
     * not considered in the comparison.
     *
     * If <code>representationClass</code> needs to be compared, then
     * <code>equals(new DataFlavor(s))</code> may be used.
     * <p>
     *  仅比较比较中不考虑<code> mimeType </code>与传递的<code> String </code>和<code> representationClass </code>
     * 
     *  如果<code> representationClass </code>需要比较,则可以使用<code> equals(new DataFlavor(s))</code>
     * 
     * 
     * @deprecated As inconsistent with <code>hashCode()</code> contract,
     *             use <code>isMimeTypeEqual(String)</code> instead.
     * @param s the {@code mimeType} to compare.
     * @return true if the String (MimeType) is equal; false otherwise or if
     *         {@code s} is {@code null}
     */
    @Deprecated
    public boolean equals(String s) {
        if (s == null || mimeType == null)
            return false;
        return isMimeTypeEqual(s);
    }

    /**
     * Returns hash code for this <code>DataFlavor</code>.
     * For two equal <code>DataFlavor</code>s, hash codes are equal.
     * For the <code>String</code>
     * that matches <code>DataFlavor.equals(String)</code>, it is not
     * guaranteed that <code>DataFlavor</code>'s hash code is equal
     * to the hash code of the <code>String</code>.
     *
     * <p>
     *  返回此<code> DataFlavor </code>的散列码对于两个相等的<code> DataFlavor </code>,散列码相等对于与<code> DataFlavorequals(Str
     * ing)< / code>,但不能保证<code> DataFlavor </code>的散列码等于<code> String </code>的散列码。
     * 
     * 
     * @return a hash code for this <code>DataFlavor</code>
     */
    public int hashCode() {
        int total = 0;

        if (representationClass != null) {
            total += representationClass.hashCode();
        }

        if (mimeType != null) {
            String primaryType = mimeType.getPrimaryType();
            if (primaryType != null) {
                total += primaryType.hashCode();
            }

            // Do not add subType.hashCode() to the total. equals uses
            // MimeType.match which reports a match if one or both of the
            // subTypes is '*', regardless of the other subType.

            if ("text".equals(primaryType)) {
                if (DataTransferer.doesSubtypeSupportCharset(this)
                        && representationClass != null
                        && !isStandardTextRepresentationClass()) {
                    String charset = DataTransferer.canonicalName(getParameter("charset"));
                    if (charset != null) {
                        total += charset.hashCode();
                    }
                }

                if ("html".equals(getSubType())) {
                    String document = this.getParameter("document");
                    if (document != null) {
                        total += document.hashCode();
                    }
                }
            }
        }

        return total;
    }

    /**
     * Identical to {@link #equals(DataFlavor)}.
     *
     * <p>
     *  与{@link #equals(DataFlavor)}相同
     * 
     * 
     * @param that the <code>DataFlavor</code> to compare with
     *        <code>this</code>
     * @return <code>true</code> if <code>that</code> is equivalent to this
     *         <code>DataFlavor</code>; <code>false</code> otherwise
     * @see #selectBestTextFlavor
     * @since 1.3
     */
    public boolean match(DataFlavor that) {
        return equals(that);
    }

    /**
     * Returns whether the string representation of the MIME type passed in
     * is equivalent to the MIME type of this <code>DataFlavor</code>.
     * Parameters are not included in the comparison.
     *
     * <p>
     * 返回传递的MIME类型的字符串表示形式是否等同于此<code> DataFlavor </code>的MIME类型参数未包含在比较中
     * 
     * 
     * @param mimeType the string representation of the MIME type
     * @return true if the string representation of the MIME type passed in is
     *         equivalent to the MIME type of this <code>DataFlavor</code>;
     *         false otherwise
     * @throws NullPointerException if mimeType is <code>null</code>
     */
    public boolean isMimeTypeEqual(String mimeType) {
        // JCK Test DataFlavor0117: if 'mimeType' is null, throw NPE
        if (mimeType == null) {
            throw new NullPointerException("mimeType");
        }
        if (this.mimeType == null) {
            return false;
        }
        try {
            return this.mimeType.match(new MimeType(mimeType));
        } catch (MimeTypeParseException mtpe) {
            return false;
        }
    }

    /**
     * Compares the <code>mimeType</code> of two <code>DataFlavor</code>
     * objects. No parameters are considered.
     *
     * <p>
     *  比较两个<code> DataFlavor </code>对象的<code> mimeType </code>没有考虑参数
     * 
     * 
     * @param dataFlavor the <code>DataFlavor</code> to be compared
     * @return true if the <code>MimeType</code>s are equal,
     *  otherwise false
     */

    public final boolean isMimeTypeEqual(DataFlavor dataFlavor) {
        return isMimeTypeEqual(dataFlavor.mimeType);
    }

    /**
     * Compares the <code>mimeType</code> of two <code>DataFlavor</code>
     * objects.  No parameters are considered.
     *
     * <p>
     *  比较两个<code> DataFlavor </code>对象的<code> mimeType </code>没有考虑参数
     * 
     * 
     * @return true if the <code>MimeType</code>s are equal,
     *  otherwise false
     */

    private boolean isMimeTypeEqual(MimeType mtype) {
        if (this.mimeType == null) {
            return (mtype == null);
        }
        return mimeType.match(mtype);
    }

    /**
     * Checks if the representation class is one of the standard text
     * representation classes.
     *
     * <p>
     *  检查表示类是否是标准文本表示类之一
     * 
     * 
     * @return true if the representation class is one of the standard text
     *              representation classes, otherwise false
     */
    private boolean isStandardTextRepresentationClass() {
        return isRepresentationClassReader()
                || String.class.equals(representationClass)
                || isRepresentationClassCharBuffer()
                || char[].class.equals(representationClass);
    }

   /**
    * Does the <code>DataFlavor</code> represent a serialized object?
    * <p>
    *  <code> DataFlavor </code>是否代表一个序列化对象?
    * 
    */

    public boolean isMimeTypeSerializedObject() {
        return isMimeTypeEqual(javaSerializedObjectMimeType);
    }

    public final Class<?> getDefaultRepresentationClass() {
        return ioInputStreamClass;
    }

    public final String getDefaultRepresentationClassAsString() {
        return getDefaultRepresentationClass().getName();
    }

   /**
    * Does the <code>DataFlavor</code> represent a
    * <code>java.io.InputStream</code>?
    * <p>
    *  <code> DataFlavor </code>是否代表一个<code> javaioInputStream </code>?
    * 
    */

    public boolean isRepresentationClassInputStream() {
        return ioInputStreamClass.isAssignableFrom(representationClass);
    }

    /**
     * Returns whether the representation class for this
     * <code>DataFlavor</code> is <code>java.io.Reader</code> or a subclass
     * thereof.
     *
     * <p>
     *  返回此<code> DataFlavor </code>的表示类是<code> javaioReader </code>或其子类
     * 
     * 
     * @since 1.4
     */
    public boolean isRepresentationClassReader() {
        return java.io.Reader.class.isAssignableFrom(representationClass);
    }

    /**
     * Returns whether the representation class for this
     * <code>DataFlavor</code> is <code>java.nio.CharBuffer</code> or a
     * subclass thereof.
     *
     * <p>
     * 返回此<code> DataFlavor </code>的表示类是<code> javanioCharBuffer </code>还是其子类
     * 
     * 
     * @since 1.4
     */
    public boolean isRepresentationClassCharBuffer() {
        return java.nio.CharBuffer.class.isAssignableFrom(representationClass);
    }

    /**
     * Returns whether the representation class for this
     * <code>DataFlavor</code> is <code>java.nio.ByteBuffer</code> or a
     * subclass thereof.
     *
     * <p>
     *  返回此<code> DataFlavor </code>的表示类是<code> javanioByteBuffer </code>还是其子类
     * 
     * 
     * @since 1.4
     */
    public boolean isRepresentationClassByteBuffer() {
        return java.nio.ByteBuffer.class.isAssignableFrom(representationClass);
    }

   /**
    * Returns true if the representation class can be serialized.
    * <p>
    *  如果表示类可以序列化,则返回true
    * 
    * 
    * @return true if the representation class can be serialized
    */

    public boolean isRepresentationClassSerializable() {
        return java.io.Serializable.class.isAssignableFrom(representationClass);
    }

   /**
    * Returns true if the representation class is <code>Remote</code>.
    * <p>
    *  如果表示类是<code> Remote </code>,则返回true
    * 
    * 
    * @return true if the representation class is <code>Remote</code>
    */

    public boolean isRepresentationClassRemote() {
        return DataTransferer.isRemote(representationClass);
    }

   /**
    * Returns true if the <code>DataFlavor</code> specified represents
    * a serialized object.
    * <p>
    *  如果指定的<code> DataFlavor </code>表示序列化对象,则返回true
    * 
    * 
    * @return true if the <code>DataFlavor</code> specified represents
    *   a Serialized Object
    */

    public boolean isFlavorSerializedObjectType() {
        return isRepresentationClassSerializable() && isMimeTypeEqual(javaSerializedObjectMimeType);
    }

    /**
     * Returns true if the <code>DataFlavor</code> specified represents
     * a remote object.
     * <p>
     *  如果指定的<code> DataFlavor </code>表示远程对象,则返回true
     * 
     * 
     * @return true if the <code>DataFlavor</code> specified represents
     *  a Remote Object
     */

    public boolean isFlavorRemoteObjectType() {
        return isRepresentationClassRemote()
            && isRepresentationClassSerializable()
            && isMimeTypeEqual(javaRemoteObjectMimeType);
    }


   /**
    * Returns true if the <code>DataFlavor</code> specified represents
    * a list of file objects.
    * <p>
    *  如果指定的<code> DataFlavor </code>表示文件对象的列表,则返回true
    * 
    * 
    * @return true if the <code>DataFlavor</code> specified represents
    *   a List of File objects
    */

   public boolean isFlavorJavaFileListType() {
        if (mimeType == null || representationClass == null)
            return false;
        return java.util.List.class.isAssignableFrom(representationClass) &&
               mimeType.match(javaFileListFlavor.mimeType);

   }

    /**
     * Returns whether this <code>DataFlavor</code> is a valid text flavor for
     * this implementation of the Java platform. Only flavors equivalent to
     * <code>DataFlavor.stringFlavor</code> and <code>DataFlavor</code>s with
     * a primary MIME type of "text" can be valid text flavors.
     * <p>
     * If this flavor supports the charset parameter, it must be equivalent to
     * <code>DataFlavor.stringFlavor</code>, or its representation must be
     * <code>java.io.Reader</code>, <code>java.lang.String</code>,
     * <code>java.nio.CharBuffer</code>, <code>[C</code>,
     * <code>java.io.InputStream</code>, <code>java.nio.ByteBuffer</code>, or
     * <code>[B</code>. If the representation is
     * <code>java.io.InputStream</code>, <code>java.nio.ByteBuffer</code>, or
     * <code>[B</code>, then this flavor's <code>charset</code> parameter must
     * be supported by this implementation of the Java platform. If a charset
     * is not specified, then the platform default charset, which is always
     * supported, is assumed.
     * <p>
     * If this flavor does not support the charset parameter, its
     * representation must be <code>java.io.InputStream</code>,
     * <code>java.nio.ByteBuffer</code>, or <code>[B</code>.
     * <p>
     * See <code>selectBestTextFlavor</code> for a list of text flavors which
     * support the charset parameter.
     *
     * <p>
     * 返回这个<code> DataFlavor </code>是否是Java平台实现的有效文本风格只有与主MIME类型相同的<code> DataFlavorstringFlavor </code>和<code>
     *  DataFlavor </code>的"文本"可以是有效的文本风格。
     * <p>
     * 如果这个flavor支持charset参数,它必须等同于<code> DataFlavorstringFlavor </code>,或者它的表示形式必须是<code> javaioReader </code>
     * ,<code> javalangString </code>,<code> javanioCharBuffer < / code>,<code> [C </code>,<code> javaioInpu
     * tStream </code>,<code> javanioByteBuffer </code>或<code> [B </code>如果表示是<code> javaioInputStream </code>
     * ,<code> javanioByteBuffer </code>或<code> [B </code>,那么此平台的<code> charset </code>参数必须由Java平台的这个实现支持。
     * 未指定,则假定平台缺省字符集(始终支持)。
     * <p>
     * 如果这个flavor不支持charset参数,它的表示形式必须是<code> javaioInputStream </code>,<code> javanioByteBuffer </code>或<code>
     * 。
     * <p>
     *  有关支持charset参数的文本格式列表,请参阅<code> selectBestTextFlavor </code>
     * 
     * 
     * @return <code>true</code> if this <code>DataFlavor</code> is a valid
     *         text flavor as described above; <code>false</code> otherwise
     * @see #selectBestTextFlavor
     * @since 1.4
     */
    public boolean isFlavorTextType() {
        return (DataTransferer.isFlavorCharsetTextType(this) ||
                DataTransferer.isFlavorNoncharsetTextType(this));
    }

   /**
    * Serializes this <code>DataFlavor</code>.
    * <p>
    *  将此<code> DataFlavor </code>序列化
    * 
    */

   public synchronized void writeExternal(ObjectOutput os) throws IOException {
       if (mimeType != null) {
           mimeType.setParameter("humanPresentableName", humanPresentableName);
           os.writeObject(mimeType);
           mimeType.removeParameter("humanPresentableName");
       } else {
           os.writeObject(null);
       }

       os.writeObject(representationClass);
   }

   /**
    * Restores this <code>DataFlavor</code> from a Serialized state.
    * <p>
    *  从序列化状态恢复此<code> DataFlavor </code>
    * 
    */

   public synchronized void readExternal(ObjectInput is) throws IOException , ClassNotFoundException {
       String rcn = null;
        mimeType = (MimeType)is.readObject();

        if (mimeType != null) {
            humanPresentableName =
                mimeType.getParameter("humanPresentableName");
            mimeType.removeParameter("humanPresentableName");
            rcn = mimeType.getParameter("class");
            if (rcn == null) {
                throw new IOException("no class parameter specified in: " +
                                      mimeType);
            }
        }

        try {
            representationClass = (Class)is.readObject();
        } catch (OptionalDataException ode) {
            if (!ode.eof || ode.length != 0) {
                throw ode;
            }
            // Ensure backward compatibility.
            // Old versions didn't write the representation class to the stream.
            if (rcn != null) {
                representationClass =
                    DataFlavor.tryToLoadClass(rcn, getClass().getClassLoader());
            }
        }
   }

   /**
    * Returns a clone of this <code>DataFlavor</code>.
    * <p>
    *  返回此<code> DataFlavor </code>的克隆
    * 
    * 
    * @return a clone of this <code>DataFlavor</code>
    */

    public Object clone() throws CloneNotSupportedException {
        Object newObj = super.clone();
        if (mimeType != null) {
            ((DataFlavor)newObj).mimeType = (MimeType)mimeType.clone();
        }
        return newObj;
    } // clone()

   /**
    * Called on <code>DataFlavor</code> for every MIME Type parameter
    * to allow <code>DataFlavor</code> subclasses to handle special
    * parameters like the text/plain <code>charset</code>
    * parameters, whose values are case insensitive.  (MIME type parameter
    * values are supposed to be case sensitive.
    * <p>
    * This method is called for each parameter name/value pair and should
    * return the normalized representation of the <code>parameterValue</code>.
    *
    * This method is never invoked by this implementation from 1.1 onwards.
    *
    * <p>
    *  对每个MIME类型参数调用<code> DataFlavor </code>,以允许<code> DataFlavor </code>子类处理特殊参数,如text / plain <code> cha
    * rset </code>参数,其值不区分大小写(MIME类型参数值应该区分大小写。
    * <p>
    * 对每个参数名称/值对调用此方法,并应返回<code> parameterValue </code>的规范化表示
    * 
    *  从11开始,此方法从未调用此方法
    * 
    * 
    * @deprecated
    */
    @Deprecated
    protected String normalizeMimeTypeParameter(String parameterName, String parameterValue) {
        return parameterValue;
    }

   /**
    * Called for each MIME type string to give <code>DataFlavor</code> subtypes
    * the opportunity to change how the normalization of MIME types is
    * accomplished.  One possible use would be to add default
    * parameter/value pairs in cases where none are present in the MIME
    * type string passed in.
    *
    * This method is never invoked by this implementation from 1.1 onwards.
    *
    * <p>
    *  调用每个MIME类型字符串以给予<code> DataFlavor </code>子类型改变MIME类型的规范化的机会一个可能的用途是在MIME中不存在的情况下添加默认参数/值对类型字符串传入
    * 
    *  从11开始,此方法从未调用此方法
    * 
    * 
    * @deprecated
    */
    @Deprecated
    protected String normalizeMimeType(String mimeType) {
        return mimeType;
    }

    /*
     * fields
     * <p>
     */

    /* placeholder for caching any platform-specific data for flavor */

    transient int       atom;

    /* Mime Type of DataFlavor */

    MimeType            mimeType;

    private String      humanPresentableName;

    /** Java class of objects this DataFlavor represents **/

    private Class<?>       representationClass;

} // class DataFlavor
