package com.example.bossi.repository.user;

import com.example.bossi.entity.WaitingList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WaitingListRepository extends JpaRepository<WaitingList, Long> {

    Optional<WaitingList> findByEmail(String email);
}
