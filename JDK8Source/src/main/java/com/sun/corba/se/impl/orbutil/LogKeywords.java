/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2002, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.corba.se.impl.orbutil;
/**
 * All the Keywords that will be used in Logging Messages for CORBA need to
 * be defined here. The LogKeywords will be useful for searching log messages
 * based on the standard keywords, it is also useful to work with LogAnalyzing
 * tools.
 * We will try to standardize these keywords in JSR 117 Logging
 * <p>
 *  将在此处定义将用于CORBA的日志消息中的所有关键字。 LogKeywords将有助于根据标准关键字搜索日志消息,使用LogAnalyzing工具也很有用。
 * 我们将尝试在JSR 117日志记录中标准化这些关键字。
 * 
 */
public class LogKeywords {

    /**
     ** Keywords for Lifecycle Loggers.
     ** _REVISIT_ After it is clearly defined in JSR 117
     * <p>
     *  生命周期记录器的关键字。 _REVISIT_在JSR 117中明确定义之后
     * 
     * 
     **/
    public final static String LIFECYCLE_CREATE     = "<<LIFECYCLE CREATE>>";
    public final static String LIFECYCLE_INITIALIZE = "<<LIFECYCLE INITIALIZE>>";
    public final static String LIFECYCLE_SHUTDOWN   = "<<LIFECYCLE SHUTDOWN>>";
    public final static String LIFECYCLE_DESTROY    = "<<LIFECYCLE DESTROY>>";


    public final static String LIFECYCLE_CREATE_SUCCESS =
        LIFECYCLE_CREATE + "<<SUCCESS>>";
    public final static String LIFECYCLE_CREATE_FAILURE =
        LIFECYCLE_CREATE + "<<FAILURE>>";
    public final static String LIFECYCLE_INITIALIZE_SUCCESS =
        LIFECYCLE_INITIALIZE + "<<SUCCESS>>";
    public final static String LIFECYCLE_INITIALIZE_FAILURE =
        LIFECYCLE_INITIALIZE + "<<FAILURE>>";
    public final static String LIFECYCLE_SHUTDOWN_SUCCESS =
        LIFECYCLE_SHUTDOWN + "<<SUCCESS>>";
    public final static String LIFECYCLE_SHUTDOWN_FAILURE =
        LIFECYCLE_SHUTDOWN + "<<FAILURE>>";
    public final static String LIFECYCLE_DESTROY_SUCCESS =
        LIFECYCLE_DESTROY + "<<SUCCESS>>";
    public final static String LIFECYCLE_DESTROY_FAILURE =
        LIFECYCLE_DESTROY + "<<FAILURE>>";

    /**
     ** Keywords for Naming Read Loggers.
     * <p>
     *  命名的关键字读记录器。
     * 
     * 
     **/
    public final static String NAMING_RESOLVE       = "<<NAMING RESOLVE>>";
    public final static String NAMING_LIST          = "<<NAMING LIST>>";

    public final static String NAMING_RESOLVE_SUCCESS =
        NAMING_RESOLVE + "<<SUCCESS>>";
    public final static String NAMING_RESOLVE_FAILURE =
        NAMING_RESOLVE + "<<FAILURE>>";
    public final static String NAMING_LIST_SUCCESS =
        NAMING_LIST + "<<SUCCESS>>";
    public final static String NAMING_LIST_FAILURE =
        NAMING_LIST + "<<FAILURE>>";

    /**
     ** Keywords for Naming Update Loggers.
     * <p>
     *  命名更新记录器的关键字。
     * 
     **/
    public final static String NAMING_BIND          = "<<NAMING BIND>>";
    public final static String NAMING_UNBIND        = "<<NAMING UNBIND>>";
    public final static String NAMING_REBIND        = "<<NAMING REBIND>>";

    public final static String NAMING_BIND_SUCCESS =
        NAMING_BIND + "<<SUCCESS>>";
    public final static String NAMING_BIND_FAILURE =
        NAMING_BIND + "<<FAILURE>>";
    public final static String NAMING_UNBIND_SUCCESS =
        NAMING_UNBIND + "<<SUCCESS>>";
    public final static String NAMING_UNBIND_FAILURE =
        NAMING_UNBIND + "<<FAILURE>>";
    public final static String NAMING_REBIND_SUCCESS =
        NAMING_REBIND + "<<SUCCESS>>";
    public final static String NAMING_REBIND_FAILURE =
        NAMING_REBIND + "<<FAILURE>>";
}
