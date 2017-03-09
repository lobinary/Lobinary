/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing.plaf.nimbus;

import javax.swing.Painter;

import javax.swing.JComponent;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.synth.ColorType;
import static javax.swing.plaf.synth.SynthConstants.*;
import javax.swing.plaf.synth.SynthContext;
import javax.swing.plaf.synth.SynthPainter;
import javax.swing.plaf.synth.SynthStyle;
import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * <p>A SynthStyle implementation used by Nimbus. Each Region that has been
 * registered with the NimbusLookAndFeel will have an associated NimbusStyle.
 * Third party components that are registered with the NimbusLookAndFeel will
 * therefore be handed a NimbusStyle from the look and feel from the
 * #getStyle(JComponent, Region) method.</p>
 *
 * <p>This class properly reads and retrieves values placed in the UIDefaults
 * according to the standard Nimbus naming conventions. It will create and
 * retrieve painters, fonts, colors, and other data stored there.</p>
 *
 * <p>NimbusStyle also supports the ability to override settings on a per
 * component basis. NimbusStyle checks the component's client property map for
 * "Nimbus.Overrides". If the value associated with this key is an instance of
 * UIDefaults, then the values in that defaults table will override the standard
 * Nimbus defaults in UIManager, but for that component instance only.</p>
 *
 * <p>Optionally, you may specify the client property
 * "Nimbus.Overrides.InheritDefaults". If true, this client property indicates
 * that the defaults located in UIManager should first be read, and then
 * replaced with defaults located in the component client properties. If false,
 * then only the defaults located in the component client property map will
 * be used. If not specified, it is assumed to be true.</p>
 *
 * <p>You must specify "Nimbus.Overrides" for "Nimbus.Overrides.InheritDefaults"
 * to have any effect. "Nimbus.Overrides" indicates whether there are any
 * overrides, while "Nimbus.Overrides.InheritDefaults" indicates whether those
 * overrides should first be initialized with the defaults from UIManager.</p>
 *
 * <p>The NimbusStyle is reloaded whenever a property change event is fired
 * for a component for "Nimbus.Overrides" or "Nimbus.Overrides.InheritDefaults".
 * So for example, setting a new UIDefaults on a component would cause the
 * style to be reloaded.</p>
 *
 * <p>The values are only read out of UIManager once, and then cached. If
 * you need to read the values again (for example, if the UI is being reloaded),
 * then discard this NimbusStyle and read a new one from NimbusLookAndFeel
 * using NimbusLookAndFeel.getStyle.</p>
 *
 * <p>The primary API of interest in this class for 3rd party component authors
 * are the three methods which retrieve painters: #getBackgroundPainter,
 * #getForegroundPainter, and #getBorderPainter.</p>
 *
 * <p>NimbusStyle allows you to specify custom states, or modify the order of
 * states. Synth (and thus Nimbus) has the concept of a "state". For example,
 * a JButton might be in the "MOUSE_OVER" state, or the "ENABLED" state, or the
 * "DISABLED" state. These are all "standard" states which are defined in synth,
 * and which apply to all synth Regions.</p>
 *
 * <p>Sometimes, however, you need to have a custom state. For example, you
 * want JButton to render differently if it's parent is a JToolbar. In Nimbus,
 * you specify these custom states by including a special key in UIDefaults.
 * The following UIDefaults entries define three states for this button:</p>
 *
 * <pre><code>
 *     JButton.States = Enabled, Disabled, Toolbar
 *     JButton[Enabled].backgroundPainter = somePainter
 *     JButton[Disabled].background = BLUE
 *     JButton[Toolbar].backgroundPainter = someOtherPaint
 * </code></pre>
 *
 * <p>As you can see, the <code>JButton.States</code> entry lists the states
 * that the JButton style will support. You then specify the settings for
 * each state. If you do not specify the <code>JButton.States</code> entry,
 * then the standard Synth states will be assumed. If you specify the entry
 * but the list of states is empty or null, then the standard synth states
 * will be assumed.</p>
 *
 * <p>
 *  <p> Nimbus使用的SynthStyle实现。已经注册到NimbusLookAndFeel的每个区域将具有关联的NimbusStyle。
 * 因此,向NimbusLookAndFeel注册的第三方组件将使用#getStyle(JComponent,Region)方法的外观和感觉来传递NimbusStyle。</p>。
 * 
 *  <p>此类根据标准Nimbus命名约定,正确读取并检索放置在UIDefault中的值。它将创建和检索画家,字体,颜色和存储在那里的其他数据。</p>
 * 
 *  <p> NimbusStyle还支持基于每个组件覆盖设置的功能。 NimbusStyle检查组件的客户端属性映射"Nimbus.Overrides"。
 * 如果与此键相关联的值是UIDefaults的实例,那么该默认值表中的值将覆盖UIManager中的标准Nimbus默认值,但仅限该组件实例。</p>。
 * 
 *  <p>(可选)您可以指定客户端属性"Nimbus.Overrides.InheritDefaults"。
 * 如果为true,此客户端属性指示应首先读取位于UIManager中的默认值,然后替换为位于组件客户端属性中的默认值。如果为false,则只使用位于组件客户端属性映射中的默认值。
 * 如果未指定,则假设为true。</p>。
 * 
 * <p>您必须为"Nimbus.Overrides.InheritDefaults"指定"Nimbus.Overrides"才能生效。
 *  "Nimbus.Overrides"指示是否存在任何覆盖,而"Nimbus.Overrides.InheritDefaults"指示是否应首先使用来自UIManager的默认值初始化这些覆盖。
 * </p>。
 * 
 *  <p>每当针对"Nimbus.Overrides"或"Nimbus.Overrides.InheritDefaults"的组件触发属性更改事件时,将重新装入NimbusStyle。
 * 因此,例如,在组件上设置新的UID默认值将导致样式重新加载。</p>。
 * 
 *  <p>这些值只会从UIManager读取一次,然后缓存。
 * 如果你需要再次读取值(例如,如果UI被重新加载),然后丢弃这个NimbusStyle,并使用NimbusLookAndFeel.getStyle从NimbusLookAndFeel读取一个新的。
 * </p>。
 * 
 *  <p>第三方组件作者的此类感兴趣的主要API是检索画家的三个方法：#getBackgroundPainter,#getForegroundPainter和#getBorderPainter。
 * </p>。
 * 
 *  <p> NimbusStyle允许您指定自定义状态,或修改状态的顺序。合成器(因此Nimbus)具有"状态"的概念。
 * 例如,JButton可能处于"MOUSE_OVER"状态或"ENABLED"状态或"DISABLED"状态。这些都是在合成中定义的"标准"状态,适用于所有合成器区域。</p>。
 * 
 * <p>然而,有时候,您需要有一个自定义状态。例如,如果父对象是JToolbar,则希望JButton呈现不同。在Nimbus中,通过在UIDefaults中包含特殊键来指定这些自定义状态。
 * 以下UIDefaults条目定义此按钮的三种状态：</p>。
 * 
 *  <pre> <code> JButton.States = Enabled,Disabled,Toolbar JButton [Enabled] .backgroundPainter = somePa
 * inter JButton [Disabled] .background = BLUE JButton [Toolbar] .backgroundPainter = someOtherPaint </code>
 * 。
 * 
 *  <p>如您所见,<code> JButton.States </code>条目列出了JButton样式将支持的状态。然后指定每个状态的设置。
 * 如果不指定<code> JButton.States </code>条目,那么将假定标准的Synth状态。如果指定条目但状态列表为空或为空,则将假定标准合成状态。</p>。
 * 
 * 
 * @author Richard Bair
 * @author Jasper Potts
 */
