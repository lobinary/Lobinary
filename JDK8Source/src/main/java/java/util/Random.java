/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1995, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.util;
import java.io.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.StreamSupport;

import sun.misc.Unsafe;

/**
 * An instance of this class is used to generate a stream of
 * pseudorandom numbers. The class uses a 48-bit seed, which is
 * modified using a linear congruential formula. (See Donald Knuth,
 * <i>The Art of Computer Programming, Volume 2</i>, Section 3.2.1.)
 * <p>
 * If two instances of {@code Random} are created with the same
 * seed, and the same sequence of method calls is made for each, they
 * will generate and return identical sequences of numbers. In order to
 * guarantee this property, particular algorithms are specified for the
 * class {@code Random}. Java implementations must use all the algorithms
 * shown here for the class {@code Random}, for the sake of absolute
 * portability of Java code. However, subclasses of class {@code Random}
 * are permitted to use other algorithms, so long as they adhere to the
 * general contracts for all the methods.
 * <p>
 * The algorithms implemented by class {@code Random} use a
 * {@code protected} utility method that on each invocation can supply
 * up to 32 pseudorandomly generated bits.
 * <p>
 * Many applications will find the method {@link Math#random} simpler to use.
 *
 * <p>Instances of {@code java.util.Random} are threadsafe.
 * However, the concurrent use of the same {@code java.util.Random}
 * instance across threads may encounter contention and consequent
 * poor performance. Consider instead using
 * {@link java.util.concurrent.ThreadLocalRandom} in multithreaded
 * designs.
 *
 * <p>Instances of {@code java.util.Random} are not cryptographically
 * secure.  Consider instead using {@link java.security.SecureRandom} to
 * get a cryptographically secure pseudo-random number generator for use
 * by security-sensitive applications.
 *
 * <p>
 *  该类的实例用于生成伪随机数字流。
 * 该类使用48位种子,其使用线性同余公式修改(参见Donald Knuth,<The Art of Computer Programming,Volume 2 </i >,第321节)。
 * <p>
 * 如果使用相同的种子创建{@code Random}的两个实例,并为每个实例创建相同的方法调用序列,则它们将生成并返回相同的数字序列为了保证此属性,为class {@code Random}为了Java代
 * 码的绝对可移植性,Java实现必须使用这里所示的所有算法{@code Random}。
 * 然而,类{@code Random}的子类允许使用其他算法,只要他们遵守所有方法的一般合同。
 * <p>
 *  由类{@code Random}实现的算法使用{@code protected}实用程序方法,每次调用可提供最多32个伪随机生成位
 * <p>
 * 许多应用程序会发现{@link Math#random}方法更简单
 * 
 *  <p> {@code javautilRandom}的实例是线程安全的然而,跨线程并发使用相同的{@code javautilRandom}实例可能会遇到争用和随之而来的低性能考虑在多线程设计中使用{@link javautilconcurrentThreadLocalRandom}
 * 。
 * 
 *  <p> {@code javautilRandom}的实例不是加密安全的考虑改用{@link javasecuritySecureRandom}获得一个密码安全的伪随机数生成器,供安全敏感的应用程序使
 * 用。
 * 
 * 
 * @author  Frank Yellin
 * @since   1.0
 */
public
class Random implements java.io.Serializable {
    /** use serialVersionUID from JDK 1.1 for interoperability */
    static final long serialVersionUID = 3905348978240129619L;

    /**
     * The internal state associated with this pseudorandom number generator.
     * (The specs for the methods in this class describe the ongoing
     * computation of this value.)
     * <p>
     *  与该伪随机数生成器相关联的内部状态(该类中的方法的规范描述了该值的正在进行的计算)
     * 
     */
    private final AtomicLong seed;

    private static final long multiplier = 0x5DEECE66DL;
    private static final long addend = 0xBL;
    private static final long mask = (1L << 48) - 1;

    private static final double DOUBLE_UNIT = 0x1.0p-53; // 1.0 / (1L << 53)

    // IllegalArgumentException messages
    static final String BadBound = "bound must be positive";
    static final String BadRange = "bound must be greater than origin";
    static final String BadSize  = "size must be non-negative";

    /**
     * Creates a new random number generator. This constructor sets
     * the seed of the random number generator to a value very likely
     * to be distinct from any other invocation of this constructor.
     * <p>
     * 创建一个新的随机数生成器此构造函数将随机数生成器的种子设置为很可能与此构造函数的任何其他调用不同的值
     * 
     */
    public Random() {
        this(seedUniquifier() ^ System.nanoTime());
    }

    private static long seedUniquifier() {
        // L'Ecuyer, "Tables of Linear Congruential Generators of
        // Different Sizes and Good Lattice Structure", 1999
        for (;;) {
            long current = seedUniquifier.get();
            long next = current * 181783497276652981L;
            if (seedUniquifier.compareAndSet(current, next))
                return next;
        }
    }

    private static final AtomicLong seedUniquifier
        = new AtomicLong(8682522807148012L);

    /**
     * Creates a new random number generator using a single {@code long} seed.
     * The seed is the initial value of the internal state of the pseudorandom
     * number generator which is maintained by method {@link #next}.
     *
     * <p>The invocation {@code new Random(seed)} is equivalent to:
     *  <pre> {@code
     * Random rnd = new Random();
     * rnd.setSeed(seed);}</pre>
     *
     * <p>
     *  使用单个{@code long}种子创建新的随机数生成器种子是伪随机数生成器的内部状态的初始值,由方法{@link #next}维护
     * 
     *  <p>调用{@code new Random(seed)}等价于：<pre> {@code Random rnd = new Random(); rndsetSeed(seed);} </pre>
     * 
     * 
     * @param seed the initial seed
     * @see   #setSeed(long)
     */
    public Random(long seed) {
        if (getClass() == Random.class)
            this.seed = new AtomicLong(initialScramble(seed));
        else {
            // subclass might have overriden setSeed
            this.seed = new AtomicLong();
            setSeed(seed);
        }
    }

    private static long initialScramble(long seed) {
        return (seed ^ multiplier) & mask;
    }

    /**
     * Sets the seed of this random number generator using a single
     * {@code long} seed. The general contract of {@code setSeed} is
     * that it alters the state of this random number generator object
     * so as to be in exactly the same state as if it had just been
     * created with the argument {@code seed} as a seed. The method
     * {@code setSeed} is implemented by class {@code Random} by
     * atomically updating the seed to
     *  <pre>{@code (seed ^ 0x5DEECE66DL) & ((1L << 48) - 1)}</pre>
     * and clearing the {@code haveNextNextGaussian} flag used by {@link
     * #nextGaussian}.
     *
     * <p>The implementation of {@code setSeed} by class {@code Random}
     * happens to use only 48 bits of the given seed. In general, however,
     * an overriding method may use all 64 bits of the {@code long}
     * argument as a seed value.
     *
     * <p>
     * 使用单个{@code long}种子设置此随机数生成器的种子{@code setSeed}的一般约定是它更改此随机数生成器对象的状态,以便处于完全相同的状态,如果它刚刚使用参数{@code seed}作
     * 为种子创建{@code setSeed}方法是通过类{@code Random}通过将种子原子地更新为<pre> {@ code(seed ^ 0x5DEECE66DL)& (1L << 48)-1)} </pre>
     * 并清除{@link #nextGaussian}使用的{@code haveNextNextGaussian}。
     * 
     *  <p>类{@code Random}的{@code setSeed}的实现恰好只使用给定种子的48位。然而,一般来说,重写方法可以使用{@code long}参数的所有64位种子值
     * 
     * 
     * @param seed the initial seed
     */
    synchronized public void setSeed(long seed) {
        this.seed.set(initialScramble(seed));
        haveNextNextGaussian = false;
    }

