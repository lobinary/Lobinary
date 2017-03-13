/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.event.*;
import java.lang.reflect.Array;
import java.util.EventListener;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.util.EventListener;


/**
 * {@code AWTEventMulticaster} implements efficient and thread-safe multi-cast
 * event dispatching for the AWT events defined in the {@code java.awt.event}
 * package.
 * <p>
 * The following example illustrates how to use this class:
 *
 * <pre><code>
 * public myComponent extends Component {
 *     ActionListener actionListener = null;
 *
 *     public synchronized void addActionListener(ActionListener l) {
 *         actionListener = AWTEventMulticaster.add(actionListener, l);
 *     }
 *     public synchronized void removeActionListener(ActionListener l) {
 *         actionListener = AWTEventMulticaster.remove(actionListener, l);
 *     }
 *     public void processEvent(AWTEvent e) {
 *         // when event occurs which causes "action" semantic
 *         ActionListener listener = actionListener;
 *         if (listener != null) {
 *             listener.actionPerformed(new ActionEvent());
 *         }
 *     }
 * }
 * </code></pre>
 * The important point to note is the first argument to the {@code
 * add} and {@code remove} methods is the field maintaining the
 * listeners. In addition you must assign the result of the {@code add}
 * and {@code remove} methods to the field maintaining the listeners.
 * <p>
 * {@code AWTEventMulticaster} is implemented as a pair of {@code
 * EventListeners} that are set at construction time. {@code
 * AWTEventMulticaster} is immutable. The {@code add} and {@code
 * remove} methods do not alter {@code AWTEventMulticaster} in
 * anyway. If necessary, a new {@code AWTEventMulticaster} is
 * created. In this way it is safe to add and remove listeners during
 * the process of an event dispatching.  However, event listeners
 * added during the process of an event dispatch operation are not
 * notified of the event currently being dispatched.
 * <p>
 * All of the {@code add} methods allow {@code null} arguments. If the
 * first argument is {@code null}, the second argument is returned. If
 * the first argument is not {@code null} and the second argument is
 * {@code null}, the first argument is returned. If both arguments are
 * {@code non-null}, a new {@code AWTEventMulticaster} is created using
 * the two arguments and returned.
 * <p>
 * For the {@code remove} methods that take two arguments, the following is
 * returned:
 * <ul>
 *   <li>{@code null}, if the first argument is {@code null}, or
 *       the arguments are equal, by way of {@code ==}.
 *   <li>the first argument, if the first argument is not an instance of
 *       {@code AWTEventMulticaster}.
 *   <li>result of invoking {@code remove(EventListener)} on the
 *       first argument, supplying the second argument to the
 *       {@code remove(EventListener)} method.
 * </ul>
 * <p>Swing makes use of
 * {@link javax.swing.event.EventListenerList EventListenerList} for
 * similar logic. Refer to it for details.
 *
 * <p>
 *  {@code AWTEventMulticaster}为{@code javaawtevent}包中定义的AWT事件实施高效且线程安全的多播事件分派
 * <p>
 *  以下示例说明如何使用此类：
 * 
 *  <pre> <code> public myComponent extends Component {ActionListener actionListener = null;
 * 
 * public synchronized void addActionListener(ActionListener l){actionListener = AWTEventMulticasteradd(actionListener,l); }
 *  public synchronized void removeActionListener(ActionListener l){actionListener = AWTEventMulticasterremove(actionListener,l); }
 *  public void processEvent(AWTEvent e){//当事件发生时导致"动作"语义ActionListener listener = actionListener; if(listener！= null){listeneractionPerformed(new ActionEvent());需要注意的重要一点是{@code add}
 * 和{@code remove}方法的第一个参数是保持侦听器的字段此外,您必须将{ @code add}和{@code remove}方法保留侦听器。
 * <p>
 * {@code AWTEventMulticaster}被实现为在构造时设置的一对{@code EventListeners}是不可变的{@code add}和{@code remove}方法不会改变{@code AWTEventMulticaster}
 * 无论如何如果需要,创建一个新的{@code AWTEventMulticaster}这样,在事件分派过程中添加和删除侦听器是安全的但是,在事件分派操作过程中添加的事件侦听器不会被通知事件。
 * <p>
 * 所有{@code add}方法允许{@code null}参数如果第一个参数是{@code null},则返回第二个参数如果第一个参数不是{@code null},第二个参数是{@代码null},返回第
 * 一个参数如果两个参数都是{@code non-null},则使用这两个参数创建一个新的{@code AWTEventMulticaster}并返回。
 * <p>
 *  对于接受两个参数的{@code remove}方法,返回以下内容：
 * <ul>
 * <li> {@ code null},如果第一个参数是{@code null}或参数相等,则通过{@code ==} <li>第一个参数,如果第一个参数不是实例of {@code AWTEventMulticaster}
 *  <li>对第一个参数调用{@code remove(EventListener)}的结果,将第二个参数提供给{@code remove(EventListener)}方法。
 * </ul>
 *  <p> Swing使用{@link javaxswingeventEventListenerList EventListenerList}获得类似的逻辑详情请参考
 * 
 * 
 * @see javax.swing.event.EventListenerList
 *
 * @author      John Rose
 * @author      Amy Fowler
 * @since       1.1
 */

