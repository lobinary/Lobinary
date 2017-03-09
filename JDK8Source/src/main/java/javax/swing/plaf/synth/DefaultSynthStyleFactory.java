/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2008, Oracle and/or its affiliates. All rights reserved.
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

import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.Font;
import java.util.*;
import java.util.regex.*;
import sun.swing.plaf.synth.*;
import sun.swing.BakedArrayList;

/**
 * Factory used for obtaining styles. Supports associating a style based on
 * the name of the component as returned by <code>Component.getName()</code>,
 * and the <code>Region</code> associated with the <code>JComponent</code>.
 * Lookup is done using regular expressions.
 *
 * <p>
 *  工厂用于获得样式。
 * 支持根据<code> Component.getName()</code>和与<code> JComponent </code>关联的<code> Region </code>返回的组件名称关联样式。
 * 查找是使用正则表达式完成的。
 * 
 * 
 * @author Scott Violet
 */
class DefaultSynthStyleFactory extends SynthStyleFactory {
    /**
     * Used to indicate the lookup should be done based on Component name.
     * <p>
     *  用于指示应根据组件名称进行查找。
     * 
     */
    public static final int NAME = 0;
    /**
     * Used to indicate the lookup should be done based on region.
     * <p>
     *  用于指示应该根据区域进行查找。
     * 
     */
    public static final int REGION = 1;

    /**
     * List containing set of StyleAssociations used in determining matching
     * styles.
     * <p>
     *  包含用于确定匹配样式的一组StyleAssociations的列表。
     * 
     */
    private List<StyleAssociation> _styles;
    /**
     * Used during lookup.
     * <p>
     *  在查找期间使用。
     * 
     */
    private BakedArrayList _tmpList;

    /**
     * Maps from a List (BakedArrayList to be precise) to the merged style.
     * <p>
     *  从列表中的地图(BakedArrayList要精确)到合并的样式。
     * 
     */
    private Map<BakedArrayList, SynthStyle> _resolvedStyles;

    /**
     * Used if there are no styles matching a widget.
     * <p>
     *  如果没有匹配窗口小部件的样式,则使用。
     * 
     */
    private SynthStyle _defaultStyle;


    DefaultSynthStyleFactory() {
        _tmpList = new BakedArrayList(5);
        _styles = new ArrayList<StyleAssociation>();
        _resolvedStyles = new HashMap<BakedArrayList, SynthStyle>();
    }

    public synchronized void addStyle(DefaultSynthStyle style,
                         String path, int type) throws PatternSyntaxException {
        if (path == null) {
            // Make an empty path match all.
            path = ".*";
        }
        if (type == NAME) {
            _styles.add(StyleAssociation.createStyleAssociation(
                            path, style, type));
        }
        else if (type == REGION) {
            _styles.add(StyleAssociation.createStyleAssociation(
                            path.toLowerCase(), style, type));
        }
    }

    /**
     * Returns the style for the specified Component.
     *
     * <p>
     *  返回指定组件的样式。
     * 
     * 
     * @param c Component asking for
     * @param id ID of the Component
     */
    public synchronized SynthStyle getStyle(JComponent c, Region id) {
        BakedArrayList matches = _tmpList;

        matches.clear();
        getMatchingStyles(matches, c, id);

        if (matches.size() == 0) {
            return getDefaultStyle();
        }
        // Use a cached Style if possible, otherwise create a new one.
        matches.cacheHashCode();
        SynthStyle style = getCachedStyle(matches);

        if (style == null) {
            style = mergeStyles(matches);

            if (style != null) {
                cacheStyle(matches, style);
            }
        }
        return style;
    }

    /**
     * Returns the style to use if there are no matching styles.
     * <p>
     *  返回在没有匹配样式时要使用的样式。
     * 
     */
    private SynthStyle getDefaultStyle() {
        if (_defaultStyle == null) {
            _defaultStyle = new DefaultSynthStyle();
            ((DefaultSynthStyle)_defaultStyle).setFont(
                new FontUIResource(Font.DIALOG, Font.PLAIN,12));
        }
        return _defaultStyle;
    }

    /**
     * Fetches any styles that match the passed into arguments into
     * <code>matches</code>.
     * <p>
     *  获取与传递到<code> matches </code>中的参数匹配的任何样式。
     * 
     */
    private void getMatchingStyles(List matches, JComponent c,
                                   Region id) {
        String idName = id.getLowerCaseName();
        String cName = c.getName();

        if (cName == null) {
            cName = "";
        }
        for (int counter = _styles.size() - 1; counter >= 0; counter--){
            StyleAssociation sa = _styles.get(counter);
            String path;

            if (sa.getID() == NAME) {
                path = cName;
            }
            else {
                path = idName;
            }

            if (sa.matches(path) && matches.indexOf(sa.getStyle()) == -1) {
                matches.add(sa.getStyle());
            }
        }
    }

    /**
     * Caches the specified style.
     * <p>
     *  缓存指定的样式。
     * 
     */
    private void cacheStyle(List styles, SynthStyle style) {
        BakedArrayList cachedStyles = new BakedArrayList(styles);

        _resolvedStyles.put(cachedStyles, style);
    }

    /**
     * Returns the cached style from the passed in arguments.
     * <p>
     *  从传入的参数中返回缓存的样式。
     * 
     */
    private SynthStyle getCachedStyle(List styles) {
        if (styles.size() == 0) {
            return null;
        }
        return _resolvedStyles.get(styles);
    }

    /**
     * Creates a single Style from the passed in styles. The passed in List
     * is reverse sorted, that is the most recently added style found to
     * match will be first.
     * <p>
     *  从传入的样式创建单个样式。传入的List是反向排序的,也就是说,最近添加的样式发现匹配将是第一个。
     */
    private SynthStyle mergeStyles(List styles) {
        int size = styles.size();

        if (size == 0) {
            return null;
        }
        else if (size == 1) {
            return (SynthStyle)((DefaultSynthStyle)styles.get(0)).clone();
        }
        // NOTE: merging is done backwards as DefaultSynthStyleFactory reverses
        // order, that is, the most specific style is first.
        DefaultSynthStyle style = (DefaultSynthStyle)styles.get(size - 1);

        style = (DefaultSynthStyle)style.clone();
        for (int counter = size - 2; counter >= 0; counter--) {
            style = ((DefaultSynthStyle)styles.get(counter)).addTo(style);
        }
        return style;
    }
}
