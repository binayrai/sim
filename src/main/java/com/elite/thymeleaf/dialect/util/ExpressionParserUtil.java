package com.elite.thymeleaf.dialect.util;

import org.thymeleaf.Arguments;
import org.thymeleaf.Configuration;
import org.thymeleaf.dom.Element;
import org.thymeleaf.standard.expression.IStandardExpression;
import org.thymeleaf.standard.expression.IStandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressions;

import java.util.Objects;

/**
 * Small helper class which wraps a few of the calls necessary to evaluate a spring EL expression
 * within the thymleaf context
 *
 * @author Thomas Scheinecker, Catalysts GmbH
 */
public final class ExpressionParserUtil {
    private ExpressionParserUtil() {
    }

    /**
     * Evaluates the expression given by the specified attribute on the given element
     *
     * @param arguments     the required arguments /context used for processing
     * @param element       the element to receive the property from
     * @param attributeName the name of the attribute holding the expression to evaluate
     * @return the evaluated expression specified by the given attribute
     */
    @SuppressWarnings("unchecked")
    public static <T> T evaluateExpressionAttribute(Arguments arguments, Element element, String attributeName) {
        final String propertyExpression = element.getAttributeValue(attributeName);

        return (T) (Objects.isNull(propertyExpression) ? null : evaluateExpression(arguments, propertyExpression));
    }

    /**
     * Evaluates the given property expression within the given argumets / context
     *
     * @param arguments          the required arguments /context used for processing
     * @param propertyExpression the property expression to be evaluated against the given arguments / context
     * @return the evaluated expression
     */
    @SuppressWarnings("unchecked")
    public static <T> T evaluateExpression(Arguments arguments, String propertyExpression) {
        final Configuration configuration = arguments.getConfiguration();
        final IStandardExpressionParser expressionParser = StandardExpressions.getExpressionParser(configuration);
        final IStandardExpression expression = expressionParser.parseExpression(configuration, arguments, propertyExpression);
        return (T) expression.execute(configuration, arguments);
    }
}
