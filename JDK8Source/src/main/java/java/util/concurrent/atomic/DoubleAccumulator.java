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
import java.util.function.DoubleBinaryOperator;

/**
 * One or more variables that together maintain a running {@code double}
 * value updated using a supplied function.  When updates (method
 * {@link #accumulate}) are contended across threads, the set of variables
 * may grow dynamically to reduce contention.  Method {@link #get}
 * (or, equivalently, {@link #doubleValue}) returns the current value
 * across the variables maintaining updates.
 *
 * <p>This class is usually preferable to alternatives when multiple
 * threads update a common value that is used for purposes such as
 * summary statistics that are frequently updated but less frequently
 * read.
 *
 * <p>The supplied accumulator function should be side-effect-free,
 * since it may be re-applied when attempted updates fail due to
 * contention among threads. The function is applied with the current
 * value as its first argument, and the given update as the second
 * argument.  For example, to maintain a running maximum value, you
 * could supply {@code Double::max} along with {@code
 * Double.NEGATIVE_INFINITY} as the identity. The order of
 * accumulation within or across threads is not guaranteed. Thus, this
 * class may not be applicable if numerical stability is required,
 * especially when combining values of substantially different orders
 * of magnitude.
 *
 * <p>Class {@link DoubleAdder} provides analogs of the functionality
 * of this class for the common special case of maintaining sums.  The
 * call {@code new DoubleAdder()} is equivalent to {@code new
 * DoubleAccumulator((x, y) -> x + y, 0.0)}.
 *
 * <p>This class extends {@link Number}, but does <em>not</em> define
 * methods such as {@code equals}, {@code hashCode} and {@code
 * compareTo} because instances are expected to be mutated, and so are
 * not useful as collection keys.
 *
 * <p>
 *  一个或多个变量一起使用提供的函数更新运行的{@code double}值。当更新(方法{@link #accumulate})在线程之间竞争时,变量集可以动态增长以减少争用。
 * 方法{@link #get}(或者等效地,{@link #doubleValue})返回保持更新的变量的当前值。
 * 
 *  <p>当多个线程更新用于诸如经常更新但较不频繁读取的汇总统计信息的公共值时,此类通常优先于替代。
 * 
 *  <p>所提供的累加器函数应该是无副作用的,因为由于线程之间的争用,当尝试更新失败时,它可能会重新应用。该函数应用当前值作为其第一个参数,给定的更新作为第二个参数。
 * 例如,为了保持运行最大值,您可以提供{@code Double :: max}以及{@code Double.NEGATIVE_INFINITY}作为身份。不保证线程内或线程内的累积顺序。
 * 因此,如果需要数值稳定性,这个类可能不适用,特别是当组合实质上不同数量级的值时。
 * 
 * <p>类{@link DoubleAdder}提供此类别的功能类型,用于维护和的常见特殊情况。
 * 调用{@code new DoubleAdder()}相当于{@code new DoubleAccumulator((x,y) - > x + y,0.0)}。
 * 
 *  <p>此类扩展了{@link Number},但不会</em>定义诸如{@code equals},{@code hashCode}和{@code compareTo}等方法,因为实例会被改变,因此不
 * 可用作收集键。
 * 
 * 
 * @since 1.8
 * @author Doug Lea
 */
public class DoubleAccumulator extends Striped64 implements Serializable {
    private static final long serialVersionUID = 7249069246863182397L;

    private final DoubleBinaryOperator function;
    private final long identity; // use long representation

    /**
     * Creates a new instance using the given accumulator function
     * and identity element.
     * <p>
     *  使用给定的累加器函数和标识元素创建一个新实例。
     * 
     * 
     * @param accumulatorFunction a side-effect-free function of two arguments
     * @param identity identity (initial value) for the accumulator function
     */
    public DoubleAccumulator(DoubleBinaryOperator accumulatorFunction,
                             double identity) {
        this.function = accumulatorFunction;
        base = this.identity = Double.doubleToRawLongBits(identity);
    }

    /**
     * Updates with the given value.
     *
     * <p>
     *  使用给定值更新。
     * 
     * 
     * @param x the value
     */
    public void accumulate(double x) {
        Cell[] as; long b, v, r; int m; Cell a;
        if ((as = cells) != null ||
            (r = Double.doubleToRawLongBits
             (function.applyAsDouble
              (Double.longBitsToDouble(b = base), x))) != b  && !casBase(b, r)) {
            boolean uncontended = true;
            if (as == null || (m = as.length - 1) < 0 ||
                (a = as[getProbe() & m]) == null ||
                !(uncontended =
                  (r = Double.doubleToRawLongBits
                   (function.applyAsDouble
                    (Double.longBitsToDouble(v = a.value), x))) == v ||
                  a.cas(v, r)))
                doubleAccumulate(x, function, uncontended);
        }
    }

