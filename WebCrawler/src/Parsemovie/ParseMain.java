package Parsemovie;

public class ParseMain {

	/*
	 * 우리가 읽어드릴 값은 JSON을 이용하는 것이기 때문에 JSON이 필요하다. JSON 다운받아 오기.
	 * 
	 */
	
	public final static String url = "";
	public final static String key = "";
	public static String today;
	
	
	public static void main(String[] args) throws Exception {
		ParseMovie pm = new ParseMovie();
		
		String url = pm.makeURL();
		
		String[][] arrRank = pm.getBoxOffice(url);
		
		for( int i = 0 ; i < arrRank.length; i ++) {
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
			System.out.println(arrRank[i][0] +"위");
			System.out.println(arrRank[i][1] );
			System.out.println(arrRank[i][2] +"명");
			System.out.println(arrRank[i][3] +"원");
			System.out.println("■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■■");
		}
		
		
	}
}
