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
 * Class Sides is a printing attribute class, an enumeration, that specifies
 * how print-stream pages are to be imposed upon the sides of an instance of a
 * selected medium, i.e., an impression.
 * <P>
 * The effect of a Sides attribute on a multidoc print job (a job with multiple
 * documents) depends on whether all the docs have the same sides values
 * specified or whether different docs have different sides values specified,
 * and on the (perhaps defaulted) value of the {@link MultipleDocumentHandling
 * MultipleDocumentHandling} attribute.
 * <UL>
 * <LI>
 * If all the docs have the same sides value <I>n</I> specified, then any value
 * of {@link MultipleDocumentHandling MultipleDocumentHandling} makes sense,
 * and the printer's processing depends on the {@link MultipleDocumentHandling
 * MultipleDocumentHandling} value:
 * <UL>
 * <LI>
 * SINGLE_DOCUMENT -- All the input docs will be combined together into one
 * output document. Each media sheet will consist of <I>n</I> impressions from
 * the output document.
 * <P>
 * <LI>
 * SINGLE_DOCUMENT_NEW_SHEET -- All the input docs will be combined together
 * into one output document. Each media sheet will consist of <I>n</I>
 * impressions from the output document. However, the first impression of each
 * input doc will always start on a new media sheet; this means the last media
 * sheet of an input doc may have only one impression on it.
 * <P>
 * <LI>
 * SEPARATE_DOCUMENTS_UNCOLLATED_COPIES -- The input docs will remain separate.
 * Each media sheet will consist of <I>n</I> impressions from the input doc.
 * Since the input docs are separate, the first impression of each input doc
 * will always start on a new media sheet; this means the last media sheet of
 * an input doc may have only one impression on it.
 * <P>
 * <LI>
 * SEPARATE_DOCUMENTS_COLLATED_COPIES -- The input docs will remain separate.
 * Each media sheet will consist of <I>n</I> impressions from the input doc.
 * Since the input docs are separate, the first impression of each input doc
 * will always start on a new media sheet; this means the last media sheet of
 * an input doc may have only one impression on it.
 * </UL>
 * <P>
 * <UL>
 * <LI>
 * SINGLE_DOCUMENT -- All the input docs will be combined together into one
 * output document. Each media sheet will consist of <I>n<SUB>i</SUB></I>
 * impressions from the output document, where <I>i</I> is the number of the
 * input doc corresponding to that point in the output document. When the next
 * input doc has a different sides value from the previous input doc, the first
 * print-stream page of the next input doc goes at the start of the next media
 * sheet, possibly leaving only one impression on the previous media sheet.
 * <P>
 * <LI>
 * SINGLE_DOCUMENT_NEW_SHEET -- All the input docs will be combined together
 * into one output document. Each media sheet will consist of <I>n</I>
 * impressions from the output document. However, the first impression of each
 * input doc will always start on a new media sheet; this means the last
 * impression of an input doc may have only one impression on it.
 * <P>
 * <LI>
 * SEPARATE_DOCUMENTS_UNCOLLATED_COPIES -- The input docs will remain separate.
 * For input doc <I>i,</I> each media sheet will consist of <I>n<SUB>i</SUB></I>
 * impressions from the input doc. Since the input docs are separate, the first
 * impression of each input doc will always start on a new media sheet; this
 * means the last media sheet of an input doc may have only one impression on
 * it.
 * <P>
 * <LI>
 * SEPARATE_DOCUMENTS_COLLATED_COPIES -- The input docs will remain separate.
 * For input doc <I>i,</I> each media sheet will consist of <I>n<SUB>i</SUB></I>
 * impressions from the input doc. Since the input docs are separate, the first
 * impression of each input doc will always start on a new media sheet; this
 * means the last media sheet of an input doc may have only one impression on
 * it.
 * </UL>
 * </UL>
 * <P>
 * <B>IPP Compatibility:</B> The category name returned by
 * <CODE>getName()</CODE> is the IPP attribute name.  The enumeration's
 * integer value is the IPP enum value.  The <code>toString()</code> method
 * returns the IPP string representation of the attribute value.
 * <P>
 *
 * <p>
 *  类边是打印属性类,枚举,它指定如何将打印流页面施加到所选介质的实例的侧面,即,印象。
 * <P>
 *  Sides属性对多点打印作业(具有多个文档的作业)的影响取决于所有文档是否具有指定的相同边值,或者不同的文档是否具有指定的不同边值,以及是否(可能是默认值) {@link MultipleDocumentHandling MultipleDocumentHandling}
 * 属性。
 * <UL>
 * <LI>
 *  如果所有文档都指定了相同的边值,则{@link MultipleDocumentHandling MultipleDocumentHandling}的任何值都是有意义的,打印机的处理取决于{@link MultipleDocumentHandling MultipleDocumentHandling}
 * 值：。
 * <UL>
 * <LI>
 *  SINGLE_DOCUMENT  - 所有输入文档将组合在一起成为一个输出文档。每个介质表将包含来自输​​出文档的<I> n </I>个展示。
 * <P>
 * <LI>
 *  SINGLE_DOCUMENT_NEW_SHEET  - 所有输入文档将组合在一起成为一个输出文档。每个介质表将包含来自输​​出文档的<I> n </I>个展示。
 * 但是,每个输入文档的第一印象总是从新的媒体工作表开始;这意味着输入文档的最后一个媒体工作表可能只有一个印象。
 * <P>
 * <LI>
 * SEPARATE_DOCUMENTS_UNCOLLATED_COPIES  - 输入文档将保持单独。每个媒体表将包含来自输​​入文档的<I> n </I>印象。
 * 由于输入文档是单独的,每个输入文档的第一印象总是从新的媒体工作表开始;这意味着输入文档的最后一个媒体工作表可能只有一个印象。
 * <P>
 * <LI>
 *  SEPARATE_DOCUMENTS_COLLATED_COPIES  - 输入文档将保持独立。每个媒体表将包含来自输​​入文档的<I> n </I>印象。
 * 由于输入文档是单独的,每个输入文档的第一印象总是从新的媒体工作表开始;这意味着输入文档的最后一个媒体工作表可能只有一个印象。
 * </UL>
 * <P>
 * <UL>
 * <LI>
 *  SINGLE_DOCUMENT  - 所有输入文档将组合在一起成为一个输出文档。
 * 每个介质页将包含来自输​​出文档的<I> n <SUB> i </SUB> </I>印象,其中<I> i </I>是对应于输出文档。
 * 当下一输入文档具有与先前输入文档不同的边值时,下一输入文档的第一打印流页面在下一介质页的开始处行进,可能仅在前一介质页上留下一个印记。
 * <P>
 * <LI>
 * SINGLE_DOCUMENT_NEW_SHEET  - 所有输入文档将组合在一起成为一个输出文档。每个介质表将包含来自输​​出文档的<I> n </I>个展示。
 * 但是,每个输入文档的第一印象总是从新的媒体工作表开始;这意味着输入文档的最后一次展示可能只有一次展示。
 * <P>
 * <LI>
 *  SEPARATE_DOCUMENTS_UNCOLLATED_COPIES  - 输入文档将保持单独。
 * 对于输入doc <I> i,</I>,每个媒体表将包括来自输入文档的<I> n <SUB> i </SUB> </I>印象。
 * 由于输入文档是单独的,每个输入文档的第一印象总是从新的媒体工作表开始;这意味着输入文档的最后一个媒体工作表可能只有一个印象。
 * <P>
 * <LI>
 *  SEPARATE_DOCUMENTS_COLLATED_COPIES  - 输入文档将保持独立。
 * 对于输入doc <I> i,</I>,每个媒体表将包括来自输入文档的<I> n <SUB> i </SUB> </I>印象。
 * 由于输入文档是单独的,每个输入文档的第一印象总是从新的媒体工作表开始;这意味着输入文档的最后一个媒体工作表可能只有一个印象。
 * 
 * @author  Alan Kaminsky
 */

