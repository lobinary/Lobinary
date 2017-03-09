/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2010, Oracle and/or its affiliates. All rights reserved.
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

import javax.print.attribute.EnumSyntax;
import javax.print.attribute.PrintRequestAttribute;

/**
 * Class DialogTypeSelection is a printing attribute class, an enumeration,
 * that indicates the user dialog type to be used for specifying
 * printing options.
 * If {@code NATIVE} is specified, then where available, a native
 * platform dialog is displayed.
 * If {@code COMMON} is specified, a cross-platform print dialog is displayed.
 *
 * This option to specify a native dialog for use with an IPP attribute
 * set provides a standard way to reflect back of the setting and option
 * changes made by a user to the calling application, and integrates
 * the native dialog into the Java printing APIs.
 * But note that some options and settings in a native dialog may not
 * necessarily map to IPP attributes as they may be non-standard platform,
 * or even printer specific options.
 * <P>
 * <B>IPP Compatibility:</B> This is not an IPP attribute.
 * <P>
 * <p>
 *  类DialogTypeSelection是一个打印属性类,枚举,指示用于指定打印选项的用户对话框类型。如果指定了{@code NATIVE},那么在可用的情况下,将显示本机平台对话框。
 * 如果指定了{@code COMMON},将显示跨平台打印对话框。
 * 
 *  指定用于IPP属性集的本地对话框的此选项提供了将用户对设置和选项更改反映回调用应用程序的标准方法,并将本机对话框集成到Java打印API中。
 * 但请注意,原生对话框中的某些选项和设置可能不一定映射到IPP属性,因为它们可能是非标准平台,甚至是特定于打印机的选项。
 * <P>
 *  <B> IPP兼容性：</B>这不是IPP属性。
 * <P>
 * 
 * @since 1.7
 *
 */
public final class DialogTypeSelection extends EnumSyntax
        implements PrintRequestAttribute {

    private static final long serialVersionUID = 7518682952133256029L;

    /**
     *
     * <p>
     */
    public static final DialogTypeSelection
        NATIVE = new DialogTypeSelection(0);

    /**
     *
     * <p>
     */
    public static final DialogTypeSelection
        COMMON = new DialogTypeSelection(1);

    /**
     * Construct a new dialog type selection enumeration value with the
     * given integer value.
     *
     * <p>
     *  使用给定的整数值构造新的对话框类型选择枚举值。
     * 
     * 
     * @param  value  Integer value.
     */
    protected DialogTypeSelection(int value) {
                super(value);
    }

    private static final String[] myStringTable = {
        "native", "common"};


    private static final DialogTypeSelection[] myEnumValueTable = {
        NATIVE,
        COMMON
    };

    /**
     * Returns the string table for class DialogTypeSelection.
     * <p>
     *  返回类DialogTypeSelection的字符串表。
     * 
     */
    protected String[] getStringTable() {
        return myStringTable;
    }

    /**
     * Returns the enumeration value table for class DialogTypeSelection.
     * <p>
     *  返回类DialogTypeSelection的枚举值表。
     * 
     */
    protected EnumSyntax[] getEnumValueTable() {
        return myEnumValueTable;
    }


   /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class DialogTypeSelection the category is class
     * DialogTypeSelection itself.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于类DialogTypeSelection类别是类DialogTypeSelection本身。
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class getCategory() {
        return DialogTypeSelection.class;
    }


    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class DialogTypeSelection the category name is
     * <CODE>"dialog-type-selection"</CODE>.
     *
     * <p>
     *  获取此属性值为实例的类别的名称。
     * <P>
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "dialog-type-selection";
    }

}
