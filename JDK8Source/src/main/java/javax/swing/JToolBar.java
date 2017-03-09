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

package javax.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.LayoutManager2;
import java.awt.event.*;
import java.beans.*;

import javax.swing.border.Border;
import javax.swing.plaf.*;
import javax.accessibility.*;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Hashtable;


/**
 * <code>JToolBar</code> provides a component that is useful for
 * displaying commonly used <code>Action</code>s or controls.
 * For examples and information on using tool bars see
 * <a href="https://docs.oracle.com/javase/tutorial/uiswing/components/toolbar.html">How to Use Tool Bars</a>,
 * a section in <em>The Java Tutorial</em>.
 *
 * <p>
 * With most look and feels,
 * the user can drag out a tool bar into a separate window
 * (unless the <code>floatable</code> property is set to <code>false</code>).
 * For drag-out to work correctly, it is recommended that you add
 * <code>JToolBar</code> instances to one of the four "sides" of a
 * container whose layout manager is a <code>BorderLayout</code>,
 * and do not add children to any of the other four "sides".
 * <p>
 * <strong>Warning:</strong> Swing is not thread safe. For more
 * information see <a
 * href="package-summary.html#threading">Swing's Threading
 * Policy</a>.
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
 * @beaninfo
 *   attribute: isContainer true
 * description: A component which displays commonly used controls or Actions.
 *
 * <p>
 *  <code> JToolBar </code>提供了一个组件,用于显示常用的<code> Action </code>或控件。
 * 有关使用工具栏的示例和信息,请参阅<a href="https://docs.oracle.com/javase/tutorial/uiswing/components/toolbar.html">如何
 * 使用工具栏</a>, <em> Java教程</em>。
 *  <code> JToolBar </code>提供了一个组件,用于显示常用的<code> Action </code>或控件。
 * 
 * <p>
 *  使用大多数外观和感觉,用户可以将工具栏拖出到单独的窗口中(除非<code> floatable </code>属性设置为<code> false </code>)。
 * 为了使拖动正常工作,建议您将<code> JToolBar </code>实例添加到布局管理器为<code> BorderLayout </code>的容器的四个"边"之一,并执行不要将孩子添加到其他四
 * 个"侧面"中的任何一个。
 *  使用大多数外观和感觉,用户可以将工具栏拖出到单独的窗口中(除非<code> floatable </code>属性设置为<code> false </code>)。
 * <p>
 *  <strong>警告：</strong> Swing不是线程安全的。有关详情,请参阅<a href="package-summary.html#threading"> Swing的线程策略</a>。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * @beaninfo属性：isContainer true描述：显示常用控件或操作的组件。
 * 
 * 
 * @author Georges Saab
 * @author Jeff Shapiro
 * @see Action
 */
public class JToolBar extends JComponent implements SwingConstants, Accessible
{
    /**
    /* <p>
    /* 
     * @see #getUIClassID
     * @see #readObject
     */
    private static final String uiClassID = "ToolBarUI";

    private    boolean   paintBorder              = true;
    private    Insets    margin                   = null;
    private    boolean   floatable                = true;
    private    int       orientation              = HORIZONTAL;

    /**
     * Creates a new tool bar; orientation defaults to <code>HORIZONTAL</code>.
     * <p>
     *  创建一个新的工具栏;方向默认为<code> HORIZONTAL </code>。
     * 
     */
    public JToolBar()
    {
        this( HORIZONTAL );
    }

    /**
     * Creates a new tool bar with the specified <code>orientation</code>.
     * The <code>orientation</code> must be either <code>HORIZONTAL</code>
     * or <code>VERTICAL</code>.
     *
     * <p>
     *  使用指定的<code>方向</code>创建新工具栏。 <code>方向</code>必须是<code> HORIZONTAL </code>或<code> VERTICAL </code>。
     * 
     * 
     * @param orientation  the orientation desired
     */
    public JToolBar( int orientation )
    {
        this(null, orientation);
    }

    /**
     * Creates a new tool bar with the specified <code>name</code>.  The
     * name is used as the title of the undocked tool bar.  The default
     * orientation is <code>HORIZONTAL</code>.
     *
     * <p>
     *  使用指定的<code> name </code>创建一个新工具栏。该名称用作已取消停靠的工具栏的标题。默认方向为<code> HORIZONTAL </code>。
     * 
     * 
     * @param name the name of the tool bar
     * @since 1.3
     */
    public JToolBar( String name ) {
        this(name, HORIZONTAL);
    }

