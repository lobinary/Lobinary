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
 * A recursive resultless {@link ForkJoinTask}.  This class
 * establishes conventions to parameterize resultless actions as
 * {@code Void} {@code ForkJoinTask}s. Because {@code null} is the
 * only valid value of type {@code Void}, methods such as {@code join}
 * always return {@code null} upon completion.
 *
 * <p><b>Sample Usages.</b> Here is a simple but complete ForkJoin
 * sort that sorts a given {@code long[]} array:
 *
 *  <pre> {@code
 * static class SortTask extends RecursiveAction {
 *   final long[] array; final int lo, hi;
 *   SortTask(long[] array, int lo, int hi) {
 *     this.array = array; this.lo = lo; this.hi = hi;
 *   }
 *   SortTask(long[] array) { this(array, 0, array.length); }
 *   protected void compute() {
 *     if (hi - lo < THRESHOLD)
 *       sortSequentially(lo, hi);
 *     else {
 *       int mid = (lo + hi) >>> 1;
 *       invokeAll(new SortTask(array, lo, mid),
 *                 new SortTask(array, mid, hi));
 *       merge(lo, mid, hi);
 *     }
 *   }
 *   // implementation details follow:
 *   static final int THRESHOLD = 1000;
 *   void sortSequentially(int lo, int hi) {
 *     Arrays.sort(array, lo, hi);
 *   }
 *   void merge(int lo, int mid, int hi) {
 *     long[] buf = Arrays.copyOfRange(array, lo, mid);
 *     for (int i = 0, j = lo, k = mid; i < buf.length; j++)
 *       array[j] = (k == hi || buf[i] < array[k]) ?
 *         buf[i++] : array[k++];
 *   }
 * }}</pre>
 *
 * You could then sort {@code anArray} by creating {@code new
 * SortTask(anArray)} and invoking it in a ForkJoinPool.  As a more
 * concrete simple example, the following task increments each element
 * of an array:
 *  <pre> {@code
 * class IncrementTask extends RecursiveAction {
 *   final long[] array; final int lo, hi;
 *   IncrementTask(long[] array, int lo, int hi) {
 *     this.array = array; this.lo = lo; this.hi = hi;
 *   }
 *   protected void compute() {
 *     if (hi - lo < THRESHOLD) {
 *       for (int i = lo; i < hi; ++i)
 *         array[i]++;
 *     }
 *     else {
 *       int mid = (lo + hi) >>> 1;
 *       invokeAll(new IncrementTask(array, lo, mid),
 *                 new IncrementTask(array, mid, hi));
 *     }
 *   }
 * }}</pre>
 *
 * <p>The following example illustrates some refinements and idioms
 * that may lead to better performance: RecursiveActions need not be
 * fully recursive, so long as they maintain the basic
 * divide-and-conquer approach. Here is a class that sums the squares
 * of each element of a double array, by subdividing out only the
 * right-hand-sides of repeated divisions by two, and keeping track of
 * them with a chain of {@code next} references. It uses a dynamic
 * threshold based on method {@code getSurplusQueuedTaskCount}, but
 * counterbalances potential excess partitioning by directly
 * performing leaf actions on unstolen tasks rather than further
 * subdividing.
 *
 *  <pre> {@code
 * double sumOfSquares(ForkJoinPool pool, double[] array) {
 *   int n = array.length;
 *   Applyer a = new Applyer(array, 0, n, null);
 *   pool.invoke(a);
 *   return a.result;
 * }
 *
 * class Applyer extends RecursiveAction {
 *   final double[] array;
 *   final int lo, hi;
 *   double result;
 *   Applyer next; // keeps track of right-hand-side tasks
 *   Applyer(double[] array, int lo, int hi, Applyer next) {
 *     this.array = array; this.lo = lo; this.hi = hi;
 *     this.next = next;
 *   }
 *
 *   double atLeaf(int l, int h) {
 *     double sum = 0;
 *     for (int i = l; i < h; ++i) // perform leftmost base step
 *       sum += array[i] * array[i];
 *     return sum;
 *   }
 *
 *   protected void compute() {
 *     int l = lo;
 *     int h = hi;
 *     Applyer right = null;
 *     while (h - l > 1 && getSurplusQueuedTaskCount() <= 3) {
 *       int mid = (l + h) >>> 1;
 *       right = new Applyer(array, mid, h, right);
 *       right.fork();
 *       h = mid;
 *     }
 *     double sum = atLeaf(l, h);
 *     while (right != null) {
 *       if (right.tryUnfork()) // directly calculate if not stolen
 *         sum += right.atLeaf(right.lo, right.hi);
 *       else {
 *         right.join();
 *         sum += right.result;
 *       }
 *       right = right.next;
 *     }
 *     result = sum;
 *   }
 * }}</pre>
 *
 * <p>
 *  递归无结果{@link ForkJoinTask}。此类建立约定以将无结果操作参数化为{@code Void} {@code ForkJoinTask}。
 * 因为{@code null}是类型{@code Void}的唯一有效值,所以诸如{@code join}之类的方法在完成后总是返回{@code null}。
 * 
 *  <p> <b>示例用法</b>这是一个简单但完整的ForkJoin排序,它对给定的{@code long []}数组进行排序：
 * 
 *  <pre> {@code static class SortTask extends RecursiveAction {final long [] array; final int lo,hi; SortTask(long [] array,int lo,int hi){this.array = array; this.lo = lo; this.hi = hi; }
 *  SortTask(long [] array){this(array,0,array.length); } protected void compute(){if(hi  -  lo <THRESHOLD)sortSequentially(lo,hi); else {int mid =(lo + hi)>>> 1; invokeAll(new SortTask(array,lo,mid),new SortTask(array,mid,hi));合并(lo,mid,hi); }
 * } //实现细节如下：static final int THRESHOLD = 1000; void sortSequentially(int lo,int hi){Arrays.sort(array,lo,hi); }
 *  void merge(int lo,int mid,int hi){long [] buf = Arrays.copyOfRange(array,lo,mid); for(int i = 0,j = lo,k = mid; i <buf.length; j ++)array [j] =(k == hi || buf [i] <array [k])? buf [i ++]：array [k ++]; }}} </pre>
 * 。
 * 
 * 然后,您可以通过创建{@code new SortTask(anArray)}并在ForkJoinPool中调用它来对{@code anArray}进行排序。
 * 作为一个更具体的简单例子,下面的任务递增数组的每个元素：<pre> {@code class IncrementTask extends RecursiveAction {final long [] array; final int lo,hi; IncrementTask(long [] array,int lo,int hi){this.array = array; this.lo = lo; this.hi = hi; }
 *  protected void compute(){if(hi-lo <THRESHOLD){for(int i = lo; i <hi; ++ i)array [i] ++; } else {int mid =(lo + hi)>>> 1; invokeAll(new IncrementTask(array,lo,mid),new IncrementTask(array,mid,hi)); }
 * }}} </pre>。
 * 然后,您可以通过创建{@code new SortTask(anArray)}并在ForkJoinPool中调用它来对{@code anArray}进行排序。
 * 
 *  <p>以下示例说明了一些可能导致更好性能的细化和习语：RecursiveActions不需要完全递归,只要它们维护基本的分治方法。
 * 这里是一个类,它通过将只有重复的除法的右侧除以2,并用一串{@code next}引用来跟踪它们来对双阵列的每个元素的平方求和。
 * 它使用基于方法{@code getSurplusQueuedTaskCount}的动态阈值,但是通过对未完成的任务直接执行叶片动作而不是进一步细分来平衡潜在的过剩分割。
 * 
 *  <pre> {@code double sumOfSquares(ForkJoinPool pool,double [] array){int n = array.length; Applyer a = new Applyer(array,0,n,null); pool.invoke(a); return a.result; }
 * }。
 * 
 * @since 1.7
 * @author Doug Lea
 */
