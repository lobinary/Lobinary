/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2001, Oracle and/or its affiliates. All rights reserved.
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
 * Interface PrintRequestAttribute is a tagging interface which a printing
 * attribute class implements to indicate the attribute denotes a
 * requested setting for a print job.
 * <p>
 * Attributes which are tagged with PrintRequestAttribute and are also tagged
 * as PrintJobAttribute, represent the subset of job attributes which
 * can be part of the specification of a job request.
 * <p>
 * If an attribute implements {@link DocAttribute  DocAttribute}
 * as well as PrintRequestAttribute, the client may include the
 * attribute in a <code>Doc</code>}'s attribute set to specify
 * a job setting which pertains just to that doc.
 * <P>
 *
 * <p>
 *  接口PrintRequestAttribute是打印属性类实现的标记接口,以指示属性表示打印作业的请求设置。
 * <p>
 *  用PrintRequestAttribute标记并且也标记为PrintJobAttribute的属性表示作业属性的子集,它们可以是作业请求规范的一部分。
 * <p>
 * 
 * @see DocAttributeSet
 * @see PrintRequestAttributeSet
 *
 * @author  Alan Kaminsky
 */

public interface PrintRequestAttribute extends Attribute {
}
