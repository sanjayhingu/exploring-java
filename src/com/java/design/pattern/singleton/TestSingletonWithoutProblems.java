package com.java.design.pattern.singleton;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Test;

public class TestSingletonWithoutProblems {

	@Test (expected = InvocationTargetException.class)
	public void testReflection() throws Exception {
		SingletonWithoutProblems s1 = SingletonWithoutProblems.getInstance();
		SingletonWithoutProblems s2 = SingletonWithoutProblems.getInstance();
		Assert.assertEquals("Singleton design pattern failed", s1, s2);
		
		Class singletonClass = Class.forName("com.java.design.pattern.singleton.SingletonWithoutProblems");
		Constructor<SingletonWithoutProblems> constructor = singletonClass.getDeclaredConstructor();
		constructor.setAccessible(Boolean.TRUE);
		SingletonWithoutProblems s3 = constructor.newInstance();
	}
	
	@Test
	public void testCloneable() throws Exception {
		SingletonWithoutProblems s1 = SingletonWithoutProblems.getInstance();
		SingletonWithoutProblems s2 = (SingletonWithoutProblems) s1.clone();
		Assert.assertEquals("Singleton pattern failed Cloneable test", s1, s2);
	}
	
	@Test
	public void testSerializable() throws Exception {
		SingletonWithoutProblems s1 = SingletonWithoutProblems.getInstance();
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("singletonwithoutprob.ser"));
		oos.writeObject(s1);
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream("singletonwithoutprob.ser"));
		SingletonWithoutProblems s2 = (SingletonWithoutProblems) ois.readObject();
		Assert.assertEquals("Singleton pattern failed serializable test", s1, s2);
	}
	
	private static SingletonWithoutProblems useSingleton() {
		SingletonWithoutProblems singleton = null;
		try {
			singleton = SingletonWithoutProblems.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return singleton;
	}	
	
	@Test
	public void testMultithreading() throws Exception {
		ExecutorService service = Executors.newFixedThreadPool(2);
		Future<SingletonWithoutProblems> s1 = service.submit(TestSingletonWithoutProblems::useSingleton);
		Future<SingletonWithoutProblems> s2 = service.submit(TestSingletonWithoutProblems::useSingleton);
		service.shutdown();
		Assert.assertEquals("Singleton pattern failed multithreading test", s1.get(), s2.get());
	}
	
}
