package com.salesbox.entity.enums;

/**
 * User: luult
 * Date: 6/18/14
 */
public enum RelationshipType
{
    RED(-1),
    YELLOW(0),
    GREEN(1);

    private int extension;

    private RelationshipType(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return extension;
    }

    public void setExtension(int extension)
    {
        this.extension = extension;
    }

    public static RelationshipType getByExtension(int extension)
    {
        for (RelationshipType type : RelationshipType.values())
        {
            if (type.getExtension() == extension)
                return type;
        }
        return null;
    }
}
