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
package javax.swing.plaf.multi;

import java.util.Vector;
import java.lang.reflect.Method;
import javax.swing.*;
import javax.swing.plaf.*;

/**
 * <p>A multiplexing look and feel that allows more than one UI
 * to be associated with a component at the same time.
 * The primary look and feel is called
 * the <em>default</em> look and feel,
 * and the other look and feels are called <em>auxiliary</em>.
 * <p>
 *
 * For further information, see
 * <a href="doc-files/multi_tsc.html" target="_top">Using the
 * Multiplexing Look and Feel.</a>
 *
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
 *  <p>多重外观,允许多个UI同时与组件相关联。主要外观和感觉称为<em>默认</em>外观,而其他外观和感觉称为<em>辅助</em>。
 * <p>
 * 
 *  有关详细信息,请参见<a href="doc-files/multi_tsc.html" target="_top">使用多路复用外观和感觉</a>。
 * 
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将与以后的Swing版本不兼容。当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 *  1.4以上,支持所有JavaBean和贸易的长期存储;已添加到<code> java.beans </code>包中。请参阅{@link java.beans.XMLEncoder}。
 * 
 * 
 * @see UIManager#addAuxiliaryLookAndFeel
 * @see javax.swing.plaf.multi
 *
 * @author Willie Walker
 */
public class MultiLookAndFeel extends LookAndFeel {

//////////////////////////////
// LookAndFeel methods
//////////////////////////////

    /**
     * Returns a string, suitable for use in menus,
     * that identifies this look and feel.
     *
     * <p>
     *  返回一个适合在菜单中使用的字符串,用于标识此外观。
     * 
     * 
     * @return a string such as "Multiplexing Look and Feel"
     */
    public String getName() {
        return "Multiplexing Look and Feel";
    }

    /**
     * Returns a string, suitable for use by applications/services,
     * that identifies this look and feel.
     *
     * <p>
     *  返回一个字符串,适合由应用程序/服务使用,以标识此外观。
     * 
     * 
     * @return "Multiplex"
     */
    public String getID() {
        return "Multiplex";
    }

    /**
     * Returns a one-line description of this look and feel.
     *
     * <p>
     *  返回此外观的单行描述。
     * 
     * 
     * @return a descriptive string such as "Allows multiple UI instances per component instance"
     */
    public String getDescription() {
        return "Allows multiple UI instances per component instance";
    }

    /**
     * Returns <code>false</code>;
     * this look and feel is not native to any platform.
     *
     * <p>
     *  返回<code> false </code>;这种外观和感觉不是原生的任何平台。
     * 
     * 
     * @return <code>false</code>
     */
    public boolean isNativeLookAndFeel() {
        return false;
    }

    /**
     * Returns <code>true</code>;
     * every platform permits this look and feel.
     *
     * <p>
     *  返回<code> true </code>;每个平台都允许这种外观和感觉。
     * 
     * 
     * @return <code>true</code>
     */
    public boolean isSupportedLookAndFeel() {
        return true;
    }

