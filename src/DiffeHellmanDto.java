import java.io.Serializable;
import java.math.BigInteger;
import java.util.Random;

public class DiffeHellmanDto {
	private BigInteger privateKey;
	private publicInfoDto redeDto;

	public DiffeHellmanDto() {
		BigInteger prime = Primalidade.genPrimo(100);
		this.privateKey = new BigInteger(prime.bitLength(), new Random());
		this.redeDto = new publicInfoDto(prime, this.privateKey);
	}

	public publicInfoDto getRedeDto() {
		return this.redeDto;
	}

	public static class publicInfoDto implements Serializable {
		private static final long serialVersionUID = 4773586785052555587L;
		private BigInteger prime;
		private BigInteger alfa;
		private BigInteger publicKey;

		public publicInfoDto(BigInteger prime, BigInteger privateKey) {
			this.prime = prime;
			this.alfa = RaizPrimitiva.getRaizPrimitiva(prime);
			this.publicKey = this.alfa.modPow(privateKey, prime);
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
	}
}
