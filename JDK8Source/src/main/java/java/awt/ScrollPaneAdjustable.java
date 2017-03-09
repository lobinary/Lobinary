/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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
package java.awt;

import sun.awt.AWTAccessor;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.peer.ScrollPanePeer;
import java.io.Serializable;


/**
 * This class represents the state of a horizontal or vertical
 * scrollbar of a <code>ScrollPane</code>.  Objects of this class are
 * returned by <code>ScrollPane</code> methods.
 *
 * <p>
 *  此类表示<code> ScrollPane </code>的水平或垂直滚动​​条的状态。此类的对象由<code> ScrollPane </code>方法返回。
 * 
 * 
 * @since       1.4
 */
public class ScrollPaneAdjustable implements Adjustable, Serializable {

    /**
     * The <code>ScrollPane</code> this object is a scrollbar of.
     * <p>
     *  <code> ScrollPane </code>此对象是一个滚动条。
     * 
     * 
     * @serial
     */
    private ScrollPane sp;

    /**
     * Orientation of this scrollbar.
     *
     * <p>
     *  此滚动条的方向。
     * 
     * 
     * @serial
     * @see #getOrientation
     * @see java.awt.Adjustable#HORIZONTAL
     * @see java.awt.Adjustable#VERTICAL
     */
    private int orientation;

    /**
     * The value of this scrollbar.
     * <code>value</code> should be greater than <code>minimum</code>
     * and less than <code>maximum</code>
     *
     * <p>
     *  此滚动条的值。 <code> value </code>应大于<code> minimum </code>且小于<code> maximum </code>
     * 
     * 
     * @serial
     * @see #getValue
     * @see #setValue
     */
    private int value;

    /**
     * The minimum value of this scrollbar.
     * This value can only be set by the <code>ScrollPane</code>.
     * <p>
     * <strong>ATTN:</strong> In current implementation
     * <code>minimum</code> is always <code>0</code>.  This field can
     * only be altered via <code>setSpan</code> method and
     * <code>ScrollPane</code> always calls that method with
     * <code>0</code> for the minimum.  <code>getMinimum</code> method
     * always returns <code>0</code> without checking this field.
     *
     * <p>
     *  此滚动条的最小值。此值只能由<code> ScrollPane </code>设置。
     * <p>
     *  <strong> ATTN：</strong>在当前实现中,<code> minimum </code>始终为<code> 0 </code>。
     * 此字段只能通过<code> setSpan </code>方法更改,<code> ScrollPane </code>始终使用<code> 0 </code>调用该方法的最小值。
     *  <code> getMinimum </code>方法总是返回<code> 0 </code>,而不选中此字段。
     * 
     * 
     * @serial
     * @see #getMinimum
     * @see #setSpan(int, int, int)
     */
    private int minimum;

    /**
     * The maximum value of this scrollbar.
     * This value can only be set by the <code>ScrollPane</code>.
     *
     * <p>
     *  此滚动条的最大值。此值只能由<code> ScrollPane </code>设置。
     * 
     * 
     * @serial
     * @see #getMaximum
     * @see #setSpan(int, int, int)
     */
    private int maximum;

    /**
     * The size of the visible portion of this scrollbar.
     * This value can only be set by the <code>ScrollPane</code>.
     *
     * <p>
     *  此滚动条的可见部分的大小。此值只能由<code> ScrollPane </code>设置。
     * 
     * 
     * @serial
     * @see #getVisibleAmount
     * @see #setSpan(int, int, int)
     */
    private int visibleAmount;

    /**
     * The adjusting status of the <code>Scrollbar</code>.
     * True if the value is in the process of changing as a result of
     * actions being taken by the user.
     *
     * <p>
     *  <code> Scrollbar </code>的调整状态。如果值处于由于用户执行的操作而发生更改的过程中,则为true。
     * 
     * 
     * @see #getValueIsAdjusting
     * @see #setValueIsAdjusting
     * @since 1.4
     */
    private transient boolean isAdjusting;

    /**
     * The amount by which the scrollbar value will change when going
     * up or down by a line.
     * This value should be a non negative integer.
     *
     * <p>
     *  当向上或向下移动一行时滚动条值将改变的量。此值应为非负整数。
     * 
     * 
     * @serial
     * @see #getUnitIncrement
     * @see #setUnitIncrement
     */
    private int unitIncrement  = 1;

