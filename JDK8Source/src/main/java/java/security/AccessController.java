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

package java.security;

import sun.security.util.Debug;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

/**
 * <p> The AccessController class is used for access control operations
 * and decisions.
 *
 * <p> More specifically, the AccessController class is used for
 * three purposes:
 *
 * <ul>
 * <li> to decide whether an access to a critical system
 * resource is to be allowed or denied, based on the security policy
 * currently in effect,
 * <li>to mark code as being "privileged", thus affecting subsequent
 * access determinations, and
 * <li>to obtain a "snapshot" of the current calling context so
 * access-control decisions from a different context can be made with
 * respect to the saved context. </ul>
 *
 * <p> The {@link #checkPermission(Permission) checkPermission} method
 * determines whether the access request indicated by a specified
 * permission should be granted or denied. A sample call appears
 * below. In this example, {@code checkPermission} will determine
 * whether or not to grant "read" access to the file named "testFile" in
 * the "/temp" directory.
 *
 * <pre>
 *
 * FilePermission perm = new FilePermission("/temp/testFile", "read");
 * AccessController.checkPermission(perm);
 *
 * </pre>
 *
 * <p> If a requested access is allowed,
 * {@code checkPermission} returns quietly. If denied, an
 * AccessControlException is
 * thrown. AccessControlException can also be thrown if the requested
 * permission is of an incorrect type or contains an invalid value.
 * Such information is given whenever possible.
 *
 * Suppose the current thread traversed m callers, in the order of caller 1
 * to caller 2 to caller m. Then caller m invoked the
 * {@code checkPermission} method.
 * The {@code checkPermission} method determines whether access
 * is granted or denied based on the following algorithm:
 *
 *  <pre> {@code
 * for (int i = m; i > 0; i--) {
 *
 *     if (caller i's domain does not have the permission)
 *         throw AccessControlException
 *
 *     else if (caller i is marked as privileged) {
 *         if (a context was specified in the call to doPrivileged)
 *             context.checkPermission(permission)
 *         if (limited permissions were specified in the call to doPrivileged) {
 *             for (each limited permission) {
 *                 if (the limited permission implies the requested permission)
 *                     return;
 *             }
 *         } else
 *             return;
 *     }
 * }
 *
 * // Next, check the context inherited when the thread was created.
 * // Whenever a new thread is created, the AccessControlContext at
 * // that time is stored and associated with the new thread, as the
 * // "inherited" context.
 *
 * inheritedContext.checkPermission(permission);
 * }</pre>
 *
 * <p> A caller can be marked as being "privileged"
 * (see {@link #doPrivileged(PrivilegedAction) doPrivileged} and below).
 * When making access control decisions, the {@code checkPermission}
 * method stops checking if it reaches a caller that
 * was marked as "privileged" via a {@code doPrivileged}
 * call without a context argument (see below for information about a
 * context argument). If that caller's domain has the
 * specified permission and at least one limiting permission argument (if any)
 * implies the requested permission, no further checking is done and
 * {@code checkPermission}
 * returns quietly, indicating that the requested access is allowed.
 * If that domain does not have the specified permission, an exception
 * is thrown, as usual. If the caller's domain had the specified permission
 * but it was not implied by any limiting permission arguments given in the call
 * to {@code doPrivileged} then the permission checking continues
 * until there are no more callers or another {@code doPrivileged}
 * call matches the requested permission and returns normally.
 *
 * <p> The normal use of the "privileged" feature is as follows. If you
 * don't need to return a value from within the "privileged" block, do
 * the following:
 *
 *  <pre> {@code
 * somemethod() {
 *     ...normal code here...
 *     AccessController.doPrivileged(new PrivilegedAction<Void>() {
 *         public Void run() {
 *             // privileged code goes here, for example:
 *             System.loadLibrary("awt");
 *             return null; // nothing to return
 *         }
 *     });
 *     ...normal code here...
 * }}</pre>
 *
 * <p>
 * PrivilegedAction is an interface with a single method, named
 * {@code run}.
 * The above example shows creation of an implementation
 * of that interface; a concrete implementation of the
 * {@code run} method is supplied.
 * When the call to {@code doPrivileged} is made, an
 * instance of the PrivilegedAction implementation is passed
 * to it. The {@code doPrivileged} method calls the
 * {@code run} method from the PrivilegedAction
 * implementation after enabling privileges, and returns the
 * {@code run} method's return value as the
 * {@code doPrivileged} return value (which is
 * ignored in this example).
 *
 * <p> If you need to return a value, you can do something like the following:
 *
 *  <pre> {@code
 * somemethod() {
 *     ...normal code here...
 *     String user = AccessController.doPrivileged(
 *         new PrivilegedAction<String>() {
 *         public String run() {
 *             return System.getProperty("user.name");
 *             }
 *         });
 *     ...normal code here...
 * }}</pre>
 *
 * <p>If the action performed in your {@code run} method could
 * throw a "checked" exception (those listed in the {@code throws} clause
 * of a method), then you need to use the
 * {@code PrivilegedExceptionAction} interface instead of the
 * {@code PrivilegedAction} interface:
 *
 *  <pre> {@code
 * somemethod() throws FileNotFoundException {
 *     ...normal code here...
 *     try {
 *         FileInputStream fis = AccessController.doPrivileged(
 *         new PrivilegedExceptionAction<FileInputStream>() {
 *             public FileInputStream run() throws FileNotFoundException {
 *                 return new FileInputStream("someFile");
 *             }
 *         });
 *     } catch (PrivilegedActionException e) {
 *         // e.getException() should be an instance of FileNotFoundException,
 *         // as only "checked" exceptions will be "wrapped" in a
 *         // PrivilegedActionException.
 *         throw (FileNotFoundException) e.getException();
 *     }
 *     ...normal code here...
 *  }}</pre>
 *
 * <p> Be *very* careful in your use of the "privileged" construct, and
 * always remember to make the privileged code section as small as possible.
 * You can pass {@code Permission} arguments to further limit the
 * scope of the "privilege" (see below).
 *
 *
 * <p> Note that {@code checkPermission} always performs security checks
 * within the context of the currently executing thread.
 * Sometimes a security check that should be made within a given context
 * will actually need to be done from within a
 * <i>different</i> context (for example, from within a worker thread).
 * The {@link #getContext() getContext} method and
 * AccessControlContext class are provided
 * for this situation. The {@code getContext} method takes a "snapshot"
 * of the current calling context, and places
 * it in an AccessControlContext object, which it returns. A sample call is
 * the following:
 *
 * <pre>
 *
 * AccessControlContext acc = AccessController.getContext()
 *
 * </pre>
 *
 * <p>
 * AccessControlContext itself has a {@code checkPermission} method
 * that makes access decisions based on the context <i>it</i> encapsulates,
 * rather than that of the current execution thread.
 * Code within a different context can thus call that method on the
 * previously-saved AccessControlContext object. A sample call is the
 * following:
 *
 * <pre>
 *
 * acc.checkPermission(permission)
 *
 * </pre>
 *
 * <p> There are also times where you don't know a priori which permissions
 * to check the context against. In these cases you can use the
 * doPrivileged method that takes a context. You can also limit the scope
 * of the privileged code by passing additional {@code Permission}
 * parameters.
 *
 *  <pre> {@code
 * somemethod() {
 *     AccessController.doPrivileged(new PrivilegedAction<Object>() {
 *         public Object run() {
 *             // Code goes here. Any permission checks within this
 *             // run method will require that the intersection of the
 *             // caller's protection domain and the snapshot's
 *             // context have the desired permission. If a requested
 *             // permission is not implied by the limiting FilePermission
 *             // argument then checking of the thread continues beyond the
 *             // caller of doPrivileged.
 *         }
 *     }, acc, new FilePermission("/temp/*", read));
 *     ...normal code here...
 * }}</pre>
 * <p> Passing a limiting {@code Permission} argument of an instance of
 * {@code AllPermission} is equivalent to calling the equivalent
 * {@code doPrivileged} method without limiting {@code Permission}
 * arguments. Passing a zero length array of {@code Permission} disables
 * the code privileges so that checking always continues beyond the caller of
 * that {@code doPrivileged} method.
 *
 * <p>
 *  <p> AccessController类用于访问控制操作和决策。
 * 
 *  <p>更具体地说,AccessController类用于三个目的：
 * 
 * <ul>
 *  <li>基于当前有效的安全策略<li>来决定是否允许或拒绝对关键系统资源的访问,以将代码标记为"特权",从而影响后续访问确定,<li> >以获得当前调用上下文的"快照",因此可以相对于所保存的上下文进
 * 行来自不同上下文的访问控制决策。
 *  </ul>。
 * 
 *  <p> {@link #checkPermission(Permission)checkPermission}方法决定是否授予或拒绝指定权限所指示的存取要求。示例呼叫如下所示。
 * 在本示例中,{@code checkPermission}将确定是否向"/ temp"目录中名为"testFile"的文件授予"读取"访问权限。
 * 
 * <pre>
 * 
 *  FilePermission perm = new FilePermission("/ temp / testFile","read"); AccessController.checkPermissi
 * on(perm);。
 * 
 * </pre>
 * 
 *  <p>如果允许请求访问,{@code checkPermission}会安静返回。如果拒绝,则抛出AccessControlException。
 * 如果请求的权限类型不正确或包含无效值,那么也可能抛出AccessControlException。尽可能提供此类信息。
 * 
 * 假设当前线程遍历m个调用者,按照调用者1到调用者2到调用者m的顺序。然后调用者m调用{@code checkPermission}方法。
 *  {@code checkPermission}方法根据以下算法确定是授予还是拒绝访问：。
 * 
 *  <pre> {@code for(int i = m; i> 0; i--){
 * 
 *  if(caller i的域没有权限)throw AccessControlException
 * 
 *  else if(caller i被标记为特权){if(在调用doPrivileged中指定上下文)context.checkPermission(permission)if(在doPrivileged的调用中指定了有限权限){for(each limited permission){if (有限权限意味着请求的权限)return; }
 * } else return; }}。
 * 
 *  //接下来,检查创建线程时继承的上下文。 //每当一个新线程被创建时,在那时的AccessControlContext被存储并与新线程相关联,作为//"继承"上下文。
 * 
 *  inheritedContext.checkPermission(permission); } </pre>
 * 
 * <p>来电者可以标示为「有特权」(请参阅{@link #doPrivileged(PrivilegedAction)doPrivileged}以下)。
 * 当进行访问控制决策时,{@code checkPermission}方法通过没有上下文参数的{@code doPrivileged}调用停止检查它是否到达被标记为"特权"的调用者(有关上下文参数的信息,
 * 请参见下文)。
 * <p>来电者可以标示为「有特权」(请参阅{@link #doPrivileged(PrivilegedAction)doPrivileged}以下)。
 * 如果该调用者的域具有指定的权限,并且至少有一个限制权限参数(如果有)意味着请求的权限,则不进行进一步检查,并且{@code checkPermission}静默返回,表示允许请求的访问。
 * 如果该域没有指定的权限,则会抛出异常,像往常一样。
 * 如果调用者的域具有指定的权限,但并未暗示在调用{@code doPrivileged}中提供的任何限制权限参数,则权限检查会继续,直到没有更多调用者或另一个{@code doPrivileged}调用匹
 * 配请求权限并正常返回。
 * 如果该域没有指定的权限,则会抛出异常,像往常一样。
 * 
 *  <p>"特权"功能的正常使用如下。如果不需要从"特权"块中返回值,请执行以下操作：
 * 
 *  <pre> {@code somemethod(){...在这里的普通代码... AccessController.doPrivileged(new PrivilegedAction <Void>(){public Void run(){//特权代码在这里,例如：System.loadLibrary ("awt"); return null; // nothing to return}
 * }); ... normal code here ...}} </pre>。
 * 
 * <p>
 * PrivilegedAction是一个具有单个方法的接口,命名为{@code run}。上面的例子显示了该接口的实现的创建;提供了{@code run}方法的具体实现。
 * 当调用{@code doPrivileged}时,PrivilegedAction实现的实例被传递给它。
 *  {@code doPrivileged}方法在启用特权后从PrivilegedAction实现中调用{@code run}方法,并返回{@code run}方法的返回值作为{@code doPrivileged}
 * 返回值(在此示例中忽略该值) )。
 * 当调用{@code doPrivileged}时,PrivilegedAction实现的实例被传递给它。
 * 
 *  <p>如果您需要返回值,可以执行以下操作：
 * 
 *  <pre> {@code somemethod(){... normal code here ... String user = AccessController.doPrivileged(new PrivilegedAction <String>(){public String run(){return System.getProperty("user.name" );}
 * }); ... normal code here ...}} </pre>。
 * 
 *  <p>如果在{@code run}方法中执行的操作可能会抛出"已检查"异常(在方法的{@code throws}子句中列出的异常),那么您需要使用{@code PrivilegedExceptionAction}
 * 接口而不是{@code PrivilegedAction}接口：。
 * 
 * <p> {@code somemethod()throws FileNotFoundException {...正常代码在这里... try {FileInputStream fis = AccessController.doPrivileged(new PrivilegedExceptionAction <FileInputStream>(){public FileInputStream run()throws FileNotFoundException {return new FileInputStream "someFile");}
 * }); } catch(PrivilegedActionException e){// e.getException()应该是FileNotFoundException的一个实例,//因为只有"checked"异常将被"包装"在一个PrivilegedActionException。
 *  throw(FileNotFoundException)e.getException(); } ... normal code here ...}} </pre>。
 * 
 *  <p>在使用"特权"结构时非常小心,并且始终记住使特权代码段尽可能小。您可以传递{@code Permission}参数以进一步限制"特权"的范围(请参见下文)。
 * 
 *  <p>请注意,{@code checkPermission}总是在当前正在执行的线程的上下文中执行安全检查。
 * 有时,应该在给定上下文中进行的安全检查实际上需要在<i>不同的上下文(例如,从工作者线程内)中完成。
 * 为此情况提供了{@link #getContext()getContext}方法和AccessControlContext类。
 *  {@code getContext}方法获取当前调用上下文的"快照",并将其放在AccessControlContext对象中,它返回。示例调用如下：。
 * 
 * <pre>
 * 
 *  AccessControlContext acc = AccessController.getContext()
 * 
 * </pre>
 * 
 * <p>
 * AccessControlContext本身有一个{@code checkPermission}方法,该方法基于上下文<i> it </i>封装而不是当前执行线程的访问决策。
 * 不同上下文中的代码因此可以在先前保存的AccessControlContext对象上调用该方法。示例调用如下：。
 * 
 * <pre>
 * 
 *  acc.checkPermission(permission)
 * 
 * </pre>
 * 
 *  <p>还有一些时候,你不知道先验检查上下文的权限。在这些情况下,您可以使用接受上下文的doPrivileged方法。您还可以通过传递其他{@code Permission}参数来限制特权代码的范围。
 * 
 * <pre> {@code somemethod(){AccessController.doPrivileged(new PrivilegedAction <Object>(){public Object run(){//代码在这里。
 * 这个// run方法中的任何权限检查将要求//调用者的保护域和快照的//上下文具有所需的权限如果一个被请求的//权限不是由限制FilePermission //参数隐含的,那么线程的检查继续超过doPr
 * ivileged的调用者。
 * }},acc ,new FilePermission("/ temp / *",read)); ... normal code here ...}} </pre> <p>传递{@code AllPermission}
 * 实例的限制{@code Permission}参数相当于调用等效的{@code doPrivileged}方法, {@code Permission}个参数。
 * 传递长度为零的{@code Permission}数组会禁用代码权限,因此检查总是继续超出{@code doPrivileged}方法的调用者。
 * 
 * 
 * @see AccessControlContext
 *
 * @author Li Gong
 * @author Roland Schemers
 */

