/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.awt;

import java.util.Locale;

/**
 * A set of attributes which control the output of a printed page.
 * <p>
 * Instances of this class control the color state, paper size (media type),
 * orientation, logical origin, print quality, and resolution of every
 * page which uses the instance. Attribute names are compliant with the
 * Internet Printing Protocol (IPP) 1.1 where possible. Attribute values
 * are partially compliant where possible.
 * <p>
 * To use a method which takes an inner class type, pass a reference to
 * one of the constant fields of the inner class. Client code cannot create
 * new instances of the inner class types because none of those classes
 * has a public constructor. For example, to set the color state to
 * monochrome, use the following code:
 * <pre>
 * import java.awt.PageAttributes;
 *
 * public class MonochromeExample {
 *     public void setMonochrome(PageAttributes pageAttributes) {
 *         pageAttributes.setColor(PageAttributes.ColorType.MONOCHROME);
 *     }
 * }
 * </pre>
 * <p>
 * Every IPP attribute which supports an <i>attributeName</i>-default value
 * has a corresponding <code>set<i>attributeName</i>ToDefault</code> method.
 * Default value fields are not provided.
 *
 * <p>
 *  控制打印页面输出的一组属性。
 * <p>
 *  此类的实例控制使用实例的每个页面的颜色状态,纸张大小(介质类型),方向,逻辑原点,打印质量和分辨率。属性名称尽可能符合Internet打印协议(IPP)1.1。属性值在可能的情况下部分符合。
 * <p>
 *  要使用一个接受一个内部类类型的方法,传递一个引用到内部类的一个常量字段。客户端代码无法创建内部类类型的新实例,因为这些类都没有公共构造函数。例如,要将颜色状态设置为单色,请使用以下代码：
 * <pre>
 *  import java.awt.PageAttributes;
 * 
 *  public class MonochromeExample {public void setMonochrome(PageAttributes pageAttributes){pageAttributes.setColor(PageAttributes.ColorType.MONOCHROME); }
 * }。
 * </pre>
 * <p>
 *  支持<i> attributeName </i> -default value的每个IPP属性都有相应的<code> set <i> attributeName </i> ToDefault </code>
 * 方法。
 * 不提供默认值字段。
 * 
 * 
 * @author      David Mendenhall
 * @since 1.3
 */
public final class PageAttributes implements Cloneable {
    /**
     * A type-safe enumeration of possible color states.
     * <p>
     *  可能的颜色状态的类型安全枚举。
     * 
     * 
     * @since 1.3
     */
    public static final class ColorType extends AttributeValue {
        private static final int I_COLOR = 0;
        private static final int I_MONOCHROME = 1;

        private static final String NAMES[] = {
            "color", "monochrome"
        };

        /**
         * The ColorType instance to use for specifying color printing.
         * <p>
         *  用于指定彩色打印的ColorType实例。
         * 
         */
        public static final ColorType COLOR = new ColorType(I_COLOR);
        /**
         * The ColorType instance to use for specifying monochrome printing.
         * <p>
         *  用于指定单色打印的ColorType实例。
         * 
         */
        public static final ColorType MONOCHROME = new ColorType(I_MONOCHROME);

        private ColorType(int type) {
            super(type, NAMES);
        }
    }

    /**
     * A type-safe enumeration of possible paper sizes. These sizes are in
     * compliance with IPP 1.1.
     * <p>
     *  可能的纸张尺寸的类型安全枚举。这些尺寸符合IPP 1.1。
     * 
     * 
     * @since 1.3
     */
    public static final class MediaType extends AttributeValue {
        private static final int I_ISO_4A0 = 0;
        private static final int I_ISO_2A0 = 1;
        private static final int I_ISO_A0 = 2;
        private static final int I_ISO_A1 = 3;
        private static final int I_ISO_A2 = 4;
        private static final int I_ISO_A3 = 5;
        private static final int I_ISO_A4 = 6;
        private static final int I_ISO_A5 = 7;
        private static final int I_ISO_A6 = 8;
        private static final int I_ISO_A7 = 9;
        private static final int I_ISO_A8 = 10;
        private static final int I_ISO_A9 = 11;
        private static final int I_ISO_A10 = 12;
        private static final int I_ISO_B0 = 13;
        private static final int I_ISO_B1 = 14;
        private static final int I_ISO_B2 = 15;
        private static final int I_ISO_B3 = 16;
        private static final int I_ISO_B4 = 17;
        private static final int I_ISO_B5 = 18;
        private static final int I_ISO_B6 = 19;
        private static final int I_ISO_B7 = 20;
        private static final int I_ISO_B8 = 21;
        private static final int I_ISO_B9 = 22;
        private static final int I_ISO_B10 = 23;
        private static final int I_JIS_B0 = 24;
        private static final int I_JIS_B1 = 25;
        private static final int I_JIS_B2 = 26;
        private static final int I_JIS_B3 = 27;
        private static final int I_JIS_B4 = 28;
        private static final int I_JIS_B5 = 29;
        private static final int I_JIS_B6 = 30;
        private static final int I_JIS_B7 = 31;
        private static final int I_JIS_B8 = 32;
        private static final int I_JIS_B9 = 33;
        private static final int I_JIS_B10 = 34;
        private static final int I_ISO_C0 = 35;
        private static final int I_ISO_C1 = 36;
        private static final int I_ISO_C2 = 37;
        private static final int I_ISO_C3 = 38;
        private static final int I_ISO_C4 = 39;
        private static final int I_ISO_C5 = 40;
        private static final int I_ISO_C6 = 41;
        private static final int I_ISO_C7 = 42;
        private static final int I_ISO_C8 = 43;
        private static final int I_ISO_C9 = 44;
        private static final int I_ISO_C10 = 45;
        private static final int I_ISO_DESIGNATED_LONG = 46;
        private static final int I_EXECUTIVE = 47;
        private static final int I_FOLIO = 48;
        private static final int I_INVOICE = 49;
        private static final int I_LEDGER = 50;
        private static final int I_NA_LETTER = 51;
        private static final int I_NA_LEGAL = 52;
        private static final int I_QUARTO = 53;
        private static final int I_A = 54;
        private static final int I_B = 55;
        private static final int I_C = 56;
        private static final int I_D = 57;
        private static final int I_E = 58;
        private static final int I_NA_10X15_ENVELOPE = 59;
        private static final int I_NA_10X14_ENVELOPE = 60;
        private static final int I_NA_10X13_ENVELOPE = 61;
        private static final int I_NA_9X12_ENVELOPE = 62;
        private static final int I_NA_9X11_ENVELOPE = 63;
        private static final int I_NA_7X9_ENVELOPE = 64;
        private static final int I_NA_6X9_ENVELOPE = 65;
        private static final int I_NA_NUMBER_9_ENVELOPE = 66;
        private static final int I_NA_NUMBER_10_ENVELOPE = 67;
        private static final int I_NA_NUMBER_11_ENVELOPE = 68;
        private static final int I_NA_NUMBER_12_ENVELOPE = 69;
        private static final int I_NA_NUMBER_14_ENVELOPE = 70;
        private static final int I_INVITE_ENVELOPE = 71;
        private static final int I_ITALY_ENVELOPE = 72;
        private static final int I_MONARCH_ENVELOPE = 73;
        private static final int I_PERSONAL_ENVELOPE = 74;

        private static final String NAMES[] = {
            "iso-4a0", "iso-2a0", "iso-a0", "iso-a1", "iso-a2", "iso-a3",
            "iso-a4", "iso-a5", "iso-a6", "iso-a7", "iso-a8", "iso-a9",
            "iso-a10", "iso-b0", "iso-b1", "iso-b2", "iso-b3", "iso-b4",
            "iso-b5", "iso-b6", "iso-b7", "iso-b8", "iso-b9", "iso-b10",
            "jis-b0", "jis-b1", "jis-b2", "jis-b3", "jis-b4", "jis-b5",
            "jis-b6", "jis-b7", "jis-b8", "jis-b9", "jis-b10", "iso-c0",
            "iso-c1", "iso-c2", "iso-c3", "iso-c4", "iso-c5", "iso-c6",
            "iso-c7", "iso-c8", "iso-c9", "iso-c10", "iso-designated-long",
            "executive", "folio", "invoice", "ledger", "na-letter", "na-legal",
            "quarto", "a", "b", "c", "d", "e", "na-10x15-envelope",
            "na-10x14-envelope", "na-10x13-envelope", "na-9x12-envelope",
            "na-9x11-envelope", "na-7x9-envelope", "na-6x9-envelope",
            "na-number-9-envelope", "na-number-10-envelope",
            "na-number-11-envelope", "na-number-12-envelope",
            "na-number-14-envelope", "invite-envelope", "italy-envelope",
            "monarch-envelope", "personal-envelope"
        };

