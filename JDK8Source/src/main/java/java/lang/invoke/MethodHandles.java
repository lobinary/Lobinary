/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2008, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.invoke;

import java.lang.reflect.*;
import java.util.BitSet;
import java.util.List;
import java.util.Arrays;

import sun.invoke.util.ValueConversions;
import sun.invoke.util.VerifyAccess;
import sun.invoke.util.Wrapper;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;
import sun.reflect.misc.ReflectUtil;
import sun.security.util.SecurityConstants;
import java.lang.invoke.LambdaForm.BasicType;
import static java.lang.invoke.LambdaForm.BasicType.*;
import static java.lang.invoke.MethodHandleStatics.*;
import static java.lang.invoke.MethodHandleImpl.Intrinsic;
import static java.lang.invoke.MethodHandleNatives.Constants.*;

import java.util.concurrent.ConcurrentHashMap;

/**
 * This class consists exclusively of static methods that operate on or return
 * method handles. They fall into several categories:
 * <ul>
 * <li>Lookup methods which help create method handles for methods and fields.
 * <li>Combinator methods, which combine or transform pre-existing method handles into new ones.
 * <li>Other factory methods to create method handles that emulate other common JVM operations or control flow patterns.
 * </ul>
 * <p>
 * <p>
 *  这个类只包括操作或返回方法句柄的静态方法它们分为几个类别：
 * <ul>
 *  <li>帮助为方法和字段创建方法句柄的查找方法<li> Combinator方法,将先前存在的方法句柄组合或转换为新的方法句柄<li>创建方法句柄以模拟其他常见JVM操作或控制的其他工厂方法流动模式。
 * </ul>
 * <p>
 * 
 * @author John Rose, JSR 292 EG
 * @since 1.7
 */
public class MethodHandles {

    private MethodHandles() { }  // do not instantiate

    private static final MemberName.Factory IMPL_NAMES = MemberName.getFactory();
    static { MethodHandleImpl.initStatics(); }
    // See IMPL_LOOKUP below.

    //// Method handle creation from ordinary methods.

    /**
     * Returns a {@link Lookup lookup object} with
     * full capabilities to emulate all supported bytecode behaviors of the caller.
     * These capabilities include <a href="MethodHandles.Lookup.html#privacc">private access</a> to the caller.
     * Factory methods on the lookup object can create
     * <a href="MethodHandleInfo.html#directmh">direct method handles</a>
     * for any member that the caller has access to via bytecodes,
     * including protected and private fields and methods.
     * This lookup object is a <em>capability</em> which may be delegated to trusted agents.
     * Do not store it in place where untrusted code can access it.
     * <p>
     * This method is caller sensitive, which means that it may return different
     * values to different callers.
     * <p>
     * For any given caller class {@code C}, the lookup object returned by this call
     * has equivalent capabilities to any lookup object
     * supplied by the JVM to the bootstrap method of an
     * <a href="package-summary.html#indyinsn">invokedynamic instruction</a>
     * executing in the same caller class {@code C}.
     * <p>
     * 返回具有完整功能的{@link查找查找对象},以模拟调用者的所有受支持的字节码行为这些功能包括对调用方的<a href=\"MethodHandlesLookuphtml#privacc\">私有访问</a>
     * 查找对象的工厂方法为调用者通过字节码访问的任何成员创建<a href=\"MethodHandleInfohtml#directmh\">直接方法句柄</a>,包括受保护和专用字段和方法此查找对象是<em>
     * 能力</em>可以委托给可信代理不要将其存储在不受信任的代码可以访问它的位置。
     * <p>
     *  此方法对呼叫者敏感,这意味着它可能会向不同的呼叫者返回不同的值
     * <p>
     * 对于任何给定的调用程序类{@code C},此调用返回的查找对象具有与JVM提供给<a href=\"package-summaryhtml#indyinsn\">调用的动态指令< / a>在同一个调用
     * 类中执行{@code C}。
     * 
     * 
     * @return a lookup object for the caller of this method, with private access
     */
    @CallerSensitive
    public static Lookup lookup() {
        return new Lookup(Reflection.getCallerClass());
    }

    /**
     * Returns a {@link Lookup lookup object} which is trusted minimally.
     * It can only be used to create method handles to
     * publicly accessible fields and methods.
     * <p>
     * As a matter of pure convention, the {@linkplain Lookup#lookupClass lookup class}
     * of this lookup object will be {@link java.lang.Object}.
     *
     * <p style="font-size:smaller;">
     * <em>Discussion:</em>
     * The lookup class can be changed to any other class {@code C} using an expression of the form
     * {@link Lookup#in publicLookup().in(C.class)}.
     * Since all classes have equal access to public names,
     * such a change would confer no new access rights.
     * A public lookup object is always subject to
     * <a href="MethodHandles.Lookup.html#secmgr">security manager checks</a>.
     * Also, it cannot access
     * <a href="MethodHandles.Lookup.html#callsens">caller sensitive methods</a>.
     * <p>
     *  返回最小受信任的{@link查找查找对象}它只能用于为可公开访问的字段和方法创建方法句柄
     * <p>
     *  根据纯约定,此查找对象的{@linkplain Lookup#lookupClass查找类}将为{@link javalangObject}
     * 
     * <p style="font-size:smaller;">
     * <em>讨论：</em>查找类可以更改为任何其他类{@code C},使用{@link中的查找#in publicLookup()in(Cclass)}的表达式。
     * 由于所有类都具有相同的访问权限对于公共名称,此类更改将不会赋予新的访问权限。
     * 公共查找对象始终受<a href=\"MethodHandlesLookuphtml#secmgr\">安全管理器检查</a>的限制。
     * 此外,它无法访问<a href ="MethodHandlesLookuphtml# callens">呼叫者敏感方法</a>。
     * 
     * 
     * @return a lookup object which is trusted minimally
     */
    public static Lookup publicLookup() {
        return Lookup.PUBLIC_LOOKUP;
    }

    /**
     * Performs an unchecked "crack" of a
     * <a href="MethodHandleInfo.html#directmh">direct method handle</a>.
     * The result is as if the user had obtained a lookup object capable enough
     * to crack the target method handle, called
     * {@link java.lang.invoke.MethodHandles.Lookup#revealDirect Lookup.revealDirect}
     * on the target to obtain its symbolic reference, and then called
     * {@link java.lang.invoke.MethodHandleInfo#reflectAs MethodHandleInfo.reflectAs}
     * to resolve the symbolic reference to a member.
     * <p>
     * If there is a security manager, its {@code checkPermission} method
     * is called with a {@code ReflectPermission("suppressAccessChecks")} permission.
     * <p>
     * 对<a href=\"MethodHandleInfohtml#directmh\">直接方法句柄</a>执行未选中的"破解"结果就好像用户已获得足够能够破解目标方法句柄的查找对象,称为{@link javalanginvokeMethodHandlesLookup#revealDirect LookuprevealDirect}
     * 获取它的符号引用,然后调用{@link javalanginvokeMethodHandleInfo #reflectAs MethodHandleInforeflectAs}来解析对象的符号引用。
     * <p>
     *  如果有安全管理器,则会使用{@code ReflectPermission("suppressAccessChecks")}权限调用其{@code checkPermission}方法
     * 
     * 
     * @param <T> the desired type of the result, either {@link Member} or a subtype
     * @param target a direct method handle to crack into symbolic reference components
     * @param expected a class object representing the desired result type {@code T}
     * @return a reference to the method, constructor, or field object
     * @exception SecurityException if the caller is not privileged to call {@code setAccessible}
     * @exception NullPointerException if either argument is {@code null}
     * @exception IllegalArgumentException if the target is not a direct method handle
     * @exception ClassCastException if the member is not of the expected type
     * @since 1.8
     */
    public static <T extends Member> T
    reflectAs(Class<T> expected, MethodHandle target) {
        SecurityManager smgr = System.getSecurityManager();
        if (smgr != null)  smgr.checkPermission(ACCESS_PERMISSION);
        Lookup lookup = Lookup.IMPL_LOOKUP;  // use maximally privileged lookup
        return lookup.revealDirect(target).reflectAs(expected, lookup);
    }
    // Copied from AccessibleObject, as used by Method.setAccessible, etc.:
    static final private java.security.Permission ACCESS_PERMISSION =
        new ReflectPermission("suppressAccessChecks");

