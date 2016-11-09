package com.energisme.action;

import com.energisme.bean.NumConfigLab006Client;
import com.energisme.service.Lab006ClientService;
import com.energisme.util.ConvertObject;
import com.google.gson.Gson;
import com.ifi.common.util.FrontalKey;
import com.ifi.lab.LabDAO.model.ConfigLab006Client;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * Created by hmtri on 2/15/2016.
 */
public class Lab006ClientAction extends AbstractBaseAction {
    private static final Logger LOGGER = Logger.getLogger(Lab006ClientAction.class);

    private int clientId;
    private File fileReport;
    private File fileImage;
    private String fileReportName;
    private String fileImgName;
    private boolean isCreate;

    @Autowired
    private Lab006ClientService lab006ClientService;

    private NumConfigLab006Client client;

    private String jsonString;

    public String getClientById() {
        ConfigLab006Client client = lab006ClientService.getClientById(clientId);
        ConvertObject convertObject = new ConvertObject();
        NumConfigLab006Client numClient = convertObject.convertIdtoModuleNumber(client);
        Gson gson = new Gson();
        jsonString = gson.toJson(numClient);
        return SUCCESS;
    }

    public String deleteClient() {
        boolean isDeleted = lab006ClientService.deleteClient(clientId);
        if (isDeleted) {
            jsonString = "{\"result\":\"success\"}";
        } else {
            jsonString = "{\"result\":\"failed\"}";
        }
        return SUCCESS;
    }

    public String saveClient() {
//        if (file != null){
//            boolean isUploaded = lab006ClientService.uploadFileReport(clientId+"",file,file.getName());
//            if (isUploaded)
//                client.setReportClientName(file.getName());
//        }
        boolean isSaved = lab006ClientService.saveClient(client);
        if (isSaved) {
            jsonString = "{\"result\":\"success\"}";
        } else {
            jsonString = "{\"result\":\"failed\"}";
        }
        return SUCCESS;
    }

    public String uploadFiles() {
//        LOGGER.error("hmtri0");
        if (clientId == 0){
            NumConfigLab006Client temp_Client = new NumConfigLab006Client();
            if (lab006ClientService.saveClient(temp_Client)){
                clientId = lab006ClientService.getNewestId();
            }
        }
//        LOGGER.error("hmtri1");
        if (fileReport != null) {
            boolean isUploaded = lab006ClientService.uploadFileReport(clientId + "", fileReport, fileReportName, FrontalKey.TYPE_UPLOAD_REPORT);
            if (isUploaded) {
                jsonString = "{\"result\":\"success\"}";
            } else {
                jsonString = "{\"result\":\"failed\"}";
                return SUCCESS;
            }
        }
//        LOGGER.error("hmtri2");
        if (fileImage != null) {
            boolean isUploaded = lab006ClientService.uploadFileReport(clientId + "", fileImage, fileImgName, FrontalKey.TYPE_UPLOAD_IMAGE);
            if (isUploaded) {
                jsonString = "{\"result\":\"success\"}";
            } else {
                jsonString = "{\"result\":\"failed\"}";
            }
        }
        return SUCCESS;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public NumConfigLab006Client getClient() {
        return client;
    }

    public String getJsonString() {
        return jsonString;
    }

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public void setClient(NumConfigLab006Client client) {
        this.client = client;
    }

    public File getFileReport() {
        return fileReport;
    }

    public void setFileReport(File fileReport) {
        this.fileReport = fileReport;
    }

    public File getFileImage() {
        return fileImage;
    }

    public void setFileImage(File fileImage) {
        this.fileImage = fileImage;
    }

    public String getFileReportName() {
        return fileReportName;
    }

    public void setFileReportName(String fileReportName) {
        this.fileReportName = fileReportName;
    }

    public String getFileImgName() {
        return fileImgName;
    }

    public void setFileImgName(String fileImgName) {
        this.fileImgName = fileImgName;
    }

    public boolean isCreate() {
        return isCreate;
    }

    public void setCreate(boolean create) {
        isCreate = create;
    }
}
