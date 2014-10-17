package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;

import math.RaizPrimitiva;

import comunicacao.ServerComunication;

import dto.DiffieHellmanDto;

public class Comunicacao {

	public static void main(String[] args) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Digite \"E\" para esperar pedido comunicação.");
		System.out.println("Digite \"S\" para começar um pedido de conexão.");
		String console = readConsole(br);
		BigInteger numero = new BigInteger(console);
		System.out.println(RaizPrimitiva.getRaizPrimitiva(numero));
		if (console.equals("E")) {
			ServerComunication serverComunication = new ServerComunication();
			serverComunication.waitConetion();
		} else if (console.equals("S")) {
			try {
				System.out.println("Digite endereço IP para começar um pedido de conexão.");
				console = readConsole(br);
				Socket skt = new Socket(console, 1234);
				InputStream inputStream = skt.getInputStream();
				OutputStream outputStream = skt.getOutputStream();
				DiffieHellmanDto hellmanDto = new DiffieHellmanDto();
				// outputStream.write(seria);
			} catch (Exception e) {
				e.printStackTrace();
			}
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
