/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.text;

import java.util.*;
import java.util.List;
import java.awt.*;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;

/**
 * A box that does layout asynchronously.  This
 * is useful to keep the GUI event thread moving by
 * not doing any layout on it.  The layout is done
 * on a granularity of operations on the child views.
 * After each child view is accessed for some part
 * of layout (a potentially time consuming operation)
 * the remaining tasks can be abandoned or a new higher
 * priority task (i.e. to service a synchronous request
 * or a visible area) can be taken on.
 * <p>
 * While the child view is being accessed
 * a read lock is acquired on the associated document
 * so that the model is stable while being accessed.
 *
 * <p>
 *  一个异步布局的框。这对于通过不对其执行任何布局来保持GUI事件线程移动是有用的。布局是在子视图上的粒度操作上完成的。
 * 在为布局的某一部分访问每个子视图之后(可能耗时的操作),可以放弃剩余的任务或者可以采用新的较高优先级任务(即,服务同步请求或可见区域)。
 * <p>
 *  当正在访问子视图时,在相关联的文档上获取读取锁,使得在被访问时模型是稳定的。
 * 
 * 
 * @author  Timothy Prinzing
 * @since   1.3
 */
public class AsyncBoxView extends View {

    /**
     * Construct a box view that does asynchronous layout.
     *
     * <p>
     *  构造一个执行异步布局的框视图。
     * 
     * 
     * @param elem the element of the model to represent
     * @param axis the axis to tile along.  This can be
     *  either X_AXIS or Y_AXIS.
     */
    public AsyncBoxView(Element elem, int axis) {
        super(elem);
        stats = new ArrayList<ChildState>();
        this.axis = axis;
        locator = new ChildLocator();
        flushTask = new FlushTask();
        minorSpan = Short.MAX_VALUE;
        estimatedMajorSpan = false;
    }

    /**
     * Fetch the major axis (the axis the children
     * are tiled along).  This will have a value of
     * either X_AXIS or Y_AXIS.
     * <p>
     *  获取主轴(孩子们平铺的轴)。这将具有值X_AXIS或Y_AXIS。
     * 
     */
    public int getMajorAxis() {
        return axis;
    }

    /**
     * Fetch the minor axis (the axis orthogonal
     * to the tiled axis).  This will have a value of
     * either X_AXIS or Y_AXIS.
     * <p>
     *  获取短轴(与平铺轴正交的轴)。这将具有值X_AXIS或Y_AXIS。
     * 
     */
    public int getMinorAxis() {
        return (axis == X_AXIS) ? Y_AXIS : X_AXIS;
    }

    /**
     * Get the top part of the margin around the view.
     * <p>
     *  获取视图周围边缘的顶部。
     * 
     */
    public float getTopInset() {
        return topInset;
    }

    /**
     * Set the top part of the margin around the view.
     *
     * <p>
     *  在视图周围设置边距的顶部。
     * 
     * 
     * @param i the value of the inset
     */
    public void setTopInset(float i) {
        topInset = i;
    }

    /**
     * Get the bottom part of the margin around the view.
     * <p>
     *  获取视图周围的边距的底部。
     * 
     */
    public float getBottomInset() {
        return bottomInset;
    }

    /**
     * Set the bottom part of the margin around the view.
     *
     * <p>
     *  在视图周围设置边距的底部。
     * 
     * 
     * @param i the value of the inset
     */
    public void setBottomInset(float i) {
        bottomInset = i;
    }

    /**
     * Get the left part of the margin around the view.
     * <p>
     *  获取视图周围边缘的左边部分。
     * 
     */
    public float getLeftInset() {
        return leftInset;
    }

    /**
     * Set the left part of the margin around the view.
     *
     * <p>
     *  在视图周围设置边距的左边部分。
     * 
     * 
     * @param i the value of the inset
     */
    public void setLeftInset(float i) {
        leftInset = i;
    }

    /**
     * Get the right part of the margin around the view.
     * <p>
     *  获取视图周围边缘的右侧部分。
     * 
     */
    public float getRightInset() {
        return rightInset;
    }

    /**
     * Set the right part of the margin around the view.
     *
     * <p>
     *  在视图周围设置边距的右边部分。
     * 
     * 
     * @param i the value of the inset
     */
    public void setRightInset(float i) {
        rightInset = i;
    }

    /**
     * Fetch the span along an axis that is taken up by the insets.
     *
     * <p>
     *  沿插入所占用的轴获取跨度。
     * 
     * 
     * @param axis the axis to determine the total insets along,
     *  either X_AXIS or Y_AXIS.
     * @since 1.4
     */
    protected float getInsetSpan(int axis) {
        float margin = (axis == X_AXIS) ?
            getLeftInset() + getRightInset() : getTopInset() + getBottomInset();
        return margin;
    }

    /**
     * Set the estimatedMajorSpan property that determines if the
     * major span should be treated as being estimated.  If this
     * property is true, the value of setSize along the major axis
     * will change the requirements along the major axis and incremental
     * changes will be ignored until all of the children have been updated
     * (which will cause the property to automatically be set to false).
     * If the property is false the value of the majorSpan will be
     * considered to be accurate and incremental changes will be
     * added into the total as they are calculated.
     *
     * <p>
     * 设置estimatedMajorSpan属性,以确定是否应将主要跨度视为已估计。
     * 如果此属性为true,则setSize沿主轴的值将更改沿主轴的要求,增量更改将被忽略,直到所有子项已更新(这将导致属性自动设置为false)。
     * 如果属性为false,则majorSpan的值将被视为准确的,并且增量更改将在计算时添加到总计中。
     * 
     * 
     * @since 1.4
     */
    protected void setEstimatedMajorSpan(boolean isEstimated) {
        estimatedMajorSpan = isEstimated;
    }

    /**
     * Is the major span currently estimated?
     *
     * <p>
     *  目前估计的主要跨距是多少?
     * 
     * 
     * @since 1.4
     */
    protected boolean getEstimatedMajorSpan() {
        return estimatedMajorSpan;
    }

    /**
     * Fetch the object representing the layout state of
     * of the child at the given index.
     *
     * <p>
     *  在给定的索引处获取表示子代的布局状态的对象。
     * 
     * 
     * @param index the child index.  This should be a
     *   value &gt;= 0 and &lt; getViewCount().
     */
    protected ChildState getChildState(int index) {
        synchronized(stats) {
            if ((index >= 0) && (index < stats.size())) {
                return stats.get(index);
            }
            return null;
        }
    }

