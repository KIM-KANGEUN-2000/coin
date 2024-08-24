package com.example.coin.view.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.coin.R
import com.example.coin.dataModel.CurrentPriceResult

class SelectRVAdapter(val context: Context, val coinPriceList: List<CurrentPriceResult>)
    :RecyclerView.Adapter<SelectRVAdapter.ViewHolder>() {

        val  selectedCoinList = ArrayList<String>()

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val coinName : TextView = view.findViewById(R.id.coinName)
        val coinPriceUpDown : TextView = view.findViewById(R.id.coinPriceUpDown)
        val likeImage : ImageView = view.findViewById(R.id.likeBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.intro_coin_item, parent, false)

        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return coinPriceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.coinName.text = coinPriceList[position].coinName

        val fluctate_24H = coinPriceList[position].coinInfo.fluctate_24H

        if(fluctate_24H.contains("-")) {
            holder.coinPriceUpDown.text = "하락입니다."
            holder.coinPriceUpDown.setTextColor(Color.parseColor("#114fed"))
        }else {
            holder.coinPriceUpDown.text = "상승입니다."
            holder.coinPriceUpDown.setTextColor(Color.parseColor("#ed2e11"))
        }

        val likeImage = holder.likeImage
        val currentCoin = coinPriceList[position].coinName

        // view를 그려줄 때 아래 코드가 없으면 리싸이클러뷰는 재활용을 하기 때문에 likeImage.setOnClickListener에서 선택된 하트가 스크롤시 반복되어 그려진다.
        if(selectedCoinList.contains(currentCoin)) {
            selectedCoinList.remove(currentCoin)
        } else {
            selectedCoinList.add(currentCoin)
        }

        likeImage.setOnClickListener {

            if(selectedCoinList.contains(currentCoin)) {
                // 포함하면
                selectedCoinList.remove(currentCoin)
                likeImage.setImageResource(R.drawable.like_grey)
            } else {
                // 포함하지 않으면
                selectedCoinList.add(currentCoin)
                likeImage.setImageResource(R.drawable.like_red)
            }
        }

    }

}