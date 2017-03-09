/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2003, Oracle and/or its affiliates. All rights reserved.
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
package com.sun.jmx.snmp;

/**
 * This engine is conformant with the RFC 2571. It is the main object within an SNMP entity (agent, manager...).
 * To an engine is associated an {@link SnmpEngineId}.
 * Engine instantiation is based on a factory {@link com.sun.jmx.snmp.SnmpEngineFactory  SnmpEngineFactory}.
 * When an <CODE> SnmpEngine </CODE> is created, a User based Security Model (USM) is initialized. The security configuration is located in a text file.
 * The text file is read when the engine is created.
 * <p>Note that the engine is not used when the agent is SNMPv1/SNMPv2 only.
<P> The USM configuration text file is remotely updatable using the USM Mib.</P>
<P> User that are configured in the Usm text file are nonVolatile. </P>
<P> Usm Mib userEntry supported storage type values are : volatile or nonVolatile only. Other values are rejected and a wrongValue is returned) </P>
<ul>
<li> volatile means that user entry is not flushed in security file </li>
<li> nonVolatile means that user entry is flushed in security file </li>
<li> If a nonVolatile row is set to be volatile, it will be not flushed in the file </li>
<li>If a volatile row created from the UsmMib is set to nonVolatile, it will be flushed in the file (if the file exist and is writable otherwise an inconsistentValue is returned)</li>
</ul>
 * <p><b>This API is a Sun Microsystems internal API  and is subject
 * to change without notice.</b></p>
 * <p>
 *  此引擎符合RFC 2571.它是SNMP实体(代理,管理器...)中的主要对象。引擎与一个{@link SnmpEngineId}相关联。
 * 引擎实例化基于工厂{@link com.sun.jmx.snmp.SnmpEngineFactory SnmpEngineFactory}。
 * 当创建<CODE> SnmpEngine </CODE>时,将初始化基于用户的安全模型(USM)。安全配置位于文本文件中。创建引擎时读取文本文件。
 *  <p>请注意,当代理仅为SNMPv1 / SNMPv2时,不使用引擎。 <P> USM配置文本文件可使用USM Mib进行远程更新。</P> <P>在Usm文本文件中配置的用户是非易失性的。
 *  </P> <P> Usm Mib userEntry支持的存储类型值有：volatile或nonVolatile。其他值被拒绝,并返回一个wrongValue)</P>。
 * <ul>
 *  <li> volatile表示用户条目未在安全文件中刷新</li> <li> nonVolatile意味着用户条目在安全文件中刷新</li> <li>如果非易失性行设置为volatile, </li>
 *  <li>如果将从UsmMib创建的易失行设置为nonVolatile,它将在文件中刷新(如果文件存在且可写,否则返回一个不一致的值)</li >。
 * </ul>
 * <p> <b>此API是Sun Microsystems的内部API,如有更改,恕不另行通知。</b> </p>
 * 
 * 
 * @since 1.5
 */
public interface SnmpEngine {
    /**
     * Gets the engine time in seconds. This is the time from the last reboot.
     * <p>
     *  获取引擎时间(以秒为单位)。这是从上次重新启动的时间。
     * 
     * 
     * @return The time from the last reboot.
     */
    public int getEngineTime();
    /**
     * Gets the engine Id. This is unique for each engine.
     * <p>
     *  获取引擎Id。这对于每个引擎是唯一的。
     * 
     * 
     * @return The engine Id object.
     */
    public SnmpEngineId getEngineId();

    /**
     * Gets the engine boot number. This is the number of time this engine has rebooted. Each time an <CODE>SnmpEngine</CODE> is instantiated, it will read this value in its Lcd, and store back the value incremented by one.
     * <p>
     *  获取引擎引导号。这是引擎重新启动的时间。每次实例化一个<CODE> SnmpEngine </CODE>时,它将在其Lcd中读取该值,并将返回值递增1。
     * 
     * 
     * @return The engine's number of reboot.
     */
    public int getEngineBoots();

    /**
     * Gets the Usm key handler.
     * <p>
     *  获取Usm键处理程序。
     * 
     * @return The key handler.
     */
    public SnmpUsmKeyHandler getUsmKeyHandler();
}
