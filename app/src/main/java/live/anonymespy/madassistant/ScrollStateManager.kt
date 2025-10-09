// Add this at the top level of your file, outside any composable
object ScrollStateManager {
    private var savedScrollPosition: Int = 0

    fun saveScrollPosition(position: Int) {
        savedScrollPosition = position
    }

    fun getSavedScrollPosition(): Int {
        return savedScrollPosition
    }

    fun clearScrollPosition() {
        savedScrollPosition = 0
    }
}
