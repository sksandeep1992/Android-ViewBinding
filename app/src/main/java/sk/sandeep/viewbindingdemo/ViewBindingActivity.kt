package sk.sandeep.viewbindingdemo

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import sk.sandeep.viewbindingdemo.databinding.ActivityViewBindingBinding

class ViewBindingActivity : AppCompatActivity() {

    // Scoped to the lifecycle of the activity
    private lateinit var binding: ActivityViewBindingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //1.Call the static inflate() method included in the generated binding class. This creates
        // an instance of the binding class for the activity to use.
        binding = ActivityViewBindingBinding.inflate(layoutInflater)

        //2.Get a reference to the root view by either calling the getRoot() method or using Kotlin property syntax.
        val view = binding.root
        //3.Pass the root view to setContentView() to make it the active view on the screen.
        //setContentView(binding.root)
        //OR
        setContentView(view)

        binding.buttonDone.setOnClickListener {
            binding.editTextLastName.visibility = View.GONE
            binding.editTextFirstName.visibility = View.GONE
            binding.textViewName.visibility = View.VISIBLE
            // binding.textViewName.text = "${binding.editTextFirstName.text}${binding.editTextLastName.text}"
            "${binding.editTextFirstName.text} ${binding.editTextLastName.text}".also {
                binding.textViewName.text = it
            }

        }
        binding.buttonNext.setOnClickListener {
            startActivity(Intent(this, FragmentActivity::class.java))
        }
    }
}