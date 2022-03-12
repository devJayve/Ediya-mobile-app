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
        "menu_name",
        "menu_count",
        "menu_Temp",
        "menu_size",
        "menu_price",
        "menu_img",
        "option_cost",
        "total_cost")
    val userId = intent.getStringExtra("id").toString()
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

    fun setBasketData() {
        val db = Database(this, "ediya.db", null, 1)
        val readableDb = db.writableDatabase
        val dbControl = DatabaseControl()

        var basketData = dbControl.readData(
            readableDb, "basket",
            menuColumnList.toArray(arrayOfNulls<String>(menuColumnList.size)),
            arrayListOf("id"),
            arrayOf(userId)
        )
        if (basketData.size > 0) {
            for (i in basketData.indices) passBasketData(basketData[i])
        }
    }

    fun passBasketData(menuDataList: ArrayList<String>) {
        if (isConService) {
            basketService?.getMenuData(menuDataList)
            val db = Database(this, "ediya.db",null,1)
            val writableDb = db.writableDatabase
            val dbControl = DatabaseControl()

            dbControl.createData(writableDb,"basket",menuColumnList,menuDataList)
        }
    }

    fun loadFrag(frag_num : Int) {
        var transaction = this.supportFragmentManager.beginTransaction()
        var bundle = Bundle()
        var paymentFrag = PaymentFragment()
        var basketFrag = basket_fragment()
        var basketDataList = basketService!!.putMenuData()

        for (i in basketDataList.indices) {
            bundle.putStringArrayList(menuColumnList[i],basketDataList[i])
        }

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
                clearBindService()
                deleteLocalDb()
                transaction.replace(R.id.fragment_area, MainFragment()).commit()
            }
            else -> {
                Log.d("Message","null")
            }
        }
    }

    fun deleteLocalDb() {
        val db = Database(this, "ediya.db",null,1)
        val writableDb = db.writableDatabase
        val dbControl = DatabaseControl()

        dbControl.deleteData(writableDb,"basket", arrayListOf("id"), arrayOf(userId))
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
        supportFragmentManager.beginTransaction().replace(R.id.fragment_area, MainFragment()).commit()

        startService(Intent(this, ForegroundService::class.java))
        setBasketData()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceUnBind()
    }

}
