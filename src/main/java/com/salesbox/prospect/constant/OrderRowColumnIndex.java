package com.salesbox.prospect.constant;

/**
 * Created by hunglv on 10/2/14.
 */
public enum  OrderRowColumnIndex
{
    PROSPECT_ID(0),
    PRODUCT_NAME(1),
    TYPE(2),
    NUMBER_OF_UNITS(3),
    PRICE_PER_UNIT(4),
    DELIVERY_START_DATE(5),
    DELIVERY_END_DATE(6),
    MARGIN(7);

    private int index;

    private OrderRowColumnIndex(int index)
    {
        this.index = index;
    }

    public int getIndex()
    {
        return this.index;
    }
}