public final class Sides extends EnumSyntax
    implements DocAttribute, PrintRequestAttribute, PrintJobAttribute {

    private static final long serialVersionUID = -6890309414893262822L;

    /**
     * Imposes each consecutive print-stream page upon the same side of
     * consecutive media sheets.
     * <p>
     * </UL>
     * </UL>
     * <P>
     *  <B> IPP兼容性：</B> <CODE> getName()</CODE>返回的类别名称是IPP属性名称。枚举的整数值是IPP枚举值。
     *  <code> toString()</code>方法返回属性值的IPP字符串表示形式。
     * <P>
     * 
     */
    public static final Sides ONE_SIDED = new Sides(0);

    /**
     * Imposes each consecutive pair of print-stream pages upon front and back
     * sides of consecutive media sheets, such that the orientation of each
     * pair of print-stream pages on the medium would be correct for the
     * reader as if for binding on the long edge. This imposition is also
     * known as "duplex" (see {@link #DUPLEX DUPLEX}).
     * <p>
     *  将每个连续的打印流页面强加在连续介质页的同一侧。
     * 
     */
    public static final Sides TWO_SIDED_LONG_EDGE = new Sides(1);

    /**
     * Imposes each consecutive pair of print-stream pages upon front and back
     * sides of consecutive media sheets, such that the orientation of each
     * pair of print-stream pages on the medium would be correct for the
     * reader as if for binding on the short edge. This imposition is also
     * known as "tumble" (see {@link #TUMBLE TUMBLE}).
     * <p>
     * 将每对连续的打印流页面施加在连续介质页的正面和背面上,使得介质上的每对打印流页面的取向对于读者来说对于在长边缘上的绑定是正确的。
     * 此拼版也称为"双面"(请参阅​​{@link #DUPLEX DUPLEX})。
     * 
     */
    public static final Sides TWO_SIDED_SHORT_EDGE = new Sides(2);

    /**
     * An alias for "two sided long edge" (see {@link #TWO_SIDED_LONG_EDGE
     * TWO_SIDED_LONG_EDGE}).
     * <p>
     *  将每个连续的一对打印流页面施加在连续介质页的正面和背面上,使得介质上的每对打印流页面的取向对于读者来说对于在短边缘上的绑定是正确的。
     * 这种强加也被称为"滚动"(参见{@link #TUMBLE TUMBLE})。
     * 
     */
    public static final Sides DUPLEX = TWO_SIDED_LONG_EDGE;

    /**
     * An alias for "two sided short edge" (see {@link #TWO_SIDED_SHORT_EDGE
     * TWO_SIDED_SHORT_EDGE}).
     * <p>
     *  "双面长边"的别名(请参见{@link #TWO_SIDED_LONG_EDGE TWO_SIDED_LONG_EDGE})。
     * 
     */
    public static final Sides TUMBLE = TWO_SIDED_SHORT_EDGE;

    /**
     * Construct a new sides enumeration value with the given integer value.
     *
     * <p>
     *  "双面短边"的别名(请参见{@link #TWO_SIDED_SHORT_EDGE TWO_SIDED_SHORT_EDGE})。
     * 
     * 
     * @param  value  Integer value.
     */
    protected Sides(int value) {
        super (value);
    }

    private static final String[] myStringTable = {
        "one-sided",
        "two-sided-long-edge",
        "two-sided-short-edge"
    };

    private static final Sides[] myEnumValueTable = {
        ONE_SIDED,
        TWO_SIDED_LONG_EDGE,
        TWO_SIDED_SHORT_EDGE
    };

    /**
     * Returns the string table for class Sides.
     * <p>
     *  使用给定的整数值构造新边枚举值。
     * 
     */
    protected String[] getStringTable() {
        return myStringTable;
    }

    /**
     * Returns the enumeration value table for class Sides.
     * <p>
     *  返回Sides类的字符串表。
     * 
     */
    protected EnumSyntax[] getEnumValueTable() {
        return myEnumValueTable;
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class Sides, the category is class Sides itself.
     *
     * <p>
     *  返回类Sides的枚举值表。
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return Sides.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class Sides, the category name is <CODE>"sides"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于类Sides,类别是Sides本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "sides";
    }

}
