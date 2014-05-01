package edu.boun.swe574.fsn.backend.ws.response;

import java.util.List;

import edu.boun.swe574.fsn.backend.ws.response.info.UserInfo;

public class SearchForUsersResponse extends BaseServiceResponse {
	
	private List<UserInfo> userList;

	public List<UserInfo> getUserList() {
		return userList;
	}

	public void setUserList(List<UserInfo> userList) {
		this.userList = userList;
	}
	
}
