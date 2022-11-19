package com.azhon.bridge.element;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;

/**
 * createDate: 2022/11/17 on 15:25
 * desc:
 *
 * @author azhon
 */


abstract class DartElement {
    protected final Class<?> cls;

    public DartElement(Class<?> cls) {
        this.cls = cls;
    }

    protected abstract String getType();

    public IElementType get() {
        try {
            return (IElementType) cls.getField(getType()).get(new Object());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public TokenSet getTokenSet() {
        return TokenSet.create(get());
    }
}
