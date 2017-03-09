/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2006, Oracle and/or its affiliates. All rights reserved.
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

import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.plaf.synth.SynthConstants;

/**
 * <p>Represents a built in, or custom, state in Nimbus.</p>
 *
 * <p>Synth provides several built in states, which are:
 * <ul>
 *  <li>Enabled</li>
 *  <li>Mouse Over</li>
 *  <li>Pressed</li>
 *  <li>Disabled</li>
 *  <li>Focused</li>
 *  <li>Selected</li>
 *  <li>Default</li>
 * </ul>
 *
 * <p>However, there are many more states that could be described in a LookAndFeel, and it
 * would be nice to style components differently based on these different states.
 * For example, a progress bar could be "indeterminate". It would be very convenient
 * to allow this to be defined as a "state".</p>
 *
 * <p>This class, State, is intended to be used for such situations.
 * Simply implement the abstract #isInState method. It returns true if the given
 * JComponent is "in this state", false otherwise. This method will be called
 * <em>many</em> times in <em>performance sensitive loops</em>. It must execute
 * very quickly.</p>
 *
 * <p>For example, the following might be an implementation of a custom
 * "Indeterminate" state for JProgressBars:</p>
 *
 * <pre><code>
 *     public final class IndeterminateState extends State&lt;JProgressBar&gt; {
 *         public IndeterminateState() {
 *             super("Indeterminate");
 *         }
 *
 *         &#64;Override
 *         protected boolean isInState(JProgressBar c) {
 *             return c.isIndeterminate();
 *         }
 *     }
 * </code></pre>
 * <p>
 *  <p>表示Nimbus中的内置或自定义状态。</p>
 * 
 *  <p> Synth提供了几种内置状态,它们是：
 * <ul>
 *  <li>已停用</li> <li>已停用</li> <li>已停用</li> li>默认</li>
 * </ul>
 * 
 *  <p>然而,还有许多可以在LookAndFeel中描述的状态,并且基于这些不同的状态对组件进行不同的样式是很好的。例如,进度条可以是"不确定的"。允许将其定义为"状态"将是非常方便的。</p>
 * 
 *  <p>这个类State用于这种情况。只需实现抽象#isInState方法。如果给定的JComponent是"在此状态",则返回true,否则返回false。
 * 此方法将在<em>性能敏感循环</em>中被称为<em> </em>次。它必须执行得很快。</p>。
 * 
 *  <p>例如,以下可能是JProgressBars的自定义"不确定"状态的实现：</p>
 * 
 *  <pre> <code> public final class IndeterminateState extends State&lt; JProgressBar&gt; {public IndeterminateState(){super("Indeterminate"); }
 * }。
 * 
 *  @Override protected boolean isInState(JProgressBar c){return c.isIndeterminate(); }} </code> </pre>。
 * 
 */
public abstract class State<T extends JComponent>{
    static final Map<String, StandardState> standardStates = new HashMap<String, StandardState>(7);
    static final State Enabled = new StandardState(SynthConstants.ENABLED);
    static final State MouseOver = new StandardState(SynthConstants.MOUSE_OVER);
    static final State Pressed = new StandardState(SynthConstants.PRESSED);
    static final State Disabled = new StandardState(SynthConstants.DISABLED);
    static final State Focused = new StandardState(SynthConstants.FOCUSED);
    static final State Selected = new StandardState(SynthConstants.SELECTED);
    static final State Default = new StandardState(SynthConstants.DEFAULT);

    private String name;

    /**
     * <p>Create a new custom State. Specify the name for the state. The name should
     * be unique within the states set for any one particular component.
     * The name of the state should coincide with the name used in UIDefaults.</p>
     *
     * <p>For example, the following would be correct:</p>
     * <pre><code>
     *     defaults.put("Button.States", "Enabled, Foo, Disabled");
     *     defaults.put("Button.Foo", new FooState("Foo"));
     * </code></pre>
     *
     * <p>
     * <p>创建新的自定义州。指定状态的名称。在为任何一个特定组件设置的状态中,名称应该是唯一的。状态的名称应与UIDefaults中使用的名称一致。</p>
     * 
     *  <p>例如,以下是正确的：</p> <pre> <code> defaults.put("Button.States","Enabled,Foo,Disabled"); defaults.put("B
     * utton.Foo",new FooState("Foo")); </code> </pre>。
     * 
     * 
     * @param name a simple user friendly name for the state, such as "Indeterminate"
     *        or "EmbeddedPanel" or "Blurred". It is customary to use camel case,
     *        with the first letter capitalized.
     */
    protected State(String name) {
        this.name = name;
    }

    @Override public String toString() { return name; }

