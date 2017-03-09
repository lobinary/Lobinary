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
 * $Id: SmartTransformerFactoryImpl.java,v 1.2.4.1 2005/09/14 09:57:13 pvedula Exp $
 * <p>
 *  $ Id：SmartTransformerFactoryImpl.java,v 1.2.4.1 2005/09/14 09:57:13 pvedula Exp $
 * 
 */


package com.sun.org.apache.xalan.internal.xsltc.trax;

import javax.xml.XMLConstants;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Source;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TemplatesHandler;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
import org.xml.sax.XMLFilter;

/**
 * Implementation of a transformer factory that uses an XSLTC
 * transformer factory for the creation of Templates objects
 * and uses the Xalan processor transformer factory for the
 * creation of Transformer objects.
 * <p>
 *  实现使用XSLTC变压器工厂创建Templates对象的变压器工厂,并使用Xalan处理器变压器工厂创建Transformer对象。
 * 
 * 
 * @author G. Todd Miller
 */
public class SmartTransformerFactoryImpl extends SAXTransformerFactory
{
    /**
     * <p>Name of class as a constant to use for debugging.</p>
     * <p>
     *  <p>类名称为用于调试的常量</p>
     * 
     */
    private static final String CLASS_NAME = "SmartTransformerFactoryImpl";

    private SAXTransformerFactory _xsltcFactory = null;
    private SAXTransformerFactory _xalanFactory = null;
    private SAXTransformerFactory _currFactory = null;
    private ErrorListener      _errorlistener = null;
    private URIResolver        _uriresolver = null;

    /**
     * <p>State of secure processing feature.</p>
     * <p>
     *  <p>安全处理功能的状态。</p>
     * 
     */
    private boolean featureSecureProcessing = false;

    /**
     * implementation of the SmartTransformerFactory. This factory
     * uses com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactory
     * to return Templates objects; and uses
     * com.sun.org.apache.xalan.internal.processor.TransformerFactory
     * to return Transformer objects.
     * <p>
     *  实现SmartTransformerFactory。
     * 此工厂使用com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactory返回Templates对象;并使用com.sun.org.apac
     * he.xalan.internal.processor.TransformerFactory返回Transformer对象。
     *  实现SmartTransformerFactory。
     * 
     */
    public SmartTransformerFactoryImpl() { }

    private void createXSLTCTransformerFactory() {
        _xsltcFactory = new TransformerFactoryImpl();
        _currFactory = _xsltcFactory;
    }

    private void createXalanTransformerFactory() {
        final String xalanMessage =
            "com.sun.org.apache.xalan.internal.xsltc.trax.SmartTransformerFactoryImpl "+
            "could not create an "+
            "com.sun.org.apache.xalan.internal.processor.TransformerFactoryImpl.";
        // try to create instance of Xalan factory...
        try {
            Class xalanFactClass = ObjectFactory.findProviderClass(
                "com.sun.org.apache.xalan.internal.processor.TransformerFactoryImpl",
                true);
            _xalanFactory = (SAXTransformerFactory)
                xalanFactClass.newInstance();
        }
        catch (ClassNotFoundException e) {
            System.err.println(xalanMessage);
        }
        catch (InstantiationException e) {
            System.err.println(xalanMessage);
        }
        catch (IllegalAccessException e) {
            System.err.println(xalanMessage);
        }
        _currFactory = _xalanFactory;
    }

    public void setErrorListener(ErrorListener listener)
        throws IllegalArgumentException
    {
        _errorlistener = listener;
    }

    public ErrorListener getErrorListener() {
        return _errorlistener;
    }

    public Object getAttribute(String name)
        throws IllegalArgumentException
    {
        // GTM: NB: 'debug' should change to something more unique...
        if ((name.equals("translet-name")) || (name.equals("debug"))) {
            if (_xsltcFactory == null) {
                createXSLTCTransformerFactory();
            }
            return _xsltcFactory.getAttribute(name);
        }
        else {
            if (_xalanFactory == null) {
                createXalanTransformerFactory();
            }
            return _xalanFactory.getAttribute(name);
        }
    }

    public void setAttribute(String name, Object value)
        throws IllegalArgumentException {
        // GTM: NB: 'debug' should change to something more unique...
        if ((name.equals("translet-name")) || (name.equals("debug"))) {
            if (_xsltcFactory == null) {
                createXSLTCTransformerFactory();
            }
            _xsltcFactory.setAttribute(name, value);
        }
        else {
            if (_xalanFactory == null) {
                createXalanTransformerFactory();
            }
            _xalanFactory.setAttribute(name, value);
        }
    }

