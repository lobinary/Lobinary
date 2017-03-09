/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * The Apache Software License, Version 1.1
 *
 *
 * Copyright (c) 1999-2004 The Apache Software Foundation.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 *    if any, must include the following acknowledgment:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowledgment may appear in the software itself,
 *    if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The names "Xerces" and "Apache Software Foundation" must
 *    not be used to endorse or promote products derived from this
 *    software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache",
 *    nor may "Apache" appear in their name, without prior written
 *    permission of the Apache Software Foundation.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation and was
 * originally based on software copyright (c) 1999, International
 * Business Machines, Inc., http://www.apache.org.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 * <p>
 *  Apache软件许可证,版本1.1
 * 
 *  版权所有(c)1999-2004 Apache软件基金会。版权所有。
 * 
 *  如果满足以下条件,则允许重新分发和使用源代码和二进制形式(带或不带修改)：
 * 
 *  1.源代码的再分发必须保留上述版权声明,此条件列表和以下免责声明。
 * 
 *  2.二进制形式的再分发必须在分发所提供的文档和/或其他材料中复制上述版权声明,此条件列表和以下免责声明。
 * 
 *  3.包含在重新分发中的最终用户文档(如果有)必须包括以下声明："本产品包括由Apache Software Foundation(http://www.apache.org/)开发的软件。
 * 或者,如果此类第三方确认通常出现,则此确认可能出现在软件本身中。
 * 
 *  4.未经事先书面许可,不得将"Xerces"和"Apache Software Foundation"名称用于支持或推广从本软件衍生的产品。如需书面许可,请联系apache@apache.org。
 * 
 *  未经Apache软件基金会事先书面许可,从本软件派生的产品可能不会被称为"Apache",也不可能出现在他们的名字中。
 * 
 * 本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 在任何情况下,APACHE软件基金会或其捐赠者均不对任何直接,间接,偶发,特殊,惩罚性或后果性损害(包括但不限于替代商品或服务的采购,使用,数据丢失或利润或业务中断),无论是由于任何责任推理原因,无论是
 * 在合同,严格责任或侵权(包括疏忽或其他方式)中,以任何方式使用本软件,即使已被告知此类软件的可能性损伤。
 * 本软件按"原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 *  ================================================== ==================。
 * 
 *  该软件包括许多个人代表Apache软件基金会所做的自愿捐款,最初是基于软件版权(c)1999,国际商业机器公司,http://www.apache.org。
 * 有关Apache Software Foundation的更多信息,请参阅<http://www.apache.org/>。
 * 
 */

package com.sun.org.apache.xerces.internal.impl;
import java.util.Hashtable;
import java.util.Locale;

import com.sun.org.apache.xerces.internal.util.DefaultErrorHandler;
import com.sun.org.apache.xerces.internal.util.ErrorHandlerProxy;
import com.sun.org.apache.xerces.internal.util.MessageFormatter;
import com.sun.org.apache.xerces.internal.xni.XMLLocator;
import com.sun.org.apache.xerces.internal.xni.XNIException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLComponent;
import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;
import com.sun.org.apache.xerces.internal.xni.parser.XMLParseException;
import org.xml.sax.ErrorHandler;

