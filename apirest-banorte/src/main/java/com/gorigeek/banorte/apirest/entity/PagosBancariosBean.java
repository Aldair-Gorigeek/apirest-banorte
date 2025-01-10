package com.gorigeek.banorte.apirest.entity;

public class PagosBancariosBean {

	private String urlBanco;
	private String merchant_id;
	private String merchant_user;
	private String merchant_pass;
	private String cmd_trans;
	private String terminal_id;
	private String mode;
	private String entry_mode;
	private String response_language;
	private String environment;
	private String secure_plus_3d_version;
	private String secure3D;
	
	public String getUrlBanco() {
		return urlBanco;
	}
	public void setUrlPost(String urlbanco) {
		urlBanco = urlbanco;
	}
	
	public String getSecure3D() {
		return secure3D;
	}
	public void setSecure3D(String secure3d) {
		secure3D = secure3d;
	}
	public String getMerchant_id() {
		return merchant_id;
	}
	public void setMerchant_id(String merchant_id) {
		this.merchant_id = merchant_id;
	}
	public String getMerchant_user() {
		return merchant_user;
	}
	public void setMerchant_user(String merchant_user) {
		this.merchant_user = merchant_user;
	}
	public String getMerchant_pass() {
		return merchant_pass;
	}
	public void setMerchant_pass(String merchant_pass) {
		this.merchant_pass = merchant_pass;
	}
	public String getCmd_trans() {
		return cmd_trans;
	}
	public void setCmd_trans(String cmd_trans) {
		this.cmd_trans = cmd_trans;
	}
	public String getTerminal_id() {
		return terminal_id;
	}
	public void setTerminal_id(String terminal_id) {
		this.terminal_id = terminal_id;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getEntry_mode() {
		return entry_mode;
	}
	public void setEntry_mode(String entry_mode) {
		this.entry_mode = entry_mode;
	}
	public String getResponse_language() {
		return response_language;
	}
	public void setResponse_language(String response_language) {
		this.response_language = response_language;
	}
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getSecure_plus_3d_version() {
		return secure_plus_3d_version;
	}
	public void setSecure_plus_3d_version(String secure_plus_3d_version) {
		this.secure_plus_3d_version = secure_plus_3d_version;
	}
}

