package com.github.renuevo.n_plus_one;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class AcademyService {

    private final AcademyRepository academyRepository;

    @Transactional(readOnly = true)
    public List<String> findAllSubjectNames(){
        return extractSubjectNames(academyRepository.findAll());
    }

    /**
     * Lazy Load를 수행하기 위해 메소드를 별도로 생성
     */
    private List<String> extractSubjectNames(List<Academy> academies){
        log.info(">>>>>>>>[모든 과목을 추출한다]<<<<<<<<<");
        log.info("Academy Size : {}", academies.size());

        return academies.stream()
                .map(a -> a.getSubjectSet().iterator().next().getName())
                .collect(Collectors.toList());
    }
}