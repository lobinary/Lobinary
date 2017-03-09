/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

import javax.sql.*;

/**
 * The synchronization mechanism that provides reader/writer capabilities for
 * disconnected <code>RowSet</code> objects.
 * A <code>SyncProvider</code> implementation is a class that extends the
 * <code>SyncProvider</code> abstract class.
 * <P>
 * A <code>SyncProvider</code> implementation is
 * identified by a unique ID, which is its fully qualified class name.
 * This name must be registered with the
 * <code>SyncFactory</code> SPI, thus making the implementation available to
 * all <code>RowSet</code> implementations.
 * The factory mechanism in the reference implementation uses this name to instantiate
 * the implementation, which can then provide a <code>RowSet</code> object with its
 * reader (a <code>javax.sql.RowSetReader</code> object) and its writer (a
 * <code>javax.sql.RowSetWriter</code> object).
 * <P>
 * The Jdbc <code>RowSet</code> Implementations specification provides two
 * reference implementations of the <code>SyncProvider</code> abstract class:
 * <code>RIOptimisticProvider</code> and <code>RIXMLProvider</code>.
 * The <code>RIOptimisticProvider</code> can set any <code>RowSet</code>
 * implementation with a <code>RowSetReader</code> object and a
 * <code>RowSetWriter</code> object.  However, only the <code>RIXMLProvider</code>
 * implementation can set an <code>XmlReader</code> object and an
 * <code>XmlWriter</code> object. A <code>WebRowSet</code> object uses the
 * <code>XmlReader</code> object to read data in XML format to populate itself with that
 * data.  It uses the <code>XmlWriter</code> object to write itself to a stream or
 * <code>java.io.Writer</code> object in XML format.
 *
 * <h3>1.0 Naming Convention for Implementations</h3>
 * As a guide  to naming <code>SyncProvider</code>
 * implementations, the following should be noted:
 * <UL>
 * <li>The name for a <code>SyncProvider</code> implementation
 * is its fully qualified class name.
 * <li>It is recommended that vendors supply a
 * <code>SyncProvider</code> implementation in a package named <code>providers</code>.
 * </UL>
 * <p>
 * For instance, if a vendor named Fred, Inc. offered a
 * <code>SyncProvider</code> implementation, you could have the following:
 * <PRE>
 *     Vendor name:  Fred, Inc.
 *     Domain name of vendor:  com.fred
 *     Package name:  com.fred.providers
 *     SyncProvider implementation class name:  HighAvailabilityProvider
 *
 *     Fully qualified class name of SyncProvider implementation:
 *                        com.fred.providers.HighAvailabilityProvider
 * </PRE>
 * <P>
 * The following line of code uses the fully qualified name to register
 * this implementation with the <code>SyncFactory</code> static instance.
 * <PRE>
 *     SyncFactory.registerProvider(
 *                          "com.fred.providers.HighAvailabilityProvider");
 * </PRE>
 * <P>
 * The default <code>SyncProvider</code> object provided with the reference
 * implementation uses the following name:
 * <pre>
 *     com.sun.rowset.providers.RIOptimisticProvider
 * </pre>
 * <p>
 * A vendor can register a <code>SyncProvider</code> implementation class name
 * with Oracle Corporation by sending email to jdbc@sun.com.
 * Oracle will maintain a database listing the
 * available <code>SyncProvider</code> implementations for use with compliant
 * <code>RowSet</code> implementations.  This database will be similar to the
 * one already maintained to list available JDBC drivers.
 * <P>
 * Vendors should refer to the reference implementation synchronization
 * providers for additional guidance on how to implement a new
 * <code>SyncProvider</code> implementation.
 *
 * <h3>2.0 How a <code>RowSet</code> Object Gets Its Provider</h3>
 *
 * A disconnected <code>Rowset</code> object may get access to a
 * <code>SyncProvider</code> object in one of the following two ways:
 * <UL>
 *  <LI>Using a constructor<BR>
 *      <PRE>
 *       CachedRowSet crs = new CachedRowSet(
 *                  "com.fred.providers.HighAvailabilitySyncProvider");
 *      </PRE>
 *  <LI>Using the <code>setSyncProvider</code> method
 *      <PRE>
 *       CachedRowSet crs = new CachedRowSet();
 *       crs.setSyncProvider("com.fred.providers.HighAvailabilitySyncProvider");
 *      </PRE>

 * </UL>
 * <p>
 * By default, the reference implementations of the <code>RowSet</code> synchronization
 * providers are always available to the Java platform.
 * If no other pluggable synchronization providers have been correctly
 * registered, the <code>SyncFactory</code> will automatically generate
 * an instance of the default <code>SyncProvider</code> reference implementation.
 * Thus, in the preceding code fragment, if no implementation named
 * <code>com.fred.providers.HighAvailabilitySyncProvider</code> has been
 * registered with the <code>SyncFactory</code> instance, <i>crs</i> will be
 * assigned the default provider in the reference implementation, which is
 * <code>com.sun.rowset.providers.RIOptimisticProvider</code>.
 *
 * <h3>3.0 Violations and Synchronization Issues</h3>
 * If an update between a disconnected <code>RowSet</code> object
 * and a data source violates
 * the original query or the underlying data source constraints, this will
 * result in undefined behavior for all disconnected <code>RowSet</code> implementations
 * and their designated <code>SyncProvider</code> implementations.
 * Not defining the behavior when such violations occur offers greater flexibility
 * for a <code>SyncProvider</code>
 * implementation to determine its own best course of action.
 * <p>
 * A <code>SyncProvider</code> implementation
 * may choose to implement a specific handler to
 * handle a subset of query violations.
 * However if an original query violation or a more general data source constraint
 * violation is not handled by the <code>SyncProvider</code> implementation,
 * all <code>SyncProvider</code>
 * objects must throw a <code>SyncProviderException</code>.
 *
 * <h3>4.0 Updatable SQL VIEWs</h3>
 * It is possible for any disconnected or connected <code>RowSet</code> object to be populated
 * from an SQL query that is formulated originally from an SQL <code>VIEW</code>.
 * While in many cases it is possible for an update to be performed to an
 * underlying view, such an update requires additional metadata, which may vary.
 * The <code>SyncProvider</code> class provides two constants to indicate whether
 * an implementation supports updating an SQL <code>VIEW</code>.
 * <ul>
 * <li><code><b>NONUPDATABLE_VIEW_SYNC</b></code> - Indicates that a <code>SyncProvider</code>
 * implementation does not support synchronization with an SQL <code>VIEW</code> as the
 * underlying source of data for the <code>RowSet</code> object.
 * <li><code><b>UPDATABLE_VIEW_SYNC</b></code> - Indicates that a
 * <code>SyncProvider</code> implementation
 * supports synchronization with an SQL <code>VIEW</code> as the underlying source
 * of data.
 * </ul>
 * <P>
 * The default is for a <code>RowSet</code> object not to be updatable if it was
 * populated with data from an SQL <code>VIEW</code>.
 *
 * <h3>5.0 <code>SyncProvider</code> Constants</h3>
 * The <code>SyncProvider</code> class provides three sets of constants that
 * are used as return values or parameters for <code>SyncProvider</code> methods.
 * <code>SyncProvider</code> objects may be implemented to perform synchronization
 * between a <code>RowSet</code> object and its underlying data source with varying
 * degrees of of care. The first group of constants indicate how synchronization
 * is handled. For example, <code>GRADE_NONE</code> indicates that a
 * <code>SyncProvider</code> object will not take any care to see what data is
 * valid and will simply write the <code>RowSet</code> data to the data source.
 * <code>GRADE_MODIFIED_AT_COMMIT</code> indicates that the provider will check
 * only modified data for validity.  Other grades check all data for validity
 * or set locks when data is modified or loaded.
 * <OL>
 *  <LI>Constants to indicate the synchronization grade of a
 *     <code>SyncProvider</code> object
 *   <UL>
 *    <LI>SyncProvider.GRADE_NONE
 *    <LI>SyncProvider.GRADE_MODIFIED_AT_COMMIT
 *    <LI>SyncProvider.GRADE_CHECK_ALL_AT_COMMIT
 *    <LI>SyncProvider.GRADE_LOCK_WHEN_MODIFIED
 *    <LI>SyncProvider.GRADE_LOCK_WHEN_LOADED
 *   </UL>
 *  <LI>Constants to indicate what locks are set on the data source
 *   <UL>
 *     <LI>SyncProvider.DATASOURCE_NO_LOCK
 *     <LI>SyncProvider.DATASOURCE_ROW_LOCK
 *     <LI>SyncProvider.DATASOURCE_TABLE_LOCK
 *     <LI>SyncProvider.DATASOURCE_DB_LOCK
 *   </UL>
 *  <LI>Constants to indicate whether a <code>SyncProvider</code> object can
 *       perform updates to an SQL <code>VIEW</code> <BR>
 *       These constants are explained in the preceding section (4.0).
 *   <UL>
 *     <LI>SyncProvider.UPDATABLE_VIEW_SYNC
 *     <LI>SyncProvider.NONUPDATABLE_VIEW_SYNC
 *   </UL>
 * </OL>
 *
 * <p>
 *  同步机制,为断开的<code> RowSet </code>对象提供读写器功能。
 * 一个<code> SyncProvider </code>实现是一个扩展<code> SyncProvider </code>抽象类的类。
 * <P>
 *  <code> SyncProvider </code>实现由唯一ID标识,它是其完全限定类名。
 * 此名称必须注册到<code> SyncFactory </code> SPI中,从而使该实现可用于所有<code> RowSet </code>实现。
 * 参考实现中的工厂机制使用该名称来实例化实现,然后可以用其reader(一个<code> javax.sql.RowSetReader </code>对象)提供一个<code> RowSet </code>
 *  writer(一个<code> javax.sql.RowSetWriter </code>对象)。
 * 此名称必须注册到<code> SyncFactory </code> SPI中,从而使该实现可用于所有<code> RowSet </code>实现。
 * <P>
 * Jdbc <code> RowSet </code>实现规范提供了<code> SyncProvider </code>抽象类的两个参考实现：<code> RIOptimisticProvider </code>
 * 和<code> RIXMLProvider </code>。
 *  <code> RIOptimisticProvider </code>可以使用<code> RowSetReader </code>对象和<code> RowSetWriter </code>对象来设
 * 置任何<code> RowSet </code>实现。
 * 但是,只有<code> RIXMLProvider </code>实现可以设置一个<code> XmlReader </code>对象和一个<code> XmlWriter </code>对象。
 *  <code> WebRowSet </code>对象使用<code> XmlReader </code>对象以XML格式读取数据,以使用该数据填充自身。
 * 它使用<code> XmlWriter </code>对象将自身写入到XML格式的流或<code> java.io.Writer </code>对象。
 * 
 *  <h3> 1.0实现的命名约定</h3>作为命名<code> SyncProvider </code>实现的指南,应注意以下几点：
 * <UL>
 *  <li> <code> SyncProvider </code>实施的名称是其完全限定类名称。
 *  <li>建议供应商在名为<code> providers </code>的包中提供<code> SyncProvider </code>实现。
 * </UL>
 * <p>
 *  例如,如果名为Fred,Inc.的供应商提供了一个<code> SyncProvider </code>实现,您可以具有以下：
 * <PRE>
 *  供应商名称：Fred,Inc.供应商的域名：com.fred包名称：com.fred.providers SyncProvider实现类名称：HighAvailabilityProvider
 * 
 * SyncProvider实现的完全限定类名：com.fred.providers.HighAvailabilityProvider
 * </PRE>
 * <P>
 *  以下代码行使用完全限定名称来向<code> SyncFactory </code>静态实例注册此实现。
 * <PRE>
 *  SyncFactory.registerProvider("com.fred.providers.HighAvailabilityProvider");
 * </PRE>
 * <P>
 *  与参考实现一起提供的默认<code> SyncProvider </code>对象使用以下名称：
 * <pre>
 *  com.sun.rowset.providers.RIOptimisticProvider
 * </pre>
 * <p>
 *  供应商可以通过向jdbc@sun.com发送电子邮件,向Oracle Corporation注册<code> SyncProvider </code>实现类名称。
 *  Oracle将维护一个数据库,列出可用的<code> SyncProvider </code>实现,用于符合<code> RowSet </code>的实现。
 * 此数据库将类似于已维护的列出可用的JDBC驱动程序的数据库。
 * <P>
 *  供应商应参考参考实现同步提供程序,以获取有关如何实现新的<code> SyncProvider </code>实现的更多指导。
 * 
 *  <h3> 2.0 <code> RowSet </code>对象如何获取它的提供者</h3>
 * 
 *  断开的<code> Rowset </code>对象可以通过以下两种方式之一访问<code> SyncProvider </code>对象：
 * <UL>
 *  <LI>使用构造函数<BR>
 * <PRE>
 *  CachedRowSet crs = new CachedRowSet("com.fred.providers.HighAvailabilitySyncProvider");
 * </PRE>
 *  <LI>使用<code> setSyncProvider </code>方法
 * <PRE>
 * CachedRowSet crs = new CachedRowSet(); crs.setSyncProvider("com.fred.providers.HighAvailabilitySyncPr
 * ovider");。
 * </PRE>
 * 
 * </UL>
 * <p>
 *  默认情况下,<code> RowSet </code>同步提供程序的引用实现始终可用于Java平台。
 * 如果没有其他可插入的同步提供程序已正确注册,则<code> SyncFactory </code>将自动生成默认的<code> SyncProvider </code>参考实现的实例。
 * 因此,在前面的代码片段中,如果没有向<code> SyncFactory </code>实例注册<code> com.fred.providers.HighAvailabilitySyncProvide
 * r </code>的实现,则<i> crs </i>在参考实现中分配默认提供程序,即<code> com.sun.rowset.providers.RIOptimisticProvider </code>
 * 。
 * 如果没有其他可插入的同步提供程序已正确注册,则<code> SyncFactory </code>将自动生成默认的<code> SyncProvider </code>参考实现的实例。
 * 
 *  <h3> 3.0违规和同步问题</h3>如果断开的<code> RowSet </code>对象与数据源之间的更新违反了原始查询或基础数据源约束,则会导致所有断开<code> RowSet </code>
 * 实现及其指定的<code> SyncProvider </code>实现。
 * 当这种违反发生时不定义行为为<code> SyncProvider </code>实现提供更大的灵活性以确定其自己的最佳行动方案。
 * <p>
 * <code> SyncProvider </code>实现可以选择实现特定处理程序来处理查询违反的子集。
 * 但是,如果原始查询冲突或更一般的数据源约束违反未由<code> SyncProvider </code>实现处理,则所有<code> SyncProvider </code>对象必须抛出一个<code>
 *  SyncProviderException </code> 。
 * <code> SyncProvider </code>实现可以选择实现特定处理程序来处理查询违反的子集。
 * 
 *  <h3> 4.0可更新的SQL VIEW </h3>任何断开或连接的<code> RowSet </code>对象都可以从最初从SQL <code> VIEW </code> 。
 * 虽然在许多情况下可以对基础视图执行更新,但是这样的更新需要附加的元数据,其可以变化。
 *  <code> SyncProvider </code>类提供了两个常量来指示实现是否支持更新SQL <code> VIEW </code>。
 * <ul>
 *  <li> <code> <b> NONUPDATABLE_VIEW_SYNC </b> </code>  - 表示<code> SyncProvider </code>实施不支持与SQL <code>
 *  VIEW </code>的<code> RowSet </code>对象的数据。
 *  <li> <code> <b> UPDATABLE_VIEW_SYNC </b> </code>  - 表示<code> SyncProvider </code>实施支持与SQL <code> VIE
 * W </code> 。
 * </ul>
 * <P>
 *  如果使用来自SQL <code> VIEW </code>的数据填充,则默认值为<code> RowSet </code>对象不可更新。
 * 
 * <h3> 5.0 <code> SyncProvider </code>常量</h3> <code> SyncProvider </code>类提供三组常量,用作<code> SyncProvider 
 * </code>方法的返回值或参数。
 * 可以实现<code> SyncProvider </code>对象以在不同程度的关注的情况下在<code> RowSet </code>对象及其底层数据源之间执行同步。第一组常数指示如何处理同步。
 * 例如,<code> GRADE_NONE </code>表示<code> SyncProvider </code>对象不会关心什么数据是有效的,只需将<code> RowSet </code>数据写入数
 * 据源。
 * 
 * @author Jonathan Bruce
 * @see javax.sql.rowset.spi.SyncFactory
 * @see javax.sql.rowset.spi.SyncFactoryException
 */
