package com.kh.spring.member.model.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberRepository {

	String selectPasswordByUserId(String string);
	// mybatisMapper의 셀렉트구문이 실행. 여기 변수명과 그 동네 아이디도 같고. root context에서 맵퍼 어노테이션 붙으면 mapper로 등록해주기로 했으니
	
}
