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

import java.util.ArrayList; // for Javadoc
import java.util.List;

import java.io.Serializable;

/**
 * The RelationType interface has to be implemented by any class expected to
 * represent a relation type.
 *
 * <p>
 *  RelationType接口必须由期望表示关系类型的任何类实现。
 * 
 * 
 * @since 1.5
 */
public interface RelationType extends Serializable {

    //
    // Accessors
    //

    /**
     * Returns the relation type name.
     *
     * <p>
     *  返回关系类型名称。
     * 
     * 
     * @return the relation type name.
     */
    public String getRelationTypeName();

    /**
     * Returns the list of role definitions (ArrayList of RoleInfo objects).
     *
     * <p>
     *  返回角色定义的列表(RoleInfo对象的ArrayList)。
     * 
     * 
     * @return an {@link ArrayList} of {@link RoleInfo}.
     */
    public List<RoleInfo> getRoleInfos();

    /**
     * Returns the role info (RoleInfo object) for the given role info name
     * (null if not found).
     *
     * <p>
     *  返回给定角色信息名称(如果未找到,则为null)的角色信息(RoleInfo对象)。
     * 
     * @param roleInfoName  role info name
     *
     * @return RoleInfo object providing role definition
     * does not exist
     *
     * @exception IllegalArgumentException  if null parameter
     * @exception RoleInfoNotFoundException  if no role info with that name in
     * relation type.
     */
    public RoleInfo getRoleInfo(String roleInfoName)
        throws IllegalArgumentException,
               RoleInfoNotFoundException;
}
