/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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
 * Class MediaName is a subclass of Media, a printing attribute class (an
 * enumeration) that specifies the media for a print job as a name.
 * <P>
 * This attribute can be used instead of specifying MediaSize or MediaTray.
 * <p>
 * Class MediaName currently declares a few standard media names.
 * Implementation- or site-defined names for a media name attribute may also
 * be created by defining a subclass of class MediaName.
 * <P>
 * <B>IPP Compatibility:</B> MediaName is a representation class for
 * values of the IPP "media" attribute which names media.
 * <P>
 *
 * <p>
 *  MediaName类是Media的子类,Media是一个打印属性类(枚举),用于指定打印作业的介质作为名称。
 * <P>
 *  可以使用此属性,而不是指定MediaSize或MediaTray。
 * <p>
 *  MediaName类目前声明几个标准媒体名称。媒体名称属性的实现或站点定义的名称也可以通过定义类MediaName的子类来创建。
 * <P>
 *  <B> IPP兼容性：</B> MediaName是用于命名媒体的IPP"media"属性的值的表示类。
 * <P>
 * 
 */
public class MediaName extends Media implements Attribute {

    private static final long serialVersionUID = 4653117714524155448L;

    /**
     *  white letter paper.
     * <p>
     *  白色信纸。
     * 
     */
    public static final MediaName NA_LETTER_WHITE = new MediaName(0);

    /**
     *  letter transparency.
     * <p>
     *  信透明度。
     * 
     */
    public static final MediaName NA_LETTER_TRANSPARENT = new MediaName(1);

    /**
     * white A4 paper.
     * <p>
     *  白色A4纸。
     * 
     */
    public static final MediaName ISO_A4_WHITE = new MediaName(2);


    /**
     *  A4 transparency.
     * <p>
     *  A4透明度。
     * 
     */
    public static final MediaName ISO_A4_TRANSPARENT= new MediaName(3);


    /**
     * Constructs a new media name enumeration value with the given integer
     * value.
     *
     * <p>
     *  使用给定的整数值构造新的介质名称枚举值。
     * 
     * 
     * @param  value  Integer value.
     */
    protected MediaName(int value) {
        super (value);
    }

    private static final String[] myStringTable = {
        "na-letter-white",
        "na-letter-transparent",
        "iso-a4-white",
        "iso-a4-transparent"
    };

    private static final MediaName[] myEnumValueTable = {
        NA_LETTER_WHITE,
        NA_LETTER_TRANSPARENT,
        ISO_A4_WHITE,
        ISO_A4_TRANSPARENT
    };

    /**
     * Returns the string table for class MediaTray.
     * <p>
     *  返回类MediaTray的字符串表。
     * 
     * 
     * @return the String table.
     */
    protected String[] getStringTable()
    {
        return (String[])myStringTable.clone();
    }

    /**
     * Returns the enumeration value table for class MediaTray.
     * <p>
     *  返回类MediaTray的枚举值表。
     * 
     * @return the enumeration value table.
     */
    protected EnumSyntax[] getEnumValueTable() {
        return (EnumSyntax[])myEnumValueTable.clone();
    }

}
