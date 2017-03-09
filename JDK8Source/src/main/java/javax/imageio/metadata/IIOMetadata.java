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

package javax.imageio.metadata;

import org.w3c.dom.Node;
import java.lang.reflect.Method;

/**
 * An abstract class to be extended by objects that represent metadata
 * (non-image data) associated with images and streams.  Plug-ins
 * represent metadata using opaque, plug-in specific objects.  These
 * objects, however, provide the ability to access their internal
 * information as a tree of <code>IIOMetadataNode</code> objects that
 * support the XML DOM interfaces as well as additional interfaces for
 * storing non-textual data and retrieving information about legal
 * data values.  The format of such trees is plug-in dependent, but
 * plug-ins may choose to support a plug-in neutral format described
 * below.  A single plug-in may support multiple metadata formats,
 * whose names maybe determined by calling
 * <code>getMetadataFormatNames</code>.  The plug-in may also support
 * a single special format, referred to as the "native" format, which
 * is designed to encode its metadata losslessly.  This format will
 * typically be designed specifically to work with a specific file
 * format, so that images may be loaded and saved in the same format
 * with no loss of metadata, but may be less useful for transferring
 * metadata between an <code>ImageReader</code> and an
 * <code>ImageWriter</code> for different image formats.  To convert
 * between two native formats as losslessly as the image file formats
 * will allow, an <code>ImageTranscoder</code> object must be used.
 *
 * <p>
 *  要由表示与图像和流相关联的元数据(非图像数据)的对象扩展的抽象类。插件使用不透明的插件特定对象来表示元数据。
 * 然而,这些对象提供了访问它们的内部信息的能力,如支持XML DOM接口的<code> IIOMetadataNode </code>对象的树以及用于存储非文本数据和检索关于法律数据值的信息的附加接口。
 * 这种树的格式是依赖于插件的,但是插件可以选择支持下面描述的插件中性格式。
 * 单个插件可以支持多种元数据格式,其名称可以通过调用<code> getMetadataFormatNames </code>来确定。
 * 插件还可以支持被称为"本地"格式的单个特殊格式,其被设计为无损地对其元数据进行编码。
 * 此格式通常专门设计为使用特定的文件格式,以便可以以相同的格式加载和保存图像而不丢失元数据,但是在<code> ImageReader </code之间传输元数据可能不太有用>和用于不同图像格式的<code>
 *  ImageWriter </code>。
 * 插件还可以支持被称为"本地"格式的单个特殊格式,其被设计为无损地对其元数据进行编码。
 * 要像两个原生格式一样无损地转换为图像文件格式允许,必须使用一个<code> ImageTranscoder </code>对象。
 * 
 * 
 * @see javax.imageio.ImageReader#getImageMetadata
 * @see javax.imageio.ImageReader#getStreamMetadata
 * @see javax.imageio.ImageReader#readAll
 * @see javax.imageio.ImageWriter#getDefaultStreamMetadata
 * @see javax.imageio.ImageWriter#getDefaultImageMetadata
 * @see javax.imageio.ImageWriter#write
 * @see javax.imageio.ImageWriter#convertImageMetadata
 * @see javax.imageio.ImageWriter#convertStreamMetadata
 * @see javax.imageio.IIOImage
 * @see javax.imageio.ImageTranscoder
 *
 */
public abstract class IIOMetadata {

    /**
     * A boolean indicating whether the concrete subclass supports the
     * standard metadata format, set via the constructor.
     * <p>
     * 一个布尔值,指示具体子类是否支持标准元数据格式,通过构造函数设置。
     * 
     */
    protected boolean standardFormatSupported;

    /**
     * The name of the native metadata format for this object,
     * initialized to <code>null</code> and set via the constructor.
     * <p>
     *  此对象的本机元数据格式的名称,初始化为<code> null </code>并通过构造函数设置。
     * 
     */
    protected String nativeMetadataFormatName = null;

    /**
     * The name of the class implementing <code>IIOMetadataFormat</code>
     * and representing the native metadata format, initialized to
     * <code>null</code> and set via the constructor.
     * <p>
     *  实现<code> IIOMetadataFormat </code>并表示本机元数据格式的类的名称,初始化为<code> null </code>并通过构造函数设置。
     * 
     */
    protected String nativeMetadataFormatClassName = null;

    /**
     * An array of names of formats, other than the standard and
     * native formats, that are supported by this plug-in,
     * initialized to <code>null</code> and set via the constructor.
     * <p>
     *  此插件支持的格式名称(标准和原生格式除外)的数组,初始化为<code> null </code>并通过构造函数设置。
     * 
     */
    protected String[] extraMetadataFormatNames = null;

    /**
     * An array of names of classes implementing <code>IIOMetadataFormat</code>
     * and representing the metadata formats, other than the standard and
     * native formats, that are supported by this plug-in,
     * initialized to <code>null</code> and set via the constructor.
     * <p>
     *  实现<code> IIOMetadataFormat </code>的类的名称数组,表示此插件支持的标准和原生格式以外的元数据格式,初始化为<code> null </code>通过构造函数。
     * 
     */
    protected String[] extraMetadataFormatClassNames = null;