    /**
     * Generates the next pseudorandom number. Subclasses should
     * override this, as this is used by all other methods.
     *
     * <p>The general contract of {@code next} is that it returns an
     * {@code int} value and if the argument {@code bits} is between
     * {@code 1} and {@code 32} (inclusive), then that many low-order
     * bits of the returned value will be (approximately) independently
     * chosen bit values, each of which is (approximately) equally
     * likely to be {@code 0} or {@code 1}. The method {@code next} is
     * implemented by class {@code Random} by atomically updating the seed to
     *  <pre>{@code (seed * 0x5DEECE66DL + 0xBL) & ((1L << 48) - 1)}</pre>
     * and returning
     *  <pre>{@code (int)(seed >>> (48 - bits))}.</pre>
     *
     * This is a linear congruential pseudorandom number generator, as
     * defined by D. H. Lehmer and described by Donald E. Knuth in
     * <i>The Art of Computer Programming,</i> Volume 3:
     * <i>Seminumerical Algorithms</i>, section 3.2.1.
     *
     * <p>
     * 生成下一个伪随机数子类应该覆盖此,因为这被所有其他方法使用
     * 
     *  <p> {@code next}的一般合约是它返回一个{@code int}值,如果参数{@code bits}位于{@code 1}和{@code 32}(含)之间,返回值的许多低阶位将是(近似)独
     * 立选择的位值,其中每一个(近似)同样可能是{@code 0}或{@code 1}方法{@code next}通过将种子原子地更新为<pre> {@ code(seed * 0x5DEECE66DL + 0xBL)&((1L << 48)-1)} </pre>
     * 并返回<pre> {@ code(int)(seed >>>(48-bits))} </pre>。
     * 
     * 这是由D H Lehmer定义的并且由Donald E Knuth在The Art of Computer Programming,第3卷："Seminumerical Algorithms",第321
     * 节中描述的线性同余伪随机数发生器。
     * 
     * 
     * @param  bits random bits
     * @return the next pseudorandom value from this random number
     *         generator's sequence
     * @since  1.1
     */
    protected int next(int bits) {
        long oldseed, nextseed;
        AtomicLong seed = this.seed;
        do {
            oldseed = seed.get();
            nextseed = (oldseed * multiplier + addend) & mask;
        } while (!seed.compareAndSet(oldseed, nextseed));
        return (int)(nextseed >>> (48 - bits));
    }

    /**
     * Generates random bytes and places them into a user-supplied
     * byte array.  The number of random bytes produced is equal to
     * the length of the byte array.
     *
     * <p>The method {@code nextBytes} is implemented by class {@code Random}
     * as if by:
     *  <pre> {@code
     * public void nextBytes(byte[] bytes) {
     *   for (int i = 0; i < bytes.length; )
     *     for (int rnd = nextInt(), n = Math.min(bytes.length - i, 4);
     *          n-- > 0; rnd >>= 8)
     *       bytes[i++] = (byte)rnd;
     * }}</pre>
     *
     * <p>
     *  生成随机字节并将它们放入用户提供的字节数组中产生的随机字节数等于字节数组的长度
     * 
     *  <p>方法{@code nextBytes}由类{@code Random}实现,如下：<pre> {@code public void nextBytes(byte [] bytes){for(int i = 0; i <byteslength; )for(int rnd = nextInt(),n = Mathmin(byteslength  -  i,4); n--> 0; rnd >> = 8)bytes [i ++] =(byte) }
     * } </pre>。
     * 
     * 
     * @param  bytes the byte array to fill with random bytes
     * @throws NullPointerException if the byte array is null
     * @since  1.1
     */
    public void nextBytes(byte[] bytes) {
        for (int i = 0, len = bytes.length; i < len; )
            for (int rnd = nextInt(),
                     n = Math.min(len - i, Integer.SIZE/Byte.SIZE);
                 n-- > 0; rnd >>= Byte.SIZE)
                bytes[i++] = (byte)rnd;
    }

    /**
     * The form of nextLong used by LongStream Spliterators.  If
     * origin is greater than bound, acts as unbounded form of
     * nextLong, else as bounded form.
     *
     * <p>
     * LongStream Spliterators使用的nextLong的形式如果origin大于bound,则作为nextLong的无界形式,否则作为有界形式
     * 
     * 
     * @param origin the least value, unless greater than bound
     * @param bound the upper bound (exclusive), must not equal origin
     * @return a pseudorandom value
     */
    final long internalNextLong(long origin, long bound) {
        long r = nextLong();
        if (origin < bound) {
            long n = bound - origin, m = n - 1;
            if ((n & m) == 0L)  // power of two
                r = (r & m) + origin;
            else if (n > 0L) {  // reject over-represented candidates
                for (long u = r >>> 1;            // ensure nonnegative
                     u + m - (r = u % n) < 0L;    // rejection check
                     u = nextLong() >>> 1) // retry
                    ;
                r += origin;
            }
            else {              // range not representable as long
                while (r < origin || r >= bound)
                    r = nextLong();
            }
        }
        return r;
    }

    /**
     * The form of nextInt used by IntStream Spliterators.
     * For the unbounded case: uses nextInt().
     * For the bounded case with representable range: uses nextInt(int bound)
     * For the bounded case with unrepresentable range: uses nextInt()
     *
     * <p>
     *  IntStream Spliterators使用的nextInt的形式对于无界的情况：uses nextInt()对于具有可表示范围的有界case：uses nextInt(int bound)对于不
     * 可表示范围的有界case：uses nextInt()。
     * 
     * 
     * @param origin the least value, unless greater than bound
     * @param bound the upper bound (exclusive), must not equal origin
     * @return a pseudorandom value
     */
    final int internalNextInt(int origin, int bound) {
        if (origin < bound) {
            int n = bound - origin;
            if (n > 0) {
                return nextInt(n) + origin;
            }
            else {  // range not representable as int
                int r;
                do {
                    r = nextInt();
                } while (r < origin || r >= bound);
                return r;
            }
        }
        else {
            return nextInt();
        }
    }

    /**
     * The form of nextDouble used by DoubleStream Spliterators.
     *
     * <p>
     *  DoubleStream Spliterators使用的nextDouble的形式
     * 
     * 
     * @param origin the least value, unless greater than bound
     * @param bound the upper bound (exclusive), must not equal origin
     * @return a pseudorandom value
     */
    final double internalNextDouble(double origin, double bound) {
        double r = nextDouble();
        if (origin < bound) {
            r = r * (bound - origin) + origin;
            if (r >= bound) // correct for rounding
                r = Double.longBitsToDouble(Double.doubleToLongBits(bound) - 1);
        }
        return r;
    }

    /**
     * Returns the next pseudorandom, uniformly distributed {@code int}
     * value from this random number generator's sequence. The general
     * contract of {@code nextInt} is that one {@code int} value is
     * pseudorandomly generated and returned. All 2<sup>32</sup> possible
     * {@code int} values are produced with (approximately) equal probability.
     *
     * <p>The method {@code nextInt} is implemented by class {@code Random}
     * as if by:
     *  <pre> {@code
     * public int nextInt() {
     *   return next(32);
     * }}</pre>
     *
     * <p>
     *  从这个随机数生成器的序列返回下一个伪随机,均匀分布的{@code int}值{@code nextInt}的一般约定是伪随机生成一个{@code int}值并返回所有2 <sup> 32 < sup>
     * 可能的{@code int}值是以(大致)相等的概率产生的。
     * 
     * <p>方法{@code nextInt}是由类{@code Random}实现的,如下：<pre> {@code public int nextInt(){return next(32); }} </pre>
     * 。
     * 
     * 
     * @return the next pseudorandom, uniformly distributed {@code int}
     *         value from this random number generator's sequence
     */
    public int nextInt() {
        return next(32);
    }

