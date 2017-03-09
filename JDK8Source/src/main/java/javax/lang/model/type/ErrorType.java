/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2008, Oracle and/or its affiliates. All rights reserved.
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

package javax.lang.model.type;

/**
 * Represents a class or interface type that cannot be properly modeled.
 * This may be the result of a processing error,
 * such as a missing class file or erroneous source code.
 * Most queries for
 * information derived from such a type (such as its members or its
 * supertype) will not, in general, return meaningful results.
 *
 * <p>
 *  表示无法正确建模的类或接口类型。这可能是处理错误的结果,例如缺少类文件或错误的源代码。从这种类型(例如其成员或其超类型)派生的信息的大多数查询通常不会返回有意义的结果。
 * 
 * @author Joseph D. Darcy
 * @author Scott Seligman
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
public interface ErrorType extends DeclaredType {
}
