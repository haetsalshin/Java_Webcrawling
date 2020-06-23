package daum;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DaumMovie {
	
	public static void main(String[] args) throws Exception {
		
		int count =0;
		int page = 1;
		
		
		while (true) {
			
			String url = "https://movie.daum.net/moviedb/grade?movieId"
					+ "=134684&type=netizen&page="+page;
			
			Document doc = Jsoup.connect(url).get();
			
			String writer = "";
			String score = "";
			String content = "";
			String date = "";
			String preWriter = "";
		
			
			Elements movie = doc.select("div.review_info");
			
			for(int i =1; i < movie.size(); i++) {

				writer = movie.select("em.link_profile").get(i).text();
				

				if(preWriter.equals(writer)) {
					break;
				} else {
					preWriter = writer;
				}
				
				score = movie.select("em.emph_grade").get(i).text();
				content = movie.select("p.desc_review").get(i).text();
				String dateTemp = movie.select("span.info_append").get(i).text();
				
				int dateCut = dateTemp.indexOf(" "); // 11
				
				date = (String) dateTemp.subSequence(0, 10);
				
				
				System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				System.out.println("작성자 : "+ writer);
				System.out.println(preWriter);
				System.out.println("평점 : "+ score);
				System.out.println("내용 : "+ content);
				System.out.println("작성일자 : "+ date);
				count ++;
				

			}
			page++;			
			
		}
		
	}
}
