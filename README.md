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

💻️ Docker

---------------------------------

## Referências de estudo:

[How To Monitor a Spring Boot App With Prometheus and Grafana](https://betterprogramming.pub/how-to-monitor-a-spring-boot-app-with-prometheus-and-grafana-22e2338f97fc)

[Spring Boot with Docker](https://spring.io/guides/gs/spring-boot-docker/)

[Dashboard de métricas com Spring Boot Actuator, Prometheus e Grafana](https://www.youtube.com/watch?v=K_EI1SxVQ5Q)

[Ferramentas de Observabilidade](https://github.com/liliannss/observabilidade)

----------------------------------
### Comandos:

<b>Gerar container spring:<b> 

1 - ``mvn install -DskipTests ``

2 - ``docker build -t springio/gs-spring-boot-docker .``

----------------------------------

# Sobre a cofiguração:

### Spring
Logs: Os logs da aplicação são exportados para a pasta scripts/monitoring/logs;

Docker: Foi configurado o arquivo Dockerfile para conteinerização do Spring;
 
### Prometheus

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




 