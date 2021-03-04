package org.SBI.eReferendum.models;

public enum ReferendumType {
	LOCAL("Local"), REGIONAL("Regional"), STATE("State");

	public final String displayValue;

	public String getDisplayValue() {
		return displayValue;
	}

	private ReferendumType(String displayValue) {
		this.displayValue = displayValue;
	}
}