    /**
     * Fetch the queue to use for layout.
     * <p>
     *  获取要用于布局的队列。
     * 
     */
    protected LayoutQueue getLayoutQueue() {
        return LayoutQueue.getDefaultQueue();
    }

    /**
     * New ChildState records are created through
     * this method to allow subclasses the extend
     * the ChildState records to do/hold more
     * <p>
     *  通过此方法创建新的ChildState记录,以允许子类ChildState记录执行/保持更多
     * 
     */
    protected ChildState createChildState(View v) {
        return new ChildState(v);
    }

    /**
     * Requirements changed along the major axis.
     * This is called by the thread doing layout for
     * the given ChildState object when it has completed
     * fetching the child views new preferences.
     * Typically this would be the layout thread, but
     * might be the event thread if it is trying to update
     * something immediately (such as to perform a
     * model/view translation).
     * <p>
     * This is implemented to mark the major axis as having
     * changed so that a future check to see if the requirements
     * need to be published to the parent view will consider
     * the major axis.  If the span along the major axis is
     * not estimated, it is updated by the given delta to reflect
     * the incremental change.  The delta is ignored if the
     * major span is estimated.
     * <p>
     *  需求沿主轴变化。当完成获取子视图的新偏好时,线程为给定的ChildState对象执行布局调用。通常这将是布局线程,但如果它试图立即更新某个事件线程(如执行模型/视图转换)可能是事件线程。
     * <p>
     * 这被实现为将主轴标记为已经改变,使得将来检查以查看需求是否需要被发布到父视图将考虑主轴。如果未估计沿着长轴的跨度,则其由给定的增量更新以反映增量变化。如果估计主跨度,则忽略增量。
     * 
     */
    protected synchronized void majorRequirementChange(ChildState cs, float delta) {
        if (estimatedMajorSpan == false) {
            majorSpan += delta;
        }
        majorChanged = true;
    }

    /**
     * Requirements changed along the minor axis.
     * This is called by the thread doing layout for
     * the given ChildState object when it has completed
     * fetching the child views new preferences.
     * Typically this would be the layout thread, but
     * might be the GUI thread if it is trying to update
     * something immediately (such as to perform a
     * model/view translation).
     * <p>
     *  需求沿着短轴改变。当完成获取子视图的新偏好时,线程为给定的ChildState对象执行布局调用。通常,这将是布局线程,但如果它试图更新某些东西(如执行模型/视图转换)可能是GUI线程。
     * 
     */
    protected synchronized void minorRequirementChange(ChildState cs) {
        minorChanged = true;
    }

    /**
     * Publish the changes in preferences upward to the parent
     * view.  This is normally called by the layout thread.
     * <p>
     *  将首选项中的更改向上发布到父视图。这通常由布局线程调用。
     * 
     */
    protected void flushRequirementChanges() {
        AbstractDocument doc = (AbstractDocument) getDocument();
        try {
            doc.readLock();

            View parent = null;
            boolean horizontal = false;
            boolean vertical = false;

            synchronized(this) {
                // perform tasks that iterate over the children while
                // preventing the collection from changing.
                synchronized(stats) {
                    int n = getViewCount();
                    if ((n > 0) && (minorChanged || estimatedMajorSpan)) {
                        LayoutQueue q = getLayoutQueue();
                        ChildState min = getChildState(0);
                        ChildState pref = getChildState(0);
                        float span = 0f;
                        for (int i = 1; i < n; i++) {
                            ChildState cs = getChildState(i);
                            if (minorChanged) {
                                if (cs.min > min.min) {
                                    min = cs;
                                }
                                if (cs.pref > pref.pref) {
                                    pref = cs;
                                }
                            }
                            if (estimatedMajorSpan) {
                                span += cs.getMajorSpan();
                            }
                        }

                        if (minorChanged) {
                            minRequest = min;
                            prefRequest = pref;
                        }
                        if (estimatedMajorSpan) {
                            majorSpan = span;
                            estimatedMajorSpan = false;
                            majorChanged = true;
                        }
                    }
                }

                // message preferenceChanged
                if (majorChanged || minorChanged) {
                    parent = getParent();
                    if (parent != null) {
                        if (axis == X_AXIS) {
                            horizontal = majorChanged;
                            vertical = minorChanged;
                        } else {
                            vertical = majorChanged;
                            horizontal = minorChanged;
                        }
                    }
                    majorChanged = false;
                    minorChanged = false;
                }
            }

            // propagate a preferenceChanged, using the
            // layout thread.
            if (parent != null) {
                parent.preferenceChanged(this, horizontal, vertical);

                // probably want to change this to be more exact.
                Component c = getContainer();
                if (c != null) {
                    c.repaint();
                }
            }
        } finally {
            doc.readUnlock();
        }
    }

    /**
     * Calls the superclass to update the child views, and
     * updates the status records for the children.  This
     * is expected to be called while a write lock is held
     * on the model so that interaction with the layout
     * thread will not happen (i.e. the layout thread
     * acquires a read lock before doing anything).
     *
     * <p>
     *  调用超类更新子视图,并更新子级的状态记录。这可以在模型上保持写锁定时被调用,从而不会发生与布局线程的交互(即布局线程在执行任何操作之前获取读锁定)。
     * 
     * 
     * @param offset the starting offset into the child views &gt;= 0
     * @param length the number of existing views to replace &gt;= 0
     * @param views the child views to insert
     */
    public void replace(int offset, int length, View[] views) {
        synchronized(stats) {
            // remove the replaced state records
            for (int i = 0; i < length; i++) {
                ChildState cs = stats.remove(offset);
                float csSpan = cs.getMajorSpan();

                cs.getChildView().setParent(null);
                if (csSpan != 0) {
                    majorRequirementChange(cs, -csSpan);
                }
            }

            // insert the state records for the new children
            LayoutQueue q = getLayoutQueue();
            if (views != null) {
                for (int i = 0; i < views.length; i++) {
                    ChildState s = createChildState(views[i]);
                    stats.add(offset + i, s);
                    q.addTask(s);
                }
            }

            // notify that the size changed
            q.addTask(flushTask);
        }
    }

