/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2013, Oracle and/or its affiliates. All rights reserved.
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
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import sun.reflect.annotation.AnnotationSupport;

/**
 * Information about method parameters.
 *
 * A {@code Parameter} provides information about method parameters,
 * including its name and modifiers.  It also provides an alternate
 * means of obtaining attributes for the parameter.
 *
 * <p>
 *  有关方法参数的信息。
 * 
 *  {@code Parameter}提供有关方法参数的信息,包括其名称和修饰符。它还提供了一种获取参数属性的备用方法。
 * 
 * 
 * @since 1.8
 */
public final class Parameter implements AnnotatedElement {

    private final String name;
    private final int modifiers;
    private final Executable executable;
    private final int index;

    /**
     * Package-private constructor for {@code Parameter}.
     *
     * If method parameter data is present in the classfile, then the
     * JVM creates {@code Parameter} objects directly.  If it is
     * absent, however, then {@code Executable} uses this constructor
     * to synthesize them.
     *
     * <p>
     *  {@code Parameter}的Package-private构造函数。
     * 
     *  如果方法参数数据存在于类文件中,则JVM直接创建{@code Parameter}对象。如果它不存在,然而,{@code Executable}使用这个构造函数来合成它们。
     * 
     * 
     * @param name The name of the parameter.
     * @param modifiers The modifier flags for the parameter.
     * @param executable The executable which defines this parameter.
     * @param index The index of the parameter.
     */
    Parameter(String name,
              int modifiers,
              Executable executable,
              int index) {
        this.name = name;
        this.modifiers = modifiers;
        this.executable = executable;
        this.index = index;
    }

    /**
     * Compares based on the executable and the index.
     *
     * <p>
     *  基于可执行文件和索引进行比较。
     * 
     * 
     * @param obj The object to compare.
     * @return Whether or not this is equal to the argument.
     */
    public boolean equals(Object obj) {
        if(obj instanceof Parameter) {
            Parameter other = (Parameter)obj;
            return (other.executable.equals(executable) &&
                    other.index == index);
        }
        return false;
    }

    /**
     * Returns a hash code based on the executable's hash code and the
     * index.
     *
     * <p>
     *  返回基于可执行文件的哈希码和索引的哈希码。
     * 
     * 
     * @return A hash code based on the executable's hash code.
     */
    public int hashCode() {
        return executable.hashCode() ^ index;
    }

    /**
     * Returns true if the parameter has a name according to the class
     * file; returns false otherwise. Whether a parameter has a name
     * is determined by the {@literal MethodParameters} attribute of
     * the method which declares the parameter.
     *
     * <p>
     *  如果参数具有根据类文件的名称,则返回true;否则返回false。参数是否具有名称由声明该参数的方法的{@literal MethodParameters}属性确定。
     * 
     * 
     * @return true if and only if the parameter has a name according
     * to the class file.
     */
    public boolean isNamePresent() {
        return executable.hasRealParameterData() && name != null;
    }

    /**
     * Returns a string describing this parameter.  The format is the
     * modifiers for the parameter, if any, in canonical order as
     * recommended by <cite>The Java&trade; Language
     * Specification</cite>, followed by the fully- qualified type of
     * the parameter (excluding the last [] if the parameter is
     * variable arity), followed by "..." if the parameter is variable
     * arity, followed by a space, followed by the name of the
     * parameter.
     *
     * <p>
     *  返回描述此参数的字符串。
     * 格式是参数的修饰符,如果有的话,按照<cite> Java&trade;语言规范</cite>,其后是参数的完全限定类型(如果参数是变量arity,则不包括最后一个[]),后跟"...",如果参数是变量
     * arity,后跟参数的名称。
     *  返回描述此参数的字符串。
     * 
     * 
     * @return A string representation of the parameter and associated
     * information.
     */
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final Type type = getParameterizedType();
        final String typename = type.getTypeName();

        sb.append(Modifier.toString(getModifiers()));

        if(0 != modifiers)
            sb.append(' ');

        if(isVarArgs())
            sb.append(typename.replaceFirst("\\[\\]$", "..."));
        else
            sb.append(typename);

        sb.append(' ');
        sb.append(getName());