public abstract class SyncProvider {

   /**
    * Creates a default <code>SyncProvider</code> object.
    * <p>
    * 可以实现<code> SyncProvider </code>对象以在不同程度的关注的情况下在<code> RowSet </code>对象及其底层数据源之间执行同步。第一组常数指示如何处理同步。
    *  <code> GRADE_MODIFIED_AT_COMMIT </code>表示提供程序将仅检查修改的数据的有效性。其他等级在修改或加载数据时检查所有数据的有效性或设置锁定。
    * <OL>
    *  <LI>用于指示<code> SyncProvider </code>对象的同步等级的常量
    * <UL>
    *  <LI> SyncProvider.GRADE_NONE <LI> SyncProvider.GRADE_MODIFIED_AT_COMMIT <LI> SyncProvider.GRADE_CHEC
    * K_ALL_AT_COMMIT <LI> SyncProvider.GRADE_LOCK_WHEN_MODIFIED <LI> SyncProvider.GRADE_LOCK_WHEN_LOADED。
    * </UL>
    *  <LI>用于指示在数据源上设置了哪些锁的常量
    * <UL>
    *  <LI> SyncProvider.DATASOURCE_NO_LOCK <LI> SyncProvider.DATASOURCE_ROW_LOCK <LI> SyncProvider.DATASOU
    * RCE_TABLE_LOCK <LI> SyncProvider.DATASOURCE_DB_LOCK。
    * </UL>
    * <LI>常量用于指示<code> SyncProvider </code>对象是否可以执行SQL <code> VIEW </code> <BR>的更新这些常量在前面的部分(4.0)中解释。
    * <UL>
    *  <LI> SyncProvider.UPDATABLE_VIEW_SYNC <LI> SyncProvider.NONUPDATABLE_VIEW_SYNC
    * </UL>
    * </OL>
    * 
    */
    public SyncProvider() {
    }