    /**
     * Creates a new tool bar with a specified <code>name</code> and
     * <code>orientation</code>.
     * All other constructors call this constructor.
     * If <code>orientation</code> is an invalid value, an exception will
     * be thrown.
     *
     * <p>
     *  创建具有指定的<code>名称</code>和<code>方向</code>的新工具栏。所有其他构造函数调用此构造函数。
     * 如果<code> orientation </code>是无效值,则会抛出异常。
     * 
     * 
     * @param name  the name of the tool bar
     * @param orientation  the initial orientation -- it must be
     *          either <code>HORIZONTAL</code> or <code>VERTICAL</code>
     * @exception IllegalArgumentException if orientation is neither
     *          <code>HORIZONTAL</code> nor <code>VERTICAL</code>
     * @since 1.3
     */
    public JToolBar( String name , int orientation) {
        setName(name);
        checkOrientation( orientation );

        this.orientation = orientation;
        DefaultToolBarLayout layout =  new DefaultToolBarLayout( orientation );
        setLayout( layout );

        addPropertyChangeListener( layout );

        updateUI();
    }

    /**
     * Returns the tool bar's current UI.
     * <p>
     *  返回工具栏的当前UI。
     * 
     * 
     * @see #setUI
     */
    public ToolBarUI getUI() {
        return (ToolBarUI)ui;
    }

    /**
     * Sets the L&amp;F object that renders this component.
     *
     * <p>
     *  设置呈现此组件的L&amp; F对象。
     * 
     * 
     * @param ui  the <code>ToolBarUI</code> L&amp;F object
     * @see UIDefaults#getUI
     * @beaninfo
     *        bound: true
     *       hidden: true
     *    attribute: visualUpdate true
     *  description: The UI object that implements the Component's LookAndFeel.
     */
    public void setUI(ToolBarUI ui) {
        super.setUI(ui);
    }

    /**
     * Notification from the <code>UIFactory</code> that the L&amp;F has changed.
     * Called to replace the UI with the latest version from the
     * <code>UIFactory</code>.
     *
     * <p>
     *  从<code> UIFactory </code>通知L&amp; F已更改。调用将UI替换为来自<code> UIFactory </code>的最新版本。
     * 
     * 
     * @see JComponent#updateUI
     */
    public void updateUI() {
        setUI((ToolBarUI)UIManager.getUI(this));
        // GTKLookAndFeel installs a different LayoutManager, and sets it
        // to null after changing the look and feel, so, install the default
        // if the LayoutManager is null.
        if (getLayout() == null) {
            setLayout(new DefaultToolBarLayout(getOrientation()));
        }
        invalidate();
    }



    /**
     * Returns the name of the L&amp;F class that renders this component.
     *
     * <p>
     *  返回呈现此组件的L&amp; F类的名称。
     * 
     * 
     * @return the string "ToolBarUI"
     * @see JComponent#getUIClassID
     * @see UIDefaults#getUI
     */
    public String getUIClassID() {
        return uiClassID;
    }


    /**
     * Returns the index of the specified component.
     * (Note: Separators occupy index positions.)
     *
     * <p>
     *  返回指定组件的索引。 (注意：分隔符占据索引位置。)
     * 
     * 
     * @param c  the <code>Component</code> to find
     * @return an integer indicating the component's position,
     *          where 0 is first
     */
    public int getComponentIndex(Component c) {
        int ncomponents = this.getComponentCount();
        Component[] component = this.getComponents();
        for (int i = 0 ; i < ncomponents ; i++) {
            Component comp = component[i];
            if (comp == c)
                return i;
        }
        return -1;
    }

    /**
     * Returns the component at the specified index.
     *
     * <p>
     *  返回指定索引处的组件。
     * 
     * 
     * @param i  the component's position, where 0 is first
     * @return   the <code>Component</code> at that position,
     *          or <code>null</code> for an invalid index
     *
     */
    public Component getComponentAtIndex(int i) {
        int ncomponents = this.getComponentCount();
        if ( i >= 0 && i < ncomponents) {
            Component[] component = this.getComponents();
            return component[i];
        }
        return null;
    }

