package resourcescheduler.handler;


import resourcescheduler.implementations.ExternalResources;
import resourcescheduler.implementations.GateWayImpl;
import resourcescheduler.implementations.ResourceMessage;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.logging.Logger;


public class ResourceMessageHandler {

    private static final Logger LOGGER = Logger.getLogger(ResourceMessageHandler.class.getName());
    public static String priorityGroup;
    public static int priorityGroupCount = 0;
    public static String cancelledGroup;

    private ResourceMessage resourceMessage;
    private static int resourceCount;
    private ExternalResources externalResources;

    private String completedMessage = null;

    public static Queue<ResourceMessage> messageQueue = new LinkedList<>();

    public ResourceMessageHandler(ResourceMessage resourceMessage, int resourceCount) {
        this.resourceMessage = resourceMessage;
        this.resourceCount = resourceCount;
        if (priorityGroupCount == 0) {
            this.priorityGroup = resourceMessage.getMessageGroup();
            priorityGroupCount++;
        }

    }

    public boolean handleMessage() {
        boolean queued = false;
        if (resourceMessage.getMessageGroup().equals(priorityGroup) && resourceMessage.getMessageGroup() != cancelledGroup) {
            messageQueue.add(resourceMessage);
            LOGGER.info("Message sorted: ---"+resourceMessage.getMessageGroup()+" with Message Content "+resourceMessage
                    .getMessageContent());
        } else {
            queued = true;
        }
        return queued;
    }

    public static void sendToGateWay() {
        if(resourceCount>0){
            GateWayImpl gateWay = new GateWayImpl(resourceCount);
            while(messageQueue.size()>0) {
                gateWay.send(messageQueue.remove());
            }
        } else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the number if resource you want :- ");
            resourceCount = scanner.nextInt();
            sendToGateWay();
        }
    }
}
