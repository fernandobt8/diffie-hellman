package dto;

import java.math.BigInteger;
import java.util.Random;

import main.Main;

import comunicacao.MessageManager;

public class DiffieHellmanDto {
	private String name;
	private BigInteger privateKey;
	private BigInteger publicKey;
	private BigInteger k;

	public DiffieHellmanDto(String name) {
		this.name = name;
		// gera um X aletório X < q
		this.privateKey = new BigInteger(Main.prime.bitLength(), new Random());
		// gera um Y alfa^X mod q
		this.publicKey = Main.alfa.modPow(this.privateKey, Main.prime);
	}

	public void print() {
		MessageManager.print("Meus Dados:\nX" + this.name + ": " + this.privateKey + "\nY" + this.name + ": " + this.publicKey);
	}

	// gera um k baseado no Y recebido
	public void generateK(BigInteger publicKey) {
		this.k = publicKey.modPow(this.privateKey, Main.prime);
		MessageManager.print("K: " + this.k);
	}

	// checa se o Y recebido é válido vendo se gera o mesmo k previamente estabelecido.
	public boolean check(BigInteger publicKey) {
		return publicKey.modPow(this.privateKey, Main.prime).equals(this.k);
	}

	public BigInteger getPublicKey() {
		return this.publicKey;
	}

	public BigInteger getK() {
		return this.k;
	}

	public String getName() {
		return this.name;
	}
}
