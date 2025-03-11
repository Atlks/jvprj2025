package util.msgDrv;

import java.util.LinkedList;
import java.util.Queue;

/**
 * MessageQueue
 * 功能非常有限，没有提供消息的持久化、事务等功能。
 */
public class MessageQueue {
    private Queue<Message> queue = new LinkedList<>();

    public synchronized void enqueue(Message message) {
        queue.offer(message);
        notify(); // 通知消费者
    }

    public synchronized Message dequeue() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // 等待消息
        }
        return queue.poll();
    }
}
