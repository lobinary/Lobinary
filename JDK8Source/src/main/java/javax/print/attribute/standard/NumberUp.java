/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2004, Oracle and/or its affiliates. All rights reserved.
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
import javax.print.attribute.IntegerSyntax;
import javax.print.attribute.DocAttribute;
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintJobAttribute;

/**
 * Class NumberUp is an integer valued printing attribute class that specifies
 * the number of print-stream pages to impose upon a single side of an
 * instance of a selected medium. That is, if the NumberUp value is <I>n,</I>
 * the printer must place <I>n</I> print-stream pages on a single side of
 * an instance of the
 * selected medium. To accomplish this, the printer may add some sort of
 * translation, scaling, or rotation. This attribute primarily controls the
 * translation, scaling and rotation of print-stream pages.
 * <P>
 * The effect of a NumberUp attribute on a multidoc print job (a job with
 * multiple documents) depends on whether all the docs have the same number up
 * values specified or whether different docs have different number up values
 * specified, and on the (perhaps defaulted) value of the {@link
 * MultipleDocumentHandling MultipleDocumentHandling} attribute.
 * <UL>
 * <LI>
 * If all the docs have the same number up value <I>n</I> specified, then any
 * value of {@link MultipleDocumentHandling MultipleDocumentHandling} makes
 * sense, and the printer's processing depends on the {@link
 * MultipleDocumentHandling MultipleDocumentHandling} value:
 * <UL>
 * <LI>
 * SINGLE_DOCUMENT -- All the input docs will be combined together into one
 * output document. Each media impression will consist of <I>n</I>m
 *  print-stream pages from the output document.
 * <P>
 * <LI>
 * SINGLE_DOCUMENT_NEW_SHEET -- All the input docs will be combined together
 * into one output document. Each media impression will consist of <I>n</I>
 * print-stream pages from the output document. However, the first impression of
 * each input doc will always start on a new media sheet; this means the last
 * impression of an input doc may have fewer than <I>n</I> print-stream pages
 *  on it.
 * <P>
 * <LI>
 * SEPARATE_DOCUMENTS_UNCOLLATED_COPIES -- The input docs will remain separate.
 * Each media impression will consist of <I>n</I> print-stream pages from the
 * input doc. Since the input docs are separate, the first impression of each
 * input doc will always start on a new media sheet; this means the last
 * impression of an input doc may have fewer than <I>n</I> print-stream pages on
 * it.
 * <P>
 * <LI>
 * SEPARATE_DOCUMENTS_COLLATED_COPIES -- The input docs will remain separate.
 * Each media impression will consist of <I>n</I> print-stream pages from the
 * input doc. Since the input docs are separate, the first impression of each
 * input doc will always start on a new media sheet; this means the last
 * impression of an input doc may have fewer than <I>n</I> print-stream pages
 * on it.
 * </UL>
 * <UL>
 * <LI>
 * SINGLE_DOCUMENT -- All the input docs will be combined together into one
 * output document. Each media impression will consist of <I>n<SUB>i</SUB></I>
 * print-stream pages from the output document, where <I>i</I> is the number of
 * the input doc corresponding to that point in the output document. When the
 * next input doc has a different number up value from the previous input doc,
 * the first print-stream page of the next input doc goes at the start of the
 * next media impression, possibly leaving fewer than the full number of
 * print-stream pages on the previous media impression.
 * <P>
 * <LI>
 * SINGLE_DOCUMENT_NEW_SHEET -- All the input docs will be combined together
 * into one output document. Each media impression will consist of <I>n</I>
 * print-stream pages from the output document. However, the first impression of
 * each input doc will always start on a new media sheet; this means the last
 * impression of an input doc may have fewer than <I>n</I> print-stream pages
 * on it.
 * <P>
 * <LI>
 * SEPARATE_DOCUMENTS_UNCOLLATED_COPIES -- The input docs will remain separate.
 * For input doc <I>i,</I> each media impression will consist of
 * <I>n<SUB>i</SUB></I> print-stream pages from the input doc. Since the input
 * docs are separate, the first impression of each input doc will always start
 * on a new media sheet; this means the last impression of an input doc may have
 * fewer than <I>n<SUB>i</SUB></I> print-stream pages on it.
 * <P>
 * <LI>
 * SEPARATE_DOCUMENTS_COLLATED_COPIES -- The input docs will remain separate.
 * For input doc <I>i,</I> each media impression will consist of
 * <I>n<SUB>i</SUB></I> print-stream pages from the input doc. Since the input
 * docs are separate, the first impression of each input doc will always start
 * on a new media sheet; this means the last impression of an input doc may
 * have fewer than <I>n<SUB>i</SUB></I> print-stream pages on it.
 * </UL>
 * </UL>
 * <B>IPP Compatibility:</B> The integer value gives the IPP integer value.
 * The category name returned by <CODE>getName()</CODE> gives the IPP
 * attribute name.
 * <P>
 *
 * <p>
 *  类NumberUp是整数值打印属性类,其指定要对所选介质的实例的单面施加的打印流页面的数量。
 * 也就是说,如果NumberUp值是<I> n,则打印机必须在所选介质的实例的单侧上放置<I> n </i>打印流页面。为了实现这一点,打印机可以添加某种平移,缩放或旋转。
 * 此属性主要控制打印流页面的翻译,缩放和旋转。
 * <P>
 *  NumberUp属性对多点打印作业(具有多个文档的作业)的影响取决于所有文档是否具有指定的相同的数字上升值,或者不同的文档是否具有指定的不同的数字上升值,以及是否(可能是默认值)的{@link MultipleDocumentHandling MultipleDocumentHandling}
 * 属性。
 * <UL>
 * <LI>
 *  如果所有文档具有指定的相同的数字上限值<I> n </I>,则{@link MultipleDocumentHandling MultipleDocumentHandling}的任何值都是有意义的,打
 * 印机的处理取决于{@link MultipleDocumentHandling MultipleDocumentHandling}值：。
 * <UL>
 * <LI>
 *  SINGLE_DOCUMENT  - 所有输入文档将组合在一起成为一个输出文档。每个媒体印象将包括来自输出文档的<I> n个打印流页面。
 * <P>
 * <LI>
 * SINGLE_DOCUMENT_NEW_SHEET  - 所有输入文档将组合在一起成为一个输出文档。每个媒体印象将包括来自输出文档的<I> n </I>打印流页面。
 * 但是,每个输入文档的第一印象总是从新的媒体工作表开始;这意味着输入文档的最后一个印象可以具有少于<I> n个的打印流页面。
 * <P>
 * <LI>
 *  SEPARATE_DOCUMENTS_UNCOLLATED_COPIES  - 输入文档将保持单独。每个媒体展示将包含来自输​​入文档的<I> n </I>打印流页面。
 * 由于输入文档是单独的,每个输入文档的第一印象总是从新的媒体工作表开始;这意味着输入文档的最后一个印象可以具有少于<I> n个的打印流页面。
 * <P>
 * <LI>
 *  SEPARATE_DOCUMENTS_COLLATED_COPIES  - 输入文档将保持独立。每个媒体展示将包含来自输​​入文档的<I> n </I>打印流页面。
 * 由于输入文档是单独的,每个输入文档的第一印象总是从新的媒体工作表开始;这意味着输入文档的最后一个印象可以具有少于<I> n个的打印流页面。
 * </UL>
 * <UL>
 * <LI>
 * SINGLE_DOCUMENT  - 所有输入文档将组合在一起成为一个输出文档。
 * 每个媒体印象将包括来自输出文档的<I> n <SUB> i </SUB> </I>打印流页面,其中<I> i </I>是与其对应的输入文档的编号点在输出文档中。
 * 当下一输入文档具有与先前输入文档不同的数字上升值时,下一输入文档的第一打印流页面在下一媒体印象的开始处进行,可能留下少于全部数目的打印流页面对以前的媒体印象。
 * <P>
 * <LI>
 *  SINGLE_DOCUMENT_NEW_SHEET  - 所有输入文档将组合在一起成为一个输出文档。每个媒体印象将包括来自输出文档的<I> n </I>打印流页面。
 * 但是,每个输入文档的第一印象总是从新的媒体工作表开始;这意味着输入文档的最后一个印象可以具有少于<I> n个的打印流页面。
 * <P>
 * <LI>
 * 
 * @author  Alan Kaminsky
 */
