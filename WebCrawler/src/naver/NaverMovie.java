package naver;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NaverMovie {
	public static void main(String[] args) throws IOException {
		
		int count = 0;
		int page = 1;
		String prePage = "";
		while(true) {
			
			String url = "https://movie.naver.com/movie/bi/mi/pointWriteFormList.nhn?code"
					+ "=189633&type=after&isActualPointWriteExecute"
					+ "=false&isMileageSubscriptionAlready"
					+ "=false&isMileageSubscriptionReject=false&page="+page;
			
			// 페이지네이션 돌면서 1페이지부터 끝까지~ 영화내용, 평점, 작성자, 작성일자 수집
			
			Document doc = Jsoup.connect(url).get();
			
			String content="";
			int score = 0; // 나중에 데이터 모아서 평점만 따로 편집 할 수 있기 때문에 String type 보다는 int 타입이 낫다.
			String writer = "";
			String date = "";
			
			Elements movieList = doc.select("div.score_result > ul > li");
			String nowPage =doc.select("input#page").attr("value");
			System.out.println(">>>>>>>>>>>" +prePage + "," +nowPage);
			
			if(nowPage.equals(prePage)) {
				break;
			}else {
				prePage= nowPage;
			}
			
			int index = 0;
			for (Element movie : movieList) { 
				
				content = movie.select("span#_filtered_ment_"+(index)).text();
				score = Integer.parseInt(movie.select("div.star_score > em").get(0).text()); // String type => int type 형변환 해줌.		
				
				writer = movie.select("div.score_reple > dl > dt em ").get(0).text();
				int writer_Cut= writer.indexOf("(");
				
				
				date =  movie.select("div.score_reple > dl > dt em ").get(1).text();
				int date_cut = date.lastIndexOf(" ");
				
				
				String Ymddate = date.substring(0, date_cut);
				
				
				System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
				System.out.println("■■내용 : "+content);
				System.out.println("■■평점 : "+score+"점");
				
				
				if ( writer_Cut > 0) {
					System.out.println("■■작성자 :"+writer.substring(0, writer_Cut));
				}else {
					System.out.println("■■작성자 :"+writer);
				}
				
				
				System.out.println("■■작성일자 :"+ Ymddate);
				
				index++;
				count++;
				
			}
			page ++;

		}
	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>총 수집한 댓글수 :"+ count);
	}

}
