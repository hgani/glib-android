package com.glib.core.screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.glib.core.R
import com.glib.core.logging.GLog
import com.glib.core.model.GBundle
import com.glib.core.ui.ProgressIndicator
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.actions.windows.Close
import com.glib.core.utils.Res
import java.io.Serializable
import kotlin.reflect.KClass

open class GActivity : AppCompatActivity(), GContainer {
    val launch = LaunchHelper(context)

    lateinit var inav: INavHelper
        private set

    override val gActivity: GActivity
        get() = this
//
//    val navBar: ActionBar
//        get() = supportActionBar!!  // It should exist because setSupportActionBar() should have been called

    val context: Context
        get() = this

    val mainFragment: GFragment
        get() = supportFragmentManager.findFragmentById(R.id.screen_body) as GFragment

    var args = GBundle()
        private set

    private var shouldRecreateFragmentOnNewIntent = false

    /////

    val circularProgressIndicator: ProgressIndicator
        get() {
            val indicator = findViewById<View>(R.id.circular_progress)
            return object : ProgressIndicator {
                override fun showProgress() {
                    indicator.visibility = View.VISIBLE
                }

                override fun hideProgress() {
                    indicator.visibility = View.GONE
                }
            }
        }

    protected fun initOnCreate(nav: INavHelper) {
        intent.extras?.let {
            this.args = GBundle(it)
        }

        inav = nav

        super.setContentView(inav.layout)
    }

    protected fun onCreateForDialog(savedInstanceState: Bundle?) {
        initOnCreate(object : INavHelper() {
            override val layout = LayoutInflater.from(context).inflate(R.layout.barebone_view_screen, null) as ViewGroup
            override fun setBody(resId: Int) {
                LayoutInflater.from(context).inflate(resId, layout)
            }
        })
    }

//    private fun initOnCreate() {
//        intent.extras?.let {
//            this.args = GBundle(it)
//        }
//
//        inav = object : INavHelper() {
//            override val layout = LayoutInflater.from(context).inflate(R.layout.barebone_view_screen, null) as ViewGroup
////            override val toolbar: Toolbar?
////                get() = null  // Not applicable to dialog
//
//            override fun setBody(resId: Int) {
//                LayoutInflater.from(context).inflate(resId, layout)
//            }
//        }
//
//        super.setContentView(inav.layout)
//    }
//
//    protected open fun onCreateForScreen(savedInstanceState: Bundle?) {
//        initOnCreate()
//
//        inav = NavHelper(this)
//
////        super.setContentView(inav.layout)
//    }
//
//    protected fun onCreateForDialog(savedInstanceState: Bundle?) {
//        initOnCreate()
//
////        inav = NavHelper(this)
//
////        super.setContentView(inav.layout)
//    }

    fun setOkResult(resultKey: String, resultValue: Serializable) {
        GLog.t(javaClass, "setOkResult: ${Activity.RESULT_OK } - ${resultKey} - ${resultValue}")

        val extras = Intent()
        extras.putExtra(resultKey, resultValue)
        setResult(Activity.RESULT_OK, extras)
    }

    fun finish(resultKey: String, resultValue: Serializable) {
        setOkResult(resultKey, resultValue)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            // Implement this in GActivity instead of JsonUiScreen because the opener screen might not be a JsonUiScreen
            val args = GBundle(data?.extras ?: Bundle.EMPTY)
            args[Close.RESULT_KEY_ON_AFTER_CLOSE].json?.let {
                JsonAction.execute(it, this, null, null)
            }
        }
    }

    fun getLabel(resId: Int): TextView {
        return findViewById<View>(resId) as TextView
    }

    fun getButton(resId: Int): Button {
        return findViewById<View>(resId) as Button
    }

    private fun setContent(resId: Int) {
//        inav.initNavigation(navBar)
        inav.setBody(resId)
    }
//
//    fun setContentWithToolbar(resId: Int) {
////        this.topNavigation = topNavigation
//        setContent(resId)
//
//        // TODO
////        nav.toolbar.visibility = View.VISIBLE
//
////        val toolbar = nav.toolbar
////        if (toolbar != null) {
////            toolbar.visibility = View.VISIBLE
////        }
//    }

    fun setContentWithoutToolbar(resId: Int) {
//        this.topNavigation = false
        setContent(resId)
    }

    override fun setContentView(view: View?) {
        throw UnsupportedOperationException("Use either setContentWithToolbar() or setContentWithoutToolbar()")
    }

    override fun setContentView(view: View, params: ViewGroup.LayoutParams) {
        setContentView(view)
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        setContentView(null)
    }


    // Unfortunately when setting theme programatically, the background won't be transparent. See http://stackoverflow.com/questions/15455979/translucent-theme-does-not-work-when-set-programmatically-on-android-2-3-3-or-4
    // So we'll stick to setting theme in manifest and calling the right dialog method here. Ideally both can be done in this method.
    //
    // In the future, if we want to look into this again, beware that theming is time consuming and causes weird errors (with useless stacktraces).
    //
    // Put this in themes.xml:
    // <style name="FakeDialog" parent="Theme.AppCompat.Light.Dialog">
    //   <item name="windowNoTitle">true</item>
    // </style>
    fun setContentForDialog(resId: Int) {
//        this.topNavigation = false
        setContent(resId)
    }

    protected fun setSubtitle(subtitle: String) {
        supportActionBar!!.subtitle = subtitle
    }

    ///// Fragment management /////

    fun setFragmentWithoutToolbar(fragment: GFragment?, savedInstanceState: Bundle?) {
//        this.topNavigation = false
        if (fragment != null) {
            setFragment(fragment, savedInstanceState)
        }
    }
