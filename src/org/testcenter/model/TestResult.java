package org.testcenter.model;

import java.util.Date;


public class TestResult {
	private Customer customer;
	private Test test;
	private QuestionResult[] questionResult;
	
	
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Test getTest() {
		return test;
	}
	public void setTest(Test test) {
		this.test = test;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getPass() {
		return pass;
	}
	public void setPass(int pass) {
		this.pass = pass;
	}
	public QuestionResult[] getQuestionResult() {
		return questionResult;
	}
	public void setQuestionResult(QuestionResult[] questionResult) {
		this.questionResult = questionResult;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	private Date startTime;
	private Date endTime;
	private static final int UNKNOW_SCORE=-1;
	private static final int UNKNOW_PASS=-1;
	private static final int PASS_SUCCESS=1;
	private static final int PASS_FAILURE=0;
	private int score=UNKNOW_SCORE;//
	private int pass=UNKNOW_PASS; //
	protected int computeScore(){
			if(this.score!=UNKNOW_SCORE)return this.score;
			if(this.score==UNKNOW_SCORE)this.score=0;
			for(QuestionResult qr:this.questionResult){
				this.score+=qr.computeAnswer();
				
			}
			return this.score;
		}
	protected boolean computePass(){
			if(this.pass!=UNKNOW_PASS){
				return this.pass==PASS_SUCCESS?true:false;
			}
			int rights=0;
			for(QuestionResult qr:this.questionResult){
				if(qr.getResult()){
					rights++;
				}
			}
			if(100*rights>=70*this.getQuestionResult().length){
					this.pass=PASS_SUCCESS;
					return true;
			}else{
					this.pass=PASS_FAILURE;
					return false;
			}
		}
		public boolean commitTest(){
			this.computeScore();
			return this.computePass();
		}
		
}
