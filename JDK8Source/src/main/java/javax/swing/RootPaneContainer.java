/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Component;
import java.awt.Container;


/**
 * This interface is implemented by components that have a single
 * JRootPane child: JDialog, JFrame, JWindow, JApplet, JInternalFrame.
 * The methods in  this interface are just <i>covers</i> for the JRootPane
 * properties, e.g. <code>getContentPane()</code> is generally implemented
 * like this:<pre>
 *     public Container getContentPane() {
 *         return getRootPane().getContentPane();
 *     }
 * </pre>
 * This interface serves as a <i>marker</i> for Swing GUI builders
 * that need to treat components like JFrame, that contain a
 * single JRootPane, specially.  For example in a GUI builder,
 * dropping a component on a RootPaneContainer would be interpreted
 * as <code>frame.getContentPane().add(child)</code>.
 * <p>
 * As a convenience, the standard classes that implement this interface
 * (such as {@code JFrame}, {@code JDialog}, {@code JWindow}, {@code JApplet},
 * and {@code JInternalFrame}) have their {@code add}, {@code remove},
 * and {@code setLayout} methods overridden, so that they delegate calls
 * to the corresponding methods of the {@code ContentPane}.
 * For example, you can add a child component to a frame as follows:
 * <pre>
 *       frame.add(child);
 * </pre>
 * instead of:
 * <pre>
 *       frame.getContentPane().add(child);
 * </pre>
 * <p>
 * The behavior of the <code>add</code> and
 * <code>setLayout</code> methods for
 * <code>JFrame</code>, <code>JDialog</code>, <code>JWindow</code>,
 * <code>JApplet</code> and <code>JInternalFrame</code> is controlled by
 * the <code>rootPaneCheckingEnabled</code> property. If this property is
 * true (the default), then calls to these methods are
  * forwarded to the <code>contentPane</code>; if false, these
  * methods operate directly on the <code>RootPaneContainer</code>. This
  * property is only intended for subclasses, and is therefore protected.
 *
 * <p>
 *  此接口由具有单个JRootPane子组件的组件实现：JDialog,JFrame,JWindow,JApplet,JInternalFrame。
 * 此接口中的方法只是<i>覆盖</i> JRootPane属性,例如<code> getContentPane()</code>一般实现如下：<pre> public Container getConte
 * ntPane(){return getRootPane()。
 *  此接口由具有单个JRootPane子组件的组件实现：JDialog,JFrame,JWindow,JApplet,JInternalFrame。getContentPane(); }}。
 * </pre>
 *  此接口用作Swing GUI构建器的<i>标记</i>,用于需要处理包含单个JRootPane的JFrame的组件。
 * 例如,在GUI构建器中,将组件放在RootPaneContainer上会被解释为<code> frame.getContentPane()。add(child)</code>。
 * <p>
 *  为方便起见,实现此接口的标准类(例如{@code JFrame},{@code JDialog},{@code JWindow},{@code JApplet}和{@code JInternalFrame}
 * )都有自己的{@code add},{@code remove}和{@code setLayout}方法被覆盖,因此他们将调用委托给{@code ContentPane}的相应方法。
 * 例如,您可以将子组件添加到框架,如下所示：。
 * <pre>
 *  frame.add(child);
 * </pre>
 *  代替：
 * <pre>
 *  frame.getContentPane()。add(child);
 * </pre>
 * <p>
 * <code>为<code> JFrame </code>,<code> JDialog </code>,<code> JWindow </code>添加<code> add </code>和<code>
 *  addLayout </code> <code> JApplet </code>和<code> JInternalFrame </code>由<code> rootPaneCheckingEnable
 * d </code>属性控制。
 * 如果此属性为true(默认值),那么对这些方法的调用将被转发到<code> contentPane </code>;如果为false,这些方法直接在<code> RootPaneContainer </code>
 * 上操作。
 * 此属性仅适用于子类,因此受保护。
 * 
 * 
 * @see JRootPane
 * @see JFrame
 * @see JDialog
 * @see JWindow
 * @see JApplet
 * @see JInternalFrame
 *
 * @author Hans Muller
 */
