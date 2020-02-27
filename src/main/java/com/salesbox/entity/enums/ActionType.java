package com.salesbox.entity.enums;

/**
 * Created by hunglv on 8/26/14.
 */

public enum ActionType
{
    IMPORT(0),
    EXPORT(1);

    private int extension;

    private ActionType(int extension)
    {
        this.extension =  extension;
    }

    public int getExtension()
    {
        return this.extension;
    }
}
