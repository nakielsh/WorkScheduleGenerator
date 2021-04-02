package pw.edu.wsg.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/prob")
public class Controller {

    private static final Logger LOG = Logger.getLogger(Controller.class.getName());

    @GetMapping("/")
    @ResponseStatus(OK)
    public String helloWorld(){
        String message = "Hello from test";
        LOG.info(message);
        return message;
    }
}