public final class AccessController {

    /**
     * Don't allow anyone to instantiate an AccessController
     * <p>
     *  不允许任何人实例化AccessController
     * 
     */
    private AccessController() { }

    /**
     * Performs the specified {@code PrivilegedAction} with privileges
     * enabled. The action is performed with <i>all</i> of the permissions
     * possessed by the caller's protection domain.
     *
     * <p> If the action's {@code run} method throws an (unchecked)
     * exception, it will propagate through this method.
     *
     * <p> Note that any DomainCombiner associated with the current
     * AccessControlContext will be ignored while the action is performed.
     *
     * <p>
     *  执行指定的{@code PrivilegedAction}启用权限。使用呼叫者的保护域所拥有的所有</i>权限来执行动作。
     * 
     *  <p>如果操作的{@code run}方法抛出(未检查的)异常,它将传播通过此方法。
     * 
     *  <p>请注意,在执行操作时,将忽略与当前AccessControlContext关联的任何DomainCombiner。
     * 
     * 
     * @param <T> the type of the value returned by the PrivilegedAction's
     *                  {@code run} method.
     *
     * @param action the action to be performed.
     *
     * @return the value returned by the action's {@code run} method.
     *
     * @exception NullPointerException if the action is {@code null}
     *
     * @see #doPrivileged(PrivilegedAction,AccessControlContext)
     * @see #doPrivileged(PrivilegedExceptionAction)
     * @see #doPrivilegedWithCombiner(PrivilegedAction)
     * @see java.security.DomainCombiner
     */

