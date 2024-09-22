package com.exam.smh.service;

import com.exam.smh.dto.GuestbookDTO;
import com.exam.smh.entity.GuestBook;

public interface GuestbookService {
    /**
     * 방명록을 등록한다.
     */
    Long register(GuestbookDTO dto);

    default GuestBook dtoToEntity(GuestbookDTO dto) {
        GuestBook entity = GuestBook.builder()
                .gno(dto.getGno())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .build();
        return entity;
    }
}