public class AWTEventMulticaster implements
    ComponentListener, ContainerListener, FocusListener, KeyListener,
    MouseListener, MouseMotionListener, WindowListener, WindowFocusListener,
    WindowStateListener, ActionListener, ItemListener, AdjustmentListener,
    TextListener, InputMethodListener, HierarchyListener,
    HierarchyBoundsListener, MouseWheelListener {

    protected final EventListener a, b;

    /**
     * Creates an event multicaster instance which chains listener-a
     * with listener-b. Input parameters <code>a</code> and <code>b</code>
     * should not be <code>null</code>, though implementations may vary in
     * choosing whether or not to throw <code>NullPointerException</code>
     * in that case.
     * <p>
     *  创建将listener-a与listener-b链接的事件multicaster实例输入参数<code> a </code>和<code> b </code>不应为<code> null </code>
     * 选择是否在这种情况下抛出<code> NullPointerException </code>。
     * 
     * 
     * @param a listener-a
     * @param b listener-b
     */
    protected AWTEventMulticaster(EventListener a, EventListener b) {
        this.a = a; this.b = b;
    }

    /**
     * Removes a listener from this multicaster.
     * <p>
     * The returned multicaster contains all the listeners in this
     * multicaster with the exception of all occurrences of {@code oldl}.
     * If the resulting multicaster contains only one regular listener
     * the regular listener may be returned.  If the resulting multicaster
     * is empty, then {@code null} may be returned instead.
     * <p>
     * No exception is thrown if {@code oldl} is {@code null}.
     *
     * <p>
     * 从此多任务器中删除侦听器
     * <p>
     *  返回的多任务器包含此多任务器中的所有侦听器,但所有出现的{@code oldl}除外。如果生成的多任务器只包含一个常规侦听器,则可能返回常规侦听器。
     * 如果生成的多任务器为空,则{@code null}可能会返回。
     * <p>
     *  如果{@code oldl}是{@code null},则不会抛出异常
     * 
     * 
     * @param oldl the listener to be removed
     * @return resulting listener
     */
    protected EventListener remove(EventListener oldl) {
        if (oldl == a)  return b;
        if (oldl == b)  return a;
        EventListener a2 = removeInternal(a, oldl);
        EventListener b2 = removeInternal(b, oldl);
        if (a2 == a && b2 == b) {
            return this;        // it's not here
        }
        return addInternal(a2, b2);
    }

    /**
     * Handles the componentResized event by invoking the
     * componentResized methods on listener-a and listener-b.
     * <p>
     *  通过调用listener-a和listener-b上的componentResized方法处理componentResized事件
     * 
     * 
     * @param e the component event
     */
    public void componentResized(ComponentEvent e) {
        ((ComponentListener)a).componentResized(e);
        ((ComponentListener)b).componentResized(e);
    }

    /**
     * Handles the componentMoved event by invoking the
     * componentMoved methods on listener-a and listener-b.
     * <p>
     *  通过调用侦听器a和侦听器b上的componentMoved方法处理componentMoved事件
     * 
     * 
     * @param e the component event
     */
    public void componentMoved(ComponentEvent e) {
        ((ComponentListener)a).componentMoved(e);
        ((ComponentListener)b).componentMoved(e);
    }

    /**
     * Handles the componentShown event by invoking the
     * componentShown methods on listener-a and listener-b.
     * <p>
     *  通过调用侦听器a和侦听器b上的componentShown方法处理componentShown事件
     * 
     * 
     * @param e the component event
     */
    public void componentShown(ComponentEvent e) {
        ((ComponentListener)a).componentShown(e);
        ((ComponentListener)b).componentShown(e);
    }

    /**
     * Handles the componentHidden event by invoking the
     * componentHidden methods on listener-a and listener-b.
     * <p>
     * 通过调用listener-a和listener-b上的componentHidden方法处理componentHidden事件
     * 
     * 
     * @param e the component event
     */
    public void componentHidden(ComponentEvent e) {
        ((ComponentListener)a).componentHidden(e);
        ((ComponentListener)b).componentHidden(e);
    }

    /**
     * Handles the componentAdded container event by invoking the
     * componentAdded methods on listener-a and listener-b.
     * <p>
     *  通过调用侦听器a和侦听器b上的componentAdded方法处理componentAdded容器事件
     * 
     * 
     * @param e the component event
     */
    public void componentAdded(ContainerEvent e) {
        ((ContainerListener)a).componentAdded(e);
        ((ContainerListener)b).componentAdded(e);
    }

    /**
     * Handles the componentRemoved container event by invoking the
     * componentRemoved methods on listener-a and listener-b.
     * <p>
     *  通过调用listener-a和listener-b上的componentRemoved方法处理componentRemoved容器事件
     * 
     * 
     * @param e the component event
     */
    public void componentRemoved(ContainerEvent e) {
        ((ContainerListener)a).componentRemoved(e);
        ((ContainerListener)b).componentRemoved(e);
    }

    /**
     * Handles the focusGained event by invoking the
     * focusGained methods on listener-a and listener-b.
     * <p>
     *  通过调用listener-a和listener-b上的focusGained方法处理focusGained事件
     * 
     * 
     * @param e the focus event
     */
    public void focusGained(FocusEvent e) {
        ((FocusListener)a).focusGained(e);
        ((FocusListener)b).focusGained(e);
    }

    /**
     * Handles the focusLost event by invoking the
     * focusLost methods on listener-a and listener-b.
     * <p>
     *  通过调用listener-a和listener-b上的focusLost方法处理focusLost事件
     * 
     * 
     * @param e the focus event
     */
    public void focusLost(FocusEvent e) {
        ((FocusListener)a).focusLost(e);
        ((FocusListener)b).focusLost(e);
    }

    /**
     * Handles the keyTyped event by invoking the
     * keyTyped methods on listener-a and listener-b.
     * <p>
     *  通过调用侦听器a和侦听器b上的keyTyped方法处理keyTyped事件
     * 
     * 
     * @param e the key event
     */
    public void keyTyped(KeyEvent e) {
        ((KeyListener)a).keyTyped(e);
        ((KeyListener)b).keyTyped(e);
    }

    /**
     * Handles the keyPressed event by invoking the
     * keyPressed methods on listener-a and listener-b.
     * <p>
     *  通过调用侦听器a和侦听器b上的keyPressed方法处理keyPressed事件
     * 
     * 
     * @param e the key event
     */
    public void keyPressed(KeyEvent e) {
        ((KeyListener)a).keyPressed(e);
        ((KeyListener)b).keyPressed(e);
    }

    /**
     * Handles the keyReleased event by invoking the
     * keyReleased methods on listener-a and listener-b.
     * <p>
     * 通过调用listener-a和listener-b上的keyReleased方法处理keyReleased事件
     * 
     * 
     * @param e the key event
     */
    public void keyReleased(KeyEvent e) {
        ((KeyListener)a).keyReleased(e);
        ((KeyListener)b).keyReleased(e);
    }

    /**
     * Handles the mouseClicked event by invoking the
     * mouseClicked methods on listener-a and listener-b.
     * <p>
     *  通过调用listener-a和listener-b上的mouseClicked方法处理mouseClicked事件
     * 
     * 
     * @param e the mouse event
     */
    public void mouseClicked(MouseEvent e) {
        ((MouseListener)a).mouseClicked(e);
        ((MouseListener)b).mouseClicked(e);
    }

    /**
     * Handles the mousePressed event by invoking the
     * mousePressed methods on listener-a and listener-b.
     * <p>
     *  通过调用侦听器a和侦听器b上的mousePressed方法处理mousePressed事件
     * 
     * 
     * @param e the mouse event
     */
    public void mousePressed(MouseEvent e) {
        ((MouseListener)a).mousePressed(e);
        ((MouseListener)b).mousePressed(e);
    }

    /**
     * Handles the mouseReleased event by invoking the
     * mouseReleased methods on listener-a and listener-b.
     * <p>
     *  通过调用监听器a和监听器b上的mouseReleased方法处理mouseReleased事件
     * 
     * 
     * @param e the mouse event
     */
    public void mouseReleased(MouseEvent e) {
        ((MouseListener)a).mouseReleased(e);
        ((MouseListener)b).mouseReleased(e);
    }

    /**
     * Handles the mouseEntered event by invoking the
     * mouseEntered methods on listener-a and listener-b.
     * <p>
     *  通过调用listener-a和listener-b上的mouseEntered方法处理mouseEntered事件
     * 
     * 
     * @param e the mouse event
     */
    public void mouseEntered(MouseEvent e) {
        ((MouseListener)a).mouseEntered(e);
        ((MouseListener)b).mouseEntered(e);
    }

    /**
     * Handles the mouseExited event by invoking the
     * mouseExited methods on listener-a and listener-b.
     * <p>
     *  通过调用listener-a和listener-b上的mouseExited方法处理mouseExited事件
     * 
     * 
     * @param e the mouse event
     */
    public void mouseExited(MouseEvent e) {
        ((MouseListener)a).mouseExited(e);
        ((MouseListener)b).mouseExited(e);
    }

    /**
     * Handles the mouseDragged event by invoking the
     * mouseDragged methods on listener-a and listener-b.
     * <p>
     *  通过调用listener-a和listener-b上的mouseDragged方法处理mouseDragged事件
     * 
     * 
     * @param e the mouse event
     */
    public void mouseDragged(MouseEvent e) {
        ((MouseMotionListener)a).mouseDragged(e);
        ((MouseMotionListener)b).mouseDragged(e);
    }

    /**
     * Handles the mouseMoved event by invoking the
     * mouseMoved methods on listener-a and listener-b.
     * <p>
     *  通过调用监听器a和监听器b上的mouseMoved方法处理mouseMoved事件
     * 
     * 
     * @param e the mouse event
     */
    public void mouseMoved(MouseEvent e) {
        ((MouseMotionListener)a).mouseMoved(e);
        ((MouseMotionListener)b).mouseMoved(e);
    }

    /**
     * Handles the windowOpened event by invoking the
     * windowOpened methods on listener-a and listener-b.
     * <p>
     * 通过调用侦听器a和侦听器b上的windowOpened方法处理windowOpened事件
     * 
     * 
     * @param e the window event
     */
    public void windowOpened(WindowEvent e) {
        ((WindowListener)a).windowOpened(e);
        ((WindowListener)b).windowOpened(e);
    }

    /**
     * Handles the windowClosing event by invoking the
     * windowClosing methods on listener-a and listener-b.
     * <p>
     *  通过调用侦听器a和侦听器b上的windowClosing方法处理windowClosing事件
     * 
     * 
     * @param e the window event
     */
    public void windowClosing(WindowEvent e) {
        ((WindowListener)a).windowClosing(e);
        ((WindowListener)b).windowClosing(e);
    }

    /**
     * Handles the windowClosed event by invoking the
     * windowClosed methods on listener-a and listener-b.
     * <p>
     *  通过调用侦听器a和侦听器b上的windowClosed方法处理windowClosed事件
     * 
     * 
     * @param e the window event
     */
    public void windowClosed(WindowEvent e) {
        ((WindowListener)a).windowClosed(e);
        ((WindowListener)b).windowClosed(e);
    }

    /**
     * Handles the windowIconified event by invoking the
     * windowIconified methods on listener-a and listener-b.
     * <p>
     *  通过调用侦听器a和侦听器b上的windowIconified方法处理windowIconified事件
     * 
     * 
     * @param e the window event
     */
    public void windowIconified(WindowEvent e) {
        ((WindowListener)a).windowIconified(e);
        ((WindowListener)b).windowIconified(e);
    }

    /**
     * Handles the windowDeiconfied event by invoking the
     * windowDeiconified methods on listener-a and listener-b.
     * <p>
     *  通过调用侦听器a和侦听器b上的windowDeiconified方法处理windowDeiconfied事件
     * 
     * 
     * @param e the window event
     */
    public void windowDeiconified(WindowEvent e) {
        ((WindowListener)a).windowDeiconified(e);
        ((WindowListener)b).windowDeiconified(e);
    }

    /**
     * Handles the windowActivated event by invoking the
     * windowActivated methods on listener-a and listener-b.
     * <p>
     *  通过调用listener-a和listener-b上的windowActivated方法处理windowActivated事件
     * 
     * 
     * @param e the window event
     */
    public void windowActivated(WindowEvent e) {
        ((WindowListener)a).windowActivated(e);
        ((WindowListener)b).windowActivated(e);
    }

    /**
     * Handles the windowDeactivated event by invoking the
     * windowDeactivated methods on listener-a and listener-b.
     * <p>
     *  通过调用侦听器a和侦听器b上的windowDeactivated方法处理windowDeactivated事件
     * 
     * 
     * @param e the window event
     */
    public void windowDeactivated(WindowEvent e) {
        ((WindowListener)a).windowDeactivated(e);
        ((WindowListener)b).windowDeactivated(e);
    }

    /**
     * Handles the windowStateChanged event by invoking the
     * windowStateChanged methods on listener-a and listener-b.
     * <p>
     * 通过调用listener-a和listener-b上的windowStateChanged方法处理windowStateChanged事件
     * 
     * 
     * @param e the window event
     * @since 1.4
     */
    public void windowStateChanged(WindowEvent e) {
        ((WindowStateListener)a).windowStateChanged(e);
        ((WindowStateListener)b).windowStateChanged(e);
    }


    /**
     * Handles the windowGainedFocus event by invoking the windowGainedFocus
     * methods on listener-a and listener-b.
     * <p>
     *  通过调用侦听器a和侦听器b上的windowGainedFocus方法处理windowGainedFocus事件
     * 
     * 
     * @param e the window event
     * @since 1.4
     */
    public void windowGainedFocus(WindowEvent e) {
        ((WindowFocusListener)a).windowGainedFocus(e);
        ((WindowFocusListener)b).windowGainedFocus(e);
    }

    /**
     * Handles the windowLostFocus event by invoking the windowLostFocus
     * methods on listener-a and listener-b.
     * <p>
     *  通过调用侦听器a和侦听器b上的windowLostFocus方法处理windowLostFocus事件
     * 
     * 
     * @param e the window event
     * @since 1.4
     */
    public void windowLostFocus(WindowEvent e) {
        ((WindowFocusListener)a).windowLostFocus(e);
        ((WindowFocusListener)b).windowLostFocus(e);
    }

    /**
     * Handles the actionPerformed event by invoking the
     * actionPerformed methods on listener-a and listener-b.
     * <p>
     *  通过调用侦听器a和侦听器b上的actionPerformed方法处理actionPerformed事件
     * 
     * 
     * @param e the action event
     */
    public void actionPerformed(ActionEvent e) {
        ((ActionListener)a).actionPerformed(e);
        ((ActionListener)b).actionPerformed(e);
    }

    /**
     * Handles the itemStateChanged event by invoking the
     * itemStateChanged methods on listener-a and listener-b.
     * <p>
     *  通过调用listener-a和listener-b上的itemStateChanged方法处理itemStateChanged事件
     * 
     * 
     * @param e the item event
     */
    public void itemStateChanged(ItemEvent e) {
        ((ItemListener)a).itemStateChanged(e);
        ((ItemListener)b).itemStateChanged(e);
    }

    /**
     * Handles the adjustmentValueChanged event by invoking the
     * adjustmentValueChanged methods on listener-a and listener-b.
     * <p>
     *  通过调用侦听器a和侦听器b上的adjustValueChanged方法处理adjustValueChanged事件
     * 
     * 
     * @param e the adjustment event
     */
    public void adjustmentValueChanged(AdjustmentEvent e) {
        ((AdjustmentListener)a).adjustmentValueChanged(e);
        ((AdjustmentListener)b).adjustmentValueChanged(e);
    }
    public void textValueChanged(TextEvent e) {
        ((TextListener)a).textValueChanged(e);
        ((TextListener)b).textValueChanged(e);
    }

    /**
     * Handles the inputMethodTextChanged event by invoking the
     * inputMethodTextChanged methods on listener-a and listener-b.
     * <p>
     *  通过调用listener-a和listener-b上的inputMethodTextChanged方法处理inputMethodTextChanged事件
     * 
     * 
     * @param e the item event
     */
    public void inputMethodTextChanged(InputMethodEvent e) {
       ((InputMethodListener)a).inputMethodTextChanged(e);
       ((InputMethodListener)b).inputMethodTextChanged(e);
    }

    /**
     * Handles the caretPositionChanged event by invoking the
     * caretPositionChanged methods on listener-a and listener-b.
     * <p>
     * 通过调用listener-a和listener-b上的caretPositionChanged方法处理caretPositionChanged事件
     * 
     * 
     * @param e the item event
     */
    public void caretPositionChanged(InputMethodEvent e) {
       ((InputMethodListener)a).caretPositionChanged(e);
       ((InputMethodListener)b).caretPositionChanged(e);
    }

    /**
     * Handles the hierarchyChanged event by invoking the
     * hierarchyChanged methods on listener-a and listener-b.
     * <p>
     *  通过调用侦听器a和侦听器b上的hierarchyChanged方法处理hierarchyChanged事件
     * 
     * 
     * @param e the item event
     * @since 1.3
     */
    public void hierarchyChanged(HierarchyEvent e) {
        ((HierarchyListener)a).hierarchyChanged(e);
        ((HierarchyListener)b).hierarchyChanged(e);
    }

    /**
     * Handles the ancestorMoved event by invoking the
     * ancestorMoved methods on listener-a and listener-b.
     * <p>
     *  通过调用listener-a和listener-b上的ancestorMoved方法处理ancestorMoved事件
     * 
     * 
     * @param e the item event
     * @since 1.3
     */
    public void ancestorMoved(HierarchyEvent e) {
        ((HierarchyBoundsListener)a).ancestorMoved(e);
        ((HierarchyBoundsListener)b).ancestorMoved(e);
    }

    /**
     * Handles the ancestorResized event by invoking the
     * ancestorResized methods on listener-a and listener-b.
     * <p>
     *  通过调用listener-a和listener-b上的ancestorResized方法处理ancestorResized事件
     * 
     * 
     * @param e the item event
     * @since 1.3
     */
    public void ancestorResized(HierarchyEvent e) {
        ((HierarchyBoundsListener)a).ancestorResized(e);
        ((HierarchyBoundsListener)b).ancestorResized(e);
    }

    /**
     * Handles the mouseWheelMoved event by invoking the
     * mouseWheelMoved methods on listener-a and listener-b.
     * <p>
     *  通过调用侦听器a和侦听器b上的mouseWheelMoved方法处理mouseWheelMoved事件
     * 
     * 
     * @param e the mouse event
     * @since 1.4
     */
    public void mouseWheelMoved(MouseWheelEvent e) {
        ((MouseWheelListener)a).mouseWheelMoved(e);
        ((MouseWheelListener)b).mouseWheelMoved(e);
    }

    /**
     * Adds component-listener-a with component-listener-b and
     * returns the resulting multicast listener.
     * <p>
     *  将component-listener-a添加到component-listener-b,并返回最终的组播侦听器
     * 
     * 
     * @param a component-listener-a
     * @param b component-listener-b
     */
    public static ComponentListener add(ComponentListener a, ComponentListener b) {
        return (ComponentListener)addInternal(a, b);
    }

    /**
     * Adds container-listener-a with container-listener-b and
     * returns the resulting multicast listener.
     * <p>
     *  将container-listener-a添加到container-listener-b,并返回最终的组播侦听器
     * 
     * 
     * @param a container-listener-a
     * @param b container-listener-b
     */
    public static ContainerListener add(ContainerListener a, ContainerListener b) {
        return (ContainerListener)addInternal(a, b);
    }

    /**
     * Adds focus-listener-a with focus-listener-b and
     * returns the resulting multicast listener.
     * <p>
     * 将focus-listener-a添加到focus-listener-b并返回最终的组播侦听器
     * 
     * 
     * @param a focus-listener-a
     * @param b focus-listener-b
     */
    public static FocusListener add(FocusListener a, FocusListener b) {
        return (FocusListener)addInternal(a, b);
    }

    /**
     * Adds key-listener-a with key-listener-b and
     * returns the resulting multicast listener.
     * <p>
     *  将key-listener-a与key-listener-b相加,并返回最终的组播侦听器
     * 
     * 
     * @param a key-listener-a
     * @param b key-listener-b
     */
    public static KeyListener add(KeyListener a, KeyListener b) {
        return (KeyListener)addInternal(a, b);
    }

    /**
     * Adds mouse-listener-a with mouse-listener-b and
     * returns the resulting multicast listener.
     * <p>
     *  将鼠标监听器a添加到鼠标监听器b并返回最终的组播监听器
     * 
     * 
     * @param a mouse-listener-a
     * @param b mouse-listener-b
     */
    public static MouseListener add(MouseListener a, MouseListener b) {
        return (MouseListener)addInternal(a, b);
    }

    /**
     * Adds mouse-motion-listener-a with mouse-motion-listener-b and
     * returns the resulting multicast listener.
     * <p>
     *  添加鼠标运动监听器a与鼠标运动监听器b并返回结果组播侦听器
     * 
     * 
     * @param a mouse-motion-listener-a
     * @param b mouse-motion-listener-b
     */
    public static MouseMotionListener add(MouseMotionListener a, MouseMotionListener b) {
        return (MouseMotionListener)addInternal(a, b);
    }

    /**
     * Adds window-listener-a with window-listener-b and
     * returns the resulting multicast listener.
     * <p>
     *  使用window-listener-b添加window-listener-a并返回最终的组播侦听器
     * 
     * 
     * @param a window-listener-a
     * @param b window-listener-b
     */
    public static WindowListener add(WindowListener a, WindowListener b) {
        return (WindowListener)addInternal(a, b);
    }

    /**
     * Adds window-state-listener-a with window-state-listener-b
     * and returns the resulting multicast listener.
     * <p>
     *  使用window-state-listener-b添加window-state-listener-a并返回最终的组播侦听器
     * 
     * 
     * @param a window-state-listener-a
     * @param b window-state-listener-b
     * @since 1.4
     */
    public static WindowStateListener add(WindowStateListener a,
                                          WindowStateListener b) {
        return (WindowStateListener)addInternal(a, b);
    }

    /**
     * Adds window-focus-listener-a with window-focus-listener-b
     * and returns the resulting multicast listener.
     * <p>
     *  使用window-focus-listener-b添加window-focus-listener-a并返回最终的组播侦听器
     * 
     * 
     * @param a window-focus-listener-a
     * @param b window-focus-listener-b
     * @since 1.4
     */
    public static WindowFocusListener add(WindowFocusListener a,
                                          WindowFocusListener b) {
        return (WindowFocusListener)addInternal(a, b);
    }

    /**
     * Adds action-listener-a with action-listener-b and
     * returns the resulting multicast listener.
     * <p>
     *  将action-listener-a添加到action-listener-b,并返回最终的组播侦听器
     * 
     * 
     * @param a action-listener-a
     * @param b action-listener-b
     */
    public static ActionListener add(ActionListener a, ActionListener b) {
        return (ActionListener)addInternal(a, b);
    }

    /**
     * Adds item-listener-a with item-listener-b and
     * returns the resulting multicast listener.
     * <p>
     * 将item-listener-a添加到item-listener-b,并返回最终的组播侦听器
     * 
     * 
     * @param a item-listener-a
     * @param b item-listener-b
     */
    public static ItemListener add(ItemListener a, ItemListener b) {
        return (ItemListener)addInternal(a, b);
    }

    /**
     * Adds adjustment-listener-a with adjustment-listener-b and
     * returns the resulting multicast listener.
     * <p>
     *  使用adjust-listener-b添加adjust-listener-a,并返回最终的组播侦听器
     * 
     * 
     * @param a adjustment-listener-a
     * @param b adjustment-listener-b
     */
    public static AdjustmentListener add(AdjustmentListener a, AdjustmentListener b) {
        return (AdjustmentListener)addInternal(a, b);
    }
    public static TextListener add(TextListener a, TextListener b) {
        return (TextListener)addInternal(a, b);
    }

    /**
     * Adds input-method-listener-a with input-method-listener-b and
     * returns the resulting multicast listener.
     * <p>
     *  将input-method-listener-a添加到input-method-listener-b,并返回最终的组播侦听器
     * 
     * 
     * @param a input-method-listener-a
     * @param b input-method-listener-b
     */
     public static InputMethodListener add(InputMethodListener a, InputMethodListener b) {
        return (InputMethodListener)addInternal(a, b);
     }

    /**
     * Adds hierarchy-listener-a with hierarchy-listener-b and
     * returns the resulting multicast listener.
     * <p>
     *  将hierarchy-listener-a添加到hierarchy-listener-b,并返回最终的组播侦听器
     * 
     * 
     * @param a hierarchy-listener-a
     * @param b hierarchy-listener-b
     * @since 1.3
     */
     public static HierarchyListener add(HierarchyListener a, HierarchyListener b) {
        return (HierarchyListener)addInternal(a, b);
     }

    /**
     * Adds hierarchy-bounds-listener-a with hierarchy-bounds-listener-b and
     * returns the resulting multicast listener.
     * <p>
     *  将hierarchy-bounds-listener-a添加到hierarchy-bounds-listener-b,并返回最终的组播侦听器
     * 
     * 
     * @param a hierarchy-bounds-listener-a
     * @param b hierarchy-bounds-listener-b
     * @since 1.3
     */
     public static HierarchyBoundsListener add(HierarchyBoundsListener a, HierarchyBoundsListener b) {
        return (HierarchyBoundsListener)addInternal(a, b);
     }

    /**
     * Adds mouse-wheel-listener-a with mouse-wheel-listener-b and
     * returns the resulting multicast listener.
     * <p>
     *  用鼠标滚轮监听器b添加鼠标滚轮监听器a并返回结果组播监听器
     * 
     * 
     * @param a mouse-wheel-listener-a
     * @param b mouse-wheel-listener-b
     * @since 1.4
     */
    public static MouseWheelListener add(MouseWheelListener a,
                                         MouseWheelListener b) {
        return (MouseWheelListener)addInternal(a, b);
    }

    /**
     * Removes the old component-listener from component-listener-l and
     * returns the resulting multicast listener.
     * <p>
     *  从component-listener-l中删除旧的组件侦听器,并返回最终的组播侦听器
     * 
     * 
     * @param l component-listener-l
     * @param oldl the component-listener being removed
     */
    public static ComponentListener remove(ComponentListener l, ComponentListener oldl) {
        return (ComponentListener) removeInternal(l, oldl);
    }

    /**
     * Removes the old container-listener from container-listener-l and
     * returns the resulting multicast listener.
     * <p>
     * 从container-listener-l中删除旧的容器侦听器,并返回最终的组播侦听器
     * 
     * 
     * @param l container-listener-l
     * @param oldl the container-listener being removed
     */
    public static ContainerListener remove(ContainerListener l, ContainerListener oldl) {
        return (ContainerListener) removeInternal(l, oldl);
    }

    /**
     * Removes the old focus-listener from focus-listener-l and
     * returns the resulting multicast listener.
     * <p>
     *  从focus-listener-l中删除旧的焦点监听器并返回结果组播监听器
     * 
     * 
     * @param l focus-listener-l
     * @param oldl the focus-listener being removed
     */
    public static FocusListener remove(FocusListener l, FocusListener oldl) {
        return (FocusListener) removeInternal(l, oldl);
    }

    /**
     * Removes the old key-listener from key-listener-l and
     * returns the resulting multicast listener.
     * <p>
     *  从key-listener-l删除旧的密钥侦听器并返回最终的组播侦听器
     * 
     * 
     * @param l key-listener-l
     * @param oldl the key-listener being removed
     */
    public static KeyListener remove(KeyListener l, KeyListener oldl) {
        return (KeyListener) removeInternal(l, oldl);
    }

    /**
     * Removes the old mouse-listener from mouse-listener-l and
     * returns the resulting multicast listener.
     * <p>
     *  从mouse-listener-l中删除旧的鼠标监听器,并返回最终的组播监听器
     * 
     * 
     * @param l mouse-listener-l
     * @param oldl the mouse-listener being removed
     */
    public static MouseListener remove(MouseListener l, MouseListener oldl) {
        return (MouseListener) removeInternal(l, oldl);
    }

    /**
     * Removes the old mouse-motion-listener from mouse-motion-listener-l
     * and returns the resulting multicast listener.
     * <p>
     *  从mouse-motion-listener-l中删除旧的鼠标移动侦听器,并返回最终的组播侦听器
     * 
     * 
     * @param l mouse-motion-listener-l
     * @param oldl the mouse-motion-listener being removed
     */
    public static MouseMotionListener remove(MouseMotionListener l, MouseMotionListener oldl) {
        return (MouseMotionListener) removeInternal(l, oldl);
    }

    /**
     * Removes the old window-listener from window-listener-l and
     * returns the resulting multicast listener.
     * <p>
     *  从window-listener-l删除旧的窗口侦听器并返回结果组播侦听器
     * 
     * 
     * @param l window-listener-l
     * @param oldl the window-listener being removed
     */
    public static WindowListener remove(WindowListener l, WindowListener oldl) {
        return (WindowListener) removeInternal(l, oldl);
    }

    /**
     * Removes the old window-state-listener from window-state-listener-l
     * and returns the resulting multicast listener.
     * <p>
     *  从window-state-listener-l删除旧的窗口状态侦听器并返回结果组播侦听器
     * 
     * 
     * @param l window-state-listener-l
     * @param oldl the window-state-listener being removed
     * @since 1.4
     */
    public static WindowStateListener remove(WindowStateListener l,
                                             WindowStateListener oldl) {
        return (WindowStateListener) removeInternal(l, oldl);
    }

    /**
     * Removes the old window-focus-listener from window-focus-listener-l
     * and returns the resulting multicast listener.
     * <p>
     * 从window-focus-listener-l中删除旧的window-focus-listener,并返回最终的组播侦听器
     * 
     * 
     * @param l window-focus-listener-l
     * @param oldl the window-focus-listener being removed
     * @since 1.4
     */
    public static WindowFocusListener remove(WindowFocusListener l,
                                             WindowFocusListener oldl) {
        return (WindowFocusListener) removeInternal(l, oldl);
    }

    /**
     * Removes the old action-listener from action-listener-l and
     * returns the resulting multicast listener.
     * <p>
     *  从action-listener-l中删除旧的动作监听器,并返回最终的组播监听器
     * 
     * 
     * @param l action-listener-l
     * @param oldl the action-listener being removed
     */
    public static ActionListener remove(ActionListener l, ActionListener oldl) {
        return (ActionListener) removeInternal(l, oldl);
    }

    /**
     * Removes the old item-listener from item-listener-l and
     * returns the resulting multicast listener.
     * <p>
     *  从item-listener-l中删除旧的项目监听器,并返回结果组播侦听器
     * 
     * 
     * @param l item-listener-l
     * @param oldl the item-listener being removed
     */
    public static ItemListener remove(ItemListener l, ItemListener oldl) {
        return (ItemListener) removeInternal(l, oldl);
    }

    /**
     * Removes the old adjustment-listener from adjustment-listener-l and
     * returns the resulting multicast listener.
     * <p>
     *  从adjust-listener-l中删除旧的调整监听器并返回结果组播侦听器
     * 
     * 
     * @param l adjustment-listener-l
     * @param oldl the adjustment-listener being removed
     */
    public static AdjustmentListener remove(AdjustmentListener l, AdjustmentListener oldl) {
        return (AdjustmentListener) removeInternal(l, oldl);
    }
    public static TextListener remove(TextListener l, TextListener oldl) {
        return (TextListener) removeInternal(l, oldl);
    }

    /**
     * Removes the old input-method-listener from input-method-listener-l and
     * returns the resulting multicast listener.
     * <p>
     *  从input-method-listener-l中删除旧的输入方法侦听器,并返回最终的组播侦听器
     * 
     * 
     * @param l input-method-listener-l
     * @param oldl the input-method-listener being removed
     */
    public static InputMethodListener remove(InputMethodListener l, InputMethodListener oldl) {
        return (InputMethodListener) removeInternal(l, oldl);
    }

    /**
     * Removes the old hierarchy-listener from hierarchy-listener-l and
     * returns the resulting multicast listener.
     * <p>
     *  从hierarchy-listener-l删除旧的层次结构侦听器并返回结果多播侦听器
     * 
     * 
     * @param l hierarchy-listener-l
     * @param oldl the hierarchy-listener being removed
     * @since 1.3
     */
    public static HierarchyListener remove(HierarchyListener l, HierarchyListener oldl) {
        return (HierarchyListener) removeInternal(l, oldl);
    }

    /**
     * Removes the old hierarchy-bounds-listener from
     * hierarchy-bounds-listener-l and returns the resulting multicast
     * listener.
     * <p>
     *  从hierarchy-bounds-listener-l中删除旧的hierarchy-bounds-listener,并返回最终的组播侦听器
     * 
     * 
     * @param l hierarchy-bounds-listener-l
     * @param oldl the hierarchy-bounds-listener being removed
     * @since 1.3
     */
    public static HierarchyBoundsListener remove(HierarchyBoundsListener l, HierarchyBoundsListener oldl) {
        return (HierarchyBoundsListener) removeInternal(l, oldl);
    }

    /**
     * Removes the old mouse-wheel-listener from mouse-wheel-listener-l
     * and returns the resulting multicast listener.
     * <p>
     * 从mouse-wheel-listener-l中删除旧的鼠标滚轮监听器,并返回最终的组播侦听器
     * 
     * 
     * @param l mouse-wheel-listener-l
     * @param oldl the mouse-wheel-listener being removed
     * @since 1.4
     */
    public static MouseWheelListener remove(MouseWheelListener l,
                                            MouseWheelListener oldl) {
      return (MouseWheelListener) removeInternal(l, oldl);
    }

    /**
     * Returns the resulting multicast listener from adding listener-a
     * and listener-b together.
     * If listener-a is null, it returns listener-b;
     * If listener-b is null, it returns listener-a
     * If neither are null, then it creates and returns
     * a new AWTEventMulticaster instance which chains a with b.
     * <p>
     *  返回结果多播侦听器从添加listener-a和listener-b一起如果listener-a为null,它返回listener-b;如果listener-b为null,它返回listener-a如果
     * 两者都不为null,则它创建并返回一个新的AWTEventMulticaster实例,它将a与a。
     * 
     * 
     * @param a event listener-a
     * @param b event listener-b
     */
    protected static EventListener addInternal(EventListener a, EventListener b) {
        if (a == null)  return b;
        if (b == null)  return a;
        return new AWTEventMulticaster(a, b);
    }

    /**
     * Returns the resulting multicast listener after removing the
     * old listener from listener-l.
     * If listener-l equals the old listener OR listener-l is null,
     * returns null.
     * Else if listener-l is an instance of AWTEventMulticaster,
     * then it removes the old listener from it.
     * Else, returns listener l.
     * <p>
     *  从listener-l中删除旧侦听器后返回结果多播侦听器如果listener-l等于旧侦听器或listener-l为null,则返回null Else如果listener-l是AWTEventMult
     * icaster的实例,则它将从其中删除旧的侦听器否则,返回侦听器l。
     * 
     * 
     * @param l the listener being removed from
     * @param oldl the listener being removed
     */
    protected static EventListener removeInternal(EventListener l, EventListener oldl) {
        if (l == oldl || l == null) {
            return null;
        } else if (l instanceof AWTEventMulticaster) {
            return ((AWTEventMulticaster)l).remove(oldl);
        } else {
            return l;           // it's not here
        }
    }


    /* Serialization support.
    /* <p>
     */

    protected void saveInternal(ObjectOutputStream s, String k) throws IOException {
        if (a instanceof AWTEventMulticaster) {
            ((AWTEventMulticaster)a).saveInternal(s, k);
        }
        else if (a instanceof Serializable) {
            s.writeObject(k);
            s.writeObject(a);
        }

        if (b instanceof AWTEventMulticaster) {
            ((AWTEventMulticaster)b).saveInternal(s, k);
        }
        else if (b instanceof Serializable) {
            s.writeObject(k);
            s.writeObject(b);
        }
    }

    protected static void save(ObjectOutputStream s, String k, EventListener l) throws IOException {
      if (l == null) {
          return;
      }
      else if (l instanceof AWTEventMulticaster) {
          ((AWTEventMulticaster)l).saveInternal(s, k);
      }
      else if (l instanceof Serializable) {
           s.writeObject(k);
           s.writeObject(l);
      }
    }

    /*
     * Recursive method which returns a count of the number of listeners in
     * EventListener, handling the (common) case of l actually being an
     * AWTEventMulticaster.  Additionally, only listeners of type listenerType
     * are counted.  Method modified to fix bug 4513402.  -bchristi
     * <p>
     * 递归方法返回EventListener中监听器数量的计数,处理(通用)情况l实际上是AWTEventMulticaster此外,只有listenerType类型的监听器被计数修改为修复错误4513402
     *  -bchristi。
     * 
     */
    private static int getListenerCount(EventListener l, Class<?> listenerType) {
        if (l instanceof AWTEventMulticaster) {
            AWTEventMulticaster mc = (AWTEventMulticaster)l;
            return getListenerCount(mc.a, listenerType) +
             getListenerCount(mc.b, listenerType);
        }
        else {
            // Only count listeners of correct type
            return listenerType.isInstance(l) ? 1 : 0;
        }
    }

    /*
     * Recusive method which populates EventListener array a with EventListeners
     * from l.  l is usually an AWTEventMulticaster.  Bug 4513402 revealed that
     * if l differed in type from the element type of a, an ArrayStoreException
     * would occur.  Now l is only inserted into a if it's of the appropriate
     * type.  -bchristi
     * <p>
     *  Recusion方法,它填充EventListener数组与EventListeners从l l通常是一个AWTEventMulticaster Bug 4513402揭示如果l不同的类型从a的元素类
     * 型,一个ArrayStoreException将会发生现在l只插入到一个如果它是相应的类型-bchristi。
     * 
     */
    private static int populateListenerArray(EventListener[] a, EventListener l, int index) {
        if (l instanceof AWTEventMulticaster) {
            AWTEventMulticaster mc = (AWTEventMulticaster)l;
            int lhs = populateListenerArray(a, mc.a, index);
            return populateListenerArray(a, mc.b, lhs);
        }
        else if (a.getClass().getComponentType().isInstance(l)) {
            a[index] = l;
            return index + 1;
        }
        // Skip nulls, instances of wrong class
        else {
            return index;
        }
    }

    /**
     * Returns an array of all the objects chained as
     * <code><em>Foo</em>Listener</code>s by the specified
     * <code>java.util.EventListener</code>.
     * <code><em>Foo</em>Listener</code>s are chained by the
     * <code>AWTEventMulticaster</code> using the
     * <code>add<em>Foo</em>Listener</code> method.
     * If a <code>null</code> listener is specified, this method returns an
     * empty array. If the specified listener is not an instance of
     * <code>AWTEventMulticaster</code>, this method returns an array which
     * contains only the specified listener. If no such listeners are chained,
     * this method returns an empty array.
     *
     * <p>
     * 通过指定的<code> javautilEventListener </code> <code> <em> </em>侦听器</em>返回所有链接为<code> <em> </em> / code>通过
     * <code> AWTEventMulticaster </code>使用<code> add <em> Foo </em> Listener </code>方法链接</code>如果指定了<code> 
     * null </code>此方法返回一个空数组如果指定的监听器不是<code> AWTEventMulticaster </code>的实例,此方法将返回一个只包含指定监听器的数组。
     * 
     * @param l the specified <code>java.util.EventListener</code>
     * @param listenerType the type of listeners requested; this parameter
     *          should specify an interface that descends from
     *          <code>java.util.EventListener</code>
     * @return an array of all objects chained as
     *          <code><em>Foo</em>Listener</code>s by the specified multicast
     *          listener, or an empty array if no such listeners have been
     *          chained by the specified multicast listener
     * @exception NullPointerException if the specified
     *             {@code listenertype} parameter is {@code null}
     * @exception ClassCastException if <code>listenerType</code>
     *          doesn't specify a class or interface that implements
     *          <code>java.util.EventListener</code>
     *
     * @since 1.4
     */
    @SuppressWarnings("unchecked")
    public static <T extends EventListener> T[]
        getListeners(EventListener l, Class<T> listenerType)
    {
        if (listenerType == null) {
            throw new NullPointerException ("Listener type should not be null");
        }

        int n = getListenerCount(l, listenerType);
        T[] result = (T[])Array.newInstance(listenerType, n);
        populateListenerArray(result, l, 0);
        return result;
    }
}
