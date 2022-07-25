package model;

import java.io.Serializable;

public class Email implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String mailto;
	private String mailfrom;
	private String mailbody;
	private String postfix;
	
	public Email(String mailto,String mailfrom,String mailbody) {
		this.mailto=mailto;
		this.mailfrom=mailfrom;
		this.mailbody=mailbody;
		setPostfix();
	}
	public Email() {}
	public String getMailto() {
		return mailto;
	}

	public void setMailto(String mailto) {
		this.mailto = mailto;
	}

	public String getMailfrom() {
		return mailfrom;
	}

	public void setMailfrom(String mailfrom) {
		this.mailfrom = mailfrom;
	}

	public String getMailbody() {
		return mailbody;
	}

	public void setMailbody(String mailbody) {
		this.mailbody = mailbody;
	}
	public String getPostfix() {
		return postfix;
	}
	public void setPostfix() {
		
		if(mailfrom.contains("@gmail.com")) {
			this.postfix="@gmail.com";
		}
		else if(mailfrom.contains("@walla.co.il")) {
			this.postfix="@walla.co.il";
		}
		else if(mailfrom.contains("@yahoo.com")) {
			this.postfix="@yahoo.com";
		}
		else if(mailfrom.contains("@yopmail.com")) {
			this.postfix="@yopmail.com";
		}
		else {
			this.postfix="else";
		}
		
	}
	@Override
	public String toString() {
		return  mailto + "," + mailfrom + "," + mailbody + "," + postfix;
	}
	
	

}
