package concurrency;
import java.util.Queue;
import java.util.PriorityQueue;
//дополнительное задание 2
public class SimpleBlockingQueue<T> {
	Queue<T> queue = new PriorityQueue<T>();
	int size;
	public SimpleBlockingQueue(int size) { this.size = size; }
	public synchronized void enqueue(T item) {
		if (this.size == this.size()) {
			try { wait(); }
			catch (InterruptedException interruptedException) {}
		}
		this.queue.add(item);
		notify();
		}
	public synchronized T dequeue() {
		if (this.size() == 0) {
			try { wait(); }
			catch (InterruptedException interruptedException) {}
		}
		T t = this.queue.poll();
		notify();
		return t;
		}
	int size() { return this.queue.size(); }
}