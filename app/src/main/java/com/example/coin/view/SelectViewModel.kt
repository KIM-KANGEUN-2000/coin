package com.example.coin.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coin.dataModel.CurrentPrice
import com.example.coin.dataModel.CurrentPriceResult
import com.example.coin.repository.NetWorkRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import timber.log.Timber

class SelectViewModel : ViewModel() {

    private val netWorkRepository = NetWorkRepository()

    private lateinit var currentPriceResultList: ArrayList<CurrentPriceResult>

    // 데이터변화를 관찰 LiveData
    private val _currentPriceResult = MutableLiveData<List<CurrentPriceResult>>()
    val currentPriceResult : LiveData<List<CurrentPriceResult>>
        get() = _currentPriceResult

    fun getCurrentCoinList() = viewModelScope.launch {

        val result = netWorkRepository.getCurrentCoinList()

        Timber.d(result.toString())

        currentPriceResultList = ArrayList()

        for (coin in result.data) {
            try {
                val gson = Gson()
                val gsonToJson = gson.toJson(result.data.get(coin.key))
                val gsonFromJson = gson.fromJson(gsonToJson, CurrentPrice::class.java)

                val currentPriceResult = CurrentPriceResult(coin.key, gsonFromJson)

                currentPriceResultList.add(currentPriceResult)

            }catch (e : Exception) {
                Timber.d(e.toString())
            }
        }

        _currentPriceResult.value = currentPriceResultList

    }

}