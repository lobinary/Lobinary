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
package java.util.stream;

import java.util.Spliterator;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Abstract class for fork-join tasks used to implement short-circuiting
 * stream ops, which can produce a result without processing all elements of the
 * stream.
 *
 * <p>
 *  用于fork-join任务的抽象类用于实现短路流操作,这可以产生结果而不处理流的所有元素。
 * 
 * 
 * @param <P_IN> type of input elements to the pipeline
 * @param <P_OUT> type of output elements from the pipeline
 * @param <R> type of intermediate result, may be different from operation
 *        result type
 * @param <K> type of child and sibling tasks
 * @since 1.8
 */
@SuppressWarnings("serial")
abstract class AbstractShortCircuitTask<P_IN, P_OUT, R,
                                        K extends AbstractShortCircuitTask<P_IN, P_OUT, R, K>>
        extends AbstractTask<P_IN, P_OUT, R, K> {
    /**
     * The result for this computation; this is shared among all tasks and set
     * exactly once
     * <p>
     *  这个计算的结果;这是所有任务共享,并设置一次
     * 
     */
    protected final AtomicReference<R> sharedResult;

    /**
     * Indicates whether this task has been canceled.  Tasks may cancel other
     * tasks in the computation under various conditions, such as in a
     * find-first operation, a task that finds a value will cancel all tasks
     * that are later in the encounter order.
     * <p>
     *  指示此任务是否已取消。任务可以在各种条件下在计算中取消其他任务,例如在查找优先操作中,找到值的任务将取消稍后在遇到顺序中的所有任务。
     * 
     */
    protected volatile boolean canceled;

    /**
     * Constructor for root tasks.
     *
     * <p>
     *  根任务的构造函数。
     * 
     * 
     * @param helper the {@code PipelineHelper} describing the stream pipeline
     *               up to this operation
     * @param spliterator the {@code Spliterator} describing the source for this
     *                    pipeline
     */
    protected AbstractShortCircuitTask(PipelineHelper<P_OUT> helper,
                                       Spliterator<P_IN> spliterator) {
        super(helper, spliterator);
        sharedResult = new AtomicReference<>(null);
    }

    /**
     * Constructor for non-root nodes.
     *
     * <p>
     *  非根节点的构造方法。
     * 
     * 
     * @param parent parent task in the computation tree
     * @param spliterator the {@code Spliterator} for the portion of the
     *                    computation tree described by this task
     */
    protected AbstractShortCircuitTask(K parent,
                                       Spliterator<P_IN> spliterator) {
        super(parent, spliterator);
        sharedResult = parent.sharedResult;
    }

    /**
     * Returns the value indicating the computation completed with no task
     * finding a short-circuitable result.  For example, for a "find" operation,
     * this might be null or an empty {@code Optional}.
     *
     * <p>
     *  返回指示计算完成的值,没有任务找到可短路的结果。例如,对于"find"操作,这可能为空或为空{@code Optional}。
     * 
     * 
     * @return the result to return when no task finds a result
     */
    protected abstract R getEmptyResult();

    /**
     * Overrides AbstractTask version to include checks for early
     * exits while splitting or computing.
     * <p>
     *  覆盖AbstractTask版本以包括早期退出检查,同时拆分或计算。
     * 
     */
    @Override
    public void compute() {
        Spliterator<P_IN> rs = spliterator, ls;
        long sizeEstimate = rs.estimateSize();
        long sizeThreshold = getTargetSize(sizeEstimate);
        boolean forkRight = false;
        @SuppressWarnings("unchecked") K task = (K) this;
        AtomicReference<R> sr = sharedResult;
        R result;
        while ((result = sr.get()) == null) {
            if (task.taskCanceled()) {
                result = task.getEmptyResult();
                break;
            }
            if (sizeEstimate <= sizeThreshold || (ls = rs.trySplit()) == null) {
                result = task.doLeaf();
                break;
            }
            K leftChild, rightChild, taskToFork;
            task.leftChild  = leftChild = task.makeChild(ls);
            task.rightChild = rightChild = task.makeChild(rs);
            task.setPendingCount(1);
            if (forkRight) {
                forkRight = false;
                rs = ls;
                task = leftChild;
                taskToFork = rightChild;
            }
            else {
                forkRight = true;
                task = rightChild;
                taskToFork = leftChild;
            }
            taskToFork.fork();
            sizeEstimate = rs.estimateSize();
        }
        task.setLocalResult(result);
        task.tryComplete();
    }


    /**
     * Declares that a globally valid result has been found.  If another task has
     * not already found the answer, the result is installed in
     * {@code sharedResult}.  The {@code compute()} method will check
     * {@code sharedResult} before proceeding with computation, so this causes
     * the computation to terminate early.
     *
     * <p>
     *  声明已找到全局有效的结果。如果另一个任务尚未找到答案,则结果将安装在{@code sharedResult}中。
     *  {@code compute()}方法将在继续计算之前检查{@code sharedResult},因此这会导致计算提前终止。
     * 
     * 
     * @param result the result found
     */
    protected void shortCircuit(R result) {
        if (result != null)
            sharedResult.compareAndSet(null, result);
    }

    /**
     * Sets a local result for this task.  If this task is the root, set the
     * shared result instead (if not already set).
     *
     * <p>
     *  设置此任务的本地结果。如果此任务是根,请改为设置共享结果(如果尚未设置)。
     * 
     * 
     * @param localResult The result to set for this task
     */
    @Override
    protected void setLocalResult(R localResult) {
        if (isRoot()) {
            if (localResult != null)
                sharedResult.compareAndSet(null, localResult);
        }
        else
            super.setLocalResult(localResult);
    }

    /**
     * Retrieves the local result for this task
     * <p>
     *  检索此任务的本地结果
     * 
     */
    @Override
    public R getRawResult() {
        return getLocalResult();
    }

    /**
     * Retrieves the local result for this task.  If this task is the root,
     * retrieves the shared result instead.
     * <p>
     * 检索此任务的本地结果。如果此任务是根,请检索共享结果。
     * 
     */
    @Override
    public R getLocalResult() {
        if (isRoot()) {
            R answer = sharedResult.get();
            return (answer == null) ? getEmptyResult() : answer;
        }
        else
            return super.getLocalResult();
    }

    /**
     * Mark this task as canceled
     * <p>
     *  将此任务标记为已取消
     * 
     */
    protected void cancel() {
        canceled = true;
    }

    /**
     * Queries whether this task is canceled.  A task is considered canceled if
     * it or any of its parents have been canceled.
     *
     * <p>
     *  查询此任务是否已取消。如果任务或其任何父项已取消,则认为该任务被取消。
     * 
     * 
     * @return {@code true} if this task or any parent is canceled.
     */
    protected boolean taskCanceled() {
        boolean cancel = canceled;
        if (!cancel) {
            for (K parent = getParent(); !cancel && parent != null; parent = parent.getParent())
                cancel = parent.canceled;
        }

        return cancel;
    }

    /**
     * Cancels all tasks which succeed this one in the encounter order.  This
     * includes canceling all the current task's right sibling, as well as the
     * later right siblings of all its parents.
     * <p>
     *  取消在遇到顺序中成功此任务的所有任务。这包括取消所有当前任务的右兄弟,以及所有父亲的后来的兄弟姐妹。
     */
    protected void cancelLaterNodes() {
        // Go up the tree, cancel right siblings of this node and all parents
        for (@SuppressWarnings("unchecked") K parent = getParent(), node = (K) this;
             parent != null;
             node = parent, parent = parent.getParent()) {
            // If node is a left child of parent, then has a right sibling
            if (parent.leftChild == node) {
                K rightSibling = parent.rightChild;
                if (!rightSibling.canceled)
                    rightSibling.cancel();
            }
        }
    }
}
