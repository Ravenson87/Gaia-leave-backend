package com.caci.gaia_leave.administration.repository.request;

import com.caci.gaia_leave.administration.model.request.LeaveDays;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface LeaveDaysRepository extends CrudRepository<LeaveDays, Integer> {
    boolean existsByDate(Date date);

    Optional<LeaveDays> findByDate(Date date);
    boolean findByYear(int year);
}
