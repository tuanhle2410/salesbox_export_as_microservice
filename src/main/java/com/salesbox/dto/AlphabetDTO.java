package com.salesbox.dto;

public class AlphabetDTO
{
    private Integer firstIndex = 0;
    private Integer total = 0;

    public AlphabetDTO() {
    }

    public AlphabetDTO(Integer firstIndex, Integer total) {
        this.firstIndex = firstIndex;
        this.total = total;
    }

    public Integer getFirstIndex() {
        return firstIndex;
    }

    public void setFirstIndex(Integer firstIndex) {
        this.firstIndex = firstIndex;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
