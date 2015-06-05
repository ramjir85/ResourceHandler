package resourcescheduler.implementations;


import resourcescheduler.interfaces.Message;

import java.util.logging.Logger;

public class ResourceMessage implements Message {

    private static final Logger LOGGER = Logger.getLogger(ResourceMessage.class.getName());

    private String messageGroup;
    private String messageContent;

    public ResourceMessage(String messageGroup, String messageContent) {
        this.messageGroup = messageGroup;
        this.messageContent = messageContent;
    }

    public String getMessageGroup() {
        return messageGroup;
    }

    public String getMessageContent() {
        return messageContent;
    }

    @Override
    public String completed() {
        LOGGER.info(String.format("Done processing the Message: %s with the GroupID: %s ", messageContent, messageGroup));
        return this.messageContent.concat("T");
    }

    @Override
    public String toString() {
        return "ResourceMessage{" +
                "messageGroup='" + messageGroup + '\'' +
                ", messageContent='" + messageContent + '\'' +
                '}';
    }
}
