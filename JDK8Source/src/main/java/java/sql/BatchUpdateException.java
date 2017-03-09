/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.sql;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/**
 * The subclass of {@link SQLException} thrown when an error
 * occurs during a batch update operation.  In addition to the
 * information provided by {@link SQLException}, a
 * <code>BatchUpdateException</code> provides the update
 * counts for all commands that were executed successfully during the
 * batch update, that is, all commands that were executed before the error
 * occurred.  The order of elements in an array of update counts
 * corresponds to the order in which commands were added to the batch.
 * <P>
 * After a command in a batch update fails to execute properly
 * and a <code>BatchUpdateException</code> is thrown, the driver
 * may or may not continue to process the remaining commands in
 * the batch.  If the driver continues processing after a failure,
 * the array returned by the method
 * <code>BatchUpdateException.getUpdateCounts</code> will have
 * an element for every command in the batch rather than only
 * elements for the commands that executed successfully before
 * the error.  In the case where the driver continues processing
 * commands, the array element for any command
 * that failed is <code>Statement.EXECUTE_FAILED</code>.
 * <P>
 * A JDBC driver implementation should use
 * the constructor {@code BatchUpdateException(String reason, String SQLState,
 * int vendorCode, long []updateCounts,Throwable cause) } instead of
 * constructors that take {@code int[]} for the update counts to avoid the
 * possibility of overflow.
 * <p>
 * If {@code Statement.executeLargeBatch} method is invoked it is recommended that
 * {@code getLargeUpdateCounts} be called instead of {@code getUpdateCounts}
 * in order to avoid a possible overflow of the integer update count.
 * <p>
 *  在批量更新操作期间发生错误时抛出的{@link SQLException}的子类。
 * 除了{@link SQLException}提供的信息之外,<code> BatchUpdateException </code>还提供在批量更新期间成功执行的所有命令的更新计数,即在发生错误之前执行的
 * 所有命令。
 *  在批量更新操作期间发生错误时抛出的{@link SQLException}的子类。更新计数数组中元素的顺序对应于将命令添加到批处理中的顺序。
 * <P>
 *  批处理更新中的命令无法正确执行并且抛出<code> BatchUpdateException </code>后,驱动程序可能也可能不会继续处理批处理中的剩余命令。
 * 如果驱动程序在故障后继续处理,则由方法<code> BatchUpdateException.getUpdateCounts </code>返回的数组将对批处理中的每个命令都有一个元素,而不是在错误之前
 * 成功执行的命令的元素。
 *  批处理更新中的命令无法正确执行并且抛出<code> BatchUpdateException </code>后,驱动程序可能也可能不会继续处理批处理中的剩余命令。
 * 在驱动程序继续处理命令的情况下,任何失败的命令的数组元素是<code> Statement.EXECUTE_FAILED </code>。
 * <P>
 * JDBC驱动程序实现应该使用构造函数{@code BatchUpdateException(String reason,String SQLState,int vendorCode,long [] updateCounts,Throwable cause)}
 * ,而不是使用{@code int []}溢出的可能性。
 * <p>
 *  如果调用{@code Statement.executeLargeBatch}方法,建议调用{@code getLargeUpdateCounts}而不是{@code getUpdateCounts}
 * ,以避免整数更新计数的可能溢出。
 * 
 * 
 * @since 1.2
 */

public class BatchUpdateException extends SQLException {

