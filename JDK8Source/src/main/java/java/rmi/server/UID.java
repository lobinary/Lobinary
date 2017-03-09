/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2011, Oracle and/or its affiliates. All rights reserved.
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

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;
import java.security.SecureRandom;

/**
 * A <code>UID</code> represents an identifier that is unique over time
 * with respect to the host it is generated on, or one of 2<sup>16</sup>
 * "well-known" identifiers.
 *
 * <p>The {@link #UID()} constructor can be used to generate an
 * identifier that is unique over time with respect to the host it is
 * generated on.  The {@link #UID(short)} constructor can be used to
 * create one of 2<sup>16</sup> well-known identifiers.
 *
 * <p>A <code>UID</code> instance contains three primitive values:
 * <ul>
 * <li><code>unique</code>, an <code>int</code> that uniquely identifies
 * the VM that this <code>UID</code> was generated in, with respect to its
 * host and at the time represented by the <code>time</code> value (an
 * example implementation of the <code>unique</code> value would be a
 * process identifier),
 *  or zero for a well-known <code>UID</code>
 * <li><code>time</code>, a <code>long</code> equal to a time (as returned
 * by {@link System#currentTimeMillis()}) at which the VM that this
 * <code>UID</code> was generated in was alive,
 * or zero for a well-known <code>UID</code>
 * <li><code>count</code>, a <code>short</code> to distinguish
 * <code>UID</code>s generated in the same VM with the same
 * <code>time</code> value
 * </ul>
 *
 * <p>An independently generated <code>UID</code> instance is unique
 * over time with respect to the host it is generated on as long as
 * the host requires more than one millisecond to reboot and its system
 * clock is never set backward.  A globally unique identifier can be
 * constructed by pairing a <code>UID</code> instance with a unique host
 * identifier, such as an IP address.
 *
 * <p>
 *  <code> UID </code>表示相对于其在其上生成的主机,或者2个<sup> 16 </sup>"公知"标识符之一而随时间是唯一的标识符。
 * 
 *  <p> {@link #UID()}构造函数可用于生成相对于其生成的主机而随时间唯一的标识符。
 *  {@link #UID(short)}构造函数可用于创建2个<sup> 16 </sup>众所周知的标识符之一。
 * 
 *  <p> <code> UID </code>实例包含三个基本值：
 * <ul>
 *  </li> <code> </code> <code> unique </code>是唯一标识该<code> UID </code>通过<code> time </code>值(<code> uniq
 * ue </code>值的示例实现将是进程标识符),或者为众所周知的<code> UID </code> <li > <code> time </code>,一个<code> long </code>等于
 * 一个时间(由{@link System#currentTimeMillis()}返回) >在<al> </>> <u> <u>生成</u> </u> <u> </u> / code>在相同的VM中生成,
 * 具有相同的<code> time </code>值。
 * </ul>
 * 
 * <p>独立生成的<code> UID </code>实例相对于它生成的主机而言是独一无二的,只要主机需要超过一毫秒的时间重新启动,并且其系统时钟从不向后设置。
 * 可以通过将<code> UID </code>实例与唯一主机标识符(例如IP地址)配对来构建全局唯一标识符。
 * 
 * 
 * @author      Ann Wollrath
 * @author      Peter Jones
 * @since       JDK1.1
 */
public final class UID implements Serializable {

    private static int hostUnique;
    private static boolean hostUniqueSet = false;

    private static final Object lock = new Object();
    private static long lastTime = System.currentTimeMillis();
    private static short lastCount = Short.MIN_VALUE;

    /** indicate compatibility with JDK 1.1.x version of class */
    private static final long serialVersionUID = 1086053664494604050L;

    /**
     * number that uniquely identifies the VM that this <code>UID</code>
     * was generated in with respect to its host and at the given time
     * <p>
     *  唯一标识该VM相对于其主机在给定时间生成该<code> UID </code>的数字
     * 
     * 
     * @serial
     */
    private final int unique;

    /**
     * a time (as returned by {@link System#currentTimeMillis()}) at which
     * the VM that this <code>UID</code> was generated in was alive
     * <p>
     *  生成此<code> UID </code>的VM所活动的时间(由{@link System#currentTimeMillis()}返回)
     * 
     * 
     * @serial
     */
    private final long time;

    /**
     * 16-bit number to distinguish <code>UID</code> instances created
     * in the same VM with the same time value
     * <p>
     *  16位数来区分在相同VM中创建的具有相同时间值的<code> UID </code>实例
     * 
     * 
     * @serial
     */
    private final short count;

    /**
     * Generates a <code>UID</code> that is unique over time with
     * respect to the host that it was generated on.
     * <p>
     *  生成相对于其生成的主机,随着时间的推移唯一的<code> UID </code>。
     * 
     */
    public UID() {

        synchronized (lock) {
            if (!hostUniqueSet) {
                hostUnique = (new SecureRandom()).nextInt();
                hostUniqueSet = true;
            }
            unique = hostUnique;
            if (lastCount == Short.MAX_VALUE) {
                boolean interrupted = Thread.interrupted();
                boolean done = false;
                while (!done) {
                    long now = System.currentTimeMillis();
                    if (now == lastTime) {
                        // wait for time to change
                        try {
                            Thread.sleep(1);
                        } catch (InterruptedException e) {
                            interrupted = true;
                        }
                    } else {
                        // If system time has gone backwards increase
                        // original by 1ms to maintain uniqueness
                        lastTime = (now < lastTime) ? lastTime+1 : now;
                        lastCount = Short.MIN_VALUE;
                        done = true;
                    }
                }
                if (interrupted) {
                    Thread.currentThread().interrupt();
                }
            }
            time = lastTime;
            count = lastCount++;
        }
    }

