/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.plaf.synth;

import java.awt.*;
import java.beans.*;
import java.io.*;
import java.lang.ref.*;
import java.net.*;
import java.security.*;
import java.text.*;
import java.util.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.*;

import sun.awt.*;
import sun.security.action.*;
import sun.swing.*;
import sun.swing.plaf.synth.*;

/**
 * SynthLookAndFeel provides the basis for creating a customized look and
 * feel. SynthLookAndFeel does not directly provide a look, all painting is
 * delegated.
 * You need to either provide a configuration file, by way of the
 * {@link #load} method, or provide your own {@link SynthStyleFactory}
 * to {@link #setStyleFactory}. Refer to the
 * <a href="package-summary.html">package summary</a> for an example of
 * loading a file, and {@link javax.swing.plaf.synth.SynthStyleFactory} for
 * an example of providing your own <code>SynthStyleFactory</code> to
 * <code>setStyleFactory</code>.
 * <p>
 * <strong>Warning:</strong>
 * This class implements {@link Serializable} as a side effect of it
 * extending {@link BasicLookAndFeel}. It is not intended to be serialized.
 * An attempt to serialize it will
 * result in {@link NotSerializableException}.
 *
 * <p>
 *  SynthLookAndFeel提供了创建自定义外观的基础。 SynthLookAndFeel不直接提供一个外观,所有的绘画都是委托的。
 * 您需要通过{@link #load}方法提供配置文件,或者向{@link #setStyleFactory}提供您自己的{@link SynthStyleFactory}。
 * 有关加载文件的示例,请参阅<a href="package-summary.html">程序包摘要</a>;有关提供自己的示例,请参阅{@link javax.swing.plaf.synth.SynthStyleFactory}
 *  <code> SynthStyleFactory </code>到<code> setStyleFactory </code>。
 * 您需要通过{@link #load}方法提供配置文件,或者向{@link #setStyleFactory}提供您自己的{@link SynthStyleFactory}。
 * <p>
 *  <strong>警告：</strong>此类实现{@link Serializable},因为它扩展了{@link BasicLookAndFeel}的副作用。它不打算序列化。
 * 尝试序列化它将导致{@link NotSerializableException}。
 * 
 * 
 * @serial exclude
 * @since 1.5
 * @author Scott Violet
 */
public class SynthLookAndFeel extends BasicLookAndFeel {
    /**
     * Used in a handful of places where we need an empty Insets.
     * <p>
     *  用于少数需要空插页的地方。
     * 
     */
    static final Insets EMPTY_UIRESOURCE_INSETS = new InsetsUIResource(
                                                            0, 0, 0, 0);

    /**
     * AppContext key to get the current SynthStyleFactory.
     * <p>
     *  AppContext键获取当前的SynthStyleFactory。
     * 
     */
    private static final Object STYLE_FACTORY_KEY =
                  new StringBuffer("com.sun.java.swing.plaf.gtk.StyleCache");

    /**
     * AppContext key to get selectedUI.
     * <p>
     *  AppContext键获取selectUI。
     * 
     */
    private static final Object SELECTED_UI_KEY = new StringBuilder("selectedUI");

    /**
     * AppContext key to get selectedUIState.
     * <p>
     *  AppContext键获取selectedUIState。
     * 
     */
    private static final Object SELECTED_UI_STATE_KEY = new StringBuilder("selectedUIState");

    /**
     * The last SynthStyleFactory that was asked for from AppContext
     * <code>lastContext</code>.
     * <p>
     *  最后一个从AppContext <code> lastContext </code>请求的SynthStyleFactory。
     * 
     */
    private static SynthStyleFactory lastFactory;
    /**
     * AppContext lastLAF came from.
     * <p>
     *  AppContext lastLAF来自。
     * 
     */
    private static AppContext lastContext;

    /**
     * SynthStyleFactory for the this SynthLookAndFeel.
     * <p>
     *  这个SynthLookAndFeel的SynthStyleFactory。
     * 
     */
    private SynthStyleFactory factory;

    /**
     * Map of defaults table entries. This is populated via the load
     * method.
     * <p>
     *  默认表项的映射。这是通过加载方法填充。
     * 
     */
    private Map<String, Object> defaultsMap;

    private Handler _handler;

    static ComponentUI getSelectedUI() {
        return (ComponentUI) AppContext.getAppContext().get(SELECTED_UI_KEY);
    }