public final class NimbusStyle extends SynthStyle {
    /* Keys and scales for large/small/mini components, based on Apples sizes */
    public static final String LARGE_KEY = "large";
    public static final String SMALL_KEY = "small";
    public static final String MINI_KEY = "mini";
    public static final double LARGE_SCALE = 1.15;
    public static final double SMALL_SCALE = 0.857;
    public static final double MINI_SCALE = 0.714;

    /**
     * Special constant used for performance reasons during the get() method.
     * If get() runs through all of the search locations and determines that
     * there is no value, then NULL will be placed into the values map. This way
     * on subsequent lookups it will simply extract NULL, see it, and return
     * null rather than continuing the lookup procedure.
     * <p>
     *  在get()方法期间用于性能原因的特殊常量。如果get()遍历所有搜索位置并确定没有值,那么NULL将被放入值映射中。
     * 这样在后续查找时,它将简单地提取NULL,查看它,并返回null,而不是继续查找过程。
     * 
     */
    private static final Object NULL = '\0';
    /**
     * <p>The Color to return from getColorForState if it would otherwise have
     * returned null.</p>
     *
     * <p>Returning null from getColorForState is a very bad thing, as it causes
     * the AWT peer for the component to install a SystemColor, which is not a
     * UIResource. As a result, if <code>null</code> is returned from
     * getColorForState, then thereafter the color is not updated for other
     * states or on LAF changes or updates. This DEFAULT_COLOR is used to
     * ensure that a ColorUIResource is always returned from
     * getColorForState.</p>
     * <p>
     *  <p>从getColorForState返回的颜色,否则返回null。</p>
     * 
     * <p>从getColorForState返回null是一件非常糟糕的事情,因为它导致组件的AWT对等体安装SystemColor,而不是UIResource。
     * 因此,如果从getColorForState返回<code> null </code>,则此后不会为其他状态或LAF更改或更新更新颜色。
     * 这个DEFAULT_COLOR用于确保ColorUIResource总是从getColorForState返回。</p>。
     * 
     */
    private static final Color DEFAULT_COLOR = new ColorUIResource(Color.BLACK);
    /**
     * Simple Comparator for ordering the RuntimeStates according to their
     * rank.
     * <p>
     *  简单比较器,用于根据RuntimeStates的排名对RuntimeStates进行排序。
     * 
     */
    private static final Comparator<RuntimeState> STATE_COMPARATOR =
        new Comparator<RuntimeState>() {
            @Override
            public int compare(RuntimeState a, RuntimeState b) {
                return a.state - b.state;
            }
        };
    /**
     * The prefix for the component or region that this NimbusStyle
     * represents. This prefix is used to lookup state in the UIManager.
     * It should be something like Button or Slider.Thumb or "MyButton" or
     * ComboBox."ComboBox.arrowButton" or "MyComboBox"."ComboBox.arrowButton"
     * <p>
     *  此NimbusStyle表示的组件或区域的前缀。此前缀用于查找UIManager中的状态。它应该是类似Button或Slider.Thumb或"MyButton"或ComboBox。
     * "ComboBox.arrowButton"或"MyComboBox"。"ComboBox.arrowButton"。
     * 
     */
    private String prefix;
    /**
     * The SynthPainter that will be returned from this NimbusStyle. The
     * SynthPainter returned will be a SynthPainterImpl, which will in turn
     * delegate back to this NimbusStyle for the proper Painter (not
     * SynthPainter) to use for painting the foreground, background, or border.
     * <p>
     *  将从此NimbusStyle返回的SynthPainter。
     * 返回的SynthPainter将是一个SynthPainterImpl,它将委托回到这个NimbusStyle为正确的Painter(不是SynthPainter)用于绘制前景,背景或边框。
     * 
     */
    private SynthPainter painter;
    /**
     * Data structure containing all of the defaults, insets, states, and other
     * values associated with this style. This instance refers to default
     * values, and are used when no overrides are discovered in the client
     * properties of a component. These values are lazily created on first
     * access.
     * <p>
     *  数据结构,包含与此样式关联的所有默认值,插入值,状态和其他值。此实例引用默认值,并且在组件的客户端属性中未发现覆盖时使用。这些值是在第一次访问时延迟创建的。
     * 
     */
    private Values values;

    /**
     * A temporary CacheKey used to perform lookups. This pattern avoids
     * creating useless garbage keys, or concatenating strings, etc.
     * <p>
     *  用于执行查找的临时CacheKey。此模式避免创建无用的垃圾密钥或连接字符串等。
     * 
     */
    private CacheKey tmpKey = new CacheKey("", 0);

    /**
     * Some NimbusStyles are created for a specific component only. In Nimbus,
     * this happens whenever the component has as a client property a
     * UIDefaults which overrides (or supplements) those defaults found in
     * UIManager.
     * <p>
     * 一些NimbusStyles仅为特定组件创建。在Nimbus中,只要组件具有作为客户端属性的UIDefaults,这会覆盖(或补充)在UIManager中找到的默认值。
     * 
     */
    private WeakReference<JComponent> component;

