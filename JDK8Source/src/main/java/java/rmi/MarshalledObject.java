/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.rmi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamConstants;
import java.io.OutputStream;
import java.io.Serializable;
import sun.rmi.server.MarshalInputStream;
import sun.rmi.server.MarshalOutputStream;

/**
 * A <code>MarshalledObject</code> contains a byte stream with the serialized
 * representation of an object given to its constructor.  The <code>get</code>
 * method returns a new copy of the original object, as deserialized from
 * the contained byte stream.  The contained object is serialized and
 * deserialized with the same serialization semantics used for marshaling
 * and unmarshaling parameters and return values of RMI calls:  When the
 * serialized form is created:
 *
 * <ul>
 * <li> classes are annotated with a codebase URL from where the class
 *      can be loaded (if available), and
 * <li> any remote object in the <code>MarshalledObject</code> is
 *      represented by a serialized instance of its stub.
 * </ul>
 *
 * <p>When copy of the object is retrieved (via the <code>get</code> method),
 * if the class is not available locally, it will be loaded from the
 * appropriate location (specified the URL annotated with the class descriptor
 * when the class was serialized.
 *
 * <p><code>MarshalledObject</code> facilitates passing objects in RMI calls
 * that are not automatically deserialized immediately by the remote peer.
 *
 * <p>
 *  <code> MarshalledObject </code>包含一个字节流,其中包含一个给定给它的构造函数的对象的序列化表示。
 *  <code> get </code>方法返回原始对象的新副本,与包含的字节流反序列化。
 * 包含的对象被序列化和反序列化,具有用于编组和解组的参数和RMI调用的返回值的相同的序列化语义：创建序列化表单时：。
 * 
 * <ul>
 *  <li>类使用可以加载类的代码库URL(如果可用)进行注释,<li> <code> MarshalledObject </code>中的任何远程对象由其存根的序列化实例表示。
 * </ul>
 * 
 *  <p>当检索对象的副本时(通过<code> get </code>方法),如果类在本地不可用,那么将从适当的位置加载它(指定了用类描述符注释的URL该类被序列化。
 * 
 *  <p> <code> MarshalledObject </code>有助于在远程对等体不立即自动反序列化的RMI调用中传递对象。
 * 
 * 
 * @param <T> the type of the object contained in this
 * <code>MarshalledObject</code>
 *
 * @author  Ann Wollrath
 * @author  Peter Jones
 * @since   1.2
 */
public final class MarshalledObject<T> implements Serializable {
    /**
    /* <p>
    /* 
     * @serial Bytes of serialized representation.  If <code>objBytes</code> is
     * <code>null</code> then the object marshalled was a <code>null</code>
     * reference.
     */
    private byte[] objBytes = null;

    /**
    /* <p>
    /* 
     * @serial Bytes of location annotations, which are ignored by
     * <code>equals</code>.  If <code>locBytes</code> is null, there were no
     * non-<code>null</code> annotations during marshalling.
     */
    private byte[] locBytes = null;

    /**
    /* <p>
    /* 
     * @serial Stored hash code of contained object.
     *
     * @see #hashCode
     */
    private int hash;

    /** Indicate compatibility with 1.2 version of class. */
    private static final long serialVersionUID = 8988374069173025854L;

    /**
     * Creates a new <code>MarshalledObject</code> that contains the
     * serialized representation of the current state of the supplied object.
     * The object is serialized with the semantics used for marshaling
     * parameters for RMI calls.
     *
     * <p>
     *  创建一个新的<code> MarshalledObject </code>,它包含所提供对象的当前状态的序列化表示。对象使用用于RMI调用的调度参数的语义序列化。
     * 
     * 
     * @param obj the object to be serialized (must be serializable)
     * @exception IOException if an <code>IOException</code> occurs; an
     * <code>IOException</code> may occur if <code>obj</code> is not
     * serializable.
     * @since 1.2
     */
    public MarshalledObject(T obj) throws IOException {
        if (obj == null) {
            hash = 13;
            return;
        }

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ByteArrayOutputStream lout = new ByteArrayOutputStream();
        MarshalledObjectOutputStream out =
            new MarshalledObjectOutputStream(bout, lout);
        out.writeObject(obj);
        out.flush();
        objBytes = bout.toByteArray();
        // locBytes is null if no annotations
        locBytes = (out.hadAnnotations() ? lout.toByteArray() : null);

        /*
         * Calculate hash from the marshalled representation of object
         * so the hashcode will be comparable when sent between VMs.
         * <p>
         * 从对象的编组表示计算哈希,以便在VM之间发送时哈希码将是可比较的。
         * 
         */
        int h = 0;
        for (int i = 0; i < objBytes.length; i++) {
            h = 31 * h + objBytes[i];
        }
        hash = h;
    }