    /**
     * Creates, initializes, and returns
     * the look and feel specific defaults.
     * For this look and feel,
     * the defaults consist solely of
     * mappings of UI class IDs
     * (such as "ButtonUI")
     * to <code>ComponentUI</code> class names
     * (such as "javax.swing.plaf.multi.MultiButtonUI").
     *
     * <p>
     * 创建,初始化和返回外观和感觉特定的默认值。
     * 对于这种外观,默认值只包括UI类ID(例如"ButtonUI")到<code> ComponentUI </code>类名称(例如"javax.swing.plaf.multi.MultiButtonU
     * I")的映射。
     * 创建,初始化和返回外观和感觉特定的默认值。
     * 
     * 
     * @return an initialized <code>UIDefaults</code> object
     * @see javax.swing.JComponent#getUIClassID
     */
    public UIDefaults getDefaults() {
        String packageName = "javax.swing.plaf.multi.Multi";
        Object[] uiDefaults = {
                   "ButtonUI", packageName + "ButtonUI",
         "CheckBoxMenuItemUI", packageName + "MenuItemUI",
                 "CheckBoxUI", packageName + "ButtonUI",
             "ColorChooserUI", packageName + "ColorChooserUI",
                 "ComboBoxUI", packageName + "ComboBoxUI",
              "DesktopIconUI", packageName + "DesktopIconUI",
              "DesktopPaneUI", packageName + "DesktopPaneUI",
               "EditorPaneUI", packageName + "TextUI",
              "FileChooserUI", packageName + "FileChooserUI",
       "FormattedTextFieldUI", packageName + "TextUI",
            "InternalFrameUI", packageName + "InternalFrameUI",
                    "LabelUI", packageName + "LabelUI",
                     "ListUI", packageName + "ListUI",
                  "MenuBarUI", packageName + "MenuBarUI",
                 "MenuItemUI", packageName + "MenuItemUI",
                     "MenuUI", packageName + "MenuItemUI",
               "OptionPaneUI", packageName + "OptionPaneUI",
                    "PanelUI", packageName + "PanelUI",
            "PasswordFieldUI", packageName + "TextUI",
       "PopupMenuSeparatorUI", packageName + "SeparatorUI",
                "PopupMenuUI", packageName + "PopupMenuUI",
              "ProgressBarUI", packageName + "ProgressBarUI",
      "RadioButtonMenuItemUI", packageName + "MenuItemUI",
              "RadioButtonUI", packageName + "ButtonUI",
                 "RootPaneUI", packageName + "RootPaneUI",
                "ScrollBarUI", packageName + "ScrollBarUI",
               "ScrollPaneUI", packageName + "ScrollPaneUI",
                "SeparatorUI", packageName + "SeparatorUI",
                   "SliderUI", packageName + "SliderUI",
                  "SpinnerUI", packageName + "SpinnerUI",
                "SplitPaneUI", packageName + "SplitPaneUI",
               "TabbedPaneUI", packageName + "TabbedPaneUI",
              "TableHeaderUI", packageName + "TableHeaderUI",
                    "TableUI", packageName + "TableUI",
                 "TextAreaUI", packageName + "TextUI",
                "TextFieldUI", packageName + "TextUI",
                 "TextPaneUI", packageName + "TextUI",
             "ToggleButtonUI", packageName + "ButtonUI",
         "ToolBarSeparatorUI", packageName + "SeparatorUI",
                  "ToolBarUI", packageName + "ToolBarUI",
                  "ToolTipUI", packageName + "ToolTipUI",
                     "TreeUI", packageName + "TreeUI",
                 "ViewportUI", packageName + "ViewportUI",
        };

        UIDefaults table = new MultiUIDefaults(uiDefaults.length / 2, 0.75f);
        table.putDefaults(uiDefaults);
        return table;
    }

///////////////////////////////
// Utility methods for the UI's
///////////////////////////////

