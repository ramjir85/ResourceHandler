package resourcescheduler.implementations;

import resourcescheduler.queue.MessageQueue;

import java.util.logging.Logger;

public class ExternalResources implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(ExternalResources.class.getName());

    private static int resourceCount;
    private String threadName;
    public int checkCount = 0;

    public int getCheckCount() {
        return checkCount;
    }

    public ExternalResources(String threadName) {

        this.threadName = threadName;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            processMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void processMessage() throws InterruptedException {

        if (MessageQueue.messageQueue.size() > 0) {
            LOGGER.info("Processing Message.......");
            LOGGER.info("Read the message from resourcescheduler.queue ----------" + MessageQueue.readFromWorkQueue() + "--------> by Thread: " + threadName);
        } else {
            if (checkCount < 5) {
                checkCount++;
                LOGGER.info("Awaiting for any new message before timing out......");
                processMessage();
            }
        }
    }

    public static void setResourceCount(int resourceCount) {
        ExternalResources.resourceCount = resourceCount;
    }
}