     /**
      * Sets the margin between the tool bar's border and
      * its buttons. Setting to <code>null</code> causes the tool bar to
      * use the default margins. The tool bar's default <code>Border</code>
      * object uses this value to create the proper margin.
      * However, if a non-default border is set on the tool bar,
      * it is that <code>Border</code> object's responsibility to create the
      * appropriate margin space (otherwise this property will
      * effectively be ignored).
      *
      * <p>
      * 设置工具栏边框和其按钮之间的边距。设置为<code> null </code>会使工具栏使用默认边距。工具栏的默认<code> Border </code>对象使用此值创建适当的边距。
      * 但是,如果在工具栏上设置了非默认边框,那么<code> Border </code>对象负责创建相应的边距空间(否则此属性将被有效忽略)。
      * 
      * 
      * @param m an <code>Insets</code> object that defines the space
      *         between the border and the buttons
      * @see Insets
      * @beaninfo
      * description: The margin between the tool bar's border and contents
      *       bound: true
      *      expert: true
      */
     public void setMargin(Insets m)
     {
         Insets old = margin;
         margin = m;
         firePropertyChange("margin", old, m);
         revalidate();
         repaint();
     }

     /**
      * Returns the margin between the tool bar's border and
      * its buttons.
      *
      * <p>
      *  返回工具栏边框和其按钮之间的边距。
      * 
      * 
      * @return an <code>Insets</code> object containing the margin values
      * @see Insets
      */
     public Insets getMargin()
     {
         if(margin == null) {
             return new Insets(0,0,0,0);
         } else {
             return margin;
         }
     }

     /**
      * Gets the <code>borderPainted</code> property.
      *
      * <p>
      *  获取<code> borderPainted </code>属性。
      * 
      * 
      * @return the value of the <code>borderPainted</code> property
      * @see #setBorderPainted
      */
     public boolean isBorderPainted()
     {
         return paintBorder;
     }


     /**
      * Sets the <code>borderPainted</code> property, which is
      * <code>true</code> if the border should be painted.
      * The default value for this property is <code>true</code>.
      * Some look and feels might not implement painted borders;
      * they will ignore this property.
      *
      * <p>
      *  设置<code> borderPainted </code>属性,如果应该绘制边框,则为<code> true </code>。此属性的默认值为<code> true </code>。
      * 一些外观和感觉可能不实现画边界;他们将忽略此属性。
      * 
      * 
      * @param b if true, the border is painted
      * @see #isBorderPainted
      * @beaninfo
      * description: Does the tool bar paint its borders?
      *       bound: true
      *      expert: true
      */
     public void setBorderPainted(boolean b)
     {
         if ( paintBorder != b )
         {
             boolean old = paintBorder;
             paintBorder = b;
             firePropertyChange("borderPainted", old, b);
             revalidate();
             repaint();
         }
     }

     /**
      * Paints the tool bar's border if the <code>borderPainted</code> property
      * is <code>true</code>.
      *
      * <p>
      *  如果<code> borderPainted </code>属性为<code> true </code>,则绘制工具栏的边框。
      * 
      * 
      * @param g  the <code>Graphics</code> context in which the painting
      *         is done
      * @see JComponent#paint
      * @see JComponent#setBorder
      */
     protected void paintBorder(Graphics g)
     {
         if (isBorderPainted())
         {
             super.paintBorder(g);
         }
     }

    /**
     * Gets the <code>floatable</code> property.
     *
     * <p>
     *  获取<code> floatable </code>属性。
     * 
     * 
     * @return the value of the <code>floatable</code> property
     *
     * @see #setFloatable
     */
    public boolean isFloatable()
    {
        return floatable;
    }

     /**
      * Sets the <code>floatable</code> property,
      * which must be <code>true</code> for the user to move the tool bar.
      * Typically, a floatable tool bar can be
      * dragged into a different position within the same container
      * or out into its own window.
      * The default value of this property is <code>true</code>.
      * Some look and feels might not implement floatable tool bars;
      * they will ignore this property.
      *
      * <p>
      *  设置<code> floatable </code>属性,该属性必须为<code> true </code>,以便用户移动工具栏。
      * 通常,可漂浮的工具条可以被拖动到同一容器内的不同位置或者进入其自己的窗口中。此属性的默认值为<code> true </code>。一些外观和感觉可能不实现可浮动工具栏;他们将忽略此属性。
      * 
      * 
      * @param b if <code>true</code>, the tool bar can be moved;
      *          <code>false</code> otherwise
      * @see #isFloatable
      * @beaninfo
      * description: Can the tool bar be made to float by the user?
      *       bound: true
      *   preferred: true
      */
    public void setFloatable( boolean b )
    {
        if ( floatable != b )
        {
            boolean old = floatable;
            floatable = b;

            firePropertyChange("floatable", old, b);
            revalidate();
            repaint();
        }
    }

