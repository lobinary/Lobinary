/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2001-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2001-2004 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */
/*
 * $Id: TransformerFactoryImpl.java,v 1.8 2007/04/09 21:30:41 joehw Exp $
 * <p>
 *  $ Id：TransformerFactoryImpl.java,v 1.8 2007/04/09 21:30:41 joehw Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.trax;

import com.sun.org.apache.xalan.internal.XalanConstants;
import com.sun.org.apache.xalan.internal.utils.FactoryImpl;
import com.sun.org.apache.xalan.internal.utils.FeatureManager;
import com.sun.org.apache.xalan.internal.utils.FeaturePropertyBase;
import com.sun.org.apache.xalan.internal.utils.FeaturePropertyBase.State;
import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
import com.sun.org.apache.xalan.internal.utils.XMLSecurityManager;
import com.sun.org.apache.xalan.internal.utils.XMLSecurityPropertyManager;
import com.sun.org.apache.xalan.internal.utils.XMLSecurityPropertyManager.Property;
import com.sun.org.apache.xalan.internal.xsltc.compiler.Constants;
import com.sun.org.apache.xalan.internal.xsltc.compiler.SourceLoader;
import com.sun.org.apache.xalan.internal.xsltc.compiler.XSLTC;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.sun.org.apache.xalan.internal.xsltc.dom.XSLTCDTMManager;
import com.sun.org.apache.xml.internal.utils.StopParseException;
import com.sun.org.apache.xml.internal.utils.StylesheetPIHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.xml.XMLConstants;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TemplatesHandler;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stax.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.xml.sax.InputSource;
import org.xml.sax.XMLFilter;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * Implementation of a JAXP1.1 TransformerFactory for Translets.
 * <p>
 *  实现JAXP1.1 TransformerFactory for Translets。
 * 
 * 
 * @author G. Todd Miller
 * @author Morten Jorgensen
 * @author Santiago Pericas-Geertsen
 */
