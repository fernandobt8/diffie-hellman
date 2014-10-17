
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Primalidade {
	private static final Random rnd = new Random();
	private static Set<BigInteger> firtsPrimes = new HashSet<BigInteger>(Arrays.asList(new BigInteger("2"), new BigInteger("3"), new BigInteger("5"), new BigInteger("7")));
	private static Set<BigInteger> ignore = new HashSet<BigInteger>(Arrays.asList(BigInteger.ZERO, BigInteger.ONE));

	public static BigInteger genPrimo(int numeroCasa){
		BigInteger numero = null;
		// gera um numero aleatorio e testa sua primalidade.
		do {
			String genNumero = "";
			for (int counter = 0; counter < numeroCasa; counter++) {
				int random = rnd.nextInt(10);
				genNumero += random;
			}
			numero = new BigInteger(genNumero);
			// caso seja um provavel primo sai do loop
		} while (!(!ignore.contains(numero) && isPrime(numero)));
		return numero;
	}

	// testa a primalidade de um numero
	public static boolean isPrime(BigInteger n) {
		if (firtsPrimes.contains(n)) {
			return true;
		}
		BigInteger dois = new BigInteger("2");
		BigInteger nMenosDois = n.subtract(dois);
		// testa 10 a's (1/4^10 - chance de falhar) caso nenhum diga o contrario, n é provavelmente primo
		for (int i = 0; i < 10; i++) {
			BigInteger a;
			// gera um a | 1 < a < n-1
			do {
				a = new BigInteger(n.bitLength(), rnd);
			} while (a.compareTo(dois) < 0 || a.compareTo(nMenosDois) > 0);
			if (!millerRabin(a, n)) {
				return false;
			}
		}
		return true;
	}

	// testa as condições de miller rabin para n dado um a
	public static boolean millerRabin(BigInteger a, BigInteger n) {
		BigInteger nMenosUm = n.subtract(BigInteger.ONE);

		// pega s e d de n - 1
		int s = nMenosUm.getLowestSetBit();
		BigInteger d = nMenosUm.shiftRight(s);

		// a^d mod n = 1 ou igual n - 1 provavel primo
		BigInteger aPotencia = a.modPow(d, n);
		if (aPotencia.equals(BigInteger.ONE) || aPotencia.equals(nMenosUm)) {
			return true;
		}
		// (a^(2^r))*d mod n para 0 < r < s-1
		for (int r = 0; r < s - 1; r++) {
			aPotencia = aPotencia.multiply(aPotencia).mod(n);
			// (a^(2^r))*d mod n = 1 não é primo
			if (aPotencia.equals(BigInteger.ONE)) {
				return false;
			}
			// (a^(2^r))*d mod n = n-1 provavel primo
			if (aPotencia.equals(nMenosUm)) {
				return true;
			}
		}
		// não é primo
		return false;
	}
}
