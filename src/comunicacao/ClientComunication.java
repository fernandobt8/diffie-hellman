package comunicacao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

import main.Comunicacao;

import org.apache.commons.lang3.SerializationUtils;

import dto.DiffieHellmanDto;
import dto.Mensagem;
import dto.PublicInfoDto;

public class ClientComunication {

	private Socket skt;
	private BufferedReader br;

	public ClientComunication(String ip) {
		try {
			this.skt = new Socket(ip, 1234);
			this.br = new BufferedReader(new InputStreamReader(System.in));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initConection() {
		try {
			InputStream inputStream = this.skt.getInputStream();
			OutputStream outputStream = this.skt.getOutputStream();

			DiffieHellmanDto hellmanDto = new DiffieHellmanDto();
			hellmanDto.print();

			SerializationUtils.serialize(hellmanDto.getPublicInfoDto(), outputStream);
			outputStream.flush();

			PublicInfoDto publicInfoReceived = (PublicInfoDto) SerializationUtils.deserialize(inputStream);
			System.out.println("Recebido: \n");
			publicInfoReceived.print();

			hellmanDto.generateK(publicInfoReceived.getPublicKey());
			this.listen(inputStream, hellmanDto);

			while (true) {
				System.out.println("Digite uma mensagem a enviar, ou digite \"sair\" para Sair.\n");
				String mensagem = Comunicacao.readConsole(this.br);
				if (mensagem.equals("sair")) {
					break;
				} else {
					SerializationUtils.serialize(new Mensagem(hellmanDto.getPublicInfoDto().getPublicKey(), mensagem), outputStream);
					outputStream.flush();
					System.out.println("Mensagem enviada!\n");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void listen(final InputStream inputStream, final DiffieHellmanDto hellmanDto) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					Object deserialize = SerializationUtils.deserialize(inputStream);
					if (deserialize instanceof Mensagem) {
						Mensagem men = (Mensagem) deserialize;
						if (hellmanDto.check(men.getPublicKey())) {
							System.out.println("Mensagem valida recebida, K= " + hellmanDto.getK() + "\nMensagem: " + men.getDado() + "\n");
						} else {
							System.out.println("Mensagem invalida recebida chave publica: " + men.getPublicKey());
						}
					}
				}
			}
		}).run();
	}
}
