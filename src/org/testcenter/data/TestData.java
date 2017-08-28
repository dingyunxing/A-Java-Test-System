package org.testcenter.data;

import org.testcenter.model.ChoiceItem;
import org.testcenter.model.Question;
import org.testcenter.model.Test;

public class TestData {
	public static final String RIGHT_CHOICE="#";
	private static final String[][] TEST_QUESTION_LIB={
		//test attribute：name , numQuestion , timeLimitMin , description, score
			{  "Java Test",
				"3",
			    "10",
			    "A Test for Java knowledge",
			    "10"
			 },
			{
				//Question attribute：name
				 "What's the right answers about Java?",
					//ChoiceItem
					"#It is a programming language",
					"#It is a platform",
					"#It is cross-platform",
					"It is a OO language"

			},
			{
				"The common reference website for learning Java includes:",
					"#java.sun.com",
					"#www.javaworld.com",
					"#www-130.ibm.com/developerworks/",
					"www.google.com"
			},
			{
				"If an attribute is private, what are the right?",
					"immutable",
					"synchronized",
					"#package",
					"represent is-a relation"
			}
		};
	public static Test produceTest() {
		String[] tds=TEST_QUESTION_LIB[0];
		int numQ=TEST_QUESTION_LIB.length;
		int numQuestion=Integer.parseInt(tds[1]);
		if(numQuestion>(numQ-1))numQuestion=numQ-1;
		Test test=new Test(numQuestion);
		test.setName(tds[0]);
		test.setNumQuestion(numQuestion);
		test.setTimeLimitMin(Integer.parseInt(tds[2]));
		test.setDescription(tds[3]);
		test.setScore(Integer.parseInt(tds[4]));
		int qi=0;
		while(qi<numQuestion){
			String[] qds=TEST_QUESTION_LIB[qi+1];
			Question q=new Question();
			q.setName(qds[0]);
			ChoiceItem[] items=new ChoiceItem[qds.length-1];
			for(int j=1;j<qds.length;j++){
				ChoiceItem ch=new ChoiceItem();
				String choiceText=qds[j];
				if (choiceText.indexOf(RIGHT_CHOICE)==0){
					choiceText=choiceText.substring(1);
					ch.setCorrect(true);
				}
				ch.setName(choiceText);
				items[j-1]=ch;
			}
			q.setChoiceItem(items);
			q.setScore(1);
			test.addQuestion(q);
			qi++;
		}
		return test;
	}
}

	
	