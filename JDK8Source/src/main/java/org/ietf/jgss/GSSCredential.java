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

package org.ietf.jgss;

/**
 * This interface encapsulates the GSS-API credentials for an entity.  A
 * credential contains all the necessary cryptographic information to
 * enable the creation of a context on behalf of the entity that it
 * represents.  It may contain multiple, distinct, mechanism specific
 * credential elements, each containing information for a specific
 * security mechanism, but all referring to the same entity. A credential
 * may be used to perform context initiation, acceptance, or both.<p>
 *
 * Credentials are instantiated using one of the
 * <code>createCredential</code> methods in the {@link GSSManager
 * GSSManager} class. GSS-API credential creation is not
 * intended to provide a "login to the network" function, as such a
 * function would involve the creation of new credentials rather than
 * merely acquiring a handle to existing credentials. The
 * <a href=package-summary.html#useSubjectCredsOnly>section on credential
 * acquisition</a> in the package level description describes
 * how existing credentials are acquired in the Java platform. GSS-API
 * implementations must impose a local access-control policy on callers to
 * prevent unauthorized callers from acquiring credentials to which they
 * are not entitled. <p>
 *
 * Applications will create a credential object passing the desired
 * parameters.  The application can then use the query methods to obtain
 * specific information about the instantiated credential object.
 * When the credential is no longer needed, the application should call
 * the {@link #dispose() dispose} method to release any resources held by
 * the credential object and to destroy any cryptographically sensitive
 * information.<p>
 *
 * This example code demonstrates the creation of a GSSCredential
 * implementation for a specific entity, querying of its fields, and its
 * release when it is no longer needed:<p>
 * <pre>
 *    GSSManager manager = GSSManager.getInstance();
 *
 *    // start by creating a name object for the entity
 *    GSSName name = manager.createName("myusername", GSSName.NT_USER_NAME);
 *
 *    // now acquire credentials for the entity
 *    GSSCredential cred = manager.createCredential(name,
 *                    GSSCredential.ACCEPT_ONLY);
 *
 *    // display credential information - name, remaining lifetime,
 *    // and the mechanisms it has been acquired over
 *    System.out.println(cred.getName().toString());
 *    System.out.println(cred.getRemainingLifetime());
 *
 *    Oid [] mechs = cred.getMechs();
 *    if (mechs != null) {
 *            for (int i = 0; i < mechs.length; i++)
 *                    System.out.println(mechs[i].toString());
 *    }
 *
 *    // release system resources held by the credential
 *    cred.dispose();
 * </pre>
 *
 * <p>
 *  此接口封装实体的GSS-API凭证。凭证包含所有必要的加密信息,以便能够代表其所代表的实体创建上下文。
 * 它可以包含多个不同的机制特定的凭证元素,每个元素包含用于特定安全机制的信息,但是都指向相同的实体。证书可以用于执行上下文启动,接受或两者。
 * 
 *  凭证使用{@link GSSManager GSSManager}类中的<code> createCredential </code>方法之一进行实例化。
 *  GSS-API凭证创建不打算提供"登录到网络"功能,因为这样的功能将涉及创建新凭证而不是仅获取对现有凭证的句柄。
 * 包级别描述中的<a href=package-summary.html#useSubjectCredsOnly>部分了解凭证获取</a>,介绍如何在Java平台中获取现有凭据。
 *  GSS-API实现必须对呼叫者施加本地访问控制策略,以防止未经授权的呼叫者获得他们没有被授权的凭证。 <p>。
 * 
 * 应用程序将创建传递所需参数的凭据对象。应用程序然后可以使用查询方法来获得关于实例化的凭证对象的特定信息。
 * 当不再需要凭证时,应用程序应调用{@link #dispose()dispose}方法释放凭据对象持有的任何资源,并销毁任何加密敏感信息。<p>。
 * 
 *  此示例代码演示了为特定实体创建GSSCredential实现,查询其字段以及在不再需要时的发布情况：<p>
 * <pre>
 *  GSSManager manager = GSSManager.getInstance();
 * 
 *  //通过为实体创建名称对象启动GSSName name = manager.createName("myusername",GSSName.NT_USER_NAME);
 * 
 *  //现在获取实体的凭据GSSCredential cred = manager.createCredential(name,GSSCredential.ACCEPT_ONLY);
 * 
 *  //显示凭证信息 - 名称,剩余生命周期,以及通过System.out.println(cred.getName()。toString())获取的机制。
 *  System.out.println(cred.getRemainingLifetime());。
 * 
 *  Oid [] mechs = cred.getMechs(); if(mechs！= null){for(int i = 0; i <mechs.length; i ++)System.out.println(mechs [i] .toString()); }}。
 * 
 *  //释放由证书持有的系统资源cred.dispose();
 * </pre>
 * 
 * 
 * @see GSSManager#createCredential(int)
 * @see GSSManager#createCredential(GSSName, int, Oid, int)
 * @see GSSManager#createCredential(GSSName, int, Oid[], int)
 * @see #dispose()
 *
 * @author Mayank Upadhyay
 * @since 1.4
 */
