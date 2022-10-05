package com.example.onieon.mvc.controller;

import com.example.onieon.configuration.exception.BaseException;
import com.example.onieon.configuration.http.BaseResponse;
import com.example.onieon.configuration.http.BaseResponseCode;
import com.example.onieon.framework.data.domain.PageRequestParameter;
import com.example.onieon.framework.data.domain.SQLPageRequest;
import com.example.onieon.mvc.domain.Board;
import com.example.onieon.mvc.parameter.BoardParameter;
import com.example.onieon.mvc.parameter.BoardSearchParameter;
import com.example.onieon.mvc.repository.BoardRepository;
import com.example.onieon.mvc.service.BoardService;
import io.swagger.annotations.*;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 게시판 컨트롤러
 * @author onieon
 */
@RestController
@RequestMapping("/board")
@Api(tags ="게시판 API")
public class BoardController {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private BoardService boardService;

    /**
     * 목록 정보 리턴
     * 목록 리턴
     * @return
     */
    @ApiOperation(value="목록 조회", notes = "게시물 번호에 해당하는 목록 정보를 조회할 수 있습니다.")
    @GetMapping
    public BaseResponse<List<Board>> getList(
            @ApiParam BoardSearchParameter parameter,
            @ApiParam SQLPageRequest pageRequest){
        logger.info("pageRequest : {} ", pageRequest);
        PageRequestParameter<BoardSearchParameter> pageRequestParameter = new PageRequestParameter<BoardSearchParameter>(pageRequest, parameter );
        return new BaseResponse<List<Board>>(boardService.getList(pageRequestParameter));
    }

    /**
     * 상세 정보 리턴.
     * @param boardSeq
     * @return
     */
    @ApiOperation(value="상세 조회", notes = "게시물 번호에 해당하는 상세 정보를 조회할 수 있습니다.")
    @GetMapping("/{boardSeq}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardSeq", value="게시물 번호", example = "1")
    })
    public BaseResponse<Board> get(@PathVariable int boardSeq){
        Board board = boardService.get(boardSeq);
        if(board == null){
            throw new BaseException(BaseResponseCode.DATA_IS_NULL, new String[] {"게시물"});
        }
        return new BaseResponse<Board>(boardService.get(boardSeq));
    }

    /**
     * 등록/수정 처리(
     * @param parameter
     */
    @PutMapping("/save")
    @ApiOperation(value="등록 / 수정처리", notes = "신규 게시물 저장 및 기존 게시물 업데이트가 가능합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardSeq", value="게시물 번호", example = "1"),
            @ApiImplicitParam(name = "title", value="제목", example = "spring"),
            @ApiImplicitParam(name = "contents", value="내용", example = "spring 강좌")
    })
    public BaseResponse<Integer> save(BoardParameter parameter){
        // 제목 필수 체크
        if(!StringUtils.hasLength(parameter.getTitle())){
            throw new BaseException(BaseResponseCode.VALIDATION_REQUIRED, new String[]{"title", "제목"});
        }
        // 내용 필수 체크
        if(!StringUtils.hasLength(parameter.getContents())){
            throw new BaseException(BaseResponseCode.VALIDATION_REQUIRED, new String[]{"contents", "내용"});
        }
       boardService.save(parameter);
       return new BaseResponse<Integer>(parameter.getBoardSeq());
    }

    /**
     * 등록/수정 처리(
     */
    @PutMapping("/saveList1")
    @ApiOperation(value="대용량 등록처리1", notes = "대용량 등록처리1")
    public BaseResponse<Boolean> saveList1(){
        int count = 0;
        // 테스트를 위한 랜덤 1000개의 데이터를 생성
        List<BoardParameter> list = new ArrayList<BoardParameter>();
        while(true){
            count++;
            String title = RandomStringUtils.randomAlphabetic(10);
            String contents = RandomStringUtils.randomAlphabetic(10);
            list.add(new BoardParameter(title, contents));
            if(count >= 10000){
                break;
            }
        }

        long start = System.currentTimeMillis();
        boardService.saveList1(list);
        long end = System.currentTimeMillis();
        logger.info("실행시간 : {}", (end - start) / 1000.0);

        return new BaseResponse<Boolean>(true);
    }

    /**
     * 등록/수정 처리(
     */
    @PutMapping("/saveList2")
    @ApiOperation(value="대용량 등록처리2", notes = "대용량 등록처리2")
    public BaseResponse<Boolean> saveList2(){
        int count = 0;
        // 테스트를 위한 랜덤 1000개의 데이터를 생성
        List<BoardParameter> list = new ArrayList<BoardParameter>();
        while(true){
            count++;
            String title = RandomStringUtils.randomAlphabetic(10);
            String contents = RandomStringUtils.randomAlphabetic(10);
            list.add(new BoardParameter(title, contents));
            if(count >= 10000){
                break;
            }
        }

        long start = System.currentTimeMillis();
        boardService.saveList2(list);
        long end = System.currentTimeMillis();
        logger.info("실행시간 : {}", (end - start) / 1000.0);

        return new BaseResponse<Boolean>(true);
    }

    /**
     * 삭제 처리
     * @param boardSeq
     */
    @DeleteMapping("/{boardSeq}")
    @ApiOperation(value="삭제 처리", notes = "신규 게시물 번호에 해당하는 정보를 삭제합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "boardSeq", value="게시물 번호", example = "1")
    })
    public BaseResponse<Boolean> delete(@PathVariable int boardSeq){
        Board board = boardService.get(boardSeq);
        if(board == null){
            return new BaseResponse<Boolean>(false);
        }
        boardService.delete(boardSeq);
        return new BaseResponse<Boolean>(true);
    }
}
