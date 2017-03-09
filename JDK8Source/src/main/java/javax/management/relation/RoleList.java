/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2008, Oracle and/or its affiliates. All rights reserved.
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

package javax.management.relation;

import com.sun.jmx.mbeanserver.Util;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A RoleList represents a list of roles (Role objects). It is used as
 * parameter when creating a relation, and when trying to set several roles in
 * a relation (via 'setRoles()' method). It is returned as part of a
 * RoleResult, to provide roles successfully retrieved.
 *
 * <p>
 *  RoleList表示角色(Role对象)的列表。它在创建关系时以及在尝试在关系中设置多个角色(通过"setRoles()"方法)时用作参数。
 * 它作为RoleResult的一部分返回,以提供成功检索的角色。
 * 
 * 
 * @since 1.5
 */
/* We cannot extend ArrayList<Role> because our legacy
   add(Role) method would then override add(E) in ArrayList<E>,
   and our return value is void whereas ArrayList.add(E)'s is boolean.
   Likewise for set(int,Role).  Grrr.  We cannot use covariance
   to override the most important methods and have them return
   Role, either, because that would break subclasses that
   override those methods in turn (using the original return type
   of Object).  Finally, we cannot implement Iterable<Role>
   so you could write
       for (Role r : roleList)
   because ArrayList<> implements Iterable<> and the same class cannot
   implement two versions of a generic interface.  Instead we provide
   the asList() method so you can write
       for (Role r : roleList.asList())
/* <p>
/*  add(Role)方法将覆盖ArrayList <E>中的add(E),而我们的返回值为void,而ArrayList.add(E)的值为boolean。同样对于set(int,Role)。
/*  Grrr。我们不能使用协方差来覆盖最重要的方法,并让它们返回Role,因为这会打破依次覆盖这些方法的子类(使用Object的原始返回类型)。
/* 最后,我们不能实现Iterable <Role>,所以你可以写(Role r：roleList),因为ArrayList <>实现Iterable <>,同一个类不能实现通用接口的两个版本。
/* 相反,我们提供asList()方法,所以你可以写(Role r：roleList.asList())。
/* 
*/
public class RoleList extends ArrayList<Object> {

    private transient boolean typeSafe;
    private transient boolean tainted;

    /* Serial version */
    private static final long serialVersionUID = 5568344346499649313L;

    //
    // Constructors
    //

    /**
     * Constructs an empty RoleList.
     * <p>
     *  构造一个空的RoleList。
     * 
     */
    public RoleList() {
        super();
    }

