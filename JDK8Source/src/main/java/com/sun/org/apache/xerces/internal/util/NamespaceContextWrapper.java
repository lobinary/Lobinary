/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2006, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.org.apache.xerces.internal.util;

import java.util.Enumeration;
import java.util.Vector;
import javax.xml.namespace.NamespaceContext;

/**
 * Writing a wrapper to re-use most of the namespace functionality
 * already provided by NamespaceSupport, which implements NamespaceContext
 * from XNI. It would be good if we can change the XNI NamespaceContext
 * interface to implement the JAXP NamespaceContext interface.
 *
 * Note that NamespaceSupport assumes the use of symbols. Since this class
 * can be exposed to the application, we must intern all Strings before
 * calling NamespaceSupport methods.
 *
 * <p>
 *  编写包装器以重用NamespaceSupport已经提供的大多数命名空间功能,该功能从XNI实现NamespaceContext。
 * 如果我们可以改变XNI NamespaceContext接口来实现JAXP NamespaceContext接口,这将是很好的。
 * 
 *  注意,NamespaceSupport假定使用符号。由于这个类可以暴露给应用程序,我们必须在调用NamespaceSupport方法之前实现所有Strings。
 * 
 * 
 * @author  Neeraj Bajaj, Sun Microsystems, inc.
 * @author Santiago.PericasGeertsen@sun.com
 *
 */
public class NamespaceContextWrapper implements NamespaceContext {

    private com.sun.org.apache.xerces.internal.xni.NamespaceContext fNamespaceContext;

    public NamespaceContextWrapper(NamespaceSupport namespaceContext) {
        fNamespaceContext = namespaceContext ;
    }

    public String getNamespaceURI(String prefix) {
        if (prefix == null) {
            throw new IllegalArgumentException("Prefix can't be null");
        }
        return fNamespaceContext.getURI(prefix.intern());
    }

    public String getPrefix(String namespaceURI) {
        if (namespaceURI == null) {
            throw new IllegalArgumentException("URI can't be null.");
        }
        return fNamespaceContext.getPrefix(namespaceURI.intern());
    }

    /**
     * TODO: Namespace doesn't give information giving multiple prefixes for
     * the same namespaceURI.
     * <p>
     *  TODO：命名空间不给出相同namespaceURI的多个前缀的信息。
     * 
     */
    public java.util.Iterator getPrefixes(String namespaceURI) {
        if (namespaceURI == null) {
            throw new IllegalArgumentException("URI can't be null.");
        }
        else {
            Vector vector =
                ((NamespaceSupport) fNamespaceContext).getPrefixes(namespaceURI.intern());
            return vector.iterator();
        }
    }

    /**
     * This method supports all functions in the NamespaceContext utility class
     * <p>
     *  此方法支持NamespaceContext实用程序类中的所有函数
     */
    public com.sun.org.apache.xerces.internal.xni.NamespaceContext getNamespaceContext() {
        return fNamespaceContext;
    }

}
