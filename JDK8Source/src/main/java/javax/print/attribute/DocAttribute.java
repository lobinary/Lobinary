/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, Oracle and/or its affiliates. All rights reserved.
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


package javax.print.attribute;

/**
 * Interface DocAttribute is a tagging interface which a printing attribute
 * class implements to indicate the attribute denotes a setting for a doc.
 * ("Doc" is a short, easy-to-pronounce term that means "a piece of print
 * data.") The client may include a DocAttribute in a <code>Doc</code>'s
 * attribute set to specify a characteristic of
 * that doc. If an attribute implements {@link PrintRequestAttribute
 * PrintRequestAttribute} as well as DocAttribute, the client may include the
 * attribute in a attribute set which specifies a print job
 * to specify a characteristic for all the docs in that job.
 * <P>
 *
 * <p>
 *  接口DocAttribute是打印属性类实现的标记接口,用于指示属性表示文档的设置。 ("Doc"是一个简短的易于发音的术语,意思是"一条打印数据"。
 * )客户端可以在<code> Doc </code>的属性中包括DocAttribute,那个文档。
 * 如果属性实现{@link PrintRequestAttribute PrintRequestAttribute}以及DocAttribute,则客户端可以在属性集中包括属性,该属性集指定打印作业以指定
 * 该作业中的所有文档的特性。
 * 
 * @see DocAttributeSet
 * @see PrintRequestAttributeSet
 *
 * @author  Alan Kaminsky
 */
public interface DocAttribute extends Attribute {

}
