package avinya.tech.eventics.sample.views

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import avinya.tech.eventics.sample.model.Product

class ProductAdapter : ListAdapter<Product, ProductItemHolder>(DIFF) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductItemHolder {
        return ProductItemHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ProductItemHolder, position: Int) {
        val product = getItem(position)
        holder.bind(product)
    }

    companion object {
        private val DIFF = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }
        }
    }
}