    /**
     * A <em>lookup object</em> is a factory for creating method handles,
     * when the creation requires access checking.
     * Method handles do not perform
     * access checks when they are called, but rather when they are created.
     * Therefore, method handle access
     * restrictions must be enforced when a method handle is created.
     * The caller class against which those restrictions are enforced
     * is known as the {@linkplain #lookupClass lookup class}.
     * <p>
     * A lookup class which needs to create method handles will call
     * {@link MethodHandles#lookup MethodHandles.lookup} to create a factory for itself.
     * When the {@code Lookup} factory object is created, the identity of the lookup class is
     * determined, and securely stored in the {@code Lookup} object.
     * The lookup class (or its delegates) may then use factory methods
     * on the {@code Lookup} object to create method handles for access-checked members.
     * This includes all methods, constructors, and fields which are allowed to the lookup class,
     * even private ones.
     *
     * <h1><a name="lookups"></a>Lookup Factory Methods</h1>
     * The factory methods on a {@code Lookup} object correspond to all major
     * use cases for methods, constructors, and fields.
     * Each method handle created by a factory method is the functional
     * equivalent of a particular <em>bytecode behavior</em>.
     * (Bytecode behaviors are described in section 5.4.3.5 of the Java Virtual Machine Specification.)
     * Here is a summary of the correspondence between these factory methods and
     * the behavior the resulting method handles:
     * <table border=1 cellpadding=5 summary="lookup method behaviors">
     * <tr>
     *     <th><a name="equiv"></a>lookup expression</th>
     *     <th>member</th>
     *     <th>bytecode behavior</th>
     * </tr>
     * <tr>
     *     <td>{@link java.lang.invoke.MethodHandles.Lookup#findGetter lookup.findGetter(C.class,"f",FT.class)}</td>
     *     <td>{@code FT f;}</td><td>{@code (T) this.f;}</td>
     * </tr>
     * <tr>
     *     <td>{@link java.lang.invoke.MethodHandles.Lookup#findStaticGetter lookup.findStaticGetter(C.class,"f",FT.class)}</td>
     *     <td>{@code static}<br>{@code FT f;}</td><td>{@code (T) C.f;}</td>
     * </tr>
     * <tr>
     *     <td>{@link java.lang.invoke.MethodHandles.Lookup#findSetter lookup.findSetter(C.class,"f",FT.class)}</td>
     *     <td>{@code FT f;}</td><td>{@code this.f = x;}</td>
     * </tr>
     * <tr>
     *     <td>{@link java.lang.invoke.MethodHandles.Lookup#findStaticSetter lookup.findStaticSetter(C.class,"f",FT.class)}</td>
     *     <td>{@code static}<br>{@code FT f;}</td><td>{@code C.f = arg;}</td>
     * </tr>
     * <tr>
     *     <td>{@link java.lang.invoke.MethodHandles.Lookup#findVirtual lookup.findVirtual(C.class,"m",MT)}</td>
     *     <td>{@code T m(A*);}</td><td>{@code (T) this.m(arg*);}</td>
     * </tr>
     * <tr>
     *     <td>{@link java.lang.invoke.MethodHandles.Lookup#findStatic lookup.findStatic(C.class,"m",MT)}</td>
     *     <td>{@code static}<br>{@code T m(A*);}</td><td>{@code (T) C.m(arg*);}</td>
     * </tr>
     * <tr>
     *     <td>{@link java.lang.invoke.MethodHandles.Lookup#findSpecial lookup.findSpecial(C.class,"m",MT,this.class)}</td>
     *     <td>{@code T m(A*);}</td><td>{@code (T) super.m(arg*);}</td>
     * </tr>
     * <tr>
     *     <td>{@link java.lang.invoke.MethodHandles.Lookup#findConstructor lookup.findConstructor(C.class,MT)}</td>
     *     <td>{@code C(A*);}</td><td>{@code new C(arg*);}</td>
     * </tr>
     * <tr>
     *     <td>{@link java.lang.invoke.MethodHandles.Lookup#unreflectGetter lookup.unreflectGetter(aField)}</td>
     *     <td>({@code static})?<br>{@code FT f;}</td><td>{@code (FT) aField.get(thisOrNull);}</td>
     * </tr>
     * <tr>
     *     <td>{@link java.lang.invoke.MethodHandles.Lookup#unreflectSetter lookup.unreflectSetter(aField)}</td>
     *     <td>({@code static})?<br>{@code FT f;}</td><td>{@code aField.set(thisOrNull, arg);}</td>
     * </tr>
     * <tr>
     *     <td>{@link java.lang.invoke.MethodHandles.Lookup#unreflect lookup.unreflect(aMethod)}</td>
     *     <td>({@code static})?<br>{@code T m(A*);}</td><td>{@code (T) aMethod.invoke(thisOrNull, arg*);}</td>
     * </tr>
     * <tr>
     *     <td>{@link java.lang.invoke.MethodHandles.Lookup#unreflectConstructor lookup.unreflectConstructor(aConstructor)}</td>
     *     <td>{@code C(A*);}</td><td>{@code (C) aConstructor.newInstance(arg*);}</td>
     * </tr>
     * <tr>
     *     <td>{@link java.lang.invoke.MethodHandles.Lookup#unreflect lookup.unreflect(aMethod)}</td>
     *     <td>({@code static})?<br>{@code T m(A*);}</td><td>{@code (T) aMethod.invoke(thisOrNull, arg*);}</td>
     * </tr>
     * </table>
     *
     * Here, the type {@code C} is the class or interface being searched for a member,
     * documented as a parameter named {@code refc} in the lookup methods.
     * The method type {@code MT} is composed from the return type {@code T}
     * and the sequence of argument types {@code A*}.
     * The constructor also has a sequence of argument types {@code A*} and
     * is deemed to return the newly-created object of type {@code C}.
     * Both {@code MT} and the field type {@code FT} are documented as a parameter named {@code type}.
     * The formal parameter {@code this} stands for the self-reference of type {@code C};
     * if it is present, it is always the leading argument to the method handle invocation.
     * (In the case of some {@code protected} members, {@code this} may be
     * restricted in type to the lookup class; see below.)
     * The name {@code arg} stands for all the other method handle arguments.
     * In the code examples for the Core Reflection API, the name {@code thisOrNull}
     * stands for a null reference if the accessed method or field is static,
     * and {@code this} otherwise.
     * The names {@code aMethod}, {@code aField}, and {@code aConstructor} stand
     * for reflective objects corresponding to the given members.
     * <p>
     * In cases where the given member is of variable arity (i.e., a method or constructor)
     * the returned method handle will also be of {@linkplain MethodHandle#asVarargsCollector variable arity}.
     * In all other cases, the returned method handle will be of fixed arity.
     * <p style="font-size:smaller;">
     * <em>Discussion:</em>
     * The equivalence between looked-up method handles and underlying
     * class members and bytecode behaviors
     * can break down in a few ways:
     * <ul style="font-size:smaller;">
     * <li>If {@code C} is not symbolically accessible from the lookup class's loader,
     * the lookup can still succeed, even when there is no equivalent
     * Java expression or bytecoded constant.
     * <li>Likewise, if {@code T} or {@code MT}
     * is not symbolically accessible from the lookup class's loader,
     * the lookup can still succeed.
     * For example, lookups for {@code MethodHandle.invokeExact} and
     * {@code MethodHandle.invoke} will always succeed, regardless of requested type.
     * <li>If there is a security manager installed, it can forbid the lookup
     * on various grounds (<a href="MethodHandles.Lookup.html#secmgr">see below</a>).
     * By contrast, the {@code ldc} instruction on a {@code CONSTANT_MethodHandle}
     * constant is not subject to security manager checks.
     * <li>If the looked-up method has a
     * <a href="MethodHandle.html#maxarity">very large arity</a>,
     * the method handle creation may fail, due to the method handle
     * type having too many parameters.
     * </ul>
     *
     * <h1><a name="access"></a>Access checking</h1>
     * Access checks are applied in the factory methods of {@code Lookup},
     * when a method handle is created.
     * This is a key difference from the Core Reflection API, since
     * {@link java.lang.reflect.Method#invoke java.lang.reflect.Method.invoke}
     * performs access checking against every caller, on every call.
     * <p>
     * All access checks start from a {@code Lookup} object, which
     * compares its recorded lookup class against all requests to
     * create method handles.
     * A single {@code Lookup} object can be used to create any number
     * of access-checked method handles, all checked against a single
     * lookup class.
     * <p>
     * A {@code Lookup} object can be shared with other trusted code,
     * such as a metaobject protocol.
     * A shared {@code Lookup} object delegates the capability
     * to create method handles on private members of the lookup class.
     * Even if privileged code uses the {@code Lookup} object,
     * the access checking is confined to the privileges of the
     * original lookup class.
     * <p>
     * A lookup can fail, because
     * the containing class is not accessible to the lookup class, or
     * because the desired class member is missing, or because the
     * desired class member is not accessible to the lookup class, or
     * because the lookup object is not trusted enough to access the member.
     * In any of these cases, a {@code ReflectiveOperationException} will be
     * thrown from the attempted lookup.  The exact class will be one of
     * the following:
     * <ul>
     * <li>NoSuchMethodException &mdash; if a method is requested but does not exist
     * <li>NoSuchFieldException &mdash; if a field is requested but does not exist
     * <li>IllegalAccessException &mdash; if the member exists but an access check fails
     * </ul>
     * <p>
     * In general, the conditions under which a method handle may be
     * looked up for a method {@code M} are no more restrictive than the conditions
     * under which the lookup class could have compiled, verified, and resolved a call to {@code M}.
     * Where the JVM would raise exceptions like {@code NoSuchMethodError},
     * a method handle lookup will generally raise a corresponding
     * checked exception, such as {@code NoSuchMethodException}.
     * And the effect of invoking the method handle resulting from the lookup
     * is <a href="MethodHandles.Lookup.html#equiv">exactly equivalent</a>
     * to executing the compiled, verified, and resolved call to {@code M}.
     * The same point is true of fields and constructors.
     * <p style="font-size:smaller;">
     * <em>Discussion:</em>
     * Access checks only apply to named and reflected methods,
     * constructors, and fields.
     * Other method handle creation methods, such as
     * {@link MethodHandle#asType MethodHandle.asType},
     * do not require any access checks, and are used
     * independently of any {@code Lookup} object.
     * <p>
     * If the desired member is {@code protected}, the usual JVM rules apply,
     * including the requirement that the lookup class must be either be in the
     * same package as the desired member, or must inherit that member.
     * (See the Java Virtual Machine Specification, sections 4.9.2, 5.4.3.5, and 6.4.)
     * In addition, if the desired member is a non-static field or method
     * in a different package, the resulting method handle may only be applied
     * to objects of the lookup class or one of its subclasses.
     * This requirement is enforced by narrowing the type of the leading
     * {@code this} parameter from {@code C}
     * (which will necessarily be a superclass of the lookup class)
     * to the lookup class itself.
     * <p>
     * The JVM imposes a similar requirement on {@code invokespecial} instruction,
     * that the receiver argument must match both the resolved method <em>and</em>
     * the current class.  Again, this requirement is enforced by narrowing the
     * type of the leading parameter to the resulting method handle.
     * (See the Java Virtual Machine Specification, section 4.10.1.9.)
     * <p>
     * The JVM represents constructors and static initializer blocks as internal methods
     * with special names ({@code "<init>"} and {@code "<clinit>"}).
     * The internal syntax of invocation instructions allows them to refer to such internal
     * methods as if they were normal methods, but the JVM bytecode verifier rejects them.
     * A lookup of such an internal method will produce a {@code NoSuchMethodException}.
     * <p>
     * In some cases, access between nested classes is obtained by the Java compiler by creating
     * an wrapper method to access a private method of another class
     * in the same top-level declaration.
     * For example, a nested class {@code C.D}
     * can access private members within other related classes such as
     * {@code C}, {@code C.D.E}, or {@code C.B},
     * but the Java compiler may need to generate wrapper methods in
     * those related classes.  In such cases, a {@code Lookup} object on
     * {@code C.E} would be unable to those private members.
     * A workaround for this limitation is the {@link Lookup#in Lookup.in} method,
     * which can transform a lookup on {@code C.E} into one on any of those other
     * classes, without special elevation of privilege.
     * <p>
     * The accesses permitted to a given lookup object may be limited,
     * according to its set of {@link #lookupModes lookupModes},
     * to a subset of members normally accessible to the lookup class.
     * For example, the {@link MethodHandles#publicLookup publicLookup}
     * method produces a lookup object which is only allowed to access
     * public members in public classes.
     * The caller sensitive method {@link MethodHandles#lookup lookup}
     * produces a lookup object with full capabilities relative to
     * its caller class, to emulate all supported bytecode behaviors.
     * Also, the {@link Lookup#in Lookup.in} method may produce a lookup object
     * with fewer access modes than the original lookup object.
     *
     * <p style="font-size:smaller;">
     * <a name="privacc"></a>
     * <em>Discussion of private access:</em>
     * We say that a lookup has <em>private access</em>
     * if its {@linkplain #lookupModes lookup modes}
     * include the possibility of accessing {@code private} members.
     * As documented in the relevant methods elsewhere,
     * only lookups with private access possess the following capabilities:
     * <ul style="font-size:smaller;">
     * <li>access private fields, methods, and constructors of the lookup class
     * <li>create method handles which invoke <a href="MethodHandles.Lookup.html#callsens">caller sensitive</a> methods,
     *     such as {@code Class.forName}
     * <li>create method handles which {@link Lookup#findSpecial emulate invokespecial} instructions
     * <li>avoid <a href="MethodHandles.Lookup.html#secmgr">package access checks</a>
     *     for classes accessible to the lookup class
     * <li>create {@link Lookup#in delegated lookup objects} which have private access to other classes
     *     within the same package member
     * </ul>
     * <p style="font-size:smaller;">
     * Each of these permissions is a consequence of the fact that a lookup object
     * with private access can be securely traced back to an originating class,
     * whose <a href="MethodHandles.Lookup.html#equiv">bytecode behaviors</a> and Java language access permissions
     * can be reliably determined and emulated by method handles.
     *
     * <h1><a name="secmgr"></a>Security manager interactions</h1>
     * Although bytecode instructions can only refer to classes in
     * a related class loader, this API can search for methods in any
     * class, as long as a reference to its {@code Class} object is
     * available.  Such cross-loader references are also possible with the
     * Core Reflection API, and are impossible to bytecode instructions
     * such as {@code invokestatic} or {@code getfield}.
     * There is a {@linkplain java.lang.SecurityManager security manager API}
     * to allow applications to check such cross-loader references.
     * These checks apply to both the {@code MethodHandles.Lookup} API
     * and the Core Reflection API
     * (as found on {@link java.lang.Class Class}).
     * <p>
     * If a security manager is present, member lookups are subject to
     * additional checks.
     * From one to three calls are made to the security manager.
     * Any of these calls can refuse access by throwing a
     * {@link java.lang.SecurityException SecurityException}.
     * Define {@code smgr} as the security manager,
     * {@code lookc} as the lookup class of the current lookup object,
     * {@code refc} as the containing class in which the member
     * is being sought, and {@code defc} as the class in which the
     * member is actually defined.
     * The value {@code lookc} is defined as <em>not present</em>
     * if the current lookup object does not have
     * <a href="MethodHandles.Lookup.html#privacc">private access</a>.
     * The calls are made according to the following rules:
     * <ul>
     * <li><b>Step 1:</b>
     *     If {@code lookc} is not present, or if its class loader is not
     *     the same as or an ancestor of the class loader of {@code refc},
     *     then {@link SecurityManager#checkPackageAccess
     *     smgr.checkPackageAccess(refcPkg)} is called,
     *     where {@code refcPkg} is the package of {@code refc}.
     * <li><b>Step 2:</b>
     *     If the retrieved member is not public and
     *     {@code lookc} is not present, then
     *     {@link SecurityManager#checkPermission smgr.checkPermission}
     *     with {@code RuntimePermission("accessDeclaredMembers")} is called.
     * <li><b>Step 3:</b>
     *     If the retrieved member is not public,
     *     and if {@code lookc} is not present,
     *     and if {@code defc} and {@code refc} are different,
     *     then {@link SecurityManager#checkPackageAccess
     *     smgr.checkPackageAccess(defcPkg)} is called,
     *     where {@code defcPkg} is the package of {@code defc}.
     * </ul>
     * Security checks are performed after other access checks have passed.
     * Therefore, the above rules presuppose a member that is public,
     * or else that is being accessed from a lookup class that has
     * rights to access the member.
     *
     * <h1><a name="callsens"></a>Caller sensitive methods</h1>
     * A small number of Java methods have a special property called caller sensitivity.
     * A <em>caller-sensitive</em> method can behave differently depending on the
     * identity of its immediate caller.
     * <p>
     * If a method handle for a caller-sensitive method is requested,
     * the general rules for <a href="MethodHandles.Lookup.html#equiv">bytecode behaviors</a> apply,
     * but they take account of the lookup class in a special way.
     * The resulting method handle behaves as if it were called
     * from an instruction contained in the lookup class,
     * so that the caller-sensitive method detects the lookup class.
     * (By contrast, the invoker of the method handle is disregarded.)
     * Thus, in the case of caller-sensitive methods,
     * different lookup classes may give rise to
     * differently behaving method handles.
     * <p>
     * In cases where the lookup object is
     * {@link MethodHandles#publicLookup() publicLookup()},
     * or some other lookup object without
     * <a href="MethodHandles.Lookup.html#privacc">private access</a>,
     * the lookup class is disregarded.
     * In such cases, no caller-sensitive method handle can be created,
     * access is forbidden, and the lookup fails with an
     * {@code IllegalAccessException}.
     * <p style="font-size:smaller;">
     * <em>Discussion:</em>
     * For example, the caller-sensitive method
     * {@link java.lang.Class#forName(String) Class.forName(x)}
     * can return varying classes or throw varying exceptions,
     * depending on the class loader of the class that calls it.
     * A public lookup of {@code Class.forName} will fail, because
     * there is no reasonable way to determine its bytecode behavior.
     * <p style="font-size:smaller;">
     * If an application caches method handles for broad sharing,
     * it should use {@code publicLookup()} to create them.
     * If there is a lookup of {@code Class.forName}, it will fail,
     * and the application must take appropriate action in that case.
     * It may be that a later lookup, perhaps during the invocation of a
     * bootstrap method, can incorporate the specific identity
     * of the caller, making the method accessible.
     * <p style="font-size:smaller;">
     * The function {@code MethodHandles.lookup} is caller sensitive
     * so that there can be a secure foundation for lookups.
     * Nearly all other methods in the JSR 292 API rely on lookup
     * objects to check access requests.
     * <p>
     * 当创建需要访问检查时,<em>查找对象</em>是创建方法句柄的工厂方法句柄在调用时不执行访问检查,而是在创建它们时因此,方法句柄访问限制必须在创建方法句柄时实施强制执行这些限制的调用程序类称为{@linkplain #lookupClass lookup class}
     * 。
     * <p>
     * 需要创建方法句柄的查找类将调用{@link MethodHandles#lookup MethodHandleslookup}为自己创建一个工厂当创建{@code Lookup}工厂对象时,查找类的标识
     * 被确定,并安全地存储在{@code Lookup}对象然后,查找类(或其委托)可以在{@code Lookup}对象上使用工厂方法来为访问检查成员创建方法句柄。
     * 这包括允许的所有方法,构造函数和字段查找类,甚至私人类。
     * 
     * <h1> <a name=\"lookups\"> </a>查找工厂方法</h1> {@code Lookup}对象的工厂方法对应于方法,构造函数和字段的所有主要用例每个方法句柄创建通过工厂方法是特定的
     * <em>字节码行为</em>(字节码行为在Java虚拟机规范的第5435节中描述)的功能等价物。
     * 这里是这些工厂方法与行为结果方法句柄：。
     * <table border=1 cellpadding=5 summary="lookup method behaviors">
     * <tr>
     *  <th> <a name=\"equiv\"> </a>查询表达式</th> <th>成员</th> <th>字节码行为</th>
     * </tr>
     * <tr>
     *  <td> {@ link javalanginvokeMethodHandlesLookup#findGetter lookupfindGetter(Cclass,"f",FTclass)} </td>
     *  <td> {@ code FT f;} </td> <td> {@ code(T)thisf;} </td>。
     * </tr>
     * <tr>
     * <td> {@ link javalanginvokeMethodHandlesLookup#findStaticGetter lookupfindStaticGetter(Cclass,"f",FTclass)}
     *  </td> <td> {@ code static} <br> {@code FT f;} </td> <td> { @code(T)Cf;} </td>。
     * </tr>
     * <tr>
     *  <td> {@ link javalanginvokeMethodHandlesLookup#findSetter lookupfindSetter(Cclass,"f",FTclass)} </td>
     *  <td> {@ code FT f;} </td> <td> {@ code thisf = x;} < / td>。
     * </tr>
     * <tr>
     *  <td> {@ link javalanginvokeMethodHandlesLookup#findStaticSetter lookupfindStaticSetter(Cclass,"f",FTclass)}
     *  </td> <td> {@ code static} <br> {@code FT f;} </td> <td> { @code Cf = arg;} </td>。
     * </tr>
     * <tr>
     *  <td> {@ link javalanginvokeMethodHandlesLookup#findVirtual lookupfindVirtual(Cclass,"m",MT)} </td> <td>
     *  {@ code T m(A *);} </td> <td> {@ code )thism(arg *);} </td>。
     * </tr>
     * <tr>
     * <td> {@ link javalanginvokeMethodHandlesLookup#findStatic lookupfindStatic(Cclass,"m",MT)} </td> <td>
     *  {@ code static} <br> {@code T m(A *);} </td> <td> {@ code(T)Cm(arg *);} </td>。
     * </tr>
     * <tr>
     *  <td> {@ link javalanginvokeMethodHandlesLookup#findSpecial lookupfindSpecial(Cclass,"m",MT,thisclass)}
     *  </td> <td> {@ code T m(A *);} </td> <td> {@ code (T)superm(arg *);} </td>。
     * </tr>
     * <tr>
     *  <td> {@ link javalanginvokeMethodHandlesLookup#findConstructor lookupfindConstructor(Cclass,MT)} </td>
     *  <td> {@ code C(A *);} </td> <td> {@ code new C(arg *); } </td>。
     * </tr>
     * <tr>
     *  <td> {@ link javalanginvokeMethodHandlesLookup#unreflectGetter lookupunreflectGetter(aField)} </td> 
     * <td>({@ code static})?<br> {@code FT f;} </td> <td> {@ code FT)aFieldget(thisOrNull);} </td>。
     * </tr>
     * <tr>
     * <td> {@ link javalanginvokeMethodHandlesLookup#unreflectSetter lookupunreflectSetter(aField)} </td> <td>
     * ({@ code static})?<br> {@code FT f;} </td> <td> {@ code aFieldset (thisOrNull,arg);} </td>。
     * </tr>
     * <tr>
     *  <td> {@ link javalanginvokeMethodHandlesLookup#unreflect lookupunreflect(aMethod)} </td> <td>({@ code static}
     * )?<br> {@code T m(A *);} </td> <td> {@code(T)aMethodinvoke(thisOrNull,arg *);} </td>。
     * </tr>
     * <tr>
     *  <td> {@ link javalanginvokeMethodHandlesLookup#unreflectConstructor lookupunreflectConstructor(aConstructor)}
     *  </td> <td> {@ code C(A *);} </td> <td> {@ code(C)aConstructornewInstance(arg *); } </td>。
     * </tr>
     * <tr>
     *  <td> {@ link javalanginvokeMethodHandlesLookup#unreflect lookupunreflect(aMethod)} </td> <td>({@ code static}
     * )?<br> {@code T m(A *);} </td> <td> {@code(T)aMethodinvoke(thisOrNull,arg *);} </td>。
     * </tr>
     * </table>
     * 
     * 这里,类型{@code C}是搜索成员的类或接口,在查找方法中记录为名为{@code refc}的参数。
     * {@code MT}方法类型由返回类型{@代码T}和参数类型序列{@code A *}构造函数还有一系列参数类型{@code A *},并被认为返回类型为{@code C}的新创建的对象Both {@代码MT}
     * 和字段类型{@code FT}记录为名为{@code type}的参数。
     * 这里,类型{@code C}是搜索成员的类或接口,在查找方法中记录为名为{@code refc}的参数。
     * 形式参数{@code this}代表{@code C}类型的自引用;如果它存在,它始终是方法句柄调用的主要参数(在某些{@code protected}成员的情况下,{@code this}可能在类型中
     * 被限制为查找类;参见下文)名称{@code arg}表示所有其他方法句柄参数在Core Reflection API的代码示例中,名称{@code thisOrNull}代表一个空引用,如果被访问的方法
     * 或字段是静态的,并且{@代码this}否则{@code aMethod},{@code aField}和{@code aConstructor}的名称代表与给定成员相对应的反射对象。
     * 这里,类型{@code C}是搜索成员的类或接口,在查找方法中记录为名为{@code refc}的参数。
     * <p>
     * 在给定成员是可变arity(即方法或构造函数)的情况下,返回的方法句柄也将是{@linkplain MethodHandle#asVarargsCollector variable arity}在所有其
     * 他情况下,返回的方法句柄将是固定的。
     * <p style="font-size:smaller;">
     *  <em>讨论：</em>查找方法句柄与基础类成员和字节码行为之间的等价性可以通过以下几种方式分解：
     * <ul style="font-size:smaller;">
     * <li>如果{@code C}无法从查找类的加载器进行符号访问,即使没有等效的Java表达式或字节码常量,查找仍然可以成功<li>同样,如果{@code T}或{@代码MT}不能从查找类的加载器进行符号
     * 访问,查找仍然可以成功例如,{@code MethodHandleinvokeExact}和{@code MethodHandleinvoke}的查找将始终成功,而不管请求的类型如何<li>如果有安全性
     * 管理器安装,它可以禁止在各种理由(<a href=\"MethodHandlesLookuphtml#secmgr\">见下面</a>)查找。
     * 相比之下,{@code CONSTANT_MethodHandle}常量的{@code ldc}指令不是主题到安全管理器检查<li>如果查找的方法具有<a href ="MethodHandlehtml#maxarity">
     * 非常大的arity </a>,方法句柄创建可能失败,因为方法句柄类型具有太多的参数。
     * </ul>
     * 
     * <h1> <a name=\"access\"> </a>访问检查</h1>在创建方法句柄时,在{@code Lookup}的工厂方法中应用访问检查这是与Core的主要区别Reflection API,
     * 因为{@link javalangreflectMethod#invoke javalangreflectMethodinvoke}对每个调用者执行访问检查。
     * <p>
     *  所有访问检查从{@code Lookup}对象开始,该对象将其记录的查找类与创建方法句柄的所有请求进行比较单个{@code Lookup}对象可用于创建任意数量的访问检查的方法句柄对单个查找类
     * <p>
     * {@code Lookup}对象可以与其他可信代码共享,例如元对象协议共享{@code Lookup}对象委托能够在查找类的私有成员上创建方法句柄即使特权代码使用{@代码Lookup}对象,访问检查局限
     * 于​​原始查找类的特权。
     * <p>
     *  查找可能失败,因为查找类不可访问包含类,或者因为缺少所需的类成员,或者因为查找类不可访问所需的类成员,或者因为查找对象不够信任访问成员在任何这些情况下,将从尝试的查找中抛出{@code ReflectiveOperationException}
     * 确切的类将是以下之一：。
     * <ul>
     * <li> NoSuchMethodException&mdash;如果请求了一个方法,但不存在<li> NoSuchFieldException&mdash;如果请求字段但不存在<li> Illegal
     * AccessException&mdash;如果成员存在,但访问检查失败。
     * </ul>
     * <p>
     * 通常,可以为方法{@code M}查找方法句柄的条件没有比查找类可以编译,验证和解析对{@code M}的调用的条件更严格的限制,在JVM会引发{@code NoSuchMethodError}这样的异
     * 常的情况下,方法句柄查找通常会引发相应的检查异常,例如{@code NoSuchMethodException}。
     * 从查找产生的调用方法句柄的效果是<a href =" MethodHandlesLookuphtml#equiv">完全等同于执行对{@code M}的编译,验证和解析调用同一点对于字段和构造函数是相同
     * 的。
     * <p style="font-size:smaller;">
     * <em>讨论：</em>访问检查仅适用于命名和反映的方法,构造函数和字段其他方法手柄创建方法,如{@link MethodHandle#asType MethodHandleasType},不需要任何访
     * 问检查,独立于任何{@code Lookup}对象。
     * <p>
     * 如果所需成员是{@code protected},则应用通常的JVM规则,包括要求查找类必须与所需成员在同一个包中,或必须继承该成员(请参阅Java虚拟机规范,此外,如果所需成员是不同包中的非静态字段或
     * 方法,则所得到的方法句柄只能应用于查找类或其子类之一的对象。
     * 这个要求是通过将前导{@code this}参数的类型从{@code C}(这将必然是查找类的超类)缩小到查找类本身。
     * <p>
     * JVM对{@code invokespecial}指令施加了类似的要求,即接收器参数必须匹配解析方法<em>和</em>当前类。
     * 再次,通过将前导参数的类型缩小到生成的方法句柄(请参阅Java虚拟机规范,第41019节)。
     * <p>
     *  JVM将构造函数和静态初始化器块表示为具有特殊名称({@code"<init>"}和{@code"<clinit>"})的内部方法。
     * 调用指令的内部语法允许它们引用内部方法如果他们是正常的方法,但JVM字节码验证器拒绝他们查找这样的内部方法将产生一个{@code NoSuchMethodException}。
     * <p>
     * 在某些情况下,Java编译器通过创建一个包装器方法来访问同一顶层声明中另一个类的私有方法来获得嵌套类之间的访问。
     * 例如,嵌套类{@code CD}可以访问私有成员其他相关类如{@code C},{@code CDE}或{@code CB},但Java编译器可能需要在这些相关类中生成包装器方法在这种情况下,{@code Lookup}
     * 对象{@code CE}将无法访问这些私有成员此限制的解决方法是{@link Lookup#in Lookupin}方法,它可以将{@code CE}上的查找转换为任何其他类的查找,而不会特殊提升特权。
     * 在某些情况下,Java编译器通过创建一个包装器方法来访问同一顶层声明中另一个类的私有方法来获得嵌套类之间的访问。
     * <p>
     * 给定查找对象允许的访问可以根据其{@link #lookupModes lookupModes}的集合被限制到查找类通常可访问的成员的子集。
     * 例如,{@link MethodHandles#publicLookup publicLookup}方法产生一个仅允许在公共类中访问公共成员的查找对象调用者敏感的方法{@link MethodHandles#lookup lookup}
     * 生成一个具有相对于其调用类的完整能力的查找对象,以模拟所有支持的字节码行为另外,{@链接Lookupin中的Lookupin}方法可以产生具有比原始查找对象更少的访问模式的查找对象。
     * 给定查找对象允许的访问可以根据其{@link #lookupModes lookupModes}的集合被限制到查找类通常可访问的成员的子集。
     * 
     * <p style="font-size:smaller;">
     * <a name=\"privacc\"> </a> <em>私人访问讨论：</em>我们说,如果其{@linkplain #lookupModes查找模式}包括查询,则查找具有<em>私人访问</em>
     * 访问{@code private}成员的可能性如其他地方的相关方法中所述,只有具有私有访问权限的查询具有以下功能：。
     * <ul style="font-size:smaller;">
     * <li>访问查找类的专用字段,方法和构造函数<li>创建调用<a href=\"MethodHandlesLookuphtml#callsens\">调用者敏感</a>方法的方法句柄,例如{@code ClassforName}
     *  <li >创建方法句柄{@link Lookup#findSpecial emulate invokespecial}指令<li>避免<a href=\"MethodHandlesLookuphtml#secmgr\">
     * 包访问检查</a>查找类可访问的类<li> create {@link查找#在委托查找对象},它们具有对同一包成员内的其他类的私有访问。
     * </ul>
     * <p style="font-size:smaller;">
     * 这些权限中的每一个都是具有私有访问权的查找对象可以安全地追溯到其<a href=\"MethodHandlesLookuphtml#equiv\">字节码行为</a>和Java语言访问权限可以通过方法句
     * 柄可靠地确定和仿真。
     * 
     * <h1> <a name=\"secmgr\"> </a>安全管理器交互</h1>虽然字节码指令只能引用相关类加载器中的类,但此API可以搜索任何类中的方法,只要引用其{@code Class}对象是可
     * 用的这种交叉加载器引用也可能与Core Reflection API,并且不可能的字节码指令如{@code invokestatic}或{@code getfield}有一个{@linkplain javalangSecurityManager安全管理器API}
     * 允许应用程序检查这种交叉加载器引用这些检查适用于{@code MethodHandlesLookup} API和Core Reflection API(如{@link javalangClass Class}
     * 上所述)。
     * <p>
     * 如果存在安全管理器,则成员查找受到附加检查从安全管理器进行一到三次调用任何这些调用都可以通过抛出{@link javalangSecurityException SecurityException}定义
     * {@code smgr}作为安全性来拒绝访问管理器,{@code lookc}作为当前查找对象的查找类,{@code refc}作为查找成员的包含类,{@code defc}作为成员实际定义的类如果当前
     * 查找对象没有<a href=\"MethodHandlesLookuphtml#privacc\">私有访问</a>,则{@code lookc}值定义为<em>不存在</em>。
     * </a>根据以下规则：。
     * <ul>
     * <li> <b>步骤1：</b>如果{@code lookc}不存在,或者其类加载器与{@code refc}的类加载器不同或是其祖先,步骤2：</b>如果检索到的成员不是公共的,并且{@code refc}
     * 是{@code refc}的包{@code refc},则调用SecurityManager#checkPackageAccess smgrcheckPackageAccess(refcPkg)代码lo
     * okc}不存在,则将{@link SecurityManager#checkPermission smgrcheckPermission}与{@code RuntimePermission("accessDeclaredMembers")}
     * 称为<li> <b>步骤3：</b>如果检索的成员不是公共的,如果{@code lookc}不存在,如果{@code defc}和{@code refc}不同,则{@link SecurityManager#checkPackageAccess smgrcheckPackageAccess(defcPkg)}
     * 被调用,其中{@code defcPkg}是{@code defc}。
     * </ul>
     * 在其他访问检查通过后执行安全检查因此,上述规则预先假定一个成员是公共的,或者正在从有权访问成员的查找类进行访问
     * 
     *  <h1> <a name=\"callsens\"> </a>调用者敏感方法</h1>少数Java方法具有称为调用者敏感性的特殊属性。
     * <em>调用者敏感</em>方法的行为可能不同取决于其直接呼叫者的身份。
     * <p>
     * 如果请求了调用方敏感方法的方法句柄,则适用于<a href=\"MethodHandlesLookuphtml#equiv\">字节码行为</a>的一般规则,但它们以特殊方式考虑查找类。
     * 生成的方法句柄的行为好像是从包含在查找类中的指令调用的,所以调用者敏感的方法检测查找类(相反,忽略方法句柄的调用者)。因此,在调用者敏感的方法,不同的查找类可能产生不同行为的方法句柄。
     * <p>
     * 如果查找对象是{@link MethodHandles#publicLookup()publicLookup()}或没有<a href=\"MethodHandlesLookuphtml#privacc\">
     * 私有访问</a>的其他查找对象,查找类将被忽略。
     * 在这种情况下case,不能创建任何调用者敏感的方法句柄,访问被禁止,并且查找失败,并显示{@code IllegalAccessException}。
     * <p style="font-size:smaller;">
     *  <em>讨论：</em>例如,调用者敏感的方法{@link javalangClass#forName(String)ClassforName(x)}可以返回不同的类或抛出不同的异常,这取决于类的类加
     * 载器调用it对{@code ClassforName}的公共查找将失败,因为没有合理的方法来确定其字节码行为。
     * <p style="font-size:smaller;">
     * 如果应用程序缓存了广泛共享的方法句柄,它应该使用{@code publicLookup()}来创建它们如果有一个{@code ClassforName}的查找,它会失败,应用程序必须采取适当的行动,在这
     * 种情况下可能是稍后的查找,可能在引导方法的调用期间,可以合并调用者的特定身份,使得该方法可访问。
     * <p style="font-size:smaller;">
     *  函数{@code MethodHandleslookup}是调用者敏感的,所以可以有一个安全的查找基础几乎所有其他方法在JSR 292 API依赖查找对象来检查访问请求
     * 
     */
    public static final
    class Lookup {
        /** The class on behalf of whom the lookup is being performed. */
        private final Class<?> lookupClass;

        /** The allowed sorts of members which may be looked up (PUBLIC, etc.). */
        private final int allowedModes;

        /** A single-bit mask representing {@code public} access,
         *  which may contribute to the result of {@link #lookupModes lookupModes}.
         *  The value, {@code 0x01}, happens to be the same as the value of the
         *  {@code public} {@linkplain java.lang.reflect.Modifier#PUBLIC modifier bit}.
         * <p>
         * 这可能有助于{@link #lookupModes lookupModes}的结果值{@code 0x01}恰好与{@code public} {@linkplain javalangreflectModifier#PUBLIC modifier bit}
         * 的值相同,。
         * 
         */
        public static final int PUBLIC = Modifier.PUBLIC;

        /** A single-bit mask representing {@code private} access,
         *  which may contribute to the result of {@link #lookupModes lookupModes}.
         *  The value, {@code 0x02}, happens to be the same as the value of the
         *  {@code private} {@linkplain java.lang.reflect.Modifier#PRIVATE modifier bit}.
         * <p>
         *  这可能有助于{@link #lookupModes lookupModes}的结果值{@code 0x02}恰好与{@code private} {@linkplain javalangreflectModifier#PRIVATE modifier bit}
         * 的值相同,。
         * 
         */
        public static final int PRIVATE = Modifier.PRIVATE;

        /** A single-bit mask representing {@code protected} access,
         *  which may contribute to the result of {@link #lookupModes lookupModes}.
         *  The value, {@code 0x04}, happens to be the same as the value of the
         *  {@code protected} {@linkplain java.lang.reflect.Modifier#PROTECTED modifier bit}.
         * <p>
         *  这可能有助于{@link #lookupModes lookupModes}的结果值{@code 0x04}恰好与{@code protected} {@linkplain javalangreflectModifier#PROTECTED modifier bit}
         * 的值相同,。
         * 
         */
        public static final int PROTECTED = Modifier.PROTECTED;

        /** A single-bit mask representing {@code package} access (default access),
         *  which may contribute to the result of {@link #lookupModes lookupModes}.
         *  The value is {@code 0x08}, which does not correspond meaningfully to
         *  any particular {@linkplain java.lang.reflect.Modifier modifier bit}.
         * <p>
         * 这可能有助于{@link #lookupModes lookupModes}的结果值为{@code 0x08},它不对应于任何特定的{@linkplain javalangreflectModifier modifier bit}
         * 。
         * 
         */
        public static final int PACKAGE = Modifier.STATIC;

        private static final int ALL_MODES = (PUBLIC | PRIVATE | PROTECTED | PACKAGE);
        private static final int TRUSTED   = -1;

        private static int fixmods(int mods) {
            mods &= (ALL_MODES - PACKAGE);
            return (mods != 0) ? mods : PACKAGE;
        }

        /** Tells which class is performing the lookup.  It is this class against
         *  which checks are performed for visibility and access permissions.
         *  <p>
         *  The class implies a maximum level of access permission,
         *  but the permissions may be additionally limited by the bitmask
         *  {@link #lookupModes lookupModes}, which controls whether non-public members
         *  can be accessed.
         * <p>
         *  对可见性和访问权限执行检查
         * <p>
         *  类意味着最大级别的访问权限,但是权限可能另外受位掩码{@link #lookupModes lookupModes}的限制,该位掩码控制是否可以访问非公共成员
         * 
         * 
         *  @return the lookup class, on behalf of which this lookup object finds members
         */
        public Class<?> lookupClass() {
            return lookupClass;
        }

        // This is just for calling out to MethodHandleImpl.
        private Class<?> lookupClassOrNull() {
            return (allowedModes == TRUSTED) ? null : lookupClass;
        }

        /** Tells which access-protection classes of members this lookup object can produce.
         *  The result is a bit-mask of the bits
         *  {@linkplain #PUBLIC PUBLIC (0x01)},
         *  {@linkplain #PRIVATE PRIVATE (0x02)},
         *  {@linkplain #PROTECTED PROTECTED (0x04)},
         *  and {@linkplain #PACKAGE PACKAGE (0x08)}.
         *  <p>
         *  A freshly-created lookup object
         *  on the {@linkplain java.lang.invoke.MethodHandles#lookup() caller's class}
         *  has all possible bits set, since the caller class can access all its own members.
         *  A lookup object on a new lookup class
         *  {@linkplain java.lang.invoke.MethodHandles.Lookup#in created from a previous lookup object}
         *  may have some mode bits set to zero.
         *  The purpose of this is to restrict access via the new lookup object,
         *  so that it can access only names which can be reached by the original
         *  lookup object, and also by the new lookup class.
         * <p>
         *  结果是位的掩码{@linkplain #PUBLIC PUBLIC(0x01)},{@linkplain #PRIVATE PRIVATE(0x02)},{@linkplain #PROTECTED PROTECTED(0x04)}
         * 和{@linkplain #PACKAGE PACKAGE (0x08)}。
         * <p>
         * 在{@linkplain javalanginvokeMethodHandles#lookup()调用者的类}上新创建的查找对象具有所有可能的位集,因为调用者类可以访问其所有成员在一个新的查找类上的查找
         * 对象{@linkplain javalanginvokeMethodHandlesLookup#in created从先前的查找对象}可以具有设置为零的一些模式位。
         * 这的目的是限制经由新的查找对象的访问,使得它可以仅访问可以由原始查找对象到达的名称,并且还可以通过新的查找类。
         * 
         * 
         *  @return the lookup modes, which limit the kinds of access performed by this lookup object
         */
        public int lookupModes() {
            return allowedModes & ALL_MODES;
        }

        /** Embody the current class (the lookupClass) as a lookup class
         * for method handle creation.
         * Must be called by from a method in this package,
         * which in turn is called by a method not in this package.
         * <p>
         *  用于创建方法句柄必须由此包中的方法调用,而该方法又由不在此包中的方法调用
         * 
         */
        Lookup(Class<?> lookupClass) {
            this(lookupClass, ALL_MODES);
            // make sure we haven't accidentally picked up a privileged class:
            checkUnprivilegedlookupClass(lookupClass, ALL_MODES);
        }

        private Lookup(Class<?> lookupClass, int allowedModes) {
            this.lookupClass = lookupClass;
            this.allowedModes = allowedModes;
        }

