package com.goetschalckx.rsql.elastic;

import cz.jirutka.rsql.parser.ast.AndNode;
import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.LogicalNode;
import cz.jirutka.rsql.parser.ast.LogicalOperator;
import cz.jirutka.rsql.parser.ast.NoArgRSQLVisitorAdapter;
import cz.jirutka.rsql.parser.ast.Node;
import cz.jirutka.rsql.parser.ast.OrNode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

public class ElasticRSQLVisitor extends NoArgRSQLVisitorAdapter<QueryBuilder> {

    private final ComparisonNodeInterpreter<QueryBuilder> comparisonNodeInterpreter;

    public ElasticRSQLVisitor(final ComparisonNodeInterpreter<QueryBuilder> comparisonNodeInterpreter) {
        this.comparisonNodeInterpreter = comparisonNodeInterpreter;
    }

    @Override
    public QueryBuilder visit(final AndNode andNode) {
        return visit((LogicalNode) andNode);
    }

    @Override
    public QueryBuilder visit(final OrNode orNode) {
        return visit((LogicalNode) orNode);
    }

    @Override
    public QueryBuilder visit(final ComparisonNode node) {
        return comparisonNodeInterpreter.interpret(node);
    }

    private QueryBuilder visit(final LogicalNode logicalNode) {
        final BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

        for (final Node childNode : logicalNode.getChildren()) {
            final QueryBuilder childNodeQueryBuilder = visitUnknownNode(childNode);

            if (logicalNode.getOperator() == LogicalOperator.AND) {
                boolQueryBuilder.must(childNodeQueryBuilder);
            } else {
                // if LogicalOperator.OR
                boolQueryBuilder.should(childNodeQueryBuilder);
            }
        }

        return boolQueryBuilder;
    }

    private QueryBuilder visitUnknownNode(final Node node) {
        if (node instanceof LogicalNode) {
            return visit((LogicalNode) node);
        } else {
            // if ComparisonNode
            return visit((ComparisonNode) node);
        }
    }
}
