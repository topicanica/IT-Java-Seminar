package org.SBI.eReferendum.models;

public enum QuestionValue {
	FOR("For"), AGAINST("Against"), COMPOSED("Composed"), INVALID("Invalid");

	public final String displayValue;

	public String getDisplayValue() {
		return displayValue;
	}

	private QuestionValue(String displayValue) {
		this.displayValue = displayValue;
	}
}
