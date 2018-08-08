package io.enlightendev.awsbasics.service;

import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.ObjectListing;

/**
 *
 */
public interface IS3Service {

    Bucket createBucket(String bucketName);

    ObjectListing listBucketObjects(String bucket);

}