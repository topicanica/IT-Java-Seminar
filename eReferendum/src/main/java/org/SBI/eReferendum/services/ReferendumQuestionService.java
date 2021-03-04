package org.SBI.eReferendum.services;

import java.util.List;
import java.util.Optional;

import org.SBI.eReferendum.models.QuestionValue;
import org.SBI.eReferendum.models.Referendum;
import org.SBI.eReferendum.models.ReferendumQuestion;
import org.SBI.eReferendum.repositories.ReferendumQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReferendumQuestionService {

	@Autowired
	private ReferendumQuestionRepository referendumQuestionRepository;

	public List<ReferendumQuestion> getAll() {
		return referendumQuestionRepository.findAll();
	}

	public Page<ReferendumQuestion> getPage(Pageable pageable) {
		return referendumQuestionRepository.findAll(pageable);
	}

	public Optional<ReferendumQuestion> findById(Long id) {
		return referendumQuestionRepository.findById(id);
	}

	public List<ReferendumQuestion> findByReferendum(Referendum referendum) {
		return referendumQuestionRepository.findByReferendum(referendum);
	}

	public ReferendumQuestion save(ReferendumQuestion question) {
		return referendumQuestionRepository.save(question);
	}

	public void deleteById(Long id) {
		referendumQuestionRepository.deleteById(id);

	}

	public void increaseVotes(ReferendumQuestion question, QuestionValue value) {

		switch (value) {
		case FOR:
			question.setForVotes(question.getForVotes() + 1);
			this.save(question);
			break;
		case AGAINST:
			question.setAgainstVotes(question.getAgainstVotes() + 1);
			this.save(question);
			break;
		case COMPOSED:
			question.setComposedVotes(question.getComposedVotes() + 1);
			this.save(question);
			break;
		case INVALID:
			question.setForVotes(question.getInvalidVotes() + 1);
			this.save(question);
			break;
		default:
			break;
		}

	}
}
