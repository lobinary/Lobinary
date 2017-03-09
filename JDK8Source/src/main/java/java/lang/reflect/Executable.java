/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang.reflect;

import java.lang.annotation.*;
import java.util.Map;
import java.util.Objects;
import sun.reflect.annotation.AnnotationParser;
import sun.reflect.annotation.AnnotationSupport;
import sun.reflect.annotation.TypeAnnotationParser;
import sun.reflect.annotation.TypeAnnotation;
import sun.reflect.generics.repository.ConstructorRepository;

/**
 * A shared superclass for the common functionality of {@link Method}
 * and {@link Constructor}.
 *
 * <p>
 *  用于{@link Method}和{@link Constructor}的通用功能的共享超类。
 * 
 * 
 * @since 1.8
 */
public abstract class Executable extends AccessibleObject
    implements Member, GenericDeclaration {
    /*
     * Only grant package-visibility to the constructor.
     * <p>
     *  仅授予构造函数的软件包可见性。
     * 
     */
    Executable() {}

    /**
     * Accessor method to allow code sharing
     * <p>
     *  访问者方法允许代码共享
     * 
     */
    abstract byte[] getAnnotationBytes();

    /**
     * Accessor method to allow code sharing
     * <p>
     *  访问者方法允许代码共享
     * 
     */
    abstract Executable getRoot();

    /**
     * Does the Executable have generic information.
     * <p>
     *  可执行文件是否具有通用信息。
     * 
     */
    abstract boolean hasGenericInformation();

    abstract ConstructorRepository getGenericInfo();

    boolean equalParamTypes(Class<?>[] params1, Class<?>[] params2) {
        /* Avoid unnecessary cloning */
        if (params1.length == params2.length) {
            for (int i = 0; i < params1.length; i++) {
                if (params1[i] != params2[i])
                    return false;
            }
            return true;
        }
        return false;
    }

    Annotation[][] parseParameterAnnotations(byte[] parameterAnnotations) {
        return AnnotationParser.parseParameterAnnotations(
               parameterAnnotations,
               sun.misc.SharedSecrets.getJavaLangAccess().
               getConstantPool(getDeclaringClass()),
               getDeclaringClass());
    }

    void separateWithCommas(Class<?>[] types, StringBuilder sb) {
        for (int j = 0; j < types.length; j++) {
            sb.append(types[j].getTypeName());
            if (j < (types.length - 1))
                sb.append(",");
        }

    }

    void printModifiersIfNonzero(StringBuilder sb, int mask, boolean isDefault) {
        int mod = getModifiers() & mask;

        if (mod != 0 && !isDefault) {
            sb.append(Modifier.toString(mod)).append(' ');
        } else {
            int access_mod = mod & Modifier.ACCESS_MODIFIERS;
            if (access_mod != 0)
                sb.append(Modifier.toString(access_mod)).append(' ');
            if (isDefault)
                sb.append("default ");
            mod = (mod & ~Modifier.ACCESS_MODIFIERS);
            if (mod != 0)
                sb.append(Modifier.toString(mod)).append(' ');
        }
    }

    String sharedToString(int modifierMask,
                          boolean isDefault,
                          Class<?>[] parameterTypes,
                          Class<?>[] exceptionTypes) {
        try {
            StringBuilder sb = new StringBuilder();

            printModifiersIfNonzero(sb, modifierMask, isDefault);
            specificToStringHeader(sb);

            sb.append('(');
            separateWithCommas(parameterTypes, sb);
            sb.append(')');
            if (exceptionTypes.length > 0) {
                sb.append(" throws ");
                separateWithCommas(exceptionTypes, sb);
            }
            return sb.toString();
        } catch (Exception e) {
            return "<" + e + ">";
        }
    }

    /**
     * Generate toString header information specific to a method or
     * constructor.
     * <p>
     *  生成特定于方法或构造函数的toString标题信息。
     * 
     */
    abstract void specificToStringHeader(StringBuilder sb);

    String sharedToGenericString(int modifierMask, boolean isDefault) {
        try {
            StringBuilder sb = new StringBuilder();

            printModifiersIfNonzero(sb, modifierMask, isDefault);

            TypeVariable<?>[] typeparms = getTypeParameters();
            if (typeparms.length > 0) {
                boolean first = true;
                sb.append('<');
                for(TypeVariable<?> typeparm: typeparms) {
                    if (!first)
                        sb.append(',');
                    // Class objects can't occur here; no need to test
                    // and call Class.getName().
                    sb.append(typeparm.toString());
                    first = false;
                }
                sb.append("> ");
            }

            specificToGenericStringHeader(sb);

            sb.append('(');
            Type[] params = getGenericParameterTypes();
            for (int j = 0; j < params.length; j++) {
                String param = params[j].getTypeName();
                if (isVarArgs() && (j == params.length - 1)) // replace T[] with T...
                    param = param.replaceFirst("\\[\\]$", "...");
                sb.append(param);
                if (j < (params.length - 1))
                    sb.append(',');
            }
            sb.append(')');
            Type[] exceptions = getGenericExceptionTypes();
            if (exceptions.length > 0) {
                sb.append(" throws ");
                for (int k = 0; k < exceptions.length; k++) {
                    sb.append((exceptions[k] instanceof Class)?
                              ((Class)exceptions[k]).getName():
                              exceptions[k].toString());
                    if (k < (exceptions.length - 1))
                        sb.append(',');
                }
            }
            return sb.toString();
        } catch (Exception e) {
            return "<" + e + ">";
        }
    }

    /**
     * Generate toGenericString header information specific to a
     * method or constructor.
     * <p>
     *  生成特定于方法或构造函数的toGenericString标头信息。
     * 
     */
    abstract void specificToGenericStringHeader(StringBuilder sb);

    /**
     * Returns the {@code Class} object representing the class or interface
     * that declares the executable represented by this object.
     * <p>
     *  返回表示声明此对象所表示的可执行文件的类或接口的{@code Class}对象。
     * 
     */
    public abstract Class<?> getDeclaringClass();

    /**
     * Returns the name of the executable represented by this object.
     * <p>
     *  返回此对象表示的可执行文件的名称。
     * 
     */
    public abstract String getName();

    /**
     * Returns the Java language {@linkplain Modifier modifiers} for
     * the executable represented by this object.
     * <p>
     *  返回此对象所表示的可执行文件的Java语言{@linkplain Modifier修饰符}。
     * 
     */
    public abstract int getModifiers();

    /**
     * Returns an array of {@code TypeVariable} objects that represent the
     * type variables declared by the generic declaration represented by this
     * {@code GenericDeclaration} object, in declaration order.  Returns an
     * array of length 0 if the underlying generic declaration declares no type
     * variables.
     *
     * <p>
     *  返回一个{@code TypeVariable}对象数组,它们以声明顺序表示由此{@code GenericDeclaration}对象表示的通用声明声明的类型变量。
     * 如果底层通用声明没有声明类型变量,则返回长度为0的数组。
     * 
     * 
     * @return an array of {@code TypeVariable} objects that represent
     *     the type variables declared by this generic declaration
     * @throws GenericSignatureFormatError if the generic
     *     signature of this generic declaration does not conform to
     *     the format specified in
     *     <cite>The Java&trade; Virtual Machine Specification</cite>
     */
    public abstract TypeVariable<?>[] getTypeParameters();

    /**
     * Returns an array of {@code Class} objects that represent the formal
     * parameter types, in declaration order, of the executable
     * represented by this object.  Returns an array of length
     * 0 if the underlying executable takes no parameters.
     *
     * <p>
     *  返回一个{@code Class}对象的数组,它们以声明顺序表示由此对象表示的可执行文件的形式参数类型。如果基础可执行文件不带参数,则返回长度为0的数组。
     * 
     * 
     * @return the parameter types for the executable this object
     * represents
     */
    public abstract Class<?>[] getParameterTypes();

    /**
     * Returns the number of formal parameters (whether explicitly
     * declared or implicitly declared or neither) for the executable
     * represented by this object.
     *
     * <p>
     * 返回由此对象表示的可执行文件的形式参数数(无论是显式声明还是隐含声明,或不返回)。
     * 
     * 
     * @since 1.8
     * @return The number of formal parameters for the executable this
     * object represents
     */
    public int getParameterCount() {
        throw new AbstractMethodError();
    }

    /**
     * Returns an array of {@code Type} objects that represent the formal
     * parameter types, in declaration order, of the executable represented by
     * this object. Returns an array of length 0 if the
     * underlying executable takes no parameters.
     *
     * <p>If a formal parameter type is a parameterized type,
     * the {@code Type} object returned for it must accurately reflect
     * the actual type parameters used in the source code.
     *
     * <p>If a formal parameter type is a type variable or a parameterized
     * type, it is created. Otherwise, it is resolved.
     *
     * <p>
     *  返回一个{@code Type}对象数组,它们以声明顺序表示由此对象表示的可执行文件的形式参数类型。如果基础可执行文件不带参数,则返回长度为0的数组。
     * 
     *  <p>如果形式参数类型是参数化类型,则返回的{@code Type}对象必须准确反映源代码中使用的实际类型参数。
     * 
     *  <p>如果形式参数类型是类型变量或参数化类型,则会创建它。否则,它被解决。
     * 
     * 
     * @return an array of {@code Type}s that represent the formal
     *     parameter types of the underlying executable, in declaration order
     * @throws GenericSignatureFormatError
     *     if the generic method signature does not conform to the format
     *     specified in
     *     <cite>The Java&trade; Virtual Machine Specification</cite>
     * @throws TypeNotPresentException if any of the parameter
     *     types of the underlying executable refers to a non-existent type
     *     declaration
     * @throws MalformedParameterizedTypeException if any of
     *     the underlying executable's parameter types refer to a parameterized
     *     type that cannot be instantiated for any reason
     */
    public Type[] getGenericParameterTypes() {
        if (hasGenericInformation())
            return getGenericInfo().getParameterTypes();
        else
            return getParameterTypes();
    }

    /**
     * Behaves like {@code getGenericParameterTypes}, but returns type
     * information for all parameters, including synthetic parameters.
     * <p>
     *  行为像{@code getGenericParameterTypes},但返回所有参数的类型信息,包括综合参数。
     * 
     */
    Type[] getAllGenericParameterTypes() {
        final boolean genericInfo = hasGenericInformation();

        // Easy case: we don't have generic parameter information.  In
        // this case, we just return the result of
        // getParameterTypes().
        if (!genericInfo) {
            return getParameterTypes();
        } else {
            final boolean realParamData = hasRealParameterData();
            final Type[] genericParamTypes = getGenericParameterTypes();
            final Type[] nonGenericParamTypes = getParameterTypes();
            final Type[] out = new Type[nonGenericParamTypes.length];
            final Parameter[] params = getParameters();
            int fromidx = 0;
            // If we have real parameter data, then we use the
            // synthetic and mandate flags to our advantage.
            if (realParamData) {
                for (int i = 0; i < out.length; i++) {
                    final Parameter param = params[i];
                    if (param.isSynthetic() || param.isImplicit()) {
                        // If we hit a synthetic or mandated parameter,
                        // use the non generic parameter info.
                        out[i] = nonGenericParamTypes[i];
                    } else {
                        // Otherwise, use the generic parameter info.
                        out[i] = genericParamTypes[fromidx];
                        fromidx++;
                    }
                }
            } else {
                // Otherwise, use the non-generic parameter data.
                // Without method parameter reflection data, we have
                // no way to figure out which parameters are
                // synthetic/mandated, thus, no way to match up the
                // indexes.
                return genericParamTypes.length == nonGenericParamTypes.length ?
                    genericParamTypes : nonGenericParamTypes;
            }
            return out;
        }
    }

    /**
     * Returns an array of {@code Parameter} objects that represent
     * all the parameters to the underlying executable represented by
     * this object.  Returns an array of length 0 if the executable
     * has no parameters.
     *
     * <p>The parameters of the underlying executable do not necessarily
     * have unique names, or names that are legal identifiers in the
     * Java programming language (JLS 3.8).
     *
     * <p>
     *  返回一个{@code Parameter}对象数组,表示该对象所代表的底层可执行程序的所有参数。如果可执行文件没有参数,则返回长度为0的数组。
     * 
     *  <p>基本可执行文件的参数不一定具有唯一的名称,或者是Java编程语言(JLS 3.8)中的合法标识符的名称。
     * 
     * 
     * @since 1.8
     * @throws MalformedParametersException if the class file contains
     * a MethodParameters attribute that is improperly formatted.
     * @return an array of {@code Parameter} objects representing all
     * the parameters to the executable this object represents.
     */
    public Parameter[] getParameters() {
        // TODO: This may eventually need to be guarded by security
        // mechanisms similar to those in Field, Method, etc.
        //
        // Need to copy the cached array to prevent users from messing
        // with it.  Since parameters are immutable, we can
        // shallow-copy.
        return privateGetParameters().clone();
    }

    private Parameter[] synthesizeAllParams() {
        final int realparams = getParameterCount();
        final Parameter[] out = new Parameter[realparams];
        for (int i = 0; i < realparams; i++)
            // TODO: is there a way to synthetically derive the
            // modifiers?  Probably not in the general case, since
            // we'd have no way of knowing about them, but there
            // may be specific cases.
            out[i] = new Parameter("arg" + i, 0, this, i);
        return out;
    }

    private void verifyParameters(final Parameter[] parameters) {
        final int mask = Modifier.FINAL | Modifier.SYNTHETIC | Modifier.MANDATED;

        if (getParameterTypes().length != parameters.length)
            throw new MalformedParametersException("Wrong number of parameters in MethodParameters attribute");

        for (Parameter parameter : parameters) {
            final String name = parameter.getRealName();
            final int mods = parameter.getModifiers();

            if (name != null) {
                if (name.isEmpty() || name.indexOf('.') != -1 ||
                    name.indexOf(';') != -1 || name.indexOf('[') != -1 ||
                    name.indexOf('/') != -1) {
                    throw new MalformedParametersException("Invalid parameter name \"" + name + "\"");
                }
            }

            if (mods != (mods & mask)) {
                throw new MalformedParametersException("Invalid parameter modifiers");
            }
        }
    }

    private Parameter[] privateGetParameters() {
        // Use tmp to avoid multiple writes to a volatile.
        Parameter[] tmp = parameters;

        if (tmp == null) {

            // Otherwise, go to the JVM to get them
            try {
                tmp = getParameters0();
            } catch(IllegalArgumentException e) {
                // Rethrow ClassFormatErrors
                throw new MalformedParametersException("Invalid constant pool index");
            }

            // If we get back nothing, then synthesize parameters
            if (tmp == null) {
                hasRealParameterData = false;
                tmp = synthesizeAllParams();
            } else {
                hasRealParameterData = true;
                verifyParameters(tmp);
            }

            parameters = tmp;
        }

        return tmp;
    }

    boolean hasRealParameterData() {
        // If this somehow gets called before parameters gets
        // initialized, force it into existence.
        if (parameters == null) {
            privateGetParameters();
        }
        return hasRealParameterData;
    }

    private transient volatile boolean hasRealParameterData;
    private transient volatile Parameter[] parameters;

    private native Parameter[] getParameters0();
    native byte[] getTypeAnnotationBytes0();

    // Needed by reflectaccess
    byte[] getTypeAnnotationBytes() {
        return getTypeAnnotationBytes0();
    }

    /**
     * Returns an array of {@code Class} objects that represent the
     * types of exceptions declared to be thrown by the underlying
     * executable represented by this object.  Returns an array of
     * length 0 if the executable declares no exceptions in its {@code
     * throws} clause.
     *
     * <p>
     *  返回一个{@code Class}对象数组,表示由此对象表示的底层可执行程序声明抛出的异常类型。如果可执行程序在其{@code throws}子句中声明没有异常,则返回长度为0的数组。
     * 
     * 
     * @return the exception types declared as being thrown by the
     * executable this object represents
     */
    public abstract Class<?>[] getExceptionTypes();

    /**
     * Returns an array of {@code Type} objects that represent the
     * exceptions declared to be thrown by this executable object.
     * Returns an array of length 0 if the underlying executable declares
     * no exceptions in its {@code throws} clause.
     *
     * <p>If an exception type is a type variable or a parameterized
     * type, it is created. Otherwise, it is resolved.
     *
     * <p>
     * 返回一个{@code Type}对象数组,它们表示此可执行对象声明抛出的异常。如果底层可执行程序在其{@code throws}子句中声明没有异常,则返回长度为0的数组。
     * 
     *  <p>如果异常类型是类型变量或参数化类型,则会创建它。否则,它被解决。
     * 
     * 
     * @return an array of Types that represent the exception types
     *     thrown by the underlying executable
     * @throws GenericSignatureFormatError
     *     if the generic method signature does not conform to the format
     *     specified in
     *     <cite>The Java&trade; Virtual Machine Specification</cite>
     * @throws TypeNotPresentException if the underlying executable's
     *     {@code throws} clause refers to a non-existent type declaration
     * @throws MalformedParameterizedTypeException if
     *     the underlying executable's {@code throws} clause refers to a
     *     parameterized type that cannot be instantiated for any reason
     */
    public Type[] getGenericExceptionTypes() {
        Type[] result;
        if (hasGenericInformation() &&
            ((result = getGenericInfo().getExceptionTypes()).length > 0))
            return result;
        else
            return getExceptionTypes();
    }

    /**
     * Returns a string describing this {@code Executable}, including
     * any type parameters.
     * <p>
     *  返回描述此{@code Executable}的字符串,包括任何类型参数。
     * 
     * 
     * @return a string describing this {@code Executable}, including
     * any type parameters
     */
    public abstract String toGenericString();

    /**
     * Returns {@code true} if this executable was declared to take a
     * variable number of arguments; returns {@code false} otherwise.
     *
     * <p>
     *  如果此可执行文件声明为接受可变数量的参数,则返回{@code true};否则返回{@code false}。
     * 
     * 
     * @return {@code true} if an only if this executable was declared
     * to take a variable number of arguments.
     */
    public boolean isVarArgs()  {
        return (getModifiers() & Modifier.VARARGS) != 0;
    }

    /**
     * Returns {@code true} if this executable is a synthetic
     * construct; returns {@code false} otherwise.
     *
     * <p>
     *  如果此可执行文件是合成结构,则返回{@code true};否则返回{@code false}。
     * 
     * 
     * @return true if and only if this executable is a synthetic
     * construct as defined by
     * <cite>The Java&trade; Language Specification</cite>.
     * @jls 13.1 The Form of a Binary
     */
    public boolean isSynthetic() {
        return Modifier.isSynthetic(getModifiers());
    }

    /**
     * Returns an array of arrays of {@code Annotation}s that
     * represent the annotations on the formal parameters, in
     * declaration order, of the {@code Executable} represented by
     * this object.  Synthetic and mandated parameters (see
     * explanation below), such as the outer "this" parameter to an
     * inner class constructor will be represented in the returned
     * array.  If the executable has no parameters (meaning no formal,
     * no synthetic, and no mandated parameters), a zero-length array
     * will be returned.  If the {@code Executable} has one or more
     * parameters, a nested array of length zero is returned for each
     * parameter with no annotations. The annotation objects contained
     * in the returned arrays are serializable.  The caller of this
     * method is free to modify the returned arrays; it will have no
     * effect on the arrays returned to other callers.
     *
     * A compiler may add extra parameters that are implicitly
     * declared in source ("mandated"), as well as parameters that
     * are neither implicitly nor explicitly declared in source
     * ("synthetic") to the parameter list for a method.  See {@link
     * java.lang.reflect.Parameter} for more information.
     *
     * <p>
     *  返回一个{@code Annotation}数组的数组,它表示由该对象表示的{@code Executable}的声明顺序中的形式参数的注释。
     * 合成和强制参数(参见下面的解释),例如外部"this"参数到内部类构造函数将在返回的数组中表示。如果可执行文件没有参数(意味着没有形式,没有合成,并且没有强制的参数),将返回零长度数组。
     * 如果{@code Executable}有一个或多个参数,则会为每个没有注释的参数返回长度为零的嵌套数组。包含在返回的数组中的注释对象是可序列化的。
     * 这个方法的调用者可以自由修改返回的数组;它对返回给其他调用者的数组没有影响。
     * 
     * 编译器可以添加在源("强制的")中隐式声明的额外参数,以及既不在方法的参数列表中的源("合成")中隐式地或显式地声明的参数。
     * 有关详细信息,请参阅{@link java.lang.reflect.Parameter}。
     * 
     * 
     * @see java.lang.reflect.Parameter
     * @see java.lang.reflect.Parameter#getAnnotations
     * @return an array of arrays that represent the annotations on
     *    the formal and implicit parameters, in declaration order, of
     *    the executable represented by this object
     */
    public abstract Annotation[][] getParameterAnnotations();

    Annotation[][] sharedGetParameterAnnotations(Class<?>[] parameterTypes,
                                                 byte[] parameterAnnotations) {
        int numParameters = parameterTypes.length;
        if (parameterAnnotations == null)
            return new Annotation[numParameters][0];

        Annotation[][] result = parseParameterAnnotations(parameterAnnotations);

        if (result.length != numParameters)
            handleParameterNumberMismatch(result.length, numParameters);
        return result;
    }

    abstract void handleParameterNumberMismatch(int resultLength, int numParameters);

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @throws NullPointerException  {@inheritDoc}
     */
    public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
        Objects.requireNonNull(annotationClass);
        return annotationClass.cast(declaredAnnotations().get(annotationClass));
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @throws NullPointerException {@inheritDoc}
     * @since 1.8
     */
    @Override
    public <T extends Annotation> T[] getAnnotationsByType(Class<T> annotationClass) {
        Objects.requireNonNull(annotationClass);

        return AnnotationSupport.getDirectlyAndIndirectlyPresent(declaredAnnotations(), annotationClass);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     */
    public Annotation[] getDeclaredAnnotations()  {
        return AnnotationParser.toArray(declaredAnnotations());
    }

    private transient Map<Class<? extends Annotation>, Annotation> declaredAnnotations;

    private synchronized  Map<Class<? extends Annotation>, Annotation> declaredAnnotations() {
        if (declaredAnnotations == null) {
            Executable root = getRoot();
            if (root != null) {
                declaredAnnotations = root.declaredAnnotations();
            } else {
                declaredAnnotations = AnnotationParser.parseAnnotations(
                    getAnnotationBytes(),
                    sun.misc.SharedSecrets.getJavaLangAccess().
                    getConstantPool(getDeclaringClass()),
                    getDeclaringClass());
            }
        }
        return declaredAnnotations;
    }

    /**
     * Returns an {@code AnnotatedType} object that represents the use of a type to
     * specify the return type of the method/constructor represented by this
     * Executable.
     *
     * If this {@code Executable} object represents a constructor, the {@code
     * AnnotatedType} object represents the type of the constructed object.
     *
     * If this {@code Executable} object represents a method, the {@code
     * AnnotatedType} object represents the use of a type to specify the return
     * type of the method.
     *
     * <p>
     *  返回一个{@code AnnotatedType}对象,它表示使用类型来指定此可执行文件所表示的方法/构造函数的返回类型。
     * 
     *  如果此{@code Executable}对象表示构造函数,则{@code AnnotatedType}对象表示构造对象的类型。
     * 
     *  如果{@code Executable}对象表示一个方法,{@code AnnotatedType}对象表示使用类型来指定方法的返回类型。
     * 
     * 
     * @return an object representing the return type of the method
     * or constructor represented by this {@code Executable}
     *
     * @since 1.8
     */
    public abstract AnnotatedType getAnnotatedReturnType();

    /* Helper for subclasses of Executable.
     *
     * Returns an AnnotatedType object that represents the use of a type to
     * specify the return type of the method/constructor represented by this
     * Executable.
     *
     * <p>
     *  返回一个AnnotatedType对象,它表示使用一个类型来指定由此可执行文件表示的方法/构造函数的返回类型。
     * 
     * 
     * @since 1.8
     */
    AnnotatedType getAnnotatedReturnType0(Type returnType) {
        return TypeAnnotationParser.buildAnnotatedType(getTypeAnnotationBytes0(),
                sun.misc.SharedSecrets.getJavaLangAccess().
                        getConstantPool(getDeclaringClass()),
                this,
                getDeclaringClass(),
                returnType,
                TypeAnnotation.TypeAnnotationTarget.METHOD_RETURN);
    }

    /**
     * Returns an {@code AnnotatedType} object that represents the use of a
     * type to specify the receiver type of the method/constructor represented
     * by this Executable object. The receiver type of a method/constructor is
     * available only if the method/constructor has a <em>receiver
     * parameter</em> (JLS 8.4.1).
     *
     * If this {@code Executable} object represents a constructor or instance
     * method that does not have a receiver parameter, or has a receiver
     * parameter with no annotations on its type, then the return value is an
     * {@code AnnotatedType} object representing an element with no
     * annotations.
     *
     * If this {@code Executable} object represents a static method, then the
     * return value is null.
     *
     * <p>
     *  返回一个{@code AnnotatedType}对象,它表示使用类型来指定由此可执行对象表示的方法/构造函数的接收器类型。
     * 仅当方法/构造函数具有<em>接收器参数</em>(JLS 8.4.1)时,方法/构造函数的接收器类型才可用。
     * 
     * 如果此{@code Executable}对象表示没有接收者参数的构造函数或实例方法,或者其类型上没有注释的接收者参数,则返回值是一个{@code AnnotatedType}对象,无注释。
     * 
     *  如果此{@code Executable}对象表示静态方法,则返回值为null。
     * 
     * 
     * @return an object representing the receiver type of the method or
     * constructor represented by this {@code Executable}
     *
     * @since 1.8
     */
    public AnnotatedType getAnnotatedReceiverType() {
        if (Modifier.isStatic(this.getModifiers()))
            return null;
        return TypeAnnotationParser.buildAnnotatedType(getTypeAnnotationBytes0(),
                sun.misc.SharedSecrets.getJavaLangAccess().
                        getConstantPool(getDeclaringClass()),
                this,
                getDeclaringClass(),
                getDeclaringClass(),
                TypeAnnotation.TypeAnnotationTarget.METHOD_RECEIVER);
    }

    /**
     * Returns an array of {@code AnnotatedType} objects that represent the use
     * of types to specify formal parameter types of the method/constructor
     * represented by this Executable. The order of the objects in the array
     * corresponds to the order of the formal parameter types in the
     * declaration of the method/constructor.
     *
     * Returns an array of length 0 if the method/constructor declares no
     * parameters.
     *
     * <p>
     *  返回一个{@code AnnotatedType}对象数组,表示使用类型来指定此可执行文件所表示的方法/构造函数的形式参数类型。数组中对象的顺序对应于方法/构造函数的声明中形式参数类型的顺序。
     * 
     *  如果方法/构造函数声明没有参数,则返回长度为0的数组。
     * 
     * 
     * @return an array of objects representing the types of the
     * formal parameters of the method or constructor represented by this
     * {@code Executable}
     *
     * @since 1.8
     */
    public AnnotatedType[] getAnnotatedParameterTypes() {
        return TypeAnnotationParser.buildAnnotatedTypes(getTypeAnnotationBytes0(),
                sun.misc.SharedSecrets.getJavaLangAccess().
                        getConstantPool(getDeclaringClass()),
                this,
                getDeclaringClass(),
                getAllGenericParameterTypes(),
                TypeAnnotation.TypeAnnotationTarget.METHOD_FORMAL_PARAMETER);
    }

    /**
     * Returns an array of {@code AnnotatedType} objects that represent the use
     * of types to specify the declared exceptions of the method/constructor
     * represented by this Executable. The order of the objects in the array
     * corresponds to the order of the exception types in the declaration of
     * the method/constructor.
     *
     * Returns an array of length 0 if the method/constructor declares no
     * exceptions.
     *
     * <p>
     *  返回一个{@code AnnotatedType}对象数组,表示使用类型来指定由此可执行文件表示的方法/构造函数的声明的异常。数组中对象的顺序与方法/构造函数的声明中的异常类型的顺序相对应。
     * 
     * 
     * @return an array of objects representing the declared
     * exceptions of the method or constructor represented by this {@code
     * Executable}
     *
     * @since 1.8
     */
    public AnnotatedType[] getAnnotatedExceptionTypes() {
        return TypeAnnotationParser.buildAnnotatedTypes(getTypeAnnotationBytes0(),
                sun.misc.SharedSecrets.getJavaLangAccess().
                        getConstantPool(getDeclaringClass()),
                this,
                getDeclaringClass(),
                getGenericExceptionTypes(),
                TypeAnnotation.TypeAnnotationTarget.THROWS);
    }

}