    /**
     * Used by the renderers. For the most part the renderers are implemented
     * as Labels, which is problematic in so far as they are never selected.
     * To accommodate this SynthLabelUI checks if the current
     * UI matches that of <code>selectedUI</code> (which this methods sets), if
     * it does, then a state as set by this method is returned. This provides
     * a way for labels to have a state other than selected.
     * <p>
     * 由渲染器使用。在大多数情况下,渲染器被实现为标签,这是有问题的,因为它们从未被选择。
     * 为了适应这个SynthLabelUI,检查当前UI是否与<code> selectedUI </code>(这个方法设置)匹配,如果匹配,则返回由此方法设置的状态。
     * 这为标签提供了一种不是所选状态的方式。
     * 
     */
    static void setSelectedUI(ComponentUI uix, boolean selected,
                              boolean focused, boolean enabled,
                              boolean rollover) {
        int selectedUIState = 0;

        if (selected) {
            selectedUIState = SynthConstants.SELECTED;
            if (focused) {
                selectedUIState |= SynthConstants.FOCUSED;
            }
        }
        else if (rollover && enabled) {
            selectedUIState |=
                    SynthConstants.MOUSE_OVER | SynthConstants.ENABLED;
            if (focused) {
                selectedUIState |= SynthConstants.FOCUSED;
            }
        }
        else {
            if (enabled) {
                selectedUIState |= SynthConstants.ENABLED;
                if (focused) {
                    selectedUIState |= SynthConstants.FOCUSED;
                }
            }
            else {
                selectedUIState |= SynthConstants.DISABLED;
            }
        }

        AppContext context = AppContext.getAppContext();

        context.put(SELECTED_UI_KEY, uix);
        context.put(SELECTED_UI_STATE_KEY, Integer.valueOf(selectedUIState));
    }

    static int getSelectedUIState() {
        Integer result = (Integer) AppContext.getAppContext().get(SELECTED_UI_STATE_KEY);

        return result == null ? 0 : result.intValue();
    }

    /**
     * Clears out the selected UI that was last set in setSelectedUI.
     * <p>
     *  清除最后在setSelectedUI中设置的所选UI。
     * 
     */
    static void resetSelectedUI() {
        AppContext.getAppContext().remove(SELECTED_UI_KEY);
    }


    /**
     * Sets the SynthStyleFactory that the UI classes provided by
     * synth will use to obtain a SynthStyle.
     *
     * <p>
     *  设置Synth提供的UI类用于获取SynthStyle的SynthStyleFactory。
     * 
     * 
     * @param cache SynthStyleFactory the UIs should use.
     */
    public static void setStyleFactory(SynthStyleFactory cache) {
        // We assume the setter is called BEFORE the getter has been invoked
        // for a particular AppContext.
        synchronized(SynthLookAndFeel.class) {
            AppContext context = AppContext.getAppContext();
            lastFactory = cache;
            lastContext = context;
            context.put(STYLE_FACTORY_KEY, cache);
        }
    }

    /**
     * Returns the current SynthStyleFactory.
     *
     * <p>
     *  返回当前的SynthStyleFactory。
     * 
     * 
     * @return SynthStyleFactory
     */
    public static SynthStyleFactory getStyleFactory() {
        synchronized(SynthLookAndFeel.class) {
            AppContext context = AppContext.getAppContext();

            if (lastContext == context) {
                return lastFactory;
            }
            lastContext = context;
            lastFactory = (SynthStyleFactory) context.get(STYLE_FACTORY_KEY);
            return lastFactory;
        }
    }

    /**
     * Returns the component state for the specified component. This should
     * only be used for Components that don't have any special state beyond
     * that of ENABLED, DISABLED or FOCUSED. For example, buttons shouldn't
     * call into this method.
     * <p>
     *  返回指定组件的组件状态。这只应该用于除了ENABLED,DISABLED或FOCUSED之外没有任何特殊状态的组件。例如,按钮不应调用此方法。
     * 
     */
    static int getComponentState(Component c) {
        if (c.isEnabled()) {
            if (c.isFocusOwner()) {
                return SynthUI.ENABLED | SynthUI.FOCUSED;
            }
            return SynthUI.ENABLED;
        }
        return SynthUI.DISABLED;
    }

    /**
     * Gets a SynthStyle for the specified region of the specified component.
     * This is not for general consumption, only custom UIs should call this
     * method.
     *
     * <p>
     *  获取指定组件的指定区域的SynthStyle。这不是一般消费,只有自定义UI应调用此方法。
     * 
     * 
     * @param c JComponent to get the SynthStyle for
     * @param region Identifies the region of the specified component
     * @return SynthStyle to use.
     */
    public static SynthStyle getStyle(JComponent c, Region region) {
        return getStyleFactory().getStyle(c, region);
    }