    /**
     * Creates a "well-known" <code>UID</code>.
     *
     * There are 2<sup>16</sup> possible such well-known ids.
     *
     * <p>A <code>UID</code> created via this constructor will not
     * clash with any <code>UID</code>s generated via the no-arg
     * constructor.
     *
     * <p>
     *  创建"众所周知的"<code> UID </code>。
     * 
     *  有2个<sup> 16 </sup>可能的这种众所周知的id。
     * 
     *  <p>通过此构造函数创建的<code> UID </code>不会与通过no-arg构造函数生成的任何<code> UID </code>冲突。
     * 
     * 
     * @param   num number for well-known <code>UID</code>
     */
    public UID(short num) {
        unique = 0;
        time = 0;
        count = num;
    }

    /**
     * Constructs a <code>UID</code> given data read from a stream.
     * <p>
     *  构造从流中读取的给定数据的<code> UID </code>。
     * 
     */
    private UID(int unique, long time, short count) {
        this.unique = unique;
        this.time = time;
        this.count = count;
    }

    /**
     * Returns the hash code value for this <code>UID</code>.
     *
     * <p>
     *  返回此<code> UID </code>的哈希码值。
     * 
     * 
     * @return  the hash code value for this <code>UID</code>
     */
    public int hashCode() {
        return (int) time + (int) count;
    }

    /**
     * Compares the specified object with this <code>UID</code> for
     * equality.
     *
     * This method returns <code>true</code> if and only if the
     * specified object is a <code>UID</code> instance with the same
     * <code>unique</code>, <code>time</code>, and <code>count</code>
     * values as this one.
     *
     * <p>
     *  将指定的对象与此<code> UID </code>进行比较以实现相等。
     * 
     *  当且仅当指定的对象是具有相同<code>唯一</code>,<code>时</code>的<code> UID </code>实例时,此方法返回<code> true </code>和<code> c
     * ount </code>值。
     * 
     * 
     * @param   obj the object to compare this <code>UID</code> to
     *
     * @return  <code>true</code> if the given object is equivalent to
     * this one, and <code>false</code> otherwise
     */
    public boolean equals(Object obj) {
        if (obj instanceof UID) {
            UID uid = (UID) obj;
            return (unique == uid.unique &&
                    count == uid.count &&
                    time == uid.time);
        } else {
            return false;
        }
    }

    /**
     * Returns a string representation of this <code>UID</code>.
     *
     * <p>
     * 返回此<code> UID </code>的字符串表示形式。
     * 
     * 
     * @return  a string representation of this <code>UID</code>
     */
    public String toString() {
        return Integer.toString(unique,16) + ":" +
            Long.toString(time,16) + ":" +
            Integer.toString(count,16);
    }

    /**
     * Marshals a binary representation of this <code>UID</code> to
     * a <code>DataOutput</code> instance.
     *
     * <p>Specifically, this method first invokes the given stream's
     * {@link DataOutput#writeInt(int)} method with this <code>UID</code>'s
     * <code>unique</code> value, then it invokes the stream's
     * {@link DataOutput#writeLong(long)} method with this <code>UID</code>'s
     * <code>time</code> value, and then it invokes the stream's
     * {@link DataOutput#writeShort(int)} method with this <code>UID</code>'s
     * <code>count</code> value.
     *
     * <p>
     *  将此<code> UID </code>的二进制表示封装到<code> DataOutput </code>实例中。
     * 
     *  <p>具体来说,此方法首先使用此<code> UID </code>的<code>唯一</code>值调用给定流的{@link DataOutput#writeInt(int)}方法,然后调用流使用此
     * <code> UID </code>的<code> time </code>值的{@link DataOutput#writeLong(long)}方法,然后调用流的{@link DataOutput#writeShort与此<code> UID </code>的<code> count </code>值。
     * 
     * 
     * @param   out the <code>DataOutput</code> instance to write
     * this <code>UID</code> to
     *
     * @throws  IOException if an I/O error occurs while performing
     * this operation
     */
    public void write(DataOutput out) throws IOException {
        out.writeInt(unique);
        out.writeLong(time);
        out.writeShort(count);
    }

    /**
     * Constructs and returns a new <code>UID</code> instance by
     * unmarshalling a binary representation from an
     * <code>DataInput</code> instance.
     *
     * <p>Specifically, this method first invokes the given stream's
     * {@link DataInput#readInt()} method to read a <code>unique</code> value,
     * then it invoke's the stream's
     * {@link DataInput#readLong()} method to read a <code>time</code> value,
     * then it invoke's the stream's
     * {@link DataInput#readShort()} method to read a <code>count</code> value,
     * and then it creates and returns a new <code>UID</code> instance
     * that contains the <code>unique</code>, <code>time</code>, and
     * <code>count</code> values that were read from the stream.
     *
     * <p>
     *  通过从<code> DataInput </code>实例解组合二进制表示,构造并返回一个新的<code> UID </code>实例。
     * 
     *  <p>具体来说,此方法首先调用给定流的{@link DataInput#readInt()}方法读取<code>唯一</code>值,然后调用流的{@link DataInput#readLong()}
     * 
     * @param   in the <code>DataInput</code> instance to read
     * <code>UID</code> from
     *
     * @return  unmarshalled <code>UID</code> instance
     *
     * @throws  IOException if an I/O error occurs while performing
     * this operation
     */
    public static UID read(DataInput in) throws IOException {
        int unique = in.readInt();
        long time = in.readLong();
        short count = in.readShort();
        return new UID(unique, time, count);
    }
}
