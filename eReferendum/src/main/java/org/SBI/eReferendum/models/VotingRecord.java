package org.SBI.eReferendum.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "votingRecords")
public class VotingRecord extends BaseEntity {

	@NotNull
	@JsonBackReference
	@ManyToOne(fetch = FetchType.EAGER, optional = true)
	@JoinColumn(name = "referendum_id")
	private Referendum referendum;

	@NotBlank
	@Size(min = 9, max = 13)
	@Column(nullable = false)
	private String citizenOIB;

	public VotingRecord(Referendum referendum, String citizenOIB) {
		this.referendum = referendum;
		this.citizenOIB = citizenOIB;
	}

	public Referendum getReferendum() {
		return referendum;
	}

	public void setReferendum(Referendum referendum) {
		this.referendum = referendum;
	}

	public String getCitizenOIB() {
		return citizenOIB;
	}

	public void setCitizenOIB(String citizenOIB) {
		this.citizenOIB = citizenOIB;
	}

	public VotingRecord() {
		super();
	}

}
