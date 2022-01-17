import org.apache.pulsar.client.api.PulsarClient
import org.apache.pulsar.client.api.Schema
import kotlin.random.Random

fun main() {
    val client = PulsarClient.builder().serviceUrl("pulsar://localhost:6650").build()
    val producer = client.newProducer(Schema.STRING).topic("numbers").create()

    val numMessages = 5000
    println("Producer: About to send $numMessages numbers")
    repeat(numMessages) {
        //Thread.sleep(Random.nextLong(500,1000)) // put a random delay between each event, between 5-50ms
        //producer.sendAsync(Random.nextInt(0, 100).toString())
        Thread.sleep(10)
        producer.sendAsync(it.toString())
    }
}