/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.Endpoint;
import javax.xml.ws.Service;

/**
 * This feature represents the use of MTOM with a
 * web service.
 *
 * This feature can be used during the creation of SEI proxy, and
 * {@link javax.xml.ws.Dispatch} instances on the client side and {@link Endpoint}
 * instances on the server side. This feature cannot be used for {@link Service}
 * instance creation on the client side.
 *
 * <p>
 * The following describes the affects of this feature with respect
 * to being enabled or disabled:
 * <ul>
 *  <li> ENABLED: In this Mode, MTOM will be enabled. A receiver MUST accept
 * both a non-optimized and an optimized message, and a sender MAY send an
 * optimized message, or a non-optimized message. The heuristics used by a
 * sender to determine whether to use optimization or not are
 * implementation-specific.
 *  <li> DISABLED: In this Mode, MTOM will be disabled
 * </ul>
 * <p>
 * The {@link #threshold} property can be used to set the threshold
 * value used to determine when binary data should be XOP encoded.
 *
 * <p>
 *  此功能表示MTOM与Web服务的使用。
 * 
 *  此功能可在创建SEI代理时使用,在客户端使用{@link javax.xml.ws.Dispatch}实例,并在服务器端使用{@link Endpoint}实例。
 * 此功能不能用于在客户端上创建{@link Service}实例。
 * 
 * <p>
 *  以下描述了此功能对启用或禁用的影响：
 * <ul>
 *  <li> ENABLED：在此模式下,将启用MTOM。接收器必须接受非优化消息和优化消息,并且发送器可以发送优化消息或非优化消息。发送方用于确定是否使用优化的启发式是实现特定的。
 *  <li> DISABLED：在此模式下,MTOM将被停用。
 * </ul>
 * <p>
 *  {@link #threshold}属性可用于设置用于确定二进制数据何时应进行XOP编码的阈值。
 * 
 * 
 * @since JAX-WS 2.1
 */
public final class MTOMFeature extends WebServiceFeature {
    /**
     * Constant value identifying the MTOMFeature
     * <p>
     *  标识MTOMFeature的常量值
     * 
     */
    public static final String ID = "http://www.w3.org/2004/08/soap/features/http-optimization";


    /**
     * Property for MTOM threshold value. This property serves as a hint when
     * MTOM is enabled, binary data above this size in bytes SHOULD be sent
     * as attachment.
     * The value of this property MUST always be >= 0. Default value is 0.
     * <p>
     *  MTOM阈值的属性。此属性作为一个提示,当MTOM启用时,超过此大小的字节的二进制数据应该作为附件发送。此属性的值必须始终为> = 0。默认值为0。
     * 
     */
    // should be changed to private final, keeping original modifier to keep backwards compatibility
    protected int threshold;


    /**
     * Create an <code>MTOMFeature</code>.
     * The instance created will be enabled.
     * <p>
     *  创建<code> MTOMFeature </code>。将创建已创建的实例。
     * 
     */
    public MTOMFeature() {
        this.enabled = true;
        this.threshold = 0;
    }

    /**
     * Creates an <code>MTOMFeature</code>.
     *
     * <p>
     *  创建<code> MTOMFeature </code>。
     * 
     * 
     * @param enabled specifies if this feature should be enabled or not
     */
    public MTOMFeature(boolean enabled) {
        this.enabled = enabled;
        this.threshold = 0;
    }


    /**
     * Creates an <code>MTOMFeature</code>.
     * The instance created will be enabled.
     *
     * <p>
     *  创建<code> MTOMFeature </code>。将创建已创建的实例。
     * 
     * 
     * @param threshold the size in bytes that binary data SHOULD be before
     * being sent as an attachment.
     *
     * @throws WebServiceException if threshold is < 0
     */
    public MTOMFeature(int threshold) {
        if (threshold < 0)
            throw new WebServiceException("MTOMFeature.threshold must be >= 0, actual value: "+threshold);
        this.enabled = true;
        this.threshold = threshold;
    }

    /**
     * Creates an <code>MTOMFeature</code>.
     *
     * <p>
     * 创建<code> MTOMFeature </code>。
     * 
     * 
     * @param enabled specifies if this feature should be enabled or not
     * @param threshold the size in bytes that binary data SHOULD be before
     * being sent as an attachment.
     *
     * @throws WebServiceException if threshold is < 0
     */
    public MTOMFeature(boolean enabled, int threshold) {
        if (threshold < 0)
            throw new WebServiceException("MTOMFeature.threshold must be >= 0, actual value: "+threshold);
        this.enabled = enabled;
        this.threshold = threshold;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public String getID() {
        return ID;
    }

    /**
     * Gets the threshold value used to determine when binary data
     * should be sent as an attachment.
     *
     * <p>
     *  获取用于确定应将二进制数据作为附件发送的阈值。
     * 
     * @return the current threshold size in bytes
     */
    public int getThreshold() {
        return threshold;
    }
}
