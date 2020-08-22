import android.view.Menu
import android.view.MenuInflater
import com.glib.core.BuildConfig
import com.glib.core.http.GRestCallback
import com.glib.core.http.Rest
import com.glib.core.http.UrlUtils
import com.glib.core.screen.GActivity
import com.glib.core.screen.GFragment
import com.glib.core.screen.GScreenContainer
import com.glib.core.ui.json.JsonAction
import com.glib.core.ui.json.JsonUi
import com.glib.core.ui.menu.GMenu

abstract class JsonUiFragment : GFragment {
    var path: String? = null
        private set
    protected var parseMenu = true
    private var page: GJson? = null

    constructor() {
        // Mandatory constructor
    }

    protected constructor(path: String, prependHost: Boolean) {
        setPath(path, prependHost)
    }

    protected fun setPath(path: String?, prependHost: Boolean) {
        path?.let {
            this.path = if (prependHost) UrlUtils.pathToUrl(it) else it
        }
    }

    override fun onRefresh() {
        super.onRefresh()
        reload(path, {})
    }

    fun reload(path: String?, onReload: () -> Unit) {
        val callback = GRestCallback.Default(this@JsonUiFragment) { response ->
            JsonUi.parseScreenContent(response.result, this@JsonUiFragment)
            page = response.result
            activity?.invalidateOptionsMenu()

            onReload()
        }
        path?.let {
            Rest.GET.async(it, null, false).execute(callback)
        }
    }

    override fun initContent(activity: GActivity, container: GScreenContainer) {
        enableRefreshPull()
        onRefresh()

        args[JsonUiScreen.ARG_SPEC].json?.let {
            JsonAction.execute(it, activity, null, null)
            activity.finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val page = this.page
        val activity = this.gActivity
        if (page == null || activity == null) {
            return
        }

        val gMenu = GMenu(menu)
        if (parseMenu) {
            page["rightNavButtons"].arrayValue.forEach { button ->
                val name = button["icon"]["materialName"].stringValue
                gMenu.add(name) { item ->
                    item.showIfRoom().onClick {
                        JsonAction.execute(button["onClick"], activity, null, null)
                    }
                    JsonUi.iconDrawable(button["icon"])?.let { drawable ->
                        item.icon(drawable)
                    }
                }
            }
        }

        if (BuildConfig.DEBUG) {
            gMenu.add("?") { item ->
                item.showIfRoom().onClick {
                    launch.alert(page.toString(), "Debug Info")
                }
            }
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu?, menuInflater: MenuInflater?) {
//        val page = this.page
//        val activity = this.gActivity
//        if (page == null || activity == null || menu == null) {
//            return
//        }
//
//        val gMenu = GMenu(menu)
//        if (parseMenu) {
//            page["rightNavButtons"].arrayValue.forEach { button ->
//                val name = button["icon"]["materialName"].stringValue
//                gMenu.add(name) { item ->
//                    item.showIfRoom().onClick {
//                        JsonAction.execute(button["onClick"], activity, null, null)
//                    }
//                    JsonUi.iconDrawable(button["icon"])?.let { drawable ->
//                        item.icon(drawable)
//                    }
//                }
//            }
//        }
//
//        if (BuildConfig.DEBUG) {
//            gMenu.add("?") { item ->
//                item.showIfRoom().onClick {
//                    launch.alert(page.toString())
//                }
//            }
//        }
//    }
}