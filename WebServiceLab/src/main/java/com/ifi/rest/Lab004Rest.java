package com.ifi.rest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ifi.common.bean.Lab004Bean;
import com.ifi.common.bean.Lab004LineBean;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.PropertiesReader;
import com.ifi.lab.LabDAO.dao.ConfigLab004Dao;
import com.ifi.lab.LabDAO.dao.ConfigLab004DaoLine;
import com.ifi.lab.LabDAO.dao.ConstantDAO;
import com.ifi.lab.LabDAO.dao.LabDAO;
import com.ifi.lab.LabDAO.model.ConfigLab004;
import com.ifi.lab.LabDAO.model.ConfigLab004Line;
import com.ifi.lab.LabDAO.model.Lab;

@Controller
@RequestMapping("lab004")
public class Lab004Rest {

	@Autowired
	private ConfigLab004Dao lab004DAO;

	@Autowired
	private ConfigLab004DaoLine lab004LineDAO;

	@Autowired
	private ConstantDAO constantDAO;

	@Autowired
	private LabDAO labDAO;

	@RequestMapping(value = "getData", method = RequestMethod.GET)
	public @ResponseBody String getAllConfig(Integer siteId, Integer type) {
		ConfigLab004 config = lab004DAO.getConfigBySite(siteId);
		if (config == null) {
			return null;
		}
		ConfigLab004Line configLine = lab004LineDAO.getConfigLineByType(type, config.getId());
		Lab004Bean bean = new Lab004Bean();

		bean.setAddress(configLine.getAddress());
		bean.setLogo(configLine.getLogoType());
		bean.setSiteId(siteId);
		bean.setSiteName(config.getSiteName());
		Calendar cal = Calendar.getInstance();

		Lab lab = labDAO.findByName(PropertiesReader.getValue(ConfigEnum.LAB_NAME_004));
		Integer first = Integer
				.parseInt(constantDAO.getByLabIdnKey(lab.getId(), FrontalKey.LAB004_CLOCK_FIRST).getValue());
		Integer second = Integer
				.parseInt(constantDAO.getByLabIdnKey(lab.getId(), FrontalKey.LAB004_CLOCK_SECOND).getValue());

		if (!"YES".equals(PropertiesReader.getValue(ConfigEnum.IS_CURRENT_TIME))) {
			cal.set(Calendar.YEAR, PropertiesReader.getValueInt(ConfigEnum.CURRENT_TIME_YEAR));
			cal.set(Calendar.MONTH, PropertiesReader.getValueInt(ConfigEnum.CURRENT_TIME_MONTH) - 1);
			cal.set(Calendar.DAY_OF_MONTH, PropertiesReader.getValueInt(ConfigEnum.CURRENT_TIME_DAY));
			cal.set(Calendar.HOUR_OF_DAY, PropertiesReader.getValueInt(ConfigEnum.CURRENT_TIME_HOUR));
		}

		SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yy");
		bean.setDate(sdf1.format(cal.getTime()));
		int toHour = cal.get(Calendar.HOUR_OF_DAY);
		int fromHour = toHour - 1;

		bean.setFromHour(String.format("%02d", fromHour) + "H00");
		bean.setToHour(String.format("%02d", toHour) + "H00");

		List<Lab004LineBean> lineBeanLst = new ArrayList<Lab004LineBean>();

		// List<ConfigLab004Line> lineLst = config.getLineLst();
		Lab004LineBean lineTotal = new Lab004LineBean();
		int total = 5;
		for (int i = 1; i <= total; i++) {
			// Integer currentConsumption = getConsumptionByTime(
			// lab.getModuleId(), cal, 0);
			// Integer previousConsumption = getConsumptionByTime(
			// lab.getModuleId(), cal, 1);
			// Integer currentEcart = null;
			// Integer previousEcart = null;
			Lab004LineBean lineBean = new Lab004LineBean();
			lineBean.setId(i);
			lineBean.setCible(config.getCible());
			int minMass = 300 / (2 * lineBean.getCible()) + 1;
			lineBean.setMass(getRandom(minMass, 5));
			int maxConsommation = 2 * lineBean.getCible() * lineBean.getMass();
			if (maxConsommation > 600) {
				maxConsommation = 600;
			}
			lineBean.setConsommation(getRandom(300, maxConsommation));
			lineBean.setPreviousConsommation(getRandom(300, maxConsommation));

			lineBean.setMoney((int) (lineBean.getConsommation() * config.getUnitPrice()));
			lineBean.setPreviousMoney((int) (lineBean.getPreviousConsommation() * config.getUnitPrice()));

			lineBean.setProduct((int) (lineBean.getConsommation() / lineBean.getMass()));
			lineBean.setPreviousProduct((int) (lineBean.getPreviousConsommation() / lineBean.getMass()));

			int currentEcart = (int) (((double) (lineBean.getProduct() - lineBean.getCible())) / lineBean.getCible()
					* 100);
			lineBean.setEcart(currentEcart);

			int previousEcart = (int) (((double) (lineBean.getPreviousProduct() - lineBean.getCible()))
					/ lineBean.getCible() * 100);
			lineBean.setPreviousEcart(previousEcart);

			if (currentEcart > previousEcart) {
				lineBean.setIsIncrease(true);
			} else {
				lineBean.setIsIncrease(false);
			}

			int productMoney = lineBean.getMoney() / lineBean.getMass();
			int cibleMoney = (int) (lineBean.getCible() * config.getUnitPrice());
			lineBean.setEcartMoney(productMoney - cibleMoney);
			lineBean.setClock(getClock(lineBean.getEcart(), first, second));

			// add to total line
			lineTotal.setConsommation((lineTotal.getConsommation() == null ? 0 : lineTotal.getConsommation())
					+ lineBean.getConsommation());
			lineTotal.setMoney((lineTotal.getMoney() == null ? 0 : lineTotal.getMoney()) + lineBean.getMoney());
			lineTotal.setProduct((lineTotal.getProduct() == null ? 0 : lineTotal.getProduct()) + lineBean.getProduct());
			lineTotal.setCible((lineTotal.getCible() == null ? 0 : lineTotal.getCible()) + lineBean.getCible());
			lineTotal.setEcart((lineTotal.getEcart() == null ? 0 : lineTotal.getEcart()) + lineBean.getEcart());
			lineTotal.setPreviousEcart((lineTotal.getPreviousEcart() == null ? 0 : lineTotal.getPreviousEcart())
					+ lineBean.getPreviousEcart());
			lineTotal.setEcartMoney(
					(lineTotal.getEcartMoney() == null ? 0 : lineTotal.getEcartMoney()) + lineBean.getEcartMoney());

			lineBeanLst.add(lineBean);
		}
		bean.setLineLst(lineBeanLst);
		lineTotal.setProduct(lineTotal.getProduct() / total);
		lineTotal.setCible(lineTotal.getCible() / total);
		lineTotal.setEcart(lineTotal.getEcart() / total);
		lineTotal.setEcartMoney(lineTotal.getEcartMoney() / total);
		lineTotal.setClock(getClock(lineTotal.getEcart(), first, second));
		if (lineTotal.getEcart() > lineTotal.getPreviousEcart()) {
			lineTotal.setIsIncrease(true);
		} else {
			lineTotal.setIsIncrease(false);
		}
		bean.setLineTotal(lineTotal);
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(bean);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	public double getClock(Integer ecart, Integer first, Integer second) {
		double rs = 0;
		if (ecart == null) {
			return rs;
		}
		if (ecart <= -100) {
			rs = 0;
		} else if (ecart >= 100) {
			rs = 100;
		} else if (ecart <= first) {
			rs = Math.abs((double) (ecart + 100) * 100 / (3 * (100 + first)));
		} else if (ecart > first && ecart < second) {
			rs = Math.abs(((double) ecart - first) * 100 / (3 * (second - first))) + 100d / 3;
		} else if (ecart >= second) {
			rs = Math.abs(((double) ecart - second) * 100 / (3 * (100 - second))) + 200d / 3;
		}
		return rs;
	}

//	public static void main(String[] args) {
//		Lab004Rest re = new Lab004Rest();
//		System.out.println(re.getClock(25, 0, 20));
//	}

	private int getRandom(int from, int to) {
		if (from > to) {
			to = from + 1;
		}
		Random random = new Random();
		return random.nextInt(to - from) + from;
	}

	@RequestMapping(value = "getLogo", method = RequestMethod.GET)
	public @ResponseBody String getLogo(Integer siteId) {
		ConfigLab004 config = lab004DAO.getConfigByType(siteId);
		return config.getLogo();
	}

	@RequestMapping(value = "getURIIcon", method = RequestMethod.GET)
	public @ResponseBody String getURIIcon(Integer siteId) {
		ConfigLab004 config = lab004DAO.getConfigByType(siteId);
		return config.getUriIcon();
	}

	@RequestMapping(value = "getSite", method = RequestMethod.GET)
	public @ResponseBody String getSite(Integer siteId) {
		ConfigLab004 config = lab004DAO.getConfigByType(siteId);
		Lab004Bean bean = new Lab004Bean();
		bean.setSiteInfo(config.getSiteInfo());
		bean.setSiteName(config.getSiteName());
		bean.setSiteId(config.getSiteId());
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(bean);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@RequestMapping(value = "getReportLink", method = RequestMethod.GET)
	public @ResponseBody String getReportLink(@RequestParam String siteId, @RequestParam String type) {
		Integer siteNumber = WSCommon.getNumber(siteId);
		if (siteNumber == null) {
			return "siteId required";
		}
		Integer typeNumber = WSCommon.getNumber(type);
		if (typeNumber == null) {
			return "type required";
		}
		ConfigLab004 configLab = lab004DAO.findBySite(siteNumber);
		if (configLab == null || configLab.getId() == null) {
			return "Site Id not exist";
		}
		ConfigLab004Line configLabLine = lab004LineDAO.getConfigLineByType(typeNumber, configLab.getId());
		if (configLabLine == null || configLabLine.getId() == null) {
			return "Site Id or Type not exist";
		}
		if (configLabLine.getReportName() == null || configLabLine.getReportName().trim().equals("")) {
			return "Site Id, Type - Report File not exist";
		}
		String typeFolder = "";
		if (typeNumber == 1) {
			typeFolder = FrontalKey.TYPE1;
		}
		if (typeNumber == 2) {
			typeFolder = FrontalKey.TYPE2;
		}
		if (typeNumber == 3) {
			typeFolder = FrontalKey.TYPE3;
		}
		if (typeFolder.equals("")) {
			return "failed";
		}
		String reportLink = PropertiesReader.getValue(ConfigEnum.REPORT_LAB004_LINK) + FrontalKey.WINDOWS + siteId
				+ FrontalKey.WINDOWS + typeFolder + FrontalKey.WINDOWS + configLabLine.getReportName();
		return reportLink;
	}
}
