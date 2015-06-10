package com.dd.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dd.dao.ICommonDao;
import com.dd.service.ICommonService;

@Service
public class CommonServiceImpl implements ICommonService {

	@Autowired
	private ICommonDao dao;
}
