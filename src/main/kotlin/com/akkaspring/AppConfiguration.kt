package com.akkaspring

import akka.actor.ActorSystem
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan
class AppConfiguration {
    @Autowired
    private lateinit var applicationContext: ApplicationContext

    @Bean
    fun actorSystem(): ActorSystem {
        val system = ActorSystem.create("akka-spring-demo")
        SpringExtension.get(system).initialize(applicationContext)
        return system
    }
}