    /**
     * Create a new NimbusStyle. Only the prefix must be supplied. At the
     * appropriate time, installDefaults will be called. At that point, all of
     * the state information will be pulled from UIManager and stored locally
     * within this style.
     *
     * <p>
     *  创建一个新的NimbusStyle。仅必须提供前缀。在适当的时间,将调用installDefaults。在这一点上,所有的状态信息将从UIManager中拉出并在本地存储在这个样式中。
     * 
     * 
     * @param prefix Something like Button or Slider.Thumb or
     *        org.jdesktop.swingx.JXStatusBar or ComboBox."ComboBox.arrowButton"
     * @param c an optional reference to a component that this NimbusStyle
     *        should be associated with. This is only used when the component
     *        has Nimbus overrides registered in its client properties and
     *        should be null otherwise.
     */
    NimbusStyle(String prefix, JComponent c) {
        if (c != null) {
            this.component = new WeakReference<JComponent>(c);
        }
        this.prefix = prefix;
        this.painter = new SynthPainterImpl(this);
    }

    /**
     * {@inheritDoc}
     *
     * Overridden to cause this style to populate itself with data from
     * UIDefaults, if necessary.
     * <p>
     *  {@inheritDoc}
     * 
     *  覆盖以使此样式使用UIDefaults中的数据填充自己,如果需要。
     * 
     */
    @Override public void installDefaults(SynthContext ctx) {
        validate();

        //delegate to the superclass to install defaults such as background,
        //foreground, font, and opaque onto the swing component.
        super.installDefaults(ctx);
    }

    /**
     * Pulls data out of UIDefaults, if it has not done so already, and sets
     * up the internal state.
     * <p>
     *  从UIDefaults中提取数据,如果它还没有这样做,并设置内部状态。
     * 
     */
    private void validate() {
        // a non-null values object is the flag we use to determine whether
        // to reparse from UIManager.
        if (values != null) return;

        // reconstruct this NimbusStyle based on the entries in the UIManager
        // and possibly based on any overrides within the component's
        // client properties (assuming such a component exists and contains
        // any Nimbus.Overrides)
        values = new Values();

        Map<String, Object> defaults =
                ((NimbusLookAndFeel) UIManager.getLookAndFeel()).
                        getDefaultsForPrefix(prefix);

        // inspect the client properties for the key "Nimbus.Overrides". If the
        // value is an instance of UIDefaults, then these defaults are used
        // in place of, or in addition to, the defaults in UIManager.
        if (component != null) {
            // We know component.get() is non-null here, as if the component
            // were GC'ed, we wouldn't be processing its style.
            Object o = component.get().getClientProperty("Nimbus.Overrides");
            if (o instanceof UIDefaults) {
                Object i = component.get().getClientProperty(
                        "Nimbus.Overrides.InheritDefaults");
                boolean inherit = i instanceof Boolean ? (Boolean)i : true;
                UIDefaults d = (UIDefaults)o;
                TreeMap<String, Object> map = new TreeMap<String, Object>();
                for (Object obj : d.keySet()) {
                    if (obj instanceof String) {
                        String key = (String)obj;
                        if (key.startsWith(prefix)) {
                            map.put(key, d.get(key));
                        }
                    }
                }
                if (inherit) {
                    defaults.putAll(map);
                } else {
                    defaults = map;
                }
            }
        }

        //a list of the different types of states used by this style. This
        //list may contain only "standard" states (those defined by Synth),
        //or it may contain custom states, or it may contain only "standard"
        //states but list them in a non-standard order.
        List<State> states = new ArrayList<State>();
        //a map of state name to code
        Map<String,Integer> stateCodes = new HashMap<String,Integer>();
        //This is a list of runtime "state" context objects. These contain
        //the values associated with each state.
        List<RuntimeState> runtimeStates = new ArrayList<RuntimeState>();

        //determine whether there are any custom states, or custom state
        //order. If so, then read all those custom states and define the
        //"values" stateTypes to be a non-null array.
        //Otherwise, let the "values" stateTypes be null to indicate that
        //there are no custom states or custom state ordering
        String statesString = (String)defaults.get(prefix + ".States");
        if (statesString != null) {
            String s[] = statesString.split(",");
            for (int i=0; i<s.length; i++) {
                s[i] = s[i].trim();
                if (!State.isStandardStateName(s[i])) {
                    //this is a non-standard state name, so look for the
                    //custom state associated with it
                    String stateName = prefix + "." + s[i];
                    State customState = (State)defaults.get(stateName);
                    if (customState != null) {
                        states.add(customState);
                    }
                } else {
                    states.add(State.getStandardState(s[i]));
                }
            }

            //if there were any states defined, then set the stateTypes array
            //to be non-null. Otherwise, leave it null (meaning, use the
            //standard synth states).
            if (states.size() > 0) {
                values.stateTypes = states.toArray(new State[states.size()]);
            }

            //assign codes for each of the state types
            int code = 1;
            for (State state : states) {
                stateCodes.put(state.getName(), code);
                code <<= 1;
            }
        } else {
            //since there were no custom states defined, setup the list of
            //standard synth states. Note that the "v.stateTypes" is not
            //being set here, indicating that at runtime the state selection
            //routines should use standard synth states instead of custom
            //states. I do need to popuplate this temp list now though, so that
            //the remainder of this method will function as expected.
            states.add(State.Enabled);
            states.add(State.MouseOver);
            states.add(State.Pressed);
            states.add(State.Disabled);
            states.add(State.Focused);
            states.add(State.Selected);
            states.add(State.Default);

            //assign codes for the states
            stateCodes.put("Enabled", ENABLED);
            stateCodes.put("MouseOver", MOUSE_OVER);
            stateCodes.put("Pressed", PRESSED);
            stateCodes.put("Disabled", DISABLED);
            stateCodes.put("Focused", FOCUSED);
            stateCodes.put("Selected", SELECTED);
            stateCodes.put("Default", DEFAULT);
        }

        //Now iterate over all the keys in the defaults table
        for (String key : defaults.keySet()) {
            //The key is something like JButton.Enabled.backgroundPainter,
            //or JButton.States, or JButton.background.
            //Remove the "JButton." portion of the key
            String temp = key.substring(prefix.length());
            //if there is a " or : then we skip it because it is a subregion
            //of some kind
            if (temp.indexOf('"') != -1 || temp.indexOf(':') != -1) continue;
            //remove the separator
            temp = temp.substring(1);
            //At this point, temp may be any of the following:
            //background
            //[Enabled].background
            //[Enabled+MouseOver].background
            //property.foo

            //parse out the states and the property
            String stateString = null;
            String property = null;
            int bracketIndex = temp.indexOf(']');
            if (bracketIndex < 0) {
                //there is not a state string, so property = temp
                property = temp;
            } else {
                stateString = temp.substring(0, bracketIndex);
                property = temp.substring(bracketIndex + 2);
            }

            //now that I have the state (if any) and the property, get the
            //value for this property and install it where it belongs
            if (stateString == null) {
                //there was no state, just a property. Check for the custom
                //"contentMargins" property (which is handled specially by
                //Synth/Nimbus). Also check for the property being "States",
                //in which case it is not a real property and should be ignored.
                //otherwise, assume it is a property and install it on the
                //values object
                if ("contentMargins".equals(property)) {
                    values.contentMargins = (Insets)defaults.get(key);
                } else if ("States".equals(property)) {
                    //ignore
                } else {
                    values.defaults.put(property, defaults.get(key));
                }
            } else {
                //it is possible that the developer has a malformed UIDefaults
                //entry, such that something was specified in the place of
                //the State portion of the key but it wasn't a state. In this
                //case, skip will be set to true
                boolean skip = false;
                //this variable keeps track of the int value associated with
                //the state. See SynthState for details.
                int componentState = 0;
                //Multiple states may be specified in the string, such as
                //Enabled+MouseOver
                String[] stateParts = stateString.split("\\+");
                //For each state, we need to find the State object associated
                //with it, or skip it if it cannot be found.
                for (String s : stateParts) {
                    if (stateCodes.containsKey(s)) {
                        componentState |= stateCodes.get(s);
                    } else {
                        //Was not a state. Maybe it was a subregion or something
                        //skip it.
                        skip = true;
                        break;
                    }
                }

                if (skip) continue;

                //find the RuntimeState for this State
                RuntimeState rs = null;
                for (RuntimeState s : runtimeStates) {
                    if (s.state == componentState) {
                        rs = s;
                        break;
                    }
                }

                //couldn't find the runtime state, so create a new one
                if (rs == null) {
                    rs = new RuntimeState(componentState, stateString);
                    runtimeStates.add(rs);
                }

                //check for a couple special properties, such as for the
                //painters. If these are found, then set the specially on
                //the runtime state. Else, it is just a normal property,
                //so put it in the UIDefaults associated with that runtime
                //state
                if ("backgroundPainter".equals(property)) {
                    rs.backgroundPainter = getPainter(defaults, key);
                } else if ("foregroundPainter".equals(property)) {
                    rs.foregroundPainter = getPainter(defaults, key);
                } else if ("borderPainter".equals(property)) {
                    rs.borderPainter = getPainter(defaults, key);
                } else {
                    rs.defaults.put(property, defaults.get(key));
                }
            }
        }

        //now that I've collected all the runtime states, I'll sort them based
        //on their integer "state" (see SynthState for how this works).
        Collections.sort(runtimeStates, STATE_COMPARATOR);

        //finally, set the array of runtime states on the values object
        values.states = runtimeStates.toArray(new RuntimeState[runtimeStates.size()]);
    }

