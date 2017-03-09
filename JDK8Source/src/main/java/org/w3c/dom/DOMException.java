/***** Lobxxx Translate Finished ******/
/*
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

/*
 *
 *
 *
 *
 *
 * Copyright (c) 2004 World Wide Web Consortium,
 *
 * (Massachusetts Institute of Technology, European Research Consortium for
 * Informatics and Mathematics, Keio University). All Rights Reserved. This
 * work is distributed under the W3C(r) Software License [1] in the hope that
 * it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 *
 * [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 * <p>
 *  版权所有(c)2004万维网联盟,
 * 
 *  (马萨诸塞理工学院,欧洲研究联合会信息学和数学,庆应大学)。版权所有。这项工作是根据W3C(r)软件许可证[1]分发的,希望它将是有用的,但没有任何保证;甚至没有对适销性或适用于特定用途的隐含保证。
 * 
 *  [1] http://www.w3.org/Consortium/Legal/2002/copyright-software-20021231
 * 
 */

package org.w3c.dom;

/**
 * DOM operations only raise exceptions in "exceptional" circumstances, i.e.,
 * when an operation is impossible to perform (either for logical reasons,
 * because data is lost, or because the implementation has become unstable).
 * In general, DOM methods return specific error values in ordinary
 * processing situations, such as out-of-bound errors when using
 * <code>NodeList</code>.
 * <p>Implementations should raise other exceptions under other circumstances.
 * For example, implementations should raise an implementation-dependent
 * exception if a <code>null</code> argument is passed when <code>null</code>
 *  was not expected.
 * <p>Some languages and object systems do not support the concept of
 * exceptions. For such systems, error conditions may be indicated using
 * native error reporting mechanisms. For some bindings, for example,
 * methods may return error codes similar to those listed in the
 * corresponding method descriptions.
 * <p>See also the <a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>Document Object Model (DOM) Level 3 Core Specification</a>.
 * <p>
 * DOM操作仅在"异常"情况下,即当操作不可能执行时(由于逻辑原因,因为数据丢失或者因为实现已变得不稳定)而引发异常。
 * 一般来说,DOM方法在普通处理情况下返回特定的错误值,例如使用<code> NodeList </code>时的超出错误值。 <p>在其他情况下,实施应引发其他例外。
 * 例如,如果不期望<code> null </code>时传递<code> null </code>参数,实现应该引发与实现相关的异常。 <p>某些语言和对象系统不支持异常的概念。
 * 对于这样的系统,可以使用本机错误报告机制来指示错误状况。对于某些绑定,例如,方法可能返回类似于相应方法描述中列出的错误代码。
 *  <p>另请参阅<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Core-20040407'>文档对象模型(DOM)3级核心规范< a>。
 * 
 */
