package com.java.design.pattern.singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingletonWithoutProblems implements Serializable, Cloneable {
	
	private static volatile SingletonWithoutProblems singleton;
	
	private SingletonWithoutProblems() throws Exception {
		if(SingletonWithoutProblems.singleton!=null) {
			throw new RuntimeException("Cannot create instance");
		} else {
			this.singleton = this;
		}
	}

	public static SingletonWithoutProblems getInstance() throws Exception {
		if(singleton == null) {
			synchronized (SingletonWithProblems.class) {
				if(singleton == null) {
					singleton = new SingletonWithoutProblems();
				}
			}
		}
		return singleton;
	}
	
	private Object readResolve() {
		return singleton;
	}
	
	@Override
	public Object clone() {
		return singleton;
	}
	
	public static void main(String[] args){}
}

class TestSingletonWithoutProblems  {
	
	public static void testReflection() throws Exception {
		Class singletonClass = Class.forName("com.java.design.pattern.singleton.SingletonWithoutProblems");
		Constructor<SingletonWithoutProblems> constructor = singletonClass.getDeclaredConstructor();
		constructor.setAccessible(Boolean.TRUE);
		SingletonWithoutProblems s3 = constructor.newInstance();
		System.out.println(s3.hashCode());
		
		SingletonWithoutProblems s1 = SingletonWithoutProblems.getInstance();
		SingletonWithoutProblems s2 = SingletonWithoutProblems.getInstance();
		
		System.out.println(s1.hashCode());
		System.out.println(s2.hashCode());
		
		SingletonWithoutProblems s4 = constructor.newInstance();
		System.out.println(s4.hashCode());
	}
	
	public static void main(String[] args) throws Exception {
		//testReflection();
		//testSingleton();
		//testSerializable();
		testCloaneable();
	}

	private static void testCloaneable() throws Exception {
		SingletonWithoutProblems s1 = SingletonWithoutProblems.getInstance();
		System.out.println(s1.hashCode());
		SingletonWithoutProblems s2 = (SingletonWithoutProblems) s1.clone();
		System.out.println(s2.hashCode());
	}

	private static void testSerializable() throws Exception {
		SingletonWithoutProblems s1 = SingletonWithoutProblems.getInstance();
		System.out.println(s1.hashCode());
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("singletonwithoutprob.ser"));
		oos.writeObject(s1);
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("singletonwithoutprob.ser"));
		SingletonWithoutProblems s2 = (SingletonWithoutProblems) ois.readObject();
		System.out.println(s2.hashCode());
	}

	private static void useSingleton() {
		SingletonWithoutProblems singleton = null;
		try {
			singleton = SingletonWithoutProblems.getInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("singleton hascode: "+singleton.hashCode());
	}	
	
	public static void testSingleton() throws Exception{
		ExecutorService service = Executors.newFixedThreadPool(2);
		service.submit(TestSingletonWithoutProblems::useSingleton);
		service.submit(TestSingletonWithoutProblems::useSingleton);
		service.shutdown();
	}
}