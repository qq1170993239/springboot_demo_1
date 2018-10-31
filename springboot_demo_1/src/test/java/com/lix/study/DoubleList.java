package com.lix.study;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lix on 2018/10/29/029.
 */
public class DoubleList<T> {

    private int size;

    public DoubleList(int size){
        this.size = size;
    }

    /**
     * 重入锁
     */
    private Lock lock = new ReentrantLock();

    /**
     * 取
     */
    private Condition pull = lock.newCondition();
    /**
     * 存
     */
    private Condition push = lock.newCondition();

    /**
     * 第一个队列(存)
     */
    private List<T> first = new LinkedList<T>();
    /**
     * 第二个队列(取)
     */
    private List<T> second = new LinkedList<T>();

    public static void main(String[] args) {
        DoubleList<Integer> dlist = new DoubleList(120);
        Random ram = new Random(10000000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    dlist.setValue(ram.nextInt());
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    dlist.setValue(ram.nextInt());
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    System.out.println(dlist.getValue());
                }
            }
        }).start();
    }


    public T getValue(){
        // 加锁
        lock.lock();
        try {
            // 如果取得列队为空，就从存的列队取
            if(second.size() < 1){
                // 如果存的列队大小小于size的0.75倍就等待
                if(first.size() < size * 0.75){
                    pull.await();
                }
                second.addAll(first);
                first.clear();
                push.signalAll();
            }
            // 获取一个
            T value = second.remove(0);
            return value;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return null;
    }

    public boolean setValue(T value){
        assert value != null;
        lock.lock();
        try {
            if(first.size() < size){
                first.add(value);
                if(first.size() > size * 0.75){
                    pull.signalAll();
                }
            }else{
                push.await();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return true;
    }
}
