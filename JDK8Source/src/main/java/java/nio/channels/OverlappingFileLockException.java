/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2007, Oracle and/or its affiliates. All rights reserved.
 *
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
 *
 */

// -- This file was mechanically generated: Do not edit! -- //

package java.nio.channels;


/**
 * Unchecked exception thrown when an attempt is made to acquire a lock on a
 * region of a file that overlaps a region already locked by the same Java
 * virtual machine, or when another thread is already waiting to lock an
 * overlapping region of the same file.
 *
 * <p>
 *  当尝试获取与已由同一Java虚拟机锁定的区域重叠的文件区域上的锁定时,或者当另一个线程已在等待锁定同一文件的重叠区域时,抛出未经检查的异常。
 * 
 * 
 * @since 1.4
 */

public class OverlappingFileLockException
    extends IllegalStateException
{

    private static final long serialVersionUID = 2047812138163068433L;

    /**
     * Constructs an instance of this class.
     * <p>
     *  构造此类的实例。
     */
    public OverlappingFileLockException() { }

}