    /**
     * Returns a pseudorandom, uniformly distributed {@code int} value
     * between 0 (inclusive) and the specified value (exclusive), drawn from
     * this random number generator's sequence.  The general contract of
     * {@code nextInt} is that one {@code int} value in the specified range
     * is pseudorandomly generated and returned.  All {@code bound} possible
     * {@code int} values are produced with (approximately) equal
     * probability.  The method {@code nextInt(int bound)} is implemented by
     * class {@code Random} as if by:
     *  <pre> {@code
     * public int nextInt(int bound) {
     *   if (bound <= 0)
     *     throw new IllegalArgumentException("bound must be positive");
     *
     *   if ((bound & -bound) == bound)  // i.e., bound is a power of 2
     *     return (int)((bound * (long)next(31)) >> 31);
     *
     *   int bits, val;
     *   do {
     *       bits = next(31);
     *       val = bits % bound;
     *   } while (bits - val + (bound-1) < 0);
     *   return val;
     * }}</pre>
     *
     * <p>The hedge "approximately" is used in the foregoing description only
     * because the next method is only approximately an unbiased source of
     * independently chosen bits.  If it were a perfect source of randomly
     * chosen bits, then the algorithm shown would choose {@code int}
     * values from the stated range with perfect uniformity.
     * <p>
     * The algorithm is slightly tricky.  It rejects values that would result
     * in an uneven distribution (due to the fact that 2^31 is not divisible
     * by n). The probability of a value being rejected depends on n.  The
     * worst case is n=2^30+1, for which the probability of a reject is 1/2,
     * and the expected number of iterations before the loop terminates is 2.
     * <p>
     * The algorithm treats the case where n is a power of two specially: it
     * returns the correct number of high-order bits from the underlying
     * pseudo-random number generator.  In the absence of special treatment,
     * the correct number of <i>low-order</i> bits would be returned.  Linear
     * congruential pseudo-random number generators such as the one
     * implemented by this class are known to have short periods in the
     * sequence of values of their low-order bits.  Thus, this special case
     * greatly increases the length of the sequence of values returned by
     * successive calls to this method if n is a small power of two.
     *
     * <p>
     *  从这个随机数生成器的序列中得到的返回一个伪随机的,均匀分布的{@code int}值(取值范围为0和包含的值)(不包含){@code nextInt}的一般约定是一个{@code int}值在指定的范
     * 围内伪随机生成并返回所有{@code bound}可能的{@code int}值产生与(大约)等概率方法{@code nextInt(int bound)}由类{@code Random}如果通过：<pre>
     *  {@code public int nextInt(int bound){if(bound <= 0)throw new IllegalArgumentException("bound must be positive");。
     * 
     * if((bound&-bound)== bound)// ie,bound是2的幂次return(int)(bound *(long)next(31))>> 31);
     * 
     *  int位,val; do {bits = next(31); val = bits％bound; } while(bits-val +(bound-1)<0); return val; }} </pre>
     * 。
     * 
     *  在上述描述中使用对冲"大约",因为下一个方法仅仅是大约独立选择的位的无偏差源。如果它是随机选择的位的完美源,则所示的算法将选择{@code int }值从完全均匀的规定范围
     * <p>
     * 该算法略微棘手它拒绝将导致不均匀分布的值(由于2 ^ 31不能被n整除的事实)拒绝值的概率取决于n最坏的情况是n = 2 ^ 30 + 1,其中拒绝的概率是1/2,并且循环终止之前的预期迭代次数是2
     * <p>
     * 该算法特别地处理n是2的幂的情况：它从底层伪随机数发生器返回正确数量的高阶位。
     * 在没有特殊处理的情况下,正确的低阶< / i>位将被返回线性同余伪随机数发生器,例如由该类实现的线性同余伪随机数发生器已知在其低位比特的值序列中具有短周期。
     * 因此,该特殊情况大大增加了如果n是2的小幂,则通过对该方法的连续调用返回的值的序列。
     * 
     * 
     * @param bound the upper bound (exclusive).  Must be positive.
     * @return the next pseudorandom, uniformly distributed {@code int}
     *         value between zero (inclusive) and {@code bound} (exclusive)
     *         from this random number generator's sequence
     * @throws IllegalArgumentException if bound is not positive
     * @since 1.2
     */
    public int nextInt(int bound) {
        if (bound <= 0)
            throw new IllegalArgumentException(BadBound);

        int r = next(31);
        int m = bound - 1;
        if ((bound & m) == 0)  // i.e., bound is a power of 2
            r = (int)((bound * (long)r) >> 31);
        else {
            for (int u = r;
                 u - (r = u % bound) + m < 0;
                 u = next(31))
                ;
        }
        return r;
    }

    /**
     * Returns the next pseudorandom, uniformly distributed {@code long}
     * value from this random number generator's sequence. The general
     * contract of {@code nextLong} is that one {@code long} value is
     * pseudorandomly generated and returned.
     *
     * <p>The method {@code nextLong} is implemented by class {@code Random}
     * as if by:
     *  <pre> {@code
     * public long nextLong() {
     *   return ((long)next(32) << 32) + next(32);
     * }}</pre>
     *
     * Because class {@code Random} uses a seed with only 48 bits,
     * this algorithm will not return all possible {@code long} values.
     *
     * <p>
     * 从这个随机数生成器的序列返回下一个伪随机,均匀分布的{@code long}值{@code nextLong}的一般约定是伪随机生成并返回一个{@code long}值
     * 
     *  <p>方法{@code nextLong}由类{@code Random}实现,如下：<pre> {@code public long nextLong(){return((long)next(32)<< 32)+ next (32)。
     *  }} </pre>。
     * 
     *  因为类{@code Random}使用只有48位的种子,所以此算法不会返回所有可能的{@code long}值
     * 
     * 
     * @return the next pseudorandom, uniformly distributed {@code long}
     *         value from this random number generator's sequence
     */
    public long nextLong() {
        // it's okay that the bottom word remains signed.
        return ((long)(next(32)) << 32) + next(32);
    }

    /**
     * Returns the next pseudorandom, uniformly distributed
     * {@code boolean} value from this random number generator's
     * sequence. The general contract of {@code nextBoolean} is that one
     * {@code boolean} value is pseudorandomly generated and returned.  The
     * values {@code true} and {@code false} are produced with
     * (approximately) equal probability.
     *
     * <p>The method {@code nextBoolean} is implemented by class {@code Random}
     * as if by:
     *  <pre> {@code
     * public boolean nextBoolean() {
     *   return next(1) != 0;
     * }}</pre>
     *
     * <p>
     * 从这个随机数生成器的序列返回下一个伪随机,均匀分布的{@code boolean}值{@code nextBoolean}的一般约定是伪随机生成并返回一个值{@code true}和{@code false}
     * 以(近似)相等的概率产生。
     * 
     *  <p>方法{@code nextBoolean}由类{@code Random}实现,如下：<pre> {@code public boolean nextBoolean(){return next(1)！= 0; }
     * } </pre>。
     * 
     * 
     * @return the next pseudorandom, uniformly distributed
     *         {@code boolean} value from this random number generator's
     *         sequence
     * @since 1.2
     */
    public boolean nextBoolean() {
        return next(1) != 0;
    }

