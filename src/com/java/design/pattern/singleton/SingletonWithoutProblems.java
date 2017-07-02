package com.java.design.pattern.singleton;

import java.io.Serializable;

/**
 * Implementation of Singleton design pattern which will handle following
 * scenarios.
 * 
 * 1. Multi-threading
 * 2. Cloneable
 * 3. Serializable
 * 4. Reflection
 * 
 * 
 * @author Sanjay Hingu
 */
public class SingletonWithoutProblems implements Serializable, Cloneable {

	private static volatile SingletonWithoutProblems singleton;

	private SingletonWithoutProblems() throws Exception {
		if (SingletonWithoutProblems.singleton != null) {
			throw new RuntimeException("Cannot create instance");
		} else {
			this.singleton = this;
		}
	}

	public static SingletonWithoutProblems getInstance() throws Exception {
		if (singleton == null) {
			synchronized (SingletonWithProblems.class) {
				if (singleton == null) {
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

}