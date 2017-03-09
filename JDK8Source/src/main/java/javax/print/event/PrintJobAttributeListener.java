/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2001, Oracle and/or its affiliates. All rights reserved.
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

package javax.print.event;

/**
  * Implementations of this interface are attached to a
  * {@link javax.print.DocPrintJob DocPrintJob} to monitor
  * the status of attribute changes associated with the print job.
  *
  * <p>
  *  此接口的实现附加到{@link javax.print.DocPrintJob DocPrintJob},以监视与打印作业相关联的属性更改的状态。
  * 
  */
public interface PrintJobAttributeListener {

    /**
     * Notifies the listener of a change in some print job attributes.
     * One example of an occurrence triggering this event is if the
     * {@link javax.print.attribute.standard.JobState JobState}
     * attribute changed from
     * <code>PROCESSING</code> to <code>PROCESSING_STOPPED</code>.
     * <p>
     *  通知侦听器某些打印作业属性的更改。
     * 触发此事件的事件的一个示例是如果{@link javax.print.attribute.standard.JobState JobState}属性从<code> PROCESSING </code>更
     * 改为<code> PROCESSING_STOPPED </code>。
     * 
     * @param pjae the event.
     */
    public void attributeUpdate(PrintJobAttributeEvent pjae) ;

}