    /**
     * Returns the next pseudorandom, uniformly distributed {@code float}
     * value between {@code 0.0} and {@code 1.0} from this random
     * number generator's sequence.
     *
     * <p>The general contract of {@code nextFloat} is that one
     * {@code float} value, chosen (approximately) uniformly from the
     * range {@code 0.0f} (inclusive) to {@code 1.0f} (exclusive), is
     * pseudorandomly generated and returned. All 2<sup>24</sup> possible
     * {@code float} values of the form <i>m&nbsp;x&nbsp;</i>2<sup>-24</sup>,
     * where <i>m</i> is a positive integer less than 2<sup>24</sup>, are
     * produced with (approximately) equal probability.
     *
     * <p>The method {@code nextFloat} is implemented by class {@code Random}
     * as if by:
     *  <pre> {@code
     * public float nextFloat() {
     *   return next(24) / ((float)(1 << 24));
     * }}</pre>
     *
     * <p>The hedge "approximately" is used in the foregoing description only
     * because the next method is only approximately an unbiased source of
     * independently chosen bits. If it were a perfect source of randomly
     * chosen bits, then the algorithm shown would choose {@code float}
     * values from the stated range with perfect uniformity.<p>
     * [In early versions of Java, the result was incorrectly calculated as:
     *  <pre> {@code
     *   return next(30) / ((float)(1 << 30));}</pre>
     * This might seem to be equivalent, if not better, but in fact it
     * introduced a slight nonuniformity because of the bias in the rounding
     * of floating-point numbers: it was slightly more likely that the
     * low-order bit of the significand would be 0 than that it would be 1.]
     *
     * <p>
     *  从此随机数生成器的序列中返回{@code 00}和{@code 10}之间的下一个伪随机,均匀分布的{@code float}值
     * 
     * <p> {@code nextFloat}的一般合约是{@code 00f}(含)到{@code 10f}(不含)之间一致选择(大致)的{@code float}值是伪随机的生成并返回形式为<i> m&
     * nbsp; x&nbsp; </i> 2 <sup> -24 </sup>的所有2个<sup> 24 </sup>种可能的{@code float}值,其中<m> </i>是(大致)相等概率产生的小于2
     *  <sup> 24 </sup>的正整数。
     * 
     *  <p>方法{@code nextFloat}由类{@code Random}实现,如下：<pre> {@code public float nextFloat(){return next(24)/((float) )); }
     * } </pre>。
     * 
     * 在上述描述中使用对冲"大约",因为下一个方法仅仅是大约独立选择的位的无偏差源。
     * 如果它是随机选择的位的完美源,则所示的算法将选择{@code float }值在指定范围内具有完美的一致性<p> [在Java的早期版本中,结果不正确地计算为：<pre> {@code return next(30)/((float)(1 << 30) } </pre>
     * 这可能看起来是等价的,如果不是更好,但实际上它引入了一个轻微的不均匀性,因为浮点数的舍入的偏差：稍微更有可能的低阶位的有效位将是0,它将是1]。
     * 在上述描述中使用对冲"大约",因为下一个方法仅仅是大约独立选择的位的无偏差源。
     * 
     * 
     * @return the next pseudorandom, uniformly distributed {@code float}
     *         value between {@code 0.0} and {@code 1.0} from this
     *         random number generator's sequence
     */
    public float nextFloat() {
        return next(24) / ((float)(1 << 24));
    }

    /**
     * Returns the next pseudorandom, uniformly distributed
     * {@code double} value between {@code 0.0} and
     * {@code 1.0} from this random number generator's sequence.
     *
     * <p>The general contract of {@code nextDouble} is that one
     * {@code double} value, chosen (approximately) uniformly from the
     * range {@code 0.0d} (inclusive) to {@code 1.0d} (exclusive), is
     * pseudorandomly generated and returned.
     *
     * <p>The method {@code nextDouble} is implemented by class {@code Random}
     * as if by:
     *  <pre> {@code
     * public double nextDouble() {
     *   return (((long)next(26) << 27) + next(27))
     *     / (double)(1L << 53);
     * }}</pre>
     *
     * <p>The hedge "approximately" is used in the foregoing description only
     * because the {@code next} method is only approximately an unbiased
     * source of independently chosen bits. If it were a perfect source of
     * randomly chosen bits, then the algorithm shown would choose
     * {@code double} values from the stated range with perfect uniformity.
     * <p>[In early versions of Java, the result was incorrectly calculated as:
     *  <pre> {@code
     *   return (((long)next(27) << 27) + next(27))
     *     / (double)(1L << 54);}</pre>
     * This might seem to be equivalent, if not better, but in fact it
     * introduced a large nonuniformity because of the bias in the rounding
     * of floating-point numbers: it was three times as likely that the
     * low-order bit of the significand would be 0 than that it would be 1!
     * This nonuniformity probably doesn't matter much in practice, but we
     * strive for perfection.]
     *
     * <p>
     * 从此随机数生成器的序列中返回{@code 00}和{@code 10}之间的下一个伪随机,均匀分布的{@code double}值
     * 
     *  <p> {@code nextDouble}的一般合约是{@code 00d}(含)到{@code 10d}(不含)之间一致选择(大致)的一个{@code double}值是伪随机的生成和返回
     * 
     *  <p>方法{@code nextDouble}由类{@code Random}实现,如下：<pre> {@code public double nextDouble(){return((long)next(26)<< 27) next(27))/(double)(1L << 53); }} </pre>
     * 。
     * 
     * 在上述描述中使用对冲"约"仅仅是因为{@code next}方法仅仅是近似独立选择的比特的无偏差源。
     * 如果它是随机选择的比特的完美源,则所示的算法将选择{@code double}值从指定范围内完美均匀<p> [在Java的早期版本中,结果错误地计算为：<pre> {@code return((long)next(27)<< 27)+ next(27) <54);} </pre>
     * 这似乎是等价的,如果不是更好,但实际上它引入了一个大的不均匀性,因为浮点数的舍入的偏差：它是低有效位数的有序位将是0,它将是1！这种不均匀在实践中可能没有什么关系,但我们努力完善]。
     * 在上述描述中使用对冲"约"仅仅是因为{@code next}方法仅仅是近似独立选择的比特的无偏差源。
     * 
     * 
     * @return the next pseudorandom, uniformly distributed {@code double}
     *         value between {@code 0.0} and {@code 1.0} from this
     *         random number generator's sequence
     * @see Math#random
     */
    public double nextDouble() {
        return (((long)(next(26)) << 27) + next(27)) * DOUBLE_UNIT;
    }

    private double nextNextGaussian;
    private boolean haveNextNextGaussian = false;

