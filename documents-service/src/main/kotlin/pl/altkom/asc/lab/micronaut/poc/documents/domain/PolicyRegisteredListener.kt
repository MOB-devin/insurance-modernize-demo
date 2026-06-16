package pl.altkom.asc.lab.micronaut.poc.documents.domain

import io.micronaut.kafka.annotation.KafkaListener
import io.micronaut.kafka.annotation.OffsetReset
import io.micronaut.kafka.annotation.Topic
import io.micronaut.http.annotation.Header
import pl.altkom.asc.lab.micronaut.poc.policy.service.api.v1.events.PolicyRegisteredEvent

@KafkaListener(clientId = "policy-registered-listener", offsetReset = OffsetReset.EARLIEST)
@Header(name = "Content-Type", value = "application/json")
class PolicyRegisteredListener(private val policyDocumentRepository: PolicyDocumentRepository,
                               private val reportGenerator: ReportGenerator) {

    @Topic("policy-registered")
    fun onPolicyRegistered(event: PolicyRegisteredEvent) {
        val generatedDocument = reportGenerator.generate(event)

        val policyDocument = PolicyDocument(
                id = null,
                policyNumber = event.policy.number,
                bytes = generatedDocument?.body()!!
        )

        policyDocumentRepository.add(policyDocument)
    }
}
