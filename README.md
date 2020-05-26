[![Build Status](https://travis-ci.org/goetschalckx/rsql-elastic.svg?branch=master)](https://travis-ci.org/goetschalckx/rsql-elastic)
[![License](https://img.shields.io/github/license/goetschalckx/rsql-elastic?color=4DC71F)](https://github.com/goetschalckx/rsql-elastic/blob/master/LICENSE)

[![Coverage](https://codecov.io/gh/goetschalckx/rsql-elastic/branch/master/graph/badge.svg)](https://codecov.io/gh/goetschalckx/rsql-elastic)
[![Codacy](https://app.codacy.com/project/badge/Grade/a1ea40fb7ac744b08c300526708dc966)](https://www.codacy.com/gh/goetschalckx/rsql-elastic?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=goetschalckx/rsql-elastic&amp;utm_campaign=Badge_Grade)
[![Codacy Coverage](https://app.codacy.com/project/badge/Coverage/a1ea40fb7ac744b08c300526708dc966)](https://www.codacy.com/gh/goetschalckx/rsql-elastic?utm_source=github.com&utm_medium=referral&utm_content=goetschalckx/rsql-elastic&utm_campaign=Badge_Coverage)
[![CodeFactor](https://www.codefactor.io/repository/github/goetschalckx/rsql-elastic/badge)](https://www.codefactor.io/repository/github/goetschalckx/rsql-elastic)

[![Release](https://img.shields.io/nexus/r/io.github.goetschalckx/rsql-elastic?color=4DC71F&label=release&server=https%3A%2F%2Foss.sonatype.org%2F)](https://search.maven.org/artifact/io.github.goetschalckx/rsql-elastic)
[![Snapshot](https://img.shields.io/nexus/s/io.github.goetschalckx/rsql-elastic?label=snapshot&server=https%3A%2F%2Foss.sonatype.org%2F)](https://oss.sonatype.org/#nexus-search;quick~rsql-elastic)

# rsql-elastic
by Eric Goetschalckx

Generates Elasticsearch queries from RSQL queries

## Background
[RSQL](https://github.com/jirutka/rsql-parser) is a Java implementation of [FIQL](https://fiql-parser.readthedocs.io/en/stable/)

Existing RSQL libraries include:
-   [JPA](https://github.com/tennaito/rsql-jpa)
-   [MongoDB](https://github.com/tennaito/rsql-jpa)
-   [Spring Data JPA](https://github.com/perplexhub/rsql-jpa-specification)

This library provides an Elasticsearch implementation of RSQL
