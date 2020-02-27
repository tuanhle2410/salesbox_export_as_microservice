package com.salesbox.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 5/21/14
 * Time: 10:30 AM
 */
public enum FileType
{
    NONE(0),
    PNG(1),
    JPG(2),
    PDF(3),
    XLS(4),
    XLSX(5);

    private int extension;

    private FileType(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return this.extension;
    }
}
