package com.jt.common.mq;

import java.util.Arrays;

/**
 * 基于FIFO设计一个容器对象,底层实现为数组
 * 由数组实现了循环队列
 */
public class Container {
	private Object[] array;
	private int cap;
	/**记录有效元素个数*/
	private int size;
	private int head;
	/**rear为空的数组队列*/
	private int rear;
	
	public Container() {
		this(10);//默认为10
		System.out.println("Container.Container()");
	}
	public Container(int cap) {//cap为初始容量
		array = new Object[cap];
		this.cap = cap;
		System.out.println("Container.Container(int)");
	}
	/**向容器放数据(容器满了要扩容)*/
	public void add(Object obj) {
		//1. 判定容器是否已满,满了则扩容
		if(isFull()) {
			appendArray();
		}
		//2. 放数据到容器
		array[rear] = obj;
		//3. 有效元素个数加1
		rear = (rear + cap + 1) % cap;
		size++;
	}
	private void appendArray() {
		if(head<rear) {
			//没有折叠
			array = Arrays.copyOf(array, 2*cap);
		} else {
			//存在折叠,拷贝到扩容数组时展开
			Object[] newArray = new Object[2*cap];
			for(int i=0;i<rear;i++) {
				newArray[i+cap] = array[i];
			}
			rear = rear + cap;
		}
		cap = 2*cap;
	}
	/**从容器取数据*/
	public Object remove() {
		if(isEmpty())return null;
		//从head处取数据
		Object data = array[head];
		//head置空
		array[head] = null;
		//head后移
		head = (head + cap + 1) % cap;
		size--;
		return data;
	}
	private boolean isFull() {
		return (cap+rear-head+1)%cap==0?true:false;
	}
	private boolean isEmpty() {
		return rear==head?true:false;
	}
	//重写toString
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder("[");
		for(int i=head;i!=rear;) {
			str.append(String.valueOf(array[i]));
			if((i=(i+1+cap)%cap)==rear)break;
			str.append(", ");
		}
		str.append("]");
		return str.toString();
	}
	
	//Spring中查看对象的生成和销毁
	public void destroy() {
		System.out.println("Container.destroy()");
	}
	public void init() {
		System.out.println("Container.init()");
	}
 }
