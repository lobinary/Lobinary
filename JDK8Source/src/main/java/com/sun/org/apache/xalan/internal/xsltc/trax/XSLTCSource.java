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
 * $Id: XSLTCSource.java,v 1.2.4.1 2005/09/06 12:43:28 pvedula Exp $
 * <p>
 *  $ Id：XSLTCSource.java,v 1.2.4.1 2005/09/06 12:43:28 pvedula Exp $
 * 
 */

package com.sun.org.apache.xalan.internal.xsltc.trax;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.StripFilter;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.ErrorMsg;
import com.sun.org.apache.xalan.internal.xsltc.dom.DOMWSFilter;
import com.sun.org.apache.xalan.internal.xsltc.dom.SAXImpl;
import com.sun.org.apache.xalan.internal.xsltc.dom.XSLTCDTMManager;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;

import org.xml.sax.SAXException;

/**
/* <p>
/* 
 * @author Morten Jorgensen
 */
public final class XSLTCSource implements Source {

    private String     _systemId = null;
    private Source     _source   = null;
    private ThreadLocal _dom     = new ThreadLocal();

    /**
     * Create a new XSLTC-specific source from a system ID
     * <p>
     *  从系统ID创建新的特定于XSLTC的源
     * 
     */
    public XSLTCSource(String systemId)
    {
        _systemId = systemId;
    }

    /**
     * Create a new XSLTC-specific source from a JAXP Source
     * <p>
     *  从JAXP源创建一个新的XSLTC特定源
     * 
     */
    public XSLTCSource(Source source)
    {
        _source = source;
    }

    /**
     * Implements javax.xml.transform.Source.setSystemId()
     * Set the system identifier for this Source.
     * This Source can get its input either directly from a file (in this case
     * it will instanciate and use a JAXP parser) or it can receive it through
     * ContentHandler/LexicalHandler interfaces.
     * <p>
     *  实现javax.xml.transform.Source.setSystemId()设置此源的系统标识符。
     * 这个源可以直接从一个文件(在这种情况下,它将实例化并使用一个JAXP解析器)获取它的输入,或者它可以通过ContentHandler / LexicalHandler接口接收它。
     * 
     * 
     * @param systemId The system Id for this Source
     */
    public void setSystemId(String systemId) {
        _systemId = systemId;
        if (_source != null) {
            _source.setSystemId(systemId);
        }
    }

    /**
     * Implements javax.xml.transform.Source.getSystemId()
     * Get the system identifier that was set with setSystemId.
     * <p>
     *  实现javax.xml.transform.Source.getSystemId()获取使用setSystemId设置的系统标识符。
     * 
     * 
     * @return The system identifier that was set with setSystemId,
     *         or null if setSystemId was not called.
     */
    public String getSystemId() {
        if (_source != null) {
            return _source.getSystemId();
        }
        else {
            return(_systemId);
        }
    }

    /**
     * Internal interface which returns a DOM for a given DTMManager and translet.
     * <p>
     *  内部接口,返回给定DTMManager和translet的DOM。
     */
    protected DOM getDOM(XSLTCDTMManager dtmManager, AbstractTranslet translet)
        throws SAXException
    {
        SAXImpl idom = (SAXImpl)_dom.get();

        if (idom != null) {
            if (dtmManager != null) {
                idom.migrateTo(dtmManager);
            }
        }
        else {
            Source source = _source;
            if (source == null) {
                if (_systemId != null && _systemId.length() > 0) {
                    source = new StreamSource(_systemId);
                }
                else {
                    ErrorMsg err = new ErrorMsg(ErrorMsg.XSLTC_SOURCE_ERR);
                    throw new SAXException(err.toString());
                }
            }

            DOMWSFilter wsfilter = null;
            if (translet != null && translet instanceof StripFilter) {
                wsfilter = new DOMWSFilter(translet);
            }

            boolean hasIdCall = (translet != null) ? translet.hasIdCall() : false;

            if (dtmManager == null) {
                dtmManager = XSLTCDTMManager.newInstance();
            }

            idom = (SAXImpl)dtmManager.getDTM(source, true, wsfilter, false, false, hasIdCall);

            String systemId = getSystemId();
            if (systemId != null) {
                idom.setDocumentURI(systemId);
            }
            _dom.set(idom);
        }
        return idom;
    }

}
