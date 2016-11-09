package com.energisme.action;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.energisme.service.BouyguesService;
import com.ifi.common.bean.BouyguesBean;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.LabUtils;
import com.ifi.common.util.PropertiesReader;

public class BouyguesAction extends AbstractBaseAction {

	public static String RESULT_LOGO = "logo";
	public static String RESULT_ICON = "uri";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Autowired
	private BouyguesService bouyguesService;

	private BouyguesBean bouyguesBean;

	private Integer siteId;

	private InputStream fileInputStream;

	private String fileName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getFileInputStream() {
		return fileInputStream;
	}

	public BouyguesBean getBouyguesBean() {
		return bouyguesBean;
	}

	public void setBouyguesBean(BouyguesBean bouyguesBean) {
		this.bouyguesBean = bouyguesBean;
	}

	public BouyguesService getBouyguesService() {
		return bouyguesService;
	}

	public void setBouyguesService(BouyguesService bouyguesService) {
		this.bouyguesService = bouyguesService;
	}

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	@Override
	public String execute() throws Exception {
		String url = request.getRequestURL().toString();
		String uri = request.getRequestURI();
		String path = url.substring(0, url.indexOf(uri));
		// siteId = (Integer) session.get(FrontalKey.SITE_ID);
		session.put(FrontalKey.SITE_ID, siteId);
		bouyguesBean = bouyguesService.getBouyguesDataBySite(path, siteId);
		linkReport = bouyguesService.getReportLink(siteId);
		File newfile = new File(linkReport);
		if (newfile.exists() && !newfile.isDirectory()) {
			isReport = true;
		} else {
			isReport = false;
		}
		return SUCCESS;
	}

	public String loadLogo() {
		return RESULT_LOGO;
	}

	public String loadIconUri() {
		return RESULT_ICON;
	}

	// private byte[] imageInByte = null;

	public byte[] getImageLogoInBytes() {
		@SuppressWarnings({ "resource", "unused" })
		ApplicationContext context = new ClassPathXmlApplicationContext("constantConfig.xml");
		if (siteId == null) {
			siteId = (Integer) session.get(FrontalKey.SITE_ID);
		}
		return getBytes(bouyguesService.getLogo(siteId));
	}

	public byte[] getImageIconInBytes() {
		@SuppressWarnings({ "resource", "unused" })
		ApplicationContext context = new ClassPathXmlApplicationContext("constantConfig.xml");
		if (siteId == null) {
			siteId = (Integer) session.get(FrontalKey.SITE_ID);
		}
		return getBytes(bouyguesService.getIcon(siteId));
	}

	public byte[] getBytes(String path) {
		if (path == null || path.isEmpty()) {
			path = PropertiesReader.getValue(ConfigEnum.LOGO_BOUYGUES_PATH);
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
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] bytes = null;
		try {
			originalImage = ImageIO.read(fileLogo);
			ImageIO.write(originalImage, ext, baos);
			baos.flush();
			bytes = baos.toByteArray();
		} catch (IOException e) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>Can't read file for logo action");
		} finally {
			try {
				baos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		System.out.println(">>>>>>>>>>>>>>>>>>logo success");
		return bytes;
	}

	public String exportReport() {		
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
