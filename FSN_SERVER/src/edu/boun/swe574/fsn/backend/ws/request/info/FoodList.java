package edu.boun.swe574.fsn.backend.ws.request.info;

import java.util.List;

import edu.boun.swe574.fsn.backend.ws.response.info.FoodInfo;

public class FoodList {

	private List<FoodInfo> list;

	public List<FoodInfo> getList() {
		return list;
	}

	public void setList(List<FoodInfo> list) {
		this.list = list;
	}
	
	
}
