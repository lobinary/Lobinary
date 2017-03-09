/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2010, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.ws.soap;

import javax.xml.soap.SOAPFault;

/** The <code>SOAPFaultException</code> exception represents a
 *  SOAP 1.1 or 1.2 fault.
 *
 *  <p>A <code>SOAPFaultException</code> wraps a SAAJ <code>SOAPFault</code>
 *  that manages the SOAP-specific representation of faults.
 *  The <code>createFault</code> method of
 *  <code>javax.xml.soap.SOAPFactory</code> may be used to create an instance
 *  of <code>javax.xml.soap.SOAPFault</code> for use with the
 *  constructor. <code>SOAPBinding</code> contains an accessor for the
 *  <code>SOAPFactory</code> used by the binding instance.
 *
 *  <p>Note that the value of <code>getFault</code> is the only part of the
 *  exception used when searializing a SOAP fault.
 *
 *  <p>Refer to the SOAP specification for a complete
 *  description of SOAP faults.
 *
 * <p>
 *  SOAP 1.1或1.2故障。
 * 
 *  <p> <code> SOAPFaultException </code>包装管理故障的SOAP特定表示的SAAJ <code> SOAPFault </code>。
 *  <code> javax.xml.soap.SOAPFactory </code>的<code> createFault </code>方法可用于创建<code> javax.xml.soap.SOA
 * PFault </code>的实例构造函数。
 *  <p> <code> SOAPFaultException </code>包装管理故障的SOAP特定表示的SAAJ <code> SOAPFault </code>。
 *  <code> SOAPBinding </code>包含绑定实例使用的<code> SOAPFactory </code>的访问器。
 * 
 *  @see javax.xml.soap.SOAPFault
 *  @see javax.xml.ws.soap.SOAPBinding#getSOAPFactory
 *  @see javax.xml.ws.ProtocolException
 *
 *  @since JAX-WS 2.0
 **/
public class SOAPFaultException extends javax.xml.ws.ProtocolException  {

    private SOAPFault fault;

    /** Constructor for SOAPFaultException
    /* <p>
    /* 
    /*  <p>请注意,<code> getFault </code>的值是在截断SOAP故障时使用的异常的唯一部分。
    /* 
    /*  <p>有关SOAP故障的完整说明,请参阅SOAP规范。
    /* 
     *  @param fault   <code>SOAPFault</code> representing the fault
     *
     *  @see javax.xml.soap.SOAPFactory#createFault
     **/
    public SOAPFaultException(SOAPFault fault) {
        super(fault.getFaultString());
        this.fault = fault;
    }

    /** Gets the embedded <code>SOAPFault</code> instance.
     *
     * <p>
     * 
     * 
     *  @return <code>javax.xml.soap.SOAPFault</code> SOAP
     *          fault element
     **/
    public javax.xml.soap.SOAPFault getFault() {
        return this.fault;
    }
}
