/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2006, Oracle and/or its affiliates. All rights reserved.
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

package org.omg.CORBA;

import org.omg.CORBA.portable.InputStream;
import org.omg.CORBA.portable.OutputStream;
import java.util.*;
import org.omg.CORBA.OMGVMCID;
import com.sun.corba.se.impl.util.SUNVMCID;

/**
 * The root class for all CORBA standard exceptions. These exceptions
 * may be thrown as a result of any CORBA operation invocation and may
 * also be returned by many standard CORBA API methods. The standard
 * exceptions contain a minor code, allowing more detailed specification, and a
 * completion status. This class is subclassed to
 * generate each one of the set of standard ORB exceptions.
 * <code>SystemException</code> extends
 * <code>java.lang.RuntimeException</code>; thus none of the
 * <code>SystemException</code> exceptions need to be
 * declared in signatures of the Java methods mapped from operations in
 * IDL interfaces.
 *
 * <p>
 *  所有CORBA标准异常的根类。这些异常可以作为任何CORBA操作调用的结果而抛出,并且也可以由许多标准CORBA API方法返回。标准异常包含次要代码,允许更详细的规范和完成状态。
 * 这个类被子类化以生成标准ORB异常集合中的每一个。
 *  <code> SystemException </code> extends <code> java.lang.RuntimeException </code>;因此,不需要在从IDL接口中的操作映射
 * 的Java方法的签名中声明<code> SystemException </code>异常。
 * 这个类被子类化以生成标准ORB异常集合中的每一个。
 * 
 * 
 * @see <A href="../../../../technotes/guides/idl/jidlExceptions.html">documentation on
 * Java&nbsp;IDL exceptions</A>
 */

public abstract class SystemException extends java.lang.RuntimeException {

    /**
     * The CORBA Exception minor code.
     * <p>
     *  CORBA异常次要代码。
     * 
     * 
     * @serial
     */
    public int minor;

    /**
     * The status of the operation that threw this exception.
     * <p>
     *  抛出此异常的操作的状态。
     * 
     * 
     * @serial
     */
    public CompletionStatus completed;

    /**
     * Constructs a <code>SystemException</code> exception with the specified detail
     * message, minor code, and completion status.
     * A detail message is a String that describes this particular exception.
     * <p>
     *  构造具有指定的详细消息,次要代码和完成状态的<code> SystemException </code>异常。详细消息是描述此特殊异常的字符串。
     * 
     * 
     * @param reason the String containing a detail message
     * @param minor the minor code
     * @param completed the completion status
     */
    protected SystemException(String reason, int minor, CompletionStatus completed) {
        super(reason);
        this.minor = minor;
        this.completed = completed;
    }

    /**
     * Converts this exception to a representative string.
     * <p>
     *  将此异常转换为代表性字符串。
     */
    public String toString() {
        // The fully qualified exception class name
        String result = super.toString();

        // The vmcid part
        int vmcid = minor & 0xFFFFF000;
        switch (vmcid) {
            case OMGVMCID.value:
                result += "  vmcid: OMG";
                break;
            case SUNVMCID.value:
                result += "  vmcid: SUN";
                break;
            default:
                result += "  vmcid: 0x" + Integer.toHexString(vmcid);
                break;
        }

        // The minor code part
        int mc = minor & 0x00000FFF;
        result += "  minor code: " + mc;

        // The completion status part
        switch (completed.value()) {
            case CompletionStatus._COMPLETED_YES:
                result += "  completed: Yes";
                break;
            case CompletionStatus._COMPLETED_NO:
                result += "  completed: No";
                break;
            case CompletionStatus._COMPLETED_MAYBE:
            default:
                result += " completed: Maybe";
                break;
        }
        return result;
    }
}