    /**
     * Returns the current orientation of the tool bar.  The value is either
     * <code>HORIZONTAL</code> or <code>VERTICAL</code>.
     *
     * <p>
     *  返回工具栏的当前方向。该值为<code> HORIZONTAL </code>或<code> VERTICAL </code>。
     * 
     * 
     * @return an integer representing the current orientation -- either
     *          <code>HORIZONTAL</code> or <code>VERTICAL</code>
     * @see #setOrientation
     */
    public int getOrientation()
    {
        return this.orientation;
    }

    /**
     * Sets the orientation of the tool bar.  The orientation must have
     * either the value <code>HORIZONTAL</code> or <code>VERTICAL</code>.
     * If <code>orientation</code> is
     * an invalid value, an exception will be thrown.
     *
     * <p>
     * 设置工具栏的方向。方向必须具有值<code> HORIZONTAL </code>或<code> VERTICAL </code>。
     * 如果<code> orientation </code>是无效值,则会抛出异常。
     * 
     * 
     * @param o  the new orientation -- either <code>HORIZONTAL</code> or
     *                  <code>VERTICAL</code>
     * @exception IllegalArgumentException if orientation is neither
     *          <code>HORIZONTAL</code> nor <code>VERTICAL</code>
     * @see #getOrientation
     * @beaninfo
     * description: The current orientation of the tool bar
     *       bound: true
     *   preferred: true
     *        enum: HORIZONTAL SwingConstants.HORIZONTAL
     *              VERTICAL   SwingConstants.VERTICAL
     */
    public void setOrientation( int o )
    {
        checkOrientation( o );

        if ( orientation != o )
        {
            int old = orientation;
            orientation = o;

            firePropertyChange("orientation", old, o);
            revalidate();
            repaint();
        }
    }

    /**
     * Sets the rollover state of this toolbar. If the rollover state is true
     * then the border of the toolbar buttons will be drawn only when the
     * mouse pointer hovers over them. The default value of this property
     * is false.
     * <p>
     * The implementation of a look and feel may choose to ignore this
     * property.
     *
     * <p>
     *  设置此工具栏的翻转状态。如果滚动状态为true,则仅当鼠标指针悬停在工具栏按钮的边框上时,才会绘制工具栏按钮的边框。此属性的默认值为false。
     * <p>
     *  执行一个外观和感觉可以选择忽略这个属性。
     * 
     * 
     * @param rollover true for rollover toolbar buttons; otherwise false
     * @since 1.4
     * @beaninfo
     *        bound: true
     *    preferred: true
     *    attribute: visualUpdate true
     *  description: Will draw rollover button borders in the toolbar.
     */
    public void setRollover(boolean rollover) {
        putClientProperty("JToolBar.isRollover",
                          rollover ? Boolean.TRUE : Boolean.FALSE);
    }

    /**
     * Returns the rollover state.
     *
     * <p>
     *  返回翻转状态。
     * 
     * 
     * @return true if rollover toolbar buttons are to be drawn; otherwise false
     * @see #setRollover(boolean)
     * @since 1.4
     */
    public boolean isRollover() {
        Boolean rollover = (Boolean)getClientProperty("JToolBar.isRollover");
        if (rollover != null) {
            return rollover.booleanValue();
        }
        return false;
    }

    private void checkOrientation( int orientation )
    {
        switch ( orientation )
        {
            case VERTICAL:
            case HORIZONTAL:
                break;
            default:
                throw new IllegalArgumentException( "orientation must be one of: VERTICAL, HORIZONTAL" );
        }
    }

    /**
     * Appends a separator of default size to the end of the tool bar.
     * The default size is determined by the current look and feel.
     * <p>
     *  将默认大小的分隔符附加到工具栏的末尾。默认大小由当前的外观和感觉确定。
     * 
     */
    public void addSeparator()
    {
        addSeparator(null);
    }

