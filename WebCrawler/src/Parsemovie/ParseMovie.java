package Parsemovie;
import java.io.BufferedInputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

 
public class ParseMovie {
	/*
	 * 파싱 하기 위하여 필요한 것 3가지 
	 */
	

	String key = "352f5bed97fd17245e627f99479b115d";
	String today = "";
	String[][] arrBoxOffice = new String[10][4]; 

	
	// 1. 파싱하고 싶은 URL 생성(일일박스오피스 : 하루 전날)
	// URL = 기본 + key + 날짜
	public String makeURL() {

		//오늘 날짜 구하기

		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,-1);
		System.out.println("포맷 전 : " + (cal.getTime()));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		today = sdf.format(cal.getTime());
		System.out.println("포맷 후 :" +today);
		

		// 제공서비스에서 응답예시를 복붙 후

		String url ="http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/"
				+"searchDailyBoxOfficeList.json"
				+"?key=" + key
				+ "&targetDt=" + today;

		return url;
	}
	

	private String readUrl(String preUrl) throws Exception { 
		BufferedInputStream reader = null; // BufferedInputStream 패킷으로 쪼개져서 전달된 data를 내가 읽겠다.!
										   //  buffer는 임시 저장소같은 개념
		
		try {
			URL url = new URL(preUrl); // url이 있는데 네트워크 상에 있기 때문에 import java.net.URL;을 해준다.
			reader = new BufferedInputStream(url.openStream()); // url을 열어서 bufferendinput에 넣어주게 이게 reader로 저장.
			StringBuffer buffer = new StringBuffer();
			int i;
			byte[] b = new byte[4096]; // 4096 byte만큼 크기를 쪼개라. 64. 128 .256 이런 값을 넣는게 좋은데 보통 4096를 넣어줌
									   // 자기가 패킷을 조금씩 많이 잘라서 보낼껀지, 크게 조금 보낼껀지 고려해서 보낸다.
			while((i= reader.read(b)) != -1 ) { // 전체 문서에서 read할건데 b 사이즈만큼 읽겠다. 그걸 i한테 줌.
												// 다 읽어 들여서 받아들일 데이터가 없으면 -1를 반환하겠다. 그럼 while 빠져나감.
				
				buffer.append(new String(b,0,i)); // buffer에 url data가 계속 뒤에 쌓인다.
			}
			return buffer.toString(); //buffer를 문자열로 바꾸는 것. return해서 아래 getBoxOffice의 readUrl로 간다.
		} finally {

			if(reader != null) {
				reader.close();
			}
		}
	}


	public String[][] getBoxOffice(String url) throws Exception{

		JSONParser parser = new JSONParser();
		JSONObject obj = (JSONObject)parser.parse(readUrl(url));     // 전체 파일이 obj에게 있음.
		JSONObject json = (JSONObject) obj.get("boxOfficeResult");   // 해당 url가보면 첫번째 줄에 boxOfficeResult가 전체 data를
		// array는 1~10위 까지의 BoxOffice List담김			         // 묶고 있는 걸 알 수 있다. 이걸 10개로 쪼개줘야 한다.
		JSONArray array = (JSONArray)json.get("dailyBoxOfficeList"); // 전체 묶음 중에서 dailyboxofficelist를 가져오겠다.
																		// key, vale = > 1~10까지의 List
																		
		

		for(int i = 0 ; i < array.size() ; i++) {

			JSONObject entity = (JSONObject)array.get(i);

			String rank = (String) entity.get("rank"); // 순위
			String movieNm = (String) entity.get("movieNm"); // 영화제목
			String audiAcc = (String) entity.get("audiAcc"); // 누적관객수
			String salesAcc = (String) entity.get("salesACC"); // 누적매출액

			arrBoxOffice[i][0] = rank;
			arrBoxOffice[i][1] = movieNm;
			arrBoxOffice[i][2] = audiAcc;
			arrBoxOffice[i][3] = salesAcc;
		}
		
		return arrBoxOffice;

	}

}