public interface RootPaneContainer
{
    /**
     * Return this component's single JRootPane child.  A conventional
     * implementation of this interface will have all of the other
     * methods indirect through this one.  The rootPane has two
     * children: the glassPane and the layeredPane.
     *
     * <p>
     *  返回此组件的单个JRootPane子项。该接口的常规实现将通过该接口间接地具有所有其它方法。 rootPane有两个孩子：glassPane和layeredPane。
     * 
     * 
     * @return this components single JRootPane child.
     * @see JRootPane
     */
    JRootPane getRootPane();


    /**
     * The "contentPane" is the primary container for application
     * specific components.  Applications should add children to
     * the contentPane, set its layout manager, and so on.
     * <p>
     * The contentPane may not be null.
     * <p>
     * Generally implemented with
     * <code>getRootPane().setContentPane(contentPane);</code>
     *
     * <p>
     *  "contentPane"是应用程序特定组件的主容器。应用程序应该将内容添加到contentPane,设置其布局管理器,等等。
     * <p>
     *  contentPane不能为空。
     * <p>
     *  一般用<code> getRootPane()。setContentPane(contentPane); </code>实现
     * 
     * 
     * @exception java.awt.IllegalComponentStateException (a runtime
     *            exception) if the content pane parameter is null
     * @param contentPane the Container to use for the contents of this
     *        JRootPane
     * @see JRootPane#getContentPane
     * @see #getContentPane
     */
    void setContentPane(Container contentPane);


    /**
     * Returns the contentPane.
     *
     * <p>
     *  返回contentPane。
     * 
     * 
     * @return the value of the contentPane property.
     * @see #setContentPane
     */
    Container getContentPane();


    /**
     * A Container that manages the contentPane and in some cases a menu bar.
     * The layeredPane can be used by descendants that want to add a child
     * to the RootPaneContainer that isn't layout managed.  For example
     * an internal dialog or a drag and drop effect component.
     * <p>
     * The layeredPane may not be null.
     * <p>
     * Generally implemented with<pre>
     *    getRootPane().setLayeredPane(layeredPane);</pre>
     *
     * <p>
     *  一个容器,管理contentPane,在某些情况下管理一个菜单栏。 layeredPane可以由希望向未由布局管理的RootPaneContainer添加子代的后代使用。
     * 例如内部对话框或拖放效果组件。
     * <p>
     *  layeredPane不能为空。
     * <p>
     *  一般用<pre> getRootPane()。setLayeredPane(layeredPane); </pre>
     * 
     * 
     * @exception java.awt.IllegalComponentStateException (a runtime
     *            exception) if the layered pane parameter is null
     * @see #getLayeredPane
     * @see JRootPane#getLayeredPane
     */
    void setLayeredPane(JLayeredPane layeredPane);


    /**
     * Returns the layeredPane.
     *
     * <p>
     *  返回layeredPane。
     * 
     * 
     * @return the value of the layeredPane property.
     * @see #setLayeredPane
     */
    JLayeredPane getLayeredPane();


    /**
     * The glassPane is always the first child of the rootPane
     * and the rootPanes layout manager ensures that it's always
     * as big as the rootPane.  By default it's transparent and
     * not visible.  It can be used to temporarily grab all keyboard
     * and mouse input by adding listeners and then making it visible.
     * by default it's not visible.
     * <p>
     * The glassPane may not be null.
     * <p>
     * Generally implemented with
     * <code>getRootPane().setGlassPane(glassPane);</code>
     *
     * <p>
     * glassPane总是rootPane的第一个孩子,而rootPanes布局管理器确保它总是和rootPane一样大。默认情况下,它是透明的,不可见。
     * 它可以用于临时抓取所有键盘和鼠标输入通过添加侦听器,然后使其可见。默认情况下不可见。
     * <p>
     *  glassPane不能为空。
     * <p>
     * 
     * @see #getGlassPane
     * @see JRootPane#setGlassPane
     */
    void setGlassPane(Component glassPane);


    /**
     * Returns the glassPane.
     *
     * <p>
     *  一般用<code> getRootPane()。setGlassPane(glassPane); </code>实现
     * 
     * 
     * @return the value of the glassPane property.
     * @see #setGlassPane
     */
    Component getGlassPane();

}
