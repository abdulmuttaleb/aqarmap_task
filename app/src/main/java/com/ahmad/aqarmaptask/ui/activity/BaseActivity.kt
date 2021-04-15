package com.ahmad.aqarmaptask.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.ahmad.aqarmaptask.MyApplication
import com.ahmad.aqarmaptask.receivers.ConnectivityReceiver

open class BaseActivity: AppCompatActivity(), ConnectivityReceiver.ConnectivityReceiverListener {

    var isOnline = MutableLiveData<Boolean?>().apply { value = null }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObservers()
    }

    private fun initObservers(){
        if(!isOnline.hasObservers()){
            isOnline.observe(this, {
                if (it == false) {
                    Toast.makeText(this, "Connection was lost", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    override fun onResume() {
        super.onResume()
        try {
            MyApplication.instance!!.setConnectivityListener(this)
        }catch(e:Exception){

        }
    }

    override fun onPause() {
        try{
            MyApplication.instance!!.removeConnectivityListener()
        }catch (e:Exception){

        }
        super.onPause()
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        isOnline.postValue(isConnected)
    }

    companion object{
        const val TAG = "BaseActivity"
    }
}