        /**
         * Creates a lookup on the specified new lookup class.
         * The resulting object will report the specified
         * class as its own {@link #lookupClass lookupClass}.
         * <p>
         * However, the resulting {@code Lookup} object is guaranteed
         * to have no more access capabilities than the original.
         * In particular, access capabilities can be lost as follows:<ul>
         * <li>If the new lookup class differs from the old one,
         * protected members will not be accessible by virtue of inheritance.
         * (Protected members may continue to be accessible because of package sharing.)
         * <li>If the new lookup class is in a different package
         * than the old one, protected and default (package) members will not be accessible.
         * <li>If the new lookup class is not within the same package member
         * as the old one, private members will not be accessible.
         * <li>If the new lookup class is not accessible to the old lookup class,
         * then no members, not even public members, will be accessible.
         * (In all other cases, public members will continue to be accessible.)
         * </ul>
         *
         * <p>
         * 在指定的新查找类上创建查找结果对象将报告指定的类为自己的{@link #lookupClass lookupClass}
         * <p>
         * 然而,生成的{@code Lookup}对象保证没有比原始的更多的访问能力。
         * 特别是,访问能力可能会丢失如下：<ul> <li>如果新的查找类与旧的不同,成员将无法通过继承访问(受保护的成员可能会因为包共享而继续访问)<li>如果新的查找类位于与旧包不同的包中,则受保护和默认(包
         * )成员将不会<li>如果新查找类与旧查询类不在同一个包成员中,则无法访问私有成员<li>如果新查找类不能访问旧查找类,则没有成员,公共成员,将可访问(在所有其他情况下,公众成员将继续可访问)。
         * 然而,生成的{@code Lookup}对象保证没有比原始的更多的访问能力。
         * </ul>
         * 
         * 
         * @param requestedLookupClass the desired lookup class for the new lookup object
         * @return a lookup object which reports the desired lookup class
         * @throws NullPointerException if the argument is null
         */
        public Lookup in(Class<?> requestedLookupClass) {
            requestedLookupClass.getClass();  // null check
            if (allowedModes == TRUSTED)  // IMPL_LOOKUP can make any lookup at all
                return new Lookup(requestedLookupClass, ALL_MODES);
            if (requestedLookupClass == this.lookupClass)
                return this;  // keep same capabilities
            int newModes = (allowedModes & (ALL_MODES & ~PROTECTED));
            if ((newModes & PACKAGE) != 0
                && !VerifyAccess.isSamePackage(this.lookupClass, requestedLookupClass)) {
                newModes &= ~(PACKAGE|PRIVATE);
            }
            // Allow nestmate lookups to be created without special privilege:
            if ((newModes & PRIVATE) != 0
                && !VerifyAccess.isSamePackageMember(this.lookupClass, requestedLookupClass)) {
                newModes &= ~PRIVATE;
            }
            if ((newModes & PUBLIC) != 0
                && !VerifyAccess.isClassAccessible(requestedLookupClass, this.lookupClass, allowedModes)) {
                // The requested class it not accessible from the lookup class.
                // No permissions.
                newModes = 0;
            }
            checkUnprivilegedlookupClass(requestedLookupClass, newModes);
            return new Lookup(requestedLookupClass, newModes);
        }

        // Make sure outer class is initialized first.
        static { IMPL_NAMES.getClass(); }

        /** Version of lookup which is trusted minimally.
         *  It can only be used to create method handles to
         *  publicly accessible members.
         * <p>
         * 它只能用于为可公开访问的成员创建方法句柄
         * 
         */
        static final Lookup PUBLIC_LOOKUP = new Lookup(Object.class, PUBLIC);

        /** Package-private version of lookup which is trusted. */
        static final Lookup IMPL_LOOKUP = new Lookup(Object.class, TRUSTED);

        private static void checkUnprivilegedlookupClass(Class<?> lookupClass, int allowedModes) {
            String name = lookupClass.getName();
            if (name.startsWith("java.lang.invoke."))
                throw newIllegalArgumentException("illegal lookupClass: "+lookupClass);

            // For caller-sensitive MethodHandles.lookup()
            // disallow lookup more restricted packages
            if (allowedModes == ALL_MODES && lookupClass.getClassLoader() == null) {
                if (name.startsWith("java.") ||
                        (name.startsWith("sun.") && !name.startsWith("sun.invoke."))) {
                    throw newIllegalArgumentException("illegal lookupClass: " + lookupClass);
                }
            }
        }

        /**
         * Displays the name of the class from which lookups are to be made.
         * (The name is the one reported by {@link java.lang.Class#getName() Class.getName}.)
         * If there are restrictions on the access permitted to this lookup,
         * this is indicated by adding a suffix to the class name, consisting
         * of a slash and a keyword.  The keyword represents the strongest
         * allowed access, and is chosen as follows:
         * <ul>
         * <li>If no access is allowed, the suffix is "/noaccess".
         * <li>If only public access is allowed, the suffix is "/public".
         * <li>If only public and package access are allowed, the suffix is "/package".
         * <li>If only public, package, and private access are allowed, the suffix is "/private".
         * </ul>
         * If none of the above cases apply, it is the case that full
         * access (public, package, private, and protected) is allowed.
         * In this case, no suffix is added.
         * This is true only of an object obtained originally from
         * {@link java.lang.invoke.MethodHandles#lookup MethodHandles.lookup}.
         * Objects created by {@link java.lang.invoke.MethodHandles.Lookup#in Lookup.in}
         * always have restricted access, and will display a suffix.
         * <p>
         * (It may seem strange that protected access should be
         * stronger than private access.  Viewed independently from
         * package access, protected access is the first to be lost,
         * because it requires a direct subclass relationship between
         * caller and callee.)
         * <p>
         *  显示要从中进行查找的类的名称(名称是{@link javalangClass#getName()ClassgetName}报告的名称)。
         * 如果对此查找允许的访问存在限制,则通过添加后缀到类名称,由斜杠和关键字组成。关键字表示最强的允许访问,选择如下：。
         * <ul>
         *  <li>如果不允许访问,则后缀为"/ noaccess"<li>如果仅允许公开访问,则后缀为"/ public"<li>如果仅允许公共访问和包访问,则后缀为" package"<li>如果只允许公开,
         * 包装和私有访问,则后缀为"/ private"。
         * </ul>
         * 如果上述情况都不适用,则允许完全访问(public,package,private和protected)。在这种情况下,不添加后缀。
         * 只有最初从{@link javalanginvokeMethodHandles# lookup MethodHandleslookup} {@link javalanginvokeMethodHandlesLookup#in Lookupin}
         * 创建的对象总是具有受限访问权限,并显示一个后缀。
         * 如果上述情况都不适用,则允许完全访问(public,package,private和protected)。在这种情况下,不添加后缀。
         * <p>
         *  (可能看起来很奇怪,受保护的访问应该比私人访问更强烈独立于包访问,受保护的访问是首先丢失,因为它需要调用者和被调用者之间的直接子类关系)
         * 
         * 
         * @see #in
         */
        @Override
        public String toString() {
            String cname = lookupClass.getName();
            switch (allowedModes) {
            case 0:  // no privileges
                return cname + "/noaccess";
            case PUBLIC:
                return cname + "/public";
            case PUBLIC|PACKAGE:
                return cname + "/package";
            case ALL_MODES & ~PROTECTED:
                return cname + "/private";
            case ALL_MODES:
                return cname;
            case TRUSTED:
                return "/trusted";  // internal only; not exported
            default:  // Should not happen, but it's a bitfield...
                cname = cname + "/" + Integer.toHexString(allowedModes);
                assert(false) : cname;
                return cname;
            }
        }

        /**
         * Produces a method handle for a static method.
         * The type of the method handle will be that of the method.
         * (Since static methods do not take receivers, there is no
         * additional receiver argument inserted into the method handle type,
         * as there would be with {@link #findVirtual findVirtual} or {@link #findSpecial findSpecial}.)
         * The method and all its argument types must be accessible to the lookup object.
         * <p>
         * The returned method handle will have
         * {@linkplain MethodHandle#asVarargsCollector variable arity} if and only if
         * the method's variable arity modifier bit ({@code 0x0080}) is set.
         * <p>
         * If the returned method handle is invoked, the method's class will
         * be initialized, if it has not already been initialized.
         * <p><b>Example:</b>
         * <blockquote><pre>{@code
import static java.lang.invoke.MethodHandles.*;
import static java.lang.invoke.MethodType.*;
...
MethodHandle MH_asList = publicLookup().findStatic(Arrays.class,
  "asList", methodType(List.class, Object[].class));
assertEquals("[x, y]", MH_asList.invoke("x", "y").toString());
         * }</pre></blockquote>
         * <p>
         * 生成静态方法的方法句柄方法句柄的类型将是该方法的类型(因为静态方法不接收接收器,没有额外的接收器参数插入到方法句柄类型中,因为将有{@link #findVirtual findVirtual}或{@link #findSpecial findSpecial}
         * )方法及其所有参数类型必须可供查找对象访问。
         * <p>
         *  当且仅当方法的变量arity修饰符位({@code 0x0080})被设置时,返回的方法句柄将具有{@linkplain MethodHandle#asVarargsCollector variable arity}
         * 。
         * <p>
         * 如果调用返回的方法句柄,那么该方法的类将被初始化,如果它尚未初始化<p> <b>示例：</b> <blockquote> <pre> {@ code import static javalanginvokeMethodHandles *; import static javalanginvokeMethodType *; MethodHandle MH_asList = publicLookup()findStatic(Arraysclass,"asList",methodType(Listclass,Object [] class)); assertEquals("[x,y]",MH_asListinvoke("x","y")toString()); }
         *  </pre> </blockquote>。
         * 
         * 
         * @param refc the class from which the method is accessed
         * @param name the name of the method
         * @param type the type of the method
         * @return the desired method handle
         * @throws NoSuchMethodException if the method does not exist
         * @throws IllegalAccessException if access checking fails,
         *                                or if the method is not {@code static},
         *                                or if the method's variable arity modifier bit
         *                                is set and {@code asVarargsCollector} fails
         * @exception SecurityException if a security manager is present and it
         *                              <a href="MethodHandles.Lookup.html#secmgr">refuses access</a>
         * @throws NullPointerException if any argument is null
         */
        public
        MethodHandle findStatic(Class<?> refc, String name, MethodType type) throws NoSuchMethodException, IllegalAccessException {
            MemberName method = resolveOrFail(REF_invokeStatic, refc, name, type);
            return getDirectMethod(REF_invokeStatic, refc, method, findBoundCallerClass(method));
        }

        /**
         * Produces a method handle for a virtual method.
         * The type of the method handle will be that of the method,
         * with the receiver type (usually {@code refc}) prepended.
         * The method and all its argument types must be accessible to the lookup object.
         * <p>
         * When called, the handle will treat the first argument as a receiver
         * and dispatch on the receiver's type to determine which method
         * implementation to enter.
         * (The dispatching action is identical with that performed by an
         * {@code invokevirtual} or {@code invokeinterface} instruction.)
         * <p>
         * The first argument will be of type {@code refc} if the lookup
         * class has full privileges to access the member.  Otherwise
         * the member must be {@code protected} and the first argument
         * will be restricted in type to the lookup class.
         * <p>
         * The returned method handle will have
         * {@linkplain MethodHandle#asVarargsCollector variable arity} if and only if
         * the method's variable arity modifier bit ({@code 0x0080}) is set.
         * <p>
         * Because of the general <a href="MethodHandles.Lookup.html#equiv">equivalence</a> between {@code invokevirtual}
         * instructions and method handles produced by {@code findVirtual},
         * if the class is {@code MethodHandle} and the name string is
         * {@code invokeExact} or {@code invoke}, the resulting
         * method handle is equivalent to one produced by
         * {@link java.lang.invoke.MethodHandles#exactInvoker MethodHandles.exactInvoker} or
         * {@link java.lang.invoke.MethodHandles#invoker MethodHandles.invoker}
         * with the same {@code type} argument.
         *
         * <b>Example:</b>
         * <blockquote><pre>{@code
import static java.lang.invoke.MethodHandles.*;
import static java.lang.invoke.MethodType.*;
...
MethodHandle MH_concat = publicLookup().findVirtual(String.class,
  "concat", methodType(String.class, String.class));
MethodHandle MH_hashCode = publicLookup().findVirtual(Object.class,
  "hashCode", methodType(int.class));
MethodHandle MH_hashCode_String = publicLookup().findVirtual(String.class,
  "hashCode", methodType(int.class));
assertEquals("xy", (String) MH_concat.invokeExact("x", "y"));
assertEquals("xy".hashCode(), (int) MH_hashCode.invokeExact((Object)"xy"));
assertEquals("xy".hashCode(), (int) MH_hashCode_String.invokeExact("xy"));
// interface method:
MethodHandle MH_subSequence = publicLookup().findVirtual(CharSequence.class,
  "subSequence", methodType(CharSequence.class, int.class, int.class));
assertEquals("def", MH_subSequence.invoke("abcdefghi", 3, 6).toString());
// constructor "internal method" must be accessed differently:
MethodType MT_newString = methodType(void.class); //()V for new String()
try { assertEquals("impossible", lookup()
        .findVirtual(String.class, "<init>", MT_newString));
 } catch (NoSuchMethodException ex) { } // OK
MethodHandle MH_newString = publicLookup()
  .findConstructor(String.class, MT_newString);
assertEquals("", (String) MH_newString.invokeExact());
         * }</pre></blockquote>
         *
         * <p>
         *  生成虚方法的方法句柄方法句柄的类型将是方法的类型,具有接收器类型(通常是{@code refc})prepended方法及其所有的参数类型必须可以被查找对象访问
         * <p>
         * 当被调用时,句柄将第一个参数视为接收者,并在接收者的类型上进行调度,以确定要输入哪个方法实现(调度动作与{@code invokevirtual}或{@code invokeinterface}指令相同
         * )。
         * <p>
         *  如果查找类具有访问成员的完全权限,那么第一个参数将是{@code refc}类型。否则成员必须是{@code protected},并且第一个参数将在类型中限制为查找类
         * <p>
         *  当且仅当方法的变量arity修饰符位({@code 0x0080})被设置时,返回的方法句柄将具有{@linkplain MethodHandle#asVarargsCollector variable arity}
         * 。
         * <p>
         * 由于{@code invokevirtual}指令和由{@code findVirtual}生成的方法句柄之间的一般<a href=\"MethodHandlesLookuphtml#equiv\">等
         * 价性</a>,如果类是{@code MethodHandle}和名称字符串是{@code invokeExact}或{@code invoke},生成的方法句柄相当于{@link javalanginvokeMethodHandles#exactInvoker MethodHandlesexactInvoker}
         * 或{@link javalanginvokeMethodHandles#invoker MethodHandlesinvoker}生成的具有相同{@code type}参数的方法句柄。
         * 
         * <b>示例：</b> <blockquote> <pre> {@ code import static javalanginvokeMethodHandles *; import static javalanginvokeMethodType *; MethodHandle MH_concat = publicLookup()findVirtual(Stringclass,"concat",methodType(Stringclass,Stringclass)); MethodHandle MH_hashCode = publicLookup()findVirtual(Objectclass,"hashCode",methodType(intclass)); MethodHandle MH_hashCode_String = publicLookup()findVirtual(Stringclass,"hashCode",methodType(intclass)); assertEquals("xy",(String)MH_concatinvokeExact("x","y")); assertEquals("xy"hashCode(),(int)MH_hashCodeinvokeExact((Object)"xy")); assertEquals("xy"hashCode(),(int)MH_hashCode_StringinvokeExact("xy")); //接口方法：MethodHandle MH_subSequence = publicLookup()findVirtual(CharSequenceclass,"subSequence",methodType(CharSequenceclass,intclass,intclass)); assertEquals("def",MH_subSequenceinvoke("abcdefghi",3,6)toString()); // constructor"internal method"必须以不同的方式访问：MethodType MT_newString = methodType(voidclass); //()V for new String()try {assertEquals("impossible",lookup()findVirtual(Stringclass,"<init>",MT_newString) }
         *  catch(NoSuchMethodException ex){} // OK MethodHandle MH_newString = publicLookup()findConstructor(St
         * ringclass,MT_newString); assertEquals("",(String)MH_newStringinvokeExact()); } </pre> </blockquote>。
         * 
         * 
         * @param refc the class or interface from which the method is accessed
         * @param name the name of the method
         * @param type the type of the method, with the receiver argument omitted
         * @return the desired method handle
         * @throws NoSuchMethodException if the method does not exist
         * @throws IllegalAccessException if access checking fails,
         *                                or if the method is {@code static}
         *                                or if the method's variable arity modifier bit
         *                                is set and {@code asVarargsCollector} fails
         * @exception SecurityException if a security manager is present and it
         *                              <a href="MethodHandles.Lookup.html#secmgr">refuses access</a>
         * @throws NullPointerException if any argument is null
         */
        public MethodHandle findVirtual(Class<?> refc, String name, MethodType type) throws NoSuchMethodException, IllegalAccessException {
            if (refc == MethodHandle.class) {
                MethodHandle mh = findVirtualForMH(name, type);
                if (mh != null)  return mh;
            }
            byte refKind = (refc.isInterface() ? REF_invokeInterface : REF_invokeVirtual);
            MemberName method = resolveOrFail(refKind, refc, name, type);
            return getDirectMethod(refKind, refc, method, findBoundCallerClass(method));
        }
        private MethodHandle findVirtualForMH(String name, MethodType type) {
            // these names require special lookups because of the implicit MethodType argument
            if ("invoke".equals(name))
                return invoker(type);
            if ("invokeExact".equals(name))
                return exactInvoker(type);
            if ("invokeBasic".equals(name))
                return basicInvoker(type);
            assert(!MemberName.isMethodHandleInvokeName(name));
            return null;
        }

        /**
         * Produces a method handle which creates an object and initializes it, using
         * the constructor of the specified type.
         * The parameter types of the method handle will be those of the constructor,
         * while the return type will be a reference to the constructor's class.
         * The constructor and all its argument types must be accessible to the lookup object.
         * <p>
         * The requested type must have a return type of {@code void}.
         * (This is consistent with the JVM's treatment of constructor type descriptors.)
         * <p>
         * The returned method handle will have
         * {@linkplain MethodHandle#asVarargsCollector variable arity} if and only if
         * the constructor's variable arity modifier bit ({@code 0x0080}) is set.
         * <p>
         * If the returned method handle is invoked, the constructor's class will
         * be initialized, if it has not already been initialized.
         * <p><b>Example:</b>
         * <blockquote><pre>{@code
import static java.lang.invoke.MethodHandles.*;
import static java.lang.invoke.MethodType.*;
...
MethodHandle MH_newArrayList = publicLookup().findConstructor(
  ArrayList.class, methodType(void.class, Collection.class));
Collection orig = Arrays.asList("x", "y");
Collection copy = (ArrayList) MH_newArrayList.invokeExact(orig);
assert(orig != copy);
assertEquals(orig, copy);
// a variable-arity constructor:
MethodHandle MH_newProcessBuilder = publicLookup().findConstructor(
  ProcessBuilder.class, methodType(void.class, String[].class));
ProcessBuilder pb = (ProcessBuilder)
  MH_newProcessBuilder.invoke("x", "y", "z");
assertEquals("[x, y, z]", pb.command().toString());
         * }</pre></blockquote>
         * <p>
         * 生成一个方法句柄,使用指定类型的构造函数创建一个对象并初始化它方法句柄的参数类型将是构造函数的参数类型,而返回类型将是对构造函数类的引用构造函数及其所有参数类型必须可供查找对象访问
         * <p>
         *  请求的类型必须具有{@code void}的返回类型(这与JVM对构造函数类型描述符的处理一致)
         * <p>
         *  当且仅当构造函数的变量arity修饰符位({@code 0x0080})被设置时,返回的方法句柄将具有{@linkplain MethodHandle#asVarargsCollector variable arity}
         * 。
         * <p>
         * 如果调用返回的方法句柄,构造函数的类将被初始化,如果它尚未初始化<p> <b>示例：</b> <blockquote> <pre> {@ code import static javalanginvokeMethodHandles *; import static javalanginvokeMethodType *; MethodHandle MH_newArrayList = publicLookup()findConstructor(ArrayListclass,methodType(voidclass,Collectionclass));集合orig = ArraysasList("x","y"); Collection copy =(ArrayList)MH_newArrayListinvokeExact(orig); assert(orig！= copy); assertEquals(orig,copy); //一个变量的构造函数：MethodHandle MH_newProcessBuilder = publicLookup()findConstructor(ProcessBuilderclass,methodType(voidclass,String [] class)); ProcessBuilder pb =(ProcessBuilder)MH_newProcessBuilderinvoke("x","y","z"); assertEquals("[x,y,z]",pbcommand()toString()); }
         *  </pre> </blockquote>。
         * 
         * 
         * @param refc the class or interface from which the method is accessed
         * @param type the type of the method, with the receiver argument omitted, and a void return type
         * @return the desired method handle
         * @throws NoSuchMethodException if the constructor does not exist
         * @throws IllegalAccessException if access checking fails
         *                                or if the method's variable arity modifier bit
         *                                is set and {@code asVarargsCollector} fails
         * @exception SecurityException if a security manager is present and it
         *                              <a href="MethodHandles.Lookup.html#secmgr">refuses access</a>
         * @throws NullPointerException if any argument is null
         */
        public MethodHandle findConstructor(Class<?> refc, MethodType type) throws NoSuchMethodException, IllegalAccessException {
            String name = "<init>";
            MemberName ctor = resolveOrFail(REF_newInvokeSpecial, refc, name, type);
            return getDirectConstructor(refc, ctor);
        }

        /**
         * Produces an early-bound method handle for a virtual method.
         * It will bypass checks for overriding methods on the receiver,
         * <a href="MethodHandles.Lookup.html#equiv">as if called</a> from an {@code invokespecial}
         * instruction from within the explicitly specified {@code specialCaller}.
         * The type of the method handle will be that of the method,
         * with a suitably restricted receiver type prepended.
         * (The receiver type will be {@code specialCaller} or a subtype.)
         * The method and all its argument types must be accessible
         * to the lookup object.
         * <p>
         * Before method resolution,
         * if the explicitly specified caller class is not identical with the
         * lookup class, or if this lookup object does not have
         * <a href="MethodHandles.Lookup.html#privacc">private access</a>
         * privileges, the access fails.
         * <p>
         * The returned method handle will have
         * {@linkplain MethodHandle#asVarargsCollector variable arity} if and only if
         * the method's variable arity modifier bit ({@code 0x0080}) is set.
         * <p style="font-size:smaller;">
         * <em>(Note:  JVM internal methods named {@code "<init>"} are not visible to this API,
         * even though the {@code invokespecial} instruction can refer to them
         * in special circumstances.  Use {@link #findConstructor findConstructor}
         * to access instance initialization methods in a safe manner.)</em>
         * <p><b>Example:</b>
         * <blockquote><pre>{@code
import static java.lang.invoke.MethodHandles.*;
import static java.lang.invoke.MethodType.*;
...
static class Listie extends ArrayList {
  public String toString() { return "[wee Listie]"; }
  static Lookup lookup() { return MethodHandles.lookup(); }
}
...
// no access to constructor via invokeSpecial:
MethodHandle MH_newListie = Listie.lookup()
  .findConstructor(Listie.class, methodType(void.class));
Listie l = (Listie) MH_newListie.invokeExact();
try { assertEquals("impossible", Listie.lookup().findSpecial(
        Listie.class, "<init>", methodType(void.class), Listie.class));
 } catch (NoSuchMethodException ex) { } // OK
// access to super and self methods via invokeSpecial:
MethodHandle MH_super = Listie.lookup().findSpecial(
  ArrayList.class, "toString" , methodType(String.class), Listie.class);
MethodHandle MH_this = Listie.lookup().findSpecial(
  Listie.class, "toString" , methodType(String.class), Listie.class);
MethodHandle MH_duper = Listie.lookup().findSpecial(
  Object.class, "toString" , methodType(String.class), Listie.class);
assertEquals("[]", (String) MH_super.invokeExact(l));
assertEquals(""+l, (String) MH_this.invokeExact(l));
assertEquals("[]", (String) MH_duper.invokeExact(l)); // ArrayList method
try { assertEquals("inaccessible", Listie.lookup().findSpecial(
        String.class, "toString", methodType(String.class), Listie.class));
 } catch (IllegalAccessException ex) { } // OK
Listie subl = new Listie() { public String toString() { return "[subclass]"; } };
assertEquals(""+l, (String) MH_this.invokeExact(subl)); // Listie method
         * }</pre></blockquote>
         *
         * <p>
         * 生成虚拟方法的早期方法句柄它将绕过对接收器上的重写方法的检查,<a href=\"MethodHandlesLookuphtml#equiv\">就像从{@code invokespecial}指令中调
         * 用</a>显式指定{@code specialCaller}方法句柄的类型将是该方法的类型,具有适当受限的接收器类型prepended(接收器类型将是{@code specialCaller}或子类型)
         * 。
         * 方法及其所有参数类型必须可以访问查找对象。
         * <p>
         *  在方法解析之前,如果显式指定的调用程序类与查找类不一致,或者此查找对象没有<a href=\"MethodHandlesLookuphtml#privacc\">私有访问</a>权限,则访问失败
         * <p>
         * 当且仅当方法的变量arity修饰符位({@code 0x0080})被设置时,返回的方法句柄将具有{@linkplain MethodHandle#asVarargsCollector variable arity}
         * 。
         * <p style="font-size:smaller;">
         * <em>(注意：名为{@code} <init>"}的JVM内部方法对此API不可见,即使{@code invokespecial}指令在特殊情况下可以引用它们。
         * 使用{@link #findConstructor findConstructor}以安全的方式访问实例初始化方法)</em> <p> <b>示例：</b> <blockquote> <pre> {@ code import static javalanginvokeMethodHandles *; import static javalanginvokeMethodType *;静态类Listie extends ArrayList {public String toString(){return"[wee Listie]"; }
         *  static Lookup lookup(){return MethodHandleslookup(); }} //无法通过invokeSpecial访问构造函数：MethodHandle MH_ne
         * wListie = Listielookup()findConstructor(Listieclass,methodType(voidclass)); Listie l =(Listie)MH_newL
         * istieinvokeExact(); try {assertEquals("impossible",Listielookup()findSpecial(Listieclass,"<init>",methodType(voidclass),Listieclass)); ()方法返回一个对象的方法,如下面的例子中所示：getSecurity()返回一个对象的方法。
         * <em>(注意：名为{@code} <init>"}的JVM内部方法对此API不可见,即使{@code invokespecial}指令在特殊情况下可以引用它们。
         *  MethodHandle MH_this = Listielookup()findSpecial(Listieclass,"toString",methodType(Stringclass),List
         * ieclass); MethodHandle MH_duper = Listielookup()findSpecial(Objectclass,"toString",methodType(Stringc
         * lass),Listieclass); assertEquals("[]",(String)MH_superinvokeExact(l)); assertEquals(""+ l,(String)MH_
         * thisinvokeExact(1)); assertEquals("[]",(String)MH_duperinvokeExact(l)); // ArrayList方法try {assertEquals("inaccessible",Listielookup()findSpecial(Stringclass,"toString",methodType(Stringclass),Listieclass) }
         * ;}}}}}}}}}}}}}; // catch(IllegalAccessException ex){} // OK Listed subl = new Listie(){public String toString(){return"[subclass]"; }
         * }; assertEquals(""+ l,(String)MH_thisinvokeExact(subl)); // Listie方法} </pre> </blockquote>。
         * <em>(注意：名为{@code} <init>"}的JVM内部方法对此API不可见,即使{@code invokespecial}指令在特殊情况下可以引用它们。
         * 
         * 
         * @param refc the class or interface from which the method is accessed
         * @param name the name of the method (which must not be "&lt;init&gt;")
         * @param type the type of the method, with the receiver argument omitted
         * @param specialCaller the proposed calling class to perform the {@code invokespecial}
         * @return the desired method handle
         * @throws NoSuchMethodException if the method does not exist
         * @throws IllegalAccessException if access checking fails
         *                                or if the method's variable arity modifier bit
         *                                is set and {@code asVarargsCollector} fails
         * @exception SecurityException if a security manager is present and it
         *                              <a href="MethodHandles.Lookup.html#secmgr">refuses access</a>
         * @throws NullPointerException if any argument is null
         */
        public MethodHandle findSpecial(Class<?> refc, String name, MethodType type,
                                        Class<?> specialCaller) throws NoSuchMethodException, IllegalAccessException {
            checkSpecialCaller(specialCaller);
            Lookup specialLookup = this.in(specialCaller);
            MemberName method = specialLookup.resolveOrFail(REF_invokeSpecial, refc, name, type);
            return specialLookup.getDirectMethod(REF_invokeSpecial, refc, method, findBoundCallerClass(method));
        }

