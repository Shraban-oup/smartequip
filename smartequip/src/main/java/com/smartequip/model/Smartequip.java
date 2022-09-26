package com.smartequip.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Smartequip {

	private List<Integer> questionNums;
	private int ansewer;

	public Smartequip() {
		super();
	}

	public Smartequip(List<Integer> questionNums, int ansewer) {
		super();
		this.questionNums = questionNums;
		this.ansewer = ansewer;
	}

	public List<Integer> getQuestionNums() {
		return questionNums;
	}

	public void setQuestionNums(List<Integer> questionNums) {
		this.questionNums = questionNums;
	}

	public int getAnsewer() {
		return ansewer;
	}

	public void setAnsewer(int ansewer) {
		this.ansewer = ansewer;
	}

	@Override
	public String toString() {
		return "Smartequip [questionNums=" + questionNums + ", ansewer=" + ansewer + "]";
	}
}
