package com.azhon.bridge.element;

/**
 * createDate: 2022/11/17 on 15:37
 * desc:
 *
 * @author azhon
 */


public class MethodDeclarationElement extends DartElement {
    public MethodDeclarationElement(Class<?> cls) {
        super(cls);
    }

    @Override
    protected String getType() {
        return "METHOD_DECLARATION";
    }
}