    /**
     * Returns the next pseudorandom, Gaussian ("normally") distributed
     * {@code double} value with mean {@code 0.0} and standard
     * deviation {@code 1.0} from this random number generator's sequence.
     * <p>
     * The general contract of {@code nextGaussian} is that one
     * {@code double} value, chosen from (approximately) the usual
     * normal distribution with mean {@code 0.0} and standard deviation
     * {@code 1.0}, is pseudorandomly generated and returned.
     *
     * <p>The method {@code nextGaussian} is implemented by class
     * {@code Random} as if by a threadsafe version of the following:
     *  <pre> {@code
     * private double nextNextGaussian;
     * private boolean haveNextNextGaussian = false;
     *
     * public double nextGaussian() {
     *   if (haveNextNextGaussian) {
     *     haveNextNextGaussian = false;
     *     return nextNextGaussian;
     *   } else {
     *     double v1, v2, s;
     *     do {
     *       v1 = 2 * nextDouble() - 1;   // between -1.0 and 1.0
     *       v2 = 2 * nextDouble() - 1;   // between -1.0 and 1.0
     *       s = v1 * v1 + v2 * v2;
     *     } while (s >= 1 || s == 0);
     *     double multiplier = StrictMath.sqrt(-2 * StrictMath.log(s)/s);
     *     nextNextGaussian = v2 * multiplier;
     *     haveNextNextGaussian = true;
     *     return v1 * multiplier;
     *   }
     * }}</pre>
     * This uses the <i>polar method</i> of G. E. P. Box, M. E. Muller, and
     * G. Marsaglia, as described by Donald E. Knuth in <i>The Art of
     * Computer Programming</i>, Volume 3: <i>Seminumerical Algorithms</i>,
     * section 3.4.1, subsection C, algorithm P. Note that it generates two
     * independent values at the cost of only one call to {@code StrictMath.log}
     * and one call to {@code StrictMath.sqrt}.
     *
     * <p>
     * 从此随机数生成器的序列返回下一个伪随机,高斯("正常")分布式{@code double}值,平均值为{@code 00}和标准偏差{@code 10}
     * <p>
     *  {@code nextGaussian}的一般契约是一个{@code double}值,从(大约)平均值为{@code 00}和标准偏差{@code 10}的常规正态分布中选择,伪随机生成并返回
     * 
     *  <p>方法{@code nextGaussian}是由类{@code Random}实现的,如同通过以下线程安全版本：<pre> {@code private double nextNextGaussian; private boolean hasNextNextGaussian = false;。
     * 
     * public double nextGaussian(){if(hasNextNextGaussian){haveNextNextGaussian = false; return nextNextGaussian; }
     *  else {double v1,v2,s; do {v1 = 2 * nextDouble() -  1; // -10和10之间v2 = 2 * nextDouble() -  1; // -10和10之间s = v1 * v1 + v2 * v2; }
     *  while(s> = 1 || s == 0); double multiplier = StrictMathsqrt(-2 * StrictMathlog(s)/ s); nextNextGauss
     * ian = v2 * multiplier; hasNextNextGaussian = true; return v1 * multiplier; }使用GEP Box,ME Muller和G Mar
     * saglia的<i> polar方法</i>,如Donald E Knuth在<i> The Computer of Computer Programming </i>第3卷：子数字算法</i>,第34
     * 1节,小节C,算法P注意,它产生两个独立的值,其代价是只有一次调用{@code StrictMathlog}和一个调用{@code StrictMathsqrt}。
     * 
     * 
     * @return the next pseudorandom, Gaussian ("normally") distributed
     *         {@code double} value with mean {@code 0.0} and
     *         standard deviation {@code 1.0} from this random number
     *         generator's sequence
     */
    synchronized public double nextGaussian() {
        // See Knuth, ACP, Section 3.4.1 Algorithm C.
        if (haveNextNextGaussian) {
            haveNextNextGaussian = false;
            return nextNextGaussian;
        } else {
            double v1, v2, s;
            do {
                v1 = 2 * nextDouble() - 1; // between -1 and 1
                v2 = 2 * nextDouble() - 1; // between -1 and 1
                s = v1 * v1 + v2 * v2;
            } while (s >= 1 || s == 0);
            double multiplier = StrictMath.sqrt(-2 * StrictMath.log(s)/s);
            nextNextGaussian = v2 * multiplier;
            haveNextNextGaussian = true;
            return v1 * multiplier;
        }
    }

    // stream methods, coded in a way intended to better isolate for
    // maintenance purposes the small differences across forms.

    /**
     * Returns a stream producing the given {@code streamSize} number of
     * pseudorandom {@code int} values.
     *
     * <p>A pseudorandom {@code int} value is generated as if it's the result of
     * calling the method {@link #nextInt()}.
     *
     * <p>
     * 返回产生给定{@code streamSize}伪随机{@code int}值的流
     * 
     *  <p>生成伪随机{@code int}值,如同它是调用方法{@link #nextInt()}的结果
     * 
     * 
     * @param streamSize the number of values to generate
     * @return a stream of pseudorandom {@code int} values
     * @throws IllegalArgumentException if {@code streamSize} is
     *         less than zero
     * @since 1.8
     */
    public IntStream ints(long streamSize) {
        if (streamSize < 0L)
            throw new IllegalArgumentException(BadSize);
        return StreamSupport.intStream
                (new RandomIntsSpliterator
                         (this, 0L, streamSize, Integer.MAX_VALUE, 0),
                 false);
    }

    /**
     * Returns an effectively unlimited stream of pseudorandom {@code int}
     * values.
     *
     * <p>A pseudorandom {@code int} value is generated as if it's the result of
     * calling the method {@link #nextInt()}.
     *
     * @implNote This method is implemented to be equivalent to {@code
     * ints(Long.MAX_VALUE)}.
     *
     * <p>
     *  返回一个有效无限的伪随机流{@code int}值
     * 
     *  <p>生成伪随机{@code int}值,如同它是调用方法{@link #nextInt()}的结果
     * 
     *  @implNote此方法实现为等效于{@code ints(LongMAX_VALUE)}
     * 
     * 
     * @return a stream of pseudorandom {@code int} values
     * @since 1.8
     */
    public IntStream ints() {
        return StreamSupport.intStream
                (new RandomIntsSpliterator
                         (this, 0L, Long.MAX_VALUE, Integer.MAX_VALUE, 0),
                 false);
    }

    /**
     * Returns a stream producing the given {@code streamSize} number
     * of pseudorandom {@code int} values, each conforming to the given
     * origin (inclusive) and bound (exclusive).
     *
     * <p>A pseudorandom {@code int} value is generated as if it's the result of
     * calling the following method with the origin and bound:
     * <pre> {@code
     * int nextInt(int origin, int bound) {
     *   int n = bound - origin;
     *   if (n > 0) {
     *     return nextInt(n) + origin;
     *   }
     *   else {  // range not representable as int
     *     int r;
     *     do {
     *       r = nextInt();
     *     } while (r < origin || r >= bound);
     *     return r;
     *   }
     * }}</pre>
     *
     * <p>
     *  返回生成给定{@code streamSize}伪随机{@code int}值的流,每个值都符合给定原点(包括)和bound(exclusive)
     * 
     * <p>生成伪随机{@code int}值,就像它是使用原点和边界调用以下方法的结果：<pre> {@code int nextInt(int origin,int bound){int n = bound  - 起源; if(n> 0){return nextInt(n)+ origin; }
     *  else {//范围不能表示为int int r; do {r = nextInt(); } while(r <origin || r> = bound); return r; }}} </pre>。
     * 
     * 
     * @param streamSize the number of values to generate
     * @param randomNumberOrigin the origin (inclusive) of each random value
     * @param randomNumberBound the bound (exclusive) of each random value
     * @return a stream of pseudorandom {@code int} values,
     *         each with the given origin (inclusive) and bound (exclusive)
     * @throws IllegalArgumentException if {@code streamSize} is
     *         less than zero, or {@code randomNumberOrigin}
     *         is greater than or equal to {@code randomNumberBound}
     * @since 1.8
     */
    public IntStream ints(long streamSize, int randomNumberOrigin,
                          int randomNumberBound) {
        if (streamSize < 0L)
            throw new IllegalArgumentException(BadSize);
        if (randomNumberOrigin >= randomNumberBound)
            throw new IllegalArgumentException(BadRange);
        return StreamSupport.intStream
                (new RandomIntsSpliterator
                         (this, 0L, streamSize, randomNumberOrigin, randomNumberBound),
                 false);
    }

