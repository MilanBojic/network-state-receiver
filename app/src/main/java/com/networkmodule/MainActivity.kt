package com.networkmodule

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val TAG = this.javaClass.simpleName

    private var mNetworkText: TextView? = null
    private var mNetworkIcon: ImageView? = null

    private lateinit var mNetworkStateReceiver: NetworkStateReceiver

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        injectViews()

        mNetworkStateReceiver = NetworkStateReceiver(this)
        mNetworkStateReceiver.publishSubjectNetworking.subscribe({ networkType ->
            mNetworkText?.setText(mNetworkStateReceiver.networkSwitcher(networkType).name)

            when (mNetworkStateReceiver.networkSwitcher(networkType)) {
                NetworkStateReceiver.ENetworkType.WIFI -> mNetworkIcon!!.setImageDrawable(getDrawable(this, R.drawable.wifi_icon))
                NetworkStateReceiver.ENetworkType.MOBILE -> mNetworkIcon!!.setImageDrawable(getDrawable(this, R.drawable.mobile_data_icon))
                NetworkStateReceiver.ENetworkType.NONE -> mNetworkIcon!!.setImageDrawable(getDrawable(this, R.drawable.none_icon))
            }
        })


    }


    fun injectViews() {
        mNetworkText = findViewById(R.id.network_text)
        mNetworkIcon = findViewById(R.id.network_icon)

    }

    override fun onDestroy() {
        super.onDestroy()
        mNetworkStateReceiver.destroy()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.exit -> {
                this.finish()
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


    fun getDrawable(context: Context, @DrawableRes drawable: Int): Drawable {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            context.resources.getDrawable(drawable, context.theme)
        } else {
            context.resources.getDrawable(drawable)
        }
    }
}
