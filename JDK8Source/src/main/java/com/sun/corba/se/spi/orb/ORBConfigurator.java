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

/** Interface used to configure an ORB instance.  The DataCollector dc has all
 * available config info available.  The configure method may constructor a
 * parser, call dc.setParser( parser ), get the consolidated properties from dc,
 * and parse this information.  The configure method may also register ORB
 * components with the ORB and perform other parts of ORB initialization.
 * Implementations of this interface must have a public no-args constructor.
 * <p>
 *  可用的配置信息。 configure方法可以构造一个解析器,调用dc.setParser(parser),从dc获取合并的属性,并解析这些信息。
 *  configure方法还可以向ORB注册ORB组件,并执行ORB初始化的其他部分。此接口的实现必须具有公共无参数构造函数。
 */
public interface ORBConfigurator {
    void configure( DataCollector dc, ORB orb ) ;
}
