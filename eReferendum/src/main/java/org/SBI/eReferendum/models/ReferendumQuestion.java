package org.SBI.eReferendum.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "referendumQuestions")
public class ReferendumQuestion extends BaseEntity {

	@NotBlank
	@Column(nullable = false)
	private String question;

	private Integer forVotes = 0;
	private Integer againstVotes = 0;
	private Integer composedVotes = 0;
	private Integer invalidVotes = 0;

	@JsonBackReference
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "referendum_id")
	private Referendum referendum;

	public ReferendumQuestion() {
	}

	public ReferendumQuestion(String question, Integer forVotes, Integer againstVotes, Integer composedVotes,
			Integer invalidVotes) {

		this.question = question;
		this.forVotes = forVotes;
		this.againstVotes = againstVotes;
		this.composedVotes = composedVotes;
		this.invalidVotes = invalidVotes;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Referendum getReferendum() {
		return referendum;
	}

	public void setReferendum(Referendum referendum) {
		this.referendum = referendum;
	}

	public Integer getForVotes() {
		return forVotes;
	}

	public void setForVotes(Integer forVotes) {
		this.forVotes = forVotes;
	}

	public Integer getAgainstVotes() {
		return againstVotes;
	}

	public void setAgainstVotes(Integer againstVotes) {
		this.againstVotes = againstVotes;
	}

	public Integer getComposedVotes() {
		return composedVotes;
	}

	public void setComposedVotes(Integer composedVotes) {
		this.composedVotes = composedVotes;
	}

	public Integer getInvalidVotes() {
		return invalidVotes;
	}

	public void setInvalidVotes(Integer invalidVotes) {
		this.invalidVotes = invalidVotes;
	}

}
