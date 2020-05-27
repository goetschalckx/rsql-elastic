package io.github.goetschalckx.rsql.elastic;

import cz.jirutka.rsql.parser.ast.ComparisonOperator;
import cz.jirutka.rsql.parser.ast.RSQLOperators;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * ComparisonOperatorProxy Enum
 *
 * Convert RSQLOperators to an Enumeration type.
 *
 * https://github.com/tennaito/rsql-jpa/blob/master/src/main/java/com/github/tennaito/rsql/parser/ast/ComparisonOperatorProxy.java
 *
 * @author AntonioRabelo
 * @since 2015-02-10
 */
public enum ComparisonOperatorProxy {

    EQUAL(RSQLOperators.EQUAL),
    NOT_EQUAL(RSQLOperators.NOT_EQUAL),
    GREATER_THAN(RSQLOperators.GREATER_THAN),
    GREATER_THAN_OR_EQUAL(RSQLOperators.GREATER_THAN_OR_EQUAL),
    LESS_THAN(RSQLOperators.LESS_THAN),
    LESS_THAN_OR_EQUAL(RSQLOperators.LESS_THAN_OR_EQUAL),
    IN(RSQLOperators.IN),
    NOT_IN(RSQLOperators.NOT_IN);

    private final ComparisonOperator operator;

    private static final Map<ComparisonOperator, ComparisonOperatorProxy> CACHE =
            Collections.synchronizedMap(new HashMap<>());

    static {
        for (ComparisonOperatorProxy proxy : values()) {
            CACHE.put(proxy.getOperator(), proxy);
        }
    }

    ComparisonOperatorProxy(ComparisonOperator operator) {
        this.operator = operator;
    }

    public ComparisonOperator getOperator() {
        return this.operator;
    }

    public static ComparisonOperatorProxy asEnum(ComparisonOperator operator) {
        return CACHE.get(operator);
    }

}