    /**
     * An <code>IIOMetadataController</code> that is suggested for use
     * as the controller for this <code>IIOMetadata</code> object.  It
     * may be retrieved via <code>getDefaultController</code>.  To
     * install the default controller, call
     * <code>setController(getDefaultController())</code>.  This
     * instance variable should be set by subclasses that choose to
     * provide their own default controller, usually a GUI, for
     * setting parameters.
     *
     * <p>
     *  建议用作此<code> IIOMetadata </code>对象的控制器的<code> IIOMetadataController </code>。
     * 它可以通过<code> getDefaultController </code>检索。
     * 要安装默认控制器,调用<code> setController(getDefaultController())</code>。
     * 这个实例变量应该由子类设置,子类选择提供自己的默认控制器(通常是GUI)来设置参数。
     * 
     * 
     * @see IIOMetadataController
     * @see #getDefaultController
     */
    protected IIOMetadataController defaultController = null;

    /**
     * The <code>IIOMetadataController</code> that will be
     * used to provide settings for this <code>IIOMetadata</code>
     * object when the <code>activateController</code> method
     * is called.  This value overrides any default controller,
     * even when <code>null</code>.
     *
     * <p>
     * 当调用<code> activateController </code>方法时,将用于为<code> IIOMetadata </code>对象提供设置的<code> IIOMetadataContro
     * ller </code>此值覆盖所有默认控制器,即使<code> null </code>。
     * 
     * 
     * @see IIOMetadataController
     * @see #setController(IIOMetadataController)
     * @see #hasController()
     * @see #activateController()
     */
    protected IIOMetadataController controller = null;

    /**
     * Constructs an empty <code>IIOMetadata</code> object.  The
     * subclass is responsible for supplying values for all protected
     * instance variables that will allow any non-overridden default
     * implementations of methods to satisfy their contracts.  For example,
     * <code>extraMetadataFormatNames</code> should not have length 0.
     * <p>
     *  构造一个空的<code> IIOMetadata </code>对象。子类负责为所有受保护的实例变量提供值,这将允许方法的任何未覆盖的默认实现来满足其合同。
     * 例如,<code> extraMetadataFormatNames </code>的长度不能为0。
     * 
     */
    protected IIOMetadata() {}

    /**
     * Constructs an <code>IIOMetadata</code> object with the given
     * format names and format class names, as well as a boolean
     * indicating whether the standard format is supported.
     *
     * <p> This constructor does not attempt to check the class names
     * for validity.  Invalid class names may cause exceptions in
     * subsequent calls to <code>getMetadataFormat</code>.
     *
     * <p>
     *  构造具有给定格式名称和格式类名称的<code> IIOMetadata </code>对象,以及指示是否支持标准格式的布尔值。
     * 
     *  <p>此构造函数不尝试检查类名称的有效性。无效的类名可能会导致对<code> getMetadataFormat </code>的后续调用中的异常。
     * 
     * 
     * @param standardMetadataFormatSupported <code>true</code> if
     * this object can return or accept a DOM tree using the standard
     * metadata format.
     * @param nativeMetadataFormatName the name of the native metadata
     * format, as a <code>String</code>, or <code>null</code> if there
     * is no native format.
     * @param nativeMetadataFormatClassName the name of the class of
     * the native metadata format, or <code>null</code> if there is
     * no native format.
     * @param extraMetadataFormatNames an array of <code>String</code>s
     * indicating additional formats supported by this object, or
     * <code>null</code> if there are none.
     * @param extraMetadataFormatClassNames an array of <code>String</code>s
     * indicating the class names of any additional formats supported by
     * this object, or <code>null</code> if there are none.
     *
     * @exception IllegalArgumentException if
     * <code>extraMetadataFormatNames</code> has length 0.
     * @exception IllegalArgumentException if
     * <code>extraMetadataFormatNames</code> and
     * <code>extraMetadataFormatClassNames</code> are neither both
     * <code>null</code>, nor of the same length.
     */
    protected IIOMetadata(boolean standardMetadataFormatSupported,
                          String nativeMetadataFormatName,
                          String nativeMetadataFormatClassName,
                          String[] extraMetadataFormatNames,
                          String[] extraMetadataFormatClassNames) {
        this.standardFormatSupported = standardMetadataFormatSupported;
        this.nativeMetadataFormatName = nativeMetadataFormatName;
        this.nativeMetadataFormatClassName = nativeMetadataFormatClassName;
        if (extraMetadataFormatNames != null) {
            if (extraMetadataFormatNames.length == 0) {
                throw new IllegalArgumentException
                    ("extraMetadataFormatNames.length == 0!");
            }
            if (extraMetadataFormatClassNames == null) {
                throw new IllegalArgumentException
                    ("extraMetadataFormatNames != null && extraMetadataFormatClassNames == null!");
            }
            if (extraMetadataFormatClassNames.length !=
                extraMetadataFormatNames.length) {
                throw new IllegalArgumentException
                    ("extraMetadataFormatClassNames.length != extraMetadataFormatNames.length!");
            }
            this.extraMetadataFormatNames =
                (String[]) extraMetadataFormatNames.clone();
            this.extraMetadataFormatClassNames =
                (String[]) extraMetadataFormatClassNames.clone();
        } else {
            if (extraMetadataFormatClassNames != null) {
                throw new IllegalArgumentException
                    ("extraMetadataFormatNames == null && extraMetadataFormatClassNames != null!");
            }
        }
    }

    /**
     * Returns <code>true</code> if the standard metadata format is
     * supported by <code>getMetadataFormat</code>,
     * <code>getAsTree</code>, <code>setFromTree</code>, and
     * <code>mergeTree</code>.
     *
     * <p> The default implementation returns the value of the
     * <code>standardFormatSupported</code> instance variable.
     *
     * <p>
     *  如果<code> getMetadataFormat </code>,<code> getAsTree </code>,<code> setFromTree </code>和<code> mergeT
     * ree </code>支持标准元数据格式,则返回<code> true </code> / code>。
     * 
     *  <p>默认实现返回<code> standardFormatSupported </code>实例变量的值。
     * 
     * 
     * @return <code>true</code> if the standard metadata format
     * is supported.
     *
     * @see #getAsTree
     * @see #setFromTree
     * @see #mergeTree
     * @see #getMetadataFormat
     */
    public boolean isStandardMetadataFormatSupported() {
        return standardFormatSupported;
    }

