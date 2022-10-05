package com.example.onieon.mvc.service;

import com.example.onieon.framework.data.domain.PageRequestParameter;
import com.example.onieon.mvc.domain.Board;
import com.example.onieon.mvc.parameter.BoardParameter;
import com.example.onieon.mvc.parameter.BoardSearchParameter;
import com.example.onieon.mvc.repository.BoardRepository;
import com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 게시판 서비스
 * @author onieon
 */
@Service
public class BoardService {

    @Autowired
    private BoardRepository repository;

    /**
     * 상세 정보 리턴
     * 목록 리턴
     * @return
     */
    public List<Board> getList(PageRequestParameter parameter){
       return repository.getList(parameter);
    }

    /**
     * 상세 정보 리턴.
     * @param boardSeq
     * @return
     */
    public Board get(int boardSeq){
        return repository.get(boardSeq);
    }

    /**
     * 등록/수정 처리
     * @param parameter
     */
    public void save(BoardParameter parameter){
        // 조회하여 리턴된 정보
        Board board = repository.get(parameter.getBoardSeq());
        if(board == null){
            repository.save(parameter);
        }else{
            repository.update(parameter);
        }

    }

    /**
     * 단순 반복문을 이용한 등록 처리.
     * @param list
     */
    public void saveList1(List<BoardParameter> list){
        for(BoardParameter parameter : list){
            repository.save(parameter);
        }

    }

    /**
     * 100개씩 배열에 담에서 일괄 등록 처리.
     * @param boardList
     */
    public void saveList2(List<BoardParameter> boardList){
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("boardList", boardList);
        repository.saveList(paramMap);
    }

    /**
     * 삭제 처리
     * @param boardSeq
     */
    public void delete(int boardSeq){
        repository.delete(boardSeq);
    }
}
