package resourcescheduler.queue;

import resourcescheduler.implementations.ResourceMessage;

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Logger;

public class MessageQueue {

    private static final Logger LOGGER = Logger.getLogger(MessageQueue.class.getName());

    private ResourceMessage resourceMessage;
    public static Queue<ResourceMessage> messageQueue = new LinkedList<>();

    public MessageQueue(ResourceMessage resourceMessage) {
        this.resourceMessage = resourceMessage;
    }

    public boolean addToWorkQueue(){
        LOGGER.info("Adding message to work resourcescheduler.queue-----"+resourceMessage.toString());
        messageQueue.add(this.resourceMessage);
        return true;
    }

    public static ResourceMessage readFromWorkQueue(){
        ResourceMessage resourceMessage = messageQueue.remove();
        resourceMessage.completed();
        return resourceMessage;
    }
}
