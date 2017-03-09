/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2007, 2009, Oracle and/or its affiliates. All rights reserved.
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

package java.nio.file.attribute;

import java.io.IOException;

/**
 * Checked exception thrown when a lookup of {@link UserPrincipal} fails because
 * the principal does not exist.
 *
 * <p>
 *  当{@link UserPrincipal}的查找失败,因为主体不存在时抛出检查异常。
 * 
 * 
 * @since 1.7
 */

public class UserPrincipalNotFoundException
    extends IOException
{
    static final long serialVersionUID = -5369283889045833024L;

    private final String name;

    /**
     * Constructs an instance of this class.
     *
     * <p>
     *  构造此类的实例。
     * 
     * 
     * @param   name
     *          the principal name; may be {@code null}
     */
    public UserPrincipalNotFoundException(String name) {
        super();
        this.name = name;
    }

    /**
     * Returns the user principal name if this exception was created with the
     * user principal name that was not found, otherwise <tt>null</tt>.
     *
     * <p>
     *  如果使用未找到的用户主体名称创建此异常,则返回用户主体名称,否则<tt> null </tt>。
     * 
     * @return  the user principal name or {@code null}
     */
    public String getName() {
        return name;
    }
}
