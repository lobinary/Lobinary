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

package java.util.concurrent;

/**
 * A recursive result-bearing {@link ForkJoinTask}.
 *
 * <p>For a classic example, here is a task computing Fibonacci numbers:
 *
 *  <pre> {@code
 * class Fibonacci extends RecursiveTask<Integer> {
 *   final int n;
 *   Fibonacci(int n) { this.n = n; }
 *   Integer compute() {
 *     if (n <= 1)
 *       return n;
 *     Fibonacci f1 = new Fibonacci(n - 1);
 *     f1.fork();
 *     Fibonacci f2 = new Fibonacci(n - 2);
 *     return f2.compute() + f1.join();
 *   }
 * }}</pre>
 *
 * However, besides being a dumb way to compute Fibonacci functions
 * (there is a simple fast linear algorithm that you'd use in
 * practice), this is likely to perform poorly because the smallest
 * subtasks are too small to be worthwhile splitting up. Instead, as
 * is the case for nearly all fork/join applications, you'd pick some
 * minimum granularity size (for example 10 here) for which you always
 * sequentially solve rather than subdividing.
 *
 * <p>
 *  递归结果{@link ForkJoinTask}。
 * 
 *  <p>对于一个经典的例子,这里是一个任务计算斐波那契数：
 * 
 *  <pre> {@code class Fibonacci extends RecursiveTask <Integer> {final int n; Fibonacci(int n){this.n = n; }
 *  Integer compute(){if(n <= 1)return n;斐波纳契f1 =新斐波纳契(n-1); f1.fork();斐波纳契f2 =新斐波纳契(n-2); return f2.compute()+ f1.join(); }}} </pre>
 * 。
 * 
 *  然而,除了是计算斐波纳契函数的愚蠢的方法(有一个简单的快速线性算法,你会在实践中使用),这很可能执行不好,因为最小的子任务太小,不值得分裂。
 * 相反,就像几乎所有fork / join应用程序的情况一样,您可以选择一些最小粒度大小(例如这里的10),对于这些大小,您总是顺序求解而不是细分。
 * 
 * @since 1.7
 * @author Doug Lea
 */
public abstract class RecursiveTask<V> extends ForkJoinTask<V> {
    private static final long serialVersionUID = 5232453952276485270L;

    /**
     * The result of the computation.
     * <p>
     * 
     */
    V result;

    /**
     * The main computation performed by this task.
     * <p>
     *  计算的结果。
     * 
     * 
     * @return the result of the computation
     */
    protected abstract V compute();

    public final V getRawResult() {
        return result;
    }

    protected final void setRawResult(V value) {
        result = value;
    }

    /**
     * Implements execution conventions for RecursiveTask.
     * <p>
     *  这个任务执行的主要计算。
     * 
     */
    protected final boolean exec() {
        result = compute();
        return true;
    }

}
