package com.horyu1234.kkutuweb.config

import com.horyu1234.kkutuweb.KKuTuWebApplication
import com.horyu1234.kkutuweb.yaml.YamlPropertySourceFactory
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource(value = ["file:\${app.home}/config/${KKuTuWebApplication.SETTING_FILE_NAME}.yml"], factory = YamlPropertySourceFactory::class)
class ExternalSettingConfig
