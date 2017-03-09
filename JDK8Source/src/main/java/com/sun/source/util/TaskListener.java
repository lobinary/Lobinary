/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.source.util;


/**
 * Provides a listener to monitor the activity of the JDK Java Compiler, javac.
 *
 * <p>
 *  提供一个监听器来监视JDK Java Compiler,javac的活动。
 * 
 * @author Jonathan Gibbons
 * @since 1.6
 */
@jdk.Exported
public interface TaskListener
{
    public void started(TaskEvent e);

    public void finished(TaskEvent e);
}
