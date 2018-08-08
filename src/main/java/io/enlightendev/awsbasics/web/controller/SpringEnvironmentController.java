package io.enlightendev.awsbasics.web.controller;

import com.amazonaws.util.EC2MetadataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * Created by Juan on 5/9/17.
 */
@RestController
@RequestMapping("api/spring")
public class SpringEnvironmentController {

    @Autowired
    private Environment env;

    private static final Logger LOG = LoggerFactory.getLogger(SpringEnvironmentController.class);

    /**
     *
     * @param var
     * @return
     * @throws IOException
     */
    @GetMapping("/config/{var:.+}")
    public String getConfig(@PathVariable("var") String var) throws IOException {

        LOG.info("Making config request.");

        return env.getProperty(var);

    }

}