package com.horyu1234.kkutuweb.yaml

import org.springframework.beans.factory.config.YamlProcessor
import org.springframework.core.CollectionFactory
import org.springframework.core.io.Resource
import java.io.FileNotFoundException
import java.io.IOException
import java.util.*

class YamlPropertiesProcessor
internal constructor(resource: Resource) : YamlProcessor() {
    init {
        if (!resource.exists()) {
            throw FileNotFoundException()
        }

        this.setResources(resource)
    }

    fun createProperties(): Properties {
        val result = CollectionFactory.createStringAdaptingProperties()
        process { properties, _ -> result.putAll(properties) }

        return result
    }
}