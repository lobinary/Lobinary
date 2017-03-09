/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2000, Oracle and/or its affiliates. All rights reserved.
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


/**
 * A collection of constants generally used for positioning and orienting
 * components on the screen.
 *
 * <p>
 *  通常用于在屏幕上定位和定向组件的常数集合。
 * 
 * 
 * @author Jeff Dinkins
 * @author Ralph Kar (orientation support)
 */
public interface SwingConstants {

        /**
         * The central position in an area. Used for
         * both compass-direction constants (NORTH, etc.)
         * and box-orientation constants (TOP, etc.).
         * <p>
         *  一个区域的中心位置。用于罗盘方向常数(NORTH等)和箱体方向常数(TOP等)。
         * 
         */
        public static final int CENTER  = 0;

        //
        // Box-orientation constant used to specify locations in a box.
        //
        /**
         * Box-orientation constant used to specify the top of a box.
         * <p>
         *  面向方向常数用于指定框的顶部。
         * 
         */
        public static final int TOP     = 1;
        /**
         * Box-orientation constant used to specify the left side of a box.
         * <p>
         *  面向方向常数用于指定框的左侧。
         * 
         */
        public static final int LEFT    = 2;
        /**
         * Box-orientation constant used to specify the bottom of a box.
         * <p>
         *  用于指定盒子底部的盒子方向常数。
         * 
         */
        public static final int BOTTOM  = 3;
        /**
         * Box-orientation constant used to specify the right side of a box.
         * <p>
         *  箱体方向常数,用于指定箱体的右侧。
         * 
         */
        public static final int RIGHT   = 4;

        //
        // Compass-direction constants used to specify a position.
        //
        /**
         * Compass-direction North (up).
         * <p>
         *  指南针方向北(向上)。
         * 
         */
        public static final int NORTH      = 1;
        /**
         * Compass-direction north-east (upper right).
         * <p>
         *  指南针东北(右上)。
         * 
         */
        public static final int NORTH_EAST = 2;
        /**
         * Compass-direction east (right).
         * <p>
         *  指南针方向东(右)。
         * 
         */
        public static final int EAST       = 3;
        /**
         * Compass-direction south-east (lower right).
         * <p>
         *  指南针东南(右下)。
         * 
         */
        public static final int SOUTH_EAST = 4;
        /**
         * Compass-direction south (down).
         * <p>
         *  指南针方向南(下)。
         * 
         */
        public static final int SOUTH      = 5;
        /**
         * Compass-direction south-west (lower left).
         * <p>
         *  指南针方向西南(左下)。
         * 
         */
        public static final int SOUTH_WEST = 6;
        /**
         * Compass-direction west (left).
         * <p>
         *  指南针方向西(左)。
         * 
         */
        public static final int WEST       = 7;
        /**
         * Compass-direction north west (upper left).
         * <p>
         *  指南针方向西北(左上)。
         * 
         */
        public static final int NORTH_WEST = 8;

        //
        // These constants specify a horizontal or
        // vertical orientation. For example, they are
        // used by scrollbars and sliders.
        //
        /** Horizontal orientation. Used for scrollbars and sliders. */
        public static final int HORIZONTAL = 0;
        /** Vertical orientation. Used for scrollbars and sliders. */
        public static final int VERTICAL   = 1;

        //
        // Constants for orientation support, since some languages are
        // left-to-right oriented and some are right-to-left oriented.
        // This orientation is currently used by buttons and labels.
        //
        /**
         * Identifies the leading edge of text for use with left-to-right
         * and right-to-left languages. Used by buttons and labels.
         * <p>
         *  标识用于从左到右和从右到左的语言的文本的前沿。由按钮和标签使用。
         * 
         */
        public static final int LEADING  = 10;
        /**
         * Identifies the trailing edge of text for use with left-to-right
         * and right-to-left languages. Used by buttons and labels.
         * <p>
         *  标识用于从左到右和从右到左的语言的文本的后沿。由按钮和标签使用。
         * 
         */
        public static final int TRAILING = 11;
        /**
         * Identifies the next direction in a sequence.
         *
         * <p>
         *  标识序列中的下一个方向。
         * 
         * 
         * @since 1.4
         */
        public static final int NEXT = 12;

        /**
         * Identifies the previous direction in a sequence.
         *
         * <p>
         *  标识序列中的上一个方向。
         * 
         * @since 1.4
         */
        public static final int PREVIOUS = 13;
}
