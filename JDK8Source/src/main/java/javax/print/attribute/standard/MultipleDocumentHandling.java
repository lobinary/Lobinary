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
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintJobAttribute;

/**
 * Class MultipleDocumentHandling is a printing attribute class, an enumeration,
 * that controls finishing operations and the placement of one or more
 * print-stream pages into impressions and onto media sheets. When the value of
 * the {@link Copies Copies} attribute exceeds 1, MultipleDocumentHandling also
 * controls the order in which the copies that result from processing the
 * documents are produced. This attribute is relevant only for a multidoc print
 * job consisting of two or more individual docs.
 * <P>
 * Briefly, MultipleDocumentHandling determines the relationship between the
 * multiple input (electronic) documents fed into a multidoc print job and the
 * output (physical) document or documents produced by the multidoc print job.
 * There are two possibilities:
 * <UL>
 * <LI>
 * The multiple input documents are combined into a single output document.
 * Finishing operations ({@link Finishings Finishings}),
 * are performed on this single output
 * document. The {@link Copies Copies} attribute tells how many copies of this
 * single output document to produce. The MultipleDocumentHandling values
 * SINGLE_DOCUMENT and SINGLE_DOCUMENT_NEW_SHEET specify two variations of
 * this  possibility.
 * <P>
 * <LI>
 * The multiple input documents remain separate output documents. Finishing
 * operations ({@link Finishings Finishings}),
 * are performed on each output document
 * separately. The {@link Copies Copies} attribute tells how many copies of each
 * separate output document to produce. The MultipleDocumentHandling values
 * SEPARATE_DOCUMENTS_UNCOLLATED_COPIES and SEPARATE_DOCUMENTS_COLLATED_COPIES
 * specify two variations of this possibility.
 * </UL>
 * <P>
 * In the detailed explanations below, if "<CODE>a</CODE>" represents an
 * instance of document data, then the result of processing the data in
 * document "<CODE>a</CODE>" is a sequence of media sheets represented by
 * "<CODE>a(*)</CODE>".
 * <P>
 * The standard MultipleDocumentHandling values are:
 * <UL>
 * <LI>
 * <A NAME="sdfi">{@link #SINGLE_DOCUMENT
 * <B>SINGLE_DOCUMENT</B>}</A>. If a print job has multiple
 * documents -- say, the document data is called <CODE>a</CODE> and
 * <CODE>b</CODE> -- then the result of processing all the document data
 * (<CODE>a</CODE> and then <CODE>b</CODE>) must be treated as a single sequence
 * of media sheets for finishing operations; that is, finishing would be
 * performed on the concatenation of the sequences <CODE>a(*),b(*)</CODE>. The
 * printer must not force the data in each document instance to be formatted
 * onto a new print-stream page, nor to start a new impression on a new media
 * sheet. If more than one copy is made, the ordering of the sets of media
 * sheets resulting from processing the document data must be
 * <CODE>a(*),b(*),a(*),b(*),...</CODE>, and the printer object must force
 * each copy (<CODE>a(*),b(*)</CODE>) to start on a new media sheet.
 * <P>
 * <LI>
 * <A NAME="sducfi">{@link #SEPARATE_DOCUMENTS_UNCOLLATED_COPIES
 * <B>SEPARATE_DOCUMENTS_UNCOLLATED_COPIES</B>}</A>. If a print job
 * has multiple documents -- say, the document data is called <CODE>a</CODE> and
 * <CODE>b</CODE> -- then the result of processing the data in each document
 * instance must be treated as a single sequence of media sheets for finishing
 * operations; that is, the sets <CODE>a(*)</CODE> and <CODE>b(*)</CODE> would
 * each be finished separately. The printer must force each copy of the result
 * of processing the data in a single document to start on a new media sheet.
 * If more than one copy is made, the ordering of the sets of media sheets
 * resulting from processing the document data must be
 * <CODE>a(*),a(*),...,b(*),b(*)...</CODE>.
 * <P>
 * <LI>
 * <A NAME="sdccfi">{@link #SEPARATE_DOCUMENTS_COLLATED_COPIES
 * <B>SEPARATE_DOCUMENTS_COLLATED_COPIES</B>}</A>. If a print job
 * has multiple documents -- say, the document data is called <CODE>a</CODE> and
 * <CODE>b</CODE> -- then the result of processing the data in each document
 * instance must be treated as a single sequence of media sheets for finishing
 * operations; that is, the sets <CODE>a(*)</CODE> and <CODE>b(*)</CODE> would
 * each be finished separately. The printer must force each copy of the result
 * of processing the data in a single document to start on a new media sheet.
 * If more than one copy is made, the ordering of the sets of media sheets
 * resulting from processing the document data must be
 * <CODE>a(*),b(*),a(*),b(*),...</CODE>.
 * <P>
 * <LI>
 * <A NAME="sdnsfi">{@link #SINGLE_DOCUMENT_NEW_SHEET
 * <B>SINGLE_DOCUMENT_NEW_SHEET</B>}</A>. Same as SINGLE_DOCUMENT,
 * except that the printer must ensure that the first impression of each
 * document instance in the job is placed on a new media sheet. This value
 * allows multiple documents to be stapled together with a single staple where
 * each document starts on a new sheet.
 * </UL>
 * <P>
 * SINGLE_DOCUMENT is the same as SEPARATE_DOCUMENTS_COLLATED_COPIES with
 * respect to ordering of print-stream pages, but not media sheet generation,
 * since SINGLE_DOCUMENT will put the first page of the next document on the
 * back side of a sheet if an odd number of pages have been produced so far
 * for the job, while SEPARATE_DOCUMENTS_COLLATED_COPIES always forces the
 * next document or document copy on to a new sheet.
 * <P>
 * In addition, if a {@link Finishings Finishings} attribute of
 * {@link Finishings#STAPLE STAPLE} is specified, then:
 * <UL>
 * <LI>
 * With SINGLE_DOCUMENT, documents <CODE>a</CODE> and <CODE>b</CODE> are
 * stapled together as a single document with no regard to new sheets.
 * <P>
 * <LI>
 * With SINGLE_DOCUMENT_NEW_SHEET, documents <CODE>a</CODE> and <CODE>b</CODE>
 * are stapled together as a single document, but document <CODE>b</CODE>
 * starts on a new sheet.
 * <P>
 * <LI>
 * With SEPARATE_DOCUMENTS_UNCOLLATED_COPIES and
 * SEPARATE_DOCUMENTS_COLLATED_COPIES, documents <CODE>a</CODE> and
 * <CODE>b</CODE> are stapled separately.
 * </UL>
 * <P>
 * <I>Note:</I> None of these values provide means to produce uncollated
 * sheets within a document, i.e., where multiple copies of sheet <I>n</I>
 * are produced before sheet <I>n</I>+1 of the same document.
 * To specify that, see the {@link SheetCollate SheetCollate} attribute.
 * <P>
 * <B>IPP Compatibility:</B> The category name returned by
 * <CODE>getName()</CODE> is the IPP attribute name.  The enumeration's
 * integer value is the IPP enum value.  The <code>toString()</code> method
 * returns the IPP string representation of the attribute value.
 * <P>
 *
 * <p>
 *  Class MultipleDocumentHandling是一个打印属性类,枚举,它控制完成操作以及一个或多个打印流页面在打印和介质页面上的放置。
 * 当{@link Copies Copies}属性的值超过1时,MultipleDocumentHandling还控制生成处理文档的副本的顺序。此属性仅适用于由两个或多个单独文档组成的多标识打印作业。
 * <P>
 *  简而言之,MultipleDocumentHandling确定馈送到多点打印作业的多个输入(电子)文档与由多点打印作业生成的输出(物理)文档或多个文档之间的关系。有两种可能性：
 * <UL>
 * <LI>
 *  多个输入文档组合成单个输出文档。完成操作({@link Finishings Finishings})将在此单个输出文档上执行。
 *  {@link Copies Copies}属性指示要生成的单个输出文档的副本数。
 *  MultipleDocumentHandling值SINGLE_DOCUMENT和SINGLE_DOCUMENT_NEW_SHEET指定了这种可能性的两种变体。
 * <P>
 * <LI>
 * 多个输入文档仍然是单独的输出文档。分别对每个输出文档执行整理操作({@link Finishings Finishings})。
 *  {@link Copies Copies}属性指示要生成的每个单独输出文档的副本数。
 *  MultipleDocumentHandling值SEPARATE_DOCUMENTS_UNCOLLATED_COPIES和SEPARATE_DOCUMENTS_COLLATED_COPIES指定了此
 * 可能性的两种变体。
 *  {@link Copies Copies}属性指示要生成的每个单独输出文档的副本数。
 * </UL>
 * <P>
 *  在下面的详细解释中,如果"<CODE> a </CODE>"表示文档数据的实例,则处理文档"<CODE> a </CODE>"中的数据的结果是所表示的一系列介质页由"<CODE> a(*)</CODE>
 * "。
 * <P>
 *  标准MultipleDocumentHandling值是：
 * <UL>
 * <LI>
 * <A NAME="sdfi"> {@link #SINGLE_DOCUMENT <B> SINGLE_DOCUMENT </B>} </A>。
 * 如果打印作业有多个文档 - 例如,文档数据称为<CODE> a </CODE>和<CODE> b </CODE>  - 处理所有文档数据的结果(<CODE> / CODE>,然后<CODE> b </CODE>
 * )必须视为完成操作的单个介质页序列;也就是说,将对序列<CODE> a(*),b(*)</CODE>的连接执行整理。
 * <A NAME="sdfi"> {@link #SINGLE_DOCUMENT <B> SINGLE_DOCUMENT </B>} </A>。
 * 打印机不得强制将每个文档实例中的数据格式化到新的打印流页面上,也不得在新的介质页上开始新的印象。
 * 如果进行多于一个复制,则由处理文档数据产生的介质页集合的排序必须是<CODE> a(*),b(*),a(*),b(*),... </CODE>,打印机对象必须强制每个副本(<CODE> a(*),b(*
 * )</CODE>。
 * 打印机不得强制将每个文档实例中的数据格式化到新的打印流页面上,也不得在新的介质页上开始新的印象。
 * <P>
 * <LI>
 * <A NAME="sducfi"> {@link #SEPARATE_DOCUMENTS_UNCOLLATED_COPIES <B> SEPARATE_DOCUMENTS_UNCOLLATED_COPIES </B>}
 *  </A>。
 * 如果打印作业有多个文档 - 例如,文档数据称为<CODE> a </CODE>和<CODE> b </CODE>,则处理每个文档实例中的数据的结果必须视为用于精加工操作的单个介质片材序列;也就是说,<CODE>
 *  a(*)</CODE>和<CODE> b(*)</CODE>打印机必须强制处理单个文档中的数据的每个副本以在新的介质页上开始。
 * 如果进行多于一个复制,则由处理文档数据产生的介质页集合的排序必须是<CODE> a(*),a(*),...,b(*),b(*) ... </CODE>。
 * <P>
 * <LI>
 *  <A NAME="sdccfi"> {@link #SEPARATE_DOCUMENTS_COLLATED_COPIES <B> SEPARATE_DOCUMENTS_COLLATED_COPIES </B>}
 *  </A>。
 * 如果打印作业有多个文档 - 例如,文档数据称为<CODE> a </CODE>和<CODE> b </CODE>,则处理每个文档实例中的数据的结果必须视为用于精加工操作的单个介质片材序列;也就是说,<CODE>
 *  a(*)</CODE>和<CODE> b(*)</CODE>打印机必须强制处理单个文档中的数据的每个副本以在新的介质页上开始。
 * 如果进行多于一个复制,则由处理文档数据产生的介质页集合的排序必须是<CODE> a(*),b(*),a(*),b(*),... </CODE>。
 * <P>
 * <LI>
 * <A NAME="sdnsfi"> {@link #SINGLE_DOCUMENT_NEW_SHEET <B> SINGLE_DOCUMENT_NEW_SHEET </B>} </A>。
 * 与SINGLE_DOCUMENT相同,但打印机必须确保作业中每个文档实例的第一个印象放在新的媒体工作表上。此值允许使用单个订书钉装订多个文档,其中每个文档在新工作表上开始。
 * </UL>
 * <P>
 *  SINGLE_DOCUMENT与SEPARATE_DOCUMENTS_COLLATED_COPIES相同,关于打印流页面的排序,而不是介质页面生成,因为如果生成了奇数页面,SINGLE_DOCUMEN
 * T会将下一个文档的第一页放在页面的背面。
 * 
 * @see  Copies
 * @see  Finishings
 * @see  NumberUp
 * @see  PageRanges
 * @see  SheetCollate
 * @see  Sides
 *
 * @author  David Mendenhall
 * @author  Alan Kaminsky
 */
