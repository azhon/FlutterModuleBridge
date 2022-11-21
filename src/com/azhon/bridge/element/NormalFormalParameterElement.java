package com.azhon.bridge.element;

/**
 * createDate: 2022/11/17 on 15:37
 * desc:
 *
 * @author azhon
 */


public class NormalFormalParameterElement extends DartElement {
    public NormalFormalParameterElement(Class<?> cls) {
        super(cls);
    }

    @Override
    protected String getType() {
        return "NORMAL_FORMAL_PARAMETER";
    }
}
