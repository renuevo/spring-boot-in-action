package com.github.renuevo.n_plus_one;

import com.google.common.collect.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

@SpringBootTest
class AcademyServiceTest {

    @Autowired
    private AcademyRepository academyRepository;

    @Autowired
    private AcademyService academyService;


    @AfterEach
    void cleanAll() {
        academyRepository.deleteAll();
    }


    @BeforeEach
    void setup() {
        List<Academy> academies = Lists.newArrayList();

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
    void Academy여러개를_조회시_Subject가_N1_쿼리가발생한다() throws Exception {
        //given
        List<String> subjectNames = academyService.findAllSubjectNames();

        //then
        assertThat(subjectNames.size(), is(10));
    }


    @Test
    void Academy_join_fetch사용() throws Exception {
        //given
        List<String> subjectNames = academyService.findJoinAllSubjectNames();

        //then
        assertThat(subjectNames.size(), is(10));

    }

}
