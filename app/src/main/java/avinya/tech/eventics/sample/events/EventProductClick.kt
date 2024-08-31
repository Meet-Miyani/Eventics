package avinya.tech.eventics.sample.events

import avinya.tech.eventics.BaseEventic
import avinya.tech.eventics.sample.model.Product

data class EventProductClick(
    val product: Product
) : BaseEventic {

    override val eventName: String
        get() = AppEvents.PRODUCT_CLICK

    override val properties: Map<String, Any?>
        get() = mapOf(
            "ProductName" to product.name,
            "BasePrice" to product.price,
            "TotalPrice" to product.price.times(product.quantity)
        )
}
