# 👀 Observability Api

## 🎮️ Sobre
Este projeto visa estudar a observabilidade de uma aplicação rest.

## 👨‍💻️ Tecnogias utilizadas
O projeto foi desenvolvido utilizando as seguintes tecnologias:

💻️ Spring 3.0.1

    Api (JDK: 17)

💻️ Prometheus

    Monitoramento e alertas

💻️ Grafana

    Análise e Monitoramento

💻️ Loki 

    Agregação de Registros (logs)

💻️ K6
    
    Teste de performance

💻️ ZipKin

    Trace das transações

💻️ Docker
    
    Virtualizador de aplicações

---------------------------------

## Referências de estudo:

[How To Monitor a Spring Boot App With Prometheus and Grafana](https://betterprogramming.pub/how-to-monitor-a-spring-boot-app-with-prometheus-and-grafana-22e2338f97fc)

[Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)

[Dashboard de métricas com Spring Boot Actuator, Prometheus e Grafana](https://www.youtube.com/watch?v=K_EI1SxVQ5Q)

[Ferramentas de Observabilidade](https://github.com/liliannss/observabilidade)

[Zipkin](https://springbootlearning.medium.com/using-micrometer-to-trace-your-spring-boot-app-1fe6ff9982ae)

----------------------------------
### Comandos:

<b>Gerar container spring:<b> 

1 - ``mvn install ``

2 - ``docker build -t springio/gs-spring-boot-docker .`` (deprecated)

OU

    mvn spring-boot:build-image
    docker tag observability-api:0.0.1-SNAPSHOT rhdesouza/observability-api:0.0.1-SNAPSHOT
    docker push rhdesouza/observability-api:0.0.1-SNAPSHOT

----------------------------------

# Sobre a cofiguração:

## Spring
Logs: Os logs da aplicação são exportados para a pasta scripts/monitoring/logs;

Docker: Foi configurado o arquivo Dockerfile para conteinerização do Spring;
 
## Prometheus
Ferramenta da monitoramento e alertas.

Arquivo de configuração: scripts/monitoring/prometheus/prometheus.yaml

<strong>Configuração Docker:</strong> 

    prometheus:
        image: prom/prometheus:v2.38.0
        networks:
            - grafana_network
        container_name: prometheus-container
        volumes:
            - ./prometheus/:/etc/prometheus/
        command:
            - '--config.file=/etc/prometheus/prometheus.yaml'
        ports:
            - "9090:9090"
        restart: always


## MySql

<strong>Configuração Docker:</strong>

    mysql:
        container_name: mysql_observability
        image: mysql:latest
        networks:
            - observability_network
        environment:
            MYSQL_ROOT_PASSWORD: root
            MYSQL_DATABASE: "observability"
        ports:
            - 3306:3306

## Grafana
Grafana é uma aplicação web de análise.

<strong>Configuração Docker:</strong>

    grafana:
        image: grafana/grafana
        user: "$UID:$GID"
        networks:
            - observability_network
        container_name: grafana-container
        depends_on:
            - prometheus
            - loki
        ports:
            - "3000:3000"
        volumes:
            - ./grafana/:/var/lib/grafana
        environment:
            - GF_SECURITY_ADMIN_PASSWORD=admin
            - GF_SERVER_DOMAIN=localhost

## Loki
O Loki tem como objetivo adicionar rastreabilidade entre métricas, logs e rastreio.

<strong>Configuração Docker:</strong>

    loki:
        image: grafana/loki:2.0.0
        container_name: loki-service
        ports:
            - "3100:3100"
        command:
            - -config.file=/etc/loki/local-config.yaml
        networks:
            - observability_network

## Promtail
Promtail é um agente que envia o conteúdo de logs locais para uma instância Grafana Loki.

Arquivo de configuração: scripts/monitoring/promtail/config.yaml

<strong>Configuração Docker:</strong>

    promtail:
        image: grafana/promtail:2.0.0
        container_name: promtail-service
        command: -config.file=./etc/promtail/config.yaml
        volumes:
            - ./logs/:/var/log/
            - ./promtail/config.yaml:/etc/promtail/config.yaml
            - ./tmp:/tmp
        ports:
            - "9080:9080"
        depends_on:
            - loki
        networks:
            - observability_network

## Zipkin
Zipkin é um sistema de rastreamento distribuído. Ele ajuda a coletar dados de tempo necessários para solucionar problemas de latência em arquiteturas de serviço.

<strong>Configuração Docker:</strong>

    zipkin:
        image: openzipkin/zipkin
        container_name: zipkin-service
        ports:
            - 9411:9411
        networks:
            - observability_network