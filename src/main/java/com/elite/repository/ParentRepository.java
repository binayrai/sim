package com.elite.repository;


import com.elite.domain.Parent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ParentRepository extends JpaRepository<Parent, Long> {

    @Query("select Distinct p FROM Parent p  WHERE  (null IS ?1 OR p.name like ?1)")
    Page<Parent> findAllByName(String userName, Pageable p);

   /* @Query("select p from Parent p join fetch  p.childrens c")
    List<Parent> findAllWithChildren();

    @Query("select p from Parent p  join fetch  p.childrens c where  p.id = ?1")
    List<Parent> findByIds(long id);
*/
}