    /**
     * Constructs an empty RoleList with the initial capacity
     * specified.
     *
     * <p>
     *  构造具有指定的初始容量的空RoleList。
     * 
     * 
     * @param initialCapacity  initial capacity
     */
    public RoleList(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructs a {@code RoleList} containing the elements of the
     * {@code List} specified, in the order in which they are returned by
     * the {@code List}'s iterator. The {@code RoleList} instance has
     * an initial capacity of 110% of the size of the {@code List}
     * specified.
     *
     * <p>
     *  按照{@code List}的迭代器返回的顺序构造一个包含{@code List}的元素的{@code RoleList}。
     *  {@code RoleList}实例的初始容量为指定的{@code List}大小的110％。
     * 
     * 
     * @param list the {@code List} that defines the initial contents of
     * the new {@code RoleList}.
     *
     * @exception IllegalArgumentException if the {@code list} parameter
     * is {@code null} or if the {@code list} parameter contains any
     * non-Role objects.
     *
     * @see ArrayList#ArrayList(java.util.Collection)
     */
    public RoleList(List<Role> list) throws IllegalArgumentException {
        // Check for null parameter
        //
        if (list == null)
            throw new IllegalArgumentException("Null parameter");

        // Check for non-Role objects
        //
        checkTypeSafe(list);

        // Build the List<Role>
        //
        super.addAll(list);
    }

    /**
     * Return a view of this list as a {@code List<Role>}.
     * Changes to the returned value are reflected by changes
     * to the original {@code RoleList} and vice versa.
     *
     * <p>
     * 以{@code List <Role>}形式返回此列表的视图。对原始{@code RoleList}的更改反映了对返回值的更改,反之亦然。
     * 
     * 
     * @return a {@code List<Role>} whose contents
     * reflect the contents of this {@code RoleList}.
     *
     * <p>If this method has ever been called on a given
     * {@code RoleList} instance, a subsequent attempt to add
     * an object to that instance which is not a {@code Role}
     * will fail with an {@code IllegalArgumentException}. For compatibility
     * reasons, a {@code RoleList} on which this method has never
     * been called does allow objects other than {@code Role}s to
     * be added.</p>
     *
     * @throws IllegalArgumentException if this {@code RoleList} contains
     * an element that is not a {@code Role}.
     *
     * @since 1.6
     */
    @SuppressWarnings("unchecked")
    public List<Role> asList() {
        if (!typeSafe) {
            if (tainted)
                checkTypeSafe(this);
            typeSafe = true;
        }
        return Util.cast(this);
    }

    //
    // Accessors
    //

    /**
     * Adds the Role specified as the last element of the list.
     *
     * <p>
     *  添加指定为列表的最后一个元素的角色。
     * 
     * 
     * @param role  the role to be added.
     *
     * @exception IllegalArgumentException  if the role is null.
     */
    public void add(Role role)
        throws IllegalArgumentException {

        if (role == null) {
            String excMsg = "Invalid parameter";
            throw new IllegalArgumentException(excMsg);
        }
        super.add(role);
    }

    /**
     * Inserts the role specified as an element at the position specified.
     * Elements with an index greater than or equal to the current position are
     * shifted up.
     *
     * <p>
     *  在指定的位置插入指定为元素的角色。索引大于或等于当前位置的元素向上移动。
     * 
     * 
     * @param index  The position in the list where the new Role
     * object is to be inserted.
     * @param role  The Role object to be inserted.
     *
     * @exception IllegalArgumentException  if the role is null.
     * @exception IndexOutOfBoundsException  if accessing with an index
     * outside of the list.
     */
    public void add(int index,
                    Role role)
        throws IllegalArgumentException,
               IndexOutOfBoundsException {

        if (role == null) {
            String excMsg = "Invalid parameter";
            throw new IllegalArgumentException(excMsg);
        }

        super.add(index, role);
    }

    /**
     * Sets the element at the position specified to be the role
     * specified.
     * The previous element at that position is discarded.
     *
     * <p>
     *  将指定位置处的元素设置为指定的角色。该位置上的前一个元素被丢弃。
     * 
     * 
     * @param index  The position specified.
     * @param role  The value to which the role element should be set.
     *
     * @exception IllegalArgumentException  if the role is null.
     * @exception IndexOutOfBoundsException  if accessing with an index
     * outside of the list.
     */
     public void set(int index,
                     Role role)
         throws IllegalArgumentException,
                IndexOutOfBoundsException {

        if (role == null) {
            // Revisit [cebro] Localize message
            String excMsg = "Invalid parameter.";
            throw new IllegalArgumentException(excMsg);
        }

        super.set(index, role);
     }

    /**
     * Appends all the elements in the RoleList specified to the end
     * of the list, in the order in which they are returned by the Iterator of
     * the RoleList specified.
     *
     * <p>
     *  将指定的RoleList中的所有元素以指定的RoleList的迭代器返回的顺序追加到列表的末尾。
     * 
     * 
     * @param roleList  Elements to be inserted into the list (can be null)
     *
     * @return true if this list changed as a result of the call.
     *
     * @exception IndexOutOfBoundsException  if accessing with an index
     * outside of the list.
     *
     * @see ArrayList#addAll(Collection)
     */
    public boolean addAll(RoleList roleList)
        throws IndexOutOfBoundsException {

        if (roleList == null) {
            return true;
        }

        return (super.addAll(roleList));
    }

    /**
     * Inserts all of the elements in the RoleList specified into this
     * list, starting at the specified position, in the order in which they are
     * returned by the Iterator of the RoleList specified.
     *
     * <p>
     *  将指定的RoleList中的所有元素插入到此列表中,从指定的位置开始,按照它们由指定的RoleList的迭代器返回的顺序。
     * 
     * 
     * @param index  Position at which to insert the first element from the
     * RoleList specified.
     * @param roleList  Elements to be inserted into the list.
     *
     * @return true if this list changed as a result of the call.
     *
     * @exception IllegalArgumentException  if the role is null.
     * @exception IndexOutOfBoundsException  if accessing with an index
     * outside of the list.
     *
     * @see ArrayList#addAll(int, Collection)
     */
    public boolean addAll(int index,
                          RoleList roleList)
        throws IllegalArgumentException,
               IndexOutOfBoundsException {

        if (roleList == null) {
            // Revisit [cebro] Localize message
            String excMsg = "Invalid parameter.";
            throw new IllegalArgumentException(excMsg);
        }

        return (super.addAll(index, roleList));
    }

    /*
     * Override all of the methods from ArrayList<Object> that might add
     * a non-Role to the List, and disallow that if asList has ever
     * been called on this instance.
     * <p>
     *  覆盖ArrayList <Object>中可能向List添加非Role的所有方法,并禁止在此实例上调用asList。
     * 
     */

    @Override
    public boolean add(Object o) {
        if (!tainted)
            tainted = isTainted(o);
        if (typeSafe)
            checkTypeSafe(o);
        return super.add(o);
    }

    @Override
    public void add(int index, Object element) {
        if (!tainted)
            tainted = isTainted(element);
        if (typeSafe)
            checkTypeSafe(element);
        super.add(index, element);
    }

    @Override
    public boolean addAll(Collection<?> c) {
        if (!tainted)
            tainted = isTainted(c);
        if (typeSafe)
            checkTypeSafe(c);
        return super.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<?> c) {
        if (!tainted)
            tainted = isTainted(c);
        if (typeSafe)
            checkTypeSafe(c);
        return super.addAll(index, c);
    }

    @Override
    public Object set(int index, Object element) {
        if (!tainted)
            tainted = isTainted(element);
        if (typeSafe)
            checkTypeSafe(element);
        return super.set(index, element);
    }

    /**
     * IllegalArgumentException if o is a non-Role object.
     * <p>
     *  如果o是非Role对象,则为IllegalArgumentException。
     * 
     */
    private static void checkTypeSafe(Object o) {
        try {
            o = (Role) o;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * IllegalArgumentException if c contains any non-Role objects.
     * <p>
     *  IllegalArgumentException如果c包含任何非角色对象。
     * 
     */
    private static void checkTypeSafe(Collection<?> c) {
        try {
            Role r;
            for (Object o : c)
                r = (Role) o;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Returns true if o is a non-Role object.
     * <p>
     *  如果o是非Role对象,则返回true。
     * 
     */
    private static boolean isTainted(Object o) {
        try {
            checkTypeSafe(o);
        } catch (IllegalArgumentException e) {
            return true;
        }
        return false;
    }

    /**
     * Returns true if c contains any non-Role objects.
     * <p>
     *  如果c包含任何非角色对象,则返回true。
     */
    private static boolean isTainted(Collection<?> c) {
        try {
            checkTypeSafe(c);
        } catch (IllegalArgumentException e) {
            return true;
        }
        return false;
    }
}