  /**
   * Constructs a <code>BatchUpdateException</code> object initialized with a given
   * <code>reason</code>, <code>SQLState</code>, <code>vendorCode</code> and
   * <code>updateCounts</code>.
   * The <code>cause</code> is not initialized, and may subsequently be
   * initialized by a call to the
   * {@link Throwable#initCause(java.lang.Throwable)} method.
   * <p>
   * <strong>Note:</strong> There is no validation of {@code updateCounts} for
   * overflow and because of this it is recommended that you use the constructor
   * {@code BatchUpdateException(String reason, String SQLState,
   * int vendorCode, long []updateCounts,Throwable cause) }.
   * </p>
   * <p>
   *  构造使用给定<code>原因</code>,<code> SQLState </code>,<code> vendorCode </code>和<code> updateCounts </code>初
   * 始化的<code> BatchUpdateException < 。
   *  <code> cause </code>没有被初始化,并且随后可以通过调用{@link Throwable#initCause(java.lang.Throwable)}方法来初始化。
   * <p>
   *  <strong>注意：</strong>没有验证{@code updateCounts}溢出,因此建议您使用构造函数{@code BatchUpdateException(String reason,String SQLState,int vendorCode,long [] updateCounts,Throwable cause)}
   * 。
   * </p>
   * 
   * @param reason a description of the error
   * @param SQLState an XOPEN or SQL:2003 code identifying the exception
   * @param vendorCode an exception code used by a particular
   * database vendor
   * @param updateCounts an array of <code>int</code>, with each element
   * indicating the update count, <code>Statement.SUCCESS_NO_INFO</code> or
   * <code>Statement.EXECUTE_FAILED</code> for each SQL command in
   * the batch for JDBC drivers that continue processing
   * after a command failure; an update count or
   * <code>Statement.SUCCESS_NO_INFO</code> for each SQL command in the batch
   * prior to the failure for JDBC drivers that stop processing after a command
   * failure
   * @since 1.2
   * @see #BatchUpdateException(java.lang.String, java.lang.String, int, long[],
   * java.lang.Throwable)
   */
  public BatchUpdateException( String reason, String SQLState, int vendorCode,
                               int[] updateCounts ) {
      super(reason, SQLState, vendorCode);
      this.updateCounts  = (updateCounts == null) ? null : Arrays.copyOf(updateCounts, updateCounts.length);
      this.longUpdateCounts = (updateCounts == null) ? null : copyUpdateCount(updateCounts);
  }

  /**
   * Constructs a <code>BatchUpdateException</code> object initialized with a given
   * <code>reason</code>, <code>SQLState</code> and
   * <code>updateCounts</code>.
   * The <code>cause</code> is not initialized, and may subsequently be
   * initialized by a call to the
   * {@link Throwable#initCause(java.lang.Throwable)} method. The vendor code
   * is initialized to 0.
   * <p>
   * <strong>Note:</strong> There is no validation of {@code updateCounts} for
   * overflow and because of this it is recommended that you use the constructor
   * {@code BatchUpdateException(String reason, String SQLState,
   * int vendorCode, long []updateCounts,Throwable cause) }.
   * </p>
   * <p>
   *  构造使用给定的<code>原因</code>,<code> SQLState </code>和<code> updateCounts </code>初始化的<code> BatchUpdateExce
   * ption </code>对象。
   *  <code> cause </code>没有被初始化,并且随后可以通过调用{@link Throwable#initCause(java.lang.Throwable)}方法来初始化。
   * 供应商代码初始化为0。
   * <p>
   * <strong>注意：</strong>没有验证{@code updateCounts}溢出,因此建议您使用构造函数{@code BatchUpdateException(String reason,String SQLState,int vendorCode,long [] updateCounts,Throwable cause)}
   * 。
   * </p>
   * 
   * @param reason a description of the exception
   * @param SQLState an XOPEN or SQL:2003 code identifying the exception
   * @param updateCounts an array of <code>int</code>, with each element
   * indicating the update count, <code>Statement.SUCCESS_NO_INFO</code> or
   * <code>Statement.EXECUTE_FAILED</code> for each SQL command in
   * the batch for JDBC drivers that continue processing
   * after a command failure; an update count or
   * <code>Statement.SUCCESS_NO_INFO</code> for each SQL command in the batch
   * prior to the failure for JDBC drivers that stop processing after a command
   * failure
   * @since 1.2
   * @see #BatchUpdateException(java.lang.String, java.lang.String, int, long[],
   * java.lang.Throwable)
   */
  public BatchUpdateException(String reason, String SQLState,
                              int[] updateCounts) {
      this(reason, SQLState, 0, updateCounts);
  }

