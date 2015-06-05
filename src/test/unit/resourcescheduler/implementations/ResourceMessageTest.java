package resourcescheduler.implementations;

import org.junit.Test;


import static org.assertj.core.api.Assertions.assertThat;


public class ResourceMessageTest {


    @Test
    public void shouldSendMessageProcessingCompletedSignal() {

        ResourceMessage resourceMessage = new ResourceMessage("group1","A");
        String completedSignal = resourceMessage.completed();

        assertThat(completedSignal).isEqualTo("AT");

    }
}