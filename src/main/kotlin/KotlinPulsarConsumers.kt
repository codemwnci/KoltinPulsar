import org.apache.pulsar.client.api.PulsarClient
import org.apache.pulsar.client.api.Schema
import org.apache.pulsar.client.api.SubscriptionType
import java.util.*

fun main(args: Array<String>) {
    val client = PulsarClient.builder().serviceUrl("pulsar://localhost:6650").build()
    val id = args[0]

    val name = "main-numbercounter-$id"

    val consumer = client.newConsumer(Schema.STRING).topic("numbers")
        .subscriptionName(name)
        .subscriptionType(SubscriptionType.Shared)
        .subscribe()

    println("Waiting for first message")
    while (true) {
        val msg = consumer.receive()

        println("$name - received msg: ${msg.value} from msgId: ${msg.messageId} time: ${Date(msg.publishTime)}")
        Thread.sleep(50)
        consumer.acknowledge(msg)

        println("Acknowledged msgId: ${msg.messageId}")
    }
}