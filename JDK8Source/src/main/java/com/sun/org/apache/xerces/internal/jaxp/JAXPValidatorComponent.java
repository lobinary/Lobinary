/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2015, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
/*
 * Copyright 2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 *  版权所有2005 Apache软件基金会。
 * 
 *  根据Apache许可证2.0版("许可证")授权;您不能使用此文件,除非符合许可证。您可以通过获取许可证的副本
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  除非适用法律要求或书面同意,否则根据许可证分发的软件按"原样"分发,不附带任何明示或暗示的担保或条件。请参阅管理许可证下的权限和限制的特定语言的许可证。
 * 
 */

package com.sun.org.apache.xerces.internal.jaxp;

import java.io.IOException;

import javax.xml.validation.TypeInfoProvider;
import javax.xml.validation.ValidatorHandler;

import com.sun.org.apache.xerces.internal.dom.DOMInputImpl;
import com.sun.org.apache.xerces.internal.impl.Constants;
import com.sun.org.apache.xerces.internal.impl.XMLErrorReporter;
import com.sun.org.apache.xerces.internal.impl.xs.opti.DefaultXMLDocumentHandler;
import com.sun.org.apache.xerces.internal.util.AttributesProxy;
import com.sun.org.apache.xerces.internal.util.AugmentationsImpl;
import com.sun.org.apache.xerces.internal.util.ErrorHandlerProxy;
import com.sun.org.apache.xerces.internal.util.ErrorHandlerWrapper;
import com.sun.org.apache.xerces.internal.util.LocatorProxy;
import com.sun.org.apache.xerces.internal.util.SymbolTable;
import com.sun.org.apache.xerces.internal.util.XMLResourceIdentifierImpl;
import com.sun.org.apache.xerces.internal.xni.Augmentations;
import com.sun.org.apache.xerces.internal.xni.NamespaceContext;
import com.sun.org.apache.xerces.internal.xni.QName;
import com.sun.org.apache.xerces.internal.xni.XMLAttributes;
import com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler;
import com.sun.org.apache.xerces.internal.xni.XMLLocator;
import com.sun.org.apache.xerces.internal.xni.XMLString;
import com.sun.org.apache.xerces.internal.xni.XNIException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLComponent;
import com.sun.org.apache.xerces.internal.xni.parser.XMLComponentManager;
import com.sun.org.apache.xerces.internal.xni.parser.XMLConfigurationException;
import com.sun.org.apache.xerces.internal.xni.parser.XMLEntityResolver;
import com.sun.org.apache.xerces.internal.xni.parser.XMLErrorHandler;
import com.sun.org.apache.xerces.internal.xni.parser.XMLInputSource;
import org.w3c.dom.TypeInfo;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * Runs events through a {@link javax.xml.validation.ValidatorHandler}
 * and performs validation/infoset-augmentation by an external validator.
 *
 * <p>
 * This component sets up the pipeline as follows:
 *
 * <!-- this picture may look teribble on your IDE but it is correct. -->
 * <pre>
 *             __                                           __
 *            /  |==> XNI2SAX --> Validator --> SAX2XNI ==>|
 *           /   |                                         |
 *       ==>| Tee|                                         | next
 *           \   |                                         |  component
 *            \  |============other XNI events============>|
 *             ~~                                           ~~
 * </pre>
 * <p>
 * only those events that need to go through Validator will go the 1st route,
 * and other events go the 2nd direct route.
 *
 * <p>
 *  通过{@link javax.xml.validation.ValidatorHandler}运行事件,并由外部验证程序执行验证/信息集增加。
 * 
 * <p>
 *  此组件设置管道如下：
 * 
 * <!-- this picture may look teribble on your IDE but it is correct. -->
 * <pre>
 *  __ __ / | ==> XNI2SAX  - > Validator  - > SAX2XNI ==> | / | | ==> | Tee | |下一个\ | |组件\ | ===========
 * =其他XNI事件============> | ~~~~。
 * </pre>
 * <p>
 *  只有那些需要通过Validator的事件才会进入第一条路线,而其他事件会进入第二条直接路线。
 * 
 * 
 * @author Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
 */
