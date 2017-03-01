package com.goetschalckx.rsql.elastic;

import cz.jirutka.rsql.parser.ast.ComparisonNode;

@FunctionalInterface
public interface ComparisonNodeInterpreter<T> {

    T interpret(ComparisonNode comparisonNode);
}
