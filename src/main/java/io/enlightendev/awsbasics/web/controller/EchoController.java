package io.enlightendev.awsbasics.web.controller;

import io.enlightendev.awsbasics.domain.Greeting;
import io.enlightendev.awsbasics.web.controller.util.WebDebug;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Juan on 5/9/17.
 */
@RestController
@RequestMapping("api/echo")
public class EchoController {

    @Autowired
    private Environment env;

    private static final String template = "Hello, %s!";

    private final AtomicLong counter = new AtomicLong();

    private static final Logger LOG = LoggerFactory.getLogger(EchoController.class);


    /**
     * Debug get requests
     * @param request
     * @return
     * @throws IOException
     */
    @GetMapping("/get")
    public String getGet(HttpServletRequest request) throws IOException {

        LOG.info("Making get request.");
        return WebDebug.print(request);

    }

    /**
     * Debug post requests
     * @param request
     * @return
     * @throws IOException
     */
    @PostMapping("/post")
    public String getPost(HttpServletRequest request) throws IOException {

        LOG.info("Making post request.");

        String requestDetails = WebDebug.print(request);

        String body = WebDebug.printBody(request);

        return "" + requestDetails + " \n" + body;

    }

    /**
     *
     * @param userAgent
     * @param clientId
     * @return
     */
    @RequestMapping("/requestHeaders")
    public @ResponseBody ResponseEntity<Greeting> requestHeaders (
            @RequestHeader(value="user-agent", defaultValue="foo") String userAgent,
            @RequestHeader(value="client-id", defaultValue="1234") String clientId ){

        LOG.info("requestHeaders( userAgent: " + userAgent + " clientId: " + clientId +")" );

        Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, "world"));

        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);

    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "responseHeaders", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<Greeting> responseHeaders(){

        Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, "world"));

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("RequestID", "aaa-123");
        responseHeaders.set("ResponseID", "res-8765");

        return new ResponseEntity<Greeting>(greeting, responseHeaders, HttpStatus.OK);

    }

    /**
     *
     * @param jsonString
     * @return
     */
    @RequestMapping(value = "jsonBody", method = RequestMethod.POST)
    public @ResponseBody ResponseEntity<Greeting> jsonBody(@RequestBody String jsonString){

        JSONObject jsonObject = new JSONObject(jsonString);

        JSONObject user = (JSONObject)jsonObject.get("user");
        String password = (String)user.get("password");

        Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, user));

        return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);

    }

}