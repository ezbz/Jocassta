package org.projectx.jocassta.transform;

import java.util.LinkedList;
import java.util.List;

import me.prettyprint.hector.api.ddl.ColumnDefinition;
import me.prettyprint.hector.api.ddl.ColumnFamilyDefinition;
import me.prettyprint.hector.api.ddl.KeyspaceDefinition;

import org.apache.cassandra.thrift.EndpointDetails;
import org.apache.cassandra.thrift.TokenRange;

import org.projectx.jocassta.domain.Column;
import org.projectx.jocassta.domain.ColumnFamily;
import org.projectx.jocassta.domain.EndpointDetailsElement;
import org.projectx.jocassta.domain.Keyspace;
import org.projectx.jocassta.domain.TokenRangeElement;

public class HectorDefinitionsTransformer {

  public List<Keyspace> transformKeyspaceDefs(final List<KeyspaceDefinition> keyspaceDefs) {
    final List<Keyspace> keyspaces = new LinkedList<Keyspace>();
    for (final KeyspaceDefinition keyspaceDef : keyspaceDefs) {
      final Keyspace keysapce = transformKeyspaceDef(keyspaceDef);
      keyspaces.add(keysapce);
    }
    return keyspaces;
  }

  public Keyspace transformKeyspaceDef(final KeyspaceDefinition keyspaceDef) {
    final Keyspace keysapce = new Keyspace(keyspaceDef);

    for (final ColumnFamilyDefinition columnFamilyDef : keyspaceDef.getCfDefs()) {
      final ColumnFamily columnFamily = new ColumnFamily(columnFamilyDef);

      for (final ColumnDefinition columnDef : columnFamilyDef.getColumnMetadata()) {
        final Column column = new Column(columnDef);
        columnFamily.addColumn(column);
      }
      keysapce.addColumnFamily(columnFamily);
    }
    return keysapce;
  }

  public List<TokenRangeElement> transformTokenRanges(final List<TokenRange> tokenRanges) {
    final List<TokenRangeElement> ring = new LinkedList<TokenRangeElement>();
    for (final TokenRange tokenRange : tokenRanges) {
      final TokenRangeElement tokenRangeElement = new TokenRangeElement(tokenRange);
      for (final EndpointDetails endpointDetails : tokenRange.getEndpoint_details()) {
        tokenRangeElement.addEndpointDetails(new EndpointDetailsElement(endpointDetails));
      }
      ring.add(tokenRangeElement);
    }
    return ring;
  }
}
