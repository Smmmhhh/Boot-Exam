package com.exam.smh;

import com.exam.smh.entity.GuestBook;
import com.exam.smh.entity.QGuestBook;
import com.exam.smh.repotsitory.GuestbookRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;
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

	/**
	 * 수정 테스트를 진행한다.
	 */
	@Test
	public void updateTest() {

		Optional<GuestBook> result = guestbookRepository.findById(300L);

		if (result.isPresent()) {
			GuestBook guestBook = result.get();

			guestBook.changeContent("Change Content ...");
			guestBook.changeTitle("Change Title ...");

			guestbookRepository.save(guestBook);
		}
	}

	/**
	 * 제목에 1이라는 글자가 있는 엔티티를 검색하는 쿼리이다.
	 */
	@Test
	public void testQuery() {
		// 페이징을 설정한다.
		Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

		// Q도메인 클래스를 얻어온다. Q도메인 클래스를 이용하면 엔티티 클래스에 선언된 title, content같은 필드들을 변수로 활용할 수 있다.
		QGuestBook qGuestBook = QGuestBook.guestBook;

		// 검색할 키워드를 설정한다.
		String keyword = "1";

		// where문에 들어가는 조건들을 넣어주는 컨테이너이다.
		BooleanBuilder builder = new BooleanBuilder();

		// 원하는 조건은 필드 값과 같이 결합해서 생성한다.
		BooleanExpression expression = qGuestBook.title.contains(keyword);

		// 만들어진 조건은 where 문에 and 나 or같은 키워드와 결합한다.
		builder.and(expression);

		// BooleanBuilder는 GuestbookRepository에 추가된 QuerydslPredicateExcutor 인터페이스 findAll()을 사용할 수 있다.
		Page<GuestBook> result = guestbookRepository.findAll(builder, pageable);

		result.get().forEach(guestBook -> {
			System.out.println(guestBook);
		});
	}

	/**
	 * 다중 항목 검색을 테스트한다.
	 */
	@Test
	public void testQuery2() {
		Pageable pageable = PageRequest.of(0, 10, Sort.by("gno").descending());

		QGuestBook qGuestBook = QGuestBook.guestBook;

		String keyword = "1";

		BooleanBuilder builder = new BooleanBuilder();

		// title, content의 키워드를 설정 후 Expression을 결합한다.
		BooleanExpression exTitle = qGuestBook.title.contains(keyword);
		BooleanExpression exContent = qGuestBook.content.contains(keyword);
		BooleanExpression exAll = exTitle.or(exContent);

		builder.and(exAll);

		// gno가 0보다 큰 조건을 추가한다. (greater than : 크다 / less than : 작다)
		builder.and(qGuestBook.gno.gt(0L));

		Page<GuestBook> result = guestbookRepository.findAll(builder, pageable);

		result.get().forEach(book -> {
			System.out.println(book);
		});
	}
}
