package com.exam.smh.service;

import com.exam.smh.dto.GuestbookDTO;
import com.exam.smh.dto.PageRequestDTO;
import com.exam.smh.dto.PageResultDTO;
import com.exam.smh.entity.GuestBook;
import com.exam.smh.repotsitory.GuestbookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.function.Function;

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

        // 전달받은 dto를 entity 클래스로 변환시킨다.
        GuestBook entity = dtoToEntity(dto);

        // GuestBook을 저장한다
        repository.save(entity);

        return entity.getGno();
    }

    /**
     * 페이징 처리된 목록을 돌려준다.
     */
    @Override
    public PageResultDTO<GuestbookDTO, GuestBook> getList(PageRequestDTO requestDTO) {

        Pageable pageable = requestDTO.getPageable(Sort.by("gno").descending());

        Page<GuestBook> result = repository.findAll(pageable);

        // entityToDto 메서드를 이용해서 Funtion을 생성하고 이를 PageResultDTO로 구성한다.
        Function<GuestBook, GuestbookDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }
}
