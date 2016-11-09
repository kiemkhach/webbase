package com.ifi.test;


public class AppTest {
	public static void main(String[] args) {
//		ApplicationContext context = ServiceFactory.getAppCtx();
		// UserDAO userDAO = context.getBean(UserDAO.class);
		// User user1 = new User("abc");
		// User user2 = new User("123");
		// User user3 = new User("hungnd3");
		// User user4 = new User("hungnd4");
		// List<User> listUsers = new ArrayList<User>();
		// listUsers.add(user1);
		// listUsers.add(user2);
		// listUsers.add(user3);
		// listUsers.add(user4);
		// if(userDAO.saveAll(listUsers)) {
		// System.out.println("success");
		// }

		// LabDAO labDAO = context.getBean(LabDAO.class);
		// Lab lab1 = new Lab("url","uri","name");
		// Lab lab2 = new Lab("url2","uri2","name2");
		// Lab lab3 = new Lab("url3","uri3","name3");
		// Lab lab4 = new Lab("url4","uri4","name4");
		// Lab lab5 = new Lab("url5","uri5","name5");
		//
		// List<Lab> listUsers = new ArrayList<Lab>();
		// listUsers.add(lab1);
		// listUsers.add(lab2);
		// listUsers.add(lab3);
		// listUsers.add(lab4);
		// listUsers.add(lab5);
		// if(labDAO.saveAll(listUsers)) {
		// System.out.println("success");
		// }

//		AgregatDAO agregatDAO = context.getBean(AgregatDAO.class);
//		int i = 0;
//		int batch_size = 5000;
//		try {
////			FileInputStream fstream = new FileInputStream(
////					"D:\\Project\\labteam\\CSM DB\\ENR_ST_Agregat.all.out");
//			FileInputStream fstream = new FileInputStream(
//			"D:\\Project\\labteam\\CSM DB\\ENR_ST_Agregat.all.out");
//			BufferedReader br = new BufferedReader(new InputStreamReader(
//					fstream));
//
//			String strLine;
//			// Read File Line By Line
//
//			List<Agregat> lst = new ArrayList<Agregat>();
//			while ((strLine = br.readLine()) != null) {
//
//				i++;
//				if (i <= 1) {
//
//					continue;
//				}
//				// Print the content on the console
//				System.out.println(strLine);
//				String[] arr = strLine.split("	");
//				Agregat agregat = new Agregat();
//				agregat.setId(Integer.parseInt(arr[0]));
//				agregat.setPeriod(Integer.parseInt(arr[1]));
//				agregat.setDateHeure(arr[2]);
//				agregat.setHeure(Integer.parseInt(arr[3]));
//				agregat.setConsommation(Integer.parseInt(arr[4]));
//				agregat.setIndex(Integer.parseInt(arr[5]));
//				agregat.setTemperature_min(Integer.parseInt(arr[6]));
//				agregat.setTemperature(Integer.parseInt(arr[7]));
//				agregat.setTemperature_max(Integer.parseInt(arr[8]));
//				agregat.setDebitHoraire_min(Integer.parseInt(arr[9]));
//				agregat.setDebitHoraire(Integer.parseInt(arr[10]));
//				agregat.setDebitHoraire_max(Integer.parseInt(arr[11]));
//				agregat.setRssi(Integer.parseInt(arr[12]));
//				agregat.setModuleId(Integer.parseInt(arr[13]));
//				agregat.setModulePeriod(arr[14]);
//				lst.add(agregat);
//				if (lst.size() == batch_size) {
//					if (agregatDAO.saveList(lst)) {
//						System.out.println("NUMBERRRRRRRRRR:"+i);
//						lst.clear();
//					}
//				}
//			}
//
//			agregatDAO.saveList(lst);
//
//			// Close the input stream
//			br.close();
//		} catch (Exception e) {
//			System.out.println("ERRORRRRRRRRR:" + i);
//			e.printStackTrace();
//		} finally {
//
//		}

		// UserLabDAO userlabDAO = context.getBean(UserLabDAO.class);
		// UserLab userlab1 = new UserLab(4,10);
		// UserLab userlab2 = new UserLab(3,4);
		// UserLab userlab3 = new UserLab(2,5);
		//
		// List<UserLab> listUsers = new ArrayList<UserLab>();
		// listUsers.add(userlab1);
		// listUsers.add(userlab2);
		// listUsers.add(userlab3);
		// if(userlabDAO.saveAll(listUsers)) {
		// System.out.println("success");
		// }

	}

	// public boolean save(Lab obj) {
	// try {
	// Session session = sessionFactory.openSession();
	// Transaction tx = session.beginTransaction();
	// session.persist(obj);
	// tx.commit();
	// session.close();
	// return true;
	// } catch (Exception e) {
	// e.printStackTrace();
	// return false;
	// }
	// }
}