public final class NumberUp extends IntegerSyntax
    implements DocAttribute, PrintRequestAttribute, PrintJobAttribute {

    private static final long serialVersionUID = -3040436486786527811L;


    /**
     * Construct a new number up attribute with the given integer value.
     *
     * <p>
     *  SEPARATE_DOCUMENTS_UNCOLLATED_COPIES  - 输入文档将保持单独。
     * 对于输入doc <I> i,</I>,每个媒体印象将包括来自输入文档的<I> n <SUB> i </SUB> </I>打印流页面。
     * 由于输入文档是单独的,每个输入文档的第一印象总是从新的媒体工作表开始;这意味着输入文档的最后一次印象可能具有少于<I> n <SUB> i </SUB> </I>个打印流页面。
     * <P>
     * <LI>
     * SEPARATE_DOCUMENTS_COLLATED_COPIES  - 输入文档将保持独立。
     * 对于输入doc <I> i,</I>,每个媒体印象将包括来自输入文档的<I> n <SUB> i </SUB> </I>打印流页面。
     * 由于输入文档是单独的,每个输入文档的第一印象总是从新的媒体工作表开始;这意味着输入文档的最后一次印象可能具有少于<I> n <SUB> i </SUB> </I>个打印流页面。
     * </UL>
     * </UL>
     *  <B> IPP兼容性：</B>整数值给出IPP整数值。由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
     * <P>
     * 
     * 
     * @param  value  Integer value.
     *
     * @exception  IllegalArgumentException
     *   (Unchecked exception) Thrown if <CODE>value</CODE> is less than 1.
     */
    public NumberUp(int value) {
        super (value, 1, Integer.MAX_VALUE);
    }

    /**
     * Returns whether this number up attribute is equivalent to the passed in
     * object. To be equivalent, all of the following conditions must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class NumberUp.
     * <LI>
     * This number up attribute's value and <CODE>object</CODE>'s value are
     * equal.
     * </OL>
     *
     * <p>
     *  使用给定的整数值构造新的数字向上属性。
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this number up
     *          attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return (super.equals(object) && object instanceof NumberUp);
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class NumberUp, the category is class NumberUp itself.
     *
     * <p>
     *  返回此数字up属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是NumberUp类的实例。
     * <LI>
     *  此数字up属性的值和<CODE>对象</CODE>的值相等。
     * </OL>
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return NumberUp.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class NumberUp, the category name is <CODE>"number-up"</CODE>.
     *
     * <p>
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "number-up";
    }

}
