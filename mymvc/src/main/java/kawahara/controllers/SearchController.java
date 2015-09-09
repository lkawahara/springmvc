package kawahara.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kawahara.models.SearchModel;
import kawahara.services.SearchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SearchController{
	@Autowired
	private SearchService searchService;
	
	public SearchController(){
		
	}
	public SearchController(SearchService searchService){
		this.searchService = searchService;
	}
	
	
	public static final Logger LOG = LoggerFactory.getLogger(SearchController.class);
	public static final int ITEMS_PER_PAGE = 5; //TODO grab and store in session
	
	//session stored attributes
	public static final String QUERY = "query";
	public static final String PAGE_I = "pageIndex";
	public static final String RESULTS = "searchResults";
	
	public int getPageIndex(HttpSession session, String currQuery, List<SearchModel> allSearchResults, HttpServletRequest request){
		int pageIndex = 0;//default starting page
		if(session.getAttribute(QUERY) != null){//there has been a search already made
			String sessionQuery = (String)session.getAttribute(QUERY);
			//if the requestQuery doesn't match the sessionQuery: this is a different search from the last search.
				//set page index to 0 to see first page of results
			//else grab page index from session
			if(sessionQuery.equals(currQuery) && session.getAttribute(PAGE_I) != null){
				pageIndex = (int)session.getAttribute(PAGE_I);
			}
		}
		//request can also have page index attributes (if the request is a next/prev button click)
		if(request.getParameter(PAGE_I) != null){
			int testIndex = Integer.parseInt(request.getParameter(PAGE_I));
			//clamp index
			
			testIndex = (testIndex < 0) ? 0 : ((testIndex > (allSearchResults.size() / ITEMS_PER_PAGE - 1)) ? (allSearchResults.size() / ITEMS_PER_PAGE - 1) : testIndex);
			pageIndex = testIndex;
		}
		return pageIndex;
	}
	
	private ModelAndView setAttributes(HttpSession session, String requestQuery, int pageIndex, List<SearchModel> allSearchResults){
		ModelAndView mv = new ModelAndView();
		mv.setViewName("search");
		//only display items on current page
		if(allSearchResults.isEmpty()){
			mv.addObject("message", "No Matching Results Found");
		}else{
			int startingDisplayIndex = ITEMS_PER_PAGE * pageIndex;
			
			SearchModel [] displayedSearchResults = new SearchModel[ITEMS_PER_PAGE];
			for(int i = 0; i < ITEMS_PER_PAGE; i++, startingDisplayIndex++){
				displayedSearchResults[i] = allSearchResults.get(startingDisplayIndex);			
			}
			mv.addObject(RESULTS, displayedSearchResults);
			
			String message = "Showing Page " + (pageIndex + 1) + " of " + (allSearchResults.size() / ITEMS_PER_PAGE); 
			mv.addObject("message", message);
		}
		
		mv.addObject(QUERY, requestQuery);
		mv.addObject(PAGE_I, pageIndex);
		//and session for future requests
		session.setAttribute(QUERY, requestQuery);
		session.setAttribute(PAGE_I, pageIndex);
		return mv;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView doGet(HttpServletRequest request){
		HttpSession session = request.getSession();
		String requestQuery = request.getParameter("search");
		
		//get all search results (from cache or compute)
		List<SearchModel> allSearchResults = searchService.getSearchResults(requestQuery);
		int pageIndex = getPageIndex(session, requestQuery, allSearchResults, request);
		return setAttributes(session, requestQuery, pageIndex, allSearchResults);
	}
	
	public void setSearchService(SearchService searchService){
		this.searchService = searchService;
	}
}