    /**
     * Returns an effectively unlimited stream of pseudorandom {@code
     * int} values, each conforming to the given origin (inclusive) and bound
     * (exclusive).
     *
     * <p>A pseudorandom {@code int} value is generated as if it's the result of
     * calling the following method with the origin and bound:
     * <pre> {@code
     * int nextInt(int origin, int bound) {
     *   int n = bound - origin;
     *   if (n > 0) {
     *     return nextInt(n) + origin;
     *   }
     *   else {  // range not representable as int
     *     int r;
     *     do {
     *       r = nextInt();
     *     } while (r < origin || r >= bound);
     *     return r;
     *   }
     * }}</pre>
     *
     * @implNote This method is implemented to be equivalent to {@code
     * ints(Long.MAX_VALUE, randomNumberOrigin, randomNumberBound)}.
     *
     * <p>
     *  返回一个有效无限的伪随机流{@code int}值,每个值都符合给定的原点(包括)和bound(exclusive)
     * 
     * <p>生成伪随机{@code int}值,就像它是使用原点和边界调用以下方法的结果：<pre> {@code int nextInt(int origin,int bound){int n = bound  - 起源; if(n> 0){return nextInt(n)+ origin; }
     *  else {//范围不能表示为int int r; do {r = nextInt(); } while(r <origin || r> = bound); return r; }}} </pre>。
     * 
     *  @implNote这个方法被实现为等同于{@code ints(LongMAX_VALUE,randomNumberOrigin,randomNumberBound)}
     * 
     * 
     * @param randomNumberOrigin the origin (inclusive) of each random value
     * @param randomNumberBound the bound (exclusive) of each random value
     * @return a stream of pseudorandom {@code int} values,
     *         each with the given origin (inclusive) and bound (exclusive)
     * @throws IllegalArgumentException if {@code randomNumberOrigin}
     *         is greater than or equal to {@code randomNumberBound}
     * @since 1.8
     */
    public IntStream ints(int randomNumberOrigin, int randomNumberBound) {
        if (randomNumberOrigin >= randomNumberBound)
            throw new IllegalArgumentException(BadRange);
        return StreamSupport.intStream
                (new RandomIntsSpliterator
                         (this, 0L, Long.MAX_VALUE, randomNumberOrigin, randomNumberBound),
                 false);
    }

    /**
     * Returns a stream producing the given {@code streamSize} number of
     * pseudorandom {@code long} values.
     *
     * <p>A pseudorandom {@code long} value is generated as if it's the result
     * of calling the method {@link #nextLong()}.
     *
     * <p>
     *  返回生成给定{@code streamSize}伪随机数{@code long}值的流
     * 
     *  <p>生成伪随机{@code long}值,就好像它是调用方法{@link #nextLong()}的结果
     * 
     * 
     * @param streamSize the number of values to generate
     * @return a stream of pseudorandom {@code long} values
     * @throws IllegalArgumentException if {@code streamSize} is
     *         less than zero
     * @since 1.8
     */
    public LongStream longs(long streamSize) {
        if (streamSize < 0L)
            throw new IllegalArgumentException(BadSize);
        return StreamSupport.longStream
                (new RandomLongsSpliterator
                         (this, 0L, streamSize, Long.MAX_VALUE, 0L),
                 false);
    }

    /**
     * Returns an effectively unlimited stream of pseudorandom {@code long}
     * values.
     *
     * <p>A pseudorandom {@code long} value is generated as if it's the result
     * of calling the method {@link #nextLong()}.
     *
     * @implNote This method is implemented to be equivalent to {@code
     * longs(Long.MAX_VALUE)}.
     *
     * <p>
     *  返回有效无限的伪随机{@code long}值流
     * 
     * <p>生成伪随机{@code long}值,就好像它是调用方法{@link #nextLong()}的结果
     * 
     *  @implNote此方法实现为等效于{@code longs(LongMAX_VALUE)}
     * 
     * 
     * @return a stream of pseudorandom {@code long} values
     * @since 1.8
     */
    public LongStream longs() {
        return StreamSupport.longStream
                (new RandomLongsSpliterator
                         (this, 0L, Long.MAX_VALUE, Long.MAX_VALUE, 0L),
                 false);
    }

    /**
     * Returns a stream producing the given {@code streamSize} number of
     * pseudorandom {@code long}, each conforming to the given origin
     * (inclusive) and bound (exclusive).
     *
     * <p>A pseudorandom {@code long} value is generated as if it's the result
     * of calling the following method with the origin and bound:
     * <pre> {@code
     * long nextLong(long origin, long bound) {
     *   long r = nextLong();
     *   long n = bound - origin, m = n - 1;
     *   if ((n & m) == 0L)  // power of two
     *     r = (r & m) + origin;
     *   else if (n > 0L) {  // reject over-represented candidates
     *     for (long u = r >>> 1;            // ensure nonnegative
     *          u + m - (r = u % n) < 0L;    // rejection check
     *          u = nextLong() >>> 1) // retry
     *         ;
     *     r += origin;
     *   }
     *   else {              // range not representable as long
     *     while (r < origin || r >= bound)
     *       r = nextLong();
     *   }
     *   return r;
     * }}</pre>
     *
     * <p>
     *  返回生成给定{@code streamSize}伪随机{@code long}的数据流,每个数据都符合给定的原点(包括)和bound(exclusive)
     * 
     * <p>生成伪随机{@code long}值,如同它是使用原点和边界调用以下方法的结果：<pre> {@code long nextLong(long origin,long bound){long r = nextLong );长n =结合原子,m = n-1; if((n&m)== 0L)// 2的幂r =(r&m)+ origin; else if(n> 0L){//拒绝过度表示的候选(long u = r >>> 1; //确保非负u + m-(r = u％n)<0L; nextLong()>>> 1)// retry; r + = origin; }
     *  else {//范围不能表示为long while(r <origin || r> = bound)r = nextLong(); } return r; }} </pre>。
     * 
     * 
     * @param streamSize the number of values to generate
     * @param randomNumberOrigin the origin (inclusive) of each random value
     * @param randomNumberBound the bound (exclusive) of each random value
     * @return a stream of pseudorandom {@code long} values,
     *         each with the given origin (inclusive) and bound (exclusive)
     * @throws IllegalArgumentException if {@code streamSize} is
     *         less than zero, or {@code randomNumberOrigin}
     *         is greater than or equal to {@code randomNumberBound}
     * @since 1.8
     */
    public LongStream longs(long streamSize, long randomNumberOrigin,
                            long randomNumberBound) {
        if (streamSize < 0L)
            throw new IllegalArgumentException(BadSize);
        if (randomNumberOrigin >= randomNumberBound)
            throw new IllegalArgumentException(BadRange);
        return StreamSupport.longStream
                (new RandomLongsSpliterator
                         (this, 0L, streamSize, randomNumberOrigin, randomNumberBound),
                 false);
    }

