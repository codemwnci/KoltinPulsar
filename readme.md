# Some Experiments of using Kotlin and Apache Pulsar 

This is a collection of experiments of using Kotlin with Apache Pulsar

## How to Run
To run Pulsar, the easiest way is to run straight from Docker
    
    docker run -it --rm -p 6650:6650 -p 8080:8080 -v data:/pulsar/data apachepulsar/pulsar:2.6.0 bin/pulsar standalone

And then, simply execute one of the Kotlin main classes.

- _KotlinPulsar.kt_ - This file contains both a Producer and a Consumer, running in seperate coroutines (lightweight threads)
- _KotlinPulsarProducer.kt_ - This file contains a standalone producer, that will publish messages to Pulsar 
- _KotlinPulsarConsumers.kt_ - This file contains a standalone consumer, which takes an ID as a parameter, to allow multiple consumers to read the same topic

The easiest way to execute the main classes is to use the maven exec command

1. Combined Producer / Consumer (KotlinPulsar.kt)

    
    mvn exec:java -Dexec.mainClass=KotlinPulsarKt

2. Standalone Producer


    mvn exec:java -Dexec.mainClass=KotlinPulsarProducerKt


3. Standalone Consumer(s)


    mvn exec:java -Dexec.mainClass=KotlinPulsarConsumersKt -Dexec.args=1


Note: On the standalone consumers, it takes an argument (in the example below, a 1). This appends to the consumer name. If you start multiple consumers with the same argument, the consumption will be Shared (based on the strategy specified in the code). If you start multiple consumers with different arguments, each consumer will consume all messages.




