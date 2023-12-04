package co.nz.westpac.producer;

import com.azure.messaging.eventhubs.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class Sender {

    @Value("${connection.string}")
    private String connectionString;

    @Value("${eventhub.name}")
    private String eventHubName;

    /**
     * Code sample for publishing events.
     * @throws IllegalArgumentException if the EventData is bigger than the max batch size.
     */
    public void publishEvents(List<Message> messages) {
        // create a producer client
        EventHubProducerClient producer = new EventHubClientBuilder()
                .connectionString(connectionString, eventHubName)
                .buildProducerClient();

        // sample events in an array
//        List<EventData> allEvents = Arrays.asList(new EventData("Foo"), new EventData("Bar"));
        List<EventData> allEvents = convertMessagesToEventData(messages);


        // create a batch
        EventDataBatch eventDataBatch = producer.createBatch();

        for (EventData eventData : allEvents) {
            // try to add the event from the array to the batch
            if (!eventDataBatch.tryAdd(eventData)) {
                // if the batch is full, send it and then create a new batch
                producer.send(eventDataBatch);
                eventDataBatch = producer.createBatch();

                // Try to add that event that couldn't fit before.
                if (!eventDataBatch.tryAdd(eventData)) {
                    throw new IllegalArgumentException("Event is too large for an empty batch. Max size: "
                            + eventDataBatch.getMaxSizeInBytes());
                }
            }
        }
        // send the last batch of remaining events
        if (eventDataBatch.getCount() > 0) {
            log.info("Sending...");
            producer.send(eventDataBatch);
        }
        producer.close();
    }

    public static List<EventData> convertMessagesToEventData(List<Message> messages) {
        return messages.stream().map(message -> new EventData(message.toString())).toList();
    }
}