public class DOMException extends RuntimeException {
    public DOMException(short code, String message) {
       super(message);
       this.code = code;
    }
    public short   code;
    // ExceptionCode
    /**
     * If index or size is negative, or greater than the allowed value.
     * <p>
     *  如果索引或大小为负值,或大于允许值。
     * 
     */
    public static final short INDEX_SIZE_ERR            = 1;
    /**
     * If the specified range of text does not fit into a
     * <code>DOMString</code>.
     * <p>
     *  如果指定的文本范围不适合<code> DOMString </code>。
     * 
     */
    public static final short DOMSTRING_SIZE_ERR        = 2;
    /**
     * If any <code>Node</code> is inserted somewhere it doesn't belong.
     * <p>
     *  如果任何<code> Node </code>被插入它不属于的地方。
     * 
     */
    public static final short HIERARCHY_REQUEST_ERR     = 3;
    /**
     * If a <code>Node</code> is used in a different document than the one
     * that created it (that doesn't support it).
     * <p>
     *  如果<code> Node </code>在与创建它的文档不同的文档中使用(不支持它)。
     * 
     */
    public static final short WRONG_DOCUMENT_ERR        = 4;
    /**
     * If an invalid or illegal character is specified, such as in an XML name.
     * <p>
     *  如果指定了无效或非法字符,例如在XML名称中。
     * 
     */
    public static final short INVALID_CHARACTER_ERR     = 5;
    /**
     * If data is specified for a <code>Node</code> which does not support
     * data.
     * <p>
     * 如果为不支持数据的<code> Node </code>指定数据。
     * 
     */
    public static final short NO_DATA_ALLOWED_ERR       = 6;
    /**
     * If an attempt is made to modify an object where modifications are not
     * allowed.
     * <p>
     *  如果尝试修改不允许修改的对象。
     * 
     */
    public static final short NO_MODIFICATION_ALLOWED_ERR = 7;
    /**
     * If an attempt is made to reference a <code>Node</code> in a context
     * where it does not exist.
     * <p>
     *  如果尝试在其不存在的上下文中引用<code> Node </code>。
     * 
     */
    public static final short NOT_FOUND_ERR             = 8;
    /**
     * If the implementation does not support the requested type of object or
     * operation.
     * <p>
     *  如果实现不支持请求类型的对象或操作。
     * 
     */
    public static final short NOT_SUPPORTED_ERR         = 9;
    /**
     * If an attempt is made to add an attribute that is already in use
     * elsewhere.
     * <p>
     *  如果尝试添加已在其他位置使用的属性。
     * 
     */
    public static final short INUSE_ATTRIBUTE_ERR       = 10;
    /**
     * If an attempt is made to use an object that is not, or is no longer,
     * usable.
     * <p>
     *  如果尝试使用不是或不再可用的对象。
     * 
     * 
     * @since DOM Level 2
     */
    public static final short INVALID_STATE_ERR         = 11;
    /**
     * If an invalid or illegal string is specified.
     * <p>
     *  如果指定了无效或非法的字符串。
     * 
     * 
     * @since DOM Level 2
     */
    public static final short SYNTAX_ERR                = 12;
    /**
     * If an attempt is made to modify the type of the underlying object.
     * <p>
     *  如果尝试修改基础对象的类型。
     * 
     * 
     * @since DOM Level 2
     */
    public static final short INVALID_MODIFICATION_ERR  = 13;
    /**
     * If an attempt is made to create or change an object in a way which is
     * incorrect with regard to namespaces.
     * <p>
     *  如果尝试以关于命名空间不正确的方式创建或更改对象。
     * 
     * 
     * @since DOM Level 2
     */
    public static final short NAMESPACE_ERR             = 14;
    /**
     * If a parameter or an operation is not supported by the underlying
     * object.
     * <p>
     *  如果底层对象不支持参数或操作。
     * 
     * 
     * @since DOM Level 2
     */
    public static final short INVALID_ACCESS_ERR        = 15;
    /**
     * If a call to a method such as <code>insertBefore</code> or
     * <code>removeChild</code> would make the <code>Node</code> invalid
     * with respect to "partial validity", this exception would be raised
     * and the operation would not be done. This code is used in [<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Val-20040127/'>DOM Level 3 Validation</a>]
     * . Refer to this specification for further information.
     * <p>
     *  如果调用诸如<code> insertBefore </code>或<code> removeChild </code>之类的方法将使得<code> Node </code>相对于"部分有效性"无效,
     * 并且不会进行操作。
     * 此代码用于[<a href='http://www.w3.org/TR/2004/REC-DOM-Level-3-Val-20040127/'> DOM 3级验证</a>]。
     * 有关详细信息,请参阅本规范。
     * 
     * @since DOM Level 3
     */
    public static final short VALIDATION_ERR            = 16;
    /**
     *  If the type of an object is incompatible with the expected type of the
     * parameter associated to the object.
     * <p>
     * 
     * 
     * @since DOM Level 3
     */
    public static final short TYPE_MISMATCH_ERR         = 17;

    // Added serialVersionUID to preserve binary compatibility
    static final long serialVersionUID = 6627732366795969916L;
}
