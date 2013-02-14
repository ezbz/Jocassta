package org.projectx.jocassta.qeury;

import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;

public interface QueryCallback<T> {
  T execute(Keyspace keyspace, @SuppressWarnings("rawtypes") Serializer keySerializer,
      Class<?> clazz);
}
