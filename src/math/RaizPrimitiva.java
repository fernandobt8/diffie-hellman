package math;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

public class RaizPrimitiva {

	public static BigInteger getRaizPrimitiva(BigInteger numero) {
		BigInteger phi;
		// caso o número seja primo phi = n - 1, caso contrário phi = ao número de inteiros menor ou igual a 'n' que são relativamente primos a 'n'.
		if (numero.isProbablePrime(40)) {
			phi = numero.subtract(BigInteger.ONE);
		} else {
			phi = getEulerTotient(numero);
		}

		System.out.println("start f");
		ArrayList<BigInteger> fatoresPrimos = getFatoresPrimos(phi);

		System.out.println("end f");
		ArrayList<BigInteger> potenciasDivididas = getPotenciasDivididas(fatoresPrimos, phi);
		System.out.println("end potencias");

		return ultimaRaizPrimitiva(numero, potenciasDivididas);
	}

	// conta o número de inteiros que são relativamente primos a um 'n'.
	private static BigInteger getEulerTotient(BigInteger num) {
		BigInteger count = new BigInteger("0");
		// começa com i = 1 e vai até i < num, testando se o maior divisor comum de i e n é 1.
		for (BigInteger i = new BigInteger("1"); i.compareTo(num) < 0; i = i.add(BigInteger.ONE)) {
			if (GCD(num, i).compareTo(BigInteger.ONE) == 0) {
				count = count.add(BigInteger.ONE);
			}
		}
		return count;
	}

	// algoritmo de euclides para achar o maior divisor comum de a e b.
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

	// gera uma lista contendo os fatores primos de um número.
	private static ArrayList<BigInteger> getFatoresPrimos(BigInteger phi) {
		BigInteger n = phi;
		ArrayList<BigInteger> fatoresPrimos = new ArrayList<BigInteger>();
		for (BigInteger i = new BigInteger("2"); i.compareTo(n.divide(i)) <= 0; i = i.nextProbablePrime()) {
			while (n.mod(i).equals(BigInteger.ZERO)) {
				fatoresPrimos.add(i);
				n = n.divide(i);
				System.out.println("i--" + i);
				System.out.println("n--" + n);
			}
			if (n.isProbablePrime(30)) {
				break;
			}
		}
		if (n.compareTo(BigInteger.ONE) > 0) {
			System.out.println("n--" + n);
			fatoresPrimos.add(n);
		}
		return fatoresPrimos;
	}

	// gera uma lista distinta de phi divido pela lista de fatores primos.
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

	// retorna um m onde que m^(phi/P[i]) mod n, não gere nenhum valor igual a 1.
	private static BigInteger ultimaRaizPrimitiva(BigInteger numero, ArrayList<BigInteger> p) {
		// inicializa m = 3.
		BigInteger m = new BigInteger("3");
		boolean find = false;
		while (!find) {
			int i = 0;
			BigInteger result;
			// passa por toda a lista de phi/p.
			for (i = 0; i < p.size(); i++) {
				// se m^(phi/P[i]) igual a 1 para e tenta com m+1.
				result = m.modPow(p.get(i), numero);
				if (result.equals(BigInteger.ONE)) {
					m = m.add(BigInteger.ONE);
					break;
				}
			}
			// caso nenhum m^(phi/P[i]) gere um resultado igual a 1 m é uma raiz primita de n.
			if (i == p.size()) {
				find = true;
			}
		}
		return m;
	}
}
