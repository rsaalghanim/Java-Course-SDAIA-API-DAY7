package org.example.dto;

import jakarta.ws.rs.QueryParam;

public class JobFilterDto {
    @QueryParam("minsal") Double minsal;
    @QueryParam("limit") Integer limit;
    @QueryParam("offset") int offset;

    public Double getMinsal() {
        return minsal;
    }

    public void setMinsal(Double minsal) {
        this.minsal = minsal;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}


