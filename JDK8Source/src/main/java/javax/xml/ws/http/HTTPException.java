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

package javax.xml.ws.http;


/** The <code>HTTPException</code> exception represents a
 *  XML/HTTP fault.
 *
 *  <p>Since there is no standard format for faults or exceptions
 *  in XML/HTTP messaging, only the HTTP status code is captured.
 *
 * <p>
 *  XML / HTTP故障。
 * 
 *  <p>由于XML / HTTP消息传递中没有故障或异常的标准格式,因此只捕获HTTP状态代码。
 * 
 *  @since JAX-WS 2.0
**/
public class HTTPException extends javax.xml.ws.ProtocolException  {

  private int statusCode;

  /** Constructor for the HTTPException
  /* <p>
  /* 
  /* 
   *  @param statusCode   <code>int</code> for the HTTP status code
  **/
  public HTTPException(int statusCode) {
    super();
    this.statusCode = statusCode;
  }

  /** Gets the HTTP status code.
   *
   * <p>
   * 
   *  @return HTTP status code
  **/
  public int getStatusCode() {
    return statusCode;
  }
}
