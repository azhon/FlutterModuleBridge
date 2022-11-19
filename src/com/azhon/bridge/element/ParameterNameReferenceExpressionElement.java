package com.azhon.bridge.element;

/**
 * createDate: 2022/11/17 on 15:37
 * desc:
 *
 * @author azhon
 */


public class ParameterNameReferenceExpressionElement extends DartElement {
    public ParameterNameReferenceExpressionElement(Class<?> cls) {
        super(cls);
    }

    @Override
    protected String getType() {
        return "PARAMETER_NAME_REFERENCE_EXPRESSION";
    }
}
