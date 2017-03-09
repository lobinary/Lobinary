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

import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import com.sun.source.tree.CompilationUnitTree;

/**
 * Provides details about work that has been done by the JDK Java Compiler, javac.
 *
 * <p>
 *  提供JDK Java编译器javac所做的工作的详细信息。
 * 
 * 
 * @author Jonathan Gibbons
 * @since 1.6
 */
@jdk.Exported
public final class TaskEvent
{
    /**
     * Kind of task event.
     * <p>
     *  种类的任务事件。
     * 
     * 
     * @since 1.6
     */
    @jdk.Exported
    public enum Kind {
        /**
         * For events related to the parsing of a file.
         * <p>
         *  对于与解析文件相关的事件。
         * 
         */
        PARSE,
        /**
         * For events relating to elements being entered.
         * <p>
         *  对于与正在输入的元素相关的事件。
         * 
         * 
         **/
        ENTER,
        /**
         * For events relating to elements being analyzed for errors.
         * <p>
         *  对于与正在分析错误的元素相关的事件。
         * 
         * 
         **/
        ANALYZE,
        /**
         * For events relating to class files being generated.
         * <p>
         *  对于与生成的类文件相关的事件。
         * 
         * 
         **/
        GENERATE,
        /**
         * For events relating to overall annotation processing.
         * <p>
         *  对于与整体注释处理相关的事件。
         * 
         * 
         **/
        ANNOTATION_PROCESSING,
        /**
         * For events relating to an individual annotation processing round.
         * <p>
         *  对于与单个注释处理轮次相关的事件。
         * 
         **/
        ANNOTATION_PROCESSING_ROUND
    };

    public TaskEvent(Kind kind) {
        this(kind, null, null, null);
    }

    public TaskEvent(Kind kind, JavaFileObject sourceFile) {
        this(kind, sourceFile, null, null);
    }

    public TaskEvent(Kind kind, CompilationUnitTree unit) {
        this(kind, unit.getSourceFile(), unit, null);
    }

    public TaskEvent(Kind kind, CompilationUnitTree unit, TypeElement clazz) {
        this(kind, unit.getSourceFile(), unit, clazz);
    }

    private TaskEvent(Kind kind, JavaFileObject file, CompilationUnitTree unit, TypeElement clazz) {
        this.kind = kind;
        this.file = file;
        this.unit = unit;
        this.clazz = clazz;
    }

    public Kind getKind() {
        return kind;
    }

    public JavaFileObject getSourceFile() {
        return file;
    }

    public CompilationUnitTree getCompilationUnit() {
        return unit;
    }

    public TypeElement getTypeElement() {
        return clazz;
    }

    public String toString() {
        return "TaskEvent["
            + kind + ","
            + file + ","
            // the compilation unit is identified by the file
            + clazz + "]";
    }

    private Kind kind;
    private JavaFileObject file;
    private CompilationUnitTree unit;
    private TypeElement clazz;
}
