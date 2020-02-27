package com.salesbox.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hungnh on 9/2/15.
 */
public class Content
{
    private String value;
    private Type type;
    private List<String> params = new ArrayList<>();

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    public List<String> getParams()
    {
        return params;
    }

    public void setParams(List<String> params)
    {
        this.params = params;
    }

    public enum Type
    {
        PLAIN_TEXT(null),
        HTML("text/html ; charset=UTF-8")
        ;

        private String value;

        private Type(String value)
        {
            this.value = value;
        }

        public String getValue()
        {
            return value;
        }
    }
}