    @CallerSensitive
    public static native <T> T doPrivileged(PrivilegedAction<T> action);

    /**
     * Performs the specified {@code PrivilegedAction} with privileges
     * enabled. The action is performed with <i>all</i> of the permissions
     * possessed by the caller's protection domain.
     *
     * <p> If the action's {@code run} method throws an (unchecked)
     * exception, it will propagate through this method.
     *
     * <p> This method preserves the current AccessControlContext's
     * DomainCombiner (which may be null) while the action is performed.
     *
     * <p>
     * 执行指定的{@code PrivilegedAction}启用权限。使用呼叫者的保护域所拥有的所有</i>权限来执行动作。
     * 
     *  <p>如果操作的{@code run}方法抛出(未检查的)异常,它将传播通过此方法。
     * 
     *  <p>此方法在执行操作时保留当前AccessControlContext的DomainCombiner(可能为null)。
     * 
     * 
     * @param <T> the type of the value returned by the PrivilegedAction's
     *                  {@code run} method.
     *
     * @param action the action to be performed.
     *
     * @return the value returned by the action's {@code run} method.
     *
     * @exception NullPointerException if the action is {@code null}
     *
     * @see #doPrivileged(PrivilegedAction)
     * @see java.security.DomainCombiner
     *
     * @since 1.6
     */
    @CallerSensitive
    public static <T> T doPrivilegedWithCombiner(PrivilegedAction<T> action) {
        AccessControlContext acc = getStackAccessControlContext();
        if (acc == null) {
            return AccessController.doPrivileged(action);
        }
        DomainCombiner dc = acc.getAssignedCombiner();
        return AccessController.doPrivileged(action,
                                             preserveCombiner(dc, Reflection.getCallerClass()));
    }


