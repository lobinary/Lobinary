/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2009, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing;

import sun.awt.AWTAccessor;

import javax.swing.plaf.LayerUI;
import javax.swing.border.Border;
import javax.accessibility.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * {@code JLayer} is a universal decorator for Swing components
 * which enables you to implement various advanced painting effects as well as
 * receive notifications of all {@code AWTEvent}s generated within its borders.
 * <p>
 * {@code JLayer} delegates the handling of painting and input events to a
 * {@link javax.swing.plaf.LayerUI} object, which performs the actual decoration.
 * <p>
 * The custom painting implemented in the {@code LayerUI} and events notification
 * work for the JLayer itself and all its subcomponents.
 * This combination enables you to enrich existing components
 * by adding new advanced functionality such as temporary locking of a hierarchy,
 * data tips for compound components, enhanced mouse scrolling etc and so on.
 * <p>
 * {@code JLayer} is a good solution if you only need to do custom painting
 * over compound component or catch input events from its subcomponents.
 * <pre>
 * import javax.swing.*;
 * import javax.swing.plaf.LayerUI;
 * import java.awt.*;
 *
 * public class JLayerSample {
 *
 *     private static JLayer&lt;JComponent&gt; createLayer() {
 *         // This custom layerUI will fill the layer with translucent green
 *         // and print out all mouseMotion events generated within its borders
 *         LayerUI&lt;JComponent&gt; layerUI = new LayerUI&lt;JComponent&gt;() {
 *
 *             public void paint(Graphics g, JComponent c) {
 *                 // paint the layer as is
 *                 super.paint(g, c);
 *                 // fill it with the translucent green
 *                 g.setColor(new Color(0, 128, 0, 128));
 *                 g.fillRect(0, 0, c.getWidth(), c.getHeight());
 *             }
 *
 *             public void installUI(JComponent c) {
 *                 super.installUI(c);
 *                 // enable mouse motion events for the layer's subcomponents
 *                 ((JLayer) c).setLayerEventMask(AWTEvent.MOUSE_MOTION_EVENT_MASK);
 *             }
 *
 *             public void uninstallUI(JComponent c) {
 *                 super.uninstallUI(c);
 *                 // reset the layer event mask
 *                 ((JLayer) c).setLayerEventMask(0);
 *             }
 *
 *             // overridden method which catches MouseMotion events
 *             public void eventDispatched(AWTEvent e, JLayer&lt;? extends JComponent&gt; l) {
 *                 System.out.println("AWTEvent detected: " + e);
 *             }
 *         };
 *         // create a component to be decorated with the layer
 *         JPanel panel = new JPanel();
 *         panel.add(new JButton("JButton"));
 *
 *         // create the layer for the panel using our custom layerUI
 *         return new JLayer&lt;JComponent&gt;(panel, layerUI);
 *     }
 *
 *     private static void createAndShowGUI() {
 *         final JFrame frame = new JFrame();
 *         frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 *
 *         // work with the layer as with any other Swing component
 *         frame.add(createLayer());
 *
 *         frame.setSize(200, 200);
 *         frame.setLocationRelativeTo(null);
 *         frame.setVisible(true);
 *     }
 *
 *     public static void main(String[] args) throws Exception {
 *         SwingUtilities.invokeAndWait(new Runnable() {
 *             public void run() {
 *                 createAndShowGUI();
 *             }
 *         });
 *     }
 * }
 * </pre>
 *
 * <b>Note:</b> {@code JLayer} doesn't support the following methods:
 * <ul>
 * <li>{@link Container#add(java.awt.Component)}</li>
 * <li>{@link Container#add(String, java.awt.Component)}</li>
 * <li>{@link Container#add(java.awt.Component, int)}</li>
 * <li>{@link Container#add(java.awt.Component, Object)}</li>
 * <li>{@link Container#add(java.awt.Component, Object, int)}</li>
 * </ul>
 * using any of of them will cause {@code UnsupportedOperationException} to be thrown,
 * to add a component to {@code JLayer}
 * use {@link #setView(Component)} or {@link #setGlassPane(JPanel)}.
 *
 * <p>
 *  {@code JLayer}是Swing组件的通用装饰器,使您能够实现各种高级绘画效果,以及接收在其边框内生成的所有{@code AWTEvent}的通知。
 * <p>
 *  {@code JLayer}将绘画和输入事件的处理委托给{@link javax.swing.plaf.LayerUI}对象,该对象执行实际的装饰。
 * <p>
 *  在{@code LayerUI}和事件通知中实现的自定义绘画适用于JLayer本身及其所有子组件。
 * 此组合使您能够通过添加新的高级功能(例如临时锁定层次结构,复合组件的数据提示,增强的鼠标滚动等)来丰富现有组件。
 * <p>
 *  {@code JLayer}是一个很好的解决方案,如果你只需要对复合组件进行自定义绘制或从其子组件捕获输入事件。
 * <pre>
 *  import javax.swing。*; import javax.swing.plaf.LayerUI; import java.awt。*;
 * 
 *  public class JLayerSample {
 * 
 *  private static JLayer&lt; JComponent&gt; createLayer(){//这个自定义layerUI将使用半透明的绿色填充图层//并打印所有在其边界内生成的mouseMotion事件LayerUI&lt; JComponent&gt; layerUI = new LayerUI&lt; JComponent&gt;(){。
 * 
 * public void paint(Graphics g,JComponent c){//将图层绘制为super.paint(g,c); //填充它的半透明绿色g.setColor(new Color(0,128,0,128)); g.fillRect(0,0,c.getWidth(),c.getHeight()); }
 * }。
 * 
 *  public void installUI(JComponent c){super.installUI(c); //为图层的子组件((JLayer)启用鼠标运动事件c).setLayerEventMask(AWTEvent.MOUSE_MOTION_EVENT_MASK); }
 * }。
 * 
 *  public void uninstallUI(JComponent c){super.uninstallUI(c); // reset the layer event mask((JLayer)c).setLayerEventMask(0); }
 * }。
 * 
 *  //覆盖MouseMotion事件的重写方法public void eventDispatched(AWTEvent e,JLayer&lt ;? extends JComponent&gt; 1){System.out.println("AWTEvent detected："+ e); }
 * }; //创建要用图层装饰的组件JPanel panel = new JPanel(); panel.add(new JButton("JButton"));。
 * 
 *  //使用我们的自定义layerUI创建面板的图层return new JLayer&lt; JComponent&gt;(panel,layerUI); }}
 * 
 *  private static void createAndShowGUI(){final JFrame frame = new JFrame(); frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);。
 * 
 *  //与任何其他Swing组件一样使用该图层frame.add(createLayer());
 * 
 *  frame.setSize(200,200); frame.setLocationRelativeTo(null); frame.setVisible(true); }}
 * 
 *  public static void main(String [] args)throws Exception {SwingUtilities.invokeAndWait(new Runnable(){public void run(){createAndShowGUI();}
 * }); }}。
 * </pre>
 * 
 *  <b>注意：</b> {@code JLayer}不支持以下方法：
 * <ul>
 * <li> {@ link Container#add(java.awt.Component)} </li> <li> {@ link Container#add(String,java.awt.Component)}
 *  </li> <li> {@ link Container#add(java.awt.Component,int)} </li> <li> {@ link Container#add(java.awt.Component,Object)}
 *  </li> <li> {@ link Container#add java.awt.Component,Object,int)} </li>。
 * </ul>
 *  使用其中任何一个将导致{@code UnsupportedOperationException}被抛出,向{@code JLayer}使用{@link #setView(Component)}或{@link #setGlassPane(JPanel)}
 * 添加组件。
 * 
 * 
 * @param <V> the type of {@code JLayer}'s view component
 *
 * @see #JLayer(Component)
 * @see #setView(Component)
 * @see #getView()
 * @see javax.swing.plaf.LayerUI
 * @see #JLayer(Component, LayerUI)
 * @see #setUI(javax.swing.plaf.LayerUI)
 * @see #getUI()
 * @since 1.7
 *
 * @author Alexander Potochkin
 */
public final class JLayer<V extends Component>
        extends JComponent
        implements Scrollable, PropertyChangeListener, Accessible {
    private V view;
    // this field is necessary because JComponent.ui is transient
    // when layerUI is serializable
    private LayerUI<? super V> layerUI;
    private JPanel glassPane;
    private long eventMask;
    private transient boolean isPainting;
    private transient boolean isPaintingImmediately;

    private static final LayerEventController eventController =
            new LayerEventController();

    /**
     * Creates a new {@code JLayer} object with a {@code null} view component
     * and default {@link javax.swing.plaf.LayerUI}.
     *
     * <p>
     *  使用{@code null}视图组件和默认{@link javax.swing.plaf.LayerUI}创建一个新的{@code JLayer}对象。
     * 
     * 
     * @see #setView
     * @see #setUI
     */
    public JLayer() {
        this(null);
    }

    /**
     * Creates a new {@code JLayer} object
     * with default {@link javax.swing.plaf.LayerUI}.
     *
     * <p>
     *  使用默认{@link javax.swing.plaf.LayerUI}创建一个新的{@code JLayer}对象。
     * 
     * 
     * @param view the component to be decorated by this {@code JLayer}
     *
     * @see #setUI
     */
    public JLayer(V view) {
        this(view, new LayerUI<V>());
    }

    /**
     * Creates a new {@code JLayer} object with the specified view component
     * and {@link javax.swing.plaf.LayerUI} object.
     *
     * <p>
     *  使用指定的视图组件和{@link javax.swing.plaf.LayerUI}对象创建新的{@code JLayer}对象。
     * 
     * 
     * @param view the component to be decorated
     * @param ui the {@link javax.swing.plaf.LayerUI} delegate
     * to be used by this {@code JLayer}
     */
    public JLayer(V view, LayerUI<V> ui) {
        setGlassPane(createGlassPane());
        setView(view);
        setUI(ui);
    }

    /**
     * Returns the {@code JLayer}'s view component or {@code null}.
     * <br>This is a bound property.
     *
     * <p>
     *  返回{@code JLayer}的视图组件或{@code null}。 <br>这是一个bound属性。
     * 
     * 
     * @return the {@code JLayer}'s view component
     *         or {@code null} if none exists
     *
     * @see #setView(Component)
     */
    public V getView() {
        return view;
    }

    /**
     * Sets the {@code JLayer}'s view component, which can be {@code null}.
     * <br>This is a bound property.
     *
     * <p>
     *  设置{@code JLayer}的视图组件,它可以是{@code null}。 <br>这是一个bound属性。
     * 
     * 
     * @param view the view component for this {@code JLayer}
     *
     * @see #getView()
     */
    public void setView(V view) {
        Component oldView = getView();
        if (oldView != null) {
            super.remove(oldView);
        }
        if (view != null) {
            super.addImpl(view, null, getComponentCount());
        }
        this.view = view;
        firePropertyChange("view", oldView, view);
        revalidate();
        repaint();
    }

    /**
     * Sets the {@link javax.swing.plaf.LayerUI} which will perform painting
     * and receive input events for this {@code JLayer}.
     *
     * <p>
     *  设置{@link javax.swing.plaf.LayerUI},它将为此{@code JLayer}执行绘制和接收输入事件。
     * 
     * 
     * @param ui the {@link javax.swing.plaf.LayerUI} for this {@code JLayer}
     */
    public void setUI(LayerUI<? super V> ui) {
        this.layerUI = ui;
        super.setUI(ui);
    }

    /**
     * Returns the {@link javax.swing.plaf.LayerUI} for this {@code JLayer}.
     *
     * <p>
     *  返回此{@code JLayer}的{@link javax.swing.plaf.LayerUI}。
     * 
     * 
     * @return the {@code LayerUI} for this {@code JLayer}
     */
    public LayerUI<? super V> getUI() {
        return layerUI;
    }

    /**
     * Returns the {@code JLayer}'s glassPane component or {@code null}.
     * <br>This is a bound property.
     *
     * <p>
     *  返回{@code JLayer}的glassPane组件或{@code null}。 <br>这是一个bound属性。
     * 
     * 
     * @return the {@code JLayer}'s glassPane component
     *         or {@code null} if none exists
     *
     * @see #setGlassPane(JPanel)
     */
    public JPanel getGlassPane() {
        return glassPane;
    }

    /**
     * Sets the {@code JLayer}'s glassPane component, which can be {@code null}.
     * <br>This is a bound property.
     *
     * <p>
     *  设置{@code JLayer}的glassPane组件,可以是{@code null}。 <br>这是一个bound属性。
     * 
     * 
     * @param glassPane the glassPane component of this {@code JLayer}
     *
     * @see #getGlassPane()
     */
    public void setGlassPane(JPanel glassPane) {
        Component oldGlassPane = getGlassPane();
        boolean isGlassPaneVisible = false;
        if (oldGlassPane != null) {
            isGlassPaneVisible = oldGlassPane.isVisible();
            super.remove(oldGlassPane);
        }
        if (glassPane != null) {
            AWTAccessor.getComponentAccessor().setMixingCutoutShape(glassPane,
                    new Rectangle());
            glassPane.setVisible(isGlassPaneVisible);
            super.addImpl(glassPane, null, 0);
        }
        this.glassPane = glassPane;
        firePropertyChange("glassPane", oldGlassPane, glassPane);
        revalidate();
        repaint();
    }

    /**
     * Called by the constructor methods to create a default {@code glassPane}.
     * By default this method creates a new JPanel with visibility set to true
     * and opacity set to false.
     *
     * <p>
     * 通过构造方法调用来创建一个默认的{@code glassPane}。默认情况下,此方法创建一个新的JPanel,其visibility设置为true,opacity设置为false。
     * 
     * 
     * @return the default {@code glassPane}
     */
    public JPanel createGlassPane() {
        return new DefaultLayerGlassPane();
    }

    /**
     * Sets the layout manager for this container.  This method is
     * overridden to prevent the layout manager from being set.
     * <p>Note:  If {@code mgr} is non-{@code null}, this
     * method will throw an exception as layout managers are not supported on
     * a {@code JLayer}.
     *
     * <p>
     *  设置此容器的布局管理器。将覆盖此方法以防止布局管理器设置。
     *  <p>注意：如果{@code mgr}不是{@ code null},此方法将会抛出异常,因为{@code JLayer}不支持布局管理器。
     * 
     * 
     * @param mgr the specified layout manager
     * @exception IllegalArgumentException this method is not supported
     */
    public void setLayout(LayoutManager mgr) {
        if (mgr != null) {
            throw new IllegalArgumentException("JLayer.setLayout() not supported");
        }
    }

    /**
     * A non-{@code null} border, or non-zero insets, isn't supported, to prevent the geometry
     * of this component from becoming complex enough to inhibit
     * subclassing of {@code LayerUI} class.  To create a {@code JLayer} with a border,
     * add it to a {@code JPanel} that has a border.
     * <p>Note:  If {@code border} is non-{@code null}, this
     * method will throw an exception as borders are not supported on
     * a {@code JLayer}.
     *
     * <p>
     *  不支持非 -  {@ code null}边框或非零插入,以防止此组件的几何形状变得足够复杂,无法阻止{@code LayerUI}类的子类化。
     * 要创建带有边框的{@code JLayer},请将其添加到具有边框的{@code JPanel}。
     *  <p>注意：如果{@code border}不是{@ code null},此方法将会抛出异常,因为{@code JLayer}不支持边框。
     * 
     * 
     * @param border the {@code Border} to set
     * @exception IllegalArgumentException this method is not supported
     */
    public void setBorder(Border border) {
        if (border != null) {
            throw new IllegalArgumentException("JLayer.setBorder() not supported");
        }
    }

    /**
     * This method is not supported by {@code JLayer}
     * and always throws {@code UnsupportedOperationException}
     *
     * <p>
     *  {@code JLayer}不支持此方法,并始终抛出{@code UnsupportedOperationException}
     * 
     * 
     * @throws UnsupportedOperationException this method is not supported
     *
     * @see #setView(Component)
     * @see #setGlassPane(JPanel)
     */
    protected void addImpl(Component comp, Object constraints, int index) {
        throw new UnsupportedOperationException(
                "Adding components to JLayer is not supported, " +
                        "use setView() or setGlassPane() instead");
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void remove(Component comp) {
        if (comp == null) {
            super.remove(comp);
        } else if (comp == getView()) {
            setView(null);
        } else if (comp == getGlassPane()) {
            setGlassPane(null);
        } else {
            super.remove(comp);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void removeAll() {
        if (view != null) {
            setView(null);
        }
        if (glassPane != null) {
            setGlassPane(null);
        }
    }

    /**
     * Always returns {@code true} to cause painting to originate from {@code JLayer},
     * or one of its ancestors.
     *
     * <p>
     *  始终返回{@code true},以使绘画来自{@code JLayer}或其祖先之一。
     * 
     * 
     * @return true
     * @see JComponent#isPaintingOrigin()
     */
    protected boolean isPaintingOrigin() {
        return true;
    }

    /**
     * Delegates its functionality to the
     * {@link javax.swing.plaf.LayerUI#paintImmediately(int, int, int, int, JLayer)} method,
     * if {@code LayerUI} is set.
     *
     * <p>
     *  将其功能委托给{@link javax.swing.plaf.LayerUI#paintImmediately(int,int,int,int,JLayer)}方法,如果设置了{@code LayerUI}
     * 。
     * 
     * 
     * @param x  the x value of the region to be painted
     * @param y  the y value of the region to be painted
     * @param w  the width of the region to be painted
     * @param h  the height of the region to be painted
     */
    public void paintImmediately(int x, int y, int w, int h) {
        if (!isPaintingImmediately && getUI() != null) {
            isPaintingImmediately = true;
            try {
                getUI().paintImmediately(x, y, w, h, this);
            } finally {
                isPaintingImmediately = false;
            }
        } else {
            super.paintImmediately(x, y, w, h);
        }
    }

    /**
     * Delegates all painting to the {@link javax.swing.plaf.LayerUI} object.
     *
     * <p>
     *  将所有绘画委托给{@link javax.swing.plaf.LayerUI}对象。
     * 
     * 
     * @param g the {@code Graphics} to render to
     */
    public void paint(Graphics g) {
        if (!isPainting) {
            isPainting = true;
            try {
                super.paintComponent(g);
            } finally {
                isPainting = false;
            }
        } else {
            super.paint(g);
        }
    }

    /**
     * This method is empty, because all painting is done by
     * {@link #paint(Graphics)} and
     * {@link javax.swing.plaf.LayerUI#update(Graphics, JComponent)} methods
     * <p>
     *  此方法为空,因为所有绘制都是通过{@link #paint(Graphics)}和{@link javax.swing.plaf.LayerUI#update(Graphics,JComponent)}
     * 方法完成的。
     * 
     */
    protected void paintComponent(Graphics g) {
    }

    /**
     * The {@code JLayer} overrides the default implementation of
     * this method (in {@code JComponent}) to return {@code false}.
     * This ensures
     * that the drawing machinery will call the {@code JLayer}'s
     * {@code paint}
     * implementation rather than messaging the {@code JLayer}'s
     * children directly.
     *
     * <p>
     * {@code JLayer}覆盖此方法的默认实现(在{@code JComponent}中)以返回{@code false}。
     * 这可以确保绘图机制调用{@code JLayer}的{@code paint}实现,而不是直接通知{@code JLayer}的孩子。
     * 
     * 
     * @return false
     */
    public boolean isOptimizedDrawingEnabled() {
        return false;
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if (getUI() != null) {
            getUI().applyPropertyChange(evt, this);
        }
    }

    /**
     * Enables the events from JLayer and <b>all its descendants</b>
     * defined by the specified event mask parameter
     * to be delivered to the
     * {@link LayerUI#eventDispatched(AWTEvent, JLayer)} method.
     * <p>
     * Events are delivered provided that {@code LayerUI} is set
     * for this {@code JLayer} and the {@code JLayer}
     * is displayable.
     * <p>
     * The following example shows how to correctly use this method
     * in the {@code LayerUI} implementations:
     * <pre>
     *    public void installUI(JComponent c) {
     *       super.installUI(c);
     *       JLayer l = (JLayer) c;
     *       // this LayerUI will receive only key and focus events
     *       l.setLayerEventMask(AWTEvent.KEY_EVENT_MASK | AWTEvent.FOCUS_EVENT_MASK);
     *    }
     *
     *    public void uninstallUI(JComponent c) {
     *       super.uninstallUI(c);
     *       JLayer l = (JLayer) c;
     *       // JLayer must be returned to its initial state
     *       l.setLayerEventMask(0);
     *    }
     * </pre>
     *
     * By default {@code JLayer} receives no events and its event mask is {@code 0}.
     *
     * <p>
     *  将来自JLayer的事件和由指定的事件掩码参数定义的<b>其所有后代</b>的事件传递到{@link LayerUI#eventDispatched(AWTEvent,JLayer)}方法。
     * <p>
     *  只要{@code JLayer}设置为{@code LayerUI},且可显示{@code JLayer},系统就会投放活动。
     * <p>
     *  以下示例说明如何在{@code LayerUI}实现中正确使用此方法：
     * <pre>
     *  public void installUI(JComponent c){super.installUI(c); JLayer l =(JLayer)c; //这个LayerUI将只接收键和焦点事件l.setLayerEventMask(AWTEvent.KEY_EVENT_MASK | AWTEvent.FOCUS_EVENT_MASK); }
     * }。
     * 
     *  public void uninstallUI(JComponent c){super.uninstallUI(c); JLayer l =(JLayer)c; // JLayer必须返回到其初始状态l.setLayerEventMask(0); }
     * }。
     * </pre>
     * 
     *  默认情况下,{@code JLayer}不接收事件,其事件掩码为{@code 0}。
     * 
     * 
     * @param layerEventMask the bitmask of event types to receive
     *
     * @see #getLayerEventMask()
     * @see LayerUI#eventDispatched(AWTEvent, JLayer)
     * @see Component#isDisplayable()
     */
    public void setLayerEventMask(long layerEventMask) {
        long oldEventMask = getLayerEventMask();
        this.eventMask = layerEventMask;
        firePropertyChange("layerEventMask", oldEventMask, layerEventMask);
        if (layerEventMask != oldEventMask) {
            disableEvents(oldEventMask);
            enableEvents(eventMask);
            if (isDisplayable()) {
                eventController.updateAWTEventListener(
                        oldEventMask, layerEventMask);
            }
        }
    }

    /**
     * Returns the bitmap of event mask to receive by this {@code JLayer}
     * and its {@code LayerUI}.
     * <p>
     * It means that {@link javax.swing.plaf.LayerUI#eventDispatched(AWTEvent, JLayer)} method
     * will only receive events that match the event mask.
     * <p>
     * By default {@code JLayer} receives no events.
     *
     * <p>
     *  返回此{@code JLayer}及其{@code LayerUI}接收的事件掩码的位图。
     * <p>
     *  这意味着{@link javax.swing.plaf.LayerUI#eventDispatched(AWTEvent,JLayer)}方法将只接收与事件掩码匹配的事件。
     * <p>
     *  默认情况下,{@code JLayer}不接收事件。
     * 
     * 
     * @return the bitmask of event types to receive for this {@code JLayer}
     */
    public long getLayerEventMask() {
        return eventMask;
    }

    /**
     * Delegates its functionality to the {@link javax.swing.plaf.LayerUI#updateUI(JLayer)} method,
     * if {@code LayerUI} is set.
     * <p>
     * 如果设置了{@code LayerUI},则将其功能委托给{@link javax.swing.plaf.LayerUI#updateUI(JLayer)}方法。
     * 
     */
    public void updateUI() {
        if (getUI() != null) {
            getUI().updateUI(this);
        }
    }

    /**
     * Returns the preferred size of the viewport for a view component.
     * <p>
     * If the view component of this layer implements {@link Scrollable}, this method delegates its
     * implementation to the view component.
     *
     * <p>
     *  返回视图组件的视口的首选大小。
     * <p>
     *  如果此层的视图组件实现{@link Scrollable},此方法将其实现委派给视图组件。
     * 
     * 
     * @return the preferred size of the viewport for a view component
     *
     * @see Scrollable
     */
    public Dimension getPreferredScrollableViewportSize() {
        if (getView() instanceof Scrollable) {
            return ((Scrollable)getView()).getPreferredScrollableViewportSize();
        }
        return getPreferredSize();
    }

    /**
     * Returns a scroll increment, which is required for components
     * that display logical rows or columns in order to completely expose
     * one block of rows or columns, depending on the value of orientation.
     * <p>
     * If the view component of this layer implements {@link Scrollable}, this method delegates its
     * implementation to the view component.
     *
     * <p>
     *  返回滚动增量,对于显示逻辑行或列的组件,根据定向值,完全显示一行或多列的块是必需的。
     * <p>
     *  如果此层的视图组件实现{@link Scrollable},此方法将其实现委派给视图组件。
     * 
     * 
     * @return the "block" increment for scrolling in the specified direction
     *
     * @see Scrollable
     */
    public int getScrollableBlockIncrement(Rectangle visibleRect,
                                           int orientation, int direction) {
        if (getView() instanceof Scrollable) {
            return ((Scrollable)getView()).getScrollableBlockIncrement(visibleRect,
                    orientation, direction);
        }
        return (orientation == SwingConstants.VERTICAL) ? visibleRect.height :
                visibleRect.width;
    }

    /**
     * Returns {@code false} to indicate that the height of the viewport does not
     * determine the height of the layer, unless the preferred height
     * of the layer is smaller than the height of the viewport.
     * <p>
     * If the view component of this layer implements {@link Scrollable}, this method delegates its
     * implementation to the view component.
     *
     * <p>
     *  返回{@code false}以指示视口的高度不确定图层的高度,除非图层的首选高度小于视口的高度。
     * <p>
     *  如果此层的视图组件实现{@link Scrollable},此方法将其实现委派给视图组件。
     * 
     * 
     * @return whether the layer should track the height of the viewport
     *
     * @see Scrollable
     */
    public boolean getScrollableTracksViewportHeight() {
        if (getView() instanceof Scrollable) {
            return ((Scrollable)getView()).getScrollableTracksViewportHeight();
        }
        return false;
    }

    /**
     * Returns {@code false} to indicate that the width of the viewport does not
     * determine the width of the layer, unless the preferred width
     * of the layer is smaller than the width of the viewport.
     * <p>
     * If the view component of this layer implements {@link Scrollable}, this method delegates its
     * implementation to the view component.
     *
     * <p>
     *  返回{@code false}以指示视口的宽度不确定图层的宽度,除非图层的首选宽度小于视口的宽度。
     * <p>
     *  如果此层的视图组件实现{@link Scrollable},此方法将其实现委派给视图组件。
     * 
     * 
     * @return whether the layer should track the width of the viewport
     *
     * @see Scrollable
     */
    public boolean getScrollableTracksViewportWidth() {
        if (getView() instanceof Scrollable) {
            return ((Scrollable)getView()).getScrollableTracksViewportWidth();
        }
        return false;
    }

    /**
     * Returns a scroll increment, which is required for components
     * that display logical rows or columns in order to completely expose
     * one new row or column, depending on the value of orientation.
     * Ideally, components should handle a partially exposed row or column
     * by returning the distance required to completely expose the item.
     * <p>
     * Scrolling containers, like {@code JScrollPane}, will use this method
     * each time the user requests a unit scroll.
     * <p>
     * If the view component of this layer implements {@link Scrollable}, this method delegates its
     * implementation to the view component.
     *
     * <p>
     * 返回滚动增量,这对于显示逻辑行或列的组件是必需的,以便根据定向值完全公开一个新行或列。理想情况下,组件应通过返回完全暴露项目所需的距离来处理部分暴露的行或列。
     * <p>
     *  滚动容器(如{@code JScrollPane})将在每次用户请求单元滚动时使用此方法。
     * <p>
     *  如果此层的视图组件实现{@link Scrollable},此方法将其实现委派给视图组件。
     * 
     * 
     * @return The "unit" increment for scrolling in the specified direction.
     *         This value should always be positive.
     *
     * @see Scrollable
     */
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation,
                                          int direction) {
        if (getView() instanceof Scrollable) {
            return ((Scrollable) getView()).getScrollableUnitIncrement(
                    visibleRect, orientation, direction);
        }
        return 1;
    }

    private void readObject(ObjectInputStream s)
            throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        if (layerUI != null) {
            setUI(layerUI);
        }
        if (eventMask != 0) {
            eventController.updateAWTEventListener(0, eventMask);
        }
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void addNotify() {
        super.addNotify();
        eventController.updateAWTEventListener(0, eventMask);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public void removeNotify() {
        super.removeNotify();
        eventController.updateAWTEventListener(eventMask, 0);
    }

    /**
     * Delegates its functionality to the {@link javax.swing.plaf.LayerUI#doLayout(JLayer)} method,
     * if {@code LayerUI} is set.
     * <p>
     *  如果设置了{@code LayerUI},则将其功能委托给{@link javax.swing.plaf.LayerUI#doLayout(JLayer)}方法。
     * 
     */
    public void doLayout() {
        if (getUI() != null) {
            getUI().doLayout(this);
        }
    }

    /**
     * Gets the AccessibleContext associated with this {@code JLayer}.
     *
     * <p>
     *  获取与此{@code JLayer}相关联的AccessibleContext。
     * 
     * 
     * @return the AccessibleContext associated with this {@code JLayer}.
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJComponent() {
                public AccessibleRole getAccessibleRole() {
                    return AccessibleRole.PANEL;
                }
            };
        }
        return accessibleContext;
    }

    /**
     * static AWTEventListener to be shared with all AbstractLayerUIs
     * <p>
     *  static AWTEventListener与所有AbstractLayerUI共享
     * 
     */
    private static class LayerEventController implements AWTEventListener {
        private ArrayList<Long> layerMaskList =
                new ArrayList<Long>();

        private long currentEventMask;

        private static final long ACCEPTED_EVENTS =
                AWTEvent.COMPONENT_EVENT_MASK |
                        AWTEvent.CONTAINER_EVENT_MASK |
                        AWTEvent.FOCUS_EVENT_MASK |
                        AWTEvent.KEY_EVENT_MASK |
                        AWTEvent.MOUSE_WHEEL_EVENT_MASK |
                        AWTEvent.MOUSE_MOTION_EVENT_MASK |
                        AWTEvent.MOUSE_EVENT_MASK |
                        AWTEvent.INPUT_METHOD_EVENT_MASK |
                        AWTEvent.HIERARCHY_EVENT_MASK |
                        AWTEvent.HIERARCHY_BOUNDS_EVENT_MASK;

        @SuppressWarnings("unchecked")
        public void eventDispatched(AWTEvent event) {
            Object source = event.getSource();
            if (source instanceof Component) {
                Component component = (Component) source;
                while (component != null) {
                    if (component instanceof JLayer) {
                        JLayer l = (JLayer) component;
                        LayerUI ui = l.getUI();
                        if (ui != null &&
                                isEventEnabled(l.getLayerEventMask(), event.getID()) &&
                                (!(event instanceof InputEvent) || !((InputEvent)event).isConsumed())) {
                            ui.eventDispatched(event, l);
                        }
                    }
                    component = component.getParent();
                }
            }
        }

        private void updateAWTEventListener(long oldEventMask, long newEventMask) {
            if (oldEventMask != 0) {
                layerMaskList.remove(oldEventMask);
            }
            if (newEventMask != 0) {
                layerMaskList.add(newEventMask);
            }
            long combinedMask = 0;
            for (Long mask : layerMaskList) {
                combinedMask |= mask;
            }
            // filter out all unaccepted events
            combinedMask &= ACCEPTED_EVENTS;
            if (combinedMask == 0) {
                removeAWTEventListener();
            } else if (getCurrentEventMask() != combinedMask) {
                removeAWTEventListener();
                addAWTEventListener(combinedMask);
            }
            currentEventMask = combinedMask;
        }

        private long getCurrentEventMask() {
            return currentEventMask;
        }

        private void addAWTEventListener(final long eventMask) {
            AccessController.doPrivileged(new PrivilegedAction<Void>() {
                public Void run() {
                    Toolkit.getDefaultToolkit().
                            addAWTEventListener(LayerEventController.this, eventMask);
                    return null;
                }
            });

        }

        private void removeAWTEventListener() {
            AccessController.doPrivileged(new PrivilegedAction<Void>() {
                public Void run() {
                    Toolkit.getDefaultToolkit().
                            removeAWTEventListener(LayerEventController.this);
                    return null;
                }
            });
        }

        private boolean isEventEnabled(long eventMask, int id) {
            return (((eventMask & AWTEvent.COMPONENT_EVENT_MASK) != 0 &&
                    id >= ComponentEvent.COMPONENT_FIRST &&
                    id <= ComponentEvent.COMPONENT_LAST)
                    || ((eventMask & AWTEvent.CONTAINER_EVENT_MASK) != 0 &&
                    id >= ContainerEvent.CONTAINER_FIRST &&
                    id <= ContainerEvent.CONTAINER_LAST)
                    || ((eventMask & AWTEvent.FOCUS_EVENT_MASK) != 0 &&
                    id >= FocusEvent.FOCUS_FIRST &&
                    id <= FocusEvent.FOCUS_LAST)
                    || ((eventMask & AWTEvent.KEY_EVENT_MASK) != 0 &&
                    id >= KeyEvent.KEY_FIRST &&
                    id <= KeyEvent.KEY_LAST)
                    || ((eventMask & AWTEvent.MOUSE_WHEEL_EVENT_MASK) != 0 &&
                    id == MouseEvent.MOUSE_WHEEL)
                    || ((eventMask & AWTEvent.MOUSE_MOTION_EVENT_MASK) != 0 &&
                    (id == MouseEvent.MOUSE_MOVED ||
                            id == MouseEvent.MOUSE_DRAGGED))
                    || ((eventMask & AWTEvent.MOUSE_EVENT_MASK) != 0 &&
                    id != MouseEvent.MOUSE_MOVED &&
                    id != MouseEvent.MOUSE_DRAGGED &&
                    id != MouseEvent.MOUSE_WHEEL &&
                    id >= MouseEvent.MOUSE_FIRST &&
                    id <= MouseEvent.MOUSE_LAST)
                    || ((eventMask & AWTEvent.INPUT_METHOD_EVENT_MASK) != 0 &&
                    id >= InputMethodEvent.INPUT_METHOD_FIRST &&
                    id <= InputMethodEvent.INPUT_METHOD_LAST)
                    || ((eventMask & AWTEvent.HIERARCHY_EVENT_MASK) != 0 &&
                    id == HierarchyEvent.HIERARCHY_CHANGED)
                    || ((eventMask & AWTEvent.HIERARCHY_BOUNDS_EVENT_MASK) != 0 &&
                    (id == HierarchyEvent.ANCESTOR_MOVED ||
                            id == HierarchyEvent.ANCESTOR_RESIZED)));
        }
    }

    /**
     * The default glassPane for the {@link javax.swing.JLayer}.
     * It is a subclass of {@code JPanel} which is non opaque by default.
     * <p>
     *  {@link javax.swing.JLayer}的默认glassPane。它是{@code JPanel}的子类,它默认是不透明的。
     * 
     */
    private static class DefaultLayerGlassPane extends JPanel {
        /**
         * Creates a new {@link DefaultLayerGlassPane}
         * <p>
         *  创建新的{@link DefaultLayerGlassPane}
         * 
         */
        public DefaultLayerGlassPane() {
            setOpaque(false);
        }

        /**
         * First, implementation of this method iterates through
         * glassPane's child components and returns {@code true}
         * if any of them is visible and contains passed x,y point.
         * After that it checks if no mouseListeners is attached to this component
         * and no mouse cursor is set, then it returns {@code false},
         * otherwise calls the super implementation of this method.
         *
         * <p>
         *  首先,此方法的实现迭代通过glassPane的子组件,并返回{@code true},如果它们中的任何一个是可见的并且包含传递的x,y点。
         * 之后,它检查没有mouseListeners附加到这个组件,没有鼠标光标设置,那么它返回{@code false},否则调用这个方法的超级实现。
         * 
         * @param x the <i>x</i> coordinate of the point
         * @param y the <i>y</i> coordinate of the point
         * @return true if this component logically contains x,y
         */
        public boolean contains(int x, int y) {
            for (int i = 0; i < getComponentCount(); i++) {
                Component c = getComponent(i);
                Point point = SwingUtilities.convertPoint(this, new Point(x, y), c);
                if(c.isVisible() && c.contains(point)){
                    return true;
                }
            }
            if (getMouseListeners().length == 0
                    && getMouseMotionListeners().length == 0
                    && getMouseWheelListeners().length == 0
                    && !isCursorSet()) {
                return false;
            }
            return super.contains(x, y);
        }
    }
}
