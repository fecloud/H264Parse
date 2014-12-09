package cn.yuncore.util;

public final class Log {

	public static void d(String tag, String message) {
		System.out.println(String.format("[ DEBUG %s ] %s   %s",
				Utils.formatNow(), tag, message));
	}

	public static void e(String tag, String message) {
		System.out.println(String.format("[ ERROR %s ] %s   %s",
				Utils.formatNow(), tag, message));
	}

	public static void e(String tag, String message, Throwable throwable) {
		System.out.println(String.format("[ ERROR %s ] %s   %s \n%s",
				Utils.formatNow(), tag, message, throwable.getMessage()));
	}

}
