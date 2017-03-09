/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2005, Oracle and/or its affiliates. All rights reserved.
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

/**
 * A RelationSupport object is used internally by the Relation Service to
 * represent simple relations (only roles, no properties or methods), with an
 * unlimited number of roles, of any relation type. As internal representation,
 * it is not exposed to the user.
 * <P>RelationSupport class conforms to the design patterns of standard MBean. So
 * the user can decide to instantiate a RelationSupport object himself as
 * a MBean (as it follows the MBean design patterns), to register it in the
 * MBean Server, and then to add it in the Relation Service.
 * <P>The user can also, when creating his own MBean relation class, have it
 * extending RelationSupport, to retrieve the implementations of required
 * interfaces (see below).
 * <P>It is also possible to have in a user relation MBean class a member
 * being a RelationSupport object, and to implement the required interfaces by
 * delegating all to this member.
 * <P> RelationSupport implements the Relation interface (to be handled by the
 * Relation Service).
 *
 * <p>
 *  RelationSupport对象由Relation Service内部使用,用于表示任何关系类型的无限数量角色的简单关系(仅角色,无属性或方法)。作为内部表示,它不暴露给用户。
 *  <P> RelationSupport类符合标准MBean的设计模式。
 * 因此,用户可以决定将RelationSupport对象本身实例化为MBean(因为它遵循MBean设计模式),将其注册到MBean Server中,然后将其添加到关系服务中。
 *  <P>用户还可以在创建自己的MBean关系类时,扩展RelationSupport,以检索所需接口的实现(见下文)。
 * 还可以在用户关系MBean类中具有作为RelationSupport对象的成员,并且通过将所有对象委托给该成员来实现所需的接口。 <P> RelationSupport实现关系接口(由关系服务处理)。
 * 
 * 
 * @since 1.5
 */
public interface RelationSupportMBean
    extends Relation {

    /**
     * Returns an internal flag specifying if the object is still handled by
     * the Relation Service.
     *
     * <p>
     *  返回一个内部标志,指定对象是否仍然由关系服务处理。
     * 
     * 
     * @return a Boolean equal to {@link Boolean#TRUE} if the object
     * is still handled by the Relation Service and {@link
     * Boolean#FALSE} otherwise.
     */
    public Boolean isInRelationService();

    /**
     * <p>Specifies whether this relation is handled by the Relation
     * Service.</p>
     * <P>BEWARE, this method has to be exposed as the Relation Service will
     * access the relation through its management interface. It is RECOMMENDED
     * NOT to use this method. Using it does not affect the registration of the
     * relation object in the Relation Service, but will provide wrong
     * information about it!
     *
     * <p>
     * <p>指定此关系是否由关系服务处理。</p> <P> BEWARE,此方法必须公开,因为关系服务将通过其管理接口访问关系。建议不要使用此方法。
     * 使用它不会影响关系对象在关系服务中的注册,但会提供错误的信息！。
     * 
     * @param flag whether the relation is handled by the Relation Service.
     *
     * @exception IllegalArgumentException  if null parameter
     */
    public void setRelationServiceManagementFlag(Boolean flag)
        throws IllegalArgumentException;
}
