package com.elite.thymeleaf.dialect;

import org.thymeleaf.Arguments;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.element.AbstractElementProcessor;

import static com.elite.thymeleaf.dialect.util.ExpressionParserUtil.evaluateExpressionAttribute;

public class ConfirmButtonProcessor extends AbstractElementProcessor {
    public static final String NAME = "confirmbutton";

    private static final String CLICK_HANDLER = "var that = $(this);"
            + "if(that.is('.disabled') || that.is('[disabled]')){return false;}"
            + "teamecho.util.showConfirmDialog(that);";

    public ConfirmButtonProcessor() {
        super(NAME);
    }

    @Override
    protected ProcessorResult processElement(Arguments arguments, Element element) {
        Element button = new Element("a");
        button.setAttribute("class", element.getAttributeValue("class"));
        button.setAttribute("data-teamecho-dialog-title",
                evaluateExpressionAttribute(arguments, element, "teamecho:dialog-title"));
        button.setAttribute("data-teamecho-dialog-message",
                evaluateExpressionAttribute(arguments, element, "teamecho:dialog-message"));
        button.setAttribute("data-teamecho-action-text",
                evaluateExpressionAttribute(arguments, element, "teamecho:action-text"));
        button.setAttribute("data-teamecho-action-href",
                evaluateExpressionAttribute(arguments, element, "teamecho:action-href"));

        button.setAttribute("onclick", CLICK_HANDLER);

        element.moveAllChildren(button);

        element.getParent().insertBefore(element, button);
        element.getParent().removeChild(element);

        return ProcessorResult.OK;
    }

    @Override
    public int getPrecedence() {
        return 1;
    }
}
