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

package javax.xml.stream.events;
/**
 * An interface for handling Notation Declarations
 *
 * Receive notification of a notation declaration event.
 * It is up to the application to record the notation for later reference,
 * At least one of publicId and systemId must be non-null.
 * There is no guarantee that the notation declaration
 * will be reported before any unparsed entities that use it.
 *
 * <p>
 *  用于处理符号声明的接口
 * 
 *  接收符号声明事件的通知。它由应用程序记录符号供以后引用,至少有一个publicId和systemId必须是非null。不能保证在使用它的任何未分析​​实体之前报告符号声明。
 * 
 * 
 * @version 1.0
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @since 1.6
 */
public interface NotationDeclaration extends XMLEvent {
  /**
   * The notation name.
   * <p>
   *  符号名称。
   * 
   */
  String getName();

  /**
   * The notation's public identifier, or null if none was given.
   * <p>
   *  符号的公共标识符,如果没有给出,则为null。
   * 
   */
  String getPublicId();

  /**
   * The notation's system identifier, or null if none was given.
   * <p>
   *  符号的系统标识符,如果没有给出,则为null。
   */
  String getSystemId();
}
