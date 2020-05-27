package io.github.goetschalckx.rsql.elastic;

import cz.jirutka.rsql.parser.ast.ComparisonNode;
import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import org.elasticsearch.index.query.QueryBuilder;
import org.junit.Test;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;
import static org.junit.Assert.assertEquals;

public class ElasticComparisonNodeInterpreterTest {

    private final ElasticComparisonNodeInterpreter interpreter = new ElasticComparisonNodeInterpreter();
    private final String selector = "foo";
    private final String arg = "90.01";
    private final String wildcardArg = "bar*";
    private final List<String> args = singletonList(arg);
    private final List<String> wildcardArgs = singletonList(wildcardArg);

    @Test
    public void testInterpretEquals() {
        // Arrange
        ComparisonOperator equals = new ComparisonOperator("==", false);
        QueryBuilder expectedQuery = termQuery(selector, arg);
        ComparisonNode node = new ComparisonNode(equals, selector, args);

        // Act
        QueryBuilder actualQuery = interpreter.interpret(node);

        // Assert
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void testInterpretEqualsWildcard() {
        // Arrange
        ComparisonOperator equals = new ComparisonOperator("==", false);
        QueryBuilder expectedQuery = wildcardQuery(selector, wildcardArg);
        ComparisonNode node = new ComparisonNode(equals, selector, wildcardArgs);

        // Act
        QueryBuilder actualQuery = interpreter.interpret(node);

        // Assert
        assertEquals(expectedQuery, actualQuery);
    }


    @Test
    public void testInterpretNotEquals() {
        // Arrange
        ComparisonOperator notEquals = new ComparisonOperator("!=", false);
        QueryBuilder expectedQuery = boolQuery().mustNot(termQuery(selector, arg));
        ComparisonNode node = new ComparisonNode(notEquals, selector, args);

        // Act
        QueryBuilder actualQuery = interpreter.interpret(node);

        // Assert
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void testInterpretNotEqualsWildcard() {
        // Arrange
        ComparisonOperator notEquals = new ComparisonOperator("!=", false);
        QueryBuilder expectedQuery = boolQuery().mustNot(wildcardQuery(selector, wildcardArg));
        ComparisonNode node = new ComparisonNode(notEquals, selector, wildcardArgs);

        // Act
        QueryBuilder actualQuery = interpreter.interpret(node);

        // Assert
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void testInterpretGreaterThan() {
        // Arrange
        ComparisonOperator greaterThan = new ComparisonOperator("=gt=", false);
        QueryBuilder expectedQuery = rangeQuery(selector).gt(arg);
        ComparisonNode node = new ComparisonNode(greaterThan, selector, args);

        // Act
        QueryBuilder actualQuery = interpreter.interpret(node);

        // Assert
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void testInterpretGreaterThanOrEquals() {
        // Arrange
        ComparisonOperator greaterThanOrEquals = new ComparisonOperator("=ge=", false);
        QueryBuilder expectedQuery = rangeQuery(selector).gte(arg);
        ComparisonNode node = new ComparisonNode(greaterThanOrEquals, selector, args);

        // Act
        QueryBuilder actualQuery = interpreter.interpret(node);

        // Assert
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void testInterpretLessThan() {
        // Arrange
        ComparisonOperator lessThan = new ComparisonOperator("=lt=", false);
        QueryBuilder expectedQuery = rangeQuery(selector).lt(arg);
        ComparisonNode node = new ComparisonNode(lessThan, selector, args);

        // Act
        QueryBuilder actualQuery = interpreter.interpret(node);

        // Assert
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void testInterpretLessThanOrEquals() {
        // Arrange
        ComparisonOperator lessThanOrEquals = new ComparisonOperator("=le=", false);
        QueryBuilder expectedQuery = rangeQuery(selector).lte(arg);
        ComparisonNode node = new ComparisonNode(lessThanOrEquals, selector, args);

        // Act
        QueryBuilder actualQuery = interpreter.interpret(node);

        // Assert
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void testIn() {
        // Arrange
        ComparisonOperator in = new ComparisonOperator("=in=", true);
        QueryBuilder expectedQuery = termsQuery(selector, args);
        ComparisonNode node = new ComparisonNode(in, selector, args);

        // Act
        QueryBuilder actualQuery = interpreter.interpret(node);

        // Assert
        assertEquals(expectedQuery, actualQuery);
    }

    @Test
    public void testOut() {
        // Arrange
        ComparisonOperator out = new ComparisonOperator("=out=", true);
        QueryBuilder termsQuery = termsQuery(selector, args);
        QueryBuilder expectedQuery = boolQuery().mustNot(termsQuery);
        ComparisonNode node = new ComparisonNode(out, selector, args);

        // Act
        QueryBuilder actualQuery = interpreter.interpret(node);

        // Assert
        assertEquals(expectedQuery, actualQuery);
    }
}
