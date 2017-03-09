/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, Oracle and/or its affiliates. All rights reserved.
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


package javax.naming.directory;

import javax.naming.NamingException;

/**
  * This exception is thrown when a method
  * in some ways violates the schema. An example of schema violation
  * is modifying attributes of an object that violates the object's
  * schema definition. Another example is renaming or moving an object
  * to a part of the namespace that violates the namespace's
  * schema definition.
  * <p>
  * Synchronization and serialization issues that apply to NamingException
  * apply directly here.
  *
  * <p>
  *  当方法在某些方面违反模式时抛出此异常。模式冲突的一个示例是修改违反对象的模式定义的对象的属性。另一个例子是将对象重命名或移动到违反命名空间的模式定义的命名空间的一部分。
  * <p>
  *  适用于NamingException的同步和序列化问题直接应用于此处。
  * 
  * 
  * @author Rosanna Lee
  * @author Scott Seligman
  *
  * @see javax.naming.Context#bind
  * @see DirContext#bind
  * @see javax.naming.Context#rebind
  * @see DirContext#rebind
  * @see DirContext#createSubcontext
  * @see javax.naming.Context#createSubcontext
  * @see DirContext#modifyAttributes
  * @since 1.3
  */
public class SchemaViolationException extends NamingException {
    /**
     * Constructs a new instance of SchemaViolationException.
     * All fields are set to null.
     * <p>
     *  构造一个SchemaViolationException的新实例。所有字段都设置为null。
     * 
     */
    public SchemaViolationException() {
        super();
    }

    /**
     * Constructs a new instance of SchemaViolationException
     * using the explanation supplied. All other fields are set to null.
     * <p>
     *  使用提供的解释构造一个SchemaViolationException的新实例。所有其他字段都设置为null。
     * 
     * 
     * @param explanation Detail about this exception. Can be null.
     * @see java.lang.Throwable#getMessage
     */
    public SchemaViolationException(String explanation) {
        super(explanation);
    }

    /**
     * Use serialVersionUID from JNDI 1.1.1 for interoperability
     * <p>
     *  从JNDI 1.1.1使用serialVersionUID以实现互操作性
     */
    private static final long serialVersionUID = -3041762429525049663L;
}