    /**
     * Loads all of the children to initialize the view.
     * This is called by the {@link #setParent setParent}
     * method.  Subclasses can reimplement this to initialize
     * their child views in a different manner.  The default
     * implementation creates a child view for each
     * child element.
     * <p>
     * Normally a write-lock is held on the Document while
     * the children are being changed, which keeps the rendering
     * and layout threads safe.  The exception to this is when
     * the view is initialized to represent an existing element
     * (via this method), so it is synchronized to exclude
     * preferenceChanged while we are initializing.
     *
     * <p>
     *  加载所有子项以初始化视图。这由{@link #setParent setParent}方法调用。子类可以重新实现这一点,以不同的方式初始化它们的子视图。默认实现为每个子元素创建一个子视图。
     * <p>
     * 通常在文档被改变时保持写锁定,这保持了渲染和布局线程的安全。这种情况的例外是当视图被初始化以表示现有元素(通过此方法)时,因此在我们初始化时将其同步以排除preferencesChanged。
     * 
     * 
     * @param f the view factory
     * @see #setParent
     */
    protected void loadChildren(ViewFactory f) {
        Element e = getElement();
        int n = e.getElementCount();
        if (n > 0) {
            View[] added = new View[n];
            for (int i = 0; i < n; i++) {
                added[i] = f.create(e.getElement(i));
            }
            replace(0, 0, added);
        }
    }

    /**
     * Fetches the child view index representing the given position in
     * the model.  This is implemented to fetch the view in the case
     * where there is a child view for each child element.
     *
     * <p>
     *  获取表示模型中给定位置的子视图索引。这被实现为在每个子元素存在子视图的情况下获取视图。
     * 
     * 
     * @param pos the position &gt;= 0
     * @return  index of the view representing the given position, or
     *   -1 if no view represents that position
     */
    protected synchronized int getViewIndexAtPosition(int pos, Position.Bias b) {
        boolean isBackward = (b == Position.Bias.Backward);
        pos = (isBackward) ? Math.max(0, pos - 1) : pos;
        Element elem = getElement();
        return elem.getElementIndex(pos);
    }

    /**
     * Update the layout in response to receiving notification of
     * change from the model.  This is implemented to note the
     * change on the ChildLocator so that offsets of the children
     * will be correctly computed.
     *
     * <p>
     *  响应从模型接收到更改的通知,更新布局。这是为了注意ChildLocator上的更改,以便正确计算子项的偏移量。
     * 
     * 
     * @param ec changes to the element this view is responsible
     *  for (may be null if there were no changes).
     * @param e the change information from the associated document
     * @param a the current allocation of the view
     * @see #insertUpdate
     * @see #removeUpdate
     * @see #changedUpdate
     */
    protected void updateLayout(DocumentEvent.ElementChange ec,
                                    DocumentEvent e, Shape a) {
        if (ec != null) {
            // the newly inserted children don't have a valid
            // offset so the child locator needs to be messaged
            // that the child prior to the new children has
            // changed size.
            int index = Math.max(ec.getIndex() - 1, 0);
            ChildState cs = getChildState(index);
            locator.childChanged(cs);
        }
    }

    // --- View methods ------------------------------------

    /**
     * Sets the parent of the view.
     * This is reimplemented to provide the superclass
     * behavior as well as calling the <code>loadChildren</code>
     * method if this view does not already have children.
     * The children should not be loaded in the
     * constructor because the act of setting the parent
     * may cause them to try to search up the hierarchy
     * (to get the hosting Container for example).
     * If this view has children (the view is being moved
     * from one place in the view hierarchy to another),
     * the <code>loadChildren</code> method will not be called.
     *
     * <p>
     *  设置视图的父级。这被重新实现以提供超类行为以及调用<code> loadChildren </code>方法(如果此视图尚未具有子代)。
     * 不应该在构造函数中加载子代,因为设置父代的行为可能导致它们尝试在层次结构中搜索(例如获取托管容器)。
     * 如果此视图具有子代(视图正从视图层次结构中的一个位置移动到另一个位置),则不会调用<code> loadChildren </code>方法。
     * 
     * 
     * @param parent the parent of the view, null if none
     */
    public void setParent(View parent) {
        super.setParent(parent);
        if ((parent != null) && (getViewCount() == 0)) {
            ViewFactory f = getViewFactory();
            loadChildren(f);
        }
    }

    /**
     * Child views can call this on the parent to indicate that
     * the preference has changed and should be reconsidered
     * for layout.  This is reimplemented to queue new work
     * on the layout thread.  This method gets messaged from
     * multiple threads via the children.
     *
     * <p>
     *  子视图可以在父对象上调用此选项来指示首选项已更改,并应重新考虑布局。这被重新实现以在布局线程上排队新的工作。这个方法通过子进程从多个线程消息。
     * 
     * 
     * @param child the child view
     * @param width true if the width preference has changed
     * @param height true if the height preference has changed
     * @see javax.swing.JComponent#revalidate
     */
    public synchronized void preferenceChanged(View child, boolean width, boolean height) {
        if (child == null) {
            getParent().preferenceChanged(this, width, height);
        } else {
            if (changing != null) {
                View cv = changing.getChildView();
                if (cv == child) {
                    // size was being changed on the child, no need to
                    // queue work for it.
                    changing.preferenceChanged(width, height);
                    return;
                }
            }
            int index = getViewIndex(child.getStartOffset(),
                                     Position.Bias.Forward);
            ChildState cs = getChildState(index);
            cs.preferenceChanged(width, height);
            LayoutQueue q = getLayoutQueue();
            q.addTask(cs);
            q.addTask(flushTask);
        }
    }

    /**
     * Sets the size of the view.  This should cause
     * layout of the view if the view caches any layout
     * information.
     * <p>
     * Since the major axis is updated asynchronously and should be
     * the sum of the tiled children the call is ignored for the major
     * axis.  Since the minor axis is flexible, work is queued to resize
     * the children if the minor span changes.
     *
     * <p>
     * 设置视图的大小。如果视图缓存任何布局信息,这应该导致视图的布局。
     * <p>
     *  由于主轴是异步更新的,并且应该是平铺子代的和,对于主轴忽略调用。由于短轴是灵活的,所以如果次跨度改变,工作将排队等待子节点。
     * 
     * 
     * @param width the width &gt;= 0
     * @param height the height &gt;= 0
     */
    public void setSize(float width, float height) {
        setSpanOnAxis(X_AXIS, width);
        setSpanOnAxis(Y_AXIS, height);
    }