  /**
   * Constructs a <code>BatchUpdateException</code> object initialized with a given
   * <code>reason</code> and <code>updateCounts</code>.
   * The <code>cause</code> is not initialized, and may subsequently be
   * initialized by a call to the
   * {@link Throwable#initCause(java.lang.Throwable)} method.  The
   * <code>SQLState</code> is initialized to <code>null</code>
   * and the vendor code is initialized to 0.
   * <p>
   * <strong>Note:</strong> There is no validation of {@code updateCounts} for
   * overflow and because of this it is recommended that you use the constructor
   * {@code BatchUpdateException(String reason, String SQLState,
   * int vendorCode, long []updateCounts,Throwable cause) }.
   * </p>
   * <p>
   *  构造使用给定的<code> reason </code>和<code> updateCounts </code>初始化的<code> BatchUpdateException </code>对象。
   *  <code> cause </code>没有被初始化,并且随后可以通过调用{@link Throwable#initCause(java.lang.Throwable)}方法来初始化。
   *  <code> SQLState </code>初始化为<code> null </code>,供应商代码初始化为0。
   * <p>
   *  <strong>注意：</strong>没有验证{@code updateCounts}溢出,因此建议您使用构造函数{@code BatchUpdateException(String reason,String SQLState,int vendorCode,long [] updateCounts,Throwable cause)}
   * 。
   * </p>
   * 
   * @param reason a description of the exception
   * @param updateCounts an array of <code>int</code>, with each element
   * indicating the update count, <code>Statement.SUCCESS_NO_INFO</code> or
   * <code>Statement.EXECUTE_FAILED</code> for each SQL command in
   * the batch for JDBC drivers that continue processing
   * after a command failure; an update count or
   * <code>Statement.SUCCESS_NO_INFO</code> for each SQL command in the batch
   * prior to the failure for JDBC drivers that stop processing after a command
   * failure
   * @since 1.2
   * @see #BatchUpdateException(java.lang.String, java.lang.String, int, long[],
   * java.lang.Throwable)
   */
  public  BatchUpdateException(String reason, int[] updateCounts) {
      this(reason, null, 0, updateCounts);
  }

  /**
   * Constructs a <code>BatchUpdateException</code> object initialized with a given
   * <code>updateCounts</code>.
   * initialized by a call to the
   * {@link Throwable#initCause(java.lang.Throwable)} method. The  <code>reason</code>
   * and <code>SQLState</code> are initialized to null and the vendor code
   * is initialized to 0.
   * <p>
   * <strong>Note:</strong> There is no validation of {@code updateCounts} for
   * overflow and because of this it is recommended that you use the constructor
   * {@code BatchUpdateException(String reason, String SQLState,
   * int vendorCode, long []updateCounts,Throwable cause) }.
   * </p>
   * <p>
   *  构造使用给定的<code> updateCounts </code>初始化的<code> BatchUpdateException </code>对象。
   * 通过调用{@link Throwable#initCause(java.lang.Throwable)}方法初始化。
   *  <code> reason </code>和<code> SQLState </code>初始化为null,并且供应商代码初始化为0。
   * <p>
   *  <strong>注意：</strong>没有验证{@code updateCounts}溢出,因此建议您使用构造函数{@code BatchUpdateException(String reason,String SQLState,int vendorCode,long [] updateCounts,Throwable cause)}
   * 。
   * </p>
   * 
   * @param updateCounts an array of <code>int</code>, with each element
   * indicating the update count, <code>Statement.SUCCESS_NO_INFO</code> or
   * <code>Statement.EXECUTE_FAILED</code> for each SQL command in
   * the batch for JDBC drivers that continue processing
   * after a command failure; an update count or
   * <code>Statement.SUCCESS_NO_INFO</code> for each SQL command in the batch
   * prior to the failure for JDBC drivers that stop processing after a command
   * failure
   * @since 1.2
   * @see #BatchUpdateException(java.lang.String, java.lang.String, int, long[],
   * java.lang.Throwable)
   */
  public BatchUpdateException(int[] updateCounts) {
      this(null, null, 0, updateCounts);
  }

  /**
   * Constructs a <code>BatchUpdateException</code> object.
   * The <code>reason</code>, <code>SQLState</code> and <code>updateCounts</code>
   *  are initialized to <code>null</code> and the vendor code is initialized to 0.
   * The <code>cause</code> is not initialized, and may subsequently be
   * initialized by a call to the
   * {@link Throwable#initCause(java.lang.Throwable)} method.
   * <p>
   *
   * <p>
   * 构造一个<code> BatchUpdateException </code>对象。
   *  <code>原因</code>,<code> SQLState </code>和<code> updateCounts </code>初始化为<code> null </code>,供应商代码初始化为
   * 0. <code > cause </code>没有被初始化,并且随后可以通过调用{@link Throwable#initCause(java.lang.Throwable)}方法来初始化。
   * 构造一个<code> BatchUpdateException </code>对象。
   * <p>
   * 
   * 
   * @since 1.2
   * @see #BatchUpdateException(java.lang.String, java.lang.String, int, long[],
   * java.lang.Throwable)
   */
  public BatchUpdateException() {
        this(null, null, 0, null);
  }

