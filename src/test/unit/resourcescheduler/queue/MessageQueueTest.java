package resourcescheduler.queue;

import org.junit.Test;
import resourcescheduler.implementations.ResourceMessage;

import static com.sun.javaws.JnlpxArgs.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class MessageQueueTest {

    public static final int EMPTY = 0;
    ResourceMessage resourceMessage = mock(ResourceMessage.class);

    @Test
    public void shouldAddDataToMessageQueue() {

        MessageQueue messageQueue = new MessageQueue(resourceMessage);
        boolean added = messageQueue.addToWorkQueue();

        assertThat(added).isEqualTo(true);
        assertThat(MessageQueue.messageQueue.size()).isGreaterThan(EMPTY);
    }

    @Test
    public void shouldBeAbleToRetreiveMessageFromQueue() {

        MessageQueue messageQueue = new MessageQueue(resourceMessage);
        messageQueue.addToWorkQueue();
        ResourceMessage returnMessage = MessageQueue.readFromWorkQueue();

        assertThat(returnMessage.getMessageContent()).isEqualTo(resourceMessage.getMessageContent());
        assertThat(returnMessage.getMessageGroup()).isEqualTo(resourceMessage.getMessageGroup());
    }

}