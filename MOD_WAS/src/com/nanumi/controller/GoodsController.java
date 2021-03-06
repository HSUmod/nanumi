package com.nanumi.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nanumi.dto.ApplicationDTO;
import com.nanumi.dto.FileDTO;
import com.nanumi.dto.GoodsDTO;
import com.nanumi.service.GoodsService;

@Controller
public class GoodsController {
	Logger log = Logger.getLogger(this.getClass());
	@Autowired
	private GoodsService service;

	@RequestMapping(value = "/WritingGoods.do", method = RequestMethod.POST)
	public void writingGoods(@RequestParam("userid") String userid, @RequestParam("city") String city, @RequestParam("district") String district, @RequestParam("major") String major,
			@RequestParam("sub") String sub, @RequestParam("contents") String contents, @RequestParam("hashtag") String hashtag, @RequestParam("selectionWay") String selectionWay,
			HttpServletRequest request, HttpServletResponse res) throws Exception {
		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();

		service.writingGoods(new GoodsDTO(userid, city, district, major, sub, contents, hashtag, selectionWay), request);
		pw.write("{\"result\": \"WRITING_COMPLETE\"}");
		pw.close();
	}

	@RequestMapping(value = "/ReadGoods.do", method = RequestMethod.POST)
	public void readGoods(HttpServletResponse res) throws Exception {
		List<GoodsDTO> goodsList = service.readGoods();
		StringBuilder json = new StringBuilder();

		json.append("{\"result\": \"ok\", ");
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
			json.append("\"state\": \"" + item.getState() + "\",");
			json.append("\"postingTime\": \"" + item.getPostingTime() + "\",");
			json.append("\"image\": \"" + getImgData(item.getArticleNum(), item.getUserid()) + "\",");
			json.append("\"ruserid\": \"" + item.getRuserid() + "\"");
			json.append("},");
		}
		json.delete(json.length() - 1, json.length()); // last comma delete
		json.append("]}");

		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();
		pw.write(json.toString());
		pw.close();
	}

	@RequestMapping(value = "/SearchGoods.do", method = RequestMethod.POST)
	public void searchGoods(@RequestParam("type") String type, @RequestParam("city") String city, @RequestParam("district") String district, HttpServletResponse res) throws Exception {
		List<GoodsDTO> goodsList = null;
		StringBuilder json = new StringBuilder();

		if (type.equals("city")) {
			goodsList = service.searchGoodsByCity(city);
		} else if (type.equals("district")) {
			goodsList = service.searchGoodsByDistrict(district);
		} else {
			goodsList = service.searchGoodsByAddress(city, district);
		}

		if (goodsList.size() > 0) {
			json.append("{\"result\": \"ok\", ");
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
				json.append("\"state\": \"" + item.getState() + "\",");
				json.append("\"postingTime\": \"" + item.getPostingTime() + "\",");
				json.append("\"image\": \"" + getImgData(item.getArticleNum(), item.getUserid()) + "\",");
				json.append("\"ruserid\": \"" + item.getRuserid() + "\"");
				json.append("},");
			}
			json.delete(json.length() - 1, json.length()); // last comma delete
			json.append("]}");
		} else {
			json.append("{\"result\": \"fail\"}");
		}

		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();
		pw.write(json.toString());
		pw.close();
	}

	private String getImgData(String articleNum, String userid) throws IOException {
		FileDTO file = service.selectFileInfo(articleNum);
		BufferedImage bufferedImage = ImageIO.read(new File("C:\\dev\\file\\" + userid + "\\" + file.getStored_file_name()));
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		ImageIO.write(bufferedImage, "jpg", baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();

		return Base64.encodeBase64String(imageInByte);
	}

	@RequestMapping(value = "/ApplicationList.do", method = RequestMethod.POST)
	public void getApplicationList(HttpServletResponse res) throws Exception {
		List<ApplicationDTO> applicationList = null;
		StringBuilder json = new StringBuilder();
		applicationList = service.getApplicationList();

		if (applicationList.size() > 0) {
			json.append("{\"result\": \"ok\", ");
			json.append("\"value\": [");
			for (ApplicationDTO item : applicationList) {
				json.append("{");
				json.append("\"articleNum\": \"" + item.getArticleNum() + "\",");
				json.append("\"userid\": \"" + item.getUserid() + "\",");
				json.append("\"state\": \"" + item.getState() + "\",");
				json.append("\"postingTime\": \"" + item.getPostingTime() + "\"");
				json.append("},");
			}
			json.delete(json.length() - 1, json.length()); // last comma delete
			json.append("]}");
		} else {
			json.append("{\"result\": \"fail\"}");
		}

		log.info("===========================");
		log.info(json);
		log.info("===========================");

		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();
		pw.write(json.toString());
		pw.close();
	}

	@RequestMapping(value = "/MyGoodsApplicationList.do", method = RequestMethod.POST)
	public void getApplicationListByArticleNum(@RequestParam("articleNum") String articleNum, HttpServletResponse res) throws Exception {
		List<ApplicationDTO> applicationList = null;
		StringBuilder json = new StringBuilder();

		applicationList = service.getMyGoodsApplicationList(articleNum);
		if (applicationList.size() > 0) {
			json.append("{\"result\": \"ok\", ");
			json.append("\"value\": [");
			for (ApplicationDTO item : applicationList) {
				json.append("{");
				json.append("\"articleNum\": \"" + item.getArticleNum() + "\",");
				json.append("\"userid\": \"" + item.getUserid() + "\",");
				json.append("\"state\": \"" + item.getState() + "\",");
				json.append("\"postingTime\": \"" + item.getPostingTime() + "\"");
				json.append("},");
			}
			json.delete(json.length() - 1, json.length()); // last comma delete
			json.append("]}");
		} else {
			json.append("{\"result\": \"fail\"}");
		}

		log.info("===========================");
		log.info(json);
		log.info("===========================");

		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();
		pw.write(json.toString());
		pw.close();
	}

	@RequestMapping(value = "/Apply.do", method = RequestMethod.POST)
	public void apply(@RequestParam("articleNum") String articleNum, @RequestParam("userid") String userid, @RequestParam("currentState") String state, HttpServletResponse res) throws Exception {
		String json = "{\"result\": \"ok\"}";

		if (state.equals("apply")) {
			service.apply(articleNum, userid);
		} else if (state.equals("cancle")) {
			service.applyCancle(articleNum, userid);
		} else {
			json = "{\"result\": \"fail\"}";
		}

		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();
		pw.write(json);
		pw.close();
	}

	@RequestMapping(value = "/Choice.do", method = RequestMethod.POST)
	public void choice(@RequestParam("articleNum") String articleNum, @RequestParam("userid") String userid, HttpServletResponse res) throws Exception {
		String json = "{\"result\": \"ok\"}";

		service.choice(articleNum, userid);

		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();
		pw.write(json);
		pw.close();
	}

	@RequestMapping(value = "/CancleChoice.do", method = RequestMethod.POST)
	public void choiceCancle(@RequestParam("articleNum") String articleNum, HttpServletResponse res) throws Exception {
		String json = "{\"result\": \"ok\"}";

		service.choiceCancle(articleNum);

		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();
		pw.write(json);
		pw.close();
	}
	
	@RequestMapping(value = "/DonateComplete.do", method = RequestMethod.POST)
	public void donateComplete(@RequestParam("articleNum") String articleNum, HttpServletResponse res) throws Exception {
		String json = "{\"result\": \"ok\"}";

		service.donateComplete(articleNum);

		res.setContentType("application/json; charset=utf-8");
		PrintWriter pw = res.getWriter();
		pw.write(json);
		pw.close();
	}

}
