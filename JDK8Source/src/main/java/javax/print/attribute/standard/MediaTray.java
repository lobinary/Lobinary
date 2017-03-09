/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.print.attribute.standard;

import java.util.Locale;

import javax.print.attribute.Attribute;
import javax.print.attribute.EnumSyntax;


/**
 * Class MediaTray is a subclass of Media.
 * Class MediaTray is a printing attribute class, an enumeration, that
 * specifies the media tray or bin for the job.
 * This attribute can be used instead of specifying MediaSize or MediaName.
 * <p>
 * Class MediaTray declares keywords for standard media kind values.
 * Implementation- or site-defined names for a media kind attribute may also
 * be created by defining a subclass of class MediaTray.
 * <P>
 * <B>IPP Compatibility:</B> MediaTray is a representation class for
 * values of the IPP "media" attribute which name paper trays.
 * <P>
 *
 * <p>
 *  MediaTray类是Media的子类。类MediaTray是打印属性类,枚举,用于指定作业的介质托盘或容器。可以使用此属性,而不是指定MediaSize或MediaName。
 * <p>
 *  MediaTray类声明标准媒体类型值的关键字。媒体类型属性的实现或站点定义的名称也可以通过定义类MediaTray的子​​类来创建。
 * <P>
 *  <B> IPP兼容性：</B> MediaTray是名为纸盘的IPP"media"属性的值的表示类。
 * <P>
 * 
 */
public class MediaTray extends Media implements Attribute {

    private static final long serialVersionUID = -982503611095214703L;

    /**
     * The top input tray in the printer.
     * <p>
     *  打印机中顶部的进纸盒。
     * 
     */
    public static final MediaTray TOP = new MediaTray(0);

    /**
     * The middle input tray in the printer.
     * <p>
     *  打印机中的中间进纸盒。
     * 
     */
    public static final MediaTray MIDDLE = new MediaTray(1);

    /**
     * The bottom input tray in the printer.
     * <p>
     *  打印机中的底部进纸盒。
     * 
     */
    public static final MediaTray BOTTOM = new MediaTray(2);

    /**
     * The envelope input tray in the printer.
     * <p>
     *  打印机中的信封输入托盘。
     * 
     */
    public static final MediaTray ENVELOPE = new MediaTray(3);

    /**
     * The manual feed input tray in the printer.
     * <p>
     *  打印机中的手动进纸输入盘。
     * 
     */
    public static final MediaTray MANUAL = new MediaTray(4);

    /**
     * The large capacity input tray in the printer.
     * <p>
     *  打印机中的大容量进纸盒。
     * 
     */
    public static final MediaTray LARGE_CAPACITY = new MediaTray(5);

    /**
     * The main input tray in the printer.
     * <p>
     *  打印机中的主进纸盒。
     * 
     */
    public static final MediaTray MAIN = new MediaTray(6);

    /**
     * The side input tray.
     * <p>
     *  侧面进纸盒。
     * 
     */
    public static final MediaTray SIDE = new MediaTray(7);

    /**
     * Construct a new media tray enumeration value with the given integer
     * value.
     *
     * <p>
     *  使用给定的整数值构造新的介质托盘枚举值。
     * 
     * 
     * @param  value  Integer value.
     */
    protected MediaTray(int value) {
        super (value);
    }

    private static final String[] myStringTable ={
        "top",
        "middle",
        "bottom",
        "envelope",
        "manual",
        "large-capacity",
        "main",
        "side"
    };

    private static final MediaTray[] myEnumValueTable = {
        TOP,
        MIDDLE,
        BOTTOM,
        ENVELOPE,
        MANUAL,
        LARGE_CAPACITY,
        MAIN,
        SIDE
    };

    /**
     * Returns the string table for class MediaTray.
     * <p>
     *  返回类MediaTray的字符串表。
     * 
     */
    protected String[] getStringTable()
    {
        return (String[])myStringTable.clone();
    }

    /**
     * Returns the enumeration value table for class MediaTray.
     * <p>
     *  返回类MediaTray的枚举值表。
     */
    protected EnumSyntax[] getEnumValueTable() {
        return (EnumSyntax[])myEnumValueTable.clone();
    }


}
