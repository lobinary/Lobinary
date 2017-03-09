/***** Lobxxx Translate Finished ******/
/*
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

/*
 *
 *
 *
 *
 *
 * Written by Doug Lea with assistance from members of JCP JSR-166
 * Expert Group and released to the public domain, as explained at
 * http://creativecommons.org/publicdomain/zero/1.0/
 * <p>
 *  由Doug Lea在JCP JSR-166专家组成员的帮助下撰写,并发布到公共领域,如http://creativecommons.org/publicdomain/zero/1.0/
 * 
 */

package java.util.concurrent.atomic;
import java.io.Serializable;

/**
 * One or more variables that together maintain an initially zero
 * {@code double} sum.  When updates (method {@link #add}) are
 * contended across threads, the set of variables may grow dynamically
 * to reduce contention.  Method {@link #sum} (or, equivalently {@link
 * #doubleValue}) returns the current total combined across the
 * variables maintaining the sum. The order of accumulation within or
 * across threads is not guaranteed. Thus, this class may not be
 * applicable if numerical stability is required, especially when
 * combining values of substantially different orders of magnitude.
 *
 * <p>This class is usually preferable to alternatives when multiple
 * threads update a common value that is used for purposes such as
 * summary statistics that are frequently updated but less frequently
 * read.
 *
 * <p>This class extends {@link Number}, but does <em>not</em> define
 * methods such as {@code equals}, {@code hashCode} and {@code
 * compareTo} because instances are expected to be mutated, and so are
 * not useful as collection keys.
 *
 * <p>
 *  一个或多个变量一起维持最初为零的{@code double}和。当更新(方法{@link #add})在线程之间竞争时,变量集可以动态增长以减少争用。
 * 方法{@link #sum}(或等效地{@link #doubleValue})返回保持总和的变量的当前总计。不保证线程内或线程内的累积顺序。
 * 因此,如果需要数值稳定性,这个类可能不适用,特别是当组合实质上不同数量级的值时。
 * 
 *  <p>当多个线程更新用于诸如经常更新但较不频繁读取的汇总统计信息的公共值时,此类通常优先于替代。
 * 
 *  <p>此类扩展了{@link Number},但不会</em>定义诸如{@code equals},{@code hashCode}和{@code compareTo}等方法,因为实例会被改变,因此不
 * 可用作收集键。
 * 
 * 
 * @since 1.8
 * @author Doug Lea
 */
public class DoubleAdder extends Striped64 implements Serializable {
    private static final long serialVersionUID = 7249069246863182397L;

    /*
     * Note that we must use "long" for underlying representations,
     * because there is no compareAndSet for double, due to the fact
     * that the bitwise equals used in any CAS implementation is not
     * the same as double-precision equals.  However, we use CAS only
     * to detect and alleviate contention, for which bitwise equals
     * works best anyway. In principle, the long/double conversions
     * used here should be essentially free on most platforms since
     * they just re-interpret bits.
     * <p>
     * 注意,对于底层表示,我们必须使用"long",因为没有compareAndSet用于double,这是因为在任何CAS实现中使用的按位等于不等于double-precision equals。
     * 然而,我们使用CAS来检测和缓解争用,其中逐位等于最好的工作。原则上,这里使用的长/双转换在大多数平台上应该基本上是自由的,因为它们只是重新解释比特。
     * 
     */

    /**
     * Creates a new adder with initial sum of zero.
     * <p>
     *  创建一个初始和为零的新加法器。
     * 
     */
    public DoubleAdder() {
    }

    /**
     * Adds the given value.
     *
     * <p>
     *  添加给定值。
     * 
     * 
     * @param x the value to add
     */
    public void add(double x) {
        Cell[] as; long b, v; int m; Cell a;
        if ((as = cells) != null ||
            !casBase(b = base,
                     Double.doubleToRawLongBits
                     (Double.longBitsToDouble(b) + x))) {
            boolean uncontended = true;
            if (as == null || (m = as.length - 1) < 0 ||
                (a = as[getProbe() & m]) == null ||
                !(uncontended = a.cas(v = a.value,
                                      Double.doubleToRawLongBits
                                      (Double.longBitsToDouble(v) + x))))
                doubleAccumulate(x, null, uncontended);
        }
    }

    /**
     * Returns the current sum.  The returned value is <em>NOT</em> an
     * atomic snapshot; invocation in the absence of concurrent
     * updates returns an accurate result, but concurrent updates that
     * occur while the sum is being calculated might not be
     * incorporated.  Also, because floating-point arithmetic is not
     * strictly associative, the returned result need not be identical
     * to the value that would be obtained in a sequential series of
     * updates to a single variable.
     *
     * <p>
     *  返回当前和。返回的值是<em> NOT </em>原子快照;在不存在并发更新的情况下调用返回准确的结果,但是在计算和时发生的并发更新可能不会合并。
     * 另外,因为浮点运算不是严格关联的,所以返回的结果不需要与在对单个变量的连续系列更新中获得的值相同。
     * 
     * 
     * @return the sum
     */
    public double sum() {
        Cell[] as = cells; Cell a;
        double sum = Double.longBitsToDouble(base);
        if (as != null) {
            for (int i = 0; i < as.length; ++i) {
                if ((a = as[i]) != null)
                    sum += Double.longBitsToDouble(a.value);
            }
        }
        return sum;
    }

