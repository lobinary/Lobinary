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
import javax.print.attribute.PrintRequestAttribute;
import javax.print.attribute.PrintJobAttribute;

/**
 * Class Copies is an integer valued printing attribute class that specifies the
 * number of copies to be printed.
 * <P>
 * On many devices the supported number of collated copies will be limited by
 * the number of physical output bins on the device, and may be different from
 * the number of uncollated copies which can be supported.
 * <P>
 * The effect of a Copies attribute with a value of <I>n</I> on a multidoc print
 * job (a job with multiple documents) depends on the (perhaps defaulted) value
 * of the {@link MultipleDocumentHandling MultipleDocumentHandling} attribute:
 * <UL>
 * <LI>
 * SINGLE_DOCUMENT -- The result will be <I>n</I> copies of a single output
 * document comprising all the input docs.
 * <P>
 * <LI>
 * SINGLE_DOCUMENT_NEW_SHEET -- The result will be <I>n</I> copies of a single
 * output document comprising all the input docs, and the first impression of
 * each input doc will always start on a new media sheet.
 * <P>
 * <LI>
 * SEPARATE_DOCUMENTS_UNCOLLATED_COPIES -- The result will be <I>n</I> copies of
 * the first input document, followed by <I>n</I> copies of the second input
 * document, . . . followed by <I>n</I> copies of the last input document.
 * <P>
 * <LI>
 * SEPARATE_DOCUMENTS_COLLATED_COPIES -- The result will be the first input
 * document, the second input document, . . . the last input document, the group
 * of documents being repeated <I>n</I> times.
 * </UL>
 * <P>
 * <B>IPP Compatibility:</B> The integer value gives the IPP integer value. The
 * category name returned by <CODE>getName()</CODE> gives the IPP attribute
 * name.
 * <P>
 *
 * <p>
 *  类Copies是一个整数值打印属性类,用于指定要打印的副本数。
 * <P>
 *  在许多设备上,所支持的整理副本的数量将受到设备上的物理输出存储箱的数量的限制,并且可能不同于可支持的未复制副本的数量。
 * <P>
 *  在多点打印作业(具有多个文档的作业)上的Copies属性值为<I> n </I>的效果取决于{@link MultipleDocumentHandling MultipleDocumentHandling}
 * 属性的(可能是默认值)值：。
 * <UL>
 * <LI>
 *  SINGLE_DOCUMENT  - 结果将是包含所有输入文档的单个输出文档的<I> n </I>个副本。
 * <P>
 * <LI>
 *  SINGLE_DOCUMENT_NEW_SHEET  - 结果将是包含所有输入文档的单个输出文档的<I> n </I>个副本,每个输入文档的第一印象将始终从新的媒体工作表开始。
 * <P>
 * <LI>
 *  SEPARATE_DOCUMENTS_UNCOLLATED_COPIES  - 结果将是第一个输入文档的<I> n </I>个副本,后面是第二个输入文档的<I> n </I>个副本。 。 。
 * 随后是最后一个输入文档的<I> n个副本。
 * <P>
 * <LI>
 *  SEPARATE_DOCUMENTS_COLLATED_COPIES  - 结果将是第一个输入文档,第二个输入文档。 。 。最后输入文档,该组文档被重复<I> n次。
 * </UL>
 * <P>
 * <B> IPP兼容性：</B>整数值给出IPP整数值。由<CODE> getName()</CODE>返回的类别名称给出了IPP属性名称。
 * <P>
 * 
 * 
 * @author  David Mendenhall
 * @author  Alan Kamihensky
 */
public final class Copies extends IntegerSyntax
        implements PrintRequestAttribute, PrintJobAttribute {

    private static final long serialVersionUID = -6426631521680023833L;

    /**
     * Construct a new copies attribute with the given integer value.
     *
     * <p>
     * 
     * @param  value  Integer value.
     *
     * @exception  IllegalArgumentException
     *  (Unchecked exception) Thrown if <CODE>value</CODE> is less than 1.
     */
    public Copies(int value) {
        super (value, 1, Integer.MAX_VALUE);
    }

    /**
     * Returns whether this copies attribute is equivalent to the passed in
     * object. To be equivalent, all of the following conditions must be true:
     * <OL TYPE=1>
     * <LI>
     * <CODE>object</CODE> is not null.
     * <LI>
     * <CODE>object</CODE> is an instance of class Copies.
     * <LI>
     * This copies attribute's value and <CODE>object</CODE>'s value are
     * equal.
     * </OL>
     *
     * <p>
     *  使用给定的整数值构造新的副本属性。
     * 
     * 
     * @param  object  Object to compare to.
     *
     * @return  True if <CODE>object</CODE> is equivalent to this copies
     *          attribute, false otherwise.
     */
    public boolean equals(Object object) {
        return super.equals (object) && object instanceof Copies;
    }

    /**
     * Get the printing attribute class which is to be used as the "category"
     * for this printing attribute value.
     * <P>
     * For class Copies, the category is class Copies itself.
     *
     * <p>
     *  返回此副本属性是否等同于传入的对象。为了等效,所有以下条件必须为真：
     * <OL TYPE=1>
     * <LI>
     *  <CODE>对象</CODE>不为空。
     * <LI>
     *  <CODE>对象</CODE>是类Copies的实例。
     * <LI>
     *  这个副本属性的值和<CODE>对象</CODE>的值相等。
     * </OL>
     * 
     * 
     * @return  Printing attribute class (category), an instance of class
     *          {@link java.lang.Class java.lang.Class}.
     */
    public final Class<? extends Attribute> getCategory() {
        return Copies.class;
    }

    /**
     * Get the name of the category of which this attribute value is an
     * instance.
     * <P>
     * For class Copies, the category name is <CODE>"copies"</CODE>.
     *
     * <p>
     * 
     * @return  Attribute category name.
     */
    public final String getName() {
        return "copies";
    }

}
