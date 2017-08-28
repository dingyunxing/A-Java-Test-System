package org.testcenter.model;

public class QuestionResult{
	
	private Question question;
	
	private String answer;
	private boolean result;
	
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public boolean getResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	
	
	private static final int DEFAULT_SCORE=-1;
	private int score = DEFAULT_SCORE;
	public int computeAnswer(){
		
		if(this.score!=DEFAULT_SCORE)return this.score;
		if(this.answer==null || this.answer.length()==0) {
			this.result=false;
			this.score=0;
		}
		else if(this.question==null || this.question.getAnswer()==null){
			this.result=false;
			this.score=0;
		}
		else if(this.question.getAnswer().equals(this.answer)){
			this.result=true;
			this.score=this.question.getScore();
    		if(this.question.getScore()<=0){
    			this.score=1;
    		}
		}else if(!this.question.getAnswer().equals(this.answer)){
			this.result=false;
			this.score=0;
		}
		return  this.score;
	}

}