    /**
     * Performs the specified {@code PrivilegedAction} with privileges
     * enabled and restricted by the specified {@code AccessControlContext}.
     * The action is performed with the intersection of the permissions
     * possessed by the caller's protection domain, and those possessed
     * by the domains represented by the specified {@code AccessControlContext}.
     * <p>
     * If the action's {@code run} method throws an (unchecked) exception,
     * it will propagate through this method.
     * <p>
     * If a security manager is installed and the specified
     * {@code AccessControlContext} was not created by system code and the
     * caller's {@code ProtectionDomain} has not been granted the
     * {@literal "createAccessControlContext"}
     * {@link java.security.SecurityPermission}, then the action is performed
     * with no permissions.
     *
     * <p>
     *  使用指定的{@code AccessControlContext}启用和限制的权限执行指定的{@code PrivilegedAction}。
     * 该操作使用呼叫者保护域拥有的权限和由指定的{@code AccessControlContext}表示的域拥有的权限的交集执行。
     * <p>
     *  如果操作的{@code run}方法抛出一个(未检查的)异常,它将传播通过这个方法。
     * <p>
     *  如果安装了安全管理器并且未通过系统代码创建指定的{@code AccessControlContext},并且未向调用者的{@code ProtectionDomain}授予{@literal"createAccessControlContext"}
     *  {@link java.security.SecurityPermission}那么将在没有权限的情况下执行操作。
     * 
     * 
     * @param <T> the type of the value returned by the PrivilegedAction's
     *                  {@code run} method.
     * @param action the action to be performed.
     * @param context an <i>access control context</i>
     *                representing the restriction to be applied to the
     *                caller's domain's privileges before performing
     *                the specified action.  If the context is
     *                {@code null}, then no additional restriction is applied.
     *
     * @return the value returned by the action's {@code run} method.
     *
     * @exception NullPointerException if the action is {@code null}
     *
     * @see #doPrivileged(PrivilegedAction)
     * @see #doPrivileged(PrivilegedExceptionAction,AccessControlContext)
     */
    @CallerSensitive
    public static native <T> T doPrivileged(PrivilegedAction<T> action,
                                            AccessControlContext context);


