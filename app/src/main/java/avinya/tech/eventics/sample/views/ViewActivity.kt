package avinya.tech.eventics.sample.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import avinya.tech.eventics.sample.R
import avinya.tech.eventics.sample.databinding.ActivityViewBinding
import avinya.tech.eventics.sample.events.AppEvents
import avinya.tech.eventics.sample.events.core.MyEventManager
import avinya.tech.eventics.sample.model.Product

class ViewActivity : AppCompatActivity() {

    private val productAdapter by lazy {
        ProductAdapter()
    }

    private lateinit var binding: ActivityViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Logging Event
        MyEventManager.get(this).log(AppEvents.VIEW_XML_SCREEN)

        binding.productRecyclerView.adapter = productAdapter
        productAdapter.submitList(Product.list)
    }
}