  /**
   * Constructs a <code>BatchUpdateException</code> object initialized with
   *  a given <code>cause</code>.
   * The <code>SQLState</code> and <code>updateCounts</code>
   * are initialized
   * to <code>null</code> and the vendor code is initialized to 0.
   * The <code>reason</code>  is initialized to <code>null</code> if
   * <code>cause==null</code> or to <code>cause.toString()</code> if
   *  <code>cause!=null</code>.
   * <p>
   *  构造使用给定的<code> cause </code>初始化的<code> BatchUpdateException </code>对象。
   *  <code> SQLState </code>和<code> updateCounts </code>初始化为<code> null </code>,供应商代码初始化为0. <code> reason
   *  </code> <code> null </code> if <code> cause == null </code>或<code> cause.toString()</code> if <code>
   *  cause！= null </code>。
   *  构造使用给定的<code> cause </code>初始化的<code> BatchUpdateException </code>对象。
   * 
   * 
   * @param cause the underlying reason for this <code>SQLException</code>
   * (which is saved for later retrieval by the <code>getCause()</code> method);
   * may be null indicating the cause is non-existent or unknown.
   * @since 1.6
   * @see #BatchUpdateException(java.lang.String, java.lang.String, int, long[],
   * java.lang.Throwable)
   */
  public BatchUpdateException(Throwable cause) {
      this((cause == null ? null : cause.toString()), null, 0, (int[])null, cause);
  }

  /**
   * Constructs a <code>BatchUpdateException</code> object initialized with a
   * given <code>cause</code> and <code>updateCounts</code>.
   * The <code>SQLState</code> is initialized
   * to <code>null</code> and the vendor code is initialized to 0.
   * The <code>reason</code>  is initialized to <code>null</code> if
   * <code>cause==null</code> or to <code>cause.toString()</code> if
   * <code>cause!=null</code>.
   * <p>
   * <strong>Note:</strong> There is no validation of {@code updateCounts} for
   * overflow and because of this it is recommended that you use the constructor
   * {@code BatchUpdateException(String reason, String SQLState,
   * int vendorCode, long []updateCounts,Throwable cause) }.
   * </p>
   * <p>
   *  构造使用给定的<code> cause </code>和<code> updateCounts </code>初始化的<code> BatchUpdateException </code>对象。
   *  <code> SQLState </code>初始化为<code> null </code>,供应商代码初始化为0. <code> reason </code>初始化为<code> null </code>
   *  if <code> cause == null </code>或<code> cause.toString()</code>如果<code> cause！= null </code>。
   *  构造使用给定的<code> cause </code>和<code> updateCounts </code>初始化的<code> BatchUpdateException </code>对象。
   * <p>
   *  <strong>注意：</strong>没有验证{@code updateCounts}溢出,因此建议您使用构造函数{@code BatchUpdateException(String reason,String SQLState,int vendorCode,long [] updateCounts,Throwable cause)}
   * 。
   * </p>
   * 
   * @param updateCounts an array of <code>int</code>, with each element
   * indicating the update count, <code>Statement.SUCCESS_NO_INFO</code> or
   * <code>Statement.EXECUTE_FAILED</code> for each SQL command in
   * the batch for JDBC drivers that continue processing
   * after a command failure; an update count or
   * <code>Statement.SUCCESS_NO_INFO</code> for each SQL command in the batch
   * prior to the failure for JDBC drivers that stop processing after a command
   * failure
   * @param cause the underlying reason for this <code>SQLException</code>
   * (which is saved for later retrieval by the <code>getCause()</code> method); may be null indicating
   * the cause is non-existent or unknown.
   * @since 1.6
   * @see #BatchUpdateException(java.lang.String, java.lang.String, int, long[],
   * java.lang.Throwable)
   */
  public BatchUpdateException(int []updateCounts , Throwable cause) {
      this((cause == null ? null : cause.toString()), null, 0, updateCounts, cause);
  }