    /**
     * <p>Set a feature for this <code>SmartTransformerFactory</code> and <code>Transformer</code>s
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
     * <p>为此工厂创建的<code> SmartTransformerFactory </code>和<code> Transformer </code> s或<code>模板</code>设置功能。
     * <​​/ p>。
     * 
     * <p>
     *  功能名称是完全限定的{@link java.net.URI}。实现可以定义它们自己的特征。
     * 如果此创建的<code> TransformerFactory </code>或<code> Transformer </code>或<code> Template </code>无法支持该功能,则会抛
     * 出{@link TransformerConfigurationException}。
     *  功能名称是完全限定的{@link java.net.URI}。实现可以定义它们自己的特征。
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
    public void setFeature(String name, boolean value)
        throws TransformerConfigurationException {

        // feature name cannot be null
        if (name == null) {
            ErrorMsg err = new ErrorMsg(ErrorMsg.JAXP_SET_FEATURE_NULL_NAME);
            throw new NullPointerException(err.toString());
        }
        // secure processing?
        else if (name.equals(XMLConstants.FEATURE_SECURE_PROCESSING)) {
            featureSecureProcessing = value;
            // all done processing feature
            return;
        }
        else {
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
    public boolean getFeature(String name) {
        // All supported features should be listed here
        String[] features = {
            DOMSource.FEATURE,
            DOMResult.FEATURE,
            SAXSource.FEATURE,
            SAXResult.FEATURE,
            StreamSource.FEATURE,
            StreamResult.FEATURE
        };

        // feature name cannot be null
        if (name == null) {
            ErrorMsg err = new ErrorMsg(ErrorMsg.JAXP_GET_FEATURE_NULL_NAME);
            throw new NullPointerException(err.toString());
        }

        // Inefficient, but it really does not matter in a function like this
        for (int i = 0; i < features.length; i++) {
            if (name.equals(features[i]))
                return true;
        }

        // secure processing?
        if (name.equals(XMLConstants.FEATURE_SECURE_PROCESSING)) {
            return featureSecureProcessing;
        }

        // unknown feature
        return false;
    }

    public URIResolver getURIResolver() {
        return _uriresolver;
    }

    public void setURIResolver(URIResolver resolver) {
        _uriresolver = resolver;
    }

    public Source getAssociatedStylesheet(Source source, String media,
                                          String title, String charset)
        throws TransformerConfigurationException
    {
        if (_currFactory == null) {
            createXSLTCTransformerFactory();
        }
        return _currFactory.getAssociatedStylesheet(source, media,
                title, charset);
    }

    /**
     * Create a Transformer object that copies the input document to the
     * result. Uses the com.sun.org.apache.xalan.internal.processor.TransformerFactory.
     * <p>
     *  创建将输入文档复制到结果的Transformer对象。使用com.sun.org.apache.xalan.internal.processor.TransformerFactory。
     * 
     * 
     * @return A Transformer object.
     */
    public Transformer newTransformer()
        throws TransformerConfigurationException
    {
        if (_xalanFactory == null) {
            createXalanTransformerFactory();
        }
        if (_errorlistener != null) {
            _xalanFactory.setErrorListener(_errorlistener);
        }
        if (_uriresolver != null) {
            _xalanFactory.setURIResolver(_uriresolver);
        }
        _currFactory = _xalanFactory;
        return _currFactory.newTransformer();
    }

    /**
     * Create a Transformer object that from the input stylesheet
     * Uses the com.sun.org.apache.xalan.internal.processor.TransformerFactory.
     * <p>
     *  从输入样式表创建Transformer对象使用com.sun.org.apache.xalan.internal.processor.TransformerFactory。
     * 
     * 
     * @param source the stylesheet.
     * @return A Transformer object.
     */
    public Transformer newTransformer(Source source) throws
        TransformerConfigurationException
    {
        if (_xalanFactory == null) {
            createXalanTransformerFactory();
        }
        if (_errorlistener != null) {
            _xalanFactory.setErrorListener(_errorlistener);
        }
        if (_uriresolver != null) {
            _xalanFactory.setURIResolver(_uriresolver);
        }
        _currFactory = _xalanFactory;
        return _currFactory.newTransformer(source);
    }

    /**
     * Create a Templates object that from the input stylesheet
     * Uses the com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactory.
     * <p>
     *  从输入样式表创建模板对象使用com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactory。
     * 
     * 
     * @param source the stylesheet.
     * @return A Templates object.
     */
    public Templates newTemplates(Source source)
        throws TransformerConfigurationException
    {
        if (_xsltcFactory == null) {
            createXSLTCTransformerFactory();
        }
        if (_errorlistener != null) {
            _xsltcFactory.setErrorListener(_errorlistener);
        }
        if (_uriresolver != null) {
            _xsltcFactory.setURIResolver(_uriresolver);
        }
        _currFactory = _xsltcFactory;
        return _currFactory.newTemplates(source);
    }

