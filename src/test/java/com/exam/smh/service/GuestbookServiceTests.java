package com.exam.smh.service;

import com.exam.smh.dto.GuestbookDTO;
import com.exam.smh.dto.PageRequestDTO;
import com.exam.smh.dto.PageResultDTO;
import com.exam.smh.entity.GuestBook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GuestbookServiceTests {

    @Autowired
    private GuestbookService service;

    /**
     * 등록을 테스트 한다.
     */
    @Test
    public void testRegister() {

        GuestbookDTO guestbookDTO = GuestbookDTO.builder()
                .title("Sample Title...")
                .content("Sample Content...")
                .writer("user0")
                .build();

        System.out.println(service.register(guestbookDTO));
    }

    /**
     * Entity 객체가 DTO 객체로 변환되었는지 확인한다.
     */
    @Test
    public void testList() {

        // PageRequestDTO를 입력받는다.
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        // 페이징 처리된 DTO 객체를 반환받는다.
        PageResultDTO<GuestbookDTO, GuestBook> requestDTO = service.getList(pageRequestDTO);

        // 페이징이 제대로 설정되었는지 확인한다.
        System.out.println("PREV: " + requestDTO.isPrev());
        System.out.println("NEXT: " + requestDTO.isNext());
        System.out.println("TOTAL: " + requestDTO.getTotalPage());
        System.out.println("-----------------------------------");

        // 반환된 DTO 객체를 출력한다.
        for (GuestbookDTO guestbookDTO : requestDTO.getDtoList()) {
            System.out.println(guestbookDTO);
        }

        System.out.println("===================================");
        requestDTO.getPageList().forEach(i -> System.out.println(i));
    }
}