        /**
         * Produces a method handle giving read access to a non-static field.
         * The type of the method handle will have a return type of the field's
         * value type.
         * The method handle's single argument will be the instance containing
         * the field.
         * Access checking is performed immediately on behalf of the lookup class.
         * <p>
         * 生成一个方法句柄,提供对非静态字段的读取访问方法句柄的类型将具有字段值类型的返回类型方法句柄的单个参数将是包含字段的实例访问检查立即代表查找类
         * 
         * 
         * @param refc the class or interface from which the method is accessed
         * @param name the field's name
         * @param type the field's type
         * @return a method handle which can load values from the field
         * @throws NoSuchFieldException if the field does not exist
         * @throws IllegalAccessException if access checking fails, or if the field is {@code static}
         * @exception SecurityException if a security manager is present and it
         *                              <a href="MethodHandles.Lookup.html#secmgr">refuses access</a>
         * @throws NullPointerException if any argument is null
         */
        public MethodHandle findGetter(Class<?> refc, String name, Class<?> type) throws NoSuchFieldException, IllegalAccessException {
            MemberName field = resolveOrFail(REF_getField, refc, name, type);
            return getDirectField(REF_getField, refc, field);
        }

        /**
         * Produces a method handle giving write access to a non-static field.
         * The type of the method handle will have a void return type.
         * The method handle will take two arguments, the instance containing
         * the field, and the value to be stored.
         * The second argument will be of the field's value type.
         * Access checking is performed immediately on behalf of the lookup class.
         * <p>
         *  生成一个方法句柄给非静态字段写访问方法句柄的类型将有一个void返回类型方法句柄将接受两个参数,包含字段的实例和要存储的值第二个参数将是字段的值类型访问检查立即代表lookup类执行
         * 
         * 
         * @param refc the class or interface from which the method is accessed
         * @param name the field's name
         * @param type the field's type
         * @return a method handle which can store values into the field
         * @throws NoSuchFieldException if the field does not exist
         * @throws IllegalAccessException if access checking fails, or if the field is {@code static}
         * @exception SecurityException if a security manager is present and it
         *                              <a href="MethodHandles.Lookup.html#secmgr">refuses access</a>
         * @throws NullPointerException if any argument is null
         */
        public MethodHandle findSetter(Class<?> refc, String name, Class<?> type) throws NoSuchFieldException, IllegalAccessException {
            MemberName field = resolveOrFail(REF_putField, refc, name, type);
            return getDirectField(REF_putField, refc, field);
        }

        /**
         * Produces a method handle giving read access to a static field.
         * The type of the method handle will have a return type of the field's
         * value type.
         * The method handle will take no arguments.
         * Access checking is performed immediately on behalf of the lookup class.
         * <p>
         * If the returned method handle is invoked, the field's class will
         * be initialized, if it has not already been initialized.
         * <p>
         * 生成一个方法句柄,提供对一个静态字段的读访问方法句柄的类型将有一个字段的值类型的返回类型方法句柄将不带参数立即执行访问检查代表查找类
         * <p>
         *  如果调用返回的方法句柄,则该字段的类将被初始化(如果尚未初始化)
         * 
         * 
         * @param refc the class or interface from which the method is accessed
         * @param name the field's name
         * @param type the field's type
         * @return a method handle which can load values from the field
         * @throws NoSuchFieldException if the field does not exist
         * @throws IllegalAccessException if access checking fails, or if the field is not {@code static}
         * @exception SecurityException if a security manager is present and it
         *                              <a href="MethodHandles.Lookup.html#secmgr">refuses access</a>
         * @throws NullPointerException if any argument is null
         */
        public MethodHandle findStaticGetter(Class<?> refc, String name, Class<?> type) throws NoSuchFieldException, IllegalAccessException {
            MemberName field = resolveOrFail(REF_getStatic, refc, name, type);
            return getDirectField(REF_getStatic, refc, field);
        }

        /**
         * Produces a method handle giving write access to a static field.
         * The type of the method handle will have a void return type.
         * The method handle will take a single
         * argument, of the field's value type, the value to be stored.
         * Access checking is performed immediately on behalf of the lookup class.
         * <p>
         * If the returned method handle is invoked, the field's class will
         * be initialized, if it has not already been initialized.
         * <p>
         *  生成一个给静态字段写访问的方法句柄方法句柄的类型将有一个void返回类型方法句柄将获取字段值类型的单个参数,要存储的值访问检查立即执行的查找类
         * <p>
         *  如果调用返回的方法句柄,则该字段的类将被初始化(如果尚未初始化)
         * 
         * 
         * @param refc the class or interface from which the method is accessed
         * @param name the field's name
         * @param type the field's type
         * @return a method handle which can store values into the field
         * @throws NoSuchFieldException if the field does not exist
         * @throws IllegalAccessException if access checking fails, or if the field is not {@code static}
         * @exception SecurityException if a security manager is present and it
         *                              <a href="MethodHandles.Lookup.html#secmgr">refuses access</a>
         * @throws NullPointerException if any argument is null
         */
        public MethodHandle findStaticSetter(Class<?> refc, String name, Class<?> type) throws NoSuchFieldException, IllegalAccessException {
            MemberName field = resolveOrFail(REF_putStatic, refc, name, type);
            return getDirectField(REF_putStatic, refc, field);
        }

        /**
         * Produces an early-bound method handle for a non-static method.
         * The receiver must have a supertype {@code defc} in which a method
         * of the given name and type is accessible to the lookup class.
         * The method and all its argument types must be accessible to the lookup object.
         * The type of the method handle will be that of the method,
         * without any insertion of an additional receiver parameter.
         * The given receiver will be bound into the method handle,
         * so that every call to the method handle will invoke the
         * requested method on the given receiver.
         * <p>
         * The returned method handle will have
         * {@linkplain MethodHandle#asVarargsCollector variable arity} if and only if
         * the method's variable arity modifier bit ({@code 0x0080}) is set
         * <em>and</em> the trailing array argument is not the only argument.
         * (If the trailing array argument is the only argument,
         * the given receiver value will be bound to it.)
         * <p>
         * This is equivalent to the following code:
         * <blockquote><pre>{@code
import static java.lang.invoke.MethodHandles.*;
import static java.lang.invoke.MethodType.*;
...
MethodHandle mh0 = lookup().findVirtual(defc, name, type);
MethodHandle mh1 = mh0.bindTo(receiver);
MethodType mt1 = mh1.type();
if (mh0.isVarargsCollector())
  mh1 = mh1.asVarargsCollector(mt1.parameterType(mt1.parameterCount()-1));
return mh1;
         * }</pre></blockquote>
         * where {@code defc} is either {@code receiver.getClass()} or a super
         * type of that class, in which the requested method is accessible
         * to the lookup class.
         * (Note that {@code bindTo} does not preserve variable arity.)
         * <p>
         * 为非静态方法生成早期绑定的方法句柄接收者必须有一个超类型{@code defc},其中给定名称和类型的方法对于查找类是可访问的该方法及其所有参数类型必须可访问到查找对象方法句柄的类型将是方法的类型,没有
         * 任何插入附加的接收器参数给定的接收器将被绑定到方法句柄,以便每次调用方法句柄将调用请求的方法给定接收器。
         * <p>
         * 如果且仅当方法的变量arity修饰符位({@code 0x0080})设置为<em>且</em>后面的数组参数不是唯一的,返回的方法句柄将具有{@linkplain MethodHandle#asVarargsCollector variable arity}
         * 参数(如果尾数组参数是唯一的参数,给定的接收器值将绑定到它)。
         * <p>
         * 这相当于下面的代码：<blockquote> <pre> {@ code import static javalanginvokeMethodHandles *; import static javalanginvokeMethodType *; MethodHandle mh0 = lookup()findVirtual(defc,name,type); MethodHandle mh1 = mh0bindTo(receiver); MethodType mt1 = mh1type(); if(mh0isVarargsCollector())mh1 = mh1asVarargsCollector(mt1parameterType(mt1parameterCount() -  1)); return mh1; }
         *  </block>其中{@code defc}是{@code receivergetClass()}或该类的超类型,其中所请求的方法是可访问的查找类(注意{@code bindTo }不保存变量arit
         * y)。
         * 
         * 
         * @param receiver the object from which the method is accessed
         * @param name the name of the method
         * @param type the type of the method, with the receiver argument omitted
         * @return the desired method handle
         * @throws NoSuchMethodException if the method does not exist
         * @throws IllegalAccessException if access checking fails
         *                                or if the method's variable arity modifier bit
         *                                is set and {@code asVarargsCollector} fails
         * @exception SecurityException if a security manager is present and it
         *                              <a href="MethodHandles.Lookup.html#secmgr">refuses access</a>
         * @throws NullPointerException if any argument is null
         * @see MethodHandle#bindTo
         * @see #findVirtual
         */
        public MethodHandle bind(Object receiver, String name, MethodType type) throws NoSuchMethodException, IllegalAccessException {
            Class<? extends Object> refc = receiver.getClass(); // may get NPE
            MemberName method = resolveOrFail(REF_invokeSpecial, refc, name, type);
            MethodHandle mh = getDirectMethodNoRestrict(REF_invokeSpecial, refc, method, findBoundCallerClass(method));
            return mh.bindArgumentL(0, receiver).setVarargs(method);
        }

        /**
         * Makes a <a href="MethodHandleInfo.html#directmh">direct method handle</a>
         * to <i>m</i>, if the lookup class has permission.
         * If <i>m</i> is non-static, the receiver argument is treated as an initial argument.
         * If <i>m</i> is virtual, overriding is respected on every call.
         * Unlike the Core Reflection API, exceptions are <em>not</em> wrapped.
         * The type of the method handle will be that of the method,
         * with the receiver type prepended (but only if it is non-static).
         * If the method's {@code accessible} flag is not set,
         * access checking is performed immediately on behalf of the lookup class.
         * If <i>m</i> is not public, do not share the resulting handle with untrusted parties.
         * <p>
         * The returned method handle will have
         * {@linkplain MethodHandle#asVarargsCollector variable arity} if and only if
         * the method's variable arity modifier bit ({@code 0x0080}) is set.
         * <p>
         * If <i>m</i> is static, and
         * if the returned method handle is invoked, the method's class will
         * be initialized, if it has not already been initialized.
         * <p>
         * 如果查找类具有许可权,则将<a href=\"MethodHandleInfohtml#directmh\">直接方法句柄</a>更改为<i> m </i>。
         * 如果<i> m </i>是非静态的,接收器参数被视为初始参数如果<i> m </i>是虚拟的,则在每次调用时都会重写覆盖。
         * 与Core Reflection API不同,异常不是</em>包装方法句柄的类型是该方法的类型,接收器类型是前置的(但是只有当它是非静态的)。
         * 如果方法的{@code accessible}标志没有被设置,则访问检查立即代表查找类If执行</i>不是公开的,不要与不受信任的方共享生成的句柄。
         * <p>
         * 当且仅当方法的变量arity修饰符位({@code 0x0080})被设置时,返回的方法句柄将具有{@linkplain MethodHandle#asVarargsCollector variable arity}
         * 。
         * <p>
         *  如果<i> m </i>是静态的,并且如果调用返回的方法句柄,则该方法的类将被初始化,如果它尚未被初始化
         * 
         * 
         * @param m the reflected method
         * @return a method handle which can invoke the reflected method
         * @throws IllegalAccessException if access checking fails
         *                                or if the method's variable arity modifier bit
         *                                is set and {@code asVarargsCollector} fails
         * @throws NullPointerException if the argument is null
         */
        public MethodHandle unreflect(Method m) throws IllegalAccessException {
            if (m.getDeclaringClass() == MethodHandle.class) {
                MethodHandle mh = unreflectForMH(m);
                if (mh != null)  return mh;
            }
            MemberName method = new MemberName(m);
            byte refKind = method.getReferenceKind();
            if (refKind == REF_invokeSpecial)
                refKind = REF_invokeVirtual;
            assert(method.isMethod());
            Lookup lookup = m.isAccessible() ? IMPL_LOOKUP : this;
            return lookup.getDirectMethodNoSecurityManager(refKind, method.getDeclaringClass(), method, findBoundCallerClass(method));
        }
        private MethodHandle unreflectForMH(Method m) {
            // these names require special lookups because they throw UnsupportedOperationException
            if (MemberName.isMethodHandleInvokeName(m.getName()))
                return MethodHandleImpl.fakeMethodHandleInvoke(new MemberName(m));
            return null;
        }

        /**
         * Produces a method handle for a reflected method.
         * It will bypass checks for overriding methods on the receiver,
         * <a href="MethodHandles.Lookup.html#equiv">as if called</a> from an {@code invokespecial}
         * instruction from within the explicitly specified {@code specialCaller}.
         * The type of the method handle will be that of the method,
         * with a suitably restricted receiver type prepended.
         * (The receiver type will be {@code specialCaller} or a subtype.)
         * If the method's {@code accessible} flag is not set,
         * access checking is performed immediately on behalf of the lookup class,
         * as if {@code invokespecial} instruction were being linked.
         * <p>
         * Before method resolution,
         * if the explicitly specified caller class is not identical with the
         * lookup class, or if this lookup object does not have
         * <a href="MethodHandles.Lookup.html#privacc">private access</a>
         * privileges, the access fails.
         * <p>
         * The returned method handle will have
         * {@linkplain MethodHandle#asVarargsCollector variable arity} if and only if
         * the method's variable arity modifier bit ({@code 0x0080}) is set.
         * <p>
         * 生成反射方法的方法句柄它将绕过对接收器上的重写方法的检查,<a href=\"MethodHandlesLookuphtml#equiv\">就像从{@code invokespecial}指令中明确指
         * 定的{ @code specialCaller}方法句柄的类型将是该方法的类型,具有适当受限的接收器类型prepended(接收器类型将是{@code specialCaller}或子类型)如果方法的{@code accessible}
         * 标志不是设置,访问检查立即代表lookup类执行,就像{@code invokespecial}指令被链接。
         * <p>
         * 在方法解析之前,如果显式指定的调用程序类与查找类不一致,或者此查找对象没有<a href=\"MethodHandlesLookuphtml#privacc\">私有访问</a>权限,则访问失败
         * <p>
         *  当且仅当方法的变量arity修饰符位({@code 0x0080})被设置时,返回的方法句柄将具有{@linkplain MethodHandle#asVarargsCollector variable arity}
         * 。
         * 
         * 
         * @param m the reflected method
         * @param specialCaller the class nominally calling the method
         * @return a method handle which can invoke the reflected method
         * @throws IllegalAccessException if access checking fails
         *                                or if the method's variable arity modifier bit
         *                                is set and {@code asVarargsCollector} fails
         * @throws NullPointerException if any argument is null
         */
        public MethodHandle unreflectSpecial(Method m, Class<?> specialCaller) throws IllegalAccessException {
            checkSpecialCaller(specialCaller);
            Lookup specialLookup = this.in(specialCaller);
            MemberName method = new MemberName(m, true);
            assert(method.isMethod());
            // ignore m.isAccessible:  this is a new kind of access
            return specialLookup.getDirectMethodNoSecurityManager(REF_invokeSpecial, method.getDeclaringClass(), method, findBoundCallerClass(method));
        }

        /**
         * Produces a method handle for a reflected constructor.
         * The type of the method handle will be that of the constructor,
         * with the return type changed to the declaring class.
         * The method handle will perform a {@code newInstance} operation,
         * creating a new instance of the constructor's class on the
         * arguments passed to the method handle.
         * <p>
         * If the constructor's {@code accessible} flag is not set,
         * access checking is performed immediately on behalf of the lookup class.
         * <p>
         * The returned method handle will have
         * {@linkplain MethodHandle#asVarargsCollector variable arity} if and only if
         * the constructor's variable arity modifier bit ({@code 0x0080}) is set.
         * <p>
         * If the returned method handle is invoked, the constructor's class will
         * be initialized, if it has not already been initialized.
         * <p>
         *  生成反射构造函数的方法句柄方法句柄的类型将是构造函数的类型,返回类型更改为声明类方法句柄将执行一个{@code newInstance}操作,创建一个构造函数的新实例类传递给方法句柄的参数
         * <p>
         * 如果构造函数的{@code accessible}标志未设置,则将立即代表查找类执行访问检查
         * <p>
         *  当且仅当构造函数的变量arity修饰符位({@code 0x0080})被设置时,返回的方法句柄将具有{@linkplain MethodHandle#asVarargsCollector variable arity}
         * 。
         * <p>
         *  如果返回的方法句柄被调用,构造函数的类将被初始化,如果它还没有被初始化
         * 
         * 
         * @param c the reflected constructor
         * @return a method handle which can invoke the reflected constructor
         * @throws IllegalAccessException if access checking fails
         *                                or if the method's variable arity modifier bit
         *                                is set and {@code asVarargsCollector} fails
         * @throws NullPointerException if the argument is null
         */
        public MethodHandle unreflectConstructor(Constructor<?> c) throws IllegalAccessException {
            MemberName ctor = new MemberName(c);
            assert(ctor.isConstructor());
            Lookup lookup = c.isAccessible() ? IMPL_LOOKUP : this;
            return lookup.getDirectConstructorNoSecurityManager(ctor.getDeclaringClass(), ctor);
        }

        /**
         * Produces a method handle giving read access to a reflected field.
         * The type of the method handle will have a return type of the field's
         * value type.
         * If the field is static, the method handle will take no arguments.
         * Otherwise, its single argument will be the instance containing
         * the field.
         * If the field's {@code accessible} flag is not set,
         * access checking is performed immediately on behalf of the lookup class.
         * <p>
         * If the field is static, and
         * if the returned method handle is invoked, the field's class will
         * be initialized, if it has not already been initialized.
         * <p>
         * 生成一个方法句柄,给出对反射字段的读访问方法句柄的类型将具有字段值类型的返回类型如果字段是静态的,则方法句柄将不带参数。
         * 否则,其单个参数将是包含字段如果字段的{@code accessible}标志未设置,则将立即代表查找类执行访问检查。
         * <p>
         *  如果字段是静态的,并且如果调用返回的方法句柄,则字段的类将被初始化,如果它尚未被初始化
         * 
         * 
         * @param f the reflected field
         * @return a method handle which can load values from the reflected field
         * @throws IllegalAccessException if access checking fails
         * @throws NullPointerException if the argument is null
         */
        public MethodHandle unreflectGetter(Field f) throws IllegalAccessException {
            return unreflectField(f, false);
        }
        private MethodHandle unreflectField(Field f, boolean isSetter) throws IllegalAccessException {
            MemberName field = new MemberName(f, isSetter);
            assert(isSetter
                    ? MethodHandleNatives.refKindIsSetter(field.getReferenceKind())
                    : MethodHandleNatives.refKindIsGetter(field.getReferenceKind()));
            Lookup lookup = f.isAccessible() ? IMPL_LOOKUP : this;
            return lookup.getDirectFieldNoSecurityManager(field.getReferenceKind(), f.getDeclaringClass(), field);
        }

        /**
         * Produces a method handle giving write access to a reflected field.
         * The type of the method handle will have a void return type.
         * If the field is static, the method handle will take a single
         * argument, of the field's value type, the value to be stored.
         * Otherwise, the two arguments will be the instance containing
         * the field, and the value to be stored.
         * If the field's {@code accessible} flag is not set,
         * access checking is performed immediately on behalf of the lookup class.
         * <p>
         * If the field is static, and
         * if the returned method handle is invoked, the field's class will
         * be initialized, if it has not already been initialized.
         * <p>
         * 生成一个方法句柄给出对反射字段的写访问方法句柄的类型将有一个void返回类型如果字段是静态的,方法句柄将采用单个参数,字段的值类型,要存储的值否则,两个参数将是包含该字段的实例,以及要存储的值如果字段的
         * {@code accessible}标志未设置,则将立即代表查找类执行访问检查。
         * <p>
         *  如果字段是静态的,并且如果调用返回的方法句柄,则字段的类将被初始化,如果它尚未被初始化
         * 
         * 
         * @param f the reflected field
         * @return a method handle which can store values into the reflected field
         * @throws IllegalAccessException if access checking fails
         * @throws NullPointerException if the argument is null
         */
        public MethodHandle unreflectSetter(Field f) throws IllegalAccessException {
            return unreflectField(f, true);
        }

        /**
         * Cracks a <a href="MethodHandleInfo.html#directmh">direct method handle</a>
         * created by this lookup object or a similar one.
         * Security and access checks are performed to ensure that this lookup object
         * is capable of reproducing the target method handle.
         * This means that the cracking may fail if target is a direct method handle
         * but was created by an unrelated lookup object.
         * This can happen if the method handle is <a href="MethodHandles.Lookup.html#callsens">caller sensitive</a>
         * and was created by a lookup object for a different class.
         * <p>
         * 破解此查找对象或类似对象创建的<a href=\"MethodHandleInfohtml#directmh\">直接方法手柄</a>执行安全性和访问权限检查,以确保此查找对象能够再现目标方法句柄这意味
         * 着如果目标是直接方法句柄但是由不相关的查找对象创建,则破解可能会失败如果方法句柄是<a href=\"MethodHandlesLookuphtml#callsens\">调用者敏感</a>,并且是通过
         * 查找创建的,则可能会发生对象为不同的类。
         * 
         * 
         * @param target a direct method handle to crack into symbolic reference components
         * @return a symbolic reference which can be used to reconstruct this method handle from this lookup object
         * @exception SecurityException if a security manager is present and it
         *                              <a href="MethodHandles.Lookup.html#secmgr">refuses access</a>
         * @throws IllegalArgumentException if the target is not a direct method handle or if access checking fails
         * @exception NullPointerException if the target is {@code null}
         * @see MethodHandleInfo
         * @since 1.8
         */
        public MethodHandleInfo revealDirect(MethodHandle target) {
            MemberName member = target.internalMemberName();
            if (member == null || (!member.isResolved() && !member.isMethodHandleInvoke()))
                throw newIllegalArgumentException("not a direct method handle");
            Class<?> defc = member.getDeclaringClass();
            byte refKind = member.getReferenceKind();
            assert(MethodHandleNatives.refKindIsValid(refKind));
            if (refKind == REF_invokeSpecial && !target.isInvokeSpecial())
                // Devirtualized method invocation is usually formally virtual.
                // To avoid creating extra MemberName objects for this common case,
                // we encode this extra degree of freedom using MH.isInvokeSpecial.
                refKind = REF_invokeVirtual;
            if (refKind == REF_invokeVirtual && defc.isInterface())
                // Symbolic reference is through interface but resolves to Object method (toString, etc.)
                refKind = REF_invokeInterface;
            // Check SM permissions and member access before cracking.
            try {
                checkAccess(refKind, defc, member);
                checkSecurityManager(defc, member);
            } catch (IllegalAccessException ex) {
                throw new IllegalArgumentException(ex);
            }
            if (allowedModes != TRUSTED && member.isCallerSensitive()) {
                Class<?> callerClass = target.internalCallerClass();
                if (!hasPrivateAccess() || callerClass != lookupClass())
                    throw new IllegalArgumentException("method handle is caller sensitive: "+callerClass);
            }
            // Produce the handle to the results.
            return new InfoFromMemberName(this, member, refKind);
        }

        /// Helper methods, all package-private.

        MemberName resolveOrFail(byte refKind, Class<?> refc, String name, Class<?> type) throws NoSuchFieldException, IllegalAccessException {
            checkSymbolicClass(refc);  // do this before attempting to resolve
            name.getClass();  // NPE
            type.getClass();  // NPE
            return IMPL_NAMES.resolveOrFail(refKind, new MemberName(refc, name, type, refKind), lookupClassOrNull(),
                                            NoSuchFieldException.class);
        }

