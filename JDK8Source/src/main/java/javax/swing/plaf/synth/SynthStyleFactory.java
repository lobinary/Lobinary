/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2003, Oracle and/or its affiliates. All rights reserved.
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

import javax.swing.JComponent;

/**
 * Factory used for obtaining <code>SynthStyle</code>s.  Each of the
 * Synth <code>ComponentUI</code>s will call into the current
 * <code>SynthStyleFactory</code> to obtain a <code>SynthStyle</code>
 * for each of the distinct regions they have.
 * <p>
 * The following example creates a custom <code>SynthStyleFactory</code>
 * that returns a different style based on the <code>Region</code>:
 * <pre>
 * class MyStyleFactory extends SynthStyleFactory {
 *     public SynthStyle getStyle(JComponent c, Region id) {
 *         if (id == Region.BUTTON) {
 *             return buttonStyle;
 *         }
 *         else if (id == Region.TREE) {
 *             return treeStyle;
 *         }
 *         return defaultStyle;
 *     }
 * }
 * SynthLookAndFeel laf = new SynthLookAndFeel();
 * UIManager.setLookAndFeel(laf);
 * SynthLookAndFeel.setStyleFactory(new MyStyleFactory());
 * </pre>
 *
 * <p>
 *  工厂用于获取<code> SynthStyle </code>。
 * 每个Synth <code> ComponentUI </code>将调用当前<code> SynthStyleFactory </code>以获得它们具有的每个不同区域的<code> SynthSty
 * le </code>。
 *  工厂用于获取<code> SynthStyle </code>。
 * <p>
 *  以下示例创建了一个自定义<code> SynthStyleFactory </code>,它根据<code> Region </code>返回不同的样式：
 * <pre>
 *  class MyStyleFactory extends SynthStyleFactory {public SynthStyle getStyle(JComponent c,Region id){if(id == Region.BUTTON){return buttonStyle; }
 *  else if(id == Region.TREE){return treeStyle; } return defaultStyle; }} SynthLookAndFeel laf = new Sy
 * nthLookAndFeel(); UIManager.setLookAndFeel(laf); SynthLookAndFeel.setStyleFactory(new MyStyleFactory(
 * 
 * @see SynthStyleFactory
 * @see SynthStyle
 *
 * @since 1.5
 * @author Scott Violet
 */
public abstract class SynthStyleFactory {
    /**
     * Creates a <code>SynthStyleFactory</code>.
     * <p>
     * ));。
     * </pre>
     * 
     */
    public SynthStyleFactory() {
    }

    /**
     * Returns the style for the specified Component.
     *
     * <p>
     *  创建<code> SynthStyleFactory </code>。
     * 
     * 
     * @param c Component asking for
     * @param id Region identifier
     * @return SynthStyle for region.
     */
    public abstract SynthStyle getStyle(JComponent c, Region id);
}