    /**
     * Returns the unique identifier for this <code>SyncProvider</code> object.
     *
     * <p>
     *  创建默认的<code> SyncProvider </code>对象。
     * 
     * 
     * @return a <code>String</code> object with the fully qualified class name of
     *         this <code>SyncProvider</code> object
     */
    public abstract String getProviderID();

    /**
     * Returns a <code>javax.sql.RowSetReader</code> object, which can be used to
     * populate a <code>RowSet</code> object with data.
     *
     * <p>
     *  返回此<> SyncProvider </code>对象的唯一标识符。
     * 
     * 
     * @return a <code>javax.sql.RowSetReader</code> object
     */
    public abstract RowSetReader getRowSetReader();

    /**
     * Returns a <code>javax.sql.RowSetWriter</code> object, which can be
     * used to write a <code>RowSet</code> object's data back to the
     * underlying data source.
     *
     * <p>
     *  返回一个<code> javax.sql.RowSetReader </code>对象,可用于使用数据填充<code> RowSet </code>对象。
     * 
     * 
     * @return a <code>javax.sql.RowSetWriter</code> object
     */
    public abstract RowSetWriter getRowSetWriter();

    /**
     * Returns a constant indicating the
     * grade of synchronization a <code>RowSet</code> object can expect from
     * this <code>SyncProvider</code> object.
     *
     * <p>
     *  返回一个<code> javax.sql.RowSetWriter </code>对象,可用于将<code> RowSet </code>对象的数据写回基础数据源。
     * 
     * 
     * @return an int that is one of the following constants:
     *           SyncProvider.GRADE_NONE,
     *           SyncProvider.GRADE_CHECK_MODIFIED_AT_COMMIT,
     *           SyncProvider.GRADE_CHECK_ALL_AT_COMMIT,
     *           SyncProvider.GRADE_LOCK_WHEN_MODIFIED,
     *           SyncProvider.GRADE_LOCK_WHEN_LOADED
     */
    public abstract int getProviderGrade();


