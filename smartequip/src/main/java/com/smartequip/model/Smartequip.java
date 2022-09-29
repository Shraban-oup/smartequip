package com.smartequip.model;

import java.util.List;
import java.util.Objects;

/**
 * @author Shraban.Rana
 *
 */
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

	@Override
	public int hashCode() {
		return Objects.hash(ansewer, questionNums);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Smartequip other = (Smartequip) obj;
		return ansewer == other.ansewer && Objects.equals(questionNums, other.questionNums);
	}
	
	
}
