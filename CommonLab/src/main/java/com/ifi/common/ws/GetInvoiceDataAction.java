package com.ifi.common.ws;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ifi.common.bean.Lab009Contract;
import com.ifi.common.bean.Lab009Invoice;
import com.ifi.common.bean.Lab009InvoiceYear;
import com.ifi.common.util.ConfigEnum;
import com.ifi.common.util.FrontalKey;
import com.ifi.common.util.LabUtils;
import com.ifi.common.util.PropertiesReader;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;

public class GetInvoiceDataAction {
	private static Client client;
	private static final String GET_INVOICE_DATA = "/facture/getListFacturesExport";
	private static final Logger LOGGER = LoggerFactory.getLogger(GetInvoiceDataAction.class);

	public GetInvoiceDataAction() {
//		System.setProperty("http.proxyHost", "10.225.1.1");
//		System.setProperty("http.proxyPort", "3128");
		if (client == null) {
			client = Client.create(new DefaultClientConfig());
		}
	}

	public List<Lab009Invoice> getInvoiceAll(String clientID, List<Integer> siteLst, String startDate, String endDate,
			Map<String, Lab009Contract> mapContract) {
		if (clientID == null || siteLst == null || startDate == null || endDate == null) {
			LOGGER.error("[Error]clientID:" + clientID + "-siteLst: " + siteLst + "-startDate:" + startDate
					+ "-endDate:" + endDate);
			return null;
		}
		String siteIdUri = siteLst.toString().substring(1, siteLst.toString().length() - 1).replaceAll(" ", "");
		String uriInvoice = PropertiesReader.getValue(ConfigEnum.URI_PATH_OCR_BS) + GET_INVOICE_DATA;
		String input = "{\"clientId\":" + clientID + ",\"enrSiteId\":\"" + siteIdUri
				+ "\",\"checkGaz\":true,\"checkElec\":true,\"checkEau\":true" + ",\"startDate\":\"" + startDate
				+ "\",\"endDate\":\"" + endDate + "\"}";
		List<Lab009Invoice> lst = new ArrayList<Lab009Invoice>();
//		try {
//			WebResource webResource = client.resource(uriInvoice);
//			ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);
//
//			if (response.getStatus() != 200) {
//				return null;
//			}
//			String output = response.getEntity(String.class);
//			JSONParser jsonParser = new JSONParser();
//			JSONArray jsonArray = (JSONArray) jsonParser.parse(output);
//			if (jsonArray.size() != 0) {
//				for (int i = 0; i < jsonArray.size(); i++) {
//					JSONObject jsonObjectInvoice = (JSONObject) jsonArray.get(i);
//					System.out.println(jsonObjectInvoice.get("id"));
//					Integer totalConsommation = 0;
//					Object obj = jsonObjectInvoice.get("totalConsommation");
//					if (obj != null) {
//						try {
//							// TODO: Cho sua import
//							Double tmp = Double.parseDouble(obj.toString());
//							if (tmp != null) {
//								totalConsommation = tmp.intValue();
//							}
//						} catch (NumberFormatException nfe) {
//							LOGGER.error("[totalConsommation]Not a number:" + obj);
//							continue;
//						}
//					}
//					Double montantHt = 0d;
//					obj = jsonObjectInvoice.get("montantHt");
//					if (obj != null) {
//						try {
//							montantHt = Double.parseDouble(obj.toString().replace(",", "."));
//						} catch (NumberFormatException nfe) {
//							LOGGER.error("[montantHt]Not a number:" + obj);
//							continue;
//						}
//					}
//
//					obj = jsonObjectInvoice.get("invariant");
//
//					String invariant = null;
//					if (obj != null) {
//						invariant = obj.toString();
//					} else {
//						LOGGER.error("[invariant]Not found" + obj);
//						continue;
//					}
//
//					Integer siteId = null;
//					obj = jsonObjectInvoice.get("enrSiteId");
//					if (obj != null) {
//						try {
//							siteId = Integer.parseInt(obj.toString());
//						} catch (NumberFormatException nfe) {
//							LOGGER.error("[enrSiteId]Not a number:" + obj.toString());
//							continue;
//						}
//					}
//					if (siteId == null) {
//						LOGGER.error("[enrSiteId]Not found:" + obj);
//						continue;
//					}
//
//					Date startDateConsommation = null;
//					obj = jsonObjectInvoice.get("startDateConsommation");
//					if (obj != null) {
//						startDateConsommation = LabUtils.convertDateByFormat(
//								jsonObjectInvoice.get("startDateConsommation").toString(),
//								FrontalKey.DATE_SLASH_FORMAT);
//					}
//					if (startDateConsommation == null) {
//						LOGGER.error("[startDateConsommation] error" + obj);
//						continue;
//					}
//
//					Date endDateConsommation = null;
//					obj = jsonObjectInvoice.get("endDateConsommation");
//					if (obj != null) {
//						endDateConsommation = LabUtils.convertDateByFormat(
//								jsonObjectInvoice.get("endDateConsommation").toString(), FrontalKey.DATE_SLASH_FORMAT);
//					}
//					if (endDateConsommation == null) {
//						LOGGER.error("[endDateConsommation] error:" + obj);
//						continue;
//					}
//
					Lab009Invoice invoice = new Lab009Invoice();
//					invoice.setSiteId(siteId);
//					if (mapContract.containsKey(invariant)) {
//						Lab009Contract contract = mapContract.get(invariant);
						invoice.setLotId(1);
						invoice.setEnergyTypeId(1);
						invoice.setProviderName("abc");
						invoice.setMontantTotal(12d);
						invoice.setConsommationTotal(12);
						Map<Integer, Lab009InvoiceYear> invoiceYearMap = new TreeMap<Integer, Lab009InvoiceYear>(){{
							Lab009InvoiceYear y =new Lab009InvoiceYear();
							y.setTotalConsommationYear(12);
							y.setTotalMontalYear(12d);
							y.setMapConsommationMonth(new TreeMap<Integer, Integer>(){{put(1,12);}});
							y.setMapMontalMonth(new TreeMap<Integer, Double>(){{put(1,12d);}});
							put(2016,y);
							
						}};
						invoice.setInvoiceYearMap(invoiceYearMap);
						invoice.setInvariant("abc");
						lst = addToList(lst, invoice);
//					} else {
//						LOGGER.error("[invariant] not map:" + invariant);
//						continue;
//					}
//				}
//			}
//		} catch (Exception e) {
//			LOGGER.error(e.getMessage());
//			return null;
//		}

		return lst;
	}

