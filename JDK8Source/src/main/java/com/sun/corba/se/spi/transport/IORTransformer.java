/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.transport ;

import com.sun.corba.se.spi.ior.IOR ;
import com.sun.corba.se.spi.encoding.CorbaInputObject ;
import com.sun.corba.se.spi.encoding.CorbaOutputObject ;

/** Interface that provides operations to transorm an IOR
 * between its programmatic representation and a representation
 * in an Input or Output object.
 * <p>
 *  在其程序表示和输入或输出对象中的表示之间。
 */
public interface IORTransformer {
    IOR unmarshal( CorbaInputObject io ) ;

    void marshal( CorbaOutputObject oo, IOR ior ) ;
}
