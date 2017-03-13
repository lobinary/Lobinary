/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2013, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing;

import java.awt.Component;

/**
 *  An instance of the <code>Spring</code> class holds three properties that
 *  characterize its behavior: the <em>minimum</em>, <em>preferred</em>, and
 *  <em>maximum</em> values. Each of these properties may be involved in
 *  defining its fourth, <em>value</em>, property based on a series of rules.
 *  <p>
 *  An instance of the <code>Spring</code> class can be visualized as a
 *  mechanical spring that provides a corrective force as the spring is compressed
 *  or stretched away from its preferred value. This force is modelled
 *  as linear function of the distance from the preferred value, but with
 *  two different constants -- one for the compressional force and one for the
 *  tensional one. Those constants are specified by the minimum and maximum
 *  values of the spring such that a spring at its minimum value produces an
 *  equal and opposite force to that which is created when it is at its
 *  maximum value. The difference between the <em>preferred</em> and
 *  <em>minimum</em> values, therefore, represents the ease with which the
 *  spring can be compressed and the difference between its <em>maximum</em>
 *  and <em>preferred</em> values, indicates the ease with which the
 *  <code>Spring</code> can be extended.
 *  See the {@link #sum} method for details.
 *
 *  <p>
 *  By defining simple arithmetic operations on <code>Spring</code>s,
 *  the behavior of a collection of <code>Spring</code>s
 *  can be reduced to that of an ordinary (non-compound) <code>Spring</code>. We define
 *  the "+", "-", <em>max</em>, and <em>min</em> operators on
 *  <code>Spring</code>s so that, in each case, the result is a <code>Spring</code>
 *  whose characteristics bear a useful mathematical relationship to its constituent
 *  springs.
 *
 *  <p>
 *  A <code>Spring</code> can be treated as a pair of intervals
 *  with a single common point: the preferred value.
 *  The following rules define some of the
 *  arithmetic operators that can be applied to intervals
 *  (<code>[a, b]</code> refers to the interval
 *  from <code>a</code>
 *  to <code>b</code>,
 *  where <code>a &lt;= b</code>).
 *
 *  <pre>
 *          [a1, b1] + [a2, b2] = [a1 + a2, b1 + b2]
 *
 *                      -[a, b] = [-b, -a]
 *
 *      max([a1, b1], [a2, b2]) = [max(a1, a2), max(b1, b2)]
 *  </pre>
 *  <p>
 *
 *  If we denote <code>Spring</code>s as <code>[a, b, c]</code>,
 *  where <code>a &lt;= b &lt;= c</code>, we can define the same
 *  arithmetic operators on <code>Spring</code>s:
 *
 *  <pre>
 *          [a1, b1, c1] + [a2, b2, c2] = [a1 + a2, b1 + b2, c1 + c2]
 *
 *                           -[a, b, c] = [-c, -b, -a]
 *
 *      max([a1, b1, c1], [a2, b2, c2]) = [max(a1, a2), max(b1, b2), max(c1, c2)]
 *  </pre>
 *  <p>
 *  With both intervals and <code>Spring</code>s we can define "-" and <em>min</em>
 *  in terms of negation:
 *
 *  <pre>
 *      X - Y = X + (-Y)
 *
 *      min(X, Y) = -max(-X, -Y)
 *  </pre>
 *  <p>
 *  For the static methods in this class that embody the arithmetic
 *  operators, we do not actually perform the operation in question as
 *  that would snapshot the values of the properties of the method's arguments
 *  at the time the static method is called. Instead, the static methods
 *  create a new <code>Spring</code> instance containing references to
 *  the method's arguments so that the characteristics of the new spring track the
 *  potentially changing characteristics of the springs from which it
 *  was made. This is a little like the idea of a <em>lazy value</em>
 *  in a functional language.
 * <p>
 * If you are implementing a <code>SpringLayout</code> you
 * can find further information and examples in
 * <a
 href="https://docs.oracle.com/javase/tutorial/uiswing/layout/spring.html">How to Use SpringLayout</a>,
 * a section in <em>The Java Tutorial.</em>
 * <p>
 * <strong>Warning:</strong>
 * Serialized objects of this class will not be compatible with
 * future Swing releases. The current serialization support is
 * appropriate for short term storage or RMI between applications running
 * the same version of Swing.  As of 1.4, support for long term storage
 * of all JavaBeans&trade;
 * has been added to the <code>java.beans</code> package.
 * Please see {@link java.beans.XMLEncoder}.
 *
 * <p>
 *  <code> Spring </code>类的实例包含表征其行为的三个属性：<em> minimum </em>,<em> preferred </em>和<em>根据一系列规则,这些属性中的每一个都
 * 可能涉及定义其第四个<em>值</em>属性。
 * <p>
 * <code> Spring </>>类的实例可以可视化为机械弹簧,其在弹簧被压缩或拉离其优选值时提供校正力。
 * 该力被建模为距离优选值的线性函数值,但具有两个不同的常数 - 一个用于压缩力,一个用于张量这些常数由弹簧的最小值和最大值指定,使得处于其最小值的弹簧产生等于和相反的力,当其处于其最大值时创建因此,<em>
 * 优选</em>和<em>最小</em>值之间的差异表示弹簧可以被压缩的容易程度,以及它的<em>最大值< em>首选</em>值表示<code> Spring </code>的简易性。
 * <code> Spring </>>类的实例可以可视化为机械弹簧,其在弹簧被压缩或拉离其优选值时提供校正力。有关详细信息,请参阅{@link #sum}方法。
 * 
 * <p>
 * 通过在<code> Spring </code>上定义简单的算术运算,可以将<code> Spring </code>集合的行为简化为普通(非复合) code>我们在<code> Spring </code>
 * 上定义"+"," - ",<em> max </em>和<em> min </em>运算符,结果是一个<code> Spring </code>,它的特性与它的组成弹簧有一个有用的数学关系。
 * 
 * <p>
 *  一个<code> Spring </code>可以被看作一对具有单个公共点的区间：优选值以下规则定义了一些可以应用于区间的算术运算符(<code> [a,b] </code>是指从<code> a </code>
 * 到<code> b </code>的区间,其中<​​code> a <= b </code>)。
 * 
 * <pre>
 *  [a1,b1] + [a2,b2] = [a1 + a2,b1 + b2]
 * 
 *  -  [a,b] = [-b,-a]
 * 
 *  max([a1,b1],[a2,b2])= [max(a1,a2),max(b1,b2)]
 * </pre>
 * <p>
 * 
 *  如果我们将<code> Spring </code>表示为<code> [a,b,c] </code>,其中<code> a <= b <= c </code> Spring上的相同算术运算符</code>
 *  s：。
 * 
 * <pre>
 *  [a1,b1,c1] + [a2,b2,c2] = [a1 + a2,b1 + b2,c1 + c2]
 * 
 *   -  [a,b,c] = [-c,-b,-a]
 * 
 *  max(a1,b1,b2),max(c1,c2)] max(a1,b1,c1),[a2,b2,c2]
 * </pre>
 * <p>
 *  对于两个区间和<code> Spring </code>,我们可以定义" - "和<em> min </em>：
 * 
 * <pre>
 *  X-Y = X +(-Y)
 * 
 *  min(X,Y)= -max(-X,-Y)
 * </pre>
 * <p>
 * 对于在类中实现算术运算符的静态方法,我们实际上不执行所讨论的操作,因为将在调用静态方法时对方法的参数的属性的值进行快照。
 * 相反,静态方法创建new <code> Spring </code>实例包含对方法参数的引用,以便新弹簧的特性跟踪从中创建的弹簧的潜在变化特性这是有点像懒惰的想法值</em>。
 * <p>
 * 如果您正在实施<code> SpringLayout </code>,您可以在<a href=\"https://docsoraclecom/javase/tutorial/uiswing/layout/springhtml\">
 * 如何使用SpringLayout </a>中找到更多信息和示例>,<em> </em> </em>中的一个部分。
 * <p>
 *  <strong>警告：</strong>此类的序列化对象将不与未来的Swing版本兼容当前的序列化支持适用于运行相同版本的Swing的应用程序之间的短期存储或RMI。
 * 支持长期存储所有JavaBeans&trade;已添加到<code> javabeans </code>包中请参见{@link javabeansXMLEncoder}。
 * 
 * 
 * @see SpringLayout
 * @see SpringLayout.Constraints
 *
 * @author      Philip Milne
 * @since       1.4
 */