public class MultipleDocumentHandling extends EnumSyntax
    implements PrintRequestAttribute, PrintJobAttribute {

    private static final long serialVersionUID = 8098326460746413466L;


    /**
     * Single document -- see above for <A HREF="#sdfi">further
     * information</A>.
     * <p>
     * 远处的工作,而SEPARATE_DOCUMENTS_COLLATED_COPIES总是强制下一个文档或文档副本到一个新工作表。
     * <P>
     *  此外,如果指定{@link Finishings#STAPLE STAPLE}的{@link Finishings Finishings}属性,则：
     * <UL>
     * <LI>
     *  使用SINGLE_DOCUMENT,将<CODE> a </CODE>和<CODE> b </CODE>文档作为单个文档装订在一起,而不考虑新工作表。
     * <P>
     * <LI>
     *  使用SINGLE_DOCUMENT_NEW_SHEET,文档<CODE> a </CODE>和<CODE> b </CODE>作为单个文档装订在一起,但文档<CODE> b </CODE>
     * <P>
     * <LI>
     *  使用SEPARATE_DOCUMENTS_UNCOLLATED_COPIES和SEPARATE_DOCUMENTS_COLLATED_COPIES,文档<CODE> a </CODE>和<CODE> 
     * b </CODE>将分开装订。
     * </UL>
     * <P>
     * <I>注意：</I>这些值都不提供在文档中产生未分页的方法,即在第<I> n </I>页之前生成多个副本</n> +1的同一文档。
     * 要指定,请参阅{@link SheetCollat​​e SheetCollat​​e}属性。
     * <P>
     *  <B> IPP兼容性：</B> <CODE> getName()</CODE>返回的类别名称是IPP属性名称。枚举的整数值是IPP枚举值。
     *  <code> toString()</code>方法返回属性值的IPP字符串表示形式。
     * <P>
     * 
     */
    public static final MultipleDocumentHandling
        SINGLE_DOCUMENT = new MultipleDocumentHandling (0);

    /**
     * Separate documents uncollated copies -- see above for
     * <A HREF="#sducfi">further information</A>.
     * <p>
     *  单一文件 - 请参阅上面的<A HREF="#sdfi">进一步资讯</A>。
     * 
     */
    public static final MultipleDocumentHandling
       SEPARATE_DOCUMENTS_UNCOLLATED_COPIES = new MultipleDocumentHandling (1);

    /**
     * Separate documents collated copies -- see above for
     * <A HREF="#sdccfi">further information</A>.
     * <p>
     *  单独的文档未分页副本 - 请参阅上面的<A HREF="#sducfi">进一步信息</A>。
     * 
     */
    public static final MultipleDocumentHandling
        SEPARATE_DOCUMENTS_COLLATED_COPIES = new MultipleDocumentHandling (2);

    /**
     * Single document new sheet -- see above for
     * <A HREF="#sdnsfi">further information</A>.
     * <p>
     *  单独的文档整理副本 - 请参阅上面的<A HREF="#sdccfi">进一步信息</A>。
     * 
     */
    public static final MultipleDocumentHandling
        SINGLE_DOCUMENT_NEW_SHEET = new MultipleDocumentHandling (3);


    /**
     * Construct a new multiple document handling enumeration value with the
     * given integer value.
     *
     * <p>
     *  单一文件新工作表 - 请参阅上述<A HREF="#sdnsfi">进一步资讯</A>。
     * 
     * 
     * @param  value  Integer value.
     */
    protected MultipleDocumentHandling(int value) {
        super (value);
    }

    private static final String[] myStringTable = {
        "single-document",
        "separate-documents-uncollated-copies",
        "separate-documents-collated-copies",
        "single-document-new-sheet"
    };

    private static final MultipleDocumentHandling[] myEnumValueTable = {
        SINGLE_DOCUMENT,
        SEPARATE_DOCUMENTS_UNCOLLATED_COPIES,
        SEPARATE_DOCUMENTS_COLLATED_COPIES,
        SINGLE_DOCUMENT_NEW_SHEET
    };

    /**
     * Returns the string table for class MultipleDocumentHandling.
     * <p>
     *  使用给定的整数值构造新的多文档处理枚举值。
     * 
     */
    protected String[] getStringTable() {
        return (String[])myStringTable.clone();
    }

    /**
     * Returns the enumeration value table for class MultipleDocumentHandling.
     * <p>
     *  返回类MultipleDocumentHandling的字符串表。
     * 
     */
    protected EnumSyntax[] getEnumValueTable() {
        return (EnumSyntax[])myEnumValueTable.clone();
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class MultipleDocumentHandling and any vendor-defined subclasses,
     * the category is class MultipleDocumentHandling itself.
     *
     * <p>
     *  返回类MultipleDocumentHandling的枚举值表。
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return MultipleDocumentHandling.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class MultipleDocumentHandling and any vendor-defined subclasses,
     * the category name is <CODE>"multiple-document-handling"</CODE>.
     *
     * <p>
     *  获取要用作此打印属性值的"类别"的打印属性类。
     * <P>
     *  对于MultipleDocumentHandling类和任何供应商定义的子类,类别是MultipleDocumentHandling类本身。
     * 
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "multiple-document-handling";
    }

}
