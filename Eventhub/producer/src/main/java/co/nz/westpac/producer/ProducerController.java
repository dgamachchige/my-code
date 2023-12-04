package co.nz.westpac.producer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ProducerController {

    private Sender sender;

    public ProducerController(Sender sender) {
        this.sender = sender;
    }

    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        return "hello " + name;
    }

    @PostMapping("/produce")
    public String produce(@RequestBody List<Message> messages) {
        messages.forEach(message -> log.info("Message Received {} and {} ", message.getName(), message.getAge()));

        sender.publishEvents(messages);


        return "pushed "+ messages.size();
    }

}