/**
 * This class is a common element of all parser configurations and is
 * used to report errors that occur. This component can be queried by
 * parser components from the component manager using the following
 * property ID:
 * <pre>
 *   http://apache.org/xml/properties/internal/error-reporter
 * </pre>
 * <p>
 * Errors are separated into domains that categorize a class of errors.
 * In a parser configuration, the parser would register a
 * <code>MessageFormatter</code> for each domain that is capable of
 * localizing error messages and formatting them based on information
 * about the error. Any parser component can invent new error domains
 * and register additional message formatters to localize messages in
 * those domains.
 * <p>
 * This component requires the following features and properties from the
 * component manager that uses it:
 * <ul>
 *  <li>http://apache.org/xml/properties/internal/error-handler</li>
 * </ul>
 * <p>
 * This component can use the following features and properties but they
 * are not required:
 * <ul>
 *  <li>http://apache.org/xml/features/continue-after-fatal-error</li>
 * </ul>
 *
 * @xerces.internal
 *
 * <p>
 *  此类是所有解析器配置的公共元素,用于报告发生的错误。此组件可以由组件管理器中的解析器组件使用以下属性ID查询：
 * <pre>
 *  http://apache.org/xml/properties/internal/error-reporter
 * </pre>
 * <p>
 * 错误被分为对错误类进行分类的域。在解析器配置中,解析器将为能够定位错误消息的每个域注册<code> MessageFormatter </code>,并根据有关错误的信息对其进行格式化。
 * 任何解析器组件都可以发明新的错误域,并注册其他消息格式化程序来本地化这些域中的消息。
 * <p>
 *  此组件需要使用它的组件管理器中的以下功能和属性：
 * <ul>
 *  <li> http://apache.org/xml/properties/internal/error-handler </li>
 * </ul>
 * <p>
 *  此组件可以使用以下功能和属性,但不是必需的：
 * <ul>
 *  <li> http://apache.org/xml/features/continue-after-fatal-error </li>
 * </ul>
 * 
 *  @ xerces.internal
 * 
 * 
 * @see MessageFormatter
 *
 * @author Eric Ye, IBM
 * @author Andy Clark, IBM
 *
 * @version $Id: XMLErrorReporter.java,v 1.5 2010-11-01 04:39:41 joehw Exp $
 */