        return sb.toString();
    }

    /**
     * Return the {@code Executable} which declares this parameter.
     *
     * <p>
     *  返回声明此参数的{@code Executable}。
     * 
     * 
     * @return The {@code Executable} declaring this parameter.
     */
    public Executable getDeclaringExecutable() {
        return executable;
    }

    /**
     * Get the modifier flags for this the parameter represented by
     * this {@code Parameter} object.
     *
     * <p>
     * 获取此{@code Parameter}对象所表示的参数的修饰符标志。
     * 
     * 
     * @return The modifier flags for this parameter.
     */
    public int getModifiers() {
        return modifiers;
    }

    /**
     * Returns the name of the parameter.  If the parameter's name is
     * {@linkplain #isNamePresent() present}, then this method returns
     * the name provided by the class file. Otherwise, this method
     * synthesizes a name of the form argN, where N is the index of
     * the parameter in the descriptor of the method which declares
     * the parameter.
     *
     * <p>
     *  返回参数的名称。如果参数的名称为{@linkplain #isNamePresent()present},那么此方法将返回类文件提供的名称。
     * 否则,此方法合成格式argN的名称,其中N是声明该参数的方法的描述符中的参数的索引。
     * 
     * 
     * @return The name of the parameter, either provided by the class
     *         file or synthesized if the class file does not provide
     *         a name.
     */
    public String getName() {
        // Note: empty strings as paramete names are now outlawed.
        // The .equals("") is for compatibility with current JVM
        // behavior.  It may be removed at some point.
        if(name == null || name.equals(""))
            return "arg" + index;
        else
            return name;
    }

    // Package-private accessor to the real name field.
    String getRealName() {
        return name;
    }

    /**
     * Returns a {@code Type} object that identifies the parameterized
     * type for the parameter represented by this {@code Parameter}
     * object.
     *
     * <p>
     *  返回一个{@code Type}对象,该对象标识由此{@code Parameter}对象表示的参数的参数化类型。
     * 
     * 
     * @return a {@code Type} object identifying the parameterized
     * type of the parameter represented by this object
     */
    public Type getParameterizedType() {
        Type tmp = parameterTypeCache;
        if (null == tmp) {
            tmp = executable.getAllGenericParameterTypes()[index];
            parameterTypeCache = tmp;
        }

        return tmp;
    }

    private transient volatile Type parameterTypeCache = null;

    /**
     * Returns a {@code Class} object that identifies the
     * declared type for the parameter represented by this
     * {@code Parameter} object.
     *
     * <p>
     *  返回{@code Class}对象,该对象标识由此{@code Parameter}对象表示的参数的声明类型。
     * 
     * 
     * @return a {@code Class} object identifying the declared
     * type of the parameter represented by this object
     */
    public Class<?> getType() {
        Class<?> tmp = parameterClassCache;
        if (null == tmp) {
            tmp = executable.getParameterTypes()[index];
            parameterClassCache = tmp;
        }
        return tmp;
    }

    /**
     * Returns an AnnotatedType object that represents the use of a type to
     * specify the type of the formal parameter represented by this Parameter.
     *
     * <p>
     *  返回一个AnnotatedType对象,它表示使用一个类型来指定由此参数表示的形式参数的类型。
     * 
     * 
     * @return an {@code AnnotatedType} object representing the use of a type
     *         to specify the type of the formal parameter represented by this
     *         Parameter
     */
    public AnnotatedType getAnnotatedType() {
        // no caching for now
        return executable.getAnnotatedParameterTypes()[index];
    }

    private transient volatile Class<?> parameterClassCache = null;

    /**
     * Returns {@code true} if this parameter is implicitly declared
     * in source code; returns {@code false} otherwise.
     *
     * <p>
     *  如果此参数在源代码中被隐式声明,则返回{@code true};否则返回{@code false}。
     * 
     * 
     * @return true if and only if this parameter is implicitly
     * declared as defined by <cite>The Java&trade; Language
     * Specification</cite>.
     */
    public boolean isImplicit() {
        return Modifier.isMandated(getModifiers());
    }

    /**
     * Returns {@code true} if this parameter is neither implicitly
     * nor explicitly declared in source code; returns {@code false}
     * otherwise.
     *
     * @jls 13.1 The Form of a Binary
     * <p>
     *  如果此参数在源代码中既未隐式也未显式声明,则返回{@code true};否则返回{@code false}。
     * 
     *  @jls 13.1二进制的形式
     * 
     * 
     * @return true if and only if this parameter is a synthetic
     * construct as defined by
     * <cite>The Java&trade; Language Specification</cite>.
     */
    public boolean isSynthetic() {
        return Modifier.isSynthetic(getModifiers());
    }

    /**
     * Returns {@code true} if this parameter represents a variable
     * argument list; returns {@code false} otherwise.
     *
     * <p>
     *  如果此参数表示变量参数列表,则返回{@code true};否则返回{@code false}。
     * 
     * 
     * @return {@code true} if an only if this parameter represents a
     * variable argument list.
     */
    public boolean isVarArgs() {
        return executable.isVarArgs() &&
            index == executable.getParameterCount() - 1;
    }


    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     * 
     * 
     * @throws NullPointerException {@inheritDoc}
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
    public Annotation[] getDeclaredAnnotations() {
        return executable.getParameterAnnotations()[index];
    }

    /**
    /* <p>
    /* 
     * @throws NullPointerException {@inheritDoc}
     */
    public <T extends Annotation> T getDeclaredAnnotation(Class<T> annotationClass) {
        // Only annotations on classes are inherited, for all other
        // objects getDeclaredAnnotation is the same as
        // getAnnotation.
        return getAnnotation(annotationClass);
    }

    /**
    /* <p>
    /* 
     * @throws NullPointerException {@inheritDoc}
     */
    @Override
    public <T extends Annotation> T[] getDeclaredAnnotationsByType(Class<T> annotationClass) {
        // Only annotations on classes are inherited, for all other
        // objects getDeclaredAnnotations is the same as
        // getAnnotations.
        return getAnnotationsByType(annotationClass);
    }

    /**
     * {@inheritDoc}
     * <p>
     *  {@inheritDoc}
     */
    public Annotation[] getAnnotations() {
        return getDeclaredAnnotations();
    }

    private transient Map<Class<? extends Annotation>, Annotation> declaredAnnotations;

    private synchronized Map<Class<? extends Annotation>, Annotation> declaredAnnotations() {
        if(null == declaredAnnotations) {
            declaredAnnotations =
                new HashMap<Class<? extends Annotation>, Annotation>();
            Annotation[] ann = getDeclaredAnnotations();
            for(int i = 0; i < ann.length; i++)
                declaredAnnotations.put(ann[i].annotationType(), ann[i]);
        }
        return declaredAnnotations;
   }

}
