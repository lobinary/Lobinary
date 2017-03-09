/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 1999, Oracle and/or its affiliates. All rights reserved.
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

/**
This class is used for reporting application level exceptions between ORBs and stubs.
/* <p>
/*  此类用于报告ORB和存根之间的应用程序级别异常。
/* 
*/

public class ApplicationException extends Exception {
    /**
     * Constructs an ApplicationException from the CORBA repository ID of the exception
     * and an input stream from which the exception data can be read as its parameters.
     * <p>
     *  从异常的CORBA库ID构造一个ApplicationException,并从中读取异常数据作为其参数的输入流。
     * 
     * 
     * @param id the repository id of the user exception
     * @param ins the stream which contains the user exception data
     */
    public ApplicationException(String id,
                                InputStream ins) {
        this.id = id;
        this.ins = ins;
    }

    /**
     * Returns the CORBA repository ID of the exception
     * without removing it from the exceptions input stream.
     * <p>
     *  返回异常的CORBA库ID,而不将其从异常输入流中删除。
     * 
     * 
     * @return The CORBA repository ID of this exception
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the input stream from which the exception data can be read as its parameters.
     * <p>
     *  返回可以读取异常数据作为其参数的输入流。
     * 
     * @return The stream which contains the user exception data
     */
    public InputStream getInputStream() {
        return ins;
    }

    private String id;
    private InputStream ins;
}
