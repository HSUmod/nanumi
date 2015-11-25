package com.nanumi.controller;

import java.io.File;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
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

	@RequestMapping(value = "/WritingGoods.do", method = RequestMethod.POST)
	public void writingGoods(@RequestParam("userid") String userid, @RequestParam("contents") String contents, @RequestParam("city") String city, @RequestParam("district") String district,
			@RequestParam("major") String major, @RequestParam("sub") String sub, @RequestParam("selectionWay") String selectionWay, @RequestParam("hashtag") String hashtag,
			HttpServletRequest request, HttpServletResponse res) throws Exception {
		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();

		service.writingGoods(new GoodsDTO(userid, contents, city, district, major, sub, selectionWay, hashtag), request);
		pw.write("{\"result\": \"WRITING_COMPLETE\"}");
		pw.close();
	}

	@RequestMapping(value = "/ReadGoods.do", method = RequestMethod.POST)
	public void readGoods(HttpServletResponse res) throws Exception {
		List<GoodsDTO> goodsList = service.readGoods();
		StringBuilder json = new StringBuilder();

		json.append("{\"result\": \"READ_COMPLETE\", ");
		json.append("\"goods\": [");
		for (GoodsDTO item : goodsList) {
			json.append("{");
			json.append("\"articleNum\": \"" + item.getArticleNum() + "\",");
			json.append("\"userid\": \"" + item.getUserid() + "\",");
			json.append("\"city\": \"" + item.getCity() + "\",");
			json.append("\"district\": \"" + item.getDistrict() + "\",");
			json.append("\"major\": \"" + item.getMajor() + "\",");
			json.append("\"sub\": \"" + item.getSub() + "\",");
			json.append("\"contents\": \"" + item.getContents() + "\",");
			json.append("\"hashtag\": \"" + item.getHashtag() + "\",");
			json.append("\"selectionWay\": \"" + item.getSelectionWay() + "\",");
			json.append("\"postingTime\": \"" + item.getPostingTime() + "\"");
			json.append("},");
		}
		json.delete(json.length() - 1, json.length()); // last comma delete
		json.append("]}");

		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();
		pw.write(json.toString());
		pw.close();
	}

	@RequestMapping(value = "/getGoodsImg.do", method = RequestMethod.POST)
	public void getGoodsImg(@RequestParam("articleNum") String articleNum, @RequestParam("userid") String userid, HttpServletResponse res) throws Exception {
		Map<String, Object> map = service.selectFileInfo(articleNum);
		String storedFileName = (String) map.get("stored_file_name");
		String originalFileName = (String) map.get("original_file_name");

		byte fileByte[] = FileUtils.readFileToByteArray(new File("C:\\dev\\file\\" + userid + "\\" + storedFileName));

		res.setContentType("application/octet-stream");
		res.setContentLength(fileByte.length);
		res.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(originalFileName, "UTF-8") + "\";");
		res.setHeader("Content-Transfer-Encoding", "binary");
		res.getOutputStream().write(fileByte);

		res.getOutputStream().flush();
		res.getOutputStream().close();
	}

	@RequestMapping(value = "/ReadTest.do", method = RequestMethod.POST)
	public void readTest(HttpServletResponse res) throws Exception {
		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();
		pw.write("{test text1}");
		pw.write("{test text2}");
		pw.write("{test text3}");
		pw.write("{test text4}");
		pw.write("{test text5}");
		pw.write("{test text6}");
		pw.write("{test text7}");
		pw.write("{test text8}");
		pw.write("{test text9}");
		pw.write("{test text10}");
		pw.close();
	}

}