        MemberName resolveOrFail(byte refKind, Class<?> refc, String name, MethodType type) throws NoSuchMethodException, IllegalAccessException {
            checkSymbolicClass(refc);  // do this before attempting to resolve
            name.getClass();  // NPE
            type.getClass();  // NPE
            checkMethodName(refKind, name);  // NPE check on name
            return IMPL_NAMES.resolveOrFail(refKind, new MemberName(refc, name, type, refKind), lookupClassOrNull(),
                                            NoSuchMethodException.class);
        }

        MemberName resolveOrFail(byte refKind, MemberName member) throws ReflectiveOperationException {
            checkSymbolicClass(member.getDeclaringClass());  // do this before attempting to resolve
            member.getName().getClass();  // NPE
            member.getType().getClass();  // NPE
            return IMPL_NAMES.resolveOrFail(refKind, member, lookupClassOrNull(),
                                            ReflectiveOperationException.class);
        }

        void checkSymbolicClass(Class<?> refc) throws IllegalAccessException {
            refc.getClass();  // NPE
            Class<?> caller = lookupClassOrNull();
            if (caller != null && !VerifyAccess.isClassAccessible(refc, caller, allowedModes))
                throw new MemberName(refc).makeAccessException("symbolic reference class is not public", this);
        }

        /** Check name for an illegal leading "&lt;" character. */
        void checkMethodName(byte refKind, String name) throws NoSuchMethodException {
            if (name.startsWith("<") && refKind != REF_newInvokeSpecial)
                throw new NoSuchMethodException("illegal method name: "+name);
        }


        /**
         * Find my trustable caller class if m is a caller sensitive method.
         * If this lookup object has private access, then the caller class is the lookupClass.
         * Otherwise, if m is caller-sensitive, throw IllegalAccessException.
         * <p>
         *  如果m是调用者敏感方法,则查找我的可信任的调用者类如果这个查找对象具有私有访问,则调用者类是lookupClass否则,如果m是调用者敏感的,抛出IllegalAccessException
         * 
         */
        Class<?> findBoundCallerClass(MemberName m) throws IllegalAccessException {
            Class<?> callerClass = null;
            if (MethodHandleNatives.isCallerSensitive(m)) {
                // Only lookups with private access are allowed to resolve caller-sensitive methods
                if (hasPrivateAccess()) {
                    callerClass = lookupClass;
                } else {
                    throw new IllegalAccessException("Attempt to lookup caller-sensitive method using restricted lookup object");
                }
            }
            return callerClass;
        }

        private boolean hasPrivateAccess() {
            return (allowedModes & PRIVATE) != 0;
        }

        /**
         * Perform necessary <a href="MethodHandles.Lookup.html#secmgr">access checks</a>.
         * Determines a trustable caller class to compare with refc, the symbolic reference class.
         * If this lookup object has private access, then the caller class is the lookupClass.
         * <p>
         * 执行必要的<a href=\"MethodHandlesLookuphtml#secmgr\">访问检查</a>确定可信任的调用程序类以与refc(符号引用类)进行比较如果此查找对象具有私有访问权,则调
         * 用程序类是lookupClass。
         * 
         */
        void checkSecurityManager(Class<?> refc, MemberName m) {
            SecurityManager smgr = System.getSecurityManager();
            if (smgr == null)  return;
            if (allowedModes == TRUSTED)  return;

            // Step 1:
            boolean fullPowerLookup = hasPrivateAccess();
            if (!fullPowerLookup ||
                !VerifyAccess.classLoaderIsAncestor(lookupClass, refc)) {
                ReflectUtil.checkPackageAccess(refc);
            }

            // Step 2:
            if (m.isPublic()) return;
            if (!fullPowerLookup) {
                smgr.checkPermission(SecurityConstants.CHECK_MEMBER_ACCESS_PERMISSION);
            }

            // Step 3:
            Class<?> defc = m.getDeclaringClass();
            if (!fullPowerLookup && defc != refc) {
                ReflectUtil.checkPackageAccess(defc);
            }
        }

        void checkMethod(byte refKind, Class<?> refc, MemberName m) throws IllegalAccessException {
            boolean wantStatic = (refKind == REF_invokeStatic);
            String message;
            if (m.isConstructor())
                message = "expected a method, not a constructor";
            else if (!m.isMethod())
                message = "expected a method";
            else if (wantStatic != m.isStatic())
                message = wantStatic ? "expected a static method" : "expected a non-static method";
            else
                { checkAccess(refKind, refc, m); return; }
            throw m.makeAccessException(message, this);
        }

        void checkField(byte refKind, Class<?> refc, MemberName m) throws IllegalAccessException {
            boolean wantStatic = !MethodHandleNatives.refKindHasReceiver(refKind);
            String message;
            if (wantStatic != m.isStatic())
                message = wantStatic ? "expected a static field" : "expected a non-static field";
            else
                { checkAccess(refKind, refc, m); return; }
            throw m.makeAccessException(message, this);
        }

        /** Check public/protected/private bits on the symbolic reference class and its member. */
        void checkAccess(byte refKind, Class<?> refc, MemberName m) throws IllegalAccessException {
            assert(m.referenceKindIsConsistentWith(refKind) &&
                   MethodHandleNatives.refKindIsValid(refKind) &&
                   (MethodHandleNatives.refKindIsField(refKind) == m.isField()));
            int allowedModes = this.allowedModes;
            if (allowedModes == TRUSTED)  return;
            int mods = m.getModifiers();
            if (Modifier.isProtected(mods) &&
                    refKind == REF_invokeVirtual &&
                    m.getDeclaringClass() == Object.class &&
                    m.getName().equals("clone") &&
                    refc.isArray()) {
                // The JVM does this hack also.
                // (See ClassVerifier::verify_invoke_instructions
                // and LinkResolver::check_method_accessability.)
                // Because the JVM does not allow separate methods on array types,
                // there is no separate method for int[].clone.
                // All arrays simply inherit Object.clone.
                // But for access checking logic, we make Object.clone
                // (normally protected) appear to be public.
                // Later on, when the DirectMethodHandle is created,
                // its leading argument will be restricted to the
                // requested array type.
                // N.B. The return type is not adjusted, because
                // that is *not* the bytecode behavior.
                mods ^= Modifier.PROTECTED | Modifier.PUBLIC;
            }
            if (Modifier.isProtected(mods) && refKind == REF_newInvokeSpecial) {
                // cannot "new" a protected ctor in a different package
                mods ^= Modifier.PROTECTED;
            }
            if (Modifier.isFinal(mods) &&
                    MethodHandleNatives.refKindIsSetter(refKind))
                throw m.makeAccessException("unexpected set of a final field", this);
            if (Modifier.isPublic(mods) && Modifier.isPublic(refc.getModifiers()) && allowedModes != 0)
                return;  // common case
            int requestedModes = fixmods(mods);  // adjust 0 => PACKAGE
            if ((requestedModes & allowedModes) != 0) {
                if (VerifyAccess.isMemberAccessible(refc, m.getDeclaringClass(),
                                                    mods, lookupClass(), allowedModes))
                    return;
            } else {
                // Protected members can also be checked as if they were package-private.
                if ((requestedModes & PROTECTED) != 0 && (allowedModes & PACKAGE) != 0
                        && VerifyAccess.isSamePackage(m.getDeclaringClass(), lookupClass()))
                    return;
            }
            throw m.makeAccessException(accessFailedMessage(refc, m), this);
        }

        String accessFailedMessage(Class<?> refc, MemberName m) {
            Class<?> defc = m.getDeclaringClass();
            int mods = m.getModifiers();
            // check the class first:
            boolean classOK = (Modifier.isPublic(defc.getModifiers()) &&
                               (defc == refc ||
                                Modifier.isPublic(refc.getModifiers())));
            if (!classOK && (allowedModes & PACKAGE) != 0) {
                classOK = (VerifyAccess.isClassAccessible(defc, lookupClass(), ALL_MODES) &&
                           (defc == refc ||
                            VerifyAccess.isClassAccessible(refc, lookupClass(), ALL_MODES)));
            }
            if (!classOK)
                return "class is not public";
            if (Modifier.isPublic(mods))
                return "access to public member failed";  // (how?)
            if (Modifier.isPrivate(mods))
                return "member is private";
            if (Modifier.isProtected(mods))
                return "member is protected";
            return "member is private to package";
        }

        private static final boolean ALLOW_NESTMATE_ACCESS = false;

        private void checkSpecialCaller(Class<?> specialCaller) throws IllegalAccessException {
            int allowedModes = this.allowedModes;
            if (allowedModes == TRUSTED)  return;
            if (!hasPrivateAccess()
                || (specialCaller != lookupClass()
                    && !(ALLOW_NESTMATE_ACCESS &&
                         VerifyAccess.isSamePackageMember(specialCaller, lookupClass()))))
                throw new MemberName(specialCaller).
                    makeAccessException("no private access for invokespecial", this);
        }

        private boolean restrictProtectedReceiver(MemberName method) {
            // The accessing class only has the right to use a protected member
            // on itself or a subclass.  Enforce that restriction, from JVMS 5.4.4, etc.
            if (!method.isProtected() || method.isStatic()
                || allowedModes == TRUSTED
                || method.getDeclaringClass() == lookupClass()
                || VerifyAccess.isSamePackage(method.getDeclaringClass(), lookupClass())
                || (ALLOW_NESTMATE_ACCESS &&
                    VerifyAccess.isSamePackageMember(method.getDeclaringClass(), lookupClass())))
                return false;
            return true;
        }
        private MethodHandle restrictReceiver(MemberName method, DirectMethodHandle mh, Class<?> caller) throws IllegalAccessException {
            assert(!method.isStatic());
            // receiver type of mh is too wide; narrow to caller
            if (!method.getDeclaringClass().isAssignableFrom(caller)) {
                throw method.makeAccessException("caller class must be a subclass below the method", caller);
            }
            MethodType rawType = mh.type();
            if (rawType.parameterType(0) == caller)  return mh;
            MethodType narrowType = rawType.changeParameterType(0, caller);
            assert(!mh.isVarargsCollector());  // viewAsType will lose varargs-ness
            assert(mh.viewAsTypeChecks(narrowType, true));
            return mh.copyWith(narrowType, mh.form);
        }

        /** Check access and get the requested method. */
        private MethodHandle getDirectMethod(byte refKind, Class<?> refc, MemberName method, Class<?> callerClass) throws IllegalAccessException {
            final boolean doRestrict    = true;
            final boolean checkSecurity = true;
            return getDirectMethodCommon(refKind, refc, method, checkSecurity, doRestrict, callerClass);
        }
        /** Check access and get the requested method, eliding receiver narrowing rules. */
        private MethodHandle getDirectMethodNoRestrict(byte refKind, Class<?> refc, MemberName method, Class<?> callerClass) throws IllegalAccessException {
            final boolean doRestrict    = false;
            final boolean checkSecurity = true;
            return getDirectMethodCommon(refKind, refc, method, checkSecurity, doRestrict, callerClass);
        }
        /** Check access and get the requested method, eliding security manager checks. */
        private MethodHandle getDirectMethodNoSecurityManager(byte refKind, Class<?> refc, MemberName method, Class<?> callerClass) throws IllegalAccessException {
            final boolean doRestrict    = true;
            final boolean checkSecurity = false;  // not needed for reflection or for linking CONSTANT_MH constants
            return getDirectMethodCommon(refKind, refc, method, checkSecurity, doRestrict, callerClass);
        }
        /** Common code for all methods; do not call directly except from immediately above. */
        private MethodHandle getDirectMethodCommon(byte refKind, Class<?> refc, MemberName method,
                                                   boolean checkSecurity,
                                                   boolean doRestrict, Class<?> callerClass) throws IllegalAccessException {
            checkMethod(refKind, refc, method);
            // Optionally check with the security manager; this isn't needed for unreflect* calls.
            if (checkSecurity)
                checkSecurityManager(refc, method);
            assert(!method.isMethodHandleInvoke());

            if (refKind == REF_invokeSpecial &&
                refc != lookupClass() &&
                !refc.isInterface() &&
                refc != lookupClass().getSuperclass() &&
                refc.isAssignableFrom(lookupClass())) {
                assert(!method.getName().equals("<init>"));  // not this code path
                // Per JVMS 6.5, desc. of invokespecial instruction:
                // If the method is in a superclass of the LC,
                // and if our original search was above LC.super,
                // repeat the search (symbolic lookup) from LC.super
                // and continue with the direct superclass of that class,
                // and so forth, until a match is found or no further superclasses exist.
                // FIXME: MemberName.resolve should handle this instead.
                Class<?> refcAsSuper = lookupClass();
                MemberName m2;
                do {
                    refcAsSuper = refcAsSuper.getSuperclass();
                    m2 = new MemberName(refcAsSuper,
                                        method.getName(),
                                        method.getMethodType(),
                                        REF_invokeSpecial);
                    m2 = IMPL_NAMES.resolveOrNull(refKind, m2, lookupClassOrNull());
                } while (m2 == null &&         // no method is found yet
                         refc != refcAsSuper); // search up to refc
                if (m2 == null)  throw new InternalError(method.toString());
                method = m2;
                refc = refcAsSuper;
                // redo basic checks
                checkMethod(refKind, refc, method);
            }

            DirectMethodHandle dmh = DirectMethodHandle.make(refKind, refc, method);
            MethodHandle mh = dmh;
            // Optionally narrow the receiver argument to refc using restrictReceiver.
            if (doRestrict &&
                   (refKind == REF_invokeSpecial ||
                       (MethodHandleNatives.refKindHasReceiver(refKind) &&
                           restrictProtectedReceiver(method)))) {
                mh = restrictReceiver(method, dmh, lookupClass());
            }
            mh = maybeBindCaller(method, mh, callerClass);
            mh = mh.setVarargs(method);
            return mh;
        }
        private MethodHandle maybeBindCaller(MemberName method, MethodHandle mh,
                                             Class<?> callerClass)
                                             throws IllegalAccessException {
            if (allowedModes == TRUSTED || !MethodHandleNatives.isCallerSensitive(method))
                return mh;
            Class<?> hostClass = lookupClass;
            if (!hasPrivateAccess())  // caller must have private access
                hostClass = callerClass;  // callerClass came from a security manager style stack walk
            MethodHandle cbmh = MethodHandleImpl.bindCaller(mh, hostClass);
            // Note: caller will apply varargs after this step happens.
            return cbmh;
        }
        /** Check access and get the requested field. */
        private MethodHandle getDirectField(byte refKind, Class<?> refc, MemberName field) throws IllegalAccessException {
            final boolean checkSecurity = true;
            return getDirectFieldCommon(refKind, refc, field, checkSecurity);
        }
        /** Check access and get the requested field, eliding security manager checks. */
        private MethodHandle getDirectFieldNoSecurityManager(byte refKind, Class<?> refc, MemberName field) throws IllegalAccessException {
            final boolean checkSecurity = false;  // not needed for reflection or for linking CONSTANT_MH constants
            return getDirectFieldCommon(refKind, refc, field, checkSecurity);
        }
        /** Common code for all fields; do not call directly except from immediately above. */
        private MethodHandle getDirectFieldCommon(byte refKind, Class<?> refc, MemberName field,
                                                  boolean checkSecurity) throws IllegalAccessException {
            checkField(refKind, refc, field);
            // Optionally check with the security manager; this isn't needed for unreflect* calls.
            if (checkSecurity)
                checkSecurityManager(refc, field);
            DirectMethodHandle dmh = DirectMethodHandle.make(refc, field);
            boolean doRestrict = (MethodHandleNatives.refKindHasReceiver(refKind) &&
                                    restrictProtectedReceiver(field));
            if (doRestrict)
                return restrictReceiver(field, dmh, lookupClass());
            return dmh;
        }
        /** Check access and get the requested constructor. */
        private MethodHandle getDirectConstructor(Class<?> refc, MemberName ctor) throws IllegalAccessException {
            final boolean checkSecurity = true;
            return getDirectConstructorCommon(refc, ctor, checkSecurity);
        }
        /** Check access and get the requested constructor, eliding security manager checks. */
        private MethodHandle getDirectConstructorNoSecurityManager(Class<?> refc, MemberName ctor) throws IllegalAccessException {
            final boolean checkSecurity = false;  // not needed for reflection or for linking CONSTANT_MH constants
            return getDirectConstructorCommon(refc, ctor, checkSecurity);
        }
        /** Common code for all constructors; do not call directly except from immediately above. */
        private MethodHandle getDirectConstructorCommon(Class<?> refc, MemberName ctor,
                                                  boolean checkSecurity) throws IllegalAccessException {
            assert(ctor.isConstructor());
            checkAccess(REF_newInvokeSpecial, refc, ctor);
            // Optionally check with the security manager; this isn't needed for unreflect* calls.
            if (checkSecurity)
                checkSecurityManager(refc, ctor);
            assert(!MethodHandleNatives.isCallerSensitive(ctor));  // maybeBindCaller not relevant here
            return DirectMethodHandle.make(ctor).setVarargs(ctor);
        }

        /** Hook called from the JVM (via MethodHandleNatives) to link MH constants:
        /* <p>
         */
        /*non-public*/
        MethodHandle linkMethodHandleConstant(byte refKind, Class<?> defc, String name, Object type) throws ReflectiveOperationException {
            if (!(type instanceof Class || type instanceof MethodType))
                throw new InternalError("unresolved MemberName");
            MemberName member = new MemberName(refKind, defc, name, type);
            MethodHandle mh = LOOKASIDE_TABLE.get(member);
            if (mh != null) {
                checkSymbolicClass(defc);
                return mh;
            }
            // Treat MethodHandle.invoke and invokeExact specially.
            if (defc == MethodHandle.class && refKind == REF_invokeVirtual) {
                mh = findVirtualForMH(member.getName(), member.getMethodType());
                if (mh != null) {
                    return mh;
                }
            }
            MemberName resolved = resolveOrFail(refKind, member);
            mh = getDirectMethodForConstant(refKind, defc, resolved);
            if (mh instanceof DirectMethodHandle
                    && canBeCached(refKind, defc, resolved)) {
                MemberName key = mh.internalMemberName();
                if (key != null) {
                    key = key.asNormalOriginal();
                }
                if (member.equals(key)) {  // better safe than sorry
                    LOOKASIDE_TABLE.put(key, (DirectMethodHandle) mh);
                }
            }
            return mh;
        }
        private
        boolean canBeCached(byte refKind, Class<?> defc, MemberName member) {
            if (refKind == REF_invokeSpecial) {
                return false;
            }
            if (!Modifier.isPublic(defc.getModifiers()) ||
                    !Modifier.isPublic(member.getDeclaringClass().getModifiers()) ||
                    !member.isPublic() ||
                    member.isCallerSensitive()) {
                return false;
            }
            ClassLoader loader = defc.getClassLoader();
            if (!sun.misc.VM.isSystemDomainLoader(loader)) {
                ClassLoader sysl = ClassLoader.getSystemClassLoader();
                boolean found = false;
                while (sysl != null) {
                    if (loader == sysl) { found = true; break; }
                    sysl = sysl.getParent();
                }
                if (!found) {
                    return false;
                }
            }
            try {
                MemberName resolved2 = publicLookup().resolveOrFail(refKind,
                    new MemberName(refKind, defc, member.getName(), member.getType()));
                checkSecurityManager(defc, resolved2);
            } catch (ReflectiveOperationException | SecurityException ex) {
                return false;
            }
            return true;
        }
        private
        MethodHandle getDirectMethodForConstant(byte refKind, Class<?> defc, MemberName member)
                throws ReflectiveOperationException {
            if (MethodHandleNatives.refKindIsField(refKind)) {
                return getDirectFieldNoSecurityManager(refKind, defc, member);
            } else if (MethodHandleNatives.refKindIsMethod(refKind)) {
                return getDirectMethodNoSecurityManager(refKind, defc, member, lookupClass);
            } else if (refKind == REF_newInvokeSpecial) {
                return getDirectConstructorNoSecurityManager(defc, member);
            }
            // oops
            throw newIllegalArgumentException("bad MethodHandle constant #"+member);
        }

        static ConcurrentHashMap<MemberName, DirectMethodHandle> LOOKASIDE_TABLE = new ConcurrentHashMap<>();
    }

    /**
     * Produces a method handle giving read access to elements of an array.
     * The type of the method handle will have a return type of the array's
     * element type.  Its first argument will be the array type,
     * and the second will be {@code int}.
     * <p>
     *  生成一个方法句柄给数组的元素读取访问方法句柄的类型将具有数组的元素类型的返回类型它的第一个参数是数组类型,第二个参数是{@code int}
     * 
     * 
     * @param arrayClass an array type
     * @return a method handle which can load values from the given array type
     * @throws NullPointerException if the argument is null
     * @throws  IllegalArgumentException if arrayClass is not an array type
     */
    public static
    MethodHandle arrayElementGetter(Class<?> arrayClass) throws IllegalArgumentException {
        return MethodHandleImpl.makeArrayElementAccessor(arrayClass, false);
    }

    /**
     * Produces a method handle giving write access to elements of an array.
     * The type of the method handle will have a void return type.
     * Its last argument will be the array's element type.
     * The first and second arguments will be the array type and int.
     * <p>
     *  生成一个方法句柄给数组的元素写访问方法句柄的类型将有一个void返回类型它的最后一个参数将是数组的元素类型第一个和第二个参数将是数组类型和int
     * 
     * 
     * @param arrayClass the class of an array
     * @return a method handle which can store values into the array type
     * @throws NullPointerException if the argument is null
     * @throws IllegalArgumentException if arrayClass is not an array type
     */
    public static
    MethodHandle arrayElementSetter(Class<?> arrayClass) throws IllegalArgumentException {
        return MethodHandleImpl.makeArrayElementAccessor(arrayClass, true);
    }

    /// method handle invocation (reflective style)

    /**
     * Produces a method handle which will invoke any method handle of the
     * given {@code type}, with a given number of trailing arguments replaced by
     * a single trailing {@code Object[]} array.
     * The resulting invoker will be a method handle with the following
     * arguments:
     * <ul>
     * <li>a single {@code MethodHandle} target
     * <li>zero or more leading values (counted by {@code leadingArgCount})
     * <li>an {@code Object[]} array containing trailing arguments
     * </ul>
     * <p>
     * The invoker will invoke its target like a call to {@link MethodHandle#invoke invoke} with
     * the indicated {@code type}.
     * That is, if the target is exactly of the given {@code type}, it will behave
     * like {@code invokeExact}; otherwise it behave as if {@link MethodHandle#asType asType}
     * is used to convert the target to the required {@code type}.
     * <p>
     * The type of the returned invoker will not be the given {@code type}, but rather
     * will have all parameters except the first {@code leadingArgCount}
     * replaced by a single array of type {@code Object[]}, which will be
     * the final parameter.
     * <p>
     * Before invoking its target, the invoker will spread the final array, apply
     * reference casts as necessary, and unbox and widen primitive arguments.
     * If, when the invoker is called, the supplied array argument does
     * not have the correct number of elements, the invoker will throw
     * an {@link IllegalArgumentException} instead of invoking the target.
     * <p>
     * This method is equivalent to the following code (though it may be more efficient):
     * <blockquote><pre>{@code
MethodHandle invoker = MethodHandles.invoker(type);
int spreadArgCount = type.parameterCount() - leadingArgCount;
invoker = invoker.asSpreader(Object[].class, spreadArgCount);
return invoker;
     * }</pre></blockquote>
     * This method throws no reflective or security exceptions.
     * <p>
     * 生成一个方法句柄,它将调用给定{@code type}的任何方法句柄,给定数量的尾参数由一个单独的尾部{@code Object []}数组替换。生成的调用者将是一个方法句柄,参数：
     * <ul>
     *  <li>单个{@code MethodHandle}目标<li>零个或多个引导值(由{@code leadingArgCount}计数)<li>包含尾随参数的{@code Object []}数组
     * </ul>
     * <p>
     *  调用者将像调用{@link MethodHandle#invoke invoke}一样使用指定的{@code type}来调用它的目标。
     * 也就是说,如果目标完全是给定的{@code type},它的行为将类似于{@code invokeExact};否则它的行为就像{@link MethodHandle#asType asType}用于将
     * 目标转换为所需的{@code type}。
     *  调用者将像调用{@link MethodHandle#invoke invoke}一样使用指定的{@code type}来调用它的目标。
     * <p>
     * 返回的调用器的类型不是给定的{@code type},而是将所有参数(除了第一个{@code leadingArgCount})替换为{@code Object []}类型的单个数组,这将是最终参数
     * <p>
     *  在调用它的目标之前,调用器将传播最终数组,根据需要应用引用转换,以及unbox和widen原始参数。
     * 如果,当调用调用器时,提供的数组参数没有正确数量的元素,调用者将抛出一个{@link IllegalArgumentException}而不是调用目标。
     * <p>
     * 这个方法相当于下面的代码(虽然它可能更有效)：<blockquote> <pre> {@ code MethodHandle invoker = MethodHandlesinvoker(type); int spreadArgCount = typeparameterCount() -  leadingArgCount; invoker = invokerasSpreader(Object [] class,spreadArgCount); return invoker; }
     *  </pre> </blockquote>此方法不会引发任何反射或安全性异常。
     * 
     * 
     * @param type the desired target type
     * @param leadingArgCount number of fixed arguments, to be passed unchanged to the target
     * @return a method handle suitable for invoking any method handle of the given type
     * @throws NullPointerException if {@code type} is null
     * @throws IllegalArgumentException if {@code leadingArgCount} is not in
     *                  the range from 0 to {@code type.parameterCount()} inclusive,
     *                  or if the resulting method handle's type would have
     *          <a href="MethodHandle.html#maxarity">too many parameters</a>
     */
    static public
    MethodHandle spreadInvoker(MethodType type, int leadingArgCount) {
        if (leadingArgCount < 0 || leadingArgCount > type.parameterCount())
            throw newIllegalArgumentException("bad argument count", leadingArgCount);
        type = type.asSpreaderType(Object[].class, type.parameterCount() - leadingArgCount);
        return type.invokers().spreadInvoker(leadingArgCount);
    }

