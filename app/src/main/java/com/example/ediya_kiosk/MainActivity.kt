package com.example.ediya_kiosk

import android.content.*
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.ediya_kiosk.database.Database
import com.example.ediya_kiosk.database.DatabaseControl
import com.example.ediya_kiosk.fragment.*
import com.example.ediya_kiosk.service.BasketService
import com.example.ediya_kiosk.service.ForegroundService
import com.example.ediya_kiosk.service.ForegroundService.Companion.ACTION_STOP
import java.util.*
import kotlin.collections.ArrayList

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

        val db = Database(this, "ediya.db",null,1)
        val readableDb = db.readableDatabase
        val writableDb = db.writableDatabase
        val dbControl = DatabaseControl()

        val interfaceList =
            dbControl.readData(readableDb,"interface", arrayOf("isMode","isLanguage"), arrayListOf("id"), arrayOf(userId))
        var isMode = interfaceList[0][0].toInt()
        var isLanguage = interfaceList[0][1].toInt()

        when (isMode) {
            0 -> applyDayNight(OnDayNightStateChanged.DAY)
            1 -> applyDayNight(OnDayNightStateChanged.NIGHT)
        }

        when (isLanguage) {
            0 -> setLocate("ko")
            1 -> setLocate("en")
        }

        var mainFrag = MainFragment()
        var bundle = Bundle()
        bundle.putString("userId",userId)
        mainFrag.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.fragment_area, mainFrag).commit()

        startService(Intent(this, ForegroundService::class.java))
        serviceBind()
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        Log.d("Message","onConfigurationChanged")
        val nightMode = newConfig.uiMode and Configuration.UI_MODE_NIGHT_MASK

        when (nightMode) {
            Configuration.UI_MODE_NIGHT_NO -> {
                applyDayNight(OnDayNightStateChanged.DAY)
                Log.d("Message","UI_MODE_NIGHT_NO ")
            }
            Configuration.UI_MODE_NIGHT_YES -> {
                applyDayNight(OnDayNightStateChanged.NIGHT)
                Log.d("Message","UI_MODE_NIGHT_YES ")
            }
        }
    }

    private fun applyDayNight(state: Int){
        if (state == OnDayNightStateChanged.DAY){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            updateInterface(0)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            updateInterface(1)
        }

        supportFragmentManager.fragments.forEach {
            if(it is OnDayNightStateChanged){
                it.onDayNightApplied(state)
            }
        }
    }


    fun updateInterface(num : Int) {
        val db = Database(this, "ediya.db",null,1)
        val writableDb = db.writableDatabase
        val dbControl = DatabaseControl()

        when (num) {
            0 -> {
                dbControl.updateData(writableDb,"interface", arrayListOf("isMode"), arrayListOf("0"),
                    arrayListOf("id"), arrayOf(userId))
            }
            1 -> {
                dbControl.updateData(writableDb,"interface", arrayListOf("isMode"), arrayListOf("1"),
                    arrayListOf("id"), arrayOf(userId))
            }
        }
    }

    fun setLocate(Lang: String) {
        val db = Database(this, "ediya.db",null,1)
        val writableDb = db.writableDatabase
        val dbControl = DatabaseControl()

        Log.d("로그", "setLocate")
        val locale = Locale(Lang) // Local 객체 생성. 인자로는 해당 언어의 축약어가 들어가게 됩니다. (ex. ko, en)
        Locale.setDefault(locale) // 생성한 Locale로 설정을 해줍니다.

        val config = Configuration() //이 클래스는 응용 프로그램이 검색하는 리소스에 영향을 줄 수 있는
        // 모든 장치 구성 정보를 설명합니다.

        config.setLocale(locale) // 현재 유저가 선호하는 언어를 환경 설정으로 맞춰 줍니다.
        baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)

        when (Lang) {
            "ko" -> {
                dbControl.updateData(writableDb,"interface", arrayListOf("isLanguage"), arrayListOf("0"),
                    arrayListOf("id"), arrayOf(userId))
            }
            "en" -> {
                dbControl.updateData(writableDb,"interface", arrayListOf("isLanguage"), arrayListOf("1"),
                    arrayListOf("id"), arrayOf(userId))
            }
        }
    }

//    override fun onDestroy() {
//        super.onDestroy()
//        serviceUnBind()
//    }
}
