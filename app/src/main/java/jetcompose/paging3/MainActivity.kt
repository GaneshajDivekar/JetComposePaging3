package jetcompose.paging3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import jetcompose.paging3.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import net.simplifiedcoding.data.MyApi

class MainActivity : AppCompatActivity() {

    var activityMainBinding: ActivityMainBinding? = null
    var passengersViewModel: PassengersViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val factory = PassengersViewModelFactory(MyApi())
        passengersViewModel = ViewModelProvider(this, factory).get(PassengersViewModel::class.java)

        val passengersAdapter = PassengersAdapter()
        activityMainBinding!!.recyclerView.layoutManager = LinearLayoutManager(this)
        activityMainBinding!!.recyclerView.setHasFixedSize(true)

        activityMainBinding!!.recyclerView.adapter = passengersAdapter.withLoadStateHeaderAndFooter(
            header = PassengersLoadStateAdapter { passengersAdapter.retry() },
            footer = PassengersLoadStateAdapter { passengersAdapter.retry() }
        )

        lifecycleScope.launch {
            passengersViewModel!!.passengers.collectLatest { pagedData ->
                passengersAdapter.submitData(pagedData)
            }
        }


    }
}