    /**
     * Returns a new copy of the contained marshalledobject.  The internal
     * representation is deserialized with the semantics used for
     * unmarshaling parameters for RMI calls.
     *
     * <p>
     *  返回所包含的marshalled对象的新副本。内部表示使用用于对RMI调用的解组参数的语义反序列化。
     * 
     * 
     * @return a copy of the contained object
     * @exception IOException if an <code>IOException</code> occurs while
     * deserializing the object from its internal representation.
     * @exception ClassNotFoundException if a
     * <code>ClassNotFoundException</code> occurs while deserializing the
     * object from its internal representation.
     * could not be found
     * @since 1.2
     */
    public T get() throws IOException, ClassNotFoundException {
        if (objBytes == null)   // must have been a null object
            return null;

        ByteArrayInputStream bin = new ByteArrayInputStream(objBytes);
        // locBytes is null if no annotations
        ByteArrayInputStream lin =
            (locBytes == null ? null : new ByteArrayInputStream(locBytes));
        MarshalledObjectInputStream in =
            new MarshalledObjectInputStream(bin, lin);
        @SuppressWarnings("unchecked")
        T obj = (T) in.readObject();
        in.close();
        return obj;
    }

    /**
     * Return a hash code for this <code>MarshalledObject</code>.
     *
     * <p>
     *  返回这个<code> MarshalledObject </code>的哈希码。
     * 
     * 
     * @return a hash code
     */
    public int hashCode() {
        return hash;
    }

