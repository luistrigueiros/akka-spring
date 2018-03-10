package com.akkaspring;

import akka.actor.AbstractActor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class GreetingActor extends AbstractActor {

    private GreetingService greetingService;

    public GreetingActor(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public void onGreet(Greet message) {
        String name = message.getName();
        String greet = greetingService.greet(name);
        getSender().tell(greet, getSelf());
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(Greet.class, this::onGreet)
                .build();
    }

    public static class Greet {

        private String name;

        public Greet(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

    }

}