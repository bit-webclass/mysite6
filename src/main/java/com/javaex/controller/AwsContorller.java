package com.javaex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.javaex.util.S3Util;

@Controller
@RequestMapping("/aws")
public class AwsContorller {

	@Autowired
	private S3Util s3 ;
	
	private String bucketName = "com.javaex.iremys.upload";
	
	
	//초기화(버킷생성)
	@RequestMapping("/init")
	public String init() {
		s3.createBucket(bucketName);
		System.out.println("버킷생성");
		s3.createFolder(bucketName, "imgtest");
		System.out.println("폴더생성");
		System.out.println(s3.getBucketlist());
		return "aws/form";
	}
	
	
	@RequestMapping(value="/form", method=RequestMethod.GET)
	public String form() {
		System.out.println("aws form");
		return "aws/form";
	}

	
	@RequestMapping(value="/upload", method=RequestMethod.POST)
	public String upload(@RequestParam("file") MultipartFile file, Model model) {
		System.out.println("aws upload");
		s3.fileUpload(bucketName, file);
		String fileurl = s3.getFileURL(bucketName, file.getOriginalFilename());
		model.addAttribute("fileurl", fileurl);
		System.out.println(fileurl);
		return "aws/result";
	}
	
	//test
	@RequestMapping(value="/test", method=RequestMethod.GET)
	public String upload() {
		System.out.println("test");
		s3.fileDelete(bucketName, "file");
		return "aws/result";
	}
	

}
