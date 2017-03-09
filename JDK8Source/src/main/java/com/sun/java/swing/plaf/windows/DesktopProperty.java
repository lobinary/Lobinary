/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2009, Oracle and/or its affiliates. All rights reserved.
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
package com.sun.java.swing.plaf.windows;

import java.awt.*;
import java.beans.*;
import java.lang.ref.*;
import javax.swing.*;
import javax.swing.plaf.*;

/**
 * Wrapper for a value from the desktop. The value is lazily looked up, and
 * can be accessed using the <code>UIManager.ActiveValue</code> method
 * <code>createValue</code>. If the underlying desktop property changes this
 * will force the UIs to update all known Frames. You can invoke
 * <code>invalidate</code> to force the value to be fetched again.
 *
 * <p>
 *  包装来自桌面的值。该值被懒洋洋地查找,并且可以使用<code> UIManager.ActiveValue </code>方法<code> createValue </code>来访问。
 * 如果底层桌面属性更改,这将强制UI更新所有已知的框架。您可以调用<code> invalidate </code>强制重新获取该值。
 * 
 */
// NOTE: Don't rely on this class staying in this location. It is likely
// to move to a different package in the future.
public class DesktopProperty implements UIDefaults.ActiveValue {
    /**
     * Indicates if an updateUI call is pending.
     * <p>
     *  指示updateUI调用是否挂起。
     * 
     */
    private static boolean updatePending;

    /**
     * ReferenceQueue of unreferenced WeakPCLs.
     * <p>
     *  参考没有引用WeakPCLs的队列。
     * 
     */
    private static final ReferenceQueue<DesktopProperty> queue = new ReferenceQueue<DesktopProperty>();

    /**
     * PropertyChangeListener attached to the Toolkit.
     * <p>
     *  PropertyChangeListener附加到Toolkit。
     * 
     */
    private WeakPCL pcl;
    /**
     * Key used to lookup value from desktop.
     * <p>
     *  用于从桌面查找值的键。
     * 
     */
    private final String key;
    /**
     * Value to return.
     * <p>
     *  返回值。
     * 
     */
    private Object value;
    /**
     * Fallback value in case we get null from desktop.
     * <p>
     *  备用值,以防我们从桌面收到null。
     * 
     */
    private final Object fallback;


    /**
     * Cleans up any lingering state held by unrefeernced
     * DesktopProperties.
     * <p>
     *  清理由未确定的DesktopProperties持有的任何延迟状态。
     * 
     */
    static void flushUnreferencedProperties() {
        WeakPCL pcl;

        while ((pcl = (WeakPCL)queue.poll()) != null) {
            pcl.dispose();
        }
    }


    /**
     * Sets whether or not an updateUI call is pending.
     * <p>
     *  设置updateUI调用是否正在等待。
     * 
     */
    private static synchronized void setUpdatePending(boolean update) {
        updatePending = update;
    }

    /**
     * Returns true if a UI update is pending.
     * <p>
     *  如果UI更新待处理,则返回true。
     * 
     */
    private static synchronized boolean isUpdatePending() {
        return updatePending;
    }

    /**
     * Updates the UIs of all the known Frames.
     * <p>
     *  更新所有已知帧的UI。
     * 
     */
    private static void updateAllUIs() {
        // Check if the current UI is WindowsLookAndfeel and flush the XP style map.
        // Note: Change the package test if this class is moved to a different package.
        Class uiClass = UIManager.getLookAndFeel().getClass();
        if (uiClass.getPackage().equals(DesktopProperty.class.getPackage())) {
            XPStyle.invalidateStyle();
        }
        Frame appFrames[] = Frame.getFrames();
        for (Frame appFrame : appFrames) {
            updateWindowUI(appFrame);
        }
    }

    /**
     * Updates the UI of the passed in window and all its children.
     * <p>
     *  更新传入的窗口及其所有子窗口的UI。
     * 
     */
    private static void updateWindowUI(Window window) {
        SwingUtilities.updateComponentTreeUI(window);
        Window ownedWins[] = window.getOwnedWindows();
        for (Window ownedWin : ownedWins) {
            updateWindowUI(ownedWin);
        }
    }


    /**
     * Creates a DesktopProperty.
     *
     * <p>
     *  创建DesktopProperty。
     * 
     * 
     * @param key Key used in looking up desktop value.
     * @param fallback Value used if desktop property is null.
     */
    public DesktopProperty(String key, Object fallback) {
        this.key = key;
        this.fallback = fallback;
        // The only sure fire way to clear our references is to create a
        // Thread and wait for a reference to be added to the queue.
        // Because it is so rare that you will actually change the look
        // and feel, this stepped is forgoed and a middle ground of
        // flushing references from the constructor is instead done.
        // The implication is that once one DesktopProperty is created
        // there will most likely be n (number of DesktopProperties created
        // by the LookAndFeel) WeakPCLs around, but this number will not
        // grow past n.
        flushUnreferencedProperties();
    }

