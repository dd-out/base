package com.dd.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dd.service.ICommonService;

@Controller
@RequestMapping("/common")
public class CommonAction {

	@Resource
	private ICommonService service;

}