public class XMLErrorReporter
    implements XMLComponent {

    //
    // Constants
    //

    // severity

    /**
     * Severity: warning. Warnings represent informational messages only
     * that should not be considered serious enough to stop parsing or
     * indicate an error in the document's validity.
     * <p>
     *  严重性：警告。警告仅表示不应被认为足够严重以停止解析或指示文档有效性中的错误的信息性消息。
     * 
     */
    public static final short SEVERITY_WARNING = 0;

    /**
     * Severity: error. Common causes of errors are document structure and/or
     * content that that does not conform to the grammar rules specified for
     * the document. These are typically validation errors.
     * <p>
     *  严重性：错误。错误的常见原因是不符合为文档指定的语法规则的文档结构和/或内容。这些通常是验证错误。
     * 
     */
    public static final short SEVERITY_ERROR = 1;

    /**
     * Severity: fatal error. Fatal errors are errors in the syntax of the
     * XML document or invalid byte sequences for a given encoding. The
     * XML 1.0 Specification mandates that errors of this type are not
     * recoverable.
     * <p>
     * <strong>Note:</strong> The parser does have a "continue after fatal
     * error" feature but it should be used with extreme caution and care.
     * <p>
     *  严重性：致命错误。致命错误是XML文档的语法中的错误或给定编码的无效字节序列。 XML 1.0规范要求此类型的错误不可恢复。
     * <p>
     *  <strong>注意</strong>：解析器确实具有"继续出现致命错误"功能,但应极其小心谨慎地使用。
     * 
     */
    public static final short SEVERITY_FATAL_ERROR = 2;

    // feature identifiers

    /** Feature identifier: continue after fatal error. */
    protected static final String CONTINUE_AFTER_FATAL_ERROR =
        Constants.XERCES_FEATURE_PREFIX + Constants.CONTINUE_AFTER_FATAL_ERROR_FEATURE;

    // property identifiers

    /** Property identifier: error handler. */
    protected static final String ERROR_HANDLER =
        Constants.XERCES_PROPERTY_PREFIX + Constants.ERROR_HANDLER_PROPERTY;

    // recognized features and properties

    /** Recognized features. */
    private static final String[] RECOGNIZED_FEATURES = {
        CONTINUE_AFTER_FATAL_ERROR,
    };

    /** Feature defaults. */
    private static final Boolean[] FEATURE_DEFAULTS = {
        null,
    };

    /** Recognized properties. */
    private static final String[] RECOGNIZED_PROPERTIES = {
        ERROR_HANDLER,
    };

    /** Property defaults. */
    private static final Object[] PROPERTY_DEFAULTS = {
        null,
    };

    //
    // Data
    //

    /** The locale to be used to format error messages. */
    protected Locale fLocale;

    /** Mapping of Message formatters for domains. */
    protected Hashtable fMessageFormatters;

    /** Error handler. */
    protected XMLErrorHandler fErrorHandler;

    /** Document locator. */
    protected XMLLocator fLocator;

    /** Continue after fatal error feature. */
    protected boolean fContinueAfterFatalError;

    /**
     * Default error handler. This error handler is only used in the
     * absence of a registered error handler so that errors are not
     * "swallowed" silently. This is one of the most common "problems"
     * reported by users of the parser.
     * <p>
     * 默认错误处理程序。此错误处理程序仅在没有注册的错误处理程序时使用,以便错误不会被静默"吞噬"。这是解析器的用户报告的最常见的"问题"之一。
     * 
     */
    protected XMLErrorHandler fDefaultErrorHandler;

    /** A SAX proxy to the error handler contained in this error reporter. */
    private ErrorHandler fSaxProxy = null;

    //
    // Constructors
    //

    /** Constructs an error reporter with a locator. */
    public XMLErrorReporter() {

        // REVISIT: [Q] Should the locator be passed to the reportError
        //              method? Otherwise, there is no way for a parser
        //              component to store information about where an
        //              error occurred so as to report it later.
        //
        //              An example would be to record the location of
        //              IDREFs so that, at the end of the document, if
        //              there is no associated ID declared, the error
        //              could report the location information of the
        //              reference. -Ac
        //
        // NOTE: I added another reportError method that allows the
        //       caller to specify the location of the error being
        //       reported. -Ac

        fMessageFormatters = new Hashtable();

    } // <init>()

    //
    // Methods
    //

    /**
     * Sets the current locale.
     *
     * <p>
     *  设置当前语言环境。
     * 
     * 
     * @param locale The new locale.
     */
    public void setLocale(Locale locale) {
        fLocale = locale;
    } // setLocale(Locale)

    /**
     * Gets the current locale.
     *
     * <p>
     *  获取当前语言环境。
     * 
     * 
     * @return the current Locale
     */
    public Locale getLocale() {
        return fLocale ;
    } // getLocale():  Locale

    /**
     * Sets the document locator.
     *
     * <p>
     *  设置文档定位器。
     * 
     * 
     * @param locator The locator.
     */
    public void setDocumentLocator(XMLLocator locator) {
        fLocator = locator;
    } // setDocumentLocator(XMLLocator)

    /**
     * Registers a message formatter for the specified domain.
     * <p>
     * <strong>Note:</strong> Registering a message formatter for a domain
     * when there is already a formatter registered will cause the previous
     * formatter to be lost. This method replaces any previously registered
     * message formatter for the specified domain.
     *
     * <p>
     *  注册指定域的消息格式化程序。
     * <p>
     *  <strong>注意</strong>：在已注册格式化程序的情况下为域注册消息格式化程序将导致先前的格式化程序丢失。此方法替换指定域的任何先前注册的消息格式化程序。
     * 
     * 
     * @param domain
     * @param messageFormatter
     */
    public void putMessageFormatter(String domain,
                                    MessageFormatter messageFormatter) {
        fMessageFormatters.put(domain, messageFormatter);
    } // putMessageFormatter(String,MessageFormatter)

    /**
     * Returns the message formatter associated with the specified domain,
     * or null if no message formatter is registered for that domain.
     *
     * <p>
     *  返回与指定域关联的消息格式化程序,如果没有为该域注册消息格式化程序,则返回null。
     * 
     * 
     * @param domain The domain of the message formatter.
     */
    public MessageFormatter getMessageFormatter(String domain) {
        return (MessageFormatter)fMessageFormatters.get(domain);
    } // getMessageFormatter(String):MessageFormatter

    /**
     * Removes the message formatter for the specified domain and
     * returns the removed message formatter.
     *
     * <p>
     *  删除指定域的消息格式化程序,并返回已删除的消息格式化程序。
     * 
     * 
     * @param domain The domain of the message formatter.
     */
    public MessageFormatter removeMessageFormatter(String domain) {
        return (MessageFormatter) fMessageFormatters.remove(domain);
    } // removeMessageFormatter(String):MessageFormatter

    /**
     * Reports an error. The error message passed to the error handler
     * is formatted for the locale by the message formatter installed
     * for the specified error domain.
     *
     * <p>
     *  报告错误。传递给错误处理程序的错误消息由为指定的错误域安装的消息格式化程序为区域设置进行格式化。
     * 
     * 
     * @param domain    The error domain.
     * @param key       The key of the error message.
     * @param arguments The replacement arguments for the error message,
     *                  if needed.
     * @param severity  The severity of the error.
     * @return          The formatted error message.
     *
     * @see #SEVERITY_WARNING
     * @see #SEVERITY_ERROR
     * @see #SEVERITY_FATAL_ERROR
     */
    public String reportError(String domain, String key, Object[] arguments,
                            short severity) throws XNIException {
        return reportError(fLocator, domain, key, arguments, severity);
    } // reportError(String,String,Object[],short):String

    /**
     * Reports an error. The error message passed to the error handler
     * is formatted for the locale by the message formatter installed
     * for the specified error domain.
     *
     * <p>
     *  报告错误。传递给错误处理程序的错误消息由为指定的错误域安装的消息格式化程序为区域设置进行格式化。
     * 
     * 
     * @param domain    The error domain.
     * @param key       The key of the error message.
     * @param arguments The replacement arguments for the error message,
     *                  if needed.
     * @param severity  The severity of the error.
     * @param exception The exception to wrap.
     * @return          The formatted error message.
     *
     * @see #SEVERITY_WARNING
     * @see #SEVERITY_ERROR
     * @see #SEVERITY_FATAL_ERROR
     */
    public String reportError(String domain, String key, Object[] arguments,
            short severity, Exception exception) throws XNIException {
        return reportError(fLocator, domain, key, arguments, severity, exception);
    } // reportError(String,String,Object[],short,Exception):String

    /**
     * Reports an error at a specific location.
     *
     * <p>
     *  在特定位置报告错误。
     * 
     * 
     * @param location  The error location.
     * @param domain    The error domain.
     * @param key       The key of the error message.
     * @param arguments The replacement arguments for the error message,
     *                  if needed.
     * @param severity  The severity of the error.
     * @return          The formatted error message.
     *
     * @see #SEVERITY_WARNING
     * @see #SEVERITY_ERROR
     * @see #SEVERITY_FATAL_ERROR
     */
    public String reportError(XMLLocator location,
            String domain, String key, Object[] arguments,
            short severity) throws XNIException {
        return reportError(location, domain, key, arguments, severity, null);
    } // reportError(XMLLocator,String,String,Object[],short):String

    /**
     * Reports an error at a specific location.
     *
     * <p>
     *  在特定位置报告错误。
     * 
     * 
     * @param location  The error location.
     * @param domain    The error domain.
     * @param key       The key of the error message.
     * @param arguments The replacement arguments for the error message,
     *                  if needed.
     * @param severity  The severity of the error.
     * @param exception The exception to wrap.
     * @return          The formatted error message.
     *
     * @see #SEVERITY_WARNING
     * @see #SEVERITY_ERROR
     * @see #SEVERITY_FATAL_ERROR
     */
    public String reportError(XMLLocator location,
                            String domain, String key, Object[] arguments,
                            short severity, Exception exception) throws XNIException {

        // REVISIT: [Q] Should we do anything about invalid severity
        //              parameter? -Ac

        // format error message and create parse exception
        MessageFormatter messageFormatter = getMessageFormatter(domain);
        String message;
        if (messageFormatter != null) {
            message = messageFormatter.formatMessage(fLocale, key, arguments);
        }
        else {
            StringBuffer str = new StringBuffer();
            str.append(domain);
            str.append('#');
            str.append(key);
            int argCount = arguments != null ? arguments.length : 0;
            if (argCount > 0) {
                str.append('?');
                for (int i = 0; i < argCount; i++) {
                    str.append(arguments[i]);
                    if (i < argCount -1) {
                        str.append('&');
                    }
                }
            }
            message = str.toString();
        }
        XMLParseException parseException = (exception != null) ?
            new XMLParseException(location, message, exception) :
            new XMLParseException(location, message);

        // get error handler
        XMLErrorHandler errorHandler = fErrorHandler;
        if (errorHandler == null) {
            if (fDefaultErrorHandler == null) {
                fDefaultErrorHandler = new DefaultErrorHandler();
            }
            errorHandler = fDefaultErrorHandler;
        }

        // call error handler
        switch (severity) {
            case SEVERITY_WARNING: {
                errorHandler.warning(domain, key, parseException);
                break;
            }
            case SEVERITY_ERROR: {
                errorHandler.error(domain, key, parseException);
                break;
            }
            case SEVERITY_FATAL_ERROR: {
                errorHandler.fatalError(domain, key, parseException);
                if (!fContinueAfterFatalError) {
                    throw parseException;
                }
                break;
            }
        }
        return message;

    } // reportError(XMLLocator,String,String,Object[],short,Exception):String

    //
    // XMLComponent methods
    //

    /**
     * Resets the component. The component can query the component manager
     * about any features and properties that affect the operation of the
     * component.
     *
     * <p>
     *  复位组件。组件可以向组件管理器查询影响组件操作的任何特征和属性。
     * 
     * 
     * @param componentManager The component manager.
     *
     * @throws SAXException Thrown by component on initialization error.
     *                      For example, if a feature or property is
     *                      required for the operation of the component, the
     *                      component manager may throw a
     *                      SAXNotRecognizedException or a
     *                      SAXNotSupportedException.
     */
    public void reset(XMLComponentManager componentManager)
        throws XNIException {

        // features
        fContinueAfterFatalError = componentManager.getFeature(CONTINUE_AFTER_FATAL_ERROR, false);

        // properties
        fErrorHandler = (XMLErrorHandler)componentManager.getProperty(ERROR_HANDLER);

    } // reset(XMLComponentManager)

    /**
     * Returns a list of feature identifiers that are recognized by
     * this component. This method may return null if no features
     * are recognized by this component.
     * <p>
     * 返回此组件可识别的要素标识符列表。如果此组件未识别任何功能,此方法可能返回null。
     * 
     */
    public String[] getRecognizedFeatures() {
        return (String[])(RECOGNIZED_FEATURES.clone());
    } // getRecognizedFeatures():String[]

    /**
     * Sets the state of a feature. This method is called by the component
     * manager any time after reset when a feature changes state.
     * <p>
     * <strong>Note:</strong> Components should silently ignore features
     * that do not affect the operation of the component.
     *
     * <p>
     *  设置要素的状态。当特性改变状态时,组件管理器在重置后任何时候调用此方法。
     * <p>
     *  <strong>注意：</strong>组件应默认忽略不影响组件操作的功能。
     * 
     * 
     * @param featureId The feature identifier.
     * @param state     The state of the feature.
     *
     * @throws SAXNotRecognizedException The component should not throw
     *                                   this exception.
     * @throws SAXNotSupportedException The component should not throw
     *                                  this exception.
     */
    public void setFeature(String featureId, boolean state)
        throws XMLConfigurationException {

        //
        // Xerces features
        //

        if (featureId.startsWith(Constants.XERCES_FEATURE_PREFIX)) {
            final int suffixLength = featureId.length() - Constants.XERCES_FEATURE_PREFIX.length();

            //
            // http://apache.org/xml/features/continue-after-fatal-error
            //   Allows the parser to continue after a fatal error.
            //   Normally, a fatal error would stop the parse.
            //
            if (suffixLength == Constants.CONTINUE_AFTER_FATAL_ERROR_FEATURE.length() &&
                featureId.endsWith(Constants.CONTINUE_AFTER_FATAL_ERROR_FEATURE)) {
                fContinueAfterFatalError = state;
            }
        }

    } // setFeature(String,boolean)

    // return state of given feature or false if unsupported.
    public boolean getFeature(String featureId)
        throws XMLConfigurationException {

        //
        // Xerces features
        //

        if (featureId.startsWith(Constants.XERCES_FEATURE_PREFIX)) {
                final int suffixLength = featureId.length() - Constants.XERCES_FEATURE_PREFIX.length();

            //
            // http://apache.org/xml/features/continue-after-fatal-error
            //   Allows the parser to continue after a fatal error.
            //   Normally, a fatal error would stop the parse.
            //
            if (suffixLength == Constants.CONTINUE_AFTER_FATAL_ERROR_FEATURE.length() &&
                featureId.endsWith(Constants.CONTINUE_AFTER_FATAL_ERROR_FEATURE)) {
                return fContinueAfterFatalError ;
            }
        }
        return false;

    } // setFeature(String,boolean)

    /**
     * Returns a list of property identifiers that are recognized by
     * this component. This method may return null if no properties
     * are recognized by this component.
     * <p>
     *  返回此组件可识别的属性标识符列表。如果此组件未识别任何属性,此方法可能返回null。
     * 
     */
    public String[] getRecognizedProperties() {
        return (String[])(RECOGNIZED_PROPERTIES.clone());
    } // getRecognizedProperties():String[]

    /**
     * Sets the value of a property. This method is called by the component
     * manager any time after reset when a property changes value.
     * <p>
     * <strong>Note:</strong> Components should silently ignore properties
     * that do not affect the operation of the component.
     *
     * <p>
     *  设置属性的值。当属性更改值时,组件管理器在重置后任何时候调用此方法。
     * <p>
     *  <strong>注意：</strong>组件应静默忽略不影响组件操作的属性。
     * 
     * 
     * @param propertyId The property identifier.
     * @param value      The value of the property.
     *
     * @throws SAXNotRecognizedException The component should not throw
     *                                   this exception.
     * @throws SAXNotSupportedException The component should not throw
     *                                  this exception.
     */
    public void setProperty(String propertyId, Object value)
        throws XMLConfigurationException {

        //
        // Xerces properties
        //

        if (propertyId.startsWith(Constants.XERCES_PROPERTY_PREFIX)) {
            final int suffixLength = propertyId.length() - Constants.XERCES_PROPERTY_PREFIX.length();

            if (suffixLength == Constants.ERROR_HANDLER_PROPERTY.length() &&
                propertyId.endsWith(Constants.ERROR_HANDLER_PROPERTY)) {
                fErrorHandler = (XMLErrorHandler)value;
            }
        }

    } // setProperty(String,Object)

    /**
     * Returns the default state for a feature, or null if this
     * component does not want to report a default value for this
     * feature.
     *
     * <p>
     *  返回特征的默认状态,如果此组件不希望报告此特征的默认值,则返回null。
     * 
     * 
     * @param featureId The feature identifier.
     *
     * @since Xerces 2.2.0
     */
    public Boolean getFeatureDefault(String featureId) {
        for (int i = 0; i < RECOGNIZED_FEATURES.length; i++) {
            if (RECOGNIZED_FEATURES[i].equals(featureId)) {
                return FEATURE_DEFAULTS[i];
            }
        }
        return null;
    } // getFeatureDefault(String):Boolean

    /**
     * Returns the default state for a property, or null if this
     * component does not want to report a default value for this
     * property.
     *
     * <p>
     *  返回属性的默认状态,如果此组件不希望报告此属性的默认值,则返回null。
     * 
     * 
     * @param propertyId The property identifier.
     *
     * @since Xerces 2.2.0
     */
    public Object getPropertyDefault(String propertyId) {
        for (int i = 0; i < RECOGNIZED_PROPERTIES.length; i++) {
            if (RECOGNIZED_PROPERTIES[i].equals(propertyId)) {
                return PROPERTY_DEFAULTS[i];
            }
        }
        return null;
    } // getPropertyDefault(String):Object

    /**
     * Get the internal XMLErrrorHandler.
     * <p>
     *  获取内部XMLErrrorHandler。
     * 
     */
    public XMLErrorHandler getErrorHandler() {
        return fErrorHandler;
    }

    /**
     * Gets the internal XMLErrorHandler
     * as SAX ErrorHandler.
     * <p>
     *  获取内部XMLErrorHandler作为SAX ErrorHandler。
     */
    public ErrorHandler getSAXErrorHandler() {
        if (fSaxProxy == null) {
            fSaxProxy = new ErrorHandlerProxy() {
                protected XMLErrorHandler getErrorHandler() {
                    return fErrorHandler;
                }
            };
        }
        return fSaxProxy;
    }

} // class XMLErrorReporter
