package resourcescheduler;

import resourcescheduler.builders.MessageBuilder;
import resourcescheduler.implementations.ResourceMessage;
import resourcescheduler.implementations.ExternalResources;
import resourcescheduler.handler.ResourceMessageHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;


public class ResourceScheduler implements Runnable {


    private static final Logger LOGGER = Logger.getLogger(ResourceScheduler.class.getName());
    List<ResourceMessage> messageList = new ArrayList<>();
    private static int resourceCount;

    private static List list = new ArrayList();

    public static void main(String args[]) throws InterruptedException {

        LOGGER.info("Starting the resource manager-------------");

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the number of resource you want to configure:-  ");
        resourceCount = scanner.nextInt();

        ResourceScheduler createMaintainResources = new ResourceScheduler();

        list = createMaintainResources.generateMessages(5);
        createMaintainResources.messageSender(resourceCount);

    }

    private void messageSender(int resourceCount) {
        ExternalResources.setResourceCount(resourceCount);
        new Thread(this).start();
    }

    public List generateMessages(int i) {
        String groupId[] = new String[]{"group1", "group2", "group3", "group4"};
        boolean terminationInd[] = new boolean[]{true, false};
        MessageBuilder messageBuilder = new MessageBuilder();
        int groupCount = 0;
        for (int count = 0; count < i; count++) {
            if(count==i/2) {
                LOGGER.info(String.format("Setting this groupID: %s as cancelled",groupId[groupCount]));
                ResourceMessageHandler.cancelledGroup = groupId[groupCount];
                messageList.add(messageBuilder.withMessageGroup(groupId[groupCount]).withMessageContent(randomStringGenerator(count)).build());
            } else {
                messageList.add(messageBuilder.withMessageGroup(groupId[groupCount]).withMessageContent(randomStringGenerator(count)).build());
            }
            if (groupCount == 3) {
                groupCount = 0;
            } else {
                groupCount++;
            }
        }
        return messageList;
    }

    public String randomStringGenerator(int seed) {
        return repeatCharacter(seed);

    }

    public String repeatCharacter(int times) {
        String repeatString = "A";
        for (int i = 0; i < times; i++) {
            repeatString = repeatString.concat("A");
        }
        return repeatString;
    }


    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        transmitMessage(list);

        ResourceMessageHandler.sendToGateWay();

    }

    private void transmitMessage(List list) {
        ResourceMessageHandler resourceMessageHandler = null;
        List<ResourceMessage> queuedMessage = new ArrayList<>();
        int variableListSize = list.size();
        for (int i = 0; i < variableListSize; ) {
            resourceMessageHandler = new ResourceMessageHandler((ResourceMessage) list.get(i), resourceCount );
            boolean queued = resourceMessageHandler.handleMessage(); //resourceMessageHandler.sendMessage();
            if (queued) {
                if(((ResourceMessage) list.get(i)).getMessageGroup().equals(ResourceMessageHandler.cancelledGroup)){
                    LOGGER.info(String.format("Encountered cancelled group id message"));
                    list.remove(i);
                } else {
                    queuedMessage.add((ResourceMessage) list.get(i));
                    list.remove(i);
                }
                variableListSize = list.size();
            } else {
                i++;
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while (queuedMessage.size() > 0) {
            ResourceMessageHandler.priorityGroupCount = 0;
            transmitMessage(queuedMessage);
            queuedMessage = new ArrayList<>();
        }
    }
}