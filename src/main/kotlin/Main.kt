import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.pattern.Patterns
import akka.util.Timeout
import com.akkaspring.AppConfiguration
import com.akkaspring.Greet
import com.akkaspring.SpringExtension
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import scala.concurrent.Await
import scala.concurrent.duration.FiniteDuration
import java.util.concurrent.TimeUnit


fun main(args: Array<String>) {
    val ctx = AnnotationConfigApplicationContext(AppConfiguration::class.java)
    val system = ctx.getBean(ActorSystem::class.java)
    val springExt = SpringExtension.get(system)

    val greeter = system.actorOf(springExt.props("greetingActor"), "greetingActor")
    greeter.tell(Greet("Oscar"), ActorRef.noSender())
    val duration = FiniteDuration.create(1, TimeUnit.SECONDS)
    val timeout = Timeout.durationToTimeout(duration)
    val result = Patterns.ask(greeter, Greet("John"), timeout)
    println(Await.result(result, duration))

    system.terminate()
}