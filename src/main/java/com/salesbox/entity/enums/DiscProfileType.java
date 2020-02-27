package com.salesbox.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 5/7/14
 * Time: 2:36 PM
 */
public enum DiscProfileType
{
    NONE(0),
    RED(1),
    GREEN(2),
    BLUE(3),
    YELLOW(4);

    private int extension;

    private DiscProfileType(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return this.extension;
    }

    public static DiscProfileType getDiscProfile(int extension)
    {
        try
        {
            return DiscProfileType.values()[extension];
        }
        catch (Exception ex)
        {
            return null;
        }
    }
}