  /**
   * Constructs a <code>BatchUpdateException</code> object initialized with
   * a given <code>reason</code>, <code>cause</code>
   * and <code>updateCounts</code>. The <code>SQLState</code> is initialized
   * to <code>null</code> and the vendor code is initialized to 0.
   * <p>
   * <strong>Note:</strong> There is no validation of {@code updateCounts} for
   * overflow and because of this it is recommended that you use the constructor
   * {@code BatchUpdateException(String reason, String SQLState,
   * int vendorCode, long []updateCounts,Throwable cause) }.
   * </p>
   * <p>
   * 构造使用给定的<code>原因</code>,<code>原因</code>和<code> updateCounts </code>初始化的<code> BatchUpdateException </code>
   * 对象。
   *  <code> SQLState </code>初始化为<code> null </code>,供应商代码初始化为0。
   * <p>
   *  <strong>注意：</strong>没有验证{@code updateCounts}溢出,因此建议您使用构造函数{@code BatchUpdateException(String reason,String SQLState,int vendorCode,long [] updateCounts,Throwable cause)}
   * 。
   * </p>
   * 
   * @param reason a description of the exception
   * @param updateCounts an array of <code>int</code>, with each element
   *indicating the update count, <code>Statement.SUCCESS_NO_INFO</code> or
   * <code>Statement.EXECUTE_FAILED</code> for each SQL command in
   * the batch for JDBC drivers that continue processing
   * after a command failure; an update count or
   * <code>Statement.SUCCESS_NO_INFO</code> for each SQL command in the batch
   * prior to the failure for JDBC drivers that stop processing after a command
   * failure
   * @param cause the underlying reason for this <code>SQLException</code> (which is saved for later retrieval by the <code>getCause()</code> method);
   * may be null indicating
   * the cause is non-existent or unknown.
   * @since 1.6
   * @see #BatchUpdateException(java.lang.String, java.lang.String, int, long[],
   * java.lang.Throwable)
   */
  public BatchUpdateException(String reason, int []updateCounts, Throwable cause) {
      this(reason, null, 0, updateCounts, cause);
  }

  /**
   * Constructs a <code>BatchUpdateException</code> object initialized with
   * a given <code>reason</code>, <code>SQLState</code>,<code>cause</code>, and
   * <code>updateCounts</code>. The vendor code is initialized to 0.
   *
   * <p>
   *  构造使用给定<code>原因</code>,<code> SQLState </code>,<code>原因</code>和<code> updateCounts </code初始化的<code> B
   * atchUpdateException < >。
   * 供应商代码初始化为0。
   * 
   * 
   * @param reason a description of the exception
   * @param SQLState an XOPEN or SQL:2003 code identifying the exception
   * @param updateCounts an array of <code>int</code>, with each element
   * indicating the update count, <code>Statement.SUCCESS_NO_INFO</code> or
   * <code>Statement.EXECUTE_FAILED</code> for each SQL command in
   * the batch for JDBC drivers that continue processing
   * after a command failure; an update count or
   * <code>Statement.SUCCESS_NO_INFO</code> for each SQL command in the batch
   * prior to the failure for JDBC drivers that stop processing after a command
   * failure
   * <p>
   * <strong>Note:</strong> There is no validation of {@code updateCounts} for
   * overflow and because of this it is recommended that you use the constructor
   * {@code BatchUpdateException(String reason, String SQLState,
   * int vendorCode, long []updateCounts,Throwable cause) }.
   * </p>
   * @param cause the underlying reason for this <code>SQLException</code>
   * (which is saved for later retrieval by the <code>getCause()</code> method);
   * may be null indicating
   * the cause is non-existent or unknown.
   * @since 1.6
   * @see #BatchUpdateException(java.lang.String, java.lang.String, int, long[],
   * java.lang.Throwable)
   */
  public BatchUpdateException(String reason, String SQLState,
          int []updateCounts, Throwable cause) {
      this(reason, SQLState, 0, updateCounts, cause);
  }