//
//    fun setFragmentWithToolbar(fragment: GFragment?, topNavigation: Boolean, savedInstanceState: Bundle?) {
////        this.topNavigation = topNavigation
//        if (fragment != null) {
//            setFragment(fragment, savedInstanceState)
//        }
//
//        inav.initNavigation(navBar)
//
//        // TODO
////        nav.toolbar.visibility = View.VISIBLE
//
////        val toolbar = nav.toolbar
////        if (toolbar != null) {
////            toolbar.visibility = View.VISIBLE
////        }
//    }

    private fun setFragment(fragment: GFragment, savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {  // During initial setup, plug in the fragment
            fragment.arguments = intent.extras
            // R.id.screen_body has to be unique or else we might be attaching the fragment to the wrong view
            supportFragmentManager.beginTransaction().add(R.id.screen_body, fragment).commit()
        }
    }

    fun replaceFragment(fragment: GFragment) {
        fragment.arguments = intent.extras
        // R.id.screen_body has to be unique or else we might be attaching the fragment to the wrong view
        supportFragmentManager.beginTransaction().replace(R.id.screen_body, fragment).commit()
    }

    open fun createNewIntentFragment(): GFragment? {
        return null
    }

    override fun onResume() {
        super.onResume()
        if (shouldRecreateFragmentOnNewIntent) {
            val fragment = createNewIntentFragment()
            if (fragment != null) {
                replaceFragment(fragment)
            }
            shouldRecreateFragmentOnNewIntent = false // Ensure single execution
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        setIntent(intent)
        shouldRecreateFragmentOnNewIntent = true
    }

//    // NOTE: This method also gets called when the user says no.
//    override fun onRequestPermissionsResult(reqCode: Int, permissions: Array<String>, results: IntArray) {
//        // This is important. See https://stackoverflow.com/questions/35989288/onrequestpermissionsresult-not-being-called-in-fragment-if-defined-in-both-fragm
//        super.onRequestPermissionsResult(reqCode, permissions, results)
//
//        when (reqCode) {
//            PERMISSION_LOCATION -> {
//                // TODO: Disabled as it is not required right now
////                LocationManager.instance().updateLocationSilently(this)
//                return
//            }
//        }// Nothing to do
//    }


    ///// Menu /////

    // TODO: Move to GScreen
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            android.R.id.home -> {
//                if (!nav.handleHomeClick()) {
//                    onBackPressed()  // Going up is more similar to onBackPressed() than finish(), especially because the former can have pre-finish check
//                }
//                return true
//            }
//        }
//
//        return super.onOptionsItemSelected(item)
//    }

    class IntentBuilder internal constructor(cls: KClass<out GActivity>) {
        val intent: Intent

        init {
            this.intent = Intent(Res.context, cls.java)
        }

        fun withFlags(flags: Int): IntentBuilder {
            intent.addFlags(flags)
            return this
        }

        fun <T> withArg(key: String, value: Array<T>): IntentBuilder {
            intent.putExtra(key, GBundle.ArrayWrapper(value))
            return this
        }

        fun withArg(key: String, value: Serializable?): IntentBuilder {
            value?.let { intent.putExtra(key, value) }
            return this
        }

        fun withArg(key: String, value: List<*>): IntentBuilder {
            intent.putExtra(key, value as Serializable)
            return this
        }

        fun withArg(value: Serializable): IntentBuilder {
            intent.putExtra(GBundle.KEY_SINGLETON, value)
            return this
        }
    }

    companion object {
        // TODO: Could in theory use MethodHandles.lookup().lookupClass() in Java 1.7 but MethodHandles doesn't exist in Android
        //  public final static Intent intent() {
        //    return new Intent(App.context(), ScreenVoteInfo.class);
        //  }

        fun intentBuilder(cls: KClass<out GActivity>): IntentBuilder {
            return IntentBuilder(cls)
        }
    }
}