final class JAXPValidatorComponent
    extends TeeXMLDocumentFilterImpl implements XMLComponent {

    /** Property identifier: entity manager. */
    private static final String ENTITY_MANAGER =
        Constants.XERCES_PROPERTY_PREFIX + Constants.ENTITY_MANAGER_PROPERTY;

    /** Property identifier: error reporter. */
    private static final String ERROR_REPORTER =
        Constants.XERCES_PROPERTY_PREFIX + Constants.ERROR_REPORTER_PROPERTY;

    /** Property identifier: symbol table. */
    private static final String SYMBOL_TABLE =
        Constants.XERCES_PROPERTY_PREFIX + Constants.SYMBOL_TABLE_PROPERTY;

    // pipeline parts
    private final ValidatorHandler validator;
    private final XNI2SAX xni2sax = new XNI2SAX();
    private final SAX2XNI sax2xni = new SAX2XNI();

    // never be null
    private final TypeInfoProvider typeInfoProvider;

    /**
     * Used to store the {@link Augmentations} associated with the
     * current event, so that we can pick it up again
     * when the event is forwarded by the {@link ValidatorHandler}.
     *
     * UGLY HACK.
     * <p>
     * 用于存储与当前事件相关联的{@link增强工具},以便在{@link ValidatorHandler}转发事件时,我们可以再次选择。
     * 
     *  UGLY HACK。
     * 
     */
    private Augmentations fCurrentAug;

    /**
     * {@link XMLAttributes} version of {@link #fCurrentAug}.
     * <p>
     *  {@link XMLAttributes} {@link #fCurrentAug}版本。
     * 
     */
    private XMLAttributes fCurrentAttributes;

    // components obtained from a manager / property

    private SymbolTable fSymbolTable;
    private XMLErrorReporter fErrorReporter;
    private XMLEntityResolver fEntityResolver;

    /**
    /* <p>
    /* 
     * @param validatorHandler may not be null.
     */
    public JAXPValidatorComponent( ValidatorHandler validatorHandler ) {
        this.validator = validatorHandler;
        TypeInfoProvider tip = validatorHandler.getTypeInfoProvider();
        if(tip==null)   tip = noInfoProvider;
        this.typeInfoProvider = tip;

        // configure wiring between internal components.
        xni2sax.setContentHandler(validator);
        validator.setContentHandler(sax2xni);
        this.setSide(xni2sax);

        // configure validator with proper EntityResolver/ErrorHandler.
        validator.setErrorHandler(new ErrorHandlerProxy() {
            protected XMLErrorHandler getErrorHandler() {
                XMLErrorHandler handler = fErrorReporter.getErrorHandler();
                if(handler!=null)   return handler;
                return new ErrorHandlerWrapper(DraconianErrorHandler.getInstance());
            }
        });
        validator.setResourceResolver(new LSResourceResolver() {
            public LSInput resolveResource(String type,String ns, String publicId, String systemId, String baseUri) {
                if(fEntityResolver==null)   return null;
                try {
                    XMLInputSource is = fEntityResolver.resolveEntity(
                        new XMLResourceIdentifierImpl(publicId,systemId,baseUri,null));
                    if(is==null)    return null;

                    LSInput di = new DOMInputImpl();
                    di.setBaseURI(is.getBaseSystemId());
                    di.setByteStream(is.getByteStream());
                    di.setCharacterStream(is.getCharacterStream());
                    di.setEncoding(is.getEncoding());
                    di.setPublicId(is.getPublicId());
                    di.setSystemId(is.getSystemId());

                    return di;
                } catch( IOException e ) {
                    // erors thrown by the callback is not supposed to be
                    // reported to users.
                    throw new XNIException(e);
                }
            }
        });
    }


    public void startElement(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {
        fCurrentAttributes = attributes;
        fCurrentAug = augs;
        xni2sax.startElement(element,attributes,null);
        fCurrentAttributes = null; // mostly to make it easy to find any bug.
    }

    public void endElement(QName element, Augmentations augs) throws XNIException {
        fCurrentAug = augs;
        xni2sax.endElement(element,null);
    }

    public void emptyElement(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {
        startElement(element,attributes,augs);
        endElement(element,augs);
    }


    public void characters(XMLString text, Augmentations augs) throws XNIException {
        // since a validator may change the contents,
        // let this one go through a validator
        fCurrentAug = augs;
        xni2sax.characters(text,null);
    }

    public void ignorableWhitespace(XMLString text, Augmentations augs) throws XNIException {
        // since a validator may change the contents,
        // let this one go through a validator
        fCurrentAug = augs;
        xni2sax.ignorableWhitespace(text,null);
    }

    public void reset(XMLComponentManager componentManager) throws XMLConfigurationException {
        // obtain references from the manager
        fSymbolTable = (SymbolTable)componentManager.getProperty(SYMBOL_TABLE);
        fErrorReporter = (XMLErrorReporter)componentManager.getProperty(ERROR_REPORTER);
        try {
            fEntityResolver = (XMLEntityResolver) componentManager.getProperty(ENTITY_MANAGER);
        }
        catch (XMLConfigurationException e) {
            fEntityResolver = null;
        }
    }

    /**
     *
     * Uses {@link DefaultHandler} as a default implementation of
     * {@link ContentHandler}.
     *
     * <p>
     * We only forward certain events from a {@link ValidatorHandler}.
     * Other events should go "the 2nd direct route".
     * <p>
     *  使用{@link DefaultHandler}作为{@link ContentHandler}的默认实现。
     * 
     * <p>
     *  我们只从{@link ValidatorHandler}转发某些事件。其他事件应该去"第二直达路线"。
     * 
     */
    private final class SAX2XNI extends DefaultHandler {

        /**
         * {@link Augmentations} to send along with events.
         * We reuse one object for efficiency.
         * <p>
         *  {@link扩充功能}与活动一并传送。我们重用一个对象的效率。
         * 
         */
        private final Augmentations fAugmentations = new AugmentationsImpl();

        /**
         * {@link QName} to send along events.
         * we reuse one QName for efficiency.
         * <p>
         *  {@link QName}随事件发送。我们重用一个QName的效率。
         * 
         */
        private final QName fQName = new QName();

        public void characters(char[] ch, int start, int len) throws SAXException {
            try {
                handler().characters(new XMLString(ch,start,len),aug());
            } catch( XNIException e ) {
                throw toSAXException(e);
            }
        }

        public void ignorableWhitespace(char[] ch, int start, int len) throws SAXException {
            try {
                handler().ignorableWhitespace(new XMLString(ch,start,len),aug());
            } catch( XNIException e ) {
                throw toSAXException(e);
            }
        }

        public void startElement(String uri, String localName, String qname, Attributes atts) throws SAXException {
            try {
                updateAttributes(atts);
                handler().startElement(toQName(uri,localName,qname), fCurrentAttributes, elementAug());
            } catch( XNIException e ) {
                throw toSAXException(e);
            }
        }

        public void endElement(String uri, String localName, String qname) throws SAXException {
            try {
                handler().endElement(toQName(uri,localName,qname),aug());
            } catch( XNIException e ) {
                throw toSAXException(e);
            }
        }

        private Augmentations elementAug() {
            Augmentations aug = aug();
            /** aug.putItem(Constants.TYPEINFO,typeInfoProvider.getElementTypeInfo()); **/
            return aug;
        }


        /**
         * Gets the {@link Augmentations} that should be associated with
         * the current event.
         * <p>
         *  获取应与当前事件相关联的{@link扩充功能}。
         * 
         */
        private Augmentations aug() {
            if( fCurrentAug!=null ) {
                Augmentations r = fCurrentAug;
                fCurrentAug = null; // we "consumed" this augmentation.
                return r;
            }
            fAugmentations.removeAllItems();
            return fAugmentations;
        }

        /**
         * Get the handler to which we should send events.
         * <p>
         *  获取我们应该向其发送事件的处理程序。
         * 
         */
        private XMLDocumentHandler handler() {
            return JAXPValidatorComponent.this.getDocumentHandler();
        }

        /**
         * Converts the {@link XNIException} received from a downstream
         * component to a {@link SAXException}.
         * <p>
         *  将从下游组件接收到的{@link XNIException}转换为{@link SAXException}。
         * 
         */
        private SAXException toSAXException( XNIException xe ) {
            Exception e = xe.getException();
            if( e==null )   e = xe;
            if( e instanceof SAXException )  return (SAXException)e;
            return new SAXException(e);
        }

        /**
         * Creates a proper {@link QName} object from 3 parts.
         * <p>
         * This method does the symbolization.
         * <p>
         *  从3个部分创建一个正确的{@link QName}对象。
         * <p>
         *  此方法执行符号化。
         * 
         */
        private QName toQName( String uri, String localName, String qname ) {
            String prefix = null;
            int idx = qname.indexOf(':');
            if( idx>0 )
                prefix = symbolize(qname.substring(0,idx));

            localName = symbolize(localName);
            qname = symbolize(qname);
            uri = symbolize(uri);

            // notify handlers
            fQName.setValues(prefix, localName, qname, uri);
            return fQName;
        }
    }

    /**
     * Converts {@link XNI} events to {@link ContentHandler} events.
     *
     * <p>
     * Deriving from {@link DefaultXMLDocumentHandler}
     * to reuse its default {@link com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler}
     * implementation.
     *
     * <p>
     *  将{@link XNI}事件转换为{@link ContentHandler}事件。
     * 
     * <p>
     *  导出{@link DefaultXMLDocumentHandler}以重用其默认的{@link com.sun.org.apache.xerces.internal.xni.XMLDocumentHandler}
     * 实现。
     * 
     * 
     * @author Kohsuke Kawaguchi (kohsuke.kawaguchi@sun.com)
     */
    private final class XNI2SAX extends DefaultXMLDocumentHandler {

        private ContentHandler fContentHandler;

        private String fVersion;

        /** Namespace context */
        protected NamespaceContext fNamespaceContext;

        /**
         * For efficiency, we reuse one instance.
         * <p>
         *  为了效率,我们重用一个实例。
         * 
         */
        private final AttributesProxy fAttributesProxy = new AttributesProxy(null);

        public void setContentHandler( ContentHandler handler ) {
            this.fContentHandler = handler;
        }

        public ContentHandler getContentHandler() {
            return fContentHandler;
        }


        public void xmlDecl(String version, String encoding, String standalone, Augmentations augs) throws XNIException {
            this.fVersion = version;
        }

        public void startDocument(XMLLocator locator, String encoding, NamespaceContext namespaceContext, Augmentations augs) throws XNIException {
            fNamespaceContext = namespaceContext;
            fContentHandler.setDocumentLocator(new LocatorProxy(locator));
            try {
                fContentHandler.startDocument();
            } catch (SAXException e) {
                throw new XNIException(e);
            }
        }

        public void endDocument(Augmentations augs) throws XNIException {
            try {
                fContentHandler.endDocument();
            } catch (SAXException e) {
                throw new XNIException(e);
            }
        }

        public void processingInstruction(String target, XMLString data, Augmentations augs) throws XNIException {
            try {
                fContentHandler.processingInstruction(target,data.toString());
            } catch (SAXException e) {
                throw new XNIException(e);
            }
        }

        public void startElement(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {
            try {
                // start namespace prefix mappings
                int count = fNamespaceContext.getDeclaredPrefixCount();
                if (count > 0) {
                    String prefix = null;
                    String uri = null;
                    for (int i = 0; i < count; i++) {
                        prefix = fNamespaceContext.getDeclaredPrefixAt(i);
                        uri = fNamespaceContext.getURI(prefix);
                        fContentHandler.startPrefixMapping(prefix, (uri == null)?"":uri);
                    }
                }

                String uri = element.uri != null ? element.uri : "";
                String localpart = element.localpart;
                fAttributesProxy.setAttributes(attributes);
                fContentHandler.startElement(uri, localpart, element.rawname, fAttributesProxy);
            } catch( SAXException e ) {
                throw new XNIException(e);
            }
        }

        public void endElement(QName element, Augmentations augs) throws XNIException {
            try {
                String uri = element.uri != null ? element.uri : "";
                String localpart = element.localpart;
                fContentHandler.endElement(uri, localpart, element.rawname);

                // send endPrefixMapping events
                int count = fNamespaceContext.getDeclaredPrefixCount();
                if (count > 0) {
                    for (int i = 0; i < count; i++) {
                        fContentHandler.endPrefixMapping(fNamespaceContext.getDeclaredPrefixAt(i));
                    }
                }
            } catch( SAXException e ) {
                throw new XNIException(e);
            }
        }

        public void emptyElement(QName element, XMLAttributes attributes, Augmentations augs) throws XNIException {
            startElement(element,attributes,augs);
            endElement(element,augs);
        }

        public void characters(XMLString text, Augmentations augs) throws XNIException {
            try {
                fContentHandler.characters(text.ch,text.offset,text.length);
            } catch (SAXException e) {
                throw new XNIException(e);
            }
        }

        public void ignorableWhitespace(XMLString text, Augmentations augs) throws XNIException {
            try {
                fContentHandler.ignorableWhitespace(text.ch,text.offset,text.length);
            } catch (SAXException e) {
                throw new XNIException(e);
            }
        }
    }

    private static final class DraconianErrorHandler implements ErrorHandler {

        /**
         * Singleton instance.
         * <p>
         *  单例实例。
         * 
         */
        private static final DraconianErrorHandler ERROR_HANDLER_INSTANCE
            = new DraconianErrorHandler();

        private DraconianErrorHandler() {}

        /** Returns the one and only instance of this error handler. */
        public static DraconianErrorHandler getInstance() {
            return ERROR_HANDLER_INSTANCE;
        }

        /** Warning: Ignore. */
        public void warning(SAXParseException e) throws SAXException {
            // noop
        }

        /** Error: Throws back SAXParseException. */
        public void error(SAXParseException e) throws SAXException {
            throw e;
        }

        /** Fatal Error: Throws back SAXParseException. */
        public void fatalError(SAXParseException e) throws SAXException {
            throw e;
        }

    } // DraconianErrorHandler


    /**
     * Compares the given {@link Attributes} with {@link #fCurrentAttributes}
     * and update the latter accordingly.
     * <p>
     *  将给定的{@link属性}与{@link #fCurrentAttributes}进行比较,并相应地更新后者。
     * 
     */
    private void updateAttributes( Attributes atts ) {
        int len = atts.getLength();
        for( int i=0; i<len; i++ ) {
            String aqn = atts.getQName(i);
            int j = fCurrentAttributes.getIndex(aqn);
            String av = atts.getValue(i);
            if(j==-1) {
                // newly added attribute. add to the current attribute list.

                String prefix;
                int idx = aqn.indexOf(':');
                if( idx<0 ) {
                    prefix = null;
                } else {
                    prefix = symbolize(aqn.substring(0,idx));
                }

                j = fCurrentAttributes.addAttribute(
                    new QName(
                        prefix,
                        symbolize(atts.getLocalName(i)),
                        symbolize(aqn),
                        symbolize(atts.getURI(i))),
                    atts.getType(i),av);
            } else {
                // the attribute is present.
                if( !av.equals(fCurrentAttributes.getValue(j)) ) {
                    // but the value was changed.
                    fCurrentAttributes.setValue(j,av);
                }
            }

            /** Augmentations augs = fCurrentAttributes.getAugmentations(j);
            augs.putItem( Constants.TYPEINFO,
                typeInfoProvider.getAttributeTypeInfo(i) );
            augs.putItem( Constants.ID_ATTRIBUTE,
            /* <p>
            /*  augnsputItem(Constants.TYPEINFO,typeInfoProvider.getAttributeTypeInfo(i)); augs.putItem(Constants.ID
            /* _ATTRIBUTE,。
            /* 
            /* 
                typeInfoProvider.isIdAttribute(i)?Boolean.TRUE:Boolean.FALSE ); **/
        }
    }

    private String symbolize( String s ) {
        return fSymbolTable.addSymbol(s);
    }


    /**
     * {@link TypeInfoProvider} that returns no info.
     * <p>
     */
    private static final TypeInfoProvider noInfoProvider = new TypeInfoProvider() {
        public TypeInfo getElementTypeInfo() {
            return null;
        }
        public TypeInfo getAttributeTypeInfo(int index) {
            return null;
        }
        public TypeInfo getAttributeTypeInfo(String attributeQName) {
            return null;
        }
        public TypeInfo getAttributeTypeInfo(String attributeUri, String attributeLocalName) {
            return null;
        }
        public boolean isIdAttribute(int index) {
            return false;
        }
        public boolean isSpecified(int index) {
            return false;
        }
    };

    //
    //
    // XMLComponent implementation.
    //
    //

    // no property/feature supported
    public String[] getRecognizedFeatures() {
        return null;
    }

    public void setFeature(String featureId, boolean state) throws XMLConfigurationException {
    }

    public String[] getRecognizedProperties() {
        return new String[]{ENTITY_MANAGER, ERROR_REPORTER, SYMBOL_TABLE};
    }

    public void setProperty(String propertyId, Object value) throws XMLConfigurationException {
    }

    public Boolean getFeatureDefault(String featureId) {
        return null;
    }

    public Object getPropertyDefault(String propertyId) {
        return null;
    }

}