    /**
     * Returns <code>true</code> if this object does not support the
     * <code>mergeTree</code>, <code>setFromTree</code>, and
     * <code>reset</code> methods.
     *
     * <p>
     *  如果此对象不支持<code> mergeTree </code>,<code> setFromTree </code>和<code> reset </code>方法,则返回<code> true </code>
     * 。
     * 
     * 
     * @return true if this <code>IIOMetadata</code> object cannot be
     * modified.
     */
    public abstract boolean isReadOnly();

    /**
     * Returns the name of the "native" metadata format for this
     * plug-in, which typically allows for lossless encoding and
     * transmission of the metadata stored in the format handled by
     * this plug-in.  If no such format is supported,
     * <code>null</code>will be returned.
     *
     * <p> The structure and contents of the "native" metadata format
     * are defined by the plug-in that created this
     * <code>IIOMetadata</code> object.  Plug-ins for simple formats
     * will usually create a dummy node for the root, and then a
     * series of child nodes representing individual tags, chunks, or
     * keyword/value pairs.  A plug-in may choose whether or not to
     * document its native format.
     *
     * <p> The default implementation returns the value of the
     * <code>nativeMetadataFormatName</code> instance variable.
     *
     * <p>
     * 返回此插件的"本机"元数据格式的名称,通常允许无损编码和传输以此插件处理的格式存储的元数据。如果不支持这种格式,将返回<code> null </code>。
     * 
     *  <p>"本地"元数据格式的结构和内容由创建此<code> IIOMetadata </code>对象的插件定义。
     * 简单格式的插件通常会为根创建一个虚节点,然后创建一系列表示单个标签,块或关键字/值对的子节点。插件可以选择是否记录其原生格式。
     * 
     *  <p>默认实现返回<code> nativeMetadataFormatName </code>实例变量的值。
     * 
     * 
     * @return the name of the native format, or <code>null</code>.
     *
     * @see #getExtraMetadataFormatNames
     * @see #getMetadataFormatNames
     */
    public String getNativeMetadataFormatName() {
        return nativeMetadataFormatName;
    }

    /**
     * Returns an array of <code>String</code>s containing the names
     * of additional metadata formats, other than the native and standard
     * formats, recognized by this plug-in's
     * <code>getAsTree</code>, <code>setFromTree</code>, and
     * <code>mergeTree</code> methods.  If there are no such additional
     * formats, <code>null</code> is returned.
     *
     * <p> The default implementation returns a clone of the
     * <code>extraMetadataFormatNames</code> instance variable.
     *
     * <p>
     *  返回一个<code> String </code>数组,其中包含本插件的<code> getAsTree </code>,<code> setFromTree </code>标识的附加元数据格式的名称
     * ,代码>和<code> mergeTree </code>方法。
     * 如果没有这样的附加格式,则返回<code> null </code>。
     * 
     *  <p>默认实现返回了一个克隆的<code> extraMetadataFormatNames </code>实例变量。
     * 
     * 
     * @return an array of <code>String</code>s with length at least
     * 1, or <code>null</code>.
     *
     * @see #getAsTree
     * @see #setFromTree
     * @see #mergeTree
     * @see #getNativeMetadataFormatName
     * @see #getMetadataFormatNames
     */
    public String[] getExtraMetadataFormatNames() {
        if (extraMetadataFormatNames == null) {
            return null;
        }
        return (String[])extraMetadataFormatNames.clone();
    }

    /**
     * Returns an array of <code>String</code>s containing the names
     * of all metadata formats, including the native and standard
     * formats, recognized by this plug-in's <code>getAsTree</code>,
     * <code>setFromTree</code>, and <code>mergeTree</code> methods.
     * If there are no such formats, <code>null</code> is returned.
     *
     * <p> The default implementation calls
     * <code>getNativeMetadataFormatName</code>,
     * <code>isStandardMetadataFormatSupported</code>, and
     * <code>getExtraMetadataFormatNames</code> and returns the
     * combined results.
     *
     * <p>
     *  返回一个<code> String </code>数组,包含所有元数据格式的名称,包括本插件的<code> getAsTree </code>,<code> setFromTree </code >和
     * <code> mergeTree </code>方法。
     * 如果没有这样的格式,则返回<code> null </code>。
     * 
     * <p>默认实现调用<code> getNativeMetadataFormatName </code>,<code> isStandardMetadataFormatSupported </code>和
     * <code> getExtraMetadataFormatNames </code>,并返回组合结果。
     * 
     * 
     * @return an array of <code>String</code>s.
     *
     * @see #getNativeMetadataFormatName
     * @see #isStandardMetadataFormatSupported
     * @see #getExtraMetadataFormatNames
     */
    public String[] getMetadataFormatNames() {
        String nativeName = getNativeMetadataFormatName();
        String standardName = isStandardMetadataFormatSupported() ?
            IIOMetadataFormatImpl.standardMetadataFormatName : null;
        String[] extraNames = getExtraMetadataFormatNames();

        int numFormats = 0;
        if (nativeName != null) {
            ++numFormats;
        }
        if (standardName != null) {
            ++numFormats;
        }
        if (extraNames != null) {
            numFormats += extraNames.length;
        }
        if (numFormats == 0) {
            return null;
        }

        String[] formats = new String[numFormats];
        int index = 0;
        if (nativeName != null) {
            formats[index++] = nativeName;
        }
        if (standardName != null) {
            formats[index++] = standardName;
        }
        if (extraNames != null) {
            for (int i = 0; i < extraNames.length; i++) {
                formats[index++] = extraNames[i];
            }
        }

        return formats;
    }

