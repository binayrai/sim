package com.elite.repository;

import com.elite.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by binaychap on 2/26/2016.
 */
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
