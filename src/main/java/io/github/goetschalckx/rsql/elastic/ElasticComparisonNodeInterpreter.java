package io.github.goetschalckx.rsql.elastic;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import org.elasticsearch.index.query.QueryBuilder;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

public class ElasticComparisonNodeInterpreter implements ComparisonNodeInterpreter<QueryBuilder> {

    private static final String WILDCARD_CHAR = "*";

    @Override
    public QueryBuilder interpret(final ComparisonNode comparisonNode) {
        final ComparisonOperatorProxy operator = ComparisonOperatorProxy.asEnum(comparisonNode.getOperator());
        switch (operator) {
            case NOT_EQUAL:
                return createNotEqual(comparisonNode);
            case GREATER_THAN:
                return createGreaterThanQuery(comparisonNode);
            case GREATER_THAN_OR_EQUAL:
                return createGreaterThanOrEqualQuery(comparisonNode);
            case LESS_THAN:
                return createLessThanQuery(comparisonNode);
            case LESS_THAN_OR_EQUAL:
                return createLessThanOrEqualQuery(comparisonNode);
            case IN:
                return createInQuery(comparisonNode);
            case NOT_IN:
                return createNotInQuery(comparisonNode);
            default: // EQUAL
                return createEqualQuery(comparisonNode);
        }
    }

    private static QueryBuilder createEqualQuery(final ComparisonNode comparisonNode) {
        final String firstArg = firstArg(comparisonNode);
        final String selector = comparisonNode.getSelector();

        if (firstArg.contains(WILDCARD_CHAR)) {
            return wildcardQuery(selector, firstArg);
        } else {
            return termQuery(selector, firstArg);
        }
    }

    private static QueryBuilder createNotEqual(final ComparisonNode comparisonNode) {
        final String firstArg = firstArg(comparisonNode);
        final String selector = comparisonNode.getSelector();

        final QueryBuilder termQuery;
        if (firstArg.contains(WILDCARD_CHAR)) {
            termQuery = wildcardQuery(selector, firstArg);
        } else {
            termQuery = termQuery(selector, firstArg);
        }

        return boolQuery().mustNot(termQuery);
    }

    private static QueryBuilder createInQuery(final ComparisonNode comparisonNode) {
        return termsQuery(comparisonNode.getSelector(), comparisonNode.getArguments());
    }

    private static QueryBuilder createNotInQuery(final ComparisonNode comparisonNode) {
        final QueryBuilder termsQuery = termsQuery(comparisonNode.getSelector(), comparisonNode.getArguments());
        return boolQuery().mustNot(termsQuery);
    }

    private static QueryBuilder createGreaterThanQuery(final ComparisonNode comparisonNode) {
        return rangeQuery(comparisonNode.getSelector()).gt(firstArg(comparisonNode));
    }

    private static QueryBuilder createGreaterThanOrEqualQuery(final ComparisonNode comparisonNode) {
        return rangeQuery(comparisonNode.getSelector()).gte(firstArg(comparisonNode));
    }

    private static QueryBuilder createLessThanQuery(final ComparisonNode comparisonNode) {
        return rangeQuery(comparisonNode.getSelector()).lt(firstArg(comparisonNode));
    }

    private static QueryBuilder createLessThanOrEqualQuery(final ComparisonNode comparisonNode) {
        return rangeQuery(comparisonNode.getSelector()).lte(firstArg(comparisonNode));
    }

    private static String firstArg(final ComparisonNode comparisonNode) {
        return comparisonNode.getArguments().get(0);
    }

}
