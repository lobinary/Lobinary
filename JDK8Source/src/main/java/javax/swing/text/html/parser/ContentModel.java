/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.util.Vector;
import java.util.Enumeration;
import java.io.*;


/**
 * A representation of a content model. A content model is
 * basically a restricted BNF expression. It is restricted in
 * the sense that it must be deterministic. This means that you
 * don't have to represent it as a finite state automaton.<p>
 * See Annex H on page 556 of the SGML handbook for more information.
 *
 * <p>
 *  内容模型的表示。内容模型基本上是受限制的BNF表达式。它被限制在它必须是确定性的意义上。这意味着您不必将其表示为有限状态自动机。<p>有关更多信息,请参见SGML手册第556页附件H。
 * 
 * 
 * @author   Arthur van Hoff
 *
 */
public final class ContentModel implements Serializable {
    /**
     * Type. Either '*', '?', '+', ',', '|', '&amp;'.
     * <p>
     *  类型。 '*','?','+',',','|','&amp;'。
     * 
     */
    public int type;

    /**
     * The content. Either an Element or a ContentModel.
     * <p>
     *  内容。元素或ContentModel。
     * 
     */
    public Object content;

    /**
     * The next content model (in a ',', '|' or '&amp;' expression).
     * <p>
     *  下一个内容模型(在',','|'或'&amp;'表达式中)。
     * 
     */
    public ContentModel next;

    public ContentModel() {
    }

    /**
     * Create a content model for an element.
     * <p>
     *  为元素创建内容模型。
     * 
     */
    public ContentModel(Element content) {
        this(0, content, null);
    }

    /**
     * Create a content model of a particular type.
     * <p>
     *  创建特定类型的内容模型。
     * 
     */
    public ContentModel(int type, ContentModel content) {
        this(type, content, null);
    }

    /**
     * Create a content model of a particular type.
     * <p>
     *  创建特定类型的内容模型。
     * 
     */
    public ContentModel(int type, Object content, ContentModel next) {
        this.type = type;
        this.content = content;
        this.next = next;
    }

    /**
     * Return true if the content model could
     * match an empty input stream.
     * <p>
     *  如果内容模型可以匹配空输入流,则返回true。
     * 
     */
    public boolean empty() {
        switch (type) {
          case '*':
          case '?':
            return true;

          case '+':
          case '|':
            for (ContentModel m = (ContentModel)content ; m != null ; m = m.next) {
                if (m.empty()) {
                    return true;
                }
            }
            return false;

          case ',':
          case '&':
            for (ContentModel m = (ContentModel)content ; m != null ; m = m.next) {
                if (!m.empty()) {
                    return false;
                }
            }
            return true;

          default:
            return false;
        }
    }

    /**
     * Update elemVec with the list of elements that are
     * part of the this contentModel.
     * <p>
     *  使用作为此contentModel的一部分的元素列表更新elemVec。
     * 
     */
     public void getElements(Vector<Element> elemVec) {
         switch (type) {
         case '*':
         case '?':
         case '+':
             ((ContentModel)content).getElements(elemVec);
             break;
         case ',':
         case '|':
         case '&':
             for (ContentModel m=(ContentModel)content; m != null; m=m.next){
                 m.getElements(elemVec);
             }
             break;
         default:
             elemVec.addElement((Element)content);
         }
     }

     private boolean valSet[];
     private boolean val[];
     // A cache used by first().  This cache was found to speed parsing
     // by about 10% (based on measurements of the 4-12 code base after
     // buffering was fixed).

    /**
     * Return true if the token could potentially be the
     * first token in the input stream.
     * <p>
     *  如果令牌可能是输入流中的第一个令牌,则返回true。
     * 
     */
    public boolean first(Object token) {
        switch (type) {
          case '*':
          case '?':
          case '+':
            return ((ContentModel)content).first(token);

          case ',':
            for (ContentModel m = (ContentModel)content ; m != null ; m = m.next) {
                if (m.first(token)) {
                    return true;
                }
                if (!m.empty()) {
                    return false;
                }
            }
            return false;

          case '|':
          case '&': {
            Element e = (Element) token;
            if (valSet == null) {
                valSet = new boolean[Element.getMaxIndex() + 1];
                val = new boolean[valSet.length];
                // All Element instances are created before this ever executes
            }
            if (valSet[e.index]) {
                return val[e.index];
            }
            for (ContentModel m = (ContentModel)content ; m != null ; m = m.next) {
                if (m.first(token)) {
                    val[e.index] = true;
                    break;
                }
            }
            valSet[e.index] = true;
            return val[e.index];
          }

          default:
            return (content == token);
            // PENDING: refer to comment in ContentModelState
/*
              if (content == token) {
                  return true;
              }
              Element e = (Element)content;
              if (e.omitStart() && e.content != null) {
                  return e.content.first(token);
              }
              return false;
/* <p>
/*  if(content == token){return true; } Element e =(Element)content; if(e.omitStart()&& e.content！= null
/* ){return e.content.first(token); } return false;。
/* 
*/
        }
    }

    /**
     * Return the element that must be next.
     * <p>
     *  返回必须是下一个的元素。
     * 
     */
    public Element first() {
        switch (type) {
          case '&':
          case '|':
          case '*':
          case '?':
            return null;

          case '+':
          case ',':
            return ((ContentModel)content).first();

          default:
            return (Element)content;
        }
    }

    /**
     * Convert to a string.
     * <p>
     *  转换为字符串。
     */
    public String toString() {
        switch (type) {
          case '*':
            return content + "*";
          case '?':
            return content + "?";
          case '+':
            return content + "+";

          case ',':
          case '|':
          case '&':
            char data[] = {' ', (char)type, ' '};
            String str = "";
            for (ContentModel m = (ContentModel)content ; m != null ; m = m.next) {
                str = str + m;
                if (m.next != null) {
                    str += new String(data);
                }
            }
            return "(" + str + ")";

          default:
            return content.toString();
        }
    }
}
