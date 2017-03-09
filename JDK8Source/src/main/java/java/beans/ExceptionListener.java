/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, Oracle and/or its affiliates. All rights reserved.
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
package java.beans;

/**
 * An ExceptionListener is notified of internal exceptions.
 *
 * <p>
 *  将向内部异常通知ExceptionListener。
 * 
 * 
 * @since 1.4
 *
 * @author Philip Milne
 */
public interface ExceptionListener {
    /**
     * This method is called when a recoverable exception has
     * been caught.
     *
     * <p>
     *  当捕获到可恢复异常时调用此方法。
     * 
     * @param e The exception that was caught.
     *
     */
    public void exceptionThrown(Exception e);
}
