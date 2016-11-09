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

import com.energisme.service.ELeclercService;
import com.ifi.common.bean.ELeclercBean;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.LabUtils;
import com.ifi.common.util.PropertiesReader;

/**
 * Action manager
 * 
 * @author ndhung
 * 
 */
public class ELeclercAction extends AbstractBaseAction {
	private static final long serialVersionUID = 1L;

	public static String RESULT_LOGO = "logo";
	public static String RESULT_ICON = "uri";

	@Autowired
	private ELeclercService eLeclercService;

	private ELeclercBean eLeclercBean;

	private Integer siteId;

	private InputStream fileInputStream;

	private String fileName;

	public InputStream getFileInputStream() {
		return fileInputStream;
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

	public Integer getSiteId() {
		return siteId;
	}

	public void setSiteId(Integer siteId) {
		this.siteId = siteId;
	}

	private byte[] imageLogoByte = null;

	private byte[] imageIconByte = null;

	public byte[] getImageLogoInBytes() {

		@SuppressWarnings({ "unused", "resource" })
		ApplicationContext context = new ClassPathXmlApplicationContext("constantConfig.xml");

		if (siteId == null) {
			siteId = (Integer) session.get(FrontalKey.SITE_ID);
		}

		imageLogoByte = getImage(eLeclercService.getLogo(siteId));

		return imageLogoByte;
	}

	public byte[] getImageIconInBytes() {

		@SuppressWarnings({ "unused", "resource" })
		ApplicationContext context = new ClassPathXmlApplicationContext("constantConfig.xml");

		if (siteId == null) {
			siteId = (Integer) session.get(FrontalKey.SITE_ID);
		}

		imageIconByte = getImage(eLeclercService.getUriIcon(siteId));

		return imageIconByte;
	}

	private byte[] getImage(String path) {
		// // for logo
		// if (siteId == null) {
		// siteId = (Integer) session.get(FrontalKey.SITE_ID);
		// }
		if (path == null || path.isEmpty()) {
			path = PropertiesReader.getValue(ConfigEnum.LOGO_ELECLERC_PATH);
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
	 * display home page
	 */
	public String execute() {
		// Integer siteId = (Integer) session.get(FrontalKey.SITE_ID);
		session.put(FrontalKey.SITE_ID, siteId);
		eLeclercBean = eLeclercService.getELeclercBySite(siteId, PropertiesReader.getValue(ConfigEnum.LAB_NAME_003), 1);
		session.put("eLeclercBean", eLeclercBean);
		linkReport = eLeclercService.getReportLink(siteId);
		File newfile = new File(linkReport);
		if (newfile.exists() && !newfile.isDirectory()) {
			isReport = true;
		} else {
			isReport = false;
		}
		// System.out.println(eLeclercBean.getSiteStr());
		// System.out.println(eLeclercBean.getDrive());
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

	public ELeclercService getELeclercService() {
		return eLeclercService;
	}

	public void setELeclercService(ELeclercService eLeclercService) {
		this.eLeclercService = eLeclercService;
	}

	public ELeclercBean getELeclercBean() {
		return eLeclercBean;
	}

	public void setELeclercBean(ELeclercBean eLeclercBean) {
		this.eLeclercBean = eLeclercBean;
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
