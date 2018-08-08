package io.enlightendev.awsbasics.web.controller;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import io.enlightendev.awsbasics.service.impl.S3ServiceImpl;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/s3")
public class S3Controller {


    private static final Logger LOG = LoggerFactory.getLogger(S3Controller.class);

    @Autowired
    private S3ServiceImpl s3Service;

    @RequestMapping("/list")
    public List<Bucket> listBuckets() {

        LOG.info("listing s3 buckets");
        return s3Service.listBuckets();
    }

    @RequestMapping("/list/{bucket}")
    public List<S3ObjectSummary> listObjects(@PathVariable String bucket) {

        ObjectListing objectListing = s3Service.listBucketObjects(bucket);

        return objectListing.getObjectSummaries();
    }

    @RequestMapping(value = "/create/{bucketName}", method = RequestMethod.POST)
    public String createBucket(@PathVariable String bucketName) {

        Bucket bucket = s3Service.createBucket(bucketName);

        return bucket.toString();
    }

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public String writeSomething() throws IOException {

        s3Service.writeResource("thisismyfile.txt", "content".getBytes());

        return "done";
    }

}