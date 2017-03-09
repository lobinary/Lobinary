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
 * An error class for reporting factory configuration errors.
 *
 * <p>
 *  用于报告工厂配置错误的错误类。
 * 
 * 
 * @version 1.0
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @since 1.6
 */
public class FactoryConfigurationError extends Error {
    private static final long serialVersionUID = -2994412584589975744L;

  Exception nested;

  /**
   * Default constructor
   * <p>
   *  默认构造函数
   * 
   */
  public FactoryConfigurationError(){}

  /**
   * Construct an exception with a nested inner exception
   *
   * <p>
   *  构造具有嵌套内部异常的异常
   * 
   * 
   * @param e the exception to nest
   */
  public FactoryConfigurationError(java.lang.Exception e){
    nested = e;
  }

  /**
   * Construct an exception with a nested inner exception
   * and a message
   *
   * <p>
   *  构造具有嵌套内部异常和消息的异常
   * 
   * 
   * @param e the exception to nest
   * @param msg the message to report
   */
  public FactoryConfigurationError(java.lang.Exception e, java.lang.String msg){
    super(msg);
    nested = e;
  }

  /**
   * Construct an exception with a nested inner exception
   * and a message
   *
   * <p>
   *  构造具有嵌套内部异常和消息的异常
   * 
   * 
   * @param msg the message to report
   * @param e the exception to nest
   */
  public FactoryConfigurationError(java.lang.String msg, java.lang.Exception e){
    super(msg);
    nested = e;
  }

  /**
   * Construct an exception with associated message
   *
   * <p>
   *  构造具有关联消息的异常
   * 
   * 
   * @param msg the message to report
   */
  public FactoryConfigurationError(java.lang.String msg) {
    super(msg);
  }

  /**
   * Return the nested exception (if any)
   *
   * <p>
   *  返回嵌套异常(如果有)
   * 
   * 
   * @return the nested exception or null
   */
  public Exception getException() {
    return nested;
  }
    /**
     * use the exception chaining mechanism of JDK1.4
     * <p>
     *  使用JDK1.4的异常链接机制
     * 
    */
    @Override
    public Throwable getCause() {
        return nested;
    }

  /**
   * Report the message associated with this error
   *
   * <p>
   *  报告与此错误相关联的消息
   * 
   * @return the string value of the message
   */
  public String getMessage() {
    String msg = super.getMessage();
    if(msg != null)
      return msg;
    if(nested != null){
      msg = nested.getMessage();
      if(msg == null)
        msg = nested.getClass().toString();
    }
    return msg;
  }



}
