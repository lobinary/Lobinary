/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2003, Oracle and/or its affiliates. All rights reserved.
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

package org.omg.CORBA;

import org.omg.CORBA.TypeCodePackage.*;
import org.omg.CORBA.portable.IDLEntity;

/**
 * A container for information about a specific CORBA data
 * type.
 *<P>
 * <code>TypeCode</code> objects are used:
 * <UL>
 * <LI>in the Dynamic Invocation Interface -- to indicate the types
 * of the actual arguments or the type of the return value.  <BR>
 * <code>NamedValue</code> objects are used to represent arguments and
 * return values.  One of their components is an <code>Any</code>
 * object, which in turn has as one of its components a
 * <code>TypeCode</code> object.
 * <LI>by an Interface Repository to represent the type specifications
 * that are part of many OMG IDL declarations
 * </UL>
 * <P>
 * The representation of a <code>TypeCode</code> object is opaque,
 * but abstractly, a <code>TypeCode</code> object consists of:
 * <UL>
 * <LI>a <code>kind</code> field, which is set to an instance
 * of the class <code>TCKind</code>
 * <LI>zero or more additional fields appropriate
 * for the particular kind. For example, the
 * <code>TypeCode</code> object
 * describing the OMG IDL type <code>1ong</code> has kind
 * <code>TCKind.tk_long</code> and no additional fields.
 * The <code>TypeCode</code> describing OMG IDL type
 * <code>sequence&lt;boolean, 10&gt;</code> has a <code>kind</code> field
 * with the value
 * <code>TCKind.tk_sequence</code> and also fields with the values
 * <code>boolean</code> and <code>10</code> for the
 * type of sequence elements and the length of the sequence. <p>
 * </UL>
 *
 * <code>TypeCode</code> objects can be obtained in various ways:
 * <OL>
 * <LI>from a call to the method <code>Any.insert_X</code>, where X is
 * a basic IDL type.  This method creates a <code>TypeCode</code> object
 * for type X and assigns it to the <code>Any</code> object's
 * <code>type</code> field.
 * <LI>from invocations of methods in the ORB class
 * <P>For example, the following creates a <code>TypeCode</code>
 * object for a <code>string</code> with a maximum of 30 characters:
 * <PRE>
 *   org.omg.CORBA.TypeCode tcString = orb.create_string_tc(30);
 * </PRE>
 * <P> The following creates a <code>TypeCode</code>
 * object for an <code>array</code> of five <code>string</code>s:
 * <PRE>
 *   org.omg.CORBA.TypeCode tcArray = orb.create_array_tc(
 *                                       5, TCKind.tk_string);
 * </PRE>
 * <P> The following creates a <code>TypeCode</code>
 * object for an interface named "Account":
 * <PRE>
 *   org.omg.CORBA.TypeCode tcInterface = orb.create_interface_tc(
 *                                                 "thisId", "Account");
 * </PRE>
 * <LI>as the return value from the <code>_type</code> method
 * in <code>Holder</code> classes for user-defined
 * IDL types.  These <code>Holder</code> classes are generated
 * by the <code>idltojava</code> compiler.
 * <LI>from a CORBA Interface Repository
 * </OL>
 * <P>
 * Most of the methods in the class <code>TypeCode</code>
 * are accessors, and the information contained in a <code>TypeCode</code>
 * object is specific to a particular type.  Therefore, methods
 * must be invoked
 * only on the kind of type codes to which they apply.  If an
 * accessor method
 * tries to access information from an inappropriate kind of
 * type code, it will throw
 * the exception <code>TypeCodePackage.BadKind</code>.  For example,
 * if the method <code>discriminator_type</code> is called on anything
 * other than a <code>union</code>, it will throw <code>BadKind</code>
 * because only <code>union</code>s have a discriminator.
 * The following list shows which methods apply to which kinds of
 * type codes:
 * <P>
 * These methods may be invoked on all <code>TypeCode</code> kinds:
 * <UL>
 * <LI><code>equal</code>
 * <LI><code>kind</code>
 * </UL>
 * <P>
 * These methods may be invoked on <code>objref</code>, <code>struct</code>,
 * <code>union</code>, <code>enum</code>,
 * <code>alias</code>, <code>exception</code>, <code>value</code>,
 * <code>value_box</code>, <code>native</code>,
 * and <code>abstract_interface</code>:
 * <UL>
 * <LI><code>id</code>
 * <LI><code>name</code>
 * </UL>
 * <P>
 * These methods may be invoked on <code>struct</code>,
 * <code>union</code>, <code>enum</code>,
 * and <code>exception</code>:
 * <UL>
 * <LI><code>member_count</code>
 * <LI><code>member_name</code>
 * </UL>
 * <P>
 * These methods may be invoked on <code>struct</code>,
 * <code>union</code>, and <code>exception</code>:
 * <UL>
 * <LI><code>member_type(int index)</code>
 * </UL>
 * <P>
 * These methods may be invoked on <code>union</code>:
 * <UL>
 * <LI><code>member_label</code>
 * <LI><code>discriminator_type</code>
 * <LI><code>default_index</code>
 * </UL>
 * <P>
 * These methods may be invoked on <code>string</code>,
 * <code>sequence</code>, and <code>array</code>:
 * <UL>
 * <LI><code>length</code>
 * </UL>
 * <P>
 * These methods may be invoked on <code>alias</code>,
 * <code>sequence</code>, <code>array</code>, and <code>value_box</code>:
 * <UL>
 * <LI><code>content_type</code>
 * </UL>
 * <P>
 * Unlike other CORBA pseudo-objects, <code>TypeCode</code>
 * objects can be passed as general IDL parameters. <p>
 * The methods <code>parameter</code> and <code>param_count</code>,
 * which are deprecated, are not mapped.  <p>
 *
 * Java IDL extends the CORBA specification to allow all operations permitted
 * on a <code>struct</code> <code>TypeCode</code> to be permitted
 * on an <code>exception</code> <code>TypeCode</code> as well. <p>
 *
 * <p>
 *  用于有关特定CORBA数据类型的信息的容器
 * P>
 *  使用<code> TypeCode </code>对象：
 * <UL>
 *  <LI>在动态调用接口中 - 指示实际参数的类型或返回值的类型<BR> <code> NamedValue </code>对象用于表示参数和返回值其中一个组件是一个<code> Any </code>
 * 对象,接口存储库代表了作为许多OMG IDL声明的一部分的类型规范,它具有作为其组件之一的<code> TypeCode </code>对象。
 * </UL>
 * <P>
 * <code> TypeCode </code>对象的表示是不透明的,但抽象地,<code> TypeCode </code>对象包括：
 * <UL>
 *  <LI> <code> kind </code>字段,其被设置为类<code> TCKind </code> <LI>的一个实例适合于特定种类的零个或多个附加字段例如,<code > TypeCode
 *  </code>对象描述OMG IDL类型<code> 1ong </code>有种类<code> TCKindtk_long </code>,没有其他字段描述OMG IDL类型的<code> Type
 * Code </code>序列&lt; boolean,10&gt; </code>具有值<code> TCKindtk_sequence </code>的<code>种</code>字段以及具有值<code>
 *  boolean </code> 10 </code>表示序列元素的类型和序列的长度<p>。
 * </UL>
 * 
 * <code> TypeCode </code>对象可以通过多种方式获取：
 * <OL>
 *  <li>从方法的调用<code> Anyinsert_X </code>,其中X是基本的IDL类型此方法为类型X创建一个<code> TypeCode </code>对象并将其分配给<code> An
 * y </code>对象的<code>类型</code>字段<LI>从ORB类中的方法调用<P>例如,下面为<code>字符串创建一个<code> TypeCode </code> </code>,最多3
 * 0个字符：。
 * <PRE>
 *  orgomgCORBATypeCode tcString = orbcreate_string_tc(30);
 * </PRE>
 *  <P>以下为五个<code> string </code>的<code>数组</code>创建<code> TypeCode </code>
 * <PRE>
 *  orgomgCORBATypeCode tcArray = orbcreate_array_tc(5,TCKindtk_string);
 * </PRE>
 * <P>以下为名为"Account"的接口创建<code> TypeCode </code>对象：
 * <PRE>
 *  orgomgCORBATypeCode tcInterface = orbcreate_interface_tc("thisId","Account");
 * </PRE>
 *  <LI>作为用于用户定义的IDL类型的<code> Holder </code>类中的<code> _type </code>方法的返回值这些<code> Holder </code>类由<code>
 *  > idltojava </code>编译器<LI>。
 * </OL>
 * <P>
 * <code> TypeCode </code>类中的大多数方法都是访问器,并且包含在<code> TypeCode </code>对象中的信息特定于特定类型因此,方法必须仅在类型类型代码如果访问器方法尝
 * 试访问来自不适当类型的代码的信息,它将抛出异常<code> TypeCodePackageBadKind </code>例如,如果方法<code> discriminator_type </code>除
 * 了<code> union </code>之外的任何东西,它会抛出<code> BadKind </code>,因为只有<code> union </code>有一个discriminator下面的列表
 * 显示哪些方法适用于哪种类型代码：。
 * <P>
 *  这些方法可以在所有<code> TypeCode </code>类别上调用：
 * <UL>
 * <LI> <code>等于</code> <LI> <code> kind </code>
 * </UL>
 * <P>
 *  这些方法可以在<code> objref </code>,<code> struct </code>,<code> union </code>,<code> enum </code>,<code>别名
 * </code> ,<code> exception </code>,<code> value </code>,<code> value_box </code>,<code> native </code>
 * 和<code> abstract_interface </code>。
 * <UL>
 *  <LI> <code> id </code> <li> <code> name </code>
 * </UL>
 * <P>
 *  这些方法可以在<code> struct </code>,<code> union </code>,<code>枚举</code>和<code>异常</code>
 * <UL>
 *  <LI> <code> member_count </code> <li> <code> member_name </code>
 * </UL>
 * <P>
 *  这些方法可以在<code> struct </code>,<code> union </code>和<code> exception </code>上调用：
 * <UL>
 *  <LI> <code> member_type(int index)</code>
 * </UL>
 * <P>
 *  这些方法可以在<code> union </code>上调用：
 * <UL>
 * <LI> <code> member_label </code> <LI> <code> discriminator_type </code> <LI> <code> default_index </code>
 * 。
 * </UL>
 * <P>
 *  这些方法可以在<code> string </code>,<code> sequence </code>和<code> array </code>
 * <UL>
 *  <LI> <code> length </code>
 * </UL>
 * <P>
 *  这些方法可以在<code>别名</code>,<code> sequence </code>,<code> array </code>和<code> value_box </code>
 * <UL>
 *  <LI> <code> content_type </code>
 * </UL>
 * <P>
 *  与其他CORBA伪对象不同,<code> TypeCode </code>对象可以作为一般的IDL参数传递<p>方法<code>参数</code>和<code> param_count </code>
 * 未映射<p>。
 * 
 *  Java IDL扩展CORBA规范以允许在<code>异常</code> <code> TypeCode </code>上允许<code> struct </code> <code> TypeCode
 *  </code>以及<p>。
 * 
 */
