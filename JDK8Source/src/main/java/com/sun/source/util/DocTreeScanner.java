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

import com.sun.source.doctree.*;


/**
 * A TreeVisitor that visits all the child tree nodes.
 * To visit nodes of a particular type, just override the
 * corresponding visitXYZ method.
 * Inside your method, call super.visitXYZ to visit descendant
 * nodes.
 *
 * <p>The default implementation of the visitXYZ methods will determine
 * a result as follows:
 * <ul>
 * <li>If the node being visited has no children, the result will be null.
 * <li>If the node being visited has one child, the result will be the
 * result of calling {@code scan} on that child. The child may be a simple node
 * or itself a list of nodes.
 * <li> If the node being visited has more than one child, the result will
 * be determined by calling {@code scan} each child in turn, and then combining the
 * result of each scan after the first with the cumulative result
 * so far, as determined by the {@link #reduce} method. Each child may be either
 * a simple node of a list of nodes. The default behavior of the {@code reduce}
 * method is such that the result of the visitXYZ method will be the result of
 * the last child scanned.
 * </ul>
 *
 * <p>Here is an example to count the number of erroneous nodes in a tree:
 * <pre>
 *   class CountErrors extends DocTreeScanner&lt;Integer,Void&gt; {
 *      {@literal @}Override
 *      public Integer visitErroneous(ErroneousTree node, Void p) {
 *          return 1;
 *      }
 *      {@literal @}Override
 *      public Integer reduce(Integer r1, Integer r2) {
 *          return (r1 == null ? 0 : r1) + (r2 == null ? 0 : r2);
 *      }
 *   }
 * </pre>
 *
 * <p>
 *  访问所有子树节点的TreeVisitor。要访问特定类型的节点,只需覆盖相应的visitXYZ方法。在你的方法中,调用super.visitXYZ访问后代节点。
 * 
 *  <p> visitXYZ方法的默认实现将确定结果如下：
 * <ul>
 *  <li>如果被访问的节点没有子节点,则结果将为null。 <li>如果被访问的节点有一个子节点,则结果将是对该子节点调用{@code scan}的结果。子节点可以是简单节点,也可以是节点列表。
 *  <li>如果被访问的节点有多个子节点,则结果将通过依次调用{@code scan}每个子节点,然后将第一次之后的每个扫描的结果与迄今为止的累积结果组合,由{@link #reduce}方法确定。
 * 每个子节点可以是节点列表的简单节点。 {@code reduce}方法的默认行为是visitXYZ方法的结果是最后一个子节点扫描的结果。
 * </ul>
 * 
 *  <p>以下是计算树中错误节点数的示例：
 * <pre>
 * class CountErrors extends DocTreeScanner&lt; Integer,Void&gt; {{@literal @}覆盖public Integer visitErro
 * neous(ErroneousTree节点,Void p){return 1; } {@literal @}覆盖public Integer reduce(Integer r1,Integer r2){return(r1 == null?0：r1)+(r2 == null?0：r2); }
 * }。
 * 
 * @since 1.8
 */
@jdk.Exported
public class DocTreeScanner<R,P> implements DocTreeVisitor<R,P> {

    /**
     * Scan a single node.
     * <p>
     * </pre>
     * 
     */
    public R scan(DocTree node, P p) {
        return (node == null) ? null : node.accept(this, p);
    }

    private R scanAndReduce(DocTree node, P p, R r) {
        return reduce(scan(node, p), r);
    }

    /**
     * Scan a list of nodes.
     * <p>
     *  扫描单个节点。
     * 
     */
    public R scan(Iterable<? extends DocTree> nodes, P p) {
        R r = null;
        if (nodes != null) {
            boolean first = true;
            for (DocTree node : nodes) {
                r = (first ? scan(node, p) : scanAndReduce(node, p, r));
                first = false;
            }
        }
        return r;
    }

    private R scanAndReduce(Iterable<? extends DocTree> nodes, P p, R r) {
        return reduce(scan(nodes, p), r);
    }

