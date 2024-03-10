package com.baselet.assistant;

public class StateTriple {
	private final String entity;
	private final String state;
	private String value;

	public StateTriple(String entity, String state, String value) {
		this.entity = entity;
		this.state = state;
		this.value = value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getEntity() {
		return entity;
	}

	public String getState() {
		return state;

	}

	public String getValue() {
		return value;
	}

}
