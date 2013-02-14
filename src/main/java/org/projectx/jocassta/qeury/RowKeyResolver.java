package org.projectx.jocassta.qeury;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.apache.cassandra.db.CounterColumn;
import org.apache.cassandra.db.marshal.AsciiType;
import org.apache.cassandra.db.marshal.BytesType;
import org.apache.cassandra.db.marshal.IntegerType;
import org.apache.cassandra.db.marshal.LexicalUUIDType;
import org.apache.cassandra.db.marshal.LongType;
import org.apache.cassandra.db.marshal.TimeUUIDType;
import org.apache.cassandra.db.marshal.UTF8Type;

public class RowKeyResolver {
  public static List<Object> resolveRowKeys(final Class<?> type, final List<String> rowKeys) {
    final List<Object> result = new LinkedList<Object>();
    for (final String rowKey : rowKeys) {
      result.add(RowKeyResolver.resolveRowKey(type, rowKey));
    }
    return result;
  }

  public static Object resolveRowKey(final Class<?> type, final String rowKey) {
    if (BytesType.class.equals(type)) {
      return rowKey.getBytes();
    } else if (IntegerType.class.equals(type)) {
      return Integer.valueOf(rowKey);
    } else if (LongType.class.equals(type)) {
      return Long.valueOf(rowKey);
    } else if (LexicalUUIDType.class.equals(type)) {
      return UUID.fromString(rowKey);
    } else if (TimeUUIDType.class.equals(type)) {
      return UUID.fromString(rowKey);
    } else if (UTF8Type.class.equals(type)) {
      return rowKey;
    } else if (AsciiType.class.equals(type)) {
      return rowKey;
    } else if (CounterColumn.class.equals(type)) {
      return Long.valueOf(rowKey);
    }
    throw new IllegalStateException("Cannot detect serializer");
  }

}
