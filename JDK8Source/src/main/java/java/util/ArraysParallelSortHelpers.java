/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.concurrent.RecursiveAction;
import java.util.concurrent.CountedCompleter;

/**
 * Helper utilities for the parallel sort methods in Arrays.parallelSort.
 *
 * For each primitive type, plus Object, we define a static class to
 * contain the Sorter and Merger implementations for that type:
 *
 * Sorter classes based mainly on CilkSort
 * <A href="http://supertech.lcs.mit.edu/cilk/"> Cilk</A>:
 * Basic algorithm:
 * if array size is small, just use a sequential quicksort (via Arrays.sort)
 *         Otherwise:
 *         1. Break array in half.
 *         2. For each half,
 *             a. break the half in half (i.e., quarters),
 *             b. sort the quarters
 *             c. merge them together
 *         3. merge together the two halves.
 *
 * One reason for splitting in quarters is that this guarantees that
 * the final sort is in the main array, not the workspace array.
 * (workspace and main swap roles on each subsort step.)  Leaf-level
 * sorts use the associated sequential sort.
 *
 * Merger classes perform merging for Sorter.  They are structured
 * such that if the underlying sort is stable (as is true for
 * TimSort), then so is the full sort.  If big enough, they split the
 * largest of the two partitions in half, find the greatest point in
 * smaller partition less than the beginning of the second half of
 * larger via binary search; and then merge in parallel the two
 * partitions.  In part to ensure tasks are triggered in
 * stability-preserving order, the current CountedCompleter design
 * requires some little tasks to serve as place holders for triggering
 * completion tasks.  These classes (EmptyCompleter and Relay) don't
 * need to keep track of the arrays, and are never themselves forked,
 * so don't hold any task state.
 *
 * The primitive class versions (FJByte... FJDouble) are
 * identical to each other except for type declarations.
 *
 * The base sequential sorts rely on non-public versions of TimSort,
 * ComparableTimSort, and DualPivotQuicksort sort methods that accept
 * temp workspace array slices that we will have already allocated, so
 * avoids redundant allocation. (Except for DualPivotQuicksort byte[]
 * sort, that does not ever use a workspace array.)
 * <p>
 *  用于Arrays.parallelSort中并行排序方法的辅助工具。
 * 
 *  对于每个基本类型,加上Object,我们定义一个静态类来包含该类型的Sorter和Merger实现：
 * 
 *  基于CilkSort的分类器类<A href="http://supertech.lcs.mit.edu/cilk/"> Cilk </A>：基本算法：如果数组大小较小,只需使用顺序快速排序(通过数组
 * .sort)否则：1.将数组中断一半。
 * 对于每一半,a。打破一半(即四分之一),b。排序季度c。将它们合并在一起3.将两半合并在一起。
 * 
 *  分裂的一个原因是,这保证最后的排序是在主数组,而不是工作区数组。 (每个子节点步骤上的工作区和主交换角色。)叶级排序使用关联的顺序排序。
 * 
 * 合并类对Sorter执行合并。它们的结构使得如果底层的排序是稳定的(对于TimSort是正确的),那么整个排序也是如此。
 * 如果足够大,它们将两个分区中的最大分割成两半,通过二分搜索找到小分区中的最大点小于较大分区的下半部分的开始;然后并行合并这两个分区。
 * 部分地,为了确保以稳定性保持顺序触发任务,当前的CountedCompleter设计需要一些小任务作为用于触发完成任务的占位符。
 * 这些类(EmptyCompleter和Relay)不需要跟踪数组,并且不会被分叉,所以不要保持任何状态。
 * 
 */
