package naver;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NaverFinance {
	// 긁고 싶은 페이지 url
	// static 붙이는 이유(안붙여도 에러는 안난다)? base 를 쓰려는 곳이 일반 method이면 안써도 되지만, 
	// main method일 때는 static을 써주지 않으면 출력 할 때 error가 뜬다...
	// static 변수를 사용하려면 전역변수에 반드시 static 변수만을 쓸 수 있다.
	static String base = "https://finance.naver.com/item/frgn.nhn?code=035760&page=1";
	
	
	// java는 jsoup 으로 crawling!
	public static void main(String[] args) throws IOException { // throws IOException 을 추가 해줘야 하는 이유?
																// 예외가 발생하면 하는 처리 안하고 나를 호출한 녀석한테 던져 버리겠다.
																
		// base 사이트로 가서 전체 페이지의 소스코드를 가져옴(python의 requests)
		Document doc = Jsoup.connect(base).get(); // base가 url에 갔는데 사이트가 터져있다던가, 사이트 접속이 폭주 했다던가
												  // 이런 사태가 있을 수 있기 때문에 예외처리를 해줘야 한다!
		
		
		Elements line = doc.select("table.type2 > tbody > tr"); // 46개. 쓸모 없는 trash data도 포함 된 것을 알 수있다.
																// elements 여러건담음(복수)
		System.out.println(line.size());
		
		//int count = 0;
		// 향상된 for문 ( =for each문)
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		for (Element element : line) { // element = 46개. line을 한건씩 꺼내서 element(단건)에 담는 것. python에서의 in 개념.
			
			Elements tds = element.select("td");  // tr안에 td들만 뽑아주세요. 그래서 다시 복수건이 되니까 elements.
			if(tds.size()== 9) {
				
				//System.out.println(tds.size());
				//count += 1;
				System.out.println();
				/*
				 * 우리가 꺼내오려는 것이 전부다 선택자도 똑같고 태그도 똑같아서 인덱스 번호로  꺼내오는 수 밖게 없다.
				 */
				String regdate = tds.get(0).text(); // 0번지 index값을 꺼내오는 것. 거기에 text만 꺼내온다.
				System.out.printf("날짜: %s \t",regdate);
				String songa = tds.get(1).text();
				System.out.printf("종가: %s \t", songa);
				String lastnight = tds.get(2).text();
				System.out.printf("전일비: %s \t", lastnight);
				String upanddown = tds.get(3).text();
				System.out.printf("등락률: %s \t", upanddown);
				String trade = tds.get(4).text();
				System.out.printf("거래량: %s \t", trade);
				String puretrade = tds.get(5).text();
				System.out.printf("순매매량: %s \t", puretrade);
				String forginerpuretrade = tds.get(6).text();
				System.out.printf("외국인 순매매량: %s \t", forginerpuretrade);
				String howmany = tds.get(7).text();
				System.out.printf("외국인 보유주수: %s \t", howmany);
				String howmanyrate = tds.get(8).text();
				System.out.printf("외국인 보유주수: %s \t", howmanyrate);
				
			}
			
		}
		//System.out.println("CNT: " + count);
		System.out.println();
		System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
	}

}