    private Painter getPainter(Map<String, Object> defaults, String key) {
        Object p = defaults.get(key);
        if (p instanceof UIDefaults.LazyValue) {
            p = ((UIDefaults.LazyValue)p).createValue(UIManager.getDefaults());
        }
        return (p instanceof Painter ? (Painter)p : null);
    }

    /**
     * {@inheritDoc}
     *
     * Overridden to cause this style to populate itself with data from
     * UIDefaults, if necessary.
     * <p>
     *  {@inheritDoc}
     * 
     *  覆盖以使此样式使用UIDefaults中的数据填充自己,如果必要。
     * 
     */
    @Override public Insets getInsets(SynthContext ctx, Insets in) {
        if (in == null) {
            in = new Insets(0, 0, 0, 0);
        }

        Values v = getValues(ctx);

        if (v.contentMargins == null) {
            in.bottom = in.top = in.left = in.right = 0;
            return in;
        } else {
            in.bottom = v.contentMargins.bottom;
            in.top = v.contentMargins.top;
            in.left = v.contentMargins.left;
            in.right = v.contentMargins.right;
            // Account for scale
            // The key "JComponent.sizeVariant" is used to match Apple's LAF
            String scaleKey = (String)ctx.getComponent().getClientProperty(
                    "JComponent.sizeVariant");
            if (scaleKey != null){
                if (LARGE_KEY.equals(scaleKey)){
                    in.bottom *= LARGE_SCALE;
                    in.top *= LARGE_SCALE;
                    in.left *= LARGE_SCALE;
                    in.right *= LARGE_SCALE;
                } else if (SMALL_KEY.equals(scaleKey)){
                    in.bottom *= SMALL_SCALE;
                    in.top *= SMALL_SCALE;
                    in.left *= SMALL_SCALE;
                    in.right *= SMALL_SCALE;
                } else if (MINI_KEY.equals(scaleKey)){
                    in.bottom *= MINI_SCALE;
                    in.top *= MINI_SCALE;
                    in.left *= MINI_SCALE;
                    in.right *= MINI_SCALE;
                }
            }
            return in;
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>Overridden to cause this style to populate itself with data from
     * UIDefaults, if necessary.</p>
     *
     * <p>In addition, NimbusStyle handles ColorTypes slightly differently from
     * Synth.</p>
     * <ul>
     *  <li>ColorType.BACKGROUND will equate to the color stored in UIDefaults
     *      named "background".</li>
     *  <li>ColorType.TEXT_BACKGROUND will equate to the color stored in
     *      UIDefaults named "textBackground".</li>
     *  <li>ColorType.FOREGROUND will equate to the color stored in UIDefaults
     *      named "textForeground".</li>
     *  <li>ColorType.TEXT_FOREGROUND will equate to the color stored in
     *      UIDefaults named "textForeground".</li>
     * </ul>
     * <p>
     *  {@inheritDoc}
     * 
     *  <p>如有必要,覆盖此类型以使用此样式填充来自UIDefaults的数据。</p>
     * 
     *  <p>此外,NimbusStyle处理ColorTypes与Synth稍有不同。</p>
     * <ul>
     *  <li> ColorType.BACKGROUND将等同于存储在名为"background"的UIDefault中的颜色。
     * </li> <li> ColorType.TEXT_BACKGROUND将等同于存储在名为"textBackground"的UIDefault中的颜色。
     * </li> <li> ColorType .FOREGROUND将等同于存储在名为"textForeground"的UIDefault中的颜色。
     * </li> <li> ColorType.TEXT_FOREGROUND将等同于存储在名为"textForeground"的UIDefault中的颜色。
     * </ul>
     */
    @Override protected Color getColorForState(SynthContext ctx, ColorType type) {
        String key = null;
        if (type == ColorType.BACKGROUND) {
            key = "background";
        } else if (type == ColorType.FOREGROUND) {
            //map FOREGROUND as TEXT_FOREGROUND
            key = "textForeground";
        } else if (type == ColorType.TEXT_BACKGROUND) {
            key = "textBackground";
        } else if (type == ColorType.TEXT_FOREGROUND) {
            key = "textForeground";
        } else if (type == ColorType.FOCUS) {
            key = "focus";
        } else if (type != null) {
            key = type.toString();
        } else {
            return DEFAULT_COLOR;
        }
        Color c = (Color) get(ctx, key);
        //if all else fails, return a default color (which is a ColorUIResource)
        if (c == null) c = DEFAULT_COLOR;
        return c;
    }

    /**
     * {@inheritDoc}
     *
     * Overridden to cause this style to populate itself with data from
     * UIDefaults, if necessary. If a value named "font" is not found in
     * UIDefaults, then the "defaultFont" font in UIDefaults will be returned
     * instead.
     * <p>
     *  {@inheritDoc}
     * 
     * 覆盖以使此样式使用UIDefaults中的数据填充自己,如果必要。如果在UIDefaults中找不到名为"font"的值,则将返回UIDefaults中的"defaultFont"字体。
     * 
     */
    @Override protected Font getFontForState(SynthContext ctx) {
        Font f = (Font)get(ctx, "font");
        if (f == null) f = UIManager.getFont("defaultFont");

        // Account for scale
        // The key "JComponent.sizeVariant" is used to match Apple's LAF
        String scaleKey = (String)ctx.getComponent().getClientProperty(
                "JComponent.sizeVariant");
        if (scaleKey != null){
            if (LARGE_KEY.equals(scaleKey)){
                f = f.deriveFont(Math.round(f.getSize2D()*LARGE_SCALE));
            } else if (SMALL_KEY.equals(scaleKey)){
                f = f.deriveFont(Math.round(f.getSize2D()*SMALL_SCALE));
            } else if (MINI_KEY.equals(scaleKey)){
                f = f.deriveFont(Math.round(f.getSize2D()*MINI_SCALE));
            }
        }
        return f;
    }

    /**
     * {@inheritDoc}
     *
     * Returns the SynthPainter for this style, which ends up delegating to
     * the Painters installed in this style.
     * <p>
     *  {@inheritDoc}
     * 
     *  返回此样式的SynthPainter,最终委派给以此样式安装的Painters。
     * 
     */
    @Override public SynthPainter getPainter(SynthContext ctx) {
        return painter;
    }

    /**
     * {@inheritDoc}
     *
     * Overridden to cause this style to populate itself with data from
     * UIDefaults, if necessary. If opacity is not specified in UI defaults,
     * then it defaults to being non-opaque.
     * <p>
     *  {@inheritDoc}
     * 
     *  覆盖以使此样式使用UIDefaults中的数据填充自己,如果必要。如果未在UI默认值中指定透明度,则默认为非不透明。
     * 
     */
    @Override public boolean isOpaque(SynthContext ctx) {
        // Force Table CellRenderers to be opaque
        if ("Table.cellRenderer".equals(ctx.getComponent().getName())) {
            return true;
        }
        Boolean opaque = (Boolean)get(ctx, "opaque");
        return opaque == null ? false : opaque;
    }

    /**
     * {@inheritDoc}
     *
     * <p>Overridden to cause this style to populate itself with data from
     * UIDefaults, if necessary.</p>
     *
     * <p>Properties in UIDefaults may be specified in a chained manner. For
     * example:
     * <pre>
     * background
     * Button.opacity
     * Button.Enabled.foreground
     * Button.Enabled+Selected.background
     * </pre>
     *
     * <p>In this example, suppose you were in the Enabled+Selected state and
     * searched for "foreground". In this case, we first check for
     * Button.Enabled+Selected.foreground, but no such color exists. We then
     * fall back to the next valid state, in this case,
     * Button.Enabled.foreground, and have a match. So we return it.</p>
     *
     * <p>Again, if we were in the state Enabled and looked for "background", we
     * wouldn't find it in Button.Enabled, or in Button, but would at the top
     * level in UIManager. So we return that value.</p>
     *
     * <p>One special note: the "key" passed to this method could be of the form
     * "background" or "Button.background" where "Button" equals the prefix
     * passed to the NimbusStyle constructor. In either case, it looks for
     * "background".</p>
     *
     * <p>
     *  {@inheritDoc}
     * 
     *  <p>如有必要,覆盖此类型以使用此样式填充来自UIDefaults的数据。</p>
     * 
     *  <p> UIDefaults中的属性可以以链接方式指定。例如：
     * <pre>
     *  后台Button.opacity Button.Enabled.foreground Button.Enabled + Selected.background
     * </pre>
     * 
     *  <p>在此示例中,假设您处于Enabled + Selected状态并搜索"foreground"。
     * 在这种情况下,我们首先检查Button.Enabled + Selected.foreground,但没有这样的颜色存在。
     * 然后我们回到下一个有效状态,在这种情况下,Button.Enabled.foreground,并有一个匹配。所以我们返回。</p>。
     * 
     *  <p>同样,如果我们处于"启用"状态并查找"背景",我们将无法在Button.Enabled或Button中找到它,但会在UIManager的顶级。因此我们返回该值。</p>
     * 
     * <p>一个特别的注意事项：传递给此方法的"键"可以是"background"或"Button.background"形式,其中"Button"等于传递给NimbusStyle构造函数的前缀。
     * 无论在哪种情况下,它都会查找"背景"。</p>。
     * 
     * 
     * @param ctx
     * @param key must not be null
     */
    @Override public Object get(SynthContext ctx, Object key) {
        Values v = getValues(ctx);

        // strip off the prefix, if there is one.
        String fullKey = key.toString();
        String partialKey = fullKey.substring(fullKey.indexOf(".") + 1);

        Object obj = null;
        int xstate = getExtendedState(ctx, v);

        // check the cache
        tmpKey.init(partialKey, xstate);
        obj = v.cache.get(tmpKey);
        boolean wasInCache = obj != null;
        if (!wasInCache){
            // Search exact matching states and then lesser matching states
            RuntimeState s = null;
            int[] lastIndex = new int[] {-1};
            while (obj == null &&
                    (s = getNextState(v.states, lastIndex, xstate)) != null) {
                obj = s.defaults.get(partialKey);
            }
            // Search Region Defaults
            if (obj == null && v.defaults != null) {
                obj = v.defaults.get(partialKey);
            }
            // return found object
            // Search UIManager Defaults
            if (obj == null) obj = UIManager.get(fullKey);
            // Search Synth Defaults for InputMaps
            if (obj == null && partialKey.equals("focusInputMap")) {
                obj = super.get(ctx, fullKey);
            }
            // if all we got was a null, store this fact for later use
            v.cache.put(new CacheKey(partialKey, xstate),
                    obj == null ? NULL : obj);
        }
        // return found object
        return obj == NULL ? null : obj;
    }

    /**
     * Gets the appropriate background Painter, if there is one, for the state
     * specified in the given SynthContext. This method does appropriate
     * fallback searching, as described in #get.
     *
     * <p>
     *  获取适当的背景画家,如果有一个,为给定的SynthContext指定的状态。此方法会执行适当的后备搜索,如#get中所述。
     * 
     * 
     * @param ctx The SynthContext. Must not be null.
     * @return The background painter associated for the given state, or null if
     * none could be found.
     */
    public Painter getBackgroundPainter(SynthContext ctx) {
        Values v = getValues(ctx);
        int xstate = getExtendedState(ctx, v);
        Painter p = null;

        // check the cache
        tmpKey.init("backgroundPainter$$instance", xstate);
        p = (Painter)v.cache.get(tmpKey);
        if (p != null) return p;

        // not in cache, so lookup and store in cache
        RuntimeState s = null;
        int[] lastIndex = new int[] {-1};
        while ((s = getNextState(v.states, lastIndex, xstate)) != null) {
            if (s.backgroundPainter != null) {
                p = s.backgroundPainter;
                break;
            }
        }
        if (p == null) p = (Painter)get(ctx, "backgroundPainter");
        if (p != null) {
            v.cache.put(new CacheKey("backgroundPainter$$instance", xstate), p);
        }
        return p;
    }

    /**
     * Gets the appropriate foreground Painter, if there is one, for the state
     * specified in the given SynthContext. This method does appropriate
     * fallback searching, as described in #get.
     *
     * <p>
     *  获取适当的前台画笔,如果有一个,为给定的SynthContext中指定的状态。此方法会执行适当的后备搜索,如#get中所述。
     * 
     * 
     * @param ctx The SynthContext. Must not be null.
     * @return The foreground painter associated for the given state, or null if
     * none could be found.
     */
    public Painter getForegroundPainter(SynthContext ctx) {
        Values v = getValues(ctx);
        int xstate = getExtendedState(ctx, v);
        Painter p = null;

        // check the cache
        tmpKey.init("foregroundPainter$$instance", xstate);
        p = (Painter)v.cache.get(tmpKey);
        if (p != null) return p;

        // not in cache, so lookup and store in cache
        RuntimeState s = null;
        int[] lastIndex = new int[] {-1};
        while ((s = getNextState(v.states, lastIndex, xstate)) != null) {
            if (s.foregroundPainter != null) {
                p = s.foregroundPainter;
                break;
            }
        }
        if (p == null) p = (Painter)get(ctx, "foregroundPainter");
        if (p != null) {
            v.cache.put(new CacheKey("foregroundPainter$$instance", xstate), p);
        }
        return p;
    }

    /**
     * Gets the appropriate border Painter, if there is one, for the state
     * specified in the given SynthContext. This method does appropriate
     * fallback searching, as described in #get.
     *
     * <p>
     *  获取适当的边框画笔,如果有一个,为给定的SynthContext指定的状态。此方法会执行适当的后备搜索,如#get中所述。
     * 
     * 
     * @param ctx The SynthContext. Must not be null.
     * @return The border painter associated for the given state, or null if
     * none could be found.
     */
    public Painter getBorderPainter(SynthContext ctx) {
        Values v = getValues(ctx);
        int xstate = getExtendedState(ctx, v);
        Painter p = null;

        // check the cache
        tmpKey.init("borderPainter$$instance", xstate);
        p = (Painter)v.cache.get(tmpKey);
        if (p != null) return p;

        // not in cache, so lookup and store in cache
        RuntimeState s = null;
        int[] lastIndex = new int[] {-1};
        while ((s = getNextState(v.states, lastIndex, xstate)) != null) {
            if (s.borderPainter != null) {
                p = s.borderPainter;
                break;
            }
        }
        if (p == null) p = (Painter)get(ctx, "borderPainter");
        if (p != null) {
            v.cache.put(new CacheKey("borderPainter$$instance", xstate), p);
        }
        return p;
    }

    /**
     * Utility method which returns the proper Values based on the given
     * SynthContext. Ensures that parsing of the values has occurred, or
     * reoccurs as necessary.
     *
     * <p>
     *  实用程序方法根据给定的SynthContext返回正确的值。确保对值进行解析,或根据需要重新发生。
     * 
     * 
     * @param ctx The SynthContext
     * @return a non-null values reference
     */
    private Values getValues(SynthContext ctx) {
        validate();
        return values;
    }

    /**
     * Simple utility method that searches the given array of Strings for the
     * given string. This method is only called from getExtendedState if
     * the developer has specified a specific state for the component to be
     * in (ie, has "wedged" the component in that state) by specifying
     * they client property "Nimbus.State".
     *
     * <p>
     *  简单实用程序方法搜索给定字符串数组的给定字符串。
     * 如果开发人员通过指定它们的客户端属性"Nimbus.State"来指定要在其中的组件的特定状态(即,已经"处于该状态的组件"),则该方法仅从getExtendedState调用。
     * 
     * 
     * @param names a non-null array of strings
     * @param name the name to look for in the array
     * @return true or false based on whether the given name is in the array
     */
    private boolean contains(String[] names, String name) {
        assert name != null;
        for (int i=0; i<names.length; i++) {
            if (name.equals(names[i])) {
                return true;
            }
        }
        return false;
    }

    /**
     * <p>Gets the extended state for a given synth context. Nimbus supports the
     * ability to define custom states. The algorithm used for choosing what
     * style information to use for a given state requires a single integer
     * bit string where each bit in the integer represents a different state
     * that the component is in. This method uses the componentState as
     * reported in the SynthContext, in addition to custom states, to determine
     * what this extended state is.</p>
     *
     * <p>In addition, this method checks the component in the given context
     * for a client property called "Nimbus.State". If one exists, then it will
     * decompose the String associated with that property to determine what
     * state to return. In this way, the developer can force a component to be
     * in a specific state, regardless of what the "real" state of the component
     * is.</p>
     *
     * <p>The string associated with "Nimbus.State" would be of the form:
     * <pre>Enabled+CustomState+MouseOver</pre></p>
     *
     * <p>
     * <p>获取给定合成上下文的扩展状态。 Nimbus支持定义自定义状态的能力。用于选择为给定状态使用什么样式信息的算法需要一个整数位字符串,其中整数中的每个位表示组件所处的不同状态。
     * 此方法使用SynthContext中报告的componentState,此外,自定义状态,以确定此扩展状态是什么。</p>。
     * 
     *  <p>此外,此方法在给定上下文中检查名为"Nimbus.State"的客户端属性的组件。如果存在,那么它将分解与该属性相关联的字符串,以确定返回什么状态。
     * 这样,无论组件的"真实"状态如何,开发人员都可以强制组件处于特定状态。</p>。
     * 
     *  <p>与"Nimbus.State"相关联的字符串的形式如下：<pre> Enabled + CustomState + MouseOver </pre> </p>
     * 
     * 
     * @param ctx
     * @param v
     * @return
     */
    private int getExtendedState(SynthContext ctx, Values v) {
        JComponent c = ctx.getComponent();
        int xstate = 0;
        int mask = 1;
        //check for the Nimbus.State client property
        //Performance NOTE: getClientProperty ends up inside a synchronized
        //block, so there is some potential for performance issues here, however
        //I'm not certain that there is one on a modern VM.
        Object property = c.getClientProperty("Nimbus.State");
        if (property != null) {
            String stateNames = property.toString();
            String[] states = stateNames.split("\\+");
            if (v.stateTypes == null){
                // standard states only
                for (String stateStr : states) {
                    State.StandardState s = State.getStandardState(stateStr);
                    if (s != null) xstate |= s.getState();
                }
            } else {
                // custom states
                for (State s : v.stateTypes) {
                    if (contains(states, s.getName())) {
                        xstate |= mask;
                    }
                    mask <<= 1;
                }
            }
        } else {
            //if there are no custom states defined, then simply return the
            //state that Synth reported
            if (v.stateTypes == null) return ctx.getComponentState();

            //there are custom states on this values, so I'll have to iterate
            //over them all and return a custom extended state
            int state = ctx.getComponentState();
            for (State s : v.stateTypes) {
                if (s.isInState(c, state)) {
                    xstate |= mask;
                }
                mask <<= 1;
            }
        }
        return xstate;
    }

    /**
     * <p>Gets the RuntimeState that most closely matches the state in the given
     * context, but is less specific than the given "lastState". Essentially,
     * this allows you to search for the next best state.</p>
     *
     * <p>For example, if you had the following three states:
     * <pre>
     * Enabled
     * Enabled+Pressed
     * Disabled
     * </pre>
     * And you wanted to find the state that best represented
     * ENABLED+PRESSED+FOCUSED and <code>lastState</code> was null (or an
     * empty array, or an array with a single int with index == -1), then
     * Enabled+Pressed would be returned. If you then call this method again but
     * pass the index of Enabled+Pressed as the "lastState", then
     * Enabled would be returned. If you call this method a third time and pass
     * the index of Enabled in as the <code>lastState</code>, then null would be
     * returned.</p>
     *
     * <p>The actual code path for determining the proper state is the same as
     * in Synth.</p>
     *
     * <p>
     *  <p>获取与给定上下文中的状态最接近匹配的RuntimeState,但不如给定的"lastState"特定。基本上,这允许您搜索下一个最佳状态。</p>
     * 
     *  <p>例如,如果您具有以下三个状态：
     * <pre>
     *  启用启用+按下禁用
     * </pre>
     * 并且你想找到最好地表示ENABLED + PRESSED + FOCUSED和<code> lastState </code>为null(或一个空数组,或一个单一的int与索引== -1的数组)的状态,
     * 然后Enabled +按下将返回。
     * 如果然后再次调用此方法,但将"Enabled + Pressed"的索引作为"lastState"传递,则将返回"Enabled"。
     * 如果你第三次调用这个方法,并传递Enabled的索引作为<code> lastState </code>,那么将返回null。</p>。
     * 
     *  <p>确定适当状态的实际代码路径与Synth。</p>中的相同
     * 
     * 
     * @param ctx
     * @param lastState a 1 element array, allowing me to do pass-by-reference.
     * @return
     */
    private RuntimeState getNextState(RuntimeState[] states,
                                      int[] lastState,
                                      int xstate) {
        // Use the StateInfo with the most bits that matches that of state.
        // If there are none, then fallback to
        // the StateInfo with a state of 0, indicating it'll match anything.

        // Consider if we have 3 StateInfos a, b and c with states:
        // SELECTED, SELECTED | ENABLED, 0
        //
        // Input                          Return Value
        // -----                          ------------
        // SELECTED                       a
        // SELECTED | ENABLED             b
        // MOUSE_OVER                     c
        // SELECTED | ENABLED | FOCUSED   b
        // ENABLED                        c

        if (states != null && states.length > 0) {
            int bestCount = 0;
            int bestIndex = -1;
            int wildIndex = -1;

            //if xstate is 0, then search for the runtime state with component
            //state of 0. That is, find the exact match and return it.
            if (xstate == 0) {
                for (int counter = states.length - 1; counter >= 0; counter--) {
                    if (states[counter].state == 0) {
                        lastState[0] = counter;
                        return states[counter];
                    }
                }
                //an exact match couldn't be found, so there was no match.
                lastState[0] = -1;
                return null;
            }

            //xstate is some value != 0

            //determine from which index to start looking. If lastState[0] is -1
            //then we know to start from the end of the state array. Otherwise,
            //we start at the lastIndex - 1.
            int lastStateIndex = lastState == null || lastState[0] == -1 ?
                states.length : lastState[0];

            for (int counter = lastStateIndex - 1; counter >= 0; counter--) {
                int oState = states[counter].state;

                if (oState == 0) {
                    if (wildIndex == -1) {
                        wildIndex = counter;
                    }
                } else if ((xstate & oState) == oState) {
                    // This is key, we need to make sure all bits of the
                    // StateInfo match, otherwise a StateInfo with
                    // SELECTED | ENABLED would match ENABLED, which we
                    // don't want.

                    // This comes from BigInteger.bitCnt
                    int bitCount = oState;
                    bitCount -= (0xaaaaaaaa & bitCount) >>> 1;
                    bitCount = (bitCount & 0x33333333) + ((bitCount >>> 2) &
                            0x33333333);
                    bitCount = bitCount + (bitCount >>> 4) & 0x0f0f0f0f;
                    bitCount += bitCount >>> 8;
                    bitCount += bitCount >>> 16;
                    bitCount = bitCount & 0xff;
                    if (bitCount > bestCount) {
                        bestIndex = counter;
                        bestCount = bitCount;
                    }
                }
            }
            if (bestIndex != -1) {
                lastState[0] = bestIndex;
                return states[bestIndex];
            }
            if (wildIndex != -1) {
                lastState[0] = wildIndex;
                return states[wildIndex];
            }
        }
        lastState[0] = -1;
        return null;
    }

    /**
     * Contains values such as the UIDefaults and painters associated with
     * a state. Whereas <code>State</code> represents a distinct state that a
     * component can be in (such as Enabled), this class represents the colors,
     * fonts, painters, etc associated with some state for this
     * style.
     * <p>
     *  包含与状态关联的UIDefaults和painter等值。
     * 而<code> State </code>表示组件可以处于不同状态(如Enabled),此类表示与此样式的某些状态相关联的颜色,字体,画家等。
     * 
     */
    private final class RuntimeState implements Cloneable {
        int state;
        Painter backgroundPainter;
        Painter foregroundPainter;
        Painter borderPainter;
        String stateName;
        UIDefaults defaults = new UIDefaults(10, .7f);

        private RuntimeState(int state, String stateName) {
            this.state = state;
            this.stateName = stateName;
        }

        @Override
        public String toString() {
            return stateName;
        }

        @Override
        public RuntimeState clone() {
            RuntimeState clone = new RuntimeState(state, stateName);
            clone.backgroundPainter = backgroundPainter;
            clone.foregroundPainter = foregroundPainter;
            clone.borderPainter = borderPainter;
            clone.defaults.putAll(defaults);
            return clone;
        }
    }

    /**
     * Essentially a struct of data for a style. A default instance of this
     * class is used by NimbusStyle. Additional instances exist for each
     * component that has overrides.
     * <p>
     *  基本上是一个样式的数据结构。此类的默认实例由NimbusStyle使用。每个具有覆盖的组件都存在其他实例。
     * 
     */
    private static final class Values {
        /**
         * The list of State types. A State represents a type of state, such
         * as Enabled, Default, WindowFocused, etc. These can be custom states.
         * <p>
         *  状态类型列表。状态表示状态的类型,例如Enabled,Default,WindowFocused等。这些可以是自定义状态。
         * 
         */
        State[] stateTypes = null;
        /**
         * The list of actual runtime state representations. These can represent things such
         * as Enabled + Focused. Thus, they differ from States in that they contain
         * several states together, and have associated properties, data, etc.
         * <p>
         *  实际运行时状态表示的列表。这些可以表示诸如Enabled + Focused等。因此,它们与国家不同,它们包含几个状态在一起,并具有相关的属性,数据等。
         * 
         */
        RuntimeState[] states = null;
        /**
         * The content margins for this region.
         * <p>
         *  此区域的内容边距。
         * 
         */
        Insets contentMargins;
        /**
         * Defaults on the region/component level.
         * <p>
         *  在区域/组件级别上的默认值。
         * 
         */
        UIDefaults defaults = new UIDefaults(10, .7f);
        /**
         * Simple cache. After a value has been looked up, it is stored
         * in this cache for later retrieval. The key is a concatenation of
         * the property being looked up, two dollar signs, and the extended
         * state. So for example:
         *
         * foo.bar$$2353
         * <p>
         * 简单缓存。在查找一个值之后,它被存储在这个高速缓存中以供稍后检索。关键是被查找的属性,两个美元符号和扩展状态的级联。例如：
         * 
         *  foo.bar $$ 2353
         * 
         */
        Map<CacheKey,Object> cache = new HashMap<CacheKey,Object>();
    }

    /**
     * This implementation presupposes that key is never null and that
     * the two keys being checked for equality are never null
     * <p>
     */
    private static final class CacheKey {
        private String key;
        private int xstate;

        CacheKey(Object key, int xstate) {
            init(key, xstate);
        }

        void init(Object key, int xstate) {
            this.key = key.toString();
            this.xstate = xstate;
        }

        @Override
        public boolean equals(Object obj) {
            final CacheKey other = (CacheKey) obj;
            if (obj == null) return false;
            if (this.xstate != other.xstate) return false;
            if (!this.key.equals(other.key)) return false;
            return true;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 29 * hash + this.key.hashCode();
            hash = 29 * hash + this.xstate;
            return hash;
        }
    }
}
