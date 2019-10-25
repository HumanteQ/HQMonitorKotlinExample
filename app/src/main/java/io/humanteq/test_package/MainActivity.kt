package io.humanteq.test_package

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.humanteq.hqsdkcore.HQSdk
import io.humanteq.hqsdkcore.api.interfaces.HqmCallback
import io.humanteq.hqsdkcore.models.GroupResponse
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getGroups()
        grp_tv.setOnClickListener {
            grp_tv.text = getString(R.string.requesting_grps)
            getGroups()
        }
    }

    private fun getGroups() {
        if (!HQSdk.isInitialized) {
            grp_tv.text = getString(R.string.not_initialized)
            return
        }

        HQSdk.getUserGroups(this, object : HqmCallback<List<GroupResponse>> {
            override fun onSuccess(data: List<GroupResponse>) {
                data
                    .map { it.segment_name }
                    .joinToString(separator = "\n") { it }
                    .ifEmpty { getString(R.string.empty) }
                    .apply { grp_tv.text = this }

            }

            override fun onError(exception: Throwable) {
                exception.printStackTrace()
                grp_tv.text = exception.localizedMessage
                Toast.makeText(
                    this@MainActivity,
                    "Error occured: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}