    /**
     * Retrieves the size of the view along an axis.
     *
     * <p>
     *  检索沿轴的视图大小。
     * 
     * 
     * @param axis may be either <code>View.X_AXIS</code> or
     *          <code>View.Y_AXIS</code>
     * @return the current span of the view along the given axis, >= 0
     */
    float getSpanOnAxis(int axis) {
        if (axis == getMajorAxis()) {
            return majorSpan;
        }
        return minorSpan;
    }

    /**
     * Sets the size of the view along an axis.  Since the major
     * axis is updated asynchronously and should be the sum of the
     * tiled children the call is ignored for the major axis.  Since
     * the minor axis is flexible, work is queued to resize the
     * children if the minor span changes.
     *
     * <p>
     *  设置沿轴的视图大小。由于主轴是异步更新的,并且应该是平铺子代的和,对于主轴忽略调用。由于短轴是灵活的,所以如果次跨度改变,工作将排队等待子节点。
     * 
     * 
     * @param axis may be either <code>View.X_AXIS</code> or
     *          <code>View.Y_AXIS</code>
     * @param span the span to layout to >= 0
     */
    void setSpanOnAxis(int axis, float span) {
        float margin = getInsetSpan(axis);
        if (axis == getMinorAxis()) {
            float targetSpan = span - margin;
            if (targetSpan != minorSpan) {
                minorSpan = targetSpan;

                // mark all of the ChildState instances as needing to
                // resize the child, and queue up work to fix them.
                int n = getViewCount();
                if (n != 0) {
                    LayoutQueue q = getLayoutQueue();
                    for (int i = 0; i < n; i++) {
                        ChildState cs = getChildState(i);
                        cs.childSizeValid = false;
                        q.addTask(cs);
                    }
                    q.addTask(flushTask);
                }
            }
        } else {
            // along the major axis the value is ignored
            // unless the estimatedMajorSpan property is
            // true.
            if (estimatedMajorSpan) {
                majorSpan = span - margin;
            }
        }
    }

    /**
     * Render the view using the given allocation and
     * rendering surface.
     * <p>
     * This is implemented to determine whether or not the
     * desired region to be rendered (i.e. the unclipped
     * area) is up to date or not.  If up-to-date the children
     * are rendered.  If not up-to-date, a task to build
     * the desired area is placed on the layout queue as
     * a high priority task.  This keeps by event thread
     * moving by rendering if ready, and postponing until
     * a later time if not ready (since paint requests
     * can be rescheduled).
     *
     * <p>
     *  使用给定的分配和呈现表面渲染视图。
     * <p>
     *  这被实现为确定要呈现的期望区域(即未剪裁区域)是否是最新的。如果最新的孩子被呈现。如果不是最新的,则将用于构建期望区域的任务作为高优先级任务放置在布局队列上。
     * 这保持事件线程移动通过渲染如果准备好,并推迟到以后的时间,如果没有准备好(因为绘画请求可以重新安排)。
     * 
     * 
     * @param g the rendering surface to use
     * @param alloc the allocated region to render into
     * @see View#paint
     */
    public void paint(Graphics g, Shape alloc) {
        synchronized (locator) {
            locator.setAllocation(alloc);
            locator.paintChildren(g);
        }
    }

    /**
     * Determines the preferred span for this view along an
     * axis.
     *
     * <p>
     *  确定沿着轴的此视图的首选跨度。
     * 
     * 
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @return   the span the view would like to be rendered into &gt;= 0.
     *           Typically the view is told to render into the span
     *           that is returned, although there is no guarantee.
     *           The parent may choose to resize or break the view.
     * @exception IllegalArgumentException for an invalid axis type
     */
    public float getPreferredSpan(int axis) {
        float margin = getInsetSpan(axis);
        if (axis == this.axis) {
            return majorSpan + margin;
        }
        if (prefRequest != null) {
            View child = prefRequest.getChildView();
            return child.getPreferredSpan(axis) + margin;
        }

        // nothing is known about the children yet
        return margin + 30;
    }

    /**
     * Determines the minimum span for this view along an
     * axis.
     *
     * <p>
     *  确定沿轴的此视图的最小跨度。
     * 
     * 
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @return  the span the view would like to be rendered into &gt;= 0.
     *           Typically the view is told to render into the span
     *           that is returned, although there is no guarantee.
     *           The parent may choose to resize or break the view.
     * @exception IllegalArgumentException for an invalid axis type
     */
    public float getMinimumSpan(int axis) {
        if (axis == this.axis) {
            return getPreferredSpan(axis);
        }
        if (minRequest != null) {
            View child = minRequest.getChildView();
            return child.getMinimumSpan(axis);
        }

        // nothing is known about the children yet
        if (axis == X_AXIS) {
            return getLeftInset() + getRightInset() + 5;
        } else {
            return getTopInset() + getBottomInset() + 5;
        }
    }

    /**
     * Determines the maximum span for this view along an
     * axis.
     *
     * <p>
     *  确定沿轴的此视图的最大跨度。
     * 
     * 
     * @param axis may be either View.X_AXIS or View.Y_AXIS
     * @return   the span the view would like to be rendered into &gt;= 0.
     *           Typically the view is told to render into the span
     *           that is returned, although there is no guarantee.
     *           The parent may choose to resize or break the view.
     * @exception IllegalArgumentException for an invalid axis type
     */
    public float getMaximumSpan(int axis) {
        if (axis == this.axis) {
            return getPreferredSpan(axis);
        }
        return Integer.MAX_VALUE;
    }


    /**
     * Returns the number of views in this view.  Since
     * the default is to not be a composite view this
     * returns 0.
     *
     * <p>
     *  返回此视图中的视图数。因为默认是不是一个复合视图,这将返回0。
     * 
     * 
     * @return the number of views &gt;= 0
     * @see View#getViewCount
     */
    public int getViewCount() {
        synchronized(stats) {
            return stats.size();
        }
    }

    /**
     * Gets the nth child view.  Since there are no
     * children by default, this returns null.
     *
     * <p>
     * 获取第n个子视图。由于默认情况下没有子对象,因此返回null。
     * 
     * 
     * @param n the number of the view to get, &gt;= 0 &amp;&amp; &lt; getViewCount()
     * @return the view
     */
    public View getView(int n) {
        ChildState cs = getChildState(n);
        if (cs != null) {
            return cs.getChildView();
        }
        return null;
    }

