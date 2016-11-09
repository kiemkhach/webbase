package com.energisme.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.energisme.service.CarreFourService;
import com.ifi.common.bean.CarreFourBean;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.LabUtils;
import com.ifi.common.util.PropertiesReader;

/**
 * CarreFour Action manager
 * 
 * @author ndlong
 * 
 */
public class CarreFourAction extends AbstractBaseAction {
	private static final long serialVersionUID = 1L;

	public static String RESULT_LOGO = "logo";
	public static String RESULT_ICON = "uri";

	@Autowired
	private CarreFourService carreFourService;

	private CarreFourBean carreFourBean;

	private String fileName;

	private Integer siteId;

	//
	private InputStream fileInputStream;

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	public String loadLogo() {
		return RESULT_LOGO;
	}

	public String loadIconUri() {
		return RESULT_ICON;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	private byte[] imageLogoByte = null;

	private byte[] imageIconByte = null;

	public byte[] getImageLogoInBytes() {

		@SuppressWarnings({ "unused", "resource" })
		ApplicationContext context = new ClassPathXmlApplicationContext("constantConfig.xml");

		if (siteId == null) {
			siteId = (Integer) session.get(FrontalKey.SITE_ID);
		}

		imageLogoByte = getImage(carreFourService.getLogo(siteId));

		return imageLogoByte;
	}

	public byte[] getImageIconInBytes() {

		@SuppressWarnings({ "unused", "resource" })
		ApplicationContext context = new ClassPathXmlApplicationContext("constantConfig.xml");

		if (siteId == null) {
			siteId = (Integer) session.get(FrontalKey.SITE_ID);
		}

		imageIconByte = getImage(carreFourService.getUriIcon(siteId));

		return imageIconByte;
	}

	private byte[] getImage(String path) {
		// // for logo
		// if (siteId == null) {
		// siteId = (Integer) session.get(FrontalKey.SITE_ID);
		// }
		if (path == null || path.isEmpty()) {
			path = PropertiesReader.getValue(ConfigEnum.LOGO_PATH);
		}

		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>path logo: " + path);
		String ext = FilenameUtils.getExtension(path);
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>Logo extension: " + ext.toUpperCase());
		switch (ext.toUpperCase()) {
		case "PNG":
			ext = "png";
			break;
		case "JPG":
			ext = "jpg";
			break;
		default:
			break;
		}
		File fileLogo = new File(path);
		BufferedImage originalImage;
		byte[] imageByte = null;
		try {
			originalImage = ImageIO.read(fileLogo);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(originalImage, ext, baos);
			baos.flush();
			imageByte = baos.toByteArray();
			baos.close();
		} catch (IOException e) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>Can't read file for logo action");
		}
		System.out.println(">>>>>>>>>>>>>>>>>>logo success");
		return imageByte;
	}

	/**
	 * display carrefour home page
	 */
	public String execute() {
		session.put(FrontalKey.SITE_ID, siteId);
		carreFourBean = carreFourService.getCarreFourBySite(siteId);
		linkReport = carreFourService.getReportLink(siteId);
		File newfile = new File(linkReport);
		if (newfile.exists() && !newfile.isDirectory()) {
			isReport = true;
		} else {
			isReport = false;
		}
		return SUCCESS;
	}

	public String downloadBackLog() {
		try {
			fileName = getText("file.report.name");
			fileName += LabUtils.getCurrentDate();
			fileName += ".pdf";
			fileInputStream = new FileInputStream(new File(linkReport));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public CarreFourBean getCarreFourBean() {
		return carreFourBean;
	}

	public void setCarreFourBean(CarreFourBean carreFourBean) {
		this.carreFourBean = carreFourBean;
	}

	public CarreFourService getCarreFourService() {
		return carreFourService;
	}

	public void setCarreFourService(CarreFourService carreFourService) {
		this.carreFourService = carreFourService;
	}

	private boolean isReport;
	private String linkReport;

	public boolean getIsReport() {
		return isReport;
	}

	public void setIsReport(boolean isReport) {
		this.isReport = isReport;
	}

	public String getLinkReport() {
		return linkReport;
	}

	public void setLinkReport(String linkReport) {
		this.linkReport = linkReport;
	}

}
