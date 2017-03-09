/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, Oracle and/or its affiliates. All rights reserved.
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

package javax.print.attribute;

/**
 * PrintJobAttribute is a tagging interface which a printing attribute
 * class implements to indicate the attribute describes the status of a Print
 * Job or some other characteristic of a Print Job. A Print Service
 * instance adds a number of PrintJobAttributes to a Print Job's attribute set
 * to report the Print Job's status. If an attribute implements {@link
 * PrintRequestAttribute PrintRequestAttribute} as well as PrintJobAttribute,
 * the client may include the attribute in a attribute set to
 * specify the attribute's value for the Print Job.
 * <P>
 *
 * <p>
 *  PrintJobAttribute是打印属性类实现的标记接口,以指示该属性描述打印作业的状态或打印作业的某些其他特性。
 * 打印服务实例将一些PrintJobAttributes添加到打印作业的属性集,以报告打印作业的状态。
 * 如果属性实现{@link PrintRequestAttribute PrintRequestAttribute}以及PrintJobAttribute,则客户端可以在属性集中包括属性以指定打印作业的属
 * 性值。
 * 
 * @see PrintRequestAttributeSet
 * @see PrintJobAttributeSet
 *
 * @author  Alan Kaminsky
 */
public interface PrintJobAttribute extends Attribute {
}
