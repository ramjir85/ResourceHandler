package resourcescheduler.handler;

import resourcescheduler.implementations.GateWayImpl;
import resourcescheduler.implementations.ResourceMessage;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class ResourceMessageHandlerTest {

    public static final int RESOURCE_COUNT = 1;
    ResourceMessage resourceMessage = mock(ResourceMessage.class);
    GateWayImpl gateWay = mock(GateWayImpl.class);

    @Test
    public void shouldHandleMessageAndReturnTrueForPriorityAndNonCancelledGroup() {
        when(resourceMessage.getMessageGroup()).thenReturn("group1");
        ResourceMessageHandler resourceMessageHandler = new ResourceMessageHandler(resourceMessage, RESOURCE_COUNT);
        ResourceMessageHandler.cancelledGroup = "group1";
        boolean returnValue = resourceMessageHandler.handleMessage();

        assertThat(returnValue).isEqualTo(true);
    }

    @Test
    public void shouldHandleMessageAndReturnFalseForAndCancelledGroup() {
        when(resourceMessage.getMessageGroup()).thenReturn("group1");
        ResourceMessageHandler resourceMessageHandler = new ResourceMessageHandler(resourceMessage, RESOURCE_COUNT);
        ResourceMessageHandler.cancelledGroup = "group2";
        boolean returnValue = resourceMessageHandler.handleMessage();

        assertThat(returnValue).isEqualTo(false);
    }

    /*@Test
    public void shouldSendMessageToGatewayIfResourcesAreThere() {

        ResourceMessageHandler.messageQueue.add(resourceMessage);
        System.out.println("Size of resourcescheduler.queue --"+ResourceMessageHandler.messageQueue.size());
        //ArgumentCaptor<ResourceMessage> queueArgumentCaptor = ArgumentCaptor.forClass(ResourceMessage.class);
        ResourceMessageHandler resourceMessageHandler = new ResourceMessageHandler(resourceMessage,RESOURCE_COUNT);
        resourceMessageHandler.sendToGateWay();

        verify(gateWay, atLeastOnce()).send(any(ResourceMessage.class));

    }*/

}