    /**
     * The amount by which the scrollbar value will change when going
     * up or down by a page.
     * This value should be a non negative integer.
     *
     * <p>
     * 当向上或向下按页面时滚动条值将改变的量。此值应为非负整数。
     * 
     * 
     * @serial
     * @see #getBlockIncrement
     * @see #setBlockIncrement
     */
    private int blockIncrement = 1;

    private AdjustmentListener adjustmentListener;

    /**
     * Error message for <code>AWTError</code> reported when one of
     * the public but unsupported methods is called.
     * <p>
     *  当调用其中一个公共但不支持的方法时报告的<code> AWTError </code>的错误消息。
     * 
     */
    private static final String SCROLLPANE_ONLY =
        "Can be set by scrollpane only";


    /**
     * Initialize JNI field and method ids.
     * <p>
     *  初始化JNI字段和方法标识。
     * 
     */
    private static native void initIDs();

    static {
        Toolkit.loadLibraries();
        if (!GraphicsEnvironment.isHeadless()) {
            initIDs();
        }
        AWTAccessor.setScrollPaneAdjustableAccessor(new AWTAccessor.ScrollPaneAdjustableAccessor() {
            public void setTypedValue(final ScrollPaneAdjustable adj,
                                      final int v, final int type) {
                adj.setTypedValue(v, type);
            }
        });
    }

    /**
     * JDK 1.1 serialVersionUID.
     * <p>
     *  JDK 1.1 serialVersionUID。
     * 
     */
    private static final long serialVersionUID = -3359745691033257079L;


    /**
     * Constructs a new object to represent specified scrollabar
     * of the specified <code>ScrollPane</code>.
     * Only ScrollPane creates instances of this class.
     * <p>
     *  构造一个新对象以表示指定的scrollabar指定的<code> ScrollPane </code>。只有ScrollPane创建此类的实例。
     * 
     * 
     * @param sp           <code>ScrollPane</code>
     * @param l            <code>AdjustmentListener</code> to add upon creation.
     * @param orientation  specifies which scrollbar this object represents,
     *                     can be either  <code>Adjustable.HORIZONTAL</code>
     *                     or <code>Adjustable.VERTICAL</code>.
     */
    ScrollPaneAdjustable(ScrollPane sp, AdjustmentListener l, int orientation) {
        this.sp = sp;
        this.orientation = orientation;
        addAdjustmentListener(l);
    }

    /**
     * This is called by the scrollpane itself to update the
     * <code>minimum</code>, <code>maximum</code> and
     * <code>visible</code> values.  The scrollpane is the only one
     * that should be changing these since it is the source of these
     * values.
     * <p>
     *  这由滚动条本身调用以更新<code>最小</code>,<code>最大</code>和<code>可见</code>值。滚动条是唯一一个应该更改这些,因为它是这些值的来源。
     * 
     */
    void setSpan(int min, int max, int visible) {
        // adjust the values to be reasonable
        minimum = min;
        maximum = Math.max(max, minimum + 1);
        visibleAmount = Math.min(visible, maximum - minimum);
        visibleAmount = Math.max(visibleAmount, 1);
        blockIncrement = Math.max((int)(visible * .90), 1);
        setValue(value);
    }

    /**
     * Returns the orientation of this scrollbar.
     * <p>
     *  返回此滚动条的方向。
     * 
     * 
     * @return    the orientation of this scrollbar, either
     *            <code>Adjustable.HORIZONTAL</code> or
     *            <code>Adjustable.VERTICAL</code>
     */
    public int getOrientation() {
        return orientation;
    }

    /**
     * This method should <strong>NOT</strong> be called by user code.
     * This method is public for this class to properly implement
     * <code>Adjustable</code> interface.
     *
     * <p>
     *  此方法应<strong>不</strong>由用户代码调用。这个方法是公共的这个类正确实现<code>可调整的</code>接口。
     * 
     * 
     * @throws AWTError Always throws an error when called.
     */
    public void setMinimum(int min) {
        throw new AWTError(SCROLLPANE_ONLY);
    }

    public int getMinimum() {
        // XXX: This relies on setSpan always being called with 0 for
        // the minimum (which is currently true).
        return 0;
    }

