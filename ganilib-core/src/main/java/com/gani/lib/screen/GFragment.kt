package com.gani.lib.screen

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.view.*
import com.gani.lib.R
import com.gani.lib.model.GBundle
import com.gani.lib.ui.ProgressIndicator
import com.gani.lib.ui.menu.GMenu

open class GFragment : Fragment(), GContainer {
    private var firstVisit: Boolean = false

    override val gActivity: GActivity?
        get() = activity as? GActivity

    // Implement this in Fragment instead of Activity to ensure it works well on dual panel
    lateinit var refreshView: SwipeRefreshLayout
//    var indicator: ProgressIndicator = ProgressIndicator.NULL
//        private put
    lateinit var launch: LaunchHelper
        private set
    lateinit var indicator: ProgressIndicator
        private set
    var container: GScreenContainer? = null
        private set

    protected// Override to show a refresh menu item
    val refreshStringId: Int
        get() = RESOURCE_INVALID

    var args = GBundle()
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        // Arguments may be null if the containing screen doesn't have any extras to pass on.
        getArguments()?.let { this.args = GBundle(it) }
    }

    fun withArgs(args: GBundle) : GFragment {
        this.arguments = args.native
        return this
    }

//    protected fun jsonArgs(): GJson {
//        return GJsonBundle(rawArgs).json
//    }

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val fragmentLayout = inflater.inflate(R.layout.common_fragment, null)
        val refreshView = fragmentLayout.findViewById<View>(R.id.layout_refresh) as SwipeRefreshLayout
        refreshView.setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener { this@GFragment.onRefresh() })
        refreshView.isEnabled = false

        val activity = gActivity!!

        this.firstVisit = true
        this.launch = LaunchHelper(activity)
        this.indicator = ProgressIndicator.Swipe(refreshView)
        this.refreshView = refreshView

        val screenContainer = fragmentLayout.findViewById(R.id.container) as GScreenContainer
        this.container = screenContainer
        // Execute this after everything has been initialized. At this point, context is guaranteed to be non null.
        initContent(activity, screenContainer)

        return fragmentLayout
    }

    open protected fun initContent(activity: GActivity, container: GScreenContainer) {
        // To be overridden
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onResume() {
        super.onResume()

        // Mimic activity's onRestart(). See http://stackoverflow.com/questions/35039512/android-what-to-use-instead-of-onrestart-in-a-fragment
        if (firstVisit) {
            firstVisit = false
        } else {
            onRestart()
        }
    }

    protected fun onRestart() {
        // To be overridden.
    }

    protected fun enableRefreshPull() {
        refreshView.isEnabled = true
    }

    protected open fun onRefresh() {
        indicator.hideProgress()
    }

    override fun onCreateOptionsMenu(menu: Menu?, menuInflater: MenuInflater?) {
        val strId = refreshStringId
        if (strId != RESOURCE_INVALID) {
            GMenu(menu).addSecondary(strId, GMenu.ORDER_SPECIFIC, object : GMenu.OnClickListener() {
                override fun onClick(menuItem: MenuItem) {
                    onRefresh()
                }
            })
        }
    }

    companion object {

        private val RESOURCE_INVALID = 0
    }
}
