package com.horyu1234.kkutuweb.yaml

import org.springframework.core.env.PropertySource
import org.springframework.core.io.Resource
import org.springframework.core.io.support.EncodedResource
import org.springframework.core.io.support.PropertySourceFactory
import org.springframework.core.io.support.ResourcePropertySource
import org.springframework.util.StringUtils

class YamlPropertySourceFactory : PropertySourceFactory {
    override fun createPropertySource(name: String?, resource: EncodedResource): PropertySource<*> {
        val filename = resource.resource.filename
        if (filename != null && filename.endsWith(YML_FILE_EXTENSION)) {
            return if (name != null) YamlResourcePropertySource(name, resource) else YamlResourcePropertySource(getNameForResource(resource.resource), resource)
        }

        return if (name != null) ResourcePropertySource(name, resource) else ResourcePropertySource(resource)
    }

    private fun getNameForResource(resource: Resource): String {
        var name = resource.description
        if (!StringUtils.hasText(name)) {
            name = resource.javaClass.simpleName + "@" + System.identityHashCode(resource)
        }

        return name
    }

    companion object {
        private val YML_FILE_EXTENSION = ".yml"
    }
}