    /**
     * Returns an effectively unlimited stream of pseudorandom {@code
     * long} values, each conforming to the given origin (inclusive) and bound
     * (exclusive).
     *
     * <p>A pseudorandom {@code long} value is generated as if it's the result
     * of calling the following method with the origin and bound:
     * <pre> {@code
     * long nextLong(long origin, long bound) {
     *   long r = nextLong();
     *   long n = bound - origin, m = n - 1;
     *   if ((n & m) == 0L)  // power of two
     *     r = (r & m) + origin;
     *   else if (n > 0L) {  // reject over-represented candidates
     *     for (long u = r >>> 1;            // ensure nonnegative
     *          u + m - (r = u % n) < 0L;    // rejection check
     *          u = nextLong() >>> 1) // retry
     *         ;
     *     r += origin;
     *   }
     *   else {              // range not representable as long
     *     while (r < origin || r >= bound)
     *       r = nextLong();
     *   }
     *   return r;
     * }}</pre>
     *
     * @implNote This method is implemented to be equivalent to {@code
     * longs(Long.MAX_VALUE, randomNumberOrigin, randomNumberBound)}.
     *
     * <p>
     *  返回有效无限的伪随机{@code long}值流,每个值都符合给定的起点(包括)和bound(exclusive)
     * 
     * <p>生成伪随机{@code long}值,如同它是使用原点和边界调用以下方法的结果：<pre> {@code long nextLong(long origin,long bound){long r = nextLong );长n =结合原子,m = n-1; if((n&m)== 0L)// 2的幂r =(r&m)+ origin; else if(n> 0L){//拒绝过度表示的候选(long u = r >>> 1; //确保非负u + m-(r = u％n)<0L; nextLong()>>> 1)// retry; r + = origin; }
     *  else {//范围不能表示为long while(r <origin || r> = bound)r = nextLong(); } return r; }} </pre>。
     * 
     *  @implNote此方法实现为等效于{@code longs(LongMAX_VALUE,randomNumberOrigin,randomNumberBound)}
     * 
     * 
     * @param randomNumberOrigin the origin (inclusive) of each random value
     * @param randomNumberBound the bound (exclusive) of each random value
     * @return a stream of pseudorandom {@code long} values,
     *         each with the given origin (inclusive) and bound (exclusive)
     * @throws IllegalArgumentException if {@code randomNumberOrigin}
     *         is greater than or equal to {@code randomNumberBound}
     * @since 1.8
     */
    public LongStream longs(long randomNumberOrigin, long randomNumberBound) {
        if (randomNumberOrigin >= randomNumberBound)
            throw new IllegalArgumentException(BadRange);
        return StreamSupport.longStream
                (new RandomLongsSpliterator
                         (this, 0L, Long.MAX_VALUE, randomNumberOrigin, randomNumberBound),
                 false);
    }

    /**
     * Returns a stream producing the given {@code streamSize} number of
     * pseudorandom {@code double} values, each between zero
     * (inclusive) and one (exclusive).
     *
     * <p>A pseudorandom {@code double} value is generated as if it's the result
     * of calling the method {@link #nextDouble()}.
     *
     * <p>
     * 返回产生给定{@code streamSize}伪随机{@code double}值的流,每个值在零(包括)和一个(独占)
     * 
     *  <p>生成伪随机{@code double}值,如同它是调用方法{@link #nextDouble()}的结果
     * 
     * 
     * @param streamSize the number of values to generate
     * @return a stream of {@code double} values
     * @throws IllegalArgumentException if {@code streamSize} is
     *         less than zero
     * @since 1.8
     */
    public DoubleStream doubles(long streamSize) {
        if (streamSize < 0L)
            throw new IllegalArgumentException(BadSize);
        return StreamSupport.doubleStream
                (new RandomDoublesSpliterator
                         (this, 0L, streamSize, Double.MAX_VALUE, 0.0),
                 false);
    }

    /**
     * Returns an effectively unlimited stream of pseudorandom {@code
     * double} values, each between zero (inclusive) and one
     * (exclusive).
     *
     * <p>A pseudorandom {@code double} value is generated as if it's the result
     * of calling the method {@link #nextDouble()}.
     *
     * @implNote This method is implemented to be equivalent to {@code
     * doubles(Long.MAX_VALUE)}.
     *
     * <p>
     *  返回有效无限的伪随机{@code double}值,每个值在零(包括)和一个(独占)
     * 
     *  <p>生成伪随机{@code double}值,如同它是调用方法{@link #nextDouble()}的结果
     * 
     *  @implNote此方法实现为等效于{@code doubles(LongMAX_VALUE)}
     * 
     * 
     * @return a stream of pseudorandom {@code double} values
     * @since 1.8
     */
    public DoubleStream doubles() {
        return StreamSupport.doubleStream
                (new RandomDoublesSpliterator
                         (this, 0L, Long.MAX_VALUE, Double.MAX_VALUE, 0.0),
                 false);
    }

    /**
     * Returns a stream producing the given {@code streamSize} number of
     * pseudorandom {@code double} values, each conforming to the given origin
     * (inclusive) and bound (exclusive).
     *
     * <p>A pseudorandom {@code double} value is generated as if it's the result
     * of calling the following method with the origin and bound:
     * <pre> {@code
     * double nextDouble(double origin, double bound) {
     *   double r = nextDouble();
     *   r = r * (bound - origin) + origin;
     *   if (r >= bound) // correct for rounding
     *     r = Math.nextDown(bound);
     *   return r;
     * }}</pre>
     *
     * <p>
     *  返回生成给定{@code streamSize}伪随机{@code double}值的流,每个值都符合给定原点(包括)和bound(exclusive)
     * 
     * <p>生成伪随机{@code double}值,如同它是使用原点和边界调用以下方法的结果：<pre> {@code double nextDouble(double origin,double bound){double r = nextDouble ); r = r *(结合原点)+原点; if(r> = bound)//正确舍入r = MathnextDown(bound); return r; }
     * } </pre>。
     * 
     * 
     * @param streamSize the number of values to generate
     * @param randomNumberOrigin the origin (inclusive) of each random value
     * @param randomNumberBound the bound (exclusive) of each random value
     * @return a stream of pseudorandom {@code double} values,
     *         each with the given origin (inclusive) and bound (exclusive)
     * @throws IllegalArgumentException if {@code streamSize} is
     *         less than zero
     * @throws IllegalArgumentException if {@code randomNumberOrigin}
     *         is greater than or equal to {@code randomNumberBound}
     * @since 1.8
     */
    public DoubleStream doubles(long streamSize, double randomNumberOrigin,
                                double randomNumberBound) {
        if (streamSize < 0L)
            throw new IllegalArgumentException(BadSize);
        if (!(randomNumberOrigin < randomNumberBound))
            throw new IllegalArgumentException(BadRange);
        return StreamSupport.doubleStream
                (new RandomDoublesSpliterator
                         (this, 0L, streamSize, randomNumberOrigin, randomNumberBound),
                 false);
    }

    /**
     * Returns an effectively unlimited stream of pseudorandom {@code
     * double} values, each conforming to the given origin (inclusive) and bound
     * (exclusive).
     *
     * <p>A pseudorandom {@code double} value is generated as if it's the result
     * of calling the following method with the origin and bound:
     * <pre> {@code
     * double nextDouble(double origin, double bound) {
     *   double r = nextDouble();
     *   r = r * (bound - origin) + origin;
     *   if (r >= bound) // correct for rounding
     *     r = Math.nextDown(bound);
     *   return r;
     * }}</pre>
     *
     * @implNote This method is implemented to be equivalent to {@code
     * doubles(Long.MAX_VALUE, randomNumberOrigin, randomNumberBound)}.
     *
     * <p>
     *  返回有效无限的伪随机{@code double}值流,每个值都符合给定的起点(包含)和bound(exclusive)
     * 
     * <p>生成伪随机{@code double}值,如同它是使用原点和边界调用以下方法的结果：<pre> {@code double nextDouble(double origin,double bound){double r = nextDouble ); r = r *(结合原点)+原点; if(r> = bound)//正确舍入r = MathnextDown(bound); return r; }
     * } </pre>。
     * 
     *  @implNote此方法实现为等同于{@code doubles(LongMAX_VALUE,randomNumberOrigin,randomNumberBound)}
     * 
     * 
     * @param randomNumberOrigin the origin (inclusive) of each random value
     * @param randomNumberBound the bound (exclusive) of each random value
     * @return a stream of pseudorandom {@code double} values,
     *         each with the given origin (inclusive) and bound (exclusive)
     * @throws IllegalArgumentException if {@code randomNumberOrigin}
     *         is greater than or equal to {@code randomNumberBound}
     * @since 1.8
     */
    public DoubleStream doubles(double randomNumberOrigin, double randomNumberBound) {
        if (!(randomNumberOrigin < randomNumberBound))
            throw new IllegalArgumentException(BadRange);
        return StreamSupport.doubleStream
                (new RandomDoublesSpliterator
                         (this, 0L, Long.MAX_VALUE, randomNumberOrigin, randomNumberBound),
                 false);
    }