    /**
     * Fetches the allocation for the given child view.
     * This enables finding out where various views
     * are located, without assuming the views store
     * their location.  This returns null since the
     * default is to not have any child views.
     *
     * <p>
     *  获取给定子视图的分配。这使得能够找到各种视图位于何处,而不假定视图存储它们的位置。这返回null,因为默认值是没有任何子视图。
     * 
     * 
     * @param index the index of the child, &gt;= 0 &amp;&amp; &lt; getViewCount()
     * @param a  the allocation to this view.
     * @return the allocation to the child
     */
    public Shape getChildAllocation(int index, Shape a) {
        Shape ca = locator.getChildAllocation(index, a);
        return ca;
    }

    /**
     * Returns the child view index representing the given position in
     * the model.  By default a view has no children so this is implemented
     * to return -1 to indicate there is no valid child index for any
     * position.
     *
     * <p>
     *  返回表示模型中给定位置的子视图索引。默认情况下,一个视图没有子对象,因此实现返回-1表示没有任何有效的子索引。
     * 
     * 
     * @param pos the position &gt;= 0
     * @return  index of the view representing the given position, or
     *   -1 if no view represents that position
     * @since 1.3
     */
    public int getViewIndex(int pos, Position.Bias b) {
        return getViewIndexAtPosition(pos, b);
    }

    /**
     * Provides a mapping from the document model coordinate space
     * to the coordinate space of the view mapped to it.
     *
     * <p>
     *  提供从文档模型坐标空间到映射到其的视图的坐标空间的映射。
     * 
     * 
     * @param pos the position to convert &gt;= 0
     * @param a the allocated region to render into
     * @param b the bias toward the previous character or the
     *  next character represented by the offset, in case the
     *  position is a boundary of two views.
     * @return the bounding box of the given position is returned
     * @exception BadLocationException  if the given position does
     *   not represent a valid location in the associated document
     * @exception IllegalArgumentException for an invalid bias argument
     * @see View#viewToModel
     */
    public Shape modelToView(int pos, Shape a, Position.Bias b) throws BadLocationException {
        int index = getViewIndex(pos, b);
        Shape ca = locator.getChildAllocation(index, a);

        // forward to the child view, and make sure we don't
        // interact with the layout thread by synchronizing
        // on the child state.
        ChildState cs = getChildState(index);
        synchronized (cs) {
            View cv = cs.getChildView();
            Shape v = cv.modelToView(pos, ca, b);
            return v;
        }
    }

    /**
     * Provides a mapping from the view coordinate space to the logical
     * coordinate space of the model.  The biasReturn argument will be
     * filled in to indicate that the point given is closer to the next
     * character in the model or the previous character in the model.
     * <p>
     * This is expected to be called by the GUI thread, holding a
     * read-lock on the associated model.  It is implemented to
     * locate the child view and determine it's allocation with a
     * lock on the ChildLocator object, and to call viewToModel
     * on the child view with a lock on the ChildState object
     * to avoid interaction with the layout thread.
     *
     * <p>
     *  提供从视图坐标空间到模型的逻辑坐标空间的映射。 biasReturn参数将被填充以表明给定的点更接近模型中的下一个字符或模型中的上一个字符。
     * <p>
     *  这期望由GUI线程调用,在相关联的模型上保持读锁定。
     * 它被实现为定位子视图,并使用ChildLocator对象上的锁确定其分配,并使用ChildState对象上的锁来对子视图调用viewToModel,以避免与布局线程交互。
     * 
     * 
     * @param x the X coordinate &gt;= 0
     * @param y the Y coordinate &gt;= 0
     * @param a the allocated region to render into
     * @return the location within the model that best represents the
     *  given point in the view &gt;= 0.  The biasReturn argument will be
     * filled in to indicate that the point given is closer to the next
     * character in the model or the previous character in the model.
     */
    public int viewToModel(float x, float y, Shape a, Position.Bias[] biasReturn) {
        int pos;    // return position
        int index;  // child index to forward to
        Shape ca;   // child allocation

        // locate the child view and it's allocation so that
        // we can forward to it.  Make sure the layout thread
        // doesn't change anything by trying to flush changes
        // to the parent while the GUI thread is trying to
        // find the child and it's allocation.
        synchronized (locator) {
            index = locator.getViewIndexAtPoint(x, y, a);
            ca = locator.getChildAllocation(index, a);
        }

        // forward to the child view, and make sure we don't
        // interact with the layout thread by synchronizing
        // on the child state.
        ChildState cs = getChildState(index);
        synchronized (cs) {
            View v = cs.getChildView();
            pos = v.viewToModel(x, y, ca, biasReturn);
        }
        return pos;
    }

    /**
     * Provides a way to determine the next visually represented model
     * location that one might place a caret.  Some views may not be visible,
     * they might not be in the same order found in the model, or they just
     * might not allow access to some of the locations in the model.
     * This method enables specifying a position to convert
     * within the range of &gt;=0.  If the value is -1, a position
     * will be calculated automatically.  If the value &lt; -1,
     * the {@code BadLocationException} will be thrown.
     *
     * <p>
     * 提供一种方法来确定下一个可视地表示的模型位置,人们可以放置插入符号。某些视图可能不可见,它们可能不是在模型中找到的相同顺序,或者它们可能不允许访问模型中的一些位置。
     * 该方法使得能够指定在> = 0的范围内转换的位置。如果值为-1,将自动计算位置。如果值&lt; -1,将抛出{@code BadLocationException}。
     * 
     * 
     * @param pos the position to convert
     * @param a the allocated region to render into
     * @param direction the direction from the current position that can
     *  be thought of as the arrow keys typically found on a keyboard;
     *  this may be one of the following:
     *  <ul style="list-style-type:none">
     *  <li><code>SwingConstants.WEST</code></li>
     *  <li><code>SwingConstants.EAST</code></li>
     *  <li><code>SwingConstants.NORTH</code></li>
     *  <li><code>SwingConstants.SOUTH</code></li>
     *  </ul>
     * @param biasRet an array contain the bias that was checked
     * @return the location within the model that best represents the next
     *  location visual position
     * @exception BadLocationException the given position is not a valid
     *                                 position within the document
     * @exception IllegalArgumentException if <code>direction</code> is invalid
     */
    public int getNextVisualPositionFrom(int pos, Position.Bias b, Shape a,
                                         int direction,
                                         Position.Bias[] biasRet)
                                                  throws BadLocationException {
        if (pos < -1) {
            throw new BadLocationException("invalid position", pos);
        }
        return Utilities.getNextVisualPositionFrom(
                            this, pos, b, a, direction, biasRet);
    }

