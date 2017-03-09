/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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


package com.sun.corba.se.spi.ior;


/** This interface represents an entity that can be written to an
 * OutputStream and has an identity that is represented by an integer.
 * This identity is essentially the type of the entity, and is used in
 * order to know how to read the entity back from an InputStream.
 * <p>
 *  OutputStream并且具有由整数表示的标识。这个标识本质上是实体的类型,并且用于知道如何从InputStream读回实体。
 * 
 * 
 * @author Ken Cavanaugh
 */
public interface Identifiable extends Writeable
{
    /** Return the (type) identity of this entity.
    /* <p>
    /* 
     * @return int
     */
    public int getId();
}
