package com.github.john.tsputil

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.google.gson.Gson

class TSPUtil {
    /**
     * 全部SP懒加载，最小化数据对内存的影响
     */
    private val appSp: SharedPreferences by lazy {
        context.getSharedPreferences(
            appSpName,
            MODE_PRIVATE
        )
    }
    private val userSp: SharedPreferences by lazy {
        context.getSharedPreferences(
            userSpName,
            MODE_PRIVATE
        )
    }
    private val userRelatedSp: SharedPreferences by lazy {
        context.getSharedPreferences(
            "sp_$uId",
            MODE_PRIVATE
        )
    }
    private val moreSp: List<SharedPreferences> by lazy {
        List(moreSpName.size) { pos ->
            context.getSharedPreferences(moreSpName[pos], MODE_PRIVATE)
        }
    }

    // 用户ID，用户相关的sp名称需要此变量作为
    var uId: Long
        get() = userSp.getLong("userId", -1)
        set(value) = userSp.edit().putLong("userId", value).apply()

    companion object {
        private lateinit var context: Application
        private var appSpName: String = "Ts_app"
        private var userSpName: String = "Ts_user"
        private var moreSpName: Array<String> = arrayOf()


        /**
         * 初始化必须调用
         */
        fun init(
            @NonNull context: Application,
            @Nullable appSpName: String,
            @Nullable userSpName: String,
            @Nullable moreSpName: Array<String>
        ) {
            this.context = context
            this.appSpName = appSpName
            this.userSpName = userSpName
            this.moreSpName = moreSpName
        }

        private val ins: TSPUtil by lazy {
            TSPUtil()
        }

        fun get(): TSPUtil = ins
        fun app(): SharedPreferences = ins.appSp
        fun user(): SharedPreferences = ins.userSp
        fun more(): List<SharedPreferences> = ins.moreSp
    }

    /**
     * 用户退出，直接删除sp
     */
    fun exitUser() = userSp.edit().clear().apply()


    /**
     * 存 取 数据
     */
    fun putInApp(key: String, value: Any) = putValue(appSp, key, value)
    fun getFromApp(key: String, defValue: Any): Any = getValue(appSp, key, defValue)

    fun putInUser(key: String, value: Any) = putValue(userSp, key, value)
    fun getFromUser(key: String, defValue: Any): Any = getValue(userSp, key, defValue)

    fun putInUserRelated(key: String, value: Any) = putValue(userRelatedSp, key, value)
    fun getFromUserReleated(key: String, defValue: Any): Any = getValue(userRelatedSp, key, defValue)

    fun putInMore(index:Int, key: String, value: Any) = putValue(moreSp[index], key, value)
    fun getFromMore(index: Int, key: String, defValue: Any): Any = getValue(moreSp[index], key, defValue)

    fun getValue(sharedPreferences: SharedPreferences, key: String, defValue: Any): Any =
        with(sharedPreferences) {
            when (defValue) {
                is Long -> getLong(key, defValue)
                is String -> getString(key, defValue)!!
                is Boolean -> getBoolean(key, defValue)
                is Int -> getInt(key, defValue)
                is Float -> getFloat(key, defValue)
                else -> Gson().fromJson(getString(key, ""), defValue::class.java)
            }
        }


    fun putValue(sharedPreferences: SharedPreferences, key: String, value: Any) =
        with(sharedPreferences.edit()) {
            when (value) {
                is Long -> putLong(key, value)
                is String -> putString(key, value)
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is Float -> putFloat(key, value)
                else -> putString(key, Gson().toJson(value))
            }
        }.apply()

}