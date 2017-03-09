/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2010, Oracle and/or its affiliates. All rights reserved.
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

package java.io;

/**
 * ObjectOutput extends the DataOutput interface to include writing of objects.
 * DataOutput includes methods for output of primitive types, ObjectOutput
 * extends that interface to include objects, arrays, and Strings.
 *
 * <p>
 *  ObjectOutput扩展DataOutput接口以包括对象的写入。 DataOutput包括用于输出基本类型的方法,ObjectOutput扩展了该接口以包括对象,数组和字符串。
 * 
 * 
 * @author  unascribed
 * @see java.io.InputStream
 * @see java.io.ObjectOutputStream
 * @see java.io.ObjectInputStream
 * @since   JDK1.1
 */
public interface ObjectOutput extends DataOutput, AutoCloseable {
    /**
     * Write an object to the underlying storage or stream.  The
     * class that implements this interface defines how the object is
     * written.
     *
     * <p>
     *  将对象写入底层存储或流。实现此接口的类定义了对象的写入方式。
     * 
     * 
     * @param obj the object to be written
     * @exception IOException Any of the usual Input/Output related exceptions.
     */
    public void writeObject(Object obj)
      throws IOException;

    /**
     * Writes a byte. This method will block until the byte is actually
     * written.
     * <p>
     *  写入一个字节。此方法将阻塞,直到字节实际写入。
     * 
     * 
     * @param b the byte
     * @exception IOException If an I/O error has occurred.
     */
    public void write(int b) throws IOException;

    /**
     * Writes an array of bytes. This method will block until the bytes
     * are actually written.
     * <p>
     *  写入字节数组。此方法将阻塞,直到字节实际写入。
     * 
     * 
     * @param b the data to be written
     * @exception IOException If an I/O error has occurred.
     */
    public void write(byte b[]) throws IOException;

    /**
     * Writes a sub array of bytes.
     * <p>
     *  写入字节的子数组。
     * 
     * 
     * @param b the data to be written
     * @param off       the start offset in the data
     * @param len       the number of bytes that are written
     * @exception IOException If an I/O error has occurred.
     */
    public void write(byte b[], int off, int len) throws IOException;

    /**
     * Flushes the stream. This will write any buffered
     * output bytes.
     * <p>
     *  刷新流。这将写入任何缓冲输出字节。
     * 
     * 
     * @exception IOException If an I/O error has occurred.
     */
    public void flush() throws IOException;

    /**
     * Closes the stream. This method must be called
     * to release any resources associated with the
     * stream.
     * <p>
     *  关闭流。必须调用此方法才能释放与流关联的任何资源。
     * 
     * @exception IOException If an I/O error has occurred.
     */
    public void close() throws IOException;
}
