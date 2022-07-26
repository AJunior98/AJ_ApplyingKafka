# Aplicando conceitos de mensageria com Kafka
O projeto é composto por duas "camadas", a produtora de mensagens e a consumidora da mesma. 

# Pacotes e classes de cada projeto:
- Produtor:
  - com.strproducer.producer.config.KafkaAdminConfig: Obtém os parametros de configuração para acesso ao servidor do kafka definidos no "application.yml"
  - com.strproducer.producer.config.StringProducerFactoryConfig: Cofiguração dos parametros dos serializadores da chave e valor e templates.
  - com.strproducer.producer.services.StringProducerService.java: Serviço para envio de mensagem para o tópico "str-topic".
 - Consumidor
    - com.strconsumer.consumer.config.StringConsumerConfig: Configuração dos parâmetros de deserialização da mensagem, listeners e validadores de mensagens.
    - com.strconsumer.consumer.custom.StrConsumerCustomListener: Implementando parâmetros de clean code, implementando uma interface anotada com o @interface.
    - com.strconsumer.consumer.listeners.StrConsumerListener: Essa é a classe que fica "ouvindo" a partição, e também está implementando os grupos de consumo.
    - com.strconsumer.consumer.exceptions.ErrorCustomHandler: Tratamento de exceção caso seja capturado um erro no recebimento da mensagem. 
## O que é Apache kafka?

O Apache Kafka é uma plataforma de software de código fonte aberto para processamento de fluxo de mensagens escrita em Scala com Java. Entre as vantagens da plataforma podemos enfatizar sua forma de unificar os seus componentes e Plugins externos, baixa latência e alto rendimento se aproximando e muito do Feed de dados em tempo real.

Conceitos que envolvem o Apache Kafka:
Primeiramente, antes de entrarmos mais a fundo sobre o Apache Kafka, devemos entender melhor alguns conceitos que envolvem esta plataforma

- Mensagens – Entenda por mensagens toda a informação que trafega sobre o Apache Kafka, seja uma frase, uma palavra, um array de bytes etc..
- Streaming – O Streaming aplicado nesta plataforma se trata de todo fluxo de uma mensagem até a captura pelo Apache Kafka e o consumo desta mensagem, onde temos o seguinte cenário: Ator -> Ação -> Gera x mensagens, ou seja, um fluxo de pedido por exemplo: Operador de caixa->Criação->Pedido e deste fluxo pode existir uma serie de mensagens.
- Tópico – Um tópico é uma forma de rotular ou categorizar uma mensagem, imagine um armário com 10 gavetas, cada gaveta pode ser um tópico e o armário é a plataforma Apache Kafka, portanto alem de categorizar ele agrupa as mensagens, uma outra analogia melhor sobre o tópico seria tabelas em banco de dados relacionais.
- Producer – O producer ou produtor é quem se conecta a uma plataforma de mensagens e envia uma ou mais mensagens para um tópico especifico.
- Consumer – O consumer ou consumidor é quem se conecta a uma plataforma de mensagens e consome uma ou mais mensagens de um tópico especifico.
- Broker – O conceito de broker na plataforma do Kafka é nada mais do que praticamente o proprio Kafka, ele é quem gerencia os tópicos, define a forma de armazenamento das mensagens, logs etc.
- Cluster – O conceito de cluster é nada mais do que um conjunto de Brokers que se comunicam entre si ou não para uma melhor escalabilidade e tolerância a falhas.
- Log file – Cada tópico armazena seus registros em um formato de log, ou seja, de forma estrutura e sequencial, o log file portanto são os arquivos que contem a informação de um tópico.
- Partitions – As partitions ou partições como o próprio nome disse é a camada de partição das mensagens dentro de um tópico, este particionamento garante a elasticidade, tolerância a falha e escalabilidade do Apache Kafka, portanto cada tópico pode ter varias partições em diferentes localidades.
- Replicas – As replicas são como partições das partições, elas tem o mesmo papel da partição mas como uma forma de redundância de determinada partição, portanto as partições Kafka são altamente disponíveis e replicadas, quando os dados do fluxo são persistentes no Kafka, eles ficam disponíveis mesmo que o aplicativo falhe
- Segmentos – Os segmentos ficam dentro das partições e segmentam as informações contidas nos logs files daquela partição, todo tópico possui sua partição e sua segmentação, a segmentação serve para gerenciar a ordenação da informação do log file bem como o tempo que ela ira ficar persistida.

Estes são os principais conceitos do Apache Kafka devem ser compreendidos, repare que este conjunto de conceitos é o que torna a arquitetura do Kafka escalável, performática e tolerante a falha.

O texto foi retirado do artigo do Airton Lira Junior 

Fonte: https://imasters.com.br/software/apache-kafka-entenda-conceitos-arquiteturas-e-componentes

## Instância do Kafka, Zookeper e Kafdrop

Abaixo as três imagens das tecnologias utilizadas para aplicação do projeto utilizando docker compose.
```
version: '3'

services:
  zookeeper:
    image: confluentinc/cp-zookeeper:latest
    networks:
      - broker-kafka
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000

  kafka:
    image: confluentinc/cp-kafka:latest
    networks:
      - broker-kafka
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka:29092,PLAINTEXT_HOST://localhost:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_HOST:PLAINTEXT
      KAFKA_INTER_BROKER_LISTENER_NAME: PLAINTEXT
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1

  kafdrop:
    image: obsidiandynamics/kafdrop:latest
    networks:
      - broker-kafka
    depends_on:
      - kafka
    ports:
      - "19000:9000"
    environment:
      KAFKA_BROKERCONNECT: kafka:29092

networks:
  broker-kafka:
    driver: bridge
```
