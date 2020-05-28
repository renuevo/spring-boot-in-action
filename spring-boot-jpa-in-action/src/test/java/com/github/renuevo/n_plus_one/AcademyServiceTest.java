package com.github.renuevo.n_plus_one;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;

import java.util.List;

import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AcademyServiceTest {

    @Autowired
    private AcademyRepository academyRepository;

    @Autowired
    private AcademyService academyService;


    @After
    public void cleanAll() {
        academyRepository.deleteAll();
    }


    @Before
    public void setup() {
        List<Academy> academies = List.of();

        for (int i = 0; i < 10; i++) {
            Academy academy = Academy.builder()
                    .name("강남스쿨" + i)
                    .build();

            academy.addSubject(Subject.builder().name("자바웹개발" + i).build());
            academies.add(academy);
        }

        academyRepository.saveAll(academies);
    }

    @Test
    public void Academy여러개를_조회시_Subject가_N1_쿼리가발생한다() throws Exception {
        //given
        List<String> subjectNames = academyService.findAllSubjectNames();

        //then
        assertThat(subjectNames.size(), is(10));
    }

}