    /**
     * Returns true if the Style should be updated in response to the
     * specified PropertyChangeEvent. This forwards to
     * <code>shouldUpdateStyleOnAncestorChanged</code> as necessary.
     * <p>
     *  如果应响应指定的PropertyChangeEvent更新样式,则返回true。根据需要转发到<code> shouldUpdateStyleOnAncestorChanged </code>。
     * 
     */
    static boolean shouldUpdateStyle(PropertyChangeEvent event) {
        LookAndFeel laf = UIManager.getLookAndFeel();
        return (laf instanceof SynthLookAndFeel &&
                ((SynthLookAndFeel) laf).shouldUpdateStyleOnEvent(event));
    }

    /**
     * A convience method that will reset the Style of StyleContext if
     * necessary.
     *
     * <p>
     *  如果需要,将重置StyleContext的Style的convience方法。
     * 
     * 
     * @return newStyle
     */
    static SynthStyle updateStyle(SynthContext context, SynthUI ui) {
        SynthStyle newStyle = getStyle(context.getComponent(),
                                       context.getRegion());
        SynthStyle oldStyle = context.getStyle();

        if (newStyle != oldStyle) {
            if (oldStyle != null) {
                oldStyle.uninstallDefaults(context);
            }
            context.setStyle(newStyle);
            newStyle.installDefaults(context, ui);
        }
        return newStyle;
    }

    /**
     * Updates the style associated with <code>c</code>, and all its children.
     * This is a lighter version of
     * <code>SwingUtilities.updateComponentTreeUI</code>.
     *
     * <p>
     *  更新与<code> c </code>及其所有子项关联的样式。这是一个较轻的版本的<code> SwingUtilities.updateComponentTreeUI </code>。
     * 
     * 
     * @param c Component to update style for.
     */
    public static void updateStyles(Component c) {
        if (c instanceof JComponent) {
            // Yes, this is hacky. A better solution is to get the UI
            // and cast, but JComponent doesn't expose a getter for the UI
            // (each of the UIs do), making that approach impractical.
            String name = c.getName();
            c.setName(null);
            if (name != null) {
                c.setName(name);
            }
            ((JComponent)c).revalidate();
        }
        Component[] children = null;
        if (c instanceof JMenu) {
            children = ((JMenu)c).getMenuComponents();
        }
        else if (c instanceof Container) {
            children = ((Container)c).getComponents();
        }
        if (children != null) {
            for (Component child : children) {
                updateStyles(child);
            }
        }
        c.repaint();
    }

    /**
     * Returns the Region for the JComponent <code>c</code>.
     *
     * <p>
     *  返回JComponent <code> c </code>的Region。
     * 
     * 
     * @param c JComponent to fetch the Region for
     * @return Region corresponding to <code>c</code>
     */
    public static Region getRegion(JComponent c) {
        return Region.getRegion(c);
    }

    /**
     * A convenience method to return where the foreground should be
     * painted for the Component identified by the passed in
     * AbstractSynthContext.
     * <p>
     * 一个方便的方法来返回前景应该为由AbstractSynthContext传递的组件识别的组件。
     * 
     */
    static Insets getPaintingInsets(SynthContext state, Insets insets) {
        if (state.isSubregion()) {
            insets = state.getStyle().getInsets(state, insets);
        }
        else {
            insets = state.getComponent().getInsets(insets);
        }
        return insets;
    }

    /**
     * A convenience method that handles painting of the background.
     * All SynthUI implementations should override update and invoke
     * this method.
     * <p>
     *  处理背景绘画的方便方法。所有SynthUI实现应该覆盖更新并调用此方法。
     * 
     */
    static void update(SynthContext state, Graphics g) {
        paintRegion(state, g, null);
    }

    /**
     * A convenience method that handles painting of the background for
     * subregions. All SynthUI's that have subregions should invoke
     * this method, than paint the foreground.
     * <p>
     *  处理子区域的背景绘画的便利方法。所有具有子区域的SynthUI都应该调用此方法,而不是绘制前景。
     * 
     */
    static void updateSubregion(SynthContext state, Graphics g,
                                Rectangle bounds) {
        paintRegion(state, g, bounds);
    }

