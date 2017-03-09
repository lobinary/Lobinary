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
import javax.print.attribute.EnumSyntax;
import javax.print.attribute.DocAttribute;
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintJobAttribute;

/**
 * Class SheetCollate is a printing attribute class, an enumeration, that
 * specifies whether or not the media sheets of each copy of each printed
 * document in a job are to be in sequence, when multiple copies of the document
 * are specified by the {@link Copies Copies} attribute. When SheetCollate is
 * COLLATED, each copy of each document is printed with the print-stream sheets
 * in sequence. When SheetCollate is UNCOLLATED, each print-stream sheet is
 * printed a number of times equal to the value of the {@link Copies Copies}
 * attribute in succession. For example, suppose a document produces two media
 * sheets as output, {@link Copies Copies} is 6, and SheetCollate is UNCOLLATED;
 * in this case six copies of the first media sheet are printed followed by
 * six copies of the second media sheet.
 * <P>
 * Whether the effect of sheet collation is achieved by placing copies of a
 * document in multiple output bins or in the same output bin with
 * implementation defined document separation is implementation dependent.
 * Also whether it is achieved by making multiple passes over the job or by
 * using an output sorter is implementation dependent.
 * <P>
 * If a printer does not support the SheetCollate attribute (meaning the client
 * cannot specify any particular sheet collation), the printer must behave as
 * though SheetCollate were always set to COLLATED.
 * <P>
 * The SheetCollate attribute interacts with the {@link MultipleDocumentHandling
 * MultipleDocumentHandling} attribute. The {@link MultipleDocumentHandling
 * MultipleDocumentHandling} attribute describes the collation of entire
 * documents, and the SheetCollate attribute describes the semantics of
 * collating individual pages within a document.
 * <P>
 * The effect of a SheetCollate attribute on a multidoc print job (a job with
 * multiple documents) depends on whether all the docs have the same sheet
 * collation specified or whether different docs have different sheet
 * collations specified, and on the (perhaps defaulted) value of the {@link
 * MultipleDocumentHandling MultipleDocumentHandling} attribute.
 * <UL>
 * <LI>
 * If all the docs have the same sheet collation specified, then the following
 * combinations of SheetCollate and {@link MultipleDocumentHandling
 * MultipleDocumentHandling} are permitted, and the printer reports an error
 * when the job is submitted if any other combination is specified:
 * <UL>
 * <LI>
 * SheetCollate = COLLATED, {@link MultipleDocumentHandling
 * MultipleDocumentHandling} = SINGLE_DOCUMENT -- All the input docs will be
 * combined into one output document. Multiple copies of the output document
 * will be produced with pages in collated order, i.e. pages 1, 2, 3, . . .,
 * 1, 2, 3, . . .
 * <P>
 * <LI>
 * SheetCollate = COLLATED, {@link MultipleDocumentHandling
 * MultipleDocumentHandling} = SINGLE_DOCUMENT_NEW_SHEET -- All the input docs
 * will be combined into one output document, and the first impression of each
 * input doc will always start on a new media sheet. Multiple copies of the
 * output document will be produced with pages in collated order, i.e. pages
 * 1, 2, 3, . . ., 1, 2, 3, . . .
 * <P>
 * <LI>
 * SheetCollate = COLLATED, {@link MultipleDocumentHandling
 * MultipleDocumentHandling} = SEPARATE_DOCUMENTS_UNCOLLATED_COPIES -- Each
 * input doc will remain a separate output document. Multiple copies of each
 * output document (call them A, B, . . .) will be produced with each document's
 * pages in collated order, but the documents themselves in uncollated order,
 * i.e. pages A1, A2, A3, . . ., A1, A2, A3, . . ., B1, B2, B3, . . ., B1, B2,
 * B3, . . .
 * <P>
 * <LI>
 * SheetCollate = COLLATED, {@link MultipleDocumentHandling
 * MultipleDocumentHandling} = SEPARATE_DOCUMENTS_COLLATED_COPIES -- Each input
 * doc will remain a separate output document. Multiple copies of each output
 * document (call them A, B, . . .) will be produced with each document's pages
 * in collated order, with the documents themselves also in collated order, i.e.
 * pages A1, A2, A3, . . ., B1, B2, B3, . . ., A1, A2, A3, . . ., B1, B2, B3,
 * . . .
 * <P>
 * <LI>
 * SheetCollate = UNCOLLATED, {@link MultipleDocumentHandling
 * MultipleDocumentHandling} = SINGLE_DOCUMENT -- All the input docs will be
 * combined into one output document. Multiple copies of the output document
 * will be produced with pages in uncollated order, i.e. pages 1, 1, . . .,
 * 2, 2, . . ., 3, 3, . . .
 * <P>
 * <LI>
 * SheetCollate = UNCOLLATED, {@link MultipleDocumentHandling
 * MultipleDocumentHandling} = SINGLE_DOCUMENT_NEW_SHEET -- All the input docs
 * will be combined into one output document, and the first impression of each
 * input doc will always start on a new media sheet. Multiple copies of the
 * output document will be produced with pages in uncollated order, i.e. pages
 * 1, 1, . . ., 2, 2, . . ., 3, 3, . . .
 * <P>
 * <LI>
 * SheetCollate = UNCOLLATED, {@link MultipleDocumentHandling
 * MultipleDocumentHandling} = SEPARATE_DOCUMENTS_UNCOLLATED_COPIES -- Each
 * input doc will remain a separate output document. Multiple copies of each
 * output document (call them A, B, . . .) will be produced with each document's
 * pages in uncollated order, with the documents themselves also in uncollated
 * order, i.e. pages A1, A1, . . ., A2, A2, . . ., A3, A3, . . ., B1, B1, . . .,
 * B2, B2, . . ., B3, B3, . . .
 * </UL>
 * <P>
 * <LI>
 * If different docs have different sheet collations specified, then only one
 * value of {@link MultipleDocumentHandling MultipleDocumentHandling} is
 * permitted, and the printer reports an error when the job is submitted if any
 * other value is specified:
 * <UL>
 * <LI>
 * {@link MultipleDocumentHandling MultipleDocumentHandling} =
 * SEPARATE_DOCUMENTS_UNCOLLATED_COPIES -- Each input doc will remain a separate
 * output document. Multiple copies of each output document (call them A, B,
 * . . .) will be produced with each document's pages in collated or uncollated
 * order as the corresponding input doc's SheetCollate attribute specifies, and
 * with the documents themselves in uncollated order. If document A had
 * SheetCollate = UNCOLLATED and document B had SheetCollate = COLLATED, the
 * following pages would be produced: A1, A1, . . ., A2, A2, . . ., A3, A3,
 * . . ., B1, B2, B3, . . ., B1, B2, B3, . . .
 * </UL>
 * </UL>
 * <P>
 * <B>IPP Compatibility:</B> SheetCollate is not an IPP attribute at present.
 * <P>
 *
 * <p>
 *  类SheetCollat​​e是一个打印属性类,枚举,它指定在作业中每个打印文档的每个副本的介质页是否顺序排列,当文档的多个副本由{@link Copies Copies }属性。
 * 当SheetCollat​​e为COLLATED时,每个文档的每个副本都按顺序使用打印流打印。
 * 当SheetCollat​​e为UNCOLLATED时,每个打印流表单打印的次数连续等于{@link Copies Copies}属性的值。
 * 例如,假设文档生成两个媒体工作表作为输出,{@link Copies Copies}为6,SheetCollat​​e为UNCOLLATED;在这种情况下,打印六份第一张介质页,然后打印第二张介质页的六
 * 份。
 * 当SheetCollat​​e为UNCOLLATED时,每个打印流表单打印的次数连续等于{@link Copies Copies}属性的值。
 * <P>
 *  通过将文档的副本放置在多个输出仓中或者在具有实现定义的文档分离的同一输出仓中来实现薄片对比的效果是实现相关的。此外,无论是通过在作业上进行多次遍历还是通过使用输出排序器实现,都是与实现相关的。
 * <P>
 *  如果打印机不支持SheetCollat​​e属性(意味着客户端无法指定任何特定的表排序规则),则打印机必须表现为总是将SheetCollat​​e设置为COLLATED。
 * <P>
 * SheetCollat​​e属性与{@link MultipleDocumentHandling MultipleDocumentHandling}属性交互。
 *  {@link MultipleDocumentHandling MultipleDocumentHandling}属性描述整个文档的排序规则,SheetCollat​​e属性描述了整理文档中各个页面的
 * 语义。
 * SheetCollat​​e属性与{@link MultipleDocumentHandling MultipleDocumentHandling}属性交互。
 * <P>
 *  SheetCollat​​e属性对多节点打印作业(具有多个文档的作业)的影响取决于是否所有文档都具有指定的相同的页面排序规则,或者不同的文档是否指定了不同的页面排序规则,以及是否(可能是默认值) {@link MultipleDocumentHandling MultipleDocumentHandling}
 * 属性。
 * <UL>
 * <LI>
 *  如果所有文档都指定了相同的工作表排序规则,则允许使用以下SheetCollat​​e和{@link MultipleDocumentHandling MultipleDocumentHandling}
 * 的组合,并且如果指定了任何其他组合,则在提交作业时打印机报告错误：。
 * <UL>
 * <LI>
 *  SheetCollat​​e = COLLATED,{@link MultipleDocumentHandling MultipleDocumentHandling} = SINGLE_DOCUMEN
 * T  - 所有输入文档将合并为一个输出文档。
 * 输出文档的多个副本将以整理的顺序(即,第1,2,3,...页)生成。 。 ...,1,2,3,...。 。 。
 * <P>
 * <LI>
 * SheetCollat​​e = COLLATED,{@link MultipleDocumentHandling MultipleDocumentHandling} = SINGLE_DOCUMENT
 * _NEW_SHEET  - 所有输入文档将合并为一个输出文档,每个输入文档的第一印象将始终从新的媒体工作表开始。
 * 输出文档的多个副本将以整理的顺序(即,第1,2,3,...页)生成。 。 ...,1,2,3,...。 。 。
 * <P>
 * <LI>
 *  SheetCollat​​e = COLLATED,{@link MultipleDocumentHandling MultipleDocumentHandling} = SEPARATE_DOCUM
 * ENTS_UNCOLLATED_COPIES  - 每个输入文档将保留单独的输出文档。
 * 每个输出文档(称为A,B,...)的多个副本将以整理的顺序与每个文档的页面一起生成,但是以非排序顺序的文档本身,即页面A1,A2,A3,...。 。 ...,A1,A2,A3,...。 。
 *  ...,B1,B2,B3,...。 。 ...,B1,B2,B3,...。 。 。
 * <P>
 * <LI>
 *  SheetCollat​​e = COLLATED,{@link MultipleDocumentHandling MultipleDocumentHandling} = SEPARATE_DOCUM
 * ENTS_COLLATED_COPIES  - 每个输入文档将保留单独的输出文档。
 * 每个输出文档(称为A,B,...)的多个副本将以整理的顺序与每个文档的页面一起生成,文档本身也按照整理的顺序,即页面A1,A2,A3,...。 。 ...,B1,B2,B3,...。 。
 *  ...,A1,A2,A3,...。 。 ...,B1,B2,B3,...。 。 。
 * <P>
 * <LI>
 * SheetCollat​​e = UNCOLLATED,{@link MultipleDocumentHandling MultipleDocumentHandling} = SINGLE_DOCUME
 * NT  - 所有输入文档将合并为一个输出文档。
 * 输出文档的多个副本将以未分页顺序的页面生成,即页面1,1,...。 。 ...,2,2,...。 。 ...,3,3,...。 。 。
 * <P>
 * <LI>
 *  SheetCollat​​e = UNCOLLATED,{@link MultipleDocumentHandling MultipleDocumentHandling} = SINGLE_DOCUM
 * 
 * @see  MultipleDocumentHandling
 *
 * @author  Alan Kaminsky
 */
