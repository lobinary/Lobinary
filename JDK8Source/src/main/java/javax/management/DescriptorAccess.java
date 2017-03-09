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
/*
/* <p>
/* 
 * @author    IBM Corp.
 *
 * Copyright IBM Corp. 1999-2000.  All rights reserved.
 */

package javax.management;

/**
 * This interface is used to gain access to descriptors of the Descriptor class
 * which are associated with a JMX component, i.e. MBean, MBeanInfo,
 * MBeanAttributeInfo, MBeanNotificationInfo,
 * MBeanOperationInfo, MBeanParameterInfo.
 * <P>
 * ModelMBeans make extensive use of this interface in ModelMBeanInfo classes.
 *
 * <p>
 *  该接口用于访问与JMX组件相关联的描述符类的描述符,即MBean,MBeanInfo,MBeanAttributeInfo,MBeanNotificationInfo,MBeanOperationIn
 * fo,MBeanParameterInfo。
 * <P>
 *  ModelMBeans在ModelMBeanInfo类中广泛使用此接口。
 * 
 * @since 1.5
 */
public interface DescriptorAccess extends DescriptorRead
{
    /**
    * Sets Descriptor (full replace).
    *
    * <p>
    * 
    * 
    * @param inDescriptor replaces the Descriptor associated with the
    * component implementing this interface. If the inDescriptor is invalid for the
    * type of Info object it is being set for, an exception is thrown.  If the
    * inDescriptor is null, then the Descriptor will revert to its default value
    * which should contain, at a minimum, the descriptor name and descriptorType.
    *
    * @see #getDescriptor
    */
    public void setDescriptor(Descriptor inDescriptor);
}
