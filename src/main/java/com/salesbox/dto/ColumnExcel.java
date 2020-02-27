package com.salesbox.dto;

/**
 * Created with IntelliJ IDEA.
 * User: dungpx
 * Date: 8/26/14
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class ColumnExcel
{
    String name;
    int index;
    boolean required;
    int cellType;
    boolean isCheck = false;

    public ColumnExcel()
    {
    }

    public ColumnExcel(String name, int index, boolean required)
    {
        this.name = name;
        this.index = index;
        this.required = required;
    }

    public ColumnExcel(String name, int index, boolean required, int cellType)
    {
        this.name = name;
        this.index = index;
        this.required = required;
        this.cellType = cellType;
    }

    public ColumnExcel(String name, int index, int cellType)
    {
        this.name = name;
        this.index = index;
        this.cellType = cellType;
    }

    public ColumnExcel(String name, int index)
    {
        this.name = name;
        this.index = index;
        this.required = false;
    }

    public boolean isCheck()
    {
        return isCheck;
    }

    public void setCheck(boolean isCheck)
    {
        this.isCheck = isCheck;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getIndex()
    {
        return index;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public boolean isRequired()
    {
        return required;
    }

    public void setRequired(boolean required)
    {
        this.required = required;
    }

    public int getCellType()
    {
        return cellType;
    }

    public void setCellType(int cellType)
    {
        this.cellType = cellType;
    }
}
