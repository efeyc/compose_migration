package com.eck.compose_migration.ui.base

import android.content.DialogInterface
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.eck.compose_migration.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class BaseActivity : AppCompatActivity() {

    fun setToolbar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_back)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun showDialogFragment(fragment: BaseBottomSheetDialogFragment<*>, dismissListener: DialogInterface.OnDismissListener? = null) {
        dismissListener?.let {
            fragment.setOnDismissListener(it)
        }
        fragment.show(supportFragmentManager, "")
    }
}