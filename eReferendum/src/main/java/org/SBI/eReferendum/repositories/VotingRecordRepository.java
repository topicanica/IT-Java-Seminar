package org.SBI.eReferendum.repositories;

import org.SBI.eReferendum.models.Referendum;
import org.SBI.eReferendum.models.VotingRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VotingRecordRepository extends JpaRepository<VotingRecord, Long> {
	Integer countByReferendum(Referendum referendum);

}
