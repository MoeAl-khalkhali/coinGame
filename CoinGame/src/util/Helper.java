package util;

public class Helper
{
	public static String changeCase(String coin) {
		String result = coin.toLowerCase();
		result = result.substring(0,1).toUpperCase() + result.substring(1);
		
		return result;
		
	}
	public static String changeCaseCoinPair(String coin) {
		String result = coin.toLowerCase();
		result = result.substring(0, 8) + result.substring(8,9).toUpperCase() + result.substring(9,23) +
				result.substring(23,24).toUpperCase() + result.substring(24);
		
		return result;
		
	}

}
