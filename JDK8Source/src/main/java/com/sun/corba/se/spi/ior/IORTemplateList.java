/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.ior ;

import java.util.List ;

/** An IORTemplateList is a list of IORTemplate instances.  It can be used to create IORs.
 * This is useful for representing IORs made of profiles from different object
 * adapters.
 * Note that any IORFactory can be added to an IORTemplateList, but it is flattened
 * so that the result is just a list of IORTemplate instances.
 * <p>
 *  这对于表示由来自不同对象适配器的配置文件构成的IOR非常有用。
 * 注意,任何IORFactory都可以添加到IORTemplateList中,但是它是扁平的,所以结果只是一个IORTemplate实例的列表。
 */
public interface IORTemplateList extends List, IORFactory, MakeImmutable {
}
