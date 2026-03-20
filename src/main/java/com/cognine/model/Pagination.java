package com.cognine.model;

import lombok.Data;

@Data
public class Pagination {
    public Object data;
    private int count;
    private boolean hasActiveSprintInProject;

    @Override
    public String toString(){
        return "Pagination [data="+data+",count="+count+"]";
    }
    
}
