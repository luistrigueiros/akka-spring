package com.akkaspring

import akka.actor.AbstractActor
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component

@Component
class GreetingService {
    fun greet(name: String) = "Hello, $name"
}

data class Greet(val name: String)


@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
class GreetingActor(private val greetingService: GreetingService) : AbstractActor() {

    fun onGreet(message: Greet) {
        greetingService.greet(message.name).let {
            sender.tell(it, self)
        }
    }

    override fun createReceive(): Receive {
        return receiveBuilder()
                .match(Greet::class.java, this::onGreet)
                .matchAny { msg ->
                    unhandled(msg)
                }
                .build()
    }
}