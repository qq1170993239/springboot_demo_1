package com.lix.study;

public class NumberPrint implements Runnable {

	private String name;
	public Object res;
	public static int count = 100;

	public NumberPrint(String name, Object a) {
		this.name = name;
		this.res = a;
	}

	@Override
	public void run() {
		synchronized (res) {
			while (count > 0) {
				try {
					count--;
					res.notify();// 唤醒等待res资源的线程，把锁交给线程（该同步锁执行完毕自动释放锁）
					System.out.println(" " + name);
					res.wait();// 释放CPU控制权，释放res的锁，本线程阻塞，等待被唤醒。
					System.out.println("------线程" + Thread.currentThread().getName() + "获得锁，wait()后的代码继续运行：" + name);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} // end of while
			return;
		} // synchronized
	}

	public static void main(String[] args) {
		Object lock = new Object();// 以该对象为共享资源
		new Thread(new NumberPrint("线程-0", lock)).start();
		new Thread(new NumberPrint("线程-1", lock)).start();
		new Thread(new NumberPrint("线程-2", lock)).start();
	}

}