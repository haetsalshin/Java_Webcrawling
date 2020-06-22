package daum;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DaumBoxOffice {
	public static void main(String[] args) throws IOException {
		String baseUrl ="http://ticket2.movie.daum.net/Movie/MovieRankList.aspx";
		Document doc = Jsoup.connect(baseUrl).get();
		
		Elements movieList = doc.select("ul.list_boxthumb > li > a");
		
		for(Element movie : movieList) {

			//System.out.println(movie.attr("href")); // 출력해보면 ticket ID 가 출력되는 것을 알 수 있다. 다음은 이렇게 이상하게 해놓음.
													// 우리가 필요한 것은  movienumber.
			
			String url = movie.attr("href"); // 따라서 새로운 url을 설정해주고 그 url에 들어가서 우리가 movie number를 추출해야한다.
			Document movieDoc = Jsoup.connect(url).get(); // movieDoc 해당 페이지의 모든 정보를 불러옴.
			
			
			// 다음에서 슈퍼스타 뚜뚜라는 영화를 빠른 예매에 올려놓고 클릭하면 창이 뜨지 않는다.. 이럴 경우 값이 없을 때 넘겨버리라는
			// if문 작성
			if(movieDoc.select("span.txt_name").size() ==0 ) {
				continue;
			}

			String title = movieDoc.select("span.txt_name").get(0).text();
			String titleCut = title.substring(0,title.lastIndexOf("("));
			
			
			String daumHref=movieDoc.select("a.area_poster").get(0).attr("href");
			// = 와 # 사이에 있는게 영화 코드 이므로 인덱스로 해당 문자열을 찾아서 슬라이싱 한 뒤 출력할 수 있도록 한다.
			String daumeCode=daumHref.substring(daumHref.lastIndexOf("=")+1,daumHref.lastIndexOf("#"));
		
		
			
			
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("■■영화제목 :" +titleCut);
			System.out.println("■■URL :" +daumeCode);
			
			
			
			
			
			
			
			
		}
		
		
		
		
		
		
	}
}
