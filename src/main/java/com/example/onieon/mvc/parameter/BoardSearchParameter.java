package com.example.onieon.mvc.parameter;

import com.example.onieon.mvc.domain.BoardType;
import lombok.Data;

import java.util.List;

@Data
public class BoardSearchParameter {

    private String keyword;
    private List<BoardType> boardTypes;

    public BoardSearchParameter() {
    }
}