public class TransformerFactoryImpl
    extends SAXTransformerFactory implements SourceLoader, ErrorListener
{
    // Public constants for attributes supported by the XSLTC TransformerFactory.
    public final static String TRANSLET_NAME = "translet-name";
    public final static String DESTINATION_DIRECTORY = "destination-directory";
    public final static String PACKAGE_NAME = "package-name";
    public final static String JAR_NAME = "jar-name";
    public final static String GENERATE_TRANSLET = "generate-translet";
    public final static String AUTO_TRANSLET = "auto-translet";
    public final static String USE_CLASSPATH = "use-classpath";
    public final static String DEBUG = "debug";
    public final static String ENABLE_INLINING = "enable-inlining";
    public final static String INDENT_NUMBER = "indent-number";

    /**
     * This error listener is used only for this factory and is not passed to
     * the Templates or Transformer objects that we create.
     * <p>
     *  此错误侦听器仅用于此工厂,不会传递到我们创建的模板或Transformer对象。
     * 
     */
    private ErrorListener _errorListener = this;

    /**
     * This URIResolver is passed to all created Templates and Transformers
     * <p>
     *  这个URIResolver被传递给所有创建的模板和变形金刚
     * 
     */
    private URIResolver _uriResolver = null;

    /**
     * As Gregor Samsa awoke one morning from uneasy dreams he found himself
     * transformed in his bed into a gigantic insect. He was lying on his hard,
     * as it were armour plated, back, and if he lifted his head a little he
     * could see his big, brown belly divided into stiff, arched segments, on
     * top of which the bed quilt could hardly keep in position and was about
     * to slide off completely. His numerous legs, which were pitifully thin
     * compared to the rest of his bulk, waved helplessly before his eyes.
     * "What has happened to me?", he thought. It was no dream....
     * <p>
     * 当格雷戈尔·萨姆萨从一个不安的梦想醒来的一个早晨,他发现自己在他的床上变成了一个巨大的昆虫。
     * 他躺在他的硬,因为它的盔甲电镀,后面,如果他抬起头一点,他可以看到他的大,棕色的肚皮分成僵硬的弓形段,在床的被子几乎不能保持在位置并将要完全滑落。
     * 他的许多腿,与他的大部分的其余部分相比是可怜的薄,无奈地在他的眼睛前挥手。 "我发生了什么事?",他想。这不是梦想....。
     * 
     */
    protected final static String DEFAULT_TRANSLET_NAME = "GregorSamsa";

    /**
     * The class name of the translet
     * <p>
     *  translet的类名
     * 
     */
    private String _transletName = DEFAULT_TRANSLET_NAME;

    /**
     * The destination directory for the translet
     * <p>
     *  translet的目标目录
     * 
     */
    private String _destinationDirectory = null;

    /**
     * The package name prefix for all generated translet classes
     * <p>
     *  所有生成的translet类的包名称前缀
     * 
     */
    private String _packageName = null;

    /**
     * The jar file name which the translet classes are packaged into
     * <p>
     *  将translet类打包到的jar文件名
     * 
     */
    private String _jarFileName = null;

    /**
     * This Hashtable is used to store parameters for locating
     * <?xml-stylesheet ...?> processing instructions in XML docs.
     * <p>
     *  这个Hashtable用于存储用于在XML文档中定位<?xml-stylesheet ...?>处理指令的参数。
     * 
     */
    private Hashtable _piParams = null;

    /**
     * The above hashtable stores objects of this class.
     * <p>
     *  上面的hashtable存储了这个类的对象。
     * 
     */
    private static class PIParamWrapper {
        public String _media = null;
        public String _title = null;
        public String _charset = null;

        public PIParamWrapper(String media, String title, String charset) {
            _media = media;
            _title = title;
            _charset = charset;
        }
    }

    /**
     * Set to <code>true</code> when debugging is enabled.
     * <p>
     *  当调试启用时,设置为<code> true </code>。
     * 
     */
    private boolean _debug = false;

    /**
     * Set to <code>true</code> when templates are inlined.
     * <p>
     *  当模板内联时,设置为<code> true </code>。
     * 
     */
    private boolean _enableInlining = false;

    /**
     * Set to <code>true</code> when we want to generate
     * translet classes from the stylesheet.
     * <p>
     *  当我们想从样式表中生成translet类时,设置为<code> true </code>。
     * 
     */
    private boolean _generateTranslet = false;

    /**
     * If this is set to <code>true</code>, we attempt to use translet classes
     * for transformation if possible without compiling the stylesheet. The
     * translet class is only used if its timestamp is newer than the timestamp
     * of the stylesheet.
     * <p>
     *  如果这被设置为<code> true </code>,我们尝试使用translet类进行转换,如果可能,而不编译样式表。只有当其时间戳比样式表的时间戳新的时候,才使用translet类。
     * 
     */
    private boolean _autoTranslet = false;

    /**
     * If this is set to <code>true</code>, we attempt to load the translet
     * from the CLASSPATH.
     * <p>
     *  如果这被设置为<code> true </code>,我们尝试从CLASSPATH加载translet。
     * 
     */
    private boolean _useClasspath = false;

    /**
     * Number of indent spaces when indentation is turned on.
     * <p>
     *  打开缩进时的缩进空格数。
     * 
     */
    private int _indentNumber = -1;

    /**
     * <p>State of secure processing feature.</p>
     * <p>
     * <p>安全处理功能的状态。</p>
     * 
     */
    private boolean _isNotSecureProcessing = true;
    /**
     * <p>State of secure mode.</p>
     * <p>
     *  <p>安全模式的状态。</p>
     * 
     */
    private boolean _isSecureMode = false;

    /**
     * Indicates whether implementation parts should use
     *   service loader (or similar).
     * Note the default value (false) is the safe option..
     * <p>
     *  指示实施部分是否应使用服务加载程序(或类似)。请注意,默认值(false)是安全选项。
     * 
     */
    private boolean _useServicesMechanism;

    /**
     * protocols allowed for external references set by the stylesheet processing instruction, Import and Include element.
     * <p>
     *  允许由样式表处理指令设置的外部引用的协议,Import和Include元素。
     * 
     */
    private String _accessExternalStylesheet = XalanConstants.EXTERNAL_ACCESS_DEFAULT;
     /**
     * protocols allowed for external DTD references in source file and/or stylesheet.
     * <p>
     *  允许在源文件和/或样式表中的外部DTD引用的协议。
     * 
     */
    private String _accessExternalDTD = XalanConstants.EXTERNAL_ACCESS_DEFAULT;

    private XMLSecurityPropertyManager _xmlSecurityPropertyMgr;
    private XMLSecurityManager _xmlSecurityManager;

    private final FeatureManager _featureManager;

    private ClassLoader _extensionClassLoader = null;

    // Unmodifiable view of external extension function from xslt compiler
    // It will be populated by user-specified extension functions during the
    // type checking
    private Map<String, Class> _xsltcExtensionFunctions;

    /**
     * javax.xml.transform.sax.TransformerFactory implementation.
     * <p>
     *  javax.xml.transform.sax.TransformerFactory实现。
     * 
     */
    public TransformerFactoryImpl() {
        this(true);
    }

    public static TransformerFactory newTransformerFactoryNoServiceLoader() {
        return new TransformerFactoryImpl(false);
    }

    private TransformerFactoryImpl(boolean useServicesMechanism) {
        this._useServicesMechanism = useServicesMechanism;
        _featureManager = new FeatureManager();

        if (System.getSecurityManager() != null) {
            _isSecureMode = true;
            _isNotSecureProcessing = false;
            _featureManager.setValue(FeatureManager.Feature.ORACLE_ENABLE_EXTENSION_FUNCTION,
                    FeaturePropertyBase.State.FSP, XalanConstants.FEATURE_FALSE);
        }

        _xmlSecurityPropertyMgr = new XMLSecurityPropertyManager();
        _accessExternalDTD = _xmlSecurityPropertyMgr.getValue(
                Property.ACCESS_EXTERNAL_DTD);
        _accessExternalStylesheet = _xmlSecurityPropertyMgr.getValue(
                Property.ACCESS_EXTERNAL_STYLESHEET);

        //Parser's security manager
        _xmlSecurityManager = new XMLSecurityManager(true);
        //Unmodifiable hash map with loaded external extension functions
        _xsltcExtensionFunctions = null;
    }

    public Map<String,Class> getExternalExtensionsMap() {
        return _xsltcExtensionFunctions;
    }

    /**
     * javax.xml.transform.sax.TransformerFactory implementation.
     * Set the error event listener for the TransformerFactory, which is used
     * for the processing of transformation instructions, and not for the
     * transformation itself.
     *
     * <p>
     *  javax.xml.transform.sax.TransformerFactory实现。为TransformerFactory设置错误事件侦听器,用于处理转换指令,而不是转换本身。
     * 
     * 
     * @param listener The error listener to use with the TransformerFactory
     * @throws IllegalArgumentException
     */
    @Override
    public void setErrorListener(ErrorListener listener)
        throws IllegalArgumentException
    {
        if (listener == null) {
            ErrorMsg err = new ErrorMsg(ErrorMsg.ERROR_LISTENER_NULL_ERR,
                                        "TransformerFactory");
            throw new IllegalArgumentException(err.toString());
        }
        _errorListener = listener;
    }

    /**
     * javax.xml.transform.sax.TransformerFactory implementation.
     * Get the error event handler for the TransformerFactory.
     *
     * <p>
     *  javax.xml.transform.sax.TransformerFactory实现。获取TransformerFactory的错误事件处理程序。
     * 
     * 
     * @return The error listener used with the TransformerFactory
     */
    @Override
    public ErrorListener getErrorListener() {
        return _errorListener;
    }

    /**
     * javax.xml.transform.sax.TransformerFactory implementation.
     * Returns the value set for a TransformerFactory attribute
     *
     * <p>
     *  javax.xml.transform.sax.TransformerFactory实现。返回为TransformerFactory属性设置的值
     * 
     * 
     * @param name The attribute name
     * @return An object representing the attribute value
     * @throws IllegalArgumentException
     */
    @Override
    public Object getAttribute(String name)
        throws IllegalArgumentException
    {
        // Return value for attribute 'translet-name'
        if (name.equals(TRANSLET_NAME)) {
            return _transletName;
        }
        else if (name.equals(GENERATE_TRANSLET)) {
            return new Boolean(_generateTranslet);
        }
        else if (name.equals(AUTO_TRANSLET)) {
            return new Boolean(_autoTranslet);
        }
        else if (name.equals(ENABLE_INLINING)) {
            if (_enableInlining)
              return Boolean.TRUE;
            else
              return Boolean.FALSE;
        } else if (name.equals(XalanConstants.SECURITY_MANAGER)) {
            return _xmlSecurityManager;
        } else if (name.equals(XalanConstants.JDK_EXTENSION_CLASSLOADER)) {
           return _extensionClassLoader;
        }

        /** Check to see if the property is managed by the security manager **/
        String propertyValue = (_xmlSecurityManager != null) ?
                _xmlSecurityManager.getLimitAsString(name) : null;
        if (propertyValue != null) {
            return propertyValue;
        } else {
            propertyValue = (_xmlSecurityPropertyMgr != null) ?
                _xmlSecurityPropertyMgr.getValue(name) : null;
            if (propertyValue != null) {
                return propertyValue;
            }
        }

        // Throw an exception for all other attributes
        ErrorMsg err = new ErrorMsg(ErrorMsg.JAXP_INVALID_ATTR_ERR, name);
        throw new IllegalArgumentException(err.toString());
    }

    /**
     * javax.xml.transform.sax.TransformerFactory implementation.
     * Sets the value for a TransformerFactory attribute.
     *
     * <p>
     *  javax.xml.transform.sax.TransformerFactory实现。设置TransformerFactory属性的值。
     * 
     * 
     * @param name The attribute name
     * @param value An object representing the attribute value
     * @throws IllegalArgumentException
     */
    @Override
    public void setAttribute(String name, Object value)
        throws IllegalArgumentException
    {
        // Set the default translet name (ie. class name), which will be used
        // for translets that cannot be given a name from their system-id.
        if (name.equals(TRANSLET_NAME) && value instanceof String) {
            _transletName = (String) value;
            return;
        }
        else if (name.equals(DESTINATION_DIRECTORY) && value instanceof String) {
            _destinationDirectory = (String) value;
            return;
        }
        else if (name.equals(PACKAGE_NAME) && value instanceof String) {
            _packageName = (String) value;
            return;
        }
        else if (name.equals(JAR_NAME) && value instanceof String) {
            _jarFileName = (String) value;
            return;
        }
        else if (name.equals(GENERATE_TRANSLET)) {
            if (value instanceof Boolean) {
                _generateTranslet = ((Boolean) value).booleanValue();
                return;
            }
            else if (value instanceof String) {
                _generateTranslet = ((String) value).equalsIgnoreCase("true");
                return;
            }
        }
        else if (name.equals(AUTO_TRANSLET)) {
            if (value instanceof Boolean) {
                _autoTranslet = ((Boolean) value).booleanValue();
                return;
            }
            else if (value instanceof String) {
                _autoTranslet = ((String) value).equalsIgnoreCase("true");
                return;
            }
        }
        else if (name.equals(USE_CLASSPATH)) {
            if (value instanceof Boolean) {
                _useClasspath = ((Boolean) value).booleanValue();
                return;
            }
            else if (value instanceof String) {
                _useClasspath = ((String) value).equalsIgnoreCase("true");
                return;
            }
        }
        else if (name.equals(DEBUG)) {
            if (value instanceof Boolean) {
                _debug = ((Boolean) value).booleanValue();
                return;
            }
            else if (value instanceof String) {
                _debug = ((String) value).equalsIgnoreCase("true");
                return;
            }
        }
        else if (name.equals(ENABLE_INLINING)) {
            if (value instanceof Boolean) {
                _enableInlining = ((Boolean) value).booleanValue();
                return;
            }
            else if (value instanceof String) {
                _enableInlining = ((String) value).equalsIgnoreCase("true");
                return;
            }
        }
        else if (name.equals(INDENT_NUMBER)) {
            if (value instanceof String) {
                try {
                    _indentNumber = Integer.parseInt((String) value);
                    return;
                }
                catch (NumberFormatException e) {
                    // Falls through
                }
            }
            else if (value instanceof Integer) {
                _indentNumber = ((Integer) value).intValue();
                return;
            }
        }
        else if ( name.equals(XalanConstants.JDK_EXTENSION_CLASSLOADER)) {
            if (value instanceof ClassLoader) {
                _extensionClassLoader = (ClassLoader) value;
                return;
            } else {
                final ErrorMsg err
                    = new ErrorMsg(ErrorMsg.JAXP_INVALID_ATTR_VALUE_ERR, "Extension Functions ClassLoader");
                throw new IllegalArgumentException(err.toString());
            }
        }

        if (_xmlSecurityManager != null &&
                _xmlSecurityManager.setLimit(name, XMLSecurityManager.State.APIPROPERTY, value)) {
            return;
        }

        if (_xmlSecurityPropertyMgr != null &&
            _xmlSecurityPropertyMgr.setValue(name, XMLSecurityPropertyManager.State.APIPROPERTY, value)) {
            _accessExternalDTD = _xmlSecurityPropertyMgr.getValue(
                    Property.ACCESS_EXTERNAL_DTD);
            _accessExternalStylesheet = _xmlSecurityPropertyMgr.getValue(
                    Property.ACCESS_EXTERNAL_STYLESHEET);
            return;
        }

        // Throw an exception for all other attributes
        final ErrorMsg err
            = new ErrorMsg(ErrorMsg.JAXP_INVALID_ATTR_ERR, name);
        throw new IllegalArgumentException(err.toString());
    }

    /**
     * <p>Set a feature for this <code>TransformerFactory</code> and <code>Transformer</code>s
     * or <code>Template</code>s created by this factory.</p>
     *
     * <p>
     * Feature names are fully qualified {@link java.net.URI}s.
     * Implementations may define their own features.
     * An {@link TransformerConfigurationException} is thrown if this <code>TransformerFactory</code> or the
     * <code>Transformer</code>s or <code>Template</code>s it creates cannot support the feature.
     * It is possible for an <code>TransformerFactory</code> to expose a feature value but be unable to change its state.
     * </p>
     *
     * <p>See {@link javax.xml.transform.TransformerFactory} for full documentation of specific features.</p>
     *
     * <p>
     *  <p>为此工厂创建的<code> TransformerFactory </code>和<code> Transformer </code>或<code>模板</code>设置功能。<​​/ p>
     * 
     * <p>
     * 功能名称是完全限定的{@link java.net.URI}。实现可以定义它们自己的特征。
     * 如果此创建的<code> TransformerFactory </code>或<code> Transformer </code>或<code> Template </code>无法支持该功能,则会抛
     * 出{@link TransformerConfigurationException}。
     * 功能名称是完全限定的{@link java.net.URI}。实现可以定义它们自己的特征。
     * 一个<code> TransformerFactory </code>可以暴露一个特征值,但是不能改变它的状态。
     * </p>
     * 
     *  <p>有关特定功能的完整文档,请参阅{@link javax.xml.transform.TransformerFactory}。</p>
     * 
     * 
     * @param name Feature name.
     * @param value Is feature state <code>true</code> or <code>false</code>.
     *
     * @throws TransformerConfigurationException if this <code>TransformerFactory</code>
     *   or the <code>Transformer</code>s or <code>Template</code>s it creates cannot support this feature.
     * @throws NullPointerException If the <code>name</code> parameter is null.
     */
    @Override
    public void setFeature(String name, boolean value)
        throws TransformerConfigurationException {

        // feature name cannot be null
        if (name == null) {
            ErrorMsg err = new ErrorMsg(ErrorMsg.JAXP_SET_FEATURE_NULL_NAME);
            throw new NullPointerException(err.toString());
        }
        // secure processing?
        else if (name.equals(XMLConstants.FEATURE_SECURE_PROCESSING)) {
            if ((_isSecureMode) && (!value)) {
                ErrorMsg err = new ErrorMsg(ErrorMsg.JAXP_SECUREPROCESSING_FEATURE);
                throw new TransformerConfigurationException(err.toString());
            }
            _isNotSecureProcessing = !value;
            _xmlSecurityManager.setSecureProcessing(value);

            // set external access restriction when FSP is explicitly set
            if (value && XalanConstants.IS_JDK8_OR_ABOVE) {
                _xmlSecurityPropertyMgr.setValue(Property.ACCESS_EXTERNAL_DTD,
                        State.FSP, XalanConstants.EXTERNAL_ACCESS_DEFAULT_FSP);
                _xmlSecurityPropertyMgr.setValue(Property.ACCESS_EXTERNAL_STYLESHEET,
                        State.FSP, XalanConstants.EXTERNAL_ACCESS_DEFAULT_FSP);
                _accessExternalDTD = _xmlSecurityPropertyMgr.getValue(
                        Property.ACCESS_EXTERNAL_DTD);
                _accessExternalStylesheet = _xmlSecurityPropertyMgr.getValue(
                        Property.ACCESS_EXTERNAL_STYLESHEET);
            }

            if (value && _featureManager != null) {
                _featureManager.setValue(FeatureManager.Feature.ORACLE_ENABLE_EXTENSION_FUNCTION,
                        FeaturePropertyBase.State.FSP, XalanConstants.FEATURE_FALSE);
            }
            return;
        }
        else if (name.equals(XalanConstants.ORACLE_FEATURE_SERVICE_MECHANISM)) {
            //in secure mode, let _useServicesMechanism be determined by the constructor
            if (!_isSecureMode)
                _useServicesMechanism = value;
        }
        else {
            if (_featureManager != null &&
                    _featureManager.setValue(name, State.APIPROPERTY, value)) {
                return;
            }

            // unknown feature
            ErrorMsg err = new ErrorMsg(ErrorMsg.JAXP_UNSUPPORTED_FEATURE, name);
            throw new TransformerConfigurationException(err.toString());
        }
    }

    /**
     * javax.xml.transform.sax.TransformerFactory implementation.
     * Look up the value of a feature (to see if it is supported).
     * This method must be updated as the various methods and features of this
     * class are implemented.
     *
     * <p>
     *  javax.xml.transform.sax.TransformerFactory实现。查找要素的值(以查看是否支持)。此方法必须随着此类的各种方法和功能的实现而更新。
     * 
     * 
     * @param name The feature name
     * @return 'true' if feature is supported, 'false' if not
     */
    @Override
    public boolean getFeature(String name) {
        // All supported features should be listed here
        String[] features = {
            DOMSource.FEATURE,
            DOMResult.FEATURE,
            SAXSource.FEATURE,
            SAXResult.FEATURE,
            StAXSource.FEATURE,
            StAXResult.FEATURE,
            StreamSource.FEATURE,
            StreamResult.FEATURE,
            SAXTransformerFactory.FEATURE,
            SAXTransformerFactory.FEATURE_XMLFILTER,
            XalanConstants.ORACLE_FEATURE_SERVICE_MECHANISM
        };

        // feature name cannot be null
        if (name == null) {
            ErrorMsg err = new ErrorMsg(ErrorMsg.JAXP_GET_FEATURE_NULL_NAME);
            throw new NullPointerException(err.toString());
        }

        // Inefficient, but array is small
        for (int i =0; i < features.length; i++) {
            if (name.equals(features[i])) {
                return true;
            }
        }
        // secure processing?
        if (name.equals(XMLConstants.FEATURE_SECURE_PROCESSING)) {
                return !_isNotSecureProcessing;
        }

        /** Check to see if the property is managed by the security manager **/
        String propertyValue = (_featureManager != null) ?
                _featureManager.getValueAsString(name) : null;
        if (propertyValue != null) {
            return Boolean.parseBoolean(propertyValue);
        }

        // Feature not supported
        return false;
    }
    /**
     * Return the state of the services mechanism feature.
     * <p>
     *  返回服务机制功能的状态。
     * 
     */
    public boolean useServicesMechnism() {
        return _useServicesMechanism;
    }

     /**
     /* <p>
     /* 
     * @return the feature manager
     */
    public FeatureManager getFeatureManager() {
        return _featureManager;
    }

    /**
     * javax.xml.transform.sax.TransformerFactory implementation.
     * Get the object that is used by default during the transformation to
     * resolve URIs used in document(), xsl:import, or xsl:include.
     *
     * <p>
     *  javax.xml.transform.sax.TransformerFactory实现。
     * 获取在转换期间默认使用的对象,以解析在document(),xsl：import或xsl：include中使用的URI。
     * 
     * 
     * @return The URLResolver used for this TransformerFactory and all
     * Templates and Transformer objects created using this factory
     */
    @Override
    public URIResolver getURIResolver() {
        return _uriResolver;
    }

    /**
     * javax.xml.transform.sax.TransformerFactory implementation.
     * Set the object that is used by default during the transformation to
     * resolve URIs used in document(), xsl:import, or xsl:include. Note that
     * this does not affect Templates and Transformers that are already
     * created with this factory.
     *
     * <p>
     *  javax.xml.transform.sax.TransformerFactory实现。
     * 设置转换期间默认使用的对象,以解析在document(),xsl：import或xsl：include中使用的URI。请注意,这不会影响已使用此工厂创建的模板和变形金刚。
     * 
     * 
     * @param resolver The URLResolver used for this TransformerFactory and all
     * Templates and Transformer objects created using this factory
     */
    @Override
    public void setURIResolver(URIResolver resolver) {
        _uriResolver = resolver;
    }

    /**
     * javax.xml.transform.sax.TransformerFactory implementation.
     * Get the stylesheet specification(s) associated via the xml-stylesheet
     * processing instruction (see http://www.w3.org/TR/xml-stylesheet/) with
     * the document document specified in the source parameter, and that match
     * the given criteria.
     *
     * <p>
     * javax.xml.transform.sax.TransformerFactory实现。
     * 通过xml样式表处理指令(请参阅http://www.w3.org/TR/xml-stylesheet/)关联的样式表规范与源参数中指定的文档文档相匹配,并且匹配给定的标准。
     * 
     * 
     * @param source The XML source document.
     * @param media The media attribute to be matched. May be null, in which
     * case the prefered templates will be used (i.e. alternate = no).
     * @param title The value of the title attribute to match. May be null.
     * @param charset The value of the charset attribute to match. May be null.
     * @return A Source object suitable for passing to the TransformerFactory.
     * @throws TransformerConfigurationException
     */
    @Override
    public Source  getAssociatedStylesheet(Source source, String media,
                                          String title, String charset)
        throws TransformerConfigurationException {

        String baseId;
        XMLReader reader;
        InputSource isource;


        /**
         * Fix for bugzilla bug 24187
         * <p>
         *  修复bugzilla错误24187
         * 
         */
        StylesheetPIHandler _stylesheetPIHandler = new StylesheetPIHandler(null,media,title,charset);

        try {

            if (source instanceof DOMSource ) {
                final DOMSource domsrc = (DOMSource) source;
                baseId = domsrc.getSystemId();
                final org.w3c.dom.Node node = domsrc.getNode();
                final DOM2SAX dom2sax = new DOM2SAX(node);

                _stylesheetPIHandler.setBaseId(baseId);

                dom2sax.setContentHandler( _stylesheetPIHandler);
                dom2sax.parse();
            } else {
                isource = SAXSource.sourceToInputSource(source);
                baseId = isource.getSystemId();

                SAXParserFactory factory = FactoryImpl.getSAXFactory(_useServicesMechanism);
                factory.setNamespaceAware(true);

                if (!_isNotSecureProcessing) {
                    try {
                        factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
                    }
                    catch (org.xml.sax.SAXException e) {}
                }

                SAXParser jaxpParser = factory.newSAXParser();

                reader = jaxpParser.getXMLReader();
                if (reader == null) {
                    reader = XMLReaderFactory.createXMLReader();
                }

                _stylesheetPIHandler.setBaseId(baseId);
                reader.setContentHandler(_stylesheetPIHandler);
                reader.parse(isource);

            }

            if (_uriResolver != null ) {
                _stylesheetPIHandler.setURIResolver(_uriResolver);
            }

        } catch (StopParseException e ) {
          // startElement encountered so do not parse further

        } catch (javax.xml.parsers.ParserConfigurationException e) {

             throw new TransformerConfigurationException(
             "getAssociatedStylesheets failed", e);

        } catch (org.xml.sax.SAXException se) {

             throw new TransformerConfigurationException(
             "getAssociatedStylesheets failed", se);


        } catch (IOException ioe ) {
           throw new TransformerConfigurationException(
           "getAssociatedStylesheets failed", ioe);

        }

         return _stylesheetPIHandler.getAssociatedStylesheet();

    }

    /**
     * javax.xml.transform.sax.TransformerFactory implementation.
     * Create a Transformer object that copies the input document to the result.
     *
     * <p>
     *  javax.xml.transform.sax.TransformerFactory实现。创建将输入文档复制到结果的Transformer对象。
     * 
     * 
     * @return A Transformer object that simply copies the source to the result.
     * @throws TransformerConfigurationException
     */
    @Override
    public Transformer newTransformer()
        throws TransformerConfigurationException
    {
        TransformerImpl result = new TransformerImpl(new Properties(),
            _indentNumber, this);
        if (_uriResolver != null) {
            result.setURIResolver(_uriResolver);
        }

        if (!_isNotSecureProcessing) {
            result.setSecureProcessing(true);
        }
        return result;
    }

    /**
     * javax.xml.transform.sax.TransformerFactory implementation.
     * Process the Source into a Templates object, which is a a compiled
     * representation of the source. Note that this method should not be
     * used with XSLTC, as the time-consuming compilation is done for each
     * and every transformation.
     *
     * <p>
     *  javax.xml.transform.sax.TransformerFactory实现。将源处理为模板对象,它是源的编译表示。
     * 请注意,此方法不应与XSLTC一起使用,因为每次转换都需要进行耗时的编译。
     * 
     * 
     * @return A Templates object that can be used to create Transformers.
     * @throws TransformerConfigurationException
     */
    @Override
    public Transformer newTransformer(Source source) throws
        TransformerConfigurationException
    {
        final Templates templates = newTemplates(source);
        final Transformer transformer = templates.newTransformer();
        if (_uriResolver != null) {
            transformer.setURIResolver(_uriResolver);
        }
        return(transformer);
    }

    /**
     * Pass warning messages from the compiler to the error listener
     * <p>
     *  将警告消息从编译器传递给错误侦听器
     * 
     */
    private void passWarningsToListener(Vector messages)
        throws TransformerException
    {
        if (_errorListener == null || messages == null) {
            return;
        }
        // Pass messages to listener, one by one
        final int count = messages.size();
        for (int pos = 0; pos < count; pos++) {
            ErrorMsg msg = (ErrorMsg)messages.elementAt(pos);
            // Workaround for the TCK failure ErrorListener.errorTests.error001.
            if (msg.isWarningError())
                _errorListener.error(
                    new TransformerConfigurationException(msg.toString()));
            else
                _errorListener.warning(
                    new TransformerConfigurationException(msg.toString()));
        }
    }

    /**
     * Pass error messages from the compiler to the error listener
     * <p>
     *  将错误消息从编译器传递到错误侦听器
     * 
     */
    private void passErrorsToListener(Vector messages) {
        try {
            if (_errorListener == null || messages == null) {
                return;
            }
            // Pass messages to listener, one by one
            final int count = messages.size();
            for (int pos = 0; pos < count; pos++) {
                String message = messages.elementAt(pos).toString();
                _errorListener.error(new TransformerException(message));
            }
        }
        catch (TransformerException e) {
            // nada
        }
    }

    /**
     * javax.xml.transform.sax.TransformerFactory implementation.
     * Process the Source into a Templates object, which is a a compiled
     * representation of the source.
     *
     * <p>
     *  javax.xml.transform.sax.TransformerFactory实现。将源处理为模板对象,它是源的编译表示。
     * 
     * 
     * @param source The input stylesheet - DOMSource not supported!!!
     * @return A Templates object that can be used to create Transformers.
     * @throws TransformerConfigurationException
     */
    @Override
    public Templates newTemplates(Source source)
        throws TransformerConfigurationException
    {
        // If the _useClasspath attribute is true, try to load the translet from
        // the CLASSPATH and create a template object using the loaded
        // translet.
        if (_useClasspath) {
            String transletName = getTransletBaseName(source);

            if (_packageName != null)
                transletName = _packageName + "." + transletName;

            try {
                final Class clazz = ObjectFactory.findProviderClass(transletName, true);
                resetTransientAttributes();

                return new TemplatesImpl(new Class[]{clazz}, transletName, null, _indentNumber, this);
            }
            catch (ClassNotFoundException cnfe) {
                ErrorMsg err = new ErrorMsg(ErrorMsg.CLASS_NOT_FOUND_ERR, transletName);
                throw new TransformerConfigurationException(err.toString());
            }
            catch (Exception e) {
                ErrorMsg err = new ErrorMsg(
                                     new ErrorMsg(ErrorMsg.RUNTIME_ERROR_KEY)
                                     + e.getMessage());
                throw new TransformerConfigurationException(err.toString());
            }
        }

        // If _autoTranslet is true, we will try to load the bytecodes
        // from the translet classes without compiling the stylesheet.
        if (_autoTranslet)  {
            byte[][] bytecodes;
            String transletClassName = getTransletBaseName(source);

            if (_packageName != null)
                transletClassName = _packageName + "." + transletClassName;

            if (_jarFileName != null)
                bytecodes = getBytecodesFromJar(source, transletClassName);
            else
                bytecodes = getBytecodesFromClasses(source, transletClassName);

            if (bytecodes != null) {
                if (_debug) {
                    if (_jarFileName != null)
                        System.err.println(new ErrorMsg(
                            ErrorMsg.TRANSFORM_WITH_JAR_STR, transletClassName, _jarFileName));
                    else
                        System.err.println(new ErrorMsg(
                            ErrorMsg.TRANSFORM_WITH_TRANSLET_STR, transletClassName));
                }

                // Reset the per-session attributes to their default values
                // after each newTemplates() call.
                resetTransientAttributes();
                return new TemplatesImpl(bytecodes, transletClassName, null, _indentNumber, this);
            }
        }

        // Create and initialize a stylesheet compiler
        final XSLTC xsltc = new XSLTC(_useServicesMechanism, _featureManager);
        if (_debug) xsltc.setDebug(true);
        if (_enableInlining)
                xsltc.setTemplateInlining(true);
        else
                xsltc.setTemplateInlining(false);

        if (!_isNotSecureProcessing) xsltc.setSecureProcessing(true);
        xsltc.setProperty(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, _accessExternalStylesheet);
        xsltc.setProperty(XMLConstants.ACCESS_EXTERNAL_DTD, _accessExternalDTD);
        xsltc.setProperty(XalanConstants.SECURITY_MANAGER, _xmlSecurityManager);
        xsltc.setProperty(XalanConstants.JDK_EXTENSION_CLASSLOADER, _extensionClassLoader);
        xsltc.init();
        if (!_isNotSecureProcessing)
            _xsltcExtensionFunctions = xsltc.getExternalExtensionFunctions();
        // Set a document loader (for xsl:include/import) if defined
        if (_uriResolver != null) {
            xsltc.setSourceLoader(this);
        }

        // Pass parameters to the Parser to make sure it locates the correct
        // <?xml-stylesheet ...?> PI in an XML input document
        if ((_piParams != null) && (_piParams.get(source) != null)) {
            // Get the parameters for this Source object
            PIParamWrapper p = (PIParamWrapper)_piParams.get(source);
            // Pass them on to the compiler (which will pass then to the parser)
            if (p != null) {
                xsltc.setPIParameters(p._media, p._title, p._charset);
            }
        }

        // Set the attributes for translet generation
        int outputType = XSLTC.BYTEARRAY_OUTPUT;
        if (_generateTranslet || _autoTranslet) {
            // Set the translet name
            xsltc.setClassName(getTransletBaseName(source));

            if (_destinationDirectory != null)
                xsltc.setDestDirectory(_destinationDirectory);
            else {
                String xslName = getStylesheetFileName(source);
                if (xslName != null) {
                    File xslFile = new File(xslName);
                    String xslDir = xslFile.getParent();

                    if (xslDir != null)
                        xsltc.setDestDirectory(xslDir);
                }
            }

            if (_packageName != null)
                xsltc.setPackageName(_packageName);

            if (_jarFileName != null) {
                xsltc.setJarFileName(_jarFileName);
                outputType = XSLTC.BYTEARRAY_AND_JAR_OUTPUT;
            }
            else
                outputType = XSLTC.BYTEARRAY_AND_FILE_OUTPUT;
        }

        // Compile the stylesheet
        final InputSource input = Util.getInputSource(xsltc, source);
        byte[][] bytecodes = xsltc.compile(null, input, outputType);
        final String transletName = xsltc.getClassName();

        // Output to the jar file if the jar file name is set.
        if ((_generateTranslet || _autoTranslet)
                && bytecodes != null && _jarFileName != null) {
            try {
                xsltc.outputToJar();
            }
            catch (java.io.IOException e) { }
        }

        // Reset the per-session attributes to their default values
        // after each newTemplates() call.
        resetTransientAttributes();

        // Pass compiler warnings to the error listener
        if (_errorListener != this) {
            try {
                passWarningsToListener(xsltc.getWarnings());
            }
            catch (TransformerException e) {
                throw new TransformerConfigurationException(e);
            }
        }
        else {
            xsltc.printWarnings();
        }

        // Check that the transformation went well before returning
    if (bytecodes == null) {
        Vector errs = xsltc.getErrors();
        ErrorMsg err;
        if (errs != null) {
            err = (ErrorMsg)errs.elementAt(errs.size()-1);
        } else {
            err = new ErrorMsg(ErrorMsg.JAXP_COMPILE_ERR);
        }
        Throwable cause = err.getCause();
        TransformerConfigurationException exc;
        if (cause != null) {
            exc =  new TransformerConfigurationException(cause.getMessage(), cause);
        } else {
            exc =  new TransformerConfigurationException(err.toString());
        }

        // Pass compiler errors to the error listener
        if (_errorListener != null) {
            passErrorsToListener(xsltc.getErrors());

            // As required by TCK 1.2, send a fatalError to the
            // error listener because compilation of the stylesheet
            // failed and no further processing will be possible.
            try {
                _errorListener.fatalError(exc);
            } catch (TransformerException te) {
                // well, we tried.
            }
        }
        else {
            xsltc.printErrors();
        }
        throw exc;
    }

        return new TemplatesImpl(bytecodes, transletName,
            xsltc.getOutputProperties(), _indentNumber, this);
    }

    /**
     * javax.xml.transform.sax.SAXTransformerFactory implementation.
     * Get a TemplatesHandler object that can process SAX ContentHandler
     * events into a Templates object.
     *
     * <p>
     *  javax.xml.transform.sax.SAXTransformerFactory实现。
     * 获取可以将SAX ContentHandler事件处理到Templates对象中的TemplatesHandler对象。
     * 
     * 
     * @return A TemplatesHandler object that can handle SAX events
     * @throws TransformerConfigurationException
     */
    @Override
    public TemplatesHandler newTemplatesHandler()
        throws TransformerConfigurationException
    {
        final TemplatesHandlerImpl handler =
            new TemplatesHandlerImpl(_indentNumber, this);
        if (_uriResolver != null) {
            handler.setURIResolver(_uriResolver);
        }
        return handler;
    }

    /**
     * javax.xml.transform.sax.SAXTransformerFactory implementation.
     * Get a TransformerHandler object that can process SAX ContentHandler
     * events into a Result. This method will return a pure copy transformer.
     *
     * <p>
     *  javax.xml.transform.sax.SAXTransformerFactory实现。
     * 获取可以将SAX ContentHandler事件处理到Result中的TransformerHandler对象。此方法将返回纯复制变换器。
     * 
     * 
     * @return A TransformerHandler object that can handle SAX events
     * @throws TransformerConfigurationException
     */
    @Override
    public TransformerHandler newTransformerHandler()
        throws TransformerConfigurationException
    {
        final Transformer transformer = newTransformer();
        if (_uriResolver != null) {
            transformer.setURIResolver(_uriResolver);
        }
        return new TransformerHandlerImpl((TransformerImpl) transformer);
    }

    /**
     * javax.xml.transform.sax.SAXTransformerFactory implementation.
     * Get a TransformerHandler object that can process SAX ContentHandler
     * events into a Result, based on the transformation instructions
     * specified by the argument.
     *
     * <p>
     * javax.xml.transform.sax.SAXTransformerFactory实现。
     * 获取TransformerHandler对象,该对象可以根据参数指定的变换指令将SAX ContentHandler事件处理到Result中。
     * 
     * 
     * @param src The source of the transformation instructions.
     * @return A TransformerHandler object that can handle SAX events
     * @throws TransformerConfigurationException
     */
    @Override
    public TransformerHandler newTransformerHandler(Source src)
        throws TransformerConfigurationException
    {
        final Transformer transformer = newTransformer(src);
        if (_uriResolver != null) {
            transformer.setURIResolver(_uriResolver);
        }
        return new TransformerHandlerImpl((TransformerImpl) transformer);
    }

    /**
     * javax.xml.transform.sax.SAXTransformerFactory implementation.
     * Get a TransformerHandler object that can process SAX ContentHandler
     * events into a Result, based on the transformation instructions
     * specified by the argument.
     *
     * <p>
     *  javax.xml.transform.sax.SAXTransformerFactory实现。
     * 获取TransformerHandler对象,该对象可以根据参数指定的变换指令将SAX ContentHandler事件处理到Result中。
     * 
     * 
     * @param templates Represents a pre-processed stylesheet
     * @return A TransformerHandler object that can handle SAX events
     * @throws TransformerConfigurationException
     */
    @Override
    public TransformerHandler newTransformerHandler(Templates templates)
        throws TransformerConfigurationException
    {
        final Transformer transformer = templates.newTransformer();
        final TransformerImpl internal = (TransformerImpl)transformer;
        return new TransformerHandlerImpl(internal);
    }

    /**
     * javax.xml.transform.sax.SAXTransformerFactory implementation.
     * Create an XMLFilter that uses the given source as the
     * transformation instructions.
     *
     * <p>
     *  javax.xml.transform.sax.SAXTransformerFactory实现。创建一个使用给定源作为转换指令的XMLFilter。
     * 
     * 
     * @param src The source of the transformation instructions.
     * @return An XMLFilter object, or null if this feature is not supported.
     * @throws TransformerConfigurationException
     */
    @Override
    public XMLFilter newXMLFilter(Source src)
        throws TransformerConfigurationException
    {
        Templates templates = newTemplates(src);
        if (templates == null) return null;
        return newXMLFilter(templates);
    }

    /**
     * javax.xml.transform.sax.SAXTransformerFactory implementation.
     * Create an XMLFilter that uses the given source as the
     * transformation instructions.
     *
     * <p>
     *  javax.xml.transform.sax.SAXTransformerFactory实现。创建一个使用给定源作为转换指令的XMLFilter。
     * 
     * 
     * @param templates The source of the transformation instructions.
     * @return An XMLFilter object, or null if this feature is not supported.
     * @throws TransformerConfigurationException
     */
    @Override
    public XMLFilter newXMLFilter(Templates templates)
        throws TransformerConfigurationException
    {
        try {
            return new com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter(templates);
        }
        catch (TransformerConfigurationException e1) {
            if (_errorListener != null) {
                try {
                    _errorListener.fatalError(e1);
                    return null;
                }
                catch (TransformerException e2) {
                    new TransformerConfigurationException(e2);
                }
            }
            throw e1;
        }
    }

    /**
     * Receive notification of a recoverable error.
     * The transformer must continue to provide normal parsing events after
     * invoking this method. It should still be possible for the application
     * to process the document through to the end.
     *
     * <p>
     *  接收可恢复错误的通知。变换器必须在调用此方法后继续提供正常的解析事件。应用程序应该仍然可以处理文档直到结束。
     * 
     * 
     * @param e The warning information encapsulated in a transformer
     * exception.
     * @throws TransformerException if the application chooses to discontinue
     * the transformation (always does in our case).
     */
    @Override
    public void error(TransformerException e)
        throws TransformerException
    {
        Throwable wrapped = e.getException();
        if (wrapped != null) {
            System.err.println(new ErrorMsg(ErrorMsg.ERROR_PLUS_WRAPPED_MSG,
                                            e.getMessageAndLocation(),
                                            wrapped.getMessage()));
        } else {
            System.err.println(new ErrorMsg(ErrorMsg.ERROR_MSG,
                                            e.getMessageAndLocation()));
        }
        throw e;
    }

    /**
     * Receive notification of a non-recoverable error.
     * The application must assume that the transformation cannot continue
     * after the Transformer has invoked this method, and should continue
     * (if at all) only to collect addition error messages. In fact,
     * Transformers are free to stop reporting events once this method has
     * been invoked.
     *
     * <p>
     *  接收不可恢复错误的通知。应用程序必须假定在Transformer调用此方法后转换无法继续,并且应继续(如果有)仅收集添加错误消息。
     * 事实上,一旦调用此方法,Transformers就可以自由停止报告事件。
     * 
     * 
     * @param e warning information encapsulated in a transformer
     * exception.
     * @throws TransformerException if the application chooses to discontinue
     * the transformation (always does in our case).
     */
    @Override
    public void fatalError(TransformerException e)
        throws TransformerException
    {
        Throwable wrapped = e.getException();
        if (wrapped != null) {
            System.err.println(new ErrorMsg(ErrorMsg.FATAL_ERR_PLUS_WRAPPED_MSG,
                                            e.getMessageAndLocation(),
                                            wrapped.getMessage()));
        } else {
            System.err.println(new ErrorMsg(ErrorMsg.FATAL_ERR_MSG,
                                            e.getMessageAndLocation()));
        }
        throw e;
    }

    /**
     * Receive notification of a warning.
     * Transformers can use this method to report conditions that are not
     * errors or fatal errors. The default behaviour is to take no action.
     * After invoking this method, the Transformer must continue with the
     * transformation. It should still be possible for the application to
     * process the document through to the end.
     *
     * <p>
     * 接收警告通知。变换器可以使用此方法报告不是错误或致命错误的情况。默认行为是不采取任何操作。调用此方法后,变换器必须继续执行转换。应用程序应该仍然可以处理文档直到结束。
     * 
     * 
     * @param e The warning information encapsulated in a transformer
     * exception.
     * @throws TransformerException if the application chooses to discontinue
     * the transformation (never does in our case).
     */
    @Override
    public void warning(TransformerException e)
        throws TransformerException
    {
        Throwable wrapped = e.getException();
        if (wrapped != null) {
            System.err.println(new ErrorMsg(ErrorMsg.WARNING_PLUS_WRAPPED_MSG,
                                            e.getMessageAndLocation(),
                                            wrapped.getMessage()));
        } else {
            System.err.println(new ErrorMsg(ErrorMsg.WARNING_MSG,
                                            e.getMessageAndLocation()));
        }
    }

    /**
     * This method implements XSLTC's SourceLoader interface. It is used to
     * glue a TrAX URIResolver to the XSLTC compiler's Input and Import classes.
     *
     * <p>
     *  这个方法实现了XSLTC的SourceLoader接口。它用于将TrAX URIResolver粘合到XSLTC编译器的输入和导入类。
     * 
     * 
     * @param href The URI of the document to load
     * @param context The URI of the currently loaded document
     * @param xsltc The compiler that resuests the document
     * @return An InputSource with the loaded document
     */
    @Override
    public InputSource loadSource(String href, String context, XSLTC xsltc) {
        try {
            if (_uriResolver != null) {
                final Source source = _uriResolver.resolve(href, context);
                if (source != null) {
                    return Util.getInputSource(xsltc, source);
                }
            }
        }
        catch (TransformerException e) {
            // should catch it when the resolver explicitly throws the exception
            final ErrorMsg msg = new ErrorMsg(ErrorMsg.INVALID_URI_ERR, href + "\n" + e.getMessage(), this);
            xsltc.getParser().reportError(Constants.FATAL, msg);
        }

        return null;
    }

    /**
     * Reset the per-session attributes to their default values
     * <p>
     *  将每会话属性重置为其默认值
     * 
     */
    private void resetTransientAttributes() {
        _transletName = DEFAULT_TRANSLET_NAME;
        _destinationDirectory = null;
        _packageName = null;
        _jarFileName = null;
    }

    /**
     * Load the translet classes from local .class files and return
     * the bytecode array.
     *
     * <p>
     *  从本地.class文件加载translet类并返回字节码数组。
     * 
     * 
     * @param source The xsl source
     * @param fullClassName The full name of the translet
     * @return The bytecode array
     */
    private byte[][] getBytecodesFromClasses(Source source, String fullClassName)
    {
        if (fullClassName == null)
            return null;

        String xslFileName = getStylesheetFileName(source);
        File xslFile = null;
        if (xslFileName != null)
            xslFile = new File(xslFileName);

        // Find the base name of the translet
        final String transletName;
        int lastDotIndex = fullClassName.lastIndexOf('.');
        if (lastDotIndex > 0)
            transletName = fullClassName.substring(lastDotIndex+1);
        else
            transletName = fullClassName;

        // Construct the path name for the translet class file
        String transletPath = fullClassName.replace('.', '/');
        if (_destinationDirectory != null) {
            transletPath = _destinationDirectory + "/" + transletPath + ".class";
        }
        else {
            if (xslFile != null && xslFile.getParent() != null)
                transletPath = xslFile.getParent() + "/" + transletPath + ".class";
            else
                transletPath = transletPath + ".class";
        }

        // Return null if the translet class file does not exist.
        File transletFile = new File(transletPath);
        if (!transletFile.exists())
            return null;

        // Compare the timestamps of the translet and the xsl file.
        // If the translet is older than the xsl file, return null
        // so that the xsl file is used for the transformation and
        // the translet is regenerated.
        if (xslFile != null && xslFile.exists()) {
            long xslTimestamp = xslFile.lastModified();
            long transletTimestamp = transletFile.lastModified();
            if (transletTimestamp < xslTimestamp)
                return null;
        }

        // Load the translet into a bytecode array.
        Vector bytecodes = new Vector();
        int fileLength = (int)transletFile.length();
        if (fileLength > 0) {
            FileInputStream input;
            try {
                input = new FileInputStream(transletFile);
            }
            catch (FileNotFoundException e) {
                return null;
            }

            byte[] bytes = new byte[fileLength];
            try {
                readFromInputStream(bytes, input, fileLength);
                input.close();
            }
            catch (IOException e) {
                return null;
            }

            bytecodes.addElement(bytes);
        }
        else
            return null;

        // Find the parent directory of the translet.
        String transletParentDir = transletFile.getParent();
        if (transletParentDir == null)
            transletParentDir = SecuritySupport.getSystemProperty("user.dir");

        File transletParentFile = new File(transletParentDir);

        // Find all the auxiliary files which have a name pattern of "transletClass$nnn.class".
        final String transletAuxPrefix = transletName + "$";
        File[] auxfiles = transletParentFile.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name)
                {
                    return (name.endsWith(".class") && name.startsWith(transletAuxPrefix));
                }
              });

        // Load the auxiliary class files and add them to the bytecode array.
        for (int i = 0; i < auxfiles.length; i++)
        {
            File auxfile = auxfiles[i];
            int auxlength = (int)auxfile.length();
            if (auxlength > 0) {
                FileInputStream auxinput = null;
                try {
                    auxinput = new FileInputStream(auxfile);
                }
                catch (FileNotFoundException e) {
                    continue;
                }

                byte[] bytes = new byte[auxlength];

                try {
                    readFromInputStream(bytes, auxinput, auxlength);
                    auxinput.close();
                }
                catch (IOException e) {
                    continue;
                }

                bytecodes.addElement(bytes);
            }
        }

        // Convert the Vector of byte[] to byte[][].
        final int count = bytecodes.size();
        if ( count > 0) {
            final byte[][] result = new byte[count][1];
            for (int i = 0; i < count; i++) {
                result[i] = (byte[])bytecodes.elementAt(i);
            }

            return result;
        }
        else
            return null;
    }

    /**
     * Load the translet classes from the jar file and return the bytecode.
     *
     * <p>
     *  从jar文件加载translet类并返回字节码。
     * 
     * 
     * @param source The xsl source
     * @param fullClassName The full name of the translet
     * @return The bytecode array
     */
    private byte[][] getBytecodesFromJar(Source source, String fullClassName)
    {
        String xslFileName = getStylesheetFileName(source);
        File xslFile = null;
        if (xslFileName != null)
            xslFile = new File(xslFileName);

        // Construct the path for the jar file
        String jarPath;
        if (_destinationDirectory != null)
            jarPath = _destinationDirectory + "/" + _jarFileName;
        else {
            if (xslFile != null && xslFile.getParent() != null)
                jarPath = xslFile.getParent() + "/" + _jarFileName;
            else
                jarPath = _jarFileName;
        }

        // Return null if the jar file does not exist.
        File file = new File(jarPath);
        if (!file.exists())
            return null;

        // Compare the timestamps of the jar file and the xsl file. Return null
        // if the xsl file is newer than the jar file.
        if (xslFile != null && xslFile.exists()) {
            long xslTimestamp = xslFile.lastModified();
            long transletTimestamp = file.lastModified();
            if (transletTimestamp < xslTimestamp)
                return null;
        }

        // Create a ZipFile object for the jar file
        ZipFile jarFile;
        try {
            jarFile = new ZipFile(file);
        }
        catch (IOException e) {
            return null;
        }

        String transletPath = fullClassName.replace('.', '/');
        String transletAuxPrefix = transletPath + "$";
        String transletFullName = transletPath + ".class";

        Vector bytecodes = new Vector();

        // Iterate through all entries in the jar file to find the
        // translet and auxiliary classes.
        Enumeration entries = jarFile.entries();
        while (entries.hasMoreElements())
        {
            ZipEntry entry = (ZipEntry)entries.nextElement();
            String entryName = entry.getName();
            if (entry.getSize() > 0 &&
                  (entryName.equals(transletFullName) ||
                  (entryName.endsWith(".class") &&
                      entryName.startsWith(transletAuxPrefix))))
            {
                try {
                    InputStream input = jarFile.getInputStream(entry);
                    int size = (int)entry.getSize();
                    byte[] bytes = new byte[size];
                    readFromInputStream(bytes, input, size);
                    input.close();
                    bytecodes.addElement(bytes);
                }
                catch (IOException e) {
                    return null;
                }
            }
        }

        // Convert the Vector of byte[] to byte[][].
        final int count = bytecodes.size();
        if (count > 0) {
            final byte[][] result = new byte[count][1];
            for (int i = 0; i < count; i++) {
                result[i] = (byte[])bytecodes.elementAt(i);
            }

            return result;
        }
        else
            return null;
    }

    /**
     * Read a given number of bytes from the InputStream into a byte array.
     *
     * <p>
     *  从InputStream中读取给定数量的字节为字节数组。
     * 
     * 
     * @param bytes The byte array to store the input content.
     * @param input The input stream.
     * @param size The number of bytes to read.
     */
    private void readFromInputStream(byte[] bytes, InputStream input, int size)
        throws IOException
    {
      int n = 0;
      int offset = 0;
      int length = size;
      while (length > 0 && (n = input.read(bytes, offset, length)) > 0) {
          offset = offset + n;
          length = length - n;
      }
    }

    /**
     * Return the base class name of the translet.
     * The translet name is resolved using the following rules:
     * 1. if the _transletName attribute is set and its value is not "GregorSamsa",
     *    then _transletName is returned.
     * 2. otherwise get the translet name from the base name of the system ID
     * 3. return "GregorSamsa" if the result from step 2 is null.
     *
     * <p>
     *  返回translet的基类名称。 translet名称使用以下规则解析：1.如果设置了_transletName属性并且其值不是"GregorSamsa",则返回_transletName。
     *  2.否则从系统ID的基本名称获取translet名称3.如果步骤2的结果为null,则返回"GregorSamsa"。
     * 
     * 
     * @param source The input Source
     * @return The name of the translet class
     */
    private String getTransletBaseName(Source source)
    {
        String transletBaseName = null;
        if (!_transletName.equals(DEFAULT_TRANSLET_NAME))
            return _transletName;
        else {
            String systemId = source.getSystemId();
            if (systemId != null) {
                String baseName = Util.baseName(systemId);
                if (baseName != null) {
                    baseName = Util.noExtName(baseName);
                    transletBaseName = Util.toJavaName(baseName);
                }
            }
        }

        return (transletBaseName != null) ? transletBaseName : DEFAULT_TRANSLET_NAME;
    }

    /**
     *  Return the local file name from the systemId of the Source object
     *
     * <p>
     *  从Source对象的systemId返回本地文件名
     * 
     * 
     * @param source The Source
     * @return The file name in the local filesystem, or null if the
     * systemId does not represent a local file.
     */
    private String getStylesheetFileName(Source source)
    {
        String systemId = source.getSystemId();
        if (systemId != null) {
            File file = new File(systemId);
            if (file.exists())
                return systemId;
            else {
                URL url;
                try {
                    url = new URL(systemId);
                }
                catch (MalformedURLException e) {
                    return null;
                }

                if ("file".equals(url.getProtocol()))
                    return url.getFile();
                else
                    return null;
            }
        }
        else
            return null;
    }

    /**
     * Returns a new instance of the XSLTC DTM Manager service.
     * <p>
     *  返回XSLTC DTM Manager服务的新实例。
     */
    protected final XSLTCDTMManager createNewDTMManagerInstance() {
        return XSLTCDTMManager.createNewDTMManagerInstance();
    }
}
