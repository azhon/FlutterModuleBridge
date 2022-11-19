package com.azhon.bridge.element;

/**
 * createDate: 2022/11/17 on 15:37
 * desc:
 *
 * @author azhon
 */


public class ClassBodyElement extends DartElement {
    public ClassBodyElement(Class<?> cls) {
        super(cls);
    }

    @Override
    protected String getType() {
        return "CLASS_BODY";
    }
}