    /**
     * Returns an <code>IIOMetadataFormat</code> object describing the
     * given metadata format, or <code>null</code> if no description
     * is available.  The supplied name must be one of those returned
     * by <code>getMetadataFormatNames</code> (<i>i.e.</i>, either the
     * native format name, the standard format name, or one of those
     * returned by <code>getExtraMetadataFormatNames</code>).
     *
     * <p> The default implementation checks the name against the
     * global standard metadata format name, and returns that format
     * if it is supported.  Otherwise, it checks against the native
     * format names followed by any additional format names.  If a
     * match is found, it retrieves the name of the
     * <code>IIOMetadataFormat</code> class from
     * <code>nativeMetadataFormatClassName</code> or
     * <code>extraMetadataFormatClassNames</code> as appropriate, and
     * constructs an instance of that class using its
     * <code>getInstance</code> method.
     *
     * <p>
     *  返回描述给定元数据格式的<code> IIOMetadataFormat </code>对象,如果没有可用的描述,则返回<code> null </code>。
     * 提供的名称必须是由<code> getMetadataFormatNames </code>(<i> </i>)返回的名称之一,即本机格式名称,标准格式名称或<code> getExtraMetadat
     * aFormatNames < / code>)。
     *  返回描述给定元数据格式的<code> IIOMetadataFormat </code>对象,如果没有可用的描述,则返回<code> null </code>。
     * 
     *  <p>默认实现根据全局标准元数据格式名称检查名称,并返回该格式(如果支持)。否则,它将检查本机格式名称,后跟任何其他格式名称。
     * 如果找到匹配,则从<code> nativeMetadataFormatClassName </code>或<code> extraMetadataFormatClassNames </code>中适当
     * 地检索<code> IIOMetadataFormat </code>类的名称,并构造该类的实例它的<code> getInstance </code>方法。
     *  <p>默认实现根据全局标准元数据格式名称检查名称,并返回该格式(如果支持)。否则,它将检查本机格式名称,后跟任何其他格式名称。
     * 
     * 
     * @param formatName the desired metadata format.
     *
     * @return an <code>IIOMetadataFormat</code> object.
     *
     * @exception IllegalArgumentException if <code>formatName</code>
     * is <code>null</code> or is not one of the names recognized by
     * the plug-in.
     * @exception IllegalStateException if the class corresponding to
     * the format name cannot be loaded.
     */
    public IIOMetadataFormat getMetadataFormat(String formatName) {
        if (formatName == null) {
            throw new IllegalArgumentException("formatName == null!");
        }
        if (standardFormatSupported
            && formatName.equals
                (IIOMetadataFormatImpl.standardMetadataFormatName)) {
            return IIOMetadataFormatImpl.getStandardFormatInstance();
        }
        String formatClassName = null;
        if (formatName.equals(nativeMetadataFormatName)) {
            formatClassName = nativeMetadataFormatClassName;
        } else if (extraMetadataFormatNames != null) {
            for (int i = 0; i < extraMetadataFormatNames.length; i++) {
                if (formatName.equals(extraMetadataFormatNames[i])) {
                    formatClassName = extraMetadataFormatClassNames[i];
                    break;  // out of for
                }
            }
        }
        if (formatClassName == null) {
            throw new IllegalArgumentException("Unsupported format name");
        }
        try {
            Class cls = null;
            final Object o = this;

            // firstly we try to use classloader used for loading
            // the IIOMetadata implemantation for this plugin.
            ClassLoader loader = (ClassLoader)
                java.security.AccessController.doPrivileged(
                    new java.security.PrivilegedAction() {
                            public Object run() {
                                return o.getClass().getClassLoader();
                            }
                        });

            try {
                cls = Class.forName(formatClassName, true,
                                    loader);
            } catch (ClassNotFoundException e) {
                // we failed to load IIOMetadataFormat class by
                // using IIOMetadata classloader.Next try is to
                // use thread context classloader.
                loader = (ClassLoader)
                    java.security.AccessController.doPrivileged(
                        new java.security.PrivilegedAction() {
                                public Object run() {
                                    return Thread.currentThread().getContextClassLoader();
                                }
                        });
                try {
                    cls = Class.forName(formatClassName, true,
                                        loader);
                } catch (ClassNotFoundException e1) {
                    // finally we try to use system classloader in case
                    // if we failed to load IIOMetadataFormat implementation
                    // class above.
                    cls = Class.forName(formatClassName, true,
                                        ClassLoader.getSystemClassLoader());
                }
            }

            Method meth = cls.getMethod("getInstance");
            return (IIOMetadataFormat) meth.invoke(null);
        } catch (Exception e) {
            RuntimeException ex =
                new IllegalStateException ("Can't obtain format");
            ex.initCause(e);
            throw ex;
        }

    }

