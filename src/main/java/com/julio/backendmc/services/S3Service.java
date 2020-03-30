package com.julio.backendmc.services;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service
public class S3Service {

	private Logger LOG = LoggerFactory.getLogger(S3Service.class);

	@Autowired
	private AmazonS3 s3client;

	@Value("${s3.bucket}")
	private String bucketName;

	public void uploadFile(String localFilePatch) {
		try {
			File file = new File(localFilePatch);
			LOG.info("Upload Iniciado");
			s3client.putObject(new PutObjectRequest(bucketName, "teste.jpg", file));
			LOG.info("Upload Culminado");
		} catch (AmazonServiceException e) {
			LOG.info("Amazon Service Exception: " + e.getErrorMessage());
			LOG.info("Status Code:" + e.getErrorCode());
		} catch (AmazonClientException e) {
			LOG.info("AmazonClientExcepcion: " + e.getMessage());
		}
	}
}
