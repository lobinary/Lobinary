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

package javax.security.sasl;

import javax.security.auth.callback.Callback;

/**
  * This callback is used by {@code SaslServer} to determine whether
  * one entity (identified by an authenticated authentication id)
  * can act on
  * behalf of another entity (identified by an authorization id).
  *
  * <p>
  *  {@code SaslServer}使用此回调来确定一个实体(由经过身份验证的身份验证id标识)是否可以代表另一个实体(由授权id标识)。
  * 
  * 
  * @since 1.5
  *
  * @author Rosanna Lee
  * @author Rob Weltman
  */
public class AuthorizeCallback implements Callback, java.io.Serializable {
    /**
     * The (authenticated) authentication id to check.
     * <p>
     *  要检查的(已验证)身份验证ID。
     * 
     * 
     * @serial
     */
    private String authenticationID;

    /**
     * The authorization id to check.
     * <p>
     *  要检查的授权ID。
     * 
     * 
     * @serial
     */
    private String authorizationID;

    /**
     * The id of the authorized entity. If null, the id of
     * the authorized entity is authorizationID.
     * <p>
     *  授权实体的ID。如果为null,则授权实体的id为authorizationID。
     * 
     * 
     * @serial
     */
    private String authorizedID;

    /**
     * A flag indicating whether the authentication id is allowed to
     * act on behalf of the authorization id.
     * <p>
     *  指示是否允许认证id代表授权id操作的标志。
     * 
     * 
     * @serial
     */
    private boolean authorized;

    /**
     * Constructs an instance of {@code AuthorizeCallback}.
     *
     * <p>
     *  构造{@code AuthorizeCallback}的实例。
     * 
     * 
     * @param authnID   The (authenticated) authentication id.
     * @param authzID   The authorization id.
     */
    public AuthorizeCallback(String authnID, String authzID) {
        authenticationID = authnID;
        authorizationID = authzID;
    }

    /**
     * Returns the authentication id to check.
     * <p>
     *  返回要检查的身份验证ID。
     * 
     * 
     * @return The authentication id to check.
     */
    public String getAuthenticationID() {
        return authenticationID;
    }

    /**
     * Returns the authorization id to check.
     * <p>
     *  返回要检查的授权ID。
     * 
     * 
     * @return The authentication id to check.
     */
    public String getAuthorizationID() {
        return authorizationID;
    }

    /**
     * Determines whether the authentication id is allowed to
     * act on behalf of the authorization id.
     *
     * <p>
     *  确定是否允许身份验证ID代表授权ID操作。
     * 
     * 
     * @return {@code true} if authorization is allowed; {@code false} otherwise
     * @see #setAuthorized(boolean)
     * @see #getAuthorizedID()
     */
    public boolean isAuthorized() {
        return authorized;
    }

    /**
     * Sets whether the authorization is allowed.
     * <p>
     *  设置是否允许授权。
     * 
     * 
     * @param ok {@code true} if authorization is allowed; {@code false} otherwise
     * @see #isAuthorized
     * @see #setAuthorizedID(java.lang.String)
     */
    public void setAuthorized(boolean ok) {
        authorized = ok;
    }

    /**
     * Returns the id of the authorized user.
     * <p>
     *  返回授权用户的ID。
     * 
     * 
     * @return The id of the authorized user. {@code null} means the
     * authorization failed.
     * @see #setAuthorized(boolean)
     * @see #setAuthorizedID(java.lang.String)
     */
    public String getAuthorizedID() {
        if (!authorized) {
            return null;
        }
        return (authorizedID == null) ? authorizationID : authorizedID;
    }

    /**
     * Sets the id of the authorized entity. Called by handler only when the id
     * is different from getAuthorizationID(). For example, the id
     * might need to be canonicalized for the environment in which it
     * will be used.
     * <p>
     *  设置授权实体的ID。仅当id与getAuthorizationID()不同时由处理程序调用。例如,id可能需要为其将使用的环境进行规范化。
     * 
     * @param id The id of the authorized user.
     * @see #setAuthorized(boolean)
     * @see #getAuthorizedID
     */
    public void setAuthorizedID(String id) {
        authorizedID = id;
    }

    private static final long serialVersionUID = -2353344186490470805L;
}