    /**
     * Returns the current value.  The returned value is <em>NOT</em>
     * an atomic snapshot; invocation in the absence of concurrent
     * updates returns an accurate result, but concurrent updates that
     * occur while the value is being calculated might not be
     * incorporated.
     *
     * <p>
     *  返回当前值。返回的值是<em> NOT </em>原子快照;在没有并发更新的情况下调用将返回准确的结果,但是在计算值时发生的并发更新可能不会合并。
     * 
     * 
     * @return the current value
     */
    public double get() {
        Cell[] as = cells; Cell a;
        double result = Double.longBitsToDouble(base);
        if (as != null) {
            for (int i = 0; i < as.length; ++i) {
                if ((a = as[i]) != null)
                    result = function.applyAsDouble
                        (result, Double.longBitsToDouble(a.value));
            }
        }
        return result;
    }

    /**
     * Resets variables maintaining updates to the identity value.
     * This method may be a useful alternative to creating a new
     * updater, but is only effective if there are no concurrent
     * updates.  Because this method is intrinsically racy, it should
     * only be used when it is known that no threads are concurrently
     * updating.
     * <p>
     *  重置保持对标识值更新的变量。此方法可能是创建新的更新程序的有用的替代方法,但仅在没有并发更新时有效。因为这种方法本质上是有趣的,所以它应该只在已知没有线程同时更新时才使用。
     * 
     */
    public void reset() {
        Cell[] as = cells; Cell a;
        base = identity;
        if (as != null) {
            for (int i = 0; i < as.length; ++i) {
                if ((a = as[i]) != null)
                    a.value = identity;
            }
        }
    }

    /**
     * Equivalent in effect to {@link #get} followed by {@link
     * #reset}. This method may apply for example during quiescent
     * points between multithreaded computations.  If there are
     * updates concurrent with this method, the returned value is
     * <em>not</em> guaranteed to be the final value occurring before
     * the reset.
     *
     * <p>
     *  等同于{@link #get},后跟{@link #reset}。该方法可以应用于例如在多线程计算之间的静止点期间。如果存在与该方法并发的更新,则返回的值</em>不被保证为在复位之前发生的最终值。
     * 
     * 
     * @return the value before reset
     */
    public double getThenReset() {
        Cell[] as = cells; Cell a;
        double result = Double.longBitsToDouble(base);
        base = identity;
        if (as != null) {
            for (int i = 0; i < as.length; ++i) {
                if ((a = as[i]) != null) {
                    double v = Double.longBitsToDouble(a.value);
                    a.value = identity;
                    result = function.applyAsDouble(result, v);
                }
            }
        }
        return result;
    }

    /**
     * Returns the String representation of the current value.
     * <p>
     * 返回当前值的字符串表示形式。
     * 
     * 
     * @return the String representation of the current value
     */
    public String toString() {
        return Double.toString(get());
    }

    /**
     * Equivalent to {@link #get}.
     *
     * <p>
     *  相当于{@link #get}。
     * 
     * 
     * @return the current value
     */
    public double doubleValue() {
        return get();
    }

    /**
     * Returns the {@linkplain #get current value} as a {@code long}
     * after a narrowing primitive conversion.
     * <p>
     *  在缩小的基本转换后,将{@linkplain #get current value}返回为{@code long}。
     * 
     */
    public long longValue() {
        return (long)get();
    }

    /**
     * Returns the {@linkplain #get current value} as an {@code int}
     * after a narrowing primitive conversion.
     * <p>
     *  在缩小的基本转换后,将{@linkplain #get current value}返回为{@code int}。
     * 
     */
    public int intValue() {
        return (int)get();
    }

    /**
     * Returns the {@linkplain #get current value} as a {@code float}
     * after a narrowing primitive conversion.
     * <p>
     *  在缩小的基本转换之后,将{@linkplain #get current value}返回为{@code float}。
     * 
     */
    public float floatValue() {
        return (float)get();
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
         * The current value returned by get().
         * <p>
         *  get()返回的当前值。
         * 
         * 
         * @serial
         */
        private final double value;
        /**
         * The function used for updates.
         * <p>
         *  用于更新的函数。
         * 
         * 
         * @serial
         */
        private final DoubleBinaryOperator function;
        /**
         * The identity value
         * <p>
         *  身份值
         * 
         * 
         * @serial
         */
        private final long identity;

        SerializationProxy(DoubleAccumulator a) {
            function = a.function;
            identity = a.identity;
            value = a.get();
        }

        /**
         * Returns a {@code DoubleAccumulator} object with initial state
         * held by this proxy.
         *
         * <p>
         *  返回由此代理保持的初始状态的{@code DoubleAccumulator}对象。
         * 
         * 
         * @return a {@code DoubleAccumulator} object with initial state
         * held by this proxy.
         */
        private Object readResolve() {
            double d = Double.longBitsToDouble(identity);
            DoubleAccumulator a = new DoubleAccumulator(function, d);
            a.base = Double.doubleToRawLongBits(value);
            return a;
        }
    }

    /**
     * Returns a
     * <a href="../../../../serialized-form.html#java.util.concurrent.atomic.DoubleAccumulator.SerializationProxy">
     * SerializationProxy</a>
     * representing the state of this instance.
     *
     * <p>
     *  返回a
     * <a href="../../../../serialized-form.html#java.util.concurrent.atomic.DoubleAccumulator.SerializationProxy">
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