    /**
     * Sets a lock on the underlying data source at the level indicated by
     * <i>datasource_lock</i>. This should cause the
     * <code>SyncProvider</code> to adjust its behavior by increasing or
     * decreasing the level of optimism it provides for a successful
     * synchronization.
     *
     * <p>
     *  返回一个常数,指示<code> RowSet </code>对象可以从此<code> SyncProvider </code>对象中获得的同步等级。
     * 
     * 
     * @param datasource_lock one of the following constants indicating the severity
     *           level of data source lock required:
     * <pre>
     *           SyncProvider.DATASOURCE_NO_LOCK,
     *           SyncProvider.DATASOURCE_ROW_LOCK,
     *           SyncProvider.DATASOURCE_TABLE_LOCK,
     *           SyncProvider.DATASOURCE_DB_LOCK,
     * </pre>
     * @throws SyncProviderException if an unsupported data source locking level
     *           is set.
     * @see #getDataSourceLock
     */
    public abstract void setDataSourceLock(int datasource_lock)
        throws SyncProviderException;

    /**
     * Returns the current data source lock severity level active in this
     * <code>SyncProvider</code> implementation.
     *
     * <p>
     *  在<i> datasource_lock </i>指示的级别上对底层数据源设置锁定。
     * 这应该使<code> SyncProvider </code>通过增加或减少它为成功同步提供的乐观水平来调整其行为。
     * 
     * 
     * @return a constant indicating the current level of data source lock
     *        active in this <code>SyncProvider</code> object;
     *         one of the following:
     * <pre>
     *           SyncProvider.DATASOURCE_NO_LOCK,
     *           SyncProvider.DATASOURCE_ROW_LOCK,
     *           SyncProvider.DATASOURCE_TABLE_LOCK,
     *           SyncProvider.DATASOURCE_DB_LOCK
     * </pre>
     * @throws SyncProviderException if an error occurs determining the data
     *        source locking level.
     * @see #setDataSourceLock

     */
    public abstract int getDataSourceLock()
        throws SyncProviderException;

