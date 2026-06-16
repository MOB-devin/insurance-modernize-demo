package pl.altkom.asc.lab.micronaut.poc.dashboard.infrastructure.adapters.elastic.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import jakarta.inject.Singleton;

import io.micronaut.context.annotation.Factory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Factory
@RequiredArgsConstructor
public class ElasticConfig {

    private final ElasticSearchSettings elasticSearchSettings;

    @Singleton
    public ElasticsearchClient elasticsearchClient() {
        RestClient restClient = RestClient.builder(
                new HttpHost(elasticSearchSettings.getHost(), elasticSearchSettings.getPort())
        ).setRequestConfigCallback(config -> config
                .setConnectTimeout(elasticSearchSettings.getConnectionTimeout())
                .setConnectionRequestTimeout(elasticSearchSettings.getConnectionRequestTimeout())
                .setSocketTimeout(elasticSearchSettings.getSocketTimeout())
        ).build();

        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        return new ElasticsearchClient(transport);
    }

}
