package com.exam.smh;

import com.exam.smh.entity.GuestBook;
import com.exam.smh.repotsitory.GuestbookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
class SmhApplicationTests {

	@Autowired
	private GuestbookRepository guestbookRepository;


	/**
	 * 더미데이를 300개 생성한다.
	 */
	@Test
	public void insertDummies() {
		IntStream.rangeClosed(1, 300).forEach(i -> {
			GuestBook guestBook = GuestBook.builder()
					.title("Title...." + i)
					.content("Content.... " + i)
					.writer("user " + (i % 10))
					.build();
			System.out.println(guestbookRepository.save(guestBook));
		});
	}
}