    private static void paintRegion(SynthContext state, Graphics g,
                                    Rectangle bounds) {
        JComponent c = state.getComponent();
        SynthStyle style = state.getStyle();
        int x, y, width, height;

        if (bounds == null) {
            x = 0;
            y = 0;
            width = c.getWidth();
            height = c.getHeight();
        }
        else {
            x = bounds.x;
            y = bounds.y;
            width = bounds.width;
            height = bounds.height;
        }

        // Fill in the background, if necessary.
        boolean subregion = state.isSubregion();
        if ((subregion && style.isOpaque(state)) ||
                          (!subregion && c.isOpaque())) {
            g.setColor(style.getColor(state, ColorType.BACKGROUND));
            g.fillRect(x, y, width, height);
        }
    }

    static boolean isLeftToRight(Component c) {
        return c.getComponentOrientation().isLeftToRight();
    }

    /**
     * Returns the ui that is of type <code>klass</code>, or null if
     * one can not be found.
     * <p>
     *  返回类型为<code> klass </code>的ui,如果找不到,则返回null。
     * 
     */
    static Object getUIOfType(ComponentUI ui, Class klass) {
        if (klass.isInstance(ui)) {
            return ui;
        }
        return null;
    }

    /**
     * Creates the Synth look and feel <code>ComponentUI</code> for
     * the passed in <code>JComponent</code>.
     *
     * <p>
     *  为<code> JComponent </code>中传递的</code>创建Synth外观和感觉<code> ComponentUI </code>。
     * 
     * 
     * @param c JComponent to create the <code>ComponentUI</code> for
     * @return ComponentUI to use for <code>c</code>
     */
    public static ComponentUI createUI(JComponent c) {
        String key = c.getUIClassID().intern();

        if (key == "ButtonUI") {
            return SynthButtonUI.createUI(c);
        }
        else if (key == "CheckBoxUI") {
            return SynthCheckBoxUI.createUI(c);
        }
        else if (key == "CheckBoxMenuItemUI") {
            return SynthCheckBoxMenuItemUI.createUI(c);
        }
        else if (key == "ColorChooserUI") {
            return SynthColorChooserUI.createUI(c);
        }
        else if (key == "ComboBoxUI") {
            return SynthComboBoxUI.createUI(c);
        }
        else if (key == "DesktopPaneUI") {
            return SynthDesktopPaneUI.createUI(c);
        }
        else if (key == "DesktopIconUI") {
            return SynthDesktopIconUI.createUI(c);
        }
        else if (key == "EditorPaneUI") {
            return SynthEditorPaneUI.createUI(c);
        }
        else if (key == "FileChooserUI") {
            return SynthFileChooserUI.createUI(c);
        }
        else if (key == "FormattedTextFieldUI") {
            return SynthFormattedTextFieldUI.createUI(c);
        }
        else if (key == "InternalFrameUI") {
            return SynthInternalFrameUI.createUI(c);
        }
        else if (key == "LabelUI") {
            return SynthLabelUI.createUI(c);
        }
        else if (key == "ListUI") {
            return SynthListUI.createUI(c);
        }
        else if (key == "MenuBarUI") {
            return SynthMenuBarUI.createUI(c);
        }
        else if (key == "MenuUI") {
            return SynthMenuUI.createUI(c);
        }
        else if (key == "MenuItemUI") {
            return SynthMenuItemUI.createUI(c);
        }
        else if (key == "OptionPaneUI") {
            return SynthOptionPaneUI.createUI(c);
        }
        else if (key == "PanelUI") {
            return SynthPanelUI.createUI(c);
        }
        else if (key == "PasswordFieldUI") {
            return SynthPasswordFieldUI.createUI(c);
        }
        else if (key == "PopupMenuSeparatorUI") {
            return SynthSeparatorUI.createUI(c);
        }
        else if (key == "PopupMenuUI") {
            return SynthPopupMenuUI.createUI(c);
        }
        else if (key == "ProgressBarUI") {
            return SynthProgressBarUI.createUI(c);
        }
        else if (key == "RadioButtonUI") {
            return SynthRadioButtonUI.createUI(c);
        }
        else if (key == "RadioButtonMenuItemUI") {
            return SynthRadioButtonMenuItemUI.createUI(c);
        }
        else if (key == "RootPaneUI") {
            return SynthRootPaneUI.createUI(c);
        }
        else if (key == "ScrollBarUI") {
            return SynthScrollBarUI.createUI(c);
        }
        else if (key == "ScrollPaneUI") {
            return SynthScrollPaneUI.createUI(c);
        }
        else if (key == "SeparatorUI") {
            return SynthSeparatorUI.createUI(c);
        }
        else if (key == "SliderUI") {
            return SynthSliderUI.createUI(c);
        }
        else if (key == "SpinnerUI") {
            return SynthSpinnerUI.createUI(c);
        }
        else if (key == "SplitPaneUI") {
            return SynthSplitPaneUI.createUI(c);
        }
        else if (key == "TabbedPaneUI") {
            return SynthTabbedPaneUI.createUI(c);
        }
        else if (key == "TableUI") {
            return SynthTableUI.createUI(c);
        }
        else if (key == "TableHeaderUI") {
            return SynthTableHeaderUI.createUI(c);
        }
        else if (key == "TextAreaUI") {
            return SynthTextAreaUI.createUI(c);
        }
        else if (key == "TextFieldUI") {
            return SynthTextFieldUI.createUI(c);
        }
        else if (key == "TextPaneUI") {
            return SynthTextPaneUI.createUI(c);
        }
        else if (key == "ToggleButtonUI") {
            return SynthToggleButtonUI.createUI(c);
        }
        else if (key == "ToolBarSeparatorUI") {
            return SynthSeparatorUI.createUI(c);
        }
        else if (key == "ToolBarUI") {
            return SynthToolBarUI.createUI(c);
        }
        else if (key == "ToolTipUI") {
            return SynthToolTipUI.createUI(c);
        }
        else if (key == "TreeUI") {
            return SynthTreeUI.createUI(c);
        }
        else if (key == "ViewportUI") {
            return SynthViewportUI.createUI(c);
        }
        return null;
    }


