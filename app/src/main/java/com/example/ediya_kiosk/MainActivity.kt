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
                       menuPrice : String, menuTotalPrice : String, menuImg : String) {
        if (isConService) {
            basketService?.getMenuData(menuName,menuTemp,menuSize,menuPrice,menuTotalPrice,menuImg)
        }
    }

    fun loadBasketFrag() {
        if (isConService) {
            var bundle = Bundle()
            var basketFrag = basket_fragment()
            var basketDataList = basketService!!.putMenuData()

            bundle.putStringArrayList("nameList",basketDataList[0])
            bundle.putStringArrayList("tempList",basketDataList[1])
            bundle.putStringArrayList("sizeList",basketDataList[2])
            bundle.putStringArrayList("priceList",basketDataList[3])
            bundle.putStringArrayList("totalPriceList",basketDataList[4])
            bundle.putStringArrayList("imgList",basketDataList[5])
            basketFrag.arguments = bundle

            var transaction = this.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_area,basketFrag).commit()
        }
    }

    fun loadPaymentFrag() {
        var bundle = Bundle()
        var paymentFrag = PaymentFragment()
        var basketDataList = basketService!!.putMenuData()

        bundle.putStringArrayList("nameList",basketDataList[0])
        bundle.putStringArrayList("tempList",basketDataList[1])
        bundle.putStringArrayList("sizeList",basketDataList[2])
        bundle.putStringArrayList("priceList",basketDataList[3])
        bundle.putStringArrayList("totalPriceList",basketDataList[4])
        bundle.putStringArrayList("imgList",basketDataList[5])
        paymentFrag.arguments = bundle

        this.supportFragmentManager.beginTransaction().replace(R.id.fragment_area, paymentFrag).commit()
    }

    fun loadFrag(frag_num : Int) {
        Log.d("Message","loadFrag($frag_num)")
        var transaction = this.supportFragmentManager.beginTransaction()
        var bundle = Bundle()
        var paymentFrag = PaymentFragment()
        var basketFrag = basket_fragment()
        var basketDataList = basketService!!.putMenuData()

        bundle.putStringArrayList("nameList",basketDataList[0])
        bundle.putStringArrayList("tempList",basketDataList[1])
        bundle.putStringArrayList("sizeList",basketDataList[2])
        bundle.putStringArrayList("priceList",basketDataList[3])
        bundle.putStringArrayList("totalPriceList",basketDataList[4])
        bundle.putStringArrayList("imgList",basketDataList[5])

        when (frag_num) {
            1 -> {
                basketFrag.arguments = bundle
                transaction.replace(R.id.fragment_area, basketFrag).commit()
            }
            2 -> {
                paymentFrag.arguments = bundle
                transaction.replace(R.id.fragment_area, paymentFrag).commit()
            }
            3 -> {
                Log.d("Message","loadFrag2(3)")
                initialize()
                transaction.replace(R.id.fragment_area, MainFragment()).commit()
            }
            else -> {
                Log.d("Message","null")
            }
        }
    }

    fun initialize() {
        basketService!!.initializeList()
        updateNotification()
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

    override fun onDestroy() {
        super.onDestroy()
        serviceUnBind()
    }
}
