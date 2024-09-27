package com.exam.smh.service;

import com.exam.smh.dto.GuestbookDTO;
import com.exam.smh.dto.PageRequestDTO;
import com.exam.smh.dto.PageResultDTO;
import com.exam.smh.entity.GuestBook;

public interface GuestbookService {
    /**
     * 방명록을 등록한다.
     */
    Long register(GuestbookDTO dto);

    /**
     * 페이징 처리가될 리스트를 돌려준다.
     */
    PageResultDTO<GuestbookDTO, GuestBook> getList(PageRequestDTO requestDTO);

    /**
     * 게시물을 조회한다.
     */
    GuestbookDTO read(Long gno);

    /**
     * 클라이언트로 받은 dto를 entity 객체로 변환시켜준다.
     *
     * @param dto
     * @return
     */
    default GuestBook dtoToEntity(GuestbookDTO dto) {
        GuestBook entity = GuestBook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }

    /**
     * Entity 객체를 DTO로 변환한다.
     */
    default GuestbookDTO entityToDto(GuestBook entity) {

        GuestbookDTO dto = GuestbookDTO.builder()
                .gno(entity.getGno())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .build();
        return dto;
    }
}