    /**
     * Returns an XML DOM <code>Node</code> object that represents the
     * root of a tree of metadata contained within this object
     * according to the conventions defined by a given metadata
     * format.
     *
     * <p> The names of the available metadata formats may be queried
     * using the <code>getMetadataFormatNames</code> method.
     *
     * <p>
     *  返回一个XML DOM <code> Node </code>对象,它根据给定元数据格式定义的约定表示此对象中包含的元数据树的根。
     * 
     *  <p>可用的元数据格式的名称可以使用<code> getMetadataFormatNames </code>方法查询。
     * 
     * 
     * @param formatName the desired metadata format.
     *
     * @return an XML DOM <code>Node</code> object forming the
     * root of a tree.
     *
     * @exception IllegalArgumentException if <code>formatName</code>
     * is <code>null</code> or is not one of the names returned by
     * <code>getMetadataFormatNames</code>.
     *
     * @see #getMetadataFormatNames
     * @see #setFromTree
     * @see #mergeTree
     */
    public abstract Node getAsTree(String formatName);

    /**
     * Alters the internal state of this <code>IIOMetadata</code>
     * object from a tree of XML DOM <code>Node</code>s whose syntax
     * is defined by the given metadata format.  The previous state is
     * altered only as necessary to accommodate the nodes that are
     * present in the given tree.  If the tree structure or contents
     * are invalid, an <code>IIOInvalidTreeException</code> will be
     * thrown.
     *
     * <p> As the semantics of how a tree or subtree may be merged with
     * another tree are completely format-specific, plug-in authors may
     * implement this method in whatever manner is most appropriate for
     * the format, including simply replacing all existing state with the
     * contents of the given tree.
     *
     * <p>
     * 从XML DOM <code> Node </code>的树中更改此<code> IIOMetadata </code>对象的内部状态,该树的语法由给定的元数据格式定义。
     * 仅当必要时改变先前状态以适应存在于给定树中的节点。如果树结构或内容无效,将抛出<code> IIOInvalidTreeException </code>。
     * 
     *  <p>由于树或子树如何可以与另一个树合并的语义完全是格式特定的,插件作者可以以任何最适合该格式的方式实现该方法,包括简单地用所有现有状态替换为给定树的内容。
     * 
     * 
     * @param formatName the desired metadata format.
     * @param root an XML DOM <code>Node</code> object forming the
     * root of a tree.
     *
     * @exception IllegalStateException if this object is read-only.
     * @exception IllegalArgumentException if <code>formatName</code>
     * is <code>null</code> or is not one of the names returned by
     * <code>getMetadataFormatNames</code>.
     * @exception IllegalArgumentException if <code>root</code> is
     * <code>null</code>.
     * @exception IIOInvalidTreeException if the tree cannot be parsed
     * successfully using the rules of the given format.
     *
     * @see #getMetadataFormatNames
     * @see #getAsTree
     * @see #setFromTree
     */
    public abstract void mergeTree(String formatName, Node root)
        throws IIOInvalidTreeException;

    /**
     * Returns an <code>IIOMetadataNode</code> representing the chroma
     * information of the standard <code>javax_imageio_1.0</code>
     * metadata format, or <code>null</code> if no such information is
     * available.  This method is intended to be called by the utility
     * routine <code>getStandardTree</code>.
     *
     * <p> The default implementation returns <code>null</code>.
     *
     * <p> Subclasses should override this method to produce an
     * appropriate subtree if they wish to support the standard
     * metadata format.
     *
     * <p>
     *  如果没有这样的信息,则返回表示标准<code> javax_imageio_1.0 </code>元数据格式或<code> null </code>的色度信息的<code> IIOMetadataNo
     * de </code>。
     * 此方法旨在由实用程序<code> getStandardTree </code>调用。
     * 
     *  <p>默认实现返回<code> null </code>。
     * 
     *  <p>如果子类支持标准元数据格式,则子类应重写此方法以生成相应的子树。
     * 
     * 
     * @return an <code>IIOMetadataNode</code>, or <code>null</code>.
     *
     * @see #getStandardTree
     */
    protected IIOMetadataNode getStandardChromaNode() {
        return null;
    }

    /**
     * Returns an <code>IIOMetadataNode</code> representing the
     * compression information of the standard
     * <code>javax_imageio_1.0</code> metadata format, or
     * <code>null</code> if no such information is available.  This
     * method is intended to be called by the utility routine
     * <code>getStandardTree</code>.
     *
     * <p> The default implementation returns <code>null</code>.
     *
     * <p> Subclasses should override this method to produce an
     * appropriate subtree if they wish to support the standard
     * metadata format.
     *
     * <p>
     *  如果没有这样的信息,则返回表示标准<code> javax_imageio_1.0 </code>元数据格式或<code> null </code>的压缩信息的<code> IIOMetadataNo
     * de </code>此方法旨在由实用程序<code> getStandardTree </code>调用。
     * 
     * <p>默认实现返回<code> null </code>。
     * 
     *  <p>如果子类支持标准元数据格式,则子类应重写此方法以生成相应的子树。
     * 
     * 
     * @return an <code>IIOMetadataNode</code>, or <code>null</code>.
     *
     * @see #getStandardTree
     */
    protected IIOMetadataNode getStandardCompressionNode() {
        return null;
    }

    /**
     * Returns an <code>IIOMetadataNode</code> representing the data
     * format information of the standard
     * <code>javax_imageio_1.0</code> metadata format, or
     * <code>null</code> if no such information is available.  This
     * method is intended to be called by the utility routine
     * <code>getStandardTree</code>.
     *
     * <p> The default implementation returns <code>null</code>.
     *
     * <p> Subclasses should override this method to produce an
     * appropriate subtree if they wish to support the standard
     * metadata format.
     *
     * <p>
     *  如果没有这样的信息,则返回表示标准<code> javax_imageio_1.0 </code>元数据格式或<code> null </code>的数据格式信息的<code> IIOMetadata
     * Node </code>此方法旨在由实用程序<code> getStandardTree </code>调用。
     * 
     *  <p>默认实现返回<code> null </code>。
     * 
     *  <p>如果子类支持标准元数据格式,则子类应重写此方法以生成相应的子树。
     * 
     * 
     * @return an <code>IIOMetadataNode</code>, or <code>null</code>.
     *
     * @see #getStandardTree
     */
    protected IIOMetadataNode getStandardDataNode() {
        return null;
    }

