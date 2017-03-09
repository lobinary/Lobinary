/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, Oracle and/or its affiliates. All rights reserved.
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


package javax.naming;

import javax.naming.Name;

/**
 * This exception is thrown when a method
 * does not terminate within the specified time limit.
 * This can happen, for example, if the user specifies that
 * the method should take no longer than 10 seconds, and the
 * method fails to complete with 10 seconds.
 *
 * <p> Synchronization and serialization issues that apply to NamingException
 * apply directly here.
 *
 * <p>
 *  当方法在指定的时间限制内未终止时抛出此异常。这可能发生,例如,如果用户指定该方法应该不超过10秒,并且该方法无法完成10秒。
 * 
 *  <p>适用于NamingException的同步和序列化问题直接应用于此处。
 * 
 * 
 * @author Rosanna Lee
 * @author Scott Seligman
 *
 * @since 1.3
 */
public class TimeLimitExceededException extends LimitExceededException {
    /**
     * Constructs a new instance of TimeLimitExceededException.
     * All fields default to null.
     * <p>
     *  构造TimeLimitExceededException的新实例。所有字段默认为null。
     * 
     */
    public TimeLimitExceededException() {
        super();
    }

    /**
     * Constructs a new instance of TimeLimitExceededException
     * using the argument supplied.
     * <p>
     *  使用提供的参数构造TimeLimitExceededException的新实例。
     * 
     * 
     * @param explanation possibly null detail about this exception.
     * @see java.lang.Throwable#getMessage
     */
    public TimeLimitExceededException(String explanation) {
        super(explanation);
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = -3597009011385034696L;
}
