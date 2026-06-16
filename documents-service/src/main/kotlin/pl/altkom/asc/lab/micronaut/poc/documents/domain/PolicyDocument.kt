package pl.altkom.asc.lab.micronaut.poc.documents.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id


@Entity
data class PolicyDocument(
        @Id
        @GeneratedValue
        val id: Long? = -1,
        val policyNumber: String = "",
        @Column(columnDefinition = "BINARY(200000)")
        val bytes: ByteArray = ByteArray(1)
)