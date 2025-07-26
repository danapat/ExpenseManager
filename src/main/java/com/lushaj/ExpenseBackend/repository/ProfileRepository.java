package com.lushaj.ExpenseBackend.repository;

import com.lushaj.ExpenseBackend.entity.ProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {
}
