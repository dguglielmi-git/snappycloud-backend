package com.snappy.backend.snappycloud

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class,DataSourceAutoConfiguration::class])
class SnappycloudApplication

fun main(args: Array<String>) {
	runApplication<SnappycloudApplication>(*args)
}