public abstract class Spring {

    /**
     * An integer value signifying that a property value has not yet been calculated.
     * <p>
     *  表示尚未计算属性值的整数值
     * 
     */
    public static final int UNSET = Integer.MIN_VALUE;

    /**
     * Used by factory methods to create a <code>Spring</code>.
     *
     * <p>
     *  由工厂方法使用来创建一个<code> Spring </code>
     * 
     * 
     * @see #constant(int)
     * @see #constant(int, int, int)
     * @see #max
     * @see #minus
     * @see #sum
     * @see SpringLayout.Constraints
     */
    protected Spring() {}

    /**
     * Returns the <em>minimum</em> value of this <code>Spring</code>.
     *
     * <p>
     * 传回此<code> Spring </code>的<em>最小值</em>
     * 
     * 
     * @return the <code>minimumValue</code> property of this <code>Spring</code>
     */
    public abstract int getMinimumValue();

    /**
     * Returns the <em>preferred</em> value of this <code>Spring</code>.
     *
     * <p>
     *  返回此<code> Spring </code>的<em>首选</em>值
     * 
     * 
     * @return the <code>preferredValue</code> of this <code>Spring</code>
     */
    public abstract int getPreferredValue();

    /**
     * Returns the <em>maximum</em> value of this <code>Spring</code>.
     *
     * <p>
     *  返回此<code> Spring </code>的<em>最大值</em>
     * 
     * 
     * @return the <code>maximumValue</code> property of this <code>Spring</code>
     */
    public abstract int getMaximumValue();