  /**
   * Constructs a <code>BatchUpdateException</code> object initialized with
   * a given <code>reason</code>, <code>SQLState</code>, <code>vendorCode</code>
   * <code>cause</code> and <code>updateCounts</code>.
   *
   * <p>
   *  构造使用给定<code>原因</code>,<code> SQLState </code>,<code> vendorCode </code> <code>原因</code>初始化的<code> Ba
   * tchUpdateException < <code> updateCounts </code>。
   * 
   * 
   * @param reason a description of the error
   * @param SQLState an XOPEN or SQL:2003 code identifying the exception
   * @param vendorCode an exception code used by a particular
   * database vendor
   * @param updateCounts an array of <code>int</code>, with each element
   *indicating the update count, <code>Statement.SUCCESS_NO_INFO</code> or
   * <code>Statement.EXECUTE_FAILED</code> for each SQL command in
   * the batch for JDBC drivers that continue processing
   * after a command failure; an update count or
   * <code>Statement.SUCCESS_NO_INFO</code> for each SQL command in the batch
   * prior to the failure for JDBC drivers that stop processing after a command
   * failure
   * <p>
   * <strong>Note:</strong> There is no validation of {@code updateCounts} for
   * overflow and because of this it is recommended that you use the constructor
   * {@code BatchUpdateException(String reason, String SQLState,
   * int vendorCode, long []updateCounts,Throwable cause) }.
   * </p>
   * @param cause the underlying reason for this <code>SQLException</code> (which is saved for later retrieval by the <code>getCause()</code> method);
   * may be null indicating
   * the cause is non-existent or unknown.
   * @since 1.6
   * @see #BatchUpdateException(java.lang.String, java.lang.String, int, long[],
   * java.lang.Throwable)
   */
  public BatchUpdateException(String reason, String SQLState, int vendorCode,
                                int []updateCounts,Throwable cause) {
        super(reason, SQLState, vendorCode, cause);
        this.updateCounts  = (updateCounts == null) ? null : Arrays.copyOf(updateCounts, updateCounts.length);
        this.longUpdateCounts = (updateCounts == null) ? null : copyUpdateCount(updateCounts);
  }

  /**
   * Retrieves the update count for each update statement in the batch
   * update that executed successfully before this exception occurred.
   * A driver that implements batch updates may or may not continue to
   * process the remaining commands in a batch when one of the commands
   * fails to execute properly. If the driver continues processing commands,
   * the array returned by this method will have as many elements as
   * there are commands in the batch; otherwise, it will contain an
   * update count for each command that executed successfully before
   * the <code>BatchUpdateException</code> was thrown.
   *<P>
   * The possible return values for this method were modified for
   * the Java 2 SDK, Standard Edition, version 1.3.  This was done to
   * accommodate the new option of continuing to process commands
   * in a batch update after a <code>BatchUpdateException</code> object
   * has been thrown.
   *
   * <p>
   * 检索在发生此异常之前成功执行的批量更新中的每个更新语句的更新计数。实现批量更新的驱动程序可能会或可能不会在其中一个命令无法正确执行时继续处理批处理中的剩余命令。
   * 如果驱动程序继续处理命令,则此方法返回的数组将具有与批处理中的命令一样多的元素;否则,它将包含在抛出<code> BatchUpdateException </code>之前成功执行的每个命令的更新计数
   * 。
   * 检索在发生此异常之前成功执行的批量更新中的每个更新语句的更新计数。实现批量更新的驱动程序可能会或可能不会在其中一个命令无法正确执行时继续处理批处理中的剩余命令。
   * P>
   *  此方法的可能返回值已针对Java 2 SDK标准版1.3版进行了修改。
   * 这是为了适应在一个<code> BatchUpdateException </code>对象被抛出之后继续在批量更新中处理命令的新选项。
   * 
   * 
   * @return an array of <code>int</code> containing the update counts
   * for the updates that were executed successfully before this error
   * occurred.  Or, if the driver continues to process commands after an
   * error, one of the following for every command in the batch:
   * <OL>
   * <LI>an update count
   *  <LI><code>Statement.SUCCESS_NO_INFO</code> to indicate that the command
   *     executed successfully but the number of rows affected is unknown
   *  <LI><code>Statement.EXECUTE_FAILED</code> to indicate that the command
   *     failed to execute successfully
   * </OL>
   * @since 1.3
   * @see #getLargeUpdateCounts()
   */
  public int[] getUpdateCounts() {
      return (updateCounts == null) ? null : Arrays.copyOf(updateCounts, updateCounts.length);
  }

