package com.nemanja97.eBook.search;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopScoreDocCollector;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;

import com.nemanja97.eBook.lucene.analysers.SerbianAnalyzer;
import com.nemanja97.eBook.lucene.handlers.DocumentHandler;
import com.nemanja97.eBook.lucene.handlers.PDFHandler;
import com.nemanja97.eBook.search.ResultData;

public class ResultRetriever {
	
	private TopScoreDocCollector collector;
	private static int maxHits = 10;
	
	public ResultRetriever(){
		collector=TopScoreDocCollector.create(10);
	}
	
	public static void setMaxHits(int maxHits) {
		ResultRetriever.maxHits = maxHits;
	}

	public static int getMaxHits() {
		return ResultRetriever.maxHits;
	}
	
	public static List<ResultData> getResults(Query query) {
		if (query == null) {
			return null;
		}
		try {
			Directory indexDir = new SimpleFSDirectory(FileSystems.getDefault().getPath(ResourceBundle
					.getBundle("application").getString("index")));
			DirectoryReader reader = DirectoryReader.open(indexDir);
			IndexSearcher is = new IndexSearcher(reader); //pretraga dokumenta
			TopScoreDocCollector collector = TopScoreDocCollector.create(
					maxHits);

			List<ResultData> results = new ArrayList<ResultData>();

			is.search(query, collector);
			ScoreDoc[] hits = collector.topDocs().scoreDocs;

			ResultData rd;
			Document doc;
			
			for (ScoreDoc sd : hits) {
				doc = is.doc(sd.doc);
				String[] allKeywords = doc.getValues("keyword");
				String keywords = "";
				for (String keyword : allKeywords) {
					keywords += keyword.trim() + " ";
				}
				keywords = keywords.trim();
				String title = doc.get("title");
				String location = doc.get("filename");
				String author = doc.get("author");
				int category = Integer.parseInt(doc.get("category"));
				rd = new ResultData(title, author, keywords, location, category);
				results.add(rd);
			}
			reader.close();
			return results;

		} catch (IOException e) {
			throw new IllegalArgumentException(
					"U prosledjenom direktorijumu ne postoje indeksi ili je direktorijum zakljucan");
		} 
	}
	

}
