import kotlinx.coroutines.*
import org.apache.pulsar.client.api.PulsarClient
import org.apache.pulsar.client.api.Schema
import kotlin.random.Random

fun main() {
    val client = PulsarClient.builder().serviceUrl("pulsar://localhost:6650").build()
    val producer = client.newProducer(Schema.STRING).topic("numbers").create()

    // run two parallel processes    
    runBlocking {


        launch(Dispatchers.Default) {
            println("Producer: About to send 500 numbers")
            repeat(500) {
                //delay(Random.nextLong(5,50)) // put a random delay between each event, between 5-50ms
                producer.sendAsync(Random.nextInt(0, 100).toString())
            }
        }


        launch(Dispatchers.Default) {
            println("Consumer: Getting ready to read the numbers")

            val consumer = client.newConsumer(Schema.STRING).topic("numbers")
                .subscriptionName("main-numbercounter-1")
                .subscribe()

            var total = 0
            var count = 0
            println("Waiting for first message")
            while (true) {
                val msg = consumer.receive()
                val num = msg?.value?.toInt() ?: 0
                total += num
                count++
                consumer.acknowledge(msg)

                println("$count: Received $num, total is now $total")
            }
        }
    }

    producer.close()
    client.close()
}