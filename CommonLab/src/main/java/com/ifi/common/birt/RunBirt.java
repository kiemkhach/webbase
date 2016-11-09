package com.ifi.common.birt;

import java.io.ByteArrayOutputStream;
import java.util.Locale;
import java.util.Map;

import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.HTMLRenderOption;
import org.eclipse.birt.report.engine.api.HTMLServerImageHandler;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RunBirt {
	private IReportEngine birtReportEngine = null;
	private static Logger logger = LoggerFactory.getLogger(RunBirt.class);

	@SuppressWarnings("unchecked")
	public byte[] runReport(String path, Map<String, Object> mapParameter){
		this.birtReportEngine = BirtEngine.getBirtEngine();
		IReportRunnable design;
		try {
			// Open report design
			design = birtReportEngine.openReportDesign(path);
			// create task to run and render report
			IRunAndRenderTask task = birtReportEngine.createRunAndRenderTask(design);
			task.setLocale(Locale.FRANCE);
			task.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, RunBirt.class.getClassLoader());
			// set output options
			for (Map.Entry<String, Object> entry : mapParameter.entrySet()) {
				task.setParameterValue(entry.getKey(), entry.getValue());
			}

			HTMLRenderOption options = new HTMLRenderOption();
			options.setOutputFormat(HTMLRenderOption.OUTPUT_FORMAT_PDF);
			ByteArrayOutputStream oStream = new ByteArrayOutputStream();
			options.setOutputStream(oStream);
			options.setImageHandler(new HTMLServerImageHandler());
			task.setRenderOption(options);
			// run report
			task.run();
			task.close();
			return oStream.toByteArray();
		} catch (Exception e) {
			logger.error("Error Gen report");
			e.printStackTrace();
			return null;
		}
	}
}