    /**
     * Produces a special <em>invoker method handle</em> which can be used to
     * invoke any method handle of the given type, as if by {@link MethodHandle#invokeExact invokeExact}.
     * The resulting invoker will have a type which is
     * exactly equal to the desired type, except that it will accept
     * an additional leading argument of type {@code MethodHandle}.
     * <p>
     * This method is equivalent to the following code (though it may be more efficient):
     * {@code publicLookup().findVirtual(MethodHandle.class, "invokeExact", type)}
     *
     * <p style="font-size:smaller;">
     * <em>Discussion:</em>
     * Invoker method handles can be useful when working with variable method handles
     * of unknown types.
     * For example, to emulate an {@code invokeExact} call to a variable method
     * handle {@code M}, extract its type {@code T},
     * look up the invoker method {@code X} for {@code T},
     * and call the invoker method, as {@code X.invoke(T, A...)}.
     * (It would not work to call {@code X.invokeExact}, since the type {@code T}
     * is unknown.)
     * If spreading, collecting, or other argument transformations are required,
     * they can be applied once to the invoker {@code X} and reused on many {@code M}
     * method handle values, as long as they are compatible with the type of {@code X}.
     * <p style="font-size:smaller;">
     * <em>(Note:  The invoker method is not available via the Core Reflection API.
     * An attempt to call {@linkplain java.lang.reflect.Method#invoke java.lang.reflect.Method.invoke}
     * on the declared {@code invokeExact} or {@code invoke} method will raise an
     * {@link java.lang.UnsupportedOperationException UnsupportedOperationException}.)</em>
     * <p>
     * This method throws no reflective or security exceptions.
     * <p>
     *  生成一个特殊的<em> invoker方法句柄</em>,可以用来调用给定类型的任何方法句柄,如同通过{@link MethodHandle#invokeExact invokeExact}生成的调用
     * 者将有一个类型,所需类型,除了它将接受类型为{@code MethodHandle}的附加引导参数,。
     * <p>
     * 这个方法相当于下面的代码(虽然它可能更高效)：{@code publicLookup()findVirtual(MethodHandleclass,"invokeExact",type)}
     * 
     * <p style="font-size:smaller;">
     * <em>讨论：</em>当使用未知类型的变量方法处理时,Invoker方法句柄非常有用例如,要模拟对变量方法句柄{@code M}的{@code invokeExact}调用, {@code T},为{@code T}
     * 查找调用者方法{@code X},并调用invoker方法作为{@code Xinvoke(T,A)}(这不会调用{@code因为类型{@code T}是未知的)如果需要扩展,收集或其他参数转换,它们可
     * 以应用一次到调用器{@code X},并重用在许多{@code M}方法句柄值,只要它们与{@code X}的类型兼容,。
     * <p style="font-size:smaller;">
     * <em>(注意：调用器方法不能通过Core Reflection API调用{@linkplain javalangreflectMethod#invoke javalangreflectMethodinvoke}
     * 在已声明的{@code invokeExact}或{@code invoke}方法中,将产生一个{@link javalangUnsupportedOperationException UnsupportedOperationException}
     * )</em>。
     * <p>
     *  此方法不会引发任何反射或安全性异常
     * 
     * 
     * @param type the desired target type
     * @return a method handle suitable for invoking any method handle of the given type
     * @throws IllegalArgumentException if the resulting method handle's type would have
     *          <a href="MethodHandle.html#maxarity">too many parameters</a>
     */
    static public
    MethodHandle exactInvoker(MethodType type) {
        return type.invokers().exactInvoker();
    }

    /**
     * Produces a special <em>invoker method handle</em> which can be used to
     * invoke any method handle compatible with the given type, as if by {@link MethodHandle#invoke invoke}.
     * The resulting invoker will have a type which is
     * exactly equal to the desired type, except that it will accept
     * an additional leading argument of type {@code MethodHandle}.
     * <p>
     * Before invoking its target, if the target differs from the expected type,
     * the invoker will apply reference casts as
     * necessary and box, unbox, or widen primitive values, as if by {@link MethodHandle#asType asType}.
     * Similarly, the return value will be converted as necessary.
     * If the target is a {@linkplain MethodHandle#asVarargsCollector variable arity method handle},
     * the required arity conversion will be made, again as if by {@link MethodHandle#asType asType}.
     * <p>
     * This method is equivalent to the following code (though it may be more efficient):
     * {@code publicLookup().findVirtual(MethodHandle.class, "invoke", type)}
     * <p style="font-size:smaller;">
     * <em>Discussion:</em>
     * A {@linkplain MethodType#genericMethodType general method type} is one which
     * mentions only {@code Object} arguments and return values.
     * An invoker for such a type is capable of calling any method handle
     * of the same arity as the general type.
     * <p style="font-size:smaller;">
     * <em>(Note:  The invoker method is not available via the Core Reflection API.
     * An attempt to call {@linkplain java.lang.reflect.Method#invoke java.lang.reflect.Method.invoke}
     * on the declared {@code invokeExact} or {@code invoke} method will raise an
     * {@link java.lang.UnsupportedOperationException UnsupportedOperationException}.)</em>
     * <p>
     * This method throws no reflective or security exceptions.
     * <p>
     *  生成一个特殊的<em> invoker方法句柄</em>,可以用于调用与给定类型兼容的任何方法句柄,如同通过{@link MethodHandle#invoke invoke}生成的调用者将具有完全相
     * 等的类型到所需类型,除了它将接受类型为{@code MethodHandle}的附加引导参数,。
     * <p>
     * 在调用它的目标之前,如果目标与期望的类型不同,调用者将根据需要应用引用转换,以及box,unbox或者widen原始值,就像通过{@link MethodHandle#asType asType}。
     * 同样,返回值将是根据需要进行转换如果目标是{@linkplain MethodHandle#asVarargsCollector变量arity方法句柄},则将进行所需的arity转换,再次如同通过{@link MethodHandle#asType asType}
     * 。
     * 在调用它的目标之前,如果目标与期望的类型不同,调用者将根据需要应用引用转换,以及box,unbox或者widen原始值,就像通过{@link MethodHandle#asType asType}。
     * <p>
     *  这个方法相当于下面的代码(虽然它可能更有效率)：{@code publicLookup()findVirtual(MethodHandleclass,"invoke",type)}
     * <p style="font-size:smaller;">
     * <@>讨论：</em> {@linkplain MethodType#genericMethodType一般方法类型}仅提及{@code Object}参数和返回值此类型的调用器能够调用同一个方法的任何
     * 方法句柄arity作为一般类型。
     * <p style="font-size:smaller;">
     *  <em>(注意：调用器方法不能通过Core Reflection API调用{@linkplain javalangreflectMethod#invoke javalangreflectMethodinvoke}
     * 在已声明的{@code invokeExact}或{@code invoke}方法中,将产生一个{@link javalangUnsupportedOperationException UnsupportedOperationException}
     * )</em>。
     * <p>
     *  此方法不会引发任何反射或安全性异常
     * 
     * 
     * @param type the desired target type
     * @return a method handle suitable for invoking any method handle convertible to the given type
     * @throws IllegalArgumentException if the resulting method handle's type would have
     *          <a href="MethodHandle.html#maxarity">too many parameters</a>
     */
    static public
    MethodHandle invoker(MethodType type) {
        return type.invokers().genericInvoker();
    }

    static /*non-public*/
    MethodHandle basicInvoker(MethodType type) {
        return type.invokers().basicInvoker();
    }

     /// method handle modification (creation from other method handles)

    /**
     * Produces a method handle which adapts the type of the
     * given method handle to a new type by pairwise argument and return type conversion.
     * The original type and new type must have the same number of arguments.
     * The resulting method handle is guaranteed to report a type
     * which is equal to the desired new type.
     * <p>
     * If the original type and new type are equal, returns target.
     * <p>
     * The same conversions are allowed as for {@link MethodHandle#asType MethodHandle.asType},
     * and some additional conversions are also applied if those conversions fail.
     * Given types <em>T0</em>, <em>T1</em>, one of the following conversions is applied
     * if possible, before or instead of any conversions done by {@code asType}:
     * <ul>
     * <li>If <em>T0</em> and <em>T1</em> are references, and <em>T1</em> is an interface type,
     *     then the value of type <em>T0</em> is passed as a <em>T1</em> without a cast.
     *     (This treatment of interfaces follows the usage of the bytecode verifier.)
     * <li>If <em>T0</em> is boolean and <em>T1</em> is another primitive,
     *     the boolean is converted to a byte value, 1 for true, 0 for false.
     *     (This treatment follows the usage of the bytecode verifier.)
     * <li>If <em>T1</em> is boolean and <em>T0</em> is another primitive,
     *     <em>T0</em> is converted to byte via Java casting conversion (JLS 5.5),
     *     and the low order bit of the result is tested, as if by {@code (x & 1) != 0}.
     * <li>If <em>T0</em> and <em>T1</em> are primitives other than boolean,
     *     then a Java casting conversion (JLS 5.5) is applied.
     *     (Specifically, <em>T0</em> will convert to <em>T1</em> by
     *     widening and/or narrowing.)
     * <li>If <em>T0</em> is a reference and <em>T1</em> a primitive, an unboxing
     *     conversion will be applied at runtime, possibly followed
     *     by a Java casting conversion (JLS 5.5) on the primitive value,
     *     possibly followed by a conversion from byte to boolean by testing
     *     the low-order bit.
     * <li>If <em>T0</em> is a reference and <em>T1</em> a primitive,
     *     and if the reference is null at runtime, a zero value is introduced.
     * </ul>
     * <p>
     * 生成一个方法句柄,通过成对参数和返回类型转换将给定方法句柄的类型适配为一个新类型原始类型和新类型必须具有相同的参数数量所产生的方法句柄被保证报告一个类型到所需的新类型
     * <p>
     *  如果原始类型和新类型相等,则返回目标
     * <p>
     *  允许与{@link MethodHandle#asType MethodHandleasType}相同的转换,如果这些转换失败,也会应用一些其他转换。
     * 给定类型<em> T0 </em>,<em> T1 </em>如果可能,在{@code asType}执行的任何转换之前或之后应用以下转化：。
     * <ul>
     * <li>如果<em> T0 </em>和<em> T1 </em>是引用,并且<em> T1 </em>是接口类型,则<em> T0 </em >作为<em> T1 </em>传递而没有转换(这种处理接
     * 口遵循字节码验证器的使用)<li>如果<em> T0 </em>是布尔值,<em> em>是另一个原语,布尔值被转换为字节值,1表示true,0表示false(此处理遵循字节码验证程序的用法)<li>如
     * 果<em> T1 </em> > T0 </em>是另一个原语,通过Java转换转换(JLS 55)将T0 </em>转换为字节,并且测试结果的低阶位,如同通过{@code &1)！= 0} <li>如
     * 果<em> T0 </em>和<em> T1 </em>是布尔值以外的原语,则应用Java Cast转换(JLS 55)(具体来说,<em> </em>会通过加宽和/或变窄来转换为<em> T1 </em>
     * )<li>如果<em> T0 </em> / em>一个原语,将在运行时应用拆箱转换,可能后面是对原语值的Java转换转换(JLS 55),之后可以通过测试低阶位<li>将字节转换为布尔值,如果<em>
     *  T0 </em>是引用并且<em> T1 </em>是原语,并且如果引用在运行时为空,则引入零值。
     * </ul>
     * 
     * @param target the method handle to invoke after arguments are retyped
     * @param newType the expected type of the new method handle
     * @return a method handle which delegates to the target after performing
     *           any necessary argument conversions, and arranges for any
     *           necessary return value conversions
     * @throws NullPointerException if either argument is null
     * @throws WrongMethodTypeException if the conversion cannot be made
     * @see MethodHandle#asType
     */
    public static
    MethodHandle explicitCastArguments(MethodHandle target, MethodType newType) {
        explicitCastArgumentsChecks(target, newType);
        // use the asTypeCache when possible:
        MethodType oldType = target.type();
        if (oldType == newType)  return target;
        if (oldType.explicitCastEquivalentToAsType(newType)) {
            return target.asFixedArity().asType(newType);
        }
        return MethodHandleImpl.makePairwiseConvert(target, newType, false);
    }

    private static void explicitCastArgumentsChecks(MethodHandle target, MethodType newType) {
        if (target.type().parameterCount() != newType.parameterCount()) {
            throw new WrongMethodTypeException("cannot explicitly cast " + target + " to " + newType);
        }
    }

    /**
     * Produces a method handle which adapts the calling sequence of the
     * given method handle to a new type, by reordering the arguments.
     * The resulting method handle is guaranteed to report a type
     * which is equal to the desired new type.
     * <p>
     * The given array controls the reordering.
     * Call {@code #I} the number of incoming parameters (the value
     * {@code newType.parameterCount()}, and call {@code #O} the number
     * of outgoing parameters (the value {@code target.type().parameterCount()}).
     * Then the length of the reordering array must be {@code #O},
     * and each element must be a non-negative number less than {@code #I}.
     * For every {@code N} less than {@code #O}, the {@code N}-th
     * outgoing argument will be taken from the {@code I}-th incoming
     * argument, where {@code I} is {@code reorder[N]}.
     * <p>
     * No argument or return value conversions are applied.
     * The type of each incoming argument, as determined by {@code newType},
     * must be identical to the type of the corresponding outgoing parameter
     * or parameters in the target method handle.
     * The return type of {@code newType} must be identical to the return
     * type of the original target.
     * <p>
     * The reordering array need not specify an actual permutation.
     * An incoming argument will be duplicated if its index appears
     * more than once in the array, and an incoming argument will be dropped
     * if its index does not appear in the array.
     * As in the case of {@link #dropArguments(MethodHandle,int,List) dropArguments},
     * incoming arguments which are not mentioned in the reordering array
     * are may be any type, as determined only by {@code newType}.
     * <blockquote><pre>{@code
import static java.lang.invoke.MethodHandles.*;
import static java.lang.invoke.MethodType.*;
...
MethodType intfn1 = methodType(int.class, int.class);
MethodType intfn2 = methodType(int.class, int.class, int.class);
MethodHandle sub = ... (int x, int y) -> (x-y) ...;
assert(sub.type().equals(intfn2));
MethodHandle sub1 = permuteArguments(sub, intfn2, 0, 1);
MethodHandle rsub = permuteArguments(sub, intfn2, 1, 0);
assert((int)rsub.invokeExact(1, 100) == 99);
MethodHandle add = ... (int x, int y) -> (x+y) ...;
assert(add.type().equals(intfn2));
MethodHandle twice = permuteArguments(add, intfn1, 0, 0);
assert(twice.type().equals(intfn1));
assert((int)twice.invokeExact(21) == 42);
     * }</pre></blockquote>
     * <p>
     * 生成一个方法句柄,通过重新排序参数,将给定方法句柄的调用序列适配为一个新类型。所得到的方法句柄被保证报告一个类型,它等于所需的新类型
     * <p>
     *  给定的数组控制重新排序调用{@code #I}传入参数的数量(值{@code newTypeparameterCount()},并调用{@code #O}输出参数的数量(值{@code targettype )parameterCount()}
     * )然后,重排序数组的长度必须是{@code #O},每个元素必须是小于{@code #I}的非负数。
     * 对于每个{@code N}小于{ @code #O},{@code N}输出参数将取自{@code I}输入参数,其中{@code I}是{@code reorder [N]}。
     * <p>
     * 不应用参数或返回值转换。由{@code newType}确定的每个传入参数的类型必须与目标方法句柄中相应的一个或多个输出参数的类型相同。{@code newType }必须与原始目标的返回类型相同
     * <p>
     * 重排序数组不需要指定实际的排列。如果传入的参数的索引在数组中出现多次,则传入的参数将被复制,如果传入的参数的索引没有出现在数组中,则传入的参数将被丢弃。
     * 与{@link #dropArguments(MethodHandle,int,List)dropArguments},在重排序数组中没有提到的传入参数可以是任何类型,只由{@code newType}
     *  <blockquote> <pre> {@ code import static javalanginvokeMethodHandles * ; import static javalanginvokeMethodType *; MethodType intfn1 = methodType(intclass,intclass); MethodType intfn2 = methodType(intclass,intclass,intclass); MethodHandle sub =(int x,int y) - >(x-y); assert(subtype()equals(intfn2)); MethodHandle sub1 = permuteArguments(sub,intfn2,0,1); MethodHandle rsub = permuteArguments(sub,intfn2,1,0); assert((int)rsubinvokeExact(1,100)== 99); MethodHandle add =(int x,int y) - >(x + y); assert(addtype()equals(intfn2)); MethodHandle twice = permuteArguments(add,intfn1,0,0); assert(twicetype()equals(intfn1)); assert((int)twiceinvokeExact(21)== 42); }
     *  </pre> </blockquote>。
     * 重排序数组不需要指定实际的排列。如果传入的参数的索引在数组中出现多次,则传入的参数将被复制,如果传入的参数的索引没有出现在数组中,则传入的参数将被丢弃。
     * 
     * 
     * @param target the method handle to invoke after arguments are reordered
     * @param newType the expected type of the new method handle
     * @param reorder an index array which controls the reordering
     * @return a method handle which delegates to the target after it
     *           drops unused arguments and moves and/or duplicates the other arguments
     * @throws NullPointerException if any argument is null
     * @throws IllegalArgumentException if the index array length is not equal to
     *                  the arity of the target, or if any index array element
     *                  not a valid index for a parameter of {@code newType},
     *                  or if two corresponding parameter types in
     *                  {@code target.type()} and {@code newType} are not identical,
     */
    public static
    MethodHandle permuteArguments(MethodHandle target, MethodType newType, int... reorder) {
        reorder = reorder.clone();  // get a private copy
        MethodType oldType = target.type();
        permuteArgumentChecks(reorder, newType, oldType);
        // first detect dropped arguments and handle them separately
        int[] originalReorder = reorder;
        BoundMethodHandle result = target.rebind();
        LambdaForm form = result.form;
        int newArity = newType.parameterCount();
        // Normalize the reordering into a real permutation,
        // by removing duplicates and adding dropped elements.
        // This somewhat improves lambda form caching, as well
        // as simplifying the transform by breaking it up into steps.
        for (int ddIdx; (ddIdx = findFirstDupOrDrop(reorder, newArity)) != 0; ) {
            if (ddIdx > 0) {
                // We found a duplicated entry at reorder[ddIdx].
                // Example:  (x,y,z)->asList(x,y,z)
                // permuted by [1*,0,1] => (a0,a1)=>asList(a1,a0,a1)
                // permuted by [0,1,0*] => (a0,a1)=>asList(a0,a1,a0)
                // The starred element corresponds to the argument
                // deleted by the dupArgumentForm transform.
                int srcPos = ddIdx, dstPos = srcPos, dupVal = reorder[srcPos];
                boolean killFirst = false;
                for (int val; (val = reorder[--dstPos]) != dupVal; ) {
                    // Set killFirst if the dup is larger than an intervening position.
                    // This will remove at least one inversion from the permutation.
                    if (dupVal > val) killFirst = true;
                }
                if (!killFirst) {
                    srcPos = dstPos;
                    dstPos = ddIdx;
                }
                form = form.editor().dupArgumentForm(1 + srcPos, 1 + dstPos);
                assert (reorder[srcPos] == reorder[dstPos]);
                oldType = oldType.dropParameterTypes(dstPos, dstPos + 1);
                // contract the reordering by removing the element at dstPos
                int tailPos = dstPos + 1;
                System.arraycopy(reorder, tailPos, reorder, dstPos, reorder.length - tailPos);
                reorder = Arrays.copyOf(reorder, reorder.length - 1);
            } else {
                int dropVal = ~ddIdx, insPos = 0;
                while (insPos < reorder.length && reorder[insPos] < dropVal) {
                    // Find first element of reorder larger than dropVal.
                    // This is where we will insert the dropVal.
                    insPos += 1;
                }
                Class<?> ptype = newType.parameterType(dropVal);
                form = form.editor().addArgumentForm(1 + insPos, BasicType.basicType(ptype));
                oldType = oldType.insertParameterTypes(insPos, ptype);
                // expand the reordering by inserting an element at insPos
                int tailPos = insPos + 1;
                reorder = Arrays.copyOf(reorder, reorder.length + 1);
                System.arraycopy(reorder, insPos, reorder, tailPos, reorder.length - tailPos);
                reorder[insPos] = dropVal;
            }
            assert (permuteArgumentChecks(reorder, newType, oldType));
        }
        assert (reorder.length == newArity);  // a perfect permutation
        // Note:  This may cache too many distinct LFs. Consider backing off to varargs code.
        form = form.editor().permuteArgumentsForm(1, reorder);
        if (newType == result.type() && form == result.internalForm())
            return result;
        return result.copyWith(newType, form);
    }

    /**
     * Return an indication of any duplicate or omission in reorder.
     * If the reorder contains a duplicate entry, return the index of the second occurrence.
     * Otherwise, return ~(n), for the first n in [0..newArity-1] that is not present in reorder.
     * Otherwise, return zero.
     * If an element not in [0..newArity-1] is encountered, return reorder.length.
     * <p>
     * 返回重新排序中的任何重复或省略的指示如果重新排序包含重复条目,则返回第二次出现的索引。
     * 否则,返回〜(n),对于在重新排序中不存在的[0newArity-1] ,return zero如果遇到不在[0newArity-1]中的元素,则返回reorderlength。
     * 
     */
    private static int findFirstDupOrDrop(int[] reorder, int newArity) {
        final int BIT_LIMIT = 63;  // max number of bits in bit mask
        if (newArity < BIT_LIMIT) {
            long mask = 0;
            for (int i = 0; i < reorder.length; i++) {
                int arg = reorder[i];
                if (arg >= newArity) {
                    return reorder.length;
                }
                long bit = 1L << arg;
                if ((mask & bit) != 0) {
                    return i;  // >0 indicates a dup
                }
                mask |= bit;
            }
            if (mask == (1L << newArity) - 1) {
                assert(Long.numberOfTrailingZeros(Long.lowestOneBit(~mask)) == newArity);
                return 0;
            }
            // find first zero
            long zeroBit = Long.lowestOneBit(~mask);
            int zeroPos = Long.numberOfTrailingZeros(zeroBit);
            assert(zeroPos <= newArity);
            if (zeroPos == newArity) {
                return 0;
            }
            return ~zeroPos;
        } else {
            // same algorithm, different bit set
            BitSet mask = new BitSet(newArity);
            for (int i = 0; i < reorder.length; i++) {
                int arg = reorder[i];
                if (arg >= newArity) {
                    return reorder.length;
                }
                if (mask.get(arg)) {
                    return i;  // >0 indicates a dup
                }
                mask.set(arg);
            }
            int zeroPos = mask.nextClearBit(0);
            assert(zeroPos <= newArity);
            if (zeroPos == newArity) {
                return 0;
            }
            return ~zeroPos;
        }
    }

    private static boolean permuteArgumentChecks(int[] reorder, MethodType newType, MethodType oldType) {
        if (newType.returnType() != oldType.returnType())
            throw newIllegalArgumentException("return types do not match",
                    oldType, newType);
        if (reorder.length == oldType.parameterCount()) {
            int limit = newType.parameterCount();
            boolean bad = false;
            for (int j = 0; j < reorder.length; j++) {
                int i = reorder[j];
                if (i < 0 || i >= limit) {
                    bad = true; break;
                }
                Class<?> src = newType.parameterType(i);
                Class<?> dst = oldType.parameterType(j);
                if (src != dst)
                    throw newIllegalArgumentException("parameter types do not match after reorder",
                            oldType, newType);
            }
            if (!bad)  return true;
        }
        throw newIllegalArgumentException("bad reorder array: "+Arrays.toString(reorder));
    }

    /**
     * Produces a method handle of the requested return type which returns the given
     * constant value every time it is invoked.
     * <p>
     * Before the method handle is returned, the passed-in value is converted to the requested type.
     * If the requested type is primitive, widening primitive conversions are attempted,
     * else reference conversions are attempted.
     * <p>The returned method handle is equivalent to {@code identity(type).bindTo(value)}.
     * <p>
     *  生成所请求的返回类型的方法句柄,每次调用时返回给定的常量值
     * <p>
     *  在返回方法句柄之前,将传入的值转换为请求的类型。如果请求的类型是原语,则尝试扩展原语转换,否则尝试引用转换。
     * 返回的方法句柄等同于{@code identity (type)bindTo(value)}。
     * 
     * 
     * @param type the return type of the desired method handle
     * @param value the value to return
     * @return a method handle of the given return type and no arguments, which always returns the given value
     * @throws NullPointerException if the {@code type} argument is null
     * @throws ClassCastException if the value cannot be converted to the required return type
     * @throws IllegalArgumentException if the given type is {@code void.class}
     */
    public static
    MethodHandle constant(Class<?> type, Object value) {
        if (type.isPrimitive()) {
            if (type == void.class)
                throw newIllegalArgumentException("void type");
            Wrapper w = Wrapper.forPrimitiveType(type);
            value = w.convert(value, type);
            if (w.zero().equals(value))
                return zero(w, type);
            return insertArguments(identity(type), 0, value);
        } else {
            if (value == null)
                return zero(Wrapper.OBJECT, type);
            return identity(type).bindTo(value);
        }
    }