        /**
         * The MediaType instance for ISO/DIN and JIS 4A0, 1682 x 2378 mm.
         * <p>
         * 用于ISO / DIN和JIS 4A0,1682 x 2378 mm的MediaType实例。
         * 
         */
        public static final MediaType ISO_4A0 = new MediaType(I_ISO_4A0);
        /**
         * The MediaType instance for ISO/DIN and JIS 2A0, 1189 x 1682 mm.
         * <p>
         *  ISO / DIN和JIS 2A0,1189 x 1682 mm的MediaType实例。
         * 
         */
        public static final MediaType ISO_2A0 = new MediaType(I_ISO_2A0);
        /**
         * The MediaType instance for ISO/DIN and JIS A0, 841 x 1189 mm.
         * <p>
         *  用于ISO / DIN和JIS A0,841 x 1189 mm的MediaType实例。
         * 
         */
        public static final MediaType ISO_A0 = new MediaType(I_ISO_A0);
        /**
         * The MediaType instance for ISO/DIN and JIS A1, 594 x 841 mm.
         * <p>
         *  ISO / DIN和JIS A1,594 x 841 mm的MediaType实例。
         * 
         */
        public static final MediaType ISO_A1 = new MediaType(I_ISO_A1);
        /**
         * The MediaType instance for ISO/DIN and JIS A2, 420 x 594 mm.
         * <p>
         *  用于ISO / DIN和JIS A2的MediaType实例,420 x 594 mm。
         * 
         */
        public static final MediaType ISO_A2 = new MediaType(I_ISO_A2);
        /**
         * The MediaType instance for ISO/DIN and JIS A3, 297 x 420 mm.
         * <p>
         *  用于ISO / DIN和JIS A3,297 x 420 mm的MediaType实例。
         * 
         */
        public static final MediaType ISO_A3 = new MediaType(I_ISO_A3);
        /**
         * The MediaType instance for ISO/DIN and JIS A4, 210 x 297 mm.
         * <p>
         *  ISO / DIN和JIS A4,210 x 297 mm的MediaType实例。
         * 
         */
        public static final MediaType ISO_A4 = new MediaType(I_ISO_A4);
        /**
         * The MediaType instance for ISO/DIN and JIS A5, 148 x 210 mm.
         * <p>
         *  MediaType实例用于ISO / DIN和JIS A5,148 x 210 mm。
         * 
         */
        public static final MediaType ISO_A5 = new MediaType(I_ISO_A5);
        /**
         * The MediaType instance for ISO/DIN and JIS A6, 105 x 148 mm.
         * <p>
         *  MediaType实例用于ISO / DIN和JIS A6,105 x 148 mm。
         * 
         */
        public static final MediaType ISO_A6 = new MediaType(I_ISO_A6);
        /**
         * The MediaType instance for ISO/DIN and JIS A7, 74 x 105 mm.
         * <p>
         *  MediaType实例用于ISO / DIN和JIS A7,74 x 105 mm。
         * 
         */
        public static final MediaType ISO_A7 = new MediaType(I_ISO_A7);
        /**
         * The MediaType instance for ISO/DIN and JIS A8, 52 x 74 mm.
         * <p>
         *  MediaType实例用于ISO / DIN和JIS A8,52 x 74 mm。
         * 
         */
        public static final MediaType ISO_A8 = new MediaType(I_ISO_A8);
        /**
         * The MediaType instance for ISO/DIN and JIS A9, 37 x 52 mm.
         * <p>
         *  MediaType实例用于ISO / DIN和JIS A9,37 x 52 mm。
         * 
         */
        public static final MediaType ISO_A9 = new MediaType(I_ISO_A9);
        /**
         * The MediaType instance for ISO/DIN and JIS A10, 26 x 37 mm.
         * <p>
         *  MediaType实例用于ISO / DIN和JIS A10,26 x 37 mm。
         * 
         */
        public static final MediaType ISO_A10 = new MediaType(I_ISO_A10);
        /**
         * The MediaType instance for ISO/DIN B0, 1000 x 1414 mm.
         * <p>
         *  ISO / DIN B0,1000 x 1414 mm的MediaType实例。
         * 
         */
        public static final MediaType ISO_B0 = new MediaType(I_ISO_B0);
        /**
         * The MediaType instance for ISO/DIN B1, 707 x 1000 mm.
         * <p>
         *  ISO / DIN B1的MediaType实例,707 x 1000 mm。
         * 
         */
        public static final MediaType ISO_B1 = new MediaType(I_ISO_B1);
        /**
         * The MediaType instance for ISO/DIN B2, 500 x 707 mm.
         * <p>
         *  ISO / DIN B2的MediaType实例,500 x 707 mm。
         * 
         */
        public static final MediaType ISO_B2 = new MediaType(I_ISO_B2);
        /**
         * The MediaType instance for ISO/DIN B3, 353 x 500 mm.
         * <p>
         *  ISO / DIN B3的MediaType实例,353 x 500 mm。
         * 
         */
        public static final MediaType ISO_B3 = new MediaType(I_ISO_B3);
        /**
         * The MediaType instance for ISO/DIN B4, 250 x 353 mm.
         * <p>
         *  ISO / DIN B4的MediaType实例,250 x 353 mm。
         * 
         */
        public static final MediaType ISO_B4 = new MediaType(I_ISO_B4);
        /**
         * The MediaType instance for ISO/DIN B5, 176 x 250 mm.
         * <p>
         *  ISO / DIN B5的MediaType实例,176 x 250 mm。
         * 
         */
        public static final MediaType ISO_B5 = new MediaType(I_ISO_B5);
        /**
         * The MediaType instance for ISO/DIN B6, 125 x 176 mm.
         * <p>
         *  ISO / DIN B6的MediaType实例,125 x 176 mm。
         * 
         */
        public static final MediaType ISO_B6 = new MediaType(I_ISO_B6);
        /**
         * The MediaType instance for ISO/DIN B7, 88 x 125 mm.
         * <p>
         *  ISO / DIN B7的MediaType实例,88 x 125 mm。
         * 
         */
        public static final MediaType ISO_B7 = new MediaType(I_ISO_B7);
        /**
         * The MediaType instance for ISO/DIN B8, 62 x 88 mm.
         * <p>
         *  ISO / DIN B8的MediaType实例,62 x 88 mm。
         * 
         */
        public static final MediaType ISO_B8 = new MediaType(I_ISO_B8);
        /**
         * The MediaType instance for ISO/DIN B9, 44 x 62 mm.
         * <p>
         *  ISO / DIN B9的MediaType实例,44 x 62 mm。
         * 
         */
        public static final MediaType ISO_B9 = new MediaType(I_ISO_B9);
        /**
         * The MediaType instance for ISO/DIN B10, 31 x 44 mm.
         * <p>
         *  ISO / DIN B10的MediaType实例,31 x 44 mm。
         * 
         */
        public static final MediaType ISO_B10 = new MediaType(I_ISO_B10);
        /**
         * The MediaType instance for JIS B0, 1030 x 1456 mm.
         * <p>
         *  用于JIS B0的MediaType实例,1030 x 1456 mm。
         * 
         */
        public static final MediaType JIS_B0 = new MediaType(I_JIS_B0);
        /**
         * The MediaType instance for JIS B1, 728 x 1030 mm.
         * <p>
         *  针对JIS B1的MediaType实例,728 x 1030 mm。
         * 
         */
        public static final MediaType JIS_B1 = new MediaType(I_JIS_B1);
        /**
         * The MediaType instance for JIS B2, 515 x 728 mm.
         * <p>
         * MediaType实例为JIS B2,515 x 728 mm。
         * 
         */
        public static final MediaType JIS_B2 = new MediaType(I_JIS_B2);
        /**
         * The MediaType instance for JIS B3, 364 x 515 mm.
         * <p>
         *  针对JIS B3的MediaType实例,364 x 515 mm。
         * 
         */
        public static final MediaType JIS_B3 = new MediaType(I_JIS_B3);
        /**
         * The MediaType instance for JIS B4, 257 x 364 mm.
         * <p>
         *  针对JIS B4的MediaType实例,257 x 364 mm。
         * 
         */
        public static final MediaType JIS_B4 = new MediaType(I_JIS_B4);
        /**
         * The MediaType instance for JIS B5, 182 x 257 mm.
         * <p>
         *  用于JIS B5的MediaType实例,182 x 257 mm。
         * 
         */
        public static final MediaType JIS_B5 = new MediaType(I_JIS_B5);
        /**
         * The MediaType instance for JIS B6, 128 x 182 mm.
         * <p>
         *  用于JIS B6的MediaType实例,128 x 182 mm。
         * 
         */
        public static final MediaType JIS_B6 = new MediaType(I_JIS_B6);
        /**
         * The MediaType instance for JIS B7, 91 x 128 mm.
         * <p>
         *  用于JIS B7的MediaType实例,91 x 128 mm。
         * 
         */
        public static final MediaType JIS_B7 = new MediaType(I_JIS_B7);
        /**
         * The MediaType instance for JIS B8, 64 x 91 mm.
         * <p>
         *  用于JIS B8的MediaType实例,64 x 91 mm。
         * 
         */
        public static final MediaType JIS_B8 = new MediaType(I_JIS_B8);
        /**
         * The MediaType instance for JIS B9, 45 x 64 mm.
         * <p>
         *  用于JIS B9的MediaType实例,45 x 64 mm。
         * 
         */
        public static final MediaType JIS_B9 = new MediaType(I_JIS_B9);
        /**
         * The MediaType instance for JIS B10, 32 x 45 mm.
         * <p>
         *  用于JIS B10的MediaType实例,32 x 45 mm。
         * 
         */
        public static final MediaType JIS_B10 = new MediaType(I_JIS_B10);
        /**
         * The MediaType instance for ISO/DIN C0, 917 x 1297 mm.
         * <p>
         *  ISO / DIN C0,917 x 1297 mm的MediaType实例。
         * 
         */
        public static final MediaType ISO_C0 = new MediaType(I_ISO_C0);
        /**
         * The MediaType instance for ISO/DIN C1, 648 x 917 mm.
         * <p>
         *  ISO / DIN C1,648 x 917 mm的MediaType实例。
         * 
         */
        public static final MediaType ISO_C1 = new MediaType(I_ISO_C1);
        /**
         * The MediaType instance for ISO/DIN C2, 458 x 648 mm.
         * <p>
         *  ISO / DIN C2的MediaType实例,458 x 648 mm。
         * 
         */
        public static final MediaType ISO_C2 = new MediaType(I_ISO_C2);
        /**
         * The MediaType instance for ISO/DIN C3, 324 x 458 mm.
         * <p>
         *  ISO / DIN C3,324 x 458 mm的MediaType实例。
         * 
         */
        public static final MediaType ISO_C3 = new MediaType(I_ISO_C3);
        /**
         * The MediaType instance for ISO/DIN C4, 229 x 324 mm.
         * <p>
         *  ISO / DIN C4,229 x 324 mm的MediaType实例。
         * 
         */
        public static final MediaType ISO_C4 = new MediaType(I_ISO_C4);
        /**
         * The MediaType instance for ISO/DIN C5, 162 x 229 mm.
         * <p>
         *  ISO / DIN C5的MediaType实例,162 x 229 mm。
         * 
         */
        public static final MediaType ISO_C5 = new MediaType(I_ISO_C5);
        /**
         * The MediaType instance for ISO/DIN C6, 114 x 162 mm.
         * <p>
         *  ISO / DIN C6的MediaType实例,114 x 162 mm。
         * 
         */
        public static final MediaType ISO_C6 = new MediaType(I_ISO_C6);
        /**
         * The MediaType instance for ISO/DIN C7, 81 x 114 mm.
         * <p>
         *  ISO / DIN C7的MediaType实例,81 x 114 mm。
         * 
         */
        public static final MediaType ISO_C7 = new MediaType(I_ISO_C7);
        /**
         * The MediaType instance for ISO/DIN C8, 57 x 81 mm.
         * <p>
         *  ISO / DIN C8的MediaType实例,57 x 81 mm。
         * 
         */
        public static final MediaType ISO_C8 = new MediaType(I_ISO_C8);
        /**
         * The MediaType instance for ISO/DIN C9, 40 x 57 mm.
         * <p>
         *  ISO / DIN C9的MediaType实例,40 x 57 mm。
         * 
         */
        public static final MediaType ISO_C9 = new MediaType(I_ISO_C9);
        /**
         * The MediaType instance for ISO/DIN C10, 28 x 40 mm.
         * <p>
         *  ISO / DIN C10,28 x 40 mm的MediaType实例。
         * 
         */
        public static final MediaType ISO_C10 = new MediaType(I_ISO_C10);
        /**
         * The MediaType instance for ISO Designated Long, 110 x 220 mm.
         * <p>
         *  ISO指定长的MediaType实例,110 x 220毫米。
         * 
         */
        public static final MediaType ISO_DESIGNATED_LONG =
            new MediaType(I_ISO_DESIGNATED_LONG);
        /**
         * The MediaType instance for Executive, 7 1/4 x 10 1/2 in.
         * <p>
         *  Executive的MediaType实例,7 1/4 x 10 1/2 in。
         * 
         */
        public static final MediaType EXECUTIVE = new MediaType(I_EXECUTIVE);
        /**
         * The MediaType instance for Folio, 8 1/2 x 13 in.
         * <p>
         *  Folio的MediaType实例,8 1/2 x 13英寸
         * 
         */
        public static final MediaType FOLIO = new MediaType(I_FOLIO);
        /**
         * The MediaType instance for Invoice, 5 1/2 x 8 1/2 in.
         * <p>
         *  Invoice的MediaType实例,5 1/2 x 8 1/2 in。
         * 
         */
        public static final MediaType INVOICE = new MediaType(I_INVOICE);
        /**
         * The MediaType instance for Ledger, 11 x 17 in.
         * <p>
         *  Ledger的MediaType实例,11 x 17 in。
         * 
         */
        public static final MediaType LEDGER = new MediaType(I_LEDGER);
        /**
         * The MediaType instance for North American Letter, 8 1/2 x 11 in.
         * <p>
         *  NorthType Letter的MediaType实例,8 1/2 x 11 in。
         * 
         */
        public static final MediaType NA_LETTER = new MediaType(I_NA_LETTER);
        /**
         * The MediaType instance for North American Legal, 8 1/2 x 14 in.
         * <p>
         *  北美法律的MediaType实例,8 1/2 x 14英寸
         * 
         */
        public static final MediaType NA_LEGAL = new MediaType(I_NA_LEGAL);
        /**
         * The MediaType instance for Quarto, 215 x 275 mm.
         * <p>
         *  Quarto的MediaType实例,215 x 275毫米。
         * 
         */
        public static final MediaType QUARTO = new MediaType(I_QUARTO);
        /**
         * The MediaType instance for Engineering A, 8 1/2 x 11 in.
         * <p>
         * 工程A的MediaType实例,8 1/2 x 11 in。
         * 
         */
        public static final MediaType A = new MediaType(I_A);
        /**
         * The MediaType instance for Engineering B, 11 x 17 in.
         * <p>
         *  工程B的MediaType实例,11 x 17 in。
         * 
         */
        public static final MediaType B = new MediaType(I_B);
        /**
         * The MediaType instance for Engineering C, 17 x 22 in.
         * <p>
         *  工程C的MediaType实例,17 x 22英寸
         * 
         */
        public static final MediaType C = new MediaType(I_C);
        /**
         * The MediaType instance for Engineering D, 22 x 34 in.
         * <p>
         *  工程D的MediaType实例,22 x 34 in。
         * 
         */
        public static final MediaType D = new MediaType(I_D);
        /**
         * The MediaType instance for Engineering E, 34 x 44 in.
         * <p>
         *  工程E的MediaType实例,34 x 44 in。
         * 
         */
        public static final MediaType E = new MediaType(I_E);
        /**
         * The MediaType instance for North American 10 x 15 in.
         * <p>
         *  北美地区的MediaType实例10 x 15英寸。
         * 
         */
        public static final MediaType NA_10X15_ENVELOPE =
            new MediaType(I_NA_10X15_ENVELOPE);
        /**
         * The MediaType instance for North American 10 x 14 in.
         * <p>
         *  北美的MediaType实例10 x 14 in。
         * 
         */
        public static final MediaType NA_10X14_ENVELOPE =
            new MediaType(I_NA_10X14_ENVELOPE);
        /**
         * The MediaType instance for North American 10 x 13 in.
         * <p>
         *  北美的MediaType实例10 x 13 in。
         * 
         */
        public static final MediaType NA_10X13_ENVELOPE =
            new MediaType(I_NA_10X13_ENVELOPE);
        /**
         * The MediaType instance for North American 9 x 12 in.
         * <p>
         *  北美9 * 12英寸的MediaType实例。
         * 
         */
        public static final MediaType NA_9X12_ENVELOPE =
            new MediaType(I_NA_9X12_ENVELOPE);
        /**
         * The MediaType instance for North American 9 x 11 in.
         * <p>
         *  北美9 * 11英寸的MediaType实例。
         * 
         */
        public static final MediaType NA_9X11_ENVELOPE =
            new MediaType(I_NA_9X11_ENVELOPE);
        /**
         * The MediaType instance for North American 7 x 9 in.
         * <p>
         *  北美7 x 9英寸的MediaType实例。
         * 
         */
        public static final MediaType NA_7X9_ENVELOPE =
            new MediaType(I_NA_7X9_ENVELOPE);
        /**
         * The MediaType instance for North American 6 x 9 in.
         * <p>
         *  北美6 x 9英寸的MediaType实例。
         * 
         */
        public static final MediaType NA_6X9_ENVELOPE =
            new MediaType(I_NA_6X9_ENVELOPE);
        /**
         * The MediaType instance for North American #9 Business Envelope,
         * 3 7/8 x 8 7/8 in.
         * <p>
         *  北美#9商业信封的MediaType实例,3 7/8 x 8 7/8 in。
         * 
         */
        public static final MediaType NA_NUMBER_9_ENVELOPE =
            new MediaType(I_NA_NUMBER_9_ENVELOPE);
        /**
         * The MediaType instance for North American #10 Business Envelope,
         * 4 1/8 x 9 1/2 in.
         * <p>
         *  北美#10商业信封的MediaType实例,4 1/8 x 9 1/2 in。
         * 
         */
        public static final MediaType NA_NUMBER_10_ENVELOPE =
            new MediaType(I_NA_NUMBER_10_ENVELOPE);
        /**
         * The MediaType instance for North American #11 Business Envelope,
         * 4 1/2 x 10 3/8 in.
         * <p>
         *  北美#11商业信封的MediaType实例,4 1/2 x 10 3/8英寸
         * 
         */
        public static final MediaType NA_NUMBER_11_ENVELOPE =
            new MediaType(I_NA_NUMBER_11_ENVELOPE);
        /**
         * The MediaType instance for North American #12 Business Envelope,
         * 4 3/4 x 11 in.
         * <p>
         *  北美#12商业信封的MediaType实例,4 3/4 x 11英寸
         * 
         */
        public static final MediaType NA_NUMBER_12_ENVELOPE =
            new MediaType(I_NA_NUMBER_12_ENVELOPE);
        /**
         * The MediaType instance for North American #14 Business Envelope,
         * 5 x 11 1/2 in.
         * <p>
         *  北美#14商业信封的MediaType实例,5 x 11 1/2 in。
         * 
         */
        public static final MediaType NA_NUMBER_14_ENVELOPE =
            new MediaType(I_NA_NUMBER_14_ENVELOPE);
        /**
         * The MediaType instance for Invitation Envelope, 220 x 220 mm.
         * <p>
         *  邀请信封的MediaType实例,220 x 220毫米。
         * 
         */
        public static final MediaType INVITE_ENVELOPE =
            new MediaType(I_INVITE_ENVELOPE);
        /**
         * The MediaType instance for Italy Envelope, 110 x 230 mm.
         * <p>
         *  意大利信封的MediaType实例,110 x 230毫米。
         * 
         */
        public static final MediaType ITALY_ENVELOPE =
            new MediaType(I_ITALY_ENVELOPE);
        /**
         * The MediaType instance for Monarch Envelope, 3 7/8 x 7 1/2 in.
         * <p>
         *  Monarch Envelope的MediaType实例,3 7/8 x 7 1/2 in。
         * 
         */
        public static final MediaType MONARCH_ENVELOPE =
            new MediaType(I_MONARCH_ENVELOPE);
        /**
         * The MediaType instance for 6 3/4 envelope, 3 5/8 x 6 1/2 in.
         * <p>
         *  MediaType实例为6 3/4信封,3 5/8 x 6 1/2英寸。
         * 
         */
        public static final MediaType PERSONAL_ENVELOPE =
            new MediaType(I_PERSONAL_ENVELOPE);
        /**
         * An alias for ISO_A0.
         * <p>
         *  ISO_A0的别名。
         * 
         */
        public static final MediaType A0 = ISO_A0;
        /**
         * An alias for ISO_A1.
         * <p>
         *  ISO_A1的别名。
         * 
         */
        public static final MediaType A1 = ISO_A1;
        /**
         * An alias for ISO_A2.
         * <p>
         *  ISO_A2的别名。
         * 
         */
        public static final MediaType A2 = ISO_A2;
        /**
         * An alias for ISO_A3.
         * <p>
         *  ISO_A3的别名。
         * 
         */
        public static final MediaType A3 = ISO_A3;
        /**
         * An alias for ISO_A4.
         * <p>
         *  ISO_A4的别名。
         * 
         */
        public static final MediaType A4 = ISO_A4;
        /**
         * An alias for ISO_A5.
         * <p>
         *  ISO_A5的别名。
         * 
         */
        public static final MediaType A5 = ISO_A5;
        /**
         * An alias for ISO_A6.
         * <p>
         *  ISO_A6的别名。
         * 
         */
        public static final MediaType A6 = ISO_A6;
        /**
         * An alias for ISO_A7.
         * <p>
         *  ISO_A7的别名。
         * 
         */
        public static final MediaType A7 = ISO_A7;
        /**
         * An alias for ISO_A8.
         * <p>
         * ISO_A8的别名。
         * 
         */
        public static final MediaType A8 = ISO_A8;
        /**
         * An alias for ISO_A9.
         * <p>
         *  ISO_A9的别名。
         * 
         */
        public static final MediaType A9 = ISO_A9;
        /**
         * An alias for ISO_A10.
         * <p>
         *  ISO_A10的别名。
         * 
         */
        public static final MediaType A10 = ISO_A10;
        /**
         * An alias for ISO_B0.
         * <p>
         *  ISO_B0的别名。
         * 
         */
        public static final MediaType B0 = ISO_B0;
        /**
         * An alias for ISO_B1.
         * <p>
         *  ISO_B1的别名。
         * 
         */
        public static final MediaType B1 = ISO_B1;
        /**
         * An alias for ISO_B2.
         * <p>
         *  ISO_B2的别名。
         * 
         */
        public static final MediaType B2 = ISO_B2;
        /**
         * An alias for ISO_B3.
         * <p>
         *  ISO_B3的别名。
         * 
         */
        public static final MediaType B3 = ISO_B3;
        /**
         * An alias for ISO_B4.
         * <p>
         *  ISO_B4的别名。
         * 
         */
        public static final MediaType B4 = ISO_B4;
        /**
         * An alias for ISO_B4.
         * <p>
         *  ISO_B4的别名。
         * 
         */
        public static final MediaType ISO_B4_ENVELOPE = ISO_B4;
        /**
         * An alias for ISO_B5.
         * <p>
         *  ISO_B5的别名。
         * 
         */
        public static final MediaType B5 = ISO_B5;
        /**
         * An alias for ISO_B5.
         * <p>
         *  ISO_B5的别名。
         * 
         */
        public static final MediaType ISO_B5_ENVELOPE = ISO_B5;
        /**
         * An alias for ISO_B6.
         * <p>
         *  ISO_B6的别名。
         * 
         */
        public static final MediaType B6 = ISO_B6;
        /**
         * An alias for ISO_B7.
         * <p>
         *  ISO_B7的别名。
         * 
         */
        public static final MediaType B7 = ISO_B7;
        /**
         * An alias for ISO_B8.
         * <p>
         *  ISO_B8的别名。
         * 
         */
        public static final MediaType B8 = ISO_B8;
        /**
         * An alias for ISO_B9.
         * <p>
         *  ISO_B9的别名。
         * 
         */
        public static final MediaType B9 = ISO_B9;
        /**
         * An alias for ISO_B10.
         * <p>
         *  ISO_B10的别名。
         * 
         */
        public static final MediaType B10 = ISO_B10;
        /**
         * An alias for ISO_C0.
         * <p>
         *  ISO_C0的别名。
         * 
         */
        public static final MediaType C0 = ISO_C0;
        /**
         * An alias for ISO_C0.
         * <p>
         *  ISO_C0的别名。
         * 
         */
        public static final MediaType ISO_C0_ENVELOPE = ISO_C0;
        /**
         * An alias for ISO_C1.
         * <p>
         *  ISO_C1的别名。
         * 
         */
        public static final MediaType C1 = ISO_C1;
        /**
         * An alias for ISO_C1.
         * <p>
         *  ISO_C1的别名。
         * 
         */
        public static final MediaType ISO_C1_ENVELOPE = ISO_C1;
        /**
         * An alias for ISO_C2.
         * <p>
         *  ISO_C2的别名。
         * 
         */
        public static final MediaType C2 = ISO_C2;
        /**
         * An alias for ISO_C2.
         * <p>
         *  ISO_C2的别名。
         * 
         */
        public static final MediaType ISO_C2_ENVELOPE = ISO_C2;
        /**
         * An alias for ISO_C3.
         * <p>
         *  ISO_C3的别名。
         * 
         */
        public static final MediaType C3 = ISO_C3;
        /**
         * An alias for ISO_C3.
         * <p>
         *  ISO_C3的别名。
         * 
         */
        public static final MediaType ISO_C3_ENVELOPE = ISO_C3;
        /**
         * An alias for ISO_C4.
         * <p>
         *  ISO_C4的别名。
         * 
         */
        public static final MediaType C4 = ISO_C4;
        /**
         * An alias for ISO_C4.
         * <p>
         *  ISO_C4的别名。
         * 
         */
        public static final MediaType ISO_C4_ENVELOPE = ISO_C4;
        /**
         * An alias for ISO_C5.
         * <p>
         *  ISO_C5的别名。
         * 
         */
        public static final MediaType C5 = ISO_C5;
        /**
         * An alias for ISO_C5.
         * <p>
         *  ISO_C5的别名。
         * 
         */
        public static final MediaType ISO_C5_ENVELOPE = ISO_C5;
        /**
         * An alias for ISO_C6.
         * <p>
         *  ISO_C6的别名。
         * 
         */
        public static final MediaType C6 = ISO_C6;
        /**
         * An alias for ISO_C6.
         * <p>
         *  ISO_C6的别名。
         * 
         */
        public static final MediaType ISO_C6_ENVELOPE = ISO_C6;
        /**
         * An alias for ISO_C7.
         * <p>
         *  ISO_C7的别名。
         * 
         */
        public static final MediaType C7 = ISO_C7;
        /**
         * An alias for ISO_C7.
         * <p>
         *  ISO_C7的别名。
         * 
         */
        public static final MediaType ISO_C7_ENVELOPE = ISO_C7;
        /**
         * An alias for ISO_C8.
         * <p>
         *  ISO_C8的别名。
         * 
         */
        public static final MediaType C8 = ISO_C8;
        /**
         * An alias for ISO_C8.
         * <p>
         *  ISO_C8的别名。
         * 
         */
        public static final MediaType ISO_C8_ENVELOPE = ISO_C8;
        /**
         * An alias for ISO_C9.
         * <p>
         *  ISO_C9的别名。
         * 
         */
        public static final MediaType C9 = ISO_C9;
        /**
         * An alias for ISO_C9.
         * <p>
         *  ISO_C9的别名。
         * 
         */
        public static final MediaType ISO_C9_ENVELOPE = ISO_C9;
        /**
         * An alias for ISO_C10.
         * <p>
         *  ISO_C10的别名。
         * 
         */
        public static final MediaType C10 = ISO_C10;
        /**
         * An alias for ISO_C10.
         * <p>
         *  ISO_C10的别名。
         * 
         */
        public static final MediaType ISO_C10_ENVELOPE = ISO_C10;
        /**
         * An alias for ISO_DESIGNATED_LONG.
         * <p>
         *  ISO_DESIGNATED_LONG的别名。
         * 
         */
        public static final MediaType ISO_DESIGNATED_LONG_ENVELOPE =
                ISO_DESIGNATED_LONG;
        /**
         * An alias for INVOICE.
         * <p>
         *  INVOICE的别名。
         * 
         */
        public static final MediaType STATEMENT = INVOICE;
        /**
         * An alias for LEDGER.
         * <p>
         *  LEDGER的别名。
         * 
         */
        public static final MediaType TABLOID = LEDGER;
        /**
         * An alias for NA_LETTER.
         * <p>
         *  NA_LETTER的别名。
         * 
         */
        public static final MediaType LETTER = NA_LETTER;
        /**
         * An alias for NA_LETTER.
         * <p>
         *  NA_LETTER的别名。
         * 
         */
        public static final MediaType NOTE = NA_LETTER;
        /**
         * An alias for NA_LEGAL.
         * <p>
         *  NA_LEGAL的别名。
         * 
         */
        public static final MediaType LEGAL = NA_LEGAL;
        /**
         * An alias for NA_10X15_ENVELOPE.
         * <p>
         *  NA_10X15_ENVELOPE的别名。
         * 
         */
        public static final MediaType ENV_10X15 = NA_10X15_ENVELOPE;
        /**
         * An alias for NA_10X14_ENVELOPE.
         * <p>
         *  NA_10X14_ENVELOPE的别名。
         * 
         */
        public static final MediaType ENV_10X14 = NA_10X14_ENVELOPE;
        /**
         * An alias for NA_10X13_ENVELOPE.
         * <p>
         *  NA_10X13_ENVELOPE的别名。
         * 
         */
        public static final MediaType ENV_10X13 = NA_10X13_ENVELOPE;
        /**
         * An alias for NA_9X12_ENVELOPE.
         * <p>
         *  NA_9X12_ENVELOPE的别名。
         * 
         */
        public static final MediaType ENV_9X12 = NA_9X12_ENVELOPE;
        /**
         * An alias for NA_9X11_ENVELOPE.
         * <p>
         *  NA_9X11_ENVELOPE的别名。
         * 
         */
        public static final MediaType ENV_9X11 = NA_9X11_ENVELOPE;
        /**
         * An alias for NA_7X9_ENVELOPE.
         * <p>
         *  NA_7X9_ENVELOPE的别名。
         * 
         */
        public static final MediaType ENV_7X9 = NA_7X9_ENVELOPE;
        /**
         * An alias for NA_6X9_ENVELOPE.
         * <p>
         *  NA_6X9_ENVELOPE的别名。
         * 
         */
        public static final MediaType ENV_6X9 = NA_6X9_ENVELOPE;
        /**
         * An alias for NA_NUMBER_9_ENVELOPE.
         * <p>
         *  NA_NUMBER_9_ENVELOPE的别名。
         * 
         */
        public static final MediaType ENV_9 = NA_NUMBER_9_ENVELOPE;
        /**
         * An alias for NA_NUMBER_10_ENVELOPE.
         * <p>
         *  NA_NUMBER_10_ENVELOPE的别名。
         * 
         */
        public static final MediaType ENV_10 = NA_NUMBER_10_ENVELOPE;
        /**
         * An alias for NA_NUMBER_11_ENVELOPE.
         * <p>
         *  NA_NUMBER_11_ENVELOPE的别名。
         * 
         */
        public static final MediaType ENV_11 = NA_NUMBER_11_ENVELOPE;
        /**
         * An alias for NA_NUMBER_12_ENVELOPE.
         * <p>
         *  NA_NUMBER_12_ENVELOPE的别名。
         * 
         */
        public static final MediaType ENV_12 = NA_NUMBER_12_ENVELOPE;
        /**
         * An alias for NA_NUMBER_14_ENVELOPE.
         * <p>
         *  NA_NUMBER_14_ENVELOPE的别名。
         * 
         */
        public static final MediaType ENV_14 = NA_NUMBER_14_ENVELOPE;
        /**
         * An alias for INVITE_ENVELOPE.
         * <p>
         *  INVITE_ENVELOPE的别名。
         * 
         */
        public static final MediaType ENV_INVITE = INVITE_ENVELOPE;
        /**
         * An alias for ITALY_ENVELOPE.
         * <p>
         *  ITALY_ENVELOPE的别名。
         * 
         */
        public static final MediaType ENV_ITALY = ITALY_ENVELOPE;
        /**
         * An alias for MONARCH_ENVELOPE.
         * <p>
         *  MONARCH_ENVELOPE的别名。
         * 
         */
        public static final MediaType ENV_MONARCH = MONARCH_ENVELOPE;
        /**
         * An alias for PERSONAL_ENVELOPE.
         * <p>
         * PERSONAL_ENVELOPE的别名。
         * 
         */
        public static final MediaType ENV_PERSONAL = PERSONAL_ENVELOPE;
        /**
         * An alias for INVITE_ENVELOPE.
         * <p>
         *  INVITE_ENVELOPE的别名。
         * 
         */
        public static final MediaType INVITE = INVITE_ENVELOPE;
        /**
         * An alias for ITALY_ENVELOPE.
         * <p>
         *  ITALY_ENVELOPE的别名。
         * 
         */
        public static final MediaType ITALY = ITALY_ENVELOPE;
        /**
         * An alias for MONARCH_ENVELOPE.
         * <p>
         *  MONARCH_ENVELOPE的别名。
         * 
         */
        public static final MediaType MONARCH = MONARCH_ENVELOPE;
        /**
         * An alias for PERSONAL_ENVELOPE.
         * <p>
         *  PERSONAL_ENVELOPE的别名。
         * 
         */
        public static final MediaType PERSONAL = PERSONAL_ENVELOPE;

