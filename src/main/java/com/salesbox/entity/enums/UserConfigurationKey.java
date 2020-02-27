package com.salesbox.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: dungpx
 * Date: 7/22/14
 * Time: 5:59 PM
 * To change this template use File | Settings | File Templates.
 */
public enum UserConfigurationKey
{

    LANGUAGE("LANGUAGE"),
    HELP_MODE("HELP_MODE"),
    COLOR("COLOR");



    private String name;

    private UserConfigurationKey(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return this.name;
    }
}