    /**
     * Produces a method handle which returns its sole argument when invoked.
     * <p>
     * 生成一个方法句柄,当被调用时返回其唯一的参数
     * 
     * 
     * @param type the type of the sole parameter and return value of the desired method handle
     * @return a unary method handle which accepts and returns the given type
     * @throws NullPointerException if the argument is null
     * @throws IllegalArgumentException if the given type is {@code void.class}
     */
    public static
    MethodHandle identity(Class<?> type) {
        Wrapper btw = (type.isPrimitive() ? Wrapper.forPrimitiveType(type) : Wrapper.OBJECT);
        int pos = btw.ordinal();
        MethodHandle ident = IDENTITY_MHS[pos];
        if (ident == null) {
            ident = setCachedMethodHandle(IDENTITY_MHS, pos, makeIdentity(btw.primitiveType()));
        }
        if (ident.type().returnType() == type)
            return ident;
        // something like identity(Foo.class); do not bother to intern these
        assert(btw == Wrapper.OBJECT);
        return makeIdentity(type);
    }
    private static final MethodHandle[] IDENTITY_MHS = new MethodHandle[Wrapper.values().length];
    private static MethodHandle makeIdentity(Class<?> ptype) {
        MethodType mtype = MethodType.methodType(ptype, ptype);
        LambdaForm lform = LambdaForm.identityForm(BasicType.basicType(ptype));
        return MethodHandleImpl.makeIntrinsic(mtype, lform, Intrinsic.IDENTITY);
    }

    private static MethodHandle zero(Wrapper btw, Class<?> rtype) {
        int pos = btw.ordinal();
        MethodHandle zero = ZERO_MHS[pos];
        if (zero == null) {
            zero = setCachedMethodHandle(ZERO_MHS, pos, makeZero(btw.primitiveType()));
        }
        if (zero.type().returnType() == rtype)
            return zero;
        assert(btw == Wrapper.OBJECT);
        return makeZero(rtype);
    }
    private static final MethodHandle[] ZERO_MHS = new MethodHandle[Wrapper.values().length];
    private static MethodHandle makeZero(Class<?> rtype) {
        MethodType mtype = MethodType.methodType(rtype);
        LambdaForm lform = LambdaForm.zeroForm(BasicType.basicType(rtype));
        return MethodHandleImpl.makeIntrinsic(mtype, lform, Intrinsic.ZERO);
    }

    synchronized private static MethodHandle setCachedMethodHandle(MethodHandle[] cache, int pos, MethodHandle value) {
        // Simulate a CAS, to avoid racy duplication of results.
        MethodHandle prev = cache[pos];
        if (prev != null) return prev;
        return cache[pos] = value;
    }

    /**
     * Provides a target method handle with one or more <em>bound arguments</em>
     * in advance of the method handle's invocation.
     * The formal parameters to the target corresponding to the bound
     * arguments are called <em>bound parameters</em>.
     * Returns a new method handle which saves away the bound arguments.
     * When it is invoked, it receives arguments for any non-bound parameters,
     * binds the saved arguments to their corresponding parameters,
     * and calls the original target.
     * <p>
     * The type of the new method handle will drop the types for the bound
     * parameters from the original target type, since the new method handle
     * will no longer require those arguments to be supplied by its callers.
     * <p>
     * Each given argument object must match the corresponding bound parameter type.
     * If a bound parameter type is a primitive, the argument object
     * must be a wrapper, and will be unboxed to produce the primitive value.
     * <p>
     * The {@code pos} argument selects which parameters are to be bound.
     * It may range between zero and <i>N-L</i> (inclusively),
     * where <i>N</i> is the arity of the target method handle
     * and <i>L</i> is the length of the values array.
     * <p>
     *  在方法句柄的调用之前提供具有一个或多个<em>绑定参数的目标方法句柄</em>与绑定参数相对应的目标的形式参数称为<em>绑定参数</em>方法句柄,它保存绑定的参数当它被调用时,它接收任何未绑定参数
     * 的参数,将保存的参数绑定到它们相应的参数,并调用原始目标。
     * <p>
     *  新方法句柄的类型将从原始目标类型删除绑定参数的类型,因为新方法句柄不再需要由其调用者提供这些参数
     * <p>
     * 每个给定的参数对象必须与相应的绑定参数类型匹配如果绑定的参数类型是基本类型,则参数对象必须是包装器,并且将被取消装箱以生成原始值
     * <p>
     *  {@code pos}参数选择要绑定哪些参数它可以在零和N i(包含)之间变化,其中N i是目标方法句柄的逻辑,以及<i> L </i>是values数组的长度
     * 
     * 
     * @param target the method handle to invoke after the argument is inserted
     * @param pos where to insert the argument (zero for the first)
     * @param values the series of arguments to insert
     * @return a method handle which inserts an additional argument,
     *         before calling the original method handle
     * @throws NullPointerException if the target or the {@code values} array is null
     * @see MethodHandle#bindTo
     */
    public static
    MethodHandle insertArguments(MethodHandle target, int pos, Object... values) {
        int insCount = values.length;
        Class<?>[] ptypes = insertArgumentsChecks(target, insCount, pos);
        if (insCount == 0)  return target;
        BoundMethodHandle result = target.rebind();
        for (int i = 0; i < insCount; i++) {
            Object value = values[i];
            Class<?> ptype = ptypes[pos+i];
            if (ptype.isPrimitive()) {
                result = insertArgumentPrimitive(result, pos, ptype, value);
            } else {
                value = ptype.cast(value);  // throw CCE if needed
                result = result.bindArgumentL(pos, value);
            }
        }
        return result;
    }

    private static BoundMethodHandle insertArgumentPrimitive(BoundMethodHandle result, int pos,
                                                             Class<?> ptype, Object value) {
        Wrapper w = Wrapper.forPrimitiveType(ptype);
        // perform unboxing and/or primitive conversion
        value = w.convert(value, ptype);
        switch (w) {
        case INT:     return result.bindArgumentI(pos, (int)value);
        case LONG:    return result.bindArgumentJ(pos, (long)value);
        case FLOAT:   return result.bindArgumentF(pos, (float)value);
        case DOUBLE:  return result.bindArgumentD(pos, (double)value);
        default:      return result.bindArgumentI(pos, ValueConversions.widenSubword(value));
        }
    }

    private static Class<?>[] insertArgumentsChecks(MethodHandle target, int insCount, int pos) throws RuntimeException {
        MethodType oldType = target.type();
        int outargs = oldType.parameterCount();
        int inargs  = outargs - insCount;
        if (inargs < 0)
            throw newIllegalArgumentException("too many values to insert");
        if (pos < 0 || pos > inargs)
            throw newIllegalArgumentException("no argument type to append");
        return oldType.ptypes();
    }

    /**
     * Produces a method handle which will discard some dummy arguments
     * before calling some other specified <i>target</i> method handle.
     * The type of the new method handle will be the same as the target's type,
     * except it will also include the dummy argument types,
     * at some given position.
     * <p>
     * The {@code pos} argument may range between zero and <i>N</i>,
     * where <i>N</i> is the arity of the target.
     * If {@code pos} is zero, the dummy arguments will precede
     * the target's real arguments; if {@code pos} is <i>N</i>
     * they will come after.
     * <p>
     * <b>Example:</b>
     * <blockquote><pre>{@code
import static java.lang.invoke.MethodHandles.*;
import static java.lang.invoke.MethodType.*;
...
MethodHandle cat = lookup().findVirtual(String.class,
  "concat", methodType(String.class, String.class));
assertEquals("xy", (String) cat.invokeExact("x", "y"));
MethodType bigType = cat.type().insertParameterTypes(0, int.class, String.class);
MethodHandle d0 = dropArguments(cat, 0, bigType.parameterList().subList(0,2));
assertEquals(bigType, d0.type());
assertEquals("yz", (String) d0.invokeExact(123, "x", "y", "z"));
     * }</pre></blockquote>
     * <p>
     * This method is also equivalent to the following code:
     * <blockquote><pre>
     * {@link #dropArguments(MethodHandle,int,Class...) dropArguments}{@code (target, pos, valueTypes.toArray(new Class[0]))}
     * </pre></blockquote>
     * <p>
     *  生成一个方法句柄,它将在调用某个其他指定的<i> target </i>方法句柄之前丢弃一些虚拟参数。新的方法句柄的类型将与目标的类型相同,除了它还将包括虚拟参数类型,在某一给定位置
     * <p>
     * {@code pos}参数可以在零和N之间,N </i>是目标的逻辑。如果{@code pos}是零,虚拟参数将在目标的真正理由;如果{@code pos}是<i> N </i>,他们会来
     * <p>
     * <b>示例：</b> <blockquote> <pre> {@ code import static javalanginvokeMethodHandles *; import static javalanginvokeMethodType *; MethodHandle cat = lookup()findVirtual(Stringclass,"concat",methodType(Stringclass,Stringclass)); assertEquals("xy",(String)catinvokeExact("x","y")); MethodType bigType = cattype()insertParameterTypes(0,intclass,Stringclass); MethodHandle d0 = dropArguments(cat,0,bigTypeparameterList()subList(0,2)); assertEquals(bigType,d0type()); assertEquals("yz",(String)d0invokeExact(123,"x","y","z")); }
     *  </pre> </blockquote>。
     * <p>
     *  这个方法也相当于下面的代码：<blockquote> <pre> {@link #dropArguments(MethodHandle,int,Class)dropArguments} {@ code(target,pos,valueTypestoArray(new Class [0])) pre> </blockquote>。
     * 
     * 
     * @param target the method handle to invoke after the arguments are dropped
     * @param valueTypes the type(s) of the argument(s) to drop
     * @param pos position of first argument to drop (zero for the leftmost)
     * @return a method handle which drops arguments of the given types,
     *         before calling the original method handle
     * @throws NullPointerException if the target is null,
     *                              or if the {@code valueTypes} list or any of its elements is null
     * @throws IllegalArgumentException if any element of {@code valueTypes} is {@code void.class},
     *                  or if {@code pos} is negative or greater than the arity of the target,
     *                  or if the new method handle's type would have too many parameters
     */
    public static
    MethodHandle dropArguments(MethodHandle target, int pos, List<Class<?>> valueTypes) {
        MethodType oldType = target.type();  // get NPE
        int dropped = dropArgumentChecks(oldType, pos, valueTypes);
        MethodType newType = oldType.insertParameterTypes(pos, valueTypes);
        if (dropped == 0)  return target;
        BoundMethodHandle result = target.rebind();
        LambdaForm lform = result.form;
        int insertFormArg = 1 + pos;
        for (Class<?> ptype : valueTypes) {
            lform = lform.editor().addArgumentForm(insertFormArg++, BasicType.basicType(ptype));
        }
        result = result.copyWith(newType, lform);
        return result;
    }

    private static int dropArgumentChecks(MethodType oldType, int pos, List<Class<?>> valueTypes) {
        int dropped = valueTypes.size();
        MethodType.checkSlotCount(dropped);
        int outargs = oldType.parameterCount();
        int inargs  = outargs + dropped;
        if (pos < 0 || pos > outargs)
            throw newIllegalArgumentException("no argument type to remove"
                    + Arrays.asList(oldType, pos, valueTypes, inargs, outargs)
                    );
        return dropped;
    }

    /**
     * Produces a method handle which will discard some dummy arguments
     * before calling some other specified <i>target</i> method handle.
     * The type of the new method handle will be the same as the target's type,
     * except it will also include the dummy argument types,
     * at some given position.
     * <p>
     * The {@code pos} argument may range between zero and <i>N</i>,
     * where <i>N</i> is the arity of the target.
     * If {@code pos} is zero, the dummy arguments will precede
     * the target's real arguments; if {@code pos} is <i>N</i>
     * they will come after.
     * <p>
     * <b>Example:</b>
     * <blockquote><pre>{@code
import static java.lang.invoke.MethodHandles.*;
import static java.lang.invoke.MethodType.*;
...
MethodHandle cat = lookup().findVirtual(String.class,
  "concat", methodType(String.class, String.class));
assertEquals("xy", (String) cat.invokeExact("x", "y"));
MethodHandle d0 = dropArguments(cat, 0, String.class);
assertEquals("yz", (String) d0.invokeExact("x", "y", "z"));
MethodHandle d1 = dropArguments(cat, 1, String.class);
assertEquals("xz", (String) d1.invokeExact("x", "y", "z"));
MethodHandle d2 = dropArguments(cat, 2, String.class);
assertEquals("xy", (String) d2.invokeExact("x", "y", "z"));
MethodHandle d12 = dropArguments(cat, 1, int.class, boolean.class);
assertEquals("xz", (String) d12.invokeExact("x", 12, true, "z"));
     * }</pre></blockquote>
     * <p>
     * This method is also equivalent to the following code:
     * <blockquote><pre>
     * {@link #dropArguments(MethodHandle,int,List) dropArguments}{@code (target, pos, Arrays.asList(valueTypes))}
     * </pre></blockquote>
     * <p>
     * 生成一个方法句柄,它将在调用某个其他指定的<i> target </i>方法句柄之前丢弃一些虚拟参数。新的方法句柄的类型将与目标的类型相同,除了它还将包括虚拟参数类型,在某一给定位置
     * <p>
     *  {@code pos}参数可以在零和N之间,N </i>是目标的逻辑。如果{@code pos}是零,虚拟参数将在目标的真正理由;如果{@code pos}是<i> N </i>,他们会来
     * <p>
     * <b>示例：</b> <blockquote> <pre> {@ code import static javalanginvokeMethodHandles *; import static javalanginvokeMethodType *; MethodHandle cat = lookup()findVirtual(Stringclass,"concat",methodType(Stringclass,Stringclass)); assertEquals("xy",(String)catinvokeExact("x","y")); MethodHandle d0 = dropArguments(cat,0,Stringclass); assertEquals("yz",(String)d0invokeExact("x","y","z")); MethodHandle d1 = dropArguments(cat,1,Stringclass); assertEquals("xz",(String)d1invokeExact("x","y","z")); MethodHandle d2 = dropArguments(cat,2,Stringclass); assertEquals("xy",(String)d2invokeExact("x","y","z")); MethodHandle d12 = dropArguments(cat,1,intclass,booleanclass); assertEquals("xz",(String)d12invokeExact("x",12,true,"z")); }
     *  </pre> </blockquote>。
     * <p>
     * 这个方法也等同于下面的代码：<blockquote> <pre> {@link #dropArguments(MethodHandle,int,List)dropArguments} {@ code(target,pos,ArraysasList(valueTypes))}
     *  </blockquote>。
     * 
     * 
     * @param target the method handle to invoke after the arguments are dropped
     * @param valueTypes the type(s) of the argument(s) to drop
     * @param pos position of first argument to drop (zero for the leftmost)
     * @return a method handle which drops arguments of the given types,
     *         before calling the original method handle
     * @throws NullPointerException if the target is null,
     *                              or if the {@code valueTypes} array or any of its elements is null
     * @throws IllegalArgumentException if any element of {@code valueTypes} is {@code void.class},
     *                  or if {@code pos} is negative or greater than the arity of the target,
     *                  or if the new method handle's type would have
     *                  <a href="MethodHandle.html#maxarity">too many parameters</a>
     */
    public static
    MethodHandle dropArguments(MethodHandle target, int pos, Class<?>... valueTypes) {
        return dropArguments(target, pos, Arrays.asList(valueTypes));
    }

    /**
     * Adapts a target method handle by pre-processing
     * one or more of its arguments, each with its own unary filter function,
     * and then calling the target with each pre-processed argument
     * replaced by the result of its corresponding filter function.
     * <p>
     * The pre-processing is performed by one or more method handles,
     * specified in the elements of the {@code filters} array.
     * The first element of the filter array corresponds to the {@code pos}
     * argument of the target, and so on in sequence.
     * <p>
     * Null arguments in the array are treated as identity functions,
     * and the corresponding arguments left unchanged.
     * (If there are no non-null elements in the array, the original target is returned.)
     * Each filter is applied to the corresponding argument of the adapter.
     * <p>
     * If a filter {@code F} applies to the {@code N}th argument of
     * the target, then {@code F} must be a method handle which
     * takes exactly one argument.  The type of {@code F}'s sole argument
     * replaces the corresponding argument type of the target
     * in the resulting adapted method handle.
     * The return type of {@code F} must be identical to the corresponding
     * parameter type of the target.
     * <p>
     * It is an error if there are elements of {@code filters}
     * (null or not)
     * which do not correspond to argument positions in the target.
     * <p><b>Example:</b>
     * <blockquote><pre>{@code
import static java.lang.invoke.MethodHandles.*;
import static java.lang.invoke.MethodType.*;
...
MethodHandle cat = lookup().findVirtual(String.class,
  "concat", methodType(String.class, String.class));
MethodHandle upcase = lookup().findVirtual(String.class,
  "toUpperCase", methodType(String.class));
assertEquals("xy", (String) cat.invokeExact("x", "y"));
MethodHandle f0 = filterArguments(cat, 0, upcase);
assertEquals("Xy", (String) f0.invokeExact("x", "y")); // Xy
MethodHandle f1 = filterArguments(cat, 1, upcase);
assertEquals("xY", (String) f1.invokeExact("x", "y")); // xY
MethodHandle f2 = filterArguments(cat, 0, upcase, upcase);
assertEquals("XY", (String) f2.invokeExact("x", "y")); // XY
     * }</pre></blockquote>
     * <p> Here is pseudocode for the resulting adapter:
     * <blockquote><pre>{@code
     * V target(P... p, A[i]... a[i], B... b);
     * A[i] filter[i](V[i]);
     * T adapter(P... p, V[i]... v[i], B... b) {
     *   return target(p..., f[i](v[i])..., b...);
     * }
     * }</pre></blockquote>
     *
     * <p>
     *  通过预处理其一个或多个参数来适应目标方法句柄,每个参数都有自己的一元过滤器函数,然后调用每个预处理参数的目标,并替换为其相应的过滤器函数的结果
     * <p>
     *  预处理由在{@code filters}数组的元素中指定的一个或多个方法句柄执行。过滤器数组的第一个元素对应于目标的{@code pos}参数,依此类推
     * <p>
     * 数组中的空参数被视为标识函数,相应的参数保持不变(如果数组中没有非空元素,则返回原始目标)每个过滤器应用于适配器的相应参数
     * <p>
     *  如果一个过滤器{@code F}适用于目标的{@code N}个参数,那么{@code F}必须是一个只有一个参数的方法句柄。
     * {@code F}的唯一参数的类型在所得到的适配的方法句柄中替换目标的相应参数类型{@code F}的返回类型必须与目标的相应参数类型相同。
     * <p>
     * 如果有{@code filters}(null或not)的元素与目标中的参数位置不对应<p> <b>示例：</b> <blockquote> <pre> {@ code import static javalanginvokeMethodHandles *; import static javalanginvokeMethodType *; MethodHandle cat = lookup()findVirtual(Stringclass,"concat",methodType(Stringclass,Stringclass)); MethodHandle upcase = lookup()findVirtual(Stringclass,"toUpperCase",methodType(Stringclass)); assertEquals("xy",(String)catinvokeExact("x","y")); MethodHandle f0 = filterArguments(cat,0,upcase); assertEquals("Xy",(String)f0invokeExact("x","y")); // Xy MethodHandle f1 = filterArguments(cat,1,upcase); assertEquals("xY",(String)f1invokeExact("x","y")); // xY MethodHandle f2 = filterArguments(cat,0,upcase,upcase); assertEquals("XY",(String)f2invokeExact("x","y")); // XY}
     *  </pre> </blockquote> <p>这里是生成的适配器的伪代码：<blockquote> <pre> {@ code V target(P p,A [i] a [i],B b) ; A [i] filter [i](V [i]); T适配器(P p,V [i] v [i],B b){return target(p,f [i](v [i]),b) }
     * } </pre> </blockquote>。
     * 
     * 
     * @param target the method handle to invoke after arguments are filtered
     * @param pos the position of the first argument to filter
     * @param filters method handles to call initially on filtered arguments
     * @return method handle which incorporates the specified argument filtering logic
     * @throws NullPointerException if the target is null
     *                              or if the {@code filters} array is null
     * @throws IllegalArgumentException if a non-null element of {@code filters}
     *          does not match a corresponding argument type of target as described above,
     *          or if the {@code pos+filters.length} is greater than {@code target.type().parameterCount()},
     *          or if the resulting method handle's type would have
     *          <a href="MethodHandle.html#maxarity">too many parameters</a>
     */
    public static
    MethodHandle filterArguments(MethodHandle target, int pos, MethodHandle... filters) {
        filterArgumentsCheckArity(target, pos, filters);
        MethodHandle adapter = target;
        int curPos = pos-1;  // pre-incremented
        for (MethodHandle filter : filters) {
            curPos += 1;
            if (filter == null)  continue;  // ignore null elements of filters
            adapter = filterArgument(adapter, curPos, filter);
        }
        return adapter;
    }

    /*non-public*/ static
    MethodHandle filterArgument(MethodHandle target, int pos, MethodHandle filter) {
        filterArgumentChecks(target, pos, filter);
        MethodType targetType = target.type();
        MethodType filterType = filter.type();
        BoundMethodHandle result = target.rebind();
        Class<?> newParamType = filterType.parameterType(0);
        LambdaForm lform = result.editor().filterArgumentForm(1 + pos, BasicType.basicType(newParamType));
        MethodType newType = targetType.changeParameterType(pos, newParamType);
        result = result.copyWithExtendL(newType, lform, filter);
        return result;
    }

    private static void filterArgumentsCheckArity(MethodHandle target, int pos, MethodHandle[] filters) {
        MethodType targetType = target.type();
        int maxPos = targetType.parameterCount();
        if (pos + filters.length > maxPos)
            throw newIllegalArgumentException("too many filters");
    }

    private static void filterArgumentChecks(MethodHandle target, int pos, MethodHandle filter) throws RuntimeException {
        MethodType targetType = target.type();
        MethodType filterType = filter.type();
        if (filterType.parameterCount() != 1
            || filterType.returnType() != targetType.parameterType(pos))
            throw newIllegalArgumentException("target and filter types do not match", targetType, filterType);
    }

