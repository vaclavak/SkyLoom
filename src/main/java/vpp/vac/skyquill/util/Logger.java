package vpp.vac.skyquill.util;

public class Logger {
	
	public String loggerPrefix = "[Skyquill] [Logger]: ";
	
	public void sendLog(String x) {
		System.out.println(loggerPrefix + x);
	}
	
	public void sendLog(int x) {
		System.out.println(loggerPrefix + x);
	}
	
	public void sendLog(float x) {
		System.out.println(loggerPrefix + x);
	}
	
	public void sendLog(long x) {
		System.out.println(loggerPrefix + x);
	}
	
	public void sendLog(boolean x) {
		System.out.println(loggerPrefix + x);
	}
	
	public void sendLog(double x) {
		System.out.println(loggerPrefix + x);
	}
	
	public void sendLog(char x) {
		System.out.println(loggerPrefix + x);
	}
	
	public void sendLog(String[] x) {
		System.out.println(loggerPrefix + x);
	}
	
	
}
