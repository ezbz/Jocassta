package org.projectx.jocassta.qeury;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import me.prettyprint.cassandra.model.CqlQuery;
import me.prettyprint.cassandra.model.CqlRows;
import me.prettyprint.cassandra.serializers.StringSerializer;
import me.prettyprint.cassandra.service.CassandraHostConfigurator;
import me.prettyprint.hector.api.Cluster;
import me.prettyprint.hector.api.Keyspace;
import me.prettyprint.hector.api.Serializer;
import me.prettyprint.hector.api.beans.ColumnSlice;
import me.prettyprint.hector.api.beans.CounterRow;
import me.prettyprint.hector.api.beans.CounterRows;
import me.prettyprint.hector.api.beans.CounterSlice;
import me.prettyprint.hector.api.beans.HColumn;
import me.prettyprint.hector.api.beans.HCounterColumn;
import me.prettyprint.hector.api.beans.Row;
import me.prettyprint.hector.api.beans.Rows;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;
import me.prettyprint.hector.api.factory.HFactory;
import me.prettyprint.hector.api.query.MultigetSliceCounterQuery;
import me.prettyprint.hector.api.query.MultigetSliceQuery;
import me.prettyprint.hector.api.query.QueryResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.projectx.jocassta.domain.CassandraRequest;
import org.projectx.jocassta.domain.CassandraResult;
import org.projectx.jocassta.domain.ColumnResult;
import org.projectx.jocassta.domain.CqlQueryRequest;
import org.projectx.jocassta.domain.QueryRequest;
import org.projectx.jocassta.domain.RangeQueryRequest;

public class JocasstaQueryExecutor {
  private static final Logger log = LoggerFactory.getLogger(JocasstaQueryExecutor.class);

  @SuppressWarnings("unchecked")
  public Map<String, List<ColumnResult>> queryCounter(final QueryRequest query) {
    return executeQueryInternal(query, new QueryCallback<Map<String, List<ColumnResult>>>() {
      @SuppressWarnings("rawtypes")
      @Override
      public Map<String, List<ColumnResult>> execute(final Keyspace keyspace,
          final Serializer keySerializer, final Class<?> clazz) {
        final MultigetSliceCounterQuery msCounterQuery = HFactory.createMultigetSliceCounterQuery(
            keyspace, keySerializer, StringSerializer.get());
        msCounterQuery.setColumnFamily(query.getColumnFamily());
        msCounterQuery.setColumnNames(query.getColumns().toArray(new Object[] {}));
        msCounterQuery.setKeys(RowKeyResolver.resolveRowKeys(clazz, query.getRowKeys()));
        final QueryResult<CounterRows> queryResult = msCounterQuery.execute();
        final CounterRows counterRows = queryResult.get();

        final Map<String, List<ColumnResult>> result = new HashMap<String, List<ColumnResult>>();
        for (final String key : query.getRowKeys()) {
          final CounterRow counterRow = counterRows.getByKey(RowKeyResolver.resolveRowKey(clazz,
              key));
          final CounterSlice columnSlice = counterRow.getColumnSlice();
          final List<ColumnResult> columnResult = new LinkedList<ColumnResult>();
          final List<HCounterColumn> columnsInSlice = columnSlice.getColumns();
          for (final HCounterColumn col : columnsInSlice) {
            columnResult.add(new ColumnResult(col.getName(), col.getValue(), null, col.getTtl()));
          }
          result.put(key, columnResult);
        }

        return result;
      }
    });
  }

