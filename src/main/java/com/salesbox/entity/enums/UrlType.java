package com.salesbox.entity.enums;

/**
 * URL types
 * Created by NEO on 1/11/2017.
 */
public enum UrlType
{
    LINKEDIN(0),
    FACEBOOK(1),
    TWITTER(2),
    SKYPE(3),
    INSTAGRAM(4),
    WEB(5),
    GOOGLE_SEARCH(6);

    private int extension;

    private UrlType(int extension)
    {
        this.extension = extension;
    }

    public int getExtension()
    {
        return this.extension;
    }
}
