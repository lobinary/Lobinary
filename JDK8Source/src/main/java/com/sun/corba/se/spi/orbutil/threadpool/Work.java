/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.spi.orbutil.threadpool;

public interface Work
{

    /**
     * This method denotes the actual work that is done by the work item.
     * <p>
     *  此方法表示由工作项完成的实际工作。
     * 
     */
    public void doWork();

    /**
     * This methods sets the time in millis in the work item, when this
     * work item was enqueued in the work queue.
     * <p>
     *  此方法设置工作项中的时间(以毫为单位),此工作项在工作队列中排队。
     * 
     */
    public void setEnqueueTime(long timeInMillis);

    /**
     * This methods gets the time in millis in the work item, when this
     * work item was enqueued in the work queue.
     * <p>
     *  当工作项在工作队列中排队时,此方法获取工作项中的时间(毫秒)。
     * 
     */
    public long getEnqueueTime();

    /**
    * This method will return the name of the work item.
    * <p>
    *  此方法将返回工作项的名称。
    */
    public String getName();

}

// End of file.
