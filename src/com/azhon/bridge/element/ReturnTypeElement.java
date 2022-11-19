package com.azhon.bridge.element;

/**
 * createDate: 2022/11/17 on 15:37
 * desc:
 *
 * @author azhon
 */


public class ReturnTypeElement extends DartElement {
    public ReturnTypeElement(Class<?> cls) {
        super(cls);
    }

    @Override
    protected String getType() {
        return "RETURN_TYPE";
    }
}
