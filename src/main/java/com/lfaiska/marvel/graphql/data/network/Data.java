package com.lfaiska.marvel.graphql.data.network;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {
    private int offset;
    private int limit;
    private int total;
    private int count;
    private List<JsonNode> results = new ArrayList<>();

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<JsonNode> getResults() {
        return results;
    }

    public void setResults(List<JsonNode> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Data{" +
                "offset=" + offset +
                ", limit='" + limit + '\'' +
                ", total='" + total + '\'' +
                ", count='" + count + '\'' +
                ", results='" + results.toString() + '\'' +
                '}';
    }
}
