package com.java.eight.defaultmethod;

public interface InterfaceB {
	default void methodA() {
		System.out.println("Inside B");
	}
}