    /**
     * Creates the <code>ComponentUI</code> objects
     * required to present
     * the <code>target</code> component,
     * placing the objects in the <code>uis</code> vector and
     * returning the
     * <code>ComponentUI</code> object
     * that best represents the component's UI.
     * This method finds the <code>ComponentUI</code> objects
     * by invoking
     * <code>getDefaults().getUI(target)</code> on each
     * default and auxiliary look and feel currently in use.
     * The first UI object this method adds
     * to the <code>uis</code> vector
     * is for the default look and feel.
     * <p>
     * This method is invoked by the <code>createUI</code> method
     * of <code>MultiXxxxUI</code> classes.
     *
     * <p>
     *  创建显示<code>目标</code>组件所需的<code> ComponentUI </code>对象,将对象放置在<code> uis </code>向量中并返回<code> ComponentU
     * I </code>最能代表组件UI的对象。
     * 此方法通过在当前使用的每个默认和辅助外观上调用<code> getDefaults()。getUI(target)</code>来查找<code> ComponentUI </code>对象。
     * 此方法添加到<code> uis </code>向量的第一个UI对象是默认的外观和感觉。
     * <p>
     *  此方法由<code> MultiXxxxUI </code>类的<code> createUI </code>方法调用。
     * 
     * 
     * @param mui the <code>ComponentUI</code> object
     *            that represents the complete UI
     *            for the <code>target</code> component;
     *            this should be an instance
     *            of one of the <code>MultiXxxxUI</code> classes
     * @param uis a <code>Vector</code>;
     *            generally this is the <code>uis</code> field
     *            of the <code>mui</code> argument
     * @param target a component whose UI is represented by <code>mui</code>
     *
     * @return <code>mui</code> if the component has any auxiliary UI objects;
     *         otherwise, returns the UI object for the default look and feel
     *         or <code>null</code> if the default UI object couldn't be found
     *
     * @see javax.swing.UIManager#getAuxiliaryLookAndFeels
     * @see javax.swing.UIDefaults#getUI
     * @see MultiButtonUI#uis
     * @see MultiButtonUI#createUI
     */
    public static ComponentUI createUIs(ComponentUI mui,
                                        Vector      uis,
                                        JComponent  target) {
        ComponentUI ui;

        // Make sure we can at least get the default UI
        //
        ui = UIManager.getDefaults().getUI(target);
        if (ui != null) {
            uis.addElement(ui);
            LookAndFeel[] auxiliaryLookAndFeels;
            auxiliaryLookAndFeels = UIManager.getAuxiliaryLookAndFeels();
            if (auxiliaryLookAndFeels != null) {
                for (int i = 0; i < auxiliaryLookAndFeels.length; i++) {
                    ui = auxiliaryLookAndFeels[i].getDefaults().getUI(target);
                    if (ui != null) {
                        uis.addElement(ui);
                    }
                }
            }
        } else {
            return null;
        }

        // Don't bother returning the multiplexing UI if all we did was
        // get a UI from just the default look and feel.
        //
        if (uis.size() == 1) {
            return (ComponentUI) uis.elementAt(0);
        } else {
            return mui;
        }
    }

    /**
     * Creates an array,
     * populates it with UI objects from the passed-in vector,
     * and returns the array.
     * If <code>uis</code> is null,
     * this method returns an array with zero elements.
     * If <code>uis</code> is an empty vector,
     * this method returns <code>null</code>.
     * A run-time error occurs if any objects in the <code>uis</code> vector
     * are not of type <code>ComponentUI</code>.
     *
     * <p>
     *  创建数组,使用传入的向量中的UI对象填充数组,并返回数组。如果<code> uis </code>为null,此方法返回一个零元素的数组。
     * 如果<code> uis </code>是一个空向量,此方法返回<code> null </code>。
     * 如果<code> uis </code>向量中的任何对象不是类型<code> ComponentUI </code>,则会发生运行时错误。
     * 
     * 
     * @param uis a vector containing <code>ComponentUI</code> objects
     * @return an array equivalent to the passed-in vector
     *
     */
    protected static ComponentUI[] uisToArray(Vector uis) {
        if (uis == null) {
            return new ComponentUI[0];
        } else {
            int count = uis.size();
            if (count > 0) {
                ComponentUI[] u = new ComponentUI[count];
                for (int i = 0; i < count; i++) {
                    u[i] = (ComponentUI)uis.elementAt(i);
                }
                return u;
            } else {
                return null;
            }
        }
    }
}

/**
 * We want the Multiplexing LookAndFeel to be quiet and fallback
 * gracefully if it cannot find a UI.  This class overrides the
 * getUIError method of UIDefaults, which is the method that
 * emits error messages when it cannot find a UI class in the
 * LAF.
 * <p>
 */
class MultiUIDefaults extends UIDefaults {
    MultiUIDefaults(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }
    protected void getUIError(String msg) {
        System.err.println("Multiplexing LAF:  " + msg);
    }
}
