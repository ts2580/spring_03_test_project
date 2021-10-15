package com.kh.spring.mybatis;

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
		session.update(NAMESPACE+"update", "DEV");
	}
	
	
	
	
	
	
	
	
	
	

}
