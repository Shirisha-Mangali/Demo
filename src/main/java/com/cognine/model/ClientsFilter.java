package com.cognine.model;

import lombok.Data;

@Data
public class ClientsFilter {
    private String searchClientName;
    private String searchRegion;
    private String searchAddress;
    private String searchCity;
    private String searchState;
    private String searchCountry;
    private String searchActive;
    private String sortColumn;
    private String sortType;
    private int pageNum;
    private int pageSize;
}
