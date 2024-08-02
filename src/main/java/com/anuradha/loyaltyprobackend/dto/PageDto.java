package com.anuradha.loyaltyprobackend.dto;

import java.util.List;

public class PageDto<T> {

    private long totalRows;
    private int totalPages;
    private int currentPage;
    private List<T> data;

    public PageDto() {
    }

    public PageDto(long totalRows, int totalPages, int currentPage, List<T> data) {
        this.totalRows = totalRows;
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.data = data;
    }

    public long getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(long totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
