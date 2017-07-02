package com.java.thread.deadlock;


/**
 * Deadlock describes a situation where two or more threads are blocked forever,
 * waiting for each other. Here's an example.
 * 
 * Alphonse and Gaston are friends, and great believers in courtesy. A strict
 * rule of courtesy is that when you bow to a friend, you must remain bowed
 * until your friend has a chance to return the bow. Unfortunately, this rule
 * does not account for the possibility that two friends might bow to each other
 * at the same time. This example application, Deadlock, models this
 * possibility:
 * 
 * @author Sanjay Hingu
 *
 */
public class Deadlock {

	static class Friend {
		final String name;

		public Friend(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public synchronized void bow(Friend bower) {
			System.out.format("%s: %s" + " has bowed to me! %n", this.name,
					bower.getName());
			bower.bowBack(this);
		}

		public synchronized void bowBack(Friend bower) {
			System.out.format("%s: %s" + " has bowed back to me!%n", this.name,
					bower.getName());
		}
	}

	public static void main(String[] args) {
		Friend dumb1 = new Friend("Dumb-1");
		Friend dumb2 = new Friend("Dumb-2");

		new Thread(new Runnable() {
			public void run() {
				dumb1.bow(dumb2);
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				dumb2.bow(dumb1);
			}
		}).start();

	}

}
