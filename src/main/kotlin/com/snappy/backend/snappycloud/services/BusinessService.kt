package com.snappy.backend.snappycloud.services

import com.snappy.backend.snappycloud.models.Business
import com.snappy.backend.snappycloud.repositories.BusinessRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import javax.persistence.EntityNotFoundException
import javax.transaction.Transactional

@Service
@Transactional
class BusinessService(
        private val businessRepository: BusinessRepository,
) : IGenericService<Business, Long> {

    override fun findAll(): List<Business> = this.businessRepository.findAll()

    override fun findById(id: Long): Business? = this.businessRepository.findByIdOrNull(id)

    override fun save(t: Business): Business = this.businessRepository.save(t)

    override fun update(t: Business): Business {
        return if (this.businessRepository.existsById(t.id))
            this.businessRepository.save(t)
        else throw EntityNotFoundException("${t.id} does not exist")
    }

    override fun deleteById(id: Long): Business {
        return this.findById(id).apply {
            this@BusinessService.businessRepository.deleteById(id)
        } ?: throw EntityNotFoundException("$id does not exist")
    }

}