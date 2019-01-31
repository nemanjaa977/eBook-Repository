package com.nemanja97.eBook.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.apache.lucene.search.Query;

import com.nemanja97.eBook.search.QueryBuilder;
import com.nemanja97.eBook.search.ResultData;
import com.nemanja97.eBook.search.ResultRetriever;
import com.nemanja97.eBook.search.SearchType;
import com.nemanja97.eBook.search.SimpleQuery;

@RestController
@RequestMapping(value="api/search")
@CrossOrigin("*")
public class SearchController {
	
	@PostMapping(value="/term", consumes="application/json")
	public ResponseEntity<List<ResultData>> searchTermQuery(@RequestBody SimpleQuery simpleQuery) throws Exception {		
		Query query= QueryBuilder.buildQuery(SearchType.regular, simpleQuery.getField(), simpleQuery.getValue());
		List<ResultData> results = ResultRetriever.getResults(query);			
		return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
	}
	
	@PostMapping(value="/fuzzy", consumes="application/json")
	public ResponseEntity<List<ResultData>> searchFuzzy(@RequestBody SimpleQuery simpleQuery) throws Exception {
		Query query= QueryBuilder.buildQuery(SearchType.fuzzy, simpleQuery.getField(), simpleQuery.getValue());
		List<ResultData> results = ResultRetriever.getResults(query);			
		return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
	}
	
	@PostMapping(value="/phrase", consumes="application/json")
	public ResponseEntity<List<ResultData>> searchPhrase(@RequestBody SimpleQuery simpleQuery) throws Exception {
		Query query= QueryBuilder.buildQuery(SearchType.phrase, simpleQuery.getField(), simpleQuery.getValue());
		List<ResultData> results = ResultRetriever.getResults(query);			
		return new ResponseEntity<List<ResultData>>(results, HttpStatus.OK);
	}

}