  @SuppressWarnings("unchecked")
  public Map<String, List<ColumnResult>> queryRange(final RangeQueryRequest query) {
    return executeQueryInternal(query, new QueryCallback<Map<String, List<ColumnResult>>>() {

      @SuppressWarnings("rawtypes")
      @Override
      public Map<String, List<ColumnResult>> execute(final Keyspace keyspace,
          final Serializer keySerializer, final Class<?> clazz) {
        final MultigetSliceQuery msQuery = HFactory.createMultigetSliceQuery(keyspace,
            keySerializer, StringSerializer.get(), StringSerializer.get());
        msQuery.setColumnFamily(query.getColumnFamily());
        msQuery.setRange(query.getStartColumn(), query.getEndColumn(), query.isReverseOrder(),
            query.getLimit());
        msQuery.setKeys(RowKeyResolver.resolveRowKeys(clazz, query.getRowKeys()));
        final QueryResult<Rows> queryResult = msQuery.execute();
        final Rows rows = queryResult.get();

        final Map<String, List<ColumnResult>> result = new HashMap<String, List<ColumnResult>>();
        for (final String key : query.getRowKeys()) {
          final Row row = rows.getByKey(RowKeyResolver.resolveRowKey(clazz, key));
          final ColumnSlice columnSlice = row.getColumnSlice();
          final List<ColumnResult> columnResult = new LinkedList<ColumnResult>();
          final List<HColumn> columnsInSlice = columnSlice.getColumns();
          for (final HColumn col : columnsInSlice) {
            columnResult.add(new ColumnResult(col.getName(), col.getValue(), col.getClock(),
                col.getTtl()));
          }
          result.put(key, columnResult);
        }
        return result;
      }
    });

  }

  @SuppressWarnings("unchecked")
  public Map<String, List<ColumnResult>> queryCounterRange(final RangeQueryRequest query) {
    return executeQueryInternal(query, new QueryCallback<Map<String, List<ColumnResult>>>() {

      @SuppressWarnings("rawtypes")
      @Override
      public Map<String, List<ColumnResult>> execute(final Keyspace keyspace,
          final Serializer keySerializer, final Class<?> clazz) {
        final MultigetSliceCounterQuery msCounterQuery = HFactory.createMultigetSliceCounterQuery(
            keyspace, keySerializer, StringSerializer.get());
        msCounterQuery.setColumnFamily(query.getColumnFamily());
        msCounterQuery.setRange(query.getStartColumn(), query.getEndColumn(),
            query.isReverseOrder(), query.getLimit());
        msCounterQuery.setKeys(RowKeyResolver.resolveRowKeys(clazz, query.getRowKeys()));
        final QueryResult<CounterRows> queryResult = msCounterQuery.execute();
        final CounterRows rows = queryResult.get();

        final Map<String, List<ColumnResult>> result = new HashMap<String, List<ColumnResult>>();
        for (final String key : query.getRowKeys()) {
          final CounterRow row = rows.getByKey(RowKeyResolver.resolveRowKey(clazz, key));
          final CounterSlice columnSlice = row.getColumnSlice();
          final List<ColumnResult> columnResult = new LinkedList<ColumnResult>();
          final List<HCounterColumn> columnsInSlice = columnSlice.getColumns();
          for (final HCounterColumn col : columnsInSlice) {
            columnResult.add(new ColumnResult(col.getName(), col.getValue(), null, col.getTtl()));
          }
          result.put(key, columnResult);
        }

        return result;
      }
    });

  }

  @SuppressWarnings("unchecked")
  public Map<String, List<ColumnResult>> queryColumns(final QueryRequest query) {
    return executeQueryInternal(query, new QueryCallback<Map<String, List<ColumnResult>>>() {

      @SuppressWarnings("rawtypes")
      @Override
      public Map<String, List<ColumnResult>> execute(final Keyspace keyspace,
          final Serializer keySerializer, final Class<?> clazz) {
        final MultigetSliceQuery msQuery = HFactory.createMultigetSliceQuery(keyspace,
            keySerializer, StringSerializer.get(), StringSerializer.get());
        msQuery.setColumnFamily(query.getColumnFamily());
        msQuery.setColumnNames(query.getColumns().toArray(new Object[] {}));
        msQuery.setKeys(RowKeyResolver.resolveRowKeys(clazz, query.getRowKeys()));
        final QueryResult<Rows> queryResult = msQuery.execute();
        final Rows rows = queryResult.get();

        final Map<String, List<ColumnResult>> result = new HashMap<String, List<ColumnResult>>();
        for (final String key : query.getRowKeys()) {
          final Row row = rows.getByKey(RowKeyResolver.resolveRowKey(clazz, key));
          final ColumnSlice columnSlice = row.getColumnSlice();
          final List<ColumnResult> columnResult = new LinkedList<ColumnResult>();
          final List<HColumn> columnsInSlice = columnSlice.getColumns();
          for (final HColumn col : columnsInSlice) {
            columnResult.add(new ColumnResult(col.getName(), col.getValue(), col.getClock(),
                col.getTtl()));
          }
          result.put(key, columnResult);
        }

        return result;
      }
    });

  }