    // --- variables -----------------------------------------

    /**
     * The major axis against which the children are
     * tiled.
     * <p>
     *  孩子们平铺的主轴。
     * 
     */
    int axis;

    /**
     * The children and their layout statistics.
     * <p>
     *  孩子们和他们的布局统计。
     * 
     */
    List<ChildState> stats;

    /**
     * Current span along the major axis.  This
     * is also the value returned by getMinimumSize,
     * getPreferredSize, and getMaximumSize along
     * the major axis.
     * <p>
     *  沿主轴的当前跨度。这也是getMinimumSize,getPreferredSize和getMaximumSize沿主轴返回的值。
     * 
     */
    float majorSpan;

    /**
     * Is the span along the major axis estimated?
     * <p>
     *  是沿长轴的跨度估计吗?
     * 
     */
    boolean estimatedMajorSpan;

    /**
     * Current span along the minor axis.  This
     * is what layout was done against (i.e. things
     * are flexible in this direction).
     * <p>
     *  沿短轴的电流跨度。这是对什么布局(即事情在这个方向是灵活的)。
     * 
     */
    float minorSpan;

    /**
     * Object that manages the offsets of the
     * children.  All locking for management of
     * child locations is on this object.
     * <p>
     *  管理孩子的偏移的对象。用于管理子位置的所有锁定都位于此对象上。
     * 
     */
    protected ChildLocator locator;

    float topInset;
    float bottomInset;
    float leftInset;
    float rightInset;

    ChildState minRequest;
    ChildState prefRequest;
    boolean majorChanged;
    boolean minorChanged;
    Runnable flushTask;

    /**
     * Child that is actively changing size.  This often
     * causes a preferenceChanged, so this is a cache to
     * possibly speed up the marking the state.  It also
     * helps flag an opportunity to avoid adding to flush
     * task to the layout queue.
     * <p>
     *  正在积极改变大小的孩子。这通常导致preferenceChanged,所以这是一个缓存,可能加速标记状态。它还有助于标记一个机会,以避免将冲洗任务添加到布局队列。
     * 
     */
    ChildState changing;

    /**
     * A class to manage the effective position of the
     * child views in a localized area while changes are
     * being made around the localized area.  The AsyncBoxView
     * may be continuously changing, but the visible area
     * needs to remain fairly stable until the layout thread
     * decides to publish an update to the parent.
     * <p>
     * 当在局部区域周围进行改变时管理局部区域中的子视图的有效位置的类。 AsyncBoxView可以不断变化,但可见区域需要保持相当稳定,直到布局线程决定向父级发布更新。
     * 
     * 
     * @since 1.3
     */
    public class ChildLocator {

        /**
         * construct a child locator.
         * <p>
         *  构造子定位器。
         * 
         */
        public ChildLocator() {
            lastAlloc = new Rectangle();
            childAlloc = new Rectangle();
        }

        /**
         * Notification that a child changed.  This can effect
         * whether or not new offset calculations are needed.
         * This is called by a ChildState object that has
         * changed it's major span.  This can therefore be
         * called by multiple threads.
         * <p>
         *  儿童更改的通知。这可以影响是否需要新的偏移计算。这由一个已改变其主跨度的ChildState对象调用。因此,这可以由多个线程调用。
         * 
         */
        public synchronized void childChanged(ChildState cs) {
            if (lastValidOffset == null) {
                lastValidOffset = cs;
            } else if (cs.getChildView().getStartOffset() <
                       lastValidOffset.getChildView().getStartOffset()) {
                lastValidOffset = cs;
            }
        }

        /**
         * Paint the children that intersect the clip area.
         * <p>
         *  绘制与剪裁区域相交的子节点。
         * 
         */
        public synchronized void paintChildren(Graphics g) {
            Rectangle clip = g.getClipBounds();
            float targetOffset = (axis == X_AXIS) ?
                clip.x - lastAlloc.x : clip.y - lastAlloc.y;
            int index = getViewIndexAtVisualOffset(targetOffset);
            int n = getViewCount();
            float offs = getChildState(index).getMajorOffset();
            for (int i = index; i < n; i++) {
                ChildState cs = getChildState(i);
                cs.setMajorOffset(offs);
                Shape ca = getChildAllocation(i);
                if (intersectsClip(ca, clip)) {
                    synchronized (cs) {
                        View v = cs.getChildView();
                        v.paint(g, ca);
                    }
                } else {
                    // done painting intersection
                    break;
                }
                offs += cs.getMajorSpan();
            }
        }

        /**
         * Fetch the allocation to use for a child view.
         * This will update the offsets for all children
         * not yet updated before the given index.
         * <p>
         *  获取用于子视图的分配。这将更新在给定索引之前尚未更新的所有子项的偏移量。
         * 
         */
        public synchronized Shape getChildAllocation(int index, Shape a) {
            if (a == null) {
                return null;
            }
            setAllocation(a);
            ChildState cs = getChildState(index);
            if (lastValidOffset == null) {
                lastValidOffset = getChildState(0);
            }
            if (cs.getChildView().getStartOffset() >
                lastValidOffset.getChildView().getStartOffset()) {
                // offsets need to be updated
                updateChildOffsetsToIndex(index);
            }
            Shape ca = getChildAllocation(index);
            return ca;
        }

        /**
         * Fetches the child view index at the given point.
         * This is called by the various View methods that
         * need to calculate which child to forward a message
         * to.  This should be called by a block synchronized
         * on this object, and would typically be followed
         * with one or more calls to getChildAllocation that
         * should also be in the synchronized block.
         *
         * <p>
         *  获取给定点处的子视图索引。这是由需要计算哪个孩子转发消息的各种View方法调用。这应该由在此对象上同步的块调用,并且通常将跟随一个或多个调用getChildAllocation,也应该在同步块中。
         * 
         * 
         * @param x the X coordinate &gt;= 0
         * @param y the Y coordinate &gt;= 0
         * @param a the allocation to the View
         * @return the nearest child index
         */
        public int getViewIndexAtPoint(float x, float y, Shape a) {
            setAllocation(a);
            float targetOffset = (axis == X_AXIS) ? x - lastAlloc.x : y - lastAlloc.y;
            int index = getViewIndexAtVisualOffset(targetOffset);
            return index;
        }

