/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.accessibility;

import java.util.Vector;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * Class AccessibleRelationSet determines a component's relation set.  The
 * relation set of a component is a set of AccessibleRelation objects that
 * describe the component's relationships with other components.
 *
 * <p>
 *  类AccessibleRelationSet确定组件的关系集。组件的关系集是一组描述组件与其他组件的关系的AccessibleRelation对象。
 * 
 * 
 * @see AccessibleRelation
 *
 * @author      Lynn Monsanto
 * @since 1.3
 */
public class AccessibleRelationSet {

    /**
     * Each entry in the Vector represents an AccessibleRelation.
     * <p>
     *  向量中的每个条目表示一个AccessibleRelation。
     * 
     * 
     * @see #add
     * @see #addAll
     * @see #remove
     * @see #contains
     * @see #get
     * @see #size
     * @see #toArray
     * @see #clear
     */
    protected Vector<AccessibleRelation> relations = null;

    /**
     * Creates a new empty relation set.
     * <p>
     *  创建一个新的空关系集。
     * 
     */
    public AccessibleRelationSet() {
        relations = null;
    }

    /**
     * Creates a new relation with the initial set of relations contained in
     * the array of relations passed in.  Duplicate entries are ignored.
     *
     * <p>
     *  使用包含在传递的关系数组中的初始关系集创建新关系。重复条目将被忽略。
     * 
     * 
     * @param relations an array of AccessibleRelation describing the
     * relation set.
     */
    public AccessibleRelationSet(AccessibleRelation[] relations) {
        if (relations.length != 0) {
            this.relations = new Vector(relations.length);
            for (int i = 0; i < relations.length; i++) {
                add(relations[i]);
            }
        }
    }

    /**
     * Adds a new relation to the current relation set.  If the relation
     * is already in the relation set, the target(s) of the specified
     * relation is merged with the target(s) of the existing relation.
     * Otherwise,  the new relation is added to the relation set.
     *
     * <p>
     *  向当前关系集添加新关系。如果关系已经在关系集中,则将指定关系的目标与现有关系的目标合并。否则,新关系将添加到关系集中。
     * 
     * 
     * @param relation the relation to add to the relation set
     * @return true if relation is added to the relation set; false if the
     * relation set is unchanged
     */
    public boolean add(AccessibleRelation relation) {
        if (relations == null) {
            relations = new Vector();
        }

        // Merge the relation targets if the key exists
        AccessibleRelation existingRelation = get(relation.getKey());
        if (existingRelation == null) {
            relations.addElement(relation);
            return true;
        } else {
            Object [] existingTarget = existingRelation.getTarget();
            Object [] newTarget = relation.getTarget();
            int mergedLength = existingTarget.length + newTarget.length;
            Object [] mergedTarget = new Object[mergedLength];
            for (int i = 0; i < existingTarget.length; i++) {
                mergedTarget[i] = existingTarget[i];
            }
            for (int i = existingTarget.length, j = 0;
                 i < mergedLength;
                 i++, j++) {
                mergedTarget[i] = newTarget[j];
            }
            existingRelation.setTarget(mergedTarget);
        }
        return true;
    }

    /**
     * Adds all of the relations to the existing relation set.  Duplicate
     * entries are ignored.
     *
     * <p>
     *  将所有关系添加到现有关系集。重复的条目将被忽略。
     * 
     * 
     * @param relations  AccessibleRelation array describing the relation set.
     */
    public void addAll(AccessibleRelation[] relations) {
        if (relations.length != 0) {
            if (this.relations == null) {
                this.relations = new Vector(relations.length);
            }
            for (int i = 0; i < relations.length; i++) {
                add(relations[i]);
            }
        }
    }

    /**
     * Removes a relation from the current relation set.  If the relation
     * is not in the set, the relation set will be unchanged and the
     * return value will be false.  If the relation is in the relation
     * set, it will be removed from the set and the return value will be
     * true.
     *
     * <p>
     *  从当前关系集中删除关系。如果关系不在集合中,则关系集将保持不变,返回值将为false。如果关系在关系集中,它将从集合中删除,返回值将为true。
     * 
     * 
     * @param relation the relation to remove from the relation set
     * @return true if the relation is in the relation set; false if the
     * relation set is unchanged
     */
    public boolean remove(AccessibleRelation relation) {
        if (relations == null) {
            return false;
        } else {
            return relations.removeElement(relation);
        }
    }

    /**
     * Removes all the relations from the current relation set.
     * <p>
     *  从当前关系集中删除所有关系。
     * 
     */
    public void clear() {
        if (relations != null) {
            relations.removeAllElements();
        }
    }

    /**
     * Returns the number of relations in the relation set.
     * <p>
     *  返回关系集中的关系数。
     * 
     * 
     * @return the number of relations in the relation set
     */
    public int size() {
        if (relations == null) {
            return 0;
        } else {
            return relations.size();
        }
    }

    /**
     * Returns whether the relation set contains a relation
     * that matches the specified key.
     * <p>
     *  返回关系集是否包含与指定键匹配的关系。
     * 
     * 
     * @param key the AccessibleRelation key
     * @return true if the relation is in the relation set; otherwise false
     */
    public boolean contains(String key) {
        return get(key) != null;
    }

    /**
     * Returns the relation that matches the specified key.
     * <p>
     *  返回与指定键匹配的关系。
     * 
     * 
     * @param key the AccessibleRelation key
     * @return the relation, if one exists, that matches the specified key.
     * Otherwise, null is returned.
     */
    public AccessibleRelation get(String key) {
        if (relations == null) {
            return null;
        } else {
            int len = relations.size();
            for (int i = 0; i < len; i++) {
                AccessibleRelation relation =
                    (AccessibleRelation)relations.elementAt(i);
                if (relation != null && relation.getKey().equals(key)) {
                    return relation;
                }
            }
            return null;
        }
    }

    /**
     * Returns the current relation set as an array of AccessibleRelation
     * <p>
     * 将当前关系集作为AccessibleRelation数组返回
     * 
     * 
     * @return AccessibleRelation array contacting the current relation.
     */
    public AccessibleRelation[] toArray() {
        if (relations == null) {
            return new AccessibleRelation[0];
        } else {
            AccessibleRelation[] relationArray
                = new AccessibleRelation[relations.size()];
            for (int i = 0; i < relationArray.length; i++) {
                relationArray[i] = (AccessibleRelation) relations.elementAt(i);
            }
            return relationArray;
        }
    }

    /**
     * Creates a localized String representing all the relations in the set
     * using the default locale.
     *
     * <p>
     *  使用默认语言环境创建表示集合中所有关系的本地化字符串。
     * 
     * @return comma separated localized String
     * @see AccessibleBundle#toDisplayString
     */
    public String toString() {
        String ret = "";
        if ((relations != null) && (relations.size() > 0)) {
            ret = ((AccessibleRelation) (relations.elementAt(0))).toDisplayString();
            for (int i = 1; i < relations.size(); i++) {
                ret = ret + ","
                        + ((AccessibleRelation) (relations.elementAt(i))).
                                              toDisplayString();
            }
        }
        return ret;
    }
}
