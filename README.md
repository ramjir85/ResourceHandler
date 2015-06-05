# ResourceHandler
Application for handling messages and the resources consuming those message.

## Description of various classes used

### ResourceScheduler:
This being the main class that gives the user the  option of configuring the number of resources that will be used at any time during the message processing. 

### MessageBuilder:
A builder pattern is being used here, with a prime MessageBuilder class that builds the each message with particular message content and group id. 

``java

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
``

### ResourceMessageHandler:
This class mainly handles the sorted message and sends them to the Gateway for the External Resource software to process them.

### ExternalResource:
This class is a Runnable thread that processes the messages based on the number of resources that has been configured. 

### GateWayImpl:
This class is an implementation of Gateway and Runnable, that sends the pre-sorted messages to the External Resources for processing. 

### ResourceMessage:
This is an implementation and represenation of a message. It has the message content and message group if as properties for the messages.

## Gateway and Message are the interfaces that provides the contract for the sending the message and type of message 

### MessageQueue:
This is a representation of an actual work queue where messages are sent and picked by the external resources for processing them.