        /**
         * Fetch the allocation to use for a child view.
         * <em>This does not update the offsets in the ChildState
         * records.</em>
         * <p>
         *  获取用于子视图的分配。 <em> </em>这不会更新ChildState记录中的偏移量
         * 
         */
        protected Shape getChildAllocation(int index) {
            ChildState cs = getChildState(index);
            if (! cs.isLayoutValid()) {
                cs.run();
            }
            if (axis == X_AXIS) {
                childAlloc.x = lastAlloc.x + (int) cs.getMajorOffset();
                childAlloc.y = lastAlloc.y + (int) cs.getMinorOffset();
                childAlloc.width = (int) cs.getMajorSpan();
                childAlloc.height = (int) cs.getMinorSpan();
            } else {
                childAlloc.y = lastAlloc.y + (int) cs.getMajorOffset();
                childAlloc.x = lastAlloc.x + (int) cs.getMinorOffset();
                childAlloc.height = (int) cs.getMajorSpan();
                childAlloc.width = (int) cs.getMinorSpan();
            }
            childAlloc.x += (int)getLeftInset();
            childAlloc.y += (int)getRightInset();
            return childAlloc;
        }

        /**
         * Copy the currently allocated shape into the Rectangle
         * used to store the current allocation.  This would be
         * a floating point rectangle in a Java2D-specific implementation.
         * <p>
         *  将当前分配的形状复制到用于存储当前分配的Rectangle中。在Java2D特定的实现中,这将是一个浮点矩形。
         * 
         */
        protected void setAllocation(Shape a) {
            if (a instanceof Rectangle) {
                lastAlloc.setBounds((Rectangle) a);
            } else {
                lastAlloc.setBounds(a.getBounds());
            }
            setSize(lastAlloc.width, lastAlloc.height);
        }

        /**
         * Locate the view responsible for an offset into the box
         * along the major axis.  Make sure that offsets are set
         * on the ChildState objects up to the given target span
         * past the desired offset.
         *
         * <p>
         * 找到负责沿主轴的框中的偏移的视图。确保在ChildState对象上设置偏移,直到超过所需偏移的给定目标跨度。
         * 
         * 
         * @return   index of the view representing the given visual
         *   location (targetOffset), or -1 if no view represents
         *   that location
         */
        protected int getViewIndexAtVisualOffset(float targetOffset) {
            int n = getViewCount();
            if (n > 0) {
                boolean lastValid = (lastValidOffset != null);

                if (lastValidOffset == null) {
                    lastValidOffset = getChildState(0);
                }
                if (targetOffset > majorSpan) {
                    // should only get here on the first time display.
                    if (!lastValid) {
                        return 0;
                    }
                    int pos = lastValidOffset.getChildView().getStartOffset();
                    int index = getViewIndex(pos, Position.Bias.Forward);
                    return index;
                } else if (targetOffset > lastValidOffset.getMajorOffset()) {
                    // roll offset calculations forward
                    return updateChildOffsets(targetOffset);
                } else {
                    // no changes prior to the needed offset
                    // this should be a binary search
                    float offs = 0f;
                    for (int i = 0; i < n; i++) {
                        ChildState cs = getChildState(i);
                        float nextOffs = offs + cs.getMajorSpan();
                        if (targetOffset < nextOffs) {
                            return i;
                        }
                        offs = nextOffs;
                    }
                }
            }
            return n - 1;
        }

        /**
         * Move the location of the last offset calculation forward
         * to the desired offset.
         * <p>
         *  将最后一次偏移计算的位置向前移动到所需的偏移。
         * 
         */
        int updateChildOffsets(float targetOffset) {
            int n = getViewCount();
            int targetIndex = n - 1;
            int pos = lastValidOffset.getChildView().getStartOffset();
            int startIndex = getViewIndex(pos, Position.Bias.Forward);
            float start = lastValidOffset.getMajorOffset();
            float lastOffset = start;
            for (int i = startIndex; i < n; i++) {
                ChildState cs = getChildState(i);
                cs.setMajorOffset(lastOffset);
                lastOffset += cs.getMajorSpan();
                if (targetOffset < lastOffset) {
                    targetIndex = i;
                    lastValidOffset = cs;
                    break;
                }
            }

            return targetIndex;
        }

        /**
         * Move the location of the last offset calculation forward
         * to the desired index.
         * <p>
         *  将最后一次偏移计算的位置向前移动到所需的索引。
         * 
         */
        void updateChildOffsetsToIndex(int index) {
            int pos = lastValidOffset.getChildView().getStartOffset();
            int startIndex = getViewIndex(pos, Position.Bias.Forward);
            float lastOffset = lastValidOffset.getMajorOffset();
            for (int i = startIndex; i <= index; i++) {
                ChildState cs = getChildState(i);
                cs.setMajorOffset(lastOffset);
                lastOffset += cs.getMajorSpan();
            }
        }

        boolean intersectsClip(Shape childAlloc, Rectangle clip) {
            Rectangle cs = (childAlloc instanceof Rectangle) ?
                (Rectangle) childAlloc : childAlloc.getBounds();
            if (cs.intersects(clip)) {
                // Make sure that lastAlloc also contains childAlloc,
                // this will be false if haven't yet flushed changes.
                return lastAlloc.intersects(cs);
            }
            return false;
        }

        /**
         * The location of the last offset calculation
         * that is valid.
         * <p>
         *  最后一次偏移计算的位置有效。
         * 
         */
        protected ChildState lastValidOffset;

        /**
         * The last seen allocation (for repainting when changes
         * are flushed upward).
         * <p>
         *  最后一次看到的分配(用于在更改向上刷新时重新绘制)。
         * 
         */
        protected Rectangle lastAlloc;

        /**
         * A shape to use for the child allocation to avoid
         * creating a lot of garbage.
         * <p>
         *  一个用于子分配的形状,以避免创建大量的垃圾。
         * 
         */
        protected Rectangle childAlloc;
    }

    /**
     * A record representing the layout state of a
     * child view.  It is runnable as a task on another
     * thread.  All access to the child view that is
     * based upon a read-lock on the model should synchronize
     * on this object (i.e. The layout thread and the GUI
     * thread can both have a read lock on the model at the
     * same time and are not protected from each other).
     * Access to a child view hierarchy is serialized via
     * synchronization on the ChildState instance.
     * <p>
     *  表示子视图的布局状态的记录。它可作为另一个线程上的任务运行。对基于模型上的读锁定的子视图的所有访问应该在该对象上同步(即布局线程和GUI线程可以同时具有对模型的读取锁定,并且不受每个其他)。
     * 通过ChildState实例上的同步可以对子视图层次结构进行序列化。
     * 
     * 
     * @since 1.3
     */
    public class ChildState implements Runnable {

