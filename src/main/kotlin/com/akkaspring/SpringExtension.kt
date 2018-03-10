package com.akkaspring

import akka.actor.AbstractExtensionId
import akka.actor.ExtendedActorSystem
import akka.actor.Extension
import akka.actor.Props
import org.springframework.context.ApplicationContext

object SpringExtension : AbstractExtensionId<SpringExtension.SpringExt>() {
    override fun createExtension(system: ExtendedActorSystem): SpringExt = SpringExt()

    class SpringExt : Extension {

        private lateinit var applicationContext: ApplicationContext

        fun initialize(applicationContext: ApplicationContext) {
            this.applicationContext = applicationContext
        }

        fun props(actorBeanName: String): Props {
            return Props.create(SpringActorProducer::class.java, applicationContext, actorBeanName)
        }

    }

}