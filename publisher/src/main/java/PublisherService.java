import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.client.domain.Metadata;
import java.util.Random;
import java.util.UUID;

import static java.util.Collections.singletonMap;


public class PublisherService {

  //Number of messages to be sent.
  private static final int NUM_MESSAGES = 10000;

  //Time-to-live for messages published.
  private static final String MESSAGE_TTL_IN_SECONDS = "1000";

  //The title of the topic to be used for publishing
  private static final String DEFAULT_TOPIC_NAME = "testingtopic";

  //The name of the pubsub
  private static final String PUBSUB_NAME = "messagebus";

  /**
   * This is the entry point of the publisher app example.
   * @param args Args, unused.
   * @throws Exception A startup Exception.
   */
  public static void main(String[] args) throws Exception {
    String topicName = getTopicName(args);
    try (DaprClient client = new DaprClientBuilder().build()) {
      Random random = new Random();
      for (int i = 0; i < NUM_MESSAGES; i++) {
        
        String orderId = UUID.randomUUID().toString();
        Double price = random.nextDouble() * 100;
        String message = String.format("{\"order_id\":\"%s\", \"price\":%f}", orderId, price);

        // Publishing messages
        client.publishEvent(
            PUBSUB_NAME,
            topicName,
            message,
            singletonMap(Metadata.TTL_IN_SECONDS, MESSAGE_TTL_IN_SECONDS)).block();

        System.out.println("Published message: " + message);

        try {
          Thread.sleep((long) (1000 ));
        } catch (InterruptedException e) {
          e.printStackTrace();
          Thread.currentThread().interrupt();
          return;
        }
      }

      // This is an example, so for simplicity we are just exiting here.
      // Normally a dapr app would be a web service and not exit main.
      System.out.println("Done.");
    }
  }

  /**
   * If a topic is specified in args, use that.
   * Else, fallback to the default topic.
   * @param args program arguments
   * @return name of the topic to publish messages to.
   */
  private static String getTopicName(String[] args) {
    if (args.length >= 1) {
      return args[0];
    }
    return DEFAULT_TOPIC_NAME;
  }
}