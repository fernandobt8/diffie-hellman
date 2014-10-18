package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import comunicacao.ClientComunication;
import comunicacao.ServerComunication;

public class Comunicacao {

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Digite \"E\" para esperar pedido comunicação.");
		System.out.println("Digite \"S\" para começar um pedido de conexão.");
		String console = readConsole(br);
		if (console.equals("E")) {
			ServerComunication serverComunication = new ServerComunication();
			serverComunication.waitConetion();
		} else if (console.equals("S")) {
			System.out.println("Digite endereço IP para começar um pedido de conexão.");
			console = Comunicacao.readConsole(br);
			ClientComunication clientComunication = new ClientComunication(console);
			clientComunication.initConection();
		}
	}

	public static String readConsole(BufferedReader br) {
		try {
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