    /**
     * Creates a SynthLookAndFeel.
     * <p>
     * For the returned <code>SynthLookAndFeel</code> to be useful you need to
     * invoke <code>load</code> to specify the set of
     * <code>SynthStyle</code>s, or invoke <code>setStyleFactory</code>.
     *
     * <p>
     *  创建SynthLookAndFeel。
     * <p>
     *  对于返回的<code> SynthLookAndFeel </code>有用,你需要调用<code> load </code>来指定<code> SynthStyle </code>的集合,或者调用<code>
     *  setStyleFactory </code >。
     * 
     * 
     * @see #load
     * @see #setStyleFactory
     */
    public SynthLookAndFeel() {
        factory = new DefaultSynthStyleFactory();
        _handler = new Handler();
    }

    /**
     * Loads the set of <code>SynthStyle</code>s that will be used by
     * this <code>SynthLookAndFeel</code>. <code>resourceBase</code> is
     * used to resolve any path based resources, for example an
     * <code>Image</code> would be resolved by
     * <code>resourceBase.getResource(path)</code>. Refer to
     * <a href="doc-files/synthFileFormat.html">Synth File Format</a>
     * for more information.
     *
     * <p>
     *  加载将由此<code> SynthLookAndFeel </code>使用的<code> SynthStyle </code>集合。
     *  <code> resourceBase </code>用于解析任何基于路径的资源,例如<code> Image </code>将由<code> resourceBase.getResource(pat
     * h)</code>解析。
     *  加载将由此<code> SynthLookAndFeel </code>使用的<code> SynthStyle </code>集合。
     * 有关详细信息,请参阅<a href="doc-files/synthFileFormat.html">合成文件格式</a>。
     * 
     * 
     * @param input InputStream to load from
     * @param resourceBase used to resolve any images or other resources
     * @throws ParseException if there is an error in parsing
     * @throws IllegalArgumentException if input or resourceBase is <code>null</code>
     */
    public void load(InputStream input, Class<?> resourceBase) throws
                       ParseException {
        if (resourceBase == null) {
            throw new IllegalArgumentException(
                "You must supply a valid resource base Class");
        }

        if (defaultsMap == null) {
            defaultsMap = new HashMap<String, Object>();
        }

        new SynthParser().parse(input, (DefaultSynthStyleFactory) factory,
                                null, resourceBase, defaultsMap);
    }

    /**
     * Loads the set of <code>SynthStyle</code>s that will be used by
     * this <code>SynthLookAndFeel</code>. Path based resources are resolved
     * relatively to the specified <code>URL</code> of the style. For example
     * an <code>Image</code> would be resolved by
     * <code>new URL(synthFile, path)</code>. Refer to
     * <a href="doc-files/synthFileFormat.html">Synth File Format</a> for more
     * information.
     *
     * <p>
     * 加载将由此<code> SynthLookAndFeel </code>使用的<code> SynthStyle </code>集合。
     * 基于路径的资源相对于样式的指定<code> URL </code>来解析。例如,<code> Image </code>将由<code>新网址(synthFile,path)</code>解析。
     * 有关详细信息,请参阅<a href="doc-files/synthFileFormat.html">合成文件格式</a>。
     * 
     * 
     * @param url the <code>URL</code> to load the set of
     *     <code>SynthStyle</code> from
     * @throws ParseException if there is an error in parsing
     * @throws IllegalArgumentException if synthSet is <code>null</code>
     * @throws IOException if synthSet cannot be opened as an <code>InputStream</code>
     * @since 1.6
     */
    public void load(URL url) throws ParseException, IOException {
        if (url == null) {
            throw new IllegalArgumentException(
                "You must supply a valid Synth set URL");
        }

        if (defaultsMap == null) {
            defaultsMap = new HashMap<String, Object>();
        }

        InputStream input = url.openStream();
        new SynthParser().parse(input, (DefaultSynthStyleFactory) factory,
                                url, null, defaultsMap);
    }

