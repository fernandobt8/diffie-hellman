package dto;

import java.io.Serializable;
import java.math.BigInteger;

//classe usada para enviar mensagens com um Y junto.
public class Message implements Serializable {
	private static final long serialVersionUID = -6536116242955591743L;
	private BigInteger publicKey;
	private String dado;

	public Message(BigInteger publicKey, String dado) {
		super();
		this.publicKey = publicKey;
		this.dado = dado;
	}

	public BigInteger getPublicKey() {
		return this.publicKey;
	}

	public void setPublicKey(BigInteger publicKey) {
		this.publicKey = publicKey;
	}

	public String getDado() {
		return this.dado;
	}

	public void setDado(String dado) {
		this.dado = dado;
	}
}
