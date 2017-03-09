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

package com.sun.source.util;

import com.sun.source.tree.*;

/**
 * Provides methods to obtain the position of a Tree within a CompilationUnit.
 * A position is defined as a simple character offset from the start of a
 * CompilationUnit where the first character is at offset 0.
 *
 * <p>
 *  提供在CompilationUnit中获取树的位置的方法。位置被定义为从第一个字符位于偏移0处的CompilationUnit的开始的简单字符偏移。
 * 
 * 
 * @author Peter von der Ah&eacute;
 * @since 1.6
 */
@jdk.Exported
public interface SourcePositions {

    /**
     * Gets the starting position of tree within file.  If tree is not found within
     * file, or if the starting position is not available,
     * return {@link javax.tools.Diagnostic#NOPOS}.
     * The returned position must be at the start of the yield of this tree, that
     * is for any sub-tree of this tree, the following must hold:
     *
     * <p>
     * {@code tree.getStartPosition() <= subtree.getStartPosition()} or <br>
     * {@code tree.getStartPosition() == NOPOS} or <br>
     * {@code subtree.getStartPosition() == NOPOS}
     * </p>
     *
     * <p>
     *  获取文件中树的起始位置。如果在文件中找不到tree,或者如果起始位置不可用,则返回{@link javax.tools.Diagnostic#NOPOS}。
     * 返回的位置必须位于此树的yield的开始处,即该树的任何子树,以下内容必须包含：。
     * 
     * <p>
     *  {@code tree.getStartPosition()<= subtree.getStartPosition()}或{@code tree.getStartPosition()== NOPOS}或<br>
     *  {@code subtree.getStartPosition()== NOPOS}。
     * </p>
     * 
     * 
     * @param file CompilationUnit in which to find tree.
     * @param tree tree for which a position is sought.
     * @return the start position of tree.
     */
     long getStartPosition(CompilationUnitTree file, Tree tree);

    /**
     * Gets the ending position of tree within file.  If tree is not found within
     * file, or if the ending position is not available,
     * return {@link javax.tools.Diagnostic#NOPOS}.
     * The returned position must be at the end of the yield of this tree,
     * that is for any sub-tree of this tree, the following must hold:
     *
     * <p>
     * {@code tree.getEndPosition() >= subtree.getEndPosition()} or <br>
     * {@code tree.getEndPosition() == NOPOS} or <br>
     * {@code subtree.getEndPosition() == NOPOS}
     * </p>
     *
     * In addition, the following must hold:
     *
     * <p>
     * {@code tree.getStartPosition() <= tree.getEndPosition()}  or <br>
     * {@code tree.getStartPosition() == NOPOS} or <br>
     * {@code tree.getEndPosition() == NOPOS}
     * </p>
     *
     * <p>
     *  获取文件中树的结束位置。如果在文件中找不到tree,或者如果结束位置不可用,则返回{@link javax.tools.Diagnostic#NOPOS}。
     * 返回的位置必须位于此树的yield的末尾,即此树的任何子树,以下内容必须包含：。
     * 
     * <p>
     *  {@code tree.getEndPosition()> = subtree.getEndPosition()}或<br> {@code tree.getEndPosition()== NOPOS}
     * 或<br> {@code subtree.getEndPosition()== NOPOS}。
     * </p>
     * 
     * 
     * @param file CompilationUnit in which to find tree.
     * @param tree tree for which a position is sought.
     * @return the end position of tree.
     */
     long getEndPosition(CompilationUnitTree file, Tree tree);

}
