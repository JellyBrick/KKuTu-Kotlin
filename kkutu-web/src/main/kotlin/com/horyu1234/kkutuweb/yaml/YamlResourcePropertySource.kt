package com.horyu1234.kkutuweb.yaml

import org.springframework.core.env.PropertiesPropertySource
import org.springframework.core.io.support.EncodedResource

internal class YamlResourcePropertySource
constructor(name: String, resource: EncodedResource) : PropertiesPropertySource(name, YamlPropertiesProcessor(resource.resource).createProperties())