package com.gani.lib.dialog

import android.content.res.Configuration
import android.os.Bundle
import com.gani.lib.screen.GActivity
import com.gani.lib.ui.ProgressIndicator

//import com.smartguam.guamevent.R;

/**
 * This fake dialog knows how to re-draw itself correctly on reorientation and should be configured not to be destroyed and re-created
 * on reorientation.
 * To use properly, the Activity should have the following setting in AndroidManifest.xml:
 * <br></br>
 * <pre>android:configChanges="orientation|screenSize"</pre>
 */
abstract class GDialogProgress : GActivity(), ProgressIndicator {
    private var completed = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        super.onCreateForDialog(savedInstanceState)
        setFragmentWithoutToolbar(createNewIntentFragment(), savedInstanceState)
//        setupViews()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
//        setupViews()
    }

    override fun onDestroy() {
        if (isFinishing) {
            if (!completed) {
                onCancel()
            }
        }
        super.onDestroy()
    }

//    private fun setupViews() {
//            setContentForDialog(contentView());
//
//            setTitle(title());
//
//            String text = text();
//            if (text != null) {
//        //      setText(text);
//            }
//    }

    //  protected int contentView() {
    //    return R.layout.dialog_progress;
    //  }

    //  protected int title() {
    //    return R.string.title_progress;
    //  }
//
//    protected fun title(): String? {
//        return null
//    }
//
//    protected fun text(): String? {
//        return null
//    }

    protected open fun onCancel() {
        // Do nothing by default
    }

    // TODO: Consider overriding finish() to throw an exception because there shouldn't be any
    // reason not to use this method.
    protected fun finishDueToCompletion() {
        completed = true
        finish()
    }

    override fun showProgress() {
        // Nothing to do since this dialog is a progess indicator itself.
    }

    override fun hideProgress() {
        finishDueToCompletion()
    }

    //  private void setText(String text) {
    //    ((TextView) findViewById(R.id.message)).setText(text);
    //  }
}