    /**
     * Performs the specified {@code PrivilegedAction} with privileges
     * enabled and restricted by the specified
     * {@code AccessControlContext} and with a privilege scope limited
     * by specified {@code Permission} arguments.
     *
     * The action is performed with the intersection of the permissions
     * possessed by the caller's protection domain, and those possessed
     * by the domains represented by the specified
     * {@code AccessControlContext}.
     * <p>
     * If the action's {@code run} method throws an (unchecked) exception,
     * it will propagate through this method.
     * <p>
     * If a security manager is installed and the specified
     * {@code AccessControlContext} was not created by system code and the
     * caller's {@code ProtectionDomain} has not been granted the
     * {@literal "createAccessControlContext"}
     * {@link java.security.SecurityPermission}, then the action is performed
     * with no permissions.
     *
     * <p>
     *  使用指定的{@code AccessControlContext}启用和限制的特权以及由指定的{@code Permission}参数限制的特权范围执行指定的{@code PrivilegedAction}
     * 。
     * 
     * 该操作使用呼叫者保护域拥有的权限和由指定的{@code AccessControlContext}表示的域拥有的权限的交集执行。
     * <p>
     *  如果操作的{@code run}方法抛出一个(未检查的)异常,它将传播通过这个方法。
     * <p>
     *  如果安装了安全管理器并且未通过系统代码创建指定的{@code AccessControlContext},并且未向调用者的{@code ProtectionDomain}授予{@literal"createAccessControlContext"}
     *  {@link java.security.SecurityPermission}那么将在没有权限的情况下执行操作。
     * 
     * 
     * @param <T> the type of the value returned by the PrivilegedAction's
     *                  {@code run} method.
     * @param action the action to be performed.
     * @param context an <i>access control context</i>
     *                representing the restriction to be applied to the
     *                caller's domain's privileges before performing
     *                the specified action.  If the context is
     *                {@code null},
     *                then no additional restriction is applied.
     * @param perms the {@code Permission} arguments which limit the
     *              scope of the caller's privileges. The number of arguments
     *              is variable.
     *
     * @return the value returned by the action's {@code run} method.
     *
     * @throws NullPointerException if action or perms or any element of
     *         perms is {@code null}
     *
     * @see #doPrivileged(PrivilegedAction)
     * @see #doPrivileged(PrivilegedExceptionAction,AccessControlContext)
     *
     * @since 1.8
     */
    @CallerSensitive
    public static <T> T doPrivileged(PrivilegedAction<T> action,
        AccessControlContext context, Permission... perms) {

        AccessControlContext parent = getContext();
        if (perms == null) {
            throw new NullPointerException("null permissions parameter");
        }
        Class <?> caller = Reflection.getCallerClass();
        return AccessController.doPrivileged(action, createWrapper(null,
            caller, parent, context, perms));
    }


    /**
     * Performs the specified {@code PrivilegedAction} with privileges
     * enabled and restricted by the specified
     * {@code AccessControlContext} and with a privilege scope limited
     * by specified {@code Permission} arguments.
     *
     * The action is performed with the intersection of the permissions
     * possessed by the caller's protection domain, and those possessed
     * by the domains represented by the specified
     * {@code AccessControlContext}.
     * <p>
     * If the action's {@code run} method throws an (unchecked) exception,
     * it will propagate through this method.
     *
     * <p> This method preserves the current AccessControlContext's
     * DomainCombiner (which may be null) while the action is performed.
     * <p>
     * If a security manager is installed and the specified
     * {@code AccessControlContext} was not created by system code and the
     * caller's {@code ProtectionDomain} has not been granted the
     * {@literal "createAccessControlContext"}
     * {@link java.security.SecurityPermission}, then the action is performed
     * with no permissions.
     *
     * <p>
     *  使用指定的{@code AccessControlContext}启用和限制的特权以及由指定的{@code Permission}参数限制的特权范围执行指定的{@code PrivilegedAction}
     * 。
     * 
     *  该操作使用呼叫者保护域拥有的权限和由指定的{@code AccessControlContext}表示的域拥有的权限的交集执行。
     * <p>
     *  如果操作的{@code run}方法抛出一个(未检查的)异常,它将传播通过这个方法。
     * 
     *  <p>此方法在执行操作时保留当前AccessControlContext的DomainCombiner(可能为null)。
     * <p>
     * 如果安装了安全管理器并且未通过系统代码创建指定的{@code AccessControlContext},并且未向调用者的{@code ProtectionDomain}授予{@literal"createAccessControlContext"}
     *  {@link java.security.SecurityPermission}那么将在没有权限的情况下执行操作。
     * 
     * 
     * @param <T> the type of the value returned by the PrivilegedAction's
     *                  {@code run} method.
     * @param action the action to be performed.
     * @param context an <i>access control context</i>
     *                representing the restriction to be applied to the
     *                caller's domain's privileges before performing
     *                the specified action.  If the context is
     *                {@code null},
     *                then no additional restriction is applied.
     * @param perms the {@code Permission} arguments which limit the
     *              scope of the caller's privileges. The number of arguments
     *              is variable.
     *
     * @return the value returned by the action's {@code run} method.
     *
     * @throws NullPointerException if action or perms or any element of
     *         perms is {@code null}
     *
     * @see #doPrivileged(PrivilegedAction)
     * @see #doPrivileged(PrivilegedExceptionAction,AccessControlContext)
     * @see java.security.DomainCombiner
     *
     * @since 1.8
     */
    @CallerSensitive
    public static <T> T doPrivilegedWithCombiner(PrivilegedAction<T> action,
        AccessControlContext context, Permission... perms) {

        AccessControlContext parent = getContext();
        DomainCombiner dc = parent.getCombiner();
        if (dc == null && context != null) {
            dc = context.getCombiner();
        }
        if (perms == null) {
            throw new NullPointerException("null permissions parameter");
        }
        Class <?> caller = Reflection.getCallerClass();
        return AccessController.doPrivileged(action, createWrapper(dc, caller,
            parent, context, perms));
    }

