package yz.l.compose.halomodule.extensions

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.Property
import javax.inject.Inject

/**
 * desc: 日志配置
 * createed by liyuzheng on 2022/7/25 14:51
 */
abstract class LogConfigExtensions @Inject constructor(objects: ObjectFactory) {
    var print: Property<Boolean> = objects.property(Boolean::class.java)
    var reportToFile: Property<Boolean> = objects.property(Boolean::class.java)
}
