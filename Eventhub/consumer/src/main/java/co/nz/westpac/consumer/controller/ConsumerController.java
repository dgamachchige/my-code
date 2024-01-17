package co.nz.westpac.consumer.controller;

import co.nz.westpac.consumer.Receiver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class ConsumerController {

    private Receiver receiver;

    public ConsumerController(Receiver receiver) {
        this.receiver = receiver;
    }

    @GetMapping("/consume")
    public String consume() throws IOException {
        receiver.consume();
        return "done";
    }
}
