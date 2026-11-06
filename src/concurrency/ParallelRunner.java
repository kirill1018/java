package concurrency;
import java.util.concurrent.*;
import java.util.*;
public class ParallelRunner {
	public static void main(String[] args) {
		long nanoTimeBefRun = System.nanoTime();
		ExecutorService executorService = Executors.newFixedThreadPool(4);
		try {
			List<TestResult> testOutc = collect(launch(executorService, tests()));
			Map<Status, Integer> standings = count(testOutc);
			System.out.println(result(standings, testOutc,
					measure(nanoTimeBefRun)));
			}
		catch (InterruptedException interruptedException) {}
		catch (ExecutionException executionException) {}
		finally { executorService.shutdown(); }
		//дополнительное задание 1
		try { System.out.println(DataAggregator
				.aggregateProductInfo("ноутбук").toString());
		}
		catch (InterruptedException interruptedException) {}
		catch (ExecutionException executionException) {}
		//дополнительное задание 2
		block();
	}
	static Callable<TestResult> fakeTest(String name, long durationMs,
			Status status) { return () -> {
				Thread.sleep(durationMs);
				return new TestResult(name, status, durationMs);
				};
				}
	static List<Callable<TestResult>> tests() {
		Callable<TestResult> test1 = fakeTest("test1", 400,
				Status.PASSED), test2 = fakeTest("test2", 400,
						Status.PASSED),
				test3 = fakeTest("test3", 400,
						Status.PASSED), test4 = fakeTest("test4", 400,
								Status.FAILED),
						test5 = fakeTest("test5", 400,
								Status.FAILED), test6 = fakeTest("test6", 400,
										Status.PASSED),
								test7 = fakeTest("test7", 400,
										Status.PASSED), test8 = fakeTest("test8", 400,
												Status.PASSED);
		return List.of(test1, test2,
				test3, test4,
				test5, test6,
				test7, test8);
		
	}
	static List<Future<TestResult>> launch(ExecutorService service, List<Callable<TestResult>> tests) throws InterruptedException { return service.invokeAll(tests); }
	static List<TestResult> collect(List<Future<TestResult>> tomorrows) throws ExecutionException, InterruptedException {
		List<TestResult> testRes = new ArrayList<TestResult>();
		for (Future<TestResult> future : tomorrows) testRes.add(future.get());
		return testRes;
	}
	static Map<Status, Integer> count(List<TestResult> results) {
		Map<Status, Integer> statuses = new HashMap<Status, Integer>();
		for (TestResult testResult : results) statuses.merge(testResult.status(), 1,
				(value1, value2) -> value1 + 1);
		return statuses;
	}
	static long measure(long time) { return System.nanoTime() - time; }
	static String result(Map<Status, Integer> ranks, List<TestResult> results,
			long time) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("прошло: ");
		stringBuilder.append(ranks.get(Status
				.PASSED));
		stringBuilder.append(", ");
		stringBuilder.append("упало: ");
		stringBuilder.append(ranks.get(Status
				.FAILED));
		stringBuilder.append("\n");
		stringBuilder.append("суммарное время тестов: ");
		int totTime = 0;
		for (TestResult testResult : results) totTime += testResult.durationMs();
		stringBuilder.append(totTime);
		stringBuilder.append(" ms\n");
		stringBuilder.append("реальное время прогона: ");
		long realTime = time / (long)Math.pow(10, 6);
		stringBuilder.append(realTime);
		stringBuilder.append(" ms\n");
		stringBuilder.append("параллельность сэкономила: ");
		stringBuilder.append(totTime - realTime);
		stringBuilder.append(" ms");
		return stringBuilder.toString();
	}
	//дополнительное задание 2
	static void block() {
		int size = 5;
		SimpleBlockingQueue<String> simpleBlockingQueue = new SimpleBlockingQueue<String>(1);
		Thread thread1 = new Thread() {
			@Override
			public void run() {
				String[] lines = { "line1", "line2",
						"line3", "line4",
						"line5" };
				for (int i = 0; i < size; i++) {
					simpleBlockingQueue.enqueue(lines[i]);
					try { Thread.sleep(500); }
					catch (InterruptedException interruptedException) {}
				}
			}
		}, thread2 = new Thread() {
			@Override
			public void run() { for (int i = 0; i < size; i++) System.out.println(simpleBlockingQueue
					.dequeue());
			}
		};
		Thread[] threads = { thread1, thread2 };
		for (Thread thread : threads) thread.start();
	}
}