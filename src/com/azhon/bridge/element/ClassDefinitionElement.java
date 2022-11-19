package com.azhon.bridge.element;

/**
 * createDate: 2022/11/17 on 15:25
 * desc:
 *
 * @author azhon
 */


public class ClassDefinitionElement extends DartElement {

    public ClassDefinitionElement(Class<?> cls) {
        super(cls);
    }

    @Override
    protected String getType() {
        return "CLASS_DEFINITION";
    }
}
