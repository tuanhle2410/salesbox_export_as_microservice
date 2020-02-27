package com.salesbox.entity.enums;

import java.io.Serializable;

/**
 * Custom field types
 * Created by NEO on 1/11/2017.
 */
public enum CustomFieldType implements Serializable
{
    DROPDOWN(0),
    TEXT_BOX(1),
    NUMBER(2),
    DATE(3),
    CHECK_BOXES(4),
    URL(5),
    TEXT(6),
    PRODUCT_TAG(7);

    private int extension;

    private CustomFieldType(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return this.extension;
    }
}