    /**
     * Get a TemplatesHandler object that can process SAX ContentHandler
     * events into a Templates object. Uses the
     * com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactory.
     * <p>
     *  获取可以将SAX ContentHandler事件处理到Templates对象中的TemplatesHandler对象。
     * 使用com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactory。
     * 
     */
    public TemplatesHandler newTemplatesHandler()
        throws TransformerConfigurationException
    {
        if (_xsltcFactory == null) {
            createXSLTCTransformerFactory();
        }
        if (_errorlistener != null) {
            _xsltcFactory.setErrorListener(_errorlistener);
        }
        if (_uriresolver != null) {
            _xsltcFactory.setURIResolver(_uriresolver);
        }
        return _xsltcFactory.newTemplatesHandler();
    }

    /**
     * Get a TransformerHandler object that can process SAX ContentHandler
     * events based on a copy transformer.
     * Uses com.sun.org.apache.xalan.internal.processor.TransformerFactory.
     * <p>
     * 获取可以基于复制变换器处理SAX ContentHandler事件的TransformerHandler对象。
     * 使用com.sun.org.apache.xalan.internal.processor.TransformerFactory。
     * 
     */
    public TransformerHandler newTransformerHandler()
        throws TransformerConfigurationException
    {
        if (_xalanFactory == null) {
            createXalanTransformerFactory();
        }
        if (_errorlistener != null) {
            _xalanFactory.setErrorListener(_errorlistener);
        }
        if (_uriresolver != null) {
            _xalanFactory.setURIResolver(_uriresolver);
        }
        return _xalanFactory.newTransformerHandler();
    }

    /**
     * Get a TransformerHandler object that can process SAX ContentHandler
     * events based on a transformer specified by the stylesheet Source.
     * Uses com.sun.org.apache.xalan.internal.processor.TransformerFactory.
     * <p>
     *  获取TransformerHandler对象,该对象可以基于样式表Source指定的变换器处理SAX ContentHandler事件。
     * 使用com.sun.org.apache.xalan.internal.processor.TransformerFactory。
     * 
     */
    public TransformerHandler newTransformerHandler(Source src)
        throws TransformerConfigurationException
    {
        if (_xalanFactory == null) {
            createXalanTransformerFactory();
        }
        if (_errorlistener != null) {
            _xalanFactory.setErrorListener(_errorlistener);
        }
        if (_uriresolver != null) {
            _xalanFactory.setURIResolver(_uriresolver);
        }
        return _xalanFactory.newTransformerHandler(src);
    }


    /**
     * Get a TransformerHandler object that can process SAX ContentHandler
     * events based on a transformer specified by the stylesheet Source.
     * Uses com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactory.
     * <p>
     *  获取TransformerHandler对象,该对象可以基于样式表Source指定的变换器处理SAX ContentHandler事件。
     * 使用com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactory。
     * 
     */
    public TransformerHandler newTransformerHandler(Templates templates)
        throws TransformerConfigurationException
    {
        if (_xsltcFactory == null) {
            createXSLTCTransformerFactory();
        }
        if (_errorlistener != null) {
            _xsltcFactory.setErrorListener(_errorlistener);
        }
        if (_uriresolver != null) {
            _xsltcFactory.setURIResolver(_uriresolver);
        }
        return _xsltcFactory.newTransformerHandler(templates);
    }


    /**
     * Create an XMLFilter that uses the given source as the
     * transformation instructions. Uses
     * com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactory.
     * <p>
     *  创建一个使用给定源作为转换指令的XMLFilter。使用com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactory。
     * 
     */
    public XMLFilter newXMLFilter(Source src)
        throws TransformerConfigurationException {
        if (_xsltcFactory == null) {
            createXSLTCTransformerFactory();
        }
        if (_errorlistener != null) {
            _xsltcFactory.setErrorListener(_errorlistener);
        }
        if (_uriresolver != null) {
            _xsltcFactory.setURIResolver(_uriresolver);
        }
        Templates templates = _xsltcFactory.newTemplates(src);
        if (templates == null ) return null;
        return newXMLFilter(templates);
    }

    /*
     * Create an XMLFilter that uses the given source as the
     * transformation instructions. Uses
     * com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactory.
     * <p>
     *  创建一个使用给定源作为转换指令的XMLFilter。使用com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactory。
     */
    public XMLFilter newXMLFilter(Templates templates)
        throws TransformerConfigurationException {
        try {
            return new com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter(templates);
        }
        catch(TransformerConfigurationException e1) {
            if (_xsltcFactory == null) {
                createXSLTCTransformerFactory();
            }
            ErrorListener errorListener = _xsltcFactory.getErrorListener();
            if(errorListener != null) {
                try {
                    errorListener.fatalError(e1);
                    return null;
                }
                catch( TransformerException e2) {
                    new TransformerConfigurationException(e2);
                }
            }
            throw e1;
        }
    }
}
