package avinya.tech.eventics.sample.views

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import avinya.tech.eventics.sample.databinding.ItemProductHolderBinding
import avinya.tech.eventics.sample.events.EventProductClick
import avinya.tech.eventics.sample.events.core.MyEventManager
import avinya.tech.eventics.sample.model.Product

class ProductItemHolder(private val binding: ItemProductHolderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(product: Product) {
        with(binding) {
            val context = root.context
            txtProductTitle.text = product.name
            txtProductPrice.text = "$${product.price}"

            root.setOnClickListener {
                
                // Logging Event
                val eventProductClick = EventProductClick(product)
                MyEventManager.get(context).log(eventProductClick)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): ProductItemHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemProductHolderBinding.inflate(inflater, parent, false)
            return ProductItemHolder(binding)
        }
    }
}