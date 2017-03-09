/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2005, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.java_cup.internal.runtime;

/**
 * Defines the Scanner interface, which CUP uses in the default
 * implementation of <code>lr_parser.scan()</code>.  Integration
 * of scanners implementing <code>Scanner</code> is facilitated.
 *
 * <p>
 *  定义Scanner接口,CUP在<code> lr_parser.scan()</code>的默认实现中使用它。实现<code>扫描器</code>的扫描器的集成被促进。
 * 
 * 
 * @author David MacMahon <davidm@smartsc.com>
 */

/* *************************************************
  Interface Scanner

  Declares the next_token() method that should be
  implemented by scanners.  This method is typically
  called by lr_parser.scan().
/* <p>
/*  接口扫描器
/* 
/* 
 ***************************************************/
public interface Scanner {
    public Symbol next_token() throws java.lang.Exception;
}
