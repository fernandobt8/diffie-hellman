package dto;

import java.io.Serializable;
import java.math.BigInteger;

import math.RaizPrimitiva;

public class PublicInfoDto implements Serializable {
	private static final long serialVersionUID = 4773586785052555587L;
	private BigInteger prime;
	private BigInteger alfa;
	private BigInteger publicKey;

	public PublicInfoDto(BigInteger prime, BigInteger privateKey) {
		this.prime = prime;
		this.alfa = RaizPrimitiva.getRaizPrimitiva(prime);
		this.publicKey = this.alfa.modPow(privateKey, prime);
	}

	public PublicInfoDto(BigInteger privateKey, PublicInfoDto publicInfo) {
		this.prime = publicInfo.getPrime();
		this.alfa = publicInfo.getAlfa();
		this.publicKey = this.alfa.modPow(privateKey, this.prime);
	}

	public BigInteger getPrime() {
		return this.prime;
	}

	public BigInteger getAlfa() {
		return this.alfa;
	}

	public BigInteger getPublicKey() {
		return this.publicKey;
	}

	public void print() {
		System.out.println("Primo: " + this.prime + "\nAlfa: " + this.alfa + "\nChave Publica: " + this.publicKey);
	}
}