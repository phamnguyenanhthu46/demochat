package tool.devp.demochat.common

class Optional<V> private constructor(private val value: V?) {

    private constructor(): this(null)

    val isEmpty: Boolean
        get() = this.value == null

    fun get(): V {
        if (value == null) {
            throw NoSuchElementException("No value present")
        }
        return value
    }

    companion object {

        fun <V> of(value: V): Optional<V> = Optional(value)

        @Suppress("UNCHECKED_CAST")
        fun <V> none() = NONE as Optional<V>

        private val NONE = Optional<Any>()

    }

}