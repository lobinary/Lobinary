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

/** DesktopManager objects are owned by a JDesktopPane object. They are responsible
  * for implementing L&amp;F specific behaviors for the JDesktopPane. JInternalFrame
  * implementations should delegate specific behaviors to the DesktopManager. For
  * instance, if a JInternalFrame was asked to iconify, it should try:
  * <PRE>
  *    getDesktopPane().getDesktopManager().iconifyFrame(frame);
  * </PRE>
  * This delegation allows each L&amp;F to provide custom behaviors for desktop-specific
  * actions. (For example, how and where the internal frame's icon would appear.)
  * <p>This class provides a policy for the various JInternalFrame methods, it is not
  * meant to be called directly rather the various JInternalFrame methods will call
  * into the DesktopManager.</p>
  *
  * <p>
  *  用于实现JDesktopPane的L&amp; F特定行为。 JInternalFrame实现应该将特定的行为委托给DesktopManager。
  * 例如,如果一个JInternalFrame被要求iconify,它应该尝试：。
  * <PRE>
  *  getDesktopPane()。getDesktopManager()。iconifyFrame(frame);
  * </PRE>
  *  此委派允许每个L&amp; F提供针对桌面特定动作的自定义行为。 (例如,内部框架图标的显示方式和位置。
  * )<p>此类为各种JInternalFrame方法提供了一个策略,它不是直接调用,而是各种JInternalFrame方法将调用到DesktopManager中。</p>。
  * 
  * 
  * @see JDesktopPane
  * @see JInternalFrame
  * @see JInternalFrame.JDesktopIcon
  *
  * @author David Kloba
  */
public interface DesktopManager
{
    /** If possible, display this frame in an appropriate location.
      * Normally, this is not called, as the creator of the JInternalFrame
      * will add the frame to the appropriate parent.
      * <p>
      *  通常,这不被调用,因为JInternalFrame的创建者将框架添加到适当的父。
      * 
      */
    void openFrame(JInternalFrame f);

    /** Generally, this call should remove the frame from it's parent. */
    void closeFrame(JInternalFrame f);

    /** Generally, the frame should be resized to match it's parents bounds. */
    void maximizeFrame(JInternalFrame f);
    /** Generally, this indicates that the frame should be restored to it's
      * size and position prior to a maximizeFrame() call.
      * <p>
      *  大小和位置在最大化帧()调用之前。
      * 
      */
    void minimizeFrame(JInternalFrame f);
    /** Generally, remove this frame from it's parent and add an iconic representation. */
    void iconifyFrame(JInternalFrame f);
    /** Generally, remove any iconic representation that is present and restore the
      * frame to it's original size and location.
      * <p>
      *  框架到它的原始大小和位置。
      * 
      */
    void deiconifyFrame(JInternalFrame f);

    /**
     * Generally, indicate that this frame has focus. This is usually called after
     * the JInternalFrame's IS_SELECTED_PROPERTY has been set to true.
     * <p>
     *  通常,指示此帧具有焦点。这通常在JInternalFrame的IS_SELECTED_PROPERTY设置为true之后调用。
     * 
     */
    void activateFrame(JInternalFrame f);

    /**
     * Generally, indicate that this frame has lost focus. This is usually called
     * after the JInternalFrame's IS_SELECTED_PROPERTY has been set to false.
     * <p>
     *  通常,指示此帧已失去焦点。这通常在JInternalFrame的IS_SELECTED_PROPERTY设置为false之后调用。
     * 
     */
    void deactivateFrame(JInternalFrame f);

    /** This method is normally called when the user has indicated that
      * they will begin dragging a component around. This method should be called
      * prior to any dragFrame() calls to allow the DesktopManager to prepare any
      * necessary state. Normally <b>f</b> will be a JInternalFrame.
      * <p>
      *  他们将开始拖动一个组件。此方法应在任何dragFrame()调用之前调用,以允许DesktopManager准备任何必要的状态。通常<b> f </b>将是一个JInternalFrame。
      * 
      */
    void beginDraggingFrame(JComponent f);

    /** The user has moved the frame. Calls to this method will be preceded by calls
      * to beginDraggingFrame().
      *  Normally <b>f</b> will be a JInternalFrame.
      * <p>
      * to beginDraggingFrame()。通常<b> f </b>将是一个JInternalFrame。
      * 
      */
    void dragFrame(JComponent f, int newX, int newY);
    /** This method signals the end of the dragging session. Any state maintained by
      * the DesktopManager can be removed here.  Normally <b>f</b> will be a JInternalFrame.
      * <p>
      *  可以在此处删除DesktopManager。通常<b> f </b>将是一个JInternalFrame。
      * 
      */
    void endDraggingFrame(JComponent f);

    /** This methods is normally called when the user has indicated that
      * they will begin resizing the frame. This method should be called
      * prior to any resizeFrame() calls to allow the DesktopManager to prepare any
      * necessary state.  Normally <b>f</b> will be a JInternalFrame.
      * <p>
      *  他们将开始调整框架大小。此方法应在任何resizeFrame()调用之前调用,以允许DesktopManager准备任何必要的状态。通常<b> f </b>将是一个JInternalFrame。
      * 
      */
    void beginResizingFrame(JComponent f, int direction);
    /** The user has resized the component. Calls to this method will be preceded by calls
      * to beginResizingFrame().
      *  Normally <b>f</b> will be a JInternalFrame.
      * <p>
      *  to beginResizingFrame()。通常<b> f </b>将是一个JInternalFrame。
      * 
      */
    void resizeFrame(JComponent f, int newX, int newY, int newWidth, int newHeight);
    /** This method signals the end of the resize session. Any state maintained by
      * the DesktopManager can be removed here.  Normally <b>f</b> will be a JInternalFrame.
      * <p>
      *  可以在此处删除DesktopManager。通常<b> f </b>将是一个JInternalFrame。
      */
    void endResizingFrame(JComponent f);

    /** This is a primitive reshape method.*/
    void setBoundsForFrame(JComponent f, int newX, int newY, int newWidth, int newHeight);
}
