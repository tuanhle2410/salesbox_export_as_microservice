package com.salesbox.entity.enums;

/**
 * [File description]
 *
 * @author lent
 * @version 1.0
 * @since 4/26/14
 */
public enum CardType
{
    NONE(0),
    VISA(1),
    MASTER(2),
    AMEX(3),
    DISCOVER(4);

// ------------------------------ FIELDS ------------------------------

    int extension;

// -------------------------- STATIC METHODS --------------------------

    CardType(int extension)
    {
        this.extension = extension;
    }

// --------------------------- CONSTRUCTORS ---------------------------

    public static CardType getCard(int extension)
    {
        try
        {
            return CardType.values()[extension];
        }
        catch (Exception ex)
        {
            return null;
        }
    }

// --------------------- GETTER / SETTER METHODS ---------------------

    public int getExtension()
    {
        return extension;
    }

    public void setExtension(int extension)
    {
        this.extension = extension;
    }
}
