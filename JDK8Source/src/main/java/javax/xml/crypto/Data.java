/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, Oracle and/or its affiliates. All rights reserved.
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
/*
 * $Id: Data.java,v 1.4 2005/05/10 15:47:41 mullan Exp $
 * <p>
 *  $ Id：Data.java,v 1.4 2005/05/10 15:47:41 mullan Exp $
 * 
 */
package javax.xml.crypto;

import javax.xml.crypto.dsig.Transform;

/**
 * An abstract representation of the result of dereferencing a
 * {@link URIReference} or the input/output of subsequent {@link Transform}s.
 * The primary purpose of this interface is to group and provide type safety
 * for all <code>Data</code> subtypes.
 *
 * <p>
 *  取消引用{@link URIReference}或后续{@link Transform}的输入/输出的结果的抽象表示。
 * 此接口的主要目的是为所有<code> Data </code>子类型分组并提供类型安全性。
 * 
 * @author Sean Mullan
 * @author JSR 105 Expert Group
 * @since 1.6
 */
public interface Data { }
