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
 * $Id: TemplatesImpl.java,v 1.8 2007/03/26 20:12:27 spericas Exp $
 * <p>
 *  $ Id：TemplatesImpl.java,v 1.8 2007/03/26 20:12:27 spericas Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.trax;

import com.sun.org.apache.xalan.internal.XalanConstants;
import com.sun.org.apache.xalan.internal.utils.ObjectFactory;
import com.sun.org.apache.xalan.internal.utils.SecuritySupport;
import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.Translet;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xalan.internal.xsltc.runtime.Hashtable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;
import java.util.Properties;
import javax.xml.XMLConstants;
import javax.xml.transform.Templates;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.URIResolver;

/**
/* <p>
/* 
 * @author Morten Jorgensen
 * @author G. Todd Millerj
 * @author Jochen Cordes <Jochen.Cordes@t-online.de>
 * @author Santiago Pericas-Geertsen
 */
public final class TemplatesImpl implements Templates, Serializable {
    static final long serialVersionUID = 673094361519270707L;
    public final static String DESERIALIZE_TRANSLET = "jdk.xml.enableTemplatesImplDeserialization";

    /**
     * Name of the superclass of all translets. This is needed to
     * determine which, among all classes comprising a translet,
     * is the main one.
     * <p>
     *  所有translets的超类名称。这需要确定在包括运输孔的所有类中哪些是主要的。
     * 
     */
    private static String ABSTRACT_TRANSLET
        = "com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet";

    /**
     * Name of the main class or default name if unknown.
     * <p>
     *  主类名称或默认名称(如果未知)。
     * 
     */
    private String _name = null;

    /**
     * Contains the actual class definition for the translet class and
     * any auxiliary classes.
     * <p>
     *  包含translet类和任何辅助类的实际类定义。
     * 
     */
    private byte[][] _bytecodes = null;

    /**
     * Contains the translet class definition(s). These are created when
     * this Templates is created or when it is read back from disk.
     * <p>
     *  包含translet类定义。这些是在创建此模板时或从磁盘读回时创建的。
     * 
     */
    private Class[] _class = null;

    /**
     * The index of the main translet class in the arrays _class[] and
     * _bytecodes.
     * <p>
     *  数组_class []和_bytecodes中的主translet类的索引。
     * 
     */
    private int _transletIndex = -1;

    /**
     * Contains the list of auxiliary class definitions.
     * <p>
     *  包含辅助类定义的列表。
     * 
     */
    private Hashtable _auxClasses = null;

    /**
     * Output properties of this translet.
     * <p>
     *  这个translet的输出属性。
     * 
     */
    private Properties _outputProperties;

    /**
     * Number of spaces to add for output indentation.
     * <p>
     *  要为输出缩进添加的空格数。
     * 
     */
    private int _indentNumber;

    /**
     * This URIResolver is passed to all Transformers.
     * Declaring it transient to fix bug 22438
     * <p>
     *  这个URIResolver被传递给所有的变形金刚。声明它临时修复错误22438
     * 
     */
    private transient URIResolver _uriResolver = null;

    /**
     * Cache the DTM for the stylesheet in a thread local variable,
     * which is used by the document('') function.
     * Use ThreadLocal because a DTM cannot be shared between
     * multiple threads.
     * Declaring it transient to fix bug 22438
     * <p>
     * 在线程局部变量中缓存样式表的DTM,由文档('')函数使用。使用ThreadLocal,因为DTM不能在多个线程之间共享。声明它临时修复错误22438
     * 
     */
    private transient ThreadLocal _sdom = new ThreadLocal();

    /**
     * A reference to the transformer factory that this templates
     * object belongs to.
     * <p>
     *  对此模板对象所属的变压器工厂的引用。
     * 
     */
    private transient TransformerFactoryImpl _tfactory = null;

    private boolean _useServicesMechanism;

    /**
     * protocols allowed for external references set by the stylesheet processing instruction, Import and Include element.
     * <p>
     *  允许由样式表处理指令设置的外部引用的协议,Import和Include元素。
     * 
     */
    private String _accessExternalStylesheet = XalanConstants.EXTERNAL_ACCESS_DEFAULT;

    static final class TransletClassLoader extends ClassLoader {

        private final Map<String,Class> _loadedExternalExtensionFunctions;

