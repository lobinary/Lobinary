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
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A synchronization aid that allows a set of threads to all wait for
 * each other to reach a common barrier point.  CyclicBarriers are
 * useful in programs involving a fixed sized party of threads that
 * must occasionally wait for each other. The barrier is called
 * <em>cyclic</em> because it can be re-used after the waiting threads
 * are released.
 *
 * <p>A {@code CyclicBarrier} supports an optional {@link Runnable} command
 * that is run once per barrier point, after the last thread in the party
 * arrives, but before any threads are released.
 * This <em>barrier action</em> is useful
 * for updating shared-state before any of the parties continue.
 *
 * <p><b>Sample usage:</b> Here is an example of using a barrier in a
 * parallel decomposition design:
 *
 *  <pre> {@code
 * class Solver {
 *   final int N;
 *   final float[][] data;
 *   final CyclicBarrier barrier;
 *
 *   class Worker implements Runnable {
 *     int myRow;
 *     Worker(int row) { myRow = row; }
 *     public void run() {
 *       while (!done()) {
 *         processRow(myRow);
 *
 *         try {
 *           barrier.await();
 *         } catch (InterruptedException ex) {
 *           return;
 *         } catch (BrokenBarrierException ex) {
 *           return;
 *         }
 *       }
 *     }
 *   }
 *
 *   public Solver(float[][] matrix) {
 *     data = matrix;
 *     N = matrix.length;
 *     Runnable barrierAction =
 *       new Runnable() { public void run() { mergeRows(...); }};
 *     barrier = new CyclicBarrier(N, barrierAction);
 *
 *     List<Thread> threads = new ArrayList<Thread>(N);
 *     for (int i = 0; i < N; i++) {
 *       Thread thread = new Thread(new Worker(i));
 *       threads.add(thread);
 *       thread.start();
 *     }
 *
 *     // wait until done
 *     for (Thread thread : threads)
 *       thread.join();
 *   }
 * }}</pre>
 *
 * Here, each worker thread processes a row of the matrix then waits at the
 * barrier until all rows have been processed. When all rows are processed
 * the supplied {@link Runnable} barrier action is executed and merges the
 * rows. If the merger
 * determines that a solution has been found then {@code done()} will return
 * {@code true} and each worker will terminate.
 *
 * <p>If the barrier action does not rely on the parties being suspended when
 * it is executed, then any of the threads in the party could execute that
 * action when it is released. To facilitate this, each invocation of
 * {@link #await} returns the arrival index of that thread at the barrier.
 * You can then choose which thread should execute the barrier action, for
 * example:
 *  <pre> {@code
 * if (barrier.await() == 0) {
 *   // log the completion of this iteration
 * }}</pre>
 *
 * <p>The {@code CyclicBarrier} uses an all-or-none breakage model
 * for failed synchronization attempts: If a thread leaves a barrier
 * point prematurely because of interruption, failure, or timeout, all
 * other threads waiting at that barrier point will also leave
 * abnormally via {@link BrokenBarrierException} (or
 * {@link InterruptedException} if they too were interrupted at about
 * the same time).
 *
 * <p>Memory consistency effects: Actions in a thread prior to calling
 * {@code await()}
 * <a href="package-summary.html#MemoryVisibility"><i>happen-before</i></a>
 * actions that are part of the barrier action, which in turn
 * <i>happen-before</i> actions following a successful return from the
 * corresponding {@code await()} in other threads.
 *
 * <p>
 *  允许一组线程全部等待彼此到达公共屏障点的同步辅助。循环障碍在涉及必须偶尔彼此等待的固定大小的线程方的程序中是有用的。屏障称为<em> cyclic </em>,因为它可以在等待线程释放后重新使用。
 * 
 *  <@> {@code CyclicBarrier}支持一个可选的{@link Runnable}命令,该命令在每个屏障点运行一次,在该方中的最后一个线程到达之后,在任何线程释放之前。
 * 这个<em>屏障动作</em>对于在任何方继续之前更新共享状态是有用的。
 * 
 *  <p> <b>示例用法：</b>以下是在并行分解设计中使用障碍的示例：
 * 
 *  <pre> {@code class Solver {final int N; final float [] [] data;最终循环障碍;
 * 
 *  类Worker实现Runnable {int myRow; Worker(int row){myRow = row; } public void run(){while(！done()){processRow(myRow);。
 * 
 *  try {barrier.await(); } catch(InterruptedException ex){return; } catch(BrokenBarrierException ex){return; }
 * }}}。
 * 
 * public Solver(float [] [] matrix){data = matrix; N = matrix.length; Runnable barrierAction = new Runnable(){public void run(){mergeRows(...); }
 * }; barrier = new CyclicBarrier(N,barrierAction);。
 * 
 *  List <Thread> threads = new ArrayList <Thread>(N); for(int i = 0; i <N; i ++){Thread thread = new Thread(new Worker(i)); threads.add(thread); thread.start(); }}。
 * 
 *  // wait until done for(Thread thread：threads)thread.join(); }}} </pre>
 * 
 *  这里,每个工作线程处理矩阵的行,然后在屏障等待直到所有行都已被处理。处理所有行时,将执行提供的{@link Runnable}屏蔽操作,并合并行。
 * 如果合并确定找到了一个解决方案,那么{@code done()}将返回{@code true},每个工人都将终止。
 * 
 *  <p>如果屏障动作在执行时不依赖于被暂停的各方,则当方被释放时,方中的任何线程都可以执行该动作。为了方便起见,每次调用{@link #await}都会返回该线程在屏障处的到达索引。
 * 然后可以选择哪个线程执行屏障操作,例如：<pre> {@code if(barrier.await()== 0){//记录此次迭代的完成}} </pre>。
 * 
 * <p> {@code CyclicBarrier}对失败的同步尝试使用全或无破坏模型：如果线程由于中断,失败或超时而过早地离开障碍点,则在该障碍点等待的所有其他线程也将通过{@link BrokenBarrierException}
 * (或{@link InterruptedException},如果它们也大约同时中断)异常离开。
 * 
 *  <p>内存一致性影响：在调用{@code await()} <a href="package-summary.html#MemoryVisibility"> <i>发生之前</i> </a>操作之前线
 * 程中的操作它们是屏障动作的一部分,而这又是在从其他线程中的相应{@code await()}成功返回之后发生的</i>之前的动作。
 * 
 * 
 * @since 1.5
 * @see CountDownLatch
 *
 * @author Doug Lea
 */
public class CyclicBarrier {
    /**
     * Each use of the barrier is represented as a generation instance.
     * The generation changes whenever the barrier is tripped, or
     * is reset. There can be many generations associated with threads
     * using the barrier - due to the non-deterministic way the lock
     * may be allocated to waiting threads - but only one of these
     * can be active at a time (the one to which {@code count} applies)
     * and all the rest are either broken or tripped.
     * There need not be an active generation if there has been a break
     * but no subsequent reset.
     * <p>
     *  屏障的每个使用被表示为生成实例。只要屏障跳闸或复位,生成就会改变。
     * 可能有许多代与使用障碍的线程相关联 - 由于非确定性的方式,锁可以被分配给等待线程 - 但是其中只有一个可以在某一时间是活动的({@code count}应用的那一个),所有其余的都被打破或跳闸。
     * 如果有中断但没有随后的复位,则不需要有效的代。
     * 
     */
    private static class Generation {
        boolean broken = false;
    }

    /** The lock for guarding barrier entry */
    private final ReentrantLock lock = new ReentrantLock();
    /** Condition to wait on until tripped */
    private final Condition trip = lock.newCondition();
    /** The number of parties */
    private final int parties;
    /* The command to run when tripped */
    private final Runnable barrierCommand;
    /** The current generation */
    private Generation generation = new Generation();

    /**
     * Number of parties still waiting. Counts down from parties to 0
     * on each generation.  It is reset to parties on each new
     * generation or when broken.
     * <p>
     *  仍在等待的当事人数。从每一代计数到0。它在每个新一代或破碎时重置为聚会。
     * 
     */
    private int count;

    /**
     * Updates state on barrier trip and wakes up everyone.
     * Called only while holding lock.
     * <p>
     *  更新状态的屏障旅行,唤醒大家。仅在握住锁时调用。
     * 
     */
    private void nextGeneration() {
        // signal completion of last generation
        trip.signalAll();
        // set up next generation
        count = parties;
        generation = new Generation();
    }

    /**
     * Sets current barrier generation as broken and wakes up everyone.
     * Called only while holding lock.
     * <p>
     * 将当前屏障生成设置为已中断并唤醒每个人。仅在握住锁时调用。
     * 
     */
    private void breakBarrier() {
        generation.broken = true;
        count = parties;
        trip.signalAll();
    }

    /**
     * Main barrier code, covering the various policies.
     * <p>
     *  主要屏障代码,涵盖各种政策。
     * 
     */
    private int dowait(boolean timed, long nanos)
        throws InterruptedException, BrokenBarrierException,
               TimeoutException {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            final Generation g = generation;

            if (g.broken)
                throw new BrokenBarrierException();

            if (Thread.interrupted()) {
                breakBarrier();
                throw new InterruptedException();
            }

            int index = --count;
            if (index == 0) {  // tripped
                boolean ranAction = false;
                try {
                    final Runnable command = barrierCommand;
                    if (command != null)
                        command.run();
                    ranAction = true;
                    nextGeneration();
                    return 0;
                } finally {
                    if (!ranAction)
                        breakBarrier();
                }
            }

            // loop until tripped, broken, interrupted, or timed out
            for (;;) {
                try {
                    if (!timed)
                        trip.await();
                    else if (nanos > 0L)
                        nanos = trip.awaitNanos(nanos);
                } catch (InterruptedException ie) {
                    if (g == generation && ! g.broken) {
                        breakBarrier();
                        throw ie;
                    } else {
                        // We're about to finish waiting even if we had not
                        // been interrupted, so this interrupt is deemed to
                        // "belong" to subsequent execution.
                        Thread.currentThread().interrupt();
                    }
                }

                if (g.broken)
                    throw new BrokenBarrierException();

                if (g != generation)
                    return index;

                if (timed && nanos <= 0L) {
                    breakBarrier();
                    throw new TimeoutException();
                }
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * Creates a new {@code CyclicBarrier} that will trip when the
     * given number of parties (threads) are waiting upon it, and which
     * will execute the given barrier action when the barrier is tripped,
     * performed by the last thread entering the barrier.
     *
     * <p>
     *  创建一个新的{@code CyclicBarrier},当给定数量的参与者(线程)等待它时,它将跳闸,并且当障碍跳闸时执行给定的障碍动作,由最后一个线程进入障碍。
     * 
     * 
     * @param parties the number of threads that must invoke {@link #await}
     *        before the barrier is tripped
     * @param barrierAction the command to execute when the barrier is
     *        tripped, or {@code null} if there is no action
     * @throws IllegalArgumentException if {@code parties} is less than 1
     */
    public CyclicBarrier(int parties, Runnable barrierAction) {
        if (parties <= 0) throw new IllegalArgumentException();
        this.parties = parties;
        this.count = parties;
        this.barrierCommand = barrierAction;
    }

    /**
     * Creates a new {@code CyclicBarrier} that will trip when the
     * given number of parties (threads) are waiting upon it, and
     * does not perform a predefined action when the barrier is tripped.
     *
     * <p>
     *  创建一个新的{@code CyclicBarrier},当给定数量的参与者(线程)等待它时,它将跳闸,并且当障碍跳闸时不执行预定义的动作。
     * 
     * 
     * @param parties the number of threads that must invoke {@link #await}
     *        before the barrier is tripped
     * @throws IllegalArgumentException if {@code parties} is less than 1
     */
    public CyclicBarrier(int parties) {
        this(parties, null);
    }

    /**
     * Returns the number of parties required to trip this barrier.
     *
     * <p>
     *  返回跳过此障碍所需的参与者的数量。
     * 
     * 
     * @return the number of parties required to trip this barrier
     */
    public int getParties() {
        return parties;
    }

    /**
     * Waits until all {@linkplain #getParties parties} have invoked
     * {@code await} on this barrier.
     *
     * <p>If the current thread is not the last to arrive then it is
     * disabled for thread scheduling purposes and lies dormant until
     * one of the following things happens:
     * <ul>
     * <li>The last thread arrives; or
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread; or
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * one of the other waiting threads; or
     * <li>Some other thread times out while waiting for barrier; or
     * <li>Some other thread invokes {@link #reset} on this barrier.
     * </ul>
     *
     * <p>If the current thread:
     * <ul>
     * <li>has its interrupted status set on entry to this method; or
     * <li>is {@linkplain Thread#interrupt interrupted} while waiting
     * </ul>
     * then {@link InterruptedException} is thrown and the current thread's
     * interrupted status is cleared.
     *
     * <p>If the barrier is {@link #reset} while any thread is waiting,
     * or if the barrier {@linkplain #isBroken is broken} when
     * {@code await} is invoked, or while any thread is waiting, then
     * {@link BrokenBarrierException} is thrown.
     *
     * <p>If any thread is {@linkplain Thread#interrupt interrupted} while waiting,
     * then all other waiting threads will throw
     * {@link BrokenBarrierException} and the barrier is placed in the broken
     * state.
     *
     * <p>If the current thread is the last thread to arrive, and a
     * non-null barrier action was supplied in the constructor, then the
     * current thread runs the action before allowing the other threads to
     * continue.
     * If an exception occurs during the barrier action then that exception
     * will be propagated in the current thread and the barrier is placed in
     * the broken state.
     *
     * <p>
     *  等待所有{@linkplain #getParties party}在此障碍上调用{@code await}。
     * 
     *  <p>如果当前线程不是最后到达,那么它将被禁用以用于线程调度目的,并处于休眠状态,直到发生以下情况之一：
     * <ul>
     *  <li>最后一个线程到达;或<li>一些其他线程{@linkplain线程#中断中断}当前线程;或<li>一些其他线程{@linkplain线程#中断中断}其他等待线程之一;或<li>其他一些线程在等
     * 待屏障时超时;或<li>某些其他线程在此屏障上调用{@link #reset}。
     * </ul>
     * 
     *  <p>如果当前线程：
     * <ul>
     *  <li>在进入此方法时设置了中断状态;或<li>在{@linkplain线程#中断}时等待
     * </ul>
     *  那么将抛出{@link InterruptedException},并清除当前线程的中断状态。
     * 
     * <p>如果在任何线程等待时屏障为{@link #reset},或者{@code await}被调用时,或者当任何线程正在等待时,屏蔽{@linkplain #isBroken被破坏},那么{@链接BrokenBarrierException}
     * 抛出。
     * 
     *  <p>如果任何线程在等待期间{@linkplain线程#中断中断},则所有其他等待的线程将抛出{@link BrokenBarrierException}并且障碍被置于中断状态。
     * 
     *  <p>如果当前线程是到达的最后一个线程,并且在构造函数中提供了非空屏障操作,则当前线程运行操作,然后允许其他线程继续。如果在屏障动作期间发生异常,则该异常将在当前线程中传播,并且屏障被置于断开状态。
     * 
     * 
     * @return the arrival index of the current thread, where index
     *         {@code getParties() - 1} indicates the first
     *         to arrive and zero indicates the last to arrive
     * @throws InterruptedException if the current thread was interrupted
     *         while waiting
     * @throws BrokenBarrierException if <em>another</em> thread was
     *         interrupted or timed out while the current thread was
     *         waiting, or the barrier was reset, or the barrier was
     *         broken when {@code await} was called, or the barrier
     *         action (if present) failed due to an exception
     */
    public int await() throws InterruptedException, BrokenBarrierException {
        try {
            return dowait(false, 0L);
        } catch (TimeoutException toe) {
            throw new Error(toe); // cannot happen
        }
    }

    /**
     * Waits until all {@linkplain #getParties parties} have invoked
     * {@code await} on this barrier, or the specified waiting time elapses.
     *
     * <p>If the current thread is not the last to arrive then it is
     * disabled for thread scheduling purposes and lies dormant until
     * one of the following things happens:
     * <ul>
     * <li>The last thread arrives; or
     * <li>The specified timeout elapses; or
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * the current thread; or
     * <li>Some other thread {@linkplain Thread#interrupt interrupts}
     * one of the other waiting threads; or
     * <li>Some other thread times out while waiting for barrier; or
     * <li>Some other thread invokes {@link #reset} on this barrier.
     * </ul>
     *
     * <p>If the current thread:
     * <ul>
     * <li>has its interrupted status set on entry to this method; or
     * <li>is {@linkplain Thread#interrupt interrupted} while waiting
     * </ul>
     * then {@link InterruptedException} is thrown and the current thread's
     * interrupted status is cleared.
     *
     * <p>If the specified waiting time elapses then {@link TimeoutException}
     * is thrown. If the time is less than or equal to zero, the
     * method will not wait at all.
     *
     * <p>If the barrier is {@link #reset} while any thread is waiting,
     * or if the barrier {@linkplain #isBroken is broken} when
     * {@code await} is invoked, or while any thread is waiting, then
     * {@link BrokenBarrierException} is thrown.
     *
     * <p>If any thread is {@linkplain Thread#interrupt interrupted} while
     * waiting, then all other waiting threads will throw {@link
     * BrokenBarrierException} and the barrier is placed in the broken
     * state.
     *
     * <p>If the current thread is the last thread to arrive, and a
     * non-null barrier action was supplied in the constructor, then the
     * current thread runs the action before allowing the other threads to
     * continue.
     * If an exception occurs during the barrier action then that exception
     * will be propagated in the current thread and the barrier is placed in
     * the broken state.
     *
     * <p>
     *  等待直到所有{@linkplain #getParties party}已在此屏障上调用{@code await},或指定的等待时间已过。
     * 
     *  <p>如果当前线程不是最后到达,那么它将被禁用以用于线程调度目的,并处于休眠状态,直到发生以下情况之一：
     * <ul>
     *  <li>最后一个线程到达;或<li>指定的超时已过;或<li>一些其他线程{@linkplain线程#中断中断}当前线程;或<li>一些其他线程{@linkplain线程#中断中断}其他等待线程之一;
     * 或<li>其他一些线程在等待屏障时超时;或<li>某些其他线程在此屏障上调用{@link #reset}。
     * </ul>
     * 
     *  <p>如果当前线程：
     * <ul>
     * <li>在进入此方法时设置了中断状态;或<li>在{@linkplain线程#中断}时等待
     * </ul>
     *  那么将抛出{@link InterruptedException},并清除当前线程的中断状态。
     * 
     *  <p>如果指定的等待时间过去,则会抛出{@link TimeoutException}。如果时间小于或等于零,则该方法将不会等待。
     * 
     *  <p>如果在任何线程等待时屏障为{@link #reset},或者{@code await}被调用时,或者当任何线程正在等待时,屏蔽{@linkplain #isBroken被破坏},那么{@链接BrokenBarrierException}
     * 
     * @param timeout the time to wait for the barrier
     * @param unit the time unit of the timeout parameter
     * @return the arrival index of the current thread, where index
     *         {@code getParties() - 1} indicates the first
     *         to arrive and zero indicates the last to arrive
     * @throws InterruptedException if the current thread was interrupted
     *         while waiting
     * @throws TimeoutException if the specified timeout elapses.
     *         In this case the barrier will be broken.
     * @throws BrokenBarrierException if <em>another</em> thread was
     *         interrupted or timed out while the current thread was
     *         waiting, or the barrier was reset, or the barrier was broken
     *         when {@code await} was called, or the barrier action (if
     *         present) failed due to an exception
     */
    public int await(long timeout, TimeUnit unit)
        throws InterruptedException,
               BrokenBarrierException,
               TimeoutException {
        return dowait(true, unit.toNanos(timeout));
    }

    /**
     * Queries if this barrier is in a broken state.
     *
     * <p>
     * 抛出。
     * 
     *  <p>如果任何线程在等待期间{@linkplain线程#中断中断},则所有其他等待的线程将抛出{@link BrokenBarrierException}并且障碍被置于中断状态。
     * 
     *  <p>如果当前线程是到达的最后一个线程,并且在构造函数中提供了非空屏障操作,则当前线程运行操作,然后允许其他线程继续。如果在屏障动作期间发生异常,则该异常将在当前线程中传播,并且屏障被置于断开状态。
     * 
     * 
     * @return {@code true} if one or more parties broke out of this
     *         barrier due to interruption or timeout since
     *         construction or the last reset, or a barrier action
     *         failed due to an exception; {@code false} otherwise.
     */
    public boolean isBroken() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return generation.broken;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Resets the barrier to its initial state.  If any parties are
     * currently waiting at the barrier, they will return with a
     * {@link BrokenBarrierException}. Note that resets <em>after</em>
     * a breakage has occurred for other reasons can be complicated to
     * carry out; threads need to re-synchronize in some other way,
     * and choose one to perform the reset.  It may be preferable to
     * instead create a new barrier for subsequent use.
     * <p>
     *  查询此障碍是否处于损坏状态。
     * 
     */
    public void reset() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            breakBarrier();   // break the current generation
            nextGeneration(); // start a new generation
        } finally {
            lock.unlock();
        }
    }

    /**
     * Returns the number of parties currently waiting at the barrier.
     * This method is primarily useful for debugging and assertions.
     *
     * <p>
     * 将屏障重置为其初始状态。如果任何当事人目前正在等待障碍,他们将返回一个{@link BrokenBarrierException}。
     * 请注意,由于其他原因发生的损坏之后重置<em>可能会很复杂;线程需要以某种其他方式重新同步,并选择一个来执行重置。相反,可以优选地为随后的使用创建新的屏障。
     * 
     * 
     * @return the number of parties currently blocked in {@link #await}
     */
    public int getNumberWaiting() {
        final ReentrantLock lock = this.lock;
        lock.lock();
        try {
            return parties - count;
        } finally {
            lock.unlock();
        }
    }
}
