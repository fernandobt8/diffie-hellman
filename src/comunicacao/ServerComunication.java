package comunicacao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import main.Comunicacao;

import org.apache.commons.lang3.SerializationUtils;

import dto.DiffieHellmanDto;
import dto.Mensagem;
import dto.PublicInfoDto;

public class ServerComunication {

	private ServerSocket srvr;
	private BufferedReader br;

	public ServerComunication() {
		try {
			this.srvr = new ServerSocket(1234);
			this.br = new BufferedReader(new InputStreamReader(System.in));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void waitConetion() {
		try {
			Socket skt = this.srvr.accept();
			OutputStream outputStream = skt.getOutputStream();
			InputStream inputStream = skt.getInputStream();
			System.out.print("Conexão estabelecida!\nRecebido:\n");

			PublicInfoDto publicInfoReceived = (PublicInfoDto) SerializationUtils.deserialize(inputStream);
			publicInfoReceived.print();

			DiffieHellmanDto hellmanDto = new DiffieHellmanDto(publicInfoReceived);
			hellmanDto.print();
			hellmanDto.generateK(publicInfoReceived.getPublicKey());

			PublicInfoDto myPublicInfo = hellmanDto.getPublicInfoDto();
			SerializationUtils.serialize(myPublicInfo, outputStream);
			outputStream.flush();

			this.listen(inputStream, hellmanDto);
			while (true) {
				System.out.println("Digite uma mensagem a enviar, ou digite \"sair\" para Sair.\n");
				String mensagem = Comunicacao.readConsole(this.br);
				if (mensagem.equals("sair")) {
					break;
				} else {
					SerializationUtils.serialize(new Mensagem(myPublicInfo.getPublicKey(), mensagem), outputStream);
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
