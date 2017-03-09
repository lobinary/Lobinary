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
 * This interface declares a simple filter interface that one can
 * create to filter XMLStreamReaders
 * <p>
 *  此接口声明一个简单的过滤器接口,可以创建过滤XMLStreamReaders
 * 
 * 
 * @version 1.0
 * @author Copyright (c) 2009 by Oracle Corporation. All Rights Reserved.
 * @since 1.6
 */
public interface StreamFilter {

  /**
   * Tests whether the current state is part of this stream.  This method
   * will return true if this filter accepts this event and false otherwise.
   *
   * The method should not change the state of the reader when accepting
   * a state.
   *
   * <p>
   *  测试当前状态是否为此流的一部分。如果此过滤器接受此事件,此方法将返回true,否则返回false。
   * 
   * 
   * @param reader the event to test
   * @return true if this filter accepts this event, false otherwise
   */
  public boolean accept(XMLStreamReader reader);
}