    /**
     * UIManager.LazyValue method, returns the value from the desktop
     * or the fallback value if the desktop value is null.
     * <p>
     *  UIManager.LazyValue方法,如果桌面值为null,则返回桌面值或后备值。
     * 
     */
    public Object createValue(UIDefaults table) {
        if (value == null) {
            value = configureValue(getValueFromDesktop());
            if (value == null) {
                value = configureValue(getDefaultValue());
            }
        }
        return value;
    }

    /**
     * Returns the value from the desktop.
     * <p>
     *  从桌面返回值。
     * 
     */
    protected Object getValueFromDesktop() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();

        if (pcl == null) {
            pcl = new WeakPCL(this, getKey(), UIManager.getLookAndFeel());
            toolkit.addPropertyChangeListener(getKey(), pcl);
        }

        return toolkit.getDesktopProperty(getKey());
    }

    /**
     * Returns the value to use if the desktop property is null.
     * <p>
     *  返回桌面属性为null时要使用的值。
     * 
     */
    protected Object getDefaultValue() {
        return fallback;
    }

    /**
     * Invalidates the current value.
     *
     * <p>
     *  使当前值无效。
     * 
     * 
     * @param laf the LookAndFeel this DesktopProperty was created with
     */
    public void invalidate(LookAndFeel laf) {
        invalidate();
    }

    /**
     * Invalides the current value so that the next invocation of
     * <code>createValue</code> will ask for the property again.
     * <p>
     *  使当前值无效,以使下一次调用<code> createValue </code>时再次请求该属性。
     * 
     */
    public void invalidate() {
        value = null;
    }

    /**
     * Requests that all components in the GUI hierarchy be updated
     * to reflect dynamic changes in this look&feel.  This update occurs
     * by uninstalling and re-installing the UI objects. Requests are
     * batched and collapsed into a single update pass because often
     * many desktop properties will change at once.
     * <p>
     * 请求更新GUI层次结构中的所有组件以反映此外观和感觉的动态更改。通过卸载并重新安装UI对象来进行此更新。请求被批量处理并折叠为单个更新通知,因为很多桌面属性会同时更改。
     * 
     */
    protected void updateUI() {
        if (!isUpdatePending()) {
            setUpdatePending(true);
            Runnable uiUpdater = new Runnable() {
                public void run() {
                    updateAllUIs();
                    setUpdatePending(false);
                }
            };
            SwingUtilities.invokeLater(uiUpdater);
        }
    }

    /**
     * Configures the value as appropriate for a defaults property in
     * the UIDefaults table.
     * <p>
     *  在UIDefaults表中为defaults属性配置适当的值。
     * 
     */
    protected Object configureValue(Object value) {
        if (value != null) {
            if (value instanceof Color) {
                return new ColorUIResource((Color)value);
            }
            else if (value instanceof Font) {
                return new FontUIResource((Font)value);
            }
            else if (value instanceof UIDefaults.LazyValue) {
                value = ((UIDefaults.LazyValue)value).createValue(null);
            }
            else if (value instanceof UIDefaults.ActiveValue) {
                value = ((UIDefaults.ActiveValue)value).createValue(null);
            }
        }
        return value;
    }

    /**
     * Returns the key used to lookup the desktop properties value.
     * <p>
     *  返回用于查找桌面属性值的键。
     * 
     */
    protected String getKey() {
        return key;
    }



    /**
     * As there is typically only one Toolkit, the PropertyChangeListener
     * is handled via a WeakReference so as not to pin down the
     * DesktopProperty.
     * <p>
     *  由于通常只有一个工具包,PropertyChangeListener通过WeakReference处理,以便不会固定下来的DesktopProperty。
     */
    private static class WeakPCL extends WeakReference<DesktopProperty>
                               implements PropertyChangeListener {
        private String key;
        private LookAndFeel laf;

        WeakPCL(DesktopProperty target, String key, LookAndFeel laf) {
            super(target, queue);
            this.key = key;
            this.laf = laf;
        }

        public void propertyChange(PropertyChangeEvent pce) {
            DesktopProperty property = get();

            if (property == null || laf != UIManager.getLookAndFeel()) {
                // The property was GC'ed, we're no longer interested in
                // PropertyChanges, remove the listener.
                dispose();
            }
            else {
                property.invalidate(laf);
                property.updateUI();
            }
        }

        void dispose() {
            Toolkit.getDefaultToolkit().removePropertyChangeListener(key, this);
        }
    }
}
