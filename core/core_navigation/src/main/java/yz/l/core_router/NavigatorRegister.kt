package yz.l.core_router

/**
 * desc:
 * created by liyuzheng on 2026/3/27 16:11
 */
object NavigatorRegister {
    private val routerServices = mutableMapOf<String, INavigationApi>()
    fun register(path: String, provider: INavigationApi) {
        if (routerServices.containsKey(path)) {
            throw DuplicatePathException("path重复注册。")
        }
        routerServices[path] = provider
    }

    fun getProvider(path: String): INavigationApi {
        return routerServices[path] ?: throw NullPointerException("没有找到对应的provider")
    }
}

class DuplicatePathException(mes: String) : Exception(mes)
