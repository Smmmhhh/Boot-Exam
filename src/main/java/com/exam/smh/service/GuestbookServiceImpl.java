package com.exam.smh.service;

import com.exam.smh.dto.GuestbookDTO;
import com.exam.smh.entity.GuestBook;
import com.exam.smh.repotsitory.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor // 의존성을 자동으로 주입한다.
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository repository;

    /**
     * 방명록을 등록한다.
     */
    @Override
    public Long register(GuestbookDTO dto) {

        log.info("DTO--------------------");
        log.info(dto);

        GuestBook entity = dtoToEntity(dto);

        log.info(entity);

        // GuestBook을 저장한다
        repository.save(entity);

        return entity.getGno();
    }
}