    /**
     * This method should <strong>NOT</strong> be called by user code.
     * This method is public for this class to properly implement
     * <code>Adjustable</code> interface.
     *
     * <p>
     *  此方法应<strong>不</strong>由用户代码调用。这个方法是公共的这个类正确实现<code>可调整的</code>接口。
     * 
     * 
     * @throws AWTError Always throws an error when called.
     */
    public void setMaximum(int max) {
        throw new AWTError(SCROLLPANE_ONLY);
    }

    public int getMaximum() {
        return maximum;
    }

    public synchronized void setUnitIncrement(int u) {
        if (u != unitIncrement) {
            unitIncrement = u;
            if (sp.peer != null) {
                ScrollPanePeer peer = (ScrollPanePeer) sp.peer;
                peer.setUnitIncrement(this, u);
            }
        }
    }

    public int getUnitIncrement() {
        return unitIncrement;
    }

    public synchronized void setBlockIncrement(int b) {
        blockIncrement = b;
    }

    public int getBlockIncrement() {
        return blockIncrement;
    }

    /**
     * This method should <strong>NOT</strong> be called by user code.
     * This method is public for this class to properly implement
     * <code>Adjustable</code> interface.
     *
     * <p>
     *  此方法应<strong>不</strong>由用户代码调用。这个方法是公共的这个类正确实现<code>可调整的</code>接口。
     * 
     * 
     * @throws AWTError Always throws an error when called.
     */
    public void setVisibleAmount(int v) {
        throw new AWTError(SCROLLPANE_ONLY);
    }

    public int getVisibleAmount() {
        return visibleAmount;
    }


    /**
     * Sets the <code>valueIsAdjusting</code> property.
     *
     * <p>
     *  设置<code> valueIsAdjusting </code>属性。
     * 
     * 
     * @param b new adjustment-in-progress status
     * @see #getValueIsAdjusting
     * @since 1.4
     */
    public void setValueIsAdjusting(boolean b) {
        if (isAdjusting != b) {
            isAdjusting = b;
            AdjustmentEvent e =
                new AdjustmentEvent(this,
                        AdjustmentEvent.ADJUSTMENT_VALUE_CHANGED,
                        AdjustmentEvent.TRACK, value, b);
            adjustmentListener.adjustmentValueChanged(e);
        }
    }

    /**
     * Returns true if the value is in the process of changing as a
     * result of actions being taken by the user.
     *
     * <p>
     *  如果值处于由用户执行的操作的结果而发生更改的过程中,则返回true。
     * 
     * 
     * @return the value of the <code>valueIsAdjusting</code> property
     * @see #setValueIsAdjusting
     */
    public boolean getValueIsAdjusting() {
        return isAdjusting;
    }

    /**
     * Sets the value of this scrollbar to the specified value.
     * <p>
     * If the value supplied is less than the current minimum or
     * greater than the current maximum, then one of those values is
     * substituted, as appropriate.
     *
     * <p>
     *  将此滚动条的值设置为指定的值。
     * <p>
     * 如果所提供的值小于当前最小值或大于当前最大值,则适当地替换那些值中的一个。
     * 
     * 
     * @param v the new value of the scrollbar
     */
    public void setValue(int v) {
        setTypedValue(v, AdjustmentEvent.TRACK);
    }

    /**
     * Sets the value of this scrollbar to the specified value.
     * <p>
     * If the value supplied is less than the current minimum or
     * greater than the current maximum, then one of those values is
     * substituted, as appropriate. Also, creates and dispatches
     * the AdjustementEvent with specified type and value.
     *
     * <p>
     *  将此滚动条的值设置为指定的值。
     * <p>
     *  如果所提供的值小于当前最小值或大于当前最大值,则适当地替换那些值中的一个。此外,使用指定的类型和值创建和分派AdjustementEvent。
     * 
     * 
     * @param v the new value of the scrollbar
     * @param type the type of the scrolling operation occurred
     */
    private void setTypedValue(int v, int type) {
        v = Math.max(v, minimum);
        v = Math.min(v, maximum - visibleAmount);

        if (v != value) {
            value = v;
            // Synchronously notify the listeners so that they are
            // guaranteed to be up-to-date with the Adjustable before
            // it is mutated again.
            AdjustmentEvent e =
                new AdjustmentEvent(this,
                        AdjustmentEvent.ADJUSTMENT_VALUE_CHANGED,
                        type, value, isAdjusting);
            adjustmentListener.adjustmentValueChanged(e);
        }
    }

