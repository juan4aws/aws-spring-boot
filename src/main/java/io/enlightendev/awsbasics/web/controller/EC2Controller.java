package io.enlightendev.awsbasics.web.controller;

import com.amazonaws.util.EC2MetadataUtils;
import io.enlightendev.awsbasics.domain.Greeting;
import io.enlightendev.awsbasics.web.controller.util.WebDebug;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
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
@RequestMapping("api/ec2")
public class EC2Controller {

    @Autowired
    private ConfigurableApplicationContext ctx;

    private static final Logger LOG = LoggerFactory.getLogger(EC2Controller.class);


    /**
     * Kill this process to test auto scaling
     */
    @RequestMapping(value = "/kill", method = RequestMethod.GET)
    public void kill(){
        ctx.close();
    }

    @RequestMapping(value = "/instanceid", method = RequestMethod.GET)
    public String instanceid(){

        String instanceId = EC2MetadataUtils.getInstanceId();

        if(instanceId == null){
            instanceId = "dev-instance-na";
        }

        return "Instance ID: " + instanceId;
    }

}