public interface GSSCredential extends Cloneable{

    /**
     * Credential usage flag requesting that it be usable
     * for both context initiation and acceptance.
     *
     * <p>
     *  凭证使用标志,请求它可用于上下文启动和接受。
     * 
     */
    public static final int INITIATE_AND_ACCEPT = 0;


    /**
     * Credential usage flag requesting that it be usable
     * for context initiation only.
     *
     * <p>
     *  凭证使用标志,请求其仅可用于上下文启动。
     * 
     */
    public static final int INITIATE_ONLY = 1;


    /**
     * Credential usage flag requesting that it be usable
     * for context acceptance only.
     *
     * <p>
     * 凭证使用标志,请求其仅可用于上下文接受。
     * 
     */
    public static final int ACCEPT_ONLY = 2;


    /**
     * A lifetime constant representing the default credential lifetime. This
     * value it set to 0.
     * <p>
     *  表示默认凭据生存期的生存期常量。该值设置为0。
     * 
     */
    public static final int DEFAULT_LIFETIME = 0;

    /**
     * A lifetime constant representing indefinite credential lifetime.
     * This value must is set to the maximum integer value in Java -
     * {@link java.lang.Integer#MAX_VALUE Integer.MAX_VALUE}.
     * <p>
     *  寿命常数,代表不确定的凭证寿命。此值必须设置为Java中的最大整数值 -  {@link java.lang.Integer#MAX_VALUE Integer.MAX_VALUE}。
     * 
     */
    public static final int INDEFINITE_LIFETIME = Integer.MAX_VALUE;