    /**
     * Returns whether this <code>SyncProvider</code> implementation
     * can perform synchronization between a <code>RowSet</code> object
     * and the SQL <code>VIEW</code> in the data source from which
     * the <code>RowSet</code> object got its data.
     *
     * <p>
     *  返回此<> SyncProvider </code>实现中当前数据源锁定严重性级别。
     * 
     * 
     * @return an <code>int</code> saying whether this <code>SyncProvider</code>
     *         object supports updating an SQL <code>VIEW</code>; one of the
     *         following:
     *            SyncProvider.UPDATABLE_VIEW_SYNC,
     *            SyncProvider.NONUPDATABLE_VIEW_SYNC
     */
    public abstract int supportsUpdatableView();

    /**
     * Returns the release version of this <code>SyncProvider</code> instance.
     *
     * <p>
     *  返回这个<code> SyncProvider </code>实现是否可以在<code> RowSet </code>对象和数据源中的SQL <code> VIEW </code> code> obj
     * ect获取其数据。
     * 
     * 
     * @return a <code>String</code> detailing the release version of the
     *     <code>SyncProvider</code> implementation
     */
    public abstract String getVersion();

    /**
     * Returns the vendor name of this <code>SyncProvider</code> instance
     *
     * <p>
     *  返回此<code> SyncProvider </code>实例的发布版本。
     * 
     * 
     * @return a <code>String</code> detailing the vendor name of this
     *     <code>SyncProvider</code> implementation
     */
    public abstract String getVendor();

