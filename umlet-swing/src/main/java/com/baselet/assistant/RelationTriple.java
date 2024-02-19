package com.baselet.assistant;

public class RelationTriple {
	private final String firstEntity;
	private final String secondEntity;
	private final String type;

	public RelationTriple(String firstEntity, String secondEntity, String type) {
		this.firstEntity = firstEntity;
		this.secondEntity = secondEntity;
		this.type = type;
	}

	public String getFirstEntity() {
		return firstEntity;
	}

	public String getSecondEntity() {
		return secondEntity;

	}

	public String getType() {
		return type;
	}

}
