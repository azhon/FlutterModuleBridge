package com.azhon.bridge.element;

/**
 * createDate: 2022/11/17 on 15:37
 * desc:
 *
 * @author azhon
 */


public class ArgumentListElement extends DartElement {
    public ArgumentListElement(Class<?> cls) {
        super(cls);
    }

    @Override
    protected String getType() {
        return "ARGUMENT_LIST";
    }
}
