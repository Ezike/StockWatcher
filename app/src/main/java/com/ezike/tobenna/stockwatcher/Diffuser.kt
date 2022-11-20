package com.ezike.tobenna.stockwatcher

typealias Func<A, B> = (A) -> B

fun <T, R> Diffuser(map: Func<T, R>, action: Func<R, Unit>): Func<T, Unit> {
    var cache: R? = null
    return fun(arg: T) {
        val state = map(arg)
        if (cache == null || cache != state) {
            action(state)
            cache = state
        }
    }
}

fun <T> Renderer(vararg func: Func<T, Unit>) = fun(arg: T) {
    func.forEach { it.invoke(arg) }
}