  /**
   * Constructs a <code>BatchUpdateException</code> object initialized with
   * a given <code>reason</code>, <code>SQLState</code>, <code>vendorCode</code>
   * <code>cause</code> and <code>updateCounts</code>.
   * <p>
   * This constructor should be used when the returned update count may exceed
   * {@link Integer#MAX_VALUE}.
   * <p>
   * <p>
   *  构造使用给定<code>原因</code>,<code> SQLState </code>,<code> vendorCode </code> <code>原因</code>初始化的<code> Ba
   * tchUpdateException < <code> updateCounts </code>。
   * <p>
   *  当返回的更新计数可能超过{@link Integer#MAX_VALUE}时,应使用此构造函数。
   * <p>
   * 
   * @param reason a description of the error
   * @param SQLState an XOPEN or SQL:2003 code identifying the exception
   * @param vendorCode an exception code used by a particular
   * database vendor
   * @param updateCounts an array of <code>long</code>, with each element
   *indicating the update count, <code>Statement.SUCCESS_NO_INFO</code> or
   * <code>Statement.EXECUTE_FAILED</code> for each SQL command in
   * the batch for JDBC drivers that continue processing
   * after a command failure; an update count or
   * <code>Statement.SUCCESS_NO_INFO</code> for each SQL command in the batch
   * prior to the failure for JDBC drivers that stop processing after a command
   * failure
   * @param cause the underlying reason for this <code>SQLException</code>
   * (which is saved for later retrieval by the <code>getCause()</code> method);
   * may be null indicating the cause is non-existent or unknown.
   * @since 1.8
   */
  public BatchUpdateException(String reason, String SQLState, int vendorCode,
          long []updateCounts,Throwable cause) {
      super(reason, SQLState, vendorCode, cause);
      this.longUpdateCounts  = (updateCounts == null) ? null : Arrays.copyOf(updateCounts, updateCounts.length);
      this.updateCounts = (longUpdateCounts == null) ? null : copyUpdateCount(longUpdateCounts);
  }

  /**
   * Retrieves the update count for each update statement in the batch
   * update that executed successfully before this exception occurred.
   * A driver that implements batch updates may or may not continue to
   * process the remaining commands in a batch when one of the commands
   * fails to execute properly. If the driver continues processing commands,
   * the array returned by this method will have as many elements as
   * there are commands in the batch; otherwise, it will contain an
   * update count for each command that executed successfully before
   * the <code>BatchUpdateException</code> was thrown.
   * <p>
   * This method should be used when {@code Statement.executeLargeBatch} is
   * invoked and the returned update count may exceed {@link Integer#MAX_VALUE}.
   * <p>
   * <p>
   * 检索在发生此异常之前成功执行的批量更新中的每个更新语句的更新计数。实现批量更新的驱动程序可能会或可能不会在其中一个命令无法正确执行时继续处理批处理中的剩余命令。
   * 如果驱动程序继续处理命令,则此方法返回的数组将具有与批处理中的命令一样多的元素;否则,它将包含在抛出<code> BatchUpdateException </code>之前成功执行的每个命令的更新计数
   * 。
   * 检索在发生此异常之前成功执行的批量更新中的每个更新语句的更新计数。实现批量更新的驱动程序可能会或可能不会在其中一个命令无法正确执行时继续处理批处理中的剩余命令。
   * <p>
   *  当调用{@code Statement.executeLargeBatch}并且返回的更新计数可能超过{@link整数#MAX_VALUE}时,应使用此方法。
   * <p>
   * 
   * @return an array of <code>long</code> containing the update counts
   * for the updates that were executed successfully before this error
   * occurred.  Or, if the driver continues to process commands after an
   * error, one of the following for every command in the batch:
   * <OL>
   * <LI>an update count
   *  <LI><code>Statement.SUCCESS_NO_INFO</code> to indicate that the command
   *     executed successfully but the number of rows affected is unknown
   *  <LI><code>Statement.EXECUTE_FAILED</code> to indicate that the command
   *     failed to execute successfully
   * </OL>
   * @since 1.8
   */
  public long[] getLargeUpdateCounts() {
      return (longUpdateCounts == null) ? null :
              Arrays.copyOf(longUpdateCounts, longUpdateCounts.length);
  }

  /**
   * The array that describes the outcome of a batch execution.
   * <p>
   *  描述批处理执行结果的数组。
   * 
   * 
   * @serial
   * @since 1.2
   */
  private  int[] updateCounts;

