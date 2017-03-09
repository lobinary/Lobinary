/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2005, Oracle and/or its affiliates. All rights reserved.
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

package javax.management;

/**
 * Interface to read the Descriptor of a management interface element
 * such as an MBeanInfo.
 * <p>
 *  用于读取管理接口元素(例如MBeanInfo)的描述符的接口。
 * 
 * 
 * @since 1.6
 */
public interface DescriptorRead {
   /**
    * Returns a copy of Descriptor.
    *
    * <p>
    *  返回描述符的副本。
    * 
    * @return Descriptor associated with the component implementing this interface.
    * The return value is never null, but the returned descriptor may be empty.
    */
    public Descriptor getDescriptor();
}
