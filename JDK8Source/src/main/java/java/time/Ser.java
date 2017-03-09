/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2012, 2013, Oracle and/or its affiliates. All rights reserved.
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

/*
 * Copyright (c) 2011-2012, Stephen Colebourne & Michael Nascimento Santos
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  * Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  * Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  * Neither the name of JSR-310 nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * <p>
 *  版权所有(c)2011-2012,Stephen Colebourne和Michael Nascimento Santos
 * 
 *  版权所有。
 * 
 *  如果满足以下条件,则允许重新分发和使用源代码和二进制形式(带或不带修改)：
 * 
 *  *源代码的再分发必须保留上述版权声明,此条件列表和以下免责声明。
 * 
 *  *二进制形式的再分发必须在随发行提供的文档和/或其他材料中复制上述版权声明,此条件列表和以下免责声明。
 * 
 *  *未经特定事先书面许可,JSR-310的名称及其贡献者的名称不得用于支持或推广衍生自此软件的产品。
 * 
 * 本软件由版权所有者和贡献者"按原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 在任何情况下,版权所有者或贡献者对任何直接,间接,偶发,特殊,惩戒性或后果性损害(包括但不限于替代商品或服务的采购,使用,数据或利润损失,或业务中断),无论是由于任何责任推定,无论是在合同,严格责任,或
 * 侵权(包括疏忽或其他)任何方式使用本软件,即使已被告知此类损害的可能性。
 * 本软件由版权所有者和贡献者"按原样"提供,任何明示或默示的保证,包括但不限于适销性和特定用途适用性的默示保证。
 * 
 */
package java.time;

import java.io.Externalizable;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.StreamCorruptedException;

/**
 * The shared serialization delegate for this package.
 *
 * @implNote
 * This class wraps the object being serialized, and takes a byte representing the type of the class to
 * be serialized.  This byte can also be used for versioning the serialization format.  In this case another
 * byte flag would be used in order to specify an alternative version of the type format.
 * For example {@code LOCAL_DATE_TYPE_VERSION_2 = 21}.
 * <p>
 * In order to serialize the object it writes its byte and then calls back to the appropriate class where
 * the serialization is performed.  In order to deserialize the object it read in the type byte, switching
 * in order to select which class to call back into.
 * <p>
 * The serialization format is determined on a per class basis.  In the case of field based classes each
 * of the fields is written out with an appropriate size format in descending order of the field's size.  For
 * example in the case of {@link LocalDate} year is written before month.  Composite classes, such as
 * {@link LocalDateTime} are serialized as one object.
 * <p>
 * This class is mutable and should be created once per serialization.
 *
 * <p>
 *  此包的共享序列化委托。
 * 
 *  @implNote这个类包装被序列化的对象,并且接受一个字节,表示要被序列化的类的类型。此字节也可用于版本化序列化格式。在这种情况下,将使用另一字节标志以便指定类型格式的替代版本。
 * 例如{@code LOCAL_DATE_TYPE_VERSION_2 = 21}。
 * <p>
 *  为了序列化对象,它写入其字节,然后调用回执行序列化的适当类。为了反序列化它在类型字节中读取的对象,切换以选择要调用哪个类。
 * <p>
 * 序列化格式在每个类的基础上确定。在基于字段的类的情况下,每个字段以字段大小的降序以适当的大小格式写出。例如在{@link LocalDate}的情况下,年份是在月之前写的。
 * 复合类,例如{@link LocalDateTime}被序列化为一个对象。
 * <p>
 *  这个类是可变的,应该每次序列化创建一次。
 * 
 * 
 * @serial include
 * @since 1.8
 */
final class Ser implements Externalizable {

    /**
     * Serialization version.
     * <p>
     *  序列化版本。
     * 
     */
    private static final long serialVersionUID = -7683839454370182990L;

    static final byte DURATION_TYPE = 1;
    static final byte INSTANT_TYPE = 2;
    static final byte LOCAL_DATE_TYPE = 3;
    static final byte LOCAL_TIME_TYPE = 4;
    static final byte LOCAL_DATE_TIME_TYPE = 5;
    static final byte ZONE_DATE_TIME_TYPE = 6;
    static final byte ZONE_REGION_TYPE = 7;
    static final byte ZONE_OFFSET_TYPE = 8;
    static final byte OFFSET_TIME_TYPE = 9;
    static final byte OFFSET_DATE_TIME_TYPE = 10;
    static final byte YEAR_TYPE = 11;
    static final byte YEAR_MONTH_TYPE = 12;
    static final byte MONTH_DAY_TYPE = 13;
    static final byte PERIOD_TYPE = 14;

