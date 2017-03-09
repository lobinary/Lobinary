/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.List;
import java.util.Map;

import javax.management.ObjectName;

/**
 * This interface has to be implemented by any MBean class expected to
 * represent a relation managed using the Relation Service.
 * <P>Simple relations, i.e. having only roles, no properties or methods, can
 * be created directly by the Relation Service (represented as RelationSupport
 * objects, internally handled by the Relation Service).
 * <P>If the user wants to represent more complex relations, involving
 * properties and/or methods, he has to provide his own class implementing the
 * Relation interface. This can be achieved either by inheriting from
 * RelationSupport class, or by implementing the interface (fully or delegation to
 * a RelationSupport object member).
 * <P>Specifying such user relation class is to introduce properties and/or
 * methods. Those have to be exposed for remote management. So this means that
 * any user relation class must be a MBean class.
 *
 * <p>
 *  该接口必须由预期代表使用关系服务管理的关系的任何MBean类实现。
 *  <P>简单关系,即只有角色,没有属性或方法,可以由关系服务(表示为RelationSupport对象,由关系服务内部处理)直接创建。
 * 如果用户想要表示更复杂的关系,涉及属性和/或方法,则他必须提供实现关系接口的他自己的类。
 * 这可以通过继承RelationSupport类,或通过实现接口(完全或委托到RelationSupport对象成员)来实现。指定这样的用户关系类是引入属性和/或方法。这些必须暴露给远程管理。
 * 所以这意味着任何用户关系类都必须是MBean类。
 * 
 * 
 * @since 1.5
 */
public interface Relation {

    /**
     * Retrieves role value for given role name.
     * <P>Checks if the role exists and is readable according to the relation
     * type.
     *
     * <p>
     *  检索给定角色名称的角色值。 <P>检查角色是否存在,并根据关系类型可读。
     * 
     * 
     * @param roleName  name of role
     *
     * @return the ArrayList of ObjectName objects being the role value
     *
     * @exception IllegalArgumentException  if null role name
     * @exception RoleNotFoundException  if:
     * <P>- there is no role with given name
     * <P>- the role is not readable.
     * @exception RelationServiceNotRegisteredException  if the Relation
     * Service is not registered in the MBean Server
     *
     * @see #setRole
     */
    public List<ObjectName> getRole(String roleName)
        throws IllegalArgumentException,
               RoleNotFoundException,
               RelationServiceNotRegisteredException;

    /**
     * Retrieves values of roles with given names.
     * <P>Checks for each role if it exists and is readable according to the
     * relation type.
     *
     * <p>
     *  检索具有给定名称的角色的值。 <P>检查每个角色是否存在,并根据关系类型可读。
     * 
     * 
     * @param roleNameArray  array of names of roles to be retrieved
     *
     * @return a RoleResult object, including a RoleList (for roles
     * successfully retrieved) and a RoleUnresolvedList (for roles not
     * retrieved).
     *
     * @exception IllegalArgumentException  if null role name
     * @exception RelationServiceNotRegisteredException  if the Relation
     * Service is not registered in the MBean Server
     *
     * @see #setRoles
     */
    public RoleResult getRoles(String[] roleNameArray)
        throws IllegalArgumentException,
               RelationServiceNotRegisteredException;

    /**
     * Returns the number of MBeans currently referenced in the given role.
     *
     * <p>
     *  返回当前在给定角色中引用的MBean数。
     * 
     * 
     * @param roleName  name of role
     *
     * @return the number of currently referenced MBeans in that role
     *
     * @exception IllegalArgumentException  if null role name
     * @exception RoleNotFoundException  if there is no role with given name
     */
    public Integer getRoleCardinality(String roleName)
        throws IllegalArgumentException,
               RoleNotFoundException;

    /**
     * Returns all roles present in the relation.
     *
     * <p>
     *  返回关系中存在的所有角色。
     * 
     * 
     * @return a RoleResult object, including a RoleList (for roles
     * successfully retrieved) and a RoleUnresolvedList (for roles not
     * readable).
     *
     * @exception RelationServiceNotRegisteredException  if the Relation
     * Service is not registered in the MBean Server
     */
    public RoleResult getAllRoles()
        throws RelationServiceNotRegisteredException;

    /**
     * Returns all roles in the relation without checking read mode.
     *
     * <p>
     *  返回关系中的所有角色,而不检查读取模式。
     * 
     * 
     * @return a RoleList.
     */
    public RoleList retrieveAllRoles();

