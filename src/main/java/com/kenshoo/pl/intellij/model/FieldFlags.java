package com.kenshoo.pl.intellij.model;

public class FieldFlags {

    private final String displayName;
    private final boolean autoInc;
    private final boolean pk;

    public FieldFlags(String displayName, boolean pk, boolean autoInc) {
        this.displayName = displayName;
        this.autoInc = autoInc;
        this.pk = pk;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
