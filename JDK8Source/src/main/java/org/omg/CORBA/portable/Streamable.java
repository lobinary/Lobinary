/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 1999, Oracle and/or its affiliates. All rights reserved.
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
package org.omg.CORBA.portable;

import org.omg.CORBA.TypeCode;

/**
 * The base class for the Holder classess of all complex
 * IDL types. The ORB treats all generated Holders as Streamable to invoke
 * the methods for marshalling and unmarshalling.
 *
 * <p>
 *  所有复杂IDL类型的Holder类的基类。 ORB将所有生成的持有者视为Streamable,以调用编组和解组的方法。
 * 
 * 
 * @since   JDK1.2
 */

public interface Streamable {
    /**
     * Reads data from <code>istream</code> and initalizes the
     * <code>value</code> field of the Holder with the unmarshalled data.
     *
     * <p>
     *  从<code> istream </code>读取数据,并使用未组合的数据初始化Holder的<code>值</code>字段。
     * 
     * 
     * @param     istream   the InputStream that represents the CDR data from the wire.
     */
    void _read(InputStream istream);
    /**
     * Marshals to <code>ostream</code> the value in the
     * <code>value</code> field of the Holder.
     *
     * <p>
     *  在<code> ostream </code>字段中值为<code>值</code>。
     * 
     * 
     * @param     ostream   the CDR OutputStream
     */
    void _write(OutputStream ostream);

    /**
     * Retrieves the <code>TypeCode</code> object corresponding to the value
     * in the <code>value</code> field of the Holder.
     *
     * <p>
     *  检索与持有人的<code> value </code>字段中的值相对应的<code> TypeCode </code>对象。
     * 
     * @return    the <code>TypeCode</code> object for the value held in the holder
     */
    TypeCode _type();
}