    /**
     * Appends a separator of a specified size to the end
     * of the tool bar.
     *
     * <p>
     *  将指定大小的分隔符附加到工具栏的末尾。
     * 
     * 
     * @param size the <code>Dimension</code> of the separator
     */
    public void addSeparator( Dimension size )
    {
        JToolBar.Separator s = new JToolBar.Separator( size );
        add(s);
    }

    /**
     * Adds a new <code>JButton</code> which dispatches the action.
     *
     * <p>
     *  添加一个新的<code> JButton </code>,用于分派操作。
     * 
     * 
     * @param a the <code>Action</code> object to add as a new menu item
     * @return the new button which dispatches the action
     */
    public JButton add(Action a) {
        JButton b = createActionComponent(a);
        b.setAction(a);
        add(b);
        return b;
    }

    /**
     * Factory method which creates the <code>JButton</code> for
     * <code>Action</code>s added to the <code>JToolBar</code>.
     * The default name is empty if a <code>null</code> action is passed.
     *
     * <p>
     *  为<code> Action </code>创建<code> JButton </code>的工厂方法添加到<code> JToolBar </code>。
     * 如果传递<code> null </code>操作,则默认名称为空。
     * 
     * 
     * @param a the <code>Action</code> for the button to be added
     * @return the newly created button
     * @see Action
     * @since 1.3
     */
    protected JButton createActionComponent(Action a) {
        JButton b = new JButton() {
            protected PropertyChangeListener createActionPropertyChangeListener(Action a) {
                PropertyChangeListener pcl = createActionChangeListener(this);
                if (pcl==null) {
                    pcl = super.createActionPropertyChangeListener(a);
                }
                return pcl;
            }
        };
        if (a != null && (a.getValue(Action.SMALL_ICON) != null ||
                          a.getValue(Action.LARGE_ICON_KEY) != null)) {
            b.setHideActionText(true);
        }
        b.setHorizontalTextPosition(JButton.CENTER);
        b.setVerticalTextPosition(JButton.BOTTOM);
        return b;
    }

    /**
     * Returns a properly configured <code>PropertyChangeListener</code>
     * which updates the control as changes to the <code>Action</code> occur,
     * or <code>null</code> if the default
     * property change listener for the control is desired.
     *
     * <p>
     *  返回正确配置的<code> PropertyChangeListener </code>,如果需要控件的默认属性更改侦听器,则会更新控件作为对<code> Action </code>的更改或<code>
     *  null </code> 。
     * 
     * 
     * @return <code>null</code>
     */
    protected PropertyChangeListener createActionChangeListener(JButton b) {
        return null;
    }

    /**
     * If a <code>JButton</code> is being added, it is initially
     * set to be disabled.
     *
     * <p>
     *  如果正在添加<code> JButton </code>,它最初设置为禁用。
     * 
     * 
     * @param comp  the component to be enhanced
     * @param constraints  the constraints to be enforced on the component
     * @param index the index of the component
     *
     */
    protected void addImpl(Component comp, Object constraints, int index) {
        if (comp instanceof Separator) {
            if (getOrientation() == VERTICAL) {
                ( (Separator)comp ).setOrientation(JSeparator.HORIZONTAL);
            } else {
                ( (Separator)comp ).setOrientation(JSeparator.VERTICAL);
            }
        }
        super.addImpl(comp, constraints, index);
        if (comp instanceof JButton) {
            ((JButton)comp).setDefaultCapable(false);
        }
    }


    /**
     * A toolbar-specific separator. An object with dimension but
     * no contents used to divide buttons on a tool bar into groups.
     * <p>
     *  工具栏特定的分隔符。具有尺寸但没有内容的对象用于将工具栏上的按钮分成组。
     * 
     */
    static public class Separator extends JSeparator
    {
        private Dimension separatorSize;

        /**
         * Creates a new toolbar separator with the default size
         * as defined by the current look and feel.
         * <p>
         *  创建一个新的工具栏分隔符,其默认大小由当前外观定义。
         * 
         */
        public Separator()
        {
            this( null );  // let the UI define the default size
        }

