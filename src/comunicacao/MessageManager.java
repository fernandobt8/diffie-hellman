package comunicacao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import main.Main;
import utils.SerializationUtils;
import dto.DiffieHellmanDto;
import dto.Message;

public class MessageManager implements Runnable {

	private InputStream inputStream;
	private DiffieHellmanDto hellmanDto;
	private boolean running;
	private OutputStream outputStream;
	private BufferedReader br;

	public MessageManager(InputStream inputStream, OutputStream outputStream, DiffieHellmanDto hellmanDto) {
		this.inputStream = inputStream;
		this.outputStream = outputStream;
		this.hellmanDto = hellmanDto;
		this.running = true;
		this.br = new BufferedReader(new InputStreamReader(System.in));
	}

	public void terminate() {
		this.running = false;
	}

	@Override
	public void run() {
		while (this.running) {
			Object deserialize = SerializationUtils.deserialize(this.inputStream);
			if (deserialize instanceof Message) {
				Message men = (Message) deserialize;
				if (this.hellmanDto.check(men.getPublicKey())) {
					System.out.println("\nMensagem valida recebida:\nChave Publica: " + men.getPublicKey() + "\nK: " + this.hellmanDto.getK() + "\nMensagem: " + men.getDado()
							+ "\n");
				} else {
					System.out.println("\nMensagem invalida recebida chave publica: " + men.getPublicKey());
				}
			}
		}
	}

	public void sendMessage() throws IOException {
		while (true) {
			System.out.println("\nDigite uma mensagem a enviar, ou digite \"sair\" para Sair.");
			String mensagem = Main.readConsole(this.br);
			if (mensagem.equals("sair")) {
				break;
			} else {
				SerializationUtils.serialize(new Message(this.hellmanDto.getPublicInfoDto().getPublicKey(), mensagem), this.outputStream);
				System.out.println("Mensagem enviada!");
			}
		}
	}

}
