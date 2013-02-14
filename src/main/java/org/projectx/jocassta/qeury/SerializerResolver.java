package org.projectx.jocassta.qeury;

import me.prettyprint.cassandra.serializers.AsciiSerializer;
import me.prettyprint.cassandra.serializers.IntegerSerializer;
import me.prettyprint.cassandra.serializers.LongSerializer;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.serializers.TimeUUIDSerializer;
import me.prettyprint.cassandra.serializers.UUIDSerializer;
import me.prettyprint.hector.api.Serializer;

import org.apache.cassandra.db.CounterColumn;
import org.apache.cassandra.db.marshal.AsciiType;
import org.apache.cassandra.db.marshal.BytesType;
import org.apache.cassandra.db.marshal.IntegerType;
import org.apache.cassandra.db.marshal.LexicalUUIDType;
import org.apache.cassandra.db.marshal.LongType;
import org.apache.cassandra.db.marshal.TimeUUIDType;
import org.apache.cassandra.db.marshal.UTF8Type;

public class SerializerResolver {
  public static Serializer<?> getSerializer(final Class<?> type) {
    if (BytesType.class.equals(type)) {
      return StringSerializer.get();
    } else if (IntegerType.class.equals(type)) {
      return IntegerSerializer.get();
    } else if (LongType.class.equals(type)) {
      return LongSerializer.get();
    } else if (LexicalUUIDType.class.equals(type)) {
      return UUIDSerializer.get();
    } else if (TimeUUIDType.class.equals(type)) {
      return TimeUUIDSerializer.get();
    } else if (UTF8Type.class.equals(type)) {
      return StringSerializer.get();
    } else if (AsciiType.class.equals(type)) {
      return AsciiSerializer.get();
    } else if (CounterColumn.class.equals(type)) {
      return LongSerializer.get();
    }
    throw new IllegalStateException("Cannot detect serializer");
  }
}