    /**
     * Returns an <code>IIOMetadataNode</code> representing the
     * dimension information of the standard
     * <code>javax_imageio_1.0</code> metadata format, or
     * <code>null</code> if no such information is available.  This
     * method is intended to be called by the utility routine
     * <code>getStandardTree</code>.
     *
     * <p> The default implementation returns <code>null</code>.
     *
     * <p> Subclasses should override this method to produce an
     * appropriate subtree if they wish to support the standard
     * metadata format.
     *
     * <p>
     *  如果没有此类信息可用,则返回表示标准<code> javax_imageio_1.0 </code>元数据格式或<code> null </code>的维度信息的<code> IIOMetadataN
     * ode </code>此方法旨在由实用程序<code> getStandardTree </code>调用。
     * 
     *  <p>默认实现返回<code> null </code>。
     * 
     *  <p>如果子类支持标准元数据格式,则子类应重写此方法以生成相应的子树。
     * 
     * 
     * @return an <code>IIOMetadataNode</code>, or <code>null</code>.
     *
     * @see #getStandardTree
     */
    protected IIOMetadataNode getStandardDimensionNode() {
        return null;
    }

    /**
     * Returns an <code>IIOMetadataNode</code> representing the document
     * information of the standard <code>javax_imageio_1.0</code>
     * metadata format, or <code>null</code> if no such information is
     * available.  This method is intended to be called by the utility
     * routine <code>getStandardTree</code>.
     *
     * <p> The default implementation returns <code>null</code>.
     *
     * <p> Subclasses should override this method to produce an
     * appropriate subtree if they wish to support the standard
     * metadata format.
     *
     * <p>
     *  如果没有此类信息可用,则返回表示标准<code> javax_imageio_1.0 </code>元数据格式或<code> null </code>的文档信息的<code> IIOMetadataN
     * ode </code>此方法旨在由实用程序<code> getStandardTree </code>调用。
     * 
     * <p>默认实现返回<code> null </code>。
     * 
     *  <p>如果子类支持标准元数据格式,则子类应重写此方法以生成相应的子树。
     * 
     * 
     * @return an <code>IIOMetadataNode</code>, or <code>null</code>.
     *
     * @see #getStandardTree
     */
    protected IIOMetadataNode getStandardDocumentNode() {
        return null;
    }

    /**
     * Returns an <code>IIOMetadataNode</code> representing the textual
     * information of the standard <code>javax_imageio_1.0</code>
     * metadata format, or <code>null</code> if no such information is
     * available.  This method is intended to be called by the utility
     * routine <code>getStandardTree</code>.
     *
     * <p> The default implementation returns <code>null</code>.
     *
     * <p> Subclasses should override this method to produce an
     * appropriate subtree if they wish to support the standard
     * metadata format.
     *
     * <p>
     *  如果没有此类信息可用,则返回表示标准<code> javax_imageio_1.0 </code>元数据格式或<code> null </code>的文本信息的<code> IIOMetadataN
     * ode </code>此方法旨在由实用程序<code> getStandardTree </code>调用。
     * 
     *  <p>默认实现返回<code> null </code>。
     * 
     *  <p>如果子类支持标准元数据格式,则子类应重写此方法以生成相应的子树。
     * 
     * 
     * @return an <code>IIOMetadataNode</code>, or <code>null</code>.
     *
     * @see #getStandardTree
     */
    protected IIOMetadataNode getStandardTextNode() {
        return null;
    }

    /**
     * Returns an <code>IIOMetadataNode</code> representing the tiling
     * information of the standard <code>javax_imageio_1.0</code>
     * metadata format, or <code>null</code> if no such information is
     * available.  This method is intended to be called by the utility
     * routine <code>getStandardTree</code>.
     *
     * <p> The default implementation returns <code>null</code>.
     *
     * <p> Subclasses should override this method to produce an
     * appropriate subtree if they wish to support the standard
     * metadata format.
     *
     * <p>
     *  如果没有这样的信息,则返回表示标准<code> javax_imageio_1.0 </code>元数据格式或<code> null </code>的平铺信息的<code> IIOMetadataNo
     * de </code>此方法旨在由实用程序<code> getStandardTree </code>调用。
     * 
     *  <p>默认实现返回<code> null </code>。
     * 
     *  <p>如果子类支持标准元数据格式,则子类应重写此方法以生成相应的子树。
     * 
     * 
     * @return an <code>IIOMetadataNode</code>, or <code>null</code>.
     *
     * @see #getStandardTree
     */
    protected IIOMetadataNode getStandardTileNode() {
        return null;
    }

