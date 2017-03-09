/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.source.tree;

/**
 * Common interface for all nodes in an abstract syntax tree.
 *
 * <p><b>WARNING:</b> This interface and its sub-interfaces are
 * subject to change as the Java&trade; programming language evolves.
 * These interfaces are implemented by the JDK Java compiler (javac)
 * and should not be implemented either directly or indirectly by
 * other applications.
 *
 * <p>
 *  抽象语法树中所有节点的公共接口。
 * 
 *  <p> <b>警告：</b>此接口及其子界面可能会随Java&trade;编程语言演变。这些接口由JDK Java编译器(javac)实现,并且不应由其他应用程序直接或间接实现。
 * 
 * 
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 *
 * @since 1.6
 */
@jdk.Exported
public interface Tree {

    /**
     * Enumerates all kinds of trees.
     * <p>
     *  枚举各种树。
     * 
     */
    @jdk.Exported
    public enum Kind {

        ANNOTATED_TYPE(AnnotatedTypeTree.class),

        /**
         * Used for instances of {@link AnnotationTree}
         * representing declaration annotations.
         * <p>
         *  用于表示声明注释的{@link AnnotationTree}实例。
         * 
         */
        ANNOTATION(AnnotationTree.class),

        /**
         * Used for instances of {@link AnnotationTree}
         * representing type annotations.
         * <p>
         *  用于表示类型注释的{@link AnnotationTree}实例。
         * 
         */
        TYPE_ANNOTATION(AnnotationTree.class),

        /**
         * Used for instances of {@link ArrayAccessTree}.
         * <p>
         *  用于{@link ArrayAccessTree}的实例。
         * 
         */
        ARRAY_ACCESS(ArrayAccessTree.class),

        /**
         * Used for instances of {@link ArrayTypeTree}.
         * <p>
         *  用于{@link ArrayTypeTree}的实例。
         * 
         */
        ARRAY_TYPE(ArrayTypeTree.class),

        /**
         * Used for instances of {@link AssertTree}.
         * <p>
         *  用于{@link AssertTree}的实例。
         * 
         */
        ASSERT(AssertTree.class),

        /**
         * Used for instances of {@link AssignmentTree}.
         * <p>
         *  用于{@link AssignmentTree}的实例。
         * 
         */
        ASSIGNMENT(AssignmentTree.class),

        /**
         * Used for instances of {@link BlockTree}.
         * <p>
         *  用于{@link BlockTree}的实例。
         * 
         */
        BLOCK(BlockTree.class),

        /**
         * Used for instances of {@link BreakTree}.
         * <p>
         *  用于{@link BreakTree}的实例。
         * 
         */
        BREAK(BreakTree.class),

        /**
         * Used for instances of {@link CaseTree}.
         * <p>
         *  用于{@link CaseTree}的实例。
         * 
         */
        CASE(CaseTree.class),

        /**
         * Used for instances of {@link CatchTree}.
         * <p>
         *  用于{@link CatchTree}的实例。
         * 
         */
        CATCH(CatchTree.class),

        /**
         * Used for instances of {@link ClassTree} representing classes.
         * <p>
         *  用于表示类的{@link ClassTree}实例。
         * 
         */
        CLASS(ClassTree.class),

        /**
         * Used for instances of {@link CompilationUnitTree}.
         * <p>
         *  用于{@link编译单元树}的实例。
         * 
         */
        COMPILATION_UNIT(CompilationUnitTree.class),

        /**
         * Used for instances of {@link ConditionalExpressionTree}.
         * <p>
         *  用于{@link ConditionalExpressionTree}的实例。
         * 
         */
        CONDITIONAL_EXPRESSION(ConditionalExpressionTree.class),

        /**
         * Used for instances of {@link ContinueTree}.
         * <p>
         *  用于{@link ContinueTree}的实例。
         * 
         */
        CONTINUE(ContinueTree.class),

        /**
         * Used for instances of {@link DoWhileLoopTree}.
         * <p>
         *  用于{@link DoWhileLoopTree}的实例。
         * 
         */
        DO_WHILE_LOOP(DoWhileLoopTree.class),