    /*
     * Standard description of synchronization grades that a SyncProvider
     * could provide.
     * <p>
     * 返回此<code> SyncProvider </code>实例的供应商名称
     * 
     */

    /**
     * Indicates that no synchronization with the originating data source is
     * provided. A <code>SyncProvider</code>
     * implementation returning this grade will simply attempt to write
     * updates in the <code>RowSet</code> object to the underlying data
     * source without checking the validity of any data.
     *
     * <p>
     *  SyncProvider可以提供的同步等级的标准描述。
     * 
     */
    public static final int GRADE_NONE = 1;

    /**
     * Indicates a low level optimistic synchronization grade with
     * respect to the originating data source.
     *
     * A <code>SyncProvider</code> implementation
     * returning this grade will check only rows that have changed.
     *
     * <p>
     *  表示未提供与源数据源的同步。
     * 返回此成绩的<code> SyncProvider </code>实现将仅尝试将<code> RowSet </code>对象中的更新写入基础数据源,而不检查任何数据的有效性。
     * 
     */
    public static final int GRADE_CHECK_MODIFIED_AT_COMMIT = 2;

    /**
     * Indicates a high level optimistic synchronization grade with
     * respect to the originating data source.
     *
     * A <code>SyncProvider</code> implementation
     * returning this grade will check all rows, including rows that have not
     * changed.
     * <p>
     *  表示相对于始发数据源的低级别乐观同步等级。
     * 
     *  返回此成绩的<code> SyncProvider </code>实现将仅检查已更改的行。
     * 
     */
    public static final int GRADE_CHECK_ALL_AT_COMMIT = 3;