        /**
         * Creates a new toolbar separator with the specified size.
         *
         * <p>
         * 创建具有指定大小的新工具栏分隔符。
         * 
         * 
         * @param size the <code>Dimension</code> of the separator
         */
        public Separator( Dimension size )
        {
            super( JSeparator.HORIZONTAL );
            setSeparatorSize(size);
        }

        /**
         * Returns the name of the L&amp;F class that renders this component.
         *
         * <p>
         *  返回呈现此组件的L&amp; F类的名称。
         * 
         * 
         * @return the string "ToolBarSeparatorUI"
         * @see JComponent#getUIClassID
         * @see UIDefaults#getUI
         */
        public String getUIClassID()
        {
            return "ToolBarSeparatorUI";
        }

        /**
         * Sets the size of the separator.
         *
         * <p>
         *  设置分隔符的大小。
         * 
         * 
         * @param size the new <code>Dimension</code> of the separator
         */
        public void setSeparatorSize( Dimension size )
        {
            if (size != null) {
                separatorSize = size;
            } else {
                super.updateUI();
            }
            this.invalidate();
        }

        /**
         * Returns the size of the separator
         *
         * <p>
         *  返回分隔符的大小
         * 
         * 
         * @return the <code>Dimension</code> object containing the separator's
         *         size (This is a reference, NOT a copy!)
         */
        public Dimension getSeparatorSize()
        {
            return separatorSize;
        }

        /**
         * Returns the minimum size for the separator.
         *
         * <p>
         *  返回分隔符的最小大小。
         * 
         * 
         * @return the <code>Dimension</code> object containing the separator's
         *         minimum size
         */
        public Dimension getMinimumSize()
        {
            if (separatorSize != null) {
                return separatorSize.getSize();
            } else {
                return super.getMinimumSize();
            }
        }

        /**
         * Returns the maximum size for the separator.
         *
         * <p>
         *  返回分隔符的最大大小。
         * 
         * 
         * @return the <code>Dimension</code> object containing the separator's
         *         maximum size
         */
        public Dimension getMaximumSize()
        {
            if (separatorSize != null) {
                return separatorSize.getSize();
            } else {
                return super.getMaximumSize();
            }
        }

        /**
         * Returns the preferred size for the separator.
         *
         * <p>
         *  返回分隔符的首选大小。
         * 
         * 
         * @return the <code>Dimension</code> object containing the separator's
         *         preferred size
         */
        public Dimension getPreferredSize()
        {
            if (separatorSize != null) {
                return separatorSize.getSize();
            } else {
                return super.getPreferredSize();
            }
        }
    }


    /**
     * See <code>readObject</code> and <code>writeObject</code> in
     * <code>JComponent</code> for more
     * information about serialization in Swing.
     * <p>
     *  有关Swing中序列化的更多信息,请参阅<code> readComponent </code>中的<code> readObject </code>和<code> writeObject </code>
     * 。
     * 
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        if (getUIClassID().equals(uiClassID)) {
            byte count = JComponent.getWriteObjCounter(this);
            JComponent.setWriteObjCounter(this, --count);
            if (count == 0 && ui != null) {
                ui.installUI(this);
            }
        }
    }


    /**
     * Returns a string representation of this <code>JToolBar</code>.
     * This method
     * is intended to be used only for debugging purposes, and the
     * content and format of the returned string may vary between
     * implementations. The returned string may be empty but may not
     * be <code>null</code>.
     *
     * <p>
     *  返回此<code> JToolBar </code>的字符串表示形式。此方法仅用于调试目的,并且返回的字符串的内容和格式可能因实现而异。
     * 返回的字符串可能为空,但可能不是<code> null </code>。
     * 
     * 
     * @return  a string representation of this <code>JToolBar</code>.
     */
    protected String paramString() {
        String paintBorderString = (paintBorder ?
                                    "true" : "false");
        String marginString = (margin != null ?
                               margin.toString() : "");
        String floatableString = (floatable ?
                                  "true" : "false");
        String orientationString = (orientation == HORIZONTAL ?
                                    "HORIZONTAL" : "VERTICAL");

        return super.paramString() +
        ",floatable=" + floatableString +
        ",margin=" + marginString +
        ",orientation=" + orientationString +
        ",paintBorder=" + paintBorderString;
    }


