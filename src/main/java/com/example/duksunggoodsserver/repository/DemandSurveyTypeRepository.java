package com.example.duksunggoodsserver.repository;

import com.example.duksunggoodsserver.model.entity.DemandSurveyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandSurveyTypeRepository extends JpaRepository<DemandSurveyType, Long> {
}
