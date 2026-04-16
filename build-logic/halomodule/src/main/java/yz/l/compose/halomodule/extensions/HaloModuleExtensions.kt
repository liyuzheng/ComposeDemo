package yz.l.compose.halomodule.extensions

import org.gradle.api.model.ObjectFactory
import org.gradle.api.provider.ListProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Nested
import javax.inject.Inject

/**
 * desc:
 * createed by liyuzheng on 2022/7/25 14:54
 */
abstract class HaloModuleExtensions @Inject constructor(objects: ObjectFactory) {
    @get:Nested
    val logConfig: LogConfigExtensions = objects.newInstance(LogConfigExtensions::class.java)

    // 是否开启 AAR 模式
    val useAar: Property<Boolean> = objects.property(Boolean::class.java)

    // 强制使用源码的模块清单
    val excludeModules: ListProperty<String> = objects.listProperty(String::class.java)

    // 本地 Maven 仓库路径
    val localRepoPath: Property<String> = objects.property(String::class.java)
}