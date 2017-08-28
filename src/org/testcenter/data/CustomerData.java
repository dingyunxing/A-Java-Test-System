package org.testcenter.data;

import org.testcenter.model.Customer;

public class CustomerData {
	public static  String[][] CUSTOMER_DATA={
	{
		"123@gmail.com",//userId
		"123",//password
		"123@gmail.com"//email
	},
	{	
		"456@gmail.com",//userId2
		"456",//password2
		"456@gmail.com"
	}
	};
	private static final Customer[] customers;
	static{
		int size=CUSTOMER_DATA.length;
		customers=new Customer[size];
		for(int i=0;i<size;i++){
			String[] cust=CUSTOMER_DATA[i];
			Customer c=new Customer();
			c.setUserId(cust[0]);
			c.setPassword(cust[1]);
			c.setEmail(cust[2]);
			customers[i]=c;
		}
	}
	public static Customer[] getCustomer(){
		return customers;
	}

}
