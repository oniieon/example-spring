package com.example.onieon.mvc.repository;

import com.example.onieon.framework.data.domain.PageRequestParameter;
import com.example.onieon.mvc.domain.Board;
import com.example.onieon.mvc.parameter.BoardParameter;
import com.example.onieon.mvc.parameter.BoardSearchParameter;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 게시판 Repository
 * @author oineon
 */
@Repository
public interface BoardRepository {

    List<Board> getList(PageRequestParameter parameter);

    Board get(int boardSeq);

    int save(BoardParameter board);

    int saveList(Map<String, Object> paramMap);

    void update(BoardParameter board);

    void delete(int boardSeq);
}
