package com.akkaspring

import akka.actor.ActorSystem
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests
import scala.concurrent.Await
import scala.concurrent.duration.FiniteDuration
import java.util.concurrent.TimeUnit
import akka.util.Timeout
import akka.pattern.Patterns.ask

@ContextConfiguration(classes = [AppConfiguration::class])
class IntegrationTest : AbstractJUnit4SpringContextTests() {
    @Autowired
    private lateinit var system: ActorSystem

    @After
    fun tearDown() {
        system.terminate().value()
    }

    @Test
    fun firstSimpleTest() {
        val greetingActor = SpringExtension.get(system).props("greetingActor")
        val greeter = system.actorOf(greetingActor, "greeter")
        val duration = FiniteDuration.create(1, TimeUnit.SECONDS)
        val timeout = Timeout.durationToTimeout(duration)
        val result = ask(greeter, GreetingActor.Greet("John"), timeout)
        Assert.assertEquals("Hello, John", Await.result(result, duration))
    }
}