        /**
         * Used for instances of {@link EnhancedForLoopTree}.
         * <p>
         *  适用于{@link EnhancedForLoopTree}的实例。
         * 
         */
        ENHANCED_FOR_LOOP(EnhancedForLoopTree.class),

        /**
         * Used for instances of {@link ExpressionStatementTree}.
         * <p>
         *  用于{@link ExpressionStatementTree}的实例。
         * 
         */
        EXPRESSION_STATEMENT(ExpressionStatementTree.class),

        /**
         * Used for instances of {@link MemberSelectTree}.
         * <p>
         *  用于{@link MemberSelectTree}的实例。
         * 
         */
        MEMBER_SELECT(MemberSelectTree.class),

        /**
         * Used for instances of {@link MemberReferenceTree}.
         * <p>
         *  用于{@link MemberReferenceTree}的实例。
         * 
         */
        MEMBER_REFERENCE(MemberReferenceTree.class),

        /**
         * Used for instances of {@link ForLoopTree}.
         * <p>
         * 用于{@link ForLoopTree}的实例。
         * 
         */
        FOR_LOOP(ForLoopTree.class),

        /**
         * Used for instances of {@link IdentifierTree}.
         * <p>
         *  用于{@link IdentifierTree}的实例。
         * 
         */
        IDENTIFIER(IdentifierTree.class),

        /**
         * Used for instances of {@link IfTree}.
         * <p>
         *  用于{@link IfTree}的实例。
         * 
         */
        IF(IfTree.class),

        /**
         * Used for instances of {@link ImportTree}.
         * <p>
         *  用于{@link ImportTree}的实例。
         * 
         */
        IMPORT(ImportTree.class),

        /**
         * Used for instances of {@link InstanceOfTree}.
         * <p>
         *  用于{@link InstanceOfTree}的实例。
         * 
         */
        INSTANCE_OF(InstanceOfTree.class),

        /**
         * Used for instances of {@link LabeledStatementTree}.
         * <p>
         *  用于{@link LabeledStatementTree}的实例。
         * 
         */
        LABELED_STATEMENT(LabeledStatementTree.class),

        /**
         * Used for instances of {@link MethodTree}.
         * <p>
         *  用于{@link MethodTree}的实例。
         * 
         */
        METHOD(MethodTree.class),

        /**
         * Used for instances of {@link MethodInvocationTree}.
         * <p>
         *  用于{@link MethodInvocationTree}的实例。
         * 
         */
        METHOD_INVOCATION(MethodInvocationTree.class),

        /**
         * Used for instances of {@link ModifiersTree}.
         * <p>
         *  用于{@link ModifiersTree}的实例。
         * 
         */
        MODIFIERS(ModifiersTree.class),

        /**
         * Used for instances of {@link NewArrayTree}.
         * <p>
         *  用于{@link NewArrayTree}的实例。
         * 
         */
        NEW_ARRAY(NewArrayTree.class),

        /**
         * Used for instances of {@link NewClassTree}.
         * <p>
         *  用于{@link NewClassTree}的实例。
         * 
         */
        NEW_CLASS(NewClassTree.class),

        /**
         * Used for instances of {@link LambdaExpressionTree}.
         * <p>
         *  用于{@link LambdaExpressionTree}的实例。
         * 
         */
        LAMBDA_EXPRESSION(LambdaExpressionTree.class),

        /**
         * Used for instances of {@link ParenthesizedTree}.
         * <p>
         *  用于{@link ParenthesizedTree}的实例。
         * 
         */
        PARENTHESIZED(ParenthesizedTree.class),

        /**
         * Used for instances of {@link PrimitiveTypeTree}.
         * <p>
         *  用于{@link PrimitiveTypeTree}的实例。
         * 
         */
        PRIMITIVE_TYPE(PrimitiveTypeTree.class),

        /**
         * Used for instances of {@link ReturnTree}.
         * <p>
         *  用于{@link ReturnTree}的实例。
         * 
         */
        RETURN(ReturnTree.class),

        /**
         * Used for instances of {@link EmptyStatementTree}.
         * <p>
         *  用于{@link EmptyStatementTree}的实例。
         * 
         */
        EMPTY_STATEMENT(EmptyStatementTree.class),

