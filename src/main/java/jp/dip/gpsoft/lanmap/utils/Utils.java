package jp.dip.gpsoft.lanmap.utils;

public class Utils {

	static public boolean empty(String s) {
		return s == null || s.length() == 0;
	}

	static public boolean blank(String s) {
		return s == null || s.trim().length() == 0;
	}
}