        private MediaType(int type) {
            super(type, NAMES);
        }
    }

    /**
     * A type-safe enumeration of possible orientations. These orientations
     * are in partial compliance with IPP 1.1.
     * <p>
     *  可能取向的类型安全枚举。这些方向部分符合IPP 1.1。
     * 
     * 
     * @since 1.3
     */
    public static final class OrientationRequestedType extends AttributeValue {
        private static final int I_PORTRAIT = 0;
        private static final int I_LANDSCAPE = 1;

        private static final String NAMES[] = {
            "portrait", "landscape"
        };

        /**
         * The OrientationRequestedType instance to use for specifying a
         * portrait orientation.
         * <p>
         *  用于指定纵向方向的OrientationRequestedType实例。
         * 
         */
        public static final OrientationRequestedType PORTRAIT =
            new OrientationRequestedType(I_PORTRAIT);
        /**
         * The OrientationRequestedType instance to use for specifying a
         * landscape orientation.
         * <p>
         *  用于指定横向方向的OrientationRequestedType实例。
         * 
         */
        public static final OrientationRequestedType LANDSCAPE =
            new OrientationRequestedType(I_LANDSCAPE);

        private OrientationRequestedType(int type) {
            super(type, NAMES);
        }
    }

    /**
     * A type-safe enumeration of possible origins.
     * <p>
     *  可能来源的类型安全枚举。
     * 
     * 
     * @since 1.3
     */
    public static final class OriginType extends AttributeValue {
        private static final int I_PHYSICAL = 0;
        private static final int I_PRINTABLE = 1;

