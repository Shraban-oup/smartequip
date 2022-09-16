package com.smartequip.model;

import org.springframework.stereotype.Component;

@Component
public class Smartequip {
	
	private String question;
	private int ansewer;
	
	public Smartequip() {
	}

	public Smartequip(String question, int ansewer) {
		super();
		this.question = question;
		this.ansewer = ansewer;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getAnsewer() {
		return ansewer;
	}

	public void setAnsewer(int ansewer) {
		this.ansewer = ansewer;
	}

	@Override
	public String toString() {
		return "Smartequip [question=" + question + ", ansewer=" + ansewer + "]";
	}

	
	
}