    /**
     * Indicates a pessimistic synchronization grade with
     * respect to the originating data source.
     *
     * A <code>SyncProvider</code>
     * implementation returning this grade will lock the row in the originating
     * data source.
     * <p>
     *  指示相对于始发数据源的高级别乐观同步等级。
     * 
     *  返回此成绩的<code> SyncProvider </code>实现将检查所有行,包括未更改的行。
     * 
     */
    public static final int GRADE_LOCK_WHEN_MODIFIED = 4;

    /**
     * Indicates the most pessimistic synchronization grade with
     * respect to the originating
     * data source. A <code>SyncProvider</code>
     * implementation returning this grade will lock the entire view and/or
     * table affected by the original statement used to populate a
     * <code>RowSet</code> object.
     * <p>
     *  表示相对于始发数据源的悲观同步等级。
     * 
     *  返回此成绩的<code> SyncProvider </code>实现将锁定源数据源中的行。
     * 
     */
    public static final int GRADE_LOCK_WHEN_LOADED = 5;

    /**
     * Indicates that no locks remain on the originating data source. This is the default
     * lock setting for all <code>SyncProvider</code> implementations unless
     * otherwise directed by a <code>RowSet</code> object.
     * <p>
     *  表示相对于始发数据源的最悲观同步等级。
     * 返回此成绩的<code> SyncProvider </code>实现将锁定受用于填充<code> RowSet </code>对象的原始语句影响的整个视图和/或表。
     * 
     */
    public static final int DATASOURCE_NO_LOCK = 1;

    /**
     * Indicates that a lock is placed on the rows that are touched by the original
     * SQL statement used to populate the <code>RowSet</code> object
     * that is using this <code>SyncProvider</code> object.
     * <p>
     * 表示始发数据源上没有锁。这是所有<code> SyncProvider </code>实现的默认锁定设置,除非<code> RowSet </code>对象另有指示。
     * 
     */
    public static final int DATASOURCE_ROW_LOCK = 2;

    /**
     * Indicates that a lock is placed on all tables that are touched by the original
     * SQL statement used to populate the <code>RowSet</code> object
     * that is using this <code>SyncProvider</code> object.
     * <p>
     *  表示在原始SQL语句所触及的行上放置了一个锁,用于填充正在使用此<code> SyncProvider </code>对象的<code> RowSet </code>对象。
     * 
     */
    public static final int DATASOURCE_TABLE_LOCK = 3;

    /**
     * Indicates that a lock is placed on the entire data source that is the source of
     * data for the <code>RowSet</code> object
     * that is using this <code>SyncProvider</code> object.
     * <p>
     *  表示在用于填充正在使用此<code> SyncProvider </code>对象的<code> RowSet </code>对象的原始SQL语句所触及的所有表上都放置了锁。
     * 
     */
    public static final int DATASOURCE_DB_LOCK = 4;

    /**
     * Indicates that a <code>SyncProvider</code> implementation
     * supports synchronization between a <code>RowSet</code> object and
     * the SQL <code>VIEW</code> used to populate it.
     * <p>
     *  表示对整个数据源进行锁定,该数据源是使用此<code> SyncProvider </code>对象的<code> RowSet </code>对象的数据源。
     * 
     */
    public static final int UPDATABLE_VIEW_SYNC = 5;

    /**
     * Indicates that a <code>SyncProvider</code> implementation
     * does <B>not</B> support synchronization between a <code>RowSet</code>
     * object and the SQL <code>VIEW</code> used to populate it.
     * <p>
     *  表示<code> SyncProvider </code>实现支持<code> RowSet </code>对象与用于填充它的SQL <code> VIEW </code>之间的同步。
     * 
     */
    public static final int NONUPDATABLE_VIEW_SYNC = 6;
}