public abstract class RecursiveAction extends ForkJoinTask<Void> {
    private static final long serialVersionUID = 5232453952276485070L;

    /**
     * The main computation performed by this task.
     * <p>
     * 
     * class Applyer extends RecursiveAction {final double [] array; final int lo,hi;双重结果;应用者; //保持跟踪右侧任务Applyer(double [] array,int lo,int hi,Applyer next){this.array = array; this.lo = lo; this.hi = hi; this.next = next; }
     * }。
     * 
     *  double atLeaf(int l,int h){double sum = 0; for(int i = l; i <h; ++ i)//执行最左边的基本步骤sum + = array [i] * array [i]; return sum; }}。
     * 
     *  protected void compute(){int l = lo; int h = hi; Applyer right = null; while(h-1> 1 && getSurplusQueuedTaskCount()<= 3){int mid =(1 + h)>>> 1; right = new Applyer(array,mid,h,right); right.fork(); h = mid; }
     *  double sum = atLeaf(1,h); while(right！= null){if(right.tryUnfork())//直接计算如果没有被偷走sum + = right.atLeaf(right.lo,right.hi); else {right.join(); sum + = right.result; }
     *  right = right.next; } result = sum; }}} </pre>。
     * 
     */
    protected abstract void compute();

    /**
     * Always returns {@code null}.
     *
     * <p>
     *  这个任务执行的主要计算。
     * 
     * 
     * @return {@code null} always
     */
    public final Void getRawResult() { return null; }

    /**
     * Requires null completion value.
     * <p>
     *  始终返回{@code null}。
     * 
     */
    protected final void setRawResult(Void mustBeNull) { }

    /**
     * Implements execution conventions for RecursiveActions.
     * <p>
     *  需要空完成值。
     * 
     */
    protected final boolean exec() {
        compute();
        return true;
    }

}
