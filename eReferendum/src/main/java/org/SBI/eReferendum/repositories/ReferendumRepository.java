package org.SBI.eReferendum.repositories;

import java.time.LocalDateTime;
import java.util.List;

import org.SBI.eReferendum.models.Referendum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferendumRepository extends JpaRepository<Referendum, Long> {

	List<Referendum> findAllByStartDateTimeLessThanEqualAndEndDateTimeGreaterThanEqual(LocalDateTime endDate,
			LocalDateTime startDate);

	List<Referendum> findByVotingRecordsCitizenOIB(String citizenOIB);
}