    /**
     * Performs the specified {@code PrivilegedExceptionAction} with
     * privileges enabled.  The action is performed with <i>all</i> of the
     * permissions possessed by the caller's protection domain.
     *
     * <p> If the action's {@code run} method throws an <i>unchecked</i>
     * exception, it will propagate through this method.
     *
     * <p> Note that any DomainCombiner associated with the current
     * AccessControlContext will be ignored while the action is performed.
     *
     * <p>
     *  执行指定的{@code PrivilegedExceptionAction}并启用权限。使用呼叫者的保护域所拥有的所有</i>权限来执行动作。
     * 
     *  <p>如果操作的{@code run}方法抛出未经检查的<i> </i>异常,它将传播通过此方法。
     * 
     *  <p>请注意,在执行操作时,将忽略与当前AccessControlContext关联的任何DomainCombiner。
     * 
     * 
     * @param <T> the type of the value returned by the
     *                  PrivilegedExceptionAction's {@code run} method.
     *
     * @param action the action to be performed
     *
     * @return the value returned by the action's {@code run} method
     *
     * @exception PrivilegedActionException if the specified action's
     *         {@code run} method threw a <i>checked</i> exception
     * @exception NullPointerException if the action is {@code null}
     *
     * @see #doPrivileged(PrivilegedAction)
     * @see #doPrivileged(PrivilegedExceptionAction,AccessControlContext)
     * @see #doPrivilegedWithCombiner(PrivilegedExceptionAction)
     * @see java.security.DomainCombiner
     */
    @CallerSensitive
    public static native <T> T
        doPrivileged(PrivilegedExceptionAction<T> action)
        throws PrivilegedActionException;


    /**
     * Performs the specified {@code PrivilegedExceptionAction} with
     * privileges enabled.  The action is performed with <i>all</i> of the
     * permissions possessed by the caller's protection domain.
     *
     * <p> If the action's {@code run} method throws an <i>unchecked</i>
     * exception, it will propagate through this method.
     *
     * <p> This method preserves the current AccessControlContext's
     * DomainCombiner (which may be null) while the action is performed.
     *
     * <p>
     *  执行指定的{@code PrivilegedExceptionAction}并启用权限。使用呼叫者的保护域所拥有的所有</i>权限来执行动作。
     * 
     *  <p>如果操作的{@code run}方法抛出未经检查的<i> </i>异常,它将传播通过此方法。
     * 
     *  <p>此方法在执行操作时保留当前AccessControlContext的DomainCombiner(可能为null)。
     * 
     * 
     * @param <T> the type of the value returned by the
     *                  PrivilegedExceptionAction's {@code run} method.
     *
     * @param action the action to be performed.
     *
     * @return the value returned by the action's {@code run} method
     *
     * @exception PrivilegedActionException if the specified action's
     *         {@code run} method threw a <i>checked</i> exception
     * @exception NullPointerException if the action is {@code null}
     *
     * @see #doPrivileged(PrivilegedAction)
     * @see #doPrivileged(PrivilegedExceptionAction,AccessControlContext)
     * @see java.security.DomainCombiner
     *
     * @since 1.6
     */
    @CallerSensitive
    public static <T> T doPrivilegedWithCombiner(PrivilegedExceptionAction<T> action)
        throws PrivilegedActionException
    {
        AccessControlContext acc = getStackAccessControlContext();
        if (acc == null) {
            return AccessController.doPrivileged(action);
        }
        DomainCombiner dc = acc.getAssignedCombiner();
        return AccessController.doPrivileged(action,
                                             preserveCombiner(dc, Reflection.getCallerClass()));
    }

    /**
     * preserve the combiner across the doPrivileged call
     * <p>
     *  保留跨越doPrivileged调用的组合器
     * 
     */
    private static AccessControlContext preserveCombiner(DomainCombiner combiner,
                                                         Class<?> caller)
    {
        return createWrapper(combiner, caller, null, null, null);
    }

    /**
     * Create a wrapper to contain the limited privilege scope data.
     * <p>
     *  创建包含有限权限范围数据的包装器。
     * 
     */
    private static AccessControlContext
        createWrapper(DomainCombiner combiner, Class<?> caller,
                      AccessControlContext parent, AccessControlContext context,
                      Permission[] perms)
    {
        ProtectionDomain callerPD = getCallerPD(caller);
        // check if caller is authorized to create context
        if (context != null && !context.isAuthorized() &&
            System.getSecurityManager() != null &&
            !callerPD.impliesCreateAccessControlContext())
        {
            ProtectionDomain nullPD = new ProtectionDomain(null, null);
            return new AccessControlContext(new ProtectionDomain[] { nullPD });
        } else {
            return new AccessControlContext(callerPD, combiner, parent,
                                            context, perms);
        }
    }

    private static ProtectionDomain getCallerPD(final Class <?> caller) {
        ProtectionDomain callerPd = doPrivileged
            (new PrivilegedAction<ProtectionDomain>() {
            public ProtectionDomain run() {
                return caller.getProtectionDomain();
            }
        });

        return callerPd;
    }

    /**
     * Performs the specified {@code PrivilegedExceptionAction} with
     * privileges enabled and restricted by the specified
     * {@code AccessControlContext}.  The action is performed with the
     * intersection of the permissions possessed by the caller's
     * protection domain, and those possessed by the domains represented by the
     * specified {@code AccessControlContext}.
     * <p>
     * If the action's {@code run} method throws an <i>unchecked</i>
     * exception, it will propagate through this method.
     * <p>
     * If a security manager is installed and the specified
     * {@code AccessControlContext} was not created by system code and the
     * caller's {@code ProtectionDomain} has not been granted the
     * {@literal "createAccessControlContext"}
     * {@link java.security.SecurityPermission}, then the action is performed
     * with no permissions.
     *
     * <p>
     * 使用指定的{@code AccessControlContext}启用和限制的权限执行指定的{@code PrivilegedExceptionAction}。
     * 该操作使用呼叫者保护域拥有的权限和由指定的{@code AccessControlContext}表示的域拥有的权限的交集执行。
     * <p>
     *  如果操作的{@code run}方法抛出未经检查的<i> </i>异常,它将通过此​​方法传播。
     * <p>
     *  如果安装了安全管理器并且未通过系统代码创建指定的{@code AccessControlContext},并且未向调用者的{@code ProtectionDomain}授予{@literal"createAccessControlContext"}
     *  {@link java.security.SecurityPermission}那么将在没有权限的情况下执行操作。
     * 
     * 
     * @param <T> the type of the value returned by the
     *                  PrivilegedExceptionAction's {@code run} method.
     * @param action the action to be performed
     * @param context an <i>access control context</i>
     *                representing the restriction to be applied to the
     *                caller's domain's privileges before performing
     *                the specified action.  If the context is
     *                {@code null}, then no additional restriction is applied.
     *
     * @return the value returned by the action's {@code run} method
     *
     * @exception PrivilegedActionException if the specified action's
     *         {@code run} method threw a <i>checked</i> exception
     * @exception NullPointerException if the action is {@code null}
     *
     * @see #doPrivileged(PrivilegedAction)
     * @see #doPrivileged(PrivilegedAction,AccessControlContext)
     */
    @CallerSensitive
    public static native <T> T
        doPrivileged(PrivilegedExceptionAction<T> action,
                     AccessControlContext context)
        throws PrivilegedActionException;


