package kawahara.models;

import java.util.List;

public class CachedSearchModel {
	private String query;
	private List<SearchModel> results;
	
	public CachedSearchModel(String query, List<SearchModel>results){
		this.setQuery(query);
		this.setResults(results);
	}

	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public List<SearchModel> getResults() {
		return results;
	}
	public void setResults(List<SearchModel> results) {
		this.results = results;
	}
}
