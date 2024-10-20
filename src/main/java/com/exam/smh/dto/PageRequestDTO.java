package com.exam.smh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * 화면에서 전달되는 page 파리미터를 처리하는 class이다.
 */
@Builder
@AllArgsConstructor
@Data
public class PageRequestDTO {

    private int page;
    private int size;
    private String type;
    private String keyword;

    /**
     * 기본 페이지와 갯수를 설정한다.
     */
    public PageRequestDTO() {
        this.page = 1;
        this.size = 10;
    }

    /**
     * 페이지 처리된 결과값을 돌려준다.
     */
    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort); // 페이지 번호는 0부터 시작하기 때문에 -1을 해준다.
    }
}