public final class SheetCollate extends EnumSyntax
    implements DocAttribute, PrintRequestAttribute, PrintJobAttribute {

    private static final long serialVersionUID = 7080587914259873003L;

    /**
     * Sheets within a document appear in uncollated order when multiple
     * copies are printed.
     * <p>
     * ENT_NEW_SHEET  - 所有输入文档将合并为一个输出文档,每个输入文档的第一印象将始终从新的媒体工作表开始。
     * 输出文档的多个副本将以未分页顺序的页面生成,即页面1,1,...。 。 ...,2,2,...。 。 ...,3,3,...。 。 。
     * <P>
     * <LI>
     *  SheetCollat​​e = UNCOLLATED,{@link MultipleDocumentHandling MultipleDocumentHandling} = SEPARATE_DOC
     * UMENTS_UNCOLLATED_COPIES  - 每个输入文档将保留单独的输出文档。
     * 每个输出文档(称为A,B,...)的多个副本将与每个文档的页面以非顺序顺序生成,文档本身也以未分页顺序,即页面A1,A1,...。 。 ...,A2,A2,...。 。 ...,A3,A3,...。
     *  。 ...,B1,B1,...。 。 ...,B2,B2,...。 。 ...,B3,B3,...。 。 。
     * </UL>
     * <P>
     * <LI>
     *  如果不同的文档指定了不同的页面排序规则,则只允许使用{@link MultipleDocumentHandling MultipleDocumentHandling}的一个值,并且如果指定了任何其他值
     * ,则在提交作业时打印机报告错误：。
     * <UL>
     * <LI>
     * {@link MultipleDocumentHandling MultipleDocumentHandling} = SEPARATE_DOCUMENTS_UNCOLLATED_COPIES  - 每
     * 个输入文档将保留单独的输出文档。
     * 每个输出文档(称为A,B,...)的多个副本将按照对应的输入文档的SheetCollat​​e属性指定的每个文档的页面以整理或未排序顺序生成,并且文档本身以未排序顺序生成。
     * 如果文档A具有SheetCollat​​e = UNCOLLATED,并且文档B具有SheetCollat​​e = COLLATED,则将产生以下页面：A1,A1,...。 。
     *  ...,A2,A2,...。 。 ...,A3,A3,...。 。 ...,B1,B2,B3,...。 。 ...,B1,B2,B3,...。 。 。
     * </UL>
     * </UL>
     * <P>
     *  <B> IPP兼容性：</B> SheetCollat​​e目前不是IPP属性。
     * <P>
     * 
     */
    public static final SheetCollate UNCOLLATED = new SheetCollate(0);

    /**
     * Sheets within a document appear in collated order when multiple copies
     * are printed.
     * <p>
     */
    public static final SheetCollate COLLATED = new SheetCollate(1);

    /**
     * Construct a new sheet collate enumeration value with the given integer
     * value.
     *
     * <p>
     *  打印多份副本时,文档中的表格以未分页的顺序显示。
     * 
     * 
     * @param  value  Integer value.
     */
    protected SheetCollate(int value) {
        super (value);
    }

    private static final String[] myStringTable = {
        "uncollated",
        "collated"
    };

    private static final SheetCollate[] myEnumValueTable = {
        UNCOLLATED,
        COLLATED
    };

    /**
     * Returns the string table for class SheetCollate.
     * <p>
     *  打印多份副本时,文档中的表格将按整理顺序显示。
     * 
     */
    protected String[] getStringTable() {
        return myStringTable;
    }

    /**
     * Returns the enumeration value table for class SheetCollate.
     * <p>
     *  使用给定的整数值构造新的工作表collat​​e枚举值。
     * 
     */
    protected EnumSyntax[] getEnumValueTable() {
        return myEnumValueTable;
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class SheetCollate, the category is class SheetCollate itself.
     *
     * <p>
     *  返回类SheetCollat​​e的字符串表。
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return SheetCollate.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class SheetCollate, the category name is <CODE>"sheet-collate"</CODE>.
     *
     * <p>
     *  返回类SheetCollat​​e的枚举值表。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "sheet-collate";
    }

}