        TransletClassLoader(ClassLoader parent) {
            super(parent);
            _loadedExternalExtensionFunctions = null;
        }

        TransletClassLoader(ClassLoader parent,Map<String, Class> mapEF) {
            super(parent);
            _loadedExternalExtensionFunctions = mapEF;
        }

        public Class<?> loadClass(String name) throws ClassNotFoundException {
            Class<?> ret = null;
            // The _loadedExternalExtensionFunctions will be empty when the
            // SecurityManager is not set and the FSP is turned off
            if (_loadedExternalExtensionFunctions != null) {
                ret = _loadedExternalExtensionFunctions.get(name);
            }
            if (ret == null) {
                ret = super.loadClass(name);
            }
            return ret;
        }

        /**
         * Access to final protected superclass member from outer class.
         * <p>
         *  从外部类访问最终受保护的超类成员。
         * 
         */
        Class defineClass(final byte[] b) {
            return defineClass(null, b, 0, b.length);
        }
    }


    /**
     * Create an XSLTC template object from the bytecodes.
     * The bytecodes for the translet and auxiliary classes, plus the name of
     * the main translet class, must be supplied.
     * <p>
     *  从字节码创建XSLTC模板对象。必须提供translet和辅助类的字节码,以及主translet类的名称。
     * 
     */
    protected TemplatesImpl(byte[][] bytecodes, String transletName,
        Properties outputProperties, int indentNumber,
        TransformerFactoryImpl tfactory)
    {
        _bytecodes = bytecodes;
        init(transletName, outputProperties, indentNumber, tfactory);
    }

    /**
     * Create an XSLTC template object from the translet class definition(s).
     * <p>
     *  从translet类定义创建一个XSLTC模板对象。
     * 
     */
    protected TemplatesImpl(Class[] transletClasses, String transletName,
        Properties outputProperties, int indentNumber,
        TransformerFactoryImpl tfactory)
    {
        _class     = transletClasses;
        _transletIndex = 0;
        init(transletName, outputProperties, indentNumber, tfactory);
    }

