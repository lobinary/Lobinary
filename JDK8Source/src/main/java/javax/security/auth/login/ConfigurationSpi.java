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


package javax.security.auth.login;

/**
 * This class defines the <i>Service Provider Interface</i> (<b>SPI</b>)
 * for the {@code Configuration} class.
 * All the abstract methods in this class must be implemented by each
 * service provider who wishes to supply a Configuration implementation.
 *
 * <p> Subclass implementations of this abstract class must provide
 * a public constructor that takes a {@code Configuration.Parameters}
 * object as an input parameter.  This constructor also must throw
 * an IllegalArgumentException if it does not understand the
 * {@code Configuration.Parameters} input.
 *
 *
 * <p>
 *  此类为{@code Configuration}类定义了<i>服务提供程序接口</i>(<b> SPI </b>)。此类中的所有抽象方法必须由希望提供配置实现的每个服务提供者实现。
 * 
 *  <p>此抽象类的子类实现必须提供一个公共构造函数,它将{@code Configuration.Parameters}对象作为输入参数。
 * 如果构造函数不理解{@code Configuration.Parameters}输入,它也必须抛出IllegalArgumentException。
 * 
 * 
 * @since 1.6
 */

public abstract class ConfigurationSpi {
    /**
     * Retrieve the AppConfigurationEntries for the specified <i>name</i>.
     *
     * <p>
     *
     * <p>
     *  检索指定<i>名称</i>的AppConfigurationEntries。
     * 
     * <p>
     * 
     * 
     * @param name the name used to index the Configuration.
     *
     * @return an array of AppConfigurationEntries for the specified
     *          <i>name</i>, or null if there are no entries.
     */
    protected abstract AppConfigurationEntry[] engineGetAppConfigurationEntry
                                                        (String name);

    /**
     * Refresh and reload the Configuration.
     *
     * <p> This method causes this Configuration object to refresh/reload its
     * contents in an implementation-dependent manner.
     * For example, if this Configuration object stores its entries in a file,
     * calling {@code refresh} may cause the file to be re-read.
     *
     * <p> The default implementation of this method does nothing.
     * This method should be overridden if a refresh operation is supported
     * by the implementation.
     *
     * <p>
     *  刷新并重新加载配置。
     * 
     *  <p>此方法导致此配置对象以实现相关方式刷新/重新加载其内容。例如,如果此配置对象将其条目存储在文件中,则调用{@code refresh}可能会导致该文件被重新读取。
     * 
     * @exception SecurityException if the caller does not have permission
     *          to refresh its Configuration.
     */
    protected void engineRefresh() { }
}
