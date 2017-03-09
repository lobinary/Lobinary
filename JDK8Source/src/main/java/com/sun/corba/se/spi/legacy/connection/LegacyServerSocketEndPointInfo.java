/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2004, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.legacy.connection;

/**
 * LegacyServerSocketEndPointInfo is an abstraction of a port.
 * <p>
 *  LegacyServerSocketEndPointInfo是端口的抽象。
 * 
 */
public interface LegacyServerSocketEndPointInfo
{
    /**
     * e.g.: "CLEAR_TEXT", "SSL", ...
     * <p>
     *  例如："CLEAR_TEXT","SSL",...
     * 
     */
    public String getType();


    /**
     * Get the host name of this end point. Subcontracts must use this
     * instead of InetAddress.getHostName() because this would take
     * into account the value of the ORBServerHost property.
     * <p>
     *  获取此端点的主机名。子合同必须使用此代替InetAddress.getHostName(),因为这将考虑ORBServerHost属性的值。
     * 
     */
    public String getHostName();

    public int getPort();

    /**
     * The ORBD's proxy port of this end point.
     * Note: Pre-ORT "port-exchange" model.
     * <p>
     *  ORBD的这个端点的代理端口。注意：Pre-ORT"端口交换"模型。
     */
    public int getLocatorPort();
    public void setLocatorPort(int port);

    // NAME is used while we still have a "port-exchange" ORBD
    // to get what used to be called "default" or "bootstrap" endpoints.

    public static final String DEFAULT_ENDPOINT = "DEFAULT_ENDPOINT";
    public static final String BOOT_NAMING = "BOOT_NAMING";
    public static final String NO_NAME = "NO_NAME";

    public String getName();
}

// End of file.
