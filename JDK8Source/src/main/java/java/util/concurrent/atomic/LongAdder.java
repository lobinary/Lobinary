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
 * {@code long} sum.  When updates (method {@link #add}) are contended
 * across threads, the set of variables may grow dynamically to reduce
 * contention. Method {@link #sum} (or, equivalently, {@link
 * #longValue}) returns the current total combined across the
 * variables maintaining the sum.
 *
 * <p>This class is usually preferable to {@link AtomicLong} when
 * multiple threads update a common sum that is used for purposes such
 * as collecting statistics, not for fine-grained synchronization
 * control.  Under low update contention, the two classes have similar
 * characteristics. But under high contention, expected throughput of
 * this class is significantly higher, at the expense of higher space
 * consumption.
 *
 * <p>LongAdders can be used with a {@link
 * java.util.concurrent.ConcurrentHashMap} to maintain a scalable
 * frequency map (a form of histogram or multiset). For example, to
 * add a count to a {@code ConcurrentHashMap<String,LongAdder> freqs},
 * initializing if not already present, you can use {@code
 * freqs.computeIfAbsent(k -> new LongAdder()).increment();}
 *
 * <p>This class extends {@link Number}, but does <em>not</em> define
 * methods such as {@code equals}, {@code hashCode} and {@code
 * compareTo} because instances are expected to be mutated, and so are
 * not useful as collection keys.
 *
 * <p>
 *  一个或多个变量一起维持最初为零的{@code long}和。当更新(方法{@link #add})在线程之间竞争时,变量集可以动态增长以减少争用。
 * 方法{@link #sum}(或等效地,{@link #longValue})返回保持总和的变量的当前总计。
 * 
 *  <p>当多个线程更新用于收集统计信息而不是用于细粒度同步控制的公共和时,此类通常优先于{@link AtomicLong}。在低更新争用下,这两个类具有类似的特性。
 * 但在高争用下,这类的预期吞吐量明显更高,以更高的空间消耗为代价。
 * 
 *  <p> LongAdders可以与{@link java.util.concurrent.ConcurrentHashMap}一起使用以维护可扩展的频率映射(直方图或多重集的形式)。
 * 例如,要向{@code ConcurrentHashMap <String,LongAdder> freqs}添加计数,初始化(如果尚未存在),可以使用{@code freqs.computeIfAbsent(k-> new LongAdder())。
 *  <p> LongAdders可以与{@link java.util.concurrent.ConcurrentHashMap}一起使用以维护可扩展的频率映射(直方图或多重集的形式)。 }}。
 * 
 * <p>此类扩展了{@link Number},但不会</em>定义诸如{@code equals},{@code hashCode}和{@code compareTo}等方法,因为实例会被改变,因此不可
 * 用作收集键。
 * 
 * 
 * @since 1.8
 * @author Doug Lea
 */
public class LongAdder extends Striped64 implements Serializable {
    private static final long serialVersionUID = 7249069246863182397L;

    /**
     * Creates a new adder with initial sum of zero.
     * <p>
     *  创建一个初始和为零的新加法器。
     * 
     */
    public LongAdder() {
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
    public void add(long x) {
        Cell[] as; long b, v; int m; Cell a;
        if ((as = cells) != null || !casBase(b = base, b + x)) {
            boolean uncontended = true;
            if (as == null || (m = as.length - 1) < 0 ||
                (a = as[getProbe() & m]) == null ||
                !(uncontended = a.cas(v = a.value, v + x)))
                longAccumulate(x, null, uncontended);
        }
    }

    /**
     * Equivalent to {@code add(1)}.
     * <p>
     *  相当于{@code add(1)}。
     * 
     */
    public void increment() {
        add(1L);
    }

    /**
     * Equivalent to {@code add(-1)}.
     * <p>
     *  相当于{@code add(-1)}。
     * 
     */
    public void decrement() {
        add(-1L);
    }

    /**
     * Returns the current sum.  The returned value is <em>NOT</em> an
     * atomic snapshot; invocation in the absence of concurrent
     * updates returns an accurate result, but concurrent updates that
     * occur while the sum is being calculated might not be
     * incorporated.
     *
     * <p>
     *  返回当前和。返回的值是<em> NOT </em>原子快照;在不存在并发更新的情况下调用返回准确的结果,但是在计算和时发生的并发更新可能不会合并。
     * 
     * 
     * @return the sum
     */
    public long sum() {
        Cell[] as = cells; Cell a;
        long sum = base;
        if (as != null) {
            for (int i = 0; i < as.length; ++i) {
                if ((a = as[i]) != null)
                    sum += a.value;
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
        base = 0L;
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
     *  等同于{@link #sum},后跟{@link #reset}。该方法可以应用于例如在多线程计算之间的静止点期间。如果存在与该方法并发的更新,则返回的值</em>不被保证为在复位之前发生的最终值。
     * 
     * 
     * @return the sum
     */
    public long sumThenReset() {
        Cell[] as = cells; Cell a;
        long sum = base;
        base = 0L;
        if (as != null) {
            for (int i = 0; i < as.length; ++i) {
                if ((a = as[i]) != null) {
                    sum += a.value;
                    a.value = 0L;
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
        return Long.toString(sum());
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
    public long longValue() {
        return sum();
    }

    /**
     * Returns the {@link #sum} as an {@code int} after a narrowing
     * primitive conversion.
     * <p>
     *  在缩小的原始转换后,将{@link #sum}返回为{@code int}。
     * 
     */
    public int intValue() {
        return (int)sum();
    }

    /**
     * Returns the {@link #sum} as a {@code float}
     * after a widening primitive conversion.
     * <p>
     *  在扩展基元转换后,将{@link #sum}返回为{@code float}。
     * 
     */
    public float floatValue() {
        return (float)sum();
    }

    /**
     * Returns the {@link #sum} as a {@code double} after a widening
     * primitive conversion.
     * <p>
     * 在扩展基元转换后返回{@link #sum}作为{@code double}。
     * 
     */
    public double doubleValue() {
        return (double)sum();
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
        private final long value;

        SerializationProxy(LongAdder a) {
            value = a.sum();
        }

        /**
         * Return a {@code LongAdder} object with initial state
         * held by this proxy.
         *
         * <p>
         *  返回一个由此代理持有的初始状态的{@code LongAdder}对象。
         * 
         * 
         * @return a {@code LongAdder} object with initial state
         * held by this proxy.
         */
        private Object readResolve() {
            LongAdder a = new LongAdder();
            a.base = value;
            return a;
        }
    }

    /**
     * Returns a
     * <a href="../../../../serialized-form.html#java.util.concurrent.atomic.LongAdder.SerializationProxy">
     * SerializationProxy</a>
     * representing the state of this instance.
     *
     * <p>
     *  返回a
     * <a href="../../../../serialized-form.html#java.util.concurrent.atomic.LongAdder.SerializationProxy">
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
