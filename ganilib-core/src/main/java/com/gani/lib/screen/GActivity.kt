package com.gani.lib.screen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import android.widget.Button
import android.widget.TextView
import com.gani.lib.R
import com.gani.lib.logging.GLog
import com.gani.lib.model.GBundle
import com.gani.lib.ui.ProgressIndicator
import com.gani.lib.utils.Res
import org.json.JSONObject
import java.io.Serializable
import kotlin.reflect.KClass

open class GActivity : AppCompatActivity(), GContainer {
    val launch = LaunchHelper(context)

    private lateinit var container: IScreenView

    private var topNavigation = false
    override val gActivity: GActivity
        get() = this

    val navBar: ActionBar
        get() = supportActionBar!!  // It should exist because setSupportActionBar() should have been called

//    protected val trackingSpec: TrackingSpec
//        get() = TrackingSpec.DO_NOTHING

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

    private fun initOnCreate() {
        intent.extras?.let {
            this.args = GBundle(it)
        }

        container = object : IScreenView(this) {
            init {
                LayoutInflater.from(context).inflate(R.layout.barebone_view_screen, this)
            }
            override val toolbar: Toolbar?
                get() = null  // Not applicable to dialog

            override fun initNavigation(topNavigation: Boolean, actionBar: ActionBar) {
                // Not applicable to dialog
            }

            override fun setBody(resId: Int) {
                LayoutInflater.from(context).inflate(resId, this)
            }

            override fun openDrawer() {
                // Not applicable to dialog
            }
        }
    }

    protected fun onCreateForScreen(savedInstanceState: Bundle?, container: GScreenView) {
//        super.onCreate(savedInstanceState)
        initOnCreate()
        this.container = container

        super.setContentView(container)
        setSupportActionBar(container.toolbar)
    }

    protected fun onCreateForDialog(savedInstanceState: Bundle?) {
        initOnCreate()
//        this.container = object : IScreenView(this) {
//            init {
//                LayoutInflater.from(context).inflate(R.layout.barebone_view_screen, this)
//            }
//            override val toolbar: Toolbar?
//                get() = null  // Not applicable to dialog
//
//            override fun initNavigation(topNavigation: Boolean, actionBar: ActionBar) {
//                // Not applicable to dialog
//            }
//
//            override fun setBody(resId: Int) {
//                LayoutInflater.from(context).inflate(resId, this)
//            }
//
//            override fun openDrawer() {
//                // Not applicable to dialog
//            }
//        }
        super.setContentView(container)
    }

//    fun args(): GBundle {
//        return GBundle(arguments)
//    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        throw UnsupportedOperationException("Should be overridden in child class")
//    }

    fun setOkResult(resultKey: String, resultValue: Serializable) {
        GLog.t(javaClass, "setOkResult: " + Activity.RESULT_OK)

        val extras = Intent()
        extras.putExtra(resultKey, resultValue)
        setResult(Activity.RESULT_OK, extras)


        //    Intent data = new Intent();
        //    data.putExtra(RESULT_DATA, result);
        //    setResult(RESULT_OK,data);
        //    finish();
    }

    fun finish(resultKey: String, resultValue: Serializable) {
        setOkResult(resultKey, resultValue)
        finish()
    }

//    override fun getGActivity(): GActivity {
//        return this
//    }

    fun updateBadge(count: Int) {
        if (container is GScreenView) {
            (container as GScreenView).updateBadge(count)
        }
    }

    fun getLabel(resId: Int): TextView {
        return findViewById<View>(resId) as TextView
    }

    fun getButton(resId: Int): Button {
        return findViewById<View>(resId) as Button
    }

    private fun setContent(resId: Int) {
        container.initNavigation(topNavigation, navBar)
        container.setBody(resId)
    }

    fun setContentWithToolbar(resId: Int, topNavigation: Boolean) {
        this.topNavigation = topNavigation
        setContent(resId)

        val toolbar = container.toolbar
        if (toolbar != null) {
            toolbar.visibility = View.VISIBLE
        }
    }

    fun setContentWithoutToolbar(resId: Int) {
        this.topNavigation = false
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
        this.topNavigation = false
        setContent(resId)
    }

    protected fun setSubtitle(subtitle: String) {
        supportActionBar!!.subtitle = subtitle
    }


    //  protected final Bundle rawArguments() {
    //    return arguments;
    //  }


    ///// Fragment management /////

    fun setFragmentWithoutToolbar(fragment: GFragment?, savedInstanceState: Bundle?) {
        this.topNavigation = false
        if (fragment != null) {
            setFragment(fragment, savedInstanceState)
        }
    }

    fun setFragmentWithToolbar(fragment: GFragment?, topNavigation: Boolean, savedInstanceState: Bundle?) {
        this.topNavigation = topNavigation
        if (fragment != null) {
            setFragment(fragment, savedInstanceState)
        }

        container.initNavigation(topNavigation, navBar)
        val toolbar = container.toolbar
        if (toolbar != null) {
            toolbar.visibility = View.VISIBLE
        }
    }

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

    // NOTE: This method also gets called when the user says no.
    override fun onRequestPermissionsResult(reqCode: Int, permissions: Array<String>, results: IntArray) {
        // This is important. See https://stackoverflow.com/questions/35989288/onrequestpermissionsresult-not-being-called-in-fragment-if-defined-in-both-fragm
        super.onRequestPermissionsResult(reqCode, permissions, results)

        when (reqCode) {
            PERMISSION_LOCATION -> {
                // TODO: Disabled as it is not required right now
//                LocationManager.instance().updateLocationSilently(this)
                return
            }
        }// Nothing to do
    }


    ///// Menu /////

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                if (topNavigation) {
                    container.openDrawer()
                } else {
                    onBackPressed()  // Going up is more similar to onBackPressed() than finish(), esp becoz the former can have pre-finish check
                }
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //    if (Build.INSTANCE.shouldShowTestFeatures()) {
        //      new GMenu(menu, this).addSecondary(R.string.menu_nav_log, GMenu.ORDER_COMMON, new GMenu.OnClickListener() {
        //        @Override
        //        protected void onClick(MenuItem menuItem) {
        //          startActivity(ScreenReadOnly.intent("Log", GLog.Reader.getLog()));
        //        }
        //      });
        //    }

        return super.onCreateOptionsMenu(menu)
    }

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

        fun withArg(key: String, value: Serializable): IntentBuilder {
            intent.putExtra(key, value)
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

        /////


        ///// Permission /////

        val PERMISSION_LOCATION = 40000

        /////


        // TODO: Could in theory use MethodHandles.lookup().lookupClass() in Java 1.7 but MethodHandles doesn't exist in Android
        //  public final static Intent intent() {
        //    return new Intent(App.context(), ScreenVoteInfo.class);
        //  }
        //
        //
        //

        fun intentBuilder(cls: KClass<out GActivity>): IntentBuilder {
            return IntentBuilder(cls)
        }
    }
}
