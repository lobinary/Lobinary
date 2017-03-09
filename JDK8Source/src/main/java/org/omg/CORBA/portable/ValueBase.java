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
/*
 * Licensed Materials - Property of IBM
 * RMI-IIOP v1.0
 * Copyright IBM Corp. 1998 1999  All Rights Reserved
 *
 * <p>
 *  许可的材料 -  IBM RMI-IIOP v1.0的属性版权所有IBM Corp. 1998 1999保留所有权利
 * 
 */

package org.omg.CORBA.portable;
/**
 * The generated Java classes corresponding to valuetype IDL types
 * implement this interface. In other words, the Java mapping of
 * valuetype objects implement the ValueBase interface. The generated
 * Java class for valuetype's shall provide an implementation of the
 * ValueBase interface for the corresponding value type.
 * For value types that are streamable (i.e. non-custom),
 * the generated Java class shall also provide an implementation
 * for the org.omg.CORBA.portable.Streamable interface.
 * (CORBA::ValueBase is mapped to java.io.Serializable.)
 * <p>
 *  与valuetype IDL类型对应的生成的Java类实现此接口。换句话说,valuetype对象的Java映射实现ValueBase接口。
 * 为valuetype生成的Java类将为相应的值类型提供ValueBase接口的实现。
 * 对于可流式(即非自定义)的值类型,生成的Java类也将为org.omg.CORBA.portable.Streamable接口提供实现。
 *  (CORBA :: ValueBase被映射到java.io.Serializable。)。
 */
public interface ValueBase extends IDLEntity {
    /**
     * Provides truncatable repository ids.
     * <p>
     * 
     * 
     * @return a String array--list of truncatable repository ids.
     */
    String[] _truncatable_ids();
}
