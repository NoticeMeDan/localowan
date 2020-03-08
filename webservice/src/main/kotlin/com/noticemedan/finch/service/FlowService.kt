package com.noticemedan.finch.service

import com.noticemedan.finch.dao.FlowDao
import com.noticemedan.finch.dto.FlowInfo
import com.noticemedan.finch.entity.Flow
import com.noticemedan.finch.exception.FlowNotFound
import com.noticemedan.finch.util.DtoFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FlowService (
	private val flowDao: FlowDao,
	private val dtoFactory: DtoFactory
) {

	@Transactional
	fun createFlow (source: FlowInfo): FlowInfo {
		return dtoFactory.toInfo(flowDao.save(Flow(source.name, source.applicationId)))
	}

	@Transactional
	fun getFlows (): List<FlowInfo> {
		return flowDao.findAll()
				.map(dtoFactory::toInfo)
				.sortedBy { x -> x.name }
	}

	@Transactional
	fun getFlow (flowId: Long): FlowInfo {
		return flowDao.findById(flowId)
				.map(dtoFactory::toInfo)
				.orElseThrow { FlowNotFound() }
	}
}