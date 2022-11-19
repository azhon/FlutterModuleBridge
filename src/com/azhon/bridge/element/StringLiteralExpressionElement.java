package com.azhon.bridge.element;

/**
 * createDate: 2022/11/17 on 15:37
 * desc:
 *
 * @author azhon
 */


public class StringLiteralExpressionElement extends DartElement {
    public StringLiteralExpressionElement(Class<?> cls) {
        super(cls);
    }

    @Override
    protected String getType() {
        return "STRING_LITERAL_EXPRESSION";
    }
}
