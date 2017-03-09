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
 * An interface that describes the data found in processing instructions
 *
 * <p>
 *  描述在处理指令中找到的数据的接口
 * 
 * 
 * @version 1.0
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @since 1.6
 */
public interface ProcessingInstruction extends XMLEvent {

  /**
   * The target section of the processing instruction
   *
   * <p>
   *  处理指令的目标部分
   * 
   * 
   * @return the String value of the PI or null
   */
  public String getTarget();

  /**
   * The data section of the processing instruction
   *
   * <p>
   *  处理指令的数据部分
   * 
   * @return the String value of the PI's data or null
   */
  public String getData();
}