    /**
     * Adapts a target method handle by pre-processing
     * a sub-sequence of its arguments with a filter (another method handle).
     * The pre-processed arguments are replaced by the result (if any) of the
     * filter function.
     * The target is then called on the modified (usually shortened) argument list.
     * <p>
     * If the filter returns a value, the target must accept that value as
     * its argument in position {@code pos}, preceded and/or followed by
     * any arguments not passed to the filter.
     * If the filter returns void, the target must accept all arguments
     * not passed to the filter.
     * No arguments are reordered, and a result returned from the filter
     * replaces (in order) the whole subsequence of arguments originally
     * passed to the adapter.
     * <p>
     * The argument types (if any) of the filter
     * replace zero or one argument types of the target, at position {@code pos},
     * in the resulting adapted method handle.
     * The return type of the filter (if any) must be identical to the
     * argument type of the target at position {@code pos}, and that target argument
     * is supplied by the return value of the filter.
     * <p>
     * In all cases, {@code pos} must be greater than or equal to zero, and
     * {@code pos} must also be less than or equal to the target's arity.
     * <p><b>Example:</b>
     * <blockquote><pre>{@code
import static java.lang.invoke.MethodHandles.*;
import static java.lang.invoke.MethodType.*;
...
MethodHandle deepToString = publicLookup()
  .findStatic(Arrays.class, "deepToString", methodType(String.class, Object[].class));

MethodHandle ts1 = deepToString.asCollector(String[].class, 1);
assertEquals("[strange]", (String) ts1.invokeExact("strange"));

MethodHandle ts2 = deepToString.asCollector(String[].class, 2);
assertEquals("[up, down]", (String) ts2.invokeExact("up", "down"));

MethodHandle ts3 = deepToString.asCollector(String[].class, 3);
MethodHandle ts3_ts2 = collectArguments(ts3, 1, ts2);
assertEquals("[top, [up, down], strange]",
             (String) ts3_ts2.invokeExact("top", "up", "down", "strange"));

MethodHandle ts3_ts2_ts1 = collectArguments(ts3_ts2, 3, ts1);
assertEquals("[top, [up, down], [strange]]",
             (String) ts3_ts2_ts1.invokeExact("top", "up", "down", "strange"));

MethodHandle ts3_ts2_ts3 = collectArguments(ts3_ts2, 1, ts3);
assertEquals("[top, [[up, down, strange], charm], bottom]",
             (String) ts3_ts2_ts3.invokeExact("top", "up", "down", "strange", "charm", "bottom"));
     * }</pre></blockquote>
     * <p> Here is pseudocode for the resulting adapter:
     * <blockquote><pre>{@code
     * T target(A...,V,C...);
     * V filter(B...);
     * T adapter(A... a,B... b,C... c) {
     *   V v = filter(b...);
     *   return target(a...,v,c...);
     * }
     * // and if the filter has no arguments:
     * T target2(A...,V,C...);
     * V filter2();
     * T adapter2(A... a,C... c) {
     *   V v = filter2();
     *   return target2(a...,v,c...);
     * }
     * // and if the filter has a void return:
     * T target3(A...,C...);
     * void filter3(B...);
     * void adapter3(A... a,B... b,C... c) {
     *   filter3(b...);
     *   return target3(a...,c...);
     * }
     * }</pre></blockquote>
     * <p>
     * A collection adapter {@code collectArguments(mh, 0, coll)} is equivalent to
     * one which first "folds" the affected arguments, and then drops them, in separate
     * steps as follows:
     * <blockquote><pre>{@code
     * mh = MethodHandles.dropArguments(mh, 1, coll.type().parameterList()); //step 2
     * mh = MethodHandles.foldArguments(mh, coll); //step 1
     * }</pre></blockquote>
     * If the target method handle consumes no arguments besides than the result
     * (if any) of the filter {@code coll}, then {@code collectArguments(mh, 0, coll)}
     * is equivalent to {@code filterReturnValue(coll, mh)}.
     * If the filter method handle {@code coll} consumes one argument and produces
     * a non-void result, then {@code collectArguments(mh, N, coll)}
     * is equivalent to {@code filterArguments(mh, N, coll)}.
     * Other equivalences are possible but would require argument permutation.
     *
     * <p>
     * MethodHandle filterArgument(MethodHandle target,int pos,MethodHandle filter){filterArgumentChecks(target,pos,filter); MethodType targetType = targettype(); MethodType filterType = filtertype(); BoundMethodHandle result = targetrebind();类<?> newParamType = filterTypeparameterType(0); LambdaForm lform =ultsitor()filterArgumentForm(1 + pos,BasicTypebasicType(newParamType)); MethodType newType = targetTypechangeParameterType(pos,newParamType); result = resultcopyWithExtendL(newType,lform,filter);返回结果; }
     * }。
     * 
     * private static void filterArgumentsCheckArity(MethodHandle target,int pos,MethodHandle [] filters){MethodType targetType = targettype(); int maxPos = targetTypeparameterCount(); if(pos + filterslength> maxPos)throw newIllegalArgumentException("too many filters"); }
     * }。
     * 
     *  private static void filterArgumentChecks(MethodHandle target,int pos,MethodHandle filter)throws Runt
     * imeException {MethodType targetType = targettype(); MethodType filterType = filtertype(); if(filterTypeparameterCount()！= 1 || filterTypereturnType()！= targetTypeparameterType(pos))throw newIllegalArgumentException("target and filter types do not match",targetType,filterType); }
     * }。
     * 
     * / **通过使用过滤器(另一个方法句柄)预处理其参数的子序列来适应目标方法句柄。预处理的参数被过滤器函数的结果(如果有的话)替换。然后调用目标对修改的(通常缩短的)参数列表
     * <p>
     *  如果过滤器返回一个值,目标必须接受该值作为其位置{@code pos}中的参数,前面和/或后面是任何未传递给过滤器的参数。
     * 如果过滤器返回void,目标必须接受所有参数传递给过滤器没有参数被重新排序,从过滤器返回的结果替换(按顺序)原始传递给适配器的参数的整个子序列。
     * <p>
     * 过滤器的参数类型(如果有的话)在所得到的自适应方法句柄中的位置{@code pos}处替换目标的零或一个参数类型。
     * 过滤器的返回类型(如果有)必须与参数相同类型的目标在位置{@code pos},并且目标参数由过滤器的返回值提供。
     * <p>
     *  在所有情况下,{@code pos}必须大于或等于零,而且{@code pos}也必须小于或等于目标的实体<p> <b>示例：</b> <blockquote> <pre> {@ code import static javalanginvokeMethodHandles *; import static javalanginvokeMethodType *; MethodHandle deepToString = publicLookup()findStatic(Arraysclass,"deepToString",methodType(Stringclass,Object [] class));。
     * 
     * MethodHandle ts1 = deepToStringasCollector(String [] class,1); assertEquals("[strange]",(String)ts1in
     * vokeExact("strange"));。
     * 
     *  MethodHandle ts2 = deepToStringasCollector(String [] class,2); assertEquals("[up,down]",(String)ts2i
     * nvokeExact("up","down"));。
     * 
     *  MethodHandle ts3 = deepToStringasCollector(String [] class,3); MethodHandle ts3_ts2 = collectArgumen
     * ts(ts3,1,ts2); assertEquals("[top,[up,down],strange]",(String)ts3_ts2invokeExact("top","up","down","s
     * trange"));。
     * 
     *  MethodHandle ts3_ts2_ts1 = collectArguments(ts3_ts2,3,ts1); assertEquals("[top,[up,down],[strange]]"
     * ,(String)ts3_ts2_ts1invokeExact("top","up","down","strange"));。
     * 
     * MethodHandle ts3_ts2_ts3 = collectArguments(ts3_ts2,1,ts3); assertEquals("top","up","down","strange",
     * "charm","bottom",assertEquals("[top,[[up,down,strange],charm],bottom]",(String)ts3_ts2_ts3invokeExact
     *  )); } </pre> </blockquote> <p>这里是生成的适配器的伪代码：<blockquote> <pre> {@ code T target(A,V,C); V滤波器(B); T适配器(A a,B b,C c){V v =滤波器(b);返回目标(a,v,c); }
     *  //如果过滤器没有参数：T target2(A,V,C); V filter2(); T adapter2(A a,C c){V v = filter2(); return target2(a,v,c); }
     *  //如果过滤器有void return：T target3(A,C); void filter3(B); void adapter3(A a,B b,C c){filter3(b); return target3(a,c); }
     * } </pre> </blockquote>。
     * <p>
     * 集合适配器{@code collectArguments(mh,0,coll)}相当于首先"折叠"受影响的参数,然后在单独的步骤中删除它们,如下所示：<blockquote> <pre> {@ code mh = MethodHandlesdropArguments(mh,1,colltype()parameterList()); // step 2 mh = MethodHandlesfoldArguments(mh,coll); // step 1}
     *  </pre> </blockquote>如果目标方法句柄除了过滤器{@code coll}的结果(如果有)之外没有使用参数,那么{@code collectArguments(mh,0,coll) }
     * 等价于{@code filterReturnValue(coll,mh)}如果过滤器方法句柄{@code coll}使用一个参数并产生一个非void结果,那么{@code collectArguments(mh,N,coll)到{@code filterArguments(mh,N,coll)}
     * 其他等价是可能的,但是将需要自变量排列。
     * 
     * 
     * @param target the method handle to invoke after filtering the subsequence of arguments
     * @param pos the position of the first adapter argument to pass to the filter,
     *            and/or the target argument which receives the result of the filter
     * @param filter method handle to call on the subsequence of arguments
     * @return method handle which incorporates the specified argument subsequence filtering logic
     * @throws NullPointerException if either argument is null
     * @throws IllegalArgumentException if the return type of {@code filter}
     *          is non-void and is not the same as the {@code pos} argument of the target,
     *          or if {@code pos} is not between 0 and the target's arity, inclusive,
     *          or if the resulting method handle's type would have
     *          <a href="MethodHandle.html#maxarity">too many parameters</a>
     * @see MethodHandles#foldArguments
     * @see MethodHandles#filterArguments
     * @see MethodHandles#filterReturnValue
     */
    public static
    MethodHandle collectArguments(MethodHandle target, int pos, MethodHandle filter) {
        MethodType newType = collectArgumentsChecks(target, pos, filter);
        MethodType collectorType = filter.type();
        BoundMethodHandle result = target.rebind();
        LambdaForm lform;
        if (collectorType.returnType().isArray() && filter.intrinsicName() == Intrinsic.NEW_ARRAY) {
            lform = result.editor().collectArgumentArrayForm(1 + pos, filter);
            if (lform != null) {
                return result.copyWith(newType, lform);
            }
        }
        lform = result.editor().collectArgumentsForm(1 + pos, collectorType.basicType());
        return result.copyWithExtendL(newType, lform, filter);
    }

    private static MethodType collectArgumentsChecks(MethodHandle target, int pos, MethodHandle filter) throws RuntimeException {
        MethodType targetType = target.type();
        MethodType filterType = filter.type();
        Class<?> rtype = filterType.returnType();
        List<Class<?>> filterArgs = filterType.parameterList();
        if (rtype == void.class) {
            return targetType.insertParameterTypes(pos, filterArgs);
        }
        if (rtype != targetType.parameterType(pos)) {
            throw newIllegalArgumentException("target and filter types do not match", targetType, filterType);
        }
        return targetType.dropParameterTypes(pos, pos+1).insertParameterTypes(pos, filterArgs);
    }

    /**
     * Adapts a target method handle by post-processing
     * its return value (if any) with a filter (another method handle).
     * The result of the filter is returned from the adapter.
     * <p>
     * If the target returns a value, the filter must accept that value as
     * its only argument.
     * If the target returns void, the filter must accept no arguments.
     * <p>
     * The return type of the filter
     * replaces the return type of the target
     * in the resulting adapted method handle.
     * The argument type of the filter (if any) must be identical to the
     * return type of the target.
     * <p><b>Example:</b>
     * <blockquote><pre>{@code
import static java.lang.invoke.MethodHandles.*;
import static java.lang.invoke.MethodType.*;
...
MethodHandle cat = lookup().findVirtual(String.class,
  "concat", methodType(String.class, String.class));
MethodHandle length = lookup().findVirtual(String.class,
  "length", methodType(int.class));
System.out.println((String) cat.invokeExact("x", "y")); // xy
MethodHandle f0 = filterReturnValue(cat, length);
System.out.println((int) f0.invokeExact("x", "y")); // 2
     * }</pre></blockquote>
     * <p> Here is pseudocode for the resulting adapter:
     * <blockquote><pre>{@code
     * V target(A...);
     * T filter(V);
     * T adapter(A... a) {
     *   V v = target(a...);
     *   return filter(v);
     * }
     * // and if the target has a void return:
     * void target2(A...);
     * T filter2();
     * T adapter2(A... a) {
     *   target2(a...);
     *   return filter2();
     * }
     * // and if the filter has a void return:
     * V target3(A...);
     * void filter3(V);
     * void adapter3(A... a) {
     *   V v = target3(a...);
     *   filter3(v);
     * }
     * }</pre></blockquote>
     * <p>
     * 通过使用过滤器(另一个方法句柄)后处理其返回值(如果有)来适应目标方法句柄过滤器的结果从适配器返回
     * <p>
     *  如果目标返回一个值,过滤器必须接受该值作为其唯一的参数如果目标返回void,过滤器必须不接受任何参数
     * <p>
     * 过滤器的返回类型替换所得到的适配方法句柄中目标的返回类型过滤器的参数类型(如果有)必须与目标的返回类型相同<p> <b>示例：</b > <blockquote> <pre> {@ code import static javalanginvokeMethodHandles *; import static javalanginvokeMethodType *; MethodHandle cat = lookup()findVirtual(Stringclass,"concat",methodType(Stringclass,Stringclass)); MethodHandle length = lookup()findVirtual(Stringclass,"length",methodType(intclass)); systemoutprintln((String)catinvokeExact("x","y")); // xy MethodHandle f0 = filterReturnValue(cat,length); Systemoutprintln((int)f0invokeExact("x","y")); // 2}
     *  </pre> </blockquote> <p>这里是生成的适配器的伪代码：<blockquote> <pre> {@ code V target(A); T滤波器(V); T衔接子(A a){V v =靶(a);返回滤波器(v); }
     *  //如果目标有void return：void target2(A); T filter2(); T adapter2(A a){target2(a); return filter2(); } //如
     * 果过滤器有void return：V target3(A); void filter3(V); void adapter3(A a){V v = target3(a); filter3(v); }} </pre>
     *  </blockquote>。
     * 
     * 
     * @param target the method handle to invoke before filtering the return value
     * @param filter method handle to call on the return value
     * @return method handle which incorporates the specified return value filtering logic
     * @throws NullPointerException if either argument is null
     * @throws IllegalArgumentException if the argument list of {@code filter}
     *          does not match the return type of target as described above
     */
    public static
    MethodHandle filterReturnValue(MethodHandle target, MethodHandle filter) {
        MethodType targetType = target.type();
        MethodType filterType = filter.type();
        filterReturnValueChecks(targetType, filterType);
        BoundMethodHandle result = target.rebind();
        BasicType rtype = BasicType.basicType(filterType.returnType());
        LambdaForm lform = result.editor().filterReturnForm(rtype, false);
        MethodType newType = targetType.changeReturnType(filterType.returnType());
        result = result.copyWithExtendL(newType, lform, filter);
        return result;
    }

    private static void filterReturnValueChecks(MethodType targetType, MethodType filterType) throws RuntimeException {
        Class<?> rtype = targetType.returnType();
        int filterValues = filterType.parameterCount();
        if (filterValues == 0
                ? (rtype != void.class)
                : (rtype != filterType.parameterType(0)))
            throw newIllegalArgumentException("target and filter types do not match", targetType, filterType);
    }

    /**
     * Adapts a target method handle by pre-processing
     * some of its arguments, and then calling the target with
     * the result of the pre-processing, inserted into the original
     * sequence of arguments.
     * <p>
     * The pre-processing is performed by {@code combiner}, a second method handle.
     * Of the arguments passed to the adapter, the first {@code N} arguments
     * are copied to the combiner, which is then called.
     * (Here, {@code N} is defined as the parameter count of the combiner.)
     * After this, control passes to the target, with any result
     * from the combiner inserted before the original {@code N} incoming
     * arguments.
     * <p>
     * If the combiner returns a value, the first parameter type of the target
     * must be identical with the return type of the combiner, and the next
     * {@code N} parameter types of the target must exactly match the parameters
     * of the combiner.
     * <p>
     * If the combiner has a void return, no result will be inserted,
     * and the first {@code N} parameter types of the target
     * must exactly match the parameters of the combiner.
     * <p>
     * The resulting adapter is the same type as the target, except that the
     * first parameter type is dropped,
     * if it corresponds to the result of the combiner.
     * <p>
     * (Note that {@link #dropArguments(MethodHandle,int,List) dropArguments} can be used to remove any arguments
     * that either the combiner or the target does not wish to receive.
     * If some of the incoming arguments are destined only for the combiner,
     * consider using {@link MethodHandle#asCollector asCollector} instead, since those
     * arguments will not need to be live on the stack on entry to the
     * target.)
     * <p><b>Example:</b>
     * <blockquote><pre>{@code
import static java.lang.invoke.MethodHandles.*;
import static java.lang.invoke.MethodType.*;
...
MethodHandle trace = publicLookup().findVirtual(java.io.PrintStream.class,
  "println", methodType(void.class, String.class))
    .bindTo(System.out);
MethodHandle cat = lookup().findVirtual(String.class,
  "concat", methodType(String.class, String.class));
assertEquals("boojum", (String) cat.invokeExact("boo", "jum"));
MethodHandle catTrace = foldArguments(cat, trace);
// also prints "boo":
assertEquals("boojum", (String) catTrace.invokeExact("boo", "jum"));
     * }</pre></blockquote>
     * <p> Here is pseudocode for the resulting adapter:
     * <blockquote><pre>{@code
     * // there are N arguments in A...
     * T target(V, A[N]..., B...);
     * V combiner(A...);
     * T adapter(A... a, B... b) {
     *   V v = combiner(a...);
     *   return target(v, a..., b...);
     * }
     * // and if the combiner has a void return:
     * T target2(A[N]..., B...);
     * void combiner2(A...);
     * T adapter2(A... a, B... b) {
     *   combiner2(a...);
     *   return target2(a..., b...);
     * }
     * }</pre></blockquote>
     * <p>
     * 通过预处理其一些参数来适应目标方法句柄,然后使用预处理的结果调用目标,插入到原始的参数序列中
     * <p>
     *  预处理由{@code combiner}(传递给适配器的参数的第二个方法句柄)执行,第一个{@code N}个参数被复制到组合器,然后被调用(这里,{@code N }被定义为组合器的参数计数)在此之
     * 后,控制传递到目标,来自组合器的任何结果在原始{@code N}传入参数之前插入。
     * <p>
     * 如果组合器返回值,则目标的第一个参数类型必须与组合器的返回类型相同,并且目标的下一个{@code N}参数类型必须与组合器的参数完全匹配
     * <p>
     *  如果组合器具有void返回,则不会插入结果,并且目标的第一个{@code N}参数类型必须与组合器的参数完全匹配
     * <p>
     *  生成的适配器与目标类型相同,除了第一个参数类型被丢弃,如果它对应于组合器的结果
     * <p>
     * invokeExact("boo","jum")); MethodHandle catTrace = foldArguments(cat,trace); //也打印"boo"：assertEquals(
     * "boojum",(String)catTraceinvokeExact("boo","jum")); } </pre> </blockquote> <p>这里是生成的适配器的伪代码：<blockquote>
     *  <pre> {@ code // A T target(V,A [N],B)中有N个参数; V组合器(A); T适配器(A a,B b){V v =组合器(a);返回目标(v,a,b); } //如果
     * 组合器有一个void返回：T target2(A [N],B); void combiner2(A); T适配器2(A a,B b){combiner2(a); return target2(a,b); }
     * } </pre> </blockquote>。
     * 
     * 
     * @param target the method handle to invoke after arguments are combined
     * @param combiner method handle to call initially on the incoming arguments
     * @return method handle which incorporates the specified argument folding logic
     * @throws NullPointerException if either argument is null
     * @throws IllegalArgumentException if {@code combiner}'s return type
     *          is non-void and not the same as the first argument type of
     *          the target, or if the initial {@code N} argument types
     *          of the target
     *          (skipping one matching the {@code combiner}'s return type)
     *          are not identical with the argument types of {@code combiner}
     */
    public static
    MethodHandle foldArguments(MethodHandle target, MethodHandle combiner) {
        int foldPos = 0;
        MethodType targetType = target.type();
        MethodType combinerType = combiner.type();
        Class<?> rtype = foldArgumentChecks(foldPos, targetType, combinerType);
        BoundMethodHandle result = target.rebind();
        boolean dropResult = (rtype == void.class);
        // Note:  This may cache too many distinct LFs. Consider backing off to varargs code.
        LambdaForm lform = result.editor().foldArgumentsForm(1 + foldPos, dropResult, combinerType.basicType());
        MethodType newType = targetType;
        if (!dropResult)
            newType = newType.dropParameterTypes(foldPos, foldPos + 1);
        result = result.copyWithExtendL(newType, lform, combiner);
        return result;
    }

    private static Class<?> foldArgumentChecks(int foldPos, MethodType targetType, MethodType combinerType) {
        int foldArgs   = combinerType.parameterCount();
        Class<?> rtype = combinerType.returnType();
        int foldVals = rtype == void.class ? 0 : 1;
        int afterInsertPos = foldPos + foldVals;
        boolean ok = (targetType.parameterCount() >= afterInsertPos + foldArgs);
        if (ok && !(combinerType.parameterList()
                    .equals(targetType.parameterList().subList(afterInsertPos,
                                                               afterInsertPos + foldArgs))))
            ok = false;
        if (ok && foldVals != 0 && combinerType.returnType() != targetType.parameterType(0))
            ok = false;
        if (!ok)
            throw misMatchedTypes("target and combiner types", targetType, combinerType);
        return rtype;
    }

    /**
     * Makes a method handle which adapts a target method handle,
     * by guarding it with a test, a boolean-valued method handle.
     * If the guard fails, a fallback handle is called instead.
     * All three method handles must have the same corresponding
     * argument and return types, except that the return type
     * of the test must be boolean, and the test is allowed
     * to have fewer arguments than the other two method handles.
     * <p> Here is pseudocode for the resulting adapter:
     * <blockquote><pre>{@code
     * boolean test(A...);
     * T target(A...,B...);
     * T fallback(A...,B...);
     * T adapter(A... a,B... b) {
     *   if (test(a...))
     *     return target(a..., b...);
     *   else
     *     return fallback(a..., b...);
     * }
     * }</pre></blockquote>
     * Note that the test arguments ({@code a...} in the pseudocode) cannot
     * be modified by execution of the test, and so are passed unchanged
     * from the caller to the target or fallback as appropriate.
     * <p>
     * 通过使用测试保护目标方法句柄,布尔值方法句柄,使得方法句柄适应目标方法句柄如果保护失败,则调用一个后退句柄。
     * 所有三个方法句柄必须具有相同的相应参数和返回类型,除非测试的返回类型必须是布尔值,并且允许测试具有比其他两个方法句柄更少的参数<p>这里是生成的适配器的伪代码：<blockquote> <pre> {@ code boolean test(A ); T目标(A,B); T fallback(A,B); T适配器(A a,B b){if(test(a))return target(a,b); else return fallback(a,b); }
     * } </pre> </blockquote>注意测试参数({@code a}在伪代码中)不能通过执行测试来修改,因此从呼叫者不变地传递到目标或适当的回退。
     * 通过使用测试保护目标方法句柄,布尔值方法句柄,使得方法句柄适应目标方法句柄如果保护失败,则调用一个后退句柄。
     * 
     * 
     * @param test method handle used for test, must return boolean
     * @param target method handle to call if test passes
     * @param fallback method handle to call if test fails
     * @return method handle which incorporates the specified if/then/else logic
     * @throws NullPointerException if any argument is null
     * @throws IllegalArgumentException if {@code test} does not return boolean,
     *          or if all three method types do not match (with the return
     *          type of {@code test} changed to match that of the target).
     */
    public static
    MethodHandle guardWithTest(MethodHandle test,
                               MethodHandle target,
                               MethodHandle fallback) {
        MethodType gtype = test.type();
        MethodType ttype = target.type();
        MethodType ftype = fallback.type();
        if (!ttype.equals(ftype))
            throw misMatchedTypes("target and fallback types", ttype, ftype);
        if (gtype.returnType() != boolean.class)
            throw newIllegalArgumentException("guard type is not a predicate "+gtype);
        List<Class<?>> targs = ttype.parameterList();
        List<Class<?>> gargs = gtype.parameterList();
        if (!targs.equals(gargs)) {
            int gpc = gargs.size(), tpc = targs.size();
            if (gpc >= tpc || !targs.subList(0, gpc).equals(gargs))
                throw misMatchedTypes("target and test types", ttype, gtype);
            test = dropArguments(test, gpc, targs.subList(gpc, tpc));
            gtype = test.type();
        }
        return MethodHandleImpl.makeGuardWithTest(test, target, fallback);
    }

    static RuntimeException misMatchedTypes(String what, MethodType t1, MethodType t2) {
        return newIllegalArgumentException(what + " must match: " + t1 + " != " + t2);
    }

    /**
     * Makes a method handle which adapts a target method handle,
     * by running it inside an exception handler.
     * If the target returns normally, the adapter returns that value.
     * If an exception matching the specified type is thrown, the fallback
     * handle is called instead on the exception, plus the original arguments.
     * <p>
     * The target and handler must have the same corresponding
     * argument and return types, except that handler may omit trailing arguments
     * (similarly to the predicate in {@link #guardWithTest guardWithTest}).
     * Also, the handler must have an extra leading parameter of {@code exType} or a supertype.
     * <p> Here is pseudocode for the resulting adapter:
     * <blockquote><pre>{@code
     * T target(A..., B...);
     * T handler(ExType, A...);
     * T adapter(A... a, B... b) {
     *   try {
     *     return target(a..., b...);
     *   } catch (ExType ex) {
     *     return handler(ex, a...);
     *   }
     * }
     * }</pre></blockquote>
     * Note that the saved arguments ({@code a...} in the pseudocode) cannot
     * be modified by execution of the target, and so are passed unchanged
     * from the caller to the handler, if the handler is invoked.
     * <p>
     * The target and handler must return the same type, even if the handler
     * always throws.  (This might happen, for instance, because the handler
     * is simulating a {@code finally} clause).
     * To create such a throwing handler, compose the handler creation logic
     * with {@link #throwException throwException},
     * in order to create a method handle of the correct return type.
     * <p>
     * 通过在异常处理程序中运行来创建适应目标方法句柄的方法句柄如果目标正常返回,那么适配器会返回该值。如果抛出与指定类型匹配的异常,则会调用该异常的后备句柄加原始参数
     * <p>
     * 目标和处理程序必须具有相同的相应参数和返回类型,除了处理程序可以省略尾随参数(类似于{@link #guardWithTest guardWithTest}中的谓词)。
     * 此外,处理程序必须有一个额外的引导参数{@code exType }或超类型<p>这里是生成的适配器的伪代码：<blockquote> <pre> {@ code T target(A,B); T处理程序(ExType,A); T适配器(A a,B b){try {return target(a,b); }
     *  catch(ExType ex){return handler(ex,a);请注意,保存的参数(伪代码中的{@code a})不能通过执行目标修改,所以如果处理程序被调用。
     * 目标和处理程序必须具有相同的相应参数和返回类型,除了处理程序可以省略尾随参数(类似于{@link #guardWithTest guardWithTest}中的谓词)。
     * <p>
     * 
     * @param target method handle to call
     * @param exType the type of exception which the handler will catch
     * @param handler method handle to call if a matching exception is thrown
     * @return method handle which incorporates the specified try/catch logic
     * @throws NullPointerException if any argument is null
     * @throws IllegalArgumentException if {@code handler} does not accept
     *          the given exception type, or if the method handle types do
     *          not match in their return types and their
     *          corresponding parameters
     */
    public static
    MethodHandle catchException(MethodHandle target,
                                Class<? extends Throwable> exType,
                                MethodHandle handler) {
        MethodType ttype = target.type();
        MethodType htype = handler.type();
        if (htype.parameterCount() < 1 ||
            !htype.parameterType(0).isAssignableFrom(exType))
            throw newIllegalArgumentException("handler does not accept exception type "+exType);
        if (htype.returnType() != ttype.returnType())
            throw misMatchedTypes("target and handler return types", ttype, htype);
        List<Class<?>> targs = ttype.parameterList();
        List<Class<?>> hargs = htype.parameterList();
        hargs = hargs.subList(1, hargs.size());  // omit leading parameter from handler
        if (!targs.equals(hargs)) {
            int hpc = hargs.size(), tpc = targs.size();
            if (hpc >= tpc || !targs.subList(0, hpc).equals(hargs))
                throw misMatchedTypes("target and handler types", ttype, htype);
            handler = dropArguments(handler, 1+hpc, targs.subList(hpc, tpc));
            htype = handler.type();
        }
        return MethodHandleImpl.makeGuardWithCatch(target, exType, handler);
    }

    /**
     * Produces a method handle which will throw exceptions of the given {@code exType}.
     * The method handle will accept a single argument of {@code exType},
     * and immediately throw it as an exception.
     * The method type will nominally specify a return of {@code returnType}.
     * The return type may be anything convenient:  It doesn't matter to the
     * method handle's behavior, since it will never return normally.
     * <p>
     * 目标和处理程序必须返回相同的类型,即使处理程序总是throws(这可能发生,例如,因为处理程序正在模拟一个{@code finally}子句)要创建这样的抛出处理程序,组成处理程序创建逻辑与{@link #throwException throwException}
     * ,以便创建正确返回类型的方法句柄。
     * 
     * 
     * @param returnType the return type of the desired method handle
     * @param exType the parameter type of the desired method handle
     * @return method handle which can throw the given exceptions
     * @throws NullPointerException if either argument is null
     */
    public static
    MethodHandle throwException(Class<?> returnType, Class<? extends Throwable> exType) {
        if (!Throwable.class.isAssignableFrom(exType))
            throw new ClassCastException(exType.getName());
        return MethodHandleImpl.throwException(MethodType.methodType(returnType, exType));
    }
}
