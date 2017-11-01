package com.lfaiska.marvel.graphql.data.network;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
    private String code;
    private String status;
    private Data data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code=" + code +
                ", status='" + status + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
