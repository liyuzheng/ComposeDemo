package yz.l.compose.halomodule.constants

import java.io.File

/**
 * desc:
 * createed by liyuzheng on 2022/7/27 10:54
 */
object LogConstant {
    //日志配置的名称
    const val LOG_CONFIG_EXTENSIONS = "logConfig"

    //日志输出路径
    var LOG_OUT_DIR = "${PluginConstant.OUT_PUT_DIR}logs${File.separator}"

    //app模块插件配置名称
    const val APP_PLUGIN_NAME = PluginConstant.APP_PLUGIN_NAME
}