    /**
     * Called by UIManager when this look and feel is installed.
     * <p>
     *  由UIManager调用时,这种外观和感觉安装。
     * 
     */
    @Override
    public void initialize() {
        super.initialize();
        DefaultLookup.setDefaultLookup(new SynthDefaultLookup());
        setStyleFactory(factory);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().
            addPropertyChangeListener(_handler);
    }

    /**
     * Called by UIManager when this look and feel is uninstalled.
     * <p>
     *  当UIManager卸载此外观时调用。
     * 
     */
    @Override
    public void uninitialize() {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().
            removePropertyChangeListener(_handler);
        // We should uninstall the StyleFactory here, but unfortunately
        // there are a handful of things that retain references to the
        // LookAndFeel and expect things to work
        super.uninitialize();
    }

    /**
     * Returns the defaults for this SynthLookAndFeel.
     *
     * <p>
     *  返回此SynthLookAndFeel的默认值。
     * 
     * 
     * @return Defaults table.
     */
    @Override
    public UIDefaults getDefaults() {
        UIDefaults table = new UIDefaults(60, 0.75f);

        Region.registerUIs(table);
        table.setDefaultLocale(Locale.getDefault());
        table.addResourceBundle(
              "com.sun.swing.internal.plaf.basic.resources.basic" );
        table.addResourceBundle("com.sun.swing.internal.plaf.synth.resources.synth");

        // SynthTabbedPaneUI supports rollover on tabs, GTK does not
        table.put("TabbedPane.isTabRollover", Boolean.TRUE);

        // These need to be defined for JColorChooser to work.
        table.put("ColorChooser.swatchesRecentSwatchSize",
                  new Dimension(10, 10));
        table.put("ColorChooser.swatchesDefaultRecentColor", Color.RED);
        table.put("ColorChooser.swatchesSwatchSize", new Dimension(10, 10));

        // These need to be defined for ImageView.
        table.put("html.pendingImage", SwingUtilities2.makeIcon(getClass(),
                                BasicLookAndFeel.class,
                                "icons/image-delayed.png"));
        table.put("html.missingImage", SwingUtilities2.makeIcon(getClass(),
                                BasicLookAndFeel.class,
                                "icons/image-failed.png"));

        // These are needed for PopupMenu.
        table.put("PopupMenu.selectedWindowInputMapBindings", new Object[] {
                  "ESCAPE", "cancel",
                    "DOWN", "selectNext",
                 "KP_DOWN", "selectNext",
                      "UP", "selectPrevious",
                   "KP_UP", "selectPrevious",
                    "LEFT", "selectParent",
                 "KP_LEFT", "selectParent",
                   "RIGHT", "selectChild",
                "KP_RIGHT", "selectChild",
                   "ENTER", "return",
                   "SPACE", "return"
        });
        table.put("PopupMenu.selectedWindowInputMapBindings.RightToLeft",
                  new Object[] {
                    "LEFT", "selectChild",
                 "KP_LEFT", "selectChild",
                   "RIGHT", "selectParent",
                "KP_RIGHT", "selectParent",
                  });

        // enabled antialiasing depending on desktop settings
        flushUnreferenced();
        Object aaTextInfo = getAATextInfo();
        table.put(SwingUtilities2.AA_TEXT_PROPERTY_KEY, aaTextInfo);
        new AATextListener(this);

        if (defaultsMap != null) {
            table.putAll(defaultsMap);
        }
        return table;
    }

    /**
     * Returns true, SynthLookAndFeel is always supported.
     *
     * <p>
     *  返回true,始终支持SynthLookAndFeel。
     * 
     * 
     * @return true.
     */
    @Override
    public boolean isSupportedLookAndFeel() {
        return true;
    }

    /**
     * Returns false, SynthLookAndFeel is not a native look and feel.
     *
     * <p>
     *  返回false,SynthLookAndFeel不是本地的外观和感觉。
     * 
     * 
     * @return false
     */
    @Override
    public boolean isNativeLookAndFeel() {
        return false;
    }

