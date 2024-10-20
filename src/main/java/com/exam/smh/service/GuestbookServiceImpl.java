package com.exam.smh.service;

import com.exam.smh.dto.GuestbookDTO;
import com.exam.smh.dto.PageRequestDTO;
import com.exam.smh.dto.PageResultDTO;
import com.exam.smh.entity.GuestBook;
import com.exam.smh.entity.QGuestBook;
import com.exam.smh.repotsitory.GuestbookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
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

        // 검색조건을 가져온다.
        BooleanBuilder booleanBuilder = getSearch(requestDTO);

        Page<GuestBook> result = repository.findAll(booleanBuilder, pageable);

        // entityToDto 메서드를 이용해서 Funtion을 생성하고 이를 PageResultDTO로 구성한다.
        Function<GuestBook, GuestbookDTO> fn = (entity) -> entityToDto(entity);

        return new PageResultDTO<>(result, fn);
    }

    /**
     * 게시물을 조회한다.
     *
     * @param gno
     * @return
     */
    @Override
    public GuestbookDTO read(Long gno) {

        Optional<GuestBook> result = repository.findById(gno);

        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    /**
     * 게시물을 수정한다.
     *
     * @param dto
     */
    @Override
    public void modify(GuestbookDTO dto) {

        Optional<GuestBook> result = repository.findById(dto.getGno());

        if (result.isPresent()) {

            GuestBook entity = result.get();

            entity.changeTitle(dto.getTitle());
            entity.changeContent(dto.getContent());

            repository.save(entity);
        }
    }

    /**
     * 게시물을 삭제한다.
     *
     * @param gno
     */
    @Override
    public void remove(Long gno) {
        repository.deleteById(gno);
    }

    /**
     * 검색결과를 돌려준다.
     *
     * @param requestDTO
     * @return
     */
    private BooleanBuilder getSearch(PageRequestDTO requestDTO) {

        BooleanBuilder booleanBuilder = new BooleanBuilder();
        QGuestBook qGuestBook = QGuestBook.guestBook;

        String type = requestDTO.getType();
        String keyword = requestDTO.getKeyword();

        BooleanExpression expression = qGuestBook.gno.gt(0L); // gno > 0 조건만 생성

        booleanBuilder.and(expression);

        // 검색조건이 없는경우 그대로 반환한다.
        if (type == null || type.trim().isEmpty()) {
            return booleanBuilder;
        }

        // 검색조건을 작성하기
        BooleanBuilder conditionBuilder = new BooleanBuilder();

        // 제목
        if (type.contains("t")) {
            conditionBuilder.or(qGuestBook.title.contains(keyword));
        }

        // 본문
        if (type.contains("c")) {
            conditionBuilder.or(qGuestBook.content.contains(keyword));
        }

        // 작성자
        if (type.contains("w")) {
            conditionBuilder.or(qGuestBook.writer.contains(keyword));
        }

        // 모든조건 통합
        booleanBuilder.and(conditionBuilder);

        return booleanBuilder;
    }
}
