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

import javax.print.attribute.Attribute;
import javax.print.attribute.EnumSyntax;
import javax.print.attribute.DocAttribute;
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintJobAttribute;

/**
 * Class Finishings is a printing attribute class, an enumeration, that
 * identifies whether the printer applies a finishing operation of some kind
 * of binding to each copy of each printed document in the job. For multidoc
 * print jobs (jobs with multiple documents), the
 * {@link MultipleDocumentHandling
 * MultipleDocumentHandling} attribute determines what constitutes a "copy"
 * for purposes of finishing.
 * <P>
 * Standard Finishings values are:
 * <TABLE BORDER=0 CELLPADDING=0 CELLSPACING=0 WIDTH=100% SUMMARY="layout">
 * <TR>
 * <TD STYLE="WIDTH:10%">
 * &nbsp;
 * </TD>
 * <TD STYLE="WIDTH:27%">
 * {@link #NONE NONE}
 * </TD>
 * <TD STYLE="WIDTH:27%">
 * {@link #STAPLE STAPLE}
 * </TD>
 * <TD STYLE="WIDTH:36%">
 * {@link #EDGE_STITCH EDGE_STITCH}
 * </TD>
 * </TR>
 * <TR>
 * <TD>
 * &nbsp;
 * </TD>
 * <TD>
 * {@link #BIND BIND}
 * </TD>
 * <TD>
 * {@link #SADDLE_STITCH SADDLE_STITCH}
 * </TD>
 * <TD>
 * {@link #COVER COVER}
 * </TD>
 * <TD>
 * &nbsp;
 * </TD>
 * </TR>
 * </TABLE>
 * <P>
 * The following Finishings values are more specific; they indicate a
 * corner or an edge as if the document were a portrait document:
 * <TABLE BORDER=0 CELLPADDING=0 CELLSPACING=0 WIDTH=100% SUMMARY="layout">
 * <TR>
 * <TD STYLE="WIDTH:10%">
 * &nbsp;
 * </TD>
 * <TD STYLE="WIDTH:27%">
 * {@link #STAPLE_TOP_LEFT STAPLE_TOP_LEFT}
 * </TD>
 * <TD STYLE="WIDTH:27%">
 * {@link #EDGE_STITCH_LEFT EDGE_STITCH_LEFT}
 * </TD>
 * <TD STYLE="WIDTH:27%">
 * {@link #STAPLE_DUAL_LEFT STAPLE_DUAL_LEFT}
 * </TD>
 * <TD STYLE="WIDTH:9%">
 * &nbsp;
 * </TD>
 * </TR>
 * <TR>
 * <TD STYLE="WIDTH:10%">
 * &nbsp;
 * </TD>
 * <TD STYLE="WIDTH:27%">
 * {@link #STAPLE_BOTTOM_LEFT STAPLE_BOTTOM_LEFT}
 * </TD>
 * <TD STYLE="WIDTH:27%">
 * {@link #EDGE_STITCH_TOP EDGE_STITCH_TOP}
 * </TD>
 * <TD STYLE="WIDTH:27%">
 * {@link #STAPLE_DUAL_TOP STAPLE_DUAL_TOP}
 * </TD>
 * <TD STYLE="WIDTH:9%">
 * &nbsp;
 * </TD>
 * </TR>
 * <TR>
 * <TD STYLE="WIDTH:10%">
 * &nbsp;
 * </TD>
 * <TD STYLE="WIDTH:27%">
 * {@link #STAPLE_TOP_RIGHT STAPLE_TOP_RIGHT}
 * </TD>
 * <TD STYLE="WIDTH:27%">
 * {@link #EDGE_STITCH_RIGHT EDGE_STITCH_RIGHT}
 * </TD>
 * <TD STYLE="WIDTH:27%">
 * {@link #STAPLE_DUAL_RIGHT STAPLE_DUAL_RIGHT}
 * </TD>
 * <TD STYLE="WIDTH:9%">
 * &nbsp;
 * </TD>
 * </TR>
 * <TR>
 * <TD STYLE="WIDTH:10%">
 * &nbsp;
 * </TD>
 * <TD STYLE="WIDTH:27%">
 * {@link #STAPLE_BOTTOM_RIGHT STAPLE_BOTTOM_RIGHT}
 * </TD>
 * <TD STYLE="WIDTH:27%">
 * {@link #EDGE_STITCH_BOTTOM EDGE_STITCH_BOTTOM}
 * </TD>
 * <TD STYLE="WIDTH:27%">
 * {@link #STAPLE_DUAL_BOTTOM STAPLE_DUAL_BOTTOM}
 * </TD>
 * <TD STYLE="WIDTH:9%">
 * &nbsp;
 * </TD>
 * </TR>
 * </TABLE>
 * <P>
 * The STAPLE_<I>XXX</I> values are specified with respect to the
 * document as if the document were a portrait document. If the document is
 * actually a landscape or a reverse-landscape document, the client supplies the
 * appropriate transformed value. For example, to position a staple in the upper
 * left hand corner of a landscape document when held for reading, the client
 * supplies the STAPLE_BOTTOM_LEFT value (since landscape is
 * defined as a +90 degree rotation from portrait, i.e., anti-clockwise). On the
 * other hand, to position a staple in the upper left hand corner of a
 * reverse-landscape document when held for reading, the client supplies the
 * STAPLE_TOP_RIGHT value (since reverse-landscape is defined as a
 * -90 degree rotation from portrait, i.e., clockwise).
 * <P>
 * The angle (vertical, horizontal, angled) of each staple with respect to the
 * document depends on the implementation which may in turn depend on the value
 * of the attribute.
 * <P>
 * The effect of a Finishings attribute on a multidoc print job (a job
 * with multiple documents) depends on whether all the docs have the same
 * binding specified or whether different docs have different bindings
 * specified, and on the (perhaps defaulted) value of the {@link
 * MultipleDocumentHandling MultipleDocumentHandling} attribute.
 * <UL>
 * <LI>
 * If all the docs have the same binding specified, then any value of {@link
 * MultipleDocumentHandling MultipleDocumentHandling} makes sense, and the
 * printer's processing depends on the {@link MultipleDocumentHandling
 * MultipleDocumentHandling} value:
 * <UL>
 * <LI>
 * SINGLE_DOCUMENT -- All the input docs will be bound together as one output
 * document with the specified binding.
 * <P>
 * <LI>
 * SINGLE_DOCUMENT_NEW_SHEET -- All the input docs will be bound together as one
 * output document with the specified binding, and the first impression of each
 * input doc will always start on a new media sheet.
 * <P>
 * <LI>
 * SEPARATE_DOCUMENTS_UNCOLLATED_COPIES -- Each input doc will be bound
 * separately with the specified binding.
 * <P>
 * <LI>
 * SEPARATE_DOCUMENTS_COLLATED_COPIES -- Each input doc will be bound separately
 * with the specified binding.
 * </UL>
 * <P>
 * <LI>
 * If different docs have different bindings specified, then only two values of
 * {@link MultipleDocumentHandling MultipleDocumentHandling} make sense, and the
 * printer reports an error when the job is submitted if any other value is
 * specified:
 * <UL>
 * <LI>
 * SEPARATE_DOCUMENTS_UNCOLLATED_COPIES -- Each input doc will be bound
 * separately with its own specified binding.
 * <P>
 * <LI>
 * SEPARATE_DOCUMENTS_COLLATED_COPIES -- Each input doc will be bound separately
 * with its own specified binding.
 * </UL>
 * </UL>
 * <P>
 * <B>IPP Compatibility:</B> Class Finishings encapsulates some of the
 * IPP enum values that can be included in an IPP "finishings" attribute, which
 * is a set of enums. The category name returned by
 * <CODE>getName()</CODE> is the IPP attribute name.  The enumeration's
 * integer value is the IPP enum value.  The <code>toString()</code> method
 * returns the IPP string representation of the attribute value.
 * In IPP Finishings is a multi-value attribute, this API currently allows
 * only one binding to be specified.
 *
 * <p>
 *  类Finishings是打印属性类,枚举,用于标识打印机是否对作业中每个打印文档的每个副本应用某种类型的绑定的完成操作。
 * 对于多标识打印作业(具有多个文档的作业),{@link MultipleDocumentHandling MultipleDocumentHandling}属性确定构成"复印"的目的是用于整理的。
 * <P>
 *  标准完成值为：
 * <TABLE BORDER=0 CELLPADDING=0 CELLSPACING=0 WIDTH=100% SUMMARY="layout">
 * <TR>
 * <TD STYLE="WIDTH:10%">
 *  &nbsp;
 * </TD>
 * <TD STYLE="WIDTH:27%">
 *  {@link #NONE NONE}
 * </TD>
 * <TD STYLE="WIDTH:27%">
 *  {@link #STAPLE STAPLE}
 * </TD>
 * <TD STYLE="WIDTH:36%">
 *  {@link #EDGE_STITCH EDGE_STITCH}
 * </TD>
 * </TR>
 * <TR>
 * <TD>
 *  &nbsp;
 * </TD>
 * <TD>
 *  {@link #BIND BIND}
 * </TD>
 * <TD>
 *  {@link #SADDLE_STITCH SADDLE_STITCH}
 * </TD>
 * <TD>
 *  {@link #COVER COVER}
 * </TD>
 * <TD>
 *  &nbsp;
 * </TD>
 * </TR>
 * </TABLE>
 * <P>
 *  以下Finishings值更具体;它们表示角落或边缘,就像文档是肖像文档一样：
 * <TABLE BORDER=0 CELLPADDING=0 CELLSPACING=0 WIDTH=100% SUMMARY="layout">
 * <TR>
 * <TD STYLE="WIDTH:10%">
 *  &nbsp;
 * </TD>
 * <TD STYLE="WIDTH:27%">
 *  {@link #STAPLE_TOP_LEFT STAPLE_TOP_LEFT}
 * </TD>
 * <TD STYLE="WIDTH:27%">
 *  {@link #EDGE_STITCH_LEFT EDGE_STITCH_LEFT}
 * </TD>
 * <TD STYLE="WIDTH:27%">
 *  {@link #STAPLE_DUAL_LEFT STAPLE_DUAL_LEFT}
 * </TD>
 * <TD STYLE="WIDTH:9%">
 *  &nbsp;
 * </TD>
 * </TR>
 * <TR>
 * <TD STYLE="WIDTH:10%">
 *  &nbsp;
 * </TD>
 * <TD STYLE="WIDTH:27%">
 *  {@link #STAPLE_BOTTOM_LEFT STAPLE_BOTTOM_LEFT}
 * </TD>
 * <TD STYLE="WIDTH:27%">
 *  {@link #EDGE_STITCH_TOP EDGE_STITCH_TOP}
 * </TD>
 * <TD STYLE="WIDTH:27%">
 *  {@link #STAPLE_DUAL_TOP STAPLE_DUAL_TOP}
 * </TD>
 * <TD STYLE="WIDTH:9%">
 *  &nbsp;
 * </TD>
 * </TR>
 * <TR>
 * <TD STYLE="WIDTH:10%">
 *  &nbsp;
 * </TD>
 * <TD STYLE="WIDTH:27%">
 *  {@link #STAPLE_TOP_RIGHT STAPLE_TOP_RIGHT}
 * </TD>
 * <TD STYLE="WIDTH:27%">
 *  {@link #EDGE_STITCH_RIGHT EDGE_STITCH_RIGHT}
 * </TD>
 * <TD STYLE="WIDTH:27%">
 *  {@link #STAPLE_DUAL_RIGHT STAPLE_DUAL_RIGHT}
 * </TD>
 * <TD STYLE="WIDTH:9%">
 *  &nbsp;
 * </TD>
 * </TR>
 * <TR>
 * <TD STYLE="WIDTH:10%">
 *  &nbsp;
 * </TD>
 * <TD STYLE="WIDTH:27%">
 *  {@link #STAPLE_BOTTOM_RIGHT STAPLE_BOTTOM_RIGHT}
 * </TD>
 * <TD STYLE="WIDTH:27%">
 *  {@link #EDGE_STITCH_BOTTOM EDGE_STITCH_BOTTOM}
 * </TD>
 * <TD STYLE="WIDTH:27%">
 *  {@link #STAPLE_DUAL_BOTTOM STAPLE_DUAL_BOTTOM}
 * </TD>
 * <TD STYLE="WIDTH:9%">
 *  &nbsp;
 * </TD>
 * </TR>
 * </TABLE>
 * <P>
 * 相对于文档指定STAPLE_ <I> XXX </I>值,如同文档是纵向文档一样。如果文档实际上是横向或反向横向文档,则客户端提供适当的变换值。
 * 例如,为了在保持用于读取时将订书钉定位在横向文档的左上角,客户端提供STAPLE_BOTTOM_LEFT值(因为横向定义为从纵向旋转+90度,即逆时针)。
 * 另一方面,为了在保持用于读取时将订书钉定位在反转横向文档的左上角中,客户端提供STAPLE_TOP_RIGHT值(因为反向横向被定义为从纵向的-90度旋转,即,顺时针)。
 * <P>
 * 
 * @author  Alan Kaminsky
 */
