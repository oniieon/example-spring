package com.example.onieon.framework.data.web;

import com.example.onieon.framework.data.domain.SQLPageRequest;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

/**
 * SQL 쿼리 페이징 LIMIT, OFFSET 값을 자동 계산하여 SQLPageRequest 클래스 담아서 컨트롤러에서 받을 수 있도록 함.
 * @author onieon
 */
public class SQLPageRequestHandleMethodArgumentResolver implements HandlerMethodArgumentResolver {

    final Logger logger = LoggerFactory.getLogger((getClass()));

    private static final String DEFAULT_PARAMETER_PAGE = "page";
    private static final String DEFAULT_PARAMETER_SIZE = "size";
    private static final int DEFAULT_SIZE = 20;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return SQLPageRequest.class.isAssignableFrom(methodParameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        //현재 페이지
        int page = NumberUtils.toInt(request.getParameter(DEFAULT_PARAMETER_PAGE), 1); // 1
        //리스트 갯수
        int offset = NumberUtils.toInt(request.getParameter(DEFAULT_PARAMETER_SIZE), DEFAULT_SIZE); // 10
        //시작 지점
        int limit = (offset * page)  - offset;
        // 1페이지 10개라 가정시
        // 1, 10 => 10 * 1 - 10 = limit : 0 =>>>> 0 ~ 10
        // 2, 10 => 10 * 2 - 10 = limit : 10 =>>>> 10 ~ 10

        logger.info("page : {}", page);
        logger.info("limit : {}, offset : {}", limit, offset);

        return new SQLPageRequest(page, offset, limit, offset);
    }
}
