package resourcescheduler.implementations;

import resourcescheduler.interfaces.Gateway;
import resourcescheduler.interfaces.Message;
import resourcescheduler.queue.MessageQueue;

public class GateWayImpl implements Gateway, Runnable {

    private static int resourceCount;
    private ResourceMessage resourceMessage;

    public MessageQueue messageQueue;


    public GateWayImpl(int resourceCount) {
        this.resourceCount = resourceCount;
    }

    @Override
    public void send(Message message) {
        resourceMessage = (ResourceMessage)message;
        new Thread(this).start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(int i=1;i<=resourceCount;i++){
            new Thread(new ExternalResources("Resource"+i)).start();
        }

    }

    @Override
    public void run() {
        messageQueue = new MessageQueue(resourceMessage);
        messageQueue.addToWorkQueue();
    }
}
