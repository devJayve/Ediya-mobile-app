package com.example.ediya_kiosk

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.ediya_kiosk.fragment.*
import com.example.ediya_kiosk.service.BasketService
import com.example.ediya_kiosk.service.ForegroundService
import com.example.ediya_kiosk.service.ForegroundService.Companion.ACTION_STOP

class MainActivity : AppCompatActivity() {

    var basketService: BasketService? = null
    var isConService = false

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

    fun passBasketData(menuName : String, menuTemp: String, menuSize : String,
                       menuPrice : Int, menuTotalPrice : Int, menuImg : String) {
        if (isConService) {
            basketService?.getMenuData(menuName,menuTemp,menuSize,menuPrice,menuTotalPrice,menuImg)
            loadNotificationInformation()
        }
    }

    fun loadBasketFrag() {
        if (isConService) {
            var basket_frag = basketService!!.putMenuData(basket_fragment())
            var transaction = this.supportFragmentManager.beginTransaction()

            transaction.replace(R.id.fragment_area, basket_frag).commit()
        }
    }

    fun loadPaymentFrag() {
        var payment_frag = basketService!!.putMenuData(PaymentFragment())
        this.supportFragmentManager.beginTransaction().replace(R.id.fragment_area,payment_frag).commit()
    }

    fun loadFrag(frag_num : Int) {
        Log.d("Message","loadFrag($frag_num)")
        var transaction = this.supportFragmentManager.beginTransaction()
        when (frag_num) {
            1 -> {
                var basket_frag = basketService!!.putMenuData(basket_fragment())
                transaction.replace(R.id.fragment_area, basket_frag).commit()
            }
            2 -> {
                var payment_frag = basketService!!.putMenuData(PaymentFragment())
                transaction.replace(R.id.fragment_area, payment_frag).commit()
            }
            3 -> {
                Log.d("Message","loadFrag2(3)")
                basketService!!.initializeList()
                updateNotification()
                transaction.replace(R.id.fragment_area, MainFragment()).commit()
            }
            else -> {
                Log.d("Message","null")
            }
        }
    }

    fun loadNotificationInformation() : ArrayList<Int>{
        var basketArrayList = basketService!!.getNotificationInformation()
        Log.d("Message","${basketArrayList!![0]}")
        return basketArrayList
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_layout)

        startService(Intent(this, ForegroundService::class.java))

        var fragment = MainFragment()
        supportFragmentManager.beginTransaction().replace(R.id.fragment_area, fragment).commit()
    }

    fun openOtherFragmentforBundle(int: Int,frag : Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        val main_frag = MainFragment()
        val basket_frag = basket_fragment()
        val detail_frag = menu_detail_fragment()

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

    override fun onDestroy() {
        super.onDestroy()
        serviceUnBind()
    }
}
