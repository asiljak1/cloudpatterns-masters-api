package com.unsa.etf.cloudpatternsmastersapi

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication@EnableCaching
class CloudpatternsMastersApiApplication {
}

fun main(args: Array<String>) {
    runApplication<CloudpatternsMastersApiApplication>(*args)
}