    /**
     * Releases any sensitive information that the GSSCredential object may
     * be containing.  Applications should call this method as soon as the
     * credential is no longer needed to minimize the time any sensitive
     * information is maintained.
     *
     * <p>
     *  释放GSSCredential对象可能包含的任何敏感信息。应用程序应在不再需要凭证时尽快调用此方法,以最小化保留敏感信息的时间。
     * 
     * 
     * @throws GSSException containing the following
     * major error codes:
     *         {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public void dispose() throws GSSException;

    /**
     *  Retrieves the name of the entity that the credential asserts.
     *
     * <p>
     *  检索凭证断言的实体的名称。
     * 
     * 
     * @return a GSSName representing the entity
     *
     * @throws GSSException containing the following
     * major error codes:
     *         {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public GSSName getName() throws GSSException;

    /**
     * Retrieves a Mechanism Name of the entity that the credential
     * asserts. This is equivalent to calling {@link
     * GSSName#canonicalize(Oid) canonicalize} on the value returned by
     * the other form of {@link #getName() getName}.
     *
     * <p>
     *  检索机制该凭证断言的实体的名称。
     * 这相当于对{@link #getName()getName}其他形式返回的值调用{@link GSSName#canonicalize(Oid)canonicalize}。
     * 
     * 
     * @param mech the Oid of the mechanism for which the Mechanism Name
     * should be returned.
     * @return a GSSName representing the entity canonicalized for the
     * desired mechanism
     *
     * @throws GSSException containing the following
     * major error codes:
     *         {@link GSSException#BAD_MECH GSSException.BAD_MECH},
     *         {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public GSSName getName(Oid mech) throws GSSException;

    /**
     * Returns the remaining lifetime in seconds for a credential.  The
     * remaining lifetime is the minimum lifetime amongst all of the underlying
     * mechanism specific credential elements.
     *
     * <p>
     *  返回凭据的以秒为单位的剩余生存期。剩余寿命是所有底层机制特定凭证元素中的最小寿命。
     * 
     * 
     * @return the minimum remaining lifetime in seconds for this
     * credential. A return value of {@link #INDEFINITE_LIFETIME
     * INDEFINITE_LIFETIME} indicates that the credential does
     * not expire. A return value of 0 indicates that the credential is
     * already expired.
     *
     * @see #getRemainingInitLifetime(Oid)
     * @see #getRemainingAcceptLifetime(Oid)
     *
     * @throws GSSException containing the following
     * major error codes:
     *         {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public int getRemainingLifetime() throws GSSException;

    /**
     * Returns the lifetime in seconds for the credential to remain capable
     * of initiating security contexts using the specified mechanism. This
     * method queries the initiator credential element that belongs to the
     * specified mechanism.
     *
     * <p>
     *  返回凭证保持能够使用指定机制启动安全上下文的生存期(以秒为单位)。此方法查询属于指定机制的启动器凭据元素。
     * 
     * 
     * @return the number of seconds remaining in the life of this credential
     * element. A return value of {@link #INDEFINITE_LIFETIME
     * INDEFINITE_LIFETIME} indicates that the credential element does not
     * expire.  A return value of 0 indicates that the credential element is
     * already expired.
     *
     * @param mech the Oid of the mechanism whose initiator credential element
     * should be queried.
     *
     * @throws GSSException containing the following
     * major error codes:
     *         {@link GSSException#BAD_MECH GSSException.BAD_MECH},
     *         {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public int getRemainingInitLifetime(Oid mech) throws GSSException;

    /**
     * Returns the lifetime in seconds for the credential to remain capable
     * of accepting security contexts using the specified mechanism. This
     * method queries the acceptor credential element that belongs to the
     * specified mechanism.
     *
     * <p>
     *  返回凭证保持能够使用指定机制接受安全上下文的生存期(以秒为单位)。此方法查询属于指定机制的接受者凭据元素。
     * 
     * 
     * @return the number of seconds remaining in the life of this credential
     * element. A return value of {@link #INDEFINITE_LIFETIME
     * INDEFINITE_LIFETIME} indicates that the credential element does not
     * expire.  A return value of 0 indicates that the credential element is
     * already expired.
     *
     * @param mech the Oid of the mechanism whose acceptor credential element
     * should be queried.
     *
     * @throws GSSException containing the following
     * major error codes:
     *         {@link GSSException#BAD_MECH GSSException.BAD_MECH},
     *         {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public int getRemainingAcceptLifetime(Oid mech) throws GSSException;

    /**
     * Returns the credential usage mode. In other words, it
     * tells us if this credential can be used for initiating or accepting
     * security contexts. It does not tell us which mechanism(s) has to be
     * used in order to do so. It is expected that an application will allow
     * the GSS-API to pick a default mechanism after calling this method.
     *
     * <p>
     * 返回凭证使用模式。换句话说,它告诉我们这个凭证是否可以用于启动或接受安全上下文。它不告诉我们为了这样做必须使用哪些机制。期望应用程序将允许GSS-API在调用此方法后选择默认机制。
     * 
     * 
     * @return The return value will be one of {@link #INITIATE_ONLY
     * INITIATE_ONLY}, {@link #ACCEPT_ONLY ACCEPT_ONLY}, and {@link
     * #INITIATE_AND_ACCEPT INITIATE_AND_ACCEPT}.
     *
     * @throws GSSException containing the following
     * major error codes:
     *         {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public int getUsage() throws GSSException;

    /**
     * Returns the credential usage mode for a specific mechanism. In other
     * words, it tells us if this credential can be used
     * for initiating or accepting security contexts with a given underlying
     * mechanism.
     *
     * <p>
     *  返回特定机制的凭据使用模式。换句话说,它告诉我们这个凭证是否可以用于发起或接受给定底层机制的安全上下文。
     * 
     * 
     * @return The return value will be one of {@link #INITIATE_ONLY
     * INITIATE_ONLY}, {@link #ACCEPT_ONLY ACCEPT_ONLY}, and {@link
     * #INITIATE_AND_ACCEPT INITIATE_AND_ACCEPT}.
     * @param mech the Oid of the mechanism whose credentials usage mode is
     * to be determined.
     *
     * @throws GSSException containing the following
     * major error codes:
     *         {@link GSSException#BAD_MECH GSSException.BAD_MECH},
     *         {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public int getUsage(Oid mech) throws GSSException;

    /**
     * Returns a list of mechanisms supported by this credential. It does
     * not tell us which ones can be used to initiate
     * contexts and which ones can be used to accept contexts. The
     * application must call the {@link #getUsage(Oid) getUsage} method with
     * each of the returned Oid's to determine the possible modes of
     * usage.
     *
     * <p>
     *  返回此凭据支持的机制列表。它不告诉我们哪些可以用于启动上下文,哪些可以用于接受上下文。
     * 应用程序必须使用每个返回的Oid调用{@link #getUsage(Oid)getUsage}方法,以确定可能的使用模式。
     * 
     * 
     * @return an array of Oid's corresponding to the supported mechanisms.
     *
     * @throws GSSException containing the following
     * major error codes:
     *         {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public Oid[] getMechs() throws GSSException;

    /**
     * Adds a mechanism specific credential-element to an existing
     * credential.  This method allows the construction of credentials, one
     * mechanism at a time.<p>
     *
     * This routine is envisioned to be used mainly by context acceptors
     * during the creation of acceptor credentials which are to be used
     * with a variety of clients using different security mechanisms.<p>
     *
     * This routine adds the new credential element "in-place".  To add the
     * element in a new credential, first call <code>clone</code> to obtain a
     * copy of this credential, then call its <code>add</code> method.<p>
     *
     * As always, GSS-API implementations must impose a local access-control
     * policy on callers to prevent unauthorized callers from acquiring
     * credentials to which they are not entitled.
     *
     * Non-default values for initLifetime and acceptLifetime cannot always
     * be honored by the underlying mechanisms, thus callers should be
     * prepared to call {@link #getRemainingInitLifetime(Oid)
     * getRemainingInitLifetime} and {@link #getRemainingAcceptLifetime(Oid)
     * getRemainingAcceptLifetime} on the credential.
     *
     * <p>
     *  向现有凭据添加机制特定凭证元素。这种方法允许建立凭证,一次一个机制。<p>
     * 
     *  该例程被设想为主要由上下文接受器在创建将与使用不同安全机制的各种客户端一起使用的接受者凭证时使用。<p>
     * 
     *  此例程添加新的凭证元素"就地"。要在新凭证中添加元素,请先调用<code> clone </code>以获取此凭证的副本,然后调用<code> add </code>方法。<p>
     * 
     * 与往常一样,GSS-API实现必须对呼叫者施加本地访问控制策略,以防止未经授权的呼叫者获取他们没有被授权的凭证。
     * 
     *  initLifetime和acceptLifetime的非默认值不能总是被底层机制支持,因此调用者应该准备在凭据上调用{@link #getRemainingInitLifetime(Oid)getRemainingInitLifetime}
     * 
     * @param name the name of the principal for whom this credential is to
     * be acquired.  Use <code>null</code> to specify the default
     * principal.
     * @param initLifetime the number of seconds that the credential element
     * should remain valid for initiating of security contexts. Use {@link
     * GSSCredential#INDEFINITE_LIFETIME GSSCredential.INDEFINITE_LIFETIME}
     * to request that the credentials have the maximum permitted lifetime
     * for this.  Use {@link GSSCredential#DEFAULT_LIFETIME
     * GSSCredential.DEFAULT_LIFETIME} to request default credential lifetime
     * for this.
     * @param acceptLifetime the number of seconds that the credential
     * element should remain valid for accepting security contexts. Use {@link
     * GSSCredential#INDEFINITE_LIFETIME GSSCredential.INDEFINITE_LIFETIME}
     * to request that the credentials have the maximum permitted lifetime
     * for this.  Use {@link GSSCredential#DEFAULT_LIFETIME
     * GSSCredential.DEFAULT_LIFETIME} to request default credential lifetime
     * for this.
     * @param mech the mechanism over which the credential is to be acquired.
     * @param usage the usage mode that this credential
     * element should add to the credential. The value
     * of this parameter must be one of:
     * {@link #INITIATE_AND_ACCEPT INITIATE_AND_ACCEPT},
     * {@link #ACCEPT_ONLY ACCEPT_ONLY}, and
     * {@link #INITIATE_ONLY INITIATE_ONLY}.
     *
     * @throws GSSException containing the following
     * major error codes:
     *         {@link GSSException#DUPLICATE_ELEMENT
     *                          GSSException.DUPLICATE_ELEMENT},
     *         {@link GSSException#BAD_MECH GSSException.BAD_MECH},
     *         {@link GSSException#BAD_NAMETYPE GSSException.BAD_NAMETYPE},
     *         {@link GSSException#NO_CRED GSSException.NO_CRED},
     *         {@link GSSException#CREDENTIALS_EXPIRED
     *                                  GSSException.CREDENTIALS_EXPIRED},
     *         {@link GSSException#FAILURE GSSException.FAILURE}
     */
    public void add(GSSName name, int initLifetime, int acceptLifetime,
                    Oid mech, int usage) throws GSSException;

    /**
     * Tests if this GSSCredential asserts the same entity as the supplied
     * object.  The two credentials must be acquired over the same
     * mechanisms and must refer to the same principal.
     *
     * <p>
     * 和{@link #getRemainingAcceptLifetime(Oid)getRemainingAcceptLifetime}。
     * 
     * 
     * @return <code>true</code> if the two GSSCredentials assert the same
     * entity; <code>false</code> otherwise.
     * @param another another GSSCredential for comparison to this one
     */
    public boolean equals(Object another);

    /**
     * Returns a hashcode value for this GSSCredential.
     *
     * <p>
     *  测试此GSSCredential是否声明与提供的对象相同的实体。这两个凭证必须通过相同的机制获取,并且必须引用相同的主体。
     * 
     * 
     * @return a hashCode value
     */
    public int hashCode();

}
