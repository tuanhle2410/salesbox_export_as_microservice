package com.salesbox.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: hunglv
 * Date: 10/2/14
 */
public class ResponseImportDTO
{
    private int numberSuccessImport;
    private int numberFailImport;
    private List<String> errorList = new ArrayList<>();
    private int numberSuccessOrderRow;
    private int numberFailOrderRow;
    private String failFileId;
    private String failFileName;

    public int getNumberSuccessImport()
    {
        return numberSuccessImport;
    }

    public void setNumberSuccessImport(int numberSuccessImport)
    {
        this.numberSuccessImport = numberSuccessImport;
    }

    public int getNumberFailImport()
    {
        return numberFailImport;
    }

    public void setNumberFailImport(int numberFailImport)
    {
        this.numberFailImport = numberFailImport;
    }

    public List<String> getErrorList()
    {
        return errorList;
    }

    public void setErrorList(List<String> errorList)
    {
        this.errorList = errorList;
    }

    public int getNumberSuccessOrderRow()
    {
        return numberSuccessOrderRow;
    }

    public void setNumberSuccessOrderRow(int numberSuccessOrderRow)
    {
        this.numberSuccessOrderRow = numberSuccessOrderRow;
    }

    public int getNumberFailOrderRow()
    {
        return numberFailOrderRow;
    }

    public void setNumberFailOrderRow(int numberFailOrderRow)
    {
        this.numberFailOrderRow = numberFailOrderRow;
    }

    public String getFailFileId()
    {
        return failFileId;
    }

    public void setFailFileId(String failFileId)
    {
        this.failFileId = failFileId;
    }

    public String getFailFileName()
    {
        return failFileName;
    }

    public void setFailFileName(String failFileName)
    {
        this.failFileName = failFileName;
    }
}
