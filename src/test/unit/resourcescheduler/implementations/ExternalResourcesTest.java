package resourcescheduler.implementations;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import resourcescheduler.queue.MessageQueue;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ExternalResourcesTest {


    private static final int EMPTY = 0;
    private final String THREAD_NAME = "Resource";


    ResourceMessage resourceMessage = mock(ResourceMessage.class);

    MessageQueue messageQueue = mock(MessageQueue.class);

    @Test
    public void shouldProcessMessageAsTheyCome() throws InterruptedException {
        messageQueue = new MessageQueue(resourceMessage);

        MessageQueue.messageQueue.add(resourceMessage);
        ExternalResources externalResources = new ExternalResources(THREAD_NAME);
        externalResources.processMessage();
        assertThat(MessageQueue.messageQueue.size()).isEqualTo(EMPTY);
    }

    @Test
    public void shouldWaitForMessagesWhenWorkQueueDoesNotHaveAnyMessages() throws InterruptedException {
        messageQueue = new MessageQueue(resourceMessage);
        ExternalResources externalResources = new ExternalResources(THREAD_NAME);
        externalResources.processMessage();

        Thread.sleep(5000);

        assertThat(externalResources.checkCount).isEqualTo(5);


    }

}