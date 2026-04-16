package yz.l.compose.halomodule.utils

import org.gradle.api.Project
import org.gradle.kotlin.dsl.create
import yz.l.compose.halomodule.constants.LogConstant.LOG_OUT_DIR
import yz.l.compose.halomodule.extensions.LogConfigExtensions
import java.io.File
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

/**
 * desc:
 * created by liyuzheng on 2026/4/14 19:59
 */
/**
 * desc: 输出log
 * 配置:logConfig {
 *      print true //是否在控制台打印日志
 *      reportToFile true //是否输出文字日志   路径 project/app/build/outputs/halo_module/logs
 * }
 * createed by liyuzheng on 2022/7/25 14:44
 */
object LogUtil {
    private const val MAX_LOG_FILE_COUNT = 5
    private var logConfigExtensions: LogConfigExtensions? = null
    private var logFilePath: String? = null //当次写入日志的文件
    private var rootDir: String = ""

    /**
     * 初始化日志组件
     */
    fun init(project: Project) {
        logConfigExtensions = project.extensions.create<LogConfigExtensions>("logConfig")
        this.logConfigExtensions?.print?.convention(true)
        this.logConfigExtensions?.reportToFile?.convention(true)
        project.afterEvaluate {
            this@LogUtil.rootDir = project.projectDir.absolutePath + LOG_OUT_DIR
            clearLogFolder()
            if (logConfigExtensions?.reportToFile?.get() == true)
                buildOutputFile()
        }
    }

    //打印日志
    fun v(log: String) {
        if (enablePrint())
            println("haloModule:${currTime()} -> $log")
        reportToFile(log)
    }

    /**
     * 创建日志输出文件
     */
    private fun buildOutputFile() {
        val fileName = "${currTime()}.log"
        val file = File(rootDir)
        file.mkdirs()
        if (file.exists()) {
            val logFile = File(rootDir + fileName)
            v("logfile-> ${logFile.absolutePath}")
            logFile.createNewFile()
            logFilePath = if (!logFile.exists()) {
                null
            } else {
                logFile.absolutePath
            }
            v("logfile-> $logFilePath")
        }
    }

    /**
     * 获取当前时间戳，用于输出日志
     */
    @Suppress("SimpleDateFormat")
    private fun currTime(): String {
        val df: DateFormat = SimpleDateFormat("yyyy-MM-dd HH_mm_ss")
        return df.format(Date())
    }

    //判断是否开启了日志
    private fun enablePrint(): Boolean {
        return logConfigExtensions?.print?.get() == true
    }

    //将日志输出到文件
    private fun reportToFile(log: String) {
        if (logConfigExtensions?.reportToFile?.get() == false) return
        logFilePath?.run {
            val logFile = File(this)
            if (logFile.exists())
                logFile.appendText(currTime() + "->" + log + "\n")
        }
    }

    private fun clearLogFolder() {
        if (rootDir.isBlank()) return
        val folder = File(rootDir)
        if (folder.exists() && folder.isDirectory) {
            val logSize = folder.listFiles()?.size ?: 0
            if (logSize > MAX_LOG_FILE_COUNT) {
                folder.listFiles()?.forEachIndexed { index, file ->
                    if (index < logSize - MAX_LOG_FILE_COUNT + 1) {
                        file.delete()
                    }
                }
            }
        }
    }
}