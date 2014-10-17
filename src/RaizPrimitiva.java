import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

public class RaizPrimitiva {

	public static BigInteger getRaizPrimitiva(BigInteger numero) {
		BigInteger phi;
		if (numero.isProbablePrime(40)) {
			phi = numero.subtract(BigInteger.ONE);
		} else {
			phi = getEulerTotient(numero);
		}

		ArrayList<BigInteger> fatoresPrimos = getFatoresPrimos(phi);

		ArrayList<BigInteger> potenciasDivididas = getPotenciasDivididas(fatoresPrimos, phi);

		return ultimaRaizPrimitiva(numero, potenciasDivididas);
	}

	private static BigInteger getEulerTotient(BigInteger num) {
		BigInteger count = new BigInteger("0");
		for (BigInteger i = new BigInteger("1"); i.compareTo(num) < 0; i = i.add(BigInteger.ONE)) {
			if (GCD(num, i).compareTo(BigInteger.ONE) == 0) {
				count = count.add(BigInteger.ONE);
			}
		}
		return count;
	}

	private static BigInteger GCD(BigInteger a, BigInteger b) {
		BigInteger temp;
		if (a.compareTo(b) < 0) {
			temp = a;
			a = b;
			b = temp;
		}
		if (a.mod(b).compareTo(BigInteger.ZERO) == 0) {
			return b;
		}
		return GCD(a.mod(b), b);
	}

	private static ArrayList<BigInteger> getFatoresPrimos(BigInteger phi) {
		BigInteger n = phi;
		ArrayList<BigInteger> fatoresPrimos = new ArrayList<BigInteger>();
		for (BigInteger i = new BigInteger("2"); i.compareTo(n.divide(i)) <= 0; i = i.add(BigInteger.ONE)) {
			while (n.mod(i).equals(BigInteger.ZERO)) {
				fatoresPrimos.add(i);
				n = n.divide(i);
			}
		}
		if (n.compareTo(BigInteger.ONE) > 0) {
			fatoresPrimos.add(n);
		}
		return fatoresPrimos;
	}

	private static ArrayList<BigInteger> getPotenciasDivididas(ArrayList<BigInteger> fatoresPrimos, BigInteger phi) {
		ArrayList<BigInteger> potenciasDividas = new ArrayList<BigInteger>();
		for (BigInteger fator : fatoresPrimos) {
			BigInteger aux = phi.divide(fator);
			if (!potenciasDividas.contains(aux)) {
				potenciasDividas.add(aux);
			}
		}
		Collections.sort(potenciasDividas);
		return potenciasDividas;
	}

	private static BigInteger ultimaRaizPrimitiva(BigInteger numero, ArrayList<BigInteger> p) {
		BigInteger m = new BigInteger("2");
		boolean find = false;
		while (!find) {
			int i = 0;
			BigInteger result;
			for (i = 0; i < p.size(); i++) {
				result = m.modPow(p.get(i), numero);
				if (result.equals(BigInteger.ONE)) {
					m = m.add(BigInteger.ONE);
					break;
				}
			}
			if (i == p.size()) {
				find = true;
			}
		}
		return m;
	}
}
