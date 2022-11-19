package com.azhon.bridge.element;

/**
 * createDate: 2022/11/17 on 15:35
 * desc:
 *
 * @author azhon
 */


public class ComponentNameElement extends DartElement {
    public ComponentNameElement(Class<?> cls) {
        super(cls);
    }

    @Override
    protected String getType() {
        return "COMPONENT_NAME";
    }
}
