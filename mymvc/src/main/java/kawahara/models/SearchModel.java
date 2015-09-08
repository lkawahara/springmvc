package kawahara.models;

public class SearchModel {
	
	private static int NUM_CHARS_BEFORE_AFTER = 10;
	private static String ELLIPSIS = "...";
	
	private String relevantSearchWords;
	private long questionId;
	private String query;
	private StringBuilder sb = new StringBuilder();
	public SearchModel(){}

	public SearchModel(long questionId, String query, String relevantString){
		this.questionId = questionId;
		this.query = query;
		sb.setLength(0);
		sb.append(ELLIPSIS)
		.append(getSurroundingPortion(query, relevantString))
		.append(ELLIPSIS);
		this.relevantSearchWords = sb.toString();
	}
	
	private String getSurroundingPortion(String query, String relevantString){
		int index = relevantString.indexOf(query);
		int temp = (index - NUM_CHARS_BEFORE_AFTER);
		int startIndex = (temp < 0) ? 0 : temp;
		temp = index + query.length() + NUM_CHARS_BEFORE_AFTER;
		int endIndex = (temp > relevantString.length()) ? relevantString.length() : temp;
		return relevantString.substring(startIndex, endIndex);
	}
	
	public long getQuestionId(){
		return this.questionId;
	}
	
	public String getRelevantWords(){
		return this.relevantSearchWords;
	}

	public String getQuery(){
		return this.query;
	}
}
