package com.example.ediya_kiosk

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.ediya_kiosk.database.Database
import com.example.ediya_kiosk.database.DatabaseControl
import com.example.ediya_kiosk.fragment.*
import com.example.ediya_kiosk.service.BasketService
import com.example.ediya_kiosk.service.ForegroundService
import com.example.ediya_kiosk.service.ForegroundService.Companion.ACTION_STOP

class MainActivity : AppCompatActivity() {

    var basketService: BasketService? = null
    var isConService = false
    val menuColumnList = arrayListOf(
        "id",
        "menu_name",
        "menu_count",
        "menu_temp",
        "menu_size",
        "menu_price",
        "menu_img",
        "option_cost",
        "total_cost")
    lateinit var userId : String
    val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val BSbinder = service as BasketService.BasketServiceBinder
            basketService = BSbinder.getService()
            isConService = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isConService = false
        }
    }

    fun serviceBind() {
        val intent = Intent(this, BasketService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    fun serviceUnBind() {
        if (isConService) {
            unbindService(serviceConnection)

            // foreground stop
            val intentStop = Intent(this, ForegroundService::class.java)
            intentStop.action = ACTION_STOP
            startService(intentStop)
            isConService = false
        }
    }

    fun updateNotification() {
        var serviceIntent = Intent(this, ForegroundService::class.java)
        serviceIntent.putIntegerArrayListExtra("basketData",loadNotificationInformation())
        startService(serviceIntent)
    }

    fun passBasketData(menuDataList: ArrayList<String>) {
        if (isConService) {
            basketService?.getMenuData(menuDataList)
        }
    }

    fun loadFrag(frag_num : Int) {
        var transaction = this.supportFragmentManager.beginTransaction()
        var bundle = Bundle()
        var paymentFrag = PaymentFragment()
        var basketFrag = basket_fragment()
        var mainFrag = MainFragment()
        val historyFrag = OrderHistoryFragment()
        var basketDataList = basketService?.putMenuData()


        for (i in basketDataList!!.indices) {
            bundle.putStringArrayList(menuColumnList[i+1],basketDataList[i])
            Log.d("TAG","put ${menuColumnList[i+1]}, ${basketDataList[i]}")
        }
        bundle.putString("userId",userId)
        Log.d("TAG","bundle put String $userId")

        when (frag_num) {
            0 -> {
                mainFrag.arguments = bundle
                transaction.replace(R.id.fragment_area, mainFrag).commit()
            }
            1 -> {
                basketFrag.arguments = bundle
                transaction.replace(R.id.fragment_area, basketFrag).commit()
            }
            2 -> {
                paymentFrag.arguments = bundle
                transaction.replace(R.id.fragment_area, paymentFrag).commit()
            }
            3 -> {
                clearBindService()
                mainFrag.arguments = bundle
                transaction.replace(R.id.fragment_area, mainFrag).commit()
            }
            4 -> {
                historyFrag.arguments = bundle
                transaction.replace(R.id.fragment_area, historyFrag).commit()
            }
            else -> {
                Log.d("Message","null")
            }
        }
    }

    fun clearBindService() {
        basketService!!.clearList()
        updateNotification()
    }

    fun loadNotificationInformation() : ArrayList<Int>{
        var basketArrayList = basketService!!.getNotificationInformation()
        Log.d("Message","${basketArrayList!![0]}")
        return basketArrayList
    }

    fun openOtherFragmentforBundle(int: Int,frag : Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        val main_frag = MainFragment()
        val basket_frag = basket_fragment()
        val detail_frag = MenuDetailFragment()

        when(int){
            1 -> {
                transaction.replace(R.id.fragment_area, frag)
            }
            2 -> {
                Log.d("Message","menu_detail_add")
                transaction.add(R.id.fragment_area, frag)
                transaction.addToBackStack(null)
            }
            3-> {
                if (!detail_frag.isHidden) {
                    transaction.add(R.id.fragment_area, detail_frag)
                }

                if (!basket_frag.isHidden) transaction.hide(basket_frag)
                if (!main_frag.isHidden) transaction.hide(main_frag)
                if (detail_frag.isHidden) transaction.show(detail_frag)
            }
            4-> {
                if (!basket_frag.isHidden) {
                    transaction.add(R.id.fragment_area,basket_frag)
                }

                if (basket_frag.isHidden) transaction.show(basket_frag)
                if (!main_frag.isHidden) transaction.hide(main_frag)
                if (!detail_frag.isHidden) transaction.hide(detail_frag)
            }
            5-> {
                //main frag 호출
                if (!main_frag.isHidden) {
                    transaction.add(R.id.fragment_area,main_frag)
                }
                if (!basket_frag.isHidden) transaction.hide(basket_frag)
                if (main_frag.isHidden) transaction.show(main_frag)
                if (!detail_frag.isHidden) transaction.hide(detail_frag)
            }
            6-> {
                    if (!frag.isHidden) {
                        transaction.add(R.id.fragment_area,frag)
                    }
                    if (frag.isHidden) transaction.show(frag)
                    if (!main_frag.isHidden) transaction.hide(main_frag)
                    if (!detail_frag.isHidden) transaction.hide(main_frag)
            }
            7 -> {
                transaction.replace(R.id.fragment_area,PaymentFragment())
            }
        }
        transaction.commit()
    }

    fun logout() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)
        userId = intent.getStringExtra("id").toString()
        var mainFrag = MainFragment()
        var bundle = Bundle()
        bundle.putString("userId",userId)
        mainFrag.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.fragment_area, mainFrag).commit()

        startService(Intent(this, ForegroundService::class.java))
        serviceBind()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceUnBind()
    }
}