    /**
     * Returns an <code>IIOMetadataNode</code> representing the
     * transparency information of the standard
     * <code>javax_imageio_1.0</code> metadata format, or
     * <code>null</code> if no such information is available.  This
     * method is intended to be called by the utility routine
     * <code>getStandardTree</code>.
     *
     * <p> The default implementation returns <code>null</code>.
     *
     * <p> Subclasses should override this method to produce an
     * appropriate subtree if they wish to support the standard
     * metadata format.
     *
     * <p>
     *  如果没有这样的信息,则返回表示标准<code> javax_imageio_1.0 </code>元数据格式或<code> null </code>的透明度信息的<code> IIOMetadataN
     * ode </code>。
     * 此方法旨在由实用程序<code> getStandardTree </code>调用。
     * 
     * <p>默认实现返回<code> null </code>。
     * 
     *  <p>如果子类支持标准元数据格式,则子类应重写此方法以生成相应的子树。
     * 
     * 
     * @return an <code>IIOMetadataNode</code>, or <code>null</code>.
     */
    protected IIOMetadataNode getStandardTransparencyNode() {
        return null;
    }

    /**
     * Appends a new node to an existing node, if the new node is
     * non-<code>null</code>.
     * <p>
     *  如果新节点是非<code> null </code>,则向现有节点追加新节点。
     * 
     */
    private void append(IIOMetadataNode root, IIOMetadataNode node) {
        if (node != null) {
            root.appendChild(node);
        }
    }

    /**
     * A utility method to return a tree of
     * <code>IIOMetadataNode</code>s representing the metadata
     * contained within this object according to the conventions of
     * the standard <code>javax_imageio_1.0</code> metadata format.
     *
     * <p> This method calls the various <code>getStandard*Node</code>
     * methods to supply each of the subtrees rooted at the children
     * of the root node.  If any of those methods returns
     * <code>null</code>, the corresponding subtree will be omitted.
     * If all of them return <code>null</code>, a tree consisting of a
     * single root node will be returned.
     *
     * <p>
     *  一种实用方法,用于根据标准<code> javax_imageio_1.0 </code>元数据格式的约定返回表示此对象中包含的元数据的<code> IIOMetadataNode </code>
     * 
     *  <p>此方法调用各种<code> getStandard * Node </code>方法来提供以根节点的子节点为根的每个子树。
     * 如果任何这些方法返回<code> null </code>,相应的子树将被省略。如果它们都返回<code> null </code>,那么将返回由单个根节点组成的树。
     * 
     * 
     * @return an <code>IIOMetadataNode</code> representing the root
     * of a metadata tree in the <code>javax_imageio_1.0</code>
     * format.
     *
     * @see #getStandardChromaNode
     * @see #getStandardCompressionNode
     * @see #getStandardDataNode
     * @see #getStandardDimensionNode
     * @see #getStandardDocumentNode
     * @see #getStandardTextNode
     * @see #getStandardTileNode
     * @see #getStandardTransparencyNode
     */
    protected final IIOMetadataNode getStandardTree() {
        IIOMetadataNode root = new IIOMetadataNode
                (IIOMetadataFormatImpl.standardMetadataFormatName);
        append(root, getStandardChromaNode());
        append(root, getStandardCompressionNode());
        append(root, getStandardDataNode());
        append(root, getStandardDimensionNode());
        append(root, getStandardDocumentNode());
        append(root, getStandardTextNode());
        append(root, getStandardTileNode());
        append(root, getStandardTransparencyNode());
        return root;
    }

    /**
     * Sets the internal state of this <code>IIOMetadata</code> object
     * from a tree of XML DOM <code>Node</code>s whose syntax is
     * defined by the given metadata format.  The previous state is
     * discarded.  If the tree's structure or contents are invalid, an
     * <code>IIOInvalidTreeException</code> will be thrown.
     *
     * <p> The default implementation calls <code>reset</code>
     * followed by <code>mergeTree(formatName, root)</code>.
     *
     * <p>
     *  从XML DOM <code> Node </code>的树中设置此<code> IIOMetadata </code>对象的内部状态,该树的语法由给定的元数据格式定义。先前的状态被丢弃。
     * 如果树的结构或内容无效,将抛出<code> IIOInvalidTreeException </code>。
     * 
     *  <p>默认实现调用<code> reset </code>后跟<code> mergeTree(formatName,root)</code>。
     * 
     * 
     * @param formatName the desired metadata format.
     * @param root an XML DOM <code>Node</code> object forming the
     * root of a tree.
     *
     * @exception IllegalStateException if this object is read-only.
     * @exception IllegalArgumentException if <code>formatName</code>
     * is <code>null</code> or is not one of the names returned by
     * <code>getMetadataFormatNames</code>.
     * @exception IllegalArgumentException if <code>root</code> is
     * <code>null</code>.
     * @exception IIOInvalidTreeException if the tree cannot be parsed
     * successfully using the rules of the given format.
     *
     * @see #getMetadataFormatNames
     * @see #getAsTree
     * @see #mergeTree
     */
    public void setFromTree(String formatName, Node root)
        throws IIOInvalidTreeException {
        reset();
        mergeTree(formatName, root);
    }

    /**
     * Resets all the data stored in this object to default values,
     * usually to the state this object was in immediately after
     * construction, though the precise semantics are plug-in specific.
     * Note that there are many possible default values, depending on
     * how the object was created.
     *
     * <p>
     * 将存储在此对象中的所有数据重置为默认值,通常为此对象在构建后立即处于的状态,尽管精确语义是插件特定的。请注意,有许多可能的默认值,具体取决于对象的创建方式。
     * 
     * 
     * @exception IllegalStateException if this object is read-only.
     *
     * @see javax.imageio.ImageReader#getStreamMetadata
     * @see javax.imageio.ImageReader#getImageMetadata
     * @see javax.imageio.ImageWriter#getDefaultStreamMetadata
     * @see javax.imageio.ImageWriter#getDefaultImageMetadata
     */
    public abstract void reset();