        private static final String NAMES[] = {
            "physical", "printable"
        };

        /**
         * The OriginType instance to use for specifying a physical origin.
         * <p>
         *  用于指定物理原点的OriginType实例。
         * 
         */
        public static final OriginType PHYSICAL = new OriginType(I_PHYSICAL);
        /**
         * The OriginType instance to use for specifying a printable origin.
         * <p>
         *  用于指定可打印来源的OriginType实例。
         * 
         */
        public static final OriginType PRINTABLE = new OriginType(I_PRINTABLE);

        private OriginType(int type) {
            super(type, NAMES);
        }
    }

    /**
     * A type-safe enumeration of possible print qualities. These print
     * qualities are in compliance with IPP 1.1.
     * <p>
     *  可能的打印质量的类型安全枚举。这些打印质量符合IPP 1.1。
     * 
     * 
     * @since 1.3
     */
    public static final class PrintQualityType extends AttributeValue {
        private static final int I_HIGH = 0;
        private static final int I_NORMAL = 1;
        private static final int I_DRAFT = 2;

        private static final String NAMES[] = {
            "high", "normal", "draft"
        };

        /**
         * The PrintQualityType instance to use for specifying a high print
         * quality.
         * <p>
         *  用于指定高打印质量的PrintQualityType实例。
         * 
         */
        public static final PrintQualityType HIGH =
            new PrintQualityType(I_HIGH);
        /**
         * The PrintQualityType instance to use for specifying a normal print
         * quality.
         * <p>
         *  用于指定正常打印质量的PrintQualityType实例。
         * 
         */
        public static final PrintQualityType NORMAL =
            new PrintQualityType(I_NORMAL);
        /**
         * The PrintQualityType instance to use for specifying a draft print
         * quality.
         * <p>
         *  用于指定草稿打印质量的PrintQualityType实例。
         * 
         */
        public static final PrintQualityType DRAFT =
            new PrintQualityType(I_DRAFT);

