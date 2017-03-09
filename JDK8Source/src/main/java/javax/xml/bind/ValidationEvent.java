/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.bind;

/**
 * This event indicates that a problem was encountered while validating the
 * incoming XML data during an unmarshal operation, while performing
 * on-demand validation of the Java content tree, or while marshalling the
 * Java content tree back to XML data.
 *
 * <p>
 *  此事件表示在执行解组操作期间验证传入XML数据时遇到问题,同时执行Java内容树的按需验证或在将Java内容树编组回XML数据时。
 * 
 * 
 * @author <ul><li>Ryan Shoemaker, Sun Microsystems, Inc.</li><li>Kohsuke Kawaguchi, Sun Microsystems, Inc.</li><li>Joe Fialli, Sun Microsystems, Inc.</li></ul>
 * @see Validator
 * @see ValidationEventHandler
 * @since JAXB1.0
 */
public interface ValidationEvent {

    /**
     * Conditions that are not errors or fatal errors as defined by the
     * XML 1.0 recommendation
     * <p>
     *  不是XML 1.0建议定义的错误或致命错误的条件
     * 
     */
    public static final int WARNING     = 0;

    /**
     * Conditions that correspond to the definition of "error" in section
     * 1.2 of the W3C XML 1.0 Recommendation
     * <p>
     *  与W3C XML 1.0建议书第1.2节中"错误"定义相对应的条件
     * 
     */
    public static final int ERROR       = 1;

    /**
     * Conditions that correspond to the definition of "fatal error" in section
     * 1.2 of the W3C XML 1.0 Recommendation
     * <p>
     *  对应于W3C XML 1.0建议书第1.2节中"致命错误"定义的条件
     * 
     */
    public static final int FATAL_ERROR = 2;

    /**
     * Retrieve the severity code for this warning/error.
     *
     * <p>
     * Must be one of <tt>ValidationError.WARNING</tt>,
     * <tt>ValidationError.ERROR</tt>, or <tt>ValidationError.FATAL_ERROR</tt>.
     *
     * <p>
     *  检索此警告/错误的严重性代码。
     * 
     * <p>
     *  必须是<tt> ValidationError.WARNING </tt>,<tt> ValidationError.ERROR </tt>或<tt> ValidationError.FATAL_ER
     * ROR </tt>中的一个。
     * 
     * 
     * @return the severity code for this warning/error
     */
    public int getSeverity();

    /**
     * Retrieve the text message for this warning/error.
     *
     * <p>
     *  检索此警告/错误的文本消息。
     * 
     * 
     * @return the text message for this warning/error or null if one wasn't set
     */
    public String getMessage();

    /**
     * Retrieve the linked exception for this warning/error.
     *
     * <p>
     *  检索此警告/错误的链接异常。
     * 
     * 
     * @return the linked exception for this warning/error or null if one
     *         wasn't set
     */
    public Throwable getLinkedException();

    /**
     * Retrieve the locator for this warning/error.
     *
     * <p>
     *  检索此警告/错误的定位器。
     * 
     * @return the locator that indicates where the warning/error occurred
     */
    public ValidationEventLocator getLocator();

}