    /**
     * Performs the specified {@code PrivilegedExceptionAction} with
     * privileges enabled and restricted by the specified
     * {@code AccessControlContext} and with a privilege scope limited by
     * specified {@code Permission} arguments.
     *
     * The action is performed with the intersection of the permissions
     * possessed by the caller's protection domain, and those possessed
     * by the domains represented by the specified
     * {@code AccessControlContext}.
     * <p>
     * If the action's {@code run} method throws an (unchecked) exception,
     * it will propagate through this method.
     * <p>
     * If a security manager is installed and the specified
     * {@code AccessControlContext} was not created by system code and the
     * caller's {@code ProtectionDomain} has not been granted the
     * {@literal "createAccessControlContext"}
     * {@link java.security.SecurityPermission}, then the action is performed
     * with no permissions.
     *
     * <p>
     *  以指定的{@code AccessControlContext}启用和限制的特权并且由指定的{@code Permission}参数限制的特权范围执行指定的{@code PrivilegedExceptionAction}
     * 。
     * 
     *  该操作使用呼叫者保护域拥有的权限和由指定的{@code AccessControlContext}表示的域拥有的权限的交集执行。
     * <p>
     *  如果操作的{@code run}方法抛出一个(未检查的)异常,它将传播通过这个方法。
     * <p>
     * 如果安装了安全管理器并且未通过系统代码创建指定的{@code AccessControlContext},并且未向调用者的{@code ProtectionDomain}授予{@literal"createAccessControlContext"}
     *  {@link java.security.SecurityPermission}那么将在没有权限的情况下执行操作。
     * 
     * 
     * @param <T> the type of the value returned by the
     *                  PrivilegedExceptionAction's {@code run} method.
     * @param action the action to be performed.
     * @param context an <i>access control context</i>
     *                representing the restriction to be applied to the
     *                caller's domain's privileges before performing
     *                the specified action.  If the context is
     *                {@code null},
     *                then no additional restriction is applied.
     * @param perms the {@code Permission} arguments which limit the
     *              scope of the caller's privileges. The number of arguments
     *              is variable.
     *
     * @return the value returned by the action's {@code run} method.
     *
     * @throws PrivilegedActionException if the specified action's
     *         {@code run} method threw a <i>checked</i> exception
     * @throws NullPointerException if action or perms or any element of
     *         perms is {@code null}
     *
     * @see #doPrivileged(PrivilegedAction)
     * @see #doPrivileged(PrivilegedAction,AccessControlContext)
     *
     * @since 1.8
     */
    @CallerSensitive
    public static <T> T doPrivileged(PrivilegedExceptionAction<T> action,
                                     AccessControlContext context, Permission... perms)
        throws PrivilegedActionException
    {
        AccessControlContext parent = getContext();
        if (perms == null) {
            throw new NullPointerException("null permissions parameter");
        }
        Class <?> caller = Reflection.getCallerClass();
        return AccessController.doPrivileged(action, createWrapper(null, caller, parent, context, perms));
    }


