/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2006, Oracle and/or its affiliates. All rights reserved.
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

package javax.sql.rowset.spi;

import java.sql.SQLException;
import javax.sql.rowset.*;

/**
 * Indicates an error with the <code>SyncProvider</code> mechanism. This exception
 * is created by a <code>SyncProvider</code> abstract class extension if it
 * encounters violations in reading from or writing to the originating data source.
 * <P>
 * If it is implemented to do so, the <code>SyncProvider</code> object may also create a
 * <code>SyncResolver</code> object and either initialize the <code>SyncProviderException</code>
 * object with it at construction time or set it with the <code>SyncProvider</code> object at
 * a later time.
 * <P>
 * The method <code>acceptChanges</code> will throw this exception after the writer
 * has finished checking for conflicts and has found one or more conflicts. An
 * application may catch a <code>SyncProviderException</code> object and call its
 * <code>getSyncResolver</code> method to get its <code>SyncResolver</code> object.
 * See the code fragment in the interface comment for
 * <a href="SyncResolver.html"><code>SyncResolver</code></a> for an example.
 * This <code>SyncResolver</code> object will mirror the <code>RowSet</code>
 * object that generated the exception, except that it will contain only the values
 * from the data source that are in conflict.  All other values in the <code>SyncResolver</code>
 * object will be <code>null</code>.
 * <P>
 * The <code>SyncResolver</code> object may be used to examine and resolve
 * each conflict in a row and then go to the next row with a conflict to
 * repeat the procedure.
 * <P>
 * A <code>SyncProviderException</code> object may or may not contain a description of the
 * condition causing the exception.  The inherited method <code>getMessage</code> may be
 * called to retrieve the description if there is one.
 *
 * <p>
 *  表示<code> SyncProvider </code>机制的错误。如果在读取或写入源数据源时遇到违例,则此异常由<code> SyncProvider </code>抽象类扩展创建。
 * <P>
 *  如果实现这样做,<code> SyncProvider </code>对象也可以创建一个<code> SyncResolver </code>对象,并在构建时初始化<code> SyncProvide
 * rException </code>稍后用<code> SyncProvider </code>对象设置它。
 * <P>
 *  方法<code> acceptChanges </code>将在写入程序完成检查冲突并发现一个或多个冲突后抛出此异常。
 * 应用程序可以捕获<code> SyncProviderException </code>对象并调用其<code> getSyncResolver </code>方法以获取其<code> SyncReso
 * lver </code>对象。
 *  方法<code> acceptChanges </code>将在写入程序完成检查冲突并发现一个或多个冲突后抛出此异常。
 * 有关示例,请参见<a href="SyncResolver.html">界面注释中的代码片段<a href="SyncResolver.html"> <code> SyncResolver </code>
 *  </a>。
 *  方法<code> acceptChanges </code>将在写入程序完成检查冲突并发现一个或多个冲突后抛出此异常。
 * 这个<code> SyncResolver </code>对象将镜像生成异常的<code> RowSet </code>对象,除了它将只包含来自数据源中冲突的值。
 *  <code> SyncResolver </code>对象中的所有其他值将为<code> null </code>。
 * <P>
 * <code> SyncResolver </code>对象可用于检查和解决行中的每个冲突,然后转到具有冲突的下一行以重复此过程。
 * <P>
 *  <code> SyncProviderException </code>对象可能包含或可能不包含导致异常的条件的描述。
 * 可以调用继承的方法<code> getMessage </code>来检索描述(如果有的话)。
 * 
 * 
 * @author Jonathan Bruce
 * @see javax.sql.rowset.spi.SyncFactory
 * @see javax.sql.rowset.spi.SyncResolver
 * @see javax.sql.rowset.spi.SyncFactoryException
 */
public class SyncProviderException extends java.sql.SQLException {

    /**
     * The instance of <code>javax.sql.rowset.spi.SyncResolver</code> that
     * this <code>SyncProviderException</code> object will return when its
     * <code>getSyncResolver</code> method is called.
     * <p>
     *  <code> javax.sql.rowset.spi.SyncResolver </code>的实例,当<code> SyncProviderException </code>对象在调用<code>
     *  getSyncResolver </code>方法时会返回。
     * 
     */
     private SyncResolver syncResolver = null;