public abstract class TypeCode implements IDLEntity {

    /**
     * Compares this <code>TypeCode</code> object with the given one,
     * testing for equality. <code>TypeCode</code> objects are equal if
     * they are interchangeable and give identical results when
     * <code>TypeCode</code> operations are applied to them.
     *
     * <p>
     * 将<code> TypeCode </code>对象与给定的对象进行比较,如果它们是可互换的,则测试等同性<code> TypeCode </code>对象是否相等,并且当应用<code> TypeCo
     * de </code>给他们。
     * 
     * 
     * @param tc                the <code>TypeCode</code> object to compare against
     * @return          <code>true</code> if the type codes are equal;
     *                <code>false</code> otherwise
     */

    public abstract boolean equal(TypeCode tc);

    /**
         * Tests to see if the given <code>TypeCode</code> object is
         * equivalent to this <code>TypeCode</code> object.
         * <P>
         *
         *
         * <p>
         *  测试给定的<code> TypeCode </code>对象是否等同于<code> TypeCode </code>对象
         * <P>
         * 
         * 
         * @param tc the typecode to compare with this typecode
         *
         * @return <code>true</code> if the given typecode is equivalent to
         *         this typecode; <code>false</code> otherwise
     *
     */
    public abstract boolean equivalent(TypeCode tc);

