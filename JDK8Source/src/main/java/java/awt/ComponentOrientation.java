/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2006, Oracle and/or its affiliates. All rights reserved.
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

/*
 * (C) Copyright IBM Corp. 1998 - All Rights Reserved
 *
 * The original version of this source code and documentation is copyrighted
 * and owned by IBM, Inc. These materials are provided under terms of a
 * License Agreement between IBM and Sun. This technology is protected by
 * multiple US and International patents. This notice and attribution to IBM
 * may not be removed.
 *
 * <p>
 *  (C)版权所有IBM Corp. 1998  - 保留所有权利
 * 
 *  此源代码和文档的原始版本受版权和归IBM,Inc.所有。这些材料是根据IBM和Sun之间的许可协议的条款提供的。该技术受多项美国和国际专利保护。本通知和归属于IBM的内容不得删除。
 * 
 */

package java.awt;

import java.util.Locale;
import java.util.ResourceBundle;

/**
  * The ComponentOrientation class encapsulates the language-sensitive
  * orientation that is to be used to order the elements of a component
  * or of text. It is used to reflect the differences in this ordering
  * between Western alphabets, Middle Eastern (such as Hebrew), and Far
  * Eastern (such as Japanese).
  * <p>
  * Fundamentally, this governs items (such as characters) which are laid out
  * in lines, with the lines then laid out in a block. This also applies
  * to items in a widget: for example, in a check box where the box is
  * positioned relative to the text.
  * <p>
  * There are four different orientations used in modern languages
  * as in the following table.<br>
  * <pre>
  * LT          RT          TL          TR
  * A B C       C B A       A D G       G D A
  * D E F       F E D       B E H       H E B
  * G H I       I H G       C F I       I F C
  * </pre><br>
  * (In the header, the two-letter abbreviation represents the item direction
  * in the first letter, and the line direction in the second. For example,
  * LT means "items left-to-right, lines top-to-bottom",
  * TL means "items top-to-bottom, lines left-to-right", and so on.)
  * <p>
  * The orientations are:
  * <ul>
  * <li>LT - Western Europe (optional for Japanese, Chinese, Korean)
  * <li>RT - Middle East (Arabic, Hebrew)
  * <li>TR - Japanese, Chinese, Korean
  * <li>TL - Mongolian
  * </ul>
  * Components whose view and controller code depends on orientation
  * should use the <code>isLeftToRight()</code> and
  * <code>isHorizontal()</code> methods to
  * determine their behavior. They should not include switch-like
  * code that keys off of the constants, such as:
  * <pre>
  * if (orientation == LEFT_TO_RIGHT) {
  *   ...
  * } else if (orientation == RIGHT_TO_LEFT) {
  *   ...
  * } else {
  *   // Oops
  * }
  * </pre>
  * This is unsafe, since more constants may be added in the future and
  * since it is not guaranteed that orientation objects will be unique.
  * <p>
  *  ComponentOrientation类封装了用于对组件或文本的元素排序的对语言敏感的方向。它用于反映西方字母,中东(如希伯来语)和远东(如日本)之间这种顺序的差异。
  * <p>
  *  从根本上来说,它管理以行排列的项目(例如字符),然后将这些行布置在块中。这也适用于窗口小部件中的项目：例如,在框相对于文本定位的复选框中。
  * <p>
  *  在现代语言中有四种不同的方向,如下表所示。<br>
  * <pre>
  * LT RT TL TR ABCCBAADGGDADEFFEDBEH HEBGHIIHGCFIIFC </pre> <br>(在标题中,两个字母的缩写代表第一个字母中的项目方向,而第二个字母代表行方向。
  * 例如,LT表示" - 右,行从上到下",TL表示"项从上到下,行从左到右",等等)。
  * <p>
  *  方向是：
  * <ul>
  *  <li> LT  - 西欧(日语,中文,韩文可选)<li> RT  - 中东(阿拉伯语,希伯来语)<li> TR  - 日语,中文,韩语<li> TL  - 蒙古语
  * </ul>
  *  其视图和控制器代码取决于方向的组件应使用<code> isLeftToRight()</code>和<code> isHorizo​​ntal()</code>方法来确定它们的行为。
  * 它们不应该包括切换常量的类似开关的代码,例如：。
  * <pre>
  *  if(orientation == LEFT_TO_RIGHT){...} else if(orientation == RIGHT_TO_LEFT){...} else {// Oops}
  * </pre>
  *  这是不安全的,因为将来可能会添加更多的常量,因为不能保证定向对象是唯一的。
  * 
  */
