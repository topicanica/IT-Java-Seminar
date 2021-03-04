package org.SBI.eReferendum.repositories;

import java.util.List;

import org.SBI.eReferendum.models.Referendum;
import org.SBI.eReferendum.models.ReferendumQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReferendumQuestionRepository extends JpaRepository<ReferendumQuestion, Long> {
	List<ReferendumQuestion> findByReferendum(Referendum referendum);
}
