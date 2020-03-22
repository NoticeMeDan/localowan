package com.noticemedan.finch.service

import com.noticemedan.finch.TestConfig
import com.noticemedan.finch.dto.FlowInfo
import com.noticemedan.finch.exception.FlowNameAlreadyInUse
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@RunWith(SpringRunner::class)
@SpringBootTest(classes = [TestConfig::class])
class FlowServiceTest {

	@Autowired
	private lateinit var flowService: FlowService

	@Test
	fun createFlow () {
		val name = FlowInfo("My cool flow", "my-cool-app")

		val subject = flowService.createFlow(name)

		assertThat(subject).isNotNull
		assertThat(subject.id).isNotNull() // If i access the isNotNull field instead of running the function, it crashes due to type inference issues
		assertThat(subject.name).isEqualTo(name.name)
		assertThat(subject.applicationId).isEqualTo(name.applicationId)
	}

	@Test
	fun getFlows () {
		val flow1 = FlowInfo("B comes last", "app-1")
		val flow2 = FlowInfo("A comes first", "app-2")

		flowService.createFlow(flow1)
		flowService.createFlow(flow2)

		val subject = flowService.getFlows(0)

		assertThat(subject).isNotNull
		assertThat(subject.totalPages).isEqualTo(1)
		assertThat(subject.pageData.size).isEqualTo(2)
	}

	@Test
	fun getFlow () {
		val flow = FlowInfo("Yee boi", "app-42")

		val createdFlow = flowService.createFlow(flow)

		val subject = flowService.getFlow(createdFlow.id!!)

		assertThat(subject).isNotNull
		assertThat(subject.id).isEqualTo(createdFlow.id!!)
		assertThat(subject.name).isEqualTo(createdFlow.name)
		assertThat(subject.applicationId).isEqualTo(createdFlow.applicationId)
	}

	@Test
	fun cannotCreateFlowsWithDuplicateNames () {
		val flow1 = FlowInfo("We have the same name", "app-42")
		val flow2 = FlowInfo("We have the same name", "app-42")

		assertDoesNotThrow { flowService.createFlow(flow1) }
		assertThrows<FlowNameAlreadyInUse> { flowService.createFlow(flow2) }
	}
}
