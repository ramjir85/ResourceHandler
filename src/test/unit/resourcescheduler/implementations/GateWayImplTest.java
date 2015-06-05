package resourcescheduler.implementations;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import resourcescheduler.interfaces.Message;
import resourcescheduler.queue.MessageQueue;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class GateWayImplTest {

    private final int RESOURCE_COUNT = 1;


    ResourceMessage resourceMessage = mock(ResourceMessage.class);

    MessageQueue messageQueue = mock(MessageQueue.class);

    ArgumentCaptor<Message> messageArgumentCaptor = ArgumentCaptor.forClass(Message.class);

    @Test
    public void shouldSendMessagesToExternalResources() {

        messageQueue = new MessageQueue(resourceMessage);
        GateWayImpl gateWay = new GateWayImpl(RESOURCE_COUNT);
        gateWay.send(messageArgumentCaptor.capture());

        verify(messageQueue, atLeastOnce()).addToWorkQueue();

    }


}