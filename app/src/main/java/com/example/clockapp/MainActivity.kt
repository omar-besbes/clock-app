package com.example.clockapp

import android.os.Bundle
import android.view.ActionMode
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.clockapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), ActionMode.Callback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var actionMode: ActionMode

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, FragmentClock(), null)
            .addToBackStack(null)
            .commit()

        binding.button.setOnLongClickListener {
            actionMode = this@MainActivity.startActionMode(this@MainActivity)!!
            return@setOnLongClickListener true
        }
    }

    fun setTime(view: View?) {
        var fragmentManager = supportFragmentManager
        var transaction = fragmentManager.beginTransaction()
        var fragmentClock = FragmentClock()
        var bundle = Bundle()
        bundle.putBoolean("digitalOK", binding.switchButton.isChecked)
        fragmentClock.arguments = bundle
        transaction.replace(R.id.fragmentContainerView, fragmentClock)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_switch) {
            binding.switchButton.isChecked = !binding.switchButton.isChecked
            setTime(null)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateActionMode(actionMode: ActionMode, menu: Menu?): Boolean {
        val inflater: MenuInflater = actionMode.menuInflater
        inflater.inflate(R.menu.context_mode_menu, menu)
        return true
    }

    override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menuItem: MenuItem?): Boolean {
        return when (menuItem?.itemId) {
            R.id.action_color -> {
                binding.button.setBackgroundColor(
                    ContextCompat.getColor(
                        binding.root.context, R.color.teal_200
                    )
                )
                actionMode?.finish()
                true
            }

            else -> false
        }
    }

    override fun onDestroyActionMode(p0: ActionMode?) {
    }

}