    /**
     * Strips out all optional name and member name fields,
     * but leaves all alias typecodes intact.
     * <p>
     *  删除所有可选的名称和成员名称字段,但保留所有别名类型代码
     * 
     * 
         * @return a <code>TypeCode</code> object with optional name and
         *         member name fields stripped out, except for alias typecodes,
         *         which are left intact
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */
    public abstract TypeCode get_compact_typecode();


    /**
     * Retrieves the kind of this <code>TypeCode</code> object.
     * The kind of a type code determines which <code>TypeCode</code>
     * methods may legally be invoked on it.
     * <P>
     * The method <code>kind</code> may be invoked on any
     * <code>TypeCode</code> object.
     *
     * <p>
     *  检索这个<code> TypeCode </code>对象的类型类型代码的种类决定了哪些<code> TypeCode </code>方法可以合法地调用它
     * <P>
     *  方法<code> kind </code>可以在任何<code> TypeCode </code>对象上调用
     * 
     * 
     * @return  the <code>TCKind</code> instance indicating the
     *            value of the <code>kind</code> field of this
     *                  <code>TypeCode</code> object
     */

    public abstract TCKind kind();

    /**
     * Retrieves the RepositoryId globally identifying the type
     * of this <code>TypeCode</code> object.
     * <P>
     * The method <code>id</code> can be invoked on object reference,
     * structure, union, enumeration, alias, exception, valuetype,
     * boxed valuetype, native, and abstract interface type codes.
     * Object reference, exception, valuetype, boxed valuetype,
     * native, and abstract interface <code>TypeCode</code> objects
     * always have a RepositoryId.
     * Structure, union, enumeration, and alias <code>TypeCode</code> objects
     * obtained from the Interface Repository or the method
     * <code>ORB.create_operation_list</code>
     * also always have a RepositoryId. If there is no RepositoryId, the
     * method can return an empty string.
     *
     * <p>
     *  检索全局地标识此<code> TypeCode </code>对象类型的RepositoryId
     * <P>
     * 可以在对象引用,结构,联合,枚举,别名,异常,值类型,盒装值类型,本机和抽象接口类型代码上调用方法<code> id </code>对象引用,异常,值类型, ,并且抽象接口<code> TypeCode
     *  </code>对象总是具有从Interface Repository或方法<code> ORBcreate_operation_list </code>获得的RepositoryId结构,联合,枚举和
     * 别名<code> TypeCode </code>也总是有一个RepositoryId如果没有RepositoryId,该方法可以返回一个空字符串。
     * 
     * 
     * @return          the RepositoryId for this <code>TypeCode</code> object
     *                or an empty string if there is no RepositoryID
     * @throws org.omg.CORBA.TypeCodePackage.BadKind if the method
     *           is invoked on an inappropriate kind of<code>TypeCode</code>
     *           object
     */

