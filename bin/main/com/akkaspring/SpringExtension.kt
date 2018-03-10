package com.akkaspring

import akka.actor.AbstractExtensionId
import akka.actor.ExtendedActorSystem
import akka.actor.Extension
import akka.actor.Props
import org.springframework.context.ApplicationContext

class SpringExt : Extension {

    @Volatile
    private var applicationContext: ApplicationContext? = null

    fun initialize(applicationContext: ApplicationContext) {
        this.applicationContext = applicationContext
    }

    fun props(actorBeanName: String): Props {
        return Props.create(SpringActorProducer::class.java, applicationContext, actorBeanName)
    }

}


object SpringExtension : AbstractExtensionId<SpringExt>() {
    override fun createExtension(system: ExtendedActorSystem): SpringExt = SpringExt()
}