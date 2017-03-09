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

package javax.xml.xpath;

import java.io.PrintWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.InvalidClassException;

/**
 * <code>XPathException</code> represents a generic XPath exception.</p>
 *
 * <p>
 *  <code> XPathException </code>表示一个通用XPath异常。</p>
 * 
 * 
 * @author  <a href="Norman.Walsh@Sun.com">Norman Walsh</a>
 * @author <a href="mailto:Jeff.Suttor@Sun.COM">Jeff Suttor</a>
 * @since 1.5
 */
public class XPathException extends Exception {

    private static final ObjectStreamField[] serialPersistentFields = {
        new ObjectStreamField( "cause", Throwable.class )
    };

    /**
     * <p>Stream Unique Identifier.</p>
     * <p>
     *  <p>流唯一标识符</p>
     * 
     */
    private static final long serialVersionUID = -1837080260374986980L;

    /**
     * <p>Constructs a new <code>XPathException</code>
     * with the specified detail <code>message</code>.</p>
     *
     * <p>The <code>cause</code> is not initialized.</p>
     *
     * <p>If <code>message</code> is <code>null</code>,
     * then a <code>NullPointerException</code> is thrown.</p>
     *
     * <p>
     *  <p>使用指定的详细信息<code> message </code>构造一个新的<code> XPathException </code>。</p>
     * 
     *  <p> <code>原因</code>未初始化。</p>
     * 
     *  <p>如果<code> message </code>是<code> null </code>,那么会抛出<code> NullPointerException </code>。</p>
     * 
     * 
     * @param message The detail message.
     *
     * @throws NullPointerException When <code>message</code> is
     *   <code>null</code>.
     */
    public XPathException(String message) {
        super(message);
        if ( message == null ) {
            throw new NullPointerException ( "message can't be null");
        }
    }

    /**
     * <p>Constructs a new <code>XPathException</code>
     * with the specified <code>cause</code>.</p>
     *
     * <p>If <code>cause</code> is <code>null</code>,
     * then a <code>NullPointerException</code> is thrown.</p>
     *
     * <p>
     *  <p>使用指定的<code> cause </code>构造新的<code> XPathException </code>。</p>
     * 
     *  <p>如果<code> cause </code>是<code> null </code>,那么会抛出<code> NullPointerException </code>。</p>
     * 
     * 
     * @param cause The cause.
     *
     * @throws NullPointerException if <code>cause</code> is <code>null</code>.
     */
    public XPathException(Throwable cause) {
        super(cause);
        if ( cause == null ) {
            throw new NullPointerException ( "cause can't be null");
        }
    }

    /**
     * <p>Get the cause of this XPathException.</p>
     *
     * <p>
     *  <p>获取此XPathException的原因。</p>
     * 
     * 
     * @return Cause of this XPathException.
     */
    public Throwable getCause() {
        return super.getCause();
    }

    /**
     * Writes "cause" field to the stream.
     * The cause is got from the parent class.
     *
     * <p>
     *  将"cause"字段写入流。原因是从父类得到的。
     * 
     * 
     * @param out stream used for serialization.
     * @throws IOException thrown by <code>ObjectOutputStream</code>
     *
     */
    private void writeObject(ObjectOutputStream out)
            throws IOException
    {
        ObjectOutputStream.PutField fields = out.putFields();
        fields.put("cause", (Throwable) super.getCause());
        out.writeFields();
    }

    /**
     * Reads the "cause" field from the stream.
     * And initializes the "cause" if it wasn't
     * done before.
     *
     * <p>
     *  从流中读取"原因"字段。并初始化"原因",如果没有做过。
     * 
     * 
     * @param in stream used for deserialization
     * @throws IOException thrown by <code>ObjectInputStream</code>
     * @throws ClassNotFoundException  thrown by <code>ObjectInputStream</code>
     */
    private void readObject(ObjectInputStream in)
            throws IOException, ClassNotFoundException
    {
        ObjectInputStream.GetField fields = in.readFields();
        Throwable scause = (Throwable) fields.get("cause", null);

        if (super.getCause() == null && scause != null) {
            try {
                super.initCause(scause);
            } catch(IllegalStateException e) {
                throw new InvalidClassException("Inconsistent state: two causes");
            }
        }
    }

    /**
     * <p>Print stack trace to specified <code>PrintStream</code>.</p>
     *
     * <p>
     *  <p>将堆栈跟踪打印到指定的<code> PrintStream </code>。</p>
     * 
     * 
     * @param s Print stack trace to this <code>PrintStream</code>.
     */
    public void printStackTrace(java.io.PrintStream s) {
        if (getCause() != null) {
            getCause().printStackTrace(s);
          s.println("--------------- linked to ------------------");
        }

        super.printStackTrace(s);
    }

    /**
     * <p>Print stack trace to <code>System.err</code>.</p>
     * <p>
     *  <p>将堆栈跟踪打印到<code> System.err </code>。</p>
     * 
     */
    public void printStackTrace() {
        printStackTrace(System.err);
    }

    /**
     * <p>Print stack trace to specified <code>PrintWriter</code>.</p>
     *
     * <p>
     *  <p>打印堆栈跟踪到指定的<code> PrintWriter </code>。</p>
     * 
     * @param s Print stack trace to this <code>PrintWriter</code>.
     */
    public void printStackTrace(PrintWriter s) {

        if (getCause() != null) {
            getCause().printStackTrace(s);
          s.println("--------------- linked to ------------------");
        }

        super.printStackTrace(s);
    }
}
