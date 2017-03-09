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
package javax.swing.plaf.metal;

import java.awt.*;

/**
 * DesktopProperty that only uses font height in configuring font. This
 * is only used on Windows.
 *
 * <p>
 *  DesktopProperty仅在配置字体时使用字体高度。这只在Windows上使用。
 * 
 */
class MetalFontDesktopProperty extends com.sun.java.swing.plaf.windows.DesktopProperty {
    /**
     * Maps from metal font theme type as defined in MetalTheme
     * to the corresponding desktop property name.
     * <p>
     *  将MetalTheme中定义的金属字体主题类型映射到相应的桌面属性名称。
     * 
     */
    private static final String[] propertyMapping = {
        "win.ansiVar.font.height",
        "win.tooltip.font.height",
        "win.ansiVar.font.height",
        "win.menu.font.height",
        "win.frame.captionFont.height",
        "win.menu.font.height"
    };

    /**
     * Corresponds to a MetalTheme font type.
     * <p>
     *  对应于MetalTheme字体类型。
     * 
     */
    private int type;


    /**
     * Creates a MetalFontDesktopProperty. The key used to lookup the
     * desktop property is determined from the type of font.
     *
     * <p>
     *  创建一个MetalFontDesktopProperty。用于查找桌面属性的键是根据字体类型确定的。
     * 
     * 
     * @param type MetalTheme font type.
     */
    MetalFontDesktopProperty(int type) {
        this(propertyMapping[type], type);
    }

    /**
     * Creates a MetalFontDesktopProperty.
     *
     * <p>
     *  创建一个MetalFontDesktopProperty。
     * 
     * 
     * @param key Key used in looking up desktop value.
     * @param toolkit Toolkit used to fetch property from, can be null
     *        in which default will be used.
     * @param type Type of font being used, corresponds to MetalTheme font
     *        type.
     */
    MetalFontDesktopProperty(String key, int type) {
        super(key, null);
        this.type = type;
    }

    /**
     * Overriden to create a Font with the size coming from the desktop
     * and the style and name coming from DefaultMetalTheme.
     * <p>
     *  覆盖创建一个字体,其大小来自于桌面,以及来自DefaultMetalTheme的样式和名称。
     * 
     */
    protected Object configureValue(Object value) {
        if (value instanceof Integer) {
            value = new Font(DefaultMetalTheme.getDefaultFontName(type),
                             DefaultMetalTheme.getDefaultFontStyle(type),
                             ((Integer)value).intValue());
        }
        return super.configureValue(value);
    }

    /**
     * Returns the default font.
     * <p>
     *  返回默认字体。
     */
    protected Object getDefaultValue() {
        return new Font(DefaultMetalTheme.getDefaultFontName(type),
                        DefaultMetalTheme.getDefaultFontStyle(type),
                        DefaultMetalTheme.getDefaultFontSize(type));
    }
}
