package cn.gn.lesson02.entity;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Result {
	/**
	 * 0表示失败
	 * 1表示成功
	 */
	private int code;
	/**
	 * 失败的消息
	 */
	private String message;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		if(message!=null && message.length()>1000){
			return message.substring(0, 300);
		}
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setMessage(Exception message) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		message.printStackTrace(new PrintStream(baos));
		this.message = new String(baos.toByteArray());
	}
	
}