        /**
         * Used for instances of {@link SwitchTree}.
         * <p>
         *  用于{@link SwitchTree}的实例。
         * 
         */
        SWITCH(SwitchTree.class),

        /**
         * Used for instances of {@link SynchronizedTree}.
         * <p>
         *  用于{@link SynchronisedTree}的实例。
         * 
         */
        SYNCHRONIZED(SynchronizedTree.class),

        /**
         * Used for instances of {@link ThrowTree}.
         * <p>
         *  用于{@link ThrowTree}的实例。
         * 
         */
        THROW(ThrowTree.class),

        /**
         * Used for instances of {@link TryTree}.
         * <p>
         *  用于{@link TryTree}的实例。
         * 
         */
        TRY(TryTree.class),

        /**
         * Used for instances of {@link ParameterizedTypeTree}.
         * <p>
         *  用于{@link ParameterizedTypeTree}的实例。
         * 
         */
        PARAMETERIZED_TYPE(ParameterizedTypeTree.class),

        /**
         * Used for instances of {@link UnionTypeTree}.
         * <p>
         *  用于{@link UnionTypeTree}的实例。
         * 
         */
        UNION_TYPE(UnionTypeTree.class),

        /**
         * Used for instances of {@link IntersectionTypeTree}.
         * <p>
         *  用于{@link IntersectionTypeTree}的实例。
         * 
         */
        INTERSECTION_TYPE(IntersectionTypeTree.class),

        /**
         * Used for instances of {@link TypeCastTree}.
         * <p>
         *  用于{@link TypeCastTree}的实例。
         * 
         */
        TYPE_CAST(TypeCastTree.class),

        /**
         * Used for instances of {@link TypeParameterTree}.
         * <p>
         *  用于{@link TypeParameterTree}的实例。
         * 
         */
        TYPE_PARAMETER(TypeParameterTree.class),

        /**
         * Used for instances of {@link VariableTree}.
         * <p>
         *  用于{@link VariableTree}的实例。
         * 
         */
        VARIABLE(VariableTree.class),

        /**
         * Used for instances of {@link WhileLoopTree}.
         * <p>
         *  用于{@link WhileLoopTree}的实例。
         * 
         */
        WHILE_LOOP(WhileLoopTree.class),

        /**
         * Used for instances of {@link UnaryTree} representing postfix
         * increment operator {@code ++}.
         * <p>
         *  用于代表后缀增量运算符{@code ++}的{@link UnaryTree}实例。
         * 
         */
        POSTFIX_INCREMENT(UnaryTree.class),

        /**
         * Used for instances of {@link UnaryTree} representing postfix
         * decrement operator {@code --}.
         * <p>
         *  用于代表后缀递减运算符{@code  - }的{@link UnaryTree}实例。
         * 
         */
        POSTFIX_DECREMENT(UnaryTree.class),

        /**
         * Used for instances of {@link UnaryTree} representing prefix
         * increment operator {@code ++}.
         * <p>
         * 用于表示前缀增量运算符{@code ++}的{@link UnaryTree}实例。
         * 
         */
        PREFIX_INCREMENT(UnaryTree.class),

        /**
         * Used for instances of {@link UnaryTree} representing prefix
         * decrement operator {@code --}.
         * <p>
         *  用于表示前缀递减运算符{@code  - }的{@link UnaryTree}实例。
         * 
         */
        PREFIX_DECREMENT(UnaryTree.class),

        /**
         * Used for instances of {@link UnaryTree} representing unary plus
         * operator {@code +}.
         * <p>
         *  用于表示一元加运算符{@code +}的{@link UnaryTree}实例。
         * 
         */
        UNARY_PLUS(UnaryTree.class),

        /**
         * Used for instances of {@link UnaryTree} representing unary minus
         * operator {@code -}.
         * <p>
         *  用于表示一元减运算符{@code  - }的{@link UnaryTree}实例。
         * 
         */
        UNARY_MINUS(UnaryTree.class),

        /**
         * Used for instances of {@link UnaryTree} representing bitwise
         * complement operator {@code ~}.
         * <p>
         *  用于表示按位补码运算符{@code〜}的{@link UnaryTree}实例。
         * 
         */
        BITWISE_COMPLEMENT(UnaryTree.class),