    /**
     * Spliterator for int streams.  We multiplex the four int
     * versions into one class by treating a bound less than origin as
     * unbounded, and also by treating "infinite" as equivalent to
     * Long.MAX_VALUE. For splits, it uses the standard divide-by-two
     * approach. The long and double versions of this class are
     * identical except for types.
     * <p>
     *  int stream的拆分器我们通过将一个小于origin的绑定处理为无界,并将"无限"当作等价于LongMAX_VALUE,将四个int版本复用到一个类中。对于拆分,它使用标准的二分法。
     * 此类的双版本是相同的,除了类型。
     * 
     */
    static final class RandomIntsSpliterator implements Spliterator.OfInt {
        final Random rng;
        long index;
        final long fence;
        final int origin;
        final int bound;
        RandomIntsSpliterator(Random rng, long index, long fence,
                              int origin, int bound) {
            this.rng = rng; this.index = index; this.fence = fence;
            this.origin = origin; this.bound = bound;
        }

        public RandomIntsSpliterator trySplit() {
            long i = index, m = (i + fence) >>> 1;
            return (m <= i) ? null :
                   new RandomIntsSpliterator(rng, i, index = m, origin, bound);
        }

        public long estimateSize() {
            return fence - index;
        }

        public int characteristics() {
            return (Spliterator.SIZED | Spliterator.SUBSIZED |
                    Spliterator.NONNULL | Spliterator.IMMUTABLE);
        }

        public boolean tryAdvance(IntConsumer consumer) {
            if (consumer == null) throw new NullPointerException();
            long i = index, f = fence;
            if (i < f) {
                consumer.accept(rng.internalNextInt(origin, bound));
                index = i + 1;
                return true;
            }
            return false;
        }

        public void forEachRemaining(IntConsumer consumer) {
            if (consumer == null) throw new NullPointerException();
            long i = index, f = fence;
            if (i < f) {
                index = f;
                Random r = rng;
                int o = origin, b = bound;
                do {
                    consumer.accept(r.internalNextInt(o, b));
                } while (++i < f);
            }
        }
    }

    /**
     * Spliterator for long streams.
     * <p>
     * 长流的分割器
     * 
     */
    static final class RandomLongsSpliterator implements Spliterator.OfLong {
        final Random rng;
        long index;
        final long fence;
        final long origin;
        final long bound;
        RandomLongsSpliterator(Random rng, long index, long fence,
                               long origin, long bound) {
            this.rng = rng; this.index = index; this.fence = fence;
            this.origin = origin; this.bound = bound;
        }

        public RandomLongsSpliterator trySplit() {
            long i = index, m = (i + fence) >>> 1;
            return (m <= i) ? null :
                   new RandomLongsSpliterator(rng, i, index = m, origin, bound);
        }

        public long estimateSize() {
            return fence - index;
        }

        public int characteristics() {
            return (Spliterator.SIZED | Spliterator.SUBSIZED |
                    Spliterator.NONNULL | Spliterator.IMMUTABLE);
        }

        public boolean tryAdvance(LongConsumer consumer) {
            if (consumer == null) throw new NullPointerException();
            long i = index, f = fence;
            if (i < f) {
                consumer.accept(rng.internalNextLong(origin, bound));
                index = i + 1;
                return true;
            }
            return false;
        }

        public void forEachRemaining(LongConsumer consumer) {
            if (consumer == null) throw new NullPointerException();
            long i = index, f = fence;
            if (i < f) {
                index = f;
                Random r = rng;
                long o = origin, b = bound;
                do {
                    consumer.accept(r.internalNextLong(o, b));
                } while (++i < f);
            }
        }

    }

    /**
     * Spliterator for double streams.
     * <p>
     *  双流的分离器
     * 
     */
    static final class RandomDoublesSpliterator implements Spliterator.OfDouble {
        final Random rng;
        long index;
        final long fence;
        final double origin;
        final double bound;
        RandomDoublesSpliterator(Random rng, long index, long fence,
                                 double origin, double bound) {
            this.rng = rng; this.index = index; this.fence = fence;
            this.origin = origin; this.bound = bound;
        }

        public RandomDoublesSpliterator trySplit() {
            long i = index, m = (i + fence) >>> 1;
            return (m <= i) ? null :
                   new RandomDoublesSpliterator(rng, i, index = m, origin, bound);
        }

        public long estimateSize() {
            return fence - index;
        }

        public int characteristics() {
            return (Spliterator.SIZED | Spliterator.SUBSIZED |
                    Spliterator.NONNULL | Spliterator.IMMUTABLE);
        }

        public boolean tryAdvance(DoubleConsumer consumer) {
            if (consumer == null) throw new NullPointerException();
            long i = index, f = fence;
            if (i < f) {
                consumer.accept(rng.internalNextDouble(origin, bound));
                index = i + 1;
                return true;
            }
            return false;
        }

        public void forEachRemaining(DoubleConsumer consumer) {
            if (consumer == null) throw new NullPointerException();
            long i = index, f = fence;
            if (i < f) {
                index = f;
                Random r = rng;
                double o = origin, b = bound;
                do {
                    consumer.accept(r.internalNextDouble(o, b));
                } while (++i < f);
            }
        }
    }

    /**
     * Serializable fields for Random.
     *
     * <p>
     *  可序列化字段为随机
     * 
     * 
     * @serialField    seed long
     *              seed for random computations
     * @serialField    nextNextGaussian double
     *              next Gaussian to be returned
     * @serialField      haveNextNextGaussian boolean
     *              nextNextGaussian is valid
     */
    private static final ObjectStreamField[] serialPersistentFields = {
        new ObjectStreamField("seed", Long.TYPE),
        new ObjectStreamField("nextNextGaussian", Double.TYPE),
        new ObjectStreamField("haveNextNextGaussian", Boolean.TYPE)
    };

    /**
     * Reconstitute the {@code Random} instance from a stream (that is,
     * deserialize it).
     * <p>
     *  从流中重新构建{@code Random}实例(即,对其进行反序列化)
     * 
     */
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {

        ObjectInputStream.GetField fields = s.readFields();

        // The seed is read in as {@code long} for
        // historical reasons, but it is converted to an AtomicLong.
        long seedVal = fields.get("seed", -1L);
        if (seedVal < 0)
          throw new java.io.StreamCorruptedException(
                              "Random: invalid seed");
        resetSeed(seedVal);
        nextNextGaussian = fields.get("nextNextGaussian", 0.0);
        haveNextNextGaussian = fields.get("haveNextNextGaussian", false);
    }

    /**
     * Save the {@code Random} instance to a stream.
     * <p>
     *  将{@code Random}实例保存到流中
     */
    synchronized private void writeObject(ObjectOutputStream s)
        throws IOException {

        // set the values of the Serializable fields
        ObjectOutputStream.PutField fields = s.putFields();

        // The seed is serialized as a long for historical reasons.
        fields.put("seed", seed.get());
        fields.put("nextNextGaussian", nextNextGaussian);
        fields.put("haveNextNextGaussian", haveNextNextGaussian);

        // save them
        s.writeFields();
    }

    // Support for resetting seed while deserializing
    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long seedOffset;
    static {
        try {
            seedOffset = unsafe.objectFieldOffset
                (Random.class.getDeclaredField("seed"));
        } catch (Exception ex) { throw new Error(ex); }
    }
    private void resetSeed(long seedVal) {
        unsafe.putObjectVolatile(this, seedOffset, new AtomicLong(seedVal));
    }
}
