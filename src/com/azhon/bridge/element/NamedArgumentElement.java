package com.azhon.bridge.element;

/**
 * createDate: 2022/11/17 on 15:37
 * desc:
 *
 * @author azhon
 */


public class NamedArgumentElement extends DartElement {
    public NamedArgumentElement(Class<?> cls) {
        super(cls);
    }

    @Override
    protected String getType() {
        return "NAMED_ARGUMENT";
    }
}
