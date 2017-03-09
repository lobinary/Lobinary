/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2004, Oracle and/or its affiliates. All rights reserved.
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

package javax.imageio.spi;

import java.util.Locale;
import javax.imageio.spi.RegisterableService;
import javax.imageio.spi.ServiceRegistry;

/**
 * A superinterface for functionality common to all Image I/O service
 * provider interfaces (SPIs).  For more information on service
 * provider classes, see the class comment for the
 * <code>IIORegistry</code> class.
 *
 * <p>
 *  所有Image I / O服务提供程序接口(SPI)的通用的超级接口。有关服务提供程序类的更多信息,请参阅<code> IIORegistry </code>类的类注释。
 * 
 * 
 * @see IIORegistry
 * @see javax.imageio.spi.ImageReaderSpi
 * @see javax.imageio.spi.ImageWriterSpi
 * @see javax.imageio.spi.ImageTranscoderSpi
 * @see javax.imageio.spi.ImageInputStreamSpi
 *
 */
public abstract class IIOServiceProvider implements RegisterableService {

    /**
     * A <code>String</code> to be returned from
     * <code>getVendorName</code>, initially <code>null</code>.
     * Constructors should set this to a non-<code>null</code> value.
     * <p>
     *  从<code> getVendorName </code>返回的<code> String </code>,最初为<code> null </code>。
     * 构造函数应将此值设置为非<code> null </code>值。
     * 
     */
    protected String vendorName;

    /**
     * A <code>String</code> to be returned from
     * <code>getVersion</code>, initially null.  Constructors should
     * set this to a non-<code>null</code> value.
     * <p>
     *  从<code> getVersion </code>返回的<code> String </code>,最初为null。构造函数应将此值设置为非<code> null </code>值。
     * 
     */
    protected String version;

    /**
     * Constructs an <code>IIOServiceProvider</code> with a given
     * vendor name and version identifier.
     *
     * <p>
     *  构造具有给定供应商名称和版本标识符的<code> IIOServiceProvider </code>。
     * 
     * 
     * @param vendorName the vendor name.
     * @param version a version identifier.
     *
     * @exception IllegalArgumentException if <code>vendorName</code>
     * is <code>null</code>.
     * @exception IllegalArgumentException if <code>version</code>
     * is <code>null</code>.
     */
    public IIOServiceProvider(String vendorName,
                              String version) {
        if (vendorName == null) {
            throw new IllegalArgumentException("vendorName == null!");
        }
        if (version == null) {
            throw new IllegalArgumentException("version == null!");
        }
        this.vendorName = vendorName;
        this.version = version;
    }

    /**
     * Constructs a blank <code>IIOServiceProvider</code>.  It is up
     * to the subclass to initialize instance variables and/or
     * override method implementations in order to ensure that the
     * <code>getVendorName</code> and <code>getVersion</code> methods
     * will return non-<code>null</code> values.
     * <p>
     *  构造一个空白的<code> IIOServiceProvider </code>。
     * 为了确保<code> getVendorName </code>和<code> getVersion </code>方法将返回非<code> null </>方法,由子类初始化实例变量和/代码>值。
     * 
     */
    public IIOServiceProvider() {
    }

    /**
     * A callback that will be called exactly once after the Spi class
     * has been instantiated and registered in a
     * <code>ServiceRegistry</code>.  This may be used to verify that
     * the environment is suitable for this service, for example that
     * native libraries can be loaded.  If the service cannot function
     * in the environment where it finds itself, it should deregister
     * itself from the registry.
     *
     * <p> Only the registry should call this method.
     *
     * <p> The default implementation does nothing.
     *
     * <p>
     *  在Spi类被实例化并注册在<code> ServiceRegistry </code>中后,将被调用一次的回调。这可以用于验证环境是否适合此服务,例如可以加载本机库。
     * 如果服务不能在它发现自己的环境中运行,它应该从注册表中注销它自己。
     * 
     *  <p>只有注册表应该调用此方法。
     * 
     * <p>默认实现什么也不做。
     * 
     * 
     * @see ServiceRegistry#registerServiceProvider(Object provider)
     */
    public void onRegistration(ServiceRegistry registry,
                               Class<?> category) {}

    /**
     * A callback that will be whenever the Spi class has been
     * deregistered from a <code>ServiceRegistry</code>.
     *
     * <p> Only the registry should call this method.
     *
     * <p> The default implementation does nothing.
     *
     * <p>
     *  每当Spi类从<code> ServiceRegistry </code>中取消注册时的回调。
     * 
     *  <p>只有注册表应该调用此方法。
     * 
     *  <p>默认实现什么也不做。
     * 
     * 
     * @see ServiceRegistry#deregisterServiceProvider(Object provider)
     */
    public void onDeregistration(ServiceRegistry registry,
                                 Class<?> category) {}

    /**
     * Returns the name of the vendor responsible for creating this
     * service provider and its associated implementation.  Because
     * the vendor name may be used to select a service provider,
     * it is not localized.
     *
     * <p> The default implementation returns the value of the
     * <code>vendorName</code> instance variable.
     *
     * <p>
     *  返回负责创建此服务提供程序及其关联实现的供应商的名称。因为供应商名称可以用于选择服务提供商,所以它不是本地化的。
     * 
     *  <p>默认实现返回<code> vendorName </code>实例变量的值。
     * 
     * 
     * @return a non-<code>null</code> <code>String</code> containing
     * the name of the vendor.
     */
    public String getVendorName() {
        return vendorName;
    }

    /**
     * Returns a string describing the version
     * number of this service provider and its associated
     * implementation.  Because the version may be used by transcoders
     * to identify the service providers they understand, this method
     * is not localized.
     *
     * <p> The default implementation returns the value of the
     * <code>version</code> instance variable.
     *
     * <p>
     *  返回描述此服务提供程序及其关联实现的版本号的字符串。因为版本可以由代码转换器用于识别他们理解的服务提供商,所以该方法不是本地化的。
     * 
     *  <p>默认实现返回<code> version </code>实例变量的值。
     * 
     * 
     * @return a non-<code>null</code> <code>String</code> containing
     * the version of this service provider.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Returns a brief, human-readable description of this service
     * provider and its associated implementation.  The resulting
     * string should be localized for the supplied
     * <code>Locale</code>, if possible.
     *
     * <p>
     * 
     * @param locale a <code>Locale</code> for which the return value
     * should be localized.
     *
     * @return a <code>String</code> containing a description of this
     * service provider.
     */
    public abstract String getDescription(Locale locale);
}
