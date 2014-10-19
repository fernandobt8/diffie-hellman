package main;

import java.math.BigInteger;

import math.Primalidade;
import math.RaizPrimitiva;

import comunicacao.ClientComunication;
import comunicacao.MessageManager;
import comunicacao.ServerComunication;

public class Main {

	// número primo e sua raiz primitiva previamente gerado, pois o algoritmo para gerar a raiz primitiva leva várias horas para números grandes.
	public static final BigInteger prime = new BigInteger("761");
	public static final BigInteger alfa = new BigInteger("6");

	public static void main(String[] args) {
		System.out.println("Digite \"w\" e enter para esperar pedido de comunicação.");
		System.out.println("Digite \"s\" e enter para começar um pedido de conexão.");
		BigInteger genPrimo = Primalidade.genPrimo(100);
		System.out.println(genPrimo);
		System.out.println(RaizPrimitiva.getRaizPrimitiva(genPrimo));
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