    /**
     * Performs the specified {@code PrivilegedExceptionAction} with
     * privileges enabled and restricted by the specified
     * {@code AccessControlContext} and with a privilege scope limited by
     * specified {@code Permission} arguments.
     *
     * The action is performed with the intersection of the permissions
     * possessed by the caller's protection domain, and those possessed
     * by the domains represented by the specified
     * {@code AccessControlContext}.
     * <p>
     * If the action's {@code run} method throws an (unchecked) exception,
     * it will propagate through this method.
     *
     * <p> This method preserves the current AccessControlContext's
     * DomainCombiner (which may be null) while the action is performed.
     * <p>
     * If a security manager is installed and the specified
     * {@code AccessControlContext} was not created by system code and the
     * caller's {@code ProtectionDomain} has not been granted the
     * {@literal "createAccessControlContext"}
     * {@link java.security.SecurityPermission}, then the action is performed
     * with no permissions.
     *
     * <p>
     *  以指定的{@code AccessControlContext}启用和限制的特权并且由指定的{@code Permission}参数限制的特权范围执行指定的{@code PrivilegedExceptionAction}
     * 。
     * 
     *  该操作使用呼叫者保护域拥有的权限和由指定的{@code AccessControlContext}表示的域拥有的权限的交集执行。
     * <p>
     *  如果操作的{@code run}方法抛出一个(未检查的)异常,它将传播通过这个方法。
     * 
     *  <p>此方法在执行操作时保留当前AccessControlContext的DomainCombiner(可能为null)。
     * <p>
     *  如果安装了安全管理器并且未通过系统代码创建指定的{@code AccessControlContext},并且未向调用者的{@code ProtectionDomain}授予{@literal"createAccessControlContext"}
     *  {@link java.security.SecurityPermission}那么将在没有权限的情况下执行操作。
     * 
     * 
     * @param <T> the type of the value returned by the
     *                  PrivilegedExceptionAction's {@code run} method.
     * @param action the action to be performed.
     * @param context an <i>access control context</i>
     *                representing the restriction to be applied to the
     *                caller's domain's privileges before performing
     *                the specified action.  If the context is
     *                {@code null},
     *                then no additional restriction is applied.
     * @param perms the {@code Permission} arguments which limit the
     *              scope of the caller's privileges. The number of arguments
     *              is variable.
     *
     * @return the value returned by the action's {@code run} method.
     *
     * @throws PrivilegedActionException if the specified action's
     *         {@code run} method threw a <i>checked</i> exception
     * @throws NullPointerException if action or perms or any element of
     *         perms is {@code null}
     *
     * @see #doPrivileged(PrivilegedAction)
     * @see #doPrivileged(PrivilegedAction,AccessControlContext)
     * @see java.security.DomainCombiner
     *
     * @since 1.8
     */
    @CallerSensitive
    public static <T> T doPrivilegedWithCombiner(PrivilegedExceptionAction<T> action,
                                                 AccessControlContext context,
                                                 Permission... perms)
        throws PrivilegedActionException
    {
        AccessControlContext parent = getContext();
        DomainCombiner dc = parent.getCombiner();
        if (dc == null && context != null) {
            dc = context.getCombiner();
        }
        if (perms == null) {
            throw new NullPointerException("null permissions parameter");
        }
        Class <?> caller = Reflection.getCallerClass();
        return AccessController.doPrivileged(action, createWrapper(dc, caller,
            parent, context, perms));
    }

    /**
     * Returns the AccessControl context. i.e., it gets
     * the protection domains of all the callers on the stack,
     * starting at the first class with a non-null
     * ProtectionDomain.
     *
     * <p>
     *  返回AccessControl上下文。即它获得堆栈上所有调用者的保护域,从具有非null ProtectionDomain的第一个类开始。
     * 
     * 
     * @return the access control context based on the current stack or
     *         null if there was only privileged system code.
     */

    private static native AccessControlContext getStackAccessControlContext();


    /**
     * Returns the "inherited" AccessControl context. This is the context
     * that existed when the thread was created. Package private so
     * AccessControlContext can use it.
     * <p>
     * 返回"继承的"AccessControl上下文。这是创建线程时存在的上下文。包私人所以AccessControlContext可以使用它。
     * 
     */

    static native AccessControlContext getInheritedAccessControlContext();

    /**
     * This method takes a "snapshot" of the current calling context, which
     * includes the current Thread's inherited AccessControlContext and any
     * limited privilege scope, and places it in an AccessControlContext object.
     * This context may then be checked at a later point, possibly in another thread.
     *
     * <p>
     *  此方法接受当前调用上下文的"快照",其中包括当前线程继承的AccessControlContext和任何有限权限范围,并将其放置在AccessControlContext对象中。
     * 然后可以在稍后的点,可能在另一线程中检查该上下文。
     * 
     * 
     * @see AccessControlContext
     *
     * @return the AccessControlContext based on the current context.
     */

    public static AccessControlContext getContext()
    {
        AccessControlContext acc = getStackAccessControlContext();
        if (acc == null) {
            // all we had was privileged system code. We don't want
            // to return null though, so we construct a real ACC.
            return new AccessControlContext(null, true);
        } else {
            return acc.optimize();
        }
    }

    /**
     * Determines whether the access request indicated by the
     * specified permission should be allowed or denied, based on
     * the current AccessControlContext and security policy.
     * This method quietly returns if the access request
     * is permitted, or throws an AccessControlException otherwise. The
     * getPermission method of the AccessControlException returns the
     * {@code perm} Permission object instance.
     *
     * <p>
     *  根据当前的AccessControlContext和安全策略确定是否允许或拒绝由指定的权限指示的访问请求。如果允许访问请求,此方法会静默返回,否则会抛出AccessControlException。
     *  AccessControlException的getPermission方法返回{@code perm} Permission对象实例。
     * 
     * @param perm the requested permission.
     *
     * @exception AccessControlException if the specified permission
     *            is not permitted, based on the current security policy.
     * @exception NullPointerException if the specified permission
     *            is {@code null} and is checked based on the
     *            security policy currently in effect.
     */

    public static void checkPermission(Permission perm)
        throws AccessControlException
    {
        //System.err.println("checkPermission "+perm);
        //Thread.currentThread().dumpStack();

        if (perm == null) {
            throw new NullPointerException("permission can't be null");
        }

        AccessControlContext stack = getStackAccessControlContext();
        // if context is null, we had privileged system code on the stack.
        if (stack == null) {
            Debug debug = AccessControlContext.getDebug();
            boolean dumpDebug = false;
            if (debug != null) {
                dumpDebug = !Debug.isOn("codebase=");
                dumpDebug &= !Debug.isOn("permission=") ||
                    Debug.isOn("permission=" + perm.getClass().getCanonicalName());
            }

            if (dumpDebug && Debug.isOn("stack")) {
                Thread.dumpStack();
            }

            if (dumpDebug && Debug.isOn("domain")) {
                debug.println("domain (context is null)");
            }

            if (dumpDebug) {
                debug.println("access allowed "+perm);
            }
            return;
        }

        AccessControlContext acc = stack.optimize();
        acc.checkPermission(perm);
    }
}
