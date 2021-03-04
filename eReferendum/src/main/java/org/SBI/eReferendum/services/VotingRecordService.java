package org.SBI.eReferendum.services;

import java.util.List;
import java.util.Optional;

import org.SBI.eReferendum.models.Referendum;
import org.SBI.eReferendum.models.VotingRecord;
import org.SBI.eReferendum.repositories.VotingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VotingRecordService {

	@Autowired
	private VotingRecordRepository votingRecordRepository;

	public List<VotingRecord> getAll() {
		return votingRecordRepository.findAll();
	}

	public Page<VotingRecord> getPage(Pageable pageable) {
		return votingRecordRepository.findAll(pageable);
	}

	public Optional<VotingRecord> findById(Long id) {
		return votingRecordRepository.findById(id);
	}

	public VotingRecord save(VotingRecord votingRecord) {
		return votingRecordRepository.save(votingRecord);
	}

	public void deleteById(Long id) {
		votingRecordRepository.deleteById(id);
	}

	public Integer countByReferendum(Referendum referendum) {
		return votingRecordRepository.countByReferendum(referendum);
	}

}
