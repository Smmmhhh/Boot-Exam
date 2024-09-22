package com.exam.smh.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * JPA를 이용하는 Repository에서는 페이지 처리 결과를 위해 Page<Entity> 타입으로 반환하게 된다.
 */
@Data
public class PageResultDTO<DTO, EN> {

    /**
     * PageResultDTO는 List<DTO> 타입으로 객체들을 보관한다.
     */
    private List<DTO> dtoList;

    private int totalPage; // 총 페이지 번호
    private int page; // 현재 페이지 번호
    private int size; // 목록 사이즈
    private int start, end; // 시작페이지 번호, 끝 페이지 번호
    private boolean prev, next; // 이전, 다음
    private List<Integer> pageList; // 페이지 번호 목록

    /**
     * Function<EN, DTO> 는 엔티티 객체들을 DTO로 변환해 주는 생성자이다.
     */
    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream().map(fn).collect(Collectors.toList());
        totalPage = result.getTotalPages(); // 총 페이지 수를 구한다.
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1; // 0부터 시작하기 때문에 1을 추가한다.
        this.size = pageable.getPageSize();

        // temp end page
        int tempEnd = (int) (Math.ceil(page / 10.0)) * 10;
        start = tempEnd - 9;
        prev = start > 1;
        end = totalPage > tempEnd ? tempEnd : totalPage;
        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
    }

}
