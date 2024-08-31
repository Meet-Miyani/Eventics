package avinya.tech.eventics.sample.model

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val quantity: Int
) {
    companion object {
        val list: List<Product>
            get() = listOf(
                Product(1, "Product 1", 10.1, 1),
                Product(2, "Product 2", 20.2, 2),
                Product(3, "Product 3", 30.3, 3),
                Product(4, "Product 4", 40.4, 4),
                Product(5, "Product 5", 50.5, 5),
            )
    }
}
