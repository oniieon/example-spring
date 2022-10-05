package com.example.onieon.framework.data.domain;

/**
 * 페이지 요청정보와 파라메터 정보
 * @author onieon
 * @param <T>
 */
public class PageRequestParameter<T> {

    private SQLPageRequest pageRequest;
    private T parameter;

    public PageRequestParameter(SQLPageRequest pageRequest, T parameter) {
        this.pageRequest = pageRequest;
        this.parameter = parameter;
    }
}
