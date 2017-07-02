package com.java.design.pattern.singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingletonWithProblems implements Serializable, Cloneable{

	private static SingletonWithProblems singleton;
	
	private SingletonWithProblems() {
	}

	public static SingletonWithProblems getInstance() {
		if(singleton == null) {
			singleton = new SingletonWithProblems();
		}
		return singleton;
	}
	
	
	
	public static void main(String[] args) throws Exception {
		SingletonWithProblems s1 = SingletonWithProblems.getInstance();
		SingletonWithProblems s2 = SingletonWithProblems.getInstance();
		
		System.out.println(s1.hashCode());
		System.out.println(s2.hashCode());
		
		// break through Reflection
		Class singletonClass = Class.forName("com.java.design.pattern.singleton.SingletonWithProblems");
		Constructor<SingletonWithProblems> constructor = singletonClass.getDeclaredConstructor();
		constructor.setAccessible(Boolean.TRUE);
		SingletonWithProblems s3 = constructor.newInstance();
		
		// it created new instance which violates Singleton
		System.out.println(s3.hashCode());
		
		// break through serialization
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("singleton.ser"));
		oos.writeObject(s2);
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("singleton.ser"));
		SingletonWithProblems s4 = (SingletonWithProblems) ois.readObject();
		//new instance created which violates Singleton
		System.out.println(s4.hashCode());
		
		
		//break through Cloneable
		SingletonWithProblems s5 = (SingletonWithProblems) s2.clone();
		System.out.println(s5.hashCode());
		
	}
	
}

class TestSingleton {
	public static void useSingleton() {
		SingletonWithProblems singleton = SingletonWithProblems.getInstance();
		System.out.println("singleton hascode: "+singleton.hashCode());
	}	
	
	public static void main(String[] args) {
		//break through multithreading
		ExecutorService service = Executors.newFixedThreadPool(2);
		service.submit(TestSingleton::useSingleton);
		service.submit(TestSingleton::useSingleton);
		service.shutdown();
	}
}
