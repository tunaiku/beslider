package app.beslider.com.utils

enum class SliderType (val type: Int) {
    IMAGE(0);
    fun value(): Int {
        return type
    }
}