package edu.boun.swe574.fsn.backend.ws.response;

import java.util.List;

import edu.boun.swe574.fsn.backend.ws.response.info.RevisionInfo;

public class GetRevisionHistoryOfRecipeResponse extends BaseServiceResponse {

	private List<RevisionInfo> listOfRevisions;

	public List<RevisionInfo> getListOfRevisions() {
		return listOfRevisions;
	}

	public void setListOfRevisions(List<RevisionInfo> listOfRevisions) {
		this.listOfRevisions = listOfRevisions;
	}
	
}
