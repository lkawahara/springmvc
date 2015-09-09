package kawahara.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import kawahara.models.AnswerModel;
import kawahara.models.CachedSearchModel;
import kawahara.models.QuestionModel;
import kawahara.models.SearchModel;

public class SearchService {
	private LoadingCache<String, CachedSearchModel> searchResultsCache;
	//injected
	private QuestionService questionService;
	private AnswerService answerService;
	
	public SearchService(){
		searchResultsCache = CacheBuilder.newBuilder()
			       .maximumSize(100)
			       .expireAfterWrite(10, TimeUnit.MINUTES)
			       .build(
			           new CacheLoader<String, CachedSearchModel>() {
			             public CachedSearchModel load(String key) throws ExecutionException {
			               return search(key);
			             }
			           });
	}
	
	public void clearCache(){
		searchResultsCache.invalidateAll();
	}
	
	public SearchService(QuestionService questionService, AnswerService answerService){
		this.questionService = questionService;
		this.answerService = answerService;
	}
	
	public CachedSearchModel search(String search) {
		List<SearchModel> ret = new ArrayList<>();
		
		Collection<QuestionModel> questions = questionService.getQuestions();
		
		for(QuestionModel q : questions){
			String relevantString = q.contains(search);
			if(relevantString != null){
				ret.add(new SearchModel(q.getId(), search, relevantString));
			}
		}
		
		Collection<AnswerModel> answers = answerService.getAnswers();
		for(AnswerModel a : answers){
			String relevantString = a.contains(search);
			if(relevantString != null){
				ret.add(new SearchModel(a.getQuestionId(), search, relevantString));
			}
		}
		
		return new CachedSearchModel(search, ret);
	}
	
	public void setAnswerService(AnswerService answerService){
		this.answerService = answerService;
	}
	
	public void setQuestionService(QuestionService questionService){
		this.questionService = questionService;
	}
	
	public List<SearchModel> getSearchResults(String search){
		try {
			return ((CacheLoader<String, CachedSearchModel>) searchResultsCache).load(search).getResults();
		} catch (Exception e) {
			e.printStackTrace();
			return new ArrayList<SearchModel>();
		}
	}
}
