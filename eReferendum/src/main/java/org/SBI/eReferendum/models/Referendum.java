package org.SBI.eReferendum.models;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "referendums")
public class Referendum extends BaseAuditableEntity {

	@NotNull
	@Column(nullable = false)
	private ReferendumType type;

	@NotNull
	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime startDateTime;

	@NotNull
	@Column(nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
	private LocalDateTime endDateTime;

	private Integer numberOfEligibleVoters = 0;
	private Integer numberOfBallots = 0;
	private Integer turnoutAt11 = 0;
	private Integer turnoutAt14 = 0;
	private Integer turnoutAt17 = 0;

	@Column(nullable = true)
	private Integer regionId;

	@Column(nullable = true)
	private Integer cityId;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "referendum", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<VotingRecord> votingRecords = new ArrayList<>();

	// @JsonBackReference
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "referendum", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ReferendumQuestion> questions = new ArrayList<>();

	public Referendum() {
		super();
	}

	public Referendum(ReferendumType type, LocalDateTime startDateTime, LocalDateTime endDateTime,
			Integer numberOfEligibleVoters, Integer numberOfBallots, Integer turnoutAt11, Integer turnoutAt14,
			Integer turnoutAt17, Integer regionId, Integer cityId, List<ReferendumQuestion> questions) {
		super();
		this.type = type;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.numberOfEligibleVoters = numberOfEligibleVoters;
		this.numberOfBallots = numberOfBallots;
		this.turnoutAt11 = turnoutAt11;
		this.turnoutAt14 = turnoutAt14;
		this.turnoutAt17 = turnoutAt17;
		this.regionId = regionId;
		this.cityId = cityId;
		this.questions = questions;

	}

	public Integer getNumberOfBallots() {
		return numberOfBallots;
	}

	public void setNumberOfBallots(Integer numberOfBallots) {
		this.numberOfBallots = numberOfBallots;
	}

	public ReferendumType getType() {
		return type;
	}

	public void setType(ReferendumType type) {
		this.type = type;
	}

	public LocalDateTime getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}

	public LocalDateTime getEndDateTime() {
		return endDateTime;
	}

	public void setEndDateTime(LocalDateTime endDateTime) {
		this.endDateTime = endDateTime;
	}

	public Integer getNumberOfEligibleVoters() {
		return numberOfEligibleVoters;
	}

	public void setNumberOfEligibleVoters(Integer numberOfEligibleVoters) {
		this.numberOfEligibleVoters = numberOfEligibleVoters;
	}

	public Integer getTournoutAt11() {
		return turnoutAt11;
	}

	public void setTournoutAt11(Integer turnoutAt11) {
		this.turnoutAt11 = turnoutAt11;
	}

	public Integer getTournoutAt14() {
		return turnoutAt14;
	}

	public void setTournoutAt14(Integer turnoutAt14) {
		this.turnoutAt14 = turnoutAt14;
	}

	public Integer getTournoutAt17() {
		return turnoutAt17;
	}

	public void setTournoutAt17(Integer turnoutAt17) {
		this.turnoutAt17 = turnoutAt17;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public List<VotingRecord> getVotingRecords() {
		return votingRecords;
	}

	public void setVotingRecords(List<VotingRecord> votingRecords) {
		this.votingRecords = votingRecords;
	}

	public List<ReferendumQuestion> getQuestions() {
		return questions;
	}

	public void setQuestions(List<ReferendumQuestion> questions) {
		this.questions = questions;
	}

	public void AddQuestion() {

		ReferendumQuestion question = new ReferendumQuestion();
		this.questions.add(question);
	}

}