    public abstract String id() throws BadKind;

    /**
     * Retrieves the simple name identifying this <code>TypeCode</code>
     * object within its
     * enclosing scope. Since names are local to a Repository, the
     * name returned from a <code>TypeCode</code> object
     * may not match the name of the
     * type in any particular Repository, and may even be an empty
     * string.
     * <P>
     * The method <code>name</code> can be invoked on object reference,
     * structure, union, enumeration, alias, exception, valuetype,
     * boxed valuetype, native, and abstract interface
     * <code>TypeCode</code> objects.
     *
     * <p>
     * 检索在其封闭范围内标识此<code> TypeCode </code>对象的简单名称由于名称对于Repository是本地的,因此从<code> TypeCode </code>对象返回的名称可能与类型
     * 名称不匹配任何特定的存储库,甚至可能是一个空字符串。
     * <P>
     *  可以在对象引用,结构,联合,枚举,别名,异常,值类型,盒装价值类型,本机和抽象接口<code> TypeCode </code>对象上调用方法<code> name </code>
     * 
     * 
     * @return          the name identifying this <code>TypeCode</code> object
     *                or an empty string
     * @throws org.omg.CORBA.TypeCodePackage.BadKind if the method
     *           is invoked on an inappropriate kind of<code>TypeCode</code>
     *           object
     */

    public abstract String name() throws BadKind;