	/**
	 * Date 28/06/2014
	 * 
	 * @return
	 */

	private Map<Integer, Lab009InvoiceYear> getInvoiceByYear(Date fromDate, Date toDate, Double montantHt,
			Integer totalConsommation) {
		Map<Integer, Lab009InvoiceYear> map = new TreeMap<Integer, Lab009InvoiceYear>();

		Calendar cal = Calendar.getInstance();
		Date tmpDate = fromDate;
		int totalDay = LabUtils.different2Date(fromDate, toDate);
		// Loop by month
		while (tmpDate.compareTo(toDate) <= 0) {
			Date startDate = tmpDate;
			cal.setTime(startDate);
			cal.add(Calendar.MONTH, 1);
			cal.set(Calendar.DAY_OF_MONTH, 1);
			cal.add(Calendar.DAY_OF_MONTH, -1);
			Date endDate = cal.getTime();
			if (endDate.compareTo(toDate) > 0) {
				endDate = toDate;
			}
			int subDay = LabUtils.different2Date(startDate, endDate);
			Integer consommationTmp = (totalConsommation * subDay) / totalDay;
			Double montantHtTmp = (montantHt * subDay) / totalDay;

			cal.setTime(endDate);
			int year = cal.get(Calendar.YEAR);
			int month = cal.get(Calendar.MONTH);
			Lab009InvoiceYear lab009InvoiceYear = null;
			if (map.containsKey(year)) {
				lab009InvoiceYear = map.get(year);
			} else {
				lab009InvoiceYear = new Lab009InvoiceYear();
			}

			if (lab009InvoiceYear.getTotalConsommationYear() == null) {
				lab009InvoiceYear.setTotalConsommationYear(consommationTmp);
			} else {
				lab009InvoiceYear
						.setTotalConsommationYear(consommationTmp + lab009InvoiceYear.getTotalConsommationYear());
			}

			if (lab009InvoiceYear.getTotalMontalYear() == null) {
				lab009InvoiceYear.setTotalMontalYear(montantHtTmp);
			} else {
				lab009InvoiceYear.setTotalMontalYear(montantHtTmp + lab009InvoiceYear.getTotalMontalYear());
			}

			lab009InvoiceYear.setMapConsommationMonth(
					LabUtils.putValueToMap(lab009InvoiceYear.getMapConsommationMonth(), month, consommationTmp));
			lab009InvoiceYear.setMapMontalMonth(
					LabUtils.putValueToMap(lab009InvoiceYear.getMapMontalMonth(), month, montantHtTmp));
			cal.setTime(endDate);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			tmpDate = cal.getTime();
			map.put(year, lab009InvoiceYear);
		}
		return map;
	}