    /**
     * Compares this <code>MarshalledObject</code> to another object.
     * Returns true if and only if the argument refers to a
     * <code>MarshalledObject</code> that contains exactly the same
     * serialized representation of an object as this one does. The
     * comparison ignores any class codebase annotation, meaning that
     * two objects are equivalent if they have the same serialized
     * representation <i>except</i> for the codebase of each class
     * in the serialized representation.
     *
     * <p>
     *  将此<code> MarshalledObject </code>与另一个对象进行比较。
     * 当且仅当参数引用包含与此对象完全相同的序列化表示的<code> MarshalledObject </code>时,返回true。
     * 比较忽略任何类的代码库注释,这意味着如果两个对象对于序列化表示中的每个类的代码库具有相同的序列化表示<i>(除了</i>),则两个对象是等价的。
     * 
     * 
     * @param obj the object to compare with this <code>MarshalledObject</code>
     * @return <code>true</code> if the argument contains an equivalent
     * serialized object; <code>false</code> otherwise
     * @since 1.2
     */
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj != null && obj instanceof MarshalledObject) {
            MarshalledObject<?> other = (MarshalledObject<?>) obj;

            // if either is a ref to null, both must be
            if (objBytes == null || other.objBytes == null)
                return objBytes == other.objBytes;

            // quick, easy test
            if (objBytes.length != other.objBytes.length)
                return false;

            //!! There is talk about adding an array comparision method
            //!! at 1.2 -- if so, this should be rewritten.  -arnold
            for (int i = 0; i < objBytes.length; ++i) {
                if (objBytes[i] != other.objBytes[i])
                    return false;
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * This class is used to marshal objects for
     * <code>MarshalledObject</code>.  It places the location annotations
     * to one side so that two <code>MarshalledObject</code>s can be
     * compared for equality if they differ only in location
     * annotations.  Objects written using this stream should be read back
     * from a <code>MarshalledObjectInputStream</code>.
     *
     * <p>
     *  这个类用于封装<code> MarshalledObject </code>的对象。
     * 它将位置注释置于一侧,使得两个<code> MarshalledObject </code>可以被比较为相等,如果它们仅在位置注释不同。
     * 使用此流写入的对象应从<code> MarshalledObjectInputStream </code>中读回。
     * 
     * 
     * @see java.rmi.MarshalledObject
     * @see MarshalledObjectInputStream
     */
    private static class MarshalledObjectOutputStream
        extends MarshalOutputStream
    {
        /** The stream on which location objects are written. */
        private ObjectOutputStream locOut;

        /** <code>true</code> if non-<code>null</code> annotations are
         *  written.
         * <p>
         *  书面。
         * 
         */
        private boolean hadAnnotations;

        /**
         * Creates a new <code>MarshalledObjectOutputStream</code> whose
         * non-location bytes will be written to <code>objOut</code> and whose
         * location annotations (if any) will be written to
         * <code>locOut</code>.
         * <p>
         *  创建一个新的<code> MarshalledObjectOutputStream </code>,其非位置字节将被写入<code> objOut </code>,其位置注释(如果有)将被写入<code>
         *  locOut </code>。
         * 
         */
        MarshalledObjectOutputStream(OutputStream objOut, OutputStream locOut)
            throws IOException
        {
            super(objOut);
            this.useProtocolVersion(ObjectStreamConstants.PROTOCOL_VERSION_2);
            this.locOut = new ObjectOutputStream(locOut);
            hadAnnotations = false;
        }

        /**
         * Returns <code>true</code> if any non-<code>null</code> location
         * annotations have been written to this stream.
         * <p>
         *  如果任何非<code> null </code>位置注释已写入此流,则返回<code> true </code>。
         * 
         */
        boolean hadAnnotations() {
            return hadAnnotations;
        }

        /**
         * Overrides MarshalOutputStream.writeLocation implementation to write
         * annotations to the location stream.
         * <p>
         * 覆盖MarshalOutputStream.writeLocation实现,以将注释写入位置流。
         * 
         */
        protected void writeLocation(String loc) throws IOException {
            hadAnnotations |= (loc != null);
            locOut.writeObject(loc);
        }


        public void flush() throws IOException {
            super.flush();
            locOut.flush();
        }
    }

    /**
     * The counterpart to <code>MarshalledObjectOutputStream</code>.
     *
     * <p>
     *  <code> MarshalledObjectOutputStream </code>的对应部分。
     * 
     * 
     * @see MarshalledObjectOutputStream
     */
    private static class MarshalledObjectInputStream
        extends MarshalInputStream
    {
        /**
         * The stream from which annotations will be read.  If this is
         * <code>null</code>, then all annotations were <code>null</code>.
         * <p>
         *  将读取注释的流。如果这是<code> null </code>,那么所有注释都是<code> null </code>。
         * 
         */
        private ObjectInputStream locIn;

        /**
         * Creates a new <code>MarshalledObjectInputStream</code> that
         * reads its objects from <code>objIn</code> and annotations
         * from <code>locIn</code>.  If <code>locIn</code> is
         * <code>null</code>, then all annotations will be
         * <code>null</code>.
         * <p>
         *  创建一个新的<code> MarshalledObjectInputStream </code>,从<code> objIn </code>读取其对象,从<code> locIn </code>读取注
         * 释。
         * 如果<code> locIn </code>是<code> null </code>,那么所有注释将是<code> null </code>。
         * 
         */
        MarshalledObjectInputStream(InputStream objIn, InputStream locIn)
            throws IOException
        {
            super(objIn);
            this.locIn = (locIn == null ? null : new ObjectInputStream(locIn));
        }

        /**
         * Overrides MarshalInputStream.readLocation to return locations from
         * the stream we were given, or <code>null</code> if we were given a
         * <code>null</code> location stream.
         * <p>
         */
        protected Object readLocation()
            throws IOException, ClassNotFoundException
        {
            return (locIn == null ? null : locIn.readObject());
        }
    }

}
