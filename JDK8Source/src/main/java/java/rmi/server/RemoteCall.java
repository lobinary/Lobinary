/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2006, Oracle and/or its affiliates. All rights reserved.
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

package java.rmi.server;
import java.rmi.*;
import java.io.ObjectOutput;
import java.io.ObjectInput;
import java.io.StreamCorruptedException;
import java.io.IOException;

/**
 * <code>RemoteCall</code> is an abstraction used solely by the RMI runtime
 * (in conjunction with stubs and skeletons of remote objects) to carry out a
 * call to a remote object.  The <code>RemoteCall</code> interface is
 * deprecated because it is only used by deprecated methods of
 * <code>java.rmi.server.RemoteRef</code>.
 *
 * <p>
 *  <code> RemoteCall </code>是一个仅由RMI运行时(结合远程对象的存根和框架)用于对远程对象执行调用的抽象。
 *  <code> RemoteCall </code>接口已弃用,因为它仅由<code> java.rmi.server.RemoteRef </code>的过时方法使用。
 * 
 * 
 * @since   JDK1.1
 * @author  Ann Wollrath
 * @author  Roger Riggs
 * @see     java.rmi.server.RemoteRef
 * @deprecated no replacement.
 */
@Deprecated
public interface RemoteCall {

    /**
     * Return the output stream the stub/skeleton should put arguments/results
     * into.
     *
     * <p>
     *  返回输出流的存根/骨架应该把参数/结果放入。
     * 
     * 
     * @return output stream for arguments/results
     * @exception java.io.IOException if an I/O error occurs.
     * @since JDK1.1
     * @deprecated no replacement
     */
    @Deprecated
    ObjectOutput getOutputStream()  throws IOException;

    /**
     * Release the output stream; in some transports this would release
     * the stream.
     *
     * <p>
     *  释放输出流;在一些运输中,这将释放流。
     * 
     * 
     * @exception java.io.IOException if an I/O error occurs.
     * @since JDK1.1
     * @deprecated no replacement
     */
    @Deprecated
    void releaseOutputStream()  throws IOException;

    /**
     * Get the InputStream that the stub/skeleton should get
     * results/arguments from.
     *
     * <p>
     *  获取存根/骨架应该从中获取结果/参数的InputStream。
     * 
     * 
     * @return input stream for reading arguments/results
     * @exception java.io.IOException if an I/O error occurs.
     * @since JDK1.1
     * @deprecated no replacement
     */
    @Deprecated
    ObjectInput getInputStream()  throws IOException;


    /**
     * Release the input stream. This would allow some transports to release
     * the channel early.
     *
     * <p>
     *  释放输入流。这将允许一些传输提前释放信道。
     * 
     * 
     * @exception java.io.IOException if an I/O error occurs.
     * @since JDK1.1
     * @deprecated no replacement
     */
    @Deprecated
    void releaseInputStream() throws IOException;

    /**
     * Returns an output stream (may put out header information
     * relating to the success of the call). Should only succeed
     * once per remote call.
     *
     * <p>
     *  返回输出流(可能会输出与调用成功相关的头信息)。每次远程调用只能成功一次。
     * 
     * 
     * @param success If true, indicates normal return, else indicates
     * exceptional return.
     * @return output stream for writing call result
     * @exception java.io.IOException              if an I/O error occurs.
     * @exception java.io.StreamCorruptedException If already been called.
     * @since JDK1.1
     * @deprecated no replacement
     */
    @Deprecated
    ObjectOutput getResultStream(boolean success) throws IOException,
        StreamCorruptedException;

    /**
     * Do whatever it takes to execute the call.
     *
     * <p>
     *  执行任何所需的执行调用。
     * 
     * 
     * @exception java.lang.Exception if a general exception occurs.
     * @since JDK1.1
     * @deprecated no replacement
     */
    @Deprecated
    void executeCall() throws Exception;

    /**
     * Allow cleanup after the remote call has completed.
     *
     * <p>
     *  在远程调用完成后允许清除。
     * 
     * @exception java.io.IOException if an I/O error occurs.
     * @since JDK1.1
     * @deprecated no replacement
     */
    @Deprecated
    void done() throws IOException;
}