        /**
         * Used for instances of {@link UnaryTree} representing logical
         * complement operator {@code !}.
         * <p>
         *  用于表示逻辑补运算符{@code！}的{@link UnaryTree}实例。
         * 
         */
        LOGICAL_COMPLEMENT(UnaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * multiplication {@code *}.
         * <p>
         *  用于表示乘法{@code *}的{@link BinaryTree}实例。
         * 
         */
        MULTIPLY(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * division {@code /}.
         * <p>
         *  用于代表{@code /}的{@link BinaryTree}实例。
         * 
         */
        DIVIDE(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * remainder {@code %}.
         * <p>
         *  用于表示{@code％}的{@link BinaryTree}实例。
         * 
         */
        REMAINDER(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * addition or string concatenation {@code +}.
         * <p>
         *  用于表示加法或字符串连接{@code +}的{@link BinaryTree}实例。
         * 
         */
        PLUS(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * subtraction {@code -}.
         * <p>
         *  用于{@link BinaryTree}表示减法{@code  - }的实例。
         * 
         */
        MINUS(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * left shift {@code <<}.
         * <p>
         *  用于表示左移{@code <<}的{@link BinaryTree}实例。
         * 
         */
        LEFT_SHIFT(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * right shift {@code >>}.
         * <p>
         *  用于{@link BinaryTree}表示右移{@code >>}的实例。
         * 
         */
        RIGHT_SHIFT(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * unsigned right shift {@code >>>}.
         * <p>
         *  用于表示无符号右移{@code >>>}的{@link BinaryTree}实例。
         * 
         */
        UNSIGNED_RIGHT_SHIFT(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * less-than {@code <}.
         * <p>
         *  用于代表小于{@code <}的{@link BinaryTree}的实例。
         * 
         */
        LESS_THAN(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * greater-than {@code >}.
         * <p>
         *  用于代表大于{@code>}的{@link BinaryTree}的实例。
         * 
         */
        GREATER_THAN(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * less-than-equal {@code <=}.
         * <p>
         *  用于代表小于等于{@code <=}的{@link BinaryTree}的实例。
         * 
         */
        LESS_THAN_EQUAL(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * greater-than-equal {@code >=}.
         * <p>
         * 用于表示大于等于{@code> =}的{@link BinaryTree}实例。
         * 
         */
        GREATER_THAN_EQUAL(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * equal-to {@code ==}.
         * <p>
         *  用于表示等于{@code ==}的{@link BinaryTree}的实例。
         * 
         */
        EQUAL_TO(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * not-equal-to {@code !=}.
         * <p>
         *  用于表示不等于{@code！=}的{@link BinaryTree}实例。
         * 
         */
        NOT_EQUAL_TO(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * bitwise and logical "and" {@code &}.
         * <p>
         *  用于表示按位和逻辑"和"{@code&}的{@link BinaryTree}实例。
         * 
         */
        AND(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * bitwise and logical "xor" {@code ^}.
         * <p>
         *  用于表示按位和逻辑"xor"{@code ^}的{@link BinaryTree}实例。
         * 
         */
        XOR(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * bitwise and logical "or" {@code |}.
         * <p>
         *  用于表示按位和逻辑"或"{@code |}的{@link BinaryTree}实例。
         * 
         */
        OR(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * conditional-and {@code &&}.
         * <p>
         *  用于表示条件和{@code &&}的{@link BinaryTree}实例。
         * 
         */
        CONDITIONAL_AND(BinaryTree.class),

        /**
         * Used for instances of {@link BinaryTree} representing
         * conditional-or {@code ||}.
         * <p>
         *  用于表示条件或{@code ||}的{@link BinaryTree}实例。
         * 
         */
        CONDITIONAL_OR(BinaryTree.class),

        /**
         * Used for instances of {@link CompoundAssignmentTree} representing
         * multiplication assignment {@code *=}.
         * <p>
         *  用于表示乘法分配{@code * =}的{@link CompoundAssignmentTree}实例。
         * 
         */
        MULTIPLY_ASSIGNMENT(CompoundAssignmentTree.class),

        /**
         * Used for instances of {@link CompoundAssignmentTree} representing
         * division assignment {@code /=}.
         * <p>
         *  用于表示分配分配{@code / =}的{@link CompoundAssignmentTree}实例。
         * 
         */
        DIVIDE_ASSIGNMENT(CompoundAssignmentTree.class),

        /**
         * Used for instances of {@link CompoundAssignmentTree} representing
         * remainder assignment {@code %=}.
         * <p>
         *  用于表示余数分配{@code％=}的{@link CompoundAssignmentTree}实例。
         * 
         */
        REMAINDER_ASSIGNMENT(CompoundAssignmentTree.class),

        /**
         * Used for instances of {@link CompoundAssignmentTree} representing
         * addition or string concatenation assignment {@code +=}.
         * <p>
         *  用于表示添加或字符串连接分配{@code + =}的{@link CompoundAssignmentTree}实例。
         * 
         */
        PLUS_ASSIGNMENT(CompoundAssignmentTree.class),

        /**
         * Used for instances of {@link CompoundAssignmentTree} representing
         * subtraction assignment {@code -=}.
         * <p>
         *  用于表示减法赋值{@code  -  =}的{@link CompoundAssignmentTree}实例。
         * 
         */
        MINUS_ASSIGNMENT(CompoundAssignmentTree.class),

        /**
         * Used for instances of {@link CompoundAssignmentTree} representing
         * left shift assignment {@code <<=}.
         * <p>
         *  用于表示左移分配{@code << =}的{@link CompoundAssignmentTree}实例。
         * 
         */
        LEFT_SHIFT_ASSIGNMENT(CompoundAssignmentTree.class),

        /**
         * Used for instances of {@link CompoundAssignmentTree} representing
         * right shift assignment {@code >>=}.
         * <p>
         *  用于表示右移赋值{@code >> =}的{@link CompoundAssignmentTree}实例。
         * 
         */
        RIGHT_SHIFT_ASSIGNMENT(CompoundAssignmentTree.class),

        /**
         * Used for instances of {@link CompoundAssignmentTree} representing
         * unsigned right shift assignment {@code >>>=}.
         * <p>
         * 用于表示无符号右移赋值{@code >>> =}的{@link CompoundAssignmentTree}实例。
         * 
         */
        UNSIGNED_RIGHT_SHIFT_ASSIGNMENT(CompoundAssignmentTree.class),

        /**
         * Used for instances of {@link CompoundAssignmentTree} representing
         * bitwise and logical "and" assignment {@code &=}.
         * <p>
         *  用于表示按位和逻辑"和"分配{@code&=}的{@link CompoundAssignmentTree}实例。
         * 
         */
        AND_ASSIGNMENT(CompoundAssignmentTree.class),

        /**
         * Used for instances of {@link CompoundAssignmentTree} representing
         * bitwise and logical "xor" assignment {@code ^=}.
         * <p>
         *  用于表示按位和逻辑"xor"分配{@code ^ =}的{@link CompoundAssignmentTree}实例。
         * 
         */
        XOR_ASSIGNMENT(CompoundAssignmentTree.class),

        /**
         * Used for instances of {@link CompoundAssignmentTree} representing
         * bitwise and logical "or" assignment {@code |=}.
         * <p>
         *  用于表示按位和逻辑"或"分配{@code | =}的{@link CompoundAssignmentTree}实例。
         * 
         */
        OR_ASSIGNMENT(CompoundAssignmentTree.class),

        /**
         * Used for instances of {@link LiteralTree} representing
         * an integral literal expression of type {@code int}.
         * <p>
         *  用于表示类型为{@code int}的整数文本表达式的{@link LiteralTree}实例。
         * 
         */
        INT_LITERAL(LiteralTree.class),

        /**
         * Used for instances of {@link LiteralTree} representing
         * an integral literal expression of type {@code long}.
         * <p>
         *  用于表示类型为{@code long}的整数文字表达式的{@link LiteralTree}实例。
         * 
         */
        LONG_LITERAL(LiteralTree.class),

        /**
         * Used for instances of {@link LiteralTree} representing
         * a floating-point literal expression of type {@code float}.
         * <p>
         *  用于表示类型为{@code float}的浮点文字表达式的{@link LiteralTree}实例。
         * 
         */
        FLOAT_LITERAL(LiteralTree.class),

        /**
         * Used for instances of {@link LiteralTree} representing
         * a floating-point literal expression of type {@code double}.
         * <p>
         *  用于表示类型为{@code double}的浮点文字表达式的{@link LiteralTree}实例。
         * 
         */
        DOUBLE_LITERAL(LiteralTree.class),

        /**
         * Used for instances of {@link LiteralTree} representing
         * a boolean literal expression of type {@code boolean}.
         * <p>
         *  用于表示{@code boolean}类型的布尔文字表达式的{@link LiteralTree}实例。
         * 
         */
        BOOLEAN_LITERAL(LiteralTree.class),

        /**
         * Used for instances of {@link LiteralTree} representing
         * a character literal expression of type {@code char}.
         * <p>
         *  用于表示{@code char}类型的字符文字表达式的{@link LiteralTree}实例。
         * 
         */
        CHAR_LITERAL(LiteralTree.class),

        /**
         * Used for instances of {@link LiteralTree} representing
         * a string literal expression of type {@link String}.
         * <p>
         *  用于表示类型为{@link String}的字符串字面表达式的{@link LiteralTree}实例。
         * 
         */
        STRING_LITERAL(LiteralTree.class),

        /**
         * Used for instances of {@link LiteralTree} representing
         * the use of {@code null}.
         * <p>
         *  用于表示{@code null}的使用的{@link LiteralTree}实例。
         * 
         */
        NULL_LITERAL(LiteralTree.class),

        /**
         * Used for instances of {@link WildcardTree} representing
         * an unbounded wildcard type argument.
         * <p>
         *  用于表示无限通配符类型参数的{@link WildcardTree}实例。
         * 
         */
        UNBOUNDED_WILDCARD(WildcardTree.class),

        /**
         * Used for instances of {@link WildcardTree} representing
         * an extends bounded wildcard type argument.
         * <p>
         * 用于表示扩展有界通配符类型参数的{@link WildcardTree}实例。
         * 
         */
        EXTENDS_WILDCARD(WildcardTree.class),

        /**
         * Used for instances of {@link WildcardTree} representing
         * a super bounded wildcard type argument.
         * <p>
         *  用于表示超级通配符类型参数的{@link WildcardTree}实例。
         * 
         */
        SUPER_WILDCARD(WildcardTree.class),

        /**
         * Used for instances of {@link ErroneousTree}.
         * <p>
         *  用于{@link ErroneousTree}的实例。
         * 
         */
        ERRONEOUS(ErroneousTree.class),

        /**
         * Used for instances of {@link ClassTree} representing interfaces.
         * <p>
         *  用于表示接口的{@link ClassTree}实例。
         * 
         */
        INTERFACE(ClassTree.class),

        /**
         * Used for instances of {@link ClassTree} representing enums.
         * <p>
         *  用于表示枚举的{@link ClassTree}实例。
         * 
         */
        ENUM(ClassTree.class),

        /**
         * Used for instances of {@link ClassTree} representing annotation types.
         * <p>
         *  用于表示注释类型的{@link ClassTree}实例。
         * 
         */
        ANNOTATION_TYPE(ClassTree.class),

        /**
         * An implementation-reserved node. This is the not the node
         * you are looking for.
         * <p>
         *  实现保留节点。这不是你要找的节点。
         * 
         */
        OTHER(null);


        Kind(Class<? extends Tree> intf) {
            associatedInterface = intf;
        }

        public Class<? extends Tree> asInterface() {
            return associatedInterface;
        }

        private final Class<? extends Tree> associatedInterface;
    }

    /**
     * Gets the kind of this tree.
     *
     * <p>
     *  获得这种树的种类。
     * 
     * 
     * @return the kind of this tree.
     */
    Kind getKind();

    /**
     * Accept method used to implement the visitor pattern.  The
     * visitor pattern is used to implement operations on trees.
     *
     * <p>
     *  用于实现访问者模式的Accept方法。访问者模式用于对树执行操作。
     * 
     * @param <R> result type of this operation.
     * @param <D> type of additional data.
     */
    <R,D> R accept(TreeVisitor<R,D> visitor, D data);
}
