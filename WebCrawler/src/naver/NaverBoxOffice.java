package naver;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NaverBoxOffice {
	// 현재 네이버 영화 페이지의 현재 상영 영화 리스트들을 크롤링해보자.
	public static void main(String[] args) throws IOException {
		
		String url = "https://movie.naver.com/movie/running/current.nhn";
		
		Document doc = Jsoup.connect(url).get();
		
		/*
		 * for문 안에 객체를 생성하면 for문 돌때마다 또다시 계속 객체를 생성하기 때문에
		 * 애초에 for문 밖에 생성해놓는게 더 낫다.
		 */
		String title ="";	  // 영화 제목
		String score ="";	  // 영화 평점
		String bookRate ="";  // 영화 예매율
		String type ="";	  // 영화 장르
		String movieTime =""; // 영화 상영시간
		String openDt ="";	  // 영화 개봉일
		String director ="";  // 영화 감독
		String actor ="";	  // 영화 출연진
		String naverCode = "";
		
		
		
		Elements movieList = doc.select("div.lst_wrap > ul.lst_detail_t1 > li");
		
		
		for (Element movie : movieList) { 
			//movieList 가 element에 하나씩 꺼내서 담는것
			// python의 for element in movieList 랑 똑같다.
			
			bookRate = "0"; // 0 값으로 초기화
			director = "";
			actor = "";
			
			title = movie.select("dt.tit > a").text();
			
			
			if(movie.select("span.num").size() == 2) {
				bookRate = movie.select("span.num").get(1).text();
			} // 애초에 bookRate 디폴트 값이 0이기 때문에 위의 조건에만 해당하면 그 값을 출력하고
			// 아니면 똑같이 0을 출력
			
			score = movie.select("span.num").get(0).text();
			type = movie.select("dd > span.link_txt").get(0).text();
			
			
			/*
			 * 상영시간
			 * dl.info_txt1 > dd로 설정하면 개요|상영시간|개봉일자가 같이 뜬다
			 * 따라서 ||의 첫번째 인덱스와 마지막 인덱스 번호를찾아서
			 * 
			 * 만약에 beginTimeIndex == endTimeIndex 이라면 인덱스0번부터 마지막 인덱스번호까지 출력하고
			 * 그렇지 않다면 첫번째 인덱스 +2 부터 마지막 인덱스까지 출력하도록 if문을 만들어준다.
			 */
			String temp= movie.select("dl.info_txt1 > dd").get(0).text();
			int beginTimeIndex = temp.indexOf("|");
			int endTimeIndex = temp.lastIndexOf("|");

			
			if(beginTimeIndex == endTimeIndex) {
				movieTime = temp.substring(0, endTimeIndex);
			}else {
				movieTime =  temp.substring((beginTimeIndex+2), endTimeIndex);
			}
			
			//System.out.println(movie.select("dt.tit_t2")); // 감독
			//System.out.println(movie.select("dt.tit_t3")); // 출연진
			
			// 0 : 없음, 1 : 있음
			int dCode = 0; // 감독 유무확인
			int aCode = 0; // 출연진 유무 확인
			if(!movie.select("dt.tit_t2").text().equals("")) {
				dCode = 1; // 감독있음!
			}if(!movie.select("dt.tit_t3").text().equals("")) {
				aCode = 1; // 출연진 있음!
			}if(dCode == 1 && aCode == 0) {
				director = movie.select("dd > span.link_txt").get(1).text(); // 감독이 있으면 무조건 1을 불러오게 만듦.
			}else if(dCode == 0 && aCode == 1) {
				actor = movie.select("dd > span.link_txt").get(1).text(); // 출연진
			} else if(dCode == 1 && aCode == 1) {
				director = movie.select("dd > span.link_txt").get(1).text(); // 감독
				actor = movie.select("dd > span.link_txt").get(2).text(); // 출연진
			}
			
			
			
			// 우리는 https://movie.naver.com/movie/bi/mi/basic.nhn?code=180378 에서 180378만 뽑을거임
			// python [0:5]    java (0,)
			String naverHref = movie.select("dt.tit > a").attr("href"); // 네이버 영화 URL
			naverCode = naverHref.substring(naverHref.lastIndexOf("=")+1);
			
			
			
			//영화 개봉일자
			int openDtTxtIndex = temp.lastIndexOf("개봉"); 
			openDt = temp.substring(endTimeIndex+2);
			
			
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println("■■ 영화제목 :"+title);
			System.out.println("■■ 영화평점 :"+score + "점");
			System.out.println("■■ 영화평점 :"+bookRate + "%"); //극장판 시티헌터는 예매율이 없다. 그래서 ERROR뜸;
			System.out.println("■■ 영화장르 :"+type );
			System.out.println("■■ 감독 :"+ director );
			System.out.println("■■ 상영시간 :"+ movieTime );
			System.out.println("■■ 개봉일 :"+ openDt );
			System.out.println("■■ 출연진 :"+ actor);	
			System.out.println("■■ 무비코드 :"+ naverCode);	// 우리는 https://movie.naver.com/movie/bi/mi/basic.nhn?code=180378 에서 180378만 뽑을거임
			
		}
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");	
		
			
		
	}

}