    public int getValue() {
        return value;
    }

    /**
     * Adds the specified adjustment listener to receive adjustment
     * events from this <code>ScrollPaneAdjustable</code>.
     * If <code>l</code> is <code>null</code>, no exception is thrown
     * and no action is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     *  添加指定的调整侦听器以从此<code> ScrollPaneAdjustable </code>接收调整事件。
     * 如果<code> l </code>是<code> null </code>,则不抛出异常,并且不执行任何操作。
     *  <p>有关AWT的线程模型的详细信息,请参阅<a href="doc-files/AWTThreadIssues.html#ListenersThreads"> AWT线程问题</a>。
     * 
     * 
     * @param    l   the adjustment listener.
     * @see      #removeAdjustmentListener
     * @see      #getAdjustmentListeners
     * @see      java.awt.event.AdjustmentListener
     * @see      java.awt.event.AdjustmentEvent
     */
    public synchronized void addAdjustmentListener(AdjustmentListener l) {
        if (l == null) {
            return;
        }
        adjustmentListener = AWTEventMulticaster.add(adjustmentListener, l);
    }

    /**
     * Removes the specified adjustment listener so that it no longer
     * receives adjustment events from this <code>ScrollPaneAdjustable</code>.
     * If <code>l</code> is <code>null</code>, no exception is thrown
     * and no action is performed.
     * <p>Refer to <a href="doc-files/AWTThreadIssues.html#ListenersThreads"
     * >AWT Threading Issues</a> for details on AWT's threading model.
     *
     * <p>
     *  删除指定的调整侦听器,以使其不再从此<code> ScrollPaneAdjustable </code>接收调整事件。
     * 如果<code> l </code>是<code> null </code>,则不抛出异常,并且不执行任何操作。
     *  <p>有关AWT的线程模型的详细信息,请参阅<a href="doc-files/AWTThreadIssues.html#ListenersThreads"> AWT线程问题</a>。
     * 
     * 
     * @param         l     the adjustment listener.
     * @see           #addAdjustmentListener
     * @see           #getAdjustmentListeners
     * @see           java.awt.event.AdjustmentListener
     * @see           java.awt.event.AdjustmentEvent
     * @since         JDK1.1
     */
    public synchronized void removeAdjustmentListener(AdjustmentListener l){
        if (l == null) {
            return;
        }
        adjustmentListener = AWTEventMulticaster.remove(adjustmentListener, l);
    }

    /**
     * Returns an array of all the adjustment listeners
     * registered on this <code>ScrollPaneAdjustable</code>.
     *
     * <p>
     *  返回在此<code> ScrollPaneAdjustable </code>上注册的所有调整侦听器的数组。
     * 
     * 
     * @return all of this <code>ScrollPaneAdjustable</code>'s
     *         <code>AdjustmentListener</code>s
     *         or an empty array if no adjustment
     *         listeners are currently registered
     *
     * @see           #addAdjustmentListener
     * @see           #removeAdjustmentListener
     * @see           java.awt.event.AdjustmentListener
     * @see           java.awt.event.AdjustmentEvent
     * @since 1.4
     */
    public synchronized AdjustmentListener[] getAdjustmentListeners() {
        return (AdjustmentListener[])(AWTEventMulticaster.getListeners(
                                      adjustmentListener,
                                      AdjustmentListener.class));
    }

    /**
     * Returns a string representation of this scrollbar and its values.
     * <p>
     *  返回此滚动条及其值的字符串表示形式。
     * 
     * 
     * @return    a string representation of this scrollbar.
     */
    public String toString() {
        return getClass().getName() + "[" + paramString() + "]";
    }

    /**
     * Returns a string representing the state of this scrollbar.
     * This method is intended to be used only for debugging purposes,
     * and the content and format of the returned string may vary
     * between implementations.  The returned string may be empty but
     * may not be <code>null</code>.
     *
     * <p>
     * 返回一个表示此滚动条状态的字符串。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * @return      the parameter string of this scrollbar.
     */
    public String paramString() {
        return ((orientation == Adjustable.VERTICAL ? "vertical,"
                                                    :"horizontal,")
                + "[0.."+maximum+"]"
                + ",val=" + value
                + ",vis=" + visibleAmount
                + ",unit=" + unitIncrement
                + ",block=" + blockIncrement
                + ",isAdjusting=" + isAdjusting);
    }
}
