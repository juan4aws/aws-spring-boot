package io.enlightendev.awsbasics.web.controller;

import com.amazonaws.util.EC2MetadataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 */
@RestController
@RequestMapping("api/ecs")
public class ECSController {

    @Autowired
    private ConfigurableApplicationContext ctx;

    private static final Logger LOG = LoggerFactory.getLogger(ECSController.class);


    /**
     * Kill this process to test auto scaling
     */
    @RequestMapping(value = "/kill", method = RequestMethod.GET)
    public void kill(){
        //TBD
    }

    @RequestMapping(value = "/taskid", method = RequestMethod.GET)
    public String instanceid(){


        return "TaskID ID not yet implemented";
    }

}