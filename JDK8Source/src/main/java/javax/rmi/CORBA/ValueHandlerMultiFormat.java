/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2006, Oracle and/or its affiliates. All rights reserved.
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

package javax.rmi.CORBA;

/**
 * Java to IDL ptc 02-01-12 1.5.1.5
 * <p>
 *  Java到IDL ptc 02-01-12 1.5.1.5
 * 
 * 
 * @since 1.5
 */
public interface ValueHandlerMultiFormat extends ValueHandler {

    /**
     * Returns the maximum stream format version for
     * RMI/IDL custom value types that is supported
     * by this ValueHandler object. The ValueHandler
     * object must support the returned stream format version and
     * all lower versions.
     *
     * An ORB may use this value to include in a standard
     * IOR tagged component or service context to indicate to other
     * ORBs the maximum RMI-IIOP stream format that it
     * supports.  If not included, the default for GIOP 1.2
     * is stream format version 1, and stream format version
     * 2 for GIOP 1.3 and higher.
     * <p>
     *  返回此ValueHandler对象支持的RMI / IDL自定义值类型的最大流格式版本。 ValueHandler对象必须支持返回的流格式版本和所有较低版本。
     * 
     *  ORB可以使用该值来包括在标准的IOR标记的组件或服务上下文中以向其它ORB指示其支持的最大RMI-IIOP流格式。
     * 如果不包括,则GIOP 1.2的默认值是流格式版本1,以及GIOP 1.3和更高版本的流格式版本2。
     * 
     */
    byte getMaximumStreamFormatVersion();

    /**
     * Allows the ORB to pass the stream format
     * version for RMI/IDL custom value types. If the ORB
     * calls this method, it must pass a stream format version
     * between 1 and the value returned by the
     * getMaximumStreamFormatVersion method inclusive,
     * or else a BAD_PARAM exception with standard minor code
     * will be thrown.
     *
     * If the ORB calls the older ValueHandler.writeValue(OutputStream,
     * Serializable) method, stream format version 1 is implied.
     *
     * The ORB output stream passed to the ValueHandlerMultiFormat.writeValue
     * method must implement the ValueOutputStream interface, and the
     * ORB input stream passed to the ValueHandler.readValue method must
     * implement the ValueInputStream interface.
     * <p>
     *  允许ORB传递RMI / IDL自定义值类型的流格式版本。
     * 如果ORB调用此方法,它必须传递流格式版本在1和由getMaximumStreamFormatVersion方法返回的值之间,否则将抛出带有标准次要代码的BAD_PARAM异常。
     * 
     *  如果ORB调用旧的ValueHandler.writeValue(OutputStream,Serializable)方法,则隐含流格式版本1。
     */
    void writeValue(org.omg.CORBA.portable.OutputStream out,
                    java.io.Serializable value,
                    byte streamFormatVersion);
}
