/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2005, Oracle and/or its affiliates. All rights reserved.
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
package com.sun.java.swing.plaf.gtk;

/**
/* <p>
/* 
 * @author Scott Violet
 */
public interface GTKConstants {

    /**
     * Used to indicate a constant is not defined.
     * <p>
     *  用于指示未定义的常数。
     * 
     */
    public static final int UNDEFINED = -100;

    /**
     * Java representation of native GtkIconSize enum
     * <p>
     *  本机GtkIconSize枚举的Java表示
     * 
     */
    public enum IconSize {
        INVALID,
        MENU,
        SMALL_TOOLBAR,
        LARGE_TOOLBAR,
        BUTTON,
        DND,
        DIALOG
    }

    /**
     * Java representation of native GtkTextDirection enum
     * <p>
     *  本机GtkTextDirection枚举的Java表示
     * 
     */
    public enum TextDirection {
        NONE,
        LTR,
        RTL
    }

    /**
     * Java representation of native GtkShadowType enum
     * <p>
     *  本机GtkShadowType枚举的Java表示
     * 
     */
    public enum ShadowType {
        NONE,
        IN,
        OUT,
        ETCHED_IN,
        ETCHED_OUT
    }

    /**
     * Java representation of native GtkStateType enum
     * <p>
     *  本机GtkStateType枚举的Java表示
     * 
     */
    public enum StateType {
        NORMAL,
        ACTIVE,
        PRELIGHT,
        SELECTED,
        INSENSITIVE
    }

    /**
     * Java representation of native GtkExpanderStyle enum
     * <p>
     *  本机GtkExpanderStyle枚举的Java表示
     * 
     */
    public enum ExpanderStyle {
        COLLAPSED,
        SEMI_COLLAPSED,
        SEMI_EXPANDED,
        EXPANDED,
    }

    /**
     * Java representation of native GtkPositionType enum
     * <p>
     *  本机GtkPositionType枚举的Java表示
     * 
     */
    public enum PositionType {
        LEFT,
        RIGHT,
        TOP,
        BOTTOM
    }

    /**
     * Java representation of native GtkArrowType enum
     * <p>
     *  本机GtkArrowType枚举的Java表示
     * 
     */
    public enum ArrowType {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

    /**
     * Java representation of native GtkOrientation enum
     * <p>
     *  本机GtkOrientation枚举的Java表示
     */
    public enum Orientation {
        HORIZONTAL,
        VERTICAL
    }
}