    private void init(String transletName,
        Properties outputProperties, int indentNumber,
        TransformerFactoryImpl tfactory) {
        _name      = transletName;
        _outputProperties = outputProperties;
        _indentNumber = indentNumber;
        _tfactory = tfactory;
        _useServicesMechanism = tfactory.useServicesMechnism();
        _accessExternalStylesheet = (String) tfactory.getAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET);
    }
    /**
     * Need for de-serialization, see readObject().
     * <p>
     *  需要反序列化,请参阅readObject()。
     * 
     */
    public TemplatesImpl() { }

    /**
     *  Overrides the default readObject implementation since we decided
     *  it would be cleaner not to serialize the entire tranformer
     *  factory.  [ ref bugzilla 12317 ]
     *  We need to check if the user defined class for URIResolver also
     *  implemented Serializable
     *  if yes then we need to deserialize the URIResolver
     *  Fix for bugzilla bug 22438
     * <p>
     *  覆盖默认的readObject实现,因为我们决定它将更清洁,不是序列化整个tranformer工厂。
     *  [ref bugzilla 12317]我们需要检查URIResolver的用户定义类是否实现Serializable如果是的,那么我们需要反序列化URIResolver修复bugzilla错误224
     * 38。
     *  覆盖默认的readObject实现,因为我们决定它将更清洁,不是序列化整个tranformer工厂。
     * 
     */
    private void  readObject(ObjectInputStream is)
      throws IOException, ClassNotFoundException
    {
        SecurityManager security = System.getSecurityManager();
        if (security != null){
            String temp = SecuritySupport.getSystemProperty(DESERIALIZE_TRANSLET);
            if (temp == null || !(temp.length()==0 || temp.equalsIgnoreCase("true"))) {
                ErrorMsg err = new ErrorMsg(ErrorMsg.DESERIALIZE_TRANSLET_ERR);
                throw new UnsupportedOperationException(err.toString());
            }
        }

        is.defaultReadObject();
        if (is.readBoolean()) {
            _uriResolver = (URIResolver) is.readObject();
        }

        _tfactory = new TransformerFactoryImpl();
    }


    /**
     *  This is to fix bugzilla bug 22438
     *  If the user defined class implements URIResolver and Serializable
     *  then we want it to get serialized
     * <p>
     *  这是修复bugzilla错误22438如果用户定义类实现URIResolver和Serializable,那么我们希望它被序列化
     * 
     */
    private void writeObject(ObjectOutputStream os)
        throws IOException, ClassNotFoundException {
        os.defaultWriteObject();
        if (_uriResolver instanceof Serializable) {
            os.writeBoolean(true);
            os.writeObject((Serializable) _uriResolver);
        }
        else {
            os.writeBoolean(false);
        }
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
     * Store URIResolver needed for Transformers.
     * <p>
     *  存储变压器所需的URIResolver。
     * 
     */
    public synchronized void setURIResolver(URIResolver resolver) {
        _uriResolver = resolver;
    }

    /**
     * The TransformerFactory must pass us the translet bytecodes using this
     * method before we can create any translet instances
     *
     * Note: This method is private for security reasons. See
     * CR 6537898. When merging with Apache, we must ensure
     * that the privateness of this method is maintained (that
     * is why it wasn't removed).
     * <p>
     *  TransformerFactory必须在我们创建任何translet实例之前使用这个方法传递translet字节码
     * 
     * 注意：出于安全原因,此方法是私有的。参见CR 6537898.当与Apache合并时,我们必须确保保持此方法的私有性(这就是为什么它不被删除)。
     * 
     */
    private synchronized void setTransletBytecodes(byte[][] bytecodes) {
        _bytecodes = bytecodes;
    }

    /**
     * Returns the translet bytecodes stored in this template
     *
     * Note: This method is private for security reasons. See
     * CR 6537898. When merging with Apache, we must ensure
     * that the privateness of this method is maintained (that
     * is why it wasn't removed).
     * <p>
     *  返回此模板中存储的translet字节码
     * 
     *  注意：出于安全原因,此方法是私有的。参见CR 6537898.当与Apache合并时,我们必须确保保持此方法的私有性(这就是为什么它不被删除)。
     * 
     */
    private synchronized byte[][] getTransletBytecodes() {
        return _bytecodes;
    }

    /**
     * Returns the translet bytecodes stored in this template
     *
     * Note: This method is private for security reasons. See
     * CR 6537898. When merging with Apache, we must ensure
     * that the privateness of this method is maintained (that
     * is why it wasn't removed).
     * <p>
     *  返回此模板中存储的translet字节码
     * 
     *  注意：出于安全原因,此方法是私有的。参见CR 6537898.当与Apache合并时,我们必须确保保持此方法的私有性(这就是为什么它不被删除)。
     * 
     */
    private synchronized Class[] getTransletClasses() {
        try {
            if (_class == null) defineTransletClasses();
        }
        catch (TransformerConfigurationException e) {
            // Falls through
        }
        return _class;
    }

    /**
     * Returns the index of the main class in array of bytecodes
     * <p>
     *  返回字节码数组中主类的索引
     * 
     */
    public synchronized int getTransletIndex() {
        try {
            if (_class == null) defineTransletClasses();
        }
        catch (TransformerConfigurationException e) {
            // Falls through
        }
        return _transletIndex;
    }

    /**
     * The TransformerFactory should call this method to set the translet name
     * <p>
     *  TransformerFactory应该调用此方法来设置translet名称
     * 
     */
    protected synchronized void setTransletName(String name) {
        _name = name;
    }

    /**
     * Returns the name of the main translet class stored in this template
     * <p>
     *  返回此模板中存储的主translet类的名称
     * 
     */
    protected synchronized String getTransletName() {
        return _name;
    }

    /**
     * Defines the translet class and auxiliary classes.
     * Returns a reference to the Class object that defines the main class
     * <p>
     *  定义translet类和辅助类。返回对定义主类的Class对象的引用
     * 
     */
    private void defineTransletClasses()
        throws TransformerConfigurationException {

        if (_bytecodes == null) {
            ErrorMsg err = new ErrorMsg(ErrorMsg.NO_TRANSLET_CLASS_ERR);
            throw new TransformerConfigurationException(err.toString());
        }

        TransletClassLoader loader = (TransletClassLoader)
            AccessController.doPrivileged(new PrivilegedAction() {
                public Object run() {
                    return new TransletClassLoader(ObjectFactory.findClassLoader(),_tfactory.getExternalExtensionsMap());
                }
            });

        try {
            final int classCount = _bytecodes.length;
            _class = new Class[classCount];

            if (classCount > 1) {
                _auxClasses = new Hashtable();
            }

            for (int i = 0; i < classCount; i++) {
                _class[i] = loader.defineClass(_bytecodes[i]);
                final Class superClass = _class[i].getSuperclass();

                // Check if this is the main class
                if (superClass.getName().equals(ABSTRACT_TRANSLET)) {
                    _transletIndex = i;
                }
                else {
                    _auxClasses.put(_class[i].getName(), _class[i]);
                }
            }

            if (_transletIndex < 0) {
                ErrorMsg err= new ErrorMsg(ErrorMsg.NO_MAIN_TRANSLET_ERR, _name);
                throw new TransformerConfigurationException(err.toString());
            }
        }
        catch (ClassFormatError e) {
            ErrorMsg err = new ErrorMsg(ErrorMsg.TRANSLET_CLASS_ERR, _name);
            throw new TransformerConfigurationException(err.toString());
        }
        catch (LinkageError e) {
            ErrorMsg err = new ErrorMsg(ErrorMsg.TRANSLET_OBJECT_ERR, _name);
            throw new TransformerConfigurationException(err.toString());
        }
    }

    /**
     * This method generates an instance of the translet class that is
     * wrapped inside this Template. The translet instance will later
     * be wrapped inside a Transformer object.
     * <p>
     *  此方法生成包含在此模板中的translet类的实例。 translet实例稍后将被包装在Transformer对象中。
     * 
     */
    private Translet getTransletInstance()
        throws TransformerConfigurationException {
        try {
            if (_name == null) return null;

            if (_class == null) defineTransletClasses();

            // The translet needs to keep a reference to all its auxiliary
            // class to prevent the GC from collecting them
            AbstractTranslet translet = (AbstractTranslet) _class[_transletIndex].newInstance();
            translet.postInitialization();
            translet.setTemplates(this);
            translet.setServicesMechnism(_useServicesMechanism);
            translet.setAllowedProtocols(_accessExternalStylesheet);
            if (_auxClasses != null) {
                translet.setAuxiliaryClasses(_auxClasses);
            }

            return translet;
        }
        catch (InstantiationException e) {
            ErrorMsg err = new ErrorMsg(ErrorMsg.TRANSLET_OBJECT_ERR, _name);
            throw new TransformerConfigurationException(err.toString());
        }
        catch (IllegalAccessException e) {
            ErrorMsg err = new ErrorMsg(ErrorMsg.TRANSLET_OBJECT_ERR, _name);
            throw new TransformerConfigurationException(err.toString());
        }
    }

    /**
     * Implements JAXP's Templates.newTransformer()
     *
     * <p>
     *  实现JAXP的Templates.newTransformer()
     * 
     * 
     * @throws TransformerConfigurationException
     */
    public synchronized Transformer newTransformer()
        throws TransformerConfigurationException
    {
        TransformerImpl transformer;

        transformer = new TransformerImpl(getTransletInstance(), _outputProperties,
            _indentNumber, _tfactory);

        if (_uriResolver != null) {
            transformer.setURIResolver(_uriResolver);
        }

        if (_tfactory.getFeature(XMLConstants.FEATURE_SECURE_PROCESSING)) {
            transformer.setSecureProcessing(true);
        }
        return transformer;
    }

    /**
     * Implements JAXP's Templates.getOutputProperties(). We need to
     * instanciate a translet to get the output settings, so
     * we might as well just instanciate a Transformer and use its
     * implementation of this method.
     * <p>
     *  实现JAXP的Templates.getOutputProperties()。我们需要实例化一个translet来获得输出设置,所以我们可能只是实例化Transformer并使用它的实现方法。
     * 
     */
    public synchronized Properties getOutputProperties() {
        try {
            return newTransformer().getOutputProperties();
        }
        catch (TransformerConfigurationException e) {
            return null;
        }
    }

    /**
     * Return the thread local copy of the stylesheet DOM.
     * <p>
     *  返回样式表DOM的线程本地副本。
     * 
     */
    public DOM getStylesheetDOM() {
        return (DOM)_sdom.get();
    }

    /**
     * Set the thread local copy of the stylesheet DOM.
     * <p>
     * 设置样式表DOM的线程本地副本。
     */
    public void setStylesheetDOM(DOM sdom) {
        _sdom.set(sdom);
    }
}
