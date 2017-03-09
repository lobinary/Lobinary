/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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



package javax.swing.plaf.basic;



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.plaf.*;
import javax.swing.border.Border;
import java.beans.*;
import sun.swing.DefaultLookup;



/**
 * Divider used by BasicSplitPaneUI. Subclassers may wish to override
 * paint to do something more interesting.
 * The border effect is drawn in BasicSplitPaneUI, so if you don't like
 * that border, reset it there.
 * To conditionally drag from certain areas subclass mousePressed and
 * call super when you wish the dragging to begin.
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  BasicSplitPaneUI使用的分隔符。子类可能希望覆盖paint来做一些更有趣的事情。边框效果在BasicSplitPaneUI中绘制,因此如果您不喜欢该边框,请重置它。
 * 有条件地从某些区域拖动mousePressed子类,并在您希望拖动开始时调用super。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @author Scott Violet
 */
public class BasicSplitPaneDivider extends Container
    implements PropertyChangeListener
{
    /**
     * Width or height of the divider based on orientation
     * BasicSplitPaneUI adds two to this.
     * <p>
     *  基于方向的分隔线的宽度或高度BasicSplitPaneUI添加两个到此。
     * 
     */
    protected static final int ONE_TOUCH_SIZE = 6;
    protected static final int ONE_TOUCH_OFFSET = 2;

    /**
     * Handles mouse dragging message to do the actual dragging.
     * <p>
     *  处理鼠标拖动消息以进行实际拖动。
     * 
     */
    protected DragController dragger;

    /**
     * UI this instance was created from.
     * <p>
     *  UI创建自此实例。
     * 
     */
    protected BasicSplitPaneUI splitPaneUI;

    /**
     * Size of the divider.
     * <p>
     *  分隔器的尺寸。
     * 
     */
    protected int dividerSize = 0; // default - SET TO 0???

    /**
     * Divider that is used for noncontinuous layout mode.
     * <p>
     *  用于非连续布局模式的分隔线。
     * 
     */
    protected Component hiddenDivider;

    /**
     * JSplitPane the receiver is contained in.
     * <p>
     *  JSplitPane的接收器是包含的。
     * 
     */
    protected JSplitPane splitPane;

    /**
     * Handles mouse events from both this class, and the split pane.
     * Mouse events are handled for the splitpane since you want to be able
     * to drag when clicking on the border of the divider, which is not
     * drawn by the divider.
     * <p>
     *  从此类和拆分窗格处理鼠标事件。鼠标事件是为分割窗格处理的,因为您想要在单击分隔符边框时可以拖动,而不是由分隔符绘制。
     * 
     */
    protected MouseHandler mouseHandler;

    /**
     * Orientation of the JSplitPane.
     * <p>
     *  JSplitPane的方向。
     * 
     */
    protected int orientation;

    /**
     * Button for quickly toggling the left component.
     * <p>
     *  快速切换左侧组件的按钮。
     * 
     */
    protected JButton leftButton;

    /**
     * Button for quickly toggling the right component.
     * <p>
     * 快速切换正确组件的按钮。
     * 
     */
    protected JButton rightButton;

    /** Border. */
    private Border border;

    /**
     * Is the mouse over the divider?
     * <p>
     *  鼠标在分隔线上吗?
     * 
     */
    private boolean mouseOver;

    private int oneTouchSize;
    private int oneTouchOffset;

    /**
     * If true the one touch buttons are centered on the divider.
     * <p>
     *  如果为真,则单触按钮位于分隔线的中心。
     * 
     */
    private boolean centerOneTouchButtons;


    /**
     * Creates an instance of BasicSplitPaneDivider. Registers this
     * instance for mouse events and mouse dragged events.
     * <p>
     *  创建BasicSplitPaneDivider的实例。为鼠标事件和鼠标拖动事件注册此实例。
     * 
     */
    public BasicSplitPaneDivider(BasicSplitPaneUI ui) {
        oneTouchSize = DefaultLookup.getInt(ui.getSplitPane(), ui,
                "SplitPane.oneTouchButtonSize", ONE_TOUCH_SIZE);
        oneTouchOffset = DefaultLookup.getInt(ui.getSplitPane(), ui,
                "SplitPane.oneTouchButtonOffset", ONE_TOUCH_OFFSET);
        centerOneTouchButtons = DefaultLookup.getBoolean(ui.getSplitPane(),
                 ui, "SplitPane.centerOneTouchButtons", true);
        setLayout(new DividerLayout());
        setBasicSplitPaneUI(ui);
        orientation = splitPane.getOrientation();
        setCursor((orientation == JSplitPane.HORIZONTAL_SPLIT) ?
                  Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR) :
                  Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
        setBackground(UIManager.getColor("SplitPane.background"));
    }

    private void revalidateSplitPane() {
        invalidate();
        if (splitPane != null) {
            splitPane.revalidate();
        }
    }

    /**
     * Sets the SplitPaneUI that is using the receiver.
     * <p>
     *  设置使用接收器的SplitPaneUI。
     * 
     */
    public void setBasicSplitPaneUI(BasicSplitPaneUI newUI) {
        if (splitPane != null) {
            splitPane.removePropertyChangeListener(this);
           if (mouseHandler != null) {
               splitPane.removeMouseListener(mouseHandler);
               splitPane.removeMouseMotionListener(mouseHandler);
               removeMouseListener(mouseHandler);
               removeMouseMotionListener(mouseHandler);
               mouseHandler = null;
           }
        }
        splitPaneUI = newUI;
        if (newUI != null) {
            splitPane = newUI.getSplitPane();
            if (splitPane != null) {
                if (mouseHandler == null) mouseHandler = new MouseHandler();
                splitPane.addMouseListener(mouseHandler);
                splitPane.addMouseMotionListener(mouseHandler);
                addMouseListener(mouseHandler);
                addMouseMotionListener(mouseHandler);
                splitPane.addPropertyChangeListener(this);
                if (splitPane.isOneTouchExpandable()) {
                    oneTouchExpandableChanged();
                }
            }
        }
        else {
            splitPane = null;
        }
    }


    /**
     * Returns the <code>SplitPaneUI</code> the receiver is currently
     * in.
     * <p>
     *  返回接收器当前所在的<code> SplitPaneUI </code>。
     * 
     */
    public BasicSplitPaneUI getBasicSplitPaneUI() {
        return splitPaneUI;
    }


    /**
     * Sets the size of the divider to <code>newSize</code>. That is
     * the width if the splitpane is <code>HORIZONTAL_SPLIT</code>, or
     * the height of <code>VERTICAL_SPLIT</code>.
     * <p>
     *  将分隔符的大小设置为<code> newSize </code>。
     * 如果splitpane是<code> HORIZONTAL_SPLIT </code>,或者是<code> VERTICAL_SPLIT </code>的高度,那么就是宽度。
     * 
     */
    public void setDividerSize(int newSize) {
        dividerSize = newSize;
    }


    /**
     * Returns the size of the divider, that is the width if the splitpane
     * is HORIZONTAL_SPLIT, or the height of VERTICAL_SPLIT.
     * <p>
     *  返回分隔符的大小,即分裂窗格为HORIZONTAL_SPLIT时的宽度或VERTICAL_SPLIT的高度。
     * 
     */
    public int getDividerSize() {
        return dividerSize;
    }


    /**
     * Sets the border of this component.
     * <p>
     *  设置此组件的边框。
     * 
     * 
     * @since 1.3
     */
    public void setBorder(Border border) {
        Border         oldBorder = this.border;

        this.border = border;
    }

    /**
     * Returns the border of this component or null if no border is
     * currently set.
     *
     * <p>
     *  返回此组件的边框或null(如果当前未设置边框)。
     * 
     * 
     * @return the border object for this component
     * @see #setBorder
     * @since 1.3
     */
    public Border getBorder() {
        return border;
    }

    /**
     * If a border has been set on this component, returns the
     * border's insets, else calls super.getInsets.
     *
     * <p>
     *  如果在此组件上设置了边框,则返回边框的insets,否则调用super.getInsets。
     * 
     * 
     * @return the value of the insets property.
     * @see #setBorder
     */
    public Insets getInsets() {
        Border    border = getBorder();

        if (border != null) {
            return border.getBorderInsets(this);
        }
        return super.getInsets();
    }

    /**
     * Sets whether or not the mouse is currently over the divider.
     *
     * <p>
     *  设置鼠标当前是否超过分频器。
     * 
     * 
     * @param mouseOver whether or not the mouse is currently over the divider
     * @since 1.5
     */
    protected void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    /**
     * Returns whether or not the mouse is currently over the divider
     *
     * <p>
     *  返回鼠标当前是否超出分隔符
     * 
     * 
     * @return whether or not the mouse is currently over the divider
     * @since 1.5
     */
    public boolean isMouseOver() {
        return mouseOver;
    }

    /**
     * Returns dividerSize x dividerSize
     * <p>
     *  返回dividerSize x dividerSize
     * 
     */
    public Dimension getPreferredSize() {
        // Ideally this would return the size from the layout manager,
        // but that could result in the layed out size being different from
        // the dividerSize, which may break developers as well as
        // BasicSplitPaneUI.
        if (orientation == JSplitPane.HORIZONTAL_SPLIT) {
            return new Dimension(getDividerSize(), 1);
        }
        return new Dimension(1, getDividerSize());
    }

    /**
     * Returns dividerSize x dividerSize
     * <p>
     *  返回dividerSize x dividerSize
     * 
     */
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }


    /**
     * Property change event, presumably from the JSplitPane, will message
     * updateOrientation if necessary.
     * <p>
     *  属性更改事件,大概从JSplitPane,将消息updateOrientation如果必要。
     * 
     */
    public void propertyChange(PropertyChangeEvent e) {
        if (e.getSource() == splitPane) {
            if (e.getPropertyName() == JSplitPane.ORIENTATION_PROPERTY) {
                orientation = splitPane.getOrientation();
                setCursor((orientation == JSplitPane.HORIZONTAL_SPLIT) ?
                          Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR) :
                          Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
                revalidateSplitPane();
            }
            else if (e.getPropertyName() == JSplitPane.
                      ONE_TOUCH_EXPANDABLE_PROPERTY) {
                oneTouchExpandableChanged();
            }
        }
    }


    /**
     * Paints the divider.
     * <p>
     *  涂抹分隔线。
     * 
     */
    public void paint(Graphics g) {
      super.paint(g);

      // Paint the border.
      Border   border = getBorder();

      if (border != null) {
          Dimension     size = getSize();

          border.paintBorder(this, g, 0, 0, size.width, size.height);
      }
    }


    /**
     * Messaged when the oneTouchExpandable value of the JSplitPane the
     * receiver is contained in changes. Will create the
     * <code>leftButton</code> and <code>rightButton</code> if they
     * are null. invalidates the receiver as well.
     * <p>
     *  当JSplitPane的oneTouchExpandable值包含在更改中时,消息被接收。
     * 将创建<code> leftButton </code>和<code> rightButton </code>(如果它们为空)。也使接收器失效。
     * 
     */
    protected void oneTouchExpandableChanged() {
        if (!DefaultLookup.getBoolean(splitPane, splitPaneUI,
                           "SplitPane.supportsOneTouchButtons", true)) {
            // Look and feel doesn't want to support one touch buttons, bail.
            return;
        }
        if (splitPane.isOneTouchExpandable() &&
            leftButton == null &&
            rightButton == null) {
            /* Create the left button and add an action listener to
            /* <p>
            /* 
               expand/collapse it. */
            leftButton = createLeftOneTouchButton();
            if (leftButton != null)
                leftButton.addActionListener(new OneTouchActionHandler(true));


            /* Create the right button and add an action listener to
            /* <p>
            /* 
               expand/collapse it. */
            rightButton = createRightOneTouchButton();
            if (rightButton != null)
                rightButton.addActionListener(new OneTouchActionHandler
                    (false));

            if (leftButton != null && rightButton != null) {
                add(leftButton);
                add(rightButton);
            }
        }
        revalidateSplitPane();
    }


    /**
     * Creates and return an instance of JButton that can be used to
     * collapse the left component in the split pane.
     * <p>
     * 创建并返回JButton的实例,可用于折叠拆分窗格中的左侧组件。
     * 
     */
    protected JButton createLeftOneTouchButton() {
        JButton b = new JButton() {
            public void setBorder(Border b) {
            }
            public void paint(Graphics g) {
                if (splitPane != null) {
                    int[]   xs = new int[3];
                    int[]   ys = new int[3];
                    int     blockSize;

                    // Fill the background first ...
                    g.setColor(this.getBackground());
                    g.fillRect(0, 0, this.getWidth(),
                               this.getHeight());

                    // ... then draw the arrow.
                    g.setColor(Color.black);
                    if (orientation == JSplitPane.VERTICAL_SPLIT) {
                        blockSize = Math.min(getHeight(), oneTouchSize);
                        xs[0] = blockSize;
                        xs[1] = 0;
                        xs[2] = blockSize << 1;
                        ys[0] = 0;
                        ys[1] = ys[2] = blockSize;
                        g.drawPolygon(xs, ys, 3); // Little trick to make the
                                                  // arrows of equal size
                    }
                    else {
                        blockSize = Math.min(getWidth(), oneTouchSize);
                        xs[0] = xs[2] = blockSize;
                        xs[1] = 0;
                        ys[0] = 0;
                        ys[1] = blockSize;
                        ys[2] = blockSize << 1;
                    }
                    g.fillPolygon(xs, ys, 3);
                }
            }
            // Don't want the button to participate in focus traversable.
            public boolean isFocusTraversable() {
                return false;
            }
        };
        b.setMinimumSize(new Dimension(oneTouchSize, oneTouchSize));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setRequestFocusEnabled(false);
        return b;
    }


    /**
     * Creates and return an instance of JButton that can be used to
     * collapse the right component in the split pane.
     * <p>
     *  创建并返回JButton的实例,可用于在拆分窗格中折叠正确的组件。
     * 
     */
    protected JButton createRightOneTouchButton() {
        JButton b = new JButton() {
            public void setBorder(Border border) {
            }
            public void paint(Graphics g) {
                if (splitPane != null) {
                    int[]          xs = new int[3];
                    int[]          ys = new int[3];
                    int            blockSize;

                    // Fill the background first ...
                    g.setColor(this.getBackground());
                    g.fillRect(0, 0, this.getWidth(),
                               this.getHeight());

                    // ... then draw the arrow.
                    if (orientation == JSplitPane.VERTICAL_SPLIT) {
                        blockSize = Math.min(getHeight(), oneTouchSize);
                        xs[0] = blockSize;
                        xs[1] = blockSize << 1;
                        xs[2] = 0;
                        ys[0] = blockSize;
                        ys[1] = ys[2] = 0;
                    }
                    else {
                        blockSize = Math.min(getWidth(), oneTouchSize);
                        xs[0] = xs[2] = 0;
                        xs[1] = blockSize;
                        ys[0] = 0;
                        ys[1] = blockSize;
                        ys[2] = blockSize << 1;
                    }
                    g.setColor(Color.black);
                    g.fillPolygon(xs, ys, 3);
                }
            }
            // Don't want the button to participate in focus traversable.
            public boolean isFocusTraversable() {
                return false;
            }
        };
        b.setMinimumSize(new Dimension(oneTouchSize, oneTouchSize));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setRequestFocusEnabled(false);
        return b;
    }


    /**
     * Message to prepare for dragging. This messages the BasicSplitPaneUI
     * with startDragging.
     * <p>
     *  准备拖动的消息。这将使用startDragging消息BasicSplitPaneUI。
     * 
     */
    protected void prepareForDragging() {
        splitPaneUI.startDragging();
    }


    /**
     * Messages the BasicSplitPaneUI with dragDividerTo that this instance
     * is contained in.
     * <p>
     *  使用此实例包含的dragDividerTo消息BasicSplitPaneUI。
     * 
     */
    protected void dragDividerTo(int location) {
        splitPaneUI.dragDividerTo(location);
    }


    /**
     * Messages the BasicSplitPaneUI with finishDraggingTo that this instance
     * is contained in.
     * <p>
     *  使用此实例包含的finishDraggingTo消息BasicSplitPaneUI。
     * 
     */
    protected void finishDraggingTo(int location) {
        splitPaneUI.finishDraggingTo(location);
    }


    /**
     * MouseHandler is responsible for converting mouse events
     * (released, dragged...) into the appropriate DragController
     * methods.
     *
     * <p>
     *  MouseHandler负责将鼠标事件(释放,拖动...)转换为适当的DragController方法。
     * 
     */
    protected class MouseHandler extends MouseAdapter
            implements MouseMotionListener
    {
        /**
         * Starts the dragging session by creating the appropriate instance
         * of DragController.
         * <p>
         *  通过创建DragController的相应实例来启动拖动会话。
         * 
         */
        public void mousePressed(MouseEvent e) {
            if ((e.getSource() == BasicSplitPaneDivider.this ||
                 e.getSource() == splitPane) &&
                dragger == null &&splitPane.isEnabled()) {
                Component            newHiddenDivider = splitPaneUI.
                                     getNonContinuousLayoutDivider();

                if (hiddenDivider != newHiddenDivider) {
                    if (hiddenDivider != null) {
                        hiddenDivider.removeMouseListener(this);
                        hiddenDivider.removeMouseMotionListener(this);
                    }
                    hiddenDivider = newHiddenDivider;
                    if (hiddenDivider != null) {
                        hiddenDivider.addMouseMotionListener(this);
                        hiddenDivider.addMouseListener(this);
                    }
                }
                if (splitPane.getLeftComponent() != null &&
                    splitPane.getRightComponent() != null) {
                    if (orientation == JSplitPane.HORIZONTAL_SPLIT) {
                        dragger = new DragController(e);
                    }
                    else {
                        dragger = new VerticalDragController(e);
                    }
                    if (!dragger.isValid()) {
                        dragger = null;
                    }
                    else {
                        prepareForDragging();
                        dragger.continueDrag(e);
                    }
                }
                e.consume();
            }
        }


        /**
         * If dragger is not null it is messaged with completeDrag.
         * <p>
         *  如果dragger不为null,它会与completeDrag发送消息。
         * 
         */
        public void mouseReleased(MouseEvent e) {
            if (dragger != null) {
                if (e.getSource() == splitPane) {
                    dragger.completeDrag(e.getX(), e.getY());
                }
                else if (e.getSource() == BasicSplitPaneDivider.this) {
                    Point   ourLoc = getLocation();

                    dragger.completeDrag(e.getX() + ourLoc.x,
                                         e.getY() + ourLoc.y);
                }
                else if (e.getSource() == hiddenDivider) {
                    Point   hDividerLoc = hiddenDivider.getLocation();
                    int     ourX = e.getX() + hDividerLoc.x;
                    int     ourY = e.getY() + hDividerLoc.y;

                    dragger.completeDrag(ourX, ourY);
                }
                dragger = null;
                e.consume();
            }
        }


        //
        // MouseMotionListener
        //

        /**
         * If dragger is not null it is messaged with continueDrag.
         * <p>
         *  如果dragger不为null,它将与continueDrag发送。
         * 
         */
        public void mouseDragged(MouseEvent e) {
            if (dragger != null) {
                if (e.getSource() == splitPane) {
                    dragger.continueDrag(e.getX(), e.getY());
                }
                else if (e.getSource() == BasicSplitPaneDivider.this) {
                    Point   ourLoc = getLocation();

                    dragger.continueDrag(e.getX() + ourLoc.x,
                                         e.getY() + ourLoc.y);
                }
                else if (e.getSource() == hiddenDivider) {
                    Point   hDividerLoc = hiddenDivider.getLocation();
                    int     ourX = e.getX() + hDividerLoc.x;
                    int     ourY = e.getY() + hDividerLoc.y;

                    dragger.continueDrag(ourX, ourY);
                }
                e.consume();
            }
        }


        /**
         *  Resets the cursor based on the orientation.
         * <p>
         *  根据方向重置光标。
         * 
         */
        public void mouseMoved(MouseEvent e) {
        }

        /**
         * Invoked when the mouse enters a component.
         *
         * <p>
         *  当鼠标进入组件时调用。
         * 
         * 
         * @param e MouseEvent describing the details of the enter event.
         * @since 1.5
         */
        public void mouseEntered(MouseEvent e) {
            if (e.getSource() == BasicSplitPaneDivider.this) {
                setMouseOver(true);
            }
        }

        /**
         * Invoked when the mouse exits a component.
         *
         * <p>
         *  在鼠标退出组件时调用。
         * 
         * 
         * @param e MouseEvent describing the details of the exit event.
         * @since 1.5
         */
        public void mouseExited(MouseEvent e) {
            if (e.getSource() == BasicSplitPaneDivider.this) {
                setMouseOver(false);
            }
        }
    }


    /**
     * Handles the events during a dragging session for a
     * HORIZONTAL_SPLIT oriented split pane. This continually
     * messages <code>dragDividerTo</code> and then when done messages
     * <code>finishDraggingTo</code>. When an instance is created it should be
     * messaged with <code>isValid</code> to insure that dragging can happen
     * (dragging won't be allowed if the two views can not be resized).
     * <p>
     * <strong>Warning:</strong>
     * Serialized objects of this class will not be compatible with
     * future Swing releases. The current serialization support is
     * appropriate for short term storage or RMI between applications running
     * the same version of Swing.  As of 1.4, support for long term storage
     * of all JavaBeans&trade;
     * has been added to the <code>java.beans</code> package.
     * Please see {@link java.beans.XMLEncoder}.
     * <p>
     *  在面向HORIZONTAL_SPLIT的拆分窗格的拖动会话期间处理事件。
     * 这不断地消息<code> dragDividerTo </code>,然后完成消息<code> finishDraggingTo </code>。
     * 当创建一个实例时,应该使用<code> isValid </code>来确保拖动可以发生(如果两个视图不能调整大小,则不允许拖动)。
     * <p>
     * <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
     *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
     * 
     */
    protected class DragController
    {
        /**
         * Initial location of the divider.
         * <p>
         *  分隔符的初始位置。
         * 
         */
        int initialX;

        /**
         * Maximum and minimum positions to drag to.
         * <p>
         *  要拖动到的最大和最小位置。
         * 
         */
        int maxX, minX;

        /**
         * Initial location the mouse down happened at.
         * <p>
         *  初始位置鼠标按下发生在。
         * 
         */
        int offset;


        protected DragController(MouseEvent e) {
            JSplitPane  splitPane = splitPaneUI.getSplitPane();
            Component   leftC = splitPane.getLeftComponent();
            Component   rightC = splitPane.getRightComponent();

            initialX = getLocation().x;
            if (e.getSource() == BasicSplitPaneDivider.this) {
                offset = e.getX();
            }
            else { // splitPane
                offset = e.getX() - initialX;
            }
            if (leftC == null || rightC == null || offset < -1 ||
                offset >= getSize().width) {
                // Don't allow dragging.
                maxX = -1;
            }
            else {
                Insets      insets = splitPane.getInsets();

                if (leftC.isVisible()) {
                    minX = leftC.getMinimumSize().width;
                    if (insets != null) {
                        minX += insets.left;
                    }
                }
                else {
                    minX = 0;
                }
                if (rightC.isVisible()) {
                    int right = (insets != null) ? insets.right : 0;
                    maxX = Math.max(0, splitPane.getSize().width -
                                    (getSize().width + right) -
                                    rightC.getMinimumSize().width);
                }
                else {
                    int right = (insets != null) ? insets.right : 0;
                    maxX = Math.max(0, splitPane.getSize().width -
                                    (getSize().width + right));
                }
                if (maxX < minX) minX = maxX = 0;
            }
        }


        /**
         * Returns true if the dragging session is valid.
         * <p>
         *  如果拖动会话有效,则返回true。
         * 
         */
        protected boolean isValid() {
            return (maxX > 0);
        }


        /**
         * Returns the new position to put the divider at based on
         * the passed in MouseEvent.
         * <p>
         *  返回新位置以根据在MouseEvent中传递的分隔符。
         * 
         */
        protected int positionForMouseEvent(MouseEvent e) {
            int newX = (e.getSource() == BasicSplitPaneDivider.this) ?
                        (e.getX() + getLocation().x) : e.getX();

            newX = Math.min(maxX, Math.max(minX, newX - offset));
            return newX;
        }


        /**
         * Returns the x argument, since this is used for horizontal
         * splits.
         * <p>
         *  返回x参数,因为它用于水平拆分。
         * 
         */
        protected int getNeededLocation(int x, int y) {
            int newX;

            newX = Math.min(maxX, Math.max(minX, x - offset));
            return newX;
        }


        protected void continueDrag(int newX, int newY) {
            dragDividerTo(getNeededLocation(newX, newY));
        }


        /**
         * Messages dragDividerTo with the new location for the mouse
         * event.
         * <p>
         *  消息dragDividerTo与鼠标事件的新位置。
         * 
         */
        protected void continueDrag(MouseEvent e) {
            dragDividerTo(positionForMouseEvent(e));
        }


        protected void completeDrag(int x, int y) {
            finishDraggingTo(getNeededLocation(x, y));
        }


        /**
         * Messages finishDraggingTo with the new location for the mouse
         * event.
         * <p>
         *  消息finishDraggingTo与鼠标事件的新位置。
         * 
         */
        protected void completeDrag(MouseEvent e) {
            finishDraggingTo(positionForMouseEvent(e));
        }
    } // End of BasicSplitPaneDivider.DragController


    /**
     * Handles the events during a dragging session for a
     * VERTICAL_SPLIT oriented split pane. This continually
     * messages <code>dragDividerTo</code> and then when done messages
     * <code>finishDraggingTo</code>. When an instance is created it should be
     * messaged with <code>isValid</code> to insure that dragging can happen
     * (dragging won't be allowed if the two views can not be resized).
     * <p>
     *  在拖动会话期间处理面向VERTICAL_SPLIT的拆分窗格中的事件。
     * 这不断地消息<code> dragDividerTo </code>,然后完成消息<code> finishDraggingTo </code>。
     * 当创建一个实例时,应该使用<code> isValid </code>来确保拖动可以发生(如果两个视图不能调整大小,则不允许拖动)。
     * 
     */
    protected class VerticalDragController extends DragController
    {
        /* DragControllers ivars are now in terms of y, not x. */
        protected VerticalDragController(MouseEvent e) {
            super(e);
            JSplitPane splitPane = splitPaneUI.getSplitPane();
            Component  leftC = splitPane.getLeftComponent();
            Component  rightC = splitPane.getRightComponent();

            initialX = getLocation().y;
            if (e.getSource() == BasicSplitPaneDivider.this) {
                offset = e.getY();
            }
            else {
                offset = e.getY() - initialX;
            }
            if (leftC == null || rightC == null || offset < -1 ||
                offset > getSize().height) {
                // Don't allow dragging.
                maxX = -1;
            }
            else {
                Insets     insets = splitPane.getInsets();

                if (leftC.isVisible()) {
                    minX = leftC.getMinimumSize().height;
                    if (insets != null) {
                        minX += insets.top;
                    }
                }
                else {
                    minX = 0;
                }
                if (rightC.isVisible()) {
                    int    bottom = (insets != null) ? insets.bottom : 0;

                    maxX = Math.max(0, splitPane.getSize().height -
                                    (getSize().height + bottom) -
                                    rightC.getMinimumSize().height);
                }
                else {
                    int    bottom = (insets != null) ? insets.bottom : 0;

                    maxX = Math.max(0, splitPane.getSize().height -
                                    (getSize().height + bottom));
                }
                if (maxX < minX) minX = maxX = 0;
            }
        }


        /**
         * Returns the y argument, since this is used for vertical
         * splits.
         * <p>
         *  返回y参数,因为它用于垂直拆分。
         * 
         */
        protected int getNeededLocation(int x, int y) {
            int newY;

            newY = Math.min(maxX, Math.max(minX, y - offset));
            return newY;
        }


        /**
         * Returns the new position to put the divider at based on
         * the passed in MouseEvent.
         * <p>
         *  返回新位置以根据在MouseEvent中传递的分隔符。
         * 
         */
        protected int positionForMouseEvent(MouseEvent e) {
            int newY = (e.getSource() == BasicSplitPaneDivider.this) ?
                        (e.getY() + getLocation().y) : e.getY();


            newY = Math.min(maxX, Math.max(minX, newY - offset));
            return newY;
        }
    } // End of BasicSplitPaneDividier.VerticalDragController


    /**
     * Used to layout a <code>BasicSplitPaneDivider</code>.
     * Layout for the divider
     * involves appropriately moving the left/right buttons around.
     *
     * <p>
     * 用于布局<code> BasicSplitPaneDivider </code>。分隔线的布局包括适当地移动左/右按钮。
     * 
     */
    protected class DividerLayout implements LayoutManager
    {
        public void layoutContainer(Container c) {
            if (leftButton != null && rightButton != null &&
                c == BasicSplitPaneDivider.this) {
                if (splitPane.isOneTouchExpandable()) {
                    Insets insets = getInsets();

                    if (orientation == JSplitPane.VERTICAL_SPLIT) {
                        int extraX = (insets != null) ? insets.left : 0;
                        int blockSize = getHeight();

                        if (insets != null) {
                            blockSize -= (insets.top + insets.bottom);
                            blockSize = Math.max(blockSize, 0);
                        }
                        blockSize = Math.min(blockSize, oneTouchSize);

                        int y = (c.getSize().height - blockSize) / 2;

                        if (!centerOneTouchButtons) {
                            y = (insets != null) ? insets.top : 0;
                            extraX = 0;
                        }
                        leftButton.setBounds(extraX + oneTouchOffset, y,
                                             blockSize * 2, blockSize);
                        rightButton.setBounds(extraX + oneTouchOffset +
                                              oneTouchSize * 2, y,
                                              blockSize * 2, blockSize);
                    }
                    else {
                        int extraY = (insets != null) ? insets.top : 0;
                        int blockSize = getWidth();

                        if (insets != null) {
                            blockSize -= (insets.left + insets.right);
                            blockSize = Math.max(blockSize, 0);
                        }
                        blockSize = Math.min(blockSize, oneTouchSize);

                        int x = (c.getSize().width - blockSize) / 2;

                        if (!centerOneTouchButtons) {
                            x = (insets != null) ? insets.left : 0;
                            extraY = 0;
                        }

                        leftButton.setBounds(x, extraY + oneTouchOffset,
                                             blockSize, blockSize * 2);
                        rightButton.setBounds(x, extraY + oneTouchOffset +
                                              oneTouchSize * 2, blockSize,
                                              blockSize * 2);
                    }
                }
                else {
                    leftButton.setBounds(-5, -5, 1, 1);
                    rightButton.setBounds(-5, -5, 1, 1);
                }
            }
        }


        public Dimension minimumLayoutSize(Container c) {
            // NOTE: This isn't really used, refer to
            // BasicSplitPaneDivider.getPreferredSize for the reason.
            // I leave it in hopes of having this used at some point.
            if (c != BasicSplitPaneDivider.this || splitPane == null) {
                return new Dimension(0,0);
            }
            Dimension buttonMinSize = null;

            if (splitPane.isOneTouchExpandable() && leftButton != null) {
                buttonMinSize = leftButton.getMinimumSize();
            }

            Insets insets = getInsets();
            int width = getDividerSize();
            int height = width;

            if (orientation == JSplitPane.VERTICAL_SPLIT) {
                if (buttonMinSize != null) {
                    int size = buttonMinSize.height;
                    if (insets != null) {
                        size += insets.top + insets.bottom;
                    }
                    height = Math.max(height, size);
                }
                width = 1;
            }
            else {
                if (buttonMinSize != null) {
                    int size = buttonMinSize.width;
                    if (insets != null) {
                        size += insets.left + insets.right;
                    }
                    width = Math.max(width, size);
                }
                height = 1;
            }
            return new Dimension(width, height);
        }


        public Dimension preferredLayoutSize(Container c) {
            return minimumLayoutSize(c);
        }


        public void removeLayoutComponent(Component c) {}

        public void addLayoutComponent(String string, Component c) {}
    } // End of class BasicSplitPaneDivider.DividerLayout


    /**
     * Listeners installed on the one touch expandable buttons.
     * <p>
     *  监听器安装在单触式可扩展按钮上。
     * 
     */
    private class OneTouchActionHandler implements ActionListener {
        /** True indicates the resize should go the minimum (top or left)
         * vs false which indicates the resize should go to the maximum.
         * <p>
         *  vs false表示调整大小应该达到最大值。
         */
        private boolean toMinimum;

        OneTouchActionHandler(boolean toMinimum) {
            this.toMinimum = toMinimum;
        }

        public void actionPerformed(ActionEvent e) {
            Insets  insets = splitPane.getInsets();
            int     lastLoc = splitPane.getLastDividerLocation();
            int     currentLoc = splitPaneUI.getDividerLocation(splitPane);
            int     newLoc;

            // We use the location from the UI directly, as the location the
            // JSplitPane itself maintains is not necessarly correct.
            if (toMinimum) {
                if (orientation == JSplitPane.VERTICAL_SPLIT) {
                    if (currentLoc >= (splitPane.getHeight() -
                                       insets.bottom - getHeight())) {
                        int maxLoc = splitPane.getMaximumDividerLocation();
                        newLoc = Math.min(lastLoc, maxLoc);
                        splitPaneUI.setKeepHidden(false);
                    }
                    else {
                        newLoc = insets.top;
                        splitPaneUI.setKeepHidden(true);
                    }
                }
                else {
                    if (currentLoc >= (splitPane.getWidth() -
                                       insets.right - getWidth())) {
                        int maxLoc = splitPane.getMaximumDividerLocation();
                        newLoc = Math.min(lastLoc, maxLoc);
                        splitPaneUI.setKeepHidden(false);
                    }
                    else {
                        newLoc = insets.left;
                        splitPaneUI.setKeepHidden(true);
                    }
                }
            }
            else {
                if (orientation == JSplitPane.VERTICAL_SPLIT) {
                    if (currentLoc == insets.top) {
                        int maxLoc = splitPane.getMaximumDividerLocation();
                        newLoc = Math.min(lastLoc, maxLoc);
                        splitPaneUI.setKeepHidden(false);
                    }
                    else {
                        newLoc = splitPane.getHeight() - getHeight() -
                                 insets.top;
                        splitPaneUI.setKeepHidden(true);
                    }
                }
                else {
                    if (currentLoc == insets.left) {
                        int maxLoc = splitPane.getMaximumDividerLocation();
                        newLoc = Math.min(lastLoc, maxLoc);
                        splitPaneUI.setKeepHidden(false);
                    }
                    else {
                        newLoc = splitPane.getWidth() - getWidth() -
                                 insets.left;
                        splitPaneUI.setKeepHidden(true);
                    }
                }
            }
            if (currentLoc != newLoc) {
                splitPane.setDividerLocation(newLoc);
                // We do this in case the dividers notion of the location
                // differs from the real location.
                splitPane.setLastDividerLocation(currentLoc);
            }
        }
    } // End of class BasicSplitPaneDivider.LeftActionListener
}