    /**
     * Retrieves the number of members in the type described by
     * this <code>TypeCode</code> object.
     * <P>
     * The method <code>member_count</code> can be invoked on
     * structure, union, and enumeration <code>TypeCode</code> objects.
     * Java IDL extends the CORBA specification to allow this method to
     * operate on exceptions as well.
     *
     * <p>
     *  检索由此<code> TypeCode </code>对象描述的类型中的成员数
     * <P>
     * 方法<code> member_count </code>可以在结构,联合和枚举中调用<code> TypeCode </code>对象Java IDL扩展CORBA规范,以允许此方法对异常
     * 
     * 
     * @return          the number of members constituting the type described
     *                by this <code>TypeCode</code> object
     *
     * @throws org.omg.CORBA.TypeCodePackage.BadKind if the method
     *           is invoked on an inappropriate kind of <code>TypeCode</code>
     *           object
     */

    public abstract int member_count() throws BadKind;

    /**
     * Retrieves the simple name of the member identified by
     * the given index. Since names are local to a
     * Repository, the name returned from a <code>TypeCode</code> object
     * may not match the name of the member in any particular
     * Repository, and may even be an empty string.
     * <P>
     * The  method <code>member_name</code> can be invoked on structure, union,
     * and enumeration <code>TypeCode</code> objects.
     * Java IDL extends the CORBA specification to allow this method to
     * operate on exceptions as well.
     *
     * <p>
     *  检索由给定索引标识的成员的简单名称由于名称是Repository的本地名称,从<code> TypeCode </code>对象返回的名称可能与任何特定存储库中的成员名称不匹配,甚至可能为空字符串
     * <P>
     *  方法<code> member_name </code>可以在结构,联合和枚举上调用<code> TypeCode </code>对象Java IDL扩展CORBA规范,以允许此方法对异常
     * 
     * 
     * @param index     index of the member for which a name is being reqested
     * @return          simple name of the member identified by the
     *                  index or an empty string
     * @throws org.omg.CORBA.TypeCodePackage.Bounds if the index is equal
     *            to or greater than
     *                  the number of members constituting the type
     * @throws org.omg.CORBA.TypeCodePackage.BadKind if the method
     *           is invoked on an inappropriate kind of <code>TypeCode</code>
     *           object
     */

    public abstract String member_name(int index)
        throws BadKind, org.omg.CORBA.TypeCodePackage.Bounds;

    /**
     * Retrieves the <code>TypeCode</code> object describing the type
     * of the member identified by the given index.
     * <P>
     * The method <code>member_type</code> can be invoked on structure
     * and union <code>TypeCode</code> objects.
     * Java IDL extends the CORBA specification to allow this method to
     * operate on exceptions as well.
     *
     * <p>
     * 检索描述由给定索引标识的成员类型的<code> TypeCode </code>对象
     * <P>
     *  方法<code> member_type </code>可以在结构和联合上调用<code> TypeCode </code>对象Java IDL扩展CORBA规范以允许此方法对异常操作
     * 
     * 
     * @param index     index of the member for which type information
     *                is begin requested
     * @return          the <code>TypeCode</code> object describing the
     *                member at the given index
     * @throws org.omg.CORBA.TypeCodePackage.Bounds if the index is
     *                equal to or greater than
     *                      the number of members constituting the type
     * @throws org.omg.CORBA.TypeCodePackage.BadKind if the method
     *           is invoked on an inappropriate kind of <code>TypeCode</code>
     *           object
     */

    public abstract TypeCode member_type(int index)
        throws BadKind, org.omg.CORBA.TypeCodePackage.Bounds;

    /**
     * Retrieves the label of the union member
     * identified by the given index. For the default member,
     * the label is the zero octet.
     *<P>
     * The method <code>member_label</code> can only be invoked on union
     * <code>TypeCode</code> objects.
     *
     * <p>
     *  检索由给定索引标识的联合成员的标签对于默认成员,标签是零八位字节
     * P>
     *  方法<code> member_label </code>只能在union <code> TypeCode </code>对象上调用
     * 
     * 
     * @param index     index of the union member for which the
     *                label is being requested
     * @return          an <code>Any</code> object describing the label of
     *                the requested union member or the zero octet for
     *                the default member
     * @throws org.omg.CORBA.TypeCodePackage.Bounds if the index is
     *                equal to or greater than
     *                      the number of members constituting the union
     * @throws org.omg.CORBA.TypeCodePackage.BadKind if the method
     *           is invoked on a non-union <code>TypeCode</code>
     *           object
     */

    public abstract Any member_label(int index)
        throws BadKind, org.omg.CORBA.TypeCodePackage.Bounds;

