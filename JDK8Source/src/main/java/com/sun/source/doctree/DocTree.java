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

package com.sun.source.doctree;

/**
 * Common interface for all nodes in a documentation syntax tree.
 *
 * <p>
 *  文档语法树中所有节点的通用接口。
 * 
 * 
 * @since 1.8
 */
@jdk.Exported
public interface DocTree {
    @jdk.Exported
    enum Kind {
        /**
         * Used for instances of {@link AttributeTree}
         * representing an HTML attribute.
         * <p>
         *  用于表示HTML属性的{@link AttributeTree}实例。
         * 
         */
        ATTRIBUTE,

        /**
         * Used for instances of {@link AuthorTree}
         * <p>
         *  用于{@link AuthorTree}的实例
         * 
         * 
         * representing an @author tag.
         */
        AUTHOR("author"),

        /**
         * Used for instances of {@link LiteralTree}
         * representing an @code tag.
         * <p>
         *  用于表示@code标签的{@link LiteralTree}实例。
         * 
         */
        CODE("code"),

        /**
         * Used for instances of {@link CommentTree}
         * representing an HTML comment.
         * <p>
         *  用于表示HTML注释的{@link CommentTree}实例。
         * 
         */
        COMMENT,

        /**
         * Used for instances of {@link DeprecatedTree}
         * <p>
         *  用于{@link DeprecatedTree}的实例
         * 
         * 
         * representing an @deprecated tag.
         */
        DEPRECATED("deprecated"),

        /**
         * Used for instances of {@link DocCommentTree}
         * representing a complete doc comment.
         * <p>
         *  用于表示完整文档评论的{@link DocCommentTree}实例。
         * 
         */
        DOC_COMMENT,

        /**
         * Used for instances of {@link DocRootTree}
         * representing an @docRoot tag.
         * <p>
         *  用于表示@docRoot标记的{@link DocRootTree}实例。
         * 
         */
        DOC_ROOT("docRoot"),

        /**
         * Used for instances of {@link EndElementTree}
         * representing the end of an HTML element.
         * <p>
         *  用于表示HTML元素结尾的{@link EndElementTree}实例。
         * 
         */
        END_ELEMENT,

        /**
         * Used for instances of {@link EntityTree}
         * representing an HTML entity.
         * <p>
         *  用于表示HTML实体的{@link EntityTree}实例。
         * 
         */
        ENTITY,

        /**
         * Used for instances of {@link ErroneousTree}
         * representing some invalid text.
         * <p>
         *  用于表示某些无效文本的{@link ErroneousTree}实例。
         * 
         */
        ERRONEOUS,

        /**
         * Used for instances of {@link ThrowsTree}
         * <p>
         *  用于{@link ThrowsTree}的实例
         * 
         * 
         * representing an @exception tag.
         */
        EXCEPTION("exception"),

        /**
         * Used for instances of {@link IdentifierTree}
         * representing an identifier.
         * <p>
         *  用于表示标识符的{@link IdentifierTree}实例。
         * 
         */
        IDENTIFIER,

        /**
         * Used for instances of {@link InheritDocTree}
         * representing an @inheritDoc tag.
         * <p>
         *  用于表示@inheritDoc标记的{@link InheritDocTree}实例。
         * 
         */
        INHERIT_DOC("inheritDoc"),

        /**
         * Used for instances of {@link LinkTree}
         * representing an @link tag.
         * <p>
         *  用于表示@link标记的{@link LinkTree}实例。
         * 
         */
        LINK("link"),

        /**
         * Used for instances of {@link LinkTree}
         * representing an @linkplain tag.
         * <p>
         *  用于表示@linkplain标记的{@link LinkTree}实例。
         * 
         */
        LINK_PLAIN("linkplain"),

        /**
         * Used for instances of {@link LiteralTree}
         * representing an @literal tag.
         * <p>
         *  用于表示@literal标记的{@link LiteralTree}实例。
         * 
         */
        LITERAL("literal"),

        /**
         * Used for instances of {@link ParamTree}
         * <p>
         *  用于{@link ParamTree}的实例
         * 
         * 
         * representing an @param tag.
         */
        PARAM("param"),

        /**
         * Used for instances of {@link ReferenceTree}
         * representing a reference to a element in the
         * Java programming language.
         * <p>
         *  用于表示对Java编程语言中元素的引用的{@link ReferenceTree}实例。
         * 
         */
        REFERENCE,

        /**
         * Used for instances of {@link ReturnTree}
         * <p>
         * 用于{@link ReturnTree}的实例
         * 
         * 
         * representing an @return tag.
         */
        RETURN("return"),

        /**
         * Used for instances of {@link SeeTree}
         * <p>
         *  用于{@link SeeTree}
         * 
         * 
         * representing an @see tag.
         */
        SEE("see"),

        /**
         * Used for instances of {@link SerialTree}
         * <p>
         *  用于{@link SerialTree}的实例
         * 
         * 
         * representing an @serial tag.
         */
        SERIAL("serial"),

        /**
         * Used for instances of {@link SerialDataTree}
         * <p>
         *  用于{@link SerialDataTree}的实例
         * 
         * 
         * representing an @serialData tag.
         */
        SERIAL_DATA("serialData"),

        /**
         * Used for instances of {@link SerialFieldTree}
         * <p>
         *  用于{@link SerialFieldTree}的实例
         * 
         * 
         * representing an @serialField tag.
         */
        SERIAL_FIELD("serialField"),

        /**
         * Used for instances of {@link SinceTree}
         * <p>
         *  用于{@link SinceTree}的实例
         * 
         * 
         * representing an @since tag.
         */
        SINCE("since"),

        /**
         * Used for instances of {@link EndElementTree}
         * representing the start of an HTML element.
         * <p>
         *  用于表示HTML元素开始的{@link EndElementTree}实例。
         * 
         */
        START_ELEMENT,

        /**
         * Used for instances of {@link TextTree}
         * representing some documentation text.
         * <p>
         *  用于表示某些文档文本的{@link TextTree}实例。
         * 
         */
        TEXT,

        /**
         * Used for instances of {@link ThrowsTree}
         * <p>
         *  用于{@link ThrowsTree}的实例
         * 
         * 
         * representing an @throws tag.
         */
        THROWS("throws"),

        /**
         * Used for instances of {@link UnknownBlockTagTree}
         * representing an unknown block tag.
         * <p>
         *  用于表示未知块标记的{@link UnknownBlockTagTree}实例。
         * 
         */
        UNKNOWN_BLOCK_TAG,

        /**
         * Used for instances of {@link UnknownInlineTagTree}
         * representing an unknown inline tag.
         * <p>
         *  用于表示未知内联标记的{@link UnknownInlineTagTree}实例。
         * 
         */
        UNKNOWN_INLINE_TAG,

        /**
         * Used for instances of {@link ValueTree}
         * representing an @value tag.
         * <p>
         *  用于表示@value标记的{@link ValueTree}实例。
         * 
         */
        VALUE("value"),

        /**
         * Used for instances of {@link VersionTree}
         * <p>
         *  用于{@link VersionTree}的实例
         * 
         * 
         * representing an @version tag.
         */
        VERSION("version"),

        /**
         * An implementation-reserved node. This is the not the node
         * you are looking for.
         * <p>
         *  实现保留节点。这不是你要找的节点。
         * 
         */
        OTHER;

        public final String tagName;

        Kind() {
            tagName = null;
        }

        Kind(String tagName) {
            this.tagName = tagName;
        }
    };

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
    <R, D> R accept(DocTreeVisitor<R,D> visitor, D data);
}