public final class ComponentOrientation implements java.io.Serializable
{
    /*
     * serialVersionUID
     * <p>
     *  serialVersionUID
     * 
     */
    private static final long serialVersionUID = -4113291392143563828L;

    // Internal constants used in the implementation
    private static final int UNK_BIT      = 1;
    private static final int HORIZ_BIT    = 2;
    private static final int LTR_BIT      = 4;

    /**
     * Items run left to right and lines flow top to bottom
     * Examples: English, French.
     * <p>
     *  项目从左到右运行,线流从上到下示例：英语,法语。
     * 
     */
    public static final ComponentOrientation LEFT_TO_RIGHT =
                    new ComponentOrientation(HORIZ_BIT|LTR_BIT);

    /**
     * Items run right to left and lines flow top to bottom
     * Examples: Arabic, Hebrew.
     * <p>
     *  项目从右到左运行,线流从上到下示例：阿拉伯语,希伯来语。
     * 
     */
    public static final ComponentOrientation RIGHT_TO_LEFT =
                    new ComponentOrientation(HORIZ_BIT);

    /**
     * Indicates that a component's orientation has not been set.
     * To preserve the behavior of existing applications,
     * isLeftToRight will return true for this value.
     * <p>
     *  表示组件的方向未设置。要保留现有应用程序的行为,isLeftToRight将为此值返回true。
     * 
     */
    public static final ComponentOrientation UNKNOWN =
                    new ComponentOrientation(HORIZ_BIT|LTR_BIT|UNK_BIT);

    /**
     * Are lines horizontal?
     * This will return true for horizontal, left-to-right writing
     * systems such as Roman.
     * <p>
     * 线是否水平?这将为水平,从左到右的书写系统(如罗马)返回true。
     * 
     */
    public boolean isHorizontal() {
        return (orientation & HORIZ_BIT) != 0;
    }

    /**
     * HorizontalLines: Do items run left-to-right?<br>
     * Vertical Lines:  Do lines run left-to-right?<br>
     * This will return true for horizontal, left-to-right writing
     * systems such as Roman.
     * <p>
     *  Horizo​​ntalLines：项目是从左到右运行吗?<br>垂直线：行从左到右?<br>对于水平,从左到右的书写系统,如Roman,它将返回true。
     * 
     */
    public boolean isLeftToRight() {
        return (orientation & LTR_BIT) != 0;
    }

    /**
     * Returns the orientation that is appropriate for the given locale.
     * <p>
     *  返回适合于给定语言环境的方向。
     * 
     * 
     * @param locale the specified locale
     */
    public static ComponentOrientation getOrientation(Locale locale) {
        // A more flexible implementation would consult a ResourceBundle
        // to find the appropriate orientation.  Until pluggable locales
        // are introduced however, the flexiblity isn't really needed.
        // So we choose efficiency instead.
        String lang = locale.getLanguage();
        if( "iw".equals(lang) || "ar".equals(lang)
            || "fa".equals(lang) || "ur".equals(lang) )
        {
            return RIGHT_TO_LEFT;
        } else {
            return LEFT_TO_RIGHT;
        }
    }

    /**
     * Returns the orientation appropriate for the given ResourceBundle's
     * localization.  Three approaches are tried, in the following order:
     * <ol>
     * <li>Retrieve a ComponentOrientation object from the ResourceBundle
     *      using the string "Orientation" as the key.
     * <li>Use the ResourceBundle.getLocale to determine the bundle's
     *      locale, then return the orientation for that locale.
     * <li>Return the default locale's orientation.
     * </ol>
     *
     * <p>
     *  返回适合于给定ResourceBundle本地化的方向。尝试三种方法,按以下顺序：
     * <ol>
     *  <li>使用字符串"Orientation"作为键,从ResourceBundle中检索ComponentOrientation对象。
     * 
     * @deprecated As of J2SE 1.4, use {@link #getOrientation(java.util.Locale)}.
     */
    @Deprecated
    public static ComponentOrientation getOrientation(ResourceBundle bdl)
    {
        ComponentOrientation result = null;

        try {
            result = (ComponentOrientation)bdl.getObject("Orientation");
        }
        catch (Exception e) {
        }

        if (result == null) {
            result = getOrientation(bdl.getLocale());
        }
        if (result == null) {
            result = getOrientation(Locale.getDefault());
        }
        return result;
    }

    private int orientation;

    private ComponentOrientation(int value)
    {
        orientation = value;
    }
 }