    /** The type being serialized. */
    private byte type;
    /** The object being serialized. */
    private Object object;

    /**
     * Constructor for deserialization.
     * <p>
     *  反序列化的构造函数。
     * 
     */
    public Ser() {
    }

    /**
     * Creates an instance for serialization.
     *
     * <p>
     *  创建用于序列化的实例。
     * 
     * 
     * @param type  the type
     * @param object  the object
     */
    Ser(byte type, Object object) {
        this.type = type;
        this.object = object;
    }

    //-----------------------------------------------------------------------
    /**
     * Implements the {@code Externalizable} interface to write the object.
     * <p>
     *  实现{@code Externalizable}接口来写入对象。
     * 
     * 
     * @serialData
     *
     * Each serializable class is mapped to a type that is the first byte
     * in the stream.  Refer to each class {@code writeReplace}
     * serialized form for the value of the type and sequence of values for the type.
     * <ul>
     * <li><a href="../../serialized-form.html#java.time.Duration">Duration.writeReplace</a>
     * <li><a href="../../serialized-form.html#java.time.Instant">Instant.writeReplace</a>
     * <li><a href="../../serialized-form.html#java.time.LocalDate">LocalDate.writeReplace</a>
     * <li><a href="../../serialized-form.html#java.time.LocalDateTime">LocalDateTime.writeReplace</a>
     * <li><a href="../../serialized-form.html#java.time.LocalTime">LocalTime.writeReplace</a>
     * <li><a href="../../serialized-form.html#java.time.MonthDay">MonthDay.writeReplace</a>
     * <li><a href="../../serialized-form.html#java.time.OffsetTime">OffsetTime.writeReplace</a>
     * <li><a href="../../serialized-form.html#java.time.OffsetDateTime">OffsetDateTime.writeReplace</a>
     * <li><a href="../../serialized-form.html#java.time.Period">Period.writeReplace</a>
     * <li><a href="../../serialized-form.html#java.time.Year">Year.writeReplace</a>
     * <li><a href="../../serialized-form.html#java.time.YearMonth">YearMonth.writeReplace</a>
     * <li><a href="../../serialized-form.html#java.time.ZoneId">ZoneId.writeReplace</a>
     * <li><a href="../../serialized-form.html#java.time.ZoneOffset">ZoneOffset.writeReplace</a>
     * <li><a href="../../serialized-form.html#java.time.ZonedDateTime">ZonedDateTime.writeReplace</a>
     * </ul>
     *
     * @param out  the data stream to write to, not null
     */
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        writeInternal(type, object, out);
    }

    static void writeInternal(byte type, Object object, ObjectOutput out) throws IOException {
        out.writeByte(type);
        switch (type) {
            case DURATION_TYPE:
                ((Duration) object).writeExternal(out);
                break;
            case INSTANT_TYPE:
                ((Instant) object).writeExternal(out);
                break;
            case LOCAL_DATE_TYPE:
                ((LocalDate) object).writeExternal(out);
                break;
            case LOCAL_DATE_TIME_TYPE:
                ((LocalDateTime) object).writeExternal(out);
                break;
            case LOCAL_TIME_TYPE:
                ((LocalTime) object).writeExternal(out);
                break;
            case ZONE_REGION_TYPE:
                ((ZoneRegion) object).writeExternal(out);
                break;
            case ZONE_OFFSET_TYPE:
                ((ZoneOffset) object).writeExternal(out);
                break;
            case ZONE_DATE_TIME_TYPE:
                ((ZonedDateTime) object).writeExternal(out);
                break;
            case OFFSET_TIME_TYPE:
                ((OffsetTime) object).writeExternal(out);
                break;
            case OFFSET_DATE_TIME_TYPE:
                ((OffsetDateTime) object).writeExternal(out);
                break;
            case YEAR_TYPE:
                ((Year) object).writeExternal(out);
                break;
            case YEAR_MONTH_TYPE:
                ((YearMonth) object).writeExternal(out);
                break;
            case MONTH_DAY_TYPE:
                ((MonthDay) object).writeExternal(out);
                break;
            case PERIOD_TYPE:
                ((Period) object).writeExternal(out);
                break;
            default:
                throw new InvalidClassException("Unknown serialized type");
        }
    }

    //-----------------------------------------------------------------------
    /**
     * Implements the {@code Externalizable} interface to read the object.
     * <p>
     *  实现{@code Externalizable}接口以读取对象。
     * 
     * 
     * @serialData
     *
     * The streamed type and parameters defined by the type's {@code writeReplace}
     * method are read and passed to the corresponding static factory for the type
     * to create a new instance.  That instance is returned as the de-serialized
     * {@code Ser} object.
     *
     * <ul>
     * <li><a href="../../serialized-form.html#java.time.Duration">Duration</a> - {@code Duration.ofSeconds(seconds, nanos);}
     * <li><a href="../../serialized-form.html#java.time.Instant">Instant</a> - {@code Instant.ofEpochSecond(seconds, nanos);}
     * <li><a href="../../serialized-form.html#java.time.LocalDate">LocalDate</a> - {@code LocalDate.of(year, month, day);}
     * <li><a href="../../serialized-form.html#java.time.LocalDateTime">LocalDateTime</a> - {@code LocalDateTime.of(date, time);}
     * <li><a href="../../serialized-form.html#java.time.LocalTime">LocalTime</a> - {@code LocalTime.of(hour, minute, second, nano);}
     * <li><a href="../../serialized-form.html#java.time.MonthDay">MonthDay</a> - {@code MonthDay.of(month, day);}
     * <li><a href="../../serialized-form.html#java.time.OffsetTime">OffsetTime</a> - {@code OffsetTime.of(time, offset);}
     * <li><a href="../../serialized-form.html#java.time.OffsetDateTime">OffsetDateTime</a> - {@code OffsetDateTime.of(dateTime, offset);}
     * <li><a href="../../serialized-form.html#java.time.Period">Period</a> - {@code Period.of(years, months, days);}
     * <li><a href="../../serialized-form.html#java.time.Year">Year</a> - {@code Year.of(year);}
     * <li><a href="../../serialized-form.html#java.time.YearMonth">YearMonth</a> - {@code YearMonth.of(year, month);}
     * <li><a href="../../serialized-form.html#java.time.ZonedDateTime">ZonedDateTime</a> - {@code ZonedDateTime.ofLenient(dateTime, offset, zone);}
     * <li><a href="../../serialized-form.html#java.time.ZoneId">ZoneId</a> - {@code ZoneId.of(id);}
     * <li><a href="../../serialized-form.html#java.time.ZoneOffset">ZoneOffset</a> - {@code (offsetByte == 127 ? ZoneOffset.ofTotalSeconds(in.readInt()) : ZoneOffset.ofTotalSeconds(offsetByte * 900));}
     * </ul>
     *
     * @param in  the data to read, not null
     */
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        type = in.readByte();
        object = readInternal(type, in);
    }

    static Object read(ObjectInput in) throws IOException, ClassNotFoundException {
        byte type = in.readByte();
        return readInternal(type, in);
    }

    private static Object readInternal(byte type, ObjectInput in) throws IOException, ClassNotFoundException {
        switch (type) {
            case DURATION_TYPE: return Duration.readExternal(in);
            case INSTANT_TYPE: return Instant.readExternal(in);
            case LOCAL_DATE_TYPE: return LocalDate.readExternal(in);
            case LOCAL_DATE_TIME_TYPE: return LocalDateTime.readExternal(in);
            case LOCAL_TIME_TYPE: return LocalTime.readExternal(in);
            case ZONE_DATE_TIME_TYPE: return ZonedDateTime.readExternal(in);
            case ZONE_OFFSET_TYPE: return ZoneOffset.readExternal(in);
            case ZONE_REGION_TYPE: return ZoneRegion.readExternal(in);
            case OFFSET_TIME_TYPE: return OffsetTime.readExternal(in);
            case OFFSET_DATE_TIME_TYPE: return OffsetDateTime.readExternal(in);
            case YEAR_TYPE: return Year.readExternal(in);
            case YEAR_MONTH_TYPE: return YearMonth.readExternal(in);
            case MONTH_DAY_TYPE: return MonthDay.readExternal(in);
            case PERIOD_TYPE: return Period.readExternal(in);
            default:
                throw new StreamCorruptedException("Unknown serialized type");
        }
    }

    /**
     * Returns the object that will replace this one.
     *
     * <p>
     *  返回将替换此对象的对象。
     * 
     * @return the read object, should never be null
     */
    private Object readResolve() {
         return object;
    }

}
