package com.dd.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;

public class WebUtil {
	public static final String SESSION_USER = "SESSION_USER";
	private static Log logger = LogFactory.getLog(WebUtil.class);

	public static final String MSG_ERROR = "ERROR";
	public static final String MSG_SUCCESS = "SUCCESS";

	public static final String processRequest(HttpServletRequest request)
			throws IOException {
		// 从请求中读取整个post数据
		InputStream inputStream = request.getInputStream();
		String postData = IOUtils.toString(inputStream, "UTF-8");
		return postData;
	}

	public static final void print(HttpServletResponse response, String msg)
			throws IOException {
		response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		out.print(msg);
		out.flush();
	}

	public static final void printError(HttpServletResponse response)
			throws IOException {
		print(response, MSG_ERROR);
	}

	public static final void printSuccess(HttpServletResponse response)
			throws IOException {
		print(response, MSG_SUCCESS);
	}

	public static final String saveFile(HttpServletRequest request,
			MultipartFile file) {
		logger.debug("file upload begin....");
		String path = request.getSession().getServletContext()
				.getRealPath("upload");
		String fileName = file.getOriginalFilename();

		String fileNameEnd = "";
		if (fileName.indexOf(".") != -1) {
			fileNameEnd = fileName.substring(fileName.indexOf("."));
		}
		fileName = new Date().getTime() + "_"
				+ (Math.round(Math.random() * 1000) + fileNameEnd);
		logger.debug("file Save Path = " + path + "/" + fileName);
		File targetFile = new File(path, fileName);
		if (!targetFile.exists()) {
			targetFile.mkdirs();
			logger.debug("mkdir upload Path = " + targetFile.getAbsolutePath());
		}

		// 保存
		try {
			file.transferTo(targetFile);
			logger.debug("file upload end....");
		} catch (Exception e) {
			logger.error(e);
			throw new RuntimeException(e);
		}
		return fileName;
	}

	/**
	 * 下载
	 * 
	 * @param request
	 * @param response
	 * @param storeName
	 * @param contentType
	 * @param realName
	 * @throws Exception
	 */
	public static void download(HttpServletRequest request,
			HttpServletResponse response, String storeName, String realName)
			throws Exception {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		String ctxPath = request.getSession().getServletContext()
				.getRealPath("upload");
		String downLoadPath = ctxPath + storeName;

		long fileLength = new File(downLoadPath).length();

		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment; filename="
				+ new String(realName.getBytes("utf-8"), "ISO8859-1"));
		response.setHeader("Content-Length", String.valueOf(fileLength));

		bis = new BufferedInputStream(new FileInputStream(downLoadPath));
		bos = new BufferedOutputStream(response.getOutputStream());
		byte[] buff = new byte[2048];
		int bytesRead;
		while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
			bos.write(buff, 0, bytesRead);
		}
		bis.close();
		bos.close();
	}
}