    /**
     * Returns a <code>TypeCode</code> object describing
     * all non-default member labels.
     * The method <code>discriminator_type</code> can be invoked only
     * on union <code>TypeCode</code> objects.
     *
     * <p>
     *  返回描述所有非默认成员标签的<code> TypeCode </code>对象方法<code> discriminator_type </code>只能在union <code> TypeCode </code>
     * 对象上调用。
     * 
     * 
     * @return          the <code>TypeCode</code> object describing
     *                the non-default member labels
     * @throws org.omg.CORBA.TypeCodePackage.BadKind if the method
     *           is invoked on a non-union <code>TypeCode</code>
     *           object
     */

    public abstract TypeCode discriminator_type()
        throws BadKind;

    /**
     * Returns the index of the
     * default member, or -1 if there is no default member.
     * <P>
     * The method <code>default_index</code> can be invoked only on union
     * <code>TypeCode</code> objects.
     *
     * <p>
     *  返回默认成员的索引,如果没有默认成员,则返回-1
     * <P>
     * 方法<code> default_index </code>只能在union <code> TypeCode </code>对象上调用
     * 
     * 
     * @return          the index of the default member, or -1 if
     *                  there is no default member
     * @throws org.omg.CORBA.TypeCodePackage.BadKind if the method
     *           is invoked on a non-union <code>TypeCode</code>
     *           object
     */

    public abstract int default_index() throws BadKind;

    /**
     * Returns the number of elements in the type described by
     * this <code>TypeCode</code> object.
     * For strings and sequences, it returns the
     * bound, with zero indicating an unbounded string or sequence.
     * For arrays, it returns the number of elements in the array.
     * <P>
     * The method <code>length</code> can be invoked on string, sequence, and
     * array <code>TypeCode</code> objects.
     *
     * <p>
     *  返回由<code> TypeCode </code>对象描述的类型中的元素数对于字符串和序列,它返回绑定,零表示无界字符串或序列对于数组,它返回数组中的元素数
     * <P>
     *  可以在字符串,序列和数组<code> TypeCode </code>对象上调用<code> length </code>
     * 
     * 
     * @return          the bound for strings and sequences, or the
     *                      number of elements for arrays
     * @throws org.omg.CORBA.TypeCodePackage.BadKind if the method
     *           is invoked on an inappropriate kind of <code>TypeCode</code>
     *           object
     */

    public abstract int length() throws BadKind;

    /**
     * Returns the <code>TypeCode</code> object representing the
     * IDL type for the members of the object described by this
     * <code>TypeCode</code> object.
     * For sequences and arrays, it returns the
     * element type. For aliases, it returns the original type. Note
     * that multidimensional arrays are represented by nesting
     * <code>TypeCode</code> objects, one per dimension.
     * For boxed valuetypes, it returns the boxed type.
     *<P>
     * The method <code>content_type</code> can be invoked on sequence, array,
     * alias, and boxed valuetype <code>TypeCode</code> objects.
     *
     * <p>
     * 返回代表由此<code> TypeCode </code>对象描述的对象的成员的IDL类型的<code> TypeCode </code>对象对于序列和数组,返回元素类型对于别名,类型注意,多维数组由嵌
     * 套<code> TypeCode </code>对象表示,每个维度有一个对于盒装的值类型,它返回盒装类型。
     * P>
     *  方法<code> content_type </code>可以在sequence,array,alias和boxed valuetype <code> TypeCode </code>对象上调用
     * 
     * 
     * @return  a <code>TypeCode</code> object representing
     *            the element type for sequences and arrays, the
     *          original type for aliases, or the
     *            boxed type for boxed valuetypes.
     * @throws org.omg.CORBA.TypeCodePackage.BadKind if the method
     *           is invoked on an inappropriate kind of <code>TypeCode</code>
     *           object
     */

    public abstract TypeCode content_type() throws BadKind;


    /**
         * Returns the number of digits in the fixed type described by this
         * <code>TypeCode</code> object. For example, the typecode for
         * the number 3000.275d could be <code>fixed<7,3></code>, where
         * 7 is the precision and 3 is the scale.
         *
         * <p>
         *  返回由<code> TypeCode </code>对象描述的固定类型的数字位数例如,数字3000275d的类型代码可以是<code> fixed <7,3> </code>,其中7是精度和3是比例。
         * 
         * 
         * @return the total number of digits
     * @throws org.omg.CORBA.TypeCodePackage.BadKind if this method
     *           is invoked on an inappropriate kind of <code>TypeCode</code>
     *           object
         *
     */
    public abstract short fixed_digits() throws BadKind ;

