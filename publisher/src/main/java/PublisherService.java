import io.dapr.client.DaprClient;
import io.dapr.client.DaprClientBuilder;
import io.dapr.client.domain.Metadata;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Random;
import java.util.UUID;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.service.model.Order;

import static java.util.Collections.singletonMap;

import java.time.Duration;

@SpringBootApplication @Slf4j
public class PublisherService {

  //Number of messages to be sent.
  private static final int NUM_MESSAGES = 10000;

  //Time-to-live for messages published.
  private static final String MESSAGE_TTL_IN_SECONDS = "1000";

  //The title of the topic to be used for publishing
  private static final String DEFAULT_TOPIC_NAME = "testingtopic";

  //The name of the pubsub
  private static final String PUBSUB_NAME = "messagebus";

  static final Random random = new Random();

  /**
   * This is the entry point of the publisher app example.
   * @param args Args, unused.
   * @throws Exception A startup Exception.
   */
  public static void main(String[] args) throws Exception {
    String topicName = getTopicName(args);

    try (DaprClient client = new DaprClientBuilder().build()) {
      client.waitForSidecar(30_000).then(
        Flux.range(0, NUM_MESSAGES).flatMap(_ -> {
          var order = Order.builder().orderId(UUID.randomUUID().toString()).price(random.nextDouble() * 100).build();
          return client.publishEvent(
              PUBSUB_NAME,
              topicName,
              order,
              singletonMap(Metadata.TTL_IN_SECONDS, MESSAGE_TTL_IN_SECONDS)
          )
          .then(Mono.fromRunnable(() -> {
            log.info("Published message: {}",  order);
          }));
        }).then()
      ).block();

      log.info("Done.");
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

