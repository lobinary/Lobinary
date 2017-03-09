/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
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
package com.sun.source.doctree;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * A tree node to stand in for a malformed text
 *
 * <p>
 *  一个树节点,用于替换格式错误的文本
 * 
 * 
 * @since 1.8
 */
@jdk.Exported
public interface ErroneousTree extends TextTree {
    /**
     * Gets a diagnostic object giving details about
     * the reason the body text is in error.
     *
     * <p>
     *  获取一个诊断对象,提供正文文本有错误的原因的详细信息。
     * 
     * @return a diagnostic
     */
    Diagnostic<JavaFileObject> getDiagnostic();
}
