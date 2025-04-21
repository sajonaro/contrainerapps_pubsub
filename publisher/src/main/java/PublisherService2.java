

import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.client.domain.Metadata;
import java.util.Random;
import java.util.UUID;
import static java.util.Collections.singletonMap;

public class PublisherService2 {

  //The name of the pubsub
  private static final String PUBSUB_NAME = "messagebus";
  private static final String TOPIC_NAME = "orders";

  /**
   * This is the entry point of the publisher app example.
   * @param args Args, unused.
   * @throws Exception A startup Exception.
   */
  public static void main(String[] args) throws Exception {
    try (DaprClient client = (new DaprClientBuilder()).build();) {
        Random random = new Random();
        while (true) {
            String orderId = UUID.randomUUID().toString();
            double price = random.nextDouble() * 100;

            String message = String.format("{\"order_id\":\"%s\", \"price\":%f}", orderId, price);

            client.publishEvent(
                    PUBSUB_NAME,
                    TOPIC_NAME,
                    message,
                    singletonMap(Metadata.TTL_IN_SECONDS, "60")).block();

            System.out.println("Published message: " + message);
            Thread.sleep(10000);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
  }
}
