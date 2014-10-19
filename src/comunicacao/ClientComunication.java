package comunicacao;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;

import utils.SerializationUtils;
import dto.DiffieHellmanDto;

public class ClientComunication {

	private Socket skt;

	public ClientComunication(String ip) {
		try {
			this.skt = new Socket(ip, 1234);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void initConection() {
		try {
			InputStream inputStream = this.skt.getInputStream();
			OutputStream outputStream = this.skt.getOutputStream();

			// gera um Xa aletório Xa < q e um Ya alfa^Xa mod q
			DiffieHellmanDto hellmanDto = new DiffieHellmanDto("a");
			hellmanDto.print();

			// envia seu Ya
			SerializationUtils.serialize(hellmanDto.getPublicKey(), outputStream);

			// recebe o Yb
			BigInteger publicKeyReceived = (BigInteger) SerializationUtils.deserialize(inputStream);
			MessageManager.print("Recebido Yb: " + publicKeyReceived);

			// gera um k baseado no Yb recebido terminando o acordo de chaves.
			hellmanDto.generateK(publicKeyReceived);

			// inicia um nova tread para ficar recebendo mensagens e outra para enviar mensagens com seu Ya junto.
			MessageManager messagemManager = new MessageManager(inputStream, outputStream, hellmanDto);
			Thread listen = new Thread(messagemManager);
			listen.start();
			messagemManager.sendMessage();

			inputStream.close();
			outputStream.close();
			messagemManager.terminate();
			listen.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