        private PrintQualityType(int type) {
            super(type, NAMES);
        }
    }

    private ColorType color;
    private MediaType media;
    private OrientationRequestedType orientationRequested;
    private OriginType origin;
    private PrintQualityType printQuality;
    private int[] printerResolution;

    /**
     * Constructs a PageAttributes instance with default values for every
     * attribute.
     * <p>
     *  使用每个属性的默认值构造一个PageAttributes实例。
     * 
     */
    public PageAttributes() {
        setColor(ColorType.MONOCHROME);
        setMediaToDefault();
        setOrientationRequestedToDefault();
        setOrigin(OriginType.PHYSICAL);
        setPrintQualityToDefault();
        setPrinterResolutionToDefault();
    }

    /**
     * Constructs a PageAttributes instance which is a copy of the supplied
     * PageAttributes.
     *
     * <p>
     *  构造一个PageAttributes实例,它是提供的PageAttributes的副本。
     * 
     * 
     * @param   obj the PageAttributes to copy.
     */
    public PageAttributes(PageAttributes obj) {
        set(obj);
    }

    /**
     * Constructs a PageAttributes instance with the specified values for
     * every attribute.
     *
     * <p>
     *  构造具有每个属性的指定值的PageAttributes实例。
     * 
     * 
     * @param   color ColorType.COLOR or ColorType.MONOCHROME.
     * @param   media one of the constant fields of the MediaType class.
     * @param   orientationRequested OrientationRequestedType.PORTRAIT or
     *          OrientationRequestedType.LANDSCAPE.
     * @param   origin OriginType.PHYSICAL or OriginType.PRINTABLE
     * @param   printQuality PrintQualityType.DRAFT, PrintQualityType.NORMAL,
     *          or PrintQualityType.HIGH
     * @param   printerResolution an integer array of 3 elements. The first
     *          element must be greater than 0. The second element must be
     *          must be greater than 0. The third element must be either
     *          <code>3</code> or <code>4</code>.
     * @throws  IllegalArgumentException if one or more of the above
     *          conditions is violated.
     */
    public PageAttributes(ColorType color, MediaType media,
                          OrientationRequestedType orientationRequested,
                          OriginType origin, PrintQualityType printQuality,
                          int[] printerResolution) {
        setColor(color);
        setMedia(media);
        setOrientationRequested(orientationRequested);
        setOrigin(origin);
        setPrintQuality(printQuality);
        setPrinterResolution(printerResolution);
    }