    /**
     * Returns a textual description of SynthLookAndFeel.
     *
     * <p>
     *  返回SynthLookAndFeel的文本描述。
     * 
     * 
     * @return textual description of synth.
     */
    @Override
    public String getDescription() {
        return "Synth look and feel";
    }

    /**
     * Return a short string that identifies this look and feel.
     *
     * <p>
     *  返回一个标识此外观的短字符串。
     * 
     * 
     * @return a short string identifying this look and feel.
     */
    @Override
    public String getName() {
        return "Synth look and feel";
    }

    /**
     * Return a string that identifies this look and feel.
     *
     * <p>
     *  返回一个标识此外观的字符串。
     * 
     * 
     * @return a short string identifying this look and feel.
     */
    @Override
    public String getID() {
        return "Synth";
    }

    /**
     * Returns whether or not the UIs should update their
     * <code>SynthStyles</code> from the <code>SynthStyleFactory</code>
     * when the ancestor of the <code>JComponent</code> changes. A subclass
     * that provided a <code>SynthStyleFactory</code> that based the
     * return value from <code>getStyle</code> off the containment hierarchy
     * would override this method to return true.
     *
     * <p>
     *  返回当<code> JComponent </code>的祖先更改时,UI是否应从<code> SynthStyleFactory </code>更新其<code> SynthStyles </code>
     * 提供了一个<code> SynthStyleFactory </code>的子类,它基于包含层次结构中的<code> getStyle </code>返回值,将覆盖此方法返回true。
     * 
     * 
     * @return whether or not the UIs should update their
     * <code>SynthStyles</code> from the <code>SynthStyleFactory</code>
     * when the ancestor changed.
     */
    public boolean shouldUpdateStyleOnAncestorChanged() {
        return false;
    }

    /**
     * Returns whether or not the UIs should update their styles when a
     * particular event occurs.
     *
     * <p>
     *  返回在特定事件发生时UI是否应更新其样式。
     * 
     * 
     * @param ev a {@code PropertyChangeEvent}
     * @return whether or not the UIs should update their styles
     * @since 1.7
     */
    protected boolean shouldUpdateStyleOnEvent(PropertyChangeEvent ev) {
        String eName = ev.getPropertyName();
        if ("name" == eName || "componentOrientation" == eName) {
            return true;
        }
        if ("ancestor" == eName && ev.getNewValue() != null) {
            // Only update on an ancestor change when getting a valid
            // parent and the LookAndFeel wants this.
            return shouldUpdateStyleOnAncestorChanged();
        }
        return false;
    }

    /**
     * Returns the antialiasing information as specified by the host desktop.
     * Antialiasing might be forced off if the desktop is GNOME and the user
     * has set his locale to Chinese, Japanese or Korean. This is consistent
     * with what GTK does. See com.sun.java.swing.plaf.gtk.GtkLookAndFeel
     * for more information about CJK and antialiased fonts.
     *
     * <p>
     * 返回主机桌面指定的抗锯齿信息。如果桌面是GNOME,并且用户已将其区域设置为中文,日语或韩语,则可能会强制关闭抗锯齿。这与GTK是一致的。
     * 有关CJK和抗锯齿字体的更多信息,请参阅com.sun.java.swing.plaf.gtk.GtkLookAndFeel。
     * 
     * 
     * @return the text antialiasing information associated to the desktop
     */
    private static Object getAATextInfo() {
        String language = Locale.getDefault().getLanguage();
        String desktop =
            AccessController.doPrivileged(new GetPropertyAction("sun.desktop"));

        boolean isCjkLocale = (Locale.CHINESE.getLanguage().equals(language) ||
                Locale.JAPANESE.getLanguage().equals(language) ||
                Locale.KOREAN.getLanguage().equals(language));
        boolean isGnome = "gnome".equals(desktop);
        boolean isLocal = SwingUtilities2.isLocalDisplay();

        boolean setAA = isLocal && (!isGnome || !isCjkLocale);

        Object aaTextInfo = SwingUtilities2.AATextInfo.getAATextInfo(setAA);
        return aaTextInfo;
    }

    private static ReferenceQueue<LookAndFeel> queue = new ReferenceQueue<LookAndFeel>();

    private static void flushUnreferenced() {
        AATextListener aatl;
        while ((aatl = (AATextListener) queue.poll()) != null) {
            aatl.dispose();
        }
    }

