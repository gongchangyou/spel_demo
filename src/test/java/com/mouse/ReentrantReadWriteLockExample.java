import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReentrantReadWriteLockExample {
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private static final ReentrantReadWriteLock.ReadLock readLock = lock.readLock();
    private static final ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    private static int sharedData = 0;

    public static void main(String[] args) {
        // 创建多个读线程
        for (int i = 1; i <= 3; i++) {
            Thread reader = new Thread(new Reader("Reader " + i));
            reader.start();
        }

        // 创建一个写线程
        Thread writer = new Thread(new Writer("Writer"));
        writer.start();
    }

    static class Reader implements Runnable {
        private String name;

        Reader(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            while (true) {
                readLock.lock();
                try {
                    System.out.println(name + " is reading: " + sharedData);
                } finally {
                    readLock.unlock();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class Writer implements Runnable {
        private String name;

        Writer(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            int count = 0;
            while (true) {
                writeLock.lock();

                try {
                    System.out.println(name + " is writing: " + count);
                    sharedData = count;
                    count++;
                } finally {
                    writeLock.unlock();

                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