    /**
     * Sets the given role.
     * <P>Will check the role according to its corresponding role definition
     * provided in relation's relation type
     * <P>Will send a notification (RelationNotification with type
     * RELATION_BASIC_UPDATE or RELATION_MBEAN_UPDATE, depending if the
     * relation is a MBean or not).
     *
     * <p>
     * 设置给定的角色。
     *  <P>将根据其关系类型中提供的相应角色定义检查角色<P>将发送通知(具有类型RELATION_BASIC_UPDATE或RELATION_MBEAN_UPDATE的RelationNotificati
     * on,取决于该关系是否是MBean)。
     * 设置给定的角色。
     * 
     * 
     * @param role  role to be set (name and new value)
     *
     * @exception IllegalArgumentException  if null role
     * @exception RoleNotFoundException  if there is no role with the supplied
     * role's name or if the role is not writable (no test on the write access
     * mode performed when initializing the role)
     * @exception InvalidRoleValueException  if value provided for
     * role is not valid, i.e.:
     * <P>- the number of referenced MBeans in given value is less than
     * expected minimum degree
     * <P>- the number of referenced MBeans in provided value exceeds expected
     * maximum degree
     * <P>- one referenced MBean in the value is not an Object of the MBean
     * class expected for that role
     * <P>- a MBean provided for that role does not exist.
     * @exception RelationServiceNotRegisteredException  if the Relation
     * Service is not registered in the MBean Server
     * @exception RelationTypeNotFoundException  if the relation type has not
     * been declared in the Relation Service.
     * @exception RelationNotFoundException  if the relation has not been
     * added in the Relation Service.
     *
     * @see #getRole
     */
    public void setRole(Role role)
        throws IllegalArgumentException,
               RoleNotFoundException,
               RelationTypeNotFoundException,
               InvalidRoleValueException,
               RelationServiceNotRegisteredException,
               RelationNotFoundException;

    /**
     * Sets the given roles.
     * <P>Will check the role according to its corresponding role definition
     * provided in relation's relation type
     * <P>Will send one notification (RelationNotification with type
     * RELATION_BASIC_UPDATE or RELATION_MBEAN_UPDATE, depending if the
     * relation is a MBean or not) per updated role.
     *
     * <p>
     *  设置指定的角色。
     *  <P>将根据每个更新的角色发送一个通知(关系类型为RELATION_BASIC_UPDATE或RELATION_MBEAN_UPDATE,取决于关系是否为MBean),根据关系类型<P>中提供的相应角
     * 色定义检查角色。
     *  设置指定的角色。
     * 
     * 
     * @param roleList  list of roles to be set
     *
     * @return a RoleResult object, including a RoleList (for roles
     * successfully set) and a RoleUnresolvedList (for roles not
     * set).
     *
     * @exception IllegalArgumentException  if null role list
     * @exception RelationServiceNotRegisteredException  if the Relation
     * Service is not registered in the MBean Server
     * @exception RelationTypeNotFoundException  if the relation type has not
     * been declared in the Relation Service.
     * @exception RelationNotFoundException  if the relation MBean has not been
     * added in the Relation Service.
     *
     * @see #getRoles
     */
    public RoleResult setRoles(RoleList roleList)
        throws IllegalArgumentException,
               RelationServiceNotRegisteredException,
               RelationTypeNotFoundException,
               RelationNotFoundException;

    /**
     * Callback used by the Relation Service when a MBean referenced in a role
     * is unregistered.
     * <P>The Relation Service will call this method to let the relation
     * take action to reflect the impact of such unregistration.
     * <P>BEWARE. the user is not expected to call this method.
     * <P>Current implementation is to set the role with its current value
     * (list of ObjectNames of referenced MBeans) without the unregistered
     * one.
     *
     * <p>
     *  在角色中引用的MBean未注册时,关系服务使用的回调。 <P>关系服务将调用此方法让关系采取行动以反映此类注销的影响。 <P>小心。用户不希望调用此方法。
     *  <P>当前实现是使用其当前值(引用MBean的ObjectName的列表)设置角色,而不使用未注册的。
     * 
     * 
     * @param objectName  ObjectName of unregistered MBean
     * @param roleName  name of role where the MBean is referenced
     *
     * @exception IllegalArgumentException  if null parameter
     * @exception RoleNotFoundException  if role does not exist in the
     * relation or is not writable
     * @exception InvalidRoleValueException  if role value does not conform to
     * the associated role info (this will never happen when called from the
     * Relation Service)
     * @exception RelationServiceNotRegisteredException  if the Relation
     * Service is not registered in the MBean Server
     * @exception RelationTypeNotFoundException  if the relation type has not
     * been declared in the Relation Service.
     * @exception RelationNotFoundException  if this method is called for a
     * relation MBean not added in the Relation Service.
     */
    public void handleMBeanUnregistration(ObjectName objectName,
                                          String roleName)
        throws IllegalArgumentException,
               RoleNotFoundException,
               InvalidRoleValueException,
               RelationServiceNotRegisteredException,
               RelationTypeNotFoundException,
               RelationNotFoundException;

    /**
     * Retrieves MBeans referenced in the various roles of the relation.
     *
     * <p>
     *  检索在关系的各种角色中引用的MBean。
     * 
     * 
     * @return a HashMap mapping:
     * <P> ObjectName {@literal ->} ArrayList of String (role names)
     */
    public Map<ObjectName,List<String>> getReferencedMBeans();

    /**
     * Returns name of associated relation type.
     *
     * <p>
     *  返回关联关系类型的名称。
     * 
     * 
     * @return the name of the relation type.
     */
    public String getRelationTypeName();

    /**
     * Returns ObjectName of the Relation Service handling the relation.
     *
     * <p>
     *  返回处理关系的关系服务的ObjectName。
     * 
     * 
     * @return the ObjectName of the Relation Service.
     */
    public ObjectName getRelationServiceName();

    /**
     * Returns relation identifier (used to uniquely identify the relation
     * inside the Relation Service).
     *
     * <p>
     *  返回关系标识符(用于唯一标识关系服务中的关系)。
     * 
     * @return the relation id.
     */
    public String getRelationId();
}
