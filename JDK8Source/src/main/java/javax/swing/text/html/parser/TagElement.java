/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.text.html.parser;

import javax.swing.text.html.HTML;
/**
 * A generic HTML TagElement class. The methods define how white
 * space is interpreted around the tag.
 *
 * <p>
 *  一个通用的HTML TagElement类。方法定义如何解释标记周围的空格。
 * 
 * @author      Sunita Mani
 */

public class TagElement {

    Element elem;
    HTML.Tag htmlTag;
    boolean insertedByErrorRecovery;

    public TagElement ( Element elem ) {
        this(elem, false);
    }

    public TagElement (Element elem, boolean fictional) {
        this.elem = elem;
        htmlTag = HTML.getTag(elem.getName());
        if (htmlTag == null) {
            htmlTag = new HTML.UnknownTag(elem.getName());
        }
        insertedByErrorRecovery = fictional;
    }

    public boolean breaksFlow() {
        return htmlTag.breaksFlow();
    }

    public boolean isPreformatted() {
        return htmlTag.isPreformatted();
    }

    public Element getElement() {
        return elem;
    }

    public HTML.Tag getHTMLTag() {
        return htmlTag;
    }

    public boolean fictional() {
        return insertedByErrorRecovery;
    }
}
