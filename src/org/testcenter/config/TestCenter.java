package org.testcenter.config;

import static org.util.Console.output;
import static org.util.Console.prompt;
import static org.util.Console.promptYesNo;

import java.util.*;

import org.testcenter.data.CustomerData;
import org.testcenter.data.TestData;
import org.testcenter.model.ChoiceItem;
import org.testcenter.model.Customer;
import org.testcenter.model.Question;
import org.testcenter.model.QuestionResult;
import org.testcenter.model.Test;
import org.testcenter.model.TestResult;
import org.util.Console;


public class TestCenter {
	
	public static void welcome() {
		output("Today is："+Configuration.getDateFormat().format(new Date()));
		output("Your operation system is："+System.getProperty("os.name"));
	}
	private static Customer findCustomer(String userId,String password){
		Customer[] customers=CustomerData.getCustomer();
		for(int i=0;i<customers.length;i++){
			Customer c=customers[i];
			if(c.getUserId().equals(userId) &&
			   c.getPassword().equals(password)){
				return customers[i];
			}
		}
		return null;
	}
	public static Customer login() {
		output("Please log in before the test. Press enter after input:");
		for (int i = 0; i < Configuration.MAX_LOGIN; i++) {
			String userId=prompt("Please enter your userid：");
			String password = prompt("Please enter your password：");
			Customer customer=findCustomer(userId, password);
			if (customer!=null) {
				output("Welecome " + customer.getUserId() + 
					  " to Fangsoft Test Center!");
				return customer;
			}
			output("Wrong userid or password, cannot log in. Please try again." +
				"Log in "+Configuration.MAX_LOGIN+"times failed. The system will exit." +
				"Thi is "+(i+1)+"time log");
		}
		return null;
	}
	private static void exit(Object msg) {
		output(msg);
		System.exit(1);
	}

	private static TestResult newTestResult(Customer c,Test test){
		TestResult tr=new TestResult();
		tr.setCustomer(c);
		tr.setTest(test);
		tr.setStartTime(new Date());
		tr.setQuestionResult(new QuestionResult[test.getQuestion().length]);
		int count=0;
		for(Question q:test.getQuestion()){
			q.assignLabel(Configuration.CHOICE_LABEL);
			QuestionResult qr=new QuestionResult();
			qr.setQuestion(q);
			tr.getQuestionResult()[count++]=qr;
		}
		return tr;
	}

	public static String presentQuestion(int pos,Question q){
		Console.output("%1$s. %2$s%n",pos,q.getName());
		for(ChoiceItem item:q.getChoiceItem()){
			Console.output("   %1$s. %2$s%n",item.getLabel(),item.getName());
		}
		Console.output("Please input the answer：");
		return Console.read();
	}

	public static TestResult takeTest(Test test,Customer customer){
		output("==========Test Begins===========");
		output( "Test name：%1$5s%n" +
				"Test description：%2$5s%n" +
				"Test questions：%3$5s%n" +
				"Test time：%4$5sminutes%n",
				test.getName(), 
				test.getDescription(), 
				test.getNumQuestion(),  
				test.getTimeLimitMin());
		long start=System.currentTimeMillis();
		output("You have %1$s minutes to finish the whole test，the current time is：%2$tT%n",
				test.getTimeLimitMin(),start);
		TestResult tr=newTestResult(customer,test);
		int count=0;
		for(QuestionResult qr:tr.getQuestionResult()){
			String answer=presentQuestion(++count,qr.getQuestion());
			qr.setAnswer(answer);
		}
		long end=System.currentTimeMillis();
		tr.setEndTime(new Date(end));
		tr.commitTest();
		output("Test ends. The current time is：%1$tT%n",end);
		return tr;
	}

	public static void reportTestResult(TestResult tr){
		output("==========Test Report===========");
		long duration=(tr.getEndTime().getTime() - tr.getStartTime().getTime())/(1000*60);
		output("You spent %1$s minutes in this test%n",duration);
		output("%1$-6s%2$-6s%3$-6s%4$-6s%n",
"NO","Your answer","Correct answer","T/F");
		int count=0;
		for(QuestionResult qr:tr.getQuestionResult()){
			output("%1$-8s%2$-8s%3$-8s%4$-8s%n",
					++count,
					qr.getAnswer(),
					qr.getQuestion().getAnswer(),
					qr.getResult()?"True":"False");
		}
		boolean pass=tr.getPass()>0?true:false;
		String displayPass="";
		if(pass)displayPass="Congradulations! You have passed the test!";
		else displayPass="Sorry, you didn't pass the test";
		output("Your final score is："+tr.getScore()+","+displayPass);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		welcome();
		Customer customer = login();
		if(customer==null){
			exit("Wrong userid or passwd，cannot log in. Test out");
		}
		boolean response=promptYesNo("Are you sure to attend the test?",
"y","Yes：y","No, exit：n");
		if(!response)exit("System out");
		Test test = TestData.produceTest();
		TestResult tr=takeTest(test,customer);
		reportTestResult(tr);


	}

}