    /**
     * Returns the current <em>value</em> of this <code>Spring</code>.
     *
     * <p>
     *  返回此<code> Spring </code>的当前<em>值</em>
     * 
     * 
     * @return  the <code>value</code> property of this <code>Spring</code>
     *
     * @see #setValue
     */
    public abstract int getValue();

    /**
     * Sets the current <em>value</em> of this <code>Spring</code> to <code>value</code>.
     *
     * <p>
     *  将<code> Spring </code>的当前<em>值</em>设置为<code> value </code>
     * 
     * 
     * @param   value the new setting of the <code>value</code> property
     *
     * @see #getValue
     */
    public abstract void setValue(int value);

    private double range(boolean contract) {
        return contract ? (getPreferredValue() - getMinimumValue()) :
                          (getMaximumValue() - getPreferredValue());
    }

    /*pp*/ double getStrain() {
        double delta = (getValue() - getPreferredValue());
        return delta/range(getValue() < getPreferredValue());
    }

    /* <p>
    /*  double delta =(getValue() -  getPreferredValue());返回delta /范围(getValue()<getPreferredValue()); }}
    /* 
    /* 
    /*pp*/ void setStrain(double strain) {
        setValue(getPreferredValue() + (int)(strain * range(strain < 0)));
    }

        setValue(getPreferredValue() + (int)(strain * <p>
        setValue(getPreferredValue() + (int)(strain *  setValue(getPreferredValue()+(int)(strain * range(strain <0))); }}
        setValue(getPreferredValue() + (int)(strain * 
        setValue(getPreferredValue() + (int)(strain * 
    /*pp*/ boolean isCyclic(SpringLayout l) {
        return false;
    }

    /* <p>
    /*  return false; }}
    /* 
    /* 
    /*pp*/ static abstract class AbstractSpring extends Spring {
        protected int size = UNSET;

        public int getValue() {
            return size != UNSET ? size : getPreferredValue();
        }

        public final void setValue(int size) {
            if (this.size == size) {
                return;
            }
            if (size == UNSET) {
                clear();
            } else {
                setNonClearValue(size);
            }
        }

        protected void clear() {
            size = UNSET;
        }

        protected void setNonClearValue(int size) {
            this.size = size;
        }
    }

    private static class StaticSpring extends AbstractSpring {
        protected int min;
        protected int pref;
        protected int max;

        public StaticSpring(int pref) {
            this(pref, pref, pref);
        }

        public StaticSpring(int min, int pref, int max) {
            this.min = min;
            this.pref = pref;
            this.max = max;
        }

         public String toString() {
             return "StaticSpring [" + min + ", " + pref + ", " + max + "]";
         }

         public int getMinimumValue() {
            return min;
        }

        public int getPreferredValue() {
            return pref;
        }

        public int getMaximumValue() {
            return max;
        }
    }

    private static class NegativeSpring extends Spring {
        private Spring s;

        public NegativeSpring(Spring s) {
            this.s = s;
        }

// Note the use of max value rather than minimum value here.
// See the opening preamble on arithmetic with springs.

        public int getMinimumValue() {
            return -s.getMaximumValue();
        }

        public int getPreferredValue() {
            return -s.getPreferredValue();
        }

        public int getMaximumValue() {
            return -s.getMinimumValue();
        }

        public int getValue() {
            return -s.getValue();
        }

        public void setValue(int size) {
            // No need to check for UNSET as
            // Integer.MIN_VALUE == -Integer.MIN_VALUE.
            s.setValue(-size);
        }

    /* <p>
    /*  protected int size = UNSET;
    /* 
    /*  public int getValue(){return size！= UNSET? size：getPreferredValue(); }}
    /* 
    /*  public final void setValue(int size){if(thissize == size){return; } if(size == UNSET){clear(); } els
    /* e {setNonClearValue(size); }}。
    /* 
    /* protected void clear(){size = UNSET; }}
    /* 
    /*  protected void setNonClearValue(int size){thissize = size; }}
    /* 
    /*  private static class StaticSpring extends AbstractSpring {protected int min; protected int pref; protected int max;。
    /* 
    /*  public staticSpring(int pref){this(pref,pref,pref); }}
    /* 
    /*  public StaticSpring(int min,int pref,int max){thismin = min; thispref = pref; thismax = max; }}
    /* 
    /*  public String toString(){return"StaticSpring ["+ min +","+ pref +","+ max +"]"; }}
    /* 
    /*  public int getMinimumValue(){return min; }}
    /* 
    /*  public int getPreferredValue(){return pref; }}
    /* 
    /*  public int getMaximumValue(){return max; }}
    /* 
    /*  private static class NegativeSpring extends Spring {private Spring s;
    /* 
    /*  public NegativeSpring(Spring s){thiss = s; }}
    /* 
    /* //注意这里使用最大值而不是最小值//见spring的算术开头
    /* 
    /*  public int getMinimumValue(){return -sgetMaximumValue(); }}
    /* 
    /*  public int getPreferredValue(){return -sgetPreferredValue(); }}
    /* 
    /*  public int getMaximumValue(){return -sgetMinimumValue(); }}
    /* 
    /*  public int getValue(){return -sgetValue(); }}
    /* 
    /*  public void setValue(int size){//不需要检查UNSET为// IntegerMIN_VALUE == -IntegerMIN_VALUE ssetValue(-size); }
    /* }。
    /* 
    /* 
        /*pp*/ boolean isCyclic(SpringLayout l) {
            return s.isCyclic(l);
        }
    }

    private static class ScaleSpring extends Spring {
        private Spring s;
        private float factor;

        private ScaleSpring(Spring s, float factor) {
            this.s = s;
            this.factor = factor;
        }

        public int getMinimumValue() {
            return Math.round((factor < 0 ? s.getMaximumValue() : s.getMinimumValue()) * factor);
        }

        public int getPreferredValue() {
            return Math.round(s.getPreferredValue() * factor);
        }

        public int getMaximumValue() {
            return Math.round((factor < 0 ? s.getMinimumValue() : s.getMaximumValue()) * factor);
        }

        public int getValue() {
            return Math.round(s.getValue() * factor);
        }

        public void setValue(int value) {
            if (value == UNSET) {
                s.setValue(UNSET);
            } else {
                s.setValue(Math.round(value / factor));
            }
        }

            return Math.round(s.getValue() * <p>
            return Math.round(s.getValue() *  return sisCyclic(l); }}
            return Math.round(s.getValue() * 
            return Math.round(s.getValue() *  private static class ScaleSpring extends Spring {private Spring s;私人浮动因子
            return Math.round(s.getValue() * 
            return Math.round(s.getValue() *  private ScaleSpring(Spring s,float factor){thiss = s; thisfactor = factor; }}
            return Math.round(s.getValue() * 
            return Math.round(s.getValue() * public int getMinimumValue(){return Mathround((factor <0?sgetMaximumValue()：sgetMinimumValue())* factor); }}。
            return Math.round(s.getValue() * 
            return Math.round(s.getValue() *  public int getPreferredValue(){return Mathround(sgetPreferredValue()* factor); }}
            return Math.round(s.getValue() * 
            return Math.round(s.getValue() *  public int getMaximumValue(){return Mathround((factor <0?sgetMinimumValue()：sgetMaximumValue())* factor); }}。
            return Math.round(s.getValue() * 
            return Math.round(s.getValue() *  public int getValue(){return Mathround(sgetValue()* factor); }}
            return Math.round(s.getValue() * 
            return Math.round(s.getValue() *  public void setValue(int value){if(value == UNSET){ssetValue(UNSET); } else {ssetValue(Mathround(value / factor)); }
            return Math.round(s.getValue() * }。
            return Math.round(s.getValue() * 
            return Math.round(s.getValue() * 
        /*pp*/ boolean isCyclic(SpringLayout l) {
            return s.isCyclic(l);
        }
    }

        /* <p>
        /*  return sisCyclic(l); }}
        /* 
        /* 
    /*pp*/ static class WidthSpring extends AbstractSpring {
    /* <p>
    /* 
        /*pp*/ Component c;

        public WidthSpring(Component c) {
            this.c = c;
        }

        public int getMinimumValue() {
            return c.getMinimumSize().width;
        }

        public int getPreferredValue() {
            return c.getPreferredSize().width;
        }

        public int getMaximumValue() {
            // We will be doing arithmetic with the results of this call,
            // so if a returned value is Integer.MAX_VALUE we will get
            // arithmetic overflow. Truncate such values.
            return Math.min(Short.MAX_VALUE, c.getMaximumSize().width);
        }
    }

        /* <p>
        /*  public WidthSpring(Component c){thisc = c; }}
        /* 
        /*  public int getMinimumValue(){return cgetMinimumSize()width; }}
        /* 
        /*  public int getPreferredValue(){return cgetPreferredSize()width; }}
        /* 
        /* public int getMaximumValue(){//我们将对这个调用的结果进行算术运算,所以如果返回的值是IntegerMAX_VALUE,我们将获得//算术溢出Truncate这样的值返回Mathmin(ShortMAX_VALUE,cgetMaximumSize()width); }
        /* }。
        /* 
        /* 
     /*pp*/  static class HeightSpring extends AbstractSpring {
     /* <p>
     /* 
        /*pp*/ Component c;

        public HeightSpring(Component c) {
            this.c = c;
        }

        public int getMinimumValue() {
            return c.getMinimumSize().height;
        }

        public int getPreferredValue() {
            return c.getPreferredSize().height;
        }

        public int getMaximumValue() {
            return Math.min(Short.MAX_VALUE, c.getMaximumSize().height);
        }
    }

        /* <p>
        /*  public HeightSpring(Component c){thisc = c; }}
        /* 
        /*  public int getMinimumValue(){return cgetMinimumSize()height; }}
        /* 
        /*  public int getPreferredValue(){return cgetPreferredSize()height; }}
        /* 
        /*  public int getMaximumValue(){return Mathmin(ShortMAX_VALUE,cgetMaximumSize()height); }}
        /* 
        /* 
   /*pp*/ static abstract class SpringMap extends Spring {
       private Spring s;

       public SpringMap(Spring s) {
           this.s = s;
       }

       protected abstract int map(int i);

       protected abstract int inv(int i);

       public int getMinimumValue() {
           return map(s.getMinimumValue());
       }

       public int getPreferredValue() {
           return map(s.getPreferredValue());
       }

       public int getMaximumValue() {
           return Math.min(Short.MAX_VALUE, map(s.getMaximumValue()));
       }

       public int getValue() {
           return map(s.getValue());
       }

       public void setValue(int value) {
           if (value == UNSET) {
               s.setValue(UNSET);
           } else {
               s.setValue(inv(value));
           }
       }

   /* <p>
   /*  私人春天
   /* 
   /*  public SpringMap(Spring s){thiss = s; }}
   /* 
   /*  protected abstract int map(int i);
   /* 
   /*  protected abstract int inv(int i);
   /* 
   /*  public int getMinimumValue(){return map(sgetMinimumValue()); }}
   /* 
   /* public int getPreferredValue(){return map(sgetPreferredValue()); }}
   /* 
   /*  public int getMaximumValue(){return Mathmin(ShortMAX_VALUE,map(sgetMaximumValue())); }}
   /* 
   /*  public int getValue(){return map(sgetValue()); }}
   /* 
   /*  public void setValue(int value){if(value == UNSET){ssetValue(UNSET); } else {ssetValue(inv(value)); }
   /* }。
   /* 
   /* 
       /*pp*/ boolean isCyclic(SpringLayout l) {
           return s.isCyclic(l);
       }
   }

// Use the instance variables of the StaticSpring superclass to
// cache values that have already been calculated.
       /* <p>
       /*  return sisCyclic(l); }}
       /* 
       /*  //使用StaticSpring超类的实例变量来缓存已经计算的值
       /* 
       /* 
    /*pp*/ static abstract class CompoundSpring extends StaticSpring {
        protected Spring s1;
        protected Spring s2;

        public CompoundSpring(Spring s1, Spring s2) {
            super(UNSET);
            this.s1 = s1;
            this.s2 = s2;
        }

        public String toString() {
            return "CompoundSpring of " + s1 + " and " + s2;
        }

        protected void clear() {
            super.clear();
            min = pref = max = UNSET;
            s1.setValue(UNSET);
            s2.setValue(UNSET);
        }

        protected abstract int op(int x, int y);

        public int getMinimumValue() {
            if (min == UNSET) {
                min = op(s1.getMinimumValue(), s2.getMinimumValue());
            }
            return min;
        }

        public int getPreferredValue() {
            if (pref == UNSET) {
                pref = op(s1.getPreferredValue(), s2.getPreferredValue());
            }
            return pref;
        }

        public int getMaximumValue() {
            if (max == UNSET) {
                max = op(s1.getMaximumValue(), s2.getMaximumValue());
            }
            return max;
        }

        public int getValue() {
            if (size == UNSET) {
                size = op(s1.getValue(), s2.getValue());
            }
            return size;
        }

    /* <p>
    /*  保护Spring s1;保护Spring s2;
    /* 
    /*  public CompoundSpring(Spring s1,Spring s2){super(UNSET); thiss1 = s1; thiss2 = s2; }}
    /* 
    /*  public String toString(){return"CompoundSpring of"+ s1 +"and"+ s2; }}
    /* 
    /*  protected void clear(){superclear(); min = pref = max = UNSET; s1setValue(UNSET); s2setValue(UNSET); }
    /* }。
    /* 
    /* protected abstract int op(int x,int y);
    /* 
    /*  public int getMinimumValue(){if(min == UNSET){min = op(s1getMinimumValue(),s2getMinimumValue()); } r
    /* eturn min; }}。
    /* 
    /*  public int getPreferredValue(){if(pref == UNSET){pref = op(s1getPreferredValue(),s2getPreferredValue()); }
    /*  return pref; }}。
    /* 
    /*  public int getMaximumValue(){if(max == UNSET){max = op(s1getMaximumValue(),s2getMaximumValue()); } r
    /* eturn max; }}。
    /* 
    /*  public int getValue(){if(size == UNSET){size = op(s1getValue(),s2getValue()); } return size; }}
    /* 
    /* 
        /*pp*/ boolean isCyclic(SpringLayout l) {
            return l.isCyclic(s1) || l.isCyclic(s2);
        }
    };

     private static class SumSpring extends CompoundSpring {
         public SumSpring(Spring s1, Spring s2) {
             super(s1, s2);
         }

         protected int op(int x, int y) {
             return x + y;
         }

         protected void setNonClearValue(int size) {
             super.setNonClearValue(size);
             s1.setStrain(this.getStrain());
             s2.setValue(size - s1.getValue());
         }
     }

    private static class MaxSpring extends CompoundSpring {

        public MaxSpring(Spring s1, Spring s2) {
            super(s1, s2);
        }

        protected int op(int x, int y) {
            return Math.max(x, y);
        }

        protected void setNonClearValue(int size) {
            super.setNonClearValue(size);
            s1.setValue(size);
            s2.setValue(size);
        }
    }

    /**
     * Returns a strut -- a spring whose <em>minimum</em>, <em>preferred</em>, and
     * <em>maximum</em> values each have the value <code>pref</code>.
     *
     * <p>
     *  return lisCyclic(s1)|| (s2); }};
     * 
     *  private static class SumSpring extends CompoundSpring {public SumSpring(Spring s1,Spring s2){super(s1,s2); }
     * }。
     * 
     *  protected int op(int x,int y){return x + y; }}
     * 
     * protected void setNonClearValue(int size){supersetNonClearValue(size); s1setStrain(thisgetStrain()); s2setValue(size  -  s1getValue()); }
     * }。
     * 
     *  private static class MaxSpring extends CompoundSpring {
     * 
     *  public MaxSpring(Spring s1,Spring s2){super(s1,s2); }}
     * 
     *  protected int op(int x,int y){return Mathmax(x,y); }}
     * 
     *  protected void setNonClearValue(int size){supersetNonClearValue(size); s1setValue(size); s2setValue(size); }
     * }。
     * 
     *  / **返回strut  -  <em>最小值</em>,<em>首选</em>和<em>最大值</em>的每个值都为<code> pref </code >
     * 
     * 
     * @param  pref the <em>minimum</em>, <em>preferred</em>, and
     *         <em>maximum</em> values of the new spring
     * @return a spring whose <em>minimum</em>, <em>preferred</em>, and
     *         <em>maximum</em> values each have the value <code>pref</code>
     *
     * @see Spring
     */
     public static Spring constant(int pref) {
         return constant(pref, pref, pref);
     }

    /**
     * Returns a spring whose <em>minimum</em>, <em>preferred</em>, and
     * <em>maximum</em> values have the values: <code>min</code>, <code>pref</code>,
     * and <code>max</code> respectively.
     *
     * <p>
     *  返回<em>最小值</em>,<em>首选</em>和<em>最大值</em>的值为<code> min </code> / code>和<code> max </code>
     * 
     * 
     * @param  min the <em>minimum</em> value of the new spring
     * @param  pref the <em>preferred</em> value of the new spring
     * @param  max the <em>maximum</em> value of the new spring
     * @return a spring whose <em>minimum</em>, <em>preferred</em>, and
     *         <em>maximum</em> values have the values: <code>min</code>, <code>pref</code>,
     *         and <code>max</code> respectively
     *
     * @see Spring
     */
     public static Spring constant(int min, int pref, int max) {
         return new StaticSpring(min, pref, max);
     }


    /**
     * Returns <code>-s</code>: a spring running in the opposite direction to <code>s</code>.
     *
     * <p>
     * 返回<code> -s </code>：以与<code> s </code>相反的方向运行的spring
     * 
     * 
     * @return <code>-s</code>: a spring running in the opposite direction to <code>s</code>
     *
     * @see Spring
     */
    public static Spring minus(Spring s) {
        return new NegativeSpring(s);
    }

    /**
     * Returns <code>s1+s2</code>: a spring representing <code>s1</code> and <code>s2</code>
     * in series. In a sum, <code>s3</code>, of two springs, <code>s1</code> and <code>s2</code>,
     * the <em>strains</em> of <code>s1</code>, <code>s2</code>, and <code>s3</code> are maintained
     * at the same level (to within the precision implied by their integer <em>value</em>s).
     * The strain of a spring in compression is:
     * <pre>
     *         value - pref
     *         ------------
     *          pref - min
     * </pre>
     * and the strain of a spring in tension is:
     * <pre>
     *         value - pref
     *         ------------
     *          max - pref
     * </pre>
     * When <code>setValue</code> is called on the sum spring, <code>s3</code>, the strain
     * in <code>s3</code> is calculated using one of the formulas above. Once the strain of
     * the sum is known, the <em>value</em>s of <code>s1</code> and <code>s2</code> are
     * then set so that they are have a strain equal to that of the sum. The formulas are
     * evaluated so as to take rounding errors into account and ensure that the sum of
     * the <em>value</em>s of <code>s1</code> and <code>s2</code> is exactly equal to
     * the <em>value</em> of <code>s3</code>.
     *
     * <p>
     *  返回<code> s1 + s2 </code>：代表<code> s1 </code>和<code> s2 </code>的春天系列中的春天</code> ,<code> s1 </code>和<code>
     *  s2 </code>,<code> s1 </code>,<code> s2 </code> > s3 </code>维持在相同的水平(在由它们的整数值</em>所暗示的精度内)。
     * 压缩弹簧的应变是：。
     * <pre>
     *  value  -  pref ------------ pref  -  min
     * </pre>
     *  并且张力弹簧的应变为：
     * <pre>
     *  value  -  pref ------------ max  -  pref
     * </pre>
     * 当在sum spring,<code> s3 </code>上调用<code> setValue </code>时,使用上述公式之一计算<code> s3 </code>中的应变。
     * 是已知的,那么设置<code> s1 </code>和<code> s2 </code>的<em>值</em>使得它们具有等于求和的应变。
     * 以便考虑舍入误差并且确保<code> s1 </code>和<code> s2 </code>的<em>值</em>的总和等于<em> > value </em> of <code> s3 </code>
     * 。
     * 是已知的,那么设置<code> s1 </code>和<code> s2 </code>的<em>值</em>使得它们具有等于求和的应变。
     * 
     * 
     * @return <code>s1+s2</code>: a spring representing <code>s1</code> and <code>s2</code> in series
     *
     * @see Spring
     */
     public static Spring sum(Spring s1, Spring s2) {
         return new SumSpring(s1, s2);
     }

    /**
     * Returns <code>max(s1, s2)</code>: a spring whose value is always greater than (or equal to)
     *         the values of both <code>s1</code> and <code>s2</code>.
     *
     * <p>
     *  返回<code> max(s1,s2)</code>：其值总是大于(或等于)<code> s1 </code>和<code> s2 </code>
     * 
     * 
     * @return <code>max(s1, s2)</code>: a spring whose value is always greater than (or equal to)
     *         the values of both <code>s1</code> and <code>s2</code>
     * @see Spring
     */
    public static Spring max(Spring s1, Spring s2) {
        return new MaxSpring(s1, s2);
    }

    // Remove these, they're not used often and can be created using minus -
    // as per these implementations.

    /*pp*/ static Spring difference(Spring s1, Spring s2) {
        return sum(s1, minus(s2));
    }

    /*
    public static Spring min(Spring s1, Spring s2) {
        return minus(max(minus(s1), minus(s2)));
    }
    /* <p>
    /*  return sum(s1,minus(s2)); }}
    /* 
    /* / * public static Spring min(Spring s1,Spring s2){return minus(max(minus(s1),minus(s2))); }}
    /* 
    */

    /**
     * Returns a spring whose <em>minimum</em>, <em>preferred</em>, <em>maximum</em>
     * and <em>value</em> properties are each multiples of the properties of the
     * argument spring, <code>s</code>. Minimum and maximum properties are
     * swapped when <code>factor</code> is negative (in accordance with the
     * rules of interval arithmetic).
     * <p>
     * When factor is, for example, 0.5f the result represents 'the mid-point'
     * of its input - an operation that is useful for centering components in
     * a container.
     *
     * <p>
     *  返回<em>最小值</em>,<em> </em>,<em>最大值</em>和<em>值</em>属性的参数spring的属性的倍数,<code> s </code>当<code> factor </code>
     * 为负值时,交换最小和最大属性(根据间隔算术规则)。
     * <p>
     *  当因子是,例如,05f时,结果表示其输入的"中点" - 这是一个操作,可用于集中容器中的组件
     * 
     * 
     * @param s the spring to scale
     * @param factor amount to scale by.
     * @return  a spring whose properties are those of the input spring <code>s</code>
     * multiplied by <code>factor</code>
     * @throws NullPointerException if <code>s</code> is null
     * @since 1.5
     */
    public static Spring scale(Spring s, float factor) {
        checkArg(s);
        return new ScaleSpring(s, factor);
    }

    /**
     * Returns a spring whose <em>minimum</em>, <em>preferred</em>, <em>maximum</em>
     * and <em>value</em> properties are defined by the widths of the <em>minimumSize</em>,
     * <em>preferredSize</em>, <em>maximumSize</em> and <em>size</em> properties
     * of the supplied component. The returned spring is a 'wrapper' implementation
     * whose methods call the appropriate size methods of the supplied component.
     * The minimum, preferred, maximum and value properties of the returned spring
     * therefore report the current state of the appropriate properties in the
     * component and track them as they change.
     *
     * <p>
     * 返回弹簧谁家的<em>最小</em>的<EM>首选</em>的<EM>最大</em>的和的<em>值</em>的性质是由所定义的宽度的<em>的minimumSize </em>的<EM> PREFER
     * REDSIZE </em>的<EM> MAXIMUMSIZE </em>的和的<em>尺寸</em>的提供的组件的属性返回的春天是一个"包装"实现,其方法调用所提供组件的适当大小方法返回的spring的
     * 最小,首选,最大和值属性因此报告的相应属性的当前状态,在组件和跟踪他们,因为他们改变。
     * 
     * 
     * @param c Component used for calculating size
     * @return  a spring whose properties are defined by the horizontal component
     * of the component's size methods.
     * @throws NullPointerException if <code>c</code> is null
     * @since 1.5
     */
    public static Spring width(Component c) {
        checkArg(c);
        return new WidthSpring(c);
    }

    /**
     * Returns a spring whose <em>minimum</em>, <em>preferred</em>, <em>maximum</em>
     * and <em>value</em> properties are defined by the heights of the <em>minimumSize</em>,
     * <em>preferredSize</em>, <em>maximumSize</em> and <em>size</em> properties
     * of the supplied component. The returned spring is a 'wrapper' implementation
     * whose methods call the appropriate size methods of the supplied component.
     * The minimum, preferred, maximum and value properties of the returned spring
     * therefore report the current state of the appropriate properties in the
     * component and track them as they change.
     *
     * <p>
     * 返回弹簧谁家的<em>最小</em>的<EM>首选</em>的<EM>最大</em>的和的<em>值</em>的属性应该的定义高度的<em>的minimumSize </em>的<EM> PREFERR
     * EDSIZE </em>的<EM> MAXIMUMSIZE </em>的和的<em>尺寸</em>的提供的组件的属性返回的春天是一个"包装"实现,其方法调用所提供组件的适当大小方法返回的spring的最
     * 小,首选,最大和值属性因此报告的相应属性的当前状态,在组件和跟踪他们,因为他们改变。
     * 
     * 
     * @param c Component used for calculating size
     * @return  a spring whose properties are defined by the vertical component
     * of the component's size methods.
     * @throws NullPointerException if <code>c</code> is null
     * @since 1.5
     */
    public static Spring height(Component c) {
        checkArg(c);
        return new HeightSpring(c);
    }


    /**
     * If <code>s</code> is null, this throws an NullPointerException.
     * <p>
     */
    private static void checkArg(Object s) {
        if (s == null) {
            throw new NullPointerException("Argument must not be null");
        }
    }
}
