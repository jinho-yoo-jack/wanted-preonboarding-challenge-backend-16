package com.wanted.preonboarding.ticket.presentation

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import jakarta.persistence.EntityManager
import jakarta.persistence.EntityManagerFactory
import jakarta.persistence.PersistenceUnit
import jakarta.persistence.Query
import org.intellij.lang.annotations.Language
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter

@ActiveProfiles("test")
@SpringBootTest
class IntegrationTest {
    @PersistenceUnit
    private lateinit var entityManagerFactory: EntityManagerFactory

    @Autowired
    private lateinit var ctx: WebApplicationContext

    private lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setup() {
        mockMvc =
            MockMvcBuilders
                .webAppContextSetup(ctx)
                .addFilters<DefaultMockMvcBuilder?>(CharacterEncodingFilter("UTF-8", true))
                .build()
    }

    @AfterEach
    fun tearDown() {
        deleteAll("performance")
        deleteAll("reservation")
        deleteAll("performance_seat_info")
    }

    fun request(
        method: HttpMethod,
        url: String,
        body: Any? = null,
        params: MultiValueMap<String, String> = LinkedMultiValueMap(),
    ): ResultActions {
        val req =
            MockMvcRequestBuilders.request(method, url)
                .params(params)
                .characterEncoding("UTF-8")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)

        body?.let {
            req.content(objectMapper().writeValueAsString(it))
        }
        return mockMvc.perform(req)
    }

    fun <T> save(entity: T): T =
        runWithEntityManager(readonly = false) {
            it.persist(entity)
            entity
        }

    private fun deleteAll(tableName: String) {
        runWithEntityManager(readonly = false) {
            it.createNativeQuery("DELETE FROM $tableName").executeUpdate()
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> findOne(
        @Language("JPAQL") qlString: String,
        params: Map<String, Any> = emptyMap(),
    ): T =
        runWithEntityManager {
            val query = it.createQuery(qlString, Any::class.java)
            setParams(query, params)
            query.singleResult as T
        }

    @Suppress("UNCHECKED_CAST")
    fun <T> findAll(
        @Language("JPAQL") qlString: String,
        params: Map<String, Any> = emptyMap(),
    ): List<T> =
        runWithEntityManager {
            val query = it.createQuery(qlString, Any::class.java)
            setParams(query, params)
            query.resultList as List<T>
        }

    private fun setParams(
        query: Query,
        params: Map<String, Any>,
    ) {
        params.forEach { (key, value) ->
            query.setParameter(key, value)
        }
    }

    private fun <T> runWithEntityManager(
        readonly: Boolean = true,
        block: (em: EntityManager) -> T,
    ): T {
        val em = entityManagerFactory.createEntityManager()
        val result =
            if (readonly) {
                runCatching {
                    block(em)
                }
            } else {
                val transaction = em.transaction
                transaction.begin()
                runCatching {
                    block(em)
                }.onFailure {
                    transaction.rollback()
                }.onSuccess {
                    transaction.commit()
                }
            }
        em.close()
        return result.getOrThrow()
    }

    private fun objectMapper(): ObjectMapper {
        return Jackson2ObjectMapperBuilder()
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .featuresToDisable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS)
            .build()
    }
}