    private class DefaultToolBarLayout
        implements LayoutManager2, Serializable, PropertyChangeListener, UIResource {

        BoxLayout lm;

        DefaultToolBarLayout(int orientation) {
            if (orientation == JToolBar.VERTICAL) {
                lm = new BoxLayout(JToolBar.this, BoxLayout.PAGE_AXIS);
            } else {
                lm = new BoxLayout(JToolBar.this, BoxLayout.LINE_AXIS);
            }
        }

        public void addLayoutComponent(String name, Component comp) {
            lm.addLayoutComponent(name, comp);
        }

        public void addLayoutComponent(Component comp, Object constraints) {
            lm.addLayoutComponent(comp, constraints);
        }

        public void removeLayoutComponent(Component comp) {
            lm.removeLayoutComponent(comp);
        }

        public Dimension preferredLayoutSize(Container target) {
            return lm.preferredLayoutSize(target);
        }

        public Dimension minimumLayoutSize(Container target) {
            return lm.minimumLayoutSize(target);
        }

        public Dimension maximumLayoutSize(Container target) {
            return lm.maximumLayoutSize(target);
        }

        public void layoutContainer(Container target) {
            lm.layoutContainer(target);
        }

        public float getLayoutAlignmentX(Container target) {
            return lm.getLayoutAlignmentX(target);
        }

        public float getLayoutAlignmentY(Container target) {
            return lm.getLayoutAlignmentY(target);
        }

        public void invalidateLayout(Container target) {
            lm.invalidateLayout(target);
        }

        public void propertyChange(PropertyChangeEvent e) {
            String name = e.getPropertyName();
            if( name.equals("orientation") ) {
                int o = ((Integer)e.getNewValue()).intValue();

                if (o == JToolBar.VERTICAL)
                    lm = new BoxLayout(JToolBar.this, BoxLayout.PAGE_AXIS);
                else {
                    lm = new BoxLayout(JToolBar.this, BoxLayout.LINE_AXIS);
                }
            }
        }
    }


    public void setLayout(LayoutManager mgr) {
        LayoutManager oldMgr = getLayout();
        if (oldMgr instanceof PropertyChangeListener) {
            removePropertyChangeListener((PropertyChangeListener)oldMgr);
        }
        super.setLayout(mgr);
    }

/////////////////
// Accessibility support
////////////////

    /**
     * Gets the AccessibleContext associated with this JToolBar.
     * For tool bars, the AccessibleContext takes the form of an
     * AccessibleJToolBar.
     * A new AccessibleJToolBar instance is created if necessary.
     *
     * <p>
     *  获取与此JToolBar相关联的AccessibleContext。对于工具栏,AccessibleContext采用AccessibleJToolBar的形式。
     * 如果需要,将创建一个新的AccessibleJToolBar实例。
     * 
     * 
     * @return an AccessibleJToolBar that serves as the
     *         AccessibleContext of this JToolBar
     */
    public AccessibleContext getAccessibleContext() {
        if (accessibleContext == null) {
            accessibleContext = new AccessibleJToolBar();
        }
        return accessibleContext;
    }

    /**
     * This class implements accessibility support for the
     * <code>JToolBar</code> class.  It provides an implementation of the
     * Java Accessibility API appropriate to toolbar user-interface elements.
     * <p>
     *  此类实现了对<code> JToolBar </code>类的辅助功能支持。它提供了适用于工具栏用户界面元素的Java辅助功能API的实现。
     * 
     */
    protected class AccessibleJToolBar extends AccessibleJComponent {

        /**
         * Get the state of this object.
         *
         * <p>
         *  获取此对象的状态。
         * 
         * 
         * @return an instance of AccessibleStateSet containing the current
         * state set of the object
         * @see AccessibleState
         */
        public AccessibleStateSet getAccessibleStateSet() {
            AccessibleStateSet states = super.getAccessibleStateSet();
            // FIXME:  [[[WDW - need to add orientation from BoxLayout]]]
            // FIXME:  [[[WDW - need to do SELECTABLE if SelectionModel is added]]]
            return states;
        }

        /**
         * Get the role of this object.
         *
         * <p>
         *  获取此对象的作用。
         * 
         * @return an instance of AccessibleRole describing the role of the object
         */
        public AccessibleRole getAccessibleRole() {
            return AccessibleRole.TOOL_BAR;
        }
    } // inner class AccessibleJToolBar
}
