package sk.sandeep.viewbindingdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import sk.sandeep.viewbindingdemo.databinding.ActivityFragmentBinding

class FragmentActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        val navController =
            binding.navHostFragmentContentFragment.getFragment<NavHostFragment>().navController


        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController =
            binding.navHostFragmentContentFragment.getFragment<NavHostFragment>().navController
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}