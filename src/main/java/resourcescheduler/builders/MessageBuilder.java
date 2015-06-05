package resourcescheduler.builders;


import resourcescheduler.implementations.ResourceMessage;

public class MessageBuilder {

    private String messageGroup;
    private String messageContent;
    private boolean terminated;

    public ResourceMessage build() {
        return new ResourceMessage(messageGroup, messageContent);
    }

    public MessageBuilder withMessageGroup(String messageGroup) {
        this.messageGroup = messageGroup;
        return this;
    }

    public MessageBuilder withMessageContent(String messageContent) {
        this.messageContent = messageContent;
        return this;
    }
}
