package com.kh.spring.mybatis;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.kh.spring.book.model.dto.Book;
import com.kh.spring.member.model.dto.Member;



@WebAppConfiguration
// 가상으로 만들어지는 web.xml을 사용해 테스트환경 구축
@RunWith(SpringJUnit4ClassRunner.class)
// Junit을 실행
// 테스트시 사용할 가상의 applicationContext를 생성하고 관리
@ContextConfiguration(locations = {"file:src/main/webapp/WEB-INF/spring/**/*-context.xml"})
// 가상의 applicationContext를 생성하기위한 spring bean 설정파일 위치 지정
public class mybatisTest {
	
	// Junit annotation
	// @Before : 테스트 전에 실행될 메소드
	// @test : 테스트에 메소드
	// @after : 테스트 후에 실행될 메소드
	
	// SqlSessionTemplate의 주요 메서드
	// SelectOne : 단일행 select문 실행
	// SelectList : 다중행 select 실행
	// insert : 메서드의 결과값은 쿼리에 의해 영향받은 row 수
	// update : 메서드의 결과값은 쿼리에 의해 영향받은 row 수
	// delete : 메서드의 결과값은 쿼리에 의해 영향받은 row 수
	// **procedure 호출은 dml 쿼리 메서드중에 선택
	
	
	@Autowired
	private SqlSessionTemplate session;
	private final String NAMESPACE = "com.kh.spring.mybatis.mybatisMapper.";
	private String userId = "DEV";
	
	@Test
	public void selectOneTest() {
		session.selectOne(NAMESPACE+"selectPasswordByUserId", userId);
	}
	
	@Test
	public void selectTwoTestDto() {
		Member member = session.selectOne(NAMESPACE+"selectMemberByUserId", userId); // dto와 db의 이름이 같은것만 ㅁ맵핑함. 따라서 config.xml을 적당히 만져줘야함.
		System.out.println(member);
	}
	
	@Test
	public void selectList() {
		List<Map<String,Object>> res = session.selectList(NAMESPACE+"selectRentAndMenberByUserId", userId); 
		for (Map<String, Object> map : res) {
			System.out.println(map);
		}
	}
	
	@Test
	public void selectListAsUsingResultMap() {
		List<Map<String,Object>> res = session.selectList(NAMESPACE+"selectRentBookByUserId", userId); 
		for (Map<String, Object> map : res) {
			System.out.println(map);
		}
	}
	
	@Test
	public void insertWithDto() {
		Member member = new Member();
		member.setUserId("mybitis");
		member.setPassword("1234");
		member.setEmail("13@as.kr");
		member.setTell("010-8311-1904");
		
		session.insert(NAMESPACE+"insertWithDto", member);
	}
	
	@Test
	public void insertWithMap() {
		Member member = new Member();
		member.setUserId("DEV");
		
		Map<String,Object> commandMap = new HashMap<String,Object>();
	
		commandMap.put("member", member);
		commandMap.put("title", "오딧세이아");
		commandMap.put("rentBookCnt", 1);
		
		session.insert(NAMESPACE+"insertWithMap", commandMap);
	}
	
	@Test
	public void delete() {
		session.insert(NAMESPACE+"delete", "오딧세이아");
	}
	
	@Test
	public void update() {
		Member member = new Member();
		member.setUserId("DEV");
		member.setPassword("ppppp");
		session.update(NAMESPACE+"update", member);
	}
	
	@Test
	public void procedure() {
		session.update(NAMESPACE+"procedure", 100005);
	}
	
	@Test
	public void test01() {
		
		Map<String, String> book  = new HashMap<>();
		book.put("title", "좁은문");
		book.put("author", "앙드레 지드");
		
		session.insert(NAMESPACE+"test01", book);
		
		/* session.insert(NAMESPACE+"test01", Map.of("title", "좁은문", "author", "앙드레 지드")); */
		
		
	}
	
	@Test
	public void test02() {
		session.update(NAMESPACE+"test02", 2);
	}
	
	@Test
	public void test03() {
		
		Date regDate = new Date(0);
		regDate.setMonth(8);
		regDate.setYear(121);
		
		
		Date regDate2 = new Date(0);
		regDate2.setMonth(9);
		regDate2.setYear(121);
		
		Map<String, Date> date = new HashMap<String, Date>();
		date.put("regDate2", regDate2);
		date.put("regDate", regDate);
		
		
		session.update(NAMESPACE+"test03", date);
	}
	
	@Test
	public void test04() {
		session.selectList(NAMESPACE+"test04", 3);
	}
	
	
	@Test
	public void dynamicIf() {
		// info 필터면 info의 키워드로
		// author면 작가 키우드로 검색
		session.selectList(NAMESPACE+"dynamicIf", Map.of("filter","info","keyword","씨이발"));
	}
	
	@Test
	public void dynamicChoose() {
		// info 필터면 info의 키워드로
		// author면 작가 키워드로 검색
		// default는 title
		session.selectList(NAMESPACE+"dynamicChoose", Map.of("keyword","바람"));
	}
	
	@Test
	public void dynamicForEachAndWhereTag() {
		// 사용자가 검색조건 다중선택
		// 해당 조건 or연산해서 결과 반환
		// 제목, 내용, 작가 검색조건 선택
		// 검색 키워드가 셋중 하나라도 걸리면 결과 토해내라고
		String[] filters = {"author","info"};
		session.selectList(NAMESPACE+"dynamicForEachAndWhereTag", Map.of("filters",filters, "keyword", "씨발"));
	}
	
	@Test
	public void dynamicForEachAnd2Tag() {
		// 사용자가 검색조건 다중선택
		// 해당 조건 and연산해서 결과 반환
		// 제목, 내용, 작가 검색조건 선택
		// 검색 키워드가 다 걸리면 결과 토해내라고
		String[] filters = {"title","author","info"};
		session.selectList(NAMESPACE+"dynamicForEachAnd2Tag", Map.of("filters",filters, "keyword", "소"));
	}
	
	@Test
	public void dynamicForEachWithList() {
		// 사용자가 선택한 도서명 중에서 DB에 존재하는 도서를 모두 반환
		session.selectList(NAMESPACE+"dynamicForEachWithList", List.of("남한산성","정의란 무엇인가","기린"));
	}
	
	@Test
	public void insertTemplate() {
		// 사용자로부터 데이터를 입력할
		// 테이블명, 컬럼명, 값을 전달받아 해당 테이블에 사용자가 원하는 데이터를 입력하는 쿼리
		session.selectList(NAMESPACE+"insertTemplate", 
				Map.of("tableName", "member", "data", 
						Map.of("user_id","dynamic", "password","1234","tell","010-8311-1904","email","trstyq@gamil.com"
								)
						)
				);
	}
	
	@Test
	public void insertTemplateBook() {
		// 시퀀스 어찌넣냐
		// 잘. 시퀀스랑, 시퀀스랑 엮인 컬럼을 하나로 묶어. 그건 테이블 이름 뿐만 아니라 값도 #{ }여기에 넣을꺼임
		session.selectList(NAMESPACE+"insertTemplateBook", 
				Map.of("tableName", "book", 
						"sec", Map.of("colName","bk_idx","val","sc_bk_idx.nextval"),
						"data", Map.of("title","쿠오바디스","author","E.시엔키비치")
						)
				);
	}
	
	
	@Test
	public void dynamicSet() {
		Member member = new Member();
		member.setUserId("DEV");
		member.setPassword("12345");
		session.update(NAMESPACE+"dynamicSet", member);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
