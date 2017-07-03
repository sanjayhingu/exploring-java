package com.java.eight.defaultmethod;

public class TestDefaultInterface implements InterfaceA, InterfaceB {

	/**
	 * Default methods are bridge between lambdas and JDK libraries
	 * 
	 * Without default method, java wouldn't have been able to provide Lambda
	 * features to existing Collection frameworks as it cannot add new methods
	 * to existing interface as it will break all the implementations.
	 *
	 * If you remove below method, compilation will fail as class cannot make
	 * out whether to call InterfaceA or InterfaceB method.
	 * 
	 * when you create below method, call specific interface super method to
	 * decide which interface method you want to call.
	 * 
	 * Please refer
	 * https://zeroturnaround.com/rebellabs/java-8-explained-default-methods/
	 * for more details.
	 * 
	 * @author Sanjay Hingu
	 * 
	 */
	public void methodA() {
		InterfaceA.super.methodA();
	}
	
	public static void main(String[] args) {
		TestDefaultInterface tdi = new TestDefaultInterface();
		tdi.methodA();
	}
}
