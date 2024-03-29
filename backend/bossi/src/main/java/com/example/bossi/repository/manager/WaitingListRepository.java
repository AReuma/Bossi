package com.example.bossi.repository.manager;

import com.example.bossi.entity.WaitingList;
import com.example.bossi.entity.WaitingListStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface WaitingListRepository extends JpaRepository<WaitingList, Long> {

    Optional<WaitingList> findByEmail(String email);

    Optional<WaitingList> findBySendEmail(String sendEmail);

    List<WaitingList> findWaitingListByStatus(WaitingListStatus status);

    void deleteByExpirationTimeBefore(LocalDateTime now);
}
