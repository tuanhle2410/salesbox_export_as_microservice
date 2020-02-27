package com.salesbox.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 5/7/14
 * Time: 2:33 PM
 */
public enum UnitType
{
    DEFAULT(0),
    UNIT(1);

    private int extension;

    private UnitType(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return this.extension;
    }
}
