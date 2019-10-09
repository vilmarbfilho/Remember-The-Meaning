package br.com.vilmar.rememberthemeaning.common.extensions

inline fun <T> T?.guard(block: () -> Nothing): T {
    if (this == null) {
        block()
    }

    return this
}