    /**
     * Sets the <code>IIOMetadataController</code> to be used
     * to provide settings for this <code>IIOMetadata</code>
     * object when the <code>activateController</code> method
     * is called, overriding any default controller.  If the
     * argument is <code>null</code>, no controller will be
     * used, including any default.  To restore the default, use
     * <code>setController(getDefaultController())</code>.
     *
     * <p> The default implementation sets the <code>controller</code>
     * instance variable to the supplied value.
     *
     * <p>
     *  当调用<code> activateController </code>方法时,设置<code> IIOMetadataController </code>用于为<code> IIOMetadata 
     * </code>对象提供设置,覆盖任何默认控制器。
     * 如果参数是<code> null </code>,则不使用控制器,包括任何默认值。
     * 要恢复默认值,使用<code> setController(getDefaultController())</code>。
     * 
     *  <p>默认实现将<code> controller </code>实例变量设置为提供的值。
     * 
     * 
     * @param controller An appropriate
     * <code>IIOMetadataController</code>, or <code>null</code>.
     *
     * @see IIOMetadataController
     * @see #getController
     * @see #getDefaultController
     * @see #hasController
     * @see #activateController()
     */
    public void setController(IIOMetadataController controller) {
        this.controller = controller;
    }

    /**
     * Returns whatever <code>IIOMetadataController</code> is currently
     * installed.  This could be the default if there is one,
     * <code>null</code>, or the argument of the most recent call
     * to <code>setController</code>.
     *
     * <p> The default implementation returns the value of the
     * <code>controller</code> instance variable.
     *
     * <p>
     *  返回当前安装的<code> IIOMetadataController </code>。
     * 如果有一个<code> null </code>,或者最近一次调用<code> setController </code>的参数,这可能是默认值。
     * 
     *  <p>默认实现返回<code> controller </code>实例变量的值。
     * 
     * 
     * @return the currently installed
     * <code>IIOMetadataController</code>, or <code>null</code>.
     *
     * @see IIOMetadataController
     * @see #setController
     * @see #getDefaultController
     * @see #hasController
     * @see #activateController()
     */
    public IIOMetadataController getController() {
        return controller;
    }

    /**
     * Returns the default <code>IIOMetadataController</code>, if there
     * is one, regardless of the currently installed controller.  If
     * there is no default controller, returns <code>null</code>.
     *
     * <p> The default implementation returns the value of the
     * <code>defaultController</code> instance variable.
     *
     * <p>
     *  返回默认<code> IIOMetadataController </code>(如果有),而不管当前安装的控制器。如果没有默认控制器,则返回<code> null </code>。
     * 
     *  <p>默认实现返回<code> defaultController </code>实例变量的值。
     * 
     * 
     * @return the default <code>IIOMetadataController</code>, or
     * <code>null</code>.
     *
     * @see IIOMetadataController
     * @see #setController(IIOMetadataController)
     * @see #getController
     * @see #hasController
     * @see #activateController()
     */
    public IIOMetadataController getDefaultController() {
        return defaultController;
    }

    /**
     * Returns <code>true</code> if there is a controller installed
     * for this <code>IIOMetadata</code> object.
     *
     * <p> The default implementation returns <code>true</code> if the
     * <code>getController</code> method returns a
     * non-<code>null</code> value.
     *
     * <p>
     *  如果为此<code> IIOMetadata </code>对象安装了控制器,则返回<code> true </code>。
     * 
     * <p>如果<code> getController </code>方法返回非<code> null </code>值,则默认实现返回<code> true </code>。
     * 
     * 
     * @return <code>true</code> if a controller is installed.
     *
     * @see IIOMetadataController
     * @see #setController(IIOMetadataController)
     * @see #getController
     * @see #getDefaultController
     * @see #activateController()
     */
    public boolean hasController() {
        return (getController() != null);
    }

    /**
     * Activates the installed <code>IIOMetadataController</code> for
     * this <code>IIOMetadata</code> object and returns the resulting
     * value.  When this method returns <code>true</code>, all values for this
     * <code>IIOMetadata</code> object will be ready for the next write
     * operation.  If <code>false</code> is
     * returned, no settings in this object will have been disturbed
     * (<i>i.e.</i>, the user canceled the operation).
     *
     * <p> Ordinarily, the controller will be a GUI providing a user
     * interface for a subclass of <code>IIOMetadata</code> for a
     * particular plug-in.  Controllers need not be GUIs, however.
     *
     * <p> The default implementation calls <code>getController</code>
     * and the calls <code>activate</code> on the returned object if
     * <code>hasController</code> returns <code>true</code>.
     *
     * <p>
     *  为此<code> IIOMetadata </code>对象激活安装的<code> IIOMetadataController </code>,并返回结果值。
     * 当此方法返回<code> true </code>时,此<code> IIOMetadata </code>对象的所有值都可以用于下一个写操作。
     * 如果返回<code> false </code>,则此对象中的任何设置都不会受到干扰(即</i>,用户取消操作)。
     * 
     *  <p>通常,控制器将是为特定插件的<code> IIOMetadata </code>子类提供用户界面的GUI。然而,控制器不需要是GUI。
     * 
     * @return <code>true</code> if the controller completed normally.
     *
     * @exception IllegalStateException if there is no controller
     * currently installed.
     *
     * @see IIOMetadataController
     * @see #setController(IIOMetadataController)
     * @see #getController
     * @see #getDefaultController
     * @see #hasController
     */
    public boolean activateController() {
        if (!hasController()) {
            throw new IllegalStateException("hasController() == false!");
        }
        return getController().activate(this);
    }
}