public class Finishings extends EnumSyntax
    implements DocAttribute, PrintRequestAttribute, PrintJobAttribute {

    private static final long serialVersionUID = -627840419548391754L;

    /**
     * Perform no binding.
     * <p>
     *  每个订书钉相对于文档的角度(垂直,水平,成角度)取决于实现,而实现又可以取决于属性的值。
     * <P>
     *  Finishings属性对多节点打印作业(具有多个文档的作业)的影响取决于所有文档是否具有指定的相同绑定,或不同的文档是否具有指定的不同绑定,以及{@(可能是默认值)链接MultipleDocumentHandling MultipleDocumentHandling}
     * 属性。
     * <UL>
     * <LI>
     * 如果所有文档都指定了相同的绑定,则{@link MultipleDocumentHandling MultipleDocumentHandling}的任何值都是有意义的,打印机的处理取决于{@link MultipleDocumentHandling MultipleDocumentHandling}
     * 值：。
     * <UL>
     * <LI>
     *  SINGLE_DOCUMENT  - 所有输入文档将绑定在一起作为一个输出文档与指定的绑定。
     * <P>
     * <LI>
     *  SINGLE_DOCUMENT_NEW_SHEET  - 所有输入文档将绑定在一起作为一个输出文档与指定的绑定,每个输入文档的第一印象将始终从新的媒体工作表。
     * <P>
     * <LI>
     *  SEPARATE_DOCUMENTS_UNCOLLATED_COPIES  - 每个输入文档将与指定的绑定分开绑定。
     * <P>
     * <LI>
     *  SEPARATE_DOCUMENTS_COLLATED_COPIES  - 每个输入文档将与指定的绑定分开绑定。
     * </UL>
     * <P>
     * <LI>
     *  如果不同的文档指定了不同的绑定,则只有两个值{@link MultipleDocumentHandling MultipleDocumentHandling}是有意义的,并且当任务提交时打印机报告错误
     * ,如果指定了任何其他值：。
     * <UL>
     * <LI>
     *  SEPARATE_DOCUMENTS_UNCOLLATED_COPIES  - 每个输入文档将单独绑定其自己指定的绑定。
     * <P>
     * <LI>
     *  SEPARATE_DOCUMENTS_COLLATED_COPIES  - 每个输入文档将单独绑定其自己指定的绑定。
     * </UL>
     * </UL>
     * <P>
     * <B> IPP兼容性：</B>类完成封装了一些可以包含在IPP"finishings"属性中的IPP枚举值,这是一组枚举。
     *  <CODE> getName()</CODE>返回的类别名称是IPP属性名称。枚举的整数值是IPP枚举值。 <code> toString()</code>方法返回属性值的IPP字符串表示形式。
     * 在IPP中,Finishings是一个多值属性,此API目前仅允许指定一个绑定。
     * 
     */
    public static final Finishings NONE = new Finishings(3);

    /**
     * Bind the document(s) with one or more staples. The exact number and
     * placement of the staples is site-defined.
     * <p>
     *  不执行绑定。
     * 
     */
    public static final Finishings STAPLE = new Finishings(4);

    /**
     * This value is specified when it is desired to select a non-printed (or
     * pre-printed) cover for the document. This does not supplant the
     * specification of a printed cover (on cover stock medium) by the
     * document  itself.
     * <p>
     *  使用一个或多个订书钉装订文档。订书钉的确切数量和位置是现场定义的。
     * 
     */
    public static final Finishings COVER = new Finishings(6);

    /**
     * This value indicates that a binding is to be applied to the document;
     * the type and placement of the binding is site-defined.
     * <p>
     *  当希望为文档选择非打印(或预打印)的盖时,指定该值。这不能取代文件本身对打印盖(在封面纸介质上)的规格。
     * 
     */
    public static final Finishings BIND = new Finishings(7);

    /**
     * Bind the document(s) with one or more staples (wire stitches) along the
     * middle fold. The exact number and placement of the staples and the
     * middle fold is implementation- and/or site-defined.
     * <p>
     *  此值指示要对文档应用绑定;绑定的类型和位置是位置定义的。
     * 
     */
    public static final Finishings SADDLE_STITCH =
        new Finishings(8);

    /**
     * Bind the document(s) with one or more staples (wire stitches) along one
     * edge. The exact number and placement of the staples is implementation-
     * and/or site- defined.
     * <p>
     *  使用一个或多个订书钉(线迹)沿中间折叠装订文档。缝钉和中间折叠的确切数量和位置是实施和/或位置限定的。
     * 
     */
    public static final Finishings EDGE_STITCH =
        new Finishings(9);

    /**
     * Bind the document(s) with one or more staples in the top left corner.
     * <p>
     *  使用一个或多个订书钉(线迹)沿着一个边缘装订文档。钉的确切数量和位置是实施和/或位置限定的。
     * 
     */
    public static final Finishings STAPLE_TOP_LEFT =
        new Finishings(20);

    /**
     * Bind the document(s) with one or more staples in the bottom left
     * corner.
     * <p>
     *  使用左上角的一个或多个订书钉装订文档。
     * 
     */
    public static final Finishings STAPLE_BOTTOM_LEFT =
        new Finishings(21);

    /**
     * Bind the document(s) with one or more staples in the top right corner.
     * <p>
     *  使用左下角的一个或多个订书钉装订文档。
     * 
     */
    public static final Finishings STAPLE_TOP_RIGHT =
        new Finishings(22);

    /**
     * Bind the document(s) with one or more staples in the bottom right
     * corner.
     * <p>
     * 使用右上角的一个或多个订书钉装订文档。
     * 
     */
    public static final Finishings STAPLE_BOTTOM_RIGHT =
        new Finishings(23);

    /**
     * Bind the document(s) with one or more staples (wire stitches) along the
     * left edge. The exact number and placement of the staples is
     * implementation- and/or site-defined.
     * <p>
     *  使用右下角的一个或多个订书钉装订文档。
     * 
     */
    public static final Finishings EDGE_STITCH_LEFT =
        new Finishings(24);

    /**
     * Bind the document(s) with one or more staples (wire stitches) along the
     * top edge. The exact number and placement of the staples is
     * implementation- and/or site-defined.
     * <p>
     *  沿左边缘用一个或多个订书钉(线迹)装订文档。钉的确切数量和位置是实施和/或位置定义的。
     * 
     */
    public static final Finishings EDGE_STITCH_TOP =
        new Finishings(25);

    /**
     * Bind the document(s) with one or more staples (wire stitches) along the
     * right edge. The exact number and placement of the staples is
     * implementation- and/or site-defined.
     * <p>
     *  使用一个或多个订书钉(线迹)沿着顶部边缘装订文档。钉的确切数量和位置是实施和/或位置定义的。
     * 
     */
    public static final Finishings EDGE_STITCH_RIGHT =
        new Finishings(26);

    /**
     * Bind the document(s) with one or more staples (wire stitches) along the
     * bottom edge. The exact number and placement of the staples is
     * implementation- and/or site-defined.
     * <p>
     *  使用一个或多个订书钉(线迹)沿着右边缘装订文档。钉的确切数量和位置是实施和/或位置定义的。
     * 
     */
    public static final Finishings EDGE_STITCH_BOTTOM =
        new Finishings(27);

    /**
     * Bind the document(s) with two staples (wire stitches) along the left
     * edge assuming a portrait document (see above).
     * <p>
     *  使用一个或多个订书钉(线迹)沿着底部边缘装订文档。钉的确切数量和位置是实施和/或位置定义的。
     * 
     */
    public static final Finishings STAPLE_DUAL_LEFT =
        new Finishings(28);

    /**
     * Bind the document(s) with two staples (wire stitches) along the top
     * edge assuming a portrait document (see above).
     * <p>
     *  使用两个订书钉(线迹)沿左边缘装订文档,假定有纵向文档(见上文)。
     * 
     */
    public static final Finishings STAPLE_DUAL_TOP =
        new Finishings(29);

    /**
     * Bind the document(s) with two staples (wire stitches) along the right
     * edge assuming a portrait document (see above).
     * <p>
     *  使用两个订书钉(线迹)沿顶边装订文档,假定有纵向文档(见上文)。
     * 
     */
    public static final Finishings STAPLE_DUAL_RIGHT =
        new Finishings(30);

    /**
     * Bind the document(s) with two staples (wire stitches) along the bottom
     * edge assuming a portrait document (see above).
     * <p>
     *  使用两个订书钉(线迹)沿着右边缘装订文档,假定为纵向文档(见上文)。
     * 
     */
    public static final Finishings STAPLE_DUAL_BOTTOM =
        new Finishings(31);

    /**
     * Construct a new finishings binding enumeration value with the given
     * integer value.
     *
     * <p>
     *  使用两个订书钉(线迹)沿底边装订文档,假定有纵向文档(见上文)。
     * 
     * 
     * @param  value  Integer value.
     */
    protected Finishings(int value) {
        super(value);
    }

    private static final String[] myStringTable =
                {"none",
                 "staple",
                 null,
                 "cover",
                 "bind",
                 "saddle-stitch",
                 "edge-stitch",
                 null, // The next ten enum values are reserved.
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 "staple-top-left",
                 "staple-bottom-left",
                 "staple-top-right",
                 "staple-bottom-right",
                 "edge-stitch-left",
                 "edge-stitch-top",
                 "edge-stitch-right",
                 "edge-stitch-bottom",
                 "staple-dual-left",
                 "staple-dual-top",
                 "staple-dual-right",
                 "staple-dual-bottom"
                };

    private static final Finishings[] myEnumValueTable =
                {NONE,
                 STAPLE,
                 null,
                 COVER,
                 BIND,
                 SADDLE_STITCH,
                 EDGE_STITCH,
                 null, // The next ten enum values are reserved.
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 null,
                 STAPLE_TOP_LEFT,
                 STAPLE_BOTTOM_LEFT,
                 STAPLE_TOP_RIGHT,
                 STAPLE_BOTTOM_RIGHT,
                 EDGE_STITCH_LEFT,
                 EDGE_STITCH_TOP,
                 EDGE_STITCH_RIGHT,
                 EDGE_STITCH_BOTTOM,
                 STAPLE_DUAL_LEFT,
                 STAPLE_DUAL_TOP,
                 STAPLE_DUAL_RIGHT,
                 STAPLE_DUAL_BOTTOM
                };

    /**
     * Returns the string table for class Finishings.
     * <p>
     *  使用给定的整数值构造新的完成绑定枚举值。
     * 
     */
    protected String[] getStringTable() {
        return (String[])myStringTable.clone();
    }

    /**
     * Returns the enumeration value table for class Finishings.
     * <p>
     *  返回类Finishings的字符串表。
     * 
     */
    protected EnumSyntax[] getEnumValueTable() {
        return (EnumSyntax[])myEnumValueTable.clone();
    }

    /**
     * Returns the lowest integer value used by class Finishings.
     * <p>
     *  返回类Finishings的枚举值表。
     * 
     */
    protected int getOffset() {
        return 3;
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class Finishings and any vendor-defined subclasses, the
     * category is class Finishings itself.
     *
     * <p>
     * 返回类Finishings使用的最小整数值。
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return Finishings.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class Finishings and any vendor-defined subclasses, the
     * category name is <CODE>"finishings"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于类Finishings和任何供应商定义的子类,类别是类Finishings本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "finishings";
    }

}
