package com.salesbox.dto;


import java.util.HashMap;
import java.util.Map;

public class CountAlphabetDTO
{
    private Integer countDistinct;
    private Integer countAll;
    private Map<String, AlphabetDTO> alphabetDTOMap = new HashMap<>();
    public Integer getCountDistinct() {
        return countDistinct;
    }

    public void setCountDistinct(Integer countDistinct) {
        this.countDistinct = countDistinct;
    }

    public Integer getCountAll() {
        return countAll;
    }

    public void setCountAll(Integer countAll) {
        this.countAll = countAll;
    }

    public Map<String, AlphabetDTO> getAlphabetDTOMap() {
        return alphabetDTOMap;
    }

    public void setAlphabetDTOMap(Map<String, AlphabetDTO> alphabetDTOMap) {
        this.alphabetDTOMap = alphabetDTOMap;
    }

    public void add(String key, AlphabetDTO value) {
        alphabetDTOMap.put(key, value);
    }
}