    /**
     * Reduces two results into a combined result.
     * The default implementation is to return the first parameter.
     * The general contract of the method is that it may take any action whatsoever.
     * <p>
     *  扫描节点列表。
     * 
     */
    public R reduce(R r1, R r2) {
        return r1;
    }


/* ***************************************************************************
 * Visitor methods
 * <p>
 *  将两个结果减少为组合结果。默认实现是返回第一个参数。该方法的一般合同是,它可以采取任何行动。
 * 
 * 
 ****************************************************************************/

    @Override
    public R visitAttribute(AttributeTree node, P p) {
        return null;
    }

    @Override
    public R visitAuthor(AuthorTree node, P p) {
        return scan(node.getName(), p);
    }

    @Override
    public R visitComment(CommentTree node, P p) {
        return null;
    }

    @Override
    public R visitDeprecated(DeprecatedTree node, P p) {
        return scan(node.getBody(), p);
    }

    @Override
    public R visitDocComment(DocCommentTree node, P p) {
        R r = scan(node.getFirstSentence(), p);
        r = scanAndReduce(node.getBody(), p, r);
        r = scanAndReduce(node.getBlockTags(), p, r);
        return r;
    }

    @Override
    public R visitDocRoot(DocRootTree node, P p) {
        return null;
    }

    @Override
    public R visitEndElement(EndElementTree node, P p) {
        return null;
    }

    @Override
    public R visitEntity(EntityTree node, P p) {
        return null;
    }

    @Override
    public R visitErroneous(ErroneousTree node, P p) {
        return null;
    }

    @Override
    public R visitIdentifier(IdentifierTree node, P p) {
        return null;
    }

    @Override
    public R visitInheritDoc(InheritDocTree node, P p) {
        return null;
    }

    @Override
    public R visitLink(LinkTree node, P p) {
        R r = scan(node.getReference(), p);
        r = scanAndReduce(node.getLabel(), p, r);
        return r;
    }

    @Override
    public R visitLiteral(LiteralTree node, P p) {
        return null;
    }

    @Override
    public R visitParam(ParamTree node, P p) {
        R r = scan(node.getName(), p);
        r = scanAndReduce(node.getDescription(), p, r);
        return r;
    }

    @Override
    public R visitReference(ReferenceTree node, P p) {
        return null;
    }

    @Override
    public R visitReturn(ReturnTree node, P p) {
        return scan(node.getDescription(), p);
    }

    @Override
    public R visitSee(SeeTree node, P p) {
        return scan(node.getReference(), p);
    }

    @Override
    public R visitSerial(SerialTree node, P p) {
        return scan(node.getDescription(), p);
    }

    @Override
    public R visitSerialData(SerialDataTree node, P p) {
        return scan(node.getDescription(), p);
    }

    @Override
    public R visitSerialField(SerialFieldTree node, P p) {
        R r = scan(node.getName(), p);
        r = scanAndReduce(node.getType(), p, r);
        r = scanAndReduce(node.getDescription(), p, r);
        return r;
    }

    @Override
    public R visitSince(SinceTree node, P p) {
        return scan(node.getBody(), p);
    }

    @Override
    public R visitStartElement(StartElementTree node, P p) {
        return scan(node.getAttributes(), p);
    }

    @Override
    public R visitText(TextTree node, P p) {
        return null;
    }

    @Override
    public R visitThrows(ThrowsTree node, P p) {
        R r = scan(node.getExceptionName(), p);
        r = scanAndReduce(node.getDescription(), p, r);
        return r;
    }

    @Override
    public R visitUnknownBlockTag(UnknownBlockTagTree node, P p) {
        return scan(node.getContent(), p);
    }

    @Override
    public R visitUnknownInlineTag(UnknownInlineTagTree node, P p) {
        return scan(node.getContent(), p);
    }

    @Override
    public R visitValue(ValueTree node, P p) {
        return scan(node.getReference(), p);
    }

    @Override
    public R visitVersion(VersionTree node, P p) {
        return scan(node.getBody(), p);
    }

    @Override
    public R visitOther(DocTree node, P p) {
        return null;
    }

}