        /**
         * Construct a child status.  This needs to start
         * out as fairly large so we don't falsely begin with
         * the idea that all of the children are visible.
         * <p>
         *  构造子状态。这需要开始相当大,所以我们不是虚假地开始的想法,所有的孩子是可见的。
         * 
         * 
         * @since 1.4
         */
        public ChildState(View v) {
            child = v;
            minorValid = false;
            majorValid = false;
            childSizeValid = false;
            child.setParent(AsyncBoxView.this);
        }

        /**
         * Fetch the child view this record represents
         * <p>
         *  获取此记录表示的子视图
         * 
         */
        public View getChildView() {
            return child;
        }

        /**
         * Update the child state.  This should be
         * called by the thread that desires to spend
         * time updating the child state (intended to
         * be the layout thread).
         * <p>
         * This acquires a read lock on the associated
         * document for the duration of the update to
         * ensure the model is not changed while it is
         * operating.  The first thing to do would be
         * to see if any work actually needs to be done.
         * The following could have conceivably happened
         * while the state was waiting to be updated:
         * <ol>
         * <li>The child may have been removed from the
         * view hierarchy.
         * <li>The child may have been updated by a
         * higher priority operation (i.e. the child
         * may have become visible).
         * </ol>
         * <p>
         *  更新子状态。这应该由希望花费更新子状态(意图是布局线程)的线程来调用。
         * <p>
         * 这将在更新期间获取相关文档的读锁定,以确保模型在操作时不会更改。首先要做的是看看是否真的需要做任何工作。在国家等待更新时可能发生以下情况：
         * <ol>
         *  <li>子视图可能已从视图层次结构中删除。 <li>子项可能已经通过较高优先级操作更新(即子项可能已变为可见)。
         * </ol>
         */
        public void run () {
            AbstractDocument doc = (AbstractDocument) getDocument();
            try {
                doc.readLock();
                if (minorValid && majorValid && childSizeValid) {
                    // nothing to do
                    return;
                }
                if (child.getParent() == AsyncBoxView.this) {
                    // this may overwrite anothers threads cached
                    // value for actively changing... but that just
                    // means it won't use the cache if there is an
                    // overwrite.
                    synchronized(AsyncBoxView.this) {
                        changing = this;
                    }
                    updateChild();
                    synchronized(AsyncBoxView.this) {
                        changing = null;
                    }

                    // setting the child size on the minor axis
                    // may have caused it to change it's preference
                    // along the major axis.
                    updateChild();
                }
            } finally {
                doc.readUnlock();
            }
        }

        void updateChild() {
            boolean minorUpdated = false;
            synchronized(this) {
                if (! minorValid) {
                    int minorAxis = getMinorAxis();
                    min = child.getMinimumSpan(minorAxis);
                    pref = child.getPreferredSpan(minorAxis);
                    max = child.getMaximumSpan(minorAxis);
                    minorValid = true;
                    minorUpdated = true;
                }
            }
            if (minorUpdated) {
                minorRequirementChange(this);
            }

            boolean majorUpdated = false;
            float delta = 0.0f;
            synchronized(this) {
                if (! majorValid) {
                    float old = span;
                    span = child.getPreferredSpan(axis);
                    delta = span - old;
                    majorValid = true;
                    majorUpdated = true;
                }
            }
            if (majorUpdated) {
                majorRequirementChange(this, delta);
                locator.childChanged(this);
            }

            synchronized(this) {
                if (! childSizeValid) {
                    float w;
                    float h;
                    if (axis == X_AXIS) {
                        w = span;
                        h = getMinorSpan();
                    } else {
                        w = getMinorSpan();
                        h = span;
                    }
                    childSizeValid = true;
                    child.setSize(w, h);
                }
            }

        }

        /**
         * What is the span along the minor axis.
         * <p>
         *  沿着短轴的跨度是多少。
         * 
         */
        public float getMinorSpan() {
            if (max < minorSpan) {
                return max;
            }
            // make it the target width, or as small as it can get.
            return Math.max(min, minorSpan);
        }

        /**
         * What is the offset along the minor axis
         * <p>
         *  沿着短轴的偏移量是多少?
         * 
         */
        public float getMinorOffset() {
            if (max < minorSpan) {
                // can't make the child this wide, align it
                float align = child.getAlignment(getMinorAxis());
                return ((minorSpan - max) * align);
            }
            return 0f;
        }

        /**
         * What is the span along the major axis.
         * <p>
         *  沿主轴的跨度是多少。
         * 
         */
        public float getMajorSpan() {
            return span;
        }

        /**
         * Get the offset along the major axis
         * <p>
         *  获得沿主轴的偏移
         * 
         */
        public float getMajorOffset() {
            return offset;
        }

        /**
         * This method should only be called by the ChildLocator,
         * it is simply a convenient place to hold the cached
         * location.
         * <p>
         *  此方法应该只由ChildLocator调用,它只是一个方便的地方来保存缓存的位置。
         * 
         */
        public void setMajorOffset(float offs) {
            offset = offs;
        }

        /**
         * Mark preferences changed for this child.
         *
         * <p>
         *  为此孩子更改了标记首选项。
         * 
         * 
         * @param width true if the width preference has changed
         * @param height true if the height preference has changed
         * @see javax.swing.JComponent#revalidate
         */
        public void preferenceChanged(boolean width, boolean height) {
            if (axis == X_AXIS) {
                if (width) {
                    majorValid = false;
                }
                if (height) {
                    minorValid = false;
                }
            } else {
                if (width) {
                    minorValid = false;
                }
                if (height) {
                    majorValid = false;
                }
            }
            childSizeValid = false;
        }

        /**
         * Has the child view been laid out.
         * <p>
         *  有儿童观点被布局。
         * 
         */
        public boolean isLayoutValid() {
            return (minorValid && majorValid && childSizeValid);
        }

        // minor axis
        private float min;
        private float pref;
        private float max;
        private boolean minorValid;

        // major axis
        private float span;
        private float offset;
        private boolean majorValid;

        private View child;
        private boolean childSizeValid;
    }

    /**
     * Task to flush requirement changes upward
     * <p>
     *  向上刷新需求更改的任务
     */
    class FlushTask implements Runnable {

        public void run() {
            flushRequirementChanges();
        }

    }

}
