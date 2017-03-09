/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2011, 2013, Oracle and/or its affiliates. All rights reserved.
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

package com.sun.source.util;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.JavaCompiler.CompilationTask;

import com.sun.source.doctree.DocCommentTree;
import javax.tools.Diagnostic;

/**
 * Provides access to syntax trees for doc comments.
 *
 * <p>
 *  提供对文档注释的语法树的访问。
 * 
 * 
 * @since 1.8
 */
@jdk.Exported
public abstract class DocTrees extends Trees {
    /**
     * Gets a DocTrees object for a given CompilationTask.
     * <p>
     *  获取给定CompilationTask的DocTrees对象。
     * 
     * 
     * @param task the compilation task for which to get the Trees object
     * @throws IllegalArgumentException if the task does not support the Trees API.
     */
    public static DocTrees instance(CompilationTask task) {
        return (DocTrees) Trees.instance(task);
    }

    /**
     * Gets a DocTrees object for a given ProcessingEnvironment.
     * <p>
     *  获取给定ProcessingEnvironment的DocTrees对象。
     * 
     * 
     * @param env the processing environment for which to get the Trees object
     * @throws IllegalArgumentException if the env does not support the Trees API.
     */
    public static DocTrees instance(ProcessingEnvironment env) {
        if (!env.getClass().getName().equals("com.sun.tools.javac.processing.JavacProcessingEnvironment"))
            throw new IllegalArgumentException();
        return (DocTrees) getJavacTrees(ProcessingEnvironment.class, env);
    }

    /**
     * Gets the doc comment tree, if any, for the Tree node identified by a given TreePath.
     * Returns null if no doc comment was found.
     * <p>
     *  获取由给定TreePath标识的Tree节点的文档注释树(如果有)。如果未找到doc注释,则返回null。
     * 
     */
    public abstract DocCommentTree getDocCommentTree(TreePath path);

    /**
     * Gets the language model element referred to by the leaf node of the given
     * {@link DocTreePath}, or null if unknown.
     * <p>
     *  获取给定{@link DocTreePath}的叶节点引用的语言模型元素,如果未知,则返回null。
     * 
     */
    public abstract Element getElement(DocTreePath path);

    public abstract DocSourcePositions getSourcePositions();

    /**
     * Prints a message of the specified kind at the location of the
     * tree within the provided compilation unit
     *
     * <p>
     *  在提供的编译单元内的树的位置打印指定类型的消息
     * 
     * @param kind the kind of message
     * @param msg  the message, or an empty string if none
     * @param t    the tree to use as a position hint
     * @param root the compilation unit that contains tree
     */
    public abstract void printMessage(Diagnostic.Kind kind, CharSequence msg,
            com.sun.source.doctree.DocTree t,
            com.sun.source.doctree.DocCommentTree c,
            com.sun.source.tree.CompilationUnitTree root);
}
