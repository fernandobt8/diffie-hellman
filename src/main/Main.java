package main;

import java.math.BigInteger;

import comunicacao.ClientComunication;
import comunicacao.MessageManager;
import comunicacao.ServerComunication;

public class Main {

	// número primo e sua raiz primitiva previamente gerado, pois o algoritmo para gerar a raiz primitiva leva várias horas para números grandes.
	public static final BigInteger prime = new BigInteger("14501674483077440933515249410481686226842322350312669366360308844975622867692369300228716640175357247");
	public static final BigInteger alfa = new BigInteger("5");

	public static void main(String[] args) {
		System.out.println("Digite \"w\" e enter para esperar um pedido de conexão.");
		System.out.println("Digite \"s\" e enter para começar um pedido de conexão.");
		String console = MessageManager.readConsole();
		System.out.println("q: " + prime);
		System.out.println("alfa: " + alfa);
		if (console.equals("w")) {
			// espera receber um Ya para iniciar uma conexão
			ServerComunication serverComunication = new ServerComunication();
			serverComunication.waitConetion();
		} else if (console.equals("s")) {
			// estabele uma conexão e envia seu Ya.
			System.out.println("Digite endereço IP para começar um pedido de conexão.");
			console = MessageManager.readConsole();
			ClientComunication clientComunication = new ClientComunication(console);
			clientComunication.initConection();
		} else {
			System.out.println("Opção inválida!!");
		}
	}

}