	private List<Lab009Invoice> addToList(List<Lab009Invoice> lst, Lab009Invoice invoice) {
		boolean isFind = false;
		for (int i = 0; i < lst.size(); i++) {
			Lab009Invoice lab009Invoice = lst.get(i);
			if (lab009Invoice.getSiteId().equals(invoice.getSiteId())
					&& lab009Invoice.getLotId().equals(invoice.getLotId())
					&& lab009Invoice.getEnergyTypeId().equals(invoice.getEnergyTypeId())
					&& lab009Invoice.getProviderName().equals(invoice.getProviderName())) {
				isFind = true;
				if (invoice.getMontantTotal() != null) {
					double montantlTotal = invoice.getMontantTotal();
					if (lab009Invoice.getMontantTotal() != null) {
						lab009Invoice.setMontantTotal(lab009Invoice.getMontantTotal() + montantlTotal);
					} else {
						lab009Invoice.setMontantTotal(montantlTotal);
					}
				}
				if (invoice.getConsommationTotal() != null) {
					Integer consommationTotal = invoice.getConsommationTotal();
					if (lab009Invoice.getConsommationTotal() != null) {
						lab009Invoice.setConsommationTotal(lab009Invoice.getConsommationTotal() + consommationTotal);
					} else {
						lab009Invoice.setConsommationTotal(consommationTotal);
					}
				}
				lab009Invoice.setInvoiceYearMap(
						putYearToAll(lab009Invoice.getInvoiceYearMap(), invoice.getInvoiceYearMap()));
				break;
			}
		}
		if (!isFind) {
			lst.add(invoice);
		}
		return lst;
	}

	public static Map<Integer, Lab009InvoiceYear> putYearToAll(Map<Integer, Lab009InvoiceYear> mapDestination,
			Map<Integer, Lab009InvoiceYear> mapSource) {
		if (mapDestination == null) {
			mapDestination = new TreeMap<Integer, Lab009InvoiceYear>();
		}
		for (Map.Entry<Integer, Lab009InvoiceYear> entry : mapSource.entrySet()) {
			Integer key = entry.getKey();
			Lab009InvoiceYear lab009InvoiceYear = entry.getValue();
			Lab009InvoiceYear lab009InvoiceInput = null;
			if (mapDestination.containsKey(key)) {
				lab009InvoiceInput = mapDestination.get(key);
			} else {
				lab009InvoiceInput = new Lab009InvoiceYear();
			}
			lab009InvoiceInput.setMapConsommationMonth(LabUtils.putMapToMap(
					lab009InvoiceInput.getMapConsommationMonth(), lab009InvoiceYear.getMapConsommationMonth()));
			lab009InvoiceInput.setMapMontalMonth(LabUtils.putMapToMap(lab009InvoiceInput.getMapMontalMonth(),
					lab009InvoiceYear.getMapMontalMonth()));

			mapDestination.put(key, lab009InvoiceInput);
		}
		return mapDestination;
	}

	public static void main(String[] args) {

	}
}