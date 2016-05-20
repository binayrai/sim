package com.elite.repository;

import com.elite.domain.ParentSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by binaychap on 3/6/2016.
 */
public interface ParentSurveyRepository extends JpaRepository<ParentSurvey, Long> {

    @Query("select p from ParentSurvey p join fetch p.option o join fetch p.parent")
    List<ParentSurvey> findAllSurvey();

    @Query("select p from ParentSurvey p where p.parent.id = ?1")
    List<ParentSurvey> findAllByByParent(long parentId);

}
