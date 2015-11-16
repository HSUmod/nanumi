package com.nanumi.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nanumi.dto.GoodsDTO;
import com.nanumi.service.GoodsService;

@Controller
public class GoodsController {
	@Autowired
	private GoodsService service;

	@RequestMapping(value = "/WritingGoods", method = RequestMethod.POST)
	public void writingGoods(@RequestParam("userid") String userid, @RequestParam("contents") String contents, @RequestParam("city") String city, @RequestParam("district") String district,
			@RequestParam("major") String major, @RequestParam("sub") String sub, @RequestParam("selectionWay") int selectionWay, HttpServletResponse res) throws IOException {
		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();

		service.writingGoods(new GoodsDTO(userid, contents, city, district, major, sub, selectionWay));
		pw.write("{\"result\": \"WRITING_COMPLETE\"}");
		pw.close();
	}
}
