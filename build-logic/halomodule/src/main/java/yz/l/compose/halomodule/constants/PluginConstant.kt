package yz.l.compose.halomodule.constants

import java.io.File

/**
 * desc:
 * createed by liyuzheng on 2022/7/27 10:47
 */
object PluginConstant {
    //总体配置名称
    const val HALO_MODULE_EXTENSIONS = "haloModule"
    const val APP_PLUGIN_NAME = "com.android.application"
    const val LIBRARY_PLUGIN_NAME = "com.android.library"
    const val PACKAGE_PATH = "/com/starmaker/halomodule/"
    const val MAVEN_CONFIG_FILE_NAME = "maven-metadata.xml"
    //输出、保存的基础目录 module/build/outputs/halo_module
    var OUT_PUT_DIR = "${File.separator}build${File.separator}" +
            "outputs${File.separator}halo_module${File.separator}"

    var DEPENDENCY_NAMES = arrayOf(
        "api",
        "implementation",
        "compile"
    )
}