    /**
     * <p>This is the main entry point, called by NimbusStyle.</p>
     *
     * <p>There are both custom states and standard states. Standard states
     * correlate to the states defined in SynthConstants. When a UI delegate
     * constructs a SynthContext, it specifies the state that the component is
     * in according to the states defined in SynthConstants. Our NimbusStyle
     * will then take this state, and query each State instance in the style
     * asking whether isInState(c, s).</p>
     *
     * <p>Now, only the standard states care about the "s" param. So we have
     * this odd arrangement:</p>
     * <ul>
     *     <li>NimbusStyle calls State.isInState(c, s)</li>
     *     <li>State.isInState(c, s) simply delegates to State.isInState(c)</li>
     *     <li><em>EXCEPT</em>, StandardState overrides State.isInState(c, s) and
     *         returns directly from that method after checking its state, and
     *         does not call isInState(c) (since it is not needed for standard states).</li>
     * </ul>
     * <p>
     *  <p>这是主入口点,由NimbusStyle调用。</p>
     * 
     *  <p>有自定义状态和标准状态。标准状态与SynthConstants中定义的状态相关。当UI委托构造一个SynthContext时,它根据SynthConstants中定义的状态指定组件所在的状态。
     * 然后,我们的NimbusStyle将采用这个状态,并查询isInState(c,s)的样式中的每个状态实例。</p>。
     * 
     *  <p>现在,只有标准状态关注"s"参数。所以我们有这种奇怪的安排：</p>
     * <ul>
     *  <li> NimbusStyle调用State.isInState(c,s)</li> <li> State.isInState(c,s)只是委派给State.isInState(c)</li> <li>
     *  <em> em>时,StandardState重写State.isInState(c,s)并在检查其状态后直接从该方法返回,并且不调用isInState(c)(因为标准状态不需要它)。
     * </li>。
     */
    boolean isInState(T c, int s) {
        return isInState(c);
    }

    /**
     * <p>Gets whether the specified JComponent is in the custom state represented
     * by this class. <em>This is an extremely performance sensitive loop.</em>
     * Please take proper precautions to ensure that it executes quickly.</p>
     *
     * <p>Nimbus uses this method to help determine what state a JComponent is
     * in. For example, a custom State could exist for JProgressBar such that
     * it would return <code>true</code> when the progress bar is indeterminate.
     * Such an implementation of this method would simply be:</p>
     *
     * <pre><code> return c.isIndeterminate();</code></pre>
     *
     * <p>
     * </ul>
     * 
     * @param c the JComponent to test. This will never be null.
     * @return true if <code>c</code> is in the custom state represented by
     *         this <code>State</code> instance
     */
    protected abstract boolean isInState(T c);

    String getName() { return name; }

    static boolean isStandardStateName(String name) {
        return standardStates.containsKey(name);
    }

    static StandardState getStandardState(String name) {
        return standardStates.get(name);
    }

    static final class StandardState extends State<JComponent> {
        private int state;

        private StandardState(int state) {
            super(toString(state));
            this.state = state;
            standardStates.put(getName(), this);
        }

        public int getState() {
            return state;
        }

        @Override
        boolean isInState(JComponent c, int s) {
            return (s & state) == state;
        }

        @Override
        protected boolean isInState(JComponent c) {
            throw new AssertionError("This method should never be called");
        }

        private static String toString(int state) {
            StringBuffer buffer = new StringBuffer();
            if ((state & SynthConstants.DEFAULT) == SynthConstants.DEFAULT) {
                buffer.append("Default");
            }
            if ((state & SynthConstants.DISABLED) == SynthConstants.DISABLED) {
                if (buffer.length() > 0) buffer.append("+");
                buffer.append("Disabled");
            }
            if ((state & SynthConstants.ENABLED) == SynthConstants.ENABLED) {
                if (buffer.length() > 0) buffer.append("+");
                buffer.append("Enabled");
            }
            if ((state & SynthConstants.FOCUSED) == SynthConstants.FOCUSED) {
                if (buffer.length() > 0) buffer.append("+");
                buffer.append("Focused");
            }
            if ((state & SynthConstants.MOUSE_OVER) == SynthConstants.MOUSE_OVER) {
                if (buffer.length() > 0) buffer.append("+");
                buffer.append("MouseOver");
            }
            if ((state & SynthConstants.PRESSED) == SynthConstants.PRESSED) {
                if (buffer.length() > 0) buffer.append("+");
                buffer.append("Pressed");
            }
            if ((state & SynthConstants.SELECTED) == SynthConstants.SELECTED) {
                if (buffer.length() > 0) buffer.append("+");
                buffer.append("Selected");
            }
            return buffer.toString();
        }
    }
}