  @SuppressWarnings({ "rawtypes" })
  private <T> T executeQueryInternal(final CassandraRequest query, final QueryCallback<T> callback) {
    log.info("about to execute query: {}", query);
    final Cluster cluster = HFactory.getOrCreateCluster(query.getCluster(),
        new CassandraHostConfigurator(query.getHostsString()));
    final Keyspace keyspace = HFactory.createKeyspace(query.getKeyspace(), cluster);
    final Class<?> clazz = getKeyClass(query.getKeyspace(), query.getColumnFamily(), cluster);
    final Serializer keySerializer = SerializerResolver.getSerializer(clazz);
    return callback.execute(keyspace, keySerializer, clazz);
  }

  public List<CassandraResult> executeCql(final CqlQueryRequest query) {
    log.info("about to execute query: {}", query);
    final Cluster cluster = HFactory.getOrCreateCluster(query.getCluster(),
        new CassandraHostConfigurator(query.getHostsString()));
    final Keyspace keyspace = HFactory.createKeyspace(query.getKeyspace(), cluster);

    final CqlQuery<String, String, String> cqlQuery = new CqlQuery<String, String, String>(
        keyspace, StringSerializer.get(), StringSerializer.get(), StringSerializer.get());
    cqlQuery.setQuery(query.getCqlQuery());
    final QueryResult<CqlRows<String, String, String>> queryResult = cqlQuery.execute();
    final CqlRows<String, String, String> rows = queryResult.get();

    final List<CassandraResult> results = new LinkedList<CassandraResult>();
    for (final Row<String, String, String> row : rows.getList()) {
      final String key = row.getKey();
      final CassandraResult rowResult = new CassandraResult(key);
      final ColumnSlice<String, String> columnSlice = row.getColumnSlice();
      final List<HColumn<String, String>> columns = columnSlice.getColumns();

      for (final HColumn<String, String> hColumn : columns) {
        final String name = hColumn.getName();
        final String value = hColumn.getValue();

        rowResult.addValue(name, value);
      }
      results.add(rowResult);
    }
    return results;
  }

  private Class<?> getKeyClass(final String keyspaceName, final String columnFamily,
      final Cluster cluster) {
    final ColumnFamilyDefinition cfDef = getCfDef(cluster, keyspaceName, columnFamily);

    final Class<?> clazz;
    try {
      clazz = Class.forName(cfDef.getKeyValidationClass());
    } catch (final ClassNotFoundException e) {
      throw new IllegalStateException(String.format("Could not find class for [%s]"
          + cfDef.getKeyValidationClass()), e);
    }
    return clazz;
  }

  private ColumnFamilyDefinition getCfDef(final Cluster cluster, final String keyspace,
      final String columnFamily) {
    final KeyspaceDefinition keyspaceDefinition = cluster.describeKeyspace(keyspace);
    if (null == keyspaceDefinition) {
      throw new IllegalStateException(String.format("No keyspace [%s]", keyspace));
    }
    final List<ColumnFamilyDefinition> columnFamilyDefinitions = keyspaceDefinition.getCfDefs();
    if (null != keyspaceDefinition) {
      for (final ColumnFamilyDefinition columnFamilyDefinition : columnFamilyDefinitions) {
        if (columnFamilyDefinition.getName().equals(columnFamily)) {
          return columnFamilyDefinition;
        }
      }
    }
    throw new IllegalStateException(String.format("No column family [%s] in keyspace [%s] ",
        columnFamily, keyspace));
  }

}
