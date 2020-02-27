package com.salesbox.entity.enums;

/**
 * Created by hungnh on 7/27/15.
 */
public enum NoteSourceType
{
    MANUALLY(0),
    IMPORT_CONTACT_DEVICE(1);
    ;

    private int extension;

    NoteSourceType(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return extension;
    }
}
