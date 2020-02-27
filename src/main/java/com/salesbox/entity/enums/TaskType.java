package com.salesbox.entity.enums;

/**
 * User: luult
 * Date: 5/21/14
 */
public enum TaskType
{
    PRIORITISED(0),
    MANUAL(1),
    INVITED(2),
    DISTRIBUTE(3);

    private int extension;

    private TaskType(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return this.extension;
    }
}
