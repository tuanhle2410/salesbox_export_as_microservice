package com.salesbox.entity;

import javax.persistence.*;

/**
 * User: luult
 * Date: 8/14/14
 */
@Entity
@Table(name = "localization")
public class Localization extends BaseEntity
{
    @Column(name = "key_code")
    private String keyCode;

    @Column(name = "value")
    private String value;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "language_id")
    private Language language;

    @Column(name = "key_list")
    private String keyList;

    public String getKeyCode()
    {
        return keyCode;
    }

    public void setKeyCode(String keyCode)
    {
        this.keyCode = keyCode;
    }

    public String getValue()
    {
        return value;
    }

    public void setValue(String value)
    {
        this.value = value;
    }

    public Language getLanguage()
    {
        return language;
    }

    public void setLanguage(Language language)
    {
        this.language = language;
    }

    public String getKeyList()
    {
        return keyList;
    }

    public void setKeyList(String keyList)
    {
        this.keyList = keyList;
    }
}
