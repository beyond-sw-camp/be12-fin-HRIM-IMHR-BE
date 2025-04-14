package com.example.be12hrimimhrbe.domain.event;

import com.example.be12hrimimhrbe.domain.event.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface    EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findAllByCompanyIdx(Long companyIdx, Pageable pageable);

    List<Event> findByCompanyIdxAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Long companyIdx, LocalDate startDate, LocalDate endDate);
}