    /**
     * Resets variables maintaining the sum to zero.  This method may
     * be a useful alternative to creating a new adder, but is only
     * effective if there are no concurrent updates.  Because this
     * method is intrinsically racy, it should only be used when it is
     * known that no threads are concurrently updating.
     * <p>
     *  重置将总和保持为零的变量。此方法可能是创建新加法器的有用替代方法,但仅在没有并发更新时才有效。因为这种方法本质上是有趣的,所以它应该只在已知没有线程同时更新时才使用。
     * 
     */
    public void reset() {
        Cell[] as = cells; Cell a;
        base = 0L; // relies on fact that double 0 must have same rep as long
        if (as != null) {
            for (int i = 0; i < as.length; ++i) {
                if ((a = as[i]) != null)
                    a.value = 0L;
            }
        }
    }

    /**
     * Equivalent in effect to {@link #sum} followed by {@link
     * #reset}. This method may apply for example during quiescent
     * points between multithreaded computations.  If there are
     * updates concurrent with this method, the returned value is
     * <em>not</em> guaranteed to be the final value occurring before
     * the reset.
     *
     * <p>
     * 等同于{@link #sum},后跟{@link #reset}。该方法可以应用于例如在多线程计算之间的静止点期间。如果存在与该方法并发的更新,则返回的值</em>不被保证为在复位之前发生的最终值。
     * 
     * 
     * @return the sum
     */
    public double sumThenReset() {
        Cell[] as = cells; Cell a;
        double sum = Double.longBitsToDouble(base);
        base = 0L;
        if (as != null) {
            for (int i = 0; i < as.length; ++i) {
                if ((a = as[i]) != null) {
                    long v = a.value;
                    a.value = 0L;
                    sum += Double.longBitsToDouble(v);
                }
            }
        }
        return sum;
    }

    /**
     * Returns the String representation of the {@link #sum}.
     * <p>
     *  返回{@link #sum}的String表示形式。
     * 
     * 
     * @return the String representation of the {@link #sum}
     */
    public String toString() {
        return Double.toString(sum());
    }

    /**
     * Equivalent to {@link #sum}.
     *
     * <p>
     *  相当于{@link #sum}。
     * 
     * 
     * @return the sum
     */
    public double doubleValue() {
        return sum();
    }

    /**
     * Returns the {@link #sum} as a {@code long} after a
     * narrowing primitive conversion.
     * <p>
     *  在缩小的基本转换后,将{@link #sum}返回为{@code long}。
     * 
     */
    public long longValue() {
        return (long)sum();
    }

    /**
     * Returns the {@link #sum} as an {@code int} after a
     * narrowing primitive conversion.
     * <p>
     *  在缩小的原始转换后,将{@link #sum}返回为{@code int}。
     * 
     */
    public int intValue() {
        return (int)sum();
    }

    /**
     * Returns the {@link #sum} as a {@code float}
     * after a narrowing primitive conversion.
     * <p>
     *  在缩小的原始转换后,将{@link #sum}返回为{@code float}。
     * 
     */
    public float floatValue() {
        return (float)sum();
    }

    /**
     * Serialization proxy, used to avoid reference to the non-public
     * Striped64 superclass in serialized forms.
     * <p>
     *  序列化代理,用于避免以序列化形式引用非公开的Striped64超类。
     * 
     * 
     * @serial include
     */
    private static class SerializationProxy implements Serializable {
        private static final long serialVersionUID = 7249069246863182397L;

        /**
         * The current value returned by sum().
         * <p>
         *  sum()返回的当前值。
         * 
         * 
         * @serial
         */
        private final double value;

        SerializationProxy(DoubleAdder a) {
            value = a.sum();
        }

        /**
         * Returns a {@code DoubleAdder} object with initial state
         * held by this proxy.
         *
         * <p>
         *  返回由此代理保持的初始状态的{@code DoubleAdder}对象。
         * 
         * 
         * @return a {@code DoubleAdder} object with initial state
         * held by this proxy.
         */
        private Object readResolve() {
            DoubleAdder a = new DoubleAdder();
            a.base = Double.doubleToRawLongBits(value);
            return a;
        }
    }

    /**
     * Returns a
     * <a href="../../../../serialized-form.html#java.util.concurrent.atomic.DoubleAdder.SerializationProxy">
     * SerializationProxy</a>
     * representing the state of this instance.
     *
     * <p>
     *  返回a
     * <a href="../../../../serialized-form.html#java.util.concurrent.atomic.DoubleAdder.SerializationProxy">
     *  SerializationProxy </a>表示此实例的状态。
     * 
     * @return a {@link SerializationProxy}
     * representing the state of this instance
     */
    private Object writeReplace() {
        return new SerializationProxy(this);
    }

    /**
    /* <p>
    /* 
    /* 
     * @param s the stream
     * @throws java.io.InvalidObjectException always
     */
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.InvalidObjectException {
        throw new java.io.InvalidObjectException("Proxy required");
    }

}