    /**
     * Creates a new <code>SyncProviderException</code> object without a detail message.
     * <p>
     *  创建一个新的<code> SyncProviderException </code>对象,不包含详细信息。
     * 
     */
    public SyncProviderException() {
        super();
    }

    /**
     * Constructs a <code>SyncProviderException</code> object with the specified
     * detail message.
     *
     * <p>
     *  构造具有指定详细消息的<code> SyncProviderException </code>对象。
     * 
     * 
     * @param msg the detail message
     */
    public SyncProviderException(String msg)  {
        super(msg);
    }

    /**
     * Constructs a <code>SyncProviderException</code> object with the specified
     * <code>SyncResolver</code> instance.
     *
     * <p>
     *  使用指定的<code> SyncResolver </code>实例构造一个<code> SyncProviderException </code>对象。
     * 
     * 
     * @param syncResolver the <code>SyncResolver</code> instance used to
     *     to process the synchronization conflicts
     * @throws IllegalArgumentException if the <code>SyncResolver</code> object
     *     is <code>null</code>.
     */
    public SyncProviderException(SyncResolver syncResolver)  {
        if (syncResolver == null) {
            throw new IllegalArgumentException("Cannot instantiate a SyncProviderException " +
                "with a null SyncResolver object");
        } else {
            this.syncResolver = syncResolver;
        }
    }

    /**
     * Retrieves the <code>SyncResolver</code> object that has been set for
     * this <code>SyncProviderException</code> object, or
     * if none has been set, an instance of the default <code>SyncResolver</code>
     * implementation included in the reference implementation.
     * <P>
     * If a <code>SyncProviderException</code> object is thrown, an application
     * may use this method to generate a <code>SyncResolver</code> object
     * with which to resolve the conflict or conflicts that caused the
     * exception to be thrown.
     *
     * <p>
     *  检索已为此<code> SyncProviderException </code>对象设置的<code> SyncResolver </code>对象,如果没有设置,则包含在默认<code> Sync
     * Resolver </code>参考实现。
     * <P>
     *  如果抛出一个<code> SyncProviderException </code>对象,应用程序可以使用此方法来生成一个<code> SyncResolver </code>对象,用于解决导致异常抛
     * 出的冲突或冲突。
     * 
     * 
     * @return the <code>SyncResolver</code> object set for this
     *     <code>SyncProviderException</code> object or, if none has
     *     been set, an instance of the default <code>SyncResolver</code>
     *     implementation. In addition, the default <code>SyncResolver</code>
     *     implementation is also returned if the <code>SyncResolver()</code> or
     *     <code>SyncResolver(String)</code> constructors are used to instantiate
     *     the <code>SyncResolver</code> instance.
     */
    public SyncResolver getSyncResolver() {
        if (syncResolver != null) {
            return syncResolver;
        } else {
            try {
              syncResolver = new com.sun.rowset.internal.SyncResolverImpl();
            } catch (SQLException sqle) {
            }
            return syncResolver;
        }
    }

    /**
     * Sets the <code>SyncResolver</code> object for this
     * <code>SyncProviderException</code> object to the one supplied.
     * If the argument supplied is <code>null</code>, a call to the method
     * <code>getSyncResolver</code> will return the default reference
     * implementation of the <code>SyncResolver</code> interface.
     *
     * <p>
     * 
     * @param syncResolver the <code>SyncResolver</code> object to be set;
     *     cannot be <code>null</code>
     * @throws IllegalArgumentException if the <code>SyncResolver</code> object
     *     is <code>null</code>.
     * @see #getSyncResolver
     */
    public void setSyncResolver(SyncResolver syncResolver) {
        if (syncResolver == null) {
            throw new IllegalArgumentException("Cannot set a null SyncResolver " +
                "object");
        } else {
            this.syncResolver = syncResolver;
        }
    }

    static final long serialVersionUID = -939908523620640692L;

}
