package org.SBI.eReferendum.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.SBI.eReferendum.models.Referendum;
import org.SBI.eReferendum.repositories.ReferendumRepository;
import org.SBI.eReferendum.repositories.VotingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ReferendumService {

	@Autowired
	private ReferendumRepository referendumRepository;
	@Autowired
	private VotingRecordRepository votingRecordRepository;

	public List<Referendum> getAll() {
		return referendumRepository.findAll();
	}

	public Page<Referendum> getPage(Pageable pageable) {
		return referendumRepository.findAll(pageable);
	}

	public Optional<Referendum> findById(Long id) {
		return referendumRepository.findById(id);
	}

	public Referendum save(Referendum referendum) {
		return referendumRepository.save(referendum);
	}

	public void deleteById(Long id) {
		referendumRepository.deleteById(id);
	}

	public List<Referendum> findActive() {
		return referendumRepository.findAllByStartDateTimeLessThanEqualAndEndDateTimeGreaterThanEqual(
				LocalDateTime.now(), LocalDateTime.now());
	}

	public List<Referendum> findByCitizenOIB(String citizenOIB) {
		return referendumRepository.findByVotingRecordsCitizenOIB(citizenOIB);
	}

	@Scheduled(cron = "0 0 22 ? * *")
	public void updateNumberOfBallots() {
		List<Referendum> activeReferendums = this.findActive();
		for (Referendum referendum : activeReferendums) {
			if (referendum.getEndDateTime().getDayOfYear() == LocalDateTime.now().getDayOfYear()) {
				referendum.setNumberOfBallots(votingRecordRepository.countByReferendum(referendum));
			}
		}
	}

	@Scheduled(cron = "0 0 11 ? * *")
	public void updateTournoutAt11() {
		List<Referendum> activeReferendums = this.findActive();
		for (Referendum referendum : activeReferendums) {
			if (referendum.getEndDateTime().getDayOfYear() == LocalDateTime.now().getDayOfYear()) {
				referendum.setTournoutAt11(votingRecordRepository.countByReferendum(referendum));
			}
		}
	}

	@Scheduled(cron = "0 0 14 ? * *")
	public void updateTournoutAt14() {
		List<Referendum> activeReferendums = this.findActive();
		for (Referendum referendum : activeReferendums) {
			if (referendum.getEndDateTime().getDayOfYear() == LocalDateTime.now().getDayOfYear()) {
				referendum.setTournoutAt14(votingRecordRepository.countByReferendum(referendum));
			}
		}
	}

	@Scheduled(cron = "0 0 17 ? * *")
	public void updateTournoutAt17() {
		List<Referendum> activeReferendums = this.findActive();
		for (Referendum referendum : activeReferendums) {
			if (referendum.getEndDateTime().getDayOfYear() == LocalDateTime.now().getDayOfYear()) {
				referendum.setTournoutAt17(votingRecordRepository.countByReferendum(referendum));
			}
		}
	}

	public List<Referendum> findRefusedReferendums(String citizenOIB) {
		List<Referendum> activeReferendums = this.findActive();
		List<Referendum> votedReferendums = this.findByCitizenOIB(citizenOIB);
		List<Referendum> refusedReferendums = new ArrayList<Referendum>();
		for (Referendum referendum : activeReferendums) {
			if (!votedReferendums.contains(referendum))
				refusedReferendums.add(referendum);
		}
		return refusedReferendums;
	}

}
