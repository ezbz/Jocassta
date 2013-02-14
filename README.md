Jocassta
========

Web Service for accessing Cassandra data and meta-data via restful, JSON-based HTTP API.

Methods
============

* Keyspaces

```api/keyspaces?hosts=cass1:9160,cass2:9160``` - shows a list of keyspaces and their column families / configuration

```api/keyspace/{keyspace}/?hosts=cass1:9160,cass2:9160``` - show the information of an individual keyspace

* Ring

```api/ring?hosts=cass1:9160``` - show the ring

* Schema Versions

```api/schema/versions?hosts=cass1:9160``` - show the schema versions

* Thrift Version

```api/thrift?hosts=cass1:9160``` - show the thrift versions

* CQL Query

```api/cql/?query=select+*+from+MyCf+limit+2&hosts=cass1:9160,cass2:9160``` - execute a generic CQL query

* Generic Query

```api/query/{cluster}/{keyspace}/{column_family}/{row_key}/{column_key}?hosts=hosts=cass1:9160,cass2:9160``` - query an individual column value

```api/query/{cluster}/{keyspace}/{column_family}/{row_key}/{column_key}/counter?hosts=hosts=cass1:9160,cass2:9160``` - query an individual counter column value

You can also post the query to ```api/query```

    {
        "cluster": "test_cluster",
        "columnFamily": "my_column_family",
        "columns": [
            "mycol1",
            "mycol2"
        ],
        "hosts": [
            "localhost:9160"
        ],
        "keyspace": "my_keyspace",
        "rowKeys": [
            "mykey1",
            "mykey2"
        ]
    }
    
Or execute a range query the same way:

    {
        "cluster": "test_cluster",
        "hosts": [
            "localhost:9160"
        ],
        "limit": 100,
        "columnFamily": "my_column_family",
        "keyspace": "my_keyspace",
        "rowKeys": [
            "mykey1",
            "mykey2"
        ],
        "startColumn": "mycol1",
        "endColumn": "mycol1",
        "reverseOrder": false
    }
