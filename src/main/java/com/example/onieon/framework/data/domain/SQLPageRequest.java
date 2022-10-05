package com.example.onieon.framework.data.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


/**
 * SQL 페이지 요청 정보 및 계산된 값
 * @author onieon
 */
@Data
public class SQLPageRequest {

    private int page;
    private int size;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private int limit;
    @JsonIgnore
    @ApiModelProperty(hidden = true)
    private int offset;

    public SQLPageRequest(int page, int size, int limit, int offset) {
        this.page = page;
        this.size = size;
        this.limit = limit;
        this.offset = offset;
    }
}