    /**
     * Creates and returns a copy of this PageAttributes.
     *
     * <p>
     *  创建并返回此PageAttributes的副本。
     * 
     * 
     * @return  the newly created copy. It is safe to cast this Object into
     *          a PageAttributes.
     */
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            // Since we implement Cloneable, this should never happen
            throw new InternalError(e);
        }
    }

    /**
     * Sets all of the attributes of this PageAttributes to the same values as
     * the attributes of obj.
     *
     * <p>
     *  将此PageAttributes的所有属性设置为与obj的属性相同的值。
     * 
     * 
     * @param   obj the PageAttributes to copy.
     */
    public void set(PageAttributes obj) {
        color = obj.color;
        media = obj.media;
        orientationRequested = obj.orientationRequested;
        origin = obj.origin;
        printQuality = obj.printQuality;
        // okay because we never modify the contents of printerResolution
        printerResolution = obj.printerResolution;
    }

    /**
     * Returns whether pages using these attributes will be rendered in
     * color or monochrome. This attribute is updated to the value chosen
     * by the user.
     *
     * <p>
     *  返回使用这些属性的页面是以彩色还是单色呈现。此属性更新为用户选择的值。
     * 
     * 
     * @return  ColorType.COLOR or ColorType.MONOCHROME.
     */
    public ColorType getColor() {
        return color;
    }

    /**
     * Specifies whether pages using these attributes will be rendered in
     * color or monochrome. Not specifying this attribute is equivalent to
     * specifying ColorType.MONOCHROME.
     *
     * <p>
     * 指定使用这些属性的页面是以彩色还是单色呈现。不指定此属性等同于指定ColorType.MONOCHROME。
     * 
     * 
     * @param   color ColorType.COLOR or ColorType.MONOCHROME.
     * @throws  IllegalArgumentException if color is null.
     */
    public void setColor(ColorType color) {
        if (color == null) {
            throw new IllegalArgumentException("Invalid value for attribute "+
                                               "color");
        }
        this.color = color;
    }

    /**
     * Returns the paper size for pages using these attributes. This
     * attribute is updated to the value chosen by the user.
     *
     * <p>
     *  返回使用这些属性的页面的纸张大小。此属性更新为用户选择的值。
     * 
     * 
     * @return  one of the constant fields of the MediaType class.
     */
    public MediaType getMedia() {
        return media;
    }

    /**
     * Specifies the desired paper size for pages using these attributes. The
     * actual paper size will be determined by the limitations of the target
     * printer. If an exact match cannot be found, an implementation will
     * choose the closest possible match. Not specifying this attribute is
     * equivalent to specifying the default size for the default locale. The
     * default size for locales in the United States and Canada is
     * MediaType.NA_LETTER. The default size for all other locales is
     * MediaType.ISO_A4.
     *
     * <p>
     *  指定使用这些属性的页面的所需纸张尺寸。实际纸张尺寸将由目标打印机的限制确定。如果找不到完全匹配,则实施将选择最接近的可能匹配。不指定此属性等效于指定默认语言环境的默认大小。
     * 美国和加拿大的区域设置的默认大小为MediaType.NA_LETTER。所有其他语言环境的默认大小为MediaType.ISO_A4。
     * 
     * 
     * @param   media one of the constant fields of the MediaType class.
     * @throws  IllegalArgumentException if media is null.
     */
    public void setMedia(MediaType media) {
        if (media == null) {
            throw new IllegalArgumentException("Invalid value for attribute "+
                                               "media");
        }
        this.media = media;
    }

    /**
     * Sets the paper size for pages using these attributes to the default
     * size for the default locale. The default size for locales in the
     * United States and Canada is MediaType.NA_LETTER. The default size for
     * all other locales is MediaType.ISO_A4.
     * <p>
     *  将使用这些属性的页面的纸张大小设置为默认语言环境的默认大小。美国和加拿大的区域设置的默认大小为MediaType.NA_LETTER。所有其他语言环境的默认大小为MediaType.ISO_A4。
     * 
     */
    public void setMediaToDefault(){
        String defaultCountry = Locale.getDefault().getCountry();
        if (defaultCountry != null &&
            (defaultCountry.equals(Locale.US.getCountry()) ||
             defaultCountry.equals(Locale.CANADA.getCountry()))) {
            setMedia(MediaType.NA_LETTER);
        } else {
            setMedia(MediaType.ISO_A4);
        }
    }

    /**
     * Returns the print orientation for pages using these attributes. This
     * attribute is updated to the value chosen by the user.
     *
     * <p>
     *  返回使用这些属性的页面的打印方向。此属性更新为用户选择的值。
     * 
     * 
     * @return  OrientationRequestedType.PORTRAIT or
     *          OrientationRequestedType.LANDSCAPE.
     */
    public OrientationRequestedType getOrientationRequested() {
        return orientationRequested;
    }

    /**
     * Specifies the print orientation for pages using these attributes. Not
     * specifying the property is equivalent to specifying
     * OrientationRequestedType.PORTRAIT.
     *
     * <p>
     *  指定使用这些属性的页面的打印方向。不指定属性等同于指定OrientationRequestedType.PORTRAIT。
     * 
     * 
     * @param   orientationRequested OrientationRequestedType.PORTRAIT or
     *          OrientationRequestedType.LANDSCAPE.
     * @throws  IllegalArgumentException if orientationRequested is null.
     */
    public void setOrientationRequested(OrientationRequestedType
                                        orientationRequested) {
        if (orientationRequested == null) {
            throw new IllegalArgumentException("Invalid value for attribute "+
                                               "orientationRequested");
        }
        this.orientationRequested = orientationRequested;
    }

    /**
     * Specifies the print orientation for pages using these attributes.
     * Specifying <code>3</code> denotes portrait. Specifying <code>4</code>
     * denotes landscape. Specifying any other value will generate an
     * IllegalArgumentException. Not specifying the property is equivalent
     * to calling setOrientationRequested(OrientationRequestedType.PORTRAIT).
     *
     * <p>
     * 指定使用这些属性的页面的打印方向。指定<code> 3 </code>表示纵向。指定<code> 4 </code>表示横向。指定任何其他值将生成IllegalArgumentException。
     * 不指定属性等效于调用setOrientationRequested(OrientationRequestedType.PORTRAIT)。
     * 
     * 
     * @param   orientationRequested <code>3</code> or <code>4</code>
     * @throws  IllegalArgumentException if orientationRequested is not
     *          <code>3</code> or <code>4</code>
     */
    public void setOrientationRequested(int orientationRequested) {
        switch (orientationRequested) {
          case 3:
            setOrientationRequested(OrientationRequestedType.PORTRAIT);
            break;
          case 4:
            setOrientationRequested(OrientationRequestedType.LANDSCAPE);
            break;
          default:
            // This will throw an IllegalArgumentException
            setOrientationRequested(null);
            break;
        }
    }

    /**
     * Sets the print orientation for pages using these attributes to the
     * default. The default orientation is portrait.
     * <p>
     *  将使用这些属性的页面的打印方向设置为默认值。默认方向为纵向。
     * 
     */
    public void setOrientationRequestedToDefault() {
        setOrientationRequested(OrientationRequestedType.PORTRAIT);
    }

    /**
     * Returns whether drawing at (0, 0) to pages using these attributes
     * draws at the upper-left corner of the physical page, or at the
     * upper-left corner of the printable area. (Note that these locations
     * could be equivalent.) This attribute cannot be modified by,
     * and is not subject to any limitations of, the implementation or the
     * target printer.
     *
     * <p>
     *  返回使用这些属性在(0,0)绘制到页面是在物理页面的左上角绘制还是在可打印区域的左上角绘制。 (请注意,这些位置可以是等效的。)此属性不能由实施或目标打印机修改,并且不受实施或目标打印机的任何限制。
     * 
     * 
     * @return  OriginType.PHYSICAL or OriginType.PRINTABLE
     */
    public OriginType getOrigin() {
        return origin;
    }

    /**
     * Specifies whether drawing at (0, 0) to pages using these attributes
     * draws at the upper-left corner of the physical page, or at the
     * upper-left corner of the printable area. (Note that these locations
     * could be equivalent.) Not specifying the property is equivalent to
     * specifying OriginType.PHYSICAL.
     *
     * <p>
     *  指定是否使用这些属性在(0,0)绘制到页面,绘制在物理页面的左上角或可打印区域的左上角。 (注意,这些位置可以是等价的。)不指定属性等同于指定OriginType.PHYSICAL。
     * 
     * 
     * @param   origin OriginType.PHYSICAL or OriginType.PRINTABLE
     * @throws  IllegalArgumentException if origin is null.
     */
    public void setOrigin(OriginType origin) {
        if (origin == null) {
            throw new IllegalArgumentException("Invalid value for attribute "+
                                               "origin");
        }
        this.origin = origin;
    }

    /**
     * Returns the print quality for pages using these attributes. This
     * attribute is updated to the value chosen by the user.
     *
     * <p>
     *  返回使用这些属性的页面的打印质量。此属性更新为用户选择的值。
     * 
     * 
     * @return  PrintQualityType.DRAFT, PrintQualityType.NORMAL, or
     *          PrintQualityType.HIGH
     */
    public PrintQualityType getPrintQuality() {
        return printQuality;
    }

    /**
     * Specifies the print quality for pages using these attributes. Not
     * specifying the property is equivalent to specifying
     * PrintQualityType.NORMAL.
     *
     * <p>
     *  指定使用这些属性的页面的打印质量。不指定属性等效于指定PrintQualityType.NORMAL。
     * 
     * 
     * @param   printQuality PrintQualityType.DRAFT, PrintQualityType.NORMAL,
     *          or PrintQualityType.HIGH
     * @throws  IllegalArgumentException if printQuality is null.
     */
    public void setPrintQuality(PrintQualityType printQuality) {
        if (printQuality == null) {
            throw new IllegalArgumentException("Invalid value for attribute "+
                                               "printQuality");
        }
        this.printQuality = printQuality;
    }

    /**
     * Specifies the print quality for pages using these attributes.
     * Specifying <code>3</code> denotes draft. Specifying <code>4</code>
     * denotes normal. Specifying <code>5</code> denotes high. Specifying
     * any other value will generate an IllegalArgumentException. Not
     * specifying the property is equivalent to calling
     * setPrintQuality(PrintQualityType.NORMAL).
     *
     * <p>
     * 指定使用这些属性的页面的打印质量。指定<code> 3 </code>表示草稿。指定<code> 4 </code>表示正常。指定<code> 5 </code>表示高。
     * 指定任何其他值将生成IllegalArgumentException。不指定属性等效于调用setPrintQuality(PrintQualityType.NORMAL)。
     * 
     * 
     * @param   printQuality <code>3</code>, <code>4</code>, or <code>5</code>
     * @throws  IllegalArgumentException if printQuality is not <code>3
     *          </code>, <code>4</code>, or <code>5</code>
     */
    public void setPrintQuality(int printQuality) {
        switch (printQuality) {
          case 3:
            setPrintQuality(PrintQualityType.DRAFT);
            break;
          case 4:
            setPrintQuality(PrintQualityType.NORMAL);
            break;
          case 5:
            setPrintQuality(PrintQualityType.HIGH);
            break;
          default:
            // This will throw an IllegalArgumentException
            setPrintQuality(null);
            break;
        }
    }

    /**
     * Sets the print quality for pages using these attributes to the default.
     * The default print quality is normal.
     * <p>
     *  将使用这些属性的页面的打印质量设置为默认值。默认打印质量正常。
     * 
     */
    public void setPrintQualityToDefault() {
        setPrintQuality(PrintQualityType.NORMAL);
    }

    /**
     * Returns the print resolution for pages using these attributes.
     * Index 0 of the array specifies the cross feed direction resolution
     * (typically the horizontal resolution). Index 1 of the array specifies
     * the feed direction resolution (typically the vertical resolution).
     * Index 2 of the array specifies whether the resolutions are in dots per
     * inch or dots per centimeter. <code>3</code> denotes dots per inch.
     * <code>4</code> denotes dots per centimeter.
     *
     * <p>
     *  返回使用这些属性的页面的打印分辨率。数组的索引0指定交叉进给方向分辨率(通常为水平分辨率)。数组的索引1指定进给方向分辨率(通常为垂直分辨率)。数组的索引2指定分辨率是每英寸的点数还是每厘米的点数。
     *  <code> 3 </code>表示每英寸的点数。 <code> 4 </code>表示每厘米的点数。
     * 
     * 
     * @return  an integer array of 3 elements. The first
     *          element must be greater than 0. The second element must be
     *          must be greater than 0. The third element must be either
     *          <code>3</code> or <code>4</code>.
     */
    public int[] getPrinterResolution() {
        // Return a copy because otherwise client code could circumvent the
        // the checks made in setPrinterResolution by modifying the
        // returned array.
        int[] copy = new int[3];
        copy[0] = printerResolution[0];
        copy[1] = printerResolution[1];
        copy[2] = printerResolution[2];
        return copy;
    }

    /**
     * Specifies the desired print resolution for pages using these attributes.
     * The actual resolution will be determined by the limitations of the
     * implementation and the target printer. Index 0 of the array specifies
     * the cross feed direction resolution (typically the horizontal
     * resolution). Index 1 of the array specifies the feed direction
     * resolution (typically the vertical resolution). Index 2 of the array
     * specifies whether the resolutions are in dots per inch or dots per
     * centimeter. <code>3</code> denotes dots per inch. <code>4</code>
     * denotes dots per centimeter. Note that the 1.1 printing implementation
     * (Toolkit.getPrintJob) requires that the feed and cross feed resolutions
     * be the same. Not specifying the property is equivalent to calling
     * setPrinterResolution(72).
     *
     * <p>
     * 指定使用这些属性的页面的所需打印分辨率。实际分辨率将由实现和目标打印机的限制确定。数组的索引0指定交叉进给方向分辨率(通常为水平分辨率)。数组的索引1指定进给方向分辨率(通常为垂直分辨率)。
     * 数组的索引2指定分辨率是每英寸的点数还是每厘米的点数。 <code> 3 </code>表示每英寸的点数。 <code> 4 </code>表示每厘米的点数。
     * 请注意,1.1打印实现(Toolkit.getPrintJob)要求feed和cross feed分辨率相同。不指定该属性等效于调用setPrinterResolution(72)。
     * 
     * 
     * @param   printerResolution an integer array of 3 elements. The first
     *          element must be greater than 0. The second element must be
     *          must be greater than 0. The third element must be either
     *          <code>3</code> or <code>4</code>.
     * @throws  IllegalArgumentException if one or more of the above
     *          conditions is violated.
     */
    public void setPrinterResolution(int[] printerResolution) {
        if (printerResolution == null ||
            printerResolution.length != 3 ||
            printerResolution[0] <= 0 ||
            printerResolution[1] <= 0 ||
            (printerResolution[2] != 3 && printerResolution[2] != 4)) {
            throw new IllegalArgumentException("Invalid value for attribute "+
                                               "printerResolution");
        }
        // Store a copy because otherwise client code could circumvent the
        // the checks made above by holding a reference to the array and
        // modifying it after calling setPrinterResolution.
        int[] copy = new int[3];
        copy[0] = printerResolution[0];
        copy[1] = printerResolution[1];
        copy[2] = printerResolution[2];
        this.printerResolution = copy;
    }

    /**
     * Specifies the desired cross feed and feed print resolutions in dots per
     * inch for pages using these attributes. The same value is used for both
     * resolutions. The actual resolutions will be determined by the
     * limitations of the implementation and the target printer. Not
     * specifying the property is equivalent to specifying <code>72</code>.
     *
     * <p>
     *  以使用这些属性的页面指定所需的交叉进纸和进纸打印分辨率(每英寸点数)。相同的值用于两个分辨率。实际分辨率将由实现和目标打印机的限制确定。不指定属性等效于指定<code> 72 </code>。
     * 
     * 
     * @param   printerResolution an integer greater than 0.
     * @throws  IllegalArgumentException if printerResolution is less than or
     *          equal to 0.
     */
    public void setPrinterResolution(int printerResolution) {
        setPrinterResolution(new int[] { printerResolution, printerResolution,
                                         3 } );
    }

    /**
     * Sets the printer resolution for pages using these attributes to the
     * default. The default is 72 dpi for both the feed and cross feed
     * resolutions.
     * <p>
     *  将使用这些属性的页面的打印机分辨率设置为默认值。默认值为进纸和交叉进纸分辨率的72 dpi。
     * 
     */
    public void setPrinterResolutionToDefault() {
        setPrinterResolution(72);
    }

    /**
     * Determines whether two PageAttributes are equal to each other.
     * <p>
     * Two PageAttributes are equal if and only if each of their attributes are
     * equal. Attributes of enumeration type are equal if and only if the
     * fields refer to the same unique enumeration object. This means that
     * an aliased media is equal to its underlying unique media. Printer
     * resolutions are equal if and only if the feed resolution, cross feed
     * resolution, and units are equal.
     *
     * <p>
     *  确定两个PageAttributes是否彼此相等。
     * <p>
     * 当且仅当它们的每个属性相等时,两个PageAttributes是相等的。当且仅当字段引用同一唯一枚举对象时,枚举类型的属性相等。这意味着别名媒体等于其底层唯一媒体。
     * 当且仅当进给分辨率,交叉进给分辨率和单位相等时,打印机分辨率相等。
     * 
     * 
     * @param   obj the object whose equality will be checked.
     * @return  whether obj is equal to this PageAttribute according to the
     *          above criteria.
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof PageAttributes)) {
            return false;
        }

        PageAttributes rhs = (PageAttributes)obj;

        return (color == rhs.color &&
                media == rhs.media &&
                orientationRequested == rhs.orientationRequested &&
                origin == rhs.origin &&
                printQuality == rhs.printQuality &&
                printerResolution[0] == rhs.printerResolution[0] &&
                printerResolution[1] == rhs.printerResolution[1] &&
                printerResolution[2] == rhs.printerResolution[2]);
    }

    /**
     * Returns a hash code value for this PageAttributes.
     *
     * <p>
     *  返回此PageAttributes的哈希码值。
     * 
     * 
     * @return  the hash code.
     */
    public int hashCode() {
        return (color.hashCode() << 31 ^
                media.hashCode() << 24 ^
                orientationRequested.hashCode() << 23 ^
                origin.hashCode() << 22 ^
                printQuality.hashCode() << 20 ^
                printerResolution[2] >> 2 << 19 ^
                printerResolution[1] << 10 ^
                printerResolution[0]);
    }

    /**
     * Returns a string representation of this PageAttributes.
     *
     * <p>
     *  返回此PageAttributes的字符串表示形式。
     * 
     * @return  the string representation.
     */
    public String toString() {
        // int[] printerResolution = getPrinterResolution();
        return "color=" + getColor() + ",media=" + getMedia() +
            ",orientation-requested=" + getOrientationRequested() +
            ",origin=" + getOrigin() + ",print-quality=" + getPrintQuality() +
            ",printer-resolution=[" + printerResolution[0] + "," +
            printerResolution[1] + "," + printerResolution[2] + "]";
    }
}
