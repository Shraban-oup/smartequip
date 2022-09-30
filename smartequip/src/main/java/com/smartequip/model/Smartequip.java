package com.smartequip.model;

import java.util.List;
import java.util.Objects;

/**
 * @author Shraban.Rana
 *
 */
public class Smartequip {

	private List<Integer> questionNums;
	private String uniqueToken;

	/**
	 * 
	 */
	public Smartequip() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param questionNums
	 * @param uniqueToken
	 */
	public Smartequip(List<Integer> questionNums, String uniqueToken) {
		super();
		this.questionNums = questionNums;
		this.uniqueToken = uniqueToken;
	}

	/**
	 * @return the questionNums
	 */
	public List<Integer> getQuestionNums() {
		return questionNums;
	}

	/**
	 * @param questionNums the questionNums to set
	 */
	public void setQuestionNums(List<Integer> questionNums) {
		this.questionNums = questionNums;
	}

	/**
	 * @return the uniqueToken
	 */
	public String getUniqueToken() {
		return uniqueToken;
	}

	/**
	 * @param uniqueToken the uniqueToken to set
	 */
	public void setUniqueToken(String uniqueToken) {
		this.uniqueToken = uniqueToken;
	}

	@Override
	public String toString() {
		return "Smartequip [questionNums=" + questionNums + ", uniqueToken=" + uniqueToken + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(questionNums, uniqueToken);
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
		return Objects.equals(questionNums, other.questionNums) && Objects.equals(uniqueToken, other.uniqueToken);
	}

}
