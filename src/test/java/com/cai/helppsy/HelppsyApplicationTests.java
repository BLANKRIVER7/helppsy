package com.cai.helppsy;

import com.cai.helppsy.freeBulletinBoard.entity.FreeBulletin;
import com.cai.helppsy.freeBulletinBoard.repository.FreeBulletinRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class HelppsyApplicationTests {
	@Autowired
	private FreeBulletinRepository bulletinRepository;

	@Test
	void contextLoads() {
		System.out.println("______________________________________________");
		System.out.println(System.getProperty("user.dir"));
		System.out.println("______________________________________________");
	}

	@Test
	void pagingTest(){
		for(int i = 0; i < 100; i++){
			bulletinRepository.save(new FreeBulletin());
		}
	}

	@Test
	void pagingTest2(){

		System.out.println("__________________");

		System.out.println("__________________");
	}

	@Test
	void updateTest(){
		int no = 3;
		FreeBulletin freeBulletin = bulletinRepository.findById(no).get();
		for(int i = 0; i < 50; i++)
			freeBulletin.setViews(freeBulletin.getViews()+1);
		bulletinRepository.saveAndFlush(freeBulletin);
	}

	@Test
	void deleteTest(){
		int no = 1;
		FreeBulletin freeBulletin = bulletinRepository.findById(no).get();
		bulletinRepository.delete(freeBulletin);
	}
}
