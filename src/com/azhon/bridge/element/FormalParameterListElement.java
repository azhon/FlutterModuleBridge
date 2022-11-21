package com.azhon.bridge.element;

/**
 * createDate: 2022/11/17 on 15:37
 * desc:
 *
 * @author azhon
 */


public class FormalParameterListElement extends DartElement {
    public FormalParameterListElement(Class<?> cls) {
        super(cls);
    }

    @Override
    protected String getType() {
        return "FORMAL_PARAMETER_LIST";
    }
}
