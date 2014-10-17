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

			PublicInfoDto publicInfo = (PublicInfoDto) SerializationUtils.deserialize(inputStream);
			publicInfo.print();

			DiffieHellmanDto hellmanDto = new DiffieHellmanDto(publicInfo);
			hellmanDto.print();
			PublicInfoDto myPublicInfo = hellmanDto.getPublicInfoDto();
			SerializationUtils.serialize(myPublicInfo, outputStream);

			while (true) {
				System.out.println("Digite uma mensagem a enviar, ou digite \"sair\" para Sair.");
				String mensagem = Comunicacao.readConsole(this.br);
				if (mensagem.equals("sair")) {
					break;
				} else {
					SerializationUtils.serialize(new Mensagem(publicInfo.getPublicKey(), mensagem), outputStream);
					System.out.println("Mensagem enviada!");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
