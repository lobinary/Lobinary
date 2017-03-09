/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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


package java.util.logging;

/**
 * A Filter can be used to provide fine grain control over
 * what is logged, beyond the control provided by log levels.
 * <p>
 * Each Logger and each Handler can have a filter associated with it.
 * The Logger or Handler will call the isLoggable method to check
 * if a given LogRecord should be published.  If isLoggable returns
 * false, the LogRecord will be discarded.
 *
 * <p>
 *  过滤器可以用于对记录的内容提供细粒度控制,超出日志级别提供的控制。
 * <p>
 *  每个记录器和每个处理器可以具有与其相关联的过滤器。 Logger或Handler将调用isLoggable方法来检查是否应该发布给定的LogRecord。
 * 如果isLoggable返回false,LogRecord将被丢弃。
 * 
 * @since 1.4
 */
@FunctionalInterface
public interface Filter {

    /**
     * Check if a given log record should be published.
     * <p>
     * 
     * 
     * @param record  a LogRecord
     * @return true if the log record should be published.
     */
    public boolean isLoggable(LogRecord record);
}
