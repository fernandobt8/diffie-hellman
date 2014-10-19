package comunicacao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import utils.SerializationUtils;
import dto.DiffieHellmanDto;
import dto.Message;

public class MessageManager implements Runnable {

	private InputStream inputStream;
	private DiffieHellmanDto hellmanDto;
	private boolean running;
	private OutputStream outputStream;

	public MessageManager(InputStream inputStream, OutputStream outputStream, DiffieHellmanDto hellmanDto) {
		this.inputStream = inputStream;
		this.outputStream = outputStream;
		this.hellmanDto = hellmanDto;
		this.running = true;
	}

	public void terminate() {
		this.running = false;
	}

	// aguarda receber uma mensagem, quando recebe verifica se o Y contido na mensagem gera um k previamente estabelecido.
	@Override
	public void run() {
		while (this.running) {
			Object deserialize = SerializationUtils.deserialize(this.inputStream);
			if (deserialize instanceof Message) {
				Message men = (Message) deserialize;
				if (this.hellmanDto.check(men.getPublicKey())) {
					MessageManager.print("Mensagem valida recebida:\nY" + this.hellmanDto.getName() + ": " + men.getPublicKey() + "\nK: " + this.hellmanDto.getK() + "\nMensagem: "
							+ men.getDado() + "\n");
					MessageManager.print("Digite uma mensagem e enter para enviar, ou digite \"sair\" e enter para Sair.");
				} else {
					MessageManager.print("Mensagem invalida recebida Y" + this.hellmanDto.getName() + ": " + men.getPublicKey());
				}
			}
		}
	}

	// envia um mensagem com meu Y.
	public void sendMessage() throws IOException {
		while (true) {
			MessageManager.print("Digite uma mensagem e enter para enviar, ou digite \"sair\" e enter para Sair.");
			String mensagem = readConsole();
			if (mensagem.equals("sair")) {
				break;
			} else {
				SerializationUtils.serialize(new Message(this.hellmanDto.getPublicKey(), mensagem), this.outputStream);
				MessageManager.print("Mensagem enviada!");
			}
		}
	}

	public static String readConsole() {
		try {
			return new BufferedReader(new InputStreamReader(System.in)).readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void print(String message) {
		System.out.println(" \n" + new SimpleDateFormat("HH:mm:ss.S a").format(new Date()) + "\n" + message);
	}
}
