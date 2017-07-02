package com.java.thread.deadlock;

/**
 * Deadlock problem occur when 2 thread waiting for each other to release lock
 * on the object they are holding.
 * 
 * In order to resolve deadlock issue, methods need to take the lock in the same
 * order. In the below code, method bow & bowBack takes order in the same order
 * so deadlock doesn't occur.
 * 
 * @author Sanjay Hingu
 *
 */
public class DeadlockResolvedWithOrderSynchronization {
	static class Friend {
		final String name;

		public Friend(String name) {
			this.name = name;
		}

		public String getName() {
			return this.name;
		}

		public void bow(Friend bower) {
			synchronized (this) {
				synchronized (bower) {
					System.out.format("%s: %s" + " has bowed to me! %n",
							this.name, bower.getName());
					bower.bowBack(this);
				}
			}
		}

		private synchronized void bowBack(Friend bower) {
			synchronized (this) {
				synchronized (bower) {
					System.out.format("%s: %s" + " has bowed back to me!%n",
							this.name, bower.getName());
				}
			}
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
