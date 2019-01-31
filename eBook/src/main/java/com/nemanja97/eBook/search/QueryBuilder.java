package com.nemanja97.eBook.search;

import com.nemanja97.eBook.lucene.analysers.SerbianAnalyzer;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.util.BytesRef;

public class QueryBuilder {
	
	private static SerbianAnalyzer analyzer = new SerbianAnalyzer();
	private static int maxEdits = 1;
	
	public static int getMaxEdits(){
		return maxEdits;
	}
	
	public static void setMaxEdits(int maxEdits){
		QueryBuilder.maxEdits = maxEdits;
	}
	
	public static Query buildQuery(SearchType queryType, String field, String value) throws IllegalArgumentException, ParseException{
		QueryParser parser = new QueryParser(field, analyzer);
		String errorMessage = "";
		if(field == null || field.equals("")){
			errorMessage += "Field not specified";
		}
		if(value == null){
			if(!errorMessage.equals("")) errorMessage += "\n";
			errorMessage += "Value not specified";
		}
		if(!errorMessage.equals("")){
			throw new IllegalArgumentException(errorMessage);
		}
		
		Query query = null;
		if(queryType.equals(SearchType.regular)){
			Term term = new Term(field, value);
			query = new TermQuery(term);
		}else if(queryType.equals(SearchType.fuzzy)){
			Term term = new Term(field, value);
			query = new FuzzyQuery(term, maxEdits);
		}else{
			String[] values = value.split(" ");
			PhraseQuery.Builder builder = new PhraseQuery.Builder();
			for(String word : values){
				Term term = new Term(field, word);
				builder.add(term);
			}
			query = builder.build();
		}
		
		return parser.parse(query.toString(field));
	}

}
