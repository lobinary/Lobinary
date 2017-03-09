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

package javax.xml.ws;

import java.lang.annotation.Documented;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Inherited;

/**
 * Used to indicate whether a {@link Provider} implementation wishes to work
 * with entire protocol messages or just with protocol message payloads.
 *
 * <p>
 *  用于指示{@link Provider}实现是希望与整个协议消息还是仅与协议消息有效负载一起使用。
 * 
 * 
 *  @since JAX-WS 2.0
**/
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ServiceMode {
  /**
   * Service mode. <code>PAYLOAD</code> indicates that the <code>Provider</code> implementation
   * wishes to work with protocol message payloads only. <code>MESSAGE</code> indicates
   * that the <code>Provider</code> implementation wishes to work with entire protocol
   * messages.
   * <p>
   *  服务模式。 <code> PAYLOAD </code>表示<code> Provider </code>实现希望仅与协议消息有效载荷一起使用。
   *  <code> MESSAGE </code>表示<code>提供程序</code>实现希望使用整个协议消息。
   * 
  **/
  public Service.Mode value() default Service.Mode.PAYLOAD;
}
