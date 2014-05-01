package edu.boun.swe574.fsn.backend.test.ws;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;

import edu.boun.swe574.fsn.common.client.network.NetworkService;
import edu.boun.swe574.fsn.common.client.network.NetworkService_Service;
import edu.boun.swe574.fsn.common.client.network.SearchForUsersResponse;
import edu.boun.swe574.fsn.common.client.network.UserInfo;

public class CallNetworkWS {

	private static SearchForUsersResponse searchForUsers;

	public static void main(String[] args) {
		try {
			URL url = new URL("http://swe.cmpe.boun.edu.tr:8080/FSN_SERVER/fsnws_network?wsdl");
			NetworkService_Service service = new NetworkService_Service(url, new QName("http://ws.backend.fsn.swe574.boun.edu/", "NetworkService"));
			NetworkService networkServicePort = service.getNetworkServicePort();
			
			searchForUsers = networkServicePort.searchForUsers("token", "queryString");
			
			System.out.println("response recived: resultCode=" + searchForUsers.getResultCode() + 
					" errorCode=" + searchForUsers.getErrorCode());
			
			List<UserInfo> userList = searchForUsers.getUserList();
			if(userList != null && !userList.isEmpty()) {
				for (UserInfo u : userList) {
					System.out.println("userId:"+u.getUserId() + "\nname:" + u.getName() + "\nsurname:" + u.getSurname() +
							"\nemail:" + u.getEmail());
				}
			}
			

			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
