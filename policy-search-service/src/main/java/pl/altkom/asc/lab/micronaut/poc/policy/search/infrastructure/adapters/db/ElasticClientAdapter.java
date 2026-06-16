package pl.altkom.asc.lab.micronaut.poc.policy.search.infrastructure.adapters.db;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import jakarta.inject.Singleton;
import java.io.IOException;

@Singleton
@Slf4j
public class ElasticClientAdapter {

    private final ElasticsearchClient client;
    private final ElasticSearchSettings elasticSearchSettings;

    public ElasticClientAdapter(ElasticSearchSettings elasticSearchSettings) {
        this.elasticSearchSettings = elasticSearchSettings;
        this.client = buildClient();
    }

    <T> IndexResponse index(IndexRequest<T> indexRequest) throws IOException {
        return client.index(indexRequest);
    }

    public <T> SearchResponse<T> search(SearchRequest searchRequest, Class<T> clazz) throws IOException {
        return client.search(searchRequest, clazz);
    }

    private ElasticsearchClient buildClient() {
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
