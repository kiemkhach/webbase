package com.ifi.lab.LabDAO.dao.Idex;

import java.util.List;

import com.ifi.lab.LabDAO.model.Idex.IdexBoiler;
import com.ifi.lab.LabDAO.model.Idex.IdexCounter;
import com.ifi.lab.LabDAO.model.Idex.IdexInstallation;
import com.ifi.lab.LabDAO.model.Idex.IdexSite;

public interface IdexValueDAO {
List<IdexInstallation> getIdexInstallation(Integer keyId,String text);
List<IdexInstallation> getIdexInstallation(Integer keyId1,String text1, Integer keyId2, String text2);
List<IdexCounter> getIdexCounter(Integer keyId,String text);
List<IdexCounter> getIdexCounter(Integer keyId1,String text1, Integer keyId2, String text2);
List<IdexBoiler> getIdexBoiler(Integer keyId,String text);
List<IdexBoiler> getIdexBoiler(Integer keyId1,String text1, Integer keyId2, String text2);
List<IdexSite> getIdexSite(Integer keyId,String text);
List<IdexSite> getIdexSite(Integer keyId1,String text1, Integer keyId2, String text2);
List<Object[]> getIdexObject(String text);
List<Object[]> findIdexInstallation(String text);
String findCode (Integer idexId);
boolean delete(Integer installationId);
}
