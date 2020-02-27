package com.salesbox.entity.enums;

/**
 * Created with IntelliJ IDEA.
 * User: tienhd
 * Date: 10/9/14
 * Time: 2:55 PM
 */
public enum SubscriptionProductType
{
    NORMAL_1999_PER_USER_PER_MONTH(0),
    IOS(1),
    AMAZON_2000_PER_USER_PER_MONTH(2),

    Salesbox_Ultimate_Yearly_USD(3),
    Salesbox_Ultimate_Monthly_USD(4),
    Salesbox_Ultra_Yearly_USD(5),
    Salesbox_Ultra_Monthly_USD(6),
    Salesbox_Super_Yearly_USD(7),
    Salesbox_Super_Monthly_USD(8),

    Salesbox_Ultimate_Yearly_SEK(9),
    Salesbox_Ultimate_Monthly_SEK(10),
    Salesbox_Ultra_Yearly_SEK(11),
    Salesbox_Ultra_Monthly_SEK(12),
    Salesbox_Super_Yearly_SEK(13),
    Salesbox_Super_Monthly_SEK(14),

    Salesbox_Ultimate_Yearly_EUR(15),
    Salesbox_Ultimate_Monthly_EUR(16),
    Salesbox_Ultra_Yearly_EUR(17),
    Salesbox_Ultra_Monthly_EUR(18),
    Salesbox_Super_Yearly_EUR(19),
    Salesbox_Super_Monthly_EUR(20),

    Salesbox_Basic_Yearly_USD(21),
    Salesbox_Basic_Monthly_USD(22),
    Salesbox_Basic_Yearly_SEK(23),
    Salesbox_Basic_Monthly_SEK(24),
    Salesbox_Basic_Yearly_EUR(25),
    Salesbox_Basic_Monthly_EUR(26)

    ;

    private int extension;

    private SubscriptionProductType(int extension)
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
    }
