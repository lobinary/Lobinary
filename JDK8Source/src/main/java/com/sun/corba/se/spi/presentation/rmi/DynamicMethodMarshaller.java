/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2004, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.presentation.rmi ;

import org.omg.CORBA_2_3.portable.InputStream ;
import org.omg.CORBA_2_3.portable.OutputStream ;
import org.omg.CORBA.portable.ApplicationException ;

import java.lang.reflect.Method ;

import java.rmi.RemoteException ;

import com.sun.corba.se.spi.orb.ORB ;

/** Used to read and write arguments and results for a particular method.
*
* <p>
*/
public interface DynamicMethodMarshaller
{
    /** Returns the method used to create this DynamicMethodMarshaller.
    /* <p>
     */
    Method getMethod() ;

    /** Copy the arguments as needed for this particular method.
     * Can be optimized so that as little copying as possible is
     * performed.
     * <p>
     *  可以优化,以便尽可能少的复制执行。
     * 
     */
    Object[] copyArguments( Object[] args, ORB orb ) throws RemoteException ;

    /** Read the arguments for this method from the InputStream.
     * Returns null if there are no arguments.
     * <p>
     *  如果没有参数,则返回null。
     * 
     */
    Object[] readArguments( InputStream is ) ;

    /** Write arguments for this method to the OutputStream.
     * Does nothing if there are no arguments.
     * <p>
     *  如果没有参数,什么都不做。
     * 
     */
    void writeArguments( OutputStream os, Object[] args ) ;

    /** Copy the result as needed for this particular method.
     * Can be optimized so that as little copying as possible is
     * performed.
     * <p>
     *  可以优化,以便尽可能少的复制执行。
     * 
     */
    Object copyResult( Object result, ORB orb ) throws RemoteException ;

    /** Read the result from the InputStream.  Returns null
     * if the result type is null.
     * <p>
     *  如果结果类型为null。
     * 
     */
    Object readResult( InputStream is ) ;

    /** Write the result to the OutputStream.  Does nothing if
     * the result type is null.
     * <p>
     *  结果类型为null。
     * 
     */
    void writeResult( OutputStream os, Object result ) ;

    /** Returns true iff thr's class is a declared exception (or a subclass of
     * a declared exception) for this DynamicMethodMarshaller's method.
     * <p>
     *  一个声明的异常)为这个DynamicMethodMarshaller的方法。
     * 
     */
    boolean isDeclaredException( Throwable thr ) ;

    /** Write the repository ID of the exception and the value of the
     * exception to the OutputStream.  ex should be a declared exception
     * for this DynamicMethodMarshaller's method.
     * <p>
     *  异常到OutputStream。 ex应该是此DynamicMethodMarshaller方法的声明异常。
     * 
     */
    void writeException( OutputStream os, Exception ex ) ;

    /** Reads an exception ID and the corresponding exception from
     * the input stream.  This should be an exception declared in
     * this method.
     * <p>
     *  输入流。这应该是在此方法中声明的异常。
     */
    Exception readException( ApplicationException ae ) ;
}