/*package*/ class ArraysParallelSortHelpers {

    /*
     * Style note: The task classes have a lot of parameters, that are
     * stored as task fields and copied to local variables and used in
     * compute() methods, We pack these into as few lines as possible,
     * and hoist consistency checks among them before main loops, to
     * reduce distraction.
     * <p>
     *  原语类版本(FJByte ... FJDouble)除了类型声明之外彼此相同。
     * 
     *  基本顺序排序依赖于接受我们已经分配的临时工作区数组切片的TimSort,ComparableTimSort和DualPivotQuicksort排序方法的非公开版本,因此避免了冗余分配。
     *  (除了DualPivotQuicksort byte []排序,它不使用工作区数组)。
     * 
     */

    /**
     * A placeholder task for Sorters, used for the lowest
     * quartile task, that does not need to maintain array state.
     * <p>
     *  / *样式注释：任务类有很多参数,它们被存储为任务字段并复制到局部变量并在compute()方法中使用,我们将这些参数包含在尽可能少的行中,并在它们之间提升一致性检查主循环,以减少分心。
     * 
     */
    static final class EmptyCompleter extends CountedCompleter<Void> {
        static final long serialVersionUID = 2446542900576103244L;
        EmptyCompleter(CountedCompleter<?> p) { super(p); }
        public final void compute() { }
    }

    /**
     * A trigger for secondary merge of two merges
     * <p>
     * 用于最低四分位数任务的Sorters的占位符任务,不需要保持数组状态。
     * 
     */
    static final class Relay extends CountedCompleter<Void> {
        static final long serialVersionUID = 2446542900576103244L;
        final CountedCompleter<?> task;
        Relay(CountedCompleter<?> task) {
            super(null, 1);
            this.task = task;
        }
        public final void compute() { }
        public final void onCompletion(CountedCompleter<?> t) {
            task.compute();
        }
    }

    /** Object + Comparator support class */
    static final class FJObject {
        static final class Sorter<T> extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final T[] a, w;
            final int base, size, wbase, gran;
            Comparator<? super T> comparator;
            Sorter(CountedCompleter<?> par, T[] a, T[] w, int base, int size,
                   int wbase, int gran,
                   Comparator<? super T> comparator) {
                super(par);
                this.a = a; this.w = w; this.base = base; this.size = size;
                this.wbase = wbase; this.gran = gran;
                this.comparator = comparator;
            }
            public final void compute() {
                CountedCompleter<?> s = this;
                Comparator<? super T> c = this.comparator;
                T[] a = this.a, w = this.w; // localize all params
                int b = this.base, n = this.size, wb = this.wbase, g = this.gran;
                while (n > g) {
                    int h = n >>> 1, q = h >>> 1, u = h + q; // quartiles
                    Relay fc = new Relay(new Merger<T>(s, w, a, wb, h,
                                                       wb+h, n-h, b, g, c));
                    Relay rc = new Relay(new Merger<T>(fc, a, w, b+h, q,
                                                       b+u, n-u, wb+h, g, c));
                    new Sorter<T>(rc, a, w, b+u, n-u, wb+u, g, c).fork();
                    new Sorter<T>(rc, a, w, b+h, q, wb+h, g, c).fork();;
                    Relay bc = new Relay(new Merger<T>(fc, a, w, b, q,
                                                       b+q, h-q, wb, g, c));
                    new Sorter<T>(bc, a, w, b+q, h-q, wb+q, g, c).fork();
                    s = new EmptyCompleter(bc);
                    n = q;
                }
                TimSort.sort(a, b, b + n, c, w, wb, n);
                s.tryComplete();
            }
        }

        static final class Merger<T> extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final T[] a, w; // main and workspace arrays
            final int lbase, lsize, rbase, rsize, wbase, gran;
            Comparator<? super T> comparator;
            Merger(CountedCompleter<?> par, T[] a, T[] w,
                   int lbase, int lsize, int rbase,
                   int rsize, int wbase, int gran,
                   Comparator<? super T> comparator) {
                super(par);
                this.a = a; this.w = w;
                this.lbase = lbase; this.lsize = lsize;
                this.rbase = rbase; this.rsize = rsize;
                this.wbase = wbase; this.gran = gran;
                this.comparator = comparator;
            }

            public final void compute() {
                Comparator<? super T> c = this.comparator;
                T[] a = this.a, w = this.w; // localize all params
                int lb = this.lbase, ln = this.lsize, rb = this.rbase,
                    rn = this.rsize, k = this.wbase, g = this.gran;
                if (a == null || w == null || lb < 0 || rb < 0 || k < 0 ||
                    c == null)
                    throw new IllegalStateException(); // hoist checks
                for (int lh, rh;;) {  // split larger, find point in smaller
                    if (ln >= rn) {
                        if (ln <= g)
                            break;
                        rh = rn;
                        T split = a[(lh = ln >>> 1) + lb];
                        for (int lo = 0; lo < rh; ) {
                            int rm = (lo + rh) >>> 1;
                            if (c.compare(split, a[rm + rb]) <= 0)
                                rh = rm;
                            else
                                lo = rm + 1;
                        }
                    }
                    else {
                        if (rn <= g)
                            break;
                        lh = ln;
                        T split = a[(rh = rn >>> 1) + rb];
                        for (int lo = 0; lo < lh; ) {
                            int lm = (lo + lh) >>> 1;
                            if (c.compare(split, a[lm + lb]) <= 0)
                                lh = lm;
                            else
                                lo = lm + 1;
                        }
                    }
                    Merger<T> m = new Merger<T>(this, a, w, lb + lh, ln - lh,
                                                rb + rh, rn - rh,
                                                k + lh + rh, g, c);
                    rn = rh;
                    ln = lh;
                    addToPendingCount(1);
                    m.fork();
                }

                int lf = lb + ln, rf = rb + rn; // index bounds
                while (lb < lf && rb < rf) {
                    T t, al, ar;
                    if (c.compare((al = a[lb]), (ar = a[rb])) <= 0) {
                        lb++; t = al;
                    }
                    else {
                        rb++; t = ar;
                    }
                    w[k++] = t;
                }
                if (rb < rf)
                    System.arraycopy(a, rb, w, k, rf - rb);
                else if (lb < lf)
                    System.arraycopy(a, lb, w, k, lf - lb);

                tryComplete();
            }

        }
    } // FJObject

    /** byte support class */
    static final class FJByte {
        static final class Sorter extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final byte[] a, w;
            final int base, size, wbase, gran;
            Sorter(CountedCompleter<?> par, byte[] a, byte[] w, int base,
                   int size, int wbase, int gran) {
                super(par);
                this.a = a; this.w = w; this.base = base; this.size = size;
                this.wbase = wbase; this.gran = gran;
            }
            public final void compute() {
                CountedCompleter<?> s = this;
                byte[] a = this.a, w = this.w; // localize all params
                int b = this.base, n = this.size, wb = this.wbase, g = this.gran;
                while (n > g) {
                    int h = n >>> 1, q = h >>> 1, u = h + q; // quartiles
                    Relay fc = new Relay(new Merger(s, w, a, wb, h,
                                                    wb+h, n-h, b, g));
                    Relay rc = new Relay(new Merger(fc, a, w, b+h, q,
                                                    b+u, n-u, wb+h, g));
                    new Sorter(rc, a, w, b+u, n-u, wb+u, g).fork();
                    new Sorter(rc, a, w, b+h, q, wb+h, g).fork();;
                    Relay bc = new Relay(new Merger(fc, a, w, b, q,
                                                    b+q, h-q, wb, g));
                    new Sorter(bc, a, w, b+q, h-q, wb+q, g).fork();
                    s = new EmptyCompleter(bc);
                    n = q;
                }
                DualPivotQuicksort.sort(a, b, b + n - 1);
                s.tryComplete();
            }
        }

        static final class Merger extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final byte[] a, w; // main and workspace arrays
            final int lbase, lsize, rbase, rsize, wbase, gran;
            Merger(CountedCompleter<?> par, byte[] a, byte[] w,
                   int lbase, int lsize, int rbase,
                   int rsize, int wbase, int gran) {
                super(par);
                this.a = a; this.w = w;
                this.lbase = lbase; this.lsize = lsize;
                this.rbase = rbase; this.rsize = rsize;
                this.wbase = wbase; this.gran = gran;
            }

            public final void compute() {
                byte[] a = this.a, w = this.w; // localize all params
                int lb = this.lbase, ln = this.lsize, rb = this.rbase,
                    rn = this.rsize, k = this.wbase, g = this.gran;
                if (a == null || w == null || lb < 0 || rb < 0 || k < 0)
                    throw new IllegalStateException(); // hoist checks
                for (int lh, rh;;) {  // split larger, find point in smaller
                    if (ln >= rn) {
                        if (ln <= g)
                            break;
                        rh = rn;
                        byte split = a[(lh = ln >>> 1) + lb];
                        for (int lo = 0; lo < rh; ) {
                            int rm = (lo + rh) >>> 1;
                            if (split <= a[rm + rb])
                                rh = rm;
                            else
                                lo = rm + 1;
                        }
                    }
                    else {
                        if (rn <= g)
                            break;
                        lh = ln;
                        byte split = a[(rh = rn >>> 1) + rb];
                        for (int lo = 0; lo < lh; ) {
                            int lm = (lo + lh) >>> 1;
                            if (split <= a[lm + lb])
                                lh = lm;
                            else
                                lo = lm + 1;
                        }
                    }
                    Merger m = new Merger(this, a, w, lb + lh, ln - lh,
                                          rb + rh, rn - rh,
                                          k + lh + rh, g);
                    rn = rh;
                    ln = lh;
                    addToPendingCount(1);
                    m.fork();
                }

                int lf = lb + ln, rf = rb + rn; // index bounds
                while (lb < lf && rb < rf) {
                    byte t, al, ar;
                    if ((al = a[lb]) <= (ar = a[rb])) {
                        lb++; t = al;
                    }
                    else {
                        rb++; t = ar;
                    }
                    w[k++] = t;
                }
                if (rb < rf)
                    System.arraycopy(a, rb, w, k, rf - rb);
                else if (lb < lf)
                    System.arraycopy(a, lb, w, k, lf - lb);
                tryComplete();
            }
        }
    } // FJByte

    /** char support class */
    static final class FJChar {
        static final class Sorter extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final char[] a, w;
            final int base, size, wbase, gran;
            Sorter(CountedCompleter<?> par, char[] a, char[] w, int base,
                   int size, int wbase, int gran) {
                super(par);
                this.a = a; this.w = w; this.base = base; this.size = size;
                this.wbase = wbase; this.gran = gran;
            }
            public final void compute() {
                CountedCompleter<?> s = this;
                char[] a = this.a, w = this.w; // localize all params
                int b = this.base, n = this.size, wb = this.wbase, g = this.gran;
                while (n > g) {
                    int h = n >>> 1, q = h >>> 1, u = h + q; // quartiles
                    Relay fc = new Relay(new Merger(s, w, a, wb, h,
                                                    wb+h, n-h, b, g));
                    Relay rc = new Relay(new Merger(fc, a, w, b+h, q,
                                                    b+u, n-u, wb+h, g));
                    new Sorter(rc, a, w, b+u, n-u, wb+u, g).fork();
                    new Sorter(rc, a, w, b+h, q, wb+h, g).fork();;
                    Relay bc = new Relay(new Merger(fc, a, w, b, q,
                                                    b+q, h-q, wb, g));
                    new Sorter(bc, a, w, b+q, h-q, wb+q, g).fork();
                    s = new EmptyCompleter(bc);
                    n = q;
                }
                DualPivotQuicksort.sort(a, b, b + n - 1, w, wb, n);
                s.tryComplete();
            }
        }

        static final class Merger extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final char[] a, w; // main and workspace arrays
            final int lbase, lsize, rbase, rsize, wbase, gran;
            Merger(CountedCompleter<?> par, char[] a, char[] w,
                   int lbase, int lsize, int rbase,
                   int rsize, int wbase, int gran) {
                super(par);
                this.a = a; this.w = w;
                this.lbase = lbase; this.lsize = lsize;
                this.rbase = rbase; this.rsize = rsize;
                this.wbase = wbase; this.gran = gran;
            }

            public final void compute() {
                char[] a = this.a, w = this.w; // localize all params
                int lb = this.lbase, ln = this.lsize, rb = this.rbase,
                    rn = this.rsize, k = this.wbase, g = this.gran;
                if (a == null || w == null || lb < 0 || rb < 0 || k < 0)
                    throw new IllegalStateException(); // hoist checks
                for (int lh, rh;;) {  // split larger, find point in smaller
                    if (ln >= rn) {
                        if (ln <= g)
                            break;
                        rh = rn;
                        char split = a[(lh = ln >>> 1) + lb];
                        for (int lo = 0; lo < rh; ) {
                            int rm = (lo + rh) >>> 1;
                            if (split <= a[rm + rb])
                                rh = rm;
                            else
                                lo = rm + 1;
                        }
                    }
                    else {
                        if (rn <= g)
                            break;
                        lh = ln;
                        char split = a[(rh = rn >>> 1) + rb];
                        for (int lo = 0; lo < lh; ) {
                            int lm = (lo + lh) >>> 1;
                            if (split <= a[lm + lb])
                                lh = lm;
                            else
                                lo = lm + 1;
                        }
                    }
                    Merger m = new Merger(this, a, w, lb + lh, ln - lh,
                                          rb + rh, rn - rh,
                                          k + lh + rh, g);
                    rn = rh;
                    ln = lh;
                    addToPendingCount(1);
                    m.fork();
                }

                int lf = lb + ln, rf = rb + rn; // index bounds
                while (lb < lf && rb < rf) {
                    char t, al, ar;
                    if ((al = a[lb]) <= (ar = a[rb])) {
                        lb++; t = al;
                    }
                    else {
                        rb++; t = ar;
                    }
                    w[k++] = t;
                }
                if (rb < rf)
                    System.arraycopy(a, rb, w, k, rf - rb);
                else if (lb < lf)
                    System.arraycopy(a, lb, w, k, lf - lb);
                tryComplete();
            }
        }
    } // FJChar

    /** short support class */
    static final class FJShort {
        static final class Sorter extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final short[] a, w;
            final int base, size, wbase, gran;
            Sorter(CountedCompleter<?> par, short[] a, short[] w, int base,
                   int size, int wbase, int gran) {
                super(par);
                this.a = a; this.w = w; this.base = base; this.size = size;
                this.wbase = wbase; this.gran = gran;
            }
            public final void compute() {
                CountedCompleter<?> s = this;
                short[] a = this.a, w = this.w; // localize all params
                int b = this.base, n = this.size, wb = this.wbase, g = this.gran;
                while (n > g) {
                    int h = n >>> 1, q = h >>> 1, u = h + q; // quartiles
                    Relay fc = new Relay(new Merger(s, w, a, wb, h,
                                                    wb+h, n-h, b, g));
                    Relay rc = new Relay(new Merger(fc, a, w, b+h, q,
                                                    b+u, n-u, wb+h, g));
                    new Sorter(rc, a, w, b+u, n-u, wb+u, g).fork();
                    new Sorter(rc, a, w, b+h, q, wb+h, g).fork();;
                    Relay bc = new Relay(new Merger(fc, a, w, b, q,
                                                    b+q, h-q, wb, g));
                    new Sorter(bc, a, w, b+q, h-q, wb+q, g).fork();
                    s = new EmptyCompleter(bc);
                    n = q;
                }
                DualPivotQuicksort.sort(a, b, b + n - 1, w, wb, n);
                s.tryComplete();
            }
        }

        static final class Merger extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final short[] a, w; // main and workspace arrays
            final int lbase, lsize, rbase, rsize, wbase, gran;
            Merger(CountedCompleter<?> par, short[] a, short[] w,
                   int lbase, int lsize, int rbase,
                   int rsize, int wbase, int gran) {
                super(par);
                this.a = a; this.w = w;
                this.lbase = lbase; this.lsize = lsize;
                this.rbase = rbase; this.rsize = rsize;
                this.wbase = wbase; this.gran = gran;
            }

            public final void compute() {
                short[] a = this.a, w = this.w; // localize all params
                int lb = this.lbase, ln = this.lsize, rb = this.rbase,
                    rn = this.rsize, k = this.wbase, g = this.gran;
                if (a == null || w == null || lb < 0 || rb < 0 || k < 0)
                    throw new IllegalStateException(); // hoist checks
                for (int lh, rh;;) {  // split larger, find point in smaller
                    if (ln >= rn) {
                        if (ln <= g)
                            break;
                        rh = rn;
                        short split = a[(lh = ln >>> 1) + lb];
                        for (int lo = 0; lo < rh; ) {
                            int rm = (lo + rh) >>> 1;
                            if (split <= a[rm + rb])
                                rh = rm;
                            else
                                lo = rm + 1;
                        }
                    }
                    else {
                        if (rn <= g)
                            break;
                        lh = ln;
                        short split = a[(rh = rn >>> 1) + rb];
                        for (int lo = 0; lo < lh; ) {
                            int lm = (lo + lh) >>> 1;
                            if (split <= a[lm + lb])
                                lh = lm;
                            else
                                lo = lm + 1;
                        }
                    }
                    Merger m = new Merger(this, a, w, lb + lh, ln - lh,
                                          rb + rh, rn - rh,
                                          k + lh + rh, g);
                    rn = rh;
                    ln = lh;
                    addToPendingCount(1);
                    m.fork();
                }

                int lf = lb + ln, rf = rb + rn; // index bounds
                while (lb < lf && rb < rf) {
                    short t, al, ar;
                    if ((al = a[lb]) <= (ar = a[rb])) {
                        lb++; t = al;
                    }
                    else {
                        rb++; t = ar;
                    }
                    w[k++] = t;
                }
                if (rb < rf)
                    System.arraycopy(a, rb, w, k, rf - rb);
                else if (lb < lf)
                    System.arraycopy(a, lb, w, k, lf - lb);
                tryComplete();
            }
        }
    } // FJShort

    /** int support class */
    static final class FJInt {
        static final class Sorter extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final int[] a, w;
            final int base, size, wbase, gran;
            Sorter(CountedCompleter<?> par, int[] a, int[] w, int base,
                   int size, int wbase, int gran) {
                super(par);
                this.a = a; this.w = w; this.base = base; this.size = size;
                this.wbase = wbase; this.gran = gran;
            }
            public final void compute() {
                CountedCompleter<?> s = this;
                int[] a = this.a, w = this.w; // localize all params
                int b = this.base, n = this.size, wb = this.wbase, g = this.gran;
                while (n > g) {
                    int h = n >>> 1, q = h >>> 1, u = h + q; // quartiles
                    Relay fc = new Relay(new Merger(s, w, a, wb, h,
                                                    wb+h, n-h, b, g));
                    Relay rc = new Relay(new Merger(fc, a, w, b+h, q,
                                                    b+u, n-u, wb+h, g));
                    new Sorter(rc, a, w, b+u, n-u, wb+u, g).fork();
                    new Sorter(rc, a, w, b+h, q, wb+h, g).fork();;
                    Relay bc = new Relay(new Merger(fc, a, w, b, q,
                                                    b+q, h-q, wb, g));
                    new Sorter(bc, a, w, b+q, h-q, wb+q, g).fork();
                    s = new EmptyCompleter(bc);
                    n = q;
                }
                DualPivotQuicksort.sort(a, b, b + n - 1, w, wb, n);
                s.tryComplete();
            }
        }

        static final class Merger extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final int[] a, w; // main and workspace arrays
            final int lbase, lsize, rbase, rsize, wbase, gran;
            Merger(CountedCompleter<?> par, int[] a, int[] w,
                   int lbase, int lsize, int rbase,
                   int rsize, int wbase, int gran) {
                super(par);
                this.a = a; this.w = w;
                this.lbase = lbase; this.lsize = lsize;
                this.rbase = rbase; this.rsize = rsize;
                this.wbase = wbase; this.gran = gran;
            }

            public final void compute() {
                int[] a = this.a, w = this.w; // localize all params
                int lb = this.lbase, ln = this.lsize, rb = this.rbase,
                    rn = this.rsize, k = this.wbase, g = this.gran;
                if (a == null || w == null || lb < 0 || rb < 0 || k < 0)
                    throw new IllegalStateException(); // hoist checks
                for (int lh, rh;;) {  // split larger, find point in smaller
                    if (ln >= rn) {
                        if (ln <= g)
                            break;
                        rh = rn;
                        int split = a[(lh = ln >>> 1) + lb];
                        for (int lo = 0; lo < rh; ) {
                            int rm = (lo + rh) >>> 1;
                            if (split <= a[rm + rb])
                                rh = rm;
                            else
                                lo = rm + 1;
                        }
                    }
                    else {
                        if (rn <= g)
                            break;
                        lh = ln;
                        int split = a[(rh = rn >>> 1) + rb];
                        for (int lo = 0; lo < lh; ) {
                            int lm = (lo + lh) >>> 1;
                            if (split <= a[lm + lb])
                                lh = lm;
                            else
                                lo = lm + 1;
                        }
                    }
                    Merger m = new Merger(this, a, w, lb + lh, ln - lh,
                                          rb + rh, rn - rh,
                                          k + lh + rh, g);
                    rn = rh;
                    ln = lh;
                    addToPendingCount(1);
                    m.fork();
                }

                int lf = lb + ln, rf = rb + rn; // index bounds
                while (lb < lf && rb < rf) {
                    int t, al, ar;
                    if ((al = a[lb]) <= (ar = a[rb])) {
                        lb++; t = al;
                    }
                    else {
                        rb++; t = ar;
                    }
                    w[k++] = t;
                }
                if (rb < rf)
                    System.arraycopy(a, rb, w, k, rf - rb);
                else if (lb < lf)
                    System.arraycopy(a, lb, w, k, lf - lb);
                tryComplete();
            }
        }
    } // FJInt

    /** long support class */
    static final class FJLong {
        static final class Sorter extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final long[] a, w;
            final int base, size, wbase, gran;
            Sorter(CountedCompleter<?> par, long[] a, long[] w, int base,
                   int size, int wbase, int gran) {
                super(par);
                this.a = a; this.w = w; this.base = base; this.size = size;
                this.wbase = wbase; this.gran = gran;
            }
            public final void compute() {
                CountedCompleter<?> s = this;
                long[] a = this.a, w = this.w; // localize all params
                int b = this.base, n = this.size, wb = this.wbase, g = this.gran;
                while (n > g) {
                    int h = n >>> 1, q = h >>> 1, u = h + q; // quartiles
                    Relay fc = new Relay(new Merger(s, w, a, wb, h,
                                                    wb+h, n-h, b, g));
                    Relay rc = new Relay(new Merger(fc, a, w, b+h, q,
                                                    b+u, n-u, wb+h, g));
                    new Sorter(rc, a, w, b+u, n-u, wb+u, g).fork();
                    new Sorter(rc, a, w, b+h, q, wb+h, g).fork();;
                    Relay bc = new Relay(new Merger(fc, a, w, b, q,
                                                    b+q, h-q, wb, g));
                    new Sorter(bc, a, w, b+q, h-q, wb+q, g).fork();
                    s = new EmptyCompleter(bc);
                    n = q;
                }
                DualPivotQuicksort.sort(a, b, b + n - 1, w, wb, n);
                s.tryComplete();
            }
        }

        static final class Merger extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final long[] a, w; // main and workspace arrays
            final int lbase, lsize, rbase, rsize, wbase, gran;
            Merger(CountedCompleter<?> par, long[] a, long[] w,
                   int lbase, int lsize, int rbase,
                   int rsize, int wbase, int gran) {
                super(par);
                this.a = a; this.w = w;
                this.lbase = lbase; this.lsize = lsize;
                this.rbase = rbase; this.rsize = rsize;
                this.wbase = wbase; this.gran = gran;
            }

            public final void compute() {
                long[] a = this.a, w = this.w; // localize all params
                int lb = this.lbase, ln = this.lsize, rb = this.rbase,
                    rn = this.rsize, k = this.wbase, g = this.gran;
                if (a == null || w == null || lb < 0 || rb < 0 || k < 0)
                    throw new IllegalStateException(); // hoist checks
                for (int lh, rh;;) {  // split larger, find point in smaller
                    if (ln >= rn) {
                        if (ln <= g)
                            break;
                        rh = rn;
                        long split = a[(lh = ln >>> 1) + lb];
                        for (int lo = 0; lo < rh; ) {
                            int rm = (lo + rh) >>> 1;
                            if (split <= a[rm + rb])
                                rh = rm;
                            else
                                lo = rm + 1;
                        }
                    }
                    else {
                        if (rn <= g)
                            break;
                        lh = ln;
                        long split = a[(rh = rn >>> 1) + rb];
                        for (int lo = 0; lo < lh; ) {
                            int lm = (lo + lh) >>> 1;
                            if (split <= a[lm + lb])
                                lh = lm;
                            else
                                lo = lm + 1;
                        }
                    }
                    Merger m = new Merger(this, a, w, lb + lh, ln - lh,
                                          rb + rh, rn - rh,
                                          k + lh + rh, g);
                    rn = rh;
                    ln = lh;
                    addToPendingCount(1);
                    m.fork();
                }

                int lf = lb + ln, rf = rb + rn; // index bounds
                while (lb < lf && rb < rf) {
                    long t, al, ar;
                    if ((al = a[lb]) <= (ar = a[rb])) {
                        lb++; t = al;
                    }
                    else {
                        rb++; t = ar;
                    }
                    w[k++] = t;
                }
                if (rb < rf)
                    System.arraycopy(a, rb, w, k, rf - rb);
                else if (lb < lf)
                    System.arraycopy(a, lb, w, k, lf - lb);
                tryComplete();
            }
        }
    } // FJLong

    /** float support class */
    static final class FJFloat {
        static final class Sorter extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final float[] a, w;
            final int base, size, wbase, gran;
            Sorter(CountedCompleter<?> par, float[] a, float[] w, int base,
                   int size, int wbase, int gran) {
                super(par);
                this.a = a; this.w = w; this.base = base; this.size = size;
                this.wbase = wbase; this.gran = gran;
            }
            public final void compute() {
                CountedCompleter<?> s = this;
                float[] a = this.a, w = this.w; // localize all params
                int b = this.base, n = this.size, wb = this.wbase, g = this.gran;
                while (n > g) {
                    int h = n >>> 1, q = h >>> 1, u = h + q; // quartiles
                    Relay fc = new Relay(new Merger(s, w, a, wb, h,
                                                    wb+h, n-h, b, g));
                    Relay rc = new Relay(new Merger(fc, a, w, b+h, q,
                                                    b+u, n-u, wb+h, g));
                    new Sorter(rc, a, w, b+u, n-u, wb+u, g).fork();
                    new Sorter(rc, a, w, b+h, q, wb+h, g).fork();;
                    Relay bc = new Relay(new Merger(fc, a, w, b, q,
                                                    b+q, h-q, wb, g));
                    new Sorter(bc, a, w, b+q, h-q, wb+q, g).fork();
                    s = new EmptyCompleter(bc);
                    n = q;
                }
                DualPivotQuicksort.sort(a, b, b + n - 1, w, wb, n);
                s.tryComplete();
            }
        }

        static final class Merger extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final float[] a, w; // main and workspace arrays
            final int lbase, lsize, rbase, rsize, wbase, gran;
            Merger(CountedCompleter<?> par, float[] a, float[] w,
                   int lbase, int lsize, int rbase,
                   int rsize, int wbase, int gran) {
                super(par);
                this.a = a; this.w = w;
                this.lbase = lbase; this.lsize = lsize;
                this.rbase = rbase; this.rsize = rsize;
                this.wbase = wbase; this.gran = gran;
            }

            public final void compute() {
                float[] a = this.a, w = this.w; // localize all params
                int lb = this.lbase, ln = this.lsize, rb = this.rbase,
                    rn = this.rsize, k = this.wbase, g = this.gran;
                if (a == null || w == null || lb < 0 || rb < 0 || k < 0)
                    throw new IllegalStateException(); // hoist checks
                for (int lh, rh;;) {  // split larger, find point in smaller
                    if (ln >= rn) {
                        if (ln <= g)
                            break;
                        rh = rn;
                        float split = a[(lh = ln >>> 1) + lb];
                        for (int lo = 0; lo < rh; ) {
                            int rm = (lo + rh) >>> 1;
                            if (split <= a[rm + rb])
                                rh = rm;
                            else
                                lo = rm + 1;
                        }
                    }
                    else {
                        if (rn <= g)
                            break;
                        lh = ln;
                        float split = a[(rh = rn >>> 1) + rb];
                        for (int lo = 0; lo < lh; ) {
                            int lm = (lo + lh) >>> 1;
                            if (split <= a[lm + lb])
                                lh = lm;
                            else
                                lo = lm + 1;
                        }
                    }
                    Merger m = new Merger(this, a, w, lb + lh, ln - lh,
                                          rb + rh, rn - rh,
                                          k + lh + rh, g);
                    rn = rh;
                    ln = lh;
                    addToPendingCount(1);
                    m.fork();
                }

                int lf = lb + ln, rf = rb + rn; // index bounds
                while (lb < lf && rb < rf) {
                    float t, al, ar;
                    if ((al = a[lb]) <= (ar = a[rb])) {
                        lb++; t = al;
                    }
                    else {
                        rb++; t = ar;
                    }
                    w[k++] = t;
                }
                if (rb < rf)
                    System.arraycopy(a, rb, w, k, rf - rb);
                else if (lb < lf)
                    System.arraycopy(a, lb, w, k, lf - lb);
                tryComplete();
            }
        }
    } // FJFloat

    /** double support class */
    static final class FJDouble {
        static final class Sorter extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final double[] a, w;
            final int base, size, wbase, gran;
            Sorter(CountedCompleter<?> par, double[] a, double[] w, int base,
                   int size, int wbase, int gran) {
                super(par);
                this.a = a; this.w = w; this.base = base; this.size = size;
                this.wbase = wbase; this.gran = gran;
            }
            public final void compute() {
                CountedCompleter<?> s = this;
                double[] a = this.a, w = this.w; // localize all params
                int b = this.base, n = this.size, wb = this.wbase, g = this.gran;
                while (n > g) {
                    int h = n >>> 1, q = h >>> 1, u = h + q; // quartiles
                    Relay fc = new Relay(new Merger(s, w, a, wb, h,
                                                    wb+h, n-h, b, g));
                    Relay rc = new Relay(new Merger(fc, a, w, b+h, q,
                                                    b+u, n-u, wb+h, g));
                    new Sorter(rc, a, w, b+u, n-u, wb+u, g).fork();
                    new Sorter(rc, a, w, b+h, q, wb+h, g).fork();;
                    Relay bc = new Relay(new Merger(fc, a, w, b, q,
                                                    b+q, h-q, wb, g));
                    new Sorter(bc, a, w, b+q, h-q, wb+q, g).fork();
                    s = new EmptyCompleter(bc);
                    n = q;
                }
                DualPivotQuicksort.sort(a, b, b + n - 1, w, wb, n);
                s.tryComplete();
            }
        }

        static final class Merger extends CountedCompleter<Void> {
            static final long serialVersionUID = 2446542900576103244L;
            final double[] a, w; // main and workspace arrays
            final int lbase, lsize, rbase, rsize, wbase, gran;
            Merger(CountedCompleter<?> par, double[] a, double[] w,
                   int lbase, int lsize, int rbase,
                   int rsize, int wbase, int gran) {
                super(par);
                this.a = a; this.w = w;
                this.lbase = lbase; this.lsize = lsize;
                this.rbase = rbase; this.rsize = rsize;
                this.wbase = wbase; this.gran = gran;
            }

            public final void compute() {
                double[] a = this.a, w = this.w; // localize all params
                int lb = this.lbase, ln = this.lsize, rb = this.rbase,
                    rn = this.rsize, k = this.wbase, g = this.gran;
                if (a == null || w == null || lb < 0 || rb < 0 || k < 0)
                    throw new IllegalStateException(); // hoist checks
                for (int lh, rh;;) {  // split larger, find point in smaller
                    if (ln >= rn) {
                        if (ln <= g)
                            break;
                        rh = rn;
                        double split = a[(lh = ln >>> 1) + lb];
                        for (int lo = 0; lo < rh; ) {
                            int rm = (lo + rh) >>> 1;
                            if (split <= a[rm + rb])
                                rh = rm;
                            else
                                lo = rm + 1;
                        }
                    }
                    else {
                        if (rn <= g)
                            break;
                        lh = ln;
                        double split = a[(rh = rn >>> 1) + rb];
                        for (int lo = 0; lo < lh; ) {
                            int lm = (lo + lh) >>> 1;
                            if (split <= a[lm + lb])
                                lh = lm;
                            else
                                lo = lm + 1;
                        }
                    }
                    Merger m = new Merger(this, a, w, lb + lh, ln - lh,
                                          rb + rh, rn - rh,
                                          k + lh + rh, g);
                    rn = rh;
                    ln = lh;
                    addToPendingCount(1);
                    m.fork();
                }

                int lf = lb + ln, rf = rb + rn; // index bounds
                while (lb < lf && rb < rf) {
                    double t, al, ar;
                    if ((al = a[lb]) <= (ar = a[rb])) {
                        lb++; t = al;
                    }
                    else {
                        rb++; t = ar;
                    }
                    w[k++] = t;
                }
                if (rb < rf)
                    System.arraycopy(a, rb, w, k, rf - rb);
                else if (lb < lf)
                    System.arraycopy(a, lb, w, k, lf - lb);
                tryComplete();
            }
        }
    } // FJDouble

}
