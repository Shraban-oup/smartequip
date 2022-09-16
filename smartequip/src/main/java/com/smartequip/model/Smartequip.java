package com.smartequip.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "smartequips")
public class Smartequip {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	@Column(name = "token")
	private String token;
	
	@Column(name = "question")
	private String question;
	
	@Column(name = "ansewer")
	private int ansewer;

	public Smartequip(String token, String question, int ansewer) {
		super();
		this.token = token;
		this.question = question;
		this.ansewer = ansewer;
	}

	public Smartequip() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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
		return "Smartequip [id=" + id + ", token=" + token + ", question=" + question + ", ansewer=" + ansewer + "]";
	}

	
	
	
}
