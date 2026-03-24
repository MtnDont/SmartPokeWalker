package com.mtndont.smartpokewalker.presentation

import android.Manifest
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import com.mtndont.smartpokewalker.ble.BLETradeClient
import com.mtndont.smartpokewalker.ble.BLETradeServer
import com.mtndont.smartpokewalker.data.MonsterModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TradeViewModel @Inject constructor(
    private val server: BLETradeServer,
    private val client: BLETradeClient
): ViewModel() {
    private val _state = MutableStateFlow<TradeState>(TradeState.Idle)
    val state: StateFlow<TradeState> = _state.asStateFlow()

    private var myMonster: MonsterModel = MonsterModel.getRandomMonster()

    fun setMonster(monster: MonsterModel) {
        myMonster = monster
    }

    @RequiresPermission(allOf = [
        Manifest.permission.BLUETOOTH_ADVERTISE,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT
    ])
    fun hostTrade() {
        val monster = myMonster// ?: return
        _state.value = TradeState.WaitingForOffer

        server.setCallbacks(
            onOfferReceived = { theirMonster ->
                _state.value = TradeState.ReviewingOffer(theirMonster)
            },
            onTradeComplete = { received ->
                finaliseTrade(received)
            },
            onTradeCancelled = {
                handleCancellation()
            }
        )
        server.startServer(monster)
    }

    @RequiresPermission(allOf = [
        Manifest.permission.BLUETOOTH_ADVERTISE,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT
    ])
    fun joinTrade() {
        val monster = myMonster// ?: return
        _state.value = TradeState.Searching

        client.setCallbacks(
            onOfferReceived = { theirMonster ->
                _state.value = TradeState.ReviewingOffer(theirMonster)
            },
            onTradeComplete = { received ->
                finaliseTrade(received)
            },
            onTradeCancelled = {
                handleCancellation()
            }
        )
        client.scanAndConnect(monster)
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_CONNECT)
    fun submitDecision(accepted: Boolean) {
        if (!accepted) {
            _state.value = TradeState.Cancelled
        } else {
            _state.value = TradeState.WaitingForPartner
        }

        // Forward decision to whichever BLE role is active
        server.submitLocalDecision(accepted)
        client.submitLocalDecision(accepted)
    }

    @RequiresPermission(allOf = [
        Manifest.permission.BLUETOOTH_ADVERTISE,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT
    ])
    private fun finaliseTrade(received: MonsterModel) {
        // Both sides confirmed, safe to persist
        saveMonster(received)
        _state.value = TradeState.Complete(received)
        cleanup()
    }

    @RequiresPermission(allOf = [
        Manifest.permission.BLUETOOTH_ADVERTISE,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT
    ])
    private fun handleCancellation() {
        _state.value = TradeState.Cancelled
        cleanup()
    }

    private fun saveMonster(monster: MonsterModel) {
        // TODO save monster to repository
    }

    @RequiresPermission(allOf = [
        Manifest.permission.BLUETOOTH_ADVERTISE,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT
    ])
    private fun cleanup() {
        server.stop()
        client.disconnect()
    }

    @RequiresPermission(allOf = [
        Manifest.permission.BLUETOOTH_ADVERTISE,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_CONNECT
    ])
    override fun onCleared() {
        super.onCleared()
        cleanup()
    }
}

sealed class TradeState {
    object Idle: TradeState()
    object Searching: TradeState()
    object WaitingForOffer: TradeState()
    data class ReviewingOffer(val theirMonster: MonsterModel): TradeState()
    object WaitingForPartner: TradeState()
    data class Complete(val received: MonsterModel): TradeState()
    object Cancelled: TradeState()
}