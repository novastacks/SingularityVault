
package com.example.passwordstorageapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.passwordstorageapp.feature.auth.MasterPasswordRepository
import com.example.passwordstorageapp.feature.home.AppContent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.passwordstorageapp.feature.auth.SessionViewModel
import androidx.activity.viewModels
import com.example.passwordstorageapp.data.AppDatabase
import com.example.passwordstorageapp.data.VaultRepository
import com.example.passwordstorageapp.feature.home.VaultViewModel
import com.example.passwordstorageapp.feature.home.VaultViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var masterPasswordRepository: MasterPasswordRepository
    private val sessionViewModel: SessionViewModel by viewModels()


    // add this:
//    private lateinit var vaultRepository: VaultRepository
    private val vaultRepository by lazy {
        val db = AppDatabase.getDatabase(applicationContext)
        val vaultDao = db.vaultDao()
        VaultRepository(vaultDao)
    }
    private val vaultViewModel: VaultViewModel by viewModels {
        VaultViewModelFactory(vaultRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        masterPasswordRepository = MasterPasswordRepository(applicationContext)

        // DB + DAO + REPO setup
        val db = AppDatabase.getDatabase(applicationContext)
        val vaultDao = db.vaultDao()
        //vaultRepository = VaultRepository(vaultDao)

        ProcessLifecycleOwner.get().lifecycle.addObserver(
            LifecycleEventObserver { _, event ->
                if (event == Lifecycle.Event.ON_STOP) {
                    sessionViewModel.markLocked()
                    sessionViewModel.vaultKey = null
                }
            }
        )

        setContent {
            AppContent(
                masterPasswordRepository = masterPasswordRepository,
                sessionViewModel = sessionViewModel,
                vaultViewModel = vaultViewModel
                //vaultRepository = vaultRepository   // ✅ pass it down
            )
        }
    }
}
