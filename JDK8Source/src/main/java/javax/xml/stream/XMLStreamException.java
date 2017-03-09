/***** Lobxxx Translate Finished ******/
/*
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

/*
 * Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * <p>
 *  Oracle Corporation的版权所有(c)2009。版权所有。
 * 
 */

package javax.xml.stream;

/**
 * The base exception for unexpected processing errors.  This Exception
 * class is used to report well-formedness errors as well as unexpected
 * processing conditions.
 * <p>
 *  意外处理错误的基本异常。此异常类用于报告良好的错误以及意外的处理条件。
 * 
 * 
 * @version 1.0
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @since 1.6
 */

public class XMLStreamException extends Exception {

  protected Throwable nested;
  protected Location location;

  /**
   * Default constructor
   * <p>
   *  默认构造函数
   * 
   */
  public XMLStreamException(){
    super();
  }

  /**
   * Construct an exception with the assocated message.
   *
   * <p>
   *  使用已合并的消息构造异常。
   * 
   * 
   * @param msg the message to report
   */
  public XMLStreamException(String msg) {
    super(msg);
  }

  /**
   * Construct an exception with the assocated exception
   *
   * <p>
   *  使用assocated异常构造异常
   * 
   * 
   * @param th a nested exception
   */
  public XMLStreamException(Throwable th) {
      super(th);
    nested = th;
  }

  /**
   * Construct an exception with the assocated message and exception
   *
   * <p>
   *  使用关联的消息和异常构造异常
   * 
   * 
   * @param th a nested exception
   * @param msg the message to report
   */
  public XMLStreamException(String msg, Throwable th) {
    super(msg, th);
    nested = th;
  }

  /**
   * Construct an exception with the assocated message, exception and location.
   *
   * <p>
   *  使用已关联的消息,异常和位置构造异常。
   * 
   * 
   * @param th a nested exception
   * @param msg the message to report
   * @param location the location of the error
   */
  public XMLStreamException(String msg, Location location, Throwable th) {
    super("ParseError at [row,col]:["+location.getLineNumber()+","+
          location.getColumnNumber()+"]\n"+
          "Message: "+msg);
    nested = th;
    this.location = location;
  }

  /**
   * Construct an exception with the assocated message, exception and location.
   *
   * <p>
   *  使用已关联的消息,异常和位置构造异常。
   * 
   * 
   * @param msg the message to report
   * @param location the location of the error
   */
  public XMLStreamException(String msg,
                            Location location) {
    super("ParseError at [row,col]:["+location.getLineNumber()+","+
          location.getColumnNumber()+"]\n"+
          "Message: "+msg);
    this.location = location;
  }


  /**
   * Gets the nested exception.
   *
   * <p>
   *  获取嵌套异常。
   * 
   * 
   * @return Nested exception
   */
  public Throwable getNestedException() {
    return nested;
  }

  /**
   * Gets the location of the exception
   *
   * <p>
   *  获取异常的位置
   * 
   * @return the location of the exception, may be null if none is available
   */
  public Location getLocation() {
    return location;
  }

}