    private static class AATextListener
        extends WeakReference<LookAndFeel> implements PropertyChangeListener {
        private String key = SunToolkit.DESKTOPFONTHINTS;

        AATextListener(LookAndFeel laf) {
            super(laf, queue);
            Toolkit tk = Toolkit.getDefaultToolkit();
            tk.addPropertyChangeListener(key, this);
        }

        @Override
        public void propertyChange(PropertyChangeEvent pce) {
            UIDefaults defaults = UIManager.getLookAndFeelDefaults();
            if (defaults.getBoolean("Synth.doNotSetTextAA")) {
                dispose();
                return;
            }

            LookAndFeel laf = get();
            if (laf == null || laf != UIManager.getLookAndFeel()) {
                dispose();
                return;
            }

            Object aaTextInfo = getAATextInfo();
            defaults.put(SwingUtilities2.AA_TEXT_PROPERTY_KEY, aaTextInfo);

            updateUI();
        }

        void dispose() {
            Toolkit tk = Toolkit.getDefaultToolkit();
            tk.removePropertyChangeListener(key, this);
        }

        /**
         * Updates the UI of the passed in window and all its children.
         * <p>
         *  更新传入的窗口及其所有子窗口的UI。
         * 
         */
        private static void updateWindowUI(Window window) {
            updateStyles(window);
            Window ownedWins[] = window.getOwnedWindows();
            for (Window w : ownedWins) {
                updateWindowUI(w);
            }
        }

        /**
         * Updates the UIs of all the known Frames.
         * <p>
         *  更新所有已知帧的UI。
         * 
         */
        private static void updateAllUIs() {
            Frame appFrames[] = Frame.getFrames();
            for (Frame frame : appFrames) {
                updateWindowUI(frame);
            }
        }

        /**
         * Indicates if an updateUI call is pending.
         * <p>
         *  指示updateUI调用是否挂起。
         * 
         */
        private static boolean updatePending;

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

        protected void updateUI() {
            if (!isUpdatePending()) {
                setUpdatePending(true);
                Runnable uiUpdater = new Runnable() {
                    @Override
                    public void run() {
                        updateAllUIs();
                        setUpdatePending(false);
                    }
                };
                SwingUtilities.invokeLater(uiUpdater);
            }
        }
    }

    private void writeObject(java.io.ObjectOutputStream out)
            throws IOException {
        throw new NotSerializableException(this.getClass().getName());
    }

    private class Handler implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            String propertyName = evt.getPropertyName();
            Object newValue = evt.getNewValue();
            Object oldValue = evt.getOldValue();

            if ("focusOwner" == propertyName) {
                if (oldValue instanceof JComponent) {
                    repaintIfBackgroundsDiffer((JComponent)oldValue);

                }

                if (newValue instanceof JComponent) {
                    repaintIfBackgroundsDiffer((JComponent)newValue);
                }
            }
            else if ("managingFocus" == propertyName) {
                // De-register listener on old keyboard focus manager and
                // register it on the new one.
                KeyboardFocusManager manager =
                    (KeyboardFocusManager)evt.getSource();
                if (newValue.equals(Boolean.FALSE)) {
                    manager.removePropertyChangeListener(_handler);
                }
                else {
                    manager.addPropertyChangeListener(_handler);
                }
            }
        }

        /**
         * This is a support method that will check if the background colors of
         * the specified component differ between focused and unfocused states.
         * If the color differ the component will then repaint itself.
         *
         * @comp the component to check
         * <p>
         *  这是一种支持方法,将检查指定组件的背景颜色在聚焦状态和非聚焦状态之间是否不同。如果颜色不同,组件将重新绘制自身。
         * 
         */
        private void repaintIfBackgroundsDiffer(JComponent comp) {
            ComponentUI ui = (ComponentUI)comp.getClientProperty(
                    SwingUtilities2.COMPONENT_UI_PROPERTY_KEY);
            if (ui instanceof SynthUI) {
                SynthUI synthUI = (SynthUI)ui;
                SynthContext context = synthUI.getContext(comp);
                SynthStyle style = context.getStyle();
                int state = context.getComponentState();

                // Get the current background color.
                Color currBG = style.getColor(context, ColorType.BACKGROUND);

                // Get the last background color.
                state ^= SynthConstants.FOCUSED;
                context.setComponentState(state);
                Color lastBG = style.getColor(context, ColorType.BACKGROUND);

                // Reset the component state back to original.
                state ^= SynthConstants.FOCUSED;
                context.setComponentState(state);

                // Repaint the component if the backgrounds differed.
                if (currBG != null && !currBG.equals(lastBG)) {
                    comp.repaint();
                }
                context.dispose();
            }
        }
    }
}
