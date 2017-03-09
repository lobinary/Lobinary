/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2004, Oracle and/or its affiliates. All rights reserved.
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

package java.rmi.server;

/**
 * An <code>Operation</code> contains a description of a Java method.
 * <code>Operation</code> objects were used in JDK1.1 version stubs and
 * skeletons. The <code>Operation</code> class is not needed for 1.2 style
 * stubs (stubs generated with <code>rmic -v1.2</code>); hence, this class
 * is deprecated.
 *
 * <p>
 *  <code>操作</code>包含Java方法的描述。 <code>操作</code>对象在JDK1.1版本存根和骨架中使用。
 *  1.2样式存根(使用<code> rmic -v1.2 </code>生成的存根)不需要<code> Operation </code>因此,此类已被弃用。
 * 
 * 
 * @since JDK1.1
 * @deprecated no replacement
 */
@Deprecated
public class Operation {
    private String operation;

    /**
     * Creates a new Operation object.
     * <p>
     *  创建一个新的操作对象。
     * 
     * 
     * @param op method name
     * @deprecated no replacement
     * @since JDK1.1
     */
    @Deprecated
    public Operation(String op) {
        operation = op;
    }

    /**
     * Returns the name of the method.
     * <p>
     *  返回方法的名称。
     * 
     * 
     * @return method name
     * @deprecated no replacement
     * @since JDK1.1
     */
    @Deprecated
    public String getOperation() {
        return operation;
    }

    /**
     * Returns the string representation of the operation.
     * <p>
     *  返回操作的字符串表示形式。
     * 
     * @deprecated no replacement
     * @since JDK1.1
     */
    @Deprecated
    public String toString() {
        return operation;
    }
}
