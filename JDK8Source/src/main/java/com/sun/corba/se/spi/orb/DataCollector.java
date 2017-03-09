/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, Oracle and/or its affiliates. All rights reserved.
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
package com.sun.corba.se.spi.orb ;

import java.applet.Applet ;
import java.util.Properties ;
import java.util.Vector ;

/** Interface for collecting all sources of ORB configuration properties
 * into a single properties object.   A PropertyParser is needed so that
 * the set of property names of interest is known.
 * <p>
 *  转换为单个属性对象。需要PropertyParser,以便知道感兴趣的属性名称集。
 * 
 */
public interface DataCollector {
    /** Return true iff this DataCollector was created from
     * applet data.
     * <p>
     *  小应用程序数据。
     * 
     */
    boolean isApplet() ;

    /** Return true iff the local host and ORB initial host are the same.
    * This is provided to avoid exposing the local host in insecure
    * contexts.
    * <p>
    *  这是为了避免在不安全的上下文中暴露本地主机。
    * 
    */
    boolean initialHostIsLocal() ;

    /** Set the parser which is used to obtain property names.
     * This must be called before getProperties
     * may be called.  It may be called multiple times if different
     * sets of properties are needed for the same data sources.
     * <p>
     *  这必须在调用getProperties之前调用。如果对于相同的数据源需要不同的属性集合,则可以多次调用。
     * 
     */
    void setParser( PropertyParser parser ) ;

    /** Return the consolidated property information to be used
     * for ORB configuration.  Note that -ORBInitRef arguments are
     * handled specially: all -ORBInitRef name=value arguments are
     * converted into ( org.omg.CORBA.ORBInitRef.name, value )
     * mappings in the resulting properties.  Also, -ORBInitialServices
     * is handled specially in applet mode: they are converted from
     * relative to absolute URLs.
     * @raises IllegalStateException if setPropertyNames has not
     * been called.
     * <p>
     *  用于ORB配置。
     * 请注意,-ORBInitRef参数被特别处理：所有-ORBInitRef name = value参数在结果属性中转换为(org.omg.CORBA.ORBInitRef.name,value)映射。
     * 此外,-ORBInitialServices在applet模式下被特别处理：它们从相对URL转换为绝对URL。
     */
    Properties getProperties() ;
}