  /*
   * Starting with Java SE 8, JDBC has added support for returning an update
   * count > Integer.MAX_VALUE.  Because of this the following changes were made
   * to BatchUpdateException:
   * <ul>
   * <li>Add field longUpdateCounts</li>
   * <li>Add Constructorr which takes long[] for update counts</li>
   * <li>Add getLargeUpdateCounts method</li>
   * </ul>
   * When any of the constructors are called, the int[] and long[] updateCount
   * fields are populated by copying the one array to each other.
   *
   * As the JDBC driver passes in the updateCounts, there has always been the
   * possiblity for overflow and BatchUpdateException does not need to account
   * for that, it simply copies the arrays.
   *
   * JDBC drivers should always use the constructor that specifies long[] and
   * JDBC application developers should call getLargeUpdateCounts.
   * <p>
   *  从Java SE 8开始,JDBC增加了对返回更新计数> Integer.MAX_VALUE的支持。因此,对BatchUpdateException进行了以下更改：
   * <ul>
   *  <li>添加字段longUpdateCounts </li> <li>添加Constructorr,需要较长的[]才能显示更新计数</li> <li>添加getLargeUpdateCounts方法</li>
   * 。
   * </ul>
   *  当调用任何构造函数时,通过将一个数组复制到另一个数组来填充int []和long [] updateCount字段。
   * 
   *  当JDBC驱动程序传递updateCounts时,一直存在overflow和BatchUpdateException的可能性不需要考虑到它,它只是复制数组。
   * 
   * JDBC驱动程序应始终使用指定long []的构造函数,JDBC应用程序开发人员应调用getLargeUpdateCounts。
   * 
   */

  /**
   * The array that describes the outcome of a batch execution.
   * <p>
   *  描述批处理执行结果的数组。
   * 
   * 
   * @serial
   * @since 1.8
   */
  private  long[] longUpdateCounts;

  private static final long serialVersionUID = 5977529877145521757L;

  /*
   * Utility method to copy int[] updateCount to long[] updateCount
   * <p>
   *  实用方法将int [] updateCount复制到long [] updateCount
   * 
   */
  private static long[] copyUpdateCount(int[] uc) {
      long[] copy = new long[uc.length];
      for(int i= 0; i< uc.length; i++) {
          copy[i] = uc[i];
      }
      return copy;
  }

  /*
   * Utility method to copy long[] updateCount to int[] updateCount.
   * No checks for overflow will be done as it is expected a  user will call
   * getLargeUpdateCounts.
   * <p>
   *  实用方法将long [] updateCount复制到int [] updateCount。不会进行溢出检查,因为预期用户将调用getLargeUpdateCounts。
   * 
   */
  private static int[] copyUpdateCount(long[] uc) {
      int[] copy = new int[uc.length];
      for(int i= 0; i< uc.length; i++) {
          copy[i] = (int) uc[i];
      }
      return copy;
  }
    /**
     * readObject is called to restore the state of the
     * {@code BatchUpdateException} from a stream.
     * <p>
     *  readObject被调用以从流中恢复{@code BatchUpdateException}的状态。
     * 
     */
    private void readObject(ObjectInputStream s)
            throws IOException, ClassNotFoundException {

       ObjectInputStream.GetField fields = s.readFields();
       int[] tmp = (int[])fields.get("updateCounts", null);
       long[] tmp2 = (long[])fields.get("longUpdateCounts", null);
       if(tmp != null && tmp2 != null && tmp.length != tmp2.length)
           throw new InvalidObjectException("update counts are not the expected size");
       if (tmp != null)
           updateCounts = tmp.clone();
       if (tmp2 != null)
           longUpdateCounts = tmp2.clone();
       if(updateCounts == null && longUpdateCounts != null)
           updateCounts = copyUpdateCount(longUpdateCounts);
       if(longUpdateCounts == null && updateCounts != null)
           longUpdateCounts = copyUpdateCount(updateCounts);

    }

    /**
     * writeObject is called to save the state of the {@code BatchUpdateException}
     * to a stream.
     * <p>
     *  writeObject被调用来将{@code BatchUpdateException}的状态保存到流。
     */
    private void writeObject(ObjectOutputStream s)
            throws IOException, ClassNotFoundException {

        ObjectOutputStream.PutField fields = s.putFields();
        fields.put("updateCounts", updateCounts);
        fields.put("longUpdateCounts", longUpdateCounts);
        s.writeFields();
    }
}