    /**
         * Returns the scale of the fixed type described by this
         * <code>TypeCode</code> object. A positive number indicates the
         * number of digits to the right of the decimal point.
         * For example, the number 3000d could have the
         * typecode <code>fixed<4,0></code>, where the first number is
         * the precision and the second number is the scale.
         * A negative number is also possible and adds zeroes to the
         * left of the decimal point.  In this case, <code>fixed<1,-3></code>,
         * could be the typecode for the number 3000d.
         *
         * <p>
         * 返回由<code> TypeCode </code>对象描述的固定类型的比例。一个正数表示小数点右边的位数。
         * 例如,数字3000d可以有类型代码<code> fixed <4 ,0> </code>,其中第一个数字是精度,第二个数字是刻度。负数也是可能的,并且在小数点的左边加上零。
         * 在这种情况下,<code> fixed < 3> </code>,可以是数字3000d的类型代码。
         * 
         * 
         * @return the scale of the fixed type that this
         *         <code>TypeCode</code> object describes
     * @throws org.omg.CORBA.TypeCodePackage.BadKind if this method
     *           is invoked on an inappropriate kind of <code>TypeCode</code>
     *           object
     */
    public abstract short fixed_scale() throws BadKind ;

    /**
     * Returns the constant that indicates the visibility of the member
     * at the given index.
     *
     * This operation can only be invoked on non-boxed value
     * <code>TypeCode</code> objects.
     *
     * <p>
     *  返回指示给定索引处的成员的可见性的常量
     * 
     *  此操作只能在非盒装值<code> TypeCode </code>对象上调用
     * 
     * 
     * @param index an <code>int</code> indicating the index into the
     *               value
     * @return either <code>PRIVATE_MEMBER.value</code> or
     *          <code>PUBLIC_MEMBER.value</code>
     * @throws org.omg.CORBA.TypeCodePackage.BadKind if this method
     *           is invoked on a non-value type <code>TypeCode</code>
     *           object
     * @throws org.omg.CORBA.TypeCodePackage.Bounds
     *           if the given index is out of bounds
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */

    abstract public short member_visibility(int index)
        throws BadKind, org.omg.CORBA.TypeCodePackage.Bounds ;

    /**
     * Returns a constant indicating the modifier of the value type
     * that this <code>TypeCode</code> object describes.  The constant
     * returned must be one of the following: <code>VM_NONE.value</code>,
     * <code>VM_ABSTRACT.value</code>, <code>VM_CUSTOM.value</code>,
     * or <code>VM_TRUNCATABLE.value</code>,
     *
     * <p>
     * 返回一个常量,指示此<> TypeCode </code>对象描述的值类型的修饰符。
     * 返回的常量必须为以下之一：<code> VM_NONEvalue </code>,<code> VM_ABSTRACTvalue </code> <code> VM_CUSTOMvalue </code>
     * 或<code> VM_TRUNCATABLEvalue </code>,。
     * 返回一个常量,指示此<> TypeCode </code>对象描述的值类型的修饰符。
     * 
     * @return a constant describing the value type
     *         that this <code>TypeCode</code> object describes
     * @throws org.omg.CORBA.TypeCodePackage.BadKind
     *           if this method
     *           is invoked on a non-value type <code>TypeCode</code>
     *           object
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */

    abstract public short type_modifier() throws BadKind ;

    /**
     * Returns the <code>TypeCode</code> object that describes the concrete base type
     * of the value type that this <code>TypeCode</code> object describes.
     * Returns null if it doesn't have a concrete base type.
     *
     * <p>
     * 
     * 
     * @return the <code>TypeCode</code> object that describes the
     * concrete base type of the value type
     * that this <code>TypeCode</code> object describes
     * @throws org.omg.CORBA.TypeCodePackage.BadKind if this method
     *           is invoked on a non-boxed value type <code>TypeCode</code> object
     * @see <a href="package-summary.html#unimpl"><code>CORBA</code> package
     *      comments for unimplemented features</a>
     */

    abstract public TypeCode concrete_base_type() throws BadKind ;
}
