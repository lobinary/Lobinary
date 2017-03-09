/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.net;


/**
 * The class PasswordAuthentication is a data holder that is used by
 * Authenticator.  It is simply a repository for a user name and a password.
 *
 * <p>
 *  PasswordAuthentication类是Authenticator使用的数据存储器。它只是一个用户名和密码的存储库。
 * 
 * 
 * @see java.net.Authenticator
 * @see java.net.Authenticator#getPasswordAuthentication()
 *
 * @author  Bill Foote
 * @since   1.2
 */

public final class PasswordAuthentication {

    private String userName;
    private char[] password;

    /**
     * Creates a new {@code PasswordAuthentication} object from the given
     * user name and password.
     *
     * <p> Note that the given user password is cloned before it is stored in
     * the new {@code PasswordAuthentication} object.
     *
     * <p>
     *  从给定的用户名和密码创建一个新的{@code PasswordAuthentication}对象。
     * 
     *  <p>请注意,给定用户密码在存储在新的{@code PasswordAuthentication}对象之前已克隆。
     * 
     * 
     * @param userName the user name
     * @param password the user's password
     */
    public PasswordAuthentication(String userName, char[] password) {
        this.userName = userName;
        this.password = password.clone();
    }

    /**
     * Returns the user name.
     *
     * <p>
     *  返回用户名。
     * 
     * 
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Returns the user password.
     *
     * <p> Note that this method returns a reference to the password. It is
     * the caller's responsibility to zero out the password information after
     * it is no longer needed.
     *
     * <p>
     *  返回用户密码。
     * 
     * 
     * @return the password
     */
    public